ALTER SESSION SET CURRENT_SCHEMA = VS_13_EQUIPE_5;

CREATE TABLE USUARIO
(
    ID_USUARIO   INT PRIMARY KEY,
    NOME         VARCHAR2(100)        NOT NULL,
    EMAIL        VARCHAR2(100) UNIQUE NOT NULL,
    SENHA        VARCHAR2(100)        NOT NULL,
    CPF           CHAR(11)   		 UNIQUE,
    DOCUMENTO     CHAR(14)   		 UNIQUE,
    ESPECIALIZACAO  VARCHAR2(30)        	, 
    ATIVO           CHAR(1) DEFAULT 'A' CHECK (ATIVO IN('A','D')) NOT NULL,
    TIPO_USUARIO VARCHAR2(12)         NOT NULL CHECK (TIPO_USUARIO IN ('ADMIN', 'CLIENTE', 'ESPECIALISTA'))
);



CREATE SEQUENCE SEQ_USUARIO
    START WITH 1
    INCREMENT BY 1
    NOCACHE NOCYCLE;
   
     
   CREATE TABLE MUDA
(
    ID_MUDA         INT PRIMARY KEY,
    NOME            VARCHAR2(255) NOT NULL,
    NOME_CIENTIFICO VARCHAR2(255) NOT NULL,
    PORTE           VARCHAR2(8)   NOT NULL CHECK (PORTE IN ('PEQUENO', 'MEDIO', 'GRANDE')),
    ECOSSISTEMA     VARCHAR2(50)  NOT NULL,
    DESCRICAO       VARCHAR2(255) NOT NULL,
    ESTOQUE         NUMBER(38,0) NOT NULL,
    ATIVO           CHAR(1) DEFAULT 'A' CHECK (ATIVO IN('A','D'))NOT NULL,
    TIPO_MUDA       VARCHAR(50)  CHECK (TIPO_MUDA IN ('PLANTA', 'ARVORE')) NOT NULL
   
);

   CREATE SEQUENCE SEQ_MUDA
    START WITH 1
    INCREMENT BY 1
    NOCACHE NOCYCLE;
   
      CREATE TABLE CLIENTE_MUDA
   (
    ID_CLIENTE NUMBER(38,0) NOT NULL,
    ID_MUDA NUMBER(38,0)NOT NULL,
    CONSTRAINT PK_CLIENTE_MUDA PRIMARY KEY (ID_CLIENTE,ID_MUDA),
    CONSTRAINT FK_CLIENTE_CLIENTExMUDA FOREIGN KEY (ID_CLIENTE) REFERENCES USUARIO (ID_USUARIO),
    CONSTRAINT FK_MUDA_CLIENTExMUDA FOREIGN KEY (ID_MUDA) REFERENCES MUDA (ID_MUDA)
   )
   
   CREATE TABLE ENDERECO
(
    ID_ENDERECO NUMBER(38,0) PRIMARY KEY,
    CEP         CHAR(8)       NOT NULL,
    LOGRADOURO  VARCHAR2(255) NOT NULL,
    NUMERO      VARCHAR2(10)  NOT NULL,
    COMPLEMENTO VARCHAR2(25),
    CIDADE      VARCHAR2(50)  NOT NULL,
    ESTADO	    CHAR(2)		  NOT NULL,
    ATIVO       CHAR(1)   DEFAULT 'D' CHECK (ATIVO IN('A','D'))NOT NULL
    
)

   CREATE SEQUENCE SEQ_ENDERECO
    START WITH 1
    INCREMENT BY 1
    NOCACHE NOCYCLE;


   
    CREATE TABLE ENDERECO_CLIENTE
  (
  	ID_CLIENTE NUMBER(38,0) NOT NULL,
  	ID_ENDERECO NUMBER(38,0)NOT NULL,
  	CONSTRAINT PK_ENDERECO_CLIENTE PRIMARY KEY (ID_ENDERECO, ID_CLIENTE),
  	CONSTRAINT FK_CLIENTE_ENDERECOxCLIENTE FOREIGN KEY(ID_CLIENTE)REFERENCES USUARIO (ID_USUARIO),
  	CONSTRAINT FK_ENDERECO_ENDERECOxCLIENTE FOREIGN KEY(ID_ENDERECO)REFERENCES ENDERECO (ID_ENDERECO)

  	
  	
  ) 


    CREATE TABLE CONTATO
