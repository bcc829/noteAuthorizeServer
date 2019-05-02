package com.rabbitcat.authorizeSever.configure

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter
import javax.servlet.Filter
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.beans.factory.annotation.Autowired
import java.util.ArrayList
import org.springframework.web.filter.CompositeFilter








class WebSecurityConfigure: WebSecurityConfigurerAdapter() {

    @Autowired
    var oauth2ClientContext: OAuth2ClientContext? = null

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**", "/webjars/**", "/error**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
    }

    private fun ssoFilter(): Filter {
        val filter = CompositeFilter()
        val filters = ArrayList<Filter>()

        val facebookFilter = OAuth2ClientAuthenticationProcessingFilter("/login/facebook")
        val facebookTemplate = OAuth2RestTemplate(facebook(), oauth2ClientContext)
        facebookFilter.setRestTemplate(facebookTemplate)
        var tokenServices = UserInfoTokenServices(facebookResource().userInfoUri, facebook().clientId)
        tokenServices.setRestTemplate(facebookTemplate)
        facebookFilter.setTokenServices(tokenServices)
        filters.add(facebookFilter)

        val githubFilter = OAuth2ClientAuthenticationProcessingFilter("/login/github")
        val githubTemplate = OAuth2RestTemplate(github(), oauth2ClientContext)
        githubFilter.setRestTemplate(githubTemplate)
        tokenServices = UserInfoTokenServices(githubResource().getUserInfoUri(), github().getClientId())
        tokenServices.setRestTemplate(githubTemplate)
        githubFilter.setTokenServices(tokenServices)
        filters.add(githubFilter)

        filter.setFilters(filters)
        return filter

    }

    @Bean
    @ConfigurationProperties("facebook.client")
    fun facebook(): AuthorizationCodeResourceDetails {
        return AuthorizationCodeResourceDetails()
    }

    @Bean
    @ConfigurationProperties("facebook.resource")
    fun facebookResource(): ResourceServerProperties {
        return ResourceServerProperties()
    }

    @Bean
    @ConfigurationProperties("github.client")
    fun github(): AuthorizationCodeResourceDetails {
        return AuthorizationCodeResourceDetails()
    }

    @Bean
    @ConfigurationProperties("github.resource")
    fun githubResource(): ResourceServerProperties {
        return ResourceServerProperties()
    }
}