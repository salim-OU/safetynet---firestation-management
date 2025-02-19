# SafetyNet Alerts API Documentation

## Person Controller
Base URL: `/api`

### Get All Persons
```http
GET /persons
```
Returns a list of all persons.

### Get Person by Name
```http
GET /person?firstName={firstName}&lastName={lastName}
```
Returns a specific person by their first and last name.

### Get Community Emails
```http
GET /communityEmail?city={city}
```
Returns email addresses of all persons in a specific city.

### Get Person Info
```http
GET /personInfo?firstName={firstName}&lastName={lastName}
```
Returns detailed information about a specific person.

### Get Child Alert
```http
GET /childAlert?address={address}
```
Returns a list of children (≤18 years) at the specified address with other household members.

## Fire Station Controller
Base URL: `/api`

### Get All Fire Stations
```http
GET /firestations
```
Returns a list of all fire stations.

### Get Fire Stations by Station Number
```http
GET /firestations/station/{station}
```
Returns fire stations for a specific station number.

### Get Fire Station by Address
```http
GET /firestations/address/{address}
```
Returns the fire station serving a specific address.

### Create Fire Station
```http
POST /firestations
Content-Type: application/json

{
    "address": "string",
    "station": "string"
}
```
Creates a new fire station mapping.

### Update Fire Station
```http
PUT /firestations/{address}
Content-Type: application/json

{
    "address": "string",
    "station": "string"
}
```
Updates an existing fire station mapping.

### Delete Fire Station
```http
DELETE /firestations/{address}
```
Deletes a fire station mapping.

### Get Addresses by Station
```http
GET /firestations/addresses/{station}
```
Returns all addresses served by a specific station number.

### Get Station Coverage
```http
GET /firestation?stationNumber={station_number}
```
Returns residents covered by the station with counts of adults and children.

### Get Fire Address Info
```http
GET /fire?address={address}
```
Returns residents at an address with the serving station number.

## Medical Record Controller
Base URL: `/api`

### Get All Medical Records
```http
GET /medicalRecords
```
Returns a list of all medical records.

### Get Medical Record by Person
```http
GET /medicalRecord?firstName={firstName}&lastName={lastName}
```
Returns the medical record for a specific person.

### Create Medical Record
```http
POST /medicalRecords
Content-Type: application/json

{
    "firstName": "string",
    "lastName": "string",
    "birthdate": "string",
    "medications": ["string"],
    "allergies": ["string"]
}
```
Creates a new medical record.

### Update Medical Record
```http
PUT /medicalRecords/{firstName}/{lastName}
Content-Type: application/json

{
    "firstName": "string",
    "lastName": "string",
    "birthdate": "string",
    "medications": ["string"],
    "allergies": ["string"]
}
```
Updates an existing medical record.

### Delete Medical Record
```http
DELETE /medicalRecords/{firstName}/{lastName}
```
Deletes a medical record.

## Response Format Examples

### Person Response
```json
{
    "firstName": "John",
    "lastName": "Boyd",
    "address": "1509 Culver St",
    "city": "Culver",
    "zip": "97451",
    "phone": "841-874-6512",
    "email": "jaboyd@email.com"
}
```

### Fire Station Response
```json
{
    "address": "1509 Culver St",
    "station": "3"
}
```

### Medical Record Response
```json
{
    "firstName": "John",
    "lastName": "Boyd",
    "birthdate": "03/06/1984",
    "medications": ["aznol:350mg", "hydrapermazol:100mg"],
    "allergies": ["nillacilan"]
}
```