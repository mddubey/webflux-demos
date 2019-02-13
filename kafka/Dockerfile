FROM ubuntu:16.04
RUN apt-get update && apt-get install wget openjdk-8-jre -y
RUN wget http://mirrors.estointernet.in/apache/kafka/2.1.0/kafka_2.12-2.1.0.tgz &&\
     tar -xzvf kafka_2.12-2.1.0.tgz && mv kafka_2.12-2.1.0 kafka