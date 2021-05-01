# SWE 645 : Component based Software development

### Name : Angeela Acharya, Jinalben Shah, Kripa Pokheral, Sujana Khakural

### Homework 4:
### WebApp Home page: [here](http://swe645-jinal.s3-website-us-east-1.amazonaws.com/)
### WebApp Survey page: [here](http://a93a1d16bb4504a03ada735ff2bc4813-400275033.us-east-1.elb.amazonaws.com:53812)

### Demo link: [here](https://drive.google.com/drive/folders/1hJn0FQd-iG94MyEfQr_lKfLLrp2utjES?usp=sharing)
#### DOCKER
Dockerfile contains : Dependencies & Web application related docker definitions
DockerHub: [here](https://hub.docker.com/repository/docker/jinal0217/hw4_rest_kafka)

#### KUBERNETES
Deployment & Service Files Contains: container, kubernetes related deployment and execution definitions

**Cluster Name**: swe645cluster

#### Rancher: URL: [here](https://ec2-18-204-37-62.compute-1.amazonaws.com/login)

#### Jenkins:URL: [here](http://18.204.37.62:8080/job/swe645_restKafka/)

#### Git: URL : [here](https://github.com/Jinal17/swe645_restKafka)


## Steps for Triggering the Build through Jenkins:

- **First Time User** Clone this Repo using: git clone https://github.com/Jinal17/swe645_restKafka
- Make necessary changes in java files for the Web service on your local system
- Modify the **DOCKER_TAG** version in Jenkinsfile. For example: Modify the **DOCKER_TAG** = ‘V8’ : to V9 (any version)
- $git status
- $git add -–all
- $git commit -m "Pushing Final Changes"
- $git push
- Once the above steps are executed successfully, the Jenkins build will get triggered and the web application with the new changes will get deployed on kubernetes cluster.
- Verify the changes on URL: [here](http://a8727104ed448441ca45544dbdcab647-250204293.us-east-1.elb.amazonaws.com:8081/students)