#!/bin/bash
for i in 2 3 4 5 7
do
echo "Copying to $i"
scp karniyarik-jobexecutor/target/karniyarik-jobexecutor.war  sshadmin@77.92.136.$i:/home/sshadmin/karniyarik/deploy
scp karniyarik-common/target/classes/deploy/db/krnyrk.sql sshadmin@77.92.136.$i:/home/sshadmin/karniyarik/deploy
scp karniyarik-common/target/classes/deploy/db/quartz.sql sshadmin@77.92.136.$i:/home/sshadmin/karniyarik/deploy
scp karniyarik-common/target/classes/deploy/db/drop.sql sshadmin@77.92.136.$i:/home/sshadmin/karniyarik/deploy
done
scp karniyarik-jobscheduler/target/karniyarik-jobscheduler.war  sshadmin@77.92.136.2:/home/sshadmin/karniyarik/deploy
