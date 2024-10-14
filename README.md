# Currency Exchange
REST API for describing currencies and exchange rates.
Allows you to view and edit lists of currencies and exchange rates, and perform calculations for converting arbitrary amounts from one currency to another.

- [Java Roadmap by Sergey Zhukov.](https://zhukovsd.github.io/java-backend-learning-course/)
- [Project "Сurrency Exchange".](https://zhukovsd.github.io/java-backend-learning-course/projects/currency-exchange/)

- ## Stack
- Java EE, PostgreSQL, Maven, Docker-Compose.

## Requests

### GET /currencies
- example of output of all currencies
```
HTTP response codes:

Success - 200
Error (e.g. database unavailable) - 500
```
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
        "id": 3,
        "name": "Algerian Dinar",
        "code": "DZD",
        "sign": ".د.ج"
    },
    {
        "id": 4,
        "name": "Zloty",
        "code": "PLN",
        "sign": "zł"
    },
    {
        "id": 5,
        "name": "Zimbabwe Dollar",
        "code": "ZWL",
        "sign": "Z$"
    },
    {
        "id": 6,
        "name": "Uzbekistan Sum",
        "code": "UZS",
        "sign": "Soʻm"
    },
    {
        "id": 7,
        "name": "US Dollar",
        "code": "USD",
        "sign": "$"
    },
    {
        "id": 8,
        "name": "Russian Ruble",
        "code": "RUB",
        "sign": "₽"
    },
    {
        "id": 9,
        "name": "Ethiopian Birr",
        "code": "ETB",
        "sign": "Br"
    },
    {
        "id": 28,
        "name": "Australian Dollar",
        "code": "AUD",
        "sign": "A$"
    },
    {
        "id": 29,
        "name": "Euro",
        "code": "EUR",
        "sign": "€"
    },
    {
        "id": 31,
        "name": "CFP Franc",
        "code": "XPF",
        "sign": "F"
    },
    {
        "id": 32,
        "name": "Danish Krone",
        "code": "DKK",
        "sign": "kr"
    }
]
```

### GET /currency/EUR
- Receiving a specific currency. Example answer:
```
HTTP response codes:

Success - 200
Currency code missing in address - 400
Currency not found - 404
Error (e.g. database unavailable) - 500
```
```json
  {
    "id": 29,
    "name": "Euro",
    "code": "EUR",
    "sign": "€"
}
```

### POST /currencies

