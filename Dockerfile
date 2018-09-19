FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/movie-score-2.0.jar moviescore.jar
ENTRYPOINT exec sudo java -jar /moviescore.jar > log.log
