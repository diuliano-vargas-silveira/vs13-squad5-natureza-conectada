


CREATE SEQUENCE VEM_SER.SEQ_USUARIO
START WITH 1
INCREMENT BY 1
NOCACHE NOCYCLE;


CREATE TABLE VEM_SER.USUARIO
(
	id_usuario  NUMBER(38,0) NOT NULL,
	nome VARCHAR2(50) NOT NULL,
	email VARCHAR2(50) NOT NULL,
	senha VARCHAR2(50) NOT NULL,
	tipoUsuario CHAR(1) NOT NULL CHECK(tipoUsuario IN('A', 'C','E')),
	PRIMARY KEY(id_usuario)
	

);
