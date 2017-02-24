/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.npm.analyzer

import scalafx.Includes._
import scalafx.application.Platform
import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, TextArea, TextField}
import scalafxml.core.macros.sfxml

/**
  * Main view controller
  *
  * @author Yuriy Stul
  */
@sfxml
class MainViewController(directoryText: TextField,
                         chooseDirButton: Button,
                         analyzeButton: Button,
                         resultText: TextArea
                        ) {
  def onChooseDir(event:ActionEvent): Unit ={
    resultText.text = resultText.text.value + "Cheese!\n"
  }

  def onAnalyze(event:ActionEvent): Unit ={
    resultText.text = resultText.text.value + "Analyze!\n"
  }
}
