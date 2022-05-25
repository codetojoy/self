#!/bin/bash 

gradle clean installDist
./staging/bin/reducer ../../books.v0.csv > out.csv 

echo "Ready."
