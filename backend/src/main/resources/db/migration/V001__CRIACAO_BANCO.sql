CREATE SCHEMA projeto;

CREATE SEQUENCE IF NOT EXISTS projeto.empresa_id_seq;

CREATE TABLE IF NOT EXISTS projeto.empresa (
    id INTEGER NOT NULL DEFAULT nextval('projeto.empresa_id_seq'::regclass),
    razao_social VARCHAR(64) NOT NULL,
    cnpj VARCHAR(32) NOT NULL,
    lograduro VARCHAR(64),
    municipio VARCHAR(64),
    numero VARCHAR(10),
    complemento VARCHAR(64),
    bairro VARCHAR(64),
    cep VARCHAR(16),
    telefone VARCHAR(32),
    email VARCHAR(254) NOT NULL,
    site VARCHAR(254),
    usuario VARCHAR(20) NOT NULL,
    senha VARCHAR(128),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,

    CONSTRAINT empresa_pk PRIMARY KEY (id),
    CONSTRAINT empresa_cnpj_uk UNIQUE (cnpj),
    CONSTRAINT empresa_usuario_uk UNIQUE (usuario)
);

CREATE SEQUENCE IF NOT EXISTS projeto.leilao_id_seq;

CREATE TABLE IF NOT EXISTS projeto.leilao (
    id INTEGER NOT NULL DEFAULT nextval('projeto.leilao_id_seq'::regclass),
    codigo INTEGER,
    descricao VARCHAR(60) NOT NULL,
    vendedor INTEGER NOT NULL,
    inicio_previsto TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT leilao_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS projeto.comprador (
    empresa INT NOT NULL,
    leilao INT NOT NULL,

    CONSTRAINT comprador_pk PRIMARY KEY (empresa, leilao),
    CONSTRAINT empresa_comp_fk FOREIGN KEY (empresa) REFERENCES projeto.empresa(id),
    CONSTRAINT leilao_comp_fk FOREIGN KEY (leilao) REFERENCES projeto.leilao(id)
);

CREATE SEQUENCE IF NOT EXISTS projeto.unidade_id_seq;

CREATE TABLE IF NOT EXISTS projeto.unidade (
    id INTEGER NOT NULL DEFAULT nextval('projeto.unidade_id_seq'::regclass),
    nome VARCHAR(128) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT unidade_pk PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS projeto.lote_id_seq;

CREATE TABLE IF NOT EXISTS projeto.lote (
    id INTEGER NOT NULL DEFAULT nextval('projeto.lote_id_seq'::regclass),
    numero_lote INTEGER,
    descricao VARCHAR(60) NOT NULL,
    quantidade DECIMAL NOT NULL,
    valor_inicial DECIMAL,
    unidade VARCHAR(128) NOT NULL,
    leilao INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT lote_pk PRIMARY KEY (id),
    CONSTRAINT leilao_lote_fk FOREIGN KEY (leilao) REFERENCES projeto.leilao(id)
);