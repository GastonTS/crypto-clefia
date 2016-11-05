package clefia

import java.nio.ByteBuffer
import java.nio.file.{Files, Paths}

import clefia.Numeric._

import scala.collection.GenSeq
import scala.collection.parallel.mutable.ParArray

/**
  * Created by gastonsantoalla on 31/10/16.
  */
class InvalidKeyException(message: String ) extends RuntimeException

trait Clefia {
  def printDuration(timestamp: Long) = println(f"Duration: ${(System.nanoTime - timestamp) * 0.000000001}s")

  def getKeys[T](key: T): (Keys, Int) = key match  {
    case (k0: Int, k1: Int, k2: Int, k3: Int) => (KeyScheduling.scheduleKeys((k0, k1, k2, k3)), 18)
    case (k0: Int, k1: Int, k2: Int, k3: Int, k4: Int, k5: Int) => (KeyScheduling.scheduleKeys((k0, k1, k2, k3, k4, k5)), 22)
    case (k0: Int, k1: Int, k2: Int, k3: Int, k4: Int, k5: Int, k6: Int, k7: Int) => (KeyScheduling.scheduleKeys((k0, k1, k2, k3, k4, k5, k6, k7)), 26)
    case k: String => k.length match {
      case 16 => getKeys(k.toNumeric128)
      case 24 => getKeys(k.toNumeric192)
      case 32 => getKeys(k.toNumeric256)
      case l => throw new InvalidKeyException(s"Invalid Key $l is not a valid length fot a string key, only 16, 24 and 32 string length are supported")
    }
    case _ => throw new InvalidKeyException("Invalid Key, only 128, 192 and 256 bits keys are supported")
  }

  def getBlocks(a: Array[Byte]): GenSeq[Numeric128] =
    a.grouped(4).map(b => ByteBuffer.wrap(Array[Byte](b(0), b(1), b(2), b(3))).getInt).grouped(4).map(_.toArray.toNumeric128).toSeq

  def getByteArray(blocks: GenSeq[Numeric128]): Array[Byte] =
    blocks.flatMap { b => ByteBuffer.allocate(16).putInt(b._1).putInt(b._2).putInt(b._3).putInt(b._4)array() }.toArray

  def processText[T](text: String, key: T, f: (GenSeq[Numeric128], T) => GenSeq[Numeric128]): String =
    f(text.toNumeric128Blocks, key).map(_.toRealString).mkString

  def processFile[T](originPath: String, destinationPath: String, key: T, operation: String, f: (Array[Byte], T) => Array[Byte]) = {
    val timestamp = System.nanoTime
    val finalPath = Paths.get(destinationPath)
    println(f"$operation: ${Files.write(finalPath, f(Files.readAllBytes(Paths.get(originPath)), key))}")
    printDuration(timestamp)
    finalPath.toString
  }

  def processFileExceptHeader[T](originPath: String, destinationPath: String, key: T, headerSize: Int, operation: String, f: (Array[Byte], T) => Array[Byte]) = {
    val timestamp = System.nanoTime
    val byteArray = Files.readAllBytes(Paths.get(originPath))
    val finalPath = Paths.get(destinationPath)
    val (header, body) = byteArray.splitAt(headerSize)
    println(f"$operation: ${Files.write(finalPath, header ++ f(body, key))}")
    printDuration(timestamp)
    finalPath.toString
  }

  def encrypt[T](blocks: GenSeq[Numeric128], key: T): GenSeq[Numeric128]
  def decrypt[T](blocks: GenSeq[Numeric128], key: T): GenSeq[Numeric128]

  def encryptBlock[T](block: Numeric128, key: T): Numeric128 = encrypt(List(block), key).head
  def decryptBlock[T](block: Numeric128, key: T): Numeric128 = decrypt(List(block), key).head

  def encryptText[T](plaintText: String, key: T): String = {
    val timestamp = System.nanoTime
    val mod = plaintText.length % 8
    val result = processText(plaintText + " " * (7 - mod) + mod, key, encrypt)
    printDuration(timestamp)
    result
  }

  def decryptText[T](cipherText: String, key: T): String = {
    val timestamp = System.nanoTime
    val decryptedString = processText(cipherText, key, decrypt)
    val result = decryptedString.dropRight(8 - decryptedString.last.asDigit)
    printDuration(timestamp)
    result
  }

  def encryptBytes[T](byteArray: Array[Byte], key: T): Array[Byte] = {
    val mod = byteArray.length % 16
    val paddedArray = byteArray ++ Array.fill(15 - mod)(0.toByte) :+ mod.toByte
    getByteArray(encrypt(getBlocks(paddedArray), key))
  }

  def decryptBytes[T](byteArray: Array[Byte], key: T): Array[Byte] = {
    val decryptedArray = getByteArray(decrypt(getBlocks(byteArray), key))
    decryptedArray.dropRight(16 - decryptedArray.last)
  }

  def encryptFile[T](originPath: String, destinationPath: String, key: T) = processFile(originPath, destinationPath, key, "Encrypted File", encryptBytes)
  def decryptFile[T](originPath: String, destinationPath: String, key: T) = processFile(originPath, destinationPath, key, "Decrypted File", decryptBytes)

  def encryptBMP[T](originPath: String, destinationPath: String, key: T) = processFileExceptHeader(originPath, destinationPath, key, 54, "Encrypted BMP", encryptBytes)
  def decryptBMP[T](originPath: String, destinationPath: String, key: T) = processFileExceptHeader(originPath, destinationPath, key, 54, "Decrypted BMP", decryptBytes)
}

object Clefia extends Clefia {
  def process[T](blocks: GenSeq[Numeric128], key: T, f: (Numeric128, Keys, Int) => Numeric128 ): GenSeq[Numeric128] = {
    val (keys, rounds) = getKeys(key)
    blocks.toParArray.map(f(_, keys, rounds))
  }

  def encrypt[T](blocks: GenSeq[Numeric128], key: T): GenSeq[Numeric128] = process(blocks, key, DataProcessing.enc)
  def decrypt[T](blocks: GenSeq[Numeric128], key: T): GenSeq[Numeric128] = process(blocks, key, DataProcessing.dec)
}
object ChainedClefia extends Clefia {
  def process[T](blocks: GenSeq[Numeric128], key: T, f: (Numeric128, Keys, Int) => Numeric128, encrypting: Boolean): GenSeq[Numeric128] = {
    def singleProcess(numberBlock: Int, keys: Keys, rounds: Int, acc: Vector[Numeric128]): Vector[Numeric128] = {
      if (numberBlock == blocks.length) acc
      else {
        val processedElement = f(blocks(numberBlock), keys, rounds)
        val newKey = if(encrypting) processedElement else blocks(numberBlock)
        val (sKeys, nRounds) = getKeys(newKey)
        singleProcess(numberBlock + 1, sKeys, nRounds, acc :+ processedElement)
      }
    }

    val (keys, rounds) = getKeys(key)
    singleProcess(0, keys, rounds, Vector())
  }

  def encrypt[T](blocks: GenSeq[Numeric128], key: T): GenSeq[Numeric128] = process(blocks, key, DataProcessing.enc, true)
  def decrypt[T](blocks: GenSeq[Numeric128], key: T): GenSeq[Numeric128] = process(blocks, key, DataProcessing.dec, false)
}

