package com.rabbitcat.authorizeSever.service.memberService

import com.rabbitcat.authorizeSever.domain.member.Member

interface MemberService {
    fun addMember(member: Member):Member?
    fun userInfoDuplicationCheck(attribute: String, value: String): Boolean
}