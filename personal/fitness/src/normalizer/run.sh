#!/bin/bash 

set -e 

echo "building..."
./gradlew clean installDist

echo "running..."
./staging/bin/normalizer data.csv > out.csv

echo "Ready."
