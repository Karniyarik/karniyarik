#!/usr/bin/expect
set serverid [lrange $argv 0 0]
spawn ssh sshadmin@77.92.136.$serverid "sudo /home/sshadmin/karniyarik/deploy.sh"
expect "password"
send "18krnyrk03\n"
