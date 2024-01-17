package com.github.italord0.cadastro.dao

import com.github.italord0.cadastro.connection.ConnectionFactory
import com.github.italord0.cadastro.model.User
import org.hibernate.type.EntityType
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

abstract class AbstractDAO<T>(private val entityType: Class<T>) {

    fun insert(entity: T): T {
        val entityManager = ConnectionFactory.getEntityManager()

        try {
            entityManager.transaction.begin()
            entityManager.persist(entity)
            entityManager.transaction.commit()
        } catch (e: Exception) {
            entityManager.transaction.rollback()
            throw e
        } finally {
            entityManager.close()
        }

        return entity
    }

    fun update(entity: T): T {
        val entityManager = ConnectionFactory.getEntityManager()

        try {
            entityManager.transaction.begin()
            entityManager.merge(entity)
            entityManager.transaction.commit()
        } catch (e: Exception) {
            entityManager.transaction.rollback()
            throw e
        } finally {
            entityManager.close()
        }

        return entity
    }

    fun delete(entity: T) {
        val entityManager = ConnectionFactory.getEntityManager()

        try {
            entityManager.transaction.begin()
            entityManager.remove(entityManager.merge(entity))
            entityManager.transaction.commit()
        } catch (e: Exception) {
            entityManager.transaction.rollback()
            throw e
        } finally {
            entityManager.close()
        }
    }

    fun delete(id: Long) {
        val entityManager = ConnectionFactory.getEntityManager()

        try {
            entityManager.transaction.begin()
            val entityToRemove = entityManager.find(entityType, id)
            if (entityToRemove != null) {
                entityManager.remove(entityToRemove)
            }
            entityManager.transaction.commit()
        } catch (e: Exception) {
            entityManager.transaction.rollback()
            throw e
        } finally {
            entityManager.close()
        }
    }

    fun findById(id: Long): T? {
        val entityManager = ConnectionFactory.getEntityManager()

        return entityManager.find(entityType, id)
    }

    fun listAll(): List<T> {
        val entityManager = ConnectionFactory.getEntityManager()

        return try {
            val query = entityManager.createQuery("FROM ${entityType.simpleName}", entityType)
            query.resultList
        } catch (e: Exception) {
            System.err.println("Error fetching entities: $e")
            emptyList()  // Return an empty list in case of an exception
        } finally {
            entityManager.close()
        }
    }


    fun count(): Long {
        val entityManager = ConnectionFactory.getEntityManager()

        return try {
            val query = entityManager.createQuery("SELECT COUNT(*) FROM ${entityType.simpleName}", Long::class.java)
            query.singleResult
        } catch (e: Exception) {
            System.err.println("Error counting entities: $e")
            0L
        } finally {
            entityManager.close()
        }
    }
}