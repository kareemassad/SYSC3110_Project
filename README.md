# SYSC 3110 Project: Uno Flip - Milestone 4

## Project description
The SYSC 3110 project is to basically create a simplified version of the card game Uno Flip. The simplified version would work identical to the original but with 
lesser functionalities. In Milestone 4, the game is developed as GUI-based, with remaining additional features such as undo/redo, restarting the game and save/loading the game.

## Project members and authors
Our group, Group #11, consists of 4 members: Mehedi Mostofa, Rayat Md Kibria, Evan Baldwin and Kareem El Assad. The contributions of the members are listed below:

| Member | Student number | Contributions                                                                       |
|--------|----------------|-------------------------------------------------------------------------------------|
| Mehedi | 101154128      | Serialize/Deserialize, UnoModel, UnoViewFrame, UnoController, UMLClass |
| Rayat  | 101151001      | Serialize, Deserialize, UnoModel, UnoViewFrame, Tests                                       |
| Evan   | 101222276      | Undo/Redo, UnoController, UnoModel, UnoViewFrame, GUI refactoring  |
| Kareem | 101107739      | Replaying, UnoModel, UnoViewFrame                                                                          |

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

- test.AITest.java:
	- Location: SYSC3110_Project/src
	- Description: Junit test file for model.AI.

- /Diagrams/Milestone {Milestone Number}/Class Diagrams/
	- Description: UML class diagram depicting the different relations between the classes.


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
      
Our UNO game application is designed to support easy extension and modification without disrupting existing modules. Follow these guidelines:

-Encapsulate Changes: Kept modifications localized within separate classes or methods to avoid affecting the fundamental logic.This ensures high cohesion due to modularity.

-Utilize Interfaces and Inheritance: Use interfaces for new functionalities and inheritance for extending behaviors, ensuring compatibility with existing components. This ensures loose coupling between classes.

-Unit Testing: Develop unit tests for new functionalities or modified behaviors to maintain stability.

### Changes to structure and UML

- No significant changes in the UML diagrams as only a few methods were added.
- There were no particular changes in data structures either.

## Known issues:
- Save and load works in GUi as no errors show, but it doesn't save the right stuff.
