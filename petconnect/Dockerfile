FROM amazoncorretto:21
LABEL authors="juliane.maran"

WORKDIR /app

COPY target/petconnect-0.0.1-SNAPSHOT.jar /app/petconnect-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/app/petconnect-0.0.1-SNAPSHOT.jar"]