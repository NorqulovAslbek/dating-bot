package com.example.datingbot.controller

import com.example.datingbot.entity.User
import com.example.datingbot.enums.Gender
import com.example.datingbot.enums.Step
import com.example.datingbot.service.UserService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import java.util.*

@Component
class BotHandler(
    private var userService: UserService
) : TelegramLongPollingBot() {
    private val deleteDate = mutableMapOf<Long, Int>()

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
                    showAlert = false // Alert chiqarishni xohlamasangiz, false qiling.
                })
                handleCallbackQuery(callbackQuery)
            }
        }
    }

    private fun handleCallbackQuery(callbackQuery: CallbackQuery) {
        val chatId = callbackQuery.message.chatId
        if (callbackQuery.data.all { it.isDigit() }) {
            val age = callbackQuery.data
            userService.saveAge(chatId, age)
            val messageId = deleteDate[chatId]
            if (messageId != null) {
                deleteMessage(chatId, messageId)
                deleteDate.remove(chatId)
                sendGenderKeyboardButton(chatId)
            }
        } else {
            val choseGender = callbackQuery.data
            if (choseGender == "MEN") {
                userService.updateGender(chatId, Gender.MEN)
            } else {
                userService.updateGender(chatId, Gender.WOMEN)
            }
            val messageId = deleteDate[chatId]
            if (messageId != null) {
                deleteMessage(chatId, messageId)
                deleteDate.remove(chatId)
            }
            userService.updateStep(chatId, Step.GENDER)
        }
    }

    private fun handleTextMessage(message: Message) {
        val chatId = message.chatId
        if (!userService.checkExistByUser(chatId)) {
            userService.save(User(chatId)) // boshida userni save qiladi chat id sini
            sendAgeInlineKeyboardButton(chatId)
            userService.updateStep(chatId, Step.AGE)
        } else {
            val userStep = userService.findByChatId(chatId)
            if (userStep != null) {
                execute(SendMessage().apply {
                    this.chatId = chatId.toString()
                    text = "mavjud bolmagan habarni jonatingiz\uD83E\uDD2C"
                })
            }
        }
    }

    private fun sendGenderKeyboardButton(chatId: Long) {
        val listColumn = LinkedList<List<InlineKeyboardButton>>()
        val listRow = LinkedList<InlineKeyboardButton>()
        listRow.add(
            InlineKeyboardButton().apply {
                text = "\uD83D\uDE4B\uD83C\uDFFB\u200D♂\uFE0F"
                callbackData = "MEN"
            }
        )
        listRow.add(
            InlineKeyboardButton().apply {
                text = "\uD83D\uDE4B\uD83C\uDFFC\u200D♀\uFE0F"
                callbackData = "WOMEN"
            }
        )
        listColumn.add(listRow)
        val inlineKeyboardMarkup = InlineKeyboardMarkup().apply {
            keyboard = listColumn
        }

        val sendMessage = SendMessage().apply {
            text = "Jinsingizni tanlang✨"
            this.chatId = chatId.toString()
            replyMarkup = inlineKeyboardMarkup
        }

        val messageId = execute(sendMessage).messageId
        deleteDate[chatId] = messageId
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
        val messageId = execute(sendMarkup).messageId
        deleteDate[chatId] = messageId
    }

    private fun deleteMessage(chatId: Long, messageId: Int) {
        try {
            execute(
                DeleteMessage(
                    chatId.toString(),
                    messageId
                )
            )
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }


}