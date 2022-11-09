package com.restaurant.manager.repositoryImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.manager.model.Branch;
import com.restaurant.manager.repository.BranchRepository;

@Repository
@Transactional
public class BranchRepositoryImpl implements BranchRepository {
	Transaction transaction = null;
	Session session = null;
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean createBranch(Branch branch) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(branch);
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
	public Branch detailBranch(String id) {
		Branch branch = new Branch();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			branch = (Branch) session.createQuery("FROM com.restaurant.manager.model.Branch b WHERE b.id = :id")
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
		return branch;
	}

	@Override
	public boolean updateBranch(Branch branch) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(branch);
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
	public boolean changeStatusBranch(String id, int status) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery("UPDATE com.restaurant.manager.model.Branch b SET b.status = :status WHERE b.id = :id")
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

	@Override
	public Branch getDetailByPhone(String phone) {
		Branch branch = new Branch();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			branch = (Branch) session.createQuery("FROM com.restaurant.manager.model.Branch b WHERE b.phone = :phone")
					.setParameter("phone", phone).uniqueResult();
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
		return branch;

	}

	@Override
	public Integer getStatusbyId(String id) {
		int status = 0;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			status = (int) session
					.createQuery("SELECT b.status FROM com.restaurant.manager.model.Branch b WHERE b.id = :id")
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
	public boolean changeStatusbyRestaurantId(String restaurantId, int status) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery(
					"UPDATE com.restaurant.manager.model.Branch b SET b.status = :status WHERE b.restaurant.id = :restaurantId")
					.setParameter("restaurantId", restaurantId).setParameter("status", status).executeUpdate();
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
	public List<Branch> listBranchByRestaurantId(String restaurantId) {
		List<Branch> listBranch = new ArrayList<>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			listBranch = session.createQuery("FROM com.restaurant.manager.model.Branch b WHERE b.restaurant.id = :id")
					.setParameter("id", restaurantId).list();
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
		return listBranch;
	}
}
