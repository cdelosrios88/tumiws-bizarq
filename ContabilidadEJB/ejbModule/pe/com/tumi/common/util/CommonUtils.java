package pe.com.tumi.common.util;

/**
 * @author Bizarq Technologies
 * Cod Req: REQ14-004
 * Descripci�n: Clase utilitaria que albergar� los m�todos m�s comunes usados en la implementaci�n
 * 				de m�todos tales como fechas, periodos, sucursal, sub sucursal, etc.
 * Fecha: 24/05/2014
 * */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;

public class CommonUtils {
	
	protected static Logger log;
	
	public CommonUtils(){
		log = Logger.getLogger(this.getClass());
		
	}
	
	/**
	 * @author Christian De los R�os
	 * @param  Ninguno
	 * @return List<SelectItem> : Retorna una lista con un a�o menos al actual y 
	 * 								5 posteriores desde el presente 
	 * */
	public static final List<SelectItem> getListAnios() {
		List<SelectItem> listYears = new ArrayList<SelectItem>(); 
		try {
			int year=Calendar.getInstance().get(Calendar.YEAR);
			int cont=0;

			for(int j=year; j>=year-5; j--){
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
	
	/**
	 * @author Christian De los R�os - Bizarq
	 * Descripci�n:
	 * M�todo que retorna el ID correcto de una determinada sucursal de acuerdo al ID Persona
	 * @param 
	 * 		<Integer>intIdPersona<Integer>
	 * @return retorna el Id Sucursal de acuerdo a su PK Jur�dica.
	 * */
	public static final Integer getSucursalIdByPkPersona(Integer intIdPersona){
		Integer intIdSucursal = null;
		Sucursal sucursal = null;
		try {
			EmpresaFacadeRemote localEmpresa = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
			sucursal = localEmpresa.getSucursalPorIdPersona(intIdPersona);
			intIdSucursal = sucursal.getId().getIntIdSucursal();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return intIdSucursal;
	}
	
	public static final Calendar getPreviousMonth(Integer intYear, Integer intMonth){
		//SimpleDateFormat format = new SimpleDateFormat(strDateFormat);
		Calendar cal = Calendar.getInstance();
		cal.set(intYear, intMonth-1, Calendar.DAY_OF_MONTH);
		cal.add(Calendar.MONTH, -1);
		//System.out.println(format.format(cal.getTime()));
		return cal;
	}
	
}
