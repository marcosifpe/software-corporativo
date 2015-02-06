    alter table TB_ITENS_CATEGORIAS
        drop
        foreign key FK_j1ijyjnjqkylwrtaxh2ej8sli

    alter table TB_ITENS_CATEGORIAS
        drop
        foreign key FK_2lfw5c5oei99b49rfbadby2c2

    alter table TB_OFERTA
        drop
        foreign key FK_n5rvfo24hjgtqijccw8f15o5b

    drop table if exists TB_CATEGORIA
    drop table if exists TB_ITEM
    drop table if exists TB_ITENS_CATEGORIAS
    drop table if exists TB_OFERTA

    create table TB_CATEGORIA (
        id bigint not null auto_increment,
        TXT_NOME varchar(50) not null,
        primary key (id)
    )

    create table TB_ITEM (
        id bigint not null auto_increment,
        TXT_DESCRICAO varchar(500) not null,
        TXT_TITULO varchar(150) not null,
        primary key (id)
    )

    create table TB_ITENS_CATEGORIAS (
        ID_ITEM bigint not null,
        ID_CATEGORIA bigint not null
    )

    create table TB_OFERTA (
        id bigint not null auto_increment,
        DT_OFERTA datetime not null,
        NUM_VALOR double precision not null,
        ID_ITEM bigint not null,
        primary key (id)
    )

    alter table TB_ITENS_CATEGORIAS
        add constraint FK_j1ijyjnjqkylwrtaxh2ej8sli
        foreign key (ID_CATEGORIA)
        references TB_CATEGORIA (id)

    alter table TB_ITENS_CATEGORIAS
        add constraint FK_2lfw5c5oei99b49rfbadby2c2
        foreign key (ID_ITEM)
        references TB_ITEM (id)

    alter table TB_OFERTA
        add constraint FK_n5rvfo24hjgtqijccw8f15o5b
        foreign key (ID_ITEM)
        references TB_ITEM (id)
