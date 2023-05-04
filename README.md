## Spring Boot Micro-service for Customer Quotation and Subscription Management
This is a micro-service for managing Quotations and Subscriptions using Spring Boot framework and H2 database.

## Prerequisites
JDK 1.8 or later
Maven 3.2+
##  Getting Started
To run this micro-service locally, follow these steps:

1.  Clone the repository from https

2.  Navigate to the project directory

3.  Run the following command to build the project:

`mvn clean package`

4.  Run the following command to start the micro-service:

`java -jar target/spring-boot-quotation-subscription-microservice-1.0-SNAPSHOT.jar
`

# Endpoints

**Create Quotation**

* Method: POST

* Path: /quotations

* Request Body:

``` json
{
"beginningOfInsurance": "2023-06-01",
"insuredAmount": 50000,
"dateOfSigningMortgage": "2023-05-01",
    "customer": {
            "firstName": "John",
            "lastName": "Doe",
            "middleName": "M",
            "email": "johndoe@example.com",
            "phoneNumber": "555-555-5555",
            "birthDate": "1990-01-01"
    }
}
```
* Response Body:

``` json
{
"id": 1,
"beginningOfInsurance": "2023-06-01",
"insuredAmount": 50000,
"dateOfSigningMortgage": "2023-05-01",
"customer": {
"id": 1,
"firstName": "John",
"lastName": "Doe",
"middleName": "M",
"email": "johndoe@example.com",
"phoneNumber": "555-555-5555",
"birthDate": "1990-01-01"
}
}
```

**Create Subscription**
* Method: POST

* Path: /subscriptions

* Request Body:

``` json
{
"quotation": {
                  "id": 1
             },
"startDate": "2023-06-01",
"validUntil": "2024-06-01"
}
```
* Response Body:

```json
{
    "id": 1,
    "quotation": {
    "id": 1,
    "beginningOfInsurance": "2023-06-01",
    "insuredAmount": 50000,
    "dateOfSigningMortgage": "2023-05-01",
    "customer": {
            "id": 1,
            "firstName": "John",
            "lastName": "Doe",
            "middleName": "M",
            "email": "johndoe@example.com",
            "phoneNumber": "555-555-5555",
            "birthDate": "1990-01-01"
    }
    },
    "startDate": "2023-06-01",
    "validUntil": "2024-06-01"
}
```

**Get Subscription**

* Method: GET

* Path: /subscriptions/{id}

* Response Body:

```json
{
"id": 1,
"quotation": {
            "id": 1,
            "beginningOfInsurance": "2023-06-01",
            "insuredAmount": 50000,
            "dateOfSigningMortgage": "2023-05-01", 
            "customer": {
                        "id": 1,
                        "firstName": "John",
                        "lastName": "Doe",
                        "middleName": "M",
                        "email": "johndoe@example.com",
                        "phoneNumber": "555-555-5555",
                        "birthDate": "1990-01-01"
                     }
          },
    "startDate": "2023-06-01",
    "validUntil": "2024-06-01"
}
```

**Update Customer**

- Method: PUT
- Path: `/customers/{id}`
- Request Body:
```json
 {
  "firstName": "John",
  "lastName": "Doe",
  "middleName": "M",
  "email": "johndoe@example.com",
  "phoneNumber": "555-555-5555",
  "birthDate": "1990-01-01"
}
```

## H2 Database

This micro-service uses an embedded H2 database. You can access the database console at `http://localhost:8080/h2-console` using the following settings:

- Driver Class: `org.h2.Driver`
- JDBC URL: `jdbc:h2:mem:testdb`
- User Name: `sa`
- Password: (leave blank)

## Conclusion

This micro-service provides endpoints for creating and managing Quotations and Subscriptions, as well as updating Customer attributes. It is built using Spring Boot framework and H2 database, and can be run locally without any external dependencies.