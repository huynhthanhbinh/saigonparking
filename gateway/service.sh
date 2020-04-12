#!/bin/sh
KONG_ADMIN_HOST=localhost
KONG_ADMIN_PORT=8001
CONNECT_TIMEOUT=10000

function registerService () {
    printf "\n\nRegister %s service\n" $1
    curl -XPOST ${KONG_ADMIN_HOST}:${KONG_ADMIN_PORT}/services \
      --data name=$1 \
      --data protocol=grpc \
      --data host=$2 \
      --data port=$3 \
      --data connect_timeout=$4 \
      --data write_timeout=$4 \
      --data read_timeout=$4
}

function registerServiceRoutes () {
    printf "\n\nRegister %s service routes\n" $1
    curl -XPOST ${KONG_ADMIN_HOST}:${KONG_ADMIN_PORT}/services/$1/routes \
      --data protocols=grpc \
      --data name=catch-all-$1-services \
      --data paths=$2
}

SERVICE_NAME=user
SERVICE_HOST=10.148.0.7
SERVICE_PORT=9999
SERVICE_PATH=/com.bht.parkingmap.api.proto.user.UserService/

registerService ${SERVICE_NAME} ${SERVICE_HOST} ${SERVICE_PORT} ${CONNECT_TIMEOUT}
registerServiceRoutes ${SERVICE_NAME} ${SERVICE_PATH}

SERVICE_NAME=parkinglot
SERVICE_HOST=10.148.0.7
SERVICE_PORT=9999
SERVICE_PATH=/com.bht.parkingmap.api.proto.parkinglot.ParkingLotService/

registerService ${SERVICE_NAME} ${SERVICE_HOST} ${SERVICE_PORT} ${CONNECT_TIMEOUT}
registerServiceRoutes ${SERVICE_NAME} ${SERVICE_PATH}