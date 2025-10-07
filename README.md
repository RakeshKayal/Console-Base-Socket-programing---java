# Console-Based Socket Programming Project

## Description
This project demonstrates a **console-based client-server messaging system** using Java Socket programming.  
It allows multiple clients to connect to a server, send messages, and receive broadcasts in real-time.  
The project is designed for learning **network programming concepts**, including **TCP communication**, **multi-threading**, and **object serialization**.

---

## Features
- Multiple clients can connect to a single server.
- Real-time message broadcast to all connected clients.
- Modular project structure with packages for server, client, and utilities.
- Easy to extend for group chat, private messaging, or other network applications.

---

## Project Structure<br/>
Socket-programming/<br/>
│
├── src/<br/>
│ ├── GroupMsgForClientServer/<br/>
│ ├── TCP/<br/>
│ └── UDP/<br/>
  |___broadCastFromServerToAll/<br/>
  |__ClientToClientCommunication/<br/>
│
├── .gitignore # Ignore IDE and build files<br/>
└── README.md # Project documentation<br/>

---

## How to Run

### 1. Server
1. Navigate to the server.java file
2. Compile the server:
   ```bash
   javac Server.java
   Run the server:
   java Server
### 2. Client
1.Navigate to the client.java file.
2.Compile the client:
    ```bash
    javac Client.java
    Run the client:
    java Client

### 3. Technologies Used
Java (JDK 8+)
Java Sockets (TCP & UDP)
Object Serialization (for message passing)
