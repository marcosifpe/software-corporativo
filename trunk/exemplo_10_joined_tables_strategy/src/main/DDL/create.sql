    drop table if exists TB_TELEFONE;
    drop table if exists TB_COMPRADOR;
    drop table if exists TB_CARTAO_CREDITO;
    drop table if exists TB_VENDEDOR;
    drop table if exists TB_USUARIO;

    create table TB_CARTAO_CREDITO (
        ID bigint not null auto_increment,
        TXT_BANDEIRA varchar(255),
        DT_EXPIRACAO date,
        TXT_NUMERO varchar(255),
        primary key (ID)
    );

    create table TB_COMPRADOR (
        ID_USUARIO bigint not null,
        ID_CARTAO_CREDITO bigint not null,
        primary key (ID_USUARIO)
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
        primary key (ID)
    );

    create table TB_VENDEDOR (
        TXT_REPUTACAO varchar(255) not null,
        NUM_VALOR_VENDAS double precision not null,
        ID_USUARIO bigint not null,
        primary key (ID_USUARIO)
    );

    alter table TB_COMPRADOR 
        add constraint UK_CARTAO_CREDITO  unique (ID_CARTAO_CREDITO);

    alter table TB_COMPRADOR 
        add constraint FK_CARTAO_CREDITO 
        foreign key (ID_CARTAO_CREDITO) 
        references TB_CARTAO_CREDITO (ID);

    alter table TB_COMPRADOR 
        add constraint FK_COMP_USUARIO 
        foreign key (ID_USUARIO) 
        references TB_USUARIO (ID);

    alter table TB_TELEFONE 
        add constraint FK_TEL_USUARIO 
        foreign key (ID_USUARIO) 
        references TB_USUARIO (ID);

    alter table TB_VENDEDOR 
        add constraint FK_VEND_USUARIO 
        foreign key (ID_USUARIO) 
        references TB_USUARIO (ID);
