FROM amazoncorretto:17-alpine3.15
LABEL MANTAINER="Vektrom Bonilha"

WORKDIR /app

EXPOSE 8083

RUN apk add --no-cache curl

RUN wget -O dd-java-agent.jar 'https://dtdg.co/latest-java-tracer'

COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]