

### Open redis
```bash
docker exec -it redis redis-cli
```

### Commands
```bash
KEYS *

SCAN 0

SET product:101 "iPhone 17"

GET product:101

EXISTS product:101

EXPIRE product:101 60

TTL product:101

DEL product:101
```