package com.restaurant.manager.repositoryimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.restaurant.manager.model.OrderDetail;
import com.restaurant.manager.repository.OrderDetailRepository;

@Repository
public class OrderDetailRepositoryImpl implements OrderDetailRepository {
	Session session = null;
	Transaction transaction = null;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean createOrderDetail(OrderDetail orderDetail) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(orderDetail);
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
	public OrderDetail getorderDetail(int orderId, int foodId) {
		OrderDetail orderDetail = new OrderDetail();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			orderDetail = (OrderDetail) session.createQuery(
					"FROM com.restaurant.manager.model.OrderDetail o WHERE o.orders.id = :orderid AND o.food.id = :foodId")
					.setParameter("orderid", orderId).setParameter("foodId", foodId).uniqueResult();
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
		return orderDetail;
	}

	@Override
	public boolean updateOrderDetail(OrderDetail orderDetail) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(orderDetail);
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
	public List<OrderDetail> listOrderDetails(int orderId) {
		List<OrderDetail> listorder = new ArrayList<>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			listorder = session
					.createQuery("FROM com.restaurant.manager.model.OrderDetail o WHERE o.orders.id = :orderId")
					.setParameter("orderId", orderId).list();
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
		return listorder;
	}

	@Override
	public boolean deleteOrderDetail(int orderId, int foodId) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery(
					"DELETE com.restaurant.manager.model.OrderDetail o WHERE o.orders.id = :orderId AND o.food.id = :foodId")
					.setParameter("orderId", orderId).setParameter("foodId", foodId).executeUpdate();
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
}
