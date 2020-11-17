create table user_details (
	id BIGSERIAL primary key,
	first_name text,
	last_name text,
	security_number text,
	contact_number text,
	preferences jsonb,
	created_at timestamp,
	updated_at timestamp
);

create table audit_history (
	id BIGSERIAL primary key,
	entity_name text,
	action_performed text,
	entity_content jsonb,
	modified_by bigint,
	modified_date timestamp
);

create unique index un_security_number on user_details(security_number);

/*CREATE OR REPLACE FUNCTION fn_user_details_audit_history_insert()
	RETURNS TRIGGER as
		$$
			begin
				update audit_history SET entity_content = jsonb_set(entity_content, '{id}', to_jsonb(new."id"))
				where entity_name = 'UserDetails' and action_performed = 'INSERTED'
				and entity_content ->> 'securityNumber' = new."security_number";
				return new;
			end
		$$
LANGUAGE 'plpgsql';

CREATE TRIGGER tr_audit_history_user_details_insert AFTER insert ON "user_details"
FOR EACH ROW EXECUTE PROCEDURE fn_user_details_audit_history_insert();*/