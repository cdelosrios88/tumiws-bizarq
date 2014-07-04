package pe.com.tumi.seguridad.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;

import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.controller.GenericReportController;
import pe.com.tumi.common.util.ConstanteReporte;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.empresa.domain.Empresa;
import pe.com.tumi.empresa.domain.PerNatural;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.file.controller.FileUploadBean;
import pe.com.tumi.seguridad.domain.AdminMenu;
import pe.com.tumi.seguridad.domain.AuditoriaSistemas;
import pe.com.tumi.seguridad.domain.Sesiones;
import pe.com.tumi.seguridad.domain.DataObjects;
import pe.com.tumi.seguridad.domain.RegistroPc;
import pe.com.tumi.seguridad.domain.ReglamentoPolitica;
import pe.com.tumi.seguridad.domain.SolicitudCambio;
import pe.com.tumi.seguridad.service.ReglamentoPoliticaService;
import pe.com.tumi.seguridad.service.impl.AdminMenuServiceImpl;
import pe.com.tumi.seguridad.service.impl.AuditoriaSistemasServiceImpl;
import pe.com.tumi.seguridad.service.impl.ReglamentoPoliticaServiceImpl;

public class AuditoriaSistController extends GenericReportController {
	//Form Sesión
	private 	AuditoriaSistemasServiceImpl auditoriaSistService;
	private 	Integer 				intCboEmpSesion;
	private 	Integer 				intCboEstadosSesion;
	private 	Integer 				intCboTiposSucursal;
	private 	Integer 				intCboUsuSesion;
	private 	Boolean 				blnRangoFechas;
	private 	Date 					dtInicioBusqSesion;
	private 	Date 					dtFinBusqSesion;
	private 	List 					beanListSesiones;
	private 	List 					beanListSesionesActivas;
	private 	SimpleDateFormat 		sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private 	Boolean					blnSelectAllSessions = false;
	private 	List 					beanListTablas = new ArrayList();
	private 	List 					beanListColumnas = new ArrayList();
	private    	AdminMenuServiceImpl 	adminMenuService;
	private 	String					strNombreTabla;
	
	//Form Data
	private 	Integer 				intCboOperadorLogico;
	private 	Integer 				intCboTipoArchivo;
	private 	List 					beanListAudiTablas = new ArrayList();
	private 	Boolean 				blnChkFechas;
	private 	Boolean 				blnFiltrarFechas;
	private 	Boolean 				blnValoresNull=true;
	private 	Boolean 				blnValoresCero=true;
	private 	Date 					dtAudiDesde;
	private 	Date 					dtAudiHasta;
	private 	String 					strValorColumna;
	
	public void listarSesiones(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging auditoriaSistController.listarSesiones-----------------------------");
		setService(auditoriaSistService);
		log.info("getIntCboEmpSesion(): "+getIntCboEmpSesion());
		log.info("getIntCboEstadosSesion(): "+getIntCboEstadosSesion());
		log.info("getIntCboTiposSucursal(): "+getIntCboTiposSucursal());
		log.info("getIntCboUsuSesion(): "+getIntCboUsuSesion());
		log.info("getBlnRangoFechas(): "+getBlnRangoFechas());
		log.info("getDtInicioBusqSesion(): "+getDtInicioBusqSesion());
		log.info("getDtFinBusqSesion(): "+getDtFinBusqSesion());
		
		Sesiones audisis = new Sesiones();
		audisis.setIntIdEmpresa(getIntCboEmpSesion().equals(0)?null:getIntCboEmpSesion());
		audisis.setIntEstadoSesion(getIntCboEstadosSesion().equals(0)?null:getIntCboEstadosSesion());
		audisis.setIntTipoSucursal(getIntCboTiposSucursal().equals(0)?null:getIntCboTiposSucursal());
		audisis.setIntIdUsuario((getIntCboUsuSesion()==null || getIntCboUsuSesion().equals(0))?null:getIntCboUsuSesion());
		if(getBlnRangoFechas()==true){
			audisis.setIntRangoFechas(1);
		}else{
			audisis.setIntRangoFechas(0);
		}
		String strFechaIni = (getDtInicioBusqSesion()!=null)?sdf.format(getDtInicioBusqSesion()):null;
		String strFechaFin = (getDtFinBusqSesion()!=null)?sdf.format(getDtFinBusqSesion()):null;
		audisis.setStrFechaRegistro(strFechaIni);
		audisis.setStrFechaFin(strFechaFin);
		
		ArrayList listaSesiones = new ArrayList();
		listaSesiones = getService().listarSesiones(audisis);
		log.info("listaSesiones.size(): "+listaSesiones.size());
		setBeanListSesiones(listaSesiones);
		String ruta_log4j="";
	}
	
