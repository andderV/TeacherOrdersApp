FROM openjdk:17
COPY . /TeacherOrdersApp
WORKDIR /TeacherOrdersApp
RUN javac Main.java
CMD ["java", "Main"]
