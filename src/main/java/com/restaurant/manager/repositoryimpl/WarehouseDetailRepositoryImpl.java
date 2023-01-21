package com.restaurant.manager.repositoryimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.manager.model.WarehouseDetail;
import com.restaurant.manager.repository.WarehouseDetailRepository;

@Repository
@Transactional
public class WarehouseDetailRepositoryImpl implements WarehouseDetailRepository {
	Session session = null;
	Transaction transaction = null;
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean createWarehouseDetail(WarehouseDetail warehouseDetail) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(warehouseDetail);
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
	public float getTotalAmountByMaterialCode(String materialCode) {
		float totalAmount = 0;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			totalAmount = (float) session.createQuery(
					"SELECT w.totalAmount FROM com.restaurant.manager.model.WarehouseDetail w WHERE w.materialCode = :materialCode")
					.setParameter("materialCode", materialCode).uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.commit();
			}
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}
		return totalAmount;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WarehouseDetail> listWarehouseDetail(int warehouseId) {
		List<WarehouseDetail> listWarehouse = new ArrayList<>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			listWarehouse = session
					.createQuery(
							"FROM com.restaurant.manager.model.WarehouseDetail w WHERE w.warehouse.id = :warehouseId")
					.setParameter("warehouseId", warehouseId).list();
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
		return listWarehouse;
	}
}
