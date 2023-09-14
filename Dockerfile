#### Stage 1: Build the react application
FROM node as frontend-build

WORKDIR /react_build
####Copy the main application
COPY ./frontend/fitness-tracker/package*.json /react_build

RUN npm install

RUN rm -rf public/images
COPY ./frontend/fitness-tracker /react_build
RUN npm run build


#### Stage 1: Build the application
FROM maven:3.8.7-openjdk-18-slim AS backend-build

#### add pom.xml and source code
ADD ./backend/pom.xml pom.xml
ADD ./backend/src src/

#### RUN mkdir ./src/main/resources/static
COPY --from=frontend-build /react_build/build ./src/main/resources/static

####package jar
RUN mvn clean package
####Second stage: runtime environment
FROM eclipse-temurin:17.0.8_7-jre

####copy jar from the first stage
COPY  --from=backend-build ./target/trvbackend-1.0-SNAPSHOT.jar /app/

EXPOSE 8080

CMD ["java","-jar","/app/trvbackend-1.0-SNAPSHOT.jar"]