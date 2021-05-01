# defines the used JDK
FROM openjdk:8-jre

# Ports used by MockMock
ENV MOCKMOCK_SMTP_PORT=25
ENV MOCKMOCK_HTTP_PORT=8989

# Link to download a MockMock release
ADD ["https://github.com/tweakers-dev/MockMock/blob/master/release/MockMock.jar?raw=true", \
     "/mockmock/"]

# Exposed ports (25 for SMTP, 8989 for HTTP)
EXPOSE 25 8989

# Working directory
WORKDIR /mockmock/

# First command to execute in the container
ENTRYPOINT java -jar MockMock.jar -p $MOCKMOCK_SMTP_PORT -h $MOCKMOCK_HTTP_PORT