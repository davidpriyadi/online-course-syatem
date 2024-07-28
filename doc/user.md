# User API Spec

## Register User

Endpoint : POST /api/users

Request Body :

```json
{
  "email" : "david@yopmail.com",
  "password" : "rahasia",
  "name" : "David",
  "address" : "Jakarta"
}
```

Response Status (Success) : 200

Response Body (Failed) :

```json
{
  "message": "Validation failed",
  "details": {
    "name": "must be less than or equal to 12",
    "email": "must be a well-formed email address"
  }
}
```

## Login User

Endpoint : POST /api/auth/login

Request Body :

```json
{
  "email" : "david@yopmail.com",
  "password" : "rahasia" 
}
```

Response Body (Success) :

```json
{
  "token": "TOKEN"
}
```

Response Body (Failed, 401) :

```json
{
  "errors" : "Username or password wrong"
}
```

## Get User

Endpoint : GET /api/users/current

Request Header :

- Authorization : Bearer Token (Mandatory)

Response Body (Success) :

```json
{
  "email": "david@yopmail.com",
  "name": "Eko Kurniawan Khannedy"
}
```

Response Body (Failed, 401) :

```json
{
  "errors" : "Unauthorized"
}
```

## Get All Users with Pagination

Endpoint: GET /api/users

Request Parameters:
- `page` (optional, default: 0): The page number to retrieve.
- `size` (optional, default: 10): The number of records per page.

Request Header:
- Authorization: Bearer Token (Mandatory)

Response Body (Success):
```json
{
  "content": [
    {
      "id": 1,
      "name": "User One",
      "email": "user1@example.com",
      "createdAt": "2023-01-01T00:00:00",
      "updatedAt": "2023-01-01T00:00:00"
    },
    {
      "id": 2,
      "name": "User Two",
      "email": "user2@example.com",
      "createdAt": "2023-01-02T00:00:00",
      "updatedAt": "2023-01-02T00:00:00"
    }
  ],
  "pageable": {
    "sort": {
      "sorted": false,
      "unsorted": true,
      "empty": true
    },
    "pageNumber": 0,
    "pageSize": 10,
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalPages": 1,
  "totalElements": 2,
  "last": true,
  "first": true,
  "sort": {
    "sorted": false,
    "unsorted": true,
    "empty": true
  },
  "numberOfElements": 2,
  "size": 10,
  "number": 0,
  "empty": false
}
```
Response Body (Failed, 401) :

```json
{
  "errors" : "Unauthorized"
}
```

## Logout User

Endpoint : DELETE /api/auth/logout

Request Header :

- Authorization : Bearer Token (Mandatory)

Response Status (Success) : 200
