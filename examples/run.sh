#!/bin/bash
{
    docker-compose up -d &&
    mvn clean verify -f simple-springboot-core/pom.xml &&
    mvn clean verify -f simple-springboot-activemq/pom.xml &&
    mvn clean verify -f simple-springboot-datasource/pom.xml &&
    mvn clean verify -f simple-springboot-mockserver/pom.xml &&
    rm -rf simple-springboot-owaspzap/src/test/resources/session/*session* &&
    mvn clean verify -f simple-springboot-owaspzap/pom.xml -Deasycucumber.target.url="http://$(ip -4 addr show scope global dev docker0 | grep inet | awk '{print $2}' | cut -d / -f 1):9000" &&
    mvn clean verify -f simple-springboot-stubby/pom.xml &&
    mvn clean verify -f complete-springboot/pom.xml
} || {
    exit 1
}