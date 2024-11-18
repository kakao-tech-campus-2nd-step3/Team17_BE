#!/bin/bash
# Gradle build
cd /home/ubuntu/Team17_BE || exit
./gradlew bootJar

BUILD_PATH=$(ls /home/ubuntu/Team17_BE/build/libs/*.jar)
JAR_NAME=$(basename $BUILD_PATH)

CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  sleep 1
else
  kill -15 $CURRENT_PID
  sleep 5
fi

DEPLOY_PATH=/home/ubuntu/deploy/
cp $BUILD_PATH $DEPLOY_PATH
cd $DEPLOY_PATH

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
nohup java -jar -Dspring.profiles.active=prod $DEPLOY_JAR > /home/ubuntu/deploy/deploy.log 2>&1 &