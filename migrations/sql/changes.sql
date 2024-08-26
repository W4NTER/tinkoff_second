alter table chat alter column chat_id DROP IDENTITY;
alter table chat alter column chat_id set not null;

