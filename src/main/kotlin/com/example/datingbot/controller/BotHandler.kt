package com.example.datingbot.controller

import com.example.datingbot.entity.User
import com.example.datingbot.enums.Step
import com.example.datingbot.service.UserService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import java.util.*

@Component
class BotHandler(
    private var userService: UserService
) : TelegramLongPollingBot() {

    override fun getBotUsername(): String {
        return "@asl_bec_bot"
    }


    override fun getBotToken(): String {
        return "6608269679:AAGM8vtudTooiUKpvQVyvlnCOAjV5vdFbBE"
    }

    override fun onUpdateReceived(update: Update) {
        when {
            update.hasMessage() -> {
                val message = update.message
                when {
                    message.hasText() -> handleTextMessage(message)
                }
            }

            update.hasCallbackQuery() -> {
                val callbackQuery = update.callbackQuery
                // Tugma bosilgandan keyingi holatni o'chirish
                execute(AnswerCallbackQuery().apply {
                    callbackQueryId = callbackQuery.id
                    text = "Siz ${callbackQuery.data}ni tanladingiz!"
                    showAlert = false // Alert chiqarishni xohlamasangiz, false qiling
                })
                handleCallbackQuery(callbackQuery)
            }
        }
    }

    private fun handleCallbackQuery(callbackQuery: CallbackQuery) {
        TODO("Not yet implemented")
    }

    private fun handleTextMessage(message: Message) {
        val chatId = message.chatId
        if (!userService.checkExistByUser(chatId)) {
            userService.save(User(chatId)) // boshida userni save qiladi chat id sini
            sendAgeInlineKeyboardButton(chatId)
            userService.updateStep(chatId, Step.AGE)
        }
    }


    private fun sendAgeInlineKeyboardButton(chatId: Long) {
        val listColumn = LinkedList<List<InlineKeyboardButton>>()
        val listRow1 = LinkedList<InlineKeyboardButton>()
        val listRow2 = LinkedList<InlineKeyboardButton>()
        val listRow3 = LinkedList<InlineKeyboardButton>()

        listRow1.add(
            InlineKeyboardButton().apply {
                text = "16"
                callbackData = "16"
            }
        )
        listRow1.add(
            InlineKeyboardButton().apply {
                text = "17"
                callbackData = "17"
            }
        )
        listRow2.add(
            InlineKeyboardButton().apply {
                text = "18"
                callbackData = "18"
            }
        )
        listRow2.add(
            InlineKeyboardButton().apply {
                text = "19"
                callbackData = "19"
            }
        )
        listRow3.add(
            InlineKeyboardButton().apply {
                text = "20⏫"
                callbackData = "20"
            }
        )
        listColumn.add(listRow1)
        listColumn.add(listRow2)
        listColumn.add(listRow3)

        val inlineKeyboardMarkup = InlineKeyboardMarkup().apply {
            keyboard = listColumn
        }

        val sendMarkup = SendMessage().apply {
            this.chatId = chatId.toString()
            text = "Yoshingizni tanlang! ✳\uFE0F"
            replyMarkup = inlineKeyboardMarkup
        }

        execute(sendMarkup)
    }


}