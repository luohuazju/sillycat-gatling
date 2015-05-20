import sbt._

object Dependencies {
  val gatling_version = "2.1.2"

  val log4j   = "log4j"                   % "log4j"                     % "1.2.17"
  var gatling_charts = "io.gatling.highcharts" % "gatling-charts-highcharts" % gatling_version % "test"
  var gatling_test = "io.gatling"            % "gatling-test-framework"    % gatling_version % "test"

  val baseDeps = Seq (
    log4j,
    gatling_charts,
    gatling_test
  )
}
