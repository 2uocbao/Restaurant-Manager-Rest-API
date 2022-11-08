package com.restaurant.manager.repositoryImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.manager.model.Employee;
import com.restaurant.manager.repository.EmployeeRepository;

@Repository
@Transactional
public class EmployeeRepositoryImpl implements EmployeeRepository {
	Session session = null;
	Transaction transaction = null;
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean createEmployee(Employee employee) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(employee);
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
	public Employee detailEmployee(String id) {
		Employee employee = new Employee();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			employee = (Employee) session
					.createQuery("FROM com.restaurant.manager.model.Employee e WHERE e.id = :id")
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
		return employee;
	}

	@Override
	public boolean updateEmployee(Employee employee) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(employee);
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
	public boolean deleteEmployee(String id) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery("DELETE com.restaurant.manager.model.Employee e WHERE e.id = :id")
					.setParameter("id", id).executeUpdate();
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
	public boolean loginEmployee(String phone, String password) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery(
					"FROM com.restaurant.manager.model.Employee e WHERE e.phone = :phone AND e.password = :password")
					.setParameter("phone", phone).setParameter("password", password).uniqueResult();
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
	public List<Employee> listEmpoyeeByResIdOrBranId(String restaurantId, String branchId) {
		List<Employee> listEmployee = new ArrayList<>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			if (branchId == "") {
			listEmployee = session
					.createQuery("FROM com.restaurant.manager.model.Employee e WHERE e.restaurant.id = :restaurantId AND e.branch.id = null")
					.setParameter("restaurantId", restaurantId).list();
			} else {
				listEmployee = session.createQuery(
						"FROM com.restaurant.manager.model.Employee e WHERE e.restaurant.id = :restaurantId AND e.branch.id = :branchId")
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
		return listEmployee;
	}

	@Override
	public Employee getEmployeeByPhone(String phone) {
		Employee employee = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			employee = (Employee) session
					.createQuery("FROM com.restaurant.manager.model.Employee e WHERE e.phone = :phone")
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
		return employee;
	}

	@Override
	public Employee getEmployeeByEmail(String email) {
		Employee employee = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			employee = (Employee) session
					.createQuery("FROM com.restaurant.manager.model.Employee e WHERE e.email = :email")
					.setParameter("email", email).uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}
			}
		}
		return employee;
	}

	@Override
	public String getPasswordByPhone(String phone) {
		String password = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			password = (String) session
					.createQuery(
							"SELECT e.password FROM com.restaurant.manager.model.Employee e WHERE e.phone = :phone")
					.setParameter("phone", phone).uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}
			}
		}
		return password;
	}

	@Override
	public boolean changePasswordEmployee(String id, String password) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery(
					"UPDATE com.restaurant.manager.model.Employee e SET e.password = :password WHERE e.id = :id")
					.setParameter("password", password).setParameter("id", id).executeUpdate();
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
	public boolean changeStatusEmployee(String id, int status) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery(
					"UPDATE com.restaurant.manager.model.Employee e SET e.status = :status WHERE e.id = :id")
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
	public boolean getStatusById(String id) {
		boolean status = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			status = (boolean) session
					.createQuery("SELECT e.status FROM com.restaurant.manager.model.Employee e WHERE e.id = :id")
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
	public boolean changeStatusEmployeeByRestaurantId(String restaurantId, int status) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery(
					"UPDATE com.restaurant.manager.model.Employee e SET e.status = :status WHERE e.restaurant.id = :restaurantId")
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
	public boolean changeStatusEmployeeByBranchId(String branchId, int status) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery(
					"UPDATE com.restaurant.manager.model.Employee e SET e.status = :status WHERE e.branch.id = :branchId")
					.setParameter("branchId", branchId).setParameter("status", status).executeUpdate();
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
		return successful;
	}
}
