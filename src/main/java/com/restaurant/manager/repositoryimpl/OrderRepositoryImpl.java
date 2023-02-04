package com.restaurant.manager.repositoryimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.manager.model.Orders;
import com.restaurant.manager.repository.OrderRepository;

@Repository
@Transactional
public class OrderRepositoryImpl implements OrderRepository {
	Session session = null;
	Transaction transaction = null;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean createOrder(Orders orders) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(orders);
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
	public Integer getStatusByOrderId(int orderId) {
		int status = 0;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			status = (int) session
					.createQuery("SELECT o.status FROM com.restaurant.manager.model.Orders o WHERE o.id = :orderId")
					.setParameter("orderId", orderId).uniqueResult();
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
		return status;
	}

	@Override
	public boolean updateOrder(Orders orders) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(orders);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Orders> listOrderByEmployeeId(int employeeId) {
		List<Orders> listOrder = new ArrayList<>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			listOrder = session
					.createQuery("FROM com.restaurant.manager.model.Orders o WHERE o.employee.id = :employeeId")
					.setParameter("employeeId", employeeId).list();
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
		return listOrder;
	}

	@Override
	public boolean changeStatus(int orderId, int status) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery(
					"UPDATE com.restaurant.manager.model.Orders o SET o.status = :Status WHERE o.id = :orderId")
					.setParameter("orderId", orderId).setParameter("Status", status).executeUpdate();
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
	public Orders detailOrders(int tableId, int status) {
		Orders order = new Orders();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			order = (Orders) session.createQuery(
					"FROM com.restaurant.manager.model.Orders o WHERE o.tables.id = :tableId AND o.status = :Status")
					.setParameter("tableId", tableId).setParameter("Status", status).uniqueResult();
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
		return order;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Orders> listOrder(int restaurantId, int branchId, int status) {
		List<Orders> listOrder = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			if (branchId == 0) {
			listOrder = session.createQuery(
					"FROM com.restaurant.manager.model.Orders o WHERE o.employee.restaurant.id = :restaurantId AND o.status = :status AND o.employee.branch.id = null")
					.setParameter("restaurantId", restaurantId).setParameter("status", status).list();
			} else {
				listOrder = session.createQuery(
						"FROM com.restaurant.manager.model.Orders o WHERE o.employee.restaurant.id = :restaurantId AND o.status = :status AND o.employee.branch.id = :branchId")
						.setParameter("restaurantId", restaurantId).setParameter("status", status)
						.setParameter("branchId", branchId).list();
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
		return listOrder;
	}
}
