package clefia

import java.nio.file.{Files, Paths}

import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by gastonsantoalla on 31/10/16.
  */
class ClefiaParallelSpec extends FreeSpec with Matchers {

  "Clefia with parallel configuration" - {

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


    "should handle 128-bit string key with more than one block" - {
      val key = "Sarlomp vacation"
      val plainText = List((0x00010203, 0x04050607, 0x08090a0b, 0x0c0d0e0f), (0xa1397814, 0x289de80c, 0x10da46d1, 0xfa48b38a))
      val cipherText = List((-608618860, -1029326990, 1355833740, -554241697), (-2080514872, 489396424, 417304930, -1699638046))

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

    "should handle 192-bit string key with more than one block" - {
      val key = "Mr.Sarlomp is a good man"
      val plainText = List((0x00010203, 0x04050607, 0x08090a0b, 0x0c0d0e0f), (0xa1397814, 0x289de80c, 0x10da46d1, 0xfa48b38a))
      val cipherText = List((-984243964, -45562810, -659253345, 1611139987), (208910872, -529051789, -784316288, 1460340374))

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

    "should handle 256-bit string key with more than one block" - {
      val key = "Mr.Sarlomp wish you were here :c"
      val plainText = List((0x00010203, 0x04050607, 0x08090a0b, 0x0c0d0e0f), (0xa1397814, 0x289de80c, 0x10da46d1, 0xfa48b38a))
      val cipherText = List((630845392, 497378928, -894724412, -1220841694), (-1512096666, -1703054826, -1409704002, 1278716346))

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
        Clefia.encryptText(plainText, key) should be ("룋亊飯\uE961趄⍗㘬襽갸⾾ﲘ댽抻矓บ逢")
      }

      "decrypt a string with length % 8 = 0" in {
        val cipherText = "\uF165옔往⤉翘균ꗕ踉"
        Clefia.decryptText(cipherText, key) should be ("!pmolraS")
      }

      "encrypt a string with length % 8 != 0" in {
        val key = (0xffeeddcc, 0xbbaa9988, 0x77665544, 0x33221100, 0xf0e0d0c0, 0xb0a09080, 0x70605040, 0x30201000)
        val plainText = "Sarlomps will raise"
        Clefia.encryptText(plainText, key) should be ("ﲪ뎧㳅䐱꯰譸켱㥾쥉騚\uE4BA\u0C00䚯榄꾷屑\uF1FDꢴ༥駱랅䫙掆漄")
      }

      "decrypt a string with length % 8 != 0" in {
        val key = (0xffeeddcc, 0xbbaa9988, 0xf0e0d0c0, 0xb0a09080, 0x70605040, 0x30201000)
        val cipherText = "⏳⧥麥떞季䗠ﮥ콆\uEC4B䭰穱韚䇥\uEBB3\uE6F2\uE219"
        Clefia.decryptText(cipherText, key) should be ("Hail Sarlomps!")
      }

      "assure encrypt and decrypt are inverse" in {
        val key = (0xffeeddcc, 0xbbaa9988, 0x77665544, 0x33221100, 0xf0e0d0c0, 0xb0a09080, 0x70605040, 0x30201000)
        val plainText = "Mr. Sarlomp is a good man"
        Clefia.decryptText(Clefia.encryptText(plainText, key), key) should be (plainText)
      }
    }

    "should handle files as plainText" - {
      val key = (0xffeeddcc, 0xbbaa9988, 0x77665544, 0x33221100)
      val fileString = "src/test/resources/batman.bmp"
      val encryptedString = "src/test/resources/encrypted"
      val decryptedString = "src/test/resources/decrypted"
      val filePath = Paths.get(fileString)
      val encryptedPath = Paths.get(encryptedString)
      val decryptedPath = Paths.get(decryptedString)

      "assure encrypt and decrypt are inverse encrypting a File" in {
        Clefia.decryptFile(Clefia.encryptFile(fileString, encryptedString, key), decryptedString, key)
        val originalFile = Files.readAllBytes(filePath)

        originalFile should not be Files.readAllBytes(encryptedPath)
        originalFile should be (Files.readAllBytes(decryptedPath))
      }

      "assure encrypt and decrypt are inverse encripting a File excepting header when is a bmp" in {
        Clefia.decryptBMP(Clefia.encryptBMP(fileString, encryptedString, key), decryptedString, key)
        val originalFile = Files.readAllBytes(filePath)
        val encryptedFile = Files.readAllBytes(encryptedPath)
        val decryptedFile = Files.readAllBytes(decryptedPath)

        originalFile.take(54) should be (encryptedFile.take(54))
        originalFile should not be encryptedFile
        originalFile should be (decryptedFile)
      }
    }
  }
}