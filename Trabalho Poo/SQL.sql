CREATE TABLE cliente_oficina (
 id SERIAL PRIMARY KEY,
 nome VARCHAR(150) NOT NULL,
 telefone VARCHAR(20),
 cpf VARCHAR(14) UNIQUE NOT NULL
);
CREATE TABLE ordem_servico (
 id SERIAL PRIMARY KEY,
 numero_os VARCHAR(30) UNIQUE NOT NULL,
 data_abertura DATE NOT NULL,
 descricao_defeito TEXT NOT NULL,
 valor_total NUMERIC(10,2) NOT NULL DEFAULT 0.00,
 cliente_id INT NOT NULL REFERENCES cliente_oficina(id) ON DELETE CASCADE
);