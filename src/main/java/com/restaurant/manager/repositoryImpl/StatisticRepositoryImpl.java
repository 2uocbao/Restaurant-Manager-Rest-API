package com.restaurant.manager.repositoryImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.restaurant.manager.model.ReportEntity;
import com.restaurant.manager.repository.StatisticRepository;

@Repository
public class StatisticRepositoryImpl implements StatisticRepository {
	Session session = null;
	Transaction transaction = null;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean createStatistic(ReportEntity statistic) {
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
	public ReportEntity detailStatistic(String restaurantId, String branchId) {
		ReportEntity statistic = new ReportEntity();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			statistic = (ReportEntity) session.createQuery(
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
	public List<ReportEntity> listStatistic(String restaurantId, String branchId) {
		List<ReportEntity> listStatistic = new ArrayList<>();
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
