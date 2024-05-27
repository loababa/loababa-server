FROM amazoncorretto:21

WORKDIR /app/loababa

ARG JAR_FILE_PATH=build/libs/*.jar
ARG ENV_FILE_PATH=src/main/resources/secret/.env
ARG DOCKER_RUN_PATH=docker/docker_run.sh

COPY ${JAR_FILE_PATH} app.jar
COPY ${ENV_FILE_PATH} .env
COPY ${DOCKER_RUN_PATH} docker_run.sh

RUN chmod +x docker_run.sh

ENTRYPOINT ["./docker_run.sh"]
