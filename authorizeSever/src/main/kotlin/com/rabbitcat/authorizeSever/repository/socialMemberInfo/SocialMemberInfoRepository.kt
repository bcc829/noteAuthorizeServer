package com.rabbitcat.authorizeSever.repository.socialMemberInfo

import com.rabbitcat.authorizeSever.domain.socialMemberInfo.SocialMemberInfo
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SocialMemberInfoRepository: CrudRepository<SocialMemberInfo, String> {
    fun findByPrincipal(principal: String): SocialMemberInfo?
    fun findByMemberIdAndProviderType(userId: String, providerType: String): SocialMemberInfo?
}