package apps

import base.Environment
import com.excilys.ebi.gatling.core.Predef._
import events.EventFeeds
import java.util.concurrent.atomic.AtomicInteger
import org.joda.time.DateTime
import java.io.File
import scala.io.Source

/**
 * Created by carl on 7/28/14.
 */
object FailDeviceFinder extends Environment {

  def main(args: Array[String]) {
  val deviceRegFeed = EventFeeds.deviceStableFeeder(5)

  val src = Source.fromFile(new File("userfiles/data/serveraws1_device.csv")).getLines
  //val headerLine = src.take(1).next

  val csvMap = src.toList

  printToFile(new File("userfiles/data/serveraws1_fails.csv"))( p =>{
    p.println("deviceId,regKey")
    while(deviceRegFeed.hasNext) {
    val n = deviceRegFeed.next
    val deviceId = n.getOrElse("deviceId","null")
    val regKey = n.getOrElse("regKey","null")
    if(!csvMap.contains(deviceId)){
      p.println(deviceId + "," + regKey)
    }
    }
  })

  }


  def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit) {
    val p = new java.io.PrintWriter(f)
    try { op(p) } finally { p.close() }
  }

}
