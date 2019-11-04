FROM openjdk:11-jre-slim

COPY /target/purple-1.0.jar /usr/local/purple/purple.jar

COPY operations /usr/local/purple

COPY entrypoint.sh /usr/local/purple

WORKDIR /usr/local/purple

ENTRYPOINT ["bash","entrypoint.sh"]
