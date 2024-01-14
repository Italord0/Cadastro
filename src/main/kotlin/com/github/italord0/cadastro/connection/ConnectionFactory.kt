package com.github.italord0.cadastro.connection

import javax.persistence.EntityManager
import javax.persistence.Persistence

object ConnectionFactory {

    private val entityManagerFactory = Persistence.createEntityManagerFactory("cadastro-jpa")

    fun getEntityManager(): EntityManager {
        return entityManagerFactory.createEntityManager()
    }

}