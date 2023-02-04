package com.restaurant.manager.repositoryimpl;

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
			if (session.isOpen())
				session.close();
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
			if (session.isOpen())
				session.close();
		}
		return successful;
	}

	@Override
	public Material detailMaterial(int materialId) {
		Material material = new Material();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			material = (Material) session.createQuery("FROM com.restaurant.manager.model.Material i WHERE i.id = :id")
					.setParameter("id", materialId).uniqueResult();

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
		return material;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Material> listMaterial(int restaurantId, int branchId) {
		List<Material> listMaterial = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			if (branchId == 0) {
				listMaterial = session.createQuery(
						"FROM com.restaurant.manager.model.Material m WHERE m.restaurant.id = :RestaurantId AND m.branch.id = null")
						.setParameter("RestaurantId", restaurantId).list();
			} else {
				listMaterial = session.createQuery(
						"FROM com.restaurant.manager.model.Material m WHERE m.restaurant.id = :RestaurantId AND m.branch.id = :branchId")
						.setParameter("RestaurantId", restaurantId).setParameter("branchId", branchId).list();
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
		return listMaterial;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Material> findMaterialByCode(int restaurantId, int branchId, String keySearch) {
		List<Material> listMaterial = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			if (branchId == 0) {
				listMaterial = session.createQuery(
						"FROM com.restaurant.manager.model.Material m WHERE (m.restaurant.id = :restaurantId AND m.branch.id = null) AND (m.code LIKE :keySearch)")
						.setParameter("restaurantId", restaurantId).setParameter("keySearch", "%" + keySearch + "%")
						.list();
			} else {
				listMaterial = session.createQuery(
						"FROM com.restaurant.manager.model.Material m WHERE (m.restaurant.id = :restaurantId AND m.branch.id = :branchId) AND (m.code LIKE :keySearch)")
						.setParameter("restaurantId", restaurantId).setParameter("branchId", branchId)
						.setParameter("keySearch", "%" + keySearch + "%").list();
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
		return listMaterial;
	}
}
