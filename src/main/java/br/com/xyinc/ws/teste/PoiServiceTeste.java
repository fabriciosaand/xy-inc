package br.com.xyinc.ws.teste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpStatus;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import br.com.xyinc.ws.service.PoiService;
import br.com.xyinc.ws.util.PropertiesUtil;

public class PoiServiceTeste extends JerseyTest {

	private static final String RETORNA_STATUS_OK = "poiServiceTeste.retorna.status.ok";
	private static final String RETORNA_STATUS_CREATED = "poiServiceTeste.retorna.status.created";
	private static final String RETORNA_STATUS_BAD_REQUEST = "poiServiceTeste.retorna.status.bad_request";
	private static final String RETORNA_MENSAGEM = "poiServiceTeste.retorna.mensagem";
	private static final String RETORNAR_NO_FORMATO_JSON = "poiServiceTeste.retorna.formato.json";
	private static final String RETORNAR_UMA_LISTA = "poiServiceTeste.retorna.lista";

	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(PoiService.class);
	}

	@Test
	public void testaListarTodos() throws IOException {
		Response output = target("/poi/listarTodos").request().get();
		assertEquals(PropertiesUtil.getProperty(RETORNA_STATUS_OK), HttpStatus.OK_200, output.getStatus());
		assertNotNull(PropertiesUtil.getProperty(RETORNAR_UMA_LISTA), output.getEntity());
		assertEquals(PropertiesUtil.getProperty(RETORNAR_NO_FORMATO_JSON), MediaType.APPLICATION_JSON,
				output.getMediaType().toString());
	}

	@Test
	public void testaListarPorProximidade() throws IOException {
		Response output = target("/poi/listarPorProximidade")
				.queryParam("coordenadaX", "20")
				.queryParam("coordenadaY", "10")
				.queryParam("dMax", "10")
				.request().get();
		
		assertEquals(PropertiesUtil.getProperty(RETORNA_STATUS_OK), HttpStatus.OK_200, output.getStatus());
		assertNotNull(PropertiesUtil.getProperty(RETORNAR_UMA_LISTA), output.getEntity());
		assertEquals(PropertiesUtil.getProperty(RETORNAR_NO_FORMATO_JSON), MediaType.APPLICATION_JSON,
				output.getMediaType().toString());
	}

	@Test
	public void testaCadastrar() throws IOException {
		Response output = target("/poi/cadastrar")
				.queryParam("nome", "Lanchonete")
				.queryParam("coordenadaX", "27")
				.queryParam("coordenadaY", "12")
				.request().get();
		
		assertEquals(PropertiesUtil.getProperty(RETORNA_STATUS_CREATED), HttpStatus.CREATED_201, output.getStatus());
		assertNotNull(PropertiesUtil.getProperty(RETORNA_MENSAGEM), output.getEntity());
		assertEquals(PropertiesUtil.getProperty(RETORNAR_NO_FORMATO_JSON), MediaType.APPLICATION_JSON, 
				output.getMediaType().toString());

	}

	@Test
	public void testaCoordenadaNegativa() throws IOException {
		Response output = target("/poi/cadastrar")
				.queryParam("nome", "Lanchonete")
				.queryParam("coordenadaX", "-1")
				.queryParam("coordenadaY", "0")
				.request().get();

		assertEquals(PropertiesUtil.getProperty(RETORNA_STATUS_OK), HttpStatus.OK_200, output.getStatus());
		assertNotNull(PropertiesUtil.getProperty(RETORNA_MENSAGEM), output.getStatus());
		assertEquals(PropertiesUtil.getProperty(RETORNAR_NO_FORMATO_JSON), MediaType.APPLICATION_JSON, 
				output.getMediaType().toString());

		output = target("/poi/cadastrar")
				.queryParam("nome", "Lanchonete")
				.queryParam("coordenadaX", "0")
				.queryParam("coordenadaY", "-1")
				.request().get();

		assertEquals(PropertiesUtil.getProperty(RETORNA_STATUS_OK), HttpStatus.OK_200, output.getStatus());
		assertNotNull(PropertiesUtil.getProperty(RETORNA_MENSAGEM), output.getStatus());
		assertEquals(PropertiesUtil.getProperty(RETORNAR_NO_FORMATO_JSON), MediaType.APPLICATION_JSON, 
				output.getMediaType().toString());
	}

	@Test
	public void testaParametrosInvalidos() throws IOException {
		Response output = target("/poi/cadastrar")
				.queryParam("erro", "Lanchonete")
				.queryParam("coordenadaX", "0")
				.queryParam("coordenadaY", "0")
				.request().get();

		assertEquals(PropertiesUtil.getProperty(RETORNA_STATUS_BAD_REQUEST), HttpStatus.BAD_REQUEST_400, 
				output.getStatus());
		assertNotNull(PropertiesUtil.getProperty(RETORNA_MENSAGEM), output.getStatus());

		output = target("/poi/cadastrar")
				.queryParam("nome", "Lanchonete")
				.queryParam("erro", "0")
				.queryParam("coordenadaY", "0")
				.request().get();

		assertEquals(PropertiesUtil.getProperty(RETORNA_STATUS_BAD_REQUEST), HttpStatus.BAD_REQUEST_400, 
				output.getStatus());
		assertNotNull(PropertiesUtil.getProperty(RETORNA_MENSAGEM), output.getStatus());

		output = target("/poi/cadastrar")
				.queryParam("nome", "Lanchonete")
				.queryParam("coordenadaX", "0")
				.queryParam("erro", "0")
				.request().get();

		assertEquals(PropertiesUtil.getProperty(RETORNA_STATUS_BAD_REQUEST), HttpStatus.BAD_REQUEST_400, 
				output.getStatus());
		assertNotNull(PropertiesUtil.getProperty(RETORNA_MENSAGEM), output.getStatus());
	}
}
