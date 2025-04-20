create table if not exists chat (
    chat_id bigint not null ,
    created_at timestamp with time zone not null,

    primary key(chat_id)
);

create table if not exists links (
    link_id bigint generated always as identity,
    link varchar not null,
    created_at timestamp with time zone not null,
    last_update timestamp with time zone not null,
    last_check timestamp with time zone not null
);

create table if not exists communications (
    chat_id bigint not null,
    link_id bigint not null
);
