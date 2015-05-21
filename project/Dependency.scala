import sbt._

object Dependencies {
  val gatling_version = "2.1.6"

  val baseDeps = Seq (
    "log4j"                   % "log4j"                     % "1.2.17",
    "org.slf4j"               % "slf4j-api"                 % "1.7.10",
    "io.gatling.highcharts"   % "gatling-charts-highcharts" % gatling_version % "test",
    "io.gatling"              % "gatling-test-framework"    % gatling_version % "test",
    "org.scala-lang"          % "scala-reflect"             % "2.10.0"
  )
}
