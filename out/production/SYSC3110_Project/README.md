# SYSC 3110 Project: Uno Flip - Milestone 1

## Project description
The SYSC 3110 project is to basically create a simplified version of the card game Uno Flip. The simplified version would work identical to the original but with 
lesser functionalities. In Milestone 1, the game is developed as text-based, allowing players to engage through the console using keyboard input.

## Project members and authors
Our group, Group #11, consists of 4 members: Mehedi Mostofa, Rayat Md Kibria, Evan Baldwin and Kareem El Assad. The contributions of the memebers are listed below:

| Member | Student number | Contributions                                    |
|--------|----------------|--------------------------------------------------|
| Mehedi | 101154128      | Player, UML, Unit tests,Java Docs, Class diagram |
| Rayat  | 101151001      | Card , Unit tests                                |
| Evan   | 101222276      | Deck , Score, Sequence diagram                   |
| Kareem | 101107739      | Uno , WildCards, Design Decisions, README        |

## How to Run
Options:
1) Run the main method within `Uno.java` and interact with the game in the console.
2) 

## Deliverables
The following is a list of deliverable that were required in the milestone:

- Card.java:
	- Location: SYSC3110_Project/src
	- Description: Card class represents the number and special cards. 

- Deck.java:
	- Location: SYSC3110_Project/src
	- Description: Deck class builds itself and draws cards.

- Player.java:
	- Location: SYSC3110_Project/src
	- Description: Player class represents each player, and their hand and scores.

- Uno.java:
	- Location: SYSC3110_Project/src
	- Description: Uno class is the main class for game functionality and running. 
	
- CardTest.java:
	- Location: SYSC3110_Project/src
	- Description: Junit test file for Card.	

- DeckTest.java:
	- Location: SYSC3110_Project/src
	- Description: Junit test file for Deck.

- PlayerTest.java:
	- Location: SYSC3110_Project/src
	- Description: Junit test file for Player.

- UnoTest.java:
	- Location: SYSC3110_Project/src
	- Description: Junit test file for Uno.

- /Diagrams/Milestone {Milestone Number}/Class Diagrams/
	- Description: UML class diagram depicting the different relations between the classes.
	
- /Diagrams/Milestone {Milestone Number}/Sequence Diagrams/
	- Description: UML sequence diagram depicting the interactions between the objects.


##  Data Structure Explanation

- Utilized an `ArrayList` to manage a collection of `Player` and `Card` objects in `Deck` and `Player` classes.
- Used an `Enum` to store Types and Colors in the `Card` class.

### Design Consideration

- We are considering using a Doubly Circular Linked List for the `Player` collection. 
  - Pros:
    - Good for reversal operations
    - Good game loop traversal 
  - Cons:
    - More complex than an ArrayList 
    - Worse access than ArrayLists

## Known issues:

- Flip Mechanism is unimplemented, deferred to Milestone 2.