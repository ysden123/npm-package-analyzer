/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.npm.analyzer

import com.stulsoft.npm.analyzer

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafxml.core.{FXMLView, NoDependencyResolver}

/**
  * @author Yuriy Stul
  */
object MainView extends JFXApp {
  val root = FXMLView(getClass.getResource("/fxml/main.fxml"), NoDependencyResolver)

  stage = new PrimaryStage() {
    title = "(c) StulSoft: npm package analyzer"
    scene = new Scene(root)
  }
  analyzer.startStage = stage
}
