# Senegal Example App

## How to build and start the example app (server)
1. Gradle "generate" command to run code generation
2. Start the DB App in app-hsqldb-server:ch/senegal/example/hsqlserver/HsqlServerApplication.kt
3. Migrate the DB with gradle "dropAll update"
4. Start the Spring Boot Example App in app:ch/senegal/example/app/SenegalExampleApplication.kt

## How to build and start the example app (frontend)
1. Go to the directory senegal-example-app/frontend
2. Run "npm install"
3. Run "npm run start"
4. Call the app under http://localhost:4200/
