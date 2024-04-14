# Tehnologii:

- Java 21
- Spring Boot 3.2.4
- Postgresql
- Docker

# API

- POST /vehicles - Creează o nouă înregistrare a unui vehicul.
- GET /vehicles - Obține o listă a tuturor vehiculelor înregistrate.
- GET /vehicles/{vehicleId} - Obține detalii despre un vehicul specific, folosind ID-ul acestuia.
- PUT /vehicles/{vehicleId} - Actualizează informațiile unui vehicul existent.
- DELETE /vehicles/{vehicleId} - Șterge un vehicul din sistem.

- POST /owners - Înregistrează un nou proprietar de vehicul.
- GET /owners - Obține o listă a tuturor proprietarilor de vehicule.
- GET /owners/{ownerId} - Obține detalii despre un proprietar specific.
- PUT /owners/{ownerId} - Actualizează informațiile unui proprietar existent.
- DELETE /owners/{ownerId} - Șterge un proprietar din sistem.

- POST /fines - Emite o nouă amendă.
- GET /fines - Obține o listă a tuturor amenzilor.
- GET /fines/{fineId} - Obține detalii despre o amendă specifică.
- PUT /fines/{fineId} - Actualizează detalii despre o amendă existentă.
- DELETE /fines/{fineId} - Anulează o amendă înregistrată.

- GET /statistics/fines - Obține statistici generale despre amenzile emise.
- GET /statistics/fines/{violation} - Obține statistici detaliate pe tipuri de incalcare de amenzi.

- GET /vehicles/{vehicleId}/owners - Obține istoricul proprietarilor pentru un vehicul specific.

- GET /vehicles/{vehicleId}/fines - Obține istoricul amenzilor pentru un vehicul specific.
- PUT /vehicles/{vehicleId}/transfer - Transferă proprietatea unui vehicul de la un proprietar la altul

- GET /owners/{ownerId}/vehicles - Listează toate vehiculele deținute de un proprietar specific.
- GET /owners/{ownerId}/active-fines - Listează toate amenzile neplătite pentru un proprietar specific.

- POST /fines/pay - Procesează plata unei amenzi.
- GET /fines/recent - Obține o listă a amenzilor emise în ultimele 30 de zile.

# Structura detaliata API-ului

# Creează o nouă înregistrare a unui vehicul

- URL: /vehicles
- METHOD: POST
- BODY:

```json
{
  "vin": "1HGBH41JXMN109186",
  "licensePlate": "GHI789",
  "ownerId": 103,
  "make": "Honda",
  "model": "Civic",
  "year": 2022
}
```

- RESPONSE BODY:

```json
{
  "vehicleId": 3,
  "vin": "1HGBH41JXMN109186",
  "licensePlate": "B789GHI",
  "ownerId": 103,
  "make": "Honda",
  "model": "Civic",
  "year": 2022
}
```

- RESPONSE STATUS CODE: 201

# Obține o listă a tuturor vehiculelor înregistrate

- URL: /vehicles
- METHOD: GET
- BODY: N/A (nu este necesar pentru metoda GET)
- RESPONSE BODY:

```json
[
  {
    "vehicleId": 1,
    "vin": "1HGBH41JXMN109186",
    "licensePlate": "B123ABC",
    "ownerId": 101,
    "make": "Toyota",
    "model": "Corolla",
    "year": 2021
  },
  {
    "vehicleId": 2,
    "vin": "1HGBH41JXMN109186",
    "licensePlate": "B456DEF",
    "ownerId": 102,
    "make": "Ford",
    "model": "Fiesta",
    "year": 2020
  }
]
```

- RESPONSE STATUS CODE: 200

# Obține detalii despre un vehicul specific, folosind numarull acestuia

- URL: /vehicles/{licensePlate}
- METHOD: GET
- BODY: N/A (nu este necesar pentru metoda GET)
- RESPONSE BODY:

