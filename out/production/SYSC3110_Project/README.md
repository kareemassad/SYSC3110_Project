# SYSC 3110 Project: Uno Flip - Milestone 3

## Project description
The SYSC 3110 project is to basically create a simplified version of the card game Uno Flip. The simplified version would work identical to the original but with 
lesser functionalities. In Milestone 3, the game is developed as GUI-based, with added features sucha as AI players and Flip mechanism.

## Project members and authors
Our group, Group #11, consists of 4 members: Mehedi Mostofa, Rayat Md Kibria, Evan Baldwin and Kareem El Assad. The contributions of the members are listed below:

| Member | Student number | Contributions                                                       |
|--------|----------------|---------------------------------------------------------------------|
| Mehedi | 101154128      | (add)                                                               |
| Rayat  | 101151001      | Card, Deck, Player, test.UnoModelTest, README                       |
| Evan   | 101222276      | Flip implementation (Card, UnoModel, UnoViewFrame), GUI refactoring |
| Kareem | 101107739      | AI Players                                                          |

## How to run
Options:
1) Run the main method within `Uno.java` and interact with the game in the pop-up window.
2) Run the jar through the terminal.
   1. Make sure you are in the SYSC3110_Project directory.
   2. Run the following command `java -jar SYSC3110_Project.jar`

## Deliverables
The following is a list of deliverable that were required in the milestone:

- model.Card.java:
	- Location: SYSC3110_Project/src
	- Description: model.Card class represents the number and special cards. 

- model.Deck.java:
	- Location: SYSC3110_Project/src
	- Description: model.Deck class builds itself and draws cards.

- model.Player.java:
	- Location: SYSC3110_Project/src
	- Description: model.Player class represents each player, and their hand and scores.

- model.UnoModel.java:
	- Location: SYSC3110_Project/src
	- Description: Uno class is the main class for game functionality and running. 
	
- test.CardTest.java:
	- Location: SYSC3110_Project/src
	- Description: Junit test file for model.Card.	

- test.DeckTest.java:
	- Location: SYSC3110_Project/src
	- Description: Junit test file for model.Deck.

- test.PlayerTest.java:
	- Location: SYSC3110_Project/src
	- Description: Junit test file for model.Player.

- view.UnoViewFrame.java:
	- Location: SYSC3110_Project/src
	- Description: JFrame class for interacting with the game.

- view.UnoView.java:
	- Location: SYSC3110_Project/src
	- Description: Interface for view.UnoViewFrame.

- controller.UnoController.java:
	- Location: SYSC3110_Project/src
	- Description: Controller for interacting with View.

- test.UnoModelTest.java:
	- Location: SYSC3110_Project/src
	- Description: Junit test file for model.UnoModel.

- model.AI.java:
	- Location: SYSC3110_Project/src
	- Description: class for implementing AI players.

- /Diagrams/Milestone {Milestone Number}/Class Diagrams/
	- Description: UML class diagram depicting the different relations between the classes.
	
- /Diagrams/Milestone {Milestone Number}/Sequence Diagrams/
	- Description: UML sequence diagram depicting the interactions between the objects.


##  Data structure explanation

- Utilized an `ArrayList` to manage a collection of `model.Player` and `model.Card` objects in `model.Deck` and `model.Player` classes.
- Used an `Enum` to store Types and Colors in the `model.Card` class.
- Used `ArrayList` to manage views inside the model.

### Design consideration

- We are considering using a Doubly Circular Linked List for the `model.Player` collection. 
  - Pros:
    - Good for reversal operations
    - Good game loop traversal 
  - Cons:
    - More complex than an ArrayList 
    - Worse access than ArrayLists

### Changes to structure and UML

- We included the model, view, controller along with the previous classes in the UML class diagram. No changes were made in sequence as the logic remained same.
- There were no particular changes in data structures either.


## Known issues:
