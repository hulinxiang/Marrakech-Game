
# Test plan

## List of classes

* List below all classes in your implementation that should have unit tests.
* For each class, list methods that can be tested in isolation.
* For each class, if there are conditions on the class' behaviour that cannot
  be tested by calling one method in isolation, give at least one example of
  a test for such a condition.

Do **not** include in your test plan the `Marrakech` class or the predefined
static methods for which we have already provided unit tests.

* **Board Class**, methods to be unit tested:
  * withInBoard(IntPair intPair)
  * While boardSetUp cannot be tested in isolation,after calling boardSetUp, the value (in the implemented code)
    returned by checkBoard should be true.  
  
* **IntPair Class,** methods to be unit tested:
  * add(IntPair other)
  
* **Merchant Class**, methods to be unit tested:
  * While the method decodeAsamString(String asamString) cannot simply be tested by checking its return value (since it
    does not return a value), Asam's position and rotation in the Marrakech class should be set as specified by 
    the string after calling the method. 
  * Similarly, the following methods: 
    * Rotate(Direction d, int rotateValue)
    * Move(Direction d, int steps)
    cannot be tested in isolation. However, after calling the methods above, the string representation of Asam's state
    returned by rotateAssam() and moveAsam() in the Marrakech class should be the correct representation. 
  
* **Player Class,** methods to be unit tested: 
  * rugCount()
  * moneyCheck()
  * rugCheck()
  * Connvected()
  * Score()
  * The methods makePayment() and getPayment() may be tested in isolation by checking that the this.coins field 
    is updated correctly. 
  * While placeRug() cannot be tested in isolation, the integer returned by rugCount should decrease by one
    since the rug has been placed and can no longer be used. This method can further be checked by confirming that the
    integer returned by the placedNumber method in the Marreakech class has increased by one. 
  * The methods decodePlayerString(String playerString) and decodeColour(String colour) can be tested indirectly
    through checking that the integer value for the number of players returned by the method countPlayers() in the 
    Marrakech class matches the number of players specified by the string. Individual fields in the Marrakech
    class such as 'players' can also be checked for the correct value. 
  
* **Rug Class**, methods to be unit tested: 
  * checkShareBoarder()
  * ifPlaceCorrectly()
  * ifOverlap()
  
* NOTE: Dice, Tile classes do not require unit tests, either because there is no need for one or because a test
 is already provided. 
  
