package clefia

import java.nio.file.{Files, Paths}

import org.scalatest.{BeforeAndAfter, FreeSpec, Matchers}

/**
  * Created by gastonsantoalla on 05/11/16.
  */
class ClefiaChainedSpec extends FreeSpec with Matchers with BeforeAndAfter {
  "Clefia with chained configuration" - {

    "should solve 128-bit key with more than one block" - {
      val key = (0xffeeddcc, 0xbbaa9988, 0x77665544, 0x33221100)
      val plainText = Array((0x00010203, 0x04050607, 0x08090a0b, 0x0c0d0e0f), (0xa1397814, 0x289de80c, 0x10da46d1, 0xfa48b38a))
      val cipherText = Array((-567545091, -1686852915, -248937131, 1167365373), (-2039550581, 424567739, 78917109, -1097072512))

      "should encrypt" in {
        ChainedClefia.encrypt(plainText, key) should be(cipherText)
      }

      "should decrypt" in {
        ChainedClefia.decrypt(cipherText, key) should be(plainText)
      }

      "assure encrypt and decrypt are inverse" in {
        ChainedClefia.decrypt(ChainedClefia.encrypt(cipherText, plainText.head), plainText.head) should be(cipherText)
      }
    }

    "should solve 192-bit key with more than one block" - {
      val key = (0xffeeddcc, 0xbbaa9988, 0x77665544, 0x33221100, 0xf0e0d0c0, 0xb0a09080)
      val plainText = Array((0x00010203, 0x04050607, 0x08090a0b, 0x0c0d0e0f), (0xa1397814, 0x289de80c, 0x10da46d1, 0xfa48b38a))
      val cipherText = Array((-498585756, -1627222588, -2132958844, -35552851), (-1129809288, 37860135, -127747911, -2011905404))

      "should encrypt" in {
        ChainedClefia.encrypt(plainText, key) should be(cipherText)
      }

      "should decrypt" in {
        ChainedClefia.decrypt(cipherText, key) should be(plainText)
      }

      "assure encrypt and decrypt are inverse" in {
        ChainedClefia.decrypt(ChainedClefia.encrypt(cipherText, plainText.head), plainText.head) should be(cipherText)
      }
    }

    "should solve 256-bit key with more than one block" - {
      val key = (0xffeeddcc, 0xbbaa9988, 0x77665544, 0x33221100, 0xf0e0d0c0, 0xb0a09080, 0x70605040, 0x30201000)
      val plainText = Array((0x00010203, 0x04050607, 0x08090a0b, 0x0c0d0e0f), (0xa1397814, 0x289de80c, 0x10da46d1, 0xfa48b38a))
      val cipherText = Array((-1590069228, 681437196, 282740433, -95898742), (1489792228, 1672367380, -938511681, -1107482608))

      "should encrypt" in {
        ChainedClefia.encrypt(plainText, key) should be(cipherText)
      }

      "should decrypt" in {
        ChainedClefia.decrypt(cipherText, key) should be(plainText)
      }

      "assure encrypt and decrypt are inverse" in {
        ChainedClefia.decrypt(ChainedClefia.encrypt(cipherText, plainText.head), plainText.head) should be(cipherText)
      }
    }


    "should handle 128-bit string key with more than one block" - {
      val key = "Sarlomp vacation"
      val plainText = Array((0x00010203, 0x04050607, 0x08090a0b, 0x0c0d0e0f), (0xa1397814, 0x289de80c, 0x10da46d1, 0xfa48b38a))
      val cipherText = Array((-608618860, -1029326990, 1355833740, -554241697), (-1447442348, 224699604, -908284807, 804202349))

      "should encrypt" in {
        ChainedClefia.encrypt(plainText, key) should be(cipherText)
      }

      "should decrypt" in {
        ChainedClefia.decrypt(cipherText, key) should be(plainText)
      }

      "assure encrypt and decrypt are inverse" in {
        ChainedClefia.decrypt(ChainedClefia.encrypt(cipherText, plainText.head), plainText.head) should be(cipherText)
      }
    }

    "should handle 192-bit string key with more than one block" - {
      val key = "Mr.Sarlomp is a good man"
      val plainText = Array((0x00010203, 0x04050607, 0x08090a0b, 0x0c0d0e0f), (0xa1397814, 0x289de80c, 0x10da46d1, 0xfa48b38a))
      val cipherText = Array((-984243964, -45562810, -659253345, 1611139987), (-1924976483, 1608413481, -178376288, -795460557))

      "should encrypt" in {
        ChainedClefia.encrypt(plainText, key) should be(cipherText)
      }

      "should decrypt" in {
        ChainedClefia.decrypt(cipherText, key) should be(plainText)
      }

      "assure encrypt and decrypt are inverse" in {
        ChainedClefia.decrypt(ChainedClefia.encrypt(cipherText, plainText.head), plainText.head) should be(cipherText)
      }
    }

    "should handle 256-bit string key with more than one block" - {
      val key = "Mr.Sarlomp wish you were here :c"
      val plainText = Array((0x00010203, 0x04050607, 0x08090a0b, 0x0c0d0e0f), (0xa1397814, 0x289de80c, 0x10da46d1, 0xfa48b38a))
      val cipherText = Array((630845392, 497378928, -894724412, -1220841694), (-1453606143, -115049568, 375888018, -1994039296))

      "should encrypt" in {
        ChainedClefia.encrypt(plainText, key) should be(cipherText)
      }

      "should decrypt" in {
        ChainedClefia.decrypt(cipherText, key) should be(plainText)
      }

      "assure encrypt and decrypt are inverse" in {
        ChainedClefia.decrypt(ChainedClefia.encrypt(cipherText, plainText.head), plainText.head) should be(cipherText)
      }
    }

    "should handle strings as plainText" - {
      val key = (0xffeeddcc, 0xbbaa9988, 0x77665544, 0x33221100)

      "encrypt a string with length % 8 = 0" in {
        val plainText = "Sarlomp!"
        ChainedClefia.encryptText(plainText, key) should be("룋亊飯\uE961趄⍗㘬襽䐢凈➧㎩쯿ⱶ嬫ಧ")
      }

      "decrypt a string with length % 8 = 0" in {
        val cipherText = "\uF165옔往⤉翘균ꗕ踉"
        ChainedClefia.decryptText(cipherText, key) should be("!pmolraS")
      }

      "encrypt a string with length % 8 != 0" in {
        val key = (0xffeeddcc, 0xbbaa9988, 0x77665544, 0x33221100, 0xf0e0d0c0, 0xb0a09080, 0x70605040, 0x30201000)
        val plainText = "May the sarlomps be with you"
        ChainedClefia.encryptText(plainText, key) should be ("ﬕ궗蔍ﵭ눴샒챥縉섺㺓し惊凿ਗ਼텕\uA795ᒴ碠딯\uE627콺츐ᴥ濿孿ᶙଥ枤궦ե䣙쒌")
      }

      "decrypt a string with length % 8 != 0" in {
        val key = (0xffeeddcc, 0xbbaa9988, 0xf0e0d0c0, 0xb0a09080, 0x70605040, 0x30201000)
        val cipherText = "⏳⧥麥떞季䗠ﮥ콆患焹\uEADBꇥ튼\uD7C8붙藦"
        ChainedClefia.decryptText(cipherText, key) should be ("Hail Sarlomps!")
      }

      "assure encrypt and decrypt are inverse" in {
        val key = (0xffeeddcc, 0xbbaa9988, 0x77665544, 0x33221100, 0xf0e0d0c0, 0xb0a09080, 0x70605040, 0x30201000)
        val plainText = "Mr. Sarlomp is a good man"
        ChainedClefia.decryptText(ChainedClefia.encryptText(plainText, key), key) should be(plainText)
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
        ChainedClefia.decryptFile(ChainedClefia.encryptFile(fileString, encryptedString, key), decryptedString, key)
        val originalFile = Files.readAllBytes(filePath)

        originalFile should not be Files.readAllBytes(encryptedPath)
        originalFile should be(Files.readAllBytes(decryptedPath))
      }

      "assure encrypt and decrypt are inverse encripting a File excepting header when is a bmp" in {
        ChainedClefia.decryptBMP(ChainedClefia.encryptBMP(fileString, encryptedString, key), decryptedString, key)
        val originalFile = Files.readAllBytes(filePath)
        val encryptedFile = Files.readAllBytes(encryptedPath)
        val decryptedFile = Files.readAllBytes(decryptedPath)

        originalFile.take(54) should be(encryptedFile.take(54))
        originalFile should not be encryptedFile
        originalFile should be(decryptedFile)
      }
    }
  }
}
