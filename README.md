Webflux-Kafka Demo
==================

## Prerequsites
- docker
- gradle

## Setup
- Clone the repo
- Build the jar

    `./gradlew clean bootjar`
- Start the docker environment. WIll take some time for the first time. This will setup 2 node kafka cluster with zookeeper and start the application. 
    
    `docker-compose up -d`
- A quick check on how many containers are running 

`docker ps -a`

```
CONTAINER ID        IMAGE                     COMMAND                  CREATED             STATUS              PORTS                    NAMES
b1676d731bfb        webflux-demos_app         "/bin/sh -c 'java -j…"   8 minutes ago       Up 8 minutes        0.0.0.0:8083->8080/tcp   app
9f639413a908        webflux-demos_kafka2      "/kafka/bin/kafka-se…"   23 minutes ago      Up 16 minutes                                kafka2
1bf99ce01bc0        webflux-demos_kafka1      "/kafka/bin/kafka-se…"   23 minutes ago      Up 16 minutes                                kafka1
ab9644187d03        webflux-demos_zookeeper   "/kafka/bin/zookeepe…"   23 minutes ago      Up 16 minutes                                zookeeper
```

- There are two endpoints exposed right now:
    
    - Open terminal; run `curl http://localhost:8083/messages/all`; This will open a stream from kafka to your terminal for available messages in Kafka. Don't close it since it is a stream and it will keep showing whenever new data comes to kafka.   
    - Open another terminal; run `curl -X POST http://localhost:8083/messages/add/{message}`. It will add the given message to kafka queue. As soon as the new message gets added, it will be reflected on the above endpoint.
    
    
## Troubleshooting 
Run `docker-compose stop;docker-compose rm -f` to remove all the containers if startup fails with `Node already exists`. Run `docker-compose up -d` after that to bring up the containers.