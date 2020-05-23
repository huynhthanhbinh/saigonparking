#!/bin/sh

# Gateway is the 1st vm instance in GCP
# Gateway is config with static EXTERNAL IP address: referenced by www.saigonparking.wtf
KONG_ADMIN_HOST=localhost
KONG_ADMIN_PORT=8001
CONNECT_TIMEOUT=20000

# Service is another vm instance in GCP
# Service is config with static INTERNAL IP address: referenced by saigonparkingservice
SERVICE_HOST=saigonparkingservice

registerService() {
  printf "\nRegister %s service\n" $1
  curl -XPOST ${KONG_ADMIN_HOST}:${KONG_ADMIN_PORT}/services \
    --data name=$1 \
    --data protocol=grpc \
    --data host=$2 \
    --data port=$3 \
    --data connect_timeout=$4 \
    --data write_timeout=$4 \
    --data read_timeout=$4
  printf "\n"
}

registerServiceGrpcRoute() {
  printf "\nRegister %s service gRPC route\n" $1
  curl -XPOST ${KONG_ADMIN_HOST}:${KONG_ADMIN_PORT}/services/$1/routes \
    --data protocols=grpc \
    --data name=$1-grpc \
    --data paths=$2
  printf "\n"
}

# register Mail Service
SERVICE_NAME=mail
SERVICE_PORT=6666
SERVICE_PATH=/com.bht.saigonparking.api.grpc.mail.MailService/

registerService ${SERVICE_NAME} ${SERVICE_HOST} ${SERVICE_PORT} ${CONNECT_TIMEOUT}
registerServiceGrpcRoute ${SERVICE_NAME} ${SERVICE_PATH}

# register Auth Service
SERVICE_NAME=auth
SERVICE_PORT=7777
SERVICE_PATH=/com.bht.saigonparking.api.grpc.auth.AuthService/

registerService ${SERVICE_NAME} ${SERVICE_HOST} ${SERVICE_PORT} ${CONNECT_TIMEOUT}
registerServiceGrpcRoute ${SERVICE_NAME} ${SERVICE_PATH}

# register User Service
SERVICE_NAME=user
SERVICE_PORT=8888
SERVICE_PATH=/com.bht.saigonparking.api.grpc.user.UserService/

registerService ${SERVICE_NAME} ${SERVICE_HOST} ${SERVICE_PORT} ${CONNECT_TIMEOUT}
registerServiceGrpcRoute ${SERVICE_NAME} ${SERVICE_PATH}

# register ParkingLot Service
SERVICE_NAME=parkinglot
SERVICE_PORT=9999
SERVICE_PATH=/com.bht.saigonparking.api.grpc.parkinglot.ParkingLotService/

registerService ${SERVICE_NAME} ${SERVICE_HOST} ${SERVICE_PORT} ${CONNECT_TIMEOUT}
registerServiceGrpcRoute ${SERVICE_NAME} ${SERVICE_PATH}
