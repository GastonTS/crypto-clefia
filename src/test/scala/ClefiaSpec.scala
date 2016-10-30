import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by gastonsantoalla on 29/10/16.
  */
class ClefiaSpec extends FreeSpec with Matchers {

  "Clefia object" -{

    "should split into 4" - {

      "a 4 char string" in {
        Clefia.splitInto4("1234") should be (List("1", "2", "3", "4"))
      }

      "a 32 char string" in {
        Clefia.splitInto4("abcdefghijklmnñopqrstuvwxyz12345") should be (List("abcdefgh", "ijklmnño", "pqrstuvw", "xyz12345"))
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
      "from 10110010110110101011010011100111 and 11100101100100111111101010010100" in {
        Clefia.getTValues("10110010110110101011010011100111", "11100101100100111111101010010100") should be (Array(87, 73, 78, 115))
      }
    }

    "should apply f0 correctly" - {
      "from 10110010110110101011010011100111 and 11100101100100111111101010010100" in {
        Clefia.f0("10110010110110101011010011100111", "11100101100100111111101010010100") should be (24698974)
      }
    }

    "should apply f1 correctly" - {
      "from 10110010110110101011010011100111 and 11100101100100111111101010010100" in {
        Clefia.f1("10110010110110101011010011100111", "11100101100100111111101010010100") should be (4096204530L)
      }
    }

    "should doubleSwap" - {
      "a 128 string correctly" in {
        Clefia.doubleSwap("11111112222222222222222222222222222222222222222222222222222222223333333333333333333333333333333333333333333333333333333337777777") should be("22222222222222222222222222222222222222222222222222222222277777771111111333333333333333333333333333333333333333333333333333333333")
      }
    }
  }
}
