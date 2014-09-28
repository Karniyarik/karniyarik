#!/bin/bash
for i in 6
do
scp karniyarik-web/target/karniyarik-web.war sshadmin@77.92.136.$i:/home/sshadmin/karniyarik/deploy
scp karniyarik-common/target/classes/deploy/db/krnyrk.sql sshadmin@77.92.136.$i:/home/sshadmin/karniyarik/deploy
scp karniyarik-common/target/classes/deploy/db/quartz.sql sshadmin@77.92.136.$i:/home/sshadmin/karniyarik/deploy
scp ../enterprise/karniyarik-enteprise/target/karniyarik-enterprise-0.1.war sshadmin@77.92.136.$i:/home/sshadmin/karniyarik/deploy
done
