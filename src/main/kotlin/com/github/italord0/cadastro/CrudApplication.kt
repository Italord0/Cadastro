package com.github.italord0.cadastro

import com.github.italord0.cadastro.controller.HomeController
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class CrudApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(CrudApplication::class.java.getResource("home-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 850.0, 600.0)
        stage.title = "Hello!"
        stage.scene = scene
        stage.show()

        fxmlLoader.getController<HomeController>().loadData()
    }
}

fun main() {
    Application.launch(CrudApplication::class.java)
}