package base

import com.typesafe.config.ConfigFactory

/**
 * Created by carl on 7/23/14.
 */
trait Environment {
  val config = ConfigFactory.load

  val projectHome = config.getString("project.home")
  val reportHome = config.getString("report.home")
}
