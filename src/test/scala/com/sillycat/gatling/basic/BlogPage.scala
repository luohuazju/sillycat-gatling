package com.sillycat.gatling.basic

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._

/**
 * Created by carl on 5/20/15.
 */
class BlogPage extends Simulation {
  // your code starts here
  val httpConf = http
    .baseURL("http://sillycat.iteye.com")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  val scn = scenario("Blog Scenario")
    .exec(http("Blog Page")
    .get("/blog/2213025"))

  setUp(
    scn.inject(atOnceUsers(5))
  ).protocols(httpConf)
  // your code ends here
}