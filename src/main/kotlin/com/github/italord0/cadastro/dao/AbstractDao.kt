package com.github.italord0.cadastro.dao

import com.github.italord0.cadastro.connection.ConnectionFactory
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

abstract class AbstractDAO<T>(private val entityType: Class<T>) {

    private val entityManager = ConnectionFactory.getEntityManager()

    fun insert(entity: T): T {
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

    fun findById(id: Long): T? {
        return entityManager.find(entityType, id)
    }

    fun listAll(): List<T> {
        val criteriaBuilder = entityManager.criteriaBuilder
        val criteriaQuery: CriteriaQuery<T> = criteriaBuilder.createQuery(entityType)
        val root = criteriaQuery.from(entityType)

        criteriaQuery.select(root)

        return entityManager.createQuery(criteriaQuery).resultList
    }

    fun count(): Long {
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder
        val criteriaQuery: CriteriaQuery<Long> = criteriaBuilder.createQuery(Long::class.java)
        val root: Root<T> = criteriaQuery.from(entityType)

        criteriaQuery.select(criteriaBuilder.count(root))

        return entityManager.createQuery(criteriaQuery).singleResult
    }
}