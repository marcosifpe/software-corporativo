    alter table TB_ITENS_CATEGORIAS 
        drop 
        foreign key FK_TB_ITENS_CATEGORIAS_CATEGORIA

    alter table TB_ITENS_CATEGORIAS 
        drop 
        foreign key FK_TB_ITENS_CATEGORIAS_ITEM

    alter table TB_OFERTA 
        drop 
        foreign key FK_TB_OFERTA_ITEM

    drop table if exists TB_CATEGORIA
    drop table if exists TB_ITEM
    drop table if exists TB_ITENS_CATEGORIAS
    drop table if exists TB_OFERTA

    create table TB_CATEGORIA (
        ID bigint not null auto_increment,
        TXT_NOME varchar(50) not null,
        primary key (ID)
    )

    create table TB_ITEM (
        ID bigint not null auto_increment,
        TXT_DESCRICAO varchar(500) not null,
        TXT_TITULO varchar(150) not null,
        primary key (ID)
    )

    create table TB_ITENS_CATEGORIAS (
        ID_ITEM bigint not null,
        ID_CATEGORIA bigint not null,
        primary key (ID_ITEM, ID_CATEGORIA)
    )

    create table TB_OFERTA (
        ID bigint not null auto_increment,
        DT_OFERTA datetime not null,
        NUM_VALOR double precision not null,
        ID_ITEM bigint,
        primary key (ID)
    )

    alter table TB_ITENS_CATEGORIAS 
        add constraint FK_TB_ITENS_CATEGORIAS_CATEGORIA 
        foreign key (ID_CATEGORIA) 
        references TB_CATEGORIA (ID)

    alter table TB_ITENS_CATEGORIAS 
        add constraint FK_TB_ITENS_CATEGORIAS_ITEM 
        foreign key (ID_ITEM) 
        references TB_ITEM (ID)

    alter table TB_OFERTA 
        add constraint FK_TB_OFERTA_ITEM 
        foreign key (ID_ITEM) 
        references TB_ITEM (ID)