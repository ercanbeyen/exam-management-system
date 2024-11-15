# Exam Management System
---

## Spring Boot Application
---

### Summary
It is an exam management system that covers exam procedures.<br/>
There are 9 applications (4 services and 5 servers) in this project.

Services:
- Exam: 8081
- Candidate: 8082 
- School: 8083
- Notification: 8084

Server:
- Admin Server: 8508
- Config Server: 8888
- Eureka Server: 8761
- Zipkin Server: 9411
- Gateway Server: 8080

### Requirements
- Exam registration should include school, classroom, date, time and candidate information.

### Tech Stack
---
- Java 21
- Spring Boot
- Spring Cloud
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

1) Config Server
2) Zipkin Server
3) Admin Server
4) Eureka Server
5) Gateway Server
6) School Service
7) Candidate Service
8) Exam Service
9) Notification Service

### Monitor
---
- Use Admin Server to monitor health of services.
- Use Zipkin Server to trace the endpoints.
