#!/bin/sh

# service host: 10.148.0.7
APP_SERSER_HOST=10.148.0.7

printf "\n\nRegister parking-lot service"
curl -XPOST localhost:8001/services \
  --data name=parkinglot \
  --data protocol=grpc \
  --data host=${APP_SERSER_HOST} \
  --data port=9999 \
  --data connect_timeout=10000 \
  --data write_timeout=10000 \
  --data read_timeout=10000

printf "\n\nRegister parking-lot routing"
curl -XPOST localhost:8001/services/parkinglot/routes \
  --data protocols=grpc \
  --data name=catch-all-parking-lot \
  --data paths=/com.bht.parkingmap.api.proto.parkinglot.ParkingLotService/