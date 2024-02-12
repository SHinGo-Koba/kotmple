# kotmple
Kotlin sample application

# How to run kotmple locally

Here is for using kubernetes and minikube(docker driver).

1. Start minikube
```bash
# You can install minikube from https://minikube.sigs.k8s.io/docs/start/
minikube start
```

2. Change kubernetes context to 'minikube'
```bash
# Install plugin "kubectx" to manage kubernetes context
# See https://github.com/ahmetb/kubectx
kubectl ctx minikube
```

3. Load docker env into minikube
```bash
eval $(minikube docker-env)
```

4. Build docker image
```bash
# At kotpmle project's root
docker build -t kotmple:1.0.0 .
```

5. Create namespace
```bash
kubectl create -f k8s/namespace.yaml
```

6. Run kubernetes commands to deploy the application
```bash
kubectl create -f k8s/deployment.yaml
kubectl create -f k8s/service.yaml
```

7. Get local url to access kotmple
```bash
minikube service kotmple -n kotmple --url
```
