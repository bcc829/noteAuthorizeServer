package com.rabbitcat.authorizeSever.handler

import com.rabbitcat.authorizeSever.domain.member.Member
import com.rabbitcat.authorizeSever.repository.member.MemberRepository
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

    override fun onAuthenticationSuccess(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {

        val userInfo = (authentication as OAuth2Authentication).userAuthentication.details as HashMap<String, String>

        var member: Member? = null

        var userEmail: String? = userInfo["email"]

        logger.info("sns user info: $userInfo")

        when(userEmail.isNullOrEmpty()){
            false -> {
                member = memberRepository.findByEmail(userEmail)
            }
        }

        when (member == null) {
            true -> {
                request?.setAttribute("email", userEmail)

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