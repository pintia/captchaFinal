FROM openjdk:8-jre

WORKDIR /app/captcha-final
COPY target/*.jar ./
ADD entrypoint.sh /
RUN bash -c "[ -e *.jar ]"

ENV APPLICATION_NAME captcha-final
ENV JAR_FILE /app/captcha-final/captcha-final-0.0.1.jar

ENTRYPOINT ["/entrypoint.sh"]

EXPOSE 8080
