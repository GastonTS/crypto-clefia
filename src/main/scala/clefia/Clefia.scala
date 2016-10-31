package clefia

import clefia.Numeric._

/**
  * Created by gastonsantoalla on 31/10/16.
  */
object Clefia {

  def encrypt[T](plaintText: Numeric128, key: T): Numeric128 = {
    key match  {
      case k: Numeric128 => DataProcessing.enc(plaintText, KeyScheduling.scheduleKeys(k), 18)
      case k: Numeric192 => DataProcessing.enc(plaintText, KeyScheduling.scheduleKeys(k), 22)
      case k: Numeric256 => DataProcessing.enc(plaintText, KeyScheduling.scheduleKeys(k), 26)
      case _ => throw new InvalidKeyException("Invalid Key")
    }
  }

  def decrypt[T](plaintText: Numeric128, key: T): Numeric128 = {
    key match  {
      case k: Numeric128 => DataProcessing.dec(plaintText, KeyScheduling.scheduleKeys(k), 18)
      case k: Numeric192 => DataProcessing.dec(plaintText, KeyScheduling.scheduleKeys(k), 22)
      case k: Numeric256 => DataProcessing.dec(plaintText, KeyScheduling.scheduleKeys(k), 26)
      case _ => throw new InvalidKeyException("Invalid Key")
    }
  }
}

class InvalidKeyException(message: String ) extends RuntimeException
