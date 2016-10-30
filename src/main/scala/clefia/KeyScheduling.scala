package clefia

/**
  * Created by gastonsantoalla on 30/10/16.
  */
object KeyScheduling {

  def doubleSwap(x: String): String = {
    x.substring(7, 64) + x.substring(121, 128) + x.substring(0, 7) + x.substring(64, 121)
  }

}
