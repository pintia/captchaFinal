FROM openjdk:8-jre

WORKDIR /app/captcha-final
COPY target/*.jar ./
RUN bash -c "[ -e *.jar ]"

ENV APPLICATION_NAME captcha-final
ENV JAR_FILE /app/captcha-final/captcha-final-0.0.1.jar

ENTRYPOINT ["java", "-jar", "captcha-final-0.0.1.jar"]

EXPOSE 8080
