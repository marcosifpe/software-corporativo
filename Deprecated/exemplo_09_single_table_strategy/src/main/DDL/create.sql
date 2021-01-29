    drop table if exists TB_TELEFONE;
    drop table if exists TB_USUARIO;
    drop table if exists TB_CARTAO_CREDITO;    
    create table TB_CARTAO_CREDITO (
        ID bigint not null auto_increment,
        TXT_BANDEIRA varchar(255),
        DT_EXPIRACAO date,
        TXT_NUMERO varchar(255),
        primary key (ID)
    );

    create table TB_TELEFONE (
        ID_USUARIO bigint not null,
        TXT_NUM_TELEFONE varchar(20) not null,
        primary key (ID_USUARIO, TXT_NUM_TELEFONE)
    );
    create table TB_USUARIO (
        ID bigint not null auto_increment,
        DISC_USUARIO varchar(1) not null,
        TXT_CPF varchar(14) not null,
        DT_NASCIMENTO date,
        TXT_EMAIL varchar(50) not null,
        END_TXT_BAIRRO varchar(150) not null,
        END_TXT_CEP varchar(20) not null,
        END_TXT_CIDADE varchar(50) not null,
        END_TXT_COMPLEMENTO varchar(30),
        END_TXT_ESTADO varchar(50) not null,
        END_TXT_LOGRADOURO varchar(150) not null,
        END_NUMERO integer not null,
        TXT_LOGIN varchar(50) not null,
        TXT_NOME varchar(255) not null,
        TXT_SENHA varchar(20) not null,
        TXT_REPUTACAO varchar(255),
        NUM_VALOR_VENDAS double precision,
        ID_CARTAO_CREDITO bigint,
        primary key (ID)
    );

    alter table TB_USUARIO 
        add constraint UK_CPF  unique (TXT_CPF);

    alter table TB_USUARIO 
        add constraint UK_LOGIN  unique (TXT_LOGIN);

    alter table TB_TELEFONE 
        add constraint FK_USUARIO
        foreign key (ID_USUARIO) 
        references TB_USUARIO (id);

    alter table TB_USUARIO 
        add constraint FK_CARTAO_CREDITO 
        foreign key (ID_CARTAO_CREDITO) 
        references TB_CARTAO_CREDITO (id);
