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
```

## Dependencies

All development and testing activities were carried out on Linux using version 1.8.0_101 of the Java OpenJDK Runtime Environment.

## Code metrics

* CLOC

```sh
github.com/AlDanial/cloc v 1.70  T=0.09 s (11.3 files/s, 6294.4 lines/s)
-------------------------------------------------------------------------------
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
Java                             1             77            202            279
-------------------------------------------------------------------------------
```

* SLOCCount

```sh
SLOC  Directory   SLOC-by-Language (Sorted)
279   top_dir     java=279

Totals grouped by language (dominant language first):
java:           279 (100.00%)

Total Physical Source Lines of Code (SLOC)                = 279
Development Effort Estimate, Person-Years (Person-Months) = 0.05 (0.63)
 (Basic COCOMO model, Person-Months = 2.4 * (KSLOC**1.05))
Schedule Estimate, Years (Months)                         = 0.17 (2.10)
 (Basic COCOMO model, Months = 2.5 * (person-months**0.38))
Estimated Average Number of Developers (Effort/Schedule)  = 0.30
Total Estimated Cost to Develop                           = $ 1,841
 (average salary = $58,598/year, overhead = 0.60).

SLOCCount, Copyright (C) 2001-2004 David A. Wheeler
```

## License

Copyright (c) 2014-2016 Eduardo Ferreira

The code in this repository is MIT licensed, and therefore free to use as you please for commercial or non-commercial purposes (see [LICENSE](LICENSE) for details).