	public void listarSesionesActivas(ActionEvent event) throws DaoException {
		log.info("-----------------------Debugging auditoriaSistController.listarSesionesActivas-----------------------------");
		setService(auditoriaSistService);
		
		Sesiones audisis = new Sesiones();
		audisis.setIntIdEmpresa(null);
		audisis.setIntEstadoSesion(1);
		audisis.setIntTipoSucursal(null);
		audisis.setIntIdUsuario(null);
		audisis.setIntRangoFechas(0);
		audisis.setStrFechaRegistro(null);
		audisis.setStrFechaFin(null);
		
		ArrayList listaSesionesActivas = new ArrayList();
		listaSesionesActivas = getService().listarSesiones(audisis);
		log.info("listaSesionesActivas.size(): "+listaSesionesActivas.size());
		setBeanListSesionesActivas(listaSesionesActivas);
		
		setBlnSelectAllSessions(false);
	}
	
	public void eliminarSesionesActivas(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging auditoriaSistController.eliminarSesionesActivas-----------------------------");
		setService(auditoriaSistService);
		
		for(int i=0; i<beanListSesionesActivas.size(); i++){
			Sesiones audisis = new Sesiones();
			audisis = (Sesiones)beanListSesionesActivas.get(i);
			log.info("audisis.getBlnSeleccionado(): "+audisis.getBlnSeleccionado());
			if(audisis.getBlnSeleccionado()==true){
				log.info("audisis.getIntIdEmpresa(): "+audisis.getIntIdEmpresa());
				log.info("audisis.getIntIdUsuario(): "+audisis.getIntIdUsuario());
				log.info("audisis.getStrFechaRegistro(): "+audisis.getStrFechaRegistro());
				getService().eliminarSesiones(audisis);
			}
		}
		listarSesionesActivas(event);
	}
	
	public void selectAllSessions(ValueChangeEvent event) throws DaoException{
		log.info("-----------------------Debugging auditoriaSistController.selectAllSessions-----------------------------");
		
		log.info("getBlnSelectAllSessions(): "+getBlnSelectAllSessions());
		Boolean blnSeleccionar = !getBlnSelectAllSessions();
		
		ArrayList arraySesiones = new ArrayList();
		log.info("beanListSesionesActivas.size(): "+beanListSesionesActivas.size());
		for(int i=0; i<beanListSesionesActivas.size(); i++){
			Sesiones audisis = new Sesiones();
			audisis = (Sesiones)beanListSesionesActivas.get(i);
			
			audisis.setBlnSeleccionado(blnSeleccionar);
			arraySesiones.add(audisis);
		}
		beanListSesionesActivas.clear();
		log.info("arraySesiones.size(): "+arraySesiones.size());
		setBeanListSesionesActivas(arraySesiones);
	}
	
	public void listarTablas(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging auditoriaSistController.listarTablas-----------------------------");
		setService(adminMenuService);
		
		//Listar tablas
		HashMap prmtTablas = new HashMap();
		prmtTablas.put("pStrIdTransaccion", null);
		ArrayList arrayTablas = new ArrayList();
		arrayTablas = getService().listarDataObjects(prmtTablas);
		log.info("arrayTablas.size(): "+arrayTablas.size());
		
		ArrayList listTablas = new ArrayList();
		for(int i=0; i<arrayTablas.size(); i++){
			DataObjects tb = new DataObjects();
			tb = (DataObjects)arrayTablas.get(i);
			
			log.info("DICC_CODIGO_N:"+tb.getIntCodigo());
			log.info("DICC_TIPO_N:"+tb.getIntTipoObjecto());
			log.info("DICC_DESCRIPCION_V: "+tb.getStrNombreObjeto());
			if(tb.getIntTipoObjecto()==1){
				listTablas.add(tb);
			}
		}
		setBeanListTablas(listTablas);
	}
	
