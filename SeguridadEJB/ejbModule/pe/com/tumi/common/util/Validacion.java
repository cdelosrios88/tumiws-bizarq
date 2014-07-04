package pe.com.tumi.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validacion {
	
	public static boolean validarFormatoClave(String pStrLinea){
		boolean esValido = false;
		Pattern pattern = Pattern.compile("^([A-Z]{1}[0-9]{2}){2,}$");
		Matcher matcher = pattern.matcher(pStrLinea.trim());
		esValido = matcher.matches();
		return esValido;
	}
}
