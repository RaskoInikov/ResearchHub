# ResearchHub RAMS – Article REST API
ResearchHub RAMS is a Spring Boot REST API application. The application provides basic CRUD-like operations for managing a core domain entity — Article.

The project demonstrates a layered architecture approach:
Controller → Service → Repository

It also includes DTO mapping, custom exception handling, and static code analysis configuration.

## Usage
### Build the Application
./gradlew build

### Run the Application
./gradlew bootRun

### Application CheckStyle
./gradlew checkstyleMain

## SonarCloud
Project analysis is available at:

https://sonarcloud.io/project/overview?id=RaskoInikov_ResearchHub

## ER Diagram
<img width="678" height="861" alt="image" src="https://github.com/user-attachments/assets/e62fd85a-de38-4c1a-84a7-2c0970b5fbae" />