	public void listarColumnas(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging auditoriaSistController.listarColumnas-----------------------------");
		setService(adminMenuService);
		
		//Listar columnas
		String strNombreTabla = getRequestParameter("pTableName");
		log.info("strNombreTabla: "+strNombreTabla);
		setStrNombreTabla(strNombreTabla);
		
		HashMap prmtColumnas = new HashMap();
		prmtColumnas.put("pNombreTabla", strNombreTabla);
		ArrayList arrayColumnas = new ArrayList();
		arrayColumnas = getService().listarColumnas(prmtColumnas);
		log.info("arrayColumnas.size(): "+arrayColumnas.size());
		
		ArrayList listColumnas = new ArrayList();
		for(int i=0; i<arrayColumnas.size(); i++){
			DataObjects col = new DataObjects();
			col = (DataObjects)arrayColumnas.get(i);
			
			log.info("COLUMN_NAME: "+col.getStrNombreObjeto());
			listColumnas.add(col);
		}
		setBeanListColumnas(listColumnas);
	}
	
	public void buscarHistoricoData(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging auditoriaSistController.buscarHistoricoData-----------------------------");
		setService(auditoriaSistService);
		String operador = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formAudiSis:hiddenOperLogico");
		log.info("operador: " + operador);
		String strNombreTabla = getStrNombreTabla();
		log.info("getStrNombreTabla(): "+strNombreTabla);
		String strNombreColumna = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formAudiSis:hiddenNombreColumna");
		log.info("strNombreColumna: "+strNombreColumna);
		String strValorColumna = getStrValorColumna();
		log.info("getStrValorColumna(): "+strValorColumna);
		
		log.info("getDtAudiDesde(): "+getDtAudiDesde());
		log.info("getDtAudiHasta(): "+getDtAudiHasta());
		log.info("getBlnValoresNull(): "+getBlnValoresNull());
		log.info("getBlnValoresCero(): "+getBlnValoresCero());
		
		AuditoriaSistemas prmtAudisis = new AuditoriaSistemas();
		ArrayList arrayAuditoriaTablas = new ArrayList();
		prmtAudisis.setStrFechaInicio((getDtAudiDesde()!=null)?sdf.format(getDtAudiDesde()):null);
		prmtAudisis.setStrFechaFin((getDtAudiHasta()!=null)?sdf.format(getDtAudiHasta()):null);
		prmtAudisis.setIntValoresNull(getBlnValoresNull()==true?1:0);
		prmtAudisis.setIntValoresCero(getBlnValoresCero()==true?1:0);
		prmtAudisis.setStrOperadorLogico(operador);
		prmtAudisis.setStrTabla(strNombreTabla);
		prmtAudisis.setStrColumna(strNombreColumna);
		prmtAudisis.setStrValorColumna(strValorColumna);
		arrayAuditoriaTablas = getService().listarAuditoriaTablas(prmtAudisis);
		log.info("arrayAuditoriaTablas.size(): "+arrayAuditoriaTablas.size());
		setBeanListAudiTablas(arrayAuditoriaTablas);
	}
	
