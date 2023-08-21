CREATE EXTENSION IF NOT EXISTS pg_trgm;

DROP TABLE IF EXISTS pessoa;

CREATE TABLE pessoa
(
    id uuid NOT NULL,
  	nome varchar(100) NOT NULL,
    apelido varchar(32) NOT NULL,
    nascimento date NOT NULL,
    stack_db character varying,
    CONSTRAINT pk_pessoa_id PRIMARY KEY (id),
    CONSTRAINT uk_apelido UNIQUE (apelido)
);

CREATE INDEX idx_busca ON pessoa USING gist (nome gist_trgm_ops, apelido gist_trgm_ops, stack_db gist_trgm_ops);