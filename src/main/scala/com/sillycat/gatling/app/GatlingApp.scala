package com.sillycat.gatling.app

import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder
import io.gatling.core.config.GatlingConfiguration

/**
 * Created by carl on 5/21/15.
 */
object GatlingApp extends App{

  val propsBuilder = new GatlingPropertiesBuilder

  propsBuilder.resultsDirectory("target/results")
  //propsBuilder.binariesDirectory("target/scala-2.11/classes")
  propsBuilder.binariesDirectory(this.jarOfClass(this.getClass).getOrElse(""))
  propsBuilder.dataDirectory("target/data")
  //propsBuilder.simulationClass("com.sillycat.gatling.basic.TaobaoHomePage")

  val props = propsBuilder.build

  props foreach println

  Gatling.fromMap(props)

  def jarOfClass(cls: Class[_]): Option[String] = {
    val uri = cls.getResource("/" + cls.getName.replace('.', '/') + ".class")
    if (uri != null) {
      val uriStr = uri.toString
      if (uriStr.startsWith("jar:file:")) {
        Some(uriStr.substring("jar:file:".length, uriStr.indexOf("lib") + "lib".length))
      } else if(uriStr.startsWith("file:")){
        Some(uriStr.substring("file:".length,uriStr.indexOf("classes") + "classes".length))
      } else {
        None
      }
    } else {
      None
    }
  }

}
