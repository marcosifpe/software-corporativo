CREATE SEQUENCE sq_usuario
  INCREMENT 20
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 20
  CACHE 1;
ALTER TABLE sq_usuario
  OWNER TO postgres;

CREATE TABLE tb_usuario
(
  id bigint NOT NULL,
  txt_cpf character varying(14) NOT NULL,
  dt_nascimento date,
  txt_email character varying(50) NOT NULL,
  txt_login character varying(50) NOT NULL,
  txt_nome character varying(255) NOT NULL,
  txt_senha character varying(20) NOT NULL,
  CONSTRAINT tb_usuario_pkey PRIMARY KEY (id),
  CONSTRAINT tb_usuario_txt_cpf_key UNIQUE (txt_cpf),
  CONSTRAINT tb_usuario_txt_login_key UNIQUE (txt_login)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE tb_usuario
  OWNER TO postgres;
