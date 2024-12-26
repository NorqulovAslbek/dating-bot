package com.example.datingbot.repository

import com.example.datingbot.entity.User
import com.example.datingbot.enums.Step
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun existsByChatId(chatId: Long): Boolean

    @Modifying
    @Transactional
    @Query("update User u set u.step=?2 where u.chatId=?1")
    fun updateStep(chatId: Long, step: Step)
}