# Online Weighted Matching with Delays

## Problem Statement
In this problem, we manage a 2 player game coordinator. Over time, a sequence of players (`Player`s) arrive desiring to be matched against other players. Each player has a known function (`matchPerception`) that reports their *dis-utility* of playing a given other player. These dis-utilities need not be symmetric.

The mechanism's (`MatchingAlgorithm`'s) role is to choose what matches to create and when; the mechanism is permitted to delay creation of matches between compatible players. In particular, a `MatchingAlgorithm` must specify what matches the mechanism would create of the players currently unmatched and when they would create them. The goal of the mechanism is to minimize the combined sum of 
1. The waiting time of each player between their arrival into the system and the start time of the match they are put in
2. Each player's perceived dis-utility of the match they are put in

## Files Included: 
* Player.java: This (abstract) class is used for representing players in a given `Instance`. Every player must implement the `matchPerception` function, which outputs their dis-utility of playing against a given other player.
* Match.java: This class holds the necessary information of a given Match, including the `Player`s involved and the match's starting time.
* Instance.java: This (abstract) class is used for creating instances of the problem. Any valid instance must define the `getNextPlayer` method, which returns the next `Player` to arrive in the coordinator.
* MatchingAlgorithm.java: This (abstract) class is used for denoting mechanisms for matching `Player`s. Any valid mechanism must implement the `matchesFromPool` method, which returns a list of matches to create from the unmatched `Player`s in the pool.
* [jgrapht-core-1.5.2.jar](jgrapht.org): Theoretically contains code for computing max-weight-matching (see problems below).
* GreedyMatching.java: An example of a matching algorithm for this problem. It's not good; it just matches everyone in the pool that it can, disregarding the players' `matchPerception`s. 
* Algorithm1.java: An algorithm we developed in class. It seems to be a bit better than Greedy, but unclear exactly how much better.

## Problems: 
* Currently, we don't have a way of computing the value of the Optimal solution. 
* We identified the problem of computing Opt as technically just being the equivalent of calculating the minimal-weight-perfect-matching, but doing so either means
* * Doing it ourselves (ugh)
* * Using some one else's. This was the purpose of relying on jgraph, but there seems to be an issue that currently eludes me. 