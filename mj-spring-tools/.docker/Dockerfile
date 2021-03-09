FROM adoptopenjdk:11-jre-hotspot
VOLUME /tmp
RUN mkdir -p /app
ADD maven/mj-spring-web-app-0.0.4-SNAPSHOT.tar.gz /app/
ENTRYPOINT ["/app/bin/startup.sh"]