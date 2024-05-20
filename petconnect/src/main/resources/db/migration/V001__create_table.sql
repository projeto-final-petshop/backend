create table petshop
(
    created_at            datetime     null,
    id                    bigint auto_increment
        primary key,
    updated_at            datetime     null,
    servicos              varchar(500) null,
    cnpj                  varchar(255) null,
    email                 varchar(255) not null,
    endereco              varchar(255) null,
    horario_funcionamento varchar(255) null,
    nome_fantasia         varchar(255) null,
    phone_number          varchar(255) null,
    constraint UK_54lk2dfx8gsftc1dkn6ygajsd
        unique (email),
    constraint UK_83t513agw8th960ufhx1nri8c
        unique (cnpj)
);

create table users
(
    created_at   datetime     null,
    id           bigint auto_increment
        primary key,
    updated_at   datetime     null,
    cpf          varchar(255) null,
    email        varchar(255) not null,
    name         varchar(255) not null,
    password     varchar(255) not null,
    phone_number varchar(255) null,
    role         varchar(255) null,
    constraint UK_6dotkott2kjsp8vw4d0m25fb7
        unique (email),
    constraint UK_7kqluf7wl0oxs7n90fpya03ss
        unique (cpf)
);

create table pets
(
    age         int          null,
    neutered    bit          null,
    sex         tinyint      null,
    vaccination bit          null,
    created_at  datetime     null,
    id          bigint auto_increment
        primary key,
    owner_id    bigint       null,
    updated_at  datetime     null,
    description varchar(500) null,
    breed       varchar(255) null,
    name        varchar(255) null,
    size        varchar(255) null,
    species     varchar(255) null,
    photo       tinyblob     null,
    constraint FK6teg4kcjcnjhduguft56wcfoa
        foreign key (owner_id) references owners (id),
    check (`sex` between 0 and 2)
);

create table owners
(
    created_at   datetime     null,
    id           bigint auto_increment
        primary key,
    updated_at   datetime     null,
    address      varchar(255) null,
    cpf          varchar(255) null,
    email        varchar(255) not null,
    name         varchar(255) not null,
    phone_number varchar(255) null,
    constraint UK_dw1w2xj1axp1le5oionrjfk7t
        unique (email),
    constraint UK_rfnfhfowwc6uwisbyskbee2uo
        unique (cpf)
);

create table owner_pet
(
    owner_id bigint not null,
    pet_id   bigint not null,
    primary key (owner_id, pet_id),
    constraint FK1br48j7q97404pi29ow7n4jhy
        foreign key (owner_id) references owners (id),
    constraint FKl3eg3cidfdaowbbjblveud4s4
        foreign key (pet_id) references pets (id)
);