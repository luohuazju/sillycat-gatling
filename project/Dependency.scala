import sbt._

object Dependencies {
  val gatling_version = "2.0.0-RC2"

  val log4j   = "log4j"                   % "log4j"                     % "1.2.17"
  val gatling = "io.gatling.highcharts"   % "gatling-charts-highcharts" % gatling_version

  val baseDeps = Seq (
    log4j,
    gatling
  )
}
