FROM kong:2.0.4-alpine
ADD certificate /usr/local/kong/certificate
USER root
COPY kong.yml /etc/kong/kong.yml
COPY kong_dev.yml /etc/kong/kong_dev.yml
RUN apk add build-base git
RUN luarocks install lua-protobuf
RUN luarocks install lua-cjson
RUN luarocks install lua_pack
RUN luarocks install kong-plugin-grpc-web