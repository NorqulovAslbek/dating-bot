package com.example.datingbot.controller

import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class BotHandler(): TelegramLongPollingBot() {
    override fun getBotUsername(): String {
        TODO("Not yet implemented")
    }

    override fun onUpdateReceived(update: Update?) {
        TODO("Not yet implemented")
    }
}