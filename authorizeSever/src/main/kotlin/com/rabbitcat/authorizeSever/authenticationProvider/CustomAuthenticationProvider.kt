package com.rabbitcat.authorizeSever.authenticationProvider

import com.rabbitcat.authorizeSever.service.userDetailsService.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationProvider: AuthenticationProvider {

    @Autowired
    lateinit var customUserDetailsService: CustomUserDetailsService

    override fun authenticate(authentication: Authentication?): Authentication {
        var userName = authentication?.principal.toString()
        var password = authentication?.credentials.toString()

        var customUserDetails = customUserDetailsService.loadUserByUsername(userName)

        if(password != customUserDetails?.password)
            throw Exception()
        else
            return UsernamePasswordAuthenticationToken(userName, password, customUserDetails?.authorities)
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return true
    }
}