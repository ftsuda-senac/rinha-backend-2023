CREATE EXTENSION IF NOT EXISTS pg_trgm;

DROP TABLE IF EXISTS pessoa;

CREATE TABLE pessoa
(
    id uuid NOT NULL,
  	nome varchar(100) NOT NULL,
    apelido varchar(32) NOT NULL,
    nascimento date NOT NULL,
    stack_db character varying,
    -- obtido em https://github.com/viniciusfonseca/rinha-backend-rust/blob/master/init.sql
    busca_helper text GENERATED ALWAYS AS (
        lower(nome || ';' || apelido || ';' || stack_db)
    ) stored,
    CONSTRAINT pk_pessoa_id PRIMARY KEY (id),
    CONSTRAINT uk_apelido UNIQUE (apelido)
);

-- Create index: https://www.postgresql.org/docs/current/sql-createindex.html
-- V0 - trimgram com todos os campos + gist (dessa forma o index não é usado nas busca
-- CREATE INDEX idx_busca ON pessoa USING gist (nome gist_trgm_ops, apelido gist_trgm_ops, stack_db gist_trgm_ops);

-- *** V1 - trimgram concatenando campos + gin (aparentemente tem melhor desempenho)
-- LEMBRAR DE ALTERAR A QUERY PARA CONCATENAR OS CAMPOS
-- CREATE INDEX CONCURRENTLY idx_busca ON pessoa USING gin((nome || ';' || apelido || ';' || stack_db) gin_trgm_ops);
CREATE INDEX CONCURRENTLY idx_busca ON pessoa USING gin(busca_helper gin_trgm_ops);

-- V2 - trimgram com campos separados + gin
-- CREATE INDEX CONCURRENTLY idx_busca_nome ON pessoa USING gin(nome gin_trgm_ops);
-- CREATE INDEX CONCURRENTLY idx_busca_apelido ON pessoa USING gin(apelido gin_trgm_ops);
-- CREATE INDEX CONCURRENTLY idx_busca_stack_db ON pessoa USING gin (stack_db gin_trgm_ops);