package com.github.italord0.cadastro

import com.github.italord0.cadastro.controller.HomeController
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import java.awt.Taskbar
import java.awt.Toolkit
import java.io.File

class CrudApplication : Application() {

    override fun init() {
        val file = File("users.db")

        if (!file.exists()) {
            file.createNewFile()
        }
    }

    override fun start(stage: Stage) {

        val appIcon = Image(CrudApplication::class.java.getResourceAsStream("drawable/dev.png"))

        if (Taskbar.isTaskbarSupported()) {
            val taskbar = Taskbar.getTaskbar()

            if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
                val toolkit = Toolkit.getDefaultToolkit()
                val toolkitImage = toolkit.getImage(CrudApplication::class.java.getResource("drawable/dev.png"))
                taskbar.setIconImage(toolkitImage)
            }
        }

        val fxmlLoader = FXMLLoader(CrudApplication::class.java.getResource("layout/home-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 850.0, 600.0)
        stage.title = "Sistema de cadastro"
        stage.scene = scene
        stage.icons.add(appIcon)
        stage.show()

        fxmlLoader.getController<HomeController>().loadData()

    }
}