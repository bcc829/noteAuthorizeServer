package com.rabbitcat.authorizeSever.service.ssoService

import com.rabbitcat.authorizeSever.domain.socialMemberInfo.SocialMemberInfo

interface SsoService {
    fun logoutAllClients(clientId: String, userName: String): String
    fun addSocialUserInfo(socialMemberInfo: SocialMemberInfo)
}