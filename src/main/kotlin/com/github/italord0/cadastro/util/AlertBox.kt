package com.github.italord0.cadastro.util

import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.stage.Modality
import javafx.stage.Stage

object AlertBox {
    fun display(alertTitle: String, alertMessage: String, buttonMessage: String = "OK") {
        val alertLabel = Label()
        alertLabel.text = alertMessage

        val alertStage = Stage()
        with(alertStage) {
            initModality(Modality.APPLICATION_MODAL)
            title = alertTitle
            minWidth = 250.0
            minHeight = 250.0
        }
        val closeButton = Button(buttonMessage)
        closeButton.setOnAction {
            alertStage.close()
        }
        val vBox = VBox()
        with(vBox) {
            children.addAll(alertLabel, closeButton)
            alignment = Pos.CENTER
        }

        val scene = Scene(vBox)
        alertStage.scene = scene
        alertStage.showAndWait()
    }
}