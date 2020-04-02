FROM openjdk:8-jdk-alpine
ARG HUB_ID
ARG JAR_FILE
ENV JAVA_OPTS "-Xms512m -Xmx2048m -XX:ReservedCodeCacheSize=256m -XX:OnOutOfMemoryError=\"shutdown -r\""
RUN apk update && apk upgrade && apk add netcat-openbsd
RUN mkdir -p /usr/local/${HUB_ID}
ADD ${JAR_FILE} /usr/local/${HUB_ID}/
ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh