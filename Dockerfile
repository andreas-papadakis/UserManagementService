FROM eclipse-temurin:17-alpine
WORKDIR /user_management_service_app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN sed -i 's/\r$//' mvnw #change line ending format to UNIX
RUN ./mvnw dependency:go-offline

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]