create table if not exists communications (
    chat_id bigint not null,
    link_id bigint not null
);

alter table links add last_check timestamp with time zone;