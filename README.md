# Exam Management System
---

## Spring Boot Application
---

### Summary
It is an exam management system that covers exam procedures.<br/>
There are 10 applications (5 services and 5 servers) in this project.

Services:
- Auth: 9898
- Exam: 8081
- Candidate: 8082 
- School: 8083
- Notification: 8084

Servers:
- Admin: 8508
- Config: 8888
- Eureka: 8761
- Zipkin: 9411
- Gateway: 8080

### Requirements
- User must log in to use the application.
- Exam registration should include school, classroom, date, time and candidate information.

### Tech Stack
---
- Java 21
- Spring Boot
- Spring Cloud
- Spring Security
- Spring Data JPA
- Spring Data MongoDB
- MySQL
- MongoDB
- Kafka
- Docker

### Prerequisites
---
- Maven
- ZooKeeper
- Kafka
- Docker

### Build & Run
---
In order to pull images from Dockerhub, you should run the below command
```
$ docker pull openzipkin/zipkin
```

Then you should run the following applications in order

1) Eureka Server
2) Zipkin Server
3) Config Server
4) Admin Server
5) Gateway Server
6) Auth Service
7) School Service
8) Candidate Service
9) Notification Service
10) Exam Service

### Monitor
---
- Use Admin Server to monitor health of services.
- Use Zipkin Server to trace requests.

### Api Documentation
---
You may use Swagger-UI with the port of the application you configured to access the project's api documentation.<br/>
You should use the below url to access the Swagger-UI.<br/>
`http://localhost:${PORT}/swagger-ui.html`
