#!/bin/sh

# register parking lot service
curl -XPOST localhost:8001/services \
  --data name=parkinglot \
  --data protocol=grpc \
  --data host=appserver \
  --data port=9999 \
  --data connect_timeout=30000 \
  --data write_timeout=30000 \
  --data read_timeout=30000
  #--client_certificate=

# route parking lot service
curl -XPOST localhost:8001/services/parkinglot/routes \
  --data protocols=grpc \
  --data name=catch-all-parking-lot \
  --data paths=/com.bht.parkingmap.api.proto.parkinglot.ParkingLotService/