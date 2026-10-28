-- ================================================================
-- BASE DE DADOS DE EMPREGADOS E DEPARTAMENTOS (JDBC + POSTGRESQL)
-- Execute no psql:  psql -U postgres -f bddepartamentos.sql
-- (ou no pgAdmin: rode o CREATE DATABASE e depois as tabelas)
-- ================================================================

CREATE DATABASE bddepartamentos;

-- Conecta-se ao banco recém-criado (meta-comando do psql):
\c bddepartamentos

CREATE TABLE departamento (
    sigla    VARCHAR(10)  PRIMARY KEY,
    nome     VARCHAR(100) NOT NULL
);

CREATE TABLE empregado (
    cpf                VARCHAR(11)  PRIMARY KEY,
    nome               VARCHAR(100) NOT NULL,
    departamento_sigla VARCHAR(10)  NOT NULL,
    CONSTRAINT fk_empregado_departamento
        FOREIGN KEY (departamento_sigla) REFERENCES departamento (sigla)
);

-- Todo departamento pode ter um gerente, que é um empregado da empresa.
-- Por isso o relacionamento é circular (departamento -> empregado ->
-- departamento) e a coluna gerente_cpf é adicionada após as duas tabelas:
ALTER TABLE departamento
    ADD COLUMN gerente_cpf VARCHAR(11),
    ADD CONSTRAINT fk_departamento_gerente
        FOREIGN KEY (gerente_cpf) REFERENCES empregado (cpf);
