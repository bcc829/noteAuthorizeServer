package com.rabbitcat.authorizeSever

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore


@SpringBootApplication
@EnableOAuth2Client
@EnableAuthorizationServer
class AuthorizeSeverApplication

@Bean
fun redisTokenStore(redisConnectionFactory: RedisConnectionFactory): RedisTokenStore {
	return RedisTokenStore(redisConnectionFactory)
}

fun main(args: Array<String>) {
	runApplication<AuthorizeSeverApplication>(*args)
}
