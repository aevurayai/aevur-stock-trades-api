# System Overview

## Architecture

```text
HTTP Request
    |
    v
Controllers
    |
    v
Services
    |
    v
JdbcTemplate DAO
    |
    v
H2 Database
```

Cross-cutting components:

- Caffeine cache for repeated reads
- Optional Spring Security HTTP Basic auth
- Actuator + Micrometer for health/info/prometheus metrics
- Swagger/OpenAPI for API discovery

## Core Components

### TradeController

Implements:

- `DELETE /erase`
- `POST /trades`
- `GET /trades/{id}`
- `GET /trades`
- `GET /trades/users/{userID}`
- `GET /trades/stocks/{stockSymbol}`

### StockPriceController

Implements:

- `GET /stocks/{stockSymbol}/price`

### TradeService

Responsibilities:

- create trades
- erase all trades
- fetch trades by ID, user, and stock filters
- enforce date-range rules
- translate duplicate IDs into clear client errors
- evict read caches after writes

### PriceService

Responsibilities:

- validate symbol/date inputs
- detect missing symbols vs empty date-range results
- compute highest/lowest prices for a symbol in a date range

### TradeDAO

Responsibilities:

- execute all SQL against `T_TRADE`
- preserve ordering by `T_UID`
- support symbol existence checks and price aggregation queries

## Data Model

The application uses a single table:

- `T_TRADE`

Important columns:

- `T_UID`: trade ID and primary key
- `T_TYPE`: `buy` or `sell`
- `T_U_UID`: user ID
- `T_U_NAME`: user name
- `T_SYMBOL`: stock symbol
- `T_SHARES`: trade quantity
- `T_PRICE`: trade price
- `T_CREATED_AT`: GMT timestamp

## Operational Notes

- Default port: `8000`
- Default base path: `/`
- Default database: in-memory H2
- Security: disabled by default, configurable through environment variables
- Swagger UI: `/swagger-ui.html`
- Prometheus scrape: `/actuator/prometheus`
- Health endpoint: `/actuator/health`
