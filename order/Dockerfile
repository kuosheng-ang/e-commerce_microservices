Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

 MAINTAINER instruction is deprecated in favor of using label
 MAINTAINER kuoshengclement.net
Information around who maintains the image
LABEL "org.opencontainers.image.authors"="kuoshengclement.net"

 Add the application's jar to the image
COPY target/order-0.1.1.jar order-0.1.1.jar

 execute the application
ENTRYPOINT ["java", "-jar", "order-0.1.1.jar"]