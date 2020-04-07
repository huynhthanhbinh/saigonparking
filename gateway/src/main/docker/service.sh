#!/bin/sh
# service host: 10.148.0.7

# register parking lot service
curl -XPOST localhost:8001/services \
  --data name=parkinglot \
  --data protocol=grpc \
  --data host=10.148.0.7 \
  --data port=9999 \
  --data connect_timeout=10000 \
  --data write_timeout=10000 \
  --data read_timeout=10000

# route parking lot service
curl -XPOST localhost:8001/services/parkinglot/routes \
  --data protocols=grpc \
  --data name=catch-all-parking-lot \
  --data paths=/com.bht.parkingmap.api.proto.parkinglot.ParkingLotService/