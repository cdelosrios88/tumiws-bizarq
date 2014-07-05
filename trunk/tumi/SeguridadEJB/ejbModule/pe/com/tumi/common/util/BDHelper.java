package pe.com.tumi.common.util;

import java.io.*;
import java.sql.SQLException;

/**
 * Clase utilitaria para menejo de mensajeria en la base de datos
 * @author Diego Bustamante
 * @Fecha: 01/12/2009
 * @FechaMod:01/12/2009
 */

/**
 * Actualizado tat!
 **/

public class BDHelper {

	/**
	 * Metodo que administra los ORA-ERROR generados por la base de datos
	 * @param erroSQL - Metodo de tipo Exception que contiene el error que se genero 
	 * al ejecutar el proceso en la base de datos
	 * 
	 * @return Mensaje de error
	 */
	public static String getSQLError(Exception erroSQL){
		try{
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			erroSQL.printStackTrace(pw);
			int errorCode = StringHelper.getError(sw);
			String mensaje = "";
			if (errorCode!=0){
					switch (errorCode){
						case 1: 
							mensaje="Error en BD: Se desea grabar un registro que ya existe"; break;
						case 18: 
							mensaje="Error en BD: Base de datos, no responde";break;
						case 22: 
							mensaje="Error en BD: Error por invalidacion de logueo a la base de datos";break;
						case 100:
							mensaje="Error en BD: Error no encuentra data que se busca";break;
						case 302:
							mensaje="Error en BD: Error no se encuentra el(los) componente(s) solicitado(s) en el package.";break;							
						case 911:
							mensaje="Error en BD: Error por motivos de caracter invalido";break;
						case 1033:
							mensaje="Error en BD: Error la base de datos se esta apagando";break;
						case 1073:
							mensaje="Error en BD: Error fallas de conexion con la base de datos";break;
						case 1426:
							mensaje="Error en BD: Error por numero muy grande";break;
						case 1456:
							mensaje="Error en BD: Error no se puede ejecutar operacion";break;
						case 1489:
							mensaje="Error en BD: Error cadena a procesar es muy larga";break;
						case 2043:
							mensaje="Error en BD: Error no se pudo ejecutar accion";break;
						case 2292: 
							mensaje="Error en BD: Error por integridad de data. Registro secundario encontrado."; break;
						case 12336:
							mensaje="Error en BD: Error no pudo sincronizar con base de datos";break;
						case 13009:
							mensaje="Error en BD: Error cadena ingresada es invalida";break;
						case 13011:
							mensaje="Error en BD: Error valor muy largo";break;
						case 22973:
							mensaje="Error en BD: Error cadena que se quiere modificar es muy larga de la permitida";break;
						case 29355:
							mensaje="Error en BD: Error faltan llenar campos";break;// Error por recibir null
						case 32802:
							mensaje="Error en BD: Error al intentar ingresar un valor que no es cadena";break;
						case 32806:
							mensaje="Error en BD: Error uno de los valores a ingresar es muy largo";break;
						case 38101:
							mensaje="Error en BD: Error no se pudo insertar la data requerida";break;
						case 38103:
							mensaje="Error en BD: Error no se pudo actualizar registro";break;
						default:
							mensaje="Error en BD: Error al ejecutar la operacion"; break;
					}
			}else{
				if (erroSQL != null){
					int i = sw.toString().indexOf("error code [");
					int j = sw.toString().indexOf("]", i);
					String valueSQL = sw.toString().substring(i+ 12,j);
					switch (Integer.parseInt(valueSQL)){
						case 17:	 	
							mensaje = "Error en BD: El adaptador de red no pudo establecer la conexión."; break;
						case 17002: 	
							mensaje = "Error en BD: El adaptador de red no pudo establecer la conexión."; break;
						case 4060:		
							mensaje = "Error en BD: No existe BD."; break;
						case 18456: 	
							mensaje = "Error en BD: Error, por invalidacion de logueo a la base de datos"; break;
						case 547: 		
							mensaje = "Error en BD: Error por integridad de data";break;
						default:		
							mensaje = "Error en BD: Error al ejecutar la operacion"; break;
					}
				}					
			}
			return mensaje;
		}catch(Exception ex){
			return "Error en BD: Error al ejecutar la operacion, error controlado";
		}
	}
	
}
