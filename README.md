# Parking-lot-manager

A simple parking lot managment system using spring Boot that allows users to manage parking spots. The system enables the creation, reservation, and status checking of parking spots through a REST API and a GraphQL API depending on users preferance. It is built using reactive programming principles, allowing for non-blocking interactions and improved performance.

To run this you will need to clone the project and run docker command in the root of the project `docker-compose up --build`

All API requests have to be made to this base URL `http://localhost:8080/`

When the project is running you can use the following endpoints:
1. Using POST `api/parking-spots` with an apllication/json content type for example  `{"name": "A1"}` will creatre a new parking spot
2. Using GET `api/parking-spots` will return an array of all parking spots with their respective data for example `[{"id": 1,"name": "A1","reserved": false}]`
3. Using POST `/api/parking-spots/{id}/reserve` will reserve a parking spot if available to be reserved
4. Using GET `/api/parking-spots/{id}/status` will return either true or false depending on if the parking spot is reserved
5. Using POST `/graphql` with an apllication/json content type and mutation like this for example `{"query":"mutation{createParkingSpot(name:\"A1\"){id name reserved}}"}` will create a new parking spot using GraphQL
6. Using POST `/graphql` with an application/json content type and query like this for example `{"query":"{parkingSpots{id name reserved}}"}` will return a complete list of parking spots
7. Using POST `/graphql` with an application/json content type and query like this for example `{"query":"{parkingSpot(id:\"1\")}"}` will return either true or fasle regarding reserved porperty
8. Using POST `/graphql` with an application/json content type and mutation like this for example `{"query":"mutation{reserveParkingSpot(id: 1){id name reserved}}"}` will reserve that parking spot
   

   
