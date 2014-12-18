/* -----------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo                      Fecha            Nombre                      Descripción
* -----------------------------------------------------------------------------------------------------------
* REQ14-009       			17/12/2014     Christian De los Ríos        Se agregan nuevos métodos reutilizables para el inicio y fin d dia         
*/
package pe.com.tumi.common.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.empresa.domain.Area;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;

public class MyUtil {
	
	protected static Logger log = Logger.getLogger(MyUtil.class);
	
	public static final boolean equalsWithNulls(Object a, Object b){
		if (a==b) return true;
		if ((a==null)||(b==null)) return false;
		return a.equals(b);
	}
	
	public static Integer	obtenerAñoActual() throws Exception{
		return Calendar.getInstance().get(Calendar.YEAR);				
	}
	
	public static Integer	obtenerPeriodoActual() throws Exception{
		String strPeriodo = "";
		Calendar cal = Calendar.getInstance();
		int año = cal.get(Calendar.YEAR);
		int mes = cal.get(Calendar.MONTH);
		mes = mes + 1; 
		if(mes<10){
			strPeriodo = año + "0" + mes;
		}else{
			strPeriodo  = año + "" + mes;
		}		
		return Integer.parseInt(strPeriodo);		
	}
	
	public static Timestamp obtenerFechaActual(){
		return new Timestamp(new Date().getTime());
	}
	
	public static boolean poseeRol(Persona persona, Integer intIdRol)throws Exception{
		if(persona==null || persona.getPersonaEmpresa()==null || persona.getPersonaEmpresa().getListaPersonaRol()==null){
			log.info("persona-empresa-rol null");
			return false;
		}
		for(PersonaRol personaRol : persona.getPersonaEmpresa().getListaPersonaRol()){
			log.info(personaRol);
			if(personaRol.getId().getIntParaRolPk().equals(intIdRol)){
				log.info("existe rol!");
				return true;
			}				
		}
		return false;
	}
	
	public static String obtenerDescripcionDiferenciaEntreFechas(Date dtFechaInicio, Date dtFechaFin)throws Exception{
		Integer intDiferencia = obtenerDiasEntreFechas(dtFechaInicio, dtFechaFin);
		Integer intCantidadAños = intDiferencia / 360;
		intDiferencia = intDiferencia % 360;
		Integer intCantidadMeses = intDiferencia / 30;
		intDiferencia = intDiferencia % 30;
		Integer intCantidadDias = intDiferencia;
		String strDescripcion = "";
		if(intCantidadAños == 1){
			strDescripcion = intCantidadAños + " año";
		}else if(intCantidadAños > 1){
			strDescripcion = intCantidadAños + " años";
		}
		
		if(intCantidadMeses == 1){
			if(intCantidadAños>0) strDescripcion = strDescripcion +", ";
			strDescripcion = strDescripcion + intCantidadMeses + " mes";
		}else if(intCantidadMeses > 1){
			if(intCantidadAños>0) strDescripcion = strDescripcion +", ";
			strDescripcion = strDescripcion + intCantidadMeses + " meses";
		}
		
		if(intCantidadDias == 1){
			if(intCantidadMeses>0) strDescripcion = strDescripcion +", ";			
			strDescripcion = strDescripcion + intCantidadDias + " día";
		}else if(intCantidadDias > 1){
			if(intCantidadMeses>0) strDescripcion = strDescripcion +", ";			
			strDescripcion = strDescripcion + intCantidadDias + " días";
		}
		
		return strDescripcion;
	}
	
//	public static Integer obtenerDiasEntreFechas(Date dtFechaInicio, Date dtFechaFin)throws Exception{
//		return (int)( (dtFechaFin.getTime() - dtFechaInicio.getTime()) / (1000 * 60 * 60 * 24) );
//	}
	
	public static Integer obtenerDiasEntreFechas(Date dtFechaInicio, Date dtFechaFin)throws Exception{
		SimpleDateFormat strEnlace = new SimpleDateFormat("dd/MM/yyyy");
		Date dtFecIni = strEnlace.parse(strEnlace.format(dtFechaInicio));
		Date dtFecFin = strEnlace.parse(strEnlace.format(dtFechaFin));
		return (int)( (dtFecFin.getTime() - dtFecIni.getTime()) / (1000 * 60 * 60 * 24) );
	} 
	
