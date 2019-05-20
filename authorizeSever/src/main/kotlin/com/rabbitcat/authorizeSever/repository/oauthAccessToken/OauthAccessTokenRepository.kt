package com.rabbitcat.authorizeSever.repository.oauthAccessToken

import com.rabbitcat.authorizeSever.domain.oauthAccessToken.OauthAccessToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.print.attribute.standard.JobOriginatingUserName

@Repository
interface OauthAccessTokenRepository: CrudRepository<OauthAccessToken, String>{
    fun findByTokenIdAndClientId(tokenId: String, clientId: String): OauthAccessToken

    fun deleteByUserName(userName: String): Int

    fun findByUserName(userName: String): List<OauthAccessToken>
}