package com.rabbitcat.authorizeSever.domain.oauthAccessToken

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="oauth_access_token", schema = "oauth")
data class OauthAccessToken(
        @Id
        val tokenId: String,

        val token: String,

        @Column(name = "user_name")
        val userName: String,

        @Column(name = "authentication_id")
        val authenticationId: String,

        @Column(name = "client_id")
        val clientId: String,

        val authentication: String)