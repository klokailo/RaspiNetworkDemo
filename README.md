# Raspberry Pi Networking Demo

This project simulates a P2P network demo where multiple clients can connect and communicate with each other directly over local network.

There is only a P2P relation option with all clients communicating directly with each other. New clients joining the network have their information propagated by others already on the network

## Running
Clone the repository and configure the maven project with JDK 17 and kotlin 1.5.31. Create a run configuration for the CLI file and run it.
## CLI
```
Select IPV4 address [number] to use: 
```
Upon running the CLI, you will be prompted to select an IP address from those assigned to your various network adapters. Choose the one that corresponds to the network to which you would like to connect other devices.
```
Enter port number: 
```
You will also be prompted to select a port on which to run the local process. This port should be exposed to your network over whichever adapter you are using.

### Commands
- `help`: Lists commands with brief descriptions
- `connect <ip> <port>`: Connects this device to an existing demo network on another specified device. 
  - If neither this device nor the one you want to connect to are already part of a network, this forms a new network between the two.
  - Our network has a flat hierarchy: all devices are equal and communicate peer-to-peer.
  - If we are already part of a network when running this command, then we <b>join both networks together</b>. All clients are informed of all new clients from their respective new networks.
- `list`: Prints a list of the current devices on our network
- `quit`: Stops this client and CLI and informs our network of our departure.
- `announce <message>`: Broadcasts a text message to all clients to print
- `whisper <ip> <port> <message>`: Whispers a message to a specific recipient.
