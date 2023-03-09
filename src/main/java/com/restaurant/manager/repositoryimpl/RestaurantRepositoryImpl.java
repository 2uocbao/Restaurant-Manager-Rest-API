package com.restaurant.manager.repositoryimpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.manager.model.Restaurant;
import com.restaurant.manager.repository.RestaurantRepository;

@Repository
@Transactional
public class RestaurantRepositoryImpl implements RestaurantRepository {
	Transaction transaction = null;
	Session session = null;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean createRestaurant(Restaurant restaurant) {
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
			if (session.isOpen())
				session.close();
		}
		return successful;
	}

	@Override
	public boolean updateRestaurant(Restaurant restaurant) {
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
			if (session.isOpen())
				session.close();
		}
		return successful;

	}

	@Override
	public Restaurant detailRestaurant(int id) {
		Restaurant restaurant = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			restaurant = session.get(Restaurant.class, id);
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
		return restaurant;
	}

	@Override
	public boolean changeStatusRestaurant(int id, int status) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery(
					"UPDATE com.restaurant.manager.model.Restaurant r SET r.status = :status WHERE r.id = :id")
					.setParameter("status", status).setParameter("id", id).executeUpdate();
			transaction.commit();
			successful = true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}
		return successful;
	}

	@Override
	public Restaurant getRestaurantbyPhone(String phone) {
		Restaurant restaurants = new Restaurant();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			restaurants = (Restaurant) session
					.createQuery("FROM com.restaurant.manager.model.Restaurant r WHERE r.phone = :phone")
					.setParameter("phone", phone).uniqueResult();
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
		return restaurants;
	}

	@Override
	public Restaurant getRestaurantbyEmail(String email) {
		Restaurant restaurant = new Restaurant();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			restaurant = (Restaurant) session
					.createQuery("FROM com.restaurant.manager.model.Restaurant r WHERE r.email = :email")
					.setParameter("email", email).uniqueResult();
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
		return restaurant;
	}

	@Override
	public int getStatusById(int id) {
		int status = 0;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			status = (Integer) session
					.createQuery("SELECT r.status FROM com.restaurant.manager.model.Restaurant r WHERE r.id = :id")
					.setParameter("id", id).uniqueResult();
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
		return status;
	}
}
