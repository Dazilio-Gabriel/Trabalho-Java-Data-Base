/* cria o esquema */
CREATE SCHEMA IF NOT EXISTS controle_estoque_novo;

USE controle_estoque_novo;

DROP TABLE IF EXISTS movimentacao;
DROP TABLE IF EXISTS produtos;

/* cria as tabelas do banco (produtos / movimentacao */
CREATE TABLE produtos (
  id_produto INT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(100) NOT NULL,
  descricao TEXT,
  quantidade_estoque INT NOT NULL,
  sr_deleted CHAR(1) NOT NULL DEFAULT 'F',
  INDEX idx_nome_produto (nome)
);

CREATE TABLE movimentacao (
  id_movimentacao INT PRIMARY KEY AUTO_INCREMENT,
  id_produto INT NOT NULL,
  tipo_movimentacao VARCHAR(10) NOT NULL,
  quantidade INT NOT NULL,
  data_movimentacao DATE NOT NULL,
  sr_deleted CHAR(1) NOT NULL DEFAULT 'F',
  FOREIGN KEY (id_produto) REFERENCES produtos(id_produto)
);