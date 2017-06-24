package br.com.xyinc.ws.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerProvider {

	private static EntityManagerFactory emf = null;

	private EntityManagerProvider() {
	}

	public static EntityManagerFactory getEntityManagerFactory() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("persistence_unit_db_xy_inc");
		}
		return emf;
	}

}
