package ru.melowetty.feedbackservice.service

import java.time.LocalDateTime
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Service
import ru.melowetty.feedbackservice.entity.Event
import ru.melowetty.feedbackservice.model.KafkaEvent
import ru.melowetty.feedbackservice.repository.EventRepository

@Service
class EventService(
    private val eventRepository: EventRepository,
) {
    @KafkaListener(topics = ["user-events"], groupId = "feedback-service")
    fun consumeNewNotification(
        payload: KafkaEvent,
        @Header(KafkaHeaders.RECEIVED_KEY) key: String
    ) {
        val newEvent = Event(
            type = key,
            source = payload.source,
            timestamp = LocalDateTime.now()
        )

        eventRepository.save(newEvent)
    }
}