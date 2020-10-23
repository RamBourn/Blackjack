# Blackjack  
My individual implementation of game Blackjack  
Execution:   
In the players vs players mode: the number of plays you enter exclude the dealer because the dealer is the computer,   
for example, you enter 3 players, so the game will be 3 players manipulated by human vs the dealer manipulated by the computer  
  
In the players vs players mode: the number of players you enter include the dealer. For example, you type in 5 players.  
one player of the five will be randomly chosen to be the dealer, and the left 4 players will remain normal gamblers  
  
After the initial two cards are distributed to the dealer and all players, then the player will finish the bet one by one.   
And during each player, all hands of the player will bet one by one. After all players finish operations. The dealer proceeds.  
  
  
Class Description:  
BlackJack: This class represents the procedure of BlackJack game  
Card: A class representing a single card with specific value and color  
CardHold: An class representing a bunch of objects which can hold cards. It is abstract implemented for extension  
Controller: main function is in it  
Dealer: A class representing the dealer of the game  
Deck: A class representing a group of cards which have 52 cards  
Hand: A hand of a person which can play the game separatly  
Player: A class representing a player of the game  
