
### Command line 
Start server : 
```console
.\zkServer.cmd
```

Connect CLI :  
```commandline
.\zkCli.cmd -server 127.0.0.1:2181
```

Create Node:
```commandline
create /config ""
create /config/db_url "Initial Value"

--- update value
set /config/db_url "Updated Value"

-- get value
get /config/db_url
```

### Java Code Methods
```java
zooKeeper.exists
zooKeeper.create
zooKeeper.getData
```

