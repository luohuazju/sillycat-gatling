package com.sillycat.gatling.app

/**
 * Created by carl on 5/21/15.
 */
object IDEPathHelper extends Environment{
  val sourcesDir =   projectHome + "/src/main/scala"
  val resultsFolder = reportHome
  val dataFolder = projectHome + "/data"
}
