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
- GET /statistics/fines/{type} - Obține statistici detaliate pe tipuri de amenzi.
- GET /owners/{ownerId}/vehicles - Listează toate vehiculele deținute de un proprietar specific.
- GET /vehicles/{vehicleId}/fines - Obține istoricul amenzilor pentru un vehicul specific.

- POST /fines/pay - Procesează plata unei amenzi.

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


[//]: # (Pe viitor v-om extrage licensePlate intr-o tabela aparte, pentru ca el este un obiect care apartie lui owner id, nu lui vehicle)

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

[//]: # (Pe viitor v-om extrage address intr-o tabela aparte pentru a normaliza tabela)
"address": "Str. Libertatii, nr. 10, Bucuresti",
"phoneNumber": "060423120"


