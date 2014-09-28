#!/bin/bash
set +e
#/opt/tomcat/admin/bin/shutdown.sh
#sleep 5
rm -rf /opt/karniyarik
rm -rf /opt/tomcat/admin/webapps/karniyarik-jobscheduler
rm -rf /opt/tomcat/admin/webapps/karniyarik-jobscheduler.war
rm -rf /opt/tomcat/admin/webapps/karniyarik-joexecutor
rm -rf /opt/tomcat/admin/webapps/karniyarik-jobexecutor.war
rm -rf /opt/tomcat/admin/work/Catalina/localhost/*
rm -rf /opt/tomcat/admin/logs/*
cp /home/sshadmin/karniyarik/deploy/karniyarik-jobscheduler.war /opt/tomcat/admin/webapps/
cp /home/sshadmin/karniyarik/deploy/karniyarik-jobexecutor.war /opt/tomcat/admin/webapps/
#/opt/tomcat/admin/bin/startup.sh
set -e
