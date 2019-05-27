package com.rabbitcat.authorizeSever.domain.member

import org.hibernate.annotations.DynamicInsert
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "member", schema = "public")
@DynamicInsert
data class Member(
        val seqId : Int? = null,
        @Id
        val id: String,
        var password: String,
        var phoneNumber: String?,
        var address: String?,
        var nickname: String,
        var email: String?,
        val regDate: Date? = Date()
        )