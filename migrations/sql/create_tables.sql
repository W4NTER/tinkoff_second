create table if not exists chat (
    chat_id bigint not null,
    created_at timestamp with time zone not null,
    edited_at timestamp with time zone not null,

    primary key(chat_id)
);

create table if not exists links (
    link_id bigint generated always as identity,
    link varchar not null,
    chat_id bigint not null,
    created_at timestamp with time zone not null,
    edited_at timestamp with time zone not null,

    foreign key (chat_id) references chat(chat_id)
);