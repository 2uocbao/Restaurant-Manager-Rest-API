package com.restaurant.manager.repositoryImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.restaurant.manager.model.Statistics;
import com.restaurant.manager.repository.StatisticRepository;

@Repository
public class StatisticRepositoryImpl implements StatisticRepository {
	Session session = null;
	Transaction transaction = null;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean createStatistic(Statistics statistic) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(statistic);
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
	public Statistics detailStatistic(String restaurantId, String branchId) {
		Statistics statistic = new Statistics();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			statistic = (Statistics) session.createQuery(
					"FROM com.restaurant.manager.model.Statistics s WHERE s.restaurantId = :restaurantId AND s.branchId = :branchId")
					.setParameter("restaurantId", restaurantId).setParameter("branchId", branchId).uniqueResult();
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
		return statistic;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Statistics> listStatistic(String restaurantId, String branchId) {
		List<Statistics> listStatistic = new ArrayList<>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			listStatistic = session.createQuery(
					"FROM com.restaurant.manager.model.Statistics s WHERE s.restaurantId = :restaurantId AND s.branchId = :branchId")
					.setParameter("restaurantId", restaurantId).setParameter("branchId", branchId).list();
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
		return listStatistic;
	}

}