(
    ID_CONTATO   NUMBER(38,0) PRIMARY KEY,
    ID_CLIENTE   NUMBER (38,0) NOT NULL,
    DESCRICAO    VARCHAR2(255),
    NUMERO       CHAR(11),
    TIPO_CONTATO VARCHAR2(11) NOT NULL CHECK (TIPO_CONTATO IN ('RESIDENCIAL', 'COMERCIAL')),
    CONSTRAINT FK_ID_USUARIO_CONTATO FOREIGN KEY (ID_CLIENTE) REFERENCES USUARIO (ID_USUARIO)
);

    CREATE SEQUENCE SEQ_CONTATO
    START WITH 1
    INCREMENT BY 1
    NOCACHE NOCYCLE;



  CREATE TABLE RELATORIO
(
    ID_RELATORIO    INT PRIMARY KEY,
    ID_CLIENTE      INT           NOT NULL,
    ID_ESPECIALISTA INT,
    ID_MUDA         INT           NOT NULL,
    ESTADO_MUDA     VARCHAR2(100) NOT NULL,
    SUGESTOES       VARCHAR2(255),
    AVALIACAO       DECIMAL(2, 1),
    CONSTRAINT FK_ID_ESPECIALISTA_RELATORIO FOREIGN KEY (ID_ESPECIALISTA) REFERENCES USUARIO (ID_USUARIO),
    CONSTRAINT FK_ID_CLIENTE_RELATORIO FOREIGN KEY (ID_CLIENTE) REFERENCES USUARIO (ID_USUARIO),
    CONSTRAINT FK_ID_MUDA_RELATORIO FOREIGN KEY (ID_MUDA) REFERENCES MUDA (ID_MUDA)
);

CREATE SEQUENCE SEQ_RELATORIO
    START WITH 1
    INCREMENT BY 1
    NOCACHE NOCYCLE;

CREATE TABLE ENTREGA
(
    ID_ENTREGA  INT PRIMARY KEY,
    ID_CLIENTE  INT NOT NULL,
    ID_ENDERECO INT NOT NULL,
    DATA_COMPRA    DATE  NOT NULL, 
    DATA_ENTREGUE    DATE  ,
    STATUS VARCHAR2(30) CHECK (STATUS IN('RECEBIDO', 'ENVIADO', 'ENTREGUE','CANCELADO')),
    CONSTRAINT FK_ID_CLIENTE_ENTREGA FOREIGN KEY (ID_CLIENTE) REFERENCES USUARIO (ID_USUARIO),
    CONSTRAINT FK_ID_ENDERECO_ENTREGA FOREIGN KEY (ID_ENDERECO) REFERENCES ENDERECO (ID_ENDERECO)
);

ALTER TABLE ENTREGA
ADD STATUS VARCHAR2(30) CHECK (STATUS IN('RECEBIDO', 'ENVIADO', 'ENTREGUE','CANCELADO'))

ALTER TABLE entrega 
DROP COLUMN status

CREATE SEQUENCE SEQ_ENTREGA
    START WITH 1
    INCREMENT BY 1
    NOCACHE NOCYCLE;

CREATE TABLE ENTREGA_MUDA
(
    ID_MUDA         INT NOT NULL,
    ID_ENTREGA      INT NOT NULL,
    QUANTIDADE      NUMBER(38,0) NOT NULL,
    CONSTRAINT PK_ENTREGA_MUDA PRIMARY KEY (ID_MUDA, ID_ENTREGA),
    CONSTRAINT FK_ID_MUDA_ENTREGA_MUDA FOREIGN KEY (ID_MUDA) REFERENCES MUDA (ID_MUDA),
    CONSTRAINT FK_ID_ENTREGA_ENTREGA_MUDA FOREIGN KEY (ID_ENTREGA) REFERENCES ENTREGA (ID_ENTREGA)
);

