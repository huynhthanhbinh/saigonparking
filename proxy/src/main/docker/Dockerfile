FROM envoyproxy/envoy-alpine:v1.14.1
USER root
RUN mkdir -p /usr/local/envoy
ADD certificate /usr/local/envoy/certificate
COPY envoy.yml /etc/envoy/envoy.yml
CMD /usr/local/bin/envoy -c /etc/envoy/envoy.yml