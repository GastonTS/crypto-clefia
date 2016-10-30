import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by gastonsantoalla on 29/10/16.
  */
class ClefiaSpec extends FreeSpec with Matchers {

  "Clefia object" -{

    "should handle bytes" - {

      "getting bytes" in {
        Clefia.getBytes(37162340L) should be (Array(2,55,13,100))
      }

      "concatenating bytes" in {
        Clefia.concatBytes(Array(1, 69, 51, 56)) should be (21312312L)
      }

      "concat should be inverse of get" in {
        Clefia.getBytes(Clefia.concatBytes(Array(11, 52, 255, 200))) should be (Array(11, 52, 255, 200))
        Clefia.concatBytes(Clefia.getBytes(393829L)) should be (393829L)
      }

    }

    "should multiply over GF(2^8)" - {

      "two numbers" in {
        Clefia.mult(22, 2) should be (44)
      }

      "even with overflow" in {
        Clefia.mult(220, 200) should be (133)
      }

    }

    "should multiply square matrix" - {
      "against vectors" in {
        Clefia.squareMatrixXVector(Clefia.M0, Array(0xa1, 0x47, 0x34, 0x5a)) should be(Vector(62, 213, 177, 210))
      }
    }

      "should get TValues" - {
      "from 3000677607 and 3851680404" in {
        Clefia.getTValues(3000677607L, 3851680404L) should be (Array(87, 73, 78, 115))
      }
    }

    "should apply f0 correctly" - {
      "from 3000677607 and 3851680404" in {
        Clefia.f0(3000677607L, 3851680404L) should be (24698974L)
      }
    }

    "should apply f1 correctly" - {
      "from 3000677607 and 3851680404" in {
        Clefia.f1(3000677607L, 3851680404L) should be (4096204530L)
      }
    }

    "should doubleSwap" - {
      "a 128 string correctly" in {
        Clefia.doubleSwap("11111112222222222222222222222222222222222222222222222222222222223333333333333333333333333333333333333333333333333333333337777777") should be("22222222222222222222222222222222222222222222222222222222277777771111111333333333333333333333333333333333333333333333333333333333")
      }
    }
  }
}
