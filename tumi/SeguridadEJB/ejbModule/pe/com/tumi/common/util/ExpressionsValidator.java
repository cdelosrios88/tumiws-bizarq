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
	 * Valida que la direcci�n IP cumpla con el formato "usuario@dominio"
	 * @param correo
	 * @return <b>true</b> en caso de �xito y <b>false</b> en caso contrario
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
	 * Valida que la cadena de texto enviada como par�metro tenga solamente valores num�ricos
	 * @param cadenaNumero string con valor num�rico
	 * @return <b>true</b> en caso de �xito y <b>false</b> en caso de error
	 */
	public static boolean validarSoloNumeros(String cadenaNumero){
		if(cadenaNumero==null){
			return false;
		}
		// Valida que el texto solamente contenga n�meros
		Pattern p = Pattern.compile("^\\d+");
		Matcher m = p.matcher(cadenaNumero);
		if (m.matches()){
			return true;
		}
		return false;
	}
	/**
	 * Retorna true si es que el par�metro 1 es menor o igual (dependiendo del tipo de validaci�n) que el par�metro 2 
	 * @param numero1 n�mero entero 
	 * @param numero2 n�mero entero
	 * @param tipoValidacion <b>LE</b> cuando se quiere que la validaci�n sea de menor o igual y 
	 * <b>L</b> cuando se quiere que la validaci�n sea menor   
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
	 * Valida que la cadena de texto s�lo contenga caracteres alfanum�ricos, gui�n 
	 * y gui�n abajo.
	 * @param cadena texto que se validar�
	 * @return <b>true</b> en caso de �xito <b>false</b> en caso contrario 
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