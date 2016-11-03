package clefia

import java.nio.ByteBuffer
import java.nio.file.{Files, Paths}

import clefia.Numeric._

import scala.collection.GenSeq

/**
  * Created by gastonsantoalla on 31/10/16.
  */
class InvalidKeyException(message: String ) extends RuntimeException

object Clefia {

  def process[T](blocks: GenSeq[Numeric128], key: T, f: (Numeric128, Keys, Int) => Numeric128 ): GenSeq[Numeric128] = {
    val (keys, rounds) = key match  {
      case (k0: Int, k1: Int, k2: Int, k3: Int) => (KeyScheduling.scheduleKeys((k0, k1, k2, k3)), 18)
      case (k0: Int, k1: Int, k2: Int, k3: Int, k4: Int, k5: Int) => (KeyScheduling.scheduleKeys((k0, k1, k2, k3, k4, k5)), 22)
      case (k0: Int, k1: Int, k2: Int, k3: Int, k4: Int, k5: Int, k6: Int, k7: Int) => (KeyScheduling.scheduleKeys((k0, k1, k2, k3, k4, k5, k6, k7)), 26)
      case _ => throw new InvalidKeyException("Invalid Key")
    }

    blocks.map(f(_, keys, rounds))
  }

  def processText[T](text: String, key: T, f: (GenSeq[Numeric128], T) => GenSeq[Numeric128]): String =
    f(text.toNumeric128Blocks.par, key).map(_.toRealString).mkString

  def encrypt[T](blocks: GenSeq[Numeric128], key: T): GenSeq[Numeric128] = process(blocks, key, DataProcessing.enc)
  def decrypt[T](blocks: GenSeq[Numeric128], key: T): GenSeq[Numeric128] = process(blocks, key, DataProcessing.dec)

  def encryptBlock[T](block: Numeric128, key: T): Numeric128 = encrypt(List(block), key).head
  def decryptBlock[T](block: Numeric128, key: T): Numeric128 = decrypt(List(block), key).head

  def encryptText[T](plaintText: String, key: T): String = {
    val mod = plaintText.length % 8
    processText(plaintText + " " * (7 - mod) + mod, key, encrypt)
  }

  def decryptText[T](cipherText: String, key: T): String = {
    val decryptedString = processText(cipherText, key, decrypt)
    decryptedString.dropRight(8 - decryptedString.last.asDigit)
  }

  def encryptFile[T](originPath: String, destinationPath: String, key: T) = {
    val byteArray = Files.readAllBytes(Paths.get(originPath))
    //TODO: Add padd
    val blocks = getBlocks(byteArray)

    val finalPath = Paths.get(destinationPath)
    println(f"Encrypted File: ${Files.write(finalPath, getByteArray(encrypt(blocks, key)))}")
    finalPath.toString
  }

  def decryptFile[T](originPath: String, destinationPath: String, key: T) = {
    val byteArray = Files.readAllBytes(Paths.get(originPath))
    val blocks = getBlocks(byteArray).par
    //TODO: Remove padd

    val finalPath = Paths.get(destinationPath)
    println(f"Encrypted File: ${Files.write(finalPath, getByteArray(decrypt(blocks, key)))}")
    finalPath.toString
  }

  def getBlocks(a: Array[Byte]): GenSeq[Numeric128] = a.grouped(4).map(b => ByteBuffer.wrap(Array[Byte](b(0), b(1), b(2), b(3))).getInt).grouped(4).map(_.toArray.toNumeric128).toList

  def getByteArray(blocks: GenSeq[Numeric128]) = {
    val buffer: ByteBuffer = ByteBuffer.allocate(16 * blocks.length)
    blocks.foreach(b => buffer.putInt(b._1).putInt(b._2).putInt(b._3).putInt(b._4))
    buffer.array()
  }
}
