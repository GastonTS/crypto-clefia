package clefia

import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by gastonsantoalla on 30/10/16.
  */
class DataProcessingSpec extends FreeSpec with Matchers {

  "In the DataProcessingPart Clefia" - {
    val keys = Keys((0x0b948714, 0x67b575bc, 0x3dc3ebba, 0x51e2228a), Array(0xfbd9678f, 0x97f8384c, 0x91fdb3c7, 0xfddc1c26, 0xa4efd9e3, 0xc8ce0e13, 0xbe66ecf1, 0xd2478709, 0x673a5e48, 0x0b1bdbd0, 0x0b948714, 0x67b575bc))
    val text = (0x7ca565a7, 0x304bf0aa, 0x5c6aaa87, 0xf4347855)

    "should encrypt" - {
      "4 longs in 6 rounds" in {
        DataProcessing.enc(text, keys, 6) should be ((67455211,787557980,-1790715634,-716357180))
      }
    }

    "should decrypt" - {
      "4 longs" in {
        DataProcessing.dec(text, keys, 6) should be ((-1155430501, -1380276188, -123428262, 798159417))
      }
    }

    "assure encrypt and decrypt are inverse functions" - {
      "4 longs" in {
        DataProcessing.dec(DataProcessing.enc(text, keys, 6), keys, 6) should be (text)
      }
    }
  }
}
