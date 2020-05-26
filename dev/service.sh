#!/bin/sh

export MSYS_NO_PATHCONV=1

# global variables
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
  printf "\nRegister %s service GRPC route\n" $1
  curl -XPOST ${KONG_ADMIN_HOST}:${KONG_ADMIN_PORT}/services/$1/routes \
    --data protocols=grpc \
    --data name=$1-grpc \
    --data paths=$2
  printf "\n"
}

# create function to prevent boilerplate code
registerServiceHttpRoute() {
  printf "\nRegister %s service HTTP route\n" $1
  curl -XPOST ${KONG_ADMIN_HOST}:${KONG_ADMIN_PORT}/services/$1/routes \
    --data protocols=http \
    --data name=$1 \
    --data paths=$2
  printf "\n"
}

# create function to prevent boilerplate code
registerServiceRoutePlugins() {
  printf "\nRegister %s service route plugins\n" $1
  curl -XPOST ${KONG_ADMIN_HOST}:${KONG_ADMIN_PORT}/routes/$1/plugins \
    --data name=grpc-web
  printf "\n"
}

# create function to prevent boilerplate code
registerRateLimiting() {
  printf "\nRegister %s service rate-limiting\n" $1
  curl -XPOST ${KONG_ADMIN_HOST}:${KONG_ADMIN_PORT}/services/$1/plugins \
    --data name=rate-limiting \
    --data config.second=2 \
    --data config.hour=10000 \
    --data config.policy=local
  printf "\n"
}

# create function to prevent boilerplate code
registerCrossOriginResourceSharing() {
  printf "\nRegister %s service CORS\n" $1
  curl -XPOST ${KONG_ADMIN_HOST}:${KONG_ADMIN_PORT}/routes/$1/plugins \
    --data name=cors \
    --data config.origins=* \
    --data config.methods=GET \
    --data config.methods=POST \
    --data config.methods=PUT \
    --data config.methods=DELETE \
    --data config.methods=OPTIONS \
    --data config.headers=Accept \
    --data config.headers=Accept-Version \
    --data config.headers=Authorization \
    --data config.headers=Cache-Control \
    --data config.headers=Content-Length \
    --data config.headers=Content-MD5 \
    --data config.headers=Content-Transfer-Encoding \
    --data config.headers=Content-Type \
    --data config.headers=Custom-Header-1 \
    --data config.headers=Date \
    --data config.headers=Grpc-Timeout \
    --data config.headers=Keep-Alive \
    --data config.headers=X-Accept-Content-Transfer-Encoding \
    --data config.headers=X-Accept-Response-Streaming \
    --data config.headers=X-Auth-Token \
    --data config.headers=X-Grpc-Web \
    --data config.headers=X-User-Agent \
    --data config.credentials=false \
    --data config.max_age=3600
  printf "\n"
}

# register Mail Service
SERVICE_NAME=mail
SERVICE_HOST=mail-service
SERVICE_PORT=6666
SERVICE_PATH=/com.bht.saigonparking.api.grpc.mail.MailService/

registerService ${SERVICE_NAME} ${SERVICE_HOST} ${SERVICE_PORT} ${CONNECT_TIMEOUT}
registerServiceGrpcRoute ${SERVICE_NAME} ${SERVICE_PATH}
registerServiceHttpRoute ${SERVICE_NAME} ${SERVICE_PATH}
registerRateLimiting ${SERVICE_NAME}
registerServiceRoutePlugins ${SERVICE_NAME}
registerCrossOriginResourceSharing ${SERVICE_NAME}

# register Auth Service
SERVICE_NAME=auth
SERVICE_HOST=auth-service
SERVICE_PORT=7777
SERVICE_PATH=/com.bht.saigonparking.api.grpc.auth.AuthService/

registerService ${SERVICE_NAME} ${SERVICE_HOST} ${SERVICE_PORT} ${CONNECT_TIMEOUT}
registerServiceGrpcRoute ${SERVICE_NAME} ${SERVICE_PATH}
registerServiceHttpRoute ${SERVICE_NAME} ${SERVICE_PATH}
registerRateLimiting ${SERVICE_NAME}
registerServiceRoutePlugins ${SERVICE_NAME}
registerCrossOriginResourceSharing ${SERVICE_NAME}

# register User Service
SERVICE_NAME=user
SERVICE_HOST=user-service
SERVICE_PORT=8888
SERVICE_PATH=/com.bht.saigonparking.api.grpc.user.UserService/

registerService ${SERVICE_NAME} ${SERVICE_HOST} ${SERVICE_PORT} ${CONNECT_TIMEOUT}
registerServiceGrpcRoute ${SERVICE_NAME} ${SERVICE_PATH}
registerServiceHttpRoute ${SERVICE_NAME} ${SERVICE_PATH}
registerRateLimiting ${SERVICE_NAME}
registerServiceRoutePlugins ${SERVICE_NAME}
registerCrossOriginResourceSharing ${SERVICE_NAME}

# register ParkingLot Service
SERVICE_NAME=parkinglot
SERVICE_HOST=parkinglot-service
SERVICE_PORT=9999
SERVICE_PATH=/com.bht.saigonparking.api.grpc.parkinglot.ParkingLotService/

registerService ${SERVICE_NAME} ${SERVICE_HOST} ${SERVICE_PORT} ${CONNECT_TIMEOUT}
registerServiceGrpcRoute ${SERVICE_NAME} ${SERVICE_PATH}
registerServiceHttpRoute ${SERVICE_NAME} ${SERVICE_PATH}
registerRateLimiting ${SERVICE_NAME}
registerServiceRoutePlugins ${SERVICE_NAME}
registerCrossOriginResourceSharing ${SERVICE_NAME}

export MSYS_NO_PATHCONV=0
