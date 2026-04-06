# Approach

## Structure

The application is organized into a small, explicit set of layers:

- `controller`: REST endpoints and HTTP status handling
- `service`: business rules and date-range semantics
- `dao`: SQL access through `JdbcTemplate`
- `dto`: request/response payloads that match the contract JSON exactly
- `util`: mapping, date parsing, normalization, and small reusable helpers
- `exception`: domain exceptions and a single global exception handler

Validation happens in two places:

- Bean Validation on incoming request DTOs for required fields and basic numeric constraints
- Service-level validation for rules such as `start <= end`, trade type parsing, and not-found decisions

Error handling is centralized in `GlobalExceptionHandler`, which maps known business errors to `400` or `404` consistently.

## Significant Trade-Off

I chose plain `JdbcTemplate` instead of JPA/Hibernate.

Why:

- The contract is small and query-driven.
- The persistence model is simple enough that explicit SQL is easy to reason about.
- It avoids ORM mapping complexity and gives precise control over ordering, date-range filtering, and duplicate-key behaviour.

The trade-off is writing SQL manually, but in this assessment that was a net win because the API behaviour is highly specific and the data model is narrow.

## No-Trades Semantics

There are two different "no data" cases in the contract, and I treated them differently on purpose:

- `GET /stocks/{symbol}/price`: if the symbol exists but there are no trades in the requested date range, return `200` with `{ "message": "There are no trades in the given date range" }`
- `GET /trades/stocks/{symbol}?type=...&start=...&end=...`: if the symbol has never existed, return `404`; if the symbol exists but there are no matching trades for the requested type/date filter, return `200` with `[]`

This matches the shipped verification tests and is the most consistent reading for client usage:

- A missing symbol is a missing resource.
- An existing symbol with no matches for the current filter is an empty query result.

## Concurrency

Duplicate trade IDs are handled with a database-level guarantee:

- `T_UID` is the primary key in `schema.sql`
- `POST /trades` performs a straight insert
- if two concurrent requests race on the same ID, exactly one insert succeeds
- the losing insert triggers `DataIntegrityViolationException`, which is translated into `400 Bad Request`

I chose the database constraint over application-level locking because it is simpler, more reliable, and keeps the source of truth in one place.

## Testing

I kept the provided verification suite intact and made the Maven test run execute the shipped `TestSuite`, which is how the ordered verification data setup is intended to run.

I also added focused unit tests for the price-summary business logic in `PriceServiceUnitTest`:

- normal case with trades in range
- no trades in range
- inclusive end-date boundary handling
- non-existent symbol
- invalid date range

Those tests cover the mandatory scenarios plus one extra edge case that commonly breaks date filtering.

## Bonus Features Included

- Caffeine-based caching on read-heavy service methods
- Optional HTTP Basic security controlled by configuration
- Actuator health/info/prometheus endpoints
- OpenAPI / Swagger UI
- Dockerfile and Compose support
