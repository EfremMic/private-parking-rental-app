FROM hashicorp/consul:1.19.2

# Copy the Consul configuration JSON file
COPY ./consul-kv-docker.json /usr/src/consul/
WORKDIR /usr/src/consul

# Set environment variable for Consul
ENV CONSUL_HTTP_ADDR=consul:8500

# Run Consul and import KV configurations
ENTRYPOINT until consul kv import @consul-kv-docker.json; do echo "Waiting for Consul"; sleep 2; done
