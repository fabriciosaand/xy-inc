package br.com.xyinc.ws.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.xyinc.ws.entity.PoiEntity;
import br.com.xyinc.ws.persistence.EntityManagerProvider;

public class PoiDao {

	private static final String SELECT_CONSULTAR_POR_PROXIMIDADE = "from PoiEntity p where (power((:cx-p.coordenadaX),2)+power((:cy-p.coordenadaY),2)) <= power(:dm,2)";
	private static final String SELECT_TODOS = "select p from PoiEntity p";

	public PoiEntity save(PoiEntity entity) {

		EntityManager entityManager = EntityManagerProvider.getEntityManagerFactory().createEntityManager();
		entityManager.getTransaction().begin();

		final PoiEntity merge = entityManager.merge(entity);

		entityManager.getTransaction().commit();
		entityManager.close();
		return merge;
	}

	public List<PoiEntity> findAll() {

		EntityManager entityManager = EntityManagerProvider.getEntityManagerFactory().createEntityManager();
		return entityManager.createQuery(SELECT_TODOS, PoiEntity.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<PoiEntity> consultarPorProximidade(Integer coordenadaX, Integer coordenadaY, Integer dMax) {
		EntityManager entityManager = EntityManagerProvider.getEntityManagerFactory().createEntityManager();
		Session session = entityManager.unwrap(Session.class);
		Query q = session.createQuery(SELECT_CONSULTAR_POR_PROXIMIDADE);
		q.setParameter("cx", coordenadaX);
		q.setParameter("cy", coordenadaY);
		q.setParameter("dm", dMax);
		return (List<PoiEntity>) q.list();
	}

}
