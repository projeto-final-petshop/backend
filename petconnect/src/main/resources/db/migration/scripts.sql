create database petconnect_teste;
use petconnect;
create table users
(
    id           bigint auto_increment
        primary key,
    active       bit          not null,
    address      varchar(255) null,
    cpf          varchar(255) not null,
    created_at   datetime(6)  null,
    email        varchar(255) not null,
    name         varchar(255) not null,
    password     varchar(255) not null,
    phone_number varchar(255) null,
    updated_at   datetime(6)  null,
    role_id      bigint       not null,
    constraint uk_cpf
        unique (cpf),
    constraint uk_email
        unique (email),
    constraint FK_user_role
        foreign key (role_id) references roles (id)
);
use petconnect;
create table appointments
(
    id               bigint auto_increment
        primary key,
    appointment_date date        null,
    appointment_time time(6)     null,
    created_at       datetime(6) null,
    service_type_id  bigint      not null,
    status_id        bigint      not null,
    updated_at       datetime(6) null,
    pet_id           bigint      not null,
    user_id          bigint      not null,
    constraint FK_appointment_pet
        foreign key (pet_id) references pets (id),
    constraint FK_appointment_user
        foreign key (user_id) references users (id),
    constraint FK_service_type
        foreign key (service_type_id) references service_types (id),
    constraint FK_status
        foreign key (status_id) references appointment_statuses (id)
);
use petconnect;
create table pets
(
    id          bigint auto_increment
        primary key,
    birthdate   date        null,
    breed       varchar(50) null,
    color       varchar(20) null,
    created_at  datetime(6) null,
    name        varchar(50) null,
    pet_type_id bigint      not null,
    updated_at  datetime(6) null,
    user_id     bigint      not null,
    constraint FK_pet_user
        foreign key (user_id) references users (id),
    constraint FK_pet_type
        foreign key (pet_type_id) references pet_types (id)
);
use petconnect;
create table roles
(
    id          bigint auto_increment
        primary key,
    created_at  datetime(6)  null,
    description varchar(255) null,
    name_id     bigint       not null,
    updated_at  datetime(6)  null,
    constraint FK_role_name
        foreign key (name_id) references role_types (id)
);
use petconnect;
create table appointment_statuses
(
    id     bigint auto_increment
        primary key,
    status varchar(50) not null unique
);
use petconnect;
insert into appointment_statuses (status)
values ('CANCELLED'),
       ('COMPLETED'),
       ('CONFIRMED'),
       ('PENDING'),
       ('SCHEDULED');
use petconnect;
create table service_types
(
    id   bigint auto_increment
        primary key,
    type varchar(50) not null unique
);
use petconnect;
insert into service_types (type)
values ('BATH'),
       ('BATH_AND_GROOMING'),
       ('GROOMING'),
       ('VETERINARY_CONSULTATION');

use petconnect;
create table pet_types
(
    id   bigint auto_increment
        primary key,
    type varchar(50) not null unique
);
use petconnect;
insert into pet_types (type)
values ('CAT'),
       ('DOG'),
       ('OTHER');

use petconnect;
create table role_types
(
    id   bigint auto_increment
        primary key,
    name varchar(50) not null unique
);

insert into role_types (name)
values ('ADMIN'),
       ('EMPLOYEE'),
       ('GROOMING'),
       ('USER'),
       ('VETERINARIAN');
