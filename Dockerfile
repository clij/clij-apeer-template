FROM maven:3-jdk-11 as builder

COPY . /usr/src/app
WORKDIR /usr/src/app

RUN mvn clean compile assembly:single

FROM openjdk:11-jre-slim
WORKDIR /usr/src/app

# didn't work:
# RUN sed -i "s#deb http://deb.debian.org/debian stretch main contrib non-free#deb http://deb.debian.org/debian stretch  main contrib non-free#g" /etc/apt/sources.list

RUN apt-get update
RUN apt-get install -y software-properties-common
RUN add-apt-repository 'deb http://deb.debian.org/debian stretch main contrib non-free'

RUN apt-get update && apt-get install -y ocl-icd-opencl-dev
RUN apt-get update && apt-get install -y clinfo nvidia-opencl-icd && clinfo


COPY --from=builder /usr/src/app/target/clapeer-0.1.0-jar-with-dependencies.jar .
CMD [ "java", "-jar", "clapeer-0.1.0-jar-with-dependencies.jar" ]
