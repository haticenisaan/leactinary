package util

import javax.inject.Inject

import play.api.Configuration

/**
  * Created by deliganli on 20.09.2017. All rights reserved
  */
class ConfigUtil @Inject()(conf: Configuration) {
  val websterApiKey: String = conf.get[String]("api.keys.mirriamWebster")
}
