package com.restaurant.manager.repositoryImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.restaurant.manager.model.orderDetail;
import com.restaurant.manager.repository.orderDetailRepository;

@Repository
public class orderDetailRepositoryImpl implements orderDetailRepository {
	Session session = null;
	Transaction transaction = null;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean createOrderDetail(orderDetail orderDetail) {
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
			if (session != null) {
				if (session.isOpen())
					session.close();
			}
		}
		return successful;
	}

	@Override
	public orderDetail detailOrder(int orderId, int foodId) {
		orderDetail orderDetail = new orderDetail();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			orderDetail = (orderDetail) session.createQuery(
					"FROM com.restaurant.manager.model.orderDetail o WHERE o.order.id = :orderId AND o.food.id = :foodId")
					.setParameter("orderId", orderId).setParameter("foodId", foodId).uniqueResult();
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
		return orderDetail;
	}

	@Override
	public boolean updateOrderDetail(orderDetail orderDetail) {
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
			if (session != null) {
				if (session.isOpen())
					session.close();
			}
		}
		return successful;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<orderDetail> listOrderbyIdorder(int orderId) {
		List<orderDetail> listorder = new ArrayList<>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			listorder = session
					.createQuery("FROM com.restaurant.manager.model.orderDetail o WHERE o.order.id = :orderId")
					.setParameter("orderId", orderId).list();
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
		return listorder;
	}

	@Override
	public boolean deleteOrderDetail(int orderId, int foodId) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery(
					"DELETE com.restaurant.manager.model.orderDetail o WHERE o.order.id = :orderId AND o.food.id = :foodId")
					.setParameter("orderId", orderId).setParameter("foodId", foodId).executeUpdate();
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
