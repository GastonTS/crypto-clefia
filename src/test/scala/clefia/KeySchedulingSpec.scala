package clefia

import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by gastonsantoalla on 30/10/16.
  */
class KeySchedulingSpec extends FreeSpec with Matchers {

  "In the KeySchedulingPart Clefia" - {
    "should doubleSwap" - {
      "a 128 string correctly" in {
        KeyScheduling.doubleSwap("11111112222222222222222222222222222222222222222222222222222222223333333333333333333333333333333333333333333333333333333337777777") should be("22222222222222222222222222222222222222222222222222222222277777771111111333333333333333333333333333333333333333333333333333333333")
      }
    }
  }
}
