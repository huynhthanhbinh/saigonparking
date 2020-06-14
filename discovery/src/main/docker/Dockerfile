FROM consul:1.6
RUN mkdir -p /var/consul && chown consul:consul /var/consul
CMD consul agent -server -bootstrap -ui -client=0.0.0.0 -log-level=err -grpc-port=8502 -node=saigonparking-service -datacenter=saigonparking-dc -data-dir=/var/consul