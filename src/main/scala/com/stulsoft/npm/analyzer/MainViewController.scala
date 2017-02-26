/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.npm.analyzer

import javafx.beans.value.{ChangeListener, ObservableValue}

import com.stulsoft.npm.analyzer

import scala.concurrent.Future
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
      analyzeButton.disable = true
      import scala.concurrent.ExecutionContext.Implicits.global
      val f: Future[String] = Future {
        try {
          new Analyzer(directoryText.text.value).analyze
        } catch {
          case e: Exception => s"Error: ${e.getMessage}"
        }
      }

      f foreach (result => {
        resultText.text = result
        analyzer.startStage.scene().setCursor(Cursor.Default)
        analyzeButton.disable = false
      })
    }
  }
}
