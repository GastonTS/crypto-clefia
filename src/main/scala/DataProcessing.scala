/**
  * Created by gastonsantoalla on 30/10/16.
  */
object DataProcessing {

  def enc(plainText: (Long, Long, Long, Long), whiteningKeys: (Long, Long, Long, Long), roundKeys:Array[Long], rounds: Int): (Long, Long, Long, Long) = {
    val (p0, p1, p2, p3) = plainText
    val (wk0, wk1, wk2, wk3) = whiteningKeys

    val (t0, t1, t2, t3) = GFN.gfn4((p0, p1 ^ wk0, p2, p3 ^ wk1), roundKeys, rounds)
    (t0, t1 ^ wk2, t2, t3 ^ wk3)
  }

  def dec(cipherText: (Long, Long, Long, Long), whiteningKeys: (Long, Long, Long, Long), roundKeys:Array[Long], rounds: Int): (Long, Long, Long, Long) = {
    val (c0, c1, c2, c3) = cipherText
    val (wk0, wk1, wk2, wk3) = whiteningKeys

    val (t0, t1, t2, t3) = GFN.gfn4Inverse((c0, c1 ^ wk2, c2, c3 ^ wk3), roundKeys, rounds)
    (t0, t1 ^ wk0, t2, t3 ^ wk1)
  }

}
