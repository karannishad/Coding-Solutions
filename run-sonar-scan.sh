#!/bin/sh
./gradlew sonarqube \
  -Dsonar.projectKey=coding-solutions \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=19ec086901f235a8a9bcdf910a6f090512445347