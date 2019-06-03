package com.rabbitcat.authorizeSever.handler

import com.rabbitcat.authorizeSever.domain.member.Member
import com.rabbitcat.authorizeSever.domain.socialMemberInfo.SocialMemberInfo
import com.rabbitcat.authorizeSever.enum.ProviderType
import com.rabbitcat.authorizeSever.repository.member.MemberRepository
import com.rabbitcat.authorizeSever.repository.socialMemberInfo.SocialMemberInfoRepository
import com.rabbitcat.authorizeSever.service.ssoService.SsoService
import com.rabbitcat.authorizeSever.service.userDetailsService.IdUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
@SuppressWarnings("unchecked")
class OAuth2SuccessHandler: SavedRequestAwareAuthenticationSuccessHandler() {

    //private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var idUserDetailsService: IdUserDetailsService

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Autowired
    lateinit var socialMemberInfoRepository: SocialMemberInfoRepository

    @Autowired
    lateinit var ssoService: SsoService

    override fun onAuthenticationSuccess(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {

        val oAuth2Authentication = (authentication as OAuth2Authentication)

        val userInfo = oAuth2Authentication.userAuthentication.details as HashMap<String, String>

        val clientId = oAuth2Authentication.oAuth2Request.clientId

        var member: Member? = null

        var userEmail: String? = userInfo["email"]

        val providerType = ProviderType.from(clientId).name

        logger.info("sns user info: $userInfo")

        when(userEmail.isNullOrEmpty()){
            false -> {
                member = memberRepository.findByEmail(userEmail)
                if(member != null){
                    //sns를 통해 가입은 하지 않았으나 sns로 로그인 하였으므로 SocialMemberInfo에 추가
                    if(socialMemberInfoRepository.findByMemberIdAndProviderType(member.id, providerType) == null){
                        val socialMemberInfo = SocialMemberInfo(memberId = member.id, providerType = providerType, principal = authentication.principal.toString())
                        ssoService.addSocialUserInfo(socialMemberInfo)
                    }
                }
            }

            true -> {
                //sns principal 로그인
                val socialMemberInfo = socialMemberInfoRepository.findByPrincipal(authentication.principal.toString())
                if(socialMemberInfo != null){
                    member = memberRepository.findByIdEquals(socialMemberInfo.memberId)
                }
            }
        }

        when (member == null) {
            true -> {
                request?.setAttribute("email", userEmail)
                request?.setAttribute("providerType", providerType)
                request?.setAttribute("principal", authentication.principal.toString())
                request?.getRequestDispatcher("/sign-up/oauth")?.forward(request, response)
            }
            false -> {
                val user = idUserDetailsService.loadUserByUsername(member.id)
                SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(user?.username, user?.password, user?.authorities)
                super.onAuthenticationSuccess(request, response, authentication)
            }
        }
    }
}