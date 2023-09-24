
# Test plan

## List of classes

* List below all classes in your implementation that should have unit tests.
* For each class, list methods that can be tested in isolation.
* For each class, if there are conditions on the class' behaviour that cannot
  be tested by calling one method in isolation, give at least one example of
  a test for such a condition.

Do **not** include in your test plan the `Marrakech` class or the predefined
static methods for which we have already provided unit tests.

* Board Class, methods to be unit tested:
  * withInBoard(IntPair intPair)
  
* IntPair Class, methods to be unit tested:
  * add(IntPair other)
  
* Merchant Class, methods to be unit tested: 
  * decodeAsamString(String asamString)
  * Rotate(Direction d, int rotateValue)
  * Move(Direction d, int steps)
  
* Player Class, methods to be unit tested: 
  * decodePlayerString(String playerString)
  * decodeColour(String colour)
  
* Rug Class, methods to be unit tested: 
  * checkShareBoarder()
  * ifPlaceCorrectly()
  * ifOverlap()
  
