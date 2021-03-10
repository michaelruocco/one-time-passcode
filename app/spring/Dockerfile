FROM adoptopenjdk/openjdk13:alpine-jre

ARG VERSION

ENV SERVER_PORT=80 \
    SPRING_PROFILES_ACTIVE=default \
    ENVIRONMENT=idv-local \
    CONTEXT_URI=http://localhost:8081 \
    AWS_REGION=eu-west-1 \
    AWS_SNS_ENDPOINT_URI=http://aws-services:4566 \
    AWS_SNS_SENDER_ID="IDV Demo" \
    AWS_SES_ENDPOINT_URI=http://aws-services:4566 \
    AWS_SES_SOURCE_EMAIL_ADDRESS=idv.demo.mail@gmail.com \
    RESPONSE_FILTERING_ENABLED=false \
    REQUEST_LOGGING_ENABLED=false \
    RESPONSE_LOGGING_ENABLED=false

COPY build/libs/idv-one-time-passcode-spring-app-${VERSION}.jar /opt/app.jar

CMD java \
  -Djava.security.egd=file:/dev/./urandom \
  -Dserver.port=${SERVER_PORT} \
  -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} \
  -Denvironment=${ENVIRONMENT} \
  -Dcontext.uri=${CONTEXT_URI} \
  -Daws.region=${AWS_REGION} \
  -Daws.sns.endpoint.uri=${AWS_SNS_ENDPOINT_URI} \
  -Daws.sns.sender.id=${AWS_SNS_SENDER_ID} \
  -Daws.ses.endpoint.uri=${AWS_SES_ENDPOINT_URI} \
  -Daws.ses.source.email.address=${AWS_SES_SOURCE_EMAIL_ADDRESS} \
  -Dspring.data.mongodb.uri=${MONGO_CONNECTION_STRING} \
  -Dresponse.filtering.enabled=${RESPONSE_FILTERING_ENABLED} \
  -Drequest.logging.enabled=${REQUEST_LOGGING_ENABLED} \
  -Dresponse.logging.enabled=${RESPONSE_LOGGING_ENABLED} \
  -jar /opt/app.jar