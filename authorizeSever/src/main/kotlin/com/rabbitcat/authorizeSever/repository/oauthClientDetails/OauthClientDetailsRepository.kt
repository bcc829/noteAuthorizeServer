package com.rabbitcat.authorizeSever.repository.oauthClientDetails

import com.rabbitcat.authorizeSever.domain.oauthClientDetails.OauthClientDetails
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OauthClientDetailsRepository: CrudRepository<OauthClientDetails, String> {
    fun findTopByClientId(clientId: String): OauthClientDetails
}