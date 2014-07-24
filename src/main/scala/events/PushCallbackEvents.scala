package events

import base.Environment
import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.core.Predef.bootstrap._
import com.excilys.ebi.gatling.http.Predef._

class PushCallbackEvents extends Simulation with Environment {

  var host = ""
  if(area == "prod"){
    host = ".api.digby.com"
  }else{
    host = "." + "api." + area + ".digby.com"
  }

  val url = protocol + "://" + brand + host

  // number of seconds over which users should gradually start
  val rampSeconds = 1 //

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

  val callbackDevice = exec(http("push_callback_events")
    .post("/api/brands/"+ brand + "/events")
    .body(pushCallbackEventJson(deviceId, brandAppId, campaginId))
    .headers(headers)
    .check(status.is(200))
    .check(bodyString))

  val multiScn = scenario("Push Callback Events Scenario")
    .repeat(repeatTimes){ exec(callbackDevice) }

  setUp(
    multiScn.users(numUsers).ramp(rampSeconds).protocolConfig(httpConf)
  )

  def pushCallbackEventJson(deviceId: String, appId: String, campaginId:String ) = {
    """{
      'apiVersion': '1.0',
      'minorSdkVersion': 5,
      'appId': '""" + appId + """',
      'majorSdkVersion': 2,
      'revisionSdkVersion': 2,
      'longitude': 30.359960,
      'timeZone': 'America/Boise',
      'callbackData': '{\"campaignId\":""" + campaginId + """,\"pushMillis\":1402939800004,\"offset\":24}',
      'latitude': -97.741921,
      'eventType': 'PUSH_CALLBACK',
      'accuracy': 40,
      'deviceId': '""" + deviceId + """'
      }"""
  }

}
