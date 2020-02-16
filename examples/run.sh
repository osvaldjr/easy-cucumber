#!/bin/bash
{
    docker-compose up -d
    mvn clean verify -f simple-springboot-core/pom.xml &&
    mvn clean verify -f simple-springboot-activemq/pom.xml &&
    mvn clean verify -f simple-springboot-datasource/pom.xml &&
    mvn clean verify -f simple-springboot-mockserver/pom.xml &&
    mvn clean verify -f simple-springboot-owaspzap/pom.xml &&
    mvn clean verify -f simple-springboot-stubby/pom.xml
} || {
    exit 1
}