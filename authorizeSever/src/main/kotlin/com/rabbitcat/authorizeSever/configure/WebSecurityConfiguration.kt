package com.rabbitcat.authorizeSever.configure

import com.rabbitcat.authorizeSever.clientResource.ClientResources
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.web.filter.CompositeFilter
import java.util.*
import javax.servlet.Filter

@EnableWebSecurity
@Order(6)
class WebSecurityConfiguration: WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var oauth2ClientContext: OAuth2ClientContext

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**", "/webjars/**", "/error**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().exceptionHandling().authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/"))
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter::class.java)

    }

    private fun ssoFilter(): Filter {
        val filter = CompositeFilter()
        val filters = ArrayList<Filter>()
        filters.add(ssoFilter(facebook(), "/login/facebook"))
        filters.add(ssoFilter(github(), "/login/github"))
        filter.setFilters(filters)
        return filter
    }

    private fun ssoFilter(client: ClientResources, path: String ): Filter {
        val filter = OAuth2ClientAuthenticationProcessingFilter(path)
        val template = OAuth2RestTemplate(client.getClient(), oauth2ClientContext)
        filter.setRestTemplate(template)
        val tokenServices = UserInfoTokenServices(
                client.getResource().userInfoUri, client.getClient().clientId)
        tokenServices.setRestTemplate(template)
        filter.setTokenServices(tokenServices)
        return filter
    }

    @Bean
    fun oauth2ClientFilterRegistration(filter: OAuth2ClientContextFilter): FilterRegistrationBean<OAuth2ClientContextFilter> {
        val registration = FilterRegistrationBean<OAuth2ClientContextFilter>()
        registration.filter = filter
        registration.order = -100
        return registration
    }

    @Bean
    @ConfigurationProperties("github")
    fun github(): ClientResources {
        return ClientResources()
    }

    @Bean
    @ConfigurationProperties("facebook")
    fun facebook(): ClientResources {
        return ClientResources()
    }
}