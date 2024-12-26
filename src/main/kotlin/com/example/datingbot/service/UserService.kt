package com.example.datingbot.service

import com.example.datingbot.entity.User
import com.example.datingbot.enums.Step

interface UserService {
    fun updateStep(chatId: Long, step: Step)
    fun checkExistByUser(chatId: Long): Boolean
    fun save(user: User)
}