# springboot-kuberneter-mongo-kafka
<h1 align="center">
  <br>
  <a><img src="images/springbootpluskubernetes.png" width="900" height="250" a>
  <br>
 Application is Hunza-Consulting Assignment with two Tier Springboot<b>(Production Ready)</b> application with Mongo and Kafka deploy on Kubernetes ecosystem.
  <br>
</h1>
  
  
## Getting Started  
Here you will find a simple spring boot application with a simple model and we will be covering implementation and deployment using Kurbernetes.
  
  Here in this application you will be introduced to list below :
* A simple spring boot application with a basic caterer model structure performing a Read, Update operation. The example is available in the branch master.
* An introduction to Kubernetes with spring boot project, that shows its the most interesting features like Spring Boot, Mongo property sources based on ConfigMap. 
* Using Spring Boot to monitor Spring Boot applications running on Kubernetes using external endpoint exposed by Node Port.
  
  ### Prerequisite
Installed: [Docker](https://www.docker.com/), [Java 11](https://www.oracle.com/technetwork/java/javase/overview/index.html), [Maven 3.x](https://maven.apache.org/install.html), [Git](https://www.digitalocean.com/community/tutorials/how-to-contribute-to-open-source-getting-started-with-git)\
For Kubernetes you can use simple [Docker for Desktop](https://www.docker.com/products/docker-desktop) in local machine , here you can enable Kubernetes service.
  
  ### Usage
* Clone [springboot-kuberneter-mongo-kafka](https://github.com/exceptionalcode/springboot-kuberneter-mongo-kafka.git)
* Download and Start Docker and Kubernetes services
* Build Maven project with using command: mvn clean install
* Build Docker images for each module using command, for example: docker build -t ishaansolanki6/caterer-service:0.0.1 .
  
## Architecture
Our caterer-service based application system consists of the following components:

* **caterer-service** - A springboot app that allows to perform Read and Update operation on Mongo Repository to Database.
* **catererdb-service** - It is a mongo server hosted inside the pod that only allows to connect springbooot application for Read/Update operations.
* **ConfigMap** - A ConfigMap is an API object used to store non-confidential data in key-value pairs such as mongo host and database info.
* **Node Port** - It is a service responsible to route traffic or access your application from external world.
* **my-cluster-kafka-bootstrap** - Kafka is hosted on kubernetes using Strimzi package. Strimzi simplifies the process of running Apache Kafka in a Kubernetes cluster. Strimzi provides container images and Operators for running Kafka on Kubernetes.
  
 ## Deploy and Run
 
 * Go to /k8s-scrpits/mongo-k8s folder and apply all the files using ccommand : $ kubectl apply -f <filename>.yaml
 * To check if the manifest resources are created : $ kubectl get pods , $ kubectl get svc , $ kubectl get deploy.
 * Do same with /k8s-scrpits/springboot-k8s/ folder apply all the files using above command.  
 * To start kafka on Kubernetes you can check Strimzi [Quick Start Document](https://strimzi.io/quickstarts/)
  
 **To check application is running**\
 Perform Read Update and Delete Cache opertation using below commands:\
 To Create:
```
curl --location --request POST 'localhost:30081/api/caterer' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "ishaan_caterer",
    "location": {
        "cityName": "Indore",
        "streetNameNumber": "street101",
        "postalCode": "452011"
    },
    "capacity": {
        "minGuest": 10,
        "maxGuest": 50
    },
    "contactInfo": {
        "phoneNumber": "092848712",
        "mobileNumber": "+91-7869212144",
        "emailAddress": "ishaan.solanki@someweb.com"
    }
}'
```
To Get All caterer:
 ```
  curl --location --request GET 'localhost:30081/api/caterer/all'
 ```
To Get Caterer by Name
```
  curl --location --request GET 'localhost:30081/api/caterer/getDetailsByName/ishaan_caterer'
```
To Get Caterer by Id
```
  curl --location --request GET 'localhost:30081/api/caterer/getDetailsById/1'
```  
To Delete All the entries in Cache
```
  curl --location --request POST 'localhost:30081/api/caterer/evictCache'
```
  
  
