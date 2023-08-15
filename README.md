# SpringOauth

**********************************************************************

#1: Save User

http://localhost:8080/api/v1/auth/register 
send JSON in Post Request

{
    "firstname" : "Joe",
    "lastname" : "Doe",
    "email":"123@gmail.com",
    "password":"123"
}

**********************************************************************

#2. Authenticate

http://localhost:8080/api/v1/auth/authenticate
send JSON in Post Request

{
    "email" : "123@gmail.com",
    "password" : "123"
}

copy the token from repsone

**********************************************************************

3. Visit endpoint

http://localhost:8080/api/v1/demo-controller

add token as authorization bearer

**********************************************************************

