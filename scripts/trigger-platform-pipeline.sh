#!/bin/bash

username=$1
password=$2

version=$(./gradlew currentVersion -q -Prelease.quiet)
body='{"ref": "master", "inputs": { "serviceName": "idv-one-time-passcode", "serviceVersion": "VERSION" } }'
body=$(echo "${body//VERSION/$version}")

curl -XPOST -u "${username}:${password}" \
  -H "Accept: application/vnd.github.everest-preview+json" \
  -H "Content-Type: application/json" \
  "https://api.github.com/repos/michaelruocco/idv-platform/actions/workflows/update-service-pipeline.yml/dispatches" \
   --data "$body"