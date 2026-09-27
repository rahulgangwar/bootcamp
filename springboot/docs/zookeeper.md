



### Open zookeeper

```bash
docker exec -it zookeeper bash
```

### Useful commands

```bash
# start zookeeper cli
zkCli.sh

# Create nodes for rate limiting
create /rate-limiter ""
create /rate-limiter/global "limit=10,window=60"

# Lookup the child nodes
ls /
ls /rate-limiter

# Get the data of the node
get /rate-limiter/global

# Other helpful commands
stat /rate-limiter/global
```