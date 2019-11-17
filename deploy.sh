#!/bin/bash

set -e

cd /project/source-code/recycle-treasure

git fetch --all -q && git reset --hard origin/master -q && git checkout -f -q && git checkout master -q && git pull
echo 'git pull master finished !'

mvn clean install -Pprod
echo 'mvn clean install -Pprod finished !'

ps -ef | grep recycle-treasure | awk '{print $2}' | xargs kill -9
echo 'killing recycle-treasure process !'

echo 'sleep 5 seconds to waiting recycle-treasure process destroyed ...'
sleep 5s

nohup java -Xmx2G -XX:MaxDirectMemorySize=64M -jar /project/source-code/recycle-treasure/target/recycle-treasure-api.jar > /tmp/recycle-treasure-api.log &
echo 'recycle-treasure-api process started !'
