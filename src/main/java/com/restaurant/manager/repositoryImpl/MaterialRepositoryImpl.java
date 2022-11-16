package com.restaurant.manager.repositoryImpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.restaurant.manager.model.Material;
import com.restaurant.manager.repository.MaterialRepository;

@Repository
public class MaterialRepositoryImpl implements MaterialRepository {
	Session session = null;
	Transaction transaction = null;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean createMaterial(Material material) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(material);
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
	public boolean updateMaterial(Material material) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(material);
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
	public Material detailMaterial(String code, String restaurantId, String branchId) {
		Material material = new Material();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			if (branchId.equals("")) {
				material = (Material) session.createQuery(
						"FROM com.restaurant.manager.model.Material i WHERE i.code = :code AND i.restaurant.id = :restaurantId AND i.branch.id = null")
						.setParameter("code", code).setParameter("restaurantId", restaurantId).uniqueResult();
			} else {
				material = (Material) session.createQuery(
						"FROM com.restaurant.manager.model.Material i WHERE i.code = :code AND i.branch.id = :branchId")
						.setParameter("code", code).setParameter("branchId", branchId).uniqueResult();
			}
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
		return material;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Material> listMaterial(String restaurantId, String branchId) {
		List<Material> listMaterial = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			if (branchId.equals("")) {
				listMaterial = session.createQuery(
						"FROM com.restaurant.manager.model.Material m WHERE m.restaurant.id = :restaurantId AND m.branch.id = null")
						.setParameter("restaurantId", restaurantId).list();
			} else {
				listMaterial = session.createQuery(
						"FROM com.restaurant.manager.model.Material m WHERE m.restaurant.id = :restaurantId AND m.branch.id = :branchId")
						.setParameter("restaurantId", restaurantId).setParameter("branchId", branchId).list();
			}
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
		return listMaterial;
	}
}
