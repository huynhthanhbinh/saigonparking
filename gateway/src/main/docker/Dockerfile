FROM kong:2.0.4-alpine
ADD proto /usr/local/kong/proto
ADD certificate /usr/local/kong/certificate
USER root
RUN apk add build-base git
RUN luarocks install lua-protobuf
RUN luarocks install lua-cjson
RUN luarocks install lua_pack
RUN luarocks install kong-plugin-grpc-web