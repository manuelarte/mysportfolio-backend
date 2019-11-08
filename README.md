# Introduction

TODO

# Deploy in minikube

- 0 Build the image
> docker build -t mysportfolio:${version} .

- 1 create cluster
    
    -- 1.a Using deployment.yaml -> preferred option
    > kubectl apply -f ./deployment.yaml
    
    -- 1.b run the image manually
    > kubectl run mysportfolio --image=mysportfolio:0.1.0 --port=8080
    
    > kubectl expose deployment hello-universe --type="LoadBalancer"

To get the ip in minikube 
    
    > minikube service mysportfolio --url

To delete deployment
    
    > kubectl delete deployment mysportfolio