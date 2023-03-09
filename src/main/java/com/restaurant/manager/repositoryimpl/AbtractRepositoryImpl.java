package com.restaurant.manager.repositoryimpl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbtractRepositoryImpl<T extends Serializable> {

	private Class<T> entityClass;

	Transaction transaction;
	Session session;
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	protected AbtractRepositoryImpl() {
		this.entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	public T create(T entity) {
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(entity);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}
		return entity;
	}

	public T update(T entity) {
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(entity);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}
		return entity;
	}

	public T getDetail(int id) {
		T entity = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			entity = session.get(this.entityClass, id);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}
		return entity;
	}

	@SuppressWarnings("unchecked")
	public List<T> getList() {
		List<T> ts = new ArrayList<>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			ts = session.createQuery("From" + entityClass.getName()).list();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}
		return ts;
	}

	@SuppressWarnings("unchecked")
	public T callQueryForEntity(String query) {
		T entity = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			entity = (T) session.createQuery(query).uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}
		return entity;
	}

	@SuppressWarnings("unchecked")
	public List<T> callQueryForListEntity(String query) {
		List<T> ts = new ArrayList<>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			ts = session.createQuery(query).list();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}
		return ts;
	}
}
