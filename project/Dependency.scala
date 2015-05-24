import sbt._

object Dependencies {
  val gatling_version = "2.1.6"

  val baseDeps = Seq (
    "com.typesafe.scala-logging" % "scala-logging_2.11"        % "3.1.0",
    "io.gatling.highcharts"      % "gatling-charts-highcharts" % gatling_version,
    "io.gatling"                 % "gatling-test-framework"    % gatling_version,
    "com.datastax.cassandra"     % "cassandra-driver-core"     % "2.1.0"
  )
}
