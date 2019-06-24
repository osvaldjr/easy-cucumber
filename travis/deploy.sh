#! /bin/sh
openssl aes-256-cbc -K $encrypted_a1fc9919b14a_key -iv $encrypted_a1fc9919b14a_iv -in ./secrets.tar.enc -out ./secrets.tar -d && tar xvf ./secrets.tar
mvn deploy -B --settings ./settings.xml -DperformRelease=true
