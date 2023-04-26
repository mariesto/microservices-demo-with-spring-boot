# Demo - Miscroservices with Spring Boot

Create demo project to build microservices using spring boot multi-module.
Develop 2 services (`book-service` and `rating-service`) which have in-memory database(H2) and communicate through HTTP (using RestTemplate). 

`service-registry` act as service discovery by using Eureka Server which have default dashboard.

`gateway-service` act as API Gateway which route the request accordingly based on defined config.

`book-service`, `rating-service` and `gateway-service` will use Eureka Client and register itself to Eureka Server.

![](/Users/amendomariestositinjak/Desktop/Screenshot 2023-04-26 at 22.38.41.png)

### Technology

|         Name         |    Version     | Description                      |
|:--------------------:|:--------------:|----------------------------------|
|     Spring Boot      |     2.7.11     | Framework to build microservices |
|         Java         |       11       | Language                         |
| Spring Cloud Gateway |    2021.0.6    | API Gateway                      |
|        Eureka        |    1.10.17     | Service Discovery                |
|   Netflix Hystrix    | 2.2.10.RELEASE | Fault Tolerance (fallback)       |
|          H2          |    2.1.214     | In-memory Database               |
|        Docker        |    20.10.21    | Containerization                 |


### How to Use
- Go to the parent module and edit the `run.sh` file by updating `MODULES_DIR` variable according to your local path after you clone this repo.
- Then, make sure this script executable with `chmod +x run.sh` 
- Run script ```./run.sh``` to trigger the docker-compose
- Go to http://localhost:8761/eureka to verify all the services already registered to service discovery
- Run below curl to tes the application
```bash 
curl --location --request POST 'http://localhost:8881/api/v1/books/ISBN-123/5'
```
- Go to H2 console of `rating-service` to verify the result


Other options, you can use the docker directly by pulling the images from below registry :
https://hub.docker.com/repositories/mariesto

> **Note**
> The docker image will be same with the module name in this repo. 

