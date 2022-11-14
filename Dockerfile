FROM eclipse-temurin:17-jre-alpine
ENV PORT 8080
ENV CLASSPATH /opt/lib
EXPOSE 8080

ARG JAR_FILE=drugsfda-core/target/*.jar
RUN mkdir -p /usr/share/ipcode/drugsfda
ENTRYPOINT ["java", "-jar", "-Xms256M", "-Xmx1G", "-Xss512k", "/usr/share/ipcode/drugsfda/drugsfda-core.jar", "-server"]
COPY ${JAR_FILE} /usr/share/ipcode/drugsfda/drugsfda-core.jar