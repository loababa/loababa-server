create table consulting_post_topics
(
    topic                   varchar(6)   not null,
    consulting_post_id      bigint       not null,
    id                      bigint auto_increment,
    created_date_time       timestamp(6) not null default current_timestamp(6),
    last_modified_date_time timestamp(6) not null default current_timestamp(6),
    primary key (id)
);
create table consulting_posts
(
    id                      bigint auto_increment,
    member_id               bigint       not null unique,
    title                   varchar(200) not null,
    contents                varchar(500) not null,
    created_date_time       timestamp(6) not null default current_timestamp(6),
    last_modified_date_time timestamp(6) not null default current_timestamp(6),
    primary key (id)
);
create table consulting_reservation_datetimes
(
    is_confirmed            boolean      not null,
    end_date_time           timestamp(6) not null,
    id                      bigint auto_increment,
    reservation_id          bigint       not null,
    start_date_time         timestamp(6) not null,
    created_date_time       timestamp(6) not null default current_timestamp(6),
    last_modified_date_time timestamp(6) not null default current_timestamp(6),
    primary key (id)
);
create table consulting_reservations
(
    id                      bigint auto_increment,
    consulting_status       tinyint      not null check (consulting_status between 0 and 3),
    is_deleted              boolean      not null,
    lossam_id               bigint       not null,
    mokoko_id               bigint       not null,
    created_date_time       timestamp(6) not null default current_timestamp(6),
    last_modified_date_time timestamp(6) not null default current_timestamp(6),
    primary key (id)
);
create table consulting_schedules
(
    end_time                TIME,
    start_time              TIME,
    id                      bigint auto_increment,
    member_id               bigint       not null,
    day_of_week             enum ('FRIDAY','MONDAY','SATURDAY','SUNDAY','THURSDAY','TUESDAY','WEDNESDAY') not null,
    created_date_time       timestamp(6) not null default current_timestamp(6),
    last_modified_date_time timestamp(6) not null default current_timestamp(6),
    primary key (id),
    constraint member_id_day_of_week_unique_key_index unique (member_id, day_of_week)
);
create table lost_ark_character_infos
(
    highest_level           integer      not null,
    id                      bigint auto_increment,
    member_id               bigint       not null unique,
    class_engravings        varchar(255) not null,
    created_date_time       timestamp(6) not null default current_timestamp(6),
    last_modified_date_time timestamp(6) not null default current_timestamp(6),
    primary key (id)
);
create table members
(
    id                      bigint auto_increment,
    o_auth_user_id          bigint       not null unique,
    nickname                varchar(255) not null unique,
    profile_image_url       varchar(255) not null,
    member_type             enum ('LOSSAM','MOKOKO') not null,
    created_date_time       timestamp(6) not null default current_timestamp(6),
    last_modified_date_time timestamp(6) not null default current_timestamp(6),
    primary key (id)
);
create table oauth_users
(
    id                      bigint auto_increment,
    o_auth_id               bigint       not null,
    o_auth_platform         enum ('GOOGLE','KAKAO') not null,
    created_date_time       timestamp(6) not null default current_timestamp(6),
    last_modified_date_time timestamp(6) not null default current_timestamp(6),
    primary key (id),
    unique (o_auth_id, o_auth_platform)
);
create table reservation_pre_responses
(
    id                      bigint auto_increment,
    member_id               bigint       not null,
    reservation_id          bigint       not null,
    character_details       varchar(255) not null,
    contact_number          varchar(255) not null,
    experience              varchar(255) not null,
    inquiry_details         varchar(255) not null,
    created_date_time       timestamp(6) not null default current_timestamp(6),
    last_modified_date_time timestamp(6) not null default current_timestamp(6),
    primary key (id)
);
