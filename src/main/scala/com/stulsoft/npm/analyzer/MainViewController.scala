/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.npm.analyzer

import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, TextArea, TextField}
import scalafx.stage.{DirectoryChooser, Stage}
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
  def onChooseDir(event: ActionEvent): Unit = {
    val dirChooser = new DirectoryChooser
    val dir = dirChooser.showDialog(new Stage())
    if (dir != null) {
      directoryText.text = dir.getAbsoluteFile.toString
    }
  }

  def onAnalyze(event: ActionEvent): Unit = {
    if (!directoryText.text.value.isEmpty)
      resultText.text = new Analyzer(directoryText.text.value).analyze
  }
}
