# CSE2115 - Project

### Description
This application is the API for a sports center management system.

### Usage
The application is secured using a JWT token, so a user will either need to log in or register to access the methods.

###### <u> Connecting to Database locally </u>

The database is hosted on an AWS server.
- Hostname: op19-sem37.c0aem7bl4xxc.eu-central-1.rds.amazonaws.com
- Port: 3306
- Username: admin
- Password: rootroot

###### <u> Logging in </u>

Path: localhost:8081/gateway/auth

Type: post

Body: the username and password as a JSON object
- For an admin account use "admin" and "pwd" as the username and password respectively.
- For a regular user account, use "user" and "pwd" ad the username and password
- Example body: {"username": "admin", "password": "pwd"}

Once this is sent, the response should say "logged in". Copy the JWT from the "Authentication" response header (without the "Bearer") and paste it in the "Authorization" request header.

All subsequent requests should contain this header.

###### <u> Creating requests </u>

All requests must be done through the gateway microservice.

Ensure that all needed microservices are running first
- EUREKA MUST BE RUN FIRST
- Authentication and Gateway must be run next
- All other microservices can then be run

Create requests in postman(FULL EXAMPLE)
-  gateway Port: 8081
- Example request: localhost:8081/gateway/allEquipment
  -Returns all the equipment in the repository as a sequence


### Running
`gradle bootRun`

### Testing
```
gradle test
```

To generate a coverage report:
```
gradle jacocoTestCoverageVerification
```


And
```
gradle jacocoTestReport
```
The coverage report is generated in: build/reports/jacoco/test/html, which does not get pushed to the repo. Open index.html in your browser to see the report.

### Static analysis
```
gradle checkStyleMain
gradle checkStyleTest
gradle pmdMain
gradle pmdTest
```

### Notes
- You should have a local .gitignore file to make sure that any OS-specific and IDE-specific files do not get pushed to the repo (e.g. .idea). These files do not belong in the .gitignore on the repo.
- If you change the name of the repo to something other than template, you should also edit the build.gradle file.
- You can add issue and merge request templates in the .gitlab folder on your repo.
- For the integration testing of the different microservices, make sure that there exists certain instances of the entities with IDs used in the test methods. E.g. for the equipment reservation, there must exists an equipment reservation with ID 50 and 51. And for the field reservation one with ID 9999 and 9998.
