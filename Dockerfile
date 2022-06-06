FROM gradle:jdk11-alpine AS build
WORKDIR /gif-on-rate
COPY . .
RUN gradle clean build

FROM openjdk:11
ENV ARTIFACT_NAME=gif-on-rate-1.0.jar
COPY --from=build /gif-on-rate/build/libs/$ARTIFACT_NAME app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]