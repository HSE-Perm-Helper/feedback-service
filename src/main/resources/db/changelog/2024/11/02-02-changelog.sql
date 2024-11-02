-- liquibase formatted sql

-- changeset denismalinin:1730508833540-1
ALTER TABLE event
    RENAME COLUMN target TO source;

