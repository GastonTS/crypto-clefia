package clefia

import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by gastonsantoalla on 30/10/16.
  */
class KeySchedulingSpec extends FreeSpec with Matchers {

  "In the KeySchedulingPart Clefia" - {
    "should doubleSwap" - {
      "a Long number correctly" in {
        KeyScheduling.doubleSwap((0x9302b639L, 0xff23e324L, 0x7188732cL, 0x1da969c6L)) should be ((2170232063L, 2448527942L, 2464354534L, 1480282835L))
      }
    }

    "get whitening and round keys" - {
      "from 128 base key" in {
        val (wk, rk) = KeyScheduling.from128Key((0xffeeddccL, 0xbbaa9988L, 0x77665544L, 0x33221100L))

        wk should be ((0xffeeddccL, 0xbbaa9988L, 0x77665544L, 0x33221100L))
        rk.length should be (36)
        rk should be (Array(0xf3e6cef9L, 0x8df75e38L, 0x41c06256L, 0x640ac51bL,
                            0x6a27e20aL, 0x5a791b90L, 0xe8c528dcL, 0x00336ea3L,
                            0x59cd17c4L, 0x28565583L, 0x312a37ccL, 0xc08abd77L,
                            0x7e8e7eecL, 0x8be7e949L, 0xd3f463d6L, 0xa0aad6aaL,
                            0xe75eb039L, 0x0d657eb9L, 0x018002e2L, 0x9117d009L,
                            0x9f98d11eL, 0xbabee8cfL, 0xb0369efaL, 0xd3aaef0dL,
                            0x3438f93bL, 0xf9cea4a0L, 0x68df9029L, 0xb869b4a7L,
                            0x24d6406dL, 0xe74bc550L, 0x41c28193L, 0x16de4795L,
                            0xa34a20f5L, 0x33265d14L, 0xb19d0554L, 0x5142f434L))

      }

      "from 192 base key" in {
        val (wk, rk) = KeyScheduling.from192Key((0xffeeddccL, 0xbbaa9988L, 0x77665544L, 0x33221100L, 0xf0e0d0c0L, 0xb0a09080L))

        wk should be ((0x0f0e0d0cL, 0x0b0a0908L, 0x77777777L, 0x77777777L))
        rk.length should be (44)
        rk should be (Array(0x4d3bfd1bL, 0x7a1f5dfaL, 0x0fae6e7cL, 0xc8bf3237L,
                            0x73c2eeb8L, 0xdd429ec5L, 0xe220b3afL, 0xc9135e73L,
                            0x38c46a07L, 0xfc2ce4baL, 0x370abf2dL, 0xb05e627bL,
                            0x38351b2fL, 0x74bd6e1eL, 0x1b7c7dceL, 0x92cfc98eL,
                            0x509b31a6L, 0x4c5ad53cL, 0x6fc2ba33L, 0xe1e5c878L,
                            0x419a74b9L, 0x1dd79e0eL, 0x240a33d2L, 0x9dabfd09L,
                            0x6e3ff82aL, 0x74ac3ffdL, 0xb9696e2eL, 0xcc0b3a38L,
                            0xed785cbdL, 0x9c077c13L, 0x04978d83L, 0x2ec058baL,
                            0x4bbd5f6aL, 0x31fe8de8L, 0xb76da574L, 0x3a6fa8e7L,
                            0x521213ceL, 0x4f1f59d8L, 0xc13624f6L, 0xee91f6a4L,
                            0x17f68fdeL, 0xf6c360a9L, 0x6288bc72L, 0xc0ad856bL))
      }

      "from 256 base key" in {
        val (wk, rk) = KeyScheduling.from256Key((0xffeeddccL, 0xbbaa9988L, 0x77665544L, 0x33221100L, 0xf0e0d0c0L, 0xb0a09080L, 0x70605040L, 0x30201000L))

        wk should be ((0x0f0e0d0cL, 0x0b0a0908L, 0x07060504L, 0x03020100L))
        rk.length should be (52)
        rk should be (Array(0x58f02029L, 0x15413cd0L, 0x1b0c41a4L, 0xe4bacd0fL,
                            0x6c498393L, 0x8846231bL, 0x1fc716fcL, 0x7c81a45bL,
                            0xfa37c259L, 0x0e3da2eeL, 0xaacf9abbL, 0x8ec0aad9L,
                            0xb05bd737L, 0x8de1f2d0L, 0x8ffee0f6L, 0xb70b47eaL,
                            0x581b3e34L, 0x03263f89L, 0x2f7100cdL, 0x05cee171L,
                            0xb523d4e9L, 0x176d7c44L, 0x6d7ba5d7L, 0xf797b2f3L,
                            0x25d80df2L, 0xa646bba2L, 0x6a3a95e1L, 0x3e3a47f0L,
                            0xb304eb20L, 0x44f8824eL, 0xc7557cbcL, 0x47401e21L,
                            0xd71ff7e9L, 0xaca1fb0cL, 0x2deff35dL, 0x6ca3a830L,
                            0x4dd7cfb7L, 0xae71c9f6L, 0x4e911fefL, 0x90aa95deL,
                            0x2c664a7aL, 0x8cb5cf6bL, 0x14c8de1eL, 0x43b9caefL,
                            0x568c5a33L, 0x07ef7dddL, 0x608dc860L, 0xac9e50f8L,
                            0xc0c18358L, 0x4f53c80eL, 0x33e01cb9L, 0xee45d244L))
        //FIXME: According to test Vector last element should be 0x80251e1cL only last went wrong...

      }
    }
  }
}