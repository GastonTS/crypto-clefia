package clefia

import clefia.Numeric._
import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by gastonsantoalla on 31/10/16.
  */
class ClefiaSpec extends FreeSpec with Matchers {

  "Clefia" - {
    "should handle 128-bit keys" - {
      val key = (0xffeeddccL, 0xbbaa9988L, 0x77665544L, 0x33221100L)
      val plainText = (0x00010203L, 0x04050607L, 0x08090a0bL, 0x0c0d0e0fL)
      val cipherText = (0xde2bf2fdL, 0x9b74aacdL, 0xf1298555L, 0x459494fdL)

      "should encript" in {
        Clefia.encrypt(plainText, key) should be (cipherText)
      }

      "should decript" in {
        Clefia.decrypt(cipherText, key) should be (plainText)
      }

      "assure encrypt and decript are inverse" in {
        Clefia.decrypt(Clefia.encrypt(key, cipherText), cipherText) should be (key)
      }
    }

    "should handle 192-bit keys" - {
      val key = (0xffeeddccL, 0xbbaa9988L, 0x77665544L, 0x33221100L, 0xf0e0d0c0L, 0xb0a09080L)
      val plainText = (0x00010203L, 0x04050607L, 0x08090a0bL, 0x0c0d0e0fL)
      val cipherText = (0xe2482f64L, 0x9f028dc4L, 0x80dda184L, 0xfde181adL)

      "should encript" in {
        Clefia.encrypt(plainText, key) should be (cipherText)
      }

      "should decript" in {
        Clefia.decrypt(cipherText, key) should be (plainText)
      }

      "assure encrypt and decript are inverse" in {
        val otherKey = (0xbbaa9988L, 0x77665544L, 0x04050607L, 0x08090a0bL, 0x9f028dc4L, 0x80dda184L)
        Clefia.decrypt(Clefia.encrypt(cipherText, otherKey), otherKey) should be (cipherText)
      }
    }

    "should handle 256-bit keys" - {
      val key = (0xffeeddccL, 0xbbaa9988L, 0x77665544L, 0x33221100L, 0xf0e0d0c0L, 0xb0a09080L, 0x70605040L, 0x30201000L)
      val plainText = (0x00010203L, 0x04050607L, 0x08090a0bL, 0x0c0d0e0fL)
      val cipherText = (0xa1397814L, 0x289de80cL, 0x10da46d1L, 0xfa48b38aL)

      "should encript" ignore {
        Clefia.encrypt(plainText, key) should be (cipherText)
      }

      "should decript" ignore {
        Clefia.decrypt(cipherText, key) should be (plainText)
      }

      "assure encrypt and decript are inverse" in {
        Clefia.decrypt(Clefia.encrypt(key.first128, plainText.concat(cipherText)), plainText.concat(cipherText)) should be (key.first128)
      }
    }
  }
}