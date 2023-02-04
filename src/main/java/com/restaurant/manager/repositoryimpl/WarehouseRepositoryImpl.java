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
			if (session.isOpen())
				session.close();
		}
		return successful;
	}

	@Override
	public Warehouse detailWarehouse(int restaurantId, int branchId, int materialId) {
		Warehouse wareHouse = new Warehouse();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			if (branchId == 0) {
				wareHouse = (Warehouse) session.createQuery(
						"FROM com.restaurant.manager.model.Warehouse w WHERE w.employee.restaurant.id = :restaurantid AND w.employee.branch = null AND w.material.id = :materialId")
						.setParameter("materialId", materialId).setParameter("restaurantid", restaurantId)
						.uniqueResult();
			} else {
				wareHouse = (Warehouse) session.createQuery(
						"FROM com.restaurant.manager.model.Warehouse w WHERE w.employee.restaurant.id = :restaurantid AND w.employee.branch.id = :branchId AND w.material.id = :materialId")
						.setParameter("materialId", materialId).setParameter("restaurantid", restaurantId)
						.setParameter("branchId", branchId).uniqueResult();
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
		return wareHouse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Warehouse> listWarehouse(int restaurantId, int branchId) {
		List<Warehouse> warehouses = new ArrayList<>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			if (branchId == 0) {
				warehouses = session.createQuery(
						"FROM com.restaurant.manager.model.Warehouse w WHERE w.employee.restaurant.id = :restaurantId AND w.employee.branch = null")
						.setParameter("restaurantId", restaurantId).list();
			} else {
				warehouses = session.createQuery(
						"FROM com.restaurant.manager.model.Warehouse w WHERE w.employee.restaurant.id = :restaurantId AND w.employee.branch.id = :branchId")
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
		return warehouses;
	}

}
