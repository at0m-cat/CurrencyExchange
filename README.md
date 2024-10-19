# 💵 Currency Exchange
REST API for describing currencies and exchange rates.
Allows you to view and edit lists of currencies and exchange rates, and perform calculations for converting arbitrary amounts from one currency to another.

### 👓 Motivation of the project

- Getting to know `MVC` pattern
- `REST API` - proper naming of resources, use of HTTP response codes
- `SQL` - basic syntax for creating tables

### 🗃 Stack
> Java EE, Tomcat, PostgreSQL, Maven, Docker, Docker-Compose.

### 🗂 Resources
- [Postman Collection](src/main/resources/CurrencyExchange.postman_collection.json), [Dump DataBase](src/main/resources/dump_db.sql)

### 🔗 Links
- [Java Roadmap by Sergey Zhukov.](https://zhukovsd.github.io/java-backend-learning-course/)
- [Project "Сurrency Exchange".](https://zhukovsd.github.io/java-backend-learning-course/projects/currency-exchange/)

## ➡️ Currencies

### 🔸 GET `/currencies`
> Output of all currencies.
- Example answer:

```json
[
    {
        "id": 1,
        "name": "Afghani",
        "code": "AFN",
        "sign": "؋"
    },
    {
        "id": 2,
        "name": "Lek",
        "code": "ALL",
        "sign": "L"
    },
    {
        "id": 32,
        "name": "Danish Krone",
        "code": "DKK",
        "sign": "kr"
    }
]
```

```
HTTP response codes:
Success - 200
Error (e.g. database unavailable) - 500
```

### 🔸 GET `/currency/EUR`
> Receiving a specific currency.
- Example answer:

```json
{
    "id": 29,
    "name": "Euro",
    "code": "EUR",
    "sign": "€"
}
```

```
HTTP response codes:
Success - 200
Currency code missing in address - 400
Currency not found - 404
Error (e.g. database unavailable) - 500
```

### 🔸 POST `/currencies`
> Adding a new currency to the database. The data is transmitted in the request body as form fields (`x-www-form-urlencoded`). The fields of the form are `name`, `code`, `sign`.
- An example of a response is a JSON representation of a record inserted into the database, including its ID:

```json
{
    "id": 42,
    "name": "Test currency6",
    "code": "TC6",
    "sign": "@$C"
}
```

```
HTTP response codes:
Success - 201
The required form field is missing - 400
The currency with this code already exists - 409
Error (for example, the database is unavailable) - 500
```

## ➡️ ExchangeRates

### 🔸 GET `/exchangeRates`
> Getting a list of all exchange rates.
- Sample response:

```json
[
    {
        "id": 11,
        "base_currency": {
            "id": 8,
            "name": "Russian Ruble",
            "code": "RUB",
            "sign": "₽"
        },
        "target_currency": {
            "id": 7,
            "name": "US Dollar",
            "code": "USD",
            "sign": "$"
        },
        "rate": 0.010000
    },
    {
        "id": 12,
        "base_currency": {
            "id": 9,
            "name": "Ethiopian Birr",
            "code": "ETB",
            "sign": "Br"
        },
        "target_currency": {
            "id": 7,
            "name": "US Dollar",
            "code": "USD",
            "sign": "$"
        },
        "rate": 0.008200
    },
    {
        "id": 24,
        "base_currency": {
            "id": 33,
            "name": "Test currency",
            "code": "SSS",
            "sign": "TC"
        },
        "target_currency": {
            "id": 8,
            "name": "Russian Ruble",
            "code": "RUB",
            "sign": "₽"
        },
        "rate": 2.330000
    }
]
```

```
HTTP response codes:
Success - 200
Error (for example, the database is unavailable) - 500
```

### 🔸 GET `/exchangeRate/RUBUSD`
> Getting a specific exchange rate. The currency pair is set by consecutive currency codes in the request address.
- Sample response:

```json
{
    "id": 11,
    "base_currency": {
        "id": 8,
        "name": "Russian Ruble",
        "code": "RUB",
        "sign": "₽"
    },
    "target_currency": {
        "id": 7,
        "name": "US Dollar",
        "code": "USD",
        "sign": "$"
    },
    "rate": 0.010000
}
```

```
HTTP response codes:
Success - 200
The currency codes of the pair are missing in the address - 400
The exchange rate for the pair was not found - 404
Error (for example, the database is unavailable) - 500
```

### 🔸 POST `/exchangeRates`
> Adding a new exchange rate to the database. The data is transmitted in the request body as form fields (`x-www-form-urlencoded`). The fields of the form are `baseCurrencyCode`, `targetCurrencyCode`, `rate`. Example of form fields:
- > `baseCurrencyCode` = PPP
- > `targetCurrencyCode` = XXX
- > `rate` = 221.002
- An example of a response is a JSON representation of a record inserted into the database, including its ID:

```json
{
    "id": 30,
    "base_currency": {
        "id": 37,
        "name": "Test currency3",
        "code": "PPP",
        "sign": "PT"
    },
    "target_currency": {
        "id": 41,
        "name": "Test currency5",
        "code": "XXX",
        "sign": "XXX"
    },
    "rate": 221.002000
}
```

```
HTTP response codes:
Success - 201
The required form field is missing - 400
A currency pair with this code already exists - 409
One (or both) currency from the currency pair does not exist in DB - 404
Error (for example, the database is unavailable) - 500
```

### 🔸 PATCH `/exchangeRate/USDRUB`

> Updating the existing exchange rate in the database. The currency pair is set by consecutive currency codes in the request address. The data is transmitted in the request body as form fields (`x-www-form-urlencoded`). The only field in the form is `rate`.
- An example of a response is a JSON representation of a record inserted into the database, including its ID:

```json
{
    "id": 30,
    "base_currency": {
        "id": 37,
        "name": "Test currency3",
        "code": "PPP",
        "sign": "PT"
    },
    "target_currency": {
        "id": 41,
        "name": "Test currency5",
        "code": "XXX",
        "sign": "XXX"
    },
    "rate": 2.330000
}
```

```
HTTP response codes:
Success - 200
The required form field is missing - 400
The currency pair is missing in the database - 404
Error (for example, the database is unavailable) - 500
```

## ➡️ CurrencyExchange

### 🔸 GET `/exchange?from=BASE_CURRENCY_CODE&to=TARGET_CURRENCY_CODE&amount=$AMOUNT`

> Calculation of the transfer of a certain amount of funds from one currency to another.
> Obtaining an exchange rate can take place according to one of three scenarios: `direct exchange`, `reverse exchange`, `exchange through an intermediary`.
- An example of a request is GET `/exchange?from=ETB&to=RUB&amount=10`.

```json
{
    "base_currency": {
        "id": 9,
        "name": "Ethiopian Birr",
        "code": "ETB",
        "sign": "Br"
    },
    "target_currency": {
        "id": 8,
        "name": "Russian Ruble",
        "code": "RUB",
        "sign": "₽"
    },
    "rate": 0.800000,
    "amount": 10.0,
    "converted_amount": 8.0000000
}
```

## ➡️ Exceptions
> Errors that are possible: `DataBaseNotAvailableException`, `IncorrectParamsException`, `ObjectAlreadyExistException`, `ObjectNotFoundException`.
- For all requests, in case of an error, the response may look like this: 

```json
{
    "message": "Currency pair not found :("
}
```

- The value of the `message` depends on what kind of error occurred.
