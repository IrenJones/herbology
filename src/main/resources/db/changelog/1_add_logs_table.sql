--liquibase formatted sql
--changeset Irina:add logs
CREATE TABLE logs(
   id SERIAL PRIMARY KEY,
   data VARCHAR(255)
);