# docker build -t app-authors .
# pone un nombre y el punto indica el directorio actual
FROM eclipse-temurin:21.0.5_11-jre-alpine


RUN mkdir /app
WORKDIR /app
# me evitar poner el /deployments

COPY build/quarkus-app/lib ./lib
COPY build/quarkus-app/quarkus ./quarkus
COPY build/quarkus-app/app ./app
COPY build/quarkus-app/quarkus-run.jar app.jar

#los primeros archibos son los que cambian con
#poca frecuencia.
# es importante porque cuando se copie la imagen
#no tengo que copiar nada. sin envargo si se hace alrevez
# copiaria redundantemente creo.

CMD ["java", "-jar", "app.jar"]
