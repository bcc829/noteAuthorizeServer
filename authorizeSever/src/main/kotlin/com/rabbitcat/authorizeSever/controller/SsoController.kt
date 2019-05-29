package com.rabbitcat.authorizeSever.controller

import com.rabbitcat.authorizeSever.domain.member.Member
import com.rabbitcat.authorizeSever.enum.CheckAttribute
import com.rabbitcat.authorizeSever.service.memberService.MemberService
import com.rabbitcat.authorizeSever.service.ssoService.SsoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
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

    @GetMapping("/sign-up/oauth")
    fun signUpPage(): String{
        return "sign-up"
    }

    @RequestMapping("/duplication_check/{checkAttribute}/{value}")
    @ResponseBody
    fun duplicationCheck(@PathVariable("checkAttribute") attribute: String, @PathVariable("value") value: String): Boolean{
        return memberService.userInfoDuplicationCheck(attribute, value.trim())
    }

    @PostMapping("/sign-up/oauth")
    fun registUesr(@RequestParam("username", required = true) username: String, @RequestParam("password", required = true) password: String,
                   @RequestParam("nickname", required = true) nickname: String, @RequestParam("address", required = false) address: String?,
                   @RequestParam("phone_number", required = false) phoneNumber: String?, @RequestParam("email", required = false) email: String?,
                   request: HttpServletRequest): String{

        val member = Member(id = username.trim(), password = password.trim(), nickname = nickname.trim(), address = address?.trim(), phoneNumber = phoneNumber?.trim(),
                email = email?.trim())

        try {
            memberService.addMember(member)
        } catch (e: Exception){
            return "redirect:/sign-up/oauth?error"
        }

        return "redirect:/?registration"
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