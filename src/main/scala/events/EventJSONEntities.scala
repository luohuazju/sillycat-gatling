package events

/**
 * Created by carl on 7/25/14.
 */
object EventJSONEntities {

  def pushCallbackEventJson(deviceId:String,latitude:String, longitude:String, appId:String, campaignId:String) = {
    """{
      'apiVersion': '1.0',
      'minorSdkVersion': 5,
      'appId': '""" + appId + """',
      'majorSdkVersion': 2,
      'revisionSdkVersion': 2,
      'longitude': """ + longitude + """,
      'timeZone': 'America/Boise',
      'callbackData': '{\"campaignId\":""" + campaignId + """,\"pushMillis\":1402939800004,\"offset\":24}',
      'latitude': """ + latitude + """,
      'eventType': 'PUSH_CALLBACK',
      'accuracy': 40,
      'deviceId': '""" + deviceId + """'
      }"""
  }

}
