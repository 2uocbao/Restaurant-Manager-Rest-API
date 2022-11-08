package com.restaurant.manager.repositoryImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.manager.model.Image;
import com.restaurant.manager.repository.ImageRepository;

@Repository
@Transactional
public class ImageRepositoryImpl implements ImageRepository {
	Session session = null;
	Transaction transaction = null;
	boolean successful = false;
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean uploadImage(Image image) {
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(image);
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
	public Image displayImagebyUserId(String userId) {
		Image image = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			image = (Image) session.createQuery("FROM com.restaurant.manager.model.Image i WHERE i.userId = :userId")
					.setParameter("userId", userId).uniqueResult();
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
		return image;
	}

	@Override
	public boolean updateImage(Image image) {
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(image);
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
	public boolean deleteImage(String userId) {
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery("DELETE i FROM com.restaurant.manager.model.Image i WHERE i.userId = :userId")
					.setParameter("userId", userId).executeUpdate();
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