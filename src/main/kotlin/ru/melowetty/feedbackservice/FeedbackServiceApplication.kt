package ru.melowetty.feedbackservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FeedbackServiceApplication

fun main(args: Array<String>) {
    runApplication<FeedbackServiceApplication>(*args)
}
