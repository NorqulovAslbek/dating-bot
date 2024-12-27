package com.example.datingbot.service

import com.example.datingbot.entity.User
import com.example.datingbot.enums.Gender
import com.example.datingbot.enums.Step

interface UserService {
    fun updateStep(chatId: Long, step: Step)
    fun checkExistByUser(chatId: Long): Boolean
    fun save(user: User)
    fun saveAge(chatId: Long, age: String)
    fun updateGender(chatId: Long, gender: Gender)
     fun findByChatId(chatId: Long): Step?
}