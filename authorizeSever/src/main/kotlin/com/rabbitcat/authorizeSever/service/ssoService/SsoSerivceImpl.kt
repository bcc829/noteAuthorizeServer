package com.rabbitcat.authorizeSever.service.ssoService

import com.rabbitcat.authorizeSever.repository.oauthAccessToken.OauthAccessTokenRepository
import com.rabbitcat.authorizeSever.repository.oauthClientDetails.OauthClientDetailsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
class SsoSerivceImpl: SsoService {

    @Autowired
    lateinit var oauthAccessTokenRepository: OauthAccessTokenRepository

    @Autowired
    lateinit var oauthClientDetailsRepository: OauthClientDetailsRepository

    @Transactional
    override fun logoutAllClients(clientId: String, userName: String): String {
        oauthAccessTokenRepository.deleteByUserName(userName)

        val oauthClientDetails = oauthClientDetailsRepository.findTopByClientId(clientId)

        return oauthClientDetails.baseUri
    }

}