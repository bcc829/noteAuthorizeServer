package com.rabbitcat.authorizeSever.service.ssoService

interface SsoService {
    fun logoutAllClients(clientId: String, userName: String): String
}