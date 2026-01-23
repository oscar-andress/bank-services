FROM eclipse-temurin:17-jdk AS build
WORKDIR /workspace

COPY common-lib/mvnw ./common-lib/
COPY common-lib/.mvn ./common-lib/.mvn
COPY common-lib/pom.xml ./common-lib/
COPY common-lib/src ./common-lib/src
RUN chmod +x ./common-lib/mvnw
RUN cd common-lib && ./mvnw clean install -DskipTests

WORKDIR /workspace/app
COPY client-person/mvnw .
COPY client-person/.mvn .mvn
COPY client-person/pom.xml .
COPY client-person/src src
RUN chmod +x mvnw
RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:17-jre
VOLUME /tmp
ENV TZ=America/Guayaquil
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
EXPOSE 8083

ENTRYPOINT ["java","-cp","app:app/lib/*", "-Xmx512M", "-Xms512M", "bank.client_person.ClientPersonApplication"]