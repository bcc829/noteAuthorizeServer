package com.rabbitcat.authorizeSever.domain.userDetails

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.ArrayList



class CustomUserDetails(    val id: String,
                            var user_password: String,
                            var phoneNumber: String?,
                            var address: String?,
                            var nickname: String,
                            var email: String?): UserDetails {


    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val auth = ArrayList<GrantedAuthority>()
        auth.add(SimpleGrantedAuthority("ROLE_USER"))
        return auth

    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getUsername(): String {
        return id
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun getPassword(): String {
        return user_password
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }
}