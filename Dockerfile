FROM amazoncorretto:17
LABEL authors="jaehyun-park"
ARG JAR_FILE=./build/libs/DuruDuru-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]