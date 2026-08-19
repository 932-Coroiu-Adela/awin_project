# Transaction API

A small REST API for creating transactions and approving or declining them.

A transaction is created with a `PENDING` status and can be changed only once to either `APPROVED` or `DECLINED`.

## Requirements

- Java 25

Gradle does not need to be installed because the project includes the Gradle wrapper.

## Running the application

From the project directory, run:


.\gradlew.bat bootRun

The application will start at:

http://localhost:8080

Interacting with the API:

Request a transaction creation:
curl.exe -i -X POST http://localhost:8080/transactions `
  -H "Content-Type: application/json" `
  -d '{\"saleAmount\":100.00,\"commissionAmount\":10.00}'

Request to approve the transaction which was previously created:
curl.exe -i -X PATCH http://localhost:8080/transactions/1/approve

Request to decline the transaction which was previously created (should create another transaction, as we are allowed to change its status only once):
curl.exe -i -X PATCH http://localhost:8080/transactions/2/decline

## AI usage:
AI was used to:
1. Discuss the initial project structure and dependency selection.
2. Clarify Spring MVC, JPA, validation, and testing concepts.
3. Suggest relevant test cases.
4. Review implementation ideas and identify potential errors.
The suggestions were reviewed and adapted to the requirements of the exercise.
The resulting behavior was validated using automated tests and manual HTTP requests.

