package com.restaurant.manager.repositoryImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.manager.model.Brand;
import com.restaurant.manager.repository.BrandRepository;

@Repository
@Transactional
public class BrandRepositoryImpl implements BrandRepository {
	Transaction transaction = null;
	Session session = null;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean createBrand(Brand brand) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(brand);
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
	public Brand detailBrand(String restaurantId) {
		Brand brand = new Brand();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			brand = (Brand) session
					.createQuery("FROM com.restaurant.manager.model.Brand b WHERE b.restaurantId = :restaurantId")
					.setParameter("restaurantId", restaurantId).uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				if (session.isOpen())
					session.close();
			}
		}
		return brand;
	}

	@Override
	public boolean updateBrand(Brand brand) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(brand);
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
	public boolean changeStatusBrand(String restaurantId, boolean status) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery(
					"UPDATE com.restaurant.manager.model.Brand b SET b.status = :status WHERE b.restaurantId = :restaurantId")
					.setParameter("restaurantId", restaurantId).setParameter("status", status).executeUpdate();
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
	public boolean getStatusbyRestaurantId(String restaurantId) {
		boolean status = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			status = (boolean) session.createQuery(
					"SELECT b.status FROM com.restaurant.manager.model.Brand b WHERE b.restaurantId = :restaurantId")
					.setParameter("restaurantId", restaurantId).uniqueResult();
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
