#!/bin/bash

set -e

MY_FILE=./plasma.csv

echo "# left arm: `grep LEFT $MY_FILE | wc -l`" 
echo "# right arm: `grep RIGHT $MY_FILE | wc -l`" 
echo ""
echo "# Two-Draw McGraw: `grep ",\\"2\\"," $MY_FILE | wc -l`"
echo ""
echo "# 2023: `grep 2023 $MY_FILE | wc -l`"
echo "# 2022: `grep 2022 $MY_FILE | wc -l`"
echo "# 2021: `grep 2021 $MY_FILE | wc -l`"
echo "# 2020: `grep 2020 $MY_FILE | wc -l`"
echo "# 2019: `grep 2019 $MY_FILE | wc -l`"
echo "# total: `grep -v "date.*draws" $MY_FILE | wc -l`" 

