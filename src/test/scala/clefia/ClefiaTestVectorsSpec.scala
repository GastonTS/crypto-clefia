package clefia

import org.scalatest.{FreeSpec, Matchers}

import clefia.Numeric._

/**
  * Created by gastonsantoalla on 05/11/16.
  */
class ClefiaTestVectorsSpec extends FreeSpec with Matchers {

  "Clefia" - {
    "should solve 128-bit key test vector with only one block" - {
      val key = (0xffeeddcc, 0xbbaa9988, 0x77665544, 0x33221100)
      val plainText = (0x00010203, 0x04050607, 0x08090a0b, 0x0c0d0e0f)
      val cipherText = (0xde2bf2fd, 0x9b74aacd, 0xf1298555, 0x459494fd)

      "should encrypt" in {
        Clefia.encryptBlock(plainText, key) should be (cipherText)
      }

      "should decrypt" in {
        Clefia.decryptBlock(cipherText, key) should be (plainText)
      }

      "assure encrypt and decrypt are inverse" in {
        Clefia.decryptBlock(Clefia.encryptBlock(key, cipherText), cipherText) should be (key)
      }
    }

    "should solve 192-bit key test vector with only one block" - {
      val key = (0xffeeddcc, 0xbbaa9988, 0x77665544, 0x33221100, 0xf0e0d0c0, 0xb0a09080)
      val plainText = (0x00010203, 0x04050607, 0x08090a0b, 0x0c0d0e0f)
      val cipherText = (0xe2482f64, 0x9f028dc4, 0x80dda184, 0xfde181ad)

      "should encrypt" in {
        Clefia.encryptBlock(plainText, key) should be (cipherText)
      }

      "should decrypt" in {
        Clefia.decryptBlock(cipherText, key) should be (plainText)
      }

      "assure encrypt and decrypt are inverse" in {
        val otherKey = (0xbbaa9988, 0x77665544, 0x04050607, 0x08090a0b, 0x9f028dc4, 0x80dda184)
        Clefia.decryptBlock(Clefia.encryptBlock(cipherText, otherKey), otherKey) should be (cipherText)
      }
    }

    "should solve 256-bit key test vector with only one block" - {
      val key = (0xffeeddcc, 0xbbaa9988, 0x77665544, 0x33221100, 0xf0e0d0c0, 0xb0a09080, 0x70605040, 0x30201000)
      val plainText = (0x00010203, 0x04050607, 0x08090a0b, 0x0c0d0e0f)
      val cipherText = (0xa1397814, 0x289de80c, 0x10da46d1, 0xfa48b38a)

      "should encrypt" in {
        Clefia.encryptBlock(plainText, key) should be (cipherText)
      }

      "should decrypt" in {
        Clefia.decryptBlock(cipherText, key) should be (plainText)
      }

      "assure encrypt and decrypt are inverse" in {
        Clefia.decryptBlock(Clefia.encryptBlock(key.first128, plainText.concat(cipherText)), plainText.concat(cipherText)) should be (key.first128)
      }
    }
  }
}
