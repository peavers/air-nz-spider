#!/bin/zsh

./gradlew spotlessApply
./gradlew clean build

docker build . -t peavers/air-nz-spider
docker push peavers/air-nz-spider