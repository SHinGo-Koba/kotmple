# App Building phase --------
FROM eclipse-temurin:17 AS build

RUN mkdir /appbuild
COPY . /appbuild

WORKDIR /appbuild

RUN ./gradlew clean build
# End App Building phase --------

# Container setup --------
FROM eclipse-temurin:17-jre
EXPOSE 8080:8080

# Creating user
ENV APPLICATION_USER user
RUN adduser --system $APPLICATION_USER

# Giving permissions
RUN mkdir /app
RUN mkdir /app/resources
RUN chown -R $APPLICATION_USER /app
RUN chmod -R 755 /app

# Setting user to use when running the image
USER $APPLICATION_USER

# Copying needed files
COPY --from=build /appbuild/build/libs/*.jar /app/kotmple.jar
WORKDIR /app

# Entrypoint definition
CMD ["java", "-jar", "/app/kotmple.jar"]
# End Container setup --------
