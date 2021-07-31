FROM amazoncorretto:11-alpine as builder
WORKDIR /application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM amazoncorretto:11-alpine
LABEL mantainer=jorgebo10@gmail.com
EXPOSE 8080
EXPOSE 9010
WORKDIR /application
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/application/ ./
COPY  ../run.sh ./
ENTRYPOINT ["./run.sh"]