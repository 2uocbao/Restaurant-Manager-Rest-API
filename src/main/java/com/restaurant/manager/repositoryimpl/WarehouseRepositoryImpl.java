package com.restaurant.manager.repositoryimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.restaurant.manager.model.Warehouse;
import com.restaurant.manager.repository.WarehouseRepository;

@Repository
public class WarehouseRepositoryImpl implements WarehouseRepository {
	Session session = null;
	Transaction transaction = null;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean createWarehouse(Warehouse warehouse) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(warehouse);
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
	public Warehouse detailWarehouse(String employeeId, String materialCode) {
		Warehouse wareHouse = new Warehouse();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			wareHouse = (Warehouse) session.createQuery(
					"FROM com.restaurant.manager.model.Warehouse w WHERE w.employee.id = :employeeId AND materialCode = :materialCode")
					.setParameter("materialCode", materialCode).setParameter("employeeId", employeeId).uniqueResult();
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
		return wareHouse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Warehouse> listWarehouse(String employeeId) {
		List<Warehouse> warehouses = new ArrayList<>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			warehouses = session
					.createQuery("FROM com.restaurant.manager.model.Warehouse w WHERE w.employee.id = :employeeId")
					.setParameter("employeeId", employeeId).list();
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
		return warehouses;
	}

}
