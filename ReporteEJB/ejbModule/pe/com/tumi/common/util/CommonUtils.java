package pe.com.tumi.common.util;

/**
 * @author Christian De los Ríos
 * Descripción: Clase utilitaria que albergará los métodos más comunes usados en la implementación
 * 				de métodos tales como fechas, periodos, sucursal, sub sucursal, etc.
 * Fecha: 24/05/2014
 * */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

public class CommonUtils {
	
	protected static Logger log;
	
	public CommonUtils(){
		log = Logger.getLogger(this.getClass());
		
	}
	
	/**
	 * @author Christian De los Ríos
	 * @param  Ninguno
	 * @return List<SelectItem> : Retorna una lista con un año menos al actual y 
	 * 								5 posteriores desde el presente 
	 * */
	public static final List<SelectItem> getListAnios() {
		List<SelectItem> listYears = new ArrayList<SelectItem>(); 
		try {
			int year=Calendar.getInstance().get(Calendar.YEAR)+5;
			int cont=0;

			for(int j=year; j>=year-6; j--){
				cont++;
			}			
			for(int i=0; i<cont; i++){
				listYears.add(i, new SelectItem(year));
				year--;
			}	
		} catch (Exception e) {
			log.error("Error en getListYears ---> "+e);
		}
		return listYears;
	}
	
	public static final Integer concatPeriodo(Integer anio, Integer mes){
		Integer intPeriodo = 0;
		String strMes="";
		if (mes.compareTo(10)<0) {
			strMes="0"+mes;
		}else{
			strMes=mes.toString();
		}
		intPeriodo = Integer.valueOf(anio+strMes);
		return intPeriodo;
	}
	
	/* Inicio: REQ14-009 Bizarq - 17/12/2014 */
	public static List<SelectItem> getListAnios(int intIniAnio) {
		List<SelectItem> listYears = new ArrayList<SelectItem>(); 
		try {
			int year=intIniAnio;
			int cont=0;

			for(int j=year; j<=Calendar.getInstance().get(Calendar.YEAR); j++){
				cont++;
			}
			for(int i=0; i<cont; i++){
				listYears.add(i, new SelectItem(year));
				year--;
			}	
		} catch (Exception e) {
			log.error("Error en getListYears ---> "+e);
		}
		return listYears;
	}
	/* Fin: REQ14-009 Bizarq - 17/12/2014 */
}
