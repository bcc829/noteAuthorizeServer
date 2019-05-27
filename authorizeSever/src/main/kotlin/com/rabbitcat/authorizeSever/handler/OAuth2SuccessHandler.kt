package com.rabbitcat.authorizeSever.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.fasterxml.jackson.databind.node.ObjectNode
import com.rabbitcat.authorizeSever.authenticationProvider.Oauth2AuthenticationProvider
import com.rabbitcat.authorizeSever.repository.member.MemberRepository
import com.rabbitcat.authorizeSever.service.userDetailsService.IdUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class OAuth2SuccessHandler: SavedRequestAwareAuthenticationSuccessHandler() {

    //private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var idUserDetailsService: IdUserDetailsService

    @Autowired
    lateinit var memberRepository: MemberRepository

    override fun onAuthenticationSuccess(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {

        logger.info(authentication?.principal.toString())

        var member = memberRepository.findBySnsPrincipal(authentication?.principal.toString())

        val userInfo = (authentication as OAuth2Authentication).userAuthentication.details as HashMap<String, String>

        var userEmail: String? = null

        logger.info("sns user info: $userInfo")

        //이메일 정보가 있으면 같은 이메일을 가진 member의 정보를 가져옴
        if(member == null){
            if(userInfo.containsKey("email") && userInfo["email"] != null){
                userEmail = userInfo["email"]
                member = memberRepository.findByEmail(userEmail!!)
            }
        }

        when (member == null) {
            true -> {
                request?.setAttribute("principal", authentication.principal.toString())
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