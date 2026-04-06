# Verification contract

The verification test suite expects the following API. Implement this contract and set `assessment.api.base-path` in `src/test/resources/application.properties` if your API is not at the root.

All paths below are **relative to the base path** (e.g. if base path is `/api`, then “DELETE /erase” means `DELETE /api/erase`).

---

## Trade representation

JSON shape:

```json
{
  "id": 1,
  "type": "buy",
  "user": {
    "id": 1,
    "name": "David"
  },
  "symbol": "AC",
  "shares": 28,
  "price": 162.17,
  "timestamp": "2014-06-14 13:13:13"
}
```

- `id`: integer, unique.
- `type`: string, `"buy"` or `"sell"`.
- `user.id`: integer; `user.name`: string.
- `symbol`: string (e.g. `"AAPL"`).
- `shares`: integer (e.g. 10–30).
- `price`: decimal (e.g. 130.42–195.65).
- `timestamp`: string, format `yyyy-MM-dd HH:mm:ss` (GMT).

---

## Endpoints

### 1. Erase all trades

- **Method/Path:** `DELETE /erase`
- **Response:** `200 OK` (no body required).

---

### 2. Add a new trade

- **Method/Path:** `POST /trades`
- **Body:** Trade JSON as above.
- **Success:** `201 Created`.
- **Error:** `400 Bad Request` if a trade with the same ID already exists.

---

### 3. Get trade by ID

- **Method/Path:** `GET /trades/{id}`
- **Success:** `200 OK`, body = single Trade JSON.
- **Error:** `404 Not Found` if the ID does not exist.

---

### 4. Get all trades

- **Method/Path:** `GET /trades`
- **Response:** `200 OK`, body = JSON array of trades, **sorted by ID ascending**.

---

### 5. Get trades by user ID

- **Method/Path:** `GET /trades/users/{userID}`
- **Success:** `200 OK`, body = JSON array of trades for that user, sorted by ID.
- **Error:** `404 Not Found` if the user has no trades.

---

### 6. Get trades by stock symbol, type, and date range

- **Method/Path:** `GET /trades/stocks/{stockSymbol}?type={tradeType}&start={startDate}&end={endDate}`
- **Query:** `type` = `buy` or `sell`; `start` and `end` = date in `yyyy-MM-dd`, inclusive.
- **Success:** `200 OK`, body = JSON array of matching trades.
- **Error:** `404 Not Found` if the stock symbol has no trades in the system (for this filter).

---

### 7. Highest and lowest price for symbol in date range

- **Method/Path:** `GET /stocks/{stockSymbol}/price?start={startDate}&end={endDate}`
- **Query:** `start` and `end` = date in `yyyy-MM-dd`, inclusive.

**When there is at least one trade in range:**

- **Response:** `200 OK`
- **Body:**
  ```json
  {
    "symbol": "AC",
    "highest": 180.45,
    "lowest": 132.14
  }
  ```

**When there are no trades in the given date range:**

- **Response:** `200 OK`
- **Body:**
  ```json
  {
    "message": "There are no trades in the given date range"
  }
  ```

**When the symbol does not exist (no trades ever):**

- **Response:** `404 Not Found`

---

## Notes for implementers

- Date/time: use GMT. Timestamps in trade payloads are `yyyy-MM-dd HH:mm:ss`; query params use `yyyy-MM-dd`.
- JSON key names must match exactly (e.g. `timestamp`, not `Timestamp`).
- The verification suite uses the configurable base path; ensure your controllers are mounted so that the full path matches the contract.
