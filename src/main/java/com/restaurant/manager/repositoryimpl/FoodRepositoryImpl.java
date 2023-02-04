package com.restaurant.manager.repositoryimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.restaurant.manager.model.Food;
import com.restaurant.manager.repository.FoodRepository;

@Repository
public class FoodRepositoryImpl implements FoodRepository {
	Session session = null;
	Transaction transaction = null;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean createFood(Food food) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(food);
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
	public Food detailFood(int id) {
		Food food = new Food();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			food = session.get(Food.class, id);
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
		return food;
	}

	@Override
	public boolean updateFood(Food food) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(food);
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
	public boolean deleteFood(int id) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery("DELETE com.restaurant.manager.model.Food f WHERE f.id = :id").setParameter("id", id)
					.executeUpdate();
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
	public boolean changeStatusFood(int id, int status) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery("UPDATE com.restaurant.manager.model.Food f SET f.status = :status WHERE f.id = :id")
					.setParameter("id", id).setParameter("status", status).executeUpdate();
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Food> listFood(int restaurantId, int branchId) {
		List<Food> listFood = new ArrayList<>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			if (branchId == 0) {
				listFood = session.createQuery(
						"FROM com.restaurant.manager.model.Food f WHERE f.restaurant.id = :restaurantId AND f.branch.id = null")
						.setParameter("restaurantId", restaurantId).list();
			} else {
				listFood = session.createQuery(
						"FROM com.restaurant.manager.model.Food f WHERE f.restaurant.id = :restaurantId AND f.branch.id = :branchId")
						.setParameter("restaurantId", restaurantId).setParameter("branchId", branchId).list();
			}
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
		return listFood;
	}
}
