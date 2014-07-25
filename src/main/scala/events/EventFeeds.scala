package events

import scala.collection.immutable.StreamIterator
import scala.util.Random
import java.util.concurrent.atomic.AtomicInteger
import com.excilys.ebi.gatling.core.feeder._

/**
 * Created by carl on 7/25/14.
 */
object EventFeeds {

  def deviceStableFeeder(num: Int ) = new Feeder[String]{

    var current = 0

    def hexString = "111111%s".format(current)

    def getRegKey = "register_key%s".format(current)

    override def hasNext = {
      if(current >= num){
        false
      }else{
        true
      }
    }

    override def next: Map[String, String] = {
      this.synchronized {
        current = current + 1
      }
      Map("deviceId" -> hexString, "regKey" -> getRegKey)
    }
  }

}
