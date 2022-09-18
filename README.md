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

### API documentation
There are 2 REST APIs for:
- Searching phone numbers which can retrieve all phone numbers, or search by a customer number.
- Activating an existing phone number.

There is a documentation UI which can be found after running the app by accessing the following endpoint

http://localhost:8080/swagger-ui/index.html