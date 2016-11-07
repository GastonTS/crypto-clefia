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
      val cipherText = Array((-567545091,-1686852915,-248937131,1167365373), (-1874150699,-1164026005,1923122875,-943214502))

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
      val cipherText = Array((-498585756,-1627222588,-2132958844,-35552851), (1605509489,-1735242684,-822242222,871942334))

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
      val cipherText = Array((-1590069228,681437196,282740433,-95898742), (219873936,899055884,1714070419,1328499983))

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
      val cipherText = Array((-608618860,-1029326990,1355833740,-554241697), (-452298688,1924775945,991145061,2046682911))

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
      val cipherText = Array((-984243964,-45562810,-659253345,1611139987), (1870421827,-339576966,1292530802,-836403251))

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
      val cipherText = Array((630845392,497378928,-894724412,-1220841694), (-278672728,72184538,-1701814194,1920677305))

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
        ChainedClefia.encryptText(plainText, key) should be("룋亊飯\uE961趄⍗㘬襽랩꾀餪⋟☞ꨝ㉮笲")
      }

      "decrypt a string with length % 8 = 0" in {
        val cipherText = "\uF165옔往⤉翘균ꗕ踉"
        ChainedClefia.decryptText(cipherText, key) should be("!pmolraS")
      }

      "encrypt a string with length % 8 != 0" in {
        val key = (0xffeeddcc, 0xbbaa9988, 0x77665544, 0x33221100, 0xf0e0d0c0, 0xb0a09080, 0x70605040, 0x30201000)
        val plainText = "Sarlomps phone home"
        ChainedClefia.encryptText(plainText, key) should be ("ﲪ뎧㳅䐱꯰譸켱㥾颁\u0C52挍վ獶\u2B6D觡\uF25Cᴏ釈펖ͪԸߞꊶ縩")
      }

      "decrypt a string with length % 8 != 0" in {
        val key = (0xffeeddcc, 0xbbaa9988, 0xf0e0d0c0, 0xb0a09080, 0x70605040, 0x30201000)
        val cipherText = "⏳⧥麥떞季䗠ﮥ콆ꑓﺤ飃\u2B6A㤌鍚棓嗵"
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
