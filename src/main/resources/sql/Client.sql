CREATE table BANK (
    ID UUID primary key not null,
    NAME VARCHAR(25) unique

);

create table CREDIT (
    ID UUID primary key not null,
    CREDIT_LIMIT NUMERIC not null,
    PERCENT_RATE NUMERIC not null,
    Bank_ID UUID not null,
    FOREIGN KEY (Bank_ID) REFERENCES BANK

);

create table CLIENT (
    ID UUID primary key not null,
    name varchar(30) not null,
    PASSPORTID varchar(10) unique not null,
    phone varchar(15) not null unique,
    Bank UUID,
    FOREIGN KEY (Bank) REFERENCES BANK

);

create table CREDIT_OFFER (
    ID UUID primary key not null,
    AMOUNT_OF_PAYMENT NUMERIC,
    CLIENT UUID not null,
    CREDIT UUID not null,
    FOREIGN KEY (CLIENT) REFERENCES CLIENT,
    FOREIGN KEY (CREDIT) REFERENCES CREDIT
);

create table PAYMENT_SCHEDULE (
    ID UUID primary key not null,
    AMOUNT_OF_INTEREST_REPAYMENT NUMERIC,
    AMOUNT_OF_PAYMENT NUMERIC,
    AMOUNT_OF_REPAYMENT_OF_THE_LOAN_BODY NUMERIC,
    PAYMENT_DATE DATE,
    CREDIT_OFFER UUID not null ,
    FOREIGN KEY (CREDIT_OFFER) REFERENCES CREDIT_OFFER
);
