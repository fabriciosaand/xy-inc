package br.com.xyinc.ws.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.com.xyinc.ws.entity.PoiEntity;
import br.com.xyinc.ws.persistence.dao.PoiDao;

@Path("/poi")
public class PoiService {

	@GET
	@Path("/cadastrar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String cadastrar(@QueryParam("nome") String nome, @QueryParam("coordenadaX") Integer coordenadaX,
			@QueryParam("coordenadaY") Integer coordenadaY) {

		PoiDao poiDao = new PoiDao();
		PoiEntity poiEntity = new PoiEntity();

		poiEntity.setNome(nome);
		poiEntity.setCoordenadaX(coordenadaX);
		poiEntity.setCoordenadaY(coordenadaY);

		poiDao.save(poiEntity);

		return "cadastrar " + nome + "(" + coordenadaX + "," + coordenadaX + ")";
	}

	@GET
	@Path("/listarTodos")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PoiEntity> listarTodos() {
		PoiDao poiDao = new PoiDao();
		List<PoiEntity> lista = poiDao.findAll();

		return lista;
	}

	@GET
	@Path("/listarPorProximidade")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<PoiEntity> listarPorProximidadeGet(@QueryParam("coordenadaX") Integer coordenadaX,
			@QueryParam("coordenadaY") Integer coordenadaY, @QueryParam("dMax") Integer dMax) {
		PoiDao poiDao = new PoiDao();
		List<PoiEntity> lista = poiDao.consultarPorProximidade(coordenadaX, coordenadaY, dMax);
		return lista;
	}

}
