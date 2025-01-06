### Ejecuciones
java -jar -Dquarkus.http.port=9090 quarkus-run.jar

### Consul
```shell
consul agent -dev
```
http://localhost:8500/ui/dc1/services

### Gateway
Traefick: https://traefik.io/
descargar: https://github.com/traefik/traefik/releases (version 3.3.0)


### Ejecutar

```shell
traefik version
traefik -api.insecure=true
```

### Acceder a UI:
- http://localhost:7070/
- http://localhost:7070/dashboard/
