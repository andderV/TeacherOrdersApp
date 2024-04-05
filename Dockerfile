FROM openjdk:17-oracle
COPY ./target/TeacherOrdersApp-0.0.1-SNAPSHOT.war /usr/app/
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "TeacherOrdersApp-0.0.1-SNAPSHOT.war"]