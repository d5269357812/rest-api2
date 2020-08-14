FROM openjdk:14-alpine
COPY build/libs/rest-api2-*-all.jar rest-api2.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "rest-api2.jar"]