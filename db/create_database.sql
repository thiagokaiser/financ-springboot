-- DROP SEQUENCE public.categoria_id_seq;

CREATE SEQUENCE public.categoria_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.conta_id_seq;

CREATE SEQUENCE public.conta_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.despesa_id_seq;

CREATE SEQUENCE public.despesa_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.usuario_id_seq;

CREATE SEQUENCE public.usuario_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;-- public.usuario definition

-- Drop table

-- DROP TABLE public.usuario;

CREATE TABLE public.usuario (
	id int4 GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	cidade varchar(255) NULL,
	descricao varchar(255) NULL,
	dt_nascimento timestamp NULL,
	email varchar(255) NULL,
	estado varchar(255) NULL,
	imagem_perfil varchar(255) NULL,
	nome varchar(255) NULL,
	senha varchar(255) NULL,
	sobrenome varchar(255) NULL,
	last_login timestamp NULL,
	CONSTRAINT uk_5171l57faosmj8myawaucatdw UNIQUE (email),
	CONSTRAINT usuario_pkey PRIMARY KEY (id)
);


-- public.categoria definition

-- Drop table

-- DROP TABLE public.categoria;

CREATE TABLE public.categoria (
	id int4 GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	cor varchar(255) NULL,
	descricao varchar(255) NULL,
	usuario_id int4 NOT NULL,
	CONSTRAINT categoria_pkey PRIMARY KEY (id),
	CONSTRAINT fk6byc7k2m35w3fwmcba5f0rqtf FOREIGN KEY (usuario_id) REFERENCES public.usuario(id)
);


-- public.conta definition

-- Drop table

-- DROP TABLE public.conta;

CREATE TABLE public.conta (
	id int4 GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	descricao varchar(255) NULL,
	usuario_id int4 NOT NULL,
	CONSTRAINT conta_pkey PRIMARY KEY (id),
	CONSTRAINT fkmn9bk6wpmnlqyv5i3utmled4c FOREIGN KEY (usuario_id) REFERENCES public.usuario(id)
);


-- public.despesa definition

-- Drop table

-- DROP TABLE public.despesa;

CREATE TABLE public.despesa (
	id int4 GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	descricao varchar(255) NULL,
	dt_vencimento timestamp NOT NULL,
	id_parcela int4 NULL,
	num_parcelas int4 NULL,
	pago bool NULL,
	parcela_atual int4 NULL,
	valor float8 NULL,
	categoria_id int4 NULL,
	conta_id int4 NULL,
	usuario_id int4 NOT NULL,
	dt_pagamento timestamp NULL,
	CONSTRAINT despesa_pkey PRIMARY KEY (id),
	CONSTRAINT fkhhqg5jva3eym0n1hp2gedxsq1 FOREIGN KEY (categoria_id) REFERENCES public.categoria(id),
	CONSTRAINT fklngcgagha9t0c6n5kgqw4ut8g FOREIGN KEY (conta_id) REFERENCES public.conta(id),
	CONSTRAINT fkpd22ej1eellqho1wi3gt6noha FOREIGN KEY (usuario_id) REFERENCES public.usuario(id)
);


-- public.perfis definition

-- Drop table

-- DROP TABLE public.perfis;

CREATE TABLE public.perfis (
	usuario_id int4 NOT NULL,
	perfis int4 NULL,
	CONSTRAINT fkiso72ajmkk36lw7dqjva1h8hl FOREIGN KEY (usuario_id) REFERENCES public.usuario(id)
);


-- public.telefone definition

-- Drop table

-- DROP TABLE public.telefone;

CREATE TABLE public.telefone (
	usuario_id int4 NOT NULL,
	telefones varchar(255) NULL,
	CONSTRAINT fk92q33nmw94rylnpis5mgcy3v FOREIGN KEY (usuario_id) REFERENCES public.usuario(id)
);