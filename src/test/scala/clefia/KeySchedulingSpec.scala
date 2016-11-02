package clefia

import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by gastonsantoalla on 30/10/16.
  */
class KeySchedulingSpec extends FreeSpec with Matchers {

  "In the KeySchedulingPart Clefia" - {
    "should doubleSwap" - {
      "a Int number correctly" in {
        KeyScheduling.doubleSwap((0x9302b639, 0xff23e324, 0x7188732c, 0x1da969c6)) should be ((-2124735233, -1846439354, -1830612762, 1480282835))
      }
    }

    "get whitening and round keys" - {
      "from 128 base key" in {
        val Keys(wk, rk) = KeyScheduling.scheduleKeys((0xffeeddcc, 0xbbaa9988, 0x77665544, 0x33221100))

        wk should be ((0xffeeddcc, 0xbbaa9988, 0x77665544, 0x33221100))
        rk.length should be (36)
        rk should be (Array(0xf3e6cef9, 0x8df75e38, 0x41c06256, 0x640ac51b,
                            0x6a27e20a, 0x5a791b90, 0xe8c528dc, 0x00336ea3,
                            0x59cd17c4, 0x28565583, 0x312a37cc, 0xc08abd77,
                            0x7e8e7eec, 0x8be7e949, 0xd3f463d6, 0xa0aad6aa,
                            0xe75eb039, 0x0d657eb9, 0x018002e2, 0x9117d009,
                            0x9f98d11e, 0xbabee8cf, 0xb0369efa, 0xd3aaef0d,
                            0x3438f93b, 0xf9cea4a0, 0x68df9029, 0xb869b4a7,
                            0x24d6406d, 0xe74bc550, 0x41c28193, 0x16de4795,
                            0xa34a20f5, 0x33265d14, 0xb19d0554, 0x5142f434))

      }

      "from 192 base key" in {
        val Keys(wk, rk) = KeyScheduling.scheduleKeys((0xffeeddcc, 0xbbaa9988, 0x77665544, 0x33221100, 0xf0e0d0c0, 0xb0a09080))

        wk should be ((0x0f0e0d0c, 0x0b0a0908, 0x77777777, 0x77777777))
        rk.length should be (44)
        rk should be (Array(0x4d3bfd1b, 0x7a1f5dfa, 0x0fae6e7c, 0xc8bf3237,
                            0x73c2eeb8, 0xdd429ec5, 0xe220b3af, 0xc9135e73,
                            0x38c46a07, 0xfc2ce4ba, 0x370abf2d, 0xb05e627b,
                            0x38351b2f, 0x74bd6e1e, 0x1b7c7dce, 0x92cfc98e,
                            0x509b31a6, 0x4c5ad53c, 0x6fc2ba33, 0xe1e5c878,
                            0x419a74b9, 0x1dd79e0e, 0x240a33d2, 0x9dabfd09,
                            0x6e3ff82a, 0x74ac3ffd, 0xb9696e2e, 0xcc0b3a38,
                            0xed785cbd, 0x9c077c13, 0x04978d83, 0x2ec058ba,
                            0x4bbd5f6a, 0x31fe8de8, 0xb76da574, 0x3a6fa8e7,
                            0x521213ce, 0x4f1f59d8, 0xc13624f6, 0xee91f6a4,
                            0x17f68fde, 0xf6c360a9, 0x6288bc72, 0xc0ad856b))
      }

      "from 256 base key" in {
        val Keys(wk, rk) = KeyScheduling.scheduleKeys((0xffeeddcc, 0xbbaa9988, 0x77665544, 0x33221100, 0xf0e0d0c0, 0xb0a09080, 0x70605040, 0x30201000))

        wk should be ((0x0f0e0d0c, 0x0b0a0908, 0x07060504, 0x03020100))
        rk.length should be (52)
        rk should be (Array(0x58f02029, 0x15413cd0, 0x1b0c41a4, 0xe4bacd0f,
                            0x6c498393, 0x8846231b, 0x1fc716fc, 0x7c81a45b,
                            0xfa37c259, 0x0e3da2ee, 0xaacf9abb, 0x8ec0aad9,
                            0xb05bd737, 0x8de1f2d0, 0x8ffee0f6, 0xb70b47ea,
                            0x581b3e34, 0x03263f89, 0x2f7100cd, 0x05cee171,
                            0xb523d4e9, 0x176d7c44, 0x6d7ba5d7, 0xf797b2f3,
                            0x25d80df2, 0xa646bba2, 0x6a3a95e1, 0x3e3a47f0,
                            0xb304eb20, 0x44f8824e, 0xc7557cbc, 0x47401e21,
                            0xd71ff7e9, 0xaca1fb0c, 0x2deff35d, 0x6ca3a830,
                            0x4dd7cfb7, 0xae71c9f6, 0x4e911fef, 0x90aa95de,
                            0x2c664a7a, 0x8cb5cf6b, 0x14c8de1e, 0x43b9caef,
                            0x568c5a33, 0x07ef7ddd, 0x608dc860, 0xac9e50f8,
                            0xc0c18358, 0x4f53c80e, 0x33e01cb9, 0x80251e1c))
      }
    }
  }
}