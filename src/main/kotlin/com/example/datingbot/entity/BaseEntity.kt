package com.example.datingbot.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @CreatedDate @Temporal(TemporalType.TIMESTAMP)
    var createdDate: Date? = null,
    @LastModifiedDate @Temporal(TemporalType.TIMESTAMP)
    var updateDate: Date? = null
)

