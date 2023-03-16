FROM oracle/graalvm-ce:20.1.0-java11 as graalvm
RUN gu install native-image

COPY . /home/app/atm-cli
WORKDIR /home/app/atm-cli

RUN native-image --no-server -cp build/libs/atm-cli-*-all.jar

FROM frolvlad/alpine-glibc
RUN apk update && apk add libstdc++
EXPOSE 8080
COPY --from=graalvm /home/app/atm-cli/atm-cli /app/atm-cli
ENTRYPOINT ["/app/atm-cli"]
