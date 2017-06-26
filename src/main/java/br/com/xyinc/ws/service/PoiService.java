package br.com.xyinc.ws.service;

import java.io.IOException;
import java.util.List;

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

	private static final String POI_SUCESSO = "poi.sucesso";
	private static final String POI_COORDENADAX_MAXIMO = "poi.coordenadax.maximo";
	private static final String POI_COORDENADAY_MAXIMO = "poi.coordenaday.maximo";
	private static final String POI_NOME_TAMANHO = "poi.nome.tamanho";
	private static final String POI_NOME_VAZIO = "poi.nome.vazio";
	private static final String POI_COORDENADAS_NEGATIVAS = "poi.coordenadas.negativas";
	private static final String POI_PARAMETROS_INVALIDOS = "poi.parametros.invalidos";

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
	public List<PoiEntity> listarTodos() {
		return new PoiDao().findAll();
	}

	@GET
	@Path("/listarPorProximidade")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<PoiEntity> listarPorProximidadeGet(@QueryParam("coordenadaX") Integer coordenadaX,
			@QueryParam("coordenadaY") Integer coordenadaY, @QueryParam("dMax") Integer dMax) {
		return new PoiDao().consultarPorProximidade(coordenadaX, coordenadaY, dMax);
	}

	public Response validar(String nome, Integer coordenadaX, Integer coordenadaY) throws IOException {

		if (coordenadaX == null || coordenadaY == null) {
			return Response.status(400).entity(PropertiesUtil.getProperty(POI_PARAMETROS_INVALIDOS)).build();
		} else {

			if (coordenadaX.intValue() < 0 || coordenadaY.intValue() < 0) {
				return Response.status(200).entity(PropertiesUtil.getProperty(POI_COORDENADAS_NEGATIVAS)).build();
			}
			
			if (nome == null || nome.isEmpty()) {
				return Response.status(200).entity(PropertiesUtil.getProperty(POI_NOME_VAZIO)).build();
			} else if (nome.length() < 3 || nome.length() > 50) {
				return Response.status(200).entity(PropertiesUtil.getProperty(POI_NOME_TAMANHO)).build();
			}
			
			if (coordenadaY.intValue() > 90) {
				return Response.status(200).entity(PropertiesUtil.getProperty(POI_COORDENADAY_MAXIMO)).build();
			}

			if (coordenadaX.intValue() > 180) {
				return Response.status(200).entity(PropertiesUtil.getProperty(POI_COORDENADAX_MAXIMO)).build();
			}

			return Response.status(200).entity(PropertiesUtil.getProperty(POI_SUCESSO, nome, coordenadaX, coordenadaY))
					.build();
		}
	}

}