	public static List<Tabla> cargarListaTablaSucursal(List<Tabla> listaTablaSucursal, Integer intIdEmpresa) throws Exception{
		
		EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
		TablaFacadeRemote tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
		
		List<Sucursal>listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(intIdEmpresa);
		//Ordena la sucursal alafabeticamente
		Collections.sort(listaSucursal, new Comparator<Sucursal>(){
			public int compare(Sucursal uno, Sucursal otro) {
				return uno.getJuridica().getStrSiglas().compareTo(otro.getJuridica().getStrSiglas());
			}
		});
		
		listaTablaSucursal = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TOTALES_SUCURSALES));
		Sucursal sucursal;
		Tabla tabla;
		for(Object o : listaSucursal){
			 sucursal = (Sucursal)o;
			 tabla = new Tabla();
			 tabla.setIntIdDetalle(sucursal.getId().getIntIdSucursal());
			 tabla.setStrDescripcion(sucursal.getJuridica().getStrSiglas());
			 listaTablaSucursal.add(tabla);
		}
		return listaTablaSucursal;
	}
	
	public static List<Sucursal> cargarListaSucursal(Integer intIdEmpresa) throws Exception{
		EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);		
		List<Sucursal> listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(intIdEmpresa);
		//Ordenamos por nombre
		Collections.sort(listaSucursal, new Comparator<Sucursal>(){
			public int compare(Sucursal uno, Sucursal otro) {
				return uno.getJuridica().getStrSiglas().compareTo(otro.getJuridica().getStrSiglas());
			}
		});	
		return listaSucursal;
	}
	
	public static List<Sucursal> cargarListaSucursalConSubsucursalArea(Integer intIdEmpresa) throws Exception{
		EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);		
		List<Sucursal> listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(intIdEmpresa);
		for(Sucursal sucursal : listaSucursal){
			sucursal.setListaSubSucursal(empresaFacade.getListaSubSucursalPorIdSucursal(sucursal.getId().getIntIdSucursal()));
			sucursal.setListaArea(empresaFacade.getListaAreaPorSucursal(sucursal));
		}
		//Ordenamos por nombre
		Collections.sort(listaSucursal, new Comparator<Sucursal>(){
			public int compare(Sucursal uno, Sucursal otro) {
				return uno.getJuridica().getStrSiglas().compareTo(otro.getJuridica().getStrSiglas());
			}
		});
		return listaSucursal;
	}
	
	public static List<Tabla> obtenerListaAnios()throws Exception{
		int cantidadAñosLista = 5; 
		List<Tabla> listaAño = new ArrayList<Tabla>();
		Calendar cal=Calendar.getInstance();
		Tabla tabla = null;
		for(int i=0;i<cantidadAñosLista;i++){
			tabla = new Tabla();
			int year = cal.get(Calendar.YEAR);
			cal.add(Calendar.YEAR, -1);
			tabla.setIntIdDetalle(year);
			tabla.setStrDescripcion(""+year);
			listaAño.add(tabla);
		}
		return listaAño;
	}
	
	public static String obtenerEtiquetaTabla(Integer intIdDetalle, List<Tabla> listaTabla)throws Exception{
		for(Tabla tabla : listaTabla)
			if(tabla.getIntIdDetalle().equals(intIdDetalle))
				return tabla.getStrDescripcion();
		
		return "";
	}
	
	public static String convertirMonto(BigDecimal bdMonto)throws Exception{
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(','); 
		NumberFormat formato = new DecimalFormat("#,###.00",otherSymbols);
		return formato.format(bdMonto);
	}
	
	public static String convertirFecha(Date dtFecha)throws Exception{
		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(dtFecha);
	}
	
	public static void agregarNombreCompleto(Persona persona)throws Exception{
		persona.getNatural().setStrNombreCompleto(
				persona.getNatural().getStrNombres()+" "+
				persona.getNatural().getStrApellidoPaterno()+" "+
				persona.getNatural().getStrApellidoMaterno());
	}
	
	public static void agregarDocumentoDNI(Persona persona)throws Exception{
		for(Documento documento : persona.getListaDocumento())
			if(documento.getIntTipoIdentidadCod().equals(Constante.PARAM_T_TIPODOCUMENTO_DNI))
				persona.setDocumento(documento);
	}
	
	public static BigDecimal redondear2(BigDecimal d) {
		log.info(d);
		//d = d.setScale(2, BigDecimal.ROUND_UP);
		return d;
	}
	
	public static String formatoMonto(BigDecimal bdMonto) {
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(','); 
		
		return  new DecimalFormat("#,##0.00",otherSymbols).format(bdMonto); 
	}
	
	public static Sucursal obtenerSucursalDeLista(Integer intIdSucursal, List<Sucursal> listaSucursal) throws Exception{
		for(Sucursal sucursal : listaSucursal){
			if(sucursal.getId().getIntIdSucursal().equals(intIdSucursal))
				return sucursal;
		}
		return null;
	}
	
	public static Subsucursal obtenerSubsucursalDeLista(Integer intIdSubsucursal, List<Subsucursal> listaSubsucursal) throws Exception{
		for(Subsucursal subsucursal : listaSubsucursal){
			if(subsucursal.getId().getIntIdSubSucursal().equals(intIdSubsucursal))
				return subsucursal;
		}
		return null;
	}
	
	public static Area obtenerAreaDeLista(Integer intIdArea, List<Area> listaArea) throws Exception{
		for(Area area : listaArea){
			if(area.getId().getIntIdArea().equals(intIdArea))
				return area;
		}
		return null;
	}
	
	public static String quitarPrimerCaracter(String s)throws Exception{
		return s.substring(1);
	}
	
	public static boolean valorValido(BigDecimal bdMonto)throws Exception{
		if(bdMonto!=null && bdMonto.signum()>=0){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	/* Inicio: REQ14-009 Bizarq - 17/12/2014 */
	public static Date getFirstDayOfMonth(Integer intMonth, Integer intYear){
		Calendar cal =Calendar.getInstance();
	    cal.set(Calendar.MONTH,intMonth-1);
	    cal.set(Calendar.YEAR,intYear);
	    cal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH));
	    return cal.getTime();
	}
	
	public static Date getLastDayOfMonth(Integer intMonth, Integer intYear){
		Calendar calendar = Calendar.getInstance();
	    calendar.set(intYear, intMonth-1, 1);
	    calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
	    return calendar.getTime();
	}
	/* Fin: REQ14-009 Bizarq - 17/12/2014 */
}