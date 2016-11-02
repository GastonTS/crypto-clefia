package clefia

import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by gastonsantoalla on 30/10/16.
  */
class GFNSpec extends FreeSpec with Matchers {

  "A GFN" - {
    "should get TValues" - {
      "from 3000677607 and 3851680404" in {
        GFN.getTValues(-1294289689, -443286892) should be ((87, 73, 78, 115))
      }
    }

    "should apply f0 correctly" - {
      "from 3000677607 and 3851680404" in {
        GFN.f0(-1294289689, -443286892) should be (24698974)
      }
    }

    "should apply f1 correctly" - {
      "from 3000677607 and 3851680404" in {
        GFN.f1(-1294289689, -443286892) should be (-198762766)
      }
    }

    "should apply gfn correctly" - {
      "using gfn4 with 6 rounds" in {
        val input = (0x094082bc, 0x6561a1be, 0x3ca9e96e, 0x5088488b)
        val keys = Array(0xf56b7aeb, 0x994a8a42, 0x96a4bd75, 0xfa854521, 0x735b768a, 0x1f7abac4, 0xd5bc3b45, 0xb99d5d62, 0x52d73592, 0x3ef636e5, 0xc57a1ac9, 0xa95b9b72)

        GFN.gfn4H(input, keys, 6) should be ((1171750116, -685233052, -606359679, -602855661))
        GFN.gfn4(input, keys, 6) should be ((1171750116, -685233052, -606359679, -602855661))
      }

      "using gfn8 with 6 rounds" in {
        val input = (0x417112de, 0x2d5090f6, 0xcca9096f, 0xa088487b, 0x8a4584b7, 0xe664a43d, 0xa933c25b, 0xc512d21e)
        val keys = Array(0x524234b8, 0x3e63a3e5, 0x1128b26c, 0x7d09c9a6,
                        0x309df106, 0x5cbc7c87, 0xf45f7883, 0x987ebe43,
                        0x963ebc41, 0xfa1fdf21, 0x73167610, 0x1f37f7c4,
                        0x01829338, 0x6da363b6, 0x38c8e1ac, 0x54e9298f,
                        0x246dd8e6, 0x484c8c93, 0xfe276c73, 0x9206c649,
                        0x9302b639, 0xff23e324, 0x7188732c, 0x1da969c6)

        GFN.gfn8H(input, keys, 6) should be ((-288320195, -400551447, -1619238495, 1500553729, 516395372, 1502064311, 1307738312, -1111021205))
        GFN.gfn8(input, keys, 6) should be ((-288320195, -400551447, -1619238495, 1500553729, 516395372, 1502064311, 1307738312, -1111021205))
      }

      "using gfn4Inverse with 6 rounds" in {
        val input = (0xee0e4c21, 0x822fef59, 0x4f0e0e20, 0x232feff8)
        val keys = Array(0x23eed7e0, 0x4fcf0f94, 0x29fec3c0, 0x45df1f9e, 0x2cf6c9d0, 0x40d7179b, 0x2e72ccd8, 0x42539399, 0x2f30ce5c, 0x4311d198, 0x2f91cf1e, 0x43b07098)

        GFN.gfn4Inverse(input, keys, 6) should be ((1585078746, 1407736314, -843626460, -897573282))
      }

      "assuring gfn4 and gfn4Inverse are inverse functions" in {
        val input = (0xee0e4c21, 0x822fef59, 0x4f0e0e20, 0x232feff8)
        val keys = Array(0x23eed7e0, 0x4fcf0f94, 0x29fec3c0, 0x45df1f9e, 0x2cf6c9d0, 0x40d7179b, 0x2e72ccd8, 0x42539399, 0x2f30ce5c, 0x4311d198, 0x2f91cf1e, 0x43b07098)

        GFN.gfn4Inverse(GFN.gfn4(input, keys, 6), keys, 6) should be (input)
        GFN.gfn4Inverse(GFN.gfn4H(input, keys, 6), keys, 6) should be (input)
      }
    }
  }
}
