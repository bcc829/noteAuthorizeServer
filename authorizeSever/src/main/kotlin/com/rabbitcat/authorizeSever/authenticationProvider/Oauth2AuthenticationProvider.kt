package com.rabbitcat.authorizeSever.authenticationProvider

import com.rabbitcat.authorizeSever.repository.member.MemberRepository
import com.rabbitcat.authorizeSever.service.userDetailsService.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class Oauth2AuthenticationProvider: AuthenticationProvider {

    @Autowired
    lateinit var customUserDetailsService: CustomUserDetailsService

    @Autowired
    lateinit var memberRepository: MemberRepository

    override fun authenticate(authentication: Authentication?): Authentication {
        var principal = authentication?.principal.toString()

        var user = memberRepository.findBySnsPrincipal(principal)

        when(user != null){
            true -> {
                var customUserDetails = customUserDetailsService.loadUserByUsername(user.id)!!
                return UsernamePasswordAuthenticationToken(customUserDetails.username, customUserDetails.password, customUserDetails.authorities)
            }
            false -> {
                throw UsernameNotFoundException("해당 SNS로 가입한 회원이 없음")
            }
        }

    }

    override fun supports(authentication: Class<*>?): Boolean {
        return true
    }
}