# use jdk17 image
FROM openjdk:17-jdk

# set jar_file
ARG JAR_FILE=./build/libs/*.jar

# When running a container with what is stored in the variable, the name is app.jar
COPY ${JAR_FILE} app-jar

# Commands to run when the built image runs
ENTRYPOINT ["java", "-jar", "app-jar"]