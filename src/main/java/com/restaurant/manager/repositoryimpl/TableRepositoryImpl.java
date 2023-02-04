package com.restaurant.manager.repositoryimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.manager.model.Tables;
import com.restaurant.manager.repository.TableRepository;

@Repository
@Transactional
public class TableRepositoryImpl implements TableRepository {

	Transaction transaction = null;
	Session session = null;
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean createTable(Tables table) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(table);
			transaction.commit();
			successful = true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		return successful;
	}

	@Override
	public Tables detailTable(int id) {
		Tables table = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			table = (Tables) session.createQuery("FROM com.restaurant.manager.model.Tables t WHERE t.id = :id")
					.setParameter("id", id).uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		return table;
	}

	@Override
	public boolean updateTable(Tables tables) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(tables);
			transaction.commit();
			successful = true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		return successful;
	}

	@Override
	public boolean changeStatusById(int id, int status) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery("UPDATE com.restaurant.manager.model.Tables t SET t.status = :Status WHERE t.id = :id")
					.setParameter("Status", status).setParameter("id", id).executeUpdate();
			transaction.commit();
			successful = true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		return successful;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tables> listTableByBranchIdandRestaurantId(int restaurantId, int branchId) {
		List<Tables> listTables = new ArrayList<>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			if (branchId == 0) {
				listTables = session.createQuery(
						"FROM com.restaurant.manager.model.Tables t WHERE t.restaurant.id = :restaurant AND t.branch.id = null")
						.setParameter("restaurant", restaurantId).list();
			} else {
				listTables = session.createQuery(
						"FROM com.restaurant.manager.model.Tables t WHERE t.branch.id = :BranchId AND t.restaurant.id = :RestaurantId")
						.setParameter("BranchId", branchId).setParameter("RestaurantId", restaurantId).list();
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		return listTables;
	}

	@Override
	public Integer getStatusById(int id) {
		int status = 0;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			status = (Integer) session
					.createQuery("SELECT t.status FROM com.restaurant.manager.model.Tables t WHERE t.id = :id")
					.setParameter("id", id).uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		return status;
	}

	@Override
	public boolean changeStatusTableByRestaurantId(int restaurantId, int status) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery(
					"UPDATE com.restaurant.manager.model.Tables t SET t.status = :status WHERE t.restaurant.id = :RestaurantId")
					.setParameter("status", status).setParameter("RestaurantId", restaurantId).executeUpdate();

			transaction.commit();
			successful = true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		return successful;
	}

	@Override
	public boolean changeStatusTableByBranchId(int branchId, int status) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery(
					"UPDATE com.restaurant.manager.model.Tables t SET t.status = :status WHERE t.branch.id = :branchId")
					.setParameter("status", status).setParameter("branchId", branchId).executeUpdate();
			transaction.commit();
			successful = true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		return successful;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tables> findTables(int restaurantId, int branchId, String keySearch) {
		List<Tables> tables = new ArrayList<>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			if (branchId == 0) {
				tables = session.createQuery(
						"FROM com.restaurant.manager.model.Tables t WHERE (t.restaurant.id = :restaurantId AND t.branch.id = null) AND (t.name LIKE :keySearch)")
						.setParameter("restaurantId", restaurantId)
						.setParameter("keySearch", "%" + keySearch + "%").list();
			} else {
				tables = session.createQuery(
						"FROM com.restaurant.manager.model.Tables t WHERE (t.restaurant.id = :restaurantId AND t.branch.id = :branchId) AND (t.name LIKE :keySearch)")
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
			if (session.isOpen()) {
				session.close();
			}
		}
		return tables;
	}
}