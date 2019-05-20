package com.rabbitcat.authorizeSever.controller

import com.rabbitcat.authorizeSever.service.ssoService.SsoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import javax.servlet.http.HttpServletRequest


@Controller
class SsoController {

    @Autowired
    lateinit var ssoService: SsoService

    @RequestMapping(value = ["/userLogout"])
    fun userLogout(@RequestParam(name = "clientId") clientId: String,
                   request: HttpServletRequest): String {
        //
        val userName = request.remoteUser
        val baseUri = ssoService.logoutAllClients(clientId, userName)

        request.session.invalidate()

        return "redirect:$baseUri"
    }
}