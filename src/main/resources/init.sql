-- Database: publishers

-- DROP DATABASE publishers;

CREATE DATABASE publishers
    WITH
    OWNER = ana
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- Table: public.publisher

-- DROP TABLE public.publisher;

CREATE TABLE public.publisher
(
    id bigint NOT NULL,
    active boolean NOT NULL,
    code character varying(255) COLLATE pg_catalog."default",
    file character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT publisher_pkey PRIMARY KEY (id)
)