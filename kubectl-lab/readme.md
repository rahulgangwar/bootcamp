### K8S Commands

### Pod
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


### Cluster
```bash
# Create cluster
kind create cluster --name k8s-lab  

# Show clusters
kind get clusters
```

### Replication Controller
```bash
# Get replication controllers
kubectl get rc
```

### ReplicaSet
```bash
# Get replicaset
kubectl get replicaset

# Scale replicaset
kubectl scale --replicas=6 <type> <name>
kubectl scale --replicas=6 replicaset myapp-replicaset

# Edit replicaset config
kubectl edit replicaset myapp-replicaset
```

### Deployment
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

# View the Deployment's YAML
kubectl get deployment myapp-deployment -o yaml

# View rollout status
kubectl rollout status deployment myapp-deployment

# View rollout history
kubectl rollout history deployment myapp-deployment

# Undo rollout
kubectl rollout undo deployment myapp-deployment

# Update image
kubectl set image deployment/myapp-deployment <container-name>=nginx:1.28
```

### YML files
```bash
# Create or update resources from a YAML file
kubectl apply -f pod.yaml

# Delete resources defined in the file
kubectl delete -f pod.yaml

# Create resources from the file (fails if they already exist)
kubectl create -f pod.yaml

# Display the resources identified by the file
kubectl get -f pod.yaml

# Apply YAML files from a directory
kubectl apply -f ./k8s/

# Scale the replica set
kubectl scale --replicas=6 -f replicaset-definition.yml
```

### Other helpful cmds
```bash
# Port forwarding
# Forwards the requests from localhost:8080 to nodePort port: 80
kubectl port-forward service/myapp-node-port 8080:80
```