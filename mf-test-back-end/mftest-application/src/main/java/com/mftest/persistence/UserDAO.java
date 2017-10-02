package com.mftest.persistence;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mftest.core.exception.PersistenceCoreException;
import com.mftest.core.persistence.UserPersistenceInterface;
import com.mftest.core.persistence.entity.Role;
import com.mftest.core.persistence.entity.User;

public class UserDAO implements UserPersistenceInterface {

	@Autowired
	@Qualifier("myPersistence")
	EntityManager em;
	
	/**
	 * Save a user.
	 */
	@Override
	public long save(User user) throws PersistenceCoreException {
		try {
			em.getTransaction().begin();
			
			// Find the "User" role
			Role role = em.find(Role.class, user.getRole().getId());
			user.setRole(role);
			
			em.persist(user);
			em.getTransaction().commit();
		} catch(EntityExistsException | IllegalArgumentException | IllegalStateException e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			
			throw new PersistenceCoreException(e.getMessage());
		}
		
		return user.getId();
	}

	/**
	 * Find a user.
	 */
	@Override
	public User find(long userId) throws PersistenceCoreException {
		try {
			return em.find(User.class, userId);
		} catch(IllegalArgumentException e) {
			throw new PersistenceCoreException(e.getMessage());
		}
	}

	/**
	 * Find a user by the user name.
	 */
	@Override
	public User findByUserData(String userName) throws PersistenceCoreException {
		return findByUserData(userName, "");
	}

	/**
	 * Find a user by user name and password.
	 */
	@Override
	public User findByUserData(String userName, String password) throws PersistenceCoreException {
		String hql = "select u from User u where u.name = :name";
		hql += !password.equals("") ? " and u.password = :password" : "";
		
		Query query = em.createQuery(hql);
		query.setParameter("name", userName);
		if (!password.equals("")) {
			query.setParameter("password", password);
		}
		
		try {
			return (User) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException e) {
			throw new PersistenceCoreException(e.getMessage());
		}
	}

}
