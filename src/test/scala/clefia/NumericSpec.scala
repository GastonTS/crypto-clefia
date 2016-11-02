package clefia

import clefia.Numeric._
import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by gastonsantoalla on 30/10/16.
  */
class NumericSpec extends FreeSpec with Matchers{
  "should handle bytes" - {
    "getting bytes" in {
      37162340.getBytes should be ((2,55,13,100))
    }

    "concatenating bytes" in {
      Array(1, 69, 51, 56).map(_.toShort).concatBytes should be (21312312)
    }

    "concat should be inverse of get" in {
      Array(11, 52, 255, 200).map(_.toShort).concatBytes.getBytes should be ((11, 52, 255, 200))
      393829.getBytes.toArray.concatBytes should be (393829)
    }
  }

  "should multiply over GF(2^8)" - {
    "two numbers" in {
      22.gf8Mult(2) should be (44)
    }

    "even with overflow" in {
      220.gf8Mult(200) should be (133)
    }
  }

  "should multiply square matrix" - {
    "against vectors" in {
      GFN.M0.squareMatrixXVector(Array(0xa1, 0x47, 0x34, 0x5a)) should be(Array(62, 213, 177, 210))
    }
  }
}