```json
{
  "vehicleId": 1,
  "vin": "1HGBH41JXMN109186",
  "licensePlate": "B123ABC",
  "ownerId": 101,
  "make": "Toyota",
  "model": "Corolla",
  "year": 2021
}
```

- RESPONSE STATUS CODE: 200

# Actualizează informațiile unui vehicul existent

- URL:  /vehicles/{licensePlate}
- METHOD: PUT
- BODY:

```json
{
  "ownerId": 104,
  "make": "Honda",
  "model": "Accord",
  "year": 2023
}
```

- RESPONSE BODY:

```json
{
  "vehicleId": 3,
  "vin": "1HGBH41JXMN109186",
  "licensePlate": "B789GHI",
  "ownerId": 104,
  "make": "Honda",
  "model": "Accord",
  "year": 2023,
  "message": "Vehicle updated successfully"
}
```

- RESPONSE STATUS CODE: 200

# Șterge un vehicul din sistem

- URL: DELETE /vehicles/{vehicleId}
- BODY: N/A (nu este necesar pentru metoda DELETE)
- RESPONSE BODY:
- RESPONSE STATUS CODE: 200

```json
{
  "message": "Vehicle deleted successfully"
}
```

# Înregistrează un nou proprietar de vehicul

- URL: /owners
- METHOD: POST
- BODY:

```json
{
  "firstName": "Ion",
  "lastName": "Popescu",
  "address": "Str. Libertatii, nr. 10, Bucuresti",
  "phoneNumber": "060423120"
}
```

- RESPONSE BODY:

```json
{
  "ownerId": 201,
  "firstName": "Ion",
  "lastName": "Popescu",
  "address": "Str. Libertatii, nr. 10, Bucuresti",
  "phoneNumber": "060423120"
}
```

- RESPONSE STATUS CODE: 201

# Obține o listă a tuturor proprietarilor de vehicule

- URL: /owners
- METHOD: GET
- BODY: N/A
- RESPONSE BODY:

```json
[
  {
    "ownerId": 201,
    "firstName": "Ion",
    "lastName": "Popescu",
    "address": "Str. Libertatii, nr. 10, Bucuresti",
    "phoneNumber": "060423120"
  },
  {
    "ownerId": 202,
    "firstName": "Maria",
    "lastName": "Ionescu",
    "address": "Str. Revolutiei, nr. 22, Cluj",
    "phoneNumber": "061223421"
  }
]
```

- RESPONSE STATUS CODE: 200 OK

# Obține detalii despre un proprietar specific

- URL: /owners/{ownerId}
- METHOD: GET
- BODY: N/A
- RESPONSE BODY:

```json
{
  "ownerId": 201,
  "firstName": "Ion",
  "lastName": "Popescu",
  "address": "Str. Libertatii, nr. 10, Bucuresti",
  "phoneNumber": "061223421"
}
```

- RESPONSE STATUS CODE: 200 OK

# Actualizează informațiile unui proprietar existent

- URL: /owners/{ownerId}
- METHOD: PUT
- BODY:

```json
{
  "firstName": "Ionel",
  "lastName": "Popescu",
  "address": "Str. Libertatii, nr. 15, Bucuresti",
  "phoneNumber": "061223421"
}
```

- RESPONSE BODY:

```json
{
  "ownerId": 201,
  "firstName": "Ionel",
  "lastName": "Popescu",
  "address": "Str. Libertatii, nr. 15, Bucuresti",
  "phoneNumber": "061223421"
}
```

- RESPONSE STATUS CODE: 200 OK

# Șterge un proprietar din sistem

- URL: /owners/{ownerId}
- METHOD: DELETE
- BODY: N/A
- RESPONSE BODY:

```json
{
  "message": "Owner deleted successfully"
}
```

- RESPONSE STATUS CODE: 200 OK

# Emite o nouă amendă

- URL: POST /fines
- METHOD: POST
- BODY:

