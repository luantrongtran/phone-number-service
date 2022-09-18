CREATE TABLE phone_number
(
    phone_no varchar(15) not null,
    customer_no varchar(20) not null,
    activated boolean not null,
    activated_date date not null,

    PRIMARY KEY (phone_no)
);