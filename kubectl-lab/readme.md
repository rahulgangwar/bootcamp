
### Pod Cmds

```bash
# Create a pod
kubectl run nginx --image=nginx

# Get pods
kubectl get pods 
kubectl get pods -o wide

# Describe pod
kubectl describe pod nginx

# Delete pod
kubectl delete pod <pod-name>
```


### Cluster Cmds

```bash
# Create cluster
kind create cluster --name k8s-lab  

# Show clusters
kind get clusters
```

### Deployment Cmds

```bash
# Deploy an application
kubectl create deployment nginx --image=nginx

# Inspect deployments
kubectl get deployments
kubectl describe deployment nginx
kubectl get replicasets

# Increase replicas to 3
kubectl scale deployment nginx-demo --replicas=3

# Delete deployment
kubectl delete deployment nginx-demo
```

### Services
```bash

```

### Using YML files

```bash
kubectl apply -f pod.yml
```


