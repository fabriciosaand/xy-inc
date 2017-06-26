package br.com.xyinc.ws.service;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.xyinc.ws.entity.PoiEntity;
import br.com.xyinc.ws.persistence.dao.PoiDao;
import br.com.xyinc.ws.util.PropertiesUtil;

@Path("/poi")
public class PoiService {

	private static final String LISTANDO_POR_PROXIMIDADE = "poi.listar.proximidade";
	private static final String LISTANDO_TODOS_OS_POIS = "poi.listar.todos";
	private static final String POI_SUCESSO = "poi.sucesso";
	private static final String POI_COORDENADAX_MAXIMO = "poi.coordenadax.maximo";
	private static final String POI_COORDENADAY_MAXIMO = "poi.coordenaday.maximo";
	private static final String POI_NOME_TAMANHO = "poi.nome.tamanho";
	private static final String POI_NOME_VAZIO = "poi.nome.vazio";
	private static final String POI_COORDENADAS_NEGATIVAS = "poi.coordenadas.negativas";
	private static final String POI_PARAMETROS_INVALIDOS = "poi.parametros.invalidos";

	private static final Logger LOG = Logger.getLogger(PoiEntity.class.getName());

	@GET
	@Path("/cadastrar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cadastrar(@QueryParam("nome") String nome, @QueryParam("coordenadaX") Integer coordenadaX,
			@QueryParam("coordenadaY") Integer coordenadaY) {

		Response response = null;
		try {
			PoiEntity poiEntity = new PoiEntity();
			response = validar(nome, coordenadaX, coordenadaY);
			if ((response.getStatus() == Status.OK.getStatusCode())) {
				poiEntity.setNome(nome);
				poiEntity.setCoordenadaX(coordenadaX);
				poiEntity.setCoordenadaY(coordenadaY);

				new PoiDao().save(poiEntity);
			}
			return response;
		} catch (Exception e) {
			return response;
		}
	}

	@GET
	@Path("/listarTodos")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PoiEntity> listarTodos() throws IOException {
		LOG.info(PropertiesUtil.getProperty(LISTANDO_TODOS_OS_POIS));
		return new PoiDao().findAll();
	}

	@GET
	@Path("/listarPorProximidade")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<PoiEntity> listarPorProximidade(@QueryParam("coordenadaX") Integer coordenadaX,
			@QueryParam("coordenadaY") Integer coordenadaY, @QueryParam("dMax") Integer dMax) throws IOException {
		LOG.info(PropertiesUtil.getProperty(LISTANDO_POR_PROXIMIDADE, coordenadaX, coordenadaY, dMax));
		return new PoiDao().consultarPorProximidade(coordenadaX, coordenadaY, dMax);
	}

	public Response validar(String nome, Integer coordenadaX, Integer coordenadaY) throws IOException {

		if (coordenadaX == null || coordenadaY == null || nome == null) {
			LOG.warning(PropertiesUtil.getProperty(POI_PARAMETROS_INVALIDOS));
			return Response.status(400).entity(PropertiesUtil.getProperty(POI_PARAMETROS_INVALIDOS)).build();
		} else {

			if (coordenadaX.intValue() < 0 || coordenadaY.intValue() < 0) {
				LOG.warning(PropertiesUtil.getProperty(POI_COORDENADAS_NEGATIVAS));
				return Response.status(200).entity(PropertiesUtil.getProperty(POI_COORDENADAS_NEGATIVAS)).build();
			}

			if (nome.isEmpty()) {
				LOG.warning(PropertiesUtil.getProperty(POI_NOME_VAZIO));
				return Response.status(200).entity(PropertiesUtil.getProperty(POI_NOME_VAZIO)).build();
			} else if (nome.length() < 3 || nome.length() > 50) {
				LOG.warning(PropertiesUtil.getProperty(POI_NOME_TAMANHO));
				return Response.status(200).entity(PropertiesUtil.getProperty(POI_NOME_TAMANHO)).build();
			}

			if (coordenadaY.intValue() > 90) {
				LOG.warning(PropertiesUtil.getProperty(POI_COORDENADAY_MAXIMO));
				return Response.status(200).entity(PropertiesUtil.getProperty(POI_COORDENADAY_MAXIMO)).build();
			}

			if (coordenadaX.intValue() > 180) {
				LOG.warning(PropertiesUtil.getProperty(POI_COORDENADAX_MAXIMO));
				return Response.status(200).entity(PropertiesUtil.getProperty(POI_COORDENADAX_MAXIMO)).build();
			}

			LOG.info(PropertiesUtil.getProperty(POI_SUCESSO, nome, coordenadaX, coordenadaY));
			return Response.status(200).entity(PropertiesUtil.getProperty(POI_SUCESSO, nome, coordenadaX, coordenadaY))
					.build();
		}
	}

}
