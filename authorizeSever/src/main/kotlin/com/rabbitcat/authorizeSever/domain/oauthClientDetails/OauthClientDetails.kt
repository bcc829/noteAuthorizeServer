package com.rabbitcat.authorizeSever.domain.oauthClientDetails

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="oauth_client_details", schema = "oauth")
data class OauthClientDetails(
      @Id
      @Column(name = "client_id")
      val clientId: String,

      @Column(name = "web_server_redirect_uri")
      val redirectUri: String,

      @Column(name = "logout_uri")
      val logoutUri: String,

      @Column(name = "base_uri")
      val baseUri: String
    )
