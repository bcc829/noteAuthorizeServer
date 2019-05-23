package com.rabbitcat.authorizeSever.handler

import com.rabbitcat.authorizeSever.repository.member.MemberRepository
import com.rabbitcat.authorizeSever.service.userDetailsService.CustomUserDetailsService
import org.omg.CORBA.Request
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession
import javax.servlet.RequestDispatcher
import javax.servlet.ServletContext


@Component
class OAuth2SuccessHandler: AuthenticationSuccessHandler {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var customUserDetailsService: CustomUserDetailsService

    @Autowired
    lateinit var memberRepository: MemberRepository

    override fun onAuthenticationSuccess(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {

        logger.info(authentication?.principal.toString())

        val member = memberRepository.findBySnsPrincipal(authentication?.principal.toString())

        when (member == null) {
            true -> {
                val dispatcher = request?.getRequestDispatcher("/sign-up/oauth")
                request?.setAttribute("principal", authentication?.principal.toString())
                dispatcher?.forward(request, response)
            }
            false -> {
                val user = customUserDetailsService.loadUserByUsername(member.id)
                SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(user?.username, user?.password, user?.authorities)
            }
        }
    }
}