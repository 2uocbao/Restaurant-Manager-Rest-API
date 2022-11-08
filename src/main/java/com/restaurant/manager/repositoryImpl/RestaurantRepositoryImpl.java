package com.restaurant.manager.repositoryImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.manager.model.Restaurants;
import com.restaurant.manager.repository.RestaurantRepository;

@Repository
@Transactional
public class RestaurantRepositoryImpl implements RestaurantRepository {
	Transaction transaction = null;
	Session session = null;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean createRestaurant(Restaurants restaurant) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(restaurant);
			transaction.commit();
			successful = true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				if (session.isOpen())
					session.close();
			}
		}
		return successful;
	}

	@Override
	public boolean updateRestaurant(Restaurants restaurant) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(restaurant);
			transaction.commit();
			successful = true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				if (session.isOpen())
					session.close();
			}
		}
		return successful;
	}

	@Override
	public Restaurants detailRestaurant(String id) {
		Restaurants restaurant = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			restaurant = (Restaurants) session
					.createQuery("FROM com.restaurant.manager.model.Restaurants r WHERE r.id = :id")
					.setParameter("id", id).uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			} 
			e.printStackTrace();
		} finally {
			if (session != null) {
				if (session.isOpen())
					session.close();
			}
		}
		return restaurant;
	}

	@Override
	public boolean changeStatusRestaurant(String id, int status) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery(
					"UPDATE com.restaurant.manager.model.Restaurants r SET r.status = :status WHERE r.id = :id")
					.setParameter("status", status).setParameter("id", id).executeUpdate();
			transaction.commit();
			successful = true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				if (session.isOpen())
					session.close();
			}
		}
		return successful;
	}

	@Override
	public Restaurants getRestaurantbyPhone(String phone) {
		Restaurants restaurants = new Restaurants();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			restaurants = (Restaurants) session
					.createQuery("FROM com.restaurant.manager.model.Restaurants r WHERE r.phone = :phone")
					.setParameter("phone", phone).uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				if (session.isOpen())
					session.close();
			}
		}
		return restaurants;
	}

	@Override
	public Restaurants getRestaurantbyEmail(String email) {
		Restaurants restaurant = new Restaurants();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			restaurant = (Restaurants) session
					.createQuery("FROM com.restaurant.manager.model.Restaurants r WHERE r.email = :email")
					.setParameter("email", email).uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				if (session.isOpen())
					session.close();
			}
		}
		return restaurant;
	}

	@Override
	public int getStatusById(String id) {
		int status = 0;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			status = (Integer) session
					.createQuery("SELECT r.status FROM com.restaurant.manager.model.Restaurants r WHERE r.id = :id")
					.setParameter("id", id).uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				if (session.isOpen())
					session.close();
			}
		}
		return status;
	}
}
