import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by gastonsantoalla on 30/10/16.
  */
class DataProcessingSpec extends FreeSpec with Matchers {

  "In the DataProcessingPart Clefia" - {
    val roundKeys = Array(0xfbd9678fL, 0x97f8384cL, 0x91fdb3c7L, 0xfddc1c26L, 0xa4efd9e3L, 0xc8ce0e13L, 0xbe66ecf1L, 0xd2478709L, 0x673a5e48L, 0x0b1bdbd0L, 0x0b948714L, 0x67b575bcL)
    val text = (0x7ca565a7L, 0x304bf0aaL, 0x5c6aaa87L, 0xf4347855L)
    val whitheningKeys = (0x0b948714L, 0x67b575bcL, 0x3dc3ebbaL, 0x51e2228aL)

    "should encript" - {
      "4 longs in 6 rounds" in {
        DataProcessing.enc(text, whitheningKeys, roundKeys, 6) should be ((67455211L, 787557980L, 2504251662L, 3578610116L))
      }
    }

    "should decript" - {
      "4 longs" in {
        DataProcessing.dec(text, whitheningKeys, roundKeys, 6) should be ((3139536795L, 2914691108L, 4171539034L, 798159417L))
      }
    }

    "assure encript and decript are inverse functions" - {
      "4 longs" in {
        DataProcessing.dec(DataProcessing.enc(text, whitheningKeys, roundKeys, 6), whitheningKeys, roundKeys, 6) should be (text)
      }
    }
  }
}
