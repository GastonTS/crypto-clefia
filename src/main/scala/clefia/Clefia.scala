package clefia

import clefia.Numeric._

/**
  * Created by gastonsantoalla on 31/10/16.
  */
object Clefia {

  def process[T](plaintText: Numeric128, key: T, f: (Numeric128, (Numeric128, Array[Long]), Int) => Numeric128 ): Numeric128 = {
    key match  {
      case (k0: Long, k1: Long, k2: Long, k3: Long) =>
        f(plaintText, KeyScheduling.scheduleKeys((k0, k1, k2, k3)), 18)
      case (k0: Long, k1: Long, k2: Long, k3: Long, k4: Long, k5: Long) =>
        f(plaintText, KeyScheduling.scheduleKeys((k0, k1, k2, k3, k4, k5)), 22)
      case (k0: Long, k1: Long, k2: Long, k3: Long, k4: Long, k5: Long, k6: Long, k7: Long) =>
        f(plaintText, KeyScheduling.scheduleKeys((k0, k1, k2, k3, k4, k5, k6, k7)), 26)
      case _ => throw new InvalidKeyException("Invalid Key")
    }
  }

  def encrypt[T](plaintText: Numeric128, key: T): Numeric128 = process(plaintText, key, DataProcessing.enc)
  def decrypt[T](plaintText: Numeric128, key: T): Numeric128 = process(plaintText, key, DataProcessing.dec)

}

class InvalidKeyException(message: String ) extends RuntimeException
