# ConnectX

## Instructions

Are you familiar with the board game Connect4? Game consists of a slot with a 7x6 grid where players alternate in dropping tokens (red and yellow). Winner is the player that first manages to get a row, column or diagonal of successive tokens in his/her color.

Imagine that you are writing a program that will track this game for two players. Players take turns, and program only has to check if last player had won the game.

Only task we are concerned with here is to write a function that will be called after each turn to update the board data model state and see if last player had posted a winning token.

You are free to design the data model for the game and pick the language of your choice.

To summarize, we are looking for:

1. Data model representing the current state of the game

2. Implementation of the function that will be called after each game turn in order to update the model and check if we have a winner

    a. Accepts the column into which the piece has been placed

    b. Accepts the type of piece placed

    c. Accepts the current board object (if necessary)

    d. Returns true if last move is the winner, false otherwise

3. Unit tests that validate the logic of the function

Things to focus on:

1. Elegant and fast implementation (hint: can use recursion)

2. Clean and easy to read code (use comments and meaningful variable names)

3. Good tests


## Solution

The solution has been named ConnectX because it is based on the well-known game ConnectFour, but the implementation has parameterized dimensions for the game to also allow for some variants of the game.

To build it and run all the tests, simply type the following command.

```sh
mvn clean package
```

That's it!

Note: While the build produces a war file, there are no web components (yet). The main thing to see is the unit test console output. The intention was to create a web interface so multiple people can play against each other via a web browser. Perhaps in a future version this can be added :)

created by *[Erik Stenflo](https://github.com/stenflo)*
