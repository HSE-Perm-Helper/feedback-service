-- liquibase formatted sql

-- changeset denismalinin:1730491485355-1
CREATE SEQUENCE IF NOT EXISTS event_seq START WITH 1 INCREMENT BY 50;

-- changeset denismalinin:1730491485355-2
CREATE TABLE event
(
    id        BIGINT NOT NULL,
    type      VARCHAR(255),
    target    UUID,
    timestamp TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_event PRIMARY KEY (id)
);

