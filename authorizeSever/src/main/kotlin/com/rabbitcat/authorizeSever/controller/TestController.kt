package com.rabbitcat.authorizeSever.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class TestController {
    @RequestMapping("/user", "/me")
    fun user(principal: Principal): Principal {
        return principal
    }
}