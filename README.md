# ResearchHub RAMS – Article REST API
ResearchHub RAMS is a Spring Boot REST API application. The application provides basic CRUD-like operations for managing a core domain entity — Article.

The project demonstrates a layered architecture approach:
Controller → Service → Repository

It also includes DTO mapping, custom exception handling, and static code analysis configuration.

# Usage
## Application Run
./gradlew build

./gradlew bootRun

## Application CheckStyle
./gradlew checkstyleMain

# SonarCloud
Project analysis is available at:

https://sonarcloud.io/project/overview?id=RaskoInikov_ResearchHub
