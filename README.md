Tic-tac-toe
===========

A simple implementation of the good old Tic-tac-toe game.

## Instructions

To run the game, just compile the source code and run it as any run-of-the-mill
Java program:

    $ javac TicTacToe.java
    $ java TicTacToe

## Examples

Here's an example run between the program and a human player:

    Computer says: (1,1)

     ⨯ |   |   
    ---+---+---
       |   |   
    ---+---+---
       |   |   


    Please insert a valid pair of coordinates: 1 3
    Puny human played: (1,3)

     ⨯ |   | ○ 
    ---+---+---
       |   |   
    ---+---+---
       |   |   


    Computer says: (3,1)

     ⨯ |   | ○ 
    ---+---+---
       |   |   
    ---+---+---
     ⨯ |   |   


    Please insert a valid pair of coordinates: 2 1
    Puny human played: (2,1)

     ⨯ |   | ○ 
    ---+---+---
     ○ |   |   
    ---+---+---
     ⨯ |   |   


    Computer says: (3,3)

     ⨯ |   | ○ 
    ---+---+---
     ○ |   |   
    ---+---+---
     ⨯ |   | ⨯ 


    Please insert a valid pair of coordinates: 2 2
    Puny human played: (2,2)

     ⨯ |   | ○ 
    ---+---+---
     ○ | ○ |   
    ---+---+---
     ⨯ |   | ⨯ 


    Computer says: (3,2)

     ⨯ |   | ○ 
    ---+---+---
     ○ | ○ |   
    ---+---+---
     ⨯ | ⨯ | ⨯ 


    Game over! Bow before your new machine overlord!

## Dependencies

All development and testing activities were carried out on Linux using version
1.8.0_101 of the Java OpenJDK Runtime Environment.

## Code metrics

    github.com/AlDanial/cloc v 1.70  T=0.09 s (11.3 files/s, 6294.4 lines/s)
    -------------------------------------------------------------------------------
    Language                     files          blank        comment           code
    -------------------------------------------------------------------------------
    Java                             1             77            202            279
    -------------------------------------------------------------------------------

## License

Copyright (c) 2014-2019 Eduardo Ferreira

The code in this repository is MIT licensed, and therefore free to use as you
please for commercial or non-commercial purposes (see [LICENSE](LICENSE) for
details).
