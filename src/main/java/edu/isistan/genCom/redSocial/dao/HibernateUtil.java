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

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.DefaultNamingStrategy;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

	private static SessionFactory sessionFactory = buildSessionFactory();
	private static ServiceRegistry serviceRegistry;

	private static String myConnectionUrl;
	private static String myUserName;
	private static String myPassword;

	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			Configuration configuration = new Configuration();
			configuration.setNamingStrategy(DefaultNamingStrategy.INSTANCE);
			configuration.configure();

			myConnectionUrl = configuration
					.getProperty("hibernate.connection.url");
			myUserName = configuration
					.getProperty("hibernate.connection.username");
			myPassword = configuration
					.getProperty("hibernate.connection.password");

			sessionFactory = configuration.buildSessionFactory();
			return sessionFactory;
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Establece una nueva configuración en base a los datos de la conexión
	 * 
	 * @param myConnectionUrl
	 * @param myUserName
	 * @param myPassword
	 */
	public static void setConfig(String myConnectionUrl, String myUserName,
			String myPassword) throws Exception {
		// modificar los datos del hibernate.cfg.xml
		Configuration configuration = new Configuration();
		configuration.configure();

		HibernateUtil.myConnectionUrl = myConnectionUrl;
		HibernateUtil.myUserName = myUserName;
		HibernateUtil.myPassword = myPassword;

		configuration.setProperty("hibernate.connection.url", myConnectionUrl);
		configuration.setProperty("hibernate.connection.username", myUserName);
		configuration.setProperty("hibernate.connection.password", myPassword);

		// Crea el nuevo buildsessionfactory
		configuration.setNamingStrategy(DefaultNamingStrategy.INSTANCE);
		SessionFactory sf = configuration.buildSessionFactory();

		// TODO Prueba la conexión

		if (!conecta()) {
			throw new Exception(
					"No se ha podido establecer la conexión. Por favor verifique los datos.");
		}

		// Establece la configuración como definitiva
		sessionFactory = sf;
	}

	private static boolean conecta() {
		boolean result = false;

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		result = true;
		tx.commit();
		session.close();

		return result;

	}

	public static Configuration getConfig() {
		return new Configuration();
	}

	public static Map getDatosDeConexion() {
		Map<String, String> result = new HashMap<>();

		Configuration conf = new Configuration();
		conf.configure();

		result.put("url", myConnectionUrl);
		result.put("username", myUserName);
		result.put("password", myPassword);

		return result;
	}

}
