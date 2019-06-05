package com.rabbitcat.authorizeSever.domain.oauthClientDetails

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="oauth_client_details", schema = "oauth")
data class OauthClientDetails(
      @Id
      val clientId: String,
      val resourceIds: String?,
      val clientSecret: String,
      val scope: String,
      val authorizedGrantTypes: String,
      val webServerRedirectUri: String,
      val authorities: String,
      val accessTokenValidity: Int,
      val refreshTokenValidity: Int,
      val additionalInformation: String?,
      val autoapprove: String
    )
