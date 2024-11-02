package ru.melowetty.feedbackservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.melowetty.feedbackservice.entity.Event

@Repository
interface EventRepository : JpaRepository<Event, Long>