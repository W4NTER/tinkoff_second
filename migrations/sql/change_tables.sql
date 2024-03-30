-- alter table chat drop column edited_at;
-- alter table links drop column chat_id;
-- alter table links rename column edited_at to last_update;
-- alter table chat

-- create table if not exists communications (
--     chat_id bigint not null,
--     link_id bigint not null
-- );

alter table links add last_check timestamp with time zone;