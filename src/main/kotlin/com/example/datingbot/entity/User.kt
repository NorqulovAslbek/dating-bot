package com.example.datingbot.entity

import com.example.datingbot.enums.Gender
import com.example.datingbot.enums.Step
import jakarta.persistence.*


@Entity
@Table(name = "users")
class User(
    val chatId: Long,
    var age: Int? = null,
    @Enumerated(EnumType.STRING)
    var gender: Gender? = null,
    @Enumerated(EnumType.STRING)
    var step: Step? = null
) : BaseEntity()