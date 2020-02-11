#/bin/sh
nohup bash -c 'while ! nc -z localhost $ACTIVEMQ_TCP; do sleep 0.5; done; /tmp/activemq-cli-0.6.0/bin/activemq-cli --cmdfile /tmp/activemq-cli.cmd' &
bin/activemq console