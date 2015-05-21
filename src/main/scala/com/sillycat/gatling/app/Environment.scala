package com.sillycat.gatling.app

import com.typesafe.config.ConfigFactory

/**
 * Created by carl on 5/21/15.
 */
trait Environment {

  val config = ConfigFactory.load

  val projectHome = config.getString("project.home")
  val reportHome = config.getString("report.home")

}
