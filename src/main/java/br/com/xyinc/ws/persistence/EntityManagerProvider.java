package br.com.xyinc.ws.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerProvider {

	private static final String PERSISTENCE_UNIT_DB_XY_INC = "persistence_unit_db_xy_inc";
	private static EntityManagerFactory entityManagerFactory = null;

	public static EntityManagerFactory getEntityManagerFactory() {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_DB_XY_INC);
		}
		return entityManagerFactory;
	}

}
