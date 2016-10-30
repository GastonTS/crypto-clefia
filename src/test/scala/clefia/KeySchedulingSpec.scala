package clefia

import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by gastonsantoalla on 30/10/16.
  */
class KeySchedulingSpec extends FreeSpec with Matchers {

  "In the KeySchedulingPart Clefia" - {
    "should doubleSwap" - {
      "a Long number correctly" in {
        KeyScheduling.doubleSwap((0x9302b639L, 0xff23e324L, 0x7188732cL, 0x1da969c6L)) should be ((2170232063L, 2448527942L, 2464354534L, 1480282835L))
      }
    }
  }
}
