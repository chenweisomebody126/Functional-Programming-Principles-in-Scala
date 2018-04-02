package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
   test("string take") {
     val message = "hello, world"
     assert(message.take(5) == "hello")
   }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
   test("adding ints") {
     assert(3 + 2 === 5)
   }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect contains intersection of two sets"){
    new TestSets {
      val uni1 = union(s1, s2)
      val uni2 = union(s2, s3)
      val s = intersect(uni1, uni2)
      assert(contains(s, 2), "Intersect (1,2) and (2,3)")
    }
  }

  test("diff contains the elements in the first but not second"){
    new TestSets {
      val uni1 = union(s1, s2)
      val uni2 = union(s2, s3)
      val s = diff(uni1, uni2)
      assert(contains(s, 1) && !contains(s, 2), "Diff (1,2) and (2,3)" )
    }
  }

  test("filter contains elements satisfying p" ){
    new TestSets {
      val s12 = union(s1,s2)
      val s123 = union(s12,s3)

      val s = filter(s123, (x: Int) => (x<2: Boolean))
      assert(contains(s, 1) && !contains(s, 2), "Filter {1,2,3} with condition <2")
    }
  }

  test("forall checks every element satisfying the condition p"){
    new TestSets {
      val s12 = union(s1,s2)
      val s123 = union(s12,s3)
      def smallerThan2 =  (x: Int) => (x < 2: Boolean)
      assert(!forall(s123,smallerThan2), "set {1,2,3} are not all smaller than 2")
      def smallerThan4 = (x: Int) => (x < 4: Boolean)
      printSet(s123)
      assert(forall(s123, smallerThan4), "set {1,2,3} are all smaller than 4")
    }
  }

  test("exist checks any element satisfying the condition p"){
    new TestSets {
      val s12 = union(s1,s2)
      val s123 = union(s12,s3)
      def largerThan2 = (x: Int) => (x > 2: Boolean)
      assert(exists(s123, largerThan2), "set {1,2,3} has element larger than 2")
      def largerThan3 = (x: Int) => (x > 3: Boolean)
      assert(!exists(s123, largerThan3), "set {1,2,3} has no element larger than 3")
    }
  }

  test("map does the mapping"){
    new TestSets {
      val s12 = union(s1,s2)
      val s123 = union(s12,s3)
      def doubleVal = (x: Int) => (2*x: Int)
      printSet(map(s123, doubleVal))
      println(FunSets.toString(map(s123, doubleVal)))
      assert(FunSets.toString(map(s123, doubleVal)).equals("{2,4,6}"), "double set {1, 2, 3} equals {2,4,6} ")
    }
  }


}
