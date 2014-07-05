package pe.com.tumi.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class ExpressionsValidator {

	protected static Logger log = Logger.getLogger(ExpressionsValidator.class);
	/*private static ClasesIP clasesIP;
	private enum ClasesIP{
			A, B, C;
	}
	*/
	/**
	 * Valida que la dirección IP cumpla con el formato "usuario@dominio"
	 * @param correo
	 * @return <b>true</b> en caso de éxito y <b>false</b> en caso contrario
	 */
	public static boolean validarEmail(String correo){
		String input = correo;
		// comprueba que no empieze por punto o @
		Pattern p = Pattern.compile("^([0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9})$");
		Matcher m = p.matcher(input);
		if(!m.matches()){
			return false;
		}
		return true;
	}

	/**
	 * Valida que la cadena de texto enviada como parámetro tenga solamente valores numéricos
	 * @param cadenaNumero string con valor numérico
	 * @return <b>true</b> en caso de éxito y <b>false</b> en caso de error
	 */
	public static boolean validarSoloNumeros(String cadenaNumero){
		if(cadenaNumero==null){
			return false;
		}
		// Valida que el texto solamente contenga números
		Pattern p = Pattern.compile("^\\d+");
		Matcher m = p.matcher(cadenaNumero);
		if (m.matches()){
			return true;
		}
		return false;
	}
	/**
	 * Retorna true si es que el parámetro 1 es menor o igual (dependiendo del tipo de validación) que el parámetro 2 
	 * @param numero1 número entero 
	 * @param numero2 número entero
	 * @param tipoValidacion <b>LE</b> cuando se quiere que la validación sea de menor o igual y 
	 * <b>L</b> cuando se quiere que la validación sea menor   
	 * @return
	 */
	public static boolean esNumeroMenor(int numero1, int numero2, String tipoValidacion){
		if(tipoValidacion.equals("L")){
			if(numero1<numero2){
				return true;
			}	
		}else if(tipoValidacion.equals("LE")){
			if(numero1<=numero2){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Valida que la cadena de texto sölo contenga caracteres alfanuméricos, guión 
	 * y guión abajo.
	 * @param cadena texto que se validará
	 * @return <b>true</b> en caso de éxito <b>false</b> en caso contrario 
	 */
	public static boolean validarSoloAlfanumerico(String cadena){
		if(cadena==null){
			return false;
		}
		Pattern p = Pattern.compile("^([a-zA-Z0-9/._-]+)$");
		Matcher m = p.matcher(cadena);
		if(m.matches()){
			return true;
		}
		return false;
	}
	
}	