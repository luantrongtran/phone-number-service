# Getting Started

An example of creating REST APIs using Open API.

The app is a simple phone-number management service which uses Open API, Spring Data and H2 database

### Install dependencies
```
./mvnw install
```

### Run the app using maven command line
```
./mvnw spring-boot:run 
```

### Database
There is only a single table `phone_number` in which the primary key is the phone number itself, and there is a column for `customer_number` represent the user owning the phone number.

The assumption is that each customer has a unique number, and each customer can have more than 1 phone number. 

The schema is defined in resources/schema.sql
```
CREATE TABLE phone_number
(
    phone_no varchar(15) not null,
    customer_no varchar(20) not null,
    activated boolean not null,
    activated_date date not null,

    PRIMARY KEY (phone_no)
);
```

The initial data is located in resources/data.sql
```
INSERT INTO phone_number (customer_no, phone_no, activated, activated_date)
VALUES ('2000111112222', '0423456789', true, '2022-05-01')
     , ('2000111112222', '04234215389', false, '2022-05-02')
     , ('2000222223333', '0424444444', true, '2022-02-04')
     , ('2000222223333', '0424444244', false, '2022-03-04')
     , ('2000333334444', '0423215555', true, '2022-07-02')
     , ('2000333334444', '0425555555', false, '2022-04-02')
     , ('2000444445555', '0426666666', false, '2021-10-08')
     , ('2000555556666', '0429999999', false, '2021-12-01')
;
```

### API documentation
There are 2 REST APIs for:
- Searching phone numbers which can retrieve all phone numbers, or search by a customer number.
- Activating an existing phone number.

There is a documentation UI which can be found after running the app by accessing the following endpoint

http://localhost:8080/swagger-ui/index.html