package com.restaurant.manager.repositoryimpl;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbtractRepositoryImpl<T extends Serializable> {

	private Class<T> c;
	
	Transaction transaction = null;
	Session session = null;
	@Autowired
	private SessionFactory sessionFactory;
	
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
			entity = session.get(c, id);
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
}
