FROM adoptopenjdk:11-jre-hotspot
VOLUME /tmp
RUN mkdir -p /app
COPY maven/ /app
ENTRYPOINT ["/app/bin/startup.sh"]