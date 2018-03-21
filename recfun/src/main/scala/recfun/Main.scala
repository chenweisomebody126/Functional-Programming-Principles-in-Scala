package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = {
      if ((c == 0) || (c == r)) 1
      else pascal(c-1, r-1) + pascal(c, r-1)
    }
  
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {
      def isBalance(chars: List[Char], num_l : Int, num_r : Int): Boolean = {
        if (chars.isEmpty)
          num_l == num_r
        else if (chars.head.equals('(') )
          isBalance(chars.tail, num_l+1, num_r)
        else if (chars.head.equals(')')) {
          if (num_l - num_r - 1 < 0)
            false
          else
            isBalance(chars.tail, num_l, num_r+1)
        }
        else
          isBalance(chars.tail, num_l, num_r)
      }
      isBalance(chars, 0, 0)
    }
  
  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = {
      if (money < 0 || coins.isEmpty) 0
      else if (money == 0)  1
      else countChange(money, coins.tail) + countChange(money- coins.head, coins)
    }
  }