	public void exportarHistoricoData(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging auditoriaSistController.exportarHistoricoData-----------------------------");
		
		log.info("getIntCboTipoArchivo(): "+getIntCboTipoArchivo());
		String strTipoArchivo="";
		switch(getIntCboTipoArchivo()){
		case 3: strTipoArchivo="__excel__"; break;
		case 4: strTipoArchivo="__pdf__"; break;
		case 6: strTipoArchivo="__html__"; break;
		}
		if(strTipoArchivo.equals("")){
			setMessageError("La exportación no está disponible en este formato.");
			return;
		}
		
		//ArrayList listAuditoria = new ArrayList();
		String operador = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formAudiSis:hiddenOperLogico");
		log.info("operador: " + operador);
		String strNombreTabla = getStrNombreTabla();
		log.info("getStrNombreTabla(): "+strNombreTabla);
		String strNombreColumna = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formAudiSis:hiddenNombreColumna");
		log.info("strNombreColumna: "+strNombreColumna);
		String strValorColumna = getStrValorColumna();
		log.info("getStrValorColumna(): "+strValorColumna);
		
		log.info("getDtAudiDesde(): "+getDtAudiDesde());
		log.info("getDtAudiHasta(): "+getDtAudiHasta());
		log.info("getBlnValoresNull(): "+getBlnValoresNull());
		log.info("getBlnValoresCero(): "+getBlnValoresCero());
		
		Map parameters = new HashMap();
		//parameters.put("cursorlista", listAuditoria);
		parameters.put("v_fechaini_v", (getDtAudiDesde()!=null)?sdf.format(getDtAudiDesde()):null);
		parameters.put("v_fechafin_v", (getDtAudiHasta()!=null)?sdf.format(getDtAudiHasta()):null);
		parameters.put("v_valoresnull", getBlnValoresNull()==true?1:0);
		parameters.put("v_valorescero", getBlnValoresCero()==true?1:0);
		parameters.put("v_operlogico", operador);
		parameters.put("v_tabla", strNombreTabla);
		parameters.put("v_columna", strNombreColumna);
		parameters.put("v_valorcolumna", strValorColumna);
		
		java.util.Date fecha = new Date();
		DateFormat formatoFecha = new SimpleDateFormat("yyyyMMddHHmmss");
		setExportFileType(strTipoArchivo);
		setReportName("Rpt_Certificados_"+String.valueOf(formatoFecha.format(fecha)));
		setInHardDisk(false);
		
		try {
			log.debug("La ruta del Reporte es  :  "+ ConstanteReporte.RUTA_REPORTES + "data_report01.jasper");
			String jasperFileReporte=getServletContext().getRealPath("") + ConstanteReporte.RUTA_REPORTES + "data_report01.jasper";
			log.info("jasperFileReporte: "+jasperFileReporte);
			setParameters(parameters);
			setJasperFile(jasperFileReporte);
			super.generate(event);
		}catch (Exception ex) {
			log.debug("El errror es  de generacion de reporte  ......... : "+ ex.getMessage());
		}
	}
	
	public void habilitarFiltroFechas(ActionEvent event){
		log.info("-----------------------Debugging auditoriaSistController.habilitarFiltroFechas-----------------------------");
		log.info("getBlnChkFechas(): "+getBlnChkFechas());
		if(getBlnChkFechas()==true){
			setBlnFiltrarFechas(false);
		}else{
			setBlnFiltrarFechas(true);
			setDtAudiDesde(null);
			setDtAudiHasta(null);
		}
	}

	//-----------------------------------------------------------------------------------------
	//Getters y Setters
	//-----------------------------------------------------------------------------------------
	public Integer getIntCboEmpSesion() {
		return intCboEmpSesion;
	}

	public void setIntCboEmpSesion(Integer intCboEmpSesion) {
		this.intCboEmpSesion = intCboEmpSesion;
	}

	public AuditoriaSistemasServiceImpl getAuditoriaSistService() {
		return auditoriaSistService;
	}

	public void setAuditoriaSistService(
			AuditoriaSistemasServiceImpl auditoriaSistService) {
		this.auditoriaSistService = auditoriaSistService;
	}
	
	public Integer getIntCboTiposSucursal() {
		return intCboTiposSucursal;
	}

	public void setIntCboTiposSucursal(Integer intCboTiposSucursal) {
		this.intCboTiposSucursal = intCboTiposSucursal;
	}

	public Integer getIntCboEstadosSesion() {
		return intCboEstadosSesion;
	}

	public void setIntCboEstadosSesion(Integer intCboEstadosSesion) {
		this.intCboEstadosSesion = intCboEstadosSesion;
	}

	public Integer getIntCboUsuSesion() {
		return intCboUsuSesion;
	}

	public void setIntCboUsuSesion(Integer intCboUsuSesion) {
		this.intCboUsuSesion = intCboUsuSesion;
	}

	public Date getDtInicioBusqSesion() {
		return dtInicioBusqSesion;
	}

	public void setDtInicioBusqSesion(Date dtInicioBusqSesion) {
		this.dtInicioBusqSesion = dtInicioBusqSesion;
	}

