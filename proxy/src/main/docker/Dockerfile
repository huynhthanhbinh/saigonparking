FROM envoyproxy/envoy:v1.12.2
USER root
RUN mkdir -p /usr/local/envoy
ADD certificate /usr/local/envoy/certificate
COPY envoy.yaml /etc/envoy/envoy.yaml
CMD /usr/local/bin/envoy -c /etc/envoy/envoy.yaml