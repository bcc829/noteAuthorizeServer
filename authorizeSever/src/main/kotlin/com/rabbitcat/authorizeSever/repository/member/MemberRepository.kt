package com.rabbitcat.authorizeSever.repository.member

import com.rabbitcat.authorizeSever.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Number> {
    fun findByIdEquals(id: String): Member?
    fun findBySnsPrincipal(snsPrincipal: String): Member?
}