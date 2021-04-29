FROM openjdk:8-jre

ENV MOCKMOCK_SMPT_PORT=80
ENV MOCKMOCK_HTTP_PORT=8989

ADD ["https://github.com/tweakers-dev/MockMock/blob/master/release/MockMock.jar?raw=true", \
     "/mockmock/"]

EXPOSE 80 8989

WORKDIR /mockmock/

ENTRYPOINT java -jar MockMock.jar -p $MOCKMOCK_SMPT_PORT -h $MOCKMOCK_HTTP_PORT
