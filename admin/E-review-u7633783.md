## Code Review

Reviewed by: Linxiang Hu, u7633783

Reviewing code written by: Lize van der Walt, u7679626 

Component: Player.class and  Mercant.class

### Comments 
There are two points I think can be promoted.

The first point is very common in Liza's code. I will take Play.class as an example.
 The Player has many attributes such as coins, rugs and so on. The problem is that 
she does not make these attributes public or write getter and setter methods for these
attributes, which causes some troubles when I am writing Viewer.class and wants 
to get some attributes of player.

Moreover, for the decoding method of GameString, it is better to make these methods static so that I can call these methods using
"ClassName.MethodName" format rather than creating many instances. 

Other aspects are excellent and 
have brought great inspiration to our team. 
Especially with a high aesthetic, for example, the chessboard she drew is really much more beautiful than the one I drew

