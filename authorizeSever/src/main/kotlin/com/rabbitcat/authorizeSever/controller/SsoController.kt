package com.rabbitcat.authorizeSever.controller

import com.rabbitcat.authorizeSever.domain.member.Member
import com.rabbitcat.authorizeSever.service.memberService.MemberService
import com.rabbitcat.authorizeSever.service.ssoService.SsoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.security.Principal
import java.util.*
import javax.servlet.http.HttpServletRequest


@Controller
class SsoController {

    @Autowired
    lateinit var ssoService: SsoService

    @Autowired
    lateinit var memberService: MemberService

    @RequestMapping("/user", "/me")
    fun user(principal: Principal): Map<String, String> {
        val map = LinkedHashMap<String, String>()
        map["name"] = principal.name
        return map
    }

    @RequestMapping("/sign-up/oauth")
    fun signUpPage(): String{

        return "sign-up"
    }

    @PostMapping("/sign-up/oauth")
    fun registUesr(@RequestParam("id", required = true) id: String, @RequestParam("password", required = true) password: String,
                   @RequestParam("nick_name", required = true) nickName: String, @RequestParam("address", required = false) address: String?,
                   @RequestParam("phone_number", required = false) phoneNumber: String?, @RequestParam("email", required = false) email: String?): String{

        val member = Member(id = id, password = password, nickname = nickName, address = address, phoneNumber = phoneNumber,
                email = email)

        try {
            memberService.addMember(member)
        } catch (e: Exception){
            return "redirect:/sign-up/oauth?error"
        }
        return "redirect:/?register"
    }

    @RequestMapping("/userLogout")
    fun userLogout(@RequestParam(name = "clientId") clientId: String,
                   request: HttpServletRequest): String {
        //
        val userName = request.remoteUser
        val baseUri = ssoService.logoutAllClients(clientId, userName)

        request.session.invalidate()

        return "redirect:$baseUri"
    }
}