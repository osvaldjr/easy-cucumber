#! /bin/sh
openssl aes-256-cbc -K $encrypted_a1fc9919b14a_key -iv $encrypted_a1fc9919b14a_iv -in $TRAVIS_BUILD_DIR/travis/secrets.tar.enc -out $TRAVIS_BUILD_DIR/travis/secrets.tar -d && tar xvf $TRAVIS_BUILD_DIR/travis/secrets.tar -C $TRAVIS_BUILD_DIR/travis/
mvn deploy -B --settings $TRAVIS_BUILD_DIR/travis/settings.xml -DperformRelease=true -DskipTests



 