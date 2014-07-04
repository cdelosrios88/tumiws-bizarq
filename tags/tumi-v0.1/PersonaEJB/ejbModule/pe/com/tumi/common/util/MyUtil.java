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
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaRol;

public class MyUtil {
	protected static Logger log = Logger.getLogger(MyUtil.class);
	
	public static final boolean equalsWithNulls(Object a, Object b){
		if (a==b) return true;
		if ((a==null)||(b==null)) return false;
		return a.equals(b);
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
	
	public static Integer obtenerDiasEntreFechas(Date dtFechaInicio, Date dtFechaFin)throws Exception{
		return (int)( (dtFechaFin.getTime() - dtFechaInicio.getTime()) / (1000 * 60 * 60 * 24) );
	}
	

	
	public static List<Tabla> cargarListaAnios(List<Tabla> listaAño)throws Exception{
		int cantidadAñosLista = 5; 
		listaAño = new ArrayList<Tabla>();
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
		for(Tabla tabla : listaTabla){
			if(tabla.getIntIdDetalle().equals(intIdDetalle)){
				return tabla.getStrDescripcion();
			}
		}
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
		for(Documento documento : persona.getListaDocumento()){
			if(documento.getIntTipoIdentidadCod().equals(Constante.PARAM_T_INT_TIPODOCUMENTO_DNI)){
				persona.setDocumento(documento);
			}
		}
	}
	
	public static String obtenerStrRoles(Persona persona)throws Exception{
		String strRoles = "";
		if(persona.getPersonaEmpresa()!=null && persona.getPersonaEmpresa().getListaPersonaRol()!=null){			
			List<PersonaRol> listPersonaRol =  persona.getPersonaEmpresa().getListaPersonaRol();
			for(PersonaRol personaRol : listPersonaRol)
				strRoles = strRoles+personaRol.getTabla().getStrDescripcion()+",";
			if(strRoles!=null && !strRoles.isEmpty())
				strRoles=strRoles.substring(0,strRoles.length()-1);				
		}
		persona.setStrRoles(strRoles);
		return strRoles;
	}
}
