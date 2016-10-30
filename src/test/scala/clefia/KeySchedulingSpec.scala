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
    }
  }
}
