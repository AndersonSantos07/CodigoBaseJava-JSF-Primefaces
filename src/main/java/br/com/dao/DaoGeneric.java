package br.com.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.engine.spi.ExecuteUpdateResultCheckStyle;

import br.com.jpautil.JPAUtil;

public class DaoGeneric<T> {

	public void salvar(T entidade) {

		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		try {
			entityTransaction.begin();
			entityManager.persist(entidade);
			entityTransaction.commit();

		} catch (Exception e) {
			if (entityTransaction != null && entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			System.out.print(e);

		} finally {
			entityManager.close();
		}

	}

	public T merge(T entidade) {

		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		try {
			entityTransaction.begin();
			T retorno = entityManager.merge(entidade);
			entityTransaction.commit();
			return retorno;

		} catch (Exception e) {
			if (entityTransaction != null && entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			System.out.print(e);

		} finally {
			entityManager.close();
		}

		return entidade;

	}

	public void delete(T entidade) {

		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		try {
			entityTransaction.begin();
			entityManager.remove(entidade);
			entityTransaction.commit();

		} catch (Exception e) {
			if (entityTransaction != null && entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			System.out.print(e);

		} finally {
			entityManager.close();
		}

	}

	public void deletePorId(T entidade) {

		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		try {
			entityTransaction.begin();
			Object id = JPAUtil.getPrimaryKey(entidade);
			System.out.print(id);
			entityManager.createQuery("delete from " + entidade.getClass().getCanonicalName() + " where id= " + id)
					.executeUpdate();
			entityTransaction.commit();

		} catch (Exception e) {
			if (entityTransaction != null && entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			System.out.print(e);

		} finally {
			entityManager.close();
		}
	}

	public List<T> getListEntity(Class<T> entidade) {

		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		try {
			entityTransaction.begin();

			List<T> retorno = entityManager.createQuery("from " + entidade.getName())
					.getResultList();
			entityTransaction.commit();
			return retorno;

		} catch (Exception e) {
			if (entityTransaction != null && entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			System.out.print(e);

		} finally {
			entityManager.close();
		}

		return null;

	}

}
