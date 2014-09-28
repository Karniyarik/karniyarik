#!/bin/bash
set +e
for i in 2 3 4 5 7
do
./deployadmin.sh $i
done
set -e