	public Date getDtFinBusqSesion() {
		return dtFinBusqSesion;
	}

	public void setDtFinBusqSesion(Date dtFinBusqSesion) {
		this.dtFinBusqSesion = dtFinBusqSesion;
	}

	public List getBeanListSesiones() {
		return beanListSesiones;
	}

	public void setBeanListSesiones(List beanListSesiones) {
		this.beanListSesiones = beanListSesiones;
	}

	public Boolean getBlnRangoFechas() {
		return blnRangoFechas;
	}

	public void setBlnRangoFechas(Boolean blnRangoFechas) {
		this.blnRangoFechas = blnRangoFechas;
	}

	public List getBeanListSesionesActivas() throws DaoException {
		return beanListSesionesActivas;
	}

	public void setBeanListSesionesActivas(List beanListSesionesActivas) {
		this.beanListSesionesActivas = beanListSesionesActivas;
	}

	public Boolean getBlnSelectAllSessions() {
		return blnSelectAllSessions;
	}

	public void setBlnSelectAllSessions(Boolean blnSelectAllSessions) {
		this.blnSelectAllSessions = blnSelectAllSessions;
	}

	public List getBeanListTablas() throws DaoException {
		return beanListTablas;
	}

	public void setBeanListTablas(List beanListTablas) {
		this.beanListTablas = beanListTablas;
	}

	public List getBeanListColumnas() {
		return beanListColumnas;
	}

	public void setBeanListColumnas(List beanListColumnas) {
		this.beanListColumnas = beanListColumnas;
	}

	public AdminMenuServiceImpl getAdminMenuService() {
		return adminMenuService;
	}

	public void setAdminMenuService(AdminMenuServiceImpl adminMenuService) {
		this.adminMenuService = adminMenuService;
	}

	public String getStrNombreTabla() {
		return strNombreTabla;
	}

	public void setStrNombreTabla(String strNombreTabla) {
		this.strNombreTabla = strNombreTabla;
	}

	public Integer getIntCboOperadorLogico() {
		return intCboOperadorLogico;
	}

	public void setIntCboOperadorLogico(Integer intCboOperadorLogico) {
		this.intCboOperadorLogico = intCboOperadorLogico;
	}

	public Integer getIntCboTipoArchivo() {
		return intCboTipoArchivo;
	}

	public void setIntCboTipoArchivo(Integer intCboTipoArchivo) {
		this.intCboTipoArchivo = intCboTipoArchivo;
	}

	public List getBeanListAudiTablas() {
		return beanListAudiTablas;
	}

	public void setBeanListAudiTablas(List beanListAudiTablas) {
		this.beanListAudiTablas = beanListAudiTablas;
	}

	public Boolean getBlnFiltrarFechas() {
		return blnFiltrarFechas;
	}

	public void setBlnFiltrarFechas(Boolean blnFiltrarFechas) {
		this.blnFiltrarFechas = blnFiltrarFechas;
	}

	public Boolean getBlnValoresNull() {
		return blnValoresNull;
	}

	public void setBlnValoresNull(Boolean blnValoresNull) {
		this.blnValoresNull = blnValoresNull;
	}

	public Boolean getBlnValoresCero() {
		return blnValoresCero;
	}

	public void setBlnValoresCero(Boolean blnValoresCero) {
		this.blnValoresCero = blnValoresCero;
	}

	public Boolean getBlnChkFechas() {
		return blnChkFechas;
	}

	public void setBlnChkFechas(Boolean blnChkFechas) {
		this.blnChkFechas = blnChkFechas;
	}

	public Date getDtAudiDesde() {
		return dtAudiDesde;
	}

	public void setDtAudiDesde(Date dtAudiDesde) {
		this.dtAudiDesde = dtAudiDesde;
	}

	public Date getDtAudiHasta() {
		return dtAudiHasta;
	}

	public void setDtAudiHasta(Date dtAudiHasta) {
		this.dtAudiHasta = dtAudiHasta;
	}

	public String getStrValorColumna() {
		return strValorColumna;
	}

	public void setStrValorColumna(String strValorColumna) {
		this.strValorColumna = strValorColumna;
	}
	
}
