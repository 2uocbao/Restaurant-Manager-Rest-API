package com.restaurant.manager.repositoryImpl;

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
			if (session != null) {
				if (session.isOpen())
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
			if (session != null) {
				if (session.isOpen())
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
			if (session != null) {
				if (session.isOpen())
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
			session.createQuery("UPDATE com.restaurant.manager.model.Tables t SET t.status = :status WHERE t.id = :id")
					.setParameter("status", status).setParameter("id", id).executeUpdate();
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Tables> listTableByBranchIdandRestaurantId(String restaurantId, String branchId) {
		List<Tables> listTables = new ArrayList<>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			if (branchId == "") {
				listTables = session
						.createQuery("FROM com.restaurant.manager.model.Tables t WHERE t.restaurant.id = :restaurantId AND t.branch.id = NULL")
						.setParameter("restaurantId", restaurantId).list();
			} else {
				listTables = session.createQuery(
						"FROM com.restaurant.manager.model.Tables t WHERE t.branch.id = :branchId AND t.restaurant.id = :restaurantId")
						.setParameter("branchId", branchId).setParameter("restaurantId", restaurantId).list();
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
			if (session != null) {
				if (session.isOpen())
					session.close();
			}
		}
		return status;
	}

	@Override
	public Tables getTablebyName(String restaurantId, String branchId, String name) {
		Tables table = new Tables();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			if (branchId == "") {
				table = (Tables) session.createQuery(
						"FROM com.restaurant.manager.model.Tables t WHERE t.restaurant.id = :restaurantId AND t.name = :name")
						.setParameter("restaurantId", restaurantId).setParameter("name", name).uniqueResult();
			} else {
				table = (Tables) session.createQuery(
						"FROM com.restaurant.manager.model.Tables t WHERE t.restaurant.id = :restaurantId AND t.branch.id = :branchId AND t.name = :name")
						.setParameter("restaurantId", restaurantId).setParameter("branchId", branchId)
						.setParameter("name", name).uniqueResult();
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
		return table;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tables> listTableByStatus(String restaurantId, String branchId, int status) {
		List<Tables> listTable = new ArrayList<>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			listTable = session.createQuery(
					"FROM com.restaurant.manager.model.Tables t WHERE t.restaurant.id = :restaurantId AND t.branch.id = :branchId AND t.status = :status")
					.setParameter("restaurantId", restaurantId).setParameter("branchId", branchId)
					.setParameter("status", status).list();
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
		return listTable;
	}

	@Override
	public boolean changeStatusTableByRestaurantId(String restaurantId, int status) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery(
					"UPDATE com.restaurant.manager.model.Tables t SET t.status = :status WHERE t.restaurant.id = :restaurantId")
					.setParameter("status", status).setParameter("restaurantId", restaurantId).executeUpdate();

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
	public boolean changeStatusTableByBranchId(String branchId, int status) {
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
			if (session != null) {
				if (session.isOpen())
					session.close();
			}
		}
		return successful;
	}
}