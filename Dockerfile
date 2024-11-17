FROM openjdk:17-oracle
COPY ./target/TeacherOrdersApp-2.0.1.war /usr/app/
WORKDIR /usr/app
EXPOSE 80
ENTRYPOINT ["java", "-jar", "TeacherOrdersApp-2.0.1.war"]