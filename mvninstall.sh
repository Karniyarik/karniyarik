#!/bin/bash
mvn -Denv=exec -Dmaven.test.skip=true install
cd ../enterprise/karniyarik-enteprise/
. /etc/profile
grails prod war
cd ../../code
