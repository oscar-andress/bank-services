-- INITIAL SCHEMA FOR ACCOUNTS
CREATE SEQUENCE IF NOT EXISTS account_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE IF NOT EXISTS account_number_saving_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE IF NOT EXISTS account_number_checking_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS  tbl_account (
    account_id       int8 NOT NULL DEFAULT nextval('account_seq'),
    client_id        VARCHAR(10) NOT NULL,
    account_number   VARCHAR(6)  NOT NULL,
    account_type     varchar(15) NOT NULL,
    initial_balance  NUMERIC(19, 2) NOT NULL,
    status           BOOLEAN DEFAULT TRUE,

    CONSTRAINT tbl_account_pkey PRIMARY KEY (account_id)
);

CREATE INDEX idx_tbl_account_client 
    ON tbl_account (client_id);

-- INITIAL SCHEMA FOR MOVEMENTS
CREATE SEQUENCE IF NOT EXISTS movement_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS tbl_movement (
    movement_id     INT8 NOT NULL DEFAULT nextval('movement_seq'),
    account_id      INT8 NOT NULL,
    movement_date   DATE DEFAULT CURRENT_DATE,
    movement_hour   TIME DEFAULT CURRENT_TIME,
    movement_type   VARCHAR(15) NOT NULL,
    value           NUMERIC(19, 2) NOT NULL,
    balance         NUMERIC(19, 2) NOT NULL,

    CONSTRAINT tbl_movement_pkey PRIMARY KEY (movement_id)
);

CREATE INDEX idx_tbl_movement_account_date_hour
    ON tbl_movement (account_id, movement_date DESC, movement_hour DESC);