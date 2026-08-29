# ZooKeeper 3.9.x — Developer Command Cheat Sheet

> Practical commands for our Java + Docker ZooKeeper POCs.

## 1. Docker — Start / Stop

```bash
docker compose up -d
docker compose ps
docker ps
docker compose stop
docker compose down
```

Follow logs:
```bash
docker logs -f zk-1
docker logs -f zk-2
docker logs -f zk-3
```

Last 30 lines:
```bash
docker logs zk-1 --tail 30
```

## 2. ZooKeeper Ports

| Node | Mac port | Container port | Purpose |
|---|---:|---:|---|
| zk-1 | 2181 | 2181 | Client connections |
| zk-2 | 2182 | 2181 | Client connections |
| zk-3 | 2183 | 2181 | Client connections |

Inside Docker:
```text
2888 → ZooKeeper server-to-server communication
3888 → ZooKeeper leader election
```

Java ensemble connection:
```text
localhost:2181,localhost:2182,localhost:2183
```

## 3. Open ZooKeeper CLI

```bash
docker exec -it zk-1 zkCli.sh
```

Or:
```bash
docker exec -it zk-2 zkCli.sh
docker exec -it zk-3 zkCli.sh
```

Inside the CLI:
```text
help
```

## 4. Navigate the ZooKeeper Tree

```text
ls /
ls /services
ls /services/order
```

Read data:
```text
get /services/order/instance-1
```

Node metadata:
```text
stat /services/order/instance-1
```

Create:
```text
create /demo "hello"
create /demo/child "data"
```

Update:
```text
set /demo "new-data"
```

Delete:
```text
delete /demo/child
```

> A znode normally must have no children before it can be deleted.

## 5. `ls` vs `get`

`ls` shows children:
```text
ls /services/order
```

`get` shows data stored in a znode:
```text
get /services/order/instance-1
```

Mental model:
```text
/services/order
├── instance-1 → data = 10.0.0.1:8080
└── instance-2 → data = 10.0.0.2:8080
```

`ls` → "Who are the children?"

`get` → "What data is stored here?"

## 6. Check Server State

ZooKeeper 3.9.x provides an AdminServer.

```bash
docker exec zk-1 sh -c "curl -s http://localhost:8080/commands/stat"
```

Filter the state:
```bash
docker exec zk-1 sh -c "curl -s http://localhost:8080/commands/stat | grep server_state"
docker exec zk-2 sh -c "curl -s http://localhost:8080/commands/stat | grep server_state"
docker exec zk-3 sh -c "curl -s http://localhost:8080/commands/stat | grep server_state"
```

Expected:
```text
"server_state" : "leader"
```
or:
```text
"server_state" : "follower"
```

Exactly one should be leader in a normal 3-node ensemble.

## 7. Check All Leaders in One Command

```bash
for i in 1 2 3; do
  echo "===== zk-$i ====="
  docker exec zk-$i sh -c "curl -s http://localhost:8080/commands/stat | grep server_state"
done
```

## 8. Check `ruok`

```bash
docker exec zk-1 sh -c "echo ruok | nc localhost 2181"
```

If enabled, expected:
```text
imok
```

If there is no response, use the AdminServer `/commands/stat` endpoint instead.

## 9. Test Leader Failover

First identify the leader:
```bash
for i in 1 2 3; do
  echo "===== zk-$i ====="
  docker exec zk-$i sh -c "curl -s http://localhost:8080/commands/stat | grep server_state"
done
```

Suppose `zk-2` is leader:
```bash
docker stop zk-2
```

Wait for election, then check the remaining nodes:
```bash
docker exec zk-1 sh -c "curl -s http://localhost:8080/commands/stat | grep server_state"
docker exec zk-3 sh -c "curl -s http://localhost:8080/commands/stat | grep server_state"
```

One should become the new leader.

## 10. Demonstrate Loss of Quorum

With one node already stopped:
```bash
docker stop zk-1
```

Now only one of the original three servers remains.

```text
3 servers
↓
quorum = 2
↓
only 1 remains
↓
no quorum
```

Restart:
```bash
docker start zk-1 zk-2
```

Then:
```bash
docker compose ps
```

## 11. Our Service Discovery Paths

```text
/services/order
├── instance-1
├── instance-2
└── instance-3
```

Example:
```text
ls /services/order
get /services/order/instance-1
```

Service instances are registered using ephemeral znodes.

## 12. Our Leader Election Paths

```text
/election
├── candidate-0000000001
├── candidate-0000000002
└── candidate-0000000003
```

Pattern:
```text
EPHEMERAL + SEQUENTIAL
        ↓
ordered temporary candidates
        ↓
smallest sequence number
        ↓
leader
```

## 13. Our Distributed Lock Paths

```text
/locks/payment
├── lock-0000000001
├── lock-0000000002
└── lock-0000000003
```

Pattern:
```text
smallest sequence number → lock owner
other workers → watch predecessor
```

## 14. Java Connection Strings

Single server:
```java
new ZooKeeper("localhost:2181", 10_000, watcher);
```

Ensemble:
```java
new ZooKeeper(
        "localhost:2181,localhost:2182,localhost:2183",
        10_000,
        watcher);
```

## 15. Java — Ephemeral Node

```java
zooKeeper.create(
        "/services/order/instance-1",
        "localhost:8080".getBytes(StandardCharsets.UTF_8),
        ZooDefs.Ids.OPEN_ACL_UNSAFE,
        CreateMode.EPHEMERAL);
```

Session expires → ephemeral node is deleted.

## 16. Java — Ephemeral Sequential Node

```java
zooKeeper.create(
        "/election/candidate-",
        new byte[0],
        ZooDefs.Ids.OPEN_ACL_UNSAFE,
        CreateMode.EPHEMERAL_SEQUENTIAL);
```

ZooKeeper returns the actual path, for example:
```text
/election/candidate-0000000001
```

## 17. Java — Watch Children

```java
List<String> children =
        zooKeeper.getChildren("/services/order", this);
```

A child change can generate:
```text
NodeChildrenChanged
```

## 18. Java — Watch a Specific Node

Used by our leader-election / lock pattern:

```java
zooKeeper.exists(
        "/election/" + predecessor,
        this);
```

Conceptually:
```text
watch predecessor
      ↓
predecessor disappears
      ↓
NodeDeleted event
      ↓
re-check election/lock
```

## 19. Important Failure Semantics

ZooKeeper server failure:
```text
ZK-1 dies
   ↓
client loses connection
   ↓
client can reconnect to another server
   ↓
session can survive
```

Application failure:
```text
Application dies
   ↓
ZooKeeper session eventually expires
   ↓
ephemeral nodes are deleted
```

Important:
```text
ZooKeeper server failure ≠ session expiration
```

## 20. Common Mistakes

### Parent paths are not automatically created

This fails if `/locks` doesn't exist:
```java
zooKeeper.create("/locks/payment", ...);
```

Create:
```text
/locks
/locks/payment
```

first.

### Ephemeral does not mean immediately deleted

An ephemeral node is deleted when its ZooKeeper session expires.

### ZooKeeper is not a load balancer

ZooKeeper is primarily a coordination service. It can maintain membership information, but it is not itself a load balancer.

### One ZooKeeper client means one session

For our 3-worker POC:
```text
Worker-1 → ZooKeeper client → Session-1
Worker-2 → ZooKeeper client → Session-2
Worker-3 → ZooKeeper client → Session-3
```

## 21. Interview Revision

```text
EPHEMERAL
    ↓
Membership / liveness

SEQUENTIAL
    ↓
Ordering

WATCH
    ↓
Notification

EPHEMERAL + SEQUENTIAL
    ↓
Leader Election / Distributed Lock

ENSEMBLE + QUORUM
    ↓
ZooKeeper high availability
```

Quorum:
```text
3 nodes → quorum 2 → tolerate 1 failure
5 nodes → quorum 3 → tolerate 2 failures
```

Three core use cases:
```text
1. Service Discovery / Membership
2. Leader Election
3. Distributed Lock / Coordination
```

## 22. Cleanup

```bash
docker compose down
```

Start again:
```bash
docker compose up -d
```

## Most-used commands

```bash
docker compose up -d
docker compose ps
docker compose down

docker logs -f zk-1

docker exec -it zk-1 zkCli.sh

docker exec zk-1 sh -c "curl -s http://localhost:8080/commands/stat"

docker exec zk-1 sh -c "curl -s http://localhost:8080/commands/stat | grep server_state"
```

Inside `zkCli.sh`:
```text
ls /
get /path
stat /path
create /path "data"
set /path "data"
delete /path
deleteall /path
```
