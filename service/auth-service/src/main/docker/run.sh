#!/bin/sh
echo "********************************************************"
echo "Starting @project.package.name@ "
echo "********************************************************"
java -jar -Dspring.profiles.active=production /usr/local/@project.docker.hub-id@/@project.package.name@
