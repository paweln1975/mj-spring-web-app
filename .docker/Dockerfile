FROM adoptopenjdk:11-jre-hotspot
VOLUME /tmp
ADD maven/mj-spring-web-0.0.4-SNAPSHOT.jar myapp.jar
RUN sh -c 'touch myapp.jar'
ENTRYPOINT ["java", "-jar", "/myapp.jar"]