package com.example.datingbot.serviceImpl

import com.example.datingbot.entity.User
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
}