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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import main.java.edu.isistan.genCom.redSocial.Investigador;
import main.java.edu.isistan.genCom.redSocial.LugarDeTrabajo;
import main.java.edu.isistan.genCom.redSocial.Publicacion;

/**
 * Home object for domain model class Investigadores.
 * 
 * @see main.java.edu.isistan.genCom.redSocial.Investigador
 * @author Hibernate Tools
 */
public class DAOInvestigador {

	private static final Log log = LogFactory.getLog(DAOInvestigador.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return HibernateUtil.getSessionFactory();
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(Investigador transientInstance) {
		log.debug("persisting Investigadores instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Investigador instance) {
		log.debug("attaching dirty Investigadores instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Investigador instance) {
		log.debug("attaching clean Investigadores instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Investigador persistentInstance) {
		log.debug("deleting Investigadores instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Investigador merge(Investigador detachedInstance) {
		log.debug("merging Investigadores instance");
		try {
			Investigador result = (Investigador) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Investigador findById(long id) {
		log.debug("getting Investigadores instance with id: " + id);
		try {
			Investigador instance = (Investigador) sessionFactory
					.getCurrentSession().get("main.resources.Investigadores",
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

	public List findByExample(Investigador instance) {
		log.debug("finding Investigadores instance by example");

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			List results = session
					.createCriteria(
							"main.java.edu.isistan.genCom.redSocial.Investigador")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());

			tx.commit();
			return results;
		} catch (RuntimeException re) {
			tx.rollback();

			log.error("find by example failed", re);
			throw re;
		} finally {
			session.close();
		}

	}

	public List getAll() {
		log.debug("Obteniendo todos los investigadores");

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			List results = session.createCriteria(
					"main.java.edu.isistan.genCom.redSocial.Investigador")
					.addOrder(Order.desc("esEspecialista"))
					.list();
			log.debug("recuperación exitosa, result size: " + results.size());

			tx.commit();
			return results;
		} catch (RuntimeException re) {
			tx.rollback();
			log.error("falló la recuperación", re);
			throw re;
		} finally {
			session.close();
		}
	}

	/**
	 * Obtiene los investigadores con iguales publicaciones, y los que tienen mismo lugar de trabajo
	 * @return
	 */
	public Set getRelacionados(Investigador investigador) {
//		TODO Obtener investigadores con mismas publicaciones y con igual lugar de trabajo
		log.debug("Obteniendo todos los coautores");

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Set results = new HashSet<>();

		try {
			tx = session.beginTransaction();
			
//			Agrega los investigadores con iguales publicaciones
			Set<Publicacion> publicaciones = investigador.getPublicaciones();
			
			for (Publicacion publicacion : publicaciones) {
				List consulta = session.createCriteria(Investigador.class)
						.createAlias("publicaciones", "pub")
						.add(Restrictions.eq("pub.publicacionId", publicacion.getPublicacionId()))
						.add(Restrictions.not(Restrictions.eq("investigadorId", investigador.getInvestigadorId())))
						.list();
				
				results.addAll(consulta);							
			}
		
//			Agrega los investigadores con iguales lugares de trabajo
			Set<LugarDeTrabajo> lugares = investigador.getLugaresDeTrabajos();
			
			for (LugarDeTrabajo lugar : lugares) {
				List consulta = session.createCriteria(Investigador.class)
						.createAlias("lugaresDeTrabajos", "lug")
						.add(Restrictions.eq("lug.id", lugar.getId()))
						.add(Restrictions.not(Restrictions.eq("investigadorId", investigador.getInvestigadorId())))
						.list();
				
				results.addAll(consulta);							
			}
			
			log.debug("recuperación exitosa, result size: " + results.size());

			tx.commit();
			return results;
		} catch (RuntimeException re) {
			tx.rollback();
			log.error("falló la recuperación", re);
			throw re;
		} finally {
			session.close();
		}
	}
}
