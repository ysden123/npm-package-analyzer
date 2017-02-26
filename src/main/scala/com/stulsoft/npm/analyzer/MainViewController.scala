/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.npm.analyzer

import javafx.beans.value.{ChangeListener, ObservableValue}

import com.stulsoft.npm.analyzer

import scalafx.event.ActionEvent
import scalafx.scene.Cursor
import scalafx.scene.control.{Button, TextArea, TextField}
import scalafx.stage.DirectoryChooser
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
  analyzeButton.disable = true

  directoryText.textProperty.addListener(new ChangeListener[String] {
    override def changed(observable: ObservableValue[_ <: String], oldValue: String, newValue: String): Unit = {
      analyzeButton.disable = newValue == null || newValue.isEmpty
    }
  })

  def onChooseDir(event: ActionEvent): Unit = {
    val dirChooser = new DirectoryChooser
    val dir = dirChooser.showDialog(analyzer.startStage)
    if (dir != null) directoryText.text = dir.getAbsoluteFile.toString
  }

  def onAnalyze(event: ActionEvent): Unit = {
    if (!directoryText.text.value.isEmpty) {
      analyzer.startStage.scene().setCursor(Cursor.Wait)
      new Thread {
        override def run(): Unit = {
          try {
            resultText.text = new Analyzer(directoryText.text.value).analyze
          } catch {
            case e: Exception => resultText.text = s"Error: ${e.getMessage}"
          }
          analyzer.startStage.scene().setCursor(Cursor.Default)
        }
      }.start()
    }
  }
}
