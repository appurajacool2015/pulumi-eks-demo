#!bash
mvn clean install -DskipTests=true
docker build -t appurajacool2015/ekspulumispringbootpostgresrestapidemo .
# we can docker login as a initial step
#docker login
docker push appurajacool2015/ekspulumispringbootpostgresrestapidemo
kubectl apply -f postgres-secrets.yaml
kubectl apply -f postgres-storage.yaml
kubectl apply -f postgres-deployment.yaml
kubectl apply -f postgres-service.yaml
kubectl delete config map hostname-config
kubectl create configmap hostname-config --from-literal=postgres_host=$(kubectl get svc postgres -o jsonpath="{.spec.clusterIP}")
kubectl apply -f springboot-deployment.yaml
kubectl apply -f springboot-service.yaml
