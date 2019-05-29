package com.rabbitcat.authorizeSever.service.memberService

import com.rabbitcat.authorizeSever.domain.member.Member
import com.rabbitcat.authorizeSever.enum.CheckAttribute

interface MemberService {
    fun addMember(member: Member):Member?
    fun userInfoDuplicationCheck(attribute: String, value: String): Boolean
}