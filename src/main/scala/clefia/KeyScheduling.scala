package clefia

import clefia.Numeric._

/**
  * Created by gastonsantoalla on 30/10/16.
  */
object KeyScheduling {

  val CON128 = Array(0xf56b7aebL, 0x994a8a42L, 0x96a4bd75L, 0xfa854521L,
                     0x735b768aL, 0x1f7abac4L, 0xd5bc3b45L, 0xb99d5d62L,
                     0x52d73592L, 0x3ef636e5L, 0xc57a1ac9L, 0xa95b9b72L,
                     0x5ab42554L, 0x369555edL, 0x1553ba9aL, 0x7972b2a2L,
                     0xe6b85d4dL, 0x8a995951L, 0x4b550696L, 0x2774b4fcL,
                     0xc9bb034bL, 0xa59a5a7eL, 0x88cc81a5L, 0xe4ed2d3fL,
                     0x7c6f68e2L, 0x104e8ecbL, 0xd2263471L, 0xbe07c765L,
                     0x511a3208L, 0x3d3bfbe6L, 0x1084b134L, 0x7ca565a7L,
                     0x304bf0aaL, 0x5c6aaa87L, 0xf4347855L, 0x9815d543L,
                     0x4213141aL, 0x2e32f2f5L, 0xcd180a0dL, 0xa139f97aL,
                     0x5e852d36L, 0x32a464e9L, 0xc353169bL, 0xaf72b274L,
                     0x8db88b4dL, 0xe199593aL, 0x7ed56d96L, 0x12f434c9L,
                     0xd37b36cbL, 0xbf5a9a64L, 0x85ac9b65L, 0xe98d4d32L,
                     0x7adf6582L, 0x16fe3ecdL, 0xd17e32c1L, 0xbd5f9f66L,
                     0x50b63150L, 0x3c9757e7L, 0x1052b098L, 0x7c73b3a7L)

  val CON192 = Array(0xc6d61d91L, 0xaaf73771L, 0x5b6226f8L, 0x374383ecL,
                    0x15b8bb4cL, 0x799959a2L, 0x32d5f596L, 0x5ef43485L,
                    0xf57b7acbL, 0x995a9a42L, 0x96acbd65L, 0xfa8d4d21L,
                    0x735f7682L, 0x1f7ebec4L, 0xd5be3b41L, 0xb99f5f62L,
                    0x52d63590L, 0x3ef737e5L, 0x1162b2f8L, 0x7d4383a6L,
                    0x30b8f14cL, 0x5c995987L, 0x2055d096L, 0x4c74b497L,
                    0xfc3b684bL, 0x901ada4bL, 0x920cb425L, 0xfe2ded25L,
                    0x710f7222L, 0x1d2eeec6L, 0xd4963911L, 0xb8b77763L,
                    0x524234b8L, 0x3e63a3e5L, 0x1128b26cL, 0x7d09c9a6L,
                    0x309df106L, 0x5cbc7c87L, 0xf45f7883L, 0x987ebe43L,
                    0x963ebc41L, 0xfa1fdf21L, 0x73167610L, 0x1f37f7c4L,
                    0x01829338L, 0x6da363b6L, 0x38c8e1acL, 0x54e9298fL,
                    0x246dd8e6L, 0x484c8c93L, 0xfe276c73L, 0x9206c649L,
                    0x9302b639L, 0xff23e324L, 0x7188732cL, 0x1da969c6L,
                    0x00cd91a6L, 0x6cec2cb7L, 0xec7748d3L, 0x8056965bL,
                    0x9a2aa469L, 0xf60bcb2dL, 0x751c7a04L, 0x193dfdc2L,
                    0x02879532L, 0x6ea666b5L, 0xed524a99L, 0x8173b35aL,
                    0x4ea00d7cL, 0x228141f9L, 0x1f59ae8eL, 0x7378b8a8L,
                    0xe3bd5747L, 0x8f9c5c54L, 0x9dcfaba3L, 0xf1ee2e2aL,
                    0xa2f6d5d1L, 0xced71715L, 0x697242d8L, 0x055393deL,
                    0x0cb0895cL, 0x609151bbL, 0x3e51ec9eL, 0x5270b089L)

