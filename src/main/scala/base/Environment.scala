package base

import com.typesafe.config.ConfigFactory

/**
 * Created by carl on 7/23/14.
 */
trait Environment {
  val config = ConfigFactory.load

  val projectHome = config.getString("project.home")
  val reportHome = config.getString("report.home")

  val protocol = config.getString("connection.protocol")
  val area = config.getString("connection.area")

  val brand = config.getString("brand.code")
  val appId = config.getString("brand.appId")

  val numUsers = config.getInt("pushcallbackevent.numUsers")
  val repeatTimes = config.getInt("pushcallbackevent.repeatTimes")
  val deviceId = config.getString("pushcallbackevent.deviceId")
  val campaignId = config.getString("pushcallbackevent.campaginId")


}
