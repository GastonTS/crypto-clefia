package clefia

import clefia.Numeric._
import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by gastonsantoalla on 31/10/16.
  */
class ClefiaSpec extends FreeSpec with Matchers {

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

    "should solve 128-bit key with more than one block" - {
      val key = (0xffeeddcc, 0xbbaa9988, 0x77665544, 0x33221100)
      val plainText = List((0x00010203, 0x04050607, 0x08090a0b, 0x0c0d0e0f), (0xa1397814, 0x289de80c, 0x10da46d1, 0xfa48b38a))
      val cipherText = List((-567545091, -1686852915, -248937131, 1167365373), (-1411681916, 1121179255, -638568068, -1930591881))

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
      val key = (0xffeeddcc, 0xbbaa9988, 0x77665544, 0x33221100, 0xf0e0d0c0, 0xb0a09080)
      val plainText = List((0x00010203, 0x04050607, 0x08090a0b, 0x0c0d0e0f), (0xa1397814, 0x289de80c, 0x10da46d1, 0xfa48b38a))
      val cipherText = List((-498585756, -1627222588, -2132958844, -35552851), (148402139, -156386572, 1173495889, 350561144))

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
      val key = (0xffeeddcc, 0xbbaa9988, 0x77665544, 0x33221100, 0xf0e0d0c0, 0xb0a09080, 0x70605040, 0x30201000)
      val plainText = List((0x00010203, 0x04050607, 0x08090a0b, 0x0c0d0e0f), (0xa1397814, 0x289de80c, 0x10da46d1, 0xfa48b38a))
      val cipherText = List((-1590069228, 681437196, 282740433, -95898742), (-177668879, -1216871642, 1130699143, 1510722439))

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

    "should handle strings as plainText" - {
      val key = (0xffeeddcc, 0xbbaa9988, 0x77665544, 0x33221100)

      "encrypt a string with length % 8 = 0" in {
        val plainText = "Sarlomp!"
        Clefia.encryptText(plainText, key) should be ("룋亊飯\uE961趄⍗㘬襽")
      }

      "decrypt a string with length % 8 = 0" in {
        val cipherText = "\uF165옔往⤉翘균ꗕ踉"
        Clefia.decryptText(cipherText, key) should be ("!pmolraS")
      }

      "encrypt a string with length % 8 != 0" in {
        val plainText = "Sarlomps will raise"
        Clefia.encryptText(plainText, key) should be ("\u9FFC杻뿉聟齗\uF0A9頫\u08AE㿬륍ﷻ恸㸨鶋슐괧摳⧃轐䷶䄟㍌\u16FC㪱")
      }

      "decrypt a string with length % 8 != 0" in {
        val cipherText = "蛨ܯ뷷≃\uE973띌龭퉊\uFBC2ᬝ盭ꏝ瘴繹뙷⼁"
        Clefia.decryptText(cipherText, key) should be ("Hail Sarlomps!")
      }

      "assure encrypt and decrypt are inverse" in {
        val plainText = "Mr. Sarlomp is a good man"
        Clefia.decryptText(Clefia.encryptText(plainText, key), key) should be (plainText)
      }
    }
  }
}