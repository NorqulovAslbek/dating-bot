package com.example.datingbot.serviceImpl

import com.example.datingbot.entity.User
import com.example.datingbot.enums.Gender
import com.example.datingbot.enums.Step
import com.example.datingbot.repository.UserRepository
import com.example.datingbot.service.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {
    override fun updateStep(chatId: Long, step: Step) {
        userRepository.updateStep(chatId, step)
    }

    override fun checkExistByUser(chatId: Long): Boolean {
        return userRepository.existsByChatId(chatId)
    }

    override fun save(user: User) {
        userRepository.save(user)
    }

    override fun saveAge(chatId: Long, age: String) {
        userRepository.updateAge(chatId,age)
    }

    override fun updateGender(chatId: Long, gender: Gender) {
       userRepository.updateGender(chatId,gender)
    }

    override fun findByChatId(chatId: Long): Step? {
        return userRepository.findByChatId(chatId)
    }
}