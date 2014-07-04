package pe.com.tumi.common.util;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {
	/**
	 * Agrega un número específico de ceros a la cadena de texto. Dependiendo
	 * del flagDirection, los posicionará a la derecha o izquierda de la cadena
	 * de texto.
	 * 
	 * @param text
	 *            cadena de texto
	 * @param quantityOfZeros
	 *            cantidad de ceros que se aumentarán a la cadena de texto
	 * @param flgDirection
	 *            <i>true</i> sitúa los ceros a la izquierda y <i>false</i>
	 *            sitúa los ceros a la derecha de la cadena de texto
	 * @return cadena de texto con ceros agregados
	*/ 
	public synchronized static String fillZeros(String text,
			int quantityOfZeros, boolean flgDirection) {
		if (text == null) {
			text = "";
		}
		// Generating an specific quantity of zeros
		String zerosGenerated = "";
		for (int i = 0; i < quantityOfZeros; i++) {
			zerosGenerated = zerosGenerated + "0";
		}
		
		if (flgDirection) {
			text = text + zerosGenerated;
		} else {
			text = zerosGenerated + text;
		}
		return text;
	}

	/**
	 * Completa un objeto con la cadena de texto enviada como parámetro hasta
	 * que el objeto (el cual será casteado a String) tenga que el sea igual al
	 * enviado como parámetro. Dependiendo del flagDirection, se completará el
	 * texto a la derecha o a la izquierda.
	 * 
	 * @param object
	 *            Integer o String, el cual será parseado a String
	 * @param padding
	 *            texto con un solo caracter (si tiene mas de uno, se coge solo
	 *            el primero) que servirá para completar el texto inicial
	 * @param length
	 *            tamaño que tendrá el texto final después de completado
	 * @param flagDirection
	 *            true para completar el texto hacia la derecha y false para la
	 *            izquieda
	 * @return texto con contenido agregado a la derecha o izquierda
	 */
	public static String fillContent(Object object, String padding, int length,
			boolean flagDirection) {
		String text = object.toString();
		if (text.length() >= length || padding == null) {
			return text;
		}
		if (padding.length() > 1) {
			padding = padding.substring(0, 1);
		}
		String newText = text;
		if (flagDirection) {
			for (int i = text.length(); i < length; i++) {
				newText = newText + padding;
			}
		} else {
			for (int i = text.length(); i < length; i++) {
				newText = padding + newText;
			}
		}
		return newText;
	}

	public boolean findStringInArray(String cadenaABuscar, String[] cadenas) {
		boolean respuesta = false;
		if (cadenas != null) {
			for (int i = 0; i < cadenas.length; i++) {
				if (cadenas[i] != null && !cadenas[i].trim().equals("")
						&& cadenas[i].trim().equals(cadenaABuscar)) {
					respuesta = true;
					break;
				}
			}
		}
		return respuesta;
	}

	public synchronized static String slotId(String cad) {
		String str = "";
		if (cad.split("/").length >= 2) {
			str = cad.split("/")[1];
		} else {
			str = null;
		}
		return str;
	}
	
	public synchronized static String extractSelectedLine(String sourceString, int indexLineToExtract){
		int linea = indexLineToExtract;
		String resultado = null;
		String cadena = sourceString;
		String[] lineas = cadena.split("\n");
		if (lineas.length >= linea){
			resultado = lineas[linea-1];  
		}
		return resultado;
	}
	
	public String[] getIpAndMaskFromRouteLine(String linea){
		String[] ip = null;
		linea = "ip route       200.37.36.32 255.255.255.224 GigabitEthernet0/1/0.2367 172.22.14.22";
		linea = linea.replaceFirst("ip route", "").trim();
		String[] resultados = linea.split(" {1,50}");
		ip = new String[]{resultados[2],resultados[3]};
		return ip;
	}
	public String[] getIpAndMaskFromPrefixLine(String linea){
		String[] ip = null;
		linea = "ip route       200.37.36.32 255.255.255.224 GigabitEthernet0/1/0.2367 172.22.14.22";
		linea = linea.replaceFirst("ip route", "").trim();
		String[] resultados = linea.split(" {1,50}");
		resultados = resultados[6].split("/");
		ip = new String[]{resultados[0],resultados[1]};
		return ip;
	}
	
	/**
	 * @author TaT!
	 * Devuelve la estacion (Hostname)
	 * del formato getLocalHost()
	 * @param String
	 */
	public static String dameMiHost(String host){
		String ip="";
			ip=host.substring(0, host.indexOf("/"));
		return ip;
	}
	
	/**
	 * @author TaT!
	 * Devuelve el ip
	 * del formato getLocalHost()
	 * @param String
	 */
	public static String dameMiIp(String host){
		String hostName="";
		hostName=host.substring(host.indexOf("/")+1);
		return hostName;
	}
	
	/**
	 * @author TaT!
	 * Devuelve verdadero si la cadena base [base]
	 * no contempla la cadena de comparacion [compareWith]
	 * @param String
	 */
	public static boolean validame(String base , String compareWith){
		String cadena = compareWith.toUpperCase();
		String miCod = cadena;
		String tuCod = miCad(cadena,",");

		for (int j=0; j<=base.length();j++){
			if (!contains(base.toUpperCase(),tuCod)){
				miCod=reCad(miCod,",");
				tuCod=miCad(miCod,",");
			}else{
				return false;
			}					
		}
			return true;
	}
	
	/**
	 * @author TaT!
	 * Devuelve el numero de veces que aparece
	 * un caracter en una cadena
	 *@param cadena, caracter
	*/
	public static int frecuenciaCaracter(String cadena, String caracter){
		int frecuencia=0;
		for(int i=0;i<=(cadena.length()-1);i++){
			if(String.valueOf(cadena.charAt(i)).equals(caracter)){
				frecuencia++;
			}
		}
		return frecuencia;
	}
	
	/**
	 * @author TaT!
	 * Invierte la cadena recibida
	 *@param sCadena
	*/	
	public static String invertirCadena(String sCadena){
		String sCadenaInvertida="";
		for (int x=sCadena.length()-1;x>=0;x--)
			sCadenaInvertida = sCadenaInvertida + sCadena.charAt(x);
		return sCadenaInvertida;
	}
	
	/**
	 * @author TaT!
	 * Devuelve verdadero si la cadena recibida
	 * tiene numeros
	 *@param cadena
	*/	
	public static boolean tieneNumeros(String cadena){
	     Pattern p = Pattern.compile("[0-9]");
	     Matcher m = p.matcher(cadena);
	      if (m.find()){
	    	  return true;
	      }
	      return false;
	}
	
	/**
	 * @author TaT!
	 * Devuelve verdadero si la cadena recibida
	 * tiene letras
	 *@param cadena
	*/	
	public static boolean tieneLetras(String cadena){
	     Pattern p = Pattern.compile("[A-Za-z]");
	     Matcher m = p.matcher(cadena);
	      if (m.find()){
	    	  return true;
	      }
	      return false;
	}

	/**
	 * @author TaT!
	 * De una cadena separada por caracteres [,]
	 * devuelve la primera subcadena hasta antes del caracter
	 * @param cadena, caracter
	*/	
	public static String miCad(String cadena , String caracter){
		String miCadena="";
		int i = cadena.indexOf(caracter);
		if (i!=-1){
			miCadena=cadena.substring(0, i);
		}else{
			miCadena=cadena;
		}	
		return miCadena;
	}

	/**
	 * @author TaT!
	 * De una cadena separada por caracteres [,]
	 * devuelve la subcadena despues del primer caracter encontrado
	 * hasta el fin de la cadena
	 * @param cadena, caracter
	 */	
	public static String reCad(String cadena, String caracter){
		String reCadena="";
		int i=cadena.indexOf(caracter);
		if (i!=-1){
			reCadena=cadena.substring(i+1,cadena.length());
		}else{
			reCadena=cadena;
		}		
			return reCadena;
	}
	
	public static String formatCondition(String condition){
			//String listaCondicion = "";
			String var = "ID=";
			String cond = " OR ";
			String listaCondicion = "";
			listaCondicion = miCad(condition,",");
			String ci=condition;
			listaCondicion = var + listaCondicion ;
			
			for (int j=0; j<frecuenciaCaracter(condition, ",");j++){
					ci=reCad(ci,",");
					listaCondicion= listaCondicion + cond + var + miCad(ci,",");
			}					
		return listaCondicion;
	}
	
	public static String formatParams(String params, String values){
		String list = "";
		String var = " = ";
		String p = params;
		String v = values;
		list = miCad(params,",") + var + miCad(values,",") ;

		for (int j=0; j<frecuenciaCaracter(params, ",");j++){
			p=reCad(p,",");
			v=reCad(v,",");
			list = list + " , " + miCad(p,",") + var + miCad(v,",");
		}
		return list;
	}
	
	public static boolean contains (String cadenaBase, String cadenaComparar){
		for (int i=0; i<= cadenaBase.length(); i++)
			{ 	if(cadenaBase.substring(i).equalsIgnoreCase(cadenaComparar)){
					return true;
			}
		}
		return false;
	}

	public static String getPinNumber() {
		return getPassword(Constante.NUMEROS, 4);
	}
 
	public static String getPassword(int length) {
		return getPassword(Constante.NUMEROS + Constante.MAYUSCULAS + Constante.MINUSCULAS, length);
	}
 
	public static String getPassword(String key, int length) {
		String pswd = "";
 
		for (int i = 0; i < length; i++) {
			pswd+=(key.charAt((int)(Math.random() * key.length())));
		}
 
		return pswd;
	}

	/**
	 * @author TaT!
	 * recibe una lista de passwords almacenados
	 * y devuelve los i ultimos elementos almacenados 
	 **/
	public static String listaPassword(String listaPass, int i){
		String listaActPass="";
		int f=0;
		for (int j=0; j<listaPass.length();j++){
			if (String.valueOf(listaPass.charAt(j)).equals(",")){
				f++;
			}
			if(f<i){
					listaActPass=listaActPass+listaPass.charAt(j);
			}
		}
		return listaActPass;
	}
	
	public static List headersList(Object referenceObject){
		List listHeaders = new ArrayList();
        Field[] fields = referenceObject.getClass().getDeclaredFields();
	    for(int z = 0; z < fields.length; z++){
	    	listHeaders.add(fields[z].getName());
	    }
	    Collections.sort(listHeaders);		
		return listHeaders;
	}	
	
	/**
	 * @author TaT!
	 * recibe un objeto y devuelve una lista con todos los valores
	 * seteados al objeto
	 * @param Object
	 * @return List  
	 **/
	public static List objectToList(Object referenceObject){
		List list = new ArrayList();
        List fieldList = headersList(referenceObject);
	    for(int y = 0; y < fieldList.size(); y++){
	    	String field = (String) fieldList.get(y);
	        try{
	            Method getter = referenceObject.getClass().getMethod("get" +
	                        String.valueOf(field.charAt(0)).toUpperCase() +
	                        field.substring(1), null);
	            list.add(getter.invoke(referenceObject, null));
	        }catch(Exception ex){
	            ex.printStackTrace();
	        }
	    }
 		return list;
	}
	
	/**
	 * @author TaT!
	 * recibe un objeto y devuelve un StringBuffer con todos los valores
	 * seteados al objeto
	 * @param Object
	 * @return StringBuffer  
	 **/
	public static StringBuffer objectToString(Object referenceObject, boolean header){
        Field[] fields = referenceObject.getClass().getDeclaredFields();
        List fieldList = new ArrayList();
		StringBuffer sb = new StringBuffer();
		//Llenar lista con campos del obj
	    for(int z = 0; z < fields.length; z++){
	        fieldList.add(fields[z].getName());
	    }
	    Collections.sort(fieldList);
	    //Llenar Cabeceras
	    if (header){
		    for(int z = 0; z < fieldList.size(); z++){
			       	sb.append(((String)fieldList.get(z)).toUpperCase())
			        .append(Constante.SEPARADOR_TEXTO_PLANO_SIMPLE);
		    	}
		    sb.append(Constante.LN);
	    }
	    //Llenar valores
	    for(int y = 0; y < fieldList.size(); y++){
	    	String field = (String) fieldList.get(y);
	        try{
	            Method getter = referenceObject.getClass().getMethod("get" +
	                        String.valueOf(field.charAt(0)).toUpperCase() +
	                        field.substring(1), null);
	            sb.append(getter.invoke(referenceObject, null));
	        }catch(Exception ex){
	            ex.printStackTrace();
	        }
	        sb.append(Constante.SEPARADOR_TEXTO_PLANO_SIMPLE);
	    }
	    sb.append(Constante.LN);
 		return sb;
	}

	public static boolean isNumeric(Object o){
		String entero = String.valueOf(o);
		try{
			int z = Integer.parseInt(entero);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public static boolean isDecimal(String o){		
		try{
			double z = Double.parseDouble(o);
			if( z > 0  && !o.substring(0, 1).equals(".") && !o.substring(o.length()-1, 1).equals(".")){
			   return true;
			}else
			     return false;
		}catch(Exception e){
			   return false;
		}
	}
	
	public static int getError(StringWriter sw){
		int i = sw.toString().indexOf("ORA-") + 4;
		int j = sw.toString().indexOf("ORA-") + 9;
		String codeValue = sw.toString().substring(i,j);
		int errorCode = 0;
		boolean flag6502 = false;
		if (StringHelper.isNumeric(codeValue)){
			int nextI = 0;
			int nextJ = 0;
			if (codeValue.equals("06502")){
				nextI = sw.toString().indexOf("ORA-", j) + 4;
				nextJ = sw.toString().indexOf("ORA-", j) + 9;
				codeValue = sw.toString().substring(nextI, nextJ);
				flag6502 = true;
			}
			if (flag6502){
				if (codeValue.equals("06512")){
					nextI = sw.toString().indexOf("ORA-", nextJ) + 4;
					nextJ = sw.toString().indexOf("ORA-", nextJ) + 9;
				}
			}
			if(codeValue.equals("06550")){
				nextI = sw.toString().indexOf("PLS-") + 4;
				nextJ = sw.toString().indexOf("PLS-") + 9;
			}
			codeValue = sw.toString().substring(nextI, nextJ);

			errorCode = Integer.parseInt(codeValue);
		}
		return errorCode;
	}
	
	/**********************************************************/
	/*                                                        */
	/* Nombre : isNumerico 									  */
	/* Param  : cadena que es string que representa un numero */
	/* 													      */
	/* Objetivo: Validar un numero entero        			  */
	/*                                                        */
	/* Retorno : True si tiene el formato correcto sino fasle */ 
	/*                                                        */ 
	/**********************************************************/
	public static boolean isNumerico(String cadena){
        //Validando que todo sea numerico
        Pattern p = Pattern.compile("[^0-9]");
        Matcher m = p.matcher(cadena);
        return !m.find();
    }
    
    public static boolean isLetter(String cadenaLetra){
        //Pattern p = Pattern.compile("[^A-Z][^a-z]");
        Pattern p = Pattern.compile("^[a-zA-ZñÑáéíóúÁÉÍÓÚ]+$");
        Matcher m = p.matcher(cadenaLetra);
        return !m.find(); 
    }
   
    /**********************************************************/
	/*                                                        */
	/* Nombre : isDouble 									  */
	/* Param  : String que representa un numero               */
	/* 													      */
	/* Objetivo: Validar un numero decimal      			  */
	/*                                                        */
	/* Retorno : True si tiene el formato correcto sino fasle */ 
	/*                                                        */ 
	/**********************************************************/
   public static boolean isDouble(String cadena){
    	
      Pattern p = Pattern.compile("[0-9]{1,5}(\\.[0-9]{1,4})?");
      Matcher m = p.matcher(cadena);
      return m.matches(); 
    }
     
	public static void main (String[] args){
	}
	/**********************************************************/
	/*                                                        */
	/* Nombre : obtenerFormatoDecimal 					      */
	/* Param  : Numero double                                 */
	/* 													      */
	/* Objetivo: Formatear numeros decimales      			  */
	/*                                                        */
	/* Retorno : Un String con formato                        */ 
	/*                                                        */ 
	/**********************************************************/
	public static String obtenerFormatoDecimal(double nValor){
		       
        DecimalFormat df = new DecimalFormat("##,###,##0.00");
        DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        dfs.setGroupingSeparator(',');
        df.setDecimalFormatSymbols(dfs);
        String cadena =df.format(nValor);        
        return cadena;

    }  
	
	/**********************************************************/
	/*                                                        */
	/* Nombre : reemplazarCaracter 					          */
	/* Param  : cadena : Numero a formatear                   */
	/*        : pos    : posicion del caracter                */
	/*        : cadena : Caracter nuevo                       */
	/* 													      */
	/* Objetivo: Reemplazar caracters      			          */
	/*                                                        */
	/* Retorno : Un String con formato                        */ 
	/*                                                        */ 
	/**********************************************************/
	
	public static String reemplazarCaracter(String cadena,int pos, char pad){
		
        StringBuffer buf = new StringBuffer( cadena );
        buf.setCharAt( pos, pad);
        return buf.toString();
    }
	
	/**********************************************************/
	/*                                                        */
	/* Nombre : obtenerFormatoDecimales1			          */
	/* Param  : cadena : Numero a formatear                   */	
	/* 													      */
	/* Objetivo: Dar formato		     			          */
	/*                                                        */
	/* Retorno : Un String con formato                        */ 
	/*                                                        */ 
	/**********************************************************/
   public static String obtenerFormatoDecimal1(String cadena){
	   
      int pos = 0;
	  for (int i = 0; i < cadena.length(); i++) {
		   if (cadena.charAt(i) == '.') {
		       pos = i;
		   }
      }			 		
	  return reemplazarCaracter(cadena.replace(',', '.'), pos, ',');   
	 }
	 
}
