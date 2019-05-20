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
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import javax.sql.DataSource


@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfiguration: AuthorizationServerConfigurerAdapter() {

    @Autowired
    private val authorizationCodeServices: AuthorizationCodeServices? = null

    @Autowired
    private val approvalStore: ApprovalStore? = null

    @Autowired
    private val tokenStore: TokenStore? = null

    @Throws(Exception::class)
    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        //
        endpoints
                .tokenStore(tokenStore)
                .authorizationCodeServices(authorizationCodeServices)
                .approvalStore(approvalStore)
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
        //
        return JdbcClientDetailsService(dataSource)
    }

    @Bean
    fun jdbcTokenStore(dataSource: DataSource): TokenStore {
        //
        return JdbcTokenStore(dataSource)
    }
}