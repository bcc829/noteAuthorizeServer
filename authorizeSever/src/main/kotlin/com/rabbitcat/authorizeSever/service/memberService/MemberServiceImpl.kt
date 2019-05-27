package com.rabbitcat.authorizeSever.service.memberService

import com.rabbitcat.authorizeSever.domain.member.Member
import com.rabbitcat.authorizeSever.repository.member.MemberRepository
import com.rabbitcat.authorizeSever.util.ValidationUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
class MemberServiceImpl: MemberService {

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Transactional
    override fun addMember(member: Member): Member? {
        var saveMember = memberRepository.findByIdEquals(member.id)

        if(!ValidationUtil.isEmailFormat(member.email!!)){
            throw Exception()
        }

        return when(saveMember != null){
            true -> throw Exception()
            false ->{
                memberRepository.save(member)
            }
        }

    }
}