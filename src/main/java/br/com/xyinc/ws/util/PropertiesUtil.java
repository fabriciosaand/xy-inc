package br.com.xyinc.ws.util;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ResourceBundle;

public class PropertiesUtil {

	public static String getProperty(String propertie, Object... parametros) throws IOException {
		ResourceBundle rb = ResourceBundle.getBundle("validationMessages");
		return MessageFormat.format(rb.getString(propertie), parametros);
	}

}
