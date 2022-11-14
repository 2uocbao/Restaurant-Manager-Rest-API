package com.restaurant.manager.repositoryImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.restaurant.manager.model.ReportEntity;
import com.restaurant.manager.repository.ReportRepository;

@Repository
public class ReportRepositoryImpl implements ReportRepository {
	Session session = null;
	Transaction transaction = null;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean createReport(ReportEntity reportEntity) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(reportEntity);
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
	public ReportEntity detailReport(String restaurantId, String branchId) {
		ReportEntity reportEntity = new ReportEntity();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			reportEntity = (ReportEntity) session.createQuery(
					"FROM com.restaurant.manager.model.Reports s WHERE s.restaurantId = :restaurantId AND s.branchId = :branchId")
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
		return reportEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReportEntity> listReport(String restaurantId, String branchId) {
		List<ReportEntity> listReport = new ArrayList<>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			listReport = session.createQuery(
					"FROM com.restaurant.manager.model.Reports s WHERE s.restaurantId = :restaurantId AND s.branchId = :branchId")
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
		return listReport;
	}

}
