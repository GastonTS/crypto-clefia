import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by gastonsantoalla on 30/10/16.
  */
class GFNSpec extends FreeSpec with Matchers {

  "With GFN Clefia" - {
    "should handle bytes" - {
      "getting bytes" in {GFN
        GFN.getBytes(37162340L) should be (Array(2,55,13,100))
      }

      "concatenating bytes" in {
        GFN.concatBytes(Array(1, 69, 51, 56)) should be (21312312L)
      }

      "concat should be inverse of get" in {
        GFN.getBytes(GFN.concatBytes(Array(11, 52, 255, 200))) should be (Array(11, 52, 255, 200))
        GFN.concatBytes(GFN.getBytes(393829L)) should be (393829L)
      }
    }

    "should multiply over GF(2^8)" - {
      "two numbers" in {
        GFN.mult(22, 2) should be (44)
      }

      "even with overflow" in {
        GFN.mult(220, 200) should be (133)
      }
    }

    "should multiply square matrix" - {
      "against vectors" in {
        GFN.squareMatrixXVector(GFN.M0, Array(0xa1, 0x47, 0x34, 0x5a)) should be(Vector(62, 213, 177, 210))
      }
    }

    "should get TValues" - {
      "from 3000677607 and 3851680404" in {
        GFN.getTValues(3000677607L, 3851680404L) should be (Array(87, 73, 78, 115))
      }
    }

    "should apply f0 correctly" - {
      "from 3000677607 and 3851680404" in {
        GFN.f0(3000677607L, 3851680404L) should be (24698974L)
      }
    }

    "should apply f1 correctly" - {
      "from 3000677607 and 3851680404" in {
        GFN.f1(3000677607L, 3851680404L) should be (4096204530L)
      }
    }

    "should apply gfn correctly" - {
      "using gfn4 with 6 rounds" in {
        val input = Array(0x094082bcL, 0x6561a1beL, 0x3ca9e96eL, 0x5088488bL)
        val keys = Array(0xf56b7aebL, 0x994a8a42L, 0x96a4bd75L, 0xfa854521L, 0x735b768aL, 0x1f7abac4L, 0xd5bc3b45L, 0xb99d5d62L, 0x52d73592L, 0x3ef636e5L, 0xc57a1ac9L, 0xa95b9b72L)

        GFN.gfn4H(input, keys, 6) should be (Array(1171750116L, 3609734244L, 3688607617L, 3692111635L))
        GFN.gfn4(input, keys, 6) should be (Array(1171750116L, 3609734244L, 3688607617L, 3692111635L))
      }

      "using gfn8 with 6 rounds" in {
        val input = Array(0x417112deL, 0x2d5090f6L, 0xcca9096fL, 0xa088487bL, 0x8a4584b7L, 0xe664a43dL, 0xa933c25bL, 0xc512d21eL)
        val keys = Array(0x524234b8L, 0x3e63a3e5L, 0x1128b26cL, 0x7d09c9a6L,
          0x309df106L, 0x5cbc7c87L, 0xf45f7883L, 0x987ebe43L,
          0x963ebc41L, 0xfa1fdf21L, 0x73167610L, 0x1f37f7c4L,
          0x01829338L, 0x6da363b6L, 0x38c8e1acL, 0x54e9298fL,
          0x246dd8e6L, 0x484c8c93L, 0xfe276c73L, 0x9206c649L,
          0x9302b639L, 0xff23e324L, 0x7188732cL, 0x1da969c6L)

        GFN.gfn8H(input, keys, 6) should be (Array(4006647101L, 3894415849L, 2675728801L, 1500553729L, 516395372L, 1502064311L, 1307738312L, 3183946091L))
        GFN.gfn8(input, keys, 6) should be (Array(4006647101L, 3894415849L, 2675728801L, 1500553729L, 516395372L, 1502064311L, 1307738312L, 3183946091L))
      }

      "using gfn4Inverse with 6 rounds" in {
        val input = Array(0xee0e4c21L, 0x822fef59L, 0x4f0e0e20L, 0x232feff8L)
        val keys = Array(0x23eed7e0L, 0x4fcf0f94L, 0x29fec3c0L, 0x45df1f9eL, 0x2cf6c9d0L, 0x40d7179bL, 0x2e72ccd8L, 0x42539399L, 0x2f30ce5cL, 0x4311d198L, 0x2f91cf1eL, 0x43b07098L)

        GFN.gfn4Inverse(input, keys, 6) should be (Array(1585078746L, 1407736314L, 3451340836L, 3397394014L))
      }

      "assuring gfn4 and gfn4Inverse are inverse functions" in {
        val input = Array(0xee0e4c21L, 0x822fef59L, 0x4f0e0e20L, 0x232feff8L)
        val keys = Array(0x23eed7e0L, 0x4fcf0f94L, 0x29fec3c0L, 0x45df1f9eL, 0x2cf6c9d0L, 0x40d7179bL, 0x2e72ccd8L, 0x42539399L, 0x2f30ce5cL, 0x4311d198L, 0x2f91cf1eL, 0x43b07098L)

        GFN.gfn4Inverse(GFN.gfn4(input, keys, 6), keys, 6) should be (input)
        GFN.gfn4Inverse(GFN.gfn4H(input, keys, 6), keys, 6) should be (input)
      }
    }
  }
}
