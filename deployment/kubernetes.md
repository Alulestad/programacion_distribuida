**## Kind
. Kind is a tool for running local Kubernetes clusters using Docker container “nodes”. 

se usa el octand en este caso.

```shell
#kind create cluster --name kind
kind create cluster
```



.Minikube is a tool that makes it easy to run Kubernetes locally. 
Minikube runs a single-node Kubernetes cluster inside a VM on your laptop for users 
looking to try out Kubernetes or develop with it day-to-day.

```shell

minikube start
```

## Port Forwarding

```shell

kubectl port-forward --namespace default svc/consul 8500:8500
kubectl port-forward --namespace default svc/app-books-service 8080:8080
```

