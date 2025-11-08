-- liquibase formatted sql

-- changeset kuat:1
INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER'),
       ('ROLE_MANAGER');