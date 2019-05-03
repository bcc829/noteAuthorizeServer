package com.rabbitcat.authorizeSever.clientResource

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails
import org.springframework.boot.context.properties.NestedConfigurationProperty



class ClientResources {
    @NestedConfigurationProperty
    private val client = AuthorizationCodeResourceDetails()

    @NestedConfigurationProperty
    private val resource = ResourceServerProperties()

    fun getClient(): AuthorizationCodeResourceDetails {
        return client
    }

    fun getResource(): ResourceServerProperties {
        return resource
    }
}