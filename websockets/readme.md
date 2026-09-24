# WebSocket — Fundamental Architecture

## 1. Connection Flow

A WebSocket connection starts as a normal TCP connection.

```
CLIENT                         SERVER
  │                              │
  │──── TCP connection ─────────►│
  │                              │
  │──── HTTP Upgrade ───────────►│
  │                              │
  │◄─── 101 Switching Protocols ─│
  │                              │
  │════ WebSocket connection ════│
  │                              │
  │──── WebSocket frame ────────►│
  │◄─── WebSocket frame ─────────│
  │                              │
  │     Connection stays open    │
  │     Full-duplex communication│
```

---

## 2. Step 1 — TCP Connection

The client first establishes a normal TCP connection with the server.

```
Client ───── TCP ─────► Server:8080
```

In Java:

```
ServerSocket serverSocket = new ServerSocket(8080);

Socket socket = serverSocket.accept();
```

### Key concepts

* `ServerSocket` listens for incoming TCP connections.
* `accept()` waits for a client to connect.
* `Socket` represents the connection with one specific client.

---

## 3. Step 2 — HTTP Upgrade Request

The client initially sends an HTTP request asking the server to upgrade the connection to WebSocket.

```
GET / HTTP/1.1
Host: localhost:8080
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Key: <key>
Sec-WebSocket-Version: 13
```

The important headers are:

```
Upgrade: websocket
Connection: Upgrade
```

They tell the server:

> "I want to upgrade this HTTP connection to WebSocket."

---

## 4. Step 3 — Server Accepts the Upgrade

The server responds:

```
HTTP/1.1 101 Switching Protocols
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Accept: <value>
```

### What does `101` mean?

`101 Switching Protocols` means:

> The server agrees to switch from HTTP to the WebSocket protocol.

---

## 5. Step 4 — WebSocket Connection

After the successful handshake, the same underlying TCP connection is now used for WebSocket communication.

```
Client ═════════════════════ Server
            TCP
         WebSocket
```

The connection remains open.

Unlike normal HTTP request/response communication, either side can send data at any time.

---

## 6. Step 5 — WebSocket Frames

Messages are transmitted using WebSocket frames.

```
Client ───── WebSocket Frame ─────► Server

Client ◄──── WebSocket Frame ────── Server
```

A WebSocket frame contains fields such as:

```
FIN
Opcode
Payload length
Mask
Payload
```

For example:

```
Client
  │
  │ Text frame: "Hello"
  ▼
Server
```

---

## 7. Full Connection Lifecycle

```
CLIENT                         SERVER
  │                              │
  │                              │
  │──── TCP connection ─────────►│
  │                              │
  │──── HTTP Upgrade ───────────►│
  │                              │
  │◄─── 101 Switching Protocols ─│
  │                              │
  │════ WebSocket connection ════│
  │                              │
  │──── "Hello" frame ──────────►│
  │                              │
  │◄──── "Hi" frame ─────────────│
  │                              │
  │──── "How are you?" ─────────►│
  │                              │
  │◄──── "Good!" frame ──────────│
  │                              │
  │       Connection remains     │
  │       open until closed      │
```

---

## 8. Key Mental Model

```
WebSocket
    │
    └── runs over TCP
          │
          ├── TCP connection
          │
          └── HTTP Upgrade handshake
                    │
                    ▼
              WebSocket protocol
                    │
                    ▼
              WebSocket frames
```

---

## 9. Mapping to Our Java POC

Our current Java code corresponds to the first part of the WebSocket lifecycle.

```
WebSocketServer
      │
      ▼
ServerSocket
      │
      │ accept()
      ▼
    Socket
      │
      │ getInputStream()
      ▼
HTTP Upgrade Request
      │
      │
      ▼
101 Switching Protocols
      │
      ▼
WebSocket Connection
      │
      ▼
WebSocket Frames
```

We have currently implemented:

1. TCP server
2. TCP connection acceptance
3. Reading the client's HTTP Upgrade request

Next we need to implement:

1. HTTP Upgrade response
2. `Sec-WebSocket-Accept` calculation
3. WebSocket frame parsing
4. WebSocket frame creation
5. Sending and receiving messages

---

## 10. One-Line Summary

**WebSocket is a persistent, full-duplex communication protocol that st**
