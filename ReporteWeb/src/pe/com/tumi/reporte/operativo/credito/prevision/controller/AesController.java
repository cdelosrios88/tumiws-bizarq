package pe.com.tumi.reporte.operativo.credito.prevision.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.PermisoUtil;
import pe.com.tumi.common.util.UtilManagerReport;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.Prevision;
import pe.com.tumi.reporte.operativo.credito.asociativo.facade.PrevisionFacadeLocal;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class AesController {
	protected 	static Logger 	log;
	
	PersonaFacadeRemote 	personaFacade;
	EmpresaFacadeRemote		empresaFacade;
	TablaFacadeRemote		tablaFacade;
	
	private	Integer	EMPRESA_USUARIO;
	//private	Integer	PERSONA_USUARIO;
	private Usuario usuario;
	private boolean poseePermiso;
	private List<Sucursal> 		listaSucursal;
	private List<Subsucursal> 	listaSubSucursal;
	private List<Tabla>			listaEstadoSolicitud;
	
	//Variables para los filtros de búsqueda
	private Integer intIdSucursal;
	private Integer intIdSubSucursal;
	private Integer intIdTipoFiltro;
	private String 	strTipoFiltro;
	private Integer intTipoTitular;
	private Integer intEstadoSolicitud;
	private Integer intEstadoPago;
	private Date dtFechaSolicitudDesde;
	private Date dtFechaSolicitudHasta;
	//Fin Variables filtros
	
	PrevisionFacadeLocal previsionFacade;
	private List<Prevision> listaAes;
	
	public AesController(){
		log = Logger.getLogger(this.getClass());
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_REPORTE_AES);
		log.info("Permiso: " + poseePermiso);
		//poseePermiso = Boolean.TRUE;
		if(usuario!=null && poseePermiso){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}
	}
	
	private void cargarUsuario(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");		
		//PERSONA_USUARIO = usuario.getIntPersPersonaPk();
		EMPRESA_USUARIO = usuario.getPerfil().getId().getIntPersEmpresaPk();
	}
	
	public void cargarValoresIniciales(){
		try{
			personaFacade =  (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			empresaFacade =  (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			tablaFacade =  (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			previsionFacade = (PrevisionFacadeLocal) EJBFactory.getLocal(PrevisionFacadeLocal.class);
			
			listaAes = new ArrayList<Prevision>();
			
			cargarListaSucursal();
			//cargarListaTipoTitular();
			cargarListaEstadoSolicitud();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarListaSucursal() throws Exception{
		listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(EMPRESA_USUARIO);
		for(Sucursal sucursal : listaSucursal){
			sucursal.setListaArea(empresaFacade.getListaAreaPorSucursal(sucursal));
		}
		//Ordenamos por nombre
		Collections.sort(listaSucursal, new Comparator<Sucursal>(){
			public int compare(Sucursal uno, Sucursal otro) {
				return uno.getJuridica().getStrSiglas().compareTo(otro.getJuridica().getStrSiglas());
			}
		});
	}
	
	/*private void cargarListaTipoTitular(){
		List<Tabla> listaTipoAes= null;
		try {
			listaTipoAes = tablaFacade.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_AES), "A");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		this.listaTipoAes = listaTipoAes;
	}*/
	
	private void cargarListaEstadoSolicitud(){
		List<Tabla> listaEstadoSolicitud = null;
		try {
			listaEstadoSolicitud = tablaFacade.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_ESTADOSOLICPRESTAMO), "C");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		this.listaEstadoSolicitud = listaEstadoSolicitud;
	}
	
	public void getListSubSucursal() {
		EmpresaFacadeRemote facade = null;
		Integer intIdSucursal = null;
		List<Subsucursal> listaSubsucursal = null;
		try {
			intIdSucursal = Integer.valueOf(getRequestParameter("pCboSucursal"));
			
			if(intIdSucursal!=0){
				facade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				listaSubsucursal = facade.getListaSubSucursalPorIdSucursal(intIdSucursal);
				log.info("listaSubsucursal.size: "+listaSubsucursal.size());
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		setListaSubSucursal(listaSubsucursal);
	}
	
	public void buscar(){
		log.info("entró método buscar");
		Integer intParaEstadoPendiente = 1;
		List<Prevision> listaPrevisionTmp = new ArrayList<Prevision>();
		intIdSucursal = (intIdSucursal==0?null:intIdSucursal);
		intIdSubSucursal = (intIdSubSucursal==0?null:intIdSubSucursal);
		intIdTipoFiltro = (intIdTipoFiltro==0?null:intIdTipoFiltro);
		intTipoTitular = (intTipoTitular==0?null:intTipoTitular);
		intEstadoSolicitud = (intEstadoSolicitud==0?null:intEstadoSolicitud);
		intEstadoPago = (intEstadoPago==0?null:intEstadoPago);
		
		if(intIdTipoFiltro==null){
			strTipoFiltro = null;
		}
		
		try{
			listaAes = previsionFacade.getListaAes(
					Constante.PARAM_T_DOCUMENTOGENERAL_AES,
					intIdSucursal, 
					intIdSubSucursal, 
					intIdTipoFiltro, 
					strTipoFiltro, 
					intTipoTitular, 
					intEstadoSolicitud, 
					intEstadoPago, 
					dtFechaSolicitudDesde,
					dtFechaSolicitudHasta);
			
			if(intEstadoSolicitud!=null && 
					(intEstadoSolicitud.equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)
						|| intEstadoSolicitud.equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD))){
				if(listaAes!=null && !listaAes.isEmpty()){
					for (Prevision aes : listaAes) {
						if(aes.getStrEvaluacionEstado()==null){
							listaPrevisionTmp.add(aes);
						}
					}
					listaAes = listaPrevisionTmp;
				}
			}
			
			if(intEstadoPago!=null && 
					intEstadoPago.equals(intParaEstadoPendiente)){
				if(listaAes!=null && !listaAes.isEmpty()){
					for (Prevision aes : listaAes) {
						if(aes.getStrEgresoFecha()==null){
							listaPrevisionTmp.add(aes);
						}
					}
					listaAes = listaPrevisionTmp;
				}
			}
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void imprimirAes(){
		String strNombreReporte = "";
		HashMap<String,Object> parametro = new HashMap<String,Object>();
		Sucursal sucursal = null;
		Subsucursal subSucursal = null;
		Tabla tipoVinculo = null;
		Tabla estadoSolicitud = null;
		//Tabla estadoPago = null;
		try {
			sucursal = empresaFacade.getSucursalPorId(intIdSucursal);
			subSucursal = empresaFacade.getSubSucursalPorIdSubSucursal(intIdSubSucursal);
			tipoVinculo = tablaFacade.getTablaPorIdMaestroYIdDetalle(new Integer(Constante.PARAM_T_TIPOCONCEPTO_AES), intTipoTitular);
			estadoSolicitud = tablaFacade.getTablaPorIdMaestroYIdDetalle(new Integer(Constante.PARAM_T_ESTADOSOLICPRESTAMO), intEstadoSolicitud);
			//estadoPago = tablaFacade.getTablaPorIdMaestroYIdDetalle(new Integer(Constante.PARAM_T_ESTADO_PAGO), intEstadoPago);
			
			parametro.put("P_SUCURSAL", (sucursal!=null?sucursal.getJuridica().getStrRazonSocial():""));
			parametro.put("P_SUBSUCURSAL", subSucursal!=null?subSucursal.getStrDescripcion():"");
			parametro.put("P_TIPOVINCULO", tipoVinculo!=null?tipoVinculo.getStrDescripcion():"");
			parametro.put("P_ESTADOSOLICITUD", estadoSolicitud!=null?estadoSolicitud.getStrDescripcion():"");
			parametro.put("P_ESTADOPAGO", intEstadoPago);
			parametro.put("P_FECSOLICINI", dtFechaSolicitudDesde);
			parametro.put("P_FECSOLICFIN", dtFechaSolicitudHasta);
			
			strNombreReporte = "prevision_aes";
			UtilManagerReport.generateReport(strNombreReporte, parametro, 
					new ArrayList<Object>(listaAes), Constante.PARAM_T_TIPOREPORTE_PDF);
		} catch (Exception e) {
			log.error("Error en imprimirReporteCaptaciones ---> "+e);
		}
	}
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}
	
	public String getLimpiarAes(){
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_REPORTE_AES);
		log.info("POSEE PERMISO" + poseePermiso);
		//poseePermiso = Boolean.TRUE;
		if(usuario!=null && poseePermiso){
			cargarValoresIniciales();
			//deshabilitarPanelInferior();
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}
		
		return "";
	}

	public Integer getIntIdSucursal() {
		return intIdSucursal;
	}

	public void setIntIdSucursal(Integer intIdSucursal) {
		this.intIdSucursal = intIdSucursal;
	}

	public Integer getIntIdSubSucursal() {
		return intIdSubSucursal;
	}

	public void setIntIdSubSucursal(Integer intIdSubSucursal) {
		this.intIdSubSucursal = intIdSubSucursal;
	}

	public Integer getIntIdTipoFiltro() {
		return intIdTipoFiltro;
	}

	public void setIntIdTipoFiltro(Integer intIdTipoFiltro) {
		this.intIdTipoFiltro = intIdTipoFiltro;
	}

	public String getStrTipoFiltro() {
		return strTipoFiltro;
	}

	public void setStrTipoFiltro(String strTipoFiltro) {
		this.strTipoFiltro = strTipoFiltro;
	}

	public Integer getIntTipoTitular() {
		return intTipoTitular;
	}

	public void setIntTipoTitular(Integer intTipoTitular) {
		this.intTipoTitular = intTipoTitular;
	}

	public Integer getIntEstadoSolicitud() {
		return intEstadoSolicitud;
	}

	public void setIntEstadoSolicitud(Integer intEstadoSolicitud) {
		this.intEstadoSolicitud = intEstadoSolicitud;
	}

	public Integer getIntEstadoPago() {
		return intEstadoPago;
	}

	public void setIntEstadoPago(Integer intEstadoPago) {
		this.intEstadoPago = intEstadoPago;
	}

	public Date getDtFechaSolicitudDesde() {
		return dtFechaSolicitudDesde;
	}

	public void setDtFechaSolicitudDesde(Date dtFechaSolicitudDesde) {
		this.dtFechaSolicitudDesde = dtFechaSolicitudDesde;
	}

	public Date getDtFechaSolicitudHasta() {
		return dtFechaSolicitudHasta;
	}

	public void setDtFechaSolicitudHasta(Date dtFechaSolicitudHasta) {
		this.dtFechaSolicitudHasta = dtFechaSolicitudHasta;
	}

	public List<Sucursal> getListaSucursal() {
		return listaSucursal;
	}

	public void setListaSucursal(List<Sucursal> listaSucursal) {
		this.listaSucursal = listaSucursal;
	}

	public List<Subsucursal> getListaSubSucursal() {
		return listaSubSucursal;
	}

	public void setListaSubSucursal(List<Subsucursal> listaSubSucursal) {
		this.listaSubSucursal = listaSubSucursal;
	}

	public boolean isPoseePermiso() {
		return poseePermiso;
	}

	public void setPoseePermiso(boolean poseePermiso) {
		this.poseePermiso = poseePermiso;
	}

	public List<Tabla> getListaEstadoSolicitud() {
		return listaEstadoSolicitud;
	}

	public void setListaEstadoSolicitud(List<Tabla> listaEstadoSolicitud) {
		this.listaEstadoSolicitud = listaEstadoSolicitud;
	}

	public List<Prevision> getListaAes() {
		return listaAes;
	}

	public void setListaAes(List<Prevision> listaAes) {
		this.listaAes = listaAes;
	}
}
