package com.restaurant.manager.repositoryimpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.manager.model.Roles;
import com.restaurant.manager.repository.RoleRepository;

@Repository
@Transactional
public class RoleRepositoryImpl implements RoleRepository {

	Transaction transaction = null;
	Session session = null;
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Roles getRole(int id) {
		Roles roles = new Roles();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			roles = session.get(Roles.class, id);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session.isOpen())
				session.close();
		}
		return roles;
	}
}
