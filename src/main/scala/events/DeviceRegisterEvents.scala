package events

import com.excilys.ebi.gatling.core.Predef._
import base.Environment
import com.excilys.ebi.gatling.http.Predef._
import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._
import com.excilys.ebi.gatling.jdbc.Predef._
import com.excilys.ebi.gatling.http.Headers.Names._
import akka.util.duration._
import bootstrap._
import com.excilys.ebi.gatling.core.structure.ScenarioBuilder
import akka.dispatch.{ExecutionContext, Future}

import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._
import com.excilys.ebi.gatling.http.Headers.Names._
import akka.util.duration._
import bootstrap._

/**
 * Created by carl on 7/28/14.
 */
class DeviceRegisterEvents extends Simulation with Environment {
  var host = ""
  if(area == "prod"){
    host = ".api.digby.com"
  }else{
    host = "." + "api." + area + ".digby.com"
  }

  val url = protocol + "://" + brand + host

  val rampSeconds = 1 //4400

  val httpConf = httpConfig
    .baseURL(url)
    .acceptCharsetHeader("ISO-8859-1,utf-8;q=0.7,*;q=0.7")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
    .disableFollowRedirect

  val headers =
    Map("Accept" -> "application/json, text/javascript, */*; q=0.01",
      "Keep-Alive" -> "180",
      "X-Requested-With" -> "XMLHttpRequest",
      "Content-type" -> "application/json")

  val registerDevice = exec(http("register_device")
    .post("/api/brands/" + brand + "/events")
    .body(EventJSONEntities.regesterDeviceEventJson("${deviceId}", appId, "${regKey}"))
    .headers(headers)
    .check(status.is(200))
    .check(bodyString))

  //val deviceRegFeed = EventFeeds.deviceStableFeeder(5)
  val deviceRegFeed = csv("serveraws1_fails.csv")

  val multiScn = scenario("Push Callback Events Scenario")
    .repeat(repeatTimes){ feed(deviceRegFeed).exec(registerDevice) }

  setUp(
    multiScn.users(numUsers).ramp(rampSeconds).protocolConfig(httpConf)
  )

}
