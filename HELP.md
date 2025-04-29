# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.5/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.5/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.4.5/reference/web/servlet.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

### Run without Docker 
./mvnw clean install
./mvnw spring-boot:run

### Run with Docker
docker build --platform linux/amd64 -t spring-boot-recipes .
docker run -p 8080:8080 -t spring-boot-recipes:latest

### Run with Docker Compose
docker compose up

# Stop and delete docker containers, images and volumes 
docker stop $(docker ps -aq)
docker rm $(docker ps -aq)
docker rmi $(docker images -q)
docker volume prune
docker volume rm $(docker volume ls -q)
docker network prune
docker system prune -a --volumes
docker compose down -v
sudo rm -rf ./data/postgres/data/*
sudo rm -rf data/

### Example of post request
curl -X POST http://localhost:8080/meals \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Meal name",
    "image": "",
    "price": 0.0
  }'
