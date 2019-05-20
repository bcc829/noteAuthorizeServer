package com.rabbitcat.authorizeSever.handler

import com.rabbitcat.authorizeSever.service.userDetailsService.CustomUserDetailsService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class OAuth2SuccessHandler: AuthenticationSuccessHandler {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var customUserDetailsService: CustomUserDetailsService

    override fun onAuthenticationSuccess(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {

        val user  = customUserDetailsService.loadUserByUsername(authentication?.principal.toString())

        logger.info(authentication?.principal.toString())

        when(user == null){
            true -> response?.sendRedirect("/sign-up")
            false -> SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(user?.username, user?.password, user?.authorities)
        }
    }
}