package clefia
/**
  * Created by gastonsantoalla on 30/10/16.
  */

object Numeric {
  type Numeric32 = (Int, Int, Int, Int)
  type Numeric128 = (Long, Long, Long, Long)
  type Numeric192 = (Long, Long, Long, Long, Long, Long)
  type Numeric256 = (Long, Long, Long, Long, Long, Long, Long, Long)

  implicit class gf8Algebra(num: Int) {
    def gf8Mult(other: Int): Int = {
      def rMult(acc: Int, v1: Int, v2: Int): Int =
        if (v2 == 0) acc & 255
        else {
          val nAcc = if ( (v2 & 1) !=0 ) acc ^ v1 else acc
          val nv1 = v1 << 1
          if ((nv1 & 256)!=0)
            rMult(nAcc, nv1 ^ 0x1d, v2 >>> 1)
          else
            rMult(nAcc, nv1, v2 >>> 1)
        }

      rMult(0, num, other)
    }
  }

  implicit class MatrixAlgebra(matrix: Array[Int]) {
    def squareMatrixXVector(vector: Array[Int]): Array[Int] = {
      val n = vector.length
      (0 until n map { i =>
        (0 until n).map(j =>
          matrix(i*n + j).gf8Mult(vector(j))).reduce(_^_)
      }).toArray
    }
  }

  implicit class LongBytesOps(num: Long) {
    def getBytes: Numeric32 = Array(num >>> 24, num >>> 16, num >>> 8, num).map( b => (b & 0xff).toInt).toNumeric32
    def concatBytes(other: Long): Long = num * 256 + other
    def neg32 = ~num & 0xFFFFFFFFL
  }

  implicit class IntArrayToNumerics(a: Array[Int]) {
    def toNumeric32: Numeric32 = (a(0), a(1), a(2), a(3))
    def concatBytes: Long =  a.map(_.toLong).reduce(_.concatBytes(_))
  }

  implicit class LongArrayToNumerics(a: Array[Long]) {
    def toNumeric128: Numeric128 = (a(0), a(1), a(2), a(3))
    def toNumeric256: Numeric256 = (a(0), a(1), a(2), a(3), a(4), a(5), a(6), a(7))
  }

  implicit class Numeric32Ops(num: Numeric32) {
    def toArray: Array[Int] = Array(num._1, num._2, num._3 ,num._4)
  }

  implicit class Numeric128Ops(num: Numeric128) {
    def ^(other: Numeric128): Numeric128 = (num._1 ^ other._1, num._2 ^ other._2, num._3 ^ other._3, num._4 ^ other._4)
    def toArray: Array[Long] = Array(num._1, num._2, num._3 ,num._4)
    def concat(other: Numeric128) = (num._1, num._2, num._3 ,num._4, other._1, other._2, other._3 , other._4)
  }

  implicit class Numeric256Ops(num: Numeric256) {
    def toArray: Array[Long] = Array(num._1, num._2, num._3 ,num._4, num._5, num._6, num._7, num._8)
    def first128: Numeric128 =  (num._1, num._2, num._3 ,num._4)
    def last128: Numeric128 =  (num._5, num._6, num._7, num._8)
  }

}
