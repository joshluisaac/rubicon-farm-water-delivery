FROM adoptopenjdk:11-jre-hotspot
ADD target/farm-water-delivery-0.0.1-SNAPSHOT.jar farm-water-delivery-0.0.1-SNAPSHOT.jar
COPY data /data
EXPOSE 8887
ENTRYPOINT ["java", "-jar", "farm-water-delivery-0.0.1-SNAPSHOT.jar"]