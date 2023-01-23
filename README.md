# Elevator System
 It represents a scenario in which two elevators are presented in a 50-story building. In 1.0 there are two types of elevator with different limitations 
## Public Elevator
- Cannot load more than 1 ton.
- You need a key card to get to the basement and the 50th floor.
## Freight Elevator
- Cannot load more than 3 tons.

# Packages
## Building
### Responsabilities.
- Contains te information of the buildings and floor.
### Clients
- Elevator

## Elevator
Is the core of the program and the package that has te most part of the intelligence.
### Responsabilities.
- Contains the information of al the elevators.
- Knows how to manage the elevators
- Response the reques of elevator users.

## Users
In this program are used as examples.
### Responsabilities
- Contains the information of the users and allows they to take decision
### Dependences
- Elevator
- Building

## Security System
### Responsabilities
- Give access key.
- Decide when an user can acces to a floor in base of the access key.
** this package could easily increase their responsabilities**
 

## Initialization and configuration
### Responsabilities
- Contains configuration values.
- Initialize Objects
- Decide when a message must be logged.

# Annotations
- To avoid concurrency problems each Elevator has his own thread.
- To decide how include the new request in the queue we assume that each Elevator works as two elevators beacause is not the same the elevator ascending that descending. The algorithm include the new step in the queue prioritizing not to change the direction of the elevator.

# Next steps
- Implement the following classes as a singleton and inject that
	- StaticConfiguration
	- Logger
	- KeyManager
	- ElevatorManager (have an elevator manager by building could be a decision too)
- Implement Queues to acces the elevators.
- Implement a System Messages manager (could be a responsability of the logger).
