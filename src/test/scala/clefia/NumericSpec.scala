package clefia

import clefia.Numeric._
import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by gastonsantoalla on 30/10/16.
  */
class NumericSpec extends FreeSpec with Matchers {

  "Numeric" - {

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

    "should add byte ops to Int" - {
      "getting bytes" in {
        37162340.getBytes should be ((2,55,13,100))
      }

      "getting chars from Int" in {
        7274603.getChars should be (Array('o', 'k'))
      }
    }

    "should convert from a String" - {

      "into Int Array" in {
        "Sarlomp!".toIntArray should be (Array(5439585, 7471212, 7274605, 7340065))
      }

      "into Numeric128" in {
        "Mr. Sarlompiting".toNumeric128 should be ((5046386,3014688,5439585,7471212))
      }

      "into Numeric192" in {
        "Sarlomp es buen hombre!!".toNumeric192 should be ((5439585,7471212,7274605,7340064,6619251,2097250))
      }

      "into Numeric256" in {
        "Mr. Sarlomp dominara el mundo!!!".toNumeric256 should be ((5046386,3014688,5439585,7471212,7274605,7340064,6553711,7143529))
      }

      "into Numeric and backwards" in {
        "Sarlomp!".toNumeric128.toRealString should be ("Sarlomp!")
      }

      "into Numerci128 blocks" in {
        "Sarlomp!".toNumeric128Blocks should be (List((5439585,7471212,7274605,7340065)))
      }
    }

    "should convert from a Short Array" - {
      "into Numeric32" in {
        Array(45, 11, 92, 0).map(_.toShort).toNumeric32 should be ((45, 11, 92, 0))
      }
    }

    "should convert from Int Array" - {
      "into Numeric128" in {
        Array(150, 22, 23, 42).toNumeric128 should be ((150, 22, 23, 42))
      }

      "into Numeric192" in {
        Array(3, 823, 2938, 33, 10293, 2222).toNumeric192 should be ((3, 823, 2938, 33, 10293, 2222))
      }

      "into Numeric256" in {
        Array(204, 385, 29, 2038, 49548, 11, 495, 395).toNumeric256 should be ((204, 385, 29, 2038, 49548, 11, 495, 395))
      }

      "into Int from chars" in {
        Array(347564, 29284).concatChars should be (1303147108)
      }
    }

    "should conver Numeric32" - {

      "into a Short Array" in {
        (254, 2, 9, 77).toArray should be (Array(254, 2, 9, 77))
      }

      "into an Int concatenating bytes" in {
        val n: Numeric32 = (1, 69, 51, 56)
        n.concatBytes should be (21312312)
      }

      "into an Int and backwards" in {
        val n: Numeric32 = (11, 52, 255, 200)
        n.concatBytes.getBytes should be (n)
        393829.getBytes.concatBytes should be (393829)
      }
    }

    "should add Numeric128 operations" - {
      "xor between Numeric128" in {
        (5439585, 7471212, 7274605, 7340065) ^ (3, 823, 2938, 33) should be ((5439586,7471963,7277335,7340032))
      }

      "convert into Array" in {
        (394, 3, 4245, 323).toArray should be(Array(394, 3, 4245, 323))
      }

      "convert into real string" in {
        (5439585,7471212,7274605,7340065).toRealString should be ("Sarlomp!")
      }

      "convert into Numeric256" in {
        (54, 293, 4, 293245).concat((3334, 13131, 234, -43)) should be ((54, 293, 4, 293245, 3334, 13131, 234, -43))
      }
    }

    "should add Numeric256 operations" - {

      "convert into Array" in {
        (23, 3456, 36, -245, 3356, 456, 77, -345).toArray should be (Array(23, 3456, 36, -245, 3356, 456, 77, -345))
      }

      "get the first 128" in {
        (23, 3456, 36, -245, 3356, 456, 77, -345).first128 should be ((23, 3456, 36, -245))
      }

      "get the last 128" in {
        (23, 3456, 36, -245, 3356, 456, 77, -345).last128 should be ((3356, 456, 77, -345))
      }

      "split and backwards" in {
        val n = (23, 3456, 36, -245, 3356, 456, 77, -345)
        n.first128.concat(n.last128) should be (n)
      }
    }
  }
}
