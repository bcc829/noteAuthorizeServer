package com.rabbitcat.authorizeSever.authenticationProvider

import com.rabbitcat.authorizeSever.service.userDetailsService.EmailUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class EmailAuthenticationProvider: AuthenticationProvider {

    @Autowired
    lateinit var emailUserDetailsService: EmailUserDetailsService

    override fun authenticate(authentication: Authentication?): Authentication {
        var userName = authentication?.principal.toString()
        var password = authentication?.credentials.toString()

        var customUserDetails = emailUserDetailsService.loadUserByUsername(userName)

        when(customUserDetails != null){
            true -> throw UsernameNotFoundException("해당 회원의 정보가 없음")
            false -> {
                if(password != customUserDetails?.password)
                    throw BadCredentialsException("비밀번호가 맞지 않음")
                else
                    return UsernamePasswordAuthenticationToken(userName, password, customUserDetails.authorities)
            }
        }


    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication?.equals(UsernamePasswordAuthenticationToken::class.java)!!
    }
}