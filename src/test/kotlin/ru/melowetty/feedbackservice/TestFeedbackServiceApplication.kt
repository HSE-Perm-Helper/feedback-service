package ru.melowetty.feedbackservice

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<FeedbackServiceApplication>().with(TestcontainersConfiguration::class).run(*args)
}
