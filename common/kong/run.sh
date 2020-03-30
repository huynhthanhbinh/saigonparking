#!/bin/sh

# catch all service

curl -XPOST localhost:8001/services \
  --data name=appserver \
  --data protocol=grpc \
  --data host=appserver \
  --data port=9999

curl -XPOST localhost:8001/services/appserver/routes \
  --data protocols=grpc \
  --data name=catch-all \
  --data paths=/

# catch parking lot service only

curl -XPOST localhost:8001/services \
  --data name=parkinglot \
  --data protocol=grpc \
  --data host=appserver \
  --data port=9999

curl -XPOST localhost:8001/services/parkinglot/routes \
  --data protocols=grpc \
  --data name=catch-all-parking-lot \
  --data paths=/com.bht.parkingmap.api.proto.parkinglot.ParkingLotService/