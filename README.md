Tic-tac-toe
===========

A simple implementation of the good old Tic-tac-toe game.
For more information on the game, please see: http://en.wikipedia.org/wiki/Tic_tac_toe

## Instructions

To run the game, just compile the source code and run it as any run-of-the-mill Java program:

```sh
$ javac TicTacToe.java
$ java TicTacToe
```

## Examples

Here's an example run between the program and a human player:

```sh
Computer says: (3,3)

   |   |   
---+---+---
   |   |   
---+---+---
   |   | X 


Please insert a valid pair of coordinates: 1 1
Puny human played: (1,1)

 ○ |   |   
---+---+---
   |   |   
---+---+---
   |   | X 


Computer says: (2,3)

 ○ |   |   
---+---+---
   |   | X 
---+---+---
   |   | X 


Please insert a valid pair of coordinates: 1 3
Puny human played: (1,3)

 ○ |   | ○ 
---+---+---
   |   | X 
---+---+---
   |   | X 


Computer says: (1,2)

 ○ | X | ○ 
---+---+---
   |   | X 
---+---+---
   |   | X 


Please insert a valid pair of coordinates: 3 1
Puny human played: (3,1)

 ○ | X | ○ 
---+---+---
   |   | X 
---+---+---
 ○ |   | X 


Computer says: (2,2)

 ○ | X | ○ 
---+---+---
   | X | X 
---+---+---
 ○ |   | X 


Please insert a valid pair of coordinates: 2 1
Puny human played: (2,1)

 ○ | X | ○ 
---+---+---
 ○ | X | X 
---+---+---
 ○ |   | X 


Game over! Humans rule!
```

## Dependencies

All development and testing activities were carried out on Linux using version 1.7.0_55 of the Java OpenJDK Runtime Environment.

## Code metrics

* CLOC

```sh
http://cloc.sourceforge.net v 1.62  T=0.09 s (11.7 files/s, 6300.9 lines/s)
-------------------------------------------------------------------------------
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
Java                             1             75            192            273
-------------------------------------------------------------------------------
```

* SLOCCount

```sh
SLOC	Directory	SLOC-by-Language (Sorted)
273     top_dir         java=273

Totals grouped by language (dominant language first):
java:           273 (100.00%)

Total Physical Source Lines of Code (SLOC)                = 273
Development Effort Estimate, Person-Years (Person-Months) = 0.05 (0.61)
 (Basic COCOMO model, Person-Months = 2.4 * (KSLOC**1.05))
Schedule Estimate, Years (Months)                         = 0.17 (2.08)
 (Basic COCOMO model, Months = 2.5 * (person-months**0.38))
Estimated Average Number of Developers (Effort/Schedule)  = 0.30
Total Estimated Cost to Develop                           = $ 6,912
 (average salary = $56,286/year, overhead = 2.40).
```

## License

Copyright (c) 2014 Eduardo Ferreira

The code in this repository is MIT licensed, and therefore free to use as you please for commercial or non-commercial purposes (see [LICENSE](LICENSE) for details).
