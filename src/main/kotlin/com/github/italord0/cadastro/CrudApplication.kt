package com.github.italord0.cadastro

import com.github.italord0.cadastro.controller.HomeController
import com.github.italord0.cadastro.util.SampleUsers
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence

class CrudApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(CrudApplication::class.java.getResource("home-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 850.0, 600.0)
        stage.title = "Hello!"
        stage.scene = scene
        stage.show()

        fxmlLoader.getController<HomeController>().loadData()

        val emf = Persistence.createEntityManagerFactory("cadastro-jpa")
        val em = emf.createEntityManager()
        em.transaction.begin()
        em.persist(SampleUsers.users[0])
        em.transaction.commit()

    }
}

fun main() {
    Application.launch(CrudApplication::class.java)
}