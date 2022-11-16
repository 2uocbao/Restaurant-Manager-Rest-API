package com.restaurant.manager.repositoryImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.restaurant.manager.model.foodDetail;
import com.restaurant.manager.repository.FoodDetailRepository;

@Repository
public class FoodDetailRepositoryImpl implements FoodDetailRepository {
	Session session = null;
	Transaction transaction = null;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean createFoodDetail(foodDetail foodDetail) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(foodDetail);
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
	public boolean deleteFoodDetail(int id) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery("DELETE com.restaurant.manager.model.foodDetail f WHERE f.food.id = :id")
					.setParameter("id", id).executeUpdate();
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

	@SuppressWarnings("unchecked")
	@Override
	public List<foodDetail> listFoodDetail(int foodId) {
		List<foodDetail> listFoodDetail = new ArrayList<>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			listFoodDetail = session
					.createQuery("FROM com.restaurant.manager.model.foodDetail f WHERE f.food.id = :foodId")
					.setParameter("foodId", foodId).list();
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
		return listFoodDetail;
	}

	@Override
	public boolean updateFoodDetail(foodDetail foodDetail) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(foodDetail);
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
	public boolean deleteFoodDetailByMateCode(int foodId, String materialCode) {
		boolean success = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery(
					"DELETE com.restaurant.manager.model.foodDetail f WHERE f.food.id = :foodId AND f.materialCode = :materialCode")
					.setParameter("foodId", foodId).setParameter("materialCode", materialCode).executeUpdate();
			transaction.commit();
			success = true;
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
		return success;
	}

	@Override
	public foodDetail detailFood(int foodId, String materialCode) {
		foodDetail foodDetail = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			foodDetail = (foodDetail) session.createQuery(
					"FROM com.restaurant.manager.model.foodDetail f WHERE f.food.id = :foodId AND f.materialCode = :materialCode")
					.setParameter("foodId", foodId).setParameter("materialCode", materialCode).uniqueResult();
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
		return foodDetail;
	}
}
