FROM openjdk:8-jre

ENV MOCKMOCK_SMTP_PORT=25
ENV MOCKMOCK_HTTP_PORT=8989

ADD ["https://github.com/tweakers-dev/MockMock/blob/master/release/MockMock.jar?raw=true", \
     "/mockmock/"]

EXPOSE 25 8989

WORKDIR /mockmock/

ENTRYPOINT java -jar MockMock.jar -p $MOCKMOCK_SMTP_PORT -h $MOCKMOCK_HTTP_PORT
