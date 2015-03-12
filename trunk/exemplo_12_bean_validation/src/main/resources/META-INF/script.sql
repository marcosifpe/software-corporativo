    alter table TB_CATEGORIA
        drop
        foreign key FK_CATEGORIA_MAE;

    alter table TB_COMPRADOR
        drop
        foreign key FK_CARTAO_CREDITO;

    alter table TB_COMPRADOR
        drop
        foreign key FK_USUARIO_COMPRADOR;

    alter table TB_ITEM
        drop
        foreign key FK_VENDEDOR_ITEM;

    alter table TB_ITENS_CATEGORIAS
        drop
        foreign key FK_ITENS_CATEGORIAS_CATEGORIA;

    alter table TB_ITENS_CATEGORIAS
        drop
        foreign key FK_ITENS_CATEGORIAS_ITEM;

    alter table TB_OFERTA
        drop
        foreign key FK_OFERTA_COMPRADOR;

    alter table TB_OFERTA
        drop
        foreign key FK_OFERTA_ITEM;

    alter table TB_TELEFONE
        drop
        foreign key FK_TELEFONE_USUARIO;

    alter table TB_VENDEDOR
        drop
        foreign key FK_VENDEDOR_USUARIO;

    drop table if exists TB_CARTAO_CREDITO;
    drop table if exists TB_CATEGORIA;
    drop table if exists TB_COMPRADOR;
    drop table if exists TB_ITEM;
    drop table if exists TB_ITENS_CATEGORIAS;
    drop table if exists TB_OFERTA;
    drop table if exists TB_TELEFONE;
    drop table if exists TB_USUARIO;
    drop table if exists TB_VENDEDOR;

    create table TB_CARTAO_CREDITO (
        id bigint not null auto_increment,
        TXT_BANDEIRA varchar(15) not null,
        DT_EXPIRACAO date not null,
        TXT_NUMERO varchar(30) not null,
        primary key (id)
    );

    create table TB_CATEGORIA (
        id bigint not null,
        TXT_NOME varchar(100) not null,
        ID_CATEGORIA_MAE bigint,
        primary key (id)
    );

    create table TB_COMPRADOR (
        ID_USUARIO bigint not null,
        ID_CARTAO_CREDITO bigint,
        primary key (ID_USUARIO)
    );

    create table TB_ITEM (
        id bigint not null auto_increment,
        TXT_DESCRICAO varchar(500) not null,
        TXT_TITULO varchar(150) not null,
        ID_VENDEDOR bigint not null,
        primary key (id)
    );

    create table TB_ITENS_CATEGORIAS (
        ID_ITEM bigint not null,
        ID_CATEGORIA bigint not null,
        primary key (ID_ITEM, ID_CATEGORIA)
    );

    create table TB_OFERTA (
        id bigint not null auto_increment,
        DT_OFERTA datetime not null,
        NUM_VALOR double precision not null,
        FLAG_VENCEDORA bit not null,
        ID_COMPRADOR bigint not null,
        ID_ITEM bigint not null,
        primary key (id)
    );

    create table TB_TELEFONE (
        ID_USUARIO bigint not null,
        TXT_NUM_TELEFONE varchar(15)
    );

    create table TB_USUARIO (
        DISC_USUARIO varchar(1) not null,
        id bigint not null auto_increment,
        TXT_CPF varchar(14) not null,
        DT_CRIACAO datetime,
        DT_NASCIMENTO date,
        TXT_EMAIL varchar(30) not null,
        END_TXT_BAIRRO varchar(150) not null,
        END_TXT_CEP varchar(10) not null,
        END_TXT_CIDADE varchar(50) not null,
        END_TXT_COMPLEMENTO varchar(30),
        END_TXT_ESTADO varchar(50) not null,
        END_TXT_LOGRADOURO varchar(150) not null,
        END_NUMERO integer not null,
        TXT_LOGIN varchar(20) not null,
        TXT_PRIMEIRO_NOME varchar(30) not null,
        TXT_SENHA varchar(20) not null,
        TXT_ULTIMO_NOME varchar(30) not null,
        primary key (id)
    );

    create table TB_VENDEDOR (
        TXT_REPUTACAO varchar(20) not null,
        NUM_VALOR_VENDAS double precision,
        ID_USUARIO bigint not null,
        primary key (ID_USUARIO)
    );

    alter table TB_CATEGORIA
        add constraint UK_NOME unique (TXT_NOME);

    alter table TB_USUARIO
        add constraint UK_CPF unique (TXT_CPF);

    alter table TB_USUARIO
        add constraint UK_LOGIN unique (TXT_LOGIN);

    alter table TB_CATEGORIA
        add constraint FK_CATEGORIA_MAE
        foreign key (ID_CATEGORIA_MAE)
        references TB_CATEGORIA (id);

    alter table TB_COMPRADOR
        add constraint FK_CARTAO_CREDITO
        foreign key (ID_CARTAO_CREDITO)
        references TB_CARTAO_CREDITO (id);

    alter table TB_COMPRADOR
        add constraint FK_USUARIO_COMPRADOR
        foreign key (ID_USUARIO)
        references TB_USUARIO (id);

    alter table TB_ITEM
        add constraint FK_VENDEDOR_ITEM
        foreign key (ID_VENDEDOR)
        references TB_VENDEDOR (ID_USUARIO);

    alter table TB_ITENS_CATEGORIAS
        add constraint FK_ITENS_CATEGORIAS_CATEGORIA
        foreign key (ID_CATEGORIA)
        references TB_CATEGORIA (id);

    alter table TB_ITENS_CATEGORIAS
        add constraint FK_ITENS_CATEGORIAS_ITEM
        foreign key (ID_ITEM)
        references TB_ITEM (id);

    alter table TB_OFERTA
        add constraint FK_OFERTA_COMPRADOR
        foreign key (ID_COMPRADOR)
        references TB_COMPRADOR (ID_USUARIO);

    alter table TB_OFERTA
        add constraint FK_OFERTA_ITEM
        foreign key (ID_ITEM)
        references TB_ITEM (id);

    alter table TB_TELEFONE
        add constraint FK_TELEFONE_USUARIO
        foreign key (ID_USUARIO)
        references TB_USUARIO (id);

    alter table TB_VENDEDOR
        add constraint FK_VENDEDOR_USUARIO
        foreign key (ID_USUARIO)
        references TB_USUARIO (id);