  val CON256 = Array(0x0221947eL, 0x6e00c0b5L, 0xed014a3fL, 0x8120e05aL,
                    0x9a91a51fL, 0xf6b0702dL, 0xa159d28fL, 0xcd78b816L,
                    0xbcbde947L, 0xd09c5c0bL, 0xb24ff4a3L, 0xde6eae05L,
                    0xb536fa51L, 0xd917d702L, 0x62925518L, 0x0eb373d5L,
                    0x094082bcL, 0x6561a1beL, 0x3ca9e96eL, 0x5088488bL,
                    0xf24574b7L, 0x9e64a445L, 0x9533ba5bL, 0xf912d222L,
                    0xa688dd2dL, 0xcaa96911L, 0x6b4d46a6L, 0x076cacdcL,
                    0xd9b72353L, 0xb596566eL, 0x80ca91a9L, 0xeceb2b37L,
                    0x786c60e4L, 0x144d8dcfL, 0x043f9842L, 0x681edeb3L,
                    0xee0e4c21L, 0x822fef59L, 0x4f0e0e20L, 0x232feff8L,
                    0x1f8eaf20L, 0x73af6fa8L, 0x37ceffa0L, 0x5bef2f80L,
                    0x23eed7e0L, 0x4fcf0f94L, 0x29fec3c0L, 0x45df1f9eL,
                    0x2cf6c9d0L, 0x40d7179bL, 0x2e72ccd8L, 0x42539399L,
                    0x2f30ce5cL, 0x4311d198L, 0x2f91cf1eL, 0x43b07098L,
                    0xfbd9678fL, 0x97f8384cL, 0x91fdb3c7L, 0xfddc1c26L,
                    0xa4efd9e3L, 0xc8ce0e13L, 0xbe66ecf1L, 0xd2478709L,
                    0x673a5e48L, 0x0b1bdbd0L, 0x0b948714L, 0x67b575bcL,
                    0x3dc3ebbaL, 0x51e2228aL, 0xf2f075ddL, 0x9ed11145L,
                    0x417112deL, 0x2d5090f6L, 0xcca9096fL, 0xa088487bL,
                    0x8a4584b7L, 0xe664a43dL, 0xa933c25bL, 0xc512d21eL,
                    0xb888e12dL, 0xd4a9690fL, 0x644d58a6L, 0x086cacd3L,
                    0xde372c53L, 0xb216d669L, 0x830a9629L, 0xef2beb34L,
                    0x798c6324L, 0x15ad6dceL, 0x04cf99a2L, 0x68ee2ebL)

  def doubleSwap(x: Numeric128): Numeric128 = {
    val (n0, n1, n2, n3) = x
    (((n0 << 7) & 0xffffff80L) | (n1 >>> 25), //n0.last25 - n1.firstseven
    ((n1 << 7) & 0xffffff80L) | (n3 & 0x7f),  //n1.last25 - n3.last7
    (n0 & 0xfe000000L) | (n2 >>> 7),          //n0.first7 - n2.first25
    ((n2 << 25) & 0xfe000000L) | (n3 >>> 7))  //n2.last7  - n3.first25
  }

  def from128Key(baseKey: Numeric128): (Numeric128, Array[Long]) = {
    def loop(l: Numeric128, i: Int, acc: Array[Long]): Array[Long] =
      if (i == 9) acc
      else {
        val t = l ^ (CON128(24 + 4 * i), CON128(24 + 4 * i + 1), CON128(24 + 4 * i  + 2), CON128(24 + 4 * i  + 3))
        val rks = if(i % 2 == 0) t else t ^ baseKey

        loop(doubleSwap(l), i + 1, acc ++ rks.toArray)
      }

    (baseKey, loop(GFN.gfn4(baseKey, CON128.slice(0, 24), 12), 0, Array()))
  }

  def from192Key(baseKey: Numeric192): (Numeric128, Array[Long]) = ???
  def from256Key(baseKey: Numeric256): (Numeric128, Array[Long]) = ???

}
