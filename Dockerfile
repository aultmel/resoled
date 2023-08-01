FROM gradle:7.6.0-jdk17


WORKDIR /resoled

COPY src /resoled/src
COPY build.gradle settings.gradle gradlew /resoled/

EXPOSE 5000

CMD ["gradle", "bootRun"]