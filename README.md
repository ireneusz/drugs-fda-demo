# Getting Started

The application is used to fetch drug application data from open FDA API and allow storage it
in local database. Provides also rest endpoint for search data in local storage.


### Run application for the first time

#### Precondition
* Java 17
* Mavnen
* Docker

#### steps to run application

1. run you local postgres using docker compose
```bash
docker-compose up -d
```
2. build application

from main project folder run 
```bash
mvn clean install -DskipTests 
```
3. run application

```bash
cd ./drugsfda-core
spring-boot:run -Dspring.profiles.active=dev 
```

Note: during first run flyway automatically migrate db schema

4. Check if application is running - info endpoint
   `http://localhost:8080/drugsfda/actuator/info`

For each command you can use predefined InteliJ runners defined in `/runConfigurations`

To check application rest api you can use requests defined in `/http`

##Flyway


```bash
cd drugsfda-persistance-postgres
mvn flyway:migrate
```

Then you can run flyway clean as many times as you want and start application with `dev` profile.

Flyway Clean:

```bash
cd drugsfda-persistance-postgres
mvn flyway:clean
```

## Profiles

For development enable the `dev` spring profile. Contains 
-predefined properties for local development
-db migration witch local test data

To receive mocked data from Open FDA api use `fda-client-mock`

## Swagger documentation

http://localhost:8080/drugsfda/swagger-ui/index.html

## Security

Application is secured by api key authorization
to call rest endpoint you have to pass in `Authorization` header
Bearer token defined in properties `application.security.apiKeyToken`


# Production run
Provide correct parameters
```text
#postgres db host url
DB_HOST
#postgres db port number
DB_PORT

#postgres db flyway user
DB_FLYWAY_USERNAME

#postgres db flyway password
DB_FLYWAY_PASSWORD

#postgres db application user
DB_APP_USERNAME

#postgres db application password
DB_APP_PASSWORD

```

