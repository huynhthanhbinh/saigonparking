#!/bin/sh
export MSYS_NO_PATHCONV=1

KONG_ADMIN_HOST=localhost
KONG_ADMIN_PORT=8001
CONNECT_TIMEOUT=20000

# create function to prevent boilerplate code
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

# create function to prevent boilerplate code
registerServiceGrpcRoute() {
  printf "\nRegister %s service gRPC route\n" $1
  curl -XPOST ${KONG_ADMIN_HOST}:${KONG_ADMIN_PORT}/services/$1/routes \
    --data protocols=grpc \
    --data name=$1-grpc \
    --data paths=$2
  printf "\n"
}

# register Auth Service
SERVICE_NAME=auth
SERVICE_HOST=auth-service
SERVICE_PORT=7777
SERVICE_PATH=/com.bht.saigonparking.api.grpc.auth.AuthService/

registerService ${SERVICE_NAME} ${SERVICE_HOST} ${SERVICE_PORT} ${CONNECT_TIMEOUT}
registerServiceGrpcRoute ${SERVICE_NAME} ${SERVICE_PATH}

# register User Service
SERVICE_NAME=user
SERVICE_HOST=user-service
SERVICE_PORT=8888
SERVICE_PATH=/com.bht.saigonparking.api.grpc.user.UserService/

registerService ${SERVICE_NAME} ${SERVICE_HOST} ${SERVICE_PORT} ${CONNECT_TIMEOUT}
registerServiceGrpcRoute ${SERVICE_NAME} ${SERVICE_PATH}

# register ParkingLot Service
SERVICE_NAME=parkinglot
SERVICE_HOST=parkinglot-service
SERVICE_PORT=9999
SERVICE_PATH=/com.bht.saigonparking.api.grpc.parkinglot.ParkingLotService/

registerService ${SERVICE_NAME} ${SERVICE_HOST} ${SERVICE_PORT} ${CONNECT_TIMEOUT}
registerServiceGrpcRoute ${SERVICE_NAME} ${SERVICE_PATH}

export MSYS_NO_PATHCONV=0
