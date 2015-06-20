#!/usr/bin/env bash

#Timeout needed for service to startup
sleep 10

response=$(curl --write-out %{http_code} --silent --output /dev/null localhost:8080/v1/healthcheck)
if [ ${response} -eq '200' ]; then
    exit 0
else
    exit 1
fi
