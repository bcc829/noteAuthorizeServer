package com.rabbitcat.authorizeSever.controller

import org.springframework.web.bind.annotation.RequestMapping
import java.security.Principal

class AuthorizeController {

    @RequestMapping("/user")
    fun user(principal: Principal): Principal {
        return principal
    }
}