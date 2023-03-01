# ecommerce-api

Ecommerce API with Spring Boot

## Setup

### Setup environment variables

```text
Intellij:
    Run
        -> Edit configurations
            -> Modify options
                -> Environment variables
Add the following variables
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
SPRING_DATASOURCE_URL
SPRING_MAIl_HOST
SPRING_MAIL_PORT
SPRING_MAIL_USERNAME
SPRING_MAIL_PASSWORD
```

### Run docker compose
```
docker-compose up -d
```

### Run the application

```
./mvnw spring-boot:run
```
