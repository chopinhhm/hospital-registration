FROM eclipse-temurin:17-jre-alpine
LABEL maintainer="chopinhhm"
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8083
ENV JAVA_OPTS="-Xms256m -Xmx512m"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
