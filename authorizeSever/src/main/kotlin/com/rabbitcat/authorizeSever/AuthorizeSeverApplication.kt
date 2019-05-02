package com.rabbitcat.authorizeSever

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import javax.sql.DataSource
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.nio.file.attribute.GroupPrincipal
import java.security.Principal
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean




@SpringBootApplication
@EnableOAuth2Sso
@RestController
class AuthorizeSeverApplication

@Bean
fun oauth2ClientFilterRegistration(filter: OAuth2ClientContextFilter): FilterRegistrationBean<OAuth2ClientContextFilter> {
	val registration = FilterRegistrationBean<OAuth2ClientContextFilter>()
	registration.filter = filter
	registration.order = -100
	return registration
}

fun main(args: Array<String>) {
	runApplication<AuthorizeSeverApplication>(*args)
}
