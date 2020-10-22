# My Sportfolio

My Sportfolio backend. Microservice to store the player matches and teams.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

- Java 11
- MongoDB

### Installing

It needs MongoDB running. A docker-compose file is provided with a mongodb image

```
> docker-compose up
```

## Running the tests

There are two type of tests in this app.

### Unit tests

Test a single class

```
> ./gradlew clean test
```

### Integration tests

Runs the spring boot application and test some functionality

```
> ./gradlew clean integrationTest
```

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/manuelarte/mysportfolio/tags). 

## Authors

* **Manuel Doncel Martos**

See also the list of [contributors](https://github.com/manuelarte/mysportfolio/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Deploy in minikube

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