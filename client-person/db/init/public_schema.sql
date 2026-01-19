CREATE SEQUENCE IF NOT EXISTS seq_client
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS tbl_client (
    person_id int8 DEFAULT nextval('seq_client'),
    client_id VARCHAR(10) UNIQUE,
    name VARCHAR(200) NOT NULL,
    gender VARCHAR(20) NOT NULL,
    birth_date DATE NOT NULL,
    age varchar(3),
    identification VARCHAR(10) NOT NULL,
    address VARCHAR(200) NOT NULL,
    cell_phone VARCHAR(10) NOT NULL,
    password TEXT NOT NULL,
    status BOOLEAN DEFAULT TRUE,
    constraint tbl_client_pkey primary key (person_id) 
);