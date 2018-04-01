# akka-cluster

Este proyecto es una prueba de concepto de cómo hacer un cluster con datos distribuidos que comparten todos sus nodos usando akka.

Para ejecutarlo:

* mvn clean package
* java -jar ./target/akka-cluster-1.0-SNAPSHOT.jar --server.port=8090  --cluster.port=2552
* java -jar ./target/akka-cluster-1.0-SNAPSHOT.jar --server.port=8091  --cluster.port=2552
* java -jar ./target/akka-cluster-1.0-SNAPSHOT.jar --server.port=8080 --cluster.port=0

Una vez que el cluster est levantado



* curl -X POST http://localhost:{port}/api/connections. Añade nuevas conexiones al cluster.
* curl -X DELETE http://localhost:{port}/api/connections. Elimina nuevas conexiones al cluster.
* curl -i http://localhost:{port}/api/connections. Devuelve el estado de conexiones del cluster.

```
{"nodes":[
  {"id":"d90f2d05-a95e-4494-acc1-83330cfbb515",
   "numberOfConnections":1},
  {"id":"863c194b-3191-4fa0-ab7e-a0d1d6fe65b0",
  "numberOfConnections":1}
  ]
}
```
