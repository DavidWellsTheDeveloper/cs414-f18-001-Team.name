# User Stories & Tasks

## As a user, I would like to be able to create and play games (1)
### Acceptance Criteria
#### Client:
* Board should be viewable
* Board should be able to be interacted with (I/O)
* Board should show valid moves
* Games should be able to be started from a list
* Games should be able to be created
#### Server:
* Should be able to handle new games
* Should be able to handle viewing games
* Should be able to handle making moves
* Should be able to handle deleting games
* Should properly interact withAs a user, I would like to be able to create and play games (1) database(s)
* Should be able to return a list of viewable games
#### Logic:
* Should be able to handle game creation
* Should be able to handle giving valid moves
* Should be able to handle making a move
* Should be able to handle winning/losing

### Associated Tasks 
* #110 GameServer create game API support **(2)**
* #111 GameServer move API support **(1)**
* #112 GameServer view game API support **(2)**
* #113 Database support of game creation **(2)**
* #114 Database support of game piece moves **(2)**
* #122 GameServer handle Create game **(2)**
* #123 GameServer handle view game **(2)**
* #124 GameServer handle move piece **(2)**
* #127 Implement game creation in GameLogic **(3)**
* #129 Implement logic to handle making move and changing turns in GameLogic **(3)**
* #130 Implement back-end logic to handle updating valid moves in GameLogic **(5)**
* #131 Implement handling win condition detection in GameLogic **(3)**
* #163 As a user I want the game to be up to date as soon as I visit the Games page **(2)**
* #20 Implement move validity detection **(2)**
* #22 Handle game logic POST requests **(2)**
* #23 Client game board **(3)**
* #31 Create a front end interface to view and interact with the game **(3)**
* #42 Create a game board with buttons that can make requests and update app state based on responses **(5)**
* #84 Refactor back-end game logic into distinct classes for easier front-end data digestion **(Not estimated)**
* #89 Display Piece, Environment, and available move data **(8)**


## As a user, I would like to know when no moves are possible (1)
### Acceptance Criteria:
#### Logic:
* Count the number of available legal moves a player has
* End the game if no moves are available for a player on their turn
### Associated Tasks
* #133
End the game if no moves are available **(1)**
* #75
Implement method of detecting when no legal moves are available for the current turn's player **(5)**
## As a user, I would like to create/delete and login/logout of my user account (2)
### Acceptance Criteria:
#### Client:
* Send in user info to register an account
* Send in user credentials to log in to an existing account
* Log out of an account
* Delete an existing account
#### Server:
* Handle user creation
* Handle user deletion
* Handle login requests
* Handle logout requests

### Associated Tasks
* #132 Client handle registering a user **(2)**
* #134 Client handle user unregistration **(1)**
* #136 GameServer handle user unregistration **(2)**
* #137 GameServer handle propagation of user unregistration to games **(1)**
* #138 Database handle propagation of game status whenever a user unregisters **(2)**
* #116 GameServer login API support **(2)**
* #118 Database support for user authentication **(2)**
* #119 GameServer register user API support **(2)**
* #120 GameServer unregister user API support **(2)**
* #135 GameServer handle user registration **(2)**
* #166 Give user feedback on failed login **(2)**
* #24 Login/Logout **(3)**
* #39 Refactored Server, added classes, handle user auth **(8)**

## As a user, I would like to be able to send and accept invites (3)

### Acceptance Criteria:
#### Client:
* Should be able to send an invitation to another user.
* Should be able to accept or decline an invitation.
* Should be able to view incoming and outgoing invites.
* Should be able to cancel and invitation sent.
#### Server:
* Should be able to handle requests for invitations sent.
* Should be able to handle requests for invitations accepted/declined.
* Should be able to handle requests for viewing incoming and outgoing invitations.
* Should be able to handle requests for invitations cancelled.

### Associated Tasks
* #104 GameServer handle create invite **(1)**
* #105 GameServer cancel invite API support **(1)**
* #106 GameServer accept invite API support **(1)**
* #107 GameServer view invites API support **(1)**
* #108 Database invite support **(2)**
* #109 Client support of Invite functionality **(Not estimated)**
* #110 GameServer create game API support **(2)**
