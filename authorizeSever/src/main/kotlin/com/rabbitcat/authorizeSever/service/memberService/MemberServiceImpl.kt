package com.rabbitcat.authorizeSever.service.memberService

import com.rabbitcat.authorizeSever.domain.member.Member
import com.rabbitcat.authorizeSever.enum.CheckAttribute
import com.rabbitcat.authorizeSever.repository.member.MemberRepository
import com.rabbitcat.authorizeSever.util.ValidationUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
class MemberServiceImpl: MemberService {

    @Autowired
    lateinit var memberRepository: MemberRepository

    private val logger = LoggerFactory.getLogger(this.javaClass)

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

    override fun userInfoDuplicationCheck(attribute: String, value: String): Boolean {

        if (value.isEmpty())
            return false

        val checkAttribute = CheckAttribute.values().filter { it.name.equals(attribute, ignoreCase = true) }

        return when (checkAttribute.isEmpty() || checkAttribute.size > 2) {
            true -> return false
            false -> {
                val dupCheckAttribute = checkAttribute[0]
                when (dupCheckAttribute) {
                    CheckAttribute.EMAIL -> memberRepository.findByEmail(value) == null
                    CheckAttribute.USERNAME -> memberRepository.findByIdEquals(value) == null
                    CheckAttribute.NICKNAME -> memberRepository.findByNickname(value) == null
                }
            }

        }
    }
}