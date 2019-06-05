package com.rabbitcat.authorizeSever.authenticationProvider

import com.rabbitcat.authorizeSever.service.userDetailsService.IdUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class IdAuthenticationProvider: AuthenticationProvider {

    @Autowired
    lateinit var idUserDetailsService: IdUserDetailsService

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    override fun authenticate(authentication: Authentication?): Authentication {
        var userName = authentication?.principal.toString()
        var password = authentication?.credentials.toString()

        var customUserDetails = idUserDetailsService.loadUserByUsername(userName)

        when(customUserDetails == null){
            true -> throw UsernameNotFoundException("해당 회원의 정보가 없음")
            false -> {
                if(passwordEncoder.matches(password, customUserDetails.password))
                    return UsernamePasswordAuthenticationToken(userName, password, customUserDetails.authorities)
                else
                    throw BadCredentialsException("비밀번호가 맞지 않음")
            }
        }
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication?.equals(UsernamePasswordAuthenticationToken::class.java)!!
    }
}