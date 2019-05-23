package com.rabbitcat.authorizeSever.controller

import com.rabbitcat.authorizeSever.service.ssoService.SsoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.security.Principal
import javax.servlet.http.HttpServletRequest
import java.util.LinkedHashMap




@Controller
class SsoController {

    @Autowired
    lateinit var ssoService: SsoService


    @RequestMapping("/user", "/me")
    fun user(principal: Principal): Map<String, String> {
        val map = LinkedHashMap<String, String>()
        map["name"] = principal.name
        return map
    }

    @GetMapping("/sign-up/oauth")
    fun signUpPage(model: Model): String{

        return "sign-up"
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