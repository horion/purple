FROM maven:3.6.2-jdk-11 as maven

COPY . purple/

WORKDIR purple

#RUN mvn dependency:go-offline package -B

RUN mvn clean install

FROM openjdk:11-jre-slim

COPY --from=maven /purple/target/purple-1.0.jar /usr/local/purple/purple.jar

COPY entrypoint.sh /usr/local/purple/entrypoint.sh

WORKDIR /usr/local/purple

ENTRYPOINT ["bash","entrypoint.sh"]