```json
{
  "vehicleId": 1,
  "ownerId": 101,
  "amount": 500,
  "violation": "Speeding",
  "date": "2024-04-12",
  "location": "Bucuresti, Sector 1"
}
```

- RESPONSE BODY:

```json
{
  "fineId": 301,
  "vehicleId": 1,
  "ownerId": 101,
  "amount": 500,
  "violation": "Speeding",
  "date": "2024-04-12",
  "location": "Bucuresti, Sector 1",
  "message": "Fine issued successfully"
}
```

- RESPONSE STATUS CODE: 201

# Obține o listă a tuturor amenzilor

- URL: GET /fines
- METHOD: GET
- BODY: N/A
- RESPONSE BODY:

```json
[
  {
    "fineId": 301,
    "vehicleId": 1,
    "ownerId": 101,
    "amount": 500,
    "violation": "Speeding",
    "date": "2024-04-12",
    "location": "Bucuresti, Sector 1"
  },
  {
    "fineId": 302,
    "vehicleId": 2,
    "ownerId": 102,
    "amount": 300,
    "violation": "Parking Violation",
    "date": "2024-04-11",
    "location": "Cluj, Centru"
  }
]
```

- RESPONSE STATUS CODE: 200 OK

# Obține detalii despre o amendă specifică

- URL: GET /fines/{fineId}
- METHOD: GET
- BODY: N/A
- RESPONSE BODY:

```json
{
  "fineId": 301,
  "vehicleId": 1,
  "ownerId": 101,
  "amount": 500,
  "violation": "SPEEDING",
  "date": "2024-04-12",
  "location": "Chisinau, Sector 1"
}
```

- RESPONSE STATUS CODE: 200 OK

# Actualizează detalii despre o amendă existentă

- URL: PUT /fines/{fineId}
- METHOD: PUT
- BODY:

```json
{
  "amount": 550,
  "violation": "Excessive Speeding",
  "date": "2024-04-12",
  "location": "Bucuresti, Sector 1"
}
```

- RESPONSE BODY:

```json
{
  "fineId": 301,
  "vehicleId": 1,
  "ownerId": 101,
  "amount": 550,
  "violation": "Excessive Speeding",
  "date": "2024-04-12",
  "location": "Bucuresti, Sector 1"
}
```

- RESPONSE STATUS CODE: 200 OK

# Anulează o amendă înregistrată

- URL: DELETE /fines/{fineId}
- METHOD: DELETE
- BODY: N/A
- RESPONSE BODY:

```json
{
  "message": "Fine deleted successfully"
}
```

- RESPONSE STATUS CODE: 200 OK

# Nu implementam urmatoarele pana cand

```text
Pe viitor v-om extrage licensePlate intr-o tabela aparte, pentru ca el este un obiect care apartie lui owner id, nu lui vehicle
```

- POST /licensePlate - Creează o nouă înregistrare a unui licensePlate
- GET /licensePlate - Obține o listă a tuturor licensePlate înregistrate
- GET /licensePlate/{licensePlateId} - Obține detalii despre un licensePlate specific, folosind ID-ul acestuia.
- PUT /licensePlate/change-owner/{licensePlateId}/{ownerId}
- PUT /licensePlate/change-vehicle/{licensePlateId}/{vehicleId}
- DELETE /licensePlate/{licensePlateId} - Șterge un licensePlate din sistem.

| ID | licensePlate | OwnerID | VehicleId |
|----|--------------|---------|-----------|
| 1  | ZYD 720      | 1       | 1         |
| 2  | DCC 220      | 1       | 2         |
| 3  | DCC 220      | 1       | 2         |

```text
Pe viitor v-om extrage address si phoneNumber intr-o tabela aparte pentru a normaliza tabela
"address": "Str. Libertatii, nr. 10, Bucuresti",
"phoneNumber": "060423120"
``` 


