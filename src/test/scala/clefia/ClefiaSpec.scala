package clefia

import clefia.Numeric._
import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by gastonsantoalla on 31/10/16.
  */
class ClefiaSpec extends FreeSpec with Matchers {

  "Clefia" - {
    "should solve 128-bit key test vector with only one block" - {
      val key = (0xffeeddccL, 0xbbaa9988L, 0x77665544L, 0x33221100L)
      val plainText = (0x00010203L, 0x04050607L, 0x08090a0bL, 0x0c0d0e0fL)
      val cipherText = (0xde2bf2fdL, 0x9b74aacdL, 0xf1298555L, 0x459494fdL)

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
      val key = (0xffeeddccL, 0xbbaa9988L, 0x77665544L, 0x33221100L, 0xf0e0d0c0L, 0xb0a09080L)
      val plainText = (0x00010203L, 0x04050607L, 0x08090a0bL, 0x0c0d0e0fL)
      val cipherText = (0xe2482f64L, 0x9f028dc4L, 0x80dda184L, 0xfde181adL)

      "should encrypt" in {
        Clefia.encryptBlock(plainText, key) should be (cipherText)
      }

      "should decrypt" in {
        Clefia.decryptBlock(cipherText, key) should be (plainText)
      }

      "assure encrypt and decrypt are inverse" in {
        val otherKey = (0xbbaa9988L, 0x77665544L, 0x04050607L, 0x08090a0bL, 0x9f028dc4L, 0x80dda184L)
        Clefia.decryptBlock(Clefia.encryptBlock(cipherText, otherKey), otherKey) should be (cipherText)
      }
    }

    "should solve 256-bit key test vector with only one block" - {
      val key = (0xffeeddccL, 0xbbaa9988L, 0x77665544L, 0x33221100L, 0xf0e0d0c0L, 0xb0a09080L, 0x70605040L, 0x30201000L)
      val plainText = (0x00010203L, 0x04050607L, 0x08090a0bL, 0x0c0d0e0fL)
      val cipherText = (0xa1397814L, 0x289de80cL, 0x10da46d1L, 0xfa48b38aL)

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

    "should solve 128-bit key with more than one block" - {
      val key = (0xffeeddccL, 0xbbaa9988L, 0x77665544L, 0x33221100L)
      val plainText = List((0x00010203L, 0x04050607L, 0x08090a0bL, 0x0c0d0e0fL), (0xa1397814L, 0x289de80cL, 0x10da46d1L, 0xfa48b38aL))
      val cipherText = List((3727422205L, 2608114381L, 4046030165L, 1167365373L), (2883285380L, 1121179255L, 3656399228L, 2364375415L))

      "should encrypt" in {
        Clefia.encrypt(plainText, key) should be (cipherText)
      }

      "should decrypt" in {
        Clefia.decrypt(cipherText, key) should be (plainText)
      }

      "assure encrypt and decrypt are inverse" in {
        Clefia.decrypt(Clefia.encrypt(cipherText, plainText.head), plainText.head) should be (cipherText)
      }
    }

    "should solve 192-bit key with more than one block" - {
      val key = (0xffeeddccL, 0xbbaa9988L, 0x77665544L, 0x33221100L, 0xf0e0d0c0L, 0xb0a09080L)
      val plainText = List((0x00010203L, 0x04050607L, 0x08090a0bL, 0x0c0d0e0fL), (0xa1397814L, 0x289de80cL, 0x10da46d1L, 0xfa48b38aL))
      val cipherText = List((3796381540L, 2667744708L, 2162008452L, 4259414445L), (148402139L, 4138580724L, 1173495889L, 350561144L))

      "should encrypt" in {
        Clefia.encrypt(plainText, key) should be (cipherText)
      }

      "should decrypt" in {
        Clefia.decrypt(cipherText, key) should be (plainText)
      }

      "assure encrypt and decrypt are inverse" in {
        Clefia.decrypt(Clefia.encrypt(cipherText, plainText.head), plainText.head) should be (cipherText)
      }
    }

    "should solve 256-bit key with more than one block" - {
      val key = (0xffeeddccL, 0xbbaa9988L, 0x77665544L, 0x33221100L, 0xf0e0d0c0L, 0xb0a09080L, 0x70605040L, 0x30201000L)
      val plainText = List((0x00010203L, 0x04050607L, 0x08090a0bL, 0x0c0d0e0fL), (0xa1397814L, 0x289de80cL, 0x10da46d1L, 0xfa48b38aL))
      val cipherText = List((2704898068L, 681437196L, 282740433L, 4199068554L), (4117298417L, 3078095654L, 1130699143L, 1510722439L))

      "should encrypt" in {
        Clefia.encrypt(plainText, key) should be (cipherText)
      }

      "should decrypt" in {
        Clefia.decrypt(cipherText, key) should be (plainText)
      }

      "assure encrypt and decrypt are inverse" in {
        Clefia.decrypt(Clefia.encrypt(cipherText, plainText.head), plainText.head) should be (cipherText)
      }
    }
  }
}