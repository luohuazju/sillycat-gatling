package base

import scala.tools.nsc.io.File
import scala.tools.nsc.io.Path

object IDEPathHelper extends Environment{
  val mavenSourcesDir =   projectHome + "/src/main/scala"
  val resultsFolder = reportHome
}