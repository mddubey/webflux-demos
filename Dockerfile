FROM openjdk:8
COPY build/libs/webflux-demos-1.0-SNAPSHOT.jar /opt/app.jar
ENTRYPOINT java -jar /opt/app.jar
#ENTRYPOINT tail -f /dev/null