
CREATE SEQUENCE IF NOT EXISTS client_report_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS tbl_client_report(
  client_report_id int8 not null default nextval('client_report_seq'),
  client_id VARCHAR(10) UNIQUE,
  name VARCHAR(200) NOT NULL,
  CONSTRAINT tbl_client_report_pkey PRIMARY KEY (client_report_id)
);

CREATE INDEX IF NOT EXISTS idx_client_report_client_id
ON tbl_client_report (client_id);

CREATE SEQUENCE IF NOT EXISTS account_report_seq
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

CREATE TABLE IF NOT EXISTS tbl_account_report(
  account_report_id int8 NOT NULL DEFAULT NEXTVAL('account_report_seq'),
  account_id INT8 NOT NULL,
  client_id VARCHAR(10),
  account_number VARCHAR(6)  NOT NULL,
  account_type varchar(15) NOT NULL,
  CONSTRAINT tbl_account_report_pkey PRIMARY KEY (account_report_id)
);

CREATE INDEX  IF NOT EXISTS idx_account_report_client_account
ON tbl_account_report (client_id, account_id);

CREATE SEQUENCE IF NOT EXISTS movement_report_seq
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

CREATE TABLE IF NOT EXISTS tbl_movement_report(
  movement_report_id int8 NOT NULL DEFAULT NEXTVAL('movement_report_seq'),
  movement_id INT8 NOT NULL,
  account_id INT8 NOT NULL,
  movement_date   DATE DEFAULT CURRENT_DATE,
  movement_hour   TIME DEFAULT CURRENT_TIME,
  movement_type   VARCHAR(15) NOT NULL,
  value           NUMERIC(19, 2) NOT NULL,
  balance         NUMERIC(19, 2) NOT NULL,
  CONSTRAINT tbl_movement_report_pkey PRIMARY KEY (movement_report_id)
);

CREATE INDEX IF NOT EXISTS idx_movement_report_account_date_hour
ON tbl_movement_report (
    account_id,
    movement_date DESC,
    movement_hour DESC
);
