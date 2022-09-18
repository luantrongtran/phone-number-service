CREATE TABLE phone_number
(
    customer_no varchar(20) not null,
    phone_no varchar(15) not null,
    activated boolean not null,
    activated_date date not null,

    PRIMARY KEY (customer_no, phone_no)
);