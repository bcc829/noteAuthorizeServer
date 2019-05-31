-- Drop table

-- DROP TABLE oauth.oauth_access_token

CREATE TABLE oauth.oauth_access_token (
	token_id varchar(256) NULL,
	"token" bytea NULL,
	authentication_id varchar(256) NOT NULL,
	user_name varchar(256) NULL,
	client_id varchar(256) NULL,
	authentication bytea NULL,
	refresh_token varchar(256) NULL,
	CONSTRAINT oauth_access_token_pkey PRIMARY KEY (authentication_id)
);

-- Drop table

-- DROP TABLE oauth.oauth_approvals

CREATE TABLE oauth.oauth_approvals (
	userid varchar(256) NULL,
	clientid varchar(256) NULL,
	"scope" varchar(256) NULL,
	status varchar(10) NULL,
	expiresat timestamp NULL,
	lastmodifiedat timestamp NULL
);

-- Drop table

-- DROP TABLE oauth.oauth_client_details

CREATE TABLE oauth.oauth_client_details (
	client_id varchar(256) NOT NULL,
	resource_ids varchar(256) NULL,
	client_secret varchar(256) NULL,
	"scope" varchar(256) NULL,
	authorized_grant_types varchar(256) NULL,
	web_server_redirect_uri varchar(256) NULL,
	authorities varchar(256) NULL,
	access_token_validity int4 NULL,
	refresh_token_validity int4 NULL,
	additional_information varchar(4096) NULL,
	autoapprove varchar(256) NULL,
	CONSTRAINT oauth_client_details_pkey PRIMARY KEY (client_id)
);

-- Drop table

-- DROP TABLE oauth.oauth_client_token

CREATE TABLE oauth.oauth_client_token (
	token_id varchar(256) NULL,
	"token" bytea NULL,
	authentication_id varchar(256) NOT NULL,
	user_name varchar(256) NULL,
	client_id varchar(256) NULL,
	CONSTRAINT oauth_client_token_pkey PRIMARY KEY (authentication_id)
);

-- Drop table

-- DROP TABLE oauth.oauth_code

CREATE TABLE oauth.oauth_code (
	code varchar(256) NULL,
	authentication bytea NULL
);

-- Drop table

-- DROP TABLE oauth.oauth_refresh_token

CREATE TABLE oauth.oauth_refresh_token (
	token_id varchar(256) NULL,
	"token" bytea NULL,
	authentication bytea NULL
);


CREATE TABLE public.social_member_info (
	member_id varchar NOT NULL,
	provider_type varchar NOT NULL,
	principal varchar NOT NULL,
	seq_id serial NOT NULL
);

ALTER TABLE public.social_member_info ADD CONSTRAINT social_member_info_member_fk FOREIGN KEY (member_id) REFERENCES public."member"(id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE public.social_member_info ADD CONSTRAINT social_member_info_pk PRIMARY KEY (seq_id);
ALTER TABLE public.social_member_info ADD CONSTRAINT social_member_info_un UNIQUE (member_id,provider_type,principal);
