#!/bin/bash

set -e 

cat 2006_fitness.csv 2007_fitness.csv 2008_fitness.csv \
    2009_fitness.csv 2010_fitness.csv 2011_fitness.csv \
    2012_fitness.csv 2013_fitness.csv 2014_fitness.csv \
    2015_fitness.csv 2016_fitness.csv 2017_fitness.csv \
    2018_fitness.csv 2019_fitness.csv 2020_fitness.csv  > all_fitness.csv 
