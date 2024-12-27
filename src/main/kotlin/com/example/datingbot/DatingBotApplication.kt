package com.example.datingbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class DatingBotApplication

fun main(args: Array<String>) {
    runApplication<DatingBotApplication>(*args)
}
