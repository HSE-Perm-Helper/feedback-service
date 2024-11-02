package ru.melowetty.feedbackservice.model

import java.util.UUID

data class KafkaEvent(
    val target: UUID,
)