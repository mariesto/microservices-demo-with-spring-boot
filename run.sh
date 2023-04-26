#!/bin/bash

MODULES_DIR="/Users/amendomariestositinjak/Downloads/bookratingmicroservice"

MODULES=( "book-service" "rating-service" "gateway-service" "service-registry" )

for MODULE in "${MODULES[@]}"
do
    cd "${MODULES_DIR}/${MODULE}"
    mvn clean package
    docker build -t "${MODULE}" .
done

# shellcheck disable=SC2164
cd /Users/amendomariestositinjak/Downloads/bookratingmicroservice
docker-compose -f docker-compose.yml up -d