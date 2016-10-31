package clefia

import clefia.Numeric.Numeric128

/**
  * Created by gastonsantoalla on 30/10/16.
  */
object DataProcessing {

  def enc(plainText: Numeric128, keys: (Numeric128, Array[Long]), rounds: Int): Numeric128 = {
    val (p0, p1, p2, p3) = plainText
    val (wk0, wk1, wk2, wk3) = keys._1

    val (t0, t1, t2, t3) = GFN.gfn4((p0, p1 ^ wk0, p2, p3 ^ wk1), keys._2, rounds)
    (t0, t1 ^ wk2, t2, t3 ^ wk3)
  }

  def dec(cipherText: Numeric128, keys: (Numeric128, Array[Long]), rounds: Int): Numeric128 = {
    val (c0, c1, c2, c3) = cipherText
    val (wk0, wk1, wk2, wk3) = keys._1

    val (t0, t1, t2, t3) = GFN.gfn4Inverse((c0, c1 ^ wk2, c2, c3 ^ wk3), keys._2, rounds)
    (t0, t1 ^ wk0, t2, t3 ^ wk1)
  }

}
