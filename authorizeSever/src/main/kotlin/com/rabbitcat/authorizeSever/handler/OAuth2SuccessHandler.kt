package com.rabbitcat.authorizeSever.handler

import com.rabbitcat.authorizeSever.repository.member.MemberRepository
import com.rabbitcat.authorizeSever.service.userDetailsService.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class OAuth2SuccessHandler: SavedRequestAwareAuthenticationSuccessHandler() {

    //private val logger = LoggerFactory.getLogger(javaClass)

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
                super.onAuthenticationSuccess(request, response, authentication)
            }
        }
    }
}