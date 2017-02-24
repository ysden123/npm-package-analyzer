/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.npm.analyzer

import java.io.IOException

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafxml.core.{FXMLView, NoDependencyResolver}

/**
  * @author Yuriy Stul
  */
object MainView extends JFXApp {
  val resourceName = "/fxml/main.fxml"
  val resource = getClass.getResource(resourceName)
  if (resource == null) {
    throw new IOException(s"Cannot load resource: $resourceName")
  }

  val root = FXMLView(resource, NoDependencyResolver)

  stage = new PrimaryStage() {
    title = "(c) StulSoft: npm package analyzer"
    scene = new Scene(root)
  }

  /*
    stage = new PrimaryStage {
      title = "ScalaFX Hello World"
      scene = new Scene {
        fill = Black
        content = new HBox {
          padding = Insets(20)
          children = Seq(
            new Text {
              text = "Hello "
              style = "-fx-font-size: 48pt"
              fill = new LinearGradient(
                endX = 0,
                stops = Stops(PaleGreen, SeaGreen))
            },
            new Text {
              text = "World!!!"
              style = "-fx-font-size: 48pt"
              fill = new LinearGradient(
                endX = 0,
                stops = Stops(Cyan, DodgerBlue)
              )
              effect = new DropShadow {
                color = DodgerBlue
                radius = 25
                spread = 0.25
              }
            }
          )
        }
      }
    }
  */
}
