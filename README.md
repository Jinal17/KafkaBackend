This Spring Boot based REST application publishes student survey messages to Kafka cluster hosted on AWS. In addition, these surveys are displayed by consuming messages from the same cluster. Here, Kafka acts as persistence layer. This application interacts with Kafka using their libraries.
This backend application is containerized using Docker and is deployed to Kubernetes cluster hosted on AWS using Jenkins.

### Demo link: [here](https://drive.google.com/drive/folders/1hJn0FQd-iG94MyEfQr_lKfLLrp2utjES?usp=sharing)
#### DOCKER
Dockerfile contains : Dependencies & Web application related docker definitions
DockerHub: [here](https://hub.docker.com/repository/docker/jinal0217/hw4_rest_kafka)

#### KUBERNETES
Deployment & Service Files Contains: container, kubernetes related deployment and execution definitions

#### Git: URL : [here](https://github.com/Jinal17/KafkaBackend)


## Steps for Triggering the Build through Jenkins:

- **First Time User** Clone this Repo using: git clone https://github.com/Jinal17/KafkaBackend
- Make necessary changes in java files for the Web service on your local system
- Modify the **DOCKER_TAG** version in Jenkinsfile. For example: Modify the **DOCKER_TAG** = ‘V8’ : to V9 (any version)
- $git status
- $git add -–all
- $git commit -m "Pushing Final Changes"
- $git push
- Once the above steps are executed successfully, the Jenkins build will get triggered and the web application with the new changes will get deployed on kubernetes cluster.
