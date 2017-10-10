package com.kek

import com.github.tminglei.slickpg.ExPostgresProfile

trait LictionaryProfile extends ExPostgresProfile {
  override val api = LictionaryAPI

  object LictionaryAPI extends API  {

  }
}

object LictionaryProfile extends LictionaryProfile