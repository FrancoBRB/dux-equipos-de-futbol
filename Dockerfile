FROM openjdk:17-oracle AS builder

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-oracle

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]