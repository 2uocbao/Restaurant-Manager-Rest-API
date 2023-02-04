package com.restaurant.manager.repositoryimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.restaurant.manager.model.FoodDetail;
import com.restaurant.manager.repository.FoodDetailRepository;

@Repository
public class FoodDetailRepositoryImpl implements FoodDetailRepository {
	Session session = null;
	Transaction transaction = null;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean createFoodDetail(FoodDetail foodDetail) {
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
			if (session.isOpen())
				session.close();
		}
		return successful;
	}

	@Override
	public boolean deleteFoodDetail(int id) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery("DELETE com.restaurant.manager.model.FoodDetail f WHERE f.food.id = :id")
					.setParameter("id", id).executeUpdate();
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
	public List<FoodDetail> listFoodDetail(int foodId) {
		List<FoodDetail> listFoodDetail = new ArrayList<>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			listFoodDetail = session
					.createQuery("FROM com.restaurant.manager.model.FoodDetail f WHERE f.food.id = :FoodId")
					.setParameter("FoodId", foodId).list();
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
		return listFoodDetail;
	}

	@Override
	public boolean updateFoodDetail(FoodDetail foodDetail) {
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
			if (session.isOpen())
				session.close();
		}
		return successful;
	}

	@Override
	public boolean deleteMaterialInFood(int foodId, int materialId) {
		boolean success = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery(
					"DELETE com.restaurant.manager.model.FoodDetail f WHERE f.food.id = :foodId AND f.material.id = :materialId")
					.setParameter("foodId", foodId).setParameter("materialId", materialId).executeUpdate();
			transaction.commit();
			success = true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}
		return success;
	}

	@Override
	public FoodDetail detailFood(int foodId, int materialId) {
		FoodDetail foodDetail = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			foodDetail = (FoodDetail) session.createQuery(
					"FROM com.restaurant.manager.model.FoodDetail f WHERE f.food.id = :foodId AND f.material.id = :materialId")
					.setParameter("foodId", foodId).setParameter("materialId", materialId).uniqueResult();
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
		return foodDetail;
	}
}
