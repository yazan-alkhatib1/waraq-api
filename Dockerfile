# Build JAR
FROM maven:3.9-amazoncorretto-17 AS build
WORKDIR /usr/src/waraq-api/
COPY . .
RUN mvn dependency:go-offline

RUN  mvn -f /usr/src/waraq-api/pom.xml clean install


FROM openjdk:17
# Environment Variables
COPY --from=build /usr/src/waraq-api/api/target/api-*.jar /opt/app.jar
WORKDIR /opt
EXPOSE 8080 80
CMD ["java","-jar" , "/opt/app.jar"]
