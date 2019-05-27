package com.rabbitcat.authorizeSever.service.userDetailsService

import com.rabbitcat.authorizeSever.domain.userDetails.CustomUserDetails
import com.rabbitcat.authorizeSever.repository.member.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class EmailUserDetailsService: UserDetailsService {
    @Autowired
    lateinit var memberRepository: MemberRepository

    override fun loadUserByUsername(email: String): UserDetails? {

        var member = memberRepository.findByEmail(email)

        return when (member == null) {
            true -> null
            false -> CustomUserDetails(member.id, member.password, member.phoneNumber, member.address, member.nickname, member.email)

        }
    }
}