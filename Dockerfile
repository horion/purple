FROM openjdk:11-jre-slim

COPY /target/purple-1.0.jar /usr/local/purple/purple.jar

COPY entrypoint.sh /usr/local/purple/entrypoint.sh

WORKDIR /usr/local/purple

ENTRYPOINT ["bash","entrypoint.sh"]
