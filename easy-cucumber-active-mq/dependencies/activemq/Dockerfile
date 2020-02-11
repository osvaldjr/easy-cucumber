FROM java:8

ENV ACTIVEMQ_VERSION 5.13.3
ENV ACTIVEMQ apache-activemq-$ACTIVEMQ_VERSION
ENV ACTIVEMQ_TCP=61616 ACTIVEMQ_AMQP=5672 ACTIVEMQ_STOMP=61613 ACTIVEMQ_MQTT=1883 ACTIVEMQ_WS=61614 ACTIVEMQ_UI=8161 ACTIVEMQ_JMX=1099
ENV ACTIVEMQ_HOME /opt/activemq
ENV ACTIVEMQ_RMI_SERVER_HOSTNAME localhost

RUN echo "deb [check-valid-until=no] http://archive.debian.org/debian jessie-backports main" > /etc/apt/sources.list.d/jessie-backports.list
RUN sed -i '/deb http:\/\/deb.debian.org\/debian jessie-updates main/d' /etc/apt/sources.list
RUN apt-get -o Acquire::Check-Valid-Until=false update
RUN apt-get install -y netcat

RUN \
    curl -O http://archive.apache.org/dist/activemq/$ACTIVEMQ_VERSION/$ACTIVEMQ-bin.tar.gz && \
    mkdir -p /opt && \
    tar xf $ACTIVEMQ-bin.tar.gz -C /opt/ && \
    rm $ACTIVEMQ-bin.tar.gz && \
    ln -s /opt/$ACTIVEMQ $ACTIVEMQ_HOME && \
    useradd -r -M -d $ACTIVEMQ_HOME activemq && \
    chown activemq:activemq /opt/$ACTIVEMQ -R

RUN sed -i 's/<managementContext createConnector=\"false\"\/>/<managementContext connectorPort=\"1099\"\/>/g' /opt/apache-activemq-5.13.3/conf/activemq.xml && \
    chmod 600 /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/management/jmxremote.password && \
    echo '\nACTIVEMQ_OPTS="$ACTIVEMQ_OPTS -Djava.rmi.server.hostname=$ACTIVEMQ_RMI_SERVER_HOSTNAME -Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote.rmi.port=1099 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false "' >> /opt/apache-activemq-5.13.3/bin/env

WORKDIR /tmp
ADD activemq-cli.cmd .
RUN \
    wget --no-check-certificate https://github.com/antonwierenga/activemq-cli/releases/download/v0.6.0/activemq-cli-0.6.0.zip && \
    unzip activemq-cli-0.6.0.zip

ADD start.sh /bin/start.sh
RUN chmod 755 /bin/start.sh

USER activemq
WORKDIR $ACTIVEMQ_HOME
EXPOSE $ACTIVEMQ_TCP $ACTIVEMQ_AMQP $ACTIVEMQ_STOMP $ACTIVEMQ_MQTT $ACTIVEMQ_WS $ACTIVEMQ_UI $ACTIVEMQ_JMX
CMD ["/bin/bash", "-c", "/bin/start.sh"]