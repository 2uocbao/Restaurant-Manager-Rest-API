package com.restaurant.manager.repositoryimpl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
	@Autowired
	EntityManager entityManager;

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
			if (session.isOpen())
				session.close();
		}
		return successful;
	}

	@Override
	public Employee detailEmployee(int id) {
		Employee employee = new Employee();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			employee = (Employee) session.createQuery("FROM com.restaurant.manager.model.Employee e WHERE e.id = :id")
					.setParameter("id", id).uniqueResult();
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
			if (session.isOpen())
				session.close();
		}
		return successful;

	}

	@Override
	public boolean deleteEmployee(int id) {
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
			if (session.isOpen())
				session.close();
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
					"FROM com.restaurant.manager.model.Employee e WHERE e.phone = :Phone AND e.password = :password")
					.setParameter("Phone", phone).setParameter("password", password).uniqueResult();
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
	public List<Employee> listEmpoyeeByResIdOrBranId(int restaurantId, int branchId) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Employee> criteria = builder.createQuery(Employee.class);
		
		Root<Employee> translation = criteria.from(Employee.class);
		
		criteria.select(translation);
		
		criteria.where(builder.equal(translation.get("restaurant"), restaurantId),
				builder.equal(translation.get("branch"), branchId));
		TypedQuery<Employee> query = entityManager.createQuery(criteria);
		
		return query.getResultList();

	}

	@Override
	public Employee getEmployeeByPhone(String phone) {
		Employee employee = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			employee = (Employee) session
					.createQuery("FROM com.restaurant.manager.model.Employee e WHERE e.phone = :Phone")
					.setParameter("Phone", phone).uniqueResult();
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
			if (session.isOpen()) {
				session.close();
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
			if (session.isOpen()) {
				session.close();
			}
		}
		return password;
	}

	@Override
	public boolean changePasswordEmployee(int id, String password) {
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
			if (session.isOpen())
				session.close();
		}
		return successful;
	}

	@Override
	public boolean changeStatusEmployee(int id, int status) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery(
					"UPDATE com.restaurant.manager.model.Employee e SET e.status = :Status WHERE e.id = :id")
					.setParameter("Status", status).setParameter("id", id).executeUpdate();
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
	public boolean getStatusById(int id) {
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
			if (session.isOpen())
				session.close();
		}
		return status;
	}

	@Override
	public boolean changeStatusEmployeeByRestaurantId(int restaurantId, int status) {
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
			if (session.isOpen())
				session.close();
		}
		return successful;
	}

	@Override
	public boolean changeStatusEmployeeByBranchId(int branchId, int status) {
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
			if (session.isOpen())
				session.close();
		}
		return successful;

	}

	@Override
	public Optional<Employee> findByPhone(String phone) {
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
			if (session.isOpen())
				session.close();
		}
		return Optional.ofNullable(employee);
	}

	@Override
	public boolean removeRole(int id, int roleId) {
		boolean successful = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery("DELETE com.restaurant.manager.model.Employee.empole e WHERE e.employee.id = :id AND e.roles.id = :roleId")
					.setParameter("id", id).setParameter("roleId", roleId).executeUpdate();
			successful = true;
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
		return successful;
	}
}
