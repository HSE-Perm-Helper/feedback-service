package ru.melowetty.feedbackservice.service

import java.time.Duration
import java.util.UUID
import java.util.concurrent.TimeUnit
import org.awaitility.Awaitility.await
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.TestPropertySource
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import ru.melowetty.feedbackservice.repository.EventRepository


@SpringBootTest
@TestPropertySource(
    properties = [
        "spring.kafka.consumer.auto-offset-reset=earliest",
        "spring.datasource.url=jdbc:tc:postgresql:16-alpine:///db"
    ]
)
@Testcontainers
@ActiveProfiles("test")
class EventServiceTest {
    companion object {
        @Container
        @JvmStatic
        val kafka: KafkaContainer = KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:7.4.0")
        )

        @DynamicPropertySource
        @JvmStatic
        fun overrideProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers)
        }
    }

    @Autowired
    private lateinit var eventRepository: EventRepository

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, Map<String, UUID>>

    @Test
    fun shouldHandleNewUserEventAndAddToDb() {
        val uuid = UUID.randomUUID()
        val event = mapOf("target" to uuid)

        kafkaTemplate.send("user-events", "TEST", event)

        await()
            .pollInterval(Duration.ofSeconds(3))
            .atMost(10, TimeUnit.SECONDS)
            .untilAsserted { ->
                val events = eventRepository.findAll()
                println("test")
                assertEquals(events.size, 1)
                val event = events.first()
                assertEquals(event.target, uuid)
                assertEquals(event.type, "TEST")
            }
    }
}