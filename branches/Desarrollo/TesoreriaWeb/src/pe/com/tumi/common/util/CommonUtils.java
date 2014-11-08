/**
* Resumen.
* Objeto: CommonUtils
* Descripción:  Clase utilitaria que albergará los métodos más comunes usados en la implementación
 * 				de métodos tales como fechas, periodos, sucursal, sub sucursal, etc.
* Fecha de Creación: 05/11/2014.
* Requerimiento de Creación: REQ14-006
* Autor: Bizarq
*/
package pe.com.tumi.common.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.tesoreria.conciliacion.controller.TumiCalendar;

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
	 * @author Christian De los Ríos - Bizarq
	 * Descripción:
	 * Método que retorna el ID correcto de una determinada sucursal de acuerdo al ID Persona
	 * @param 
	 * 		<Integer>intIdPersona<Integer>
	 * @return retorna el Id Sucursal de acuerdo a su PK Jurìdica.
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
		Calendar cal = Calendar.getInstance();
		cal.set(intYear, intMonth-1, Calendar.DAY_OF_MONTH);
		cal.add(Calendar.MONTH, -1);
		return cal;
	}
	
	public static final String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}
	
	private static final String[] HEADERS_TO_TRY = { 
	    "X-Forwarded-For",
	    "Proxy-Client-IP",
	    "WL-Proxy-Client-IP",
	    "HTTP_X_FORWARDED_FOR",
	    "HTTP_X_FORWARDED",
	    "HTTP_X_CLUSTER_CLIENT_IP",
	    "HTTP_CLIENT_IP",
	    "HTTP_FORWARDED_FOR",
	    "HTTP_FORWARDED",
	    "HTTP_VIA",
	    "REMOTE_ADDR" };

	public static final String getClientIpAddress(HttpServletRequest request) {
	    for (String header : HEADERS_TO_TRY) {
	        String ip = request.getHeader(header);
	        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
	            return ip;
	        }
	    }
	    return request.getRemoteAddr();
	}
	
	/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
	/**
	 * gets previous working day before the n number of days
	 * 
	 * @param dtDate
	 *            <code>Date</code>
	 * @param lnNoDays
	 *            <code>long</code>
	 * @return <code>Date</code>
	 * @throws BaseException
	 * 
	 */
	public static Date getPreviousUtilDay(Date dtDate, long lnNoDays)
			throws Exception {
		TumiCalendar objCavCal = new TumiCalendar(dtDate);
		try {
			int i = 0;
			while (i < lnNoDays) {
				objCavCal.add(Calendar.DATE, -1);
				if (objCavCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
					continue;
				} else {
					i++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objCavCal.getTime();
	}
	/* Fin: REQ14-006 Bizarq - 26/10/2014 */
}
