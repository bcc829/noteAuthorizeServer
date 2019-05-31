package com.rabbitcat.authorizeSever.domain.socialMemberInfo

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.GeneratorType
import javax.persistence.*

@Entity
@Table(name="social_member_info", schema = "public")
@DynamicInsert
data class SocialMemberInfo(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "public.social_member_info_seq_id_seq")
    val seqId: Int? = null,
    val memberId: String,
    val providerType: String,
    val principal: String
)