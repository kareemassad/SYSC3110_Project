# SYSC 3110 Project: Uno Flip - Milestone 2

## Project description
The SYSC 3110 project is to basically create a simplified version of the card game Uno Flip. The simplified version would work identical to the original but with 
lesser functionalities. In Milestone 2, the game is developed as GUI-based, allowing players to engage through a graphical display and use of mouse input.

## Project members and authors
Our group, Group #11, consists of 4 members: Mehedi Mostofa, Rayat Md Kibria, Evan Baldwin and Kareem El Assad. The contributions of the members are listed below:

| Member | Student number | Contributions                                    |
|--------|----------------|--------------------------------------------------|
| Mehedi | 101154128      | (add stuff) |
| Rayat  | 101151001      | UnoModelTest, README                                |
| Evan   | 101222276      |                    |
| Kareem | 101107739      |         |

## How to run
Options:
1) Run the main method within `Uno.java` and interact with the game in the pop-up window.
2) Run the jar through the terminal.
   1. Make sure you are in the SYSC3110_Project directory.
   2. Run the following command `java -jar SYSC3110_Project.jar`

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

- UnoModel.java:
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

- UnoView.java:
	- Location: SYSC3110_Project/src
	- Description: JFrame class for interacting with the game.

- UnoViewFrame.java:
	- Location: SYSC3110_Project/src
	- Description: Interface for UnoView.

- UnoController.java:
	- Location: SYSC3110_Project/src
	- Description: Controller for interacting with View.

- UnoModelTest.java:
	- Location: SYSC3110_Project/src
	- Description: Junit test file for UnoModel.

- /Diagrams/Milestone {Milestone Number}/Class Diagrams/
	- Description: UML class diagram depicting the different relations between the classes.
	
- /Diagrams/Milestone {Milestone Number}/Sequence Diagrams/
	- Description: UML sequence diagram depicting the interactions between the objects.


##  Data structure explanation

- Utilized an `ArrayList` to manage a collection of `Player` and `Card` objects in `Deck` and `Player` classes.
- Used an `Enum` to store Types and Colors in the `Card` class.
- (add data structures from view and controller)

### Design consideration

- We are considering using a Doubly Circular Linked List for the `Player` collection. 
  - Pros:
    - Good for reversal operations
    - Good game loop traversal 
  - Cons:
    - More complex than an ArrayList 
    - Worse access than ArrayLists

### Changes to structure and UML

- (fill with data structure and UML changes)

## Known issues:

- Flip Mechanism is unimplemented, deferred to Milestone 3.
