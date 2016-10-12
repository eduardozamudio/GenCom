/**
 *     This file is part of GenCom.
 *
 *     GenCom is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     GenCom is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with GenCom.  If not, see <http://www.gnu.org/licenses/>.
 */
package main.java.edu.isistan.genCom.redSocial.dao;

// Generated 06-mar-2014 11:43:22 by Hibernate Tools 4.0.0

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import main.java.edu.isistan.genCom.redSocial.LugarDeTrabajo;

/**
 * Home object for domain model class LugaresDeTrabajo.
 * @see main.java.edu.isistan.genCom.redSocial.LugarDeTrabajo
 * @author Hibernate Tools
 */
public class DAOLugarDeTrabajo {

	private static final Log log = LogFactory
			.getLog(DAOLugarDeTrabajo.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(LugarDeTrabajo transientInstance) {
		log.debug("persisting LugaresDeTrabajo instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(LugarDeTrabajo instance) {
		log.debug("attaching dirty LugaresDeTrabajo instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(LugarDeTrabajo instance) {
		log.debug("attaching clean LugaresDeTrabajo instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(LugarDeTrabajo persistentInstance) {
		log.debug("deleting LugaresDeTrabajo instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public LugarDeTrabajo merge(LugarDeTrabajo detachedInstance) {
		log.debug("merging LugaresDeTrabajo instance");
		try {
			LugarDeTrabajo result = (LugarDeTrabajo) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public LugarDeTrabajo findById(long id) {
		log.debug("getting LugaresDeTrabajo instance with id: " + id);
		try {
			LugarDeTrabajo instance = (LugarDeTrabajo) sessionFactory
					.getCurrentSession().get("main.resources.LugaresDeTrabajo",
							id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(LugarDeTrabajo instance) {
		log.debug("finding LugaresDeTrabajo instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("main.resources.LugaresDeTrabajo")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
