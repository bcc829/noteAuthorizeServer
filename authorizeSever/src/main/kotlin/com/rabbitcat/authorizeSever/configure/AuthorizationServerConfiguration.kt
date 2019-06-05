package com.rabbitcat.authorizeSever.configure

import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.context.annotation.Primary
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore
import org.springframework.security.oauth2.provider.approval.ApprovalStore
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import javax.sql.DataSource


@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfiguration: AuthorizationServerConfigurerAdapter() {

    @Autowired
    lateinit var authorizationCodeServices: AuthorizationCodeServices

    @Autowired
    lateinit var approvalStore: ApprovalStore

    @Autowired
    lateinit var tokenStore: TokenStore

    @Autowired
    lateinit var clientDetailsService: ClientDetailsService

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Throws(Exception::class)
    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        //
        endpoints
                .tokenStore(tokenStore)
                .authorizationCodeServices(authorizationCodeServices)
                .approvalStore(approvalStore)
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.withClientDetails(clientDetailsService)
    }

    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
    }

    @Bean
    fun jdbcAuthorizationCodeServices(dataSource: DataSource): AuthorizationCodeServices {
        //
        return JdbcAuthorizationCodeServices(dataSource)
    }

    @Bean
    fun jdbcApprovalStore(dataSource: DataSource): ApprovalStore {
        //
        return JdbcApprovalStore(dataSource)
    }

    @Bean
    @Primary
    fun jdbcClientDetailsService(dataSource: DataSource): ClientDetailsService {

        var clientDetailsService = JdbcClientDetailsService(dataSource)

        clientDetailsService.setPasswordEncoder(passwordEncoder)

        return clientDetailsService
    }

    @Bean
    fun jdbcTokenStore(dataSource: DataSource): TokenStore {
        return JdbcTokenStore(dataSource)
    }
}