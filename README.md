# Stock Trades API

Spring Boot 3 / Java 17 REST API for storing and querying stock trade transactions. The observable API behaviour matches [`CONTRACT.md`](CONTRACT.md), and the provided verification suite passes with `mvn clean test`.

## Prerequisites

- Java 17 or newer
- Maven 3.9 or newer
- Docker Desktop or Docker Engine if you want to run the containerized version

## Build And Test

Run the full verification suite plus the added unit tests:

```bash
mvn clean test
```

Build the runnable jar:

```bash
mvn clean package
```

## Run Locally

Start directly with Spring Boot:

```bash
mvn spring-boot:run
```

Or run the packaged jar:

```bash
java -jar target/stocktrade-1.0-SNAPSHOT.jar
```

The application listens on port `8000` by default.

- Base URL: `http://localhost:8000`
- API base path: `/`
- Example endpoint: `GET http://localhost:8000/trades`

## Run With Docker

Build the image:

```bash
docker build -t stocktrade-api .
```

Run the container:

```bash
docker run --rm -p 8000:8000 stocktrade-api
```

Or use Compose:

```bash
docker compose up --build
```

## Configuration

The app uses an in-memory H2 database by default so it runs with zero external dependencies.

Useful environment variables:

- `SERVER_PORT`: overrides the HTTP port. Default: `8000`
- `DATABASE_URL`: overrides the datasource URL
- `DATABASE_DRIVER`: overrides the datasource driver class
- `DATABASE_USERNAME`: datasource username. Default: `sa`
- `DATABASE_PASSWORD`: datasource password. Default: empty
- `APP_SECURITY_ENABLED`: enables HTTP Basic auth when set to `true`. Default: `false`
- `APP_SECURITY_USERNAME`: basic auth username when security is enabled
- `APP_SECURITY_PASSWORD`: basic auth password when security is enabled

Examples:

```bash
APP_SECURITY_ENABLED=true APP_SECURITY_USERNAME=reviewer APP_SECURITY_PASSWORD=secret mvn spring-boot:run
```

## Helpful Endpoints

- `GET /swagger-ui.html`
- `GET /v3/api-docs`
- `GET /actuator/health`
- `GET /actuator/info`
- `GET /actuator/prometheus`
- `GET /h2-console`

## Notes For Reviewers

- The contract test suite is executed through the shipped [`TestSuite`](src/test/java/com/dvtsoftware/stocktrade/controller/TestSuite.java) so the ordered verification data setup runs exactly once.
- Additional unit tests for the price-summary business logic live in [`PriceServiceUnitTest`](src/test/java/com/dvtsoftware/stocktrade/service/PriceServiceUnitTest.java).
- Design decisions, trade-offs, and edge-case interpretation are documented in [`APPROACH.md`](APPROACH.md).
- A concise architecture/operations summary is documented in [`SYSTEM.md`](SYSTEM.md).
