### Docker

## el pull permite descargar la imagen de postgres para alpine
## el run crea un contenedor/instancia con la imagen
```bash
docker pull postgres:17.2-alpine3.21

docker run --name dbsever1 -p 54321:5432 -e POSTGRES_PASSWORD=123 -e POSTGRES_DB=distribuida -d postgres:17.2-alpine3.21

```

### consul y traefik
    
## los pulls para descargar la imagen de consul y de traefik.
## el build permite crear una imagen a partir de un Dockerfile
## -f permite crear con contenedor CREo ojo creo
```bash
docker pull consul:1.15.4

docker pull traefik:v3.3.1

docker build -t app-authors .
#docker build -f path/to/Dockerfile -t app-authors .
#docker -f path/to/Dockerfile build -t app-authors .

docker build -t app-books .
#docker -f path/to/Dockerfile build -t app-books .
#docker build -f Dockerfile -t app-books .
```

### desplegar
1. base de datos

docker run --name dbsever1 -p 54321:5432 -e POSTGRES_PASSWORD=123 -e POSTGRES_DB=distribuida -d postgres:17.2-alpine3.21


2. Autores
docker run --name app-authors -p 9090:9090 -e QUARKUS_DATASOURCE_USERNAME=postgres -e QUARKUS_DATASOURCE_PASSWORD=123 -e QUARKUS_DATASOURCE_JDBC_URL=jdbc:postgresql://172.18.0.3:5432/distribuida -d app-authors


3. vamos a la raiz y ponemos:

```bash

 docker compose up 
 docker compose up -d
 docker compose -f docker-compose-metrics-author.yml up
 #esto crea una red con el mismo gateway, consul etc. el d como q es opcional
 # el d permite ejecutarlo en segundo plano
```

### docker hub

docker build -t alulest/app-authors .
docker push alulest/app-authors


docker build -t alulest/app-books .
docker push alulest/app-books
