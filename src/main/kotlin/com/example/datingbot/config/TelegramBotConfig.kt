package com.example.datingbot.config

import com.example.datingbot.controller.BotHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Configuration
class TelegramBotConfig(
    private val botHandler: BotHandler
) {
    @Bean
    fun botSession(): DefaultBotSession {
        TelegramBotsApi(DefaultBotSession::class.java).registerBot(botHandler)
        return DefaultBotSession()
    }


}