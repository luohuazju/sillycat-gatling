import sbt._

object Dependencies {
  val gatling_version = "2.1.6"

  val baseDeps = Seq (
    "log4j"                   % "log4j"                     % "1.2.17",
    "io.gatling.highcharts"   % "gatling-charts-highcharts" % gatling_version % "test",
    "io.gatling"              % "gatling-test-framework"    % gatling_version % "test"
  )
}
