package com.rabbitcat.authorizeSever.handler

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class OAuth2FailerHandler: AuthenticationFailureHandler {
    override fun onAuthenticationFailure(request: HttpServletRequest?, response: HttpServletResponse?, exception: AuthenticationException?) {
        val dispatcher = request?.getRequestDispatcher("/sign-up/oauth")
        dispatcher?.forward(request, response)

    }
}