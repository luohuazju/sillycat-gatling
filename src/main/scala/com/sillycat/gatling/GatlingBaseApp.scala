package com.sillycat.gatling


/**
 * Created by carl on 5/21/15.
 */
trait GatlingBaseApp {

  def executeTask(params : Array[String]): Unit ={

  }

  def getAppName():String = {
    "defaultJob"
  }


}
