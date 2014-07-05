package pe.com.tumi.credito.socio.captacion.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.captacion.domain.Afecto;
import pe.com.tumi.credito.socio.captacion.domain.AfectoId;
import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.captacion.domain.Condicion;
import pe.com.tumi.credito.socio.captacion.domain.CondicionComp;
import pe.com.tumi.credito.socio.captacion.domain.CondicionId;
import pe.com.tumi.credito.socio.captacion.facade.CaptacionFacadeLocal;
import pe.com.tumi.credito.socio.convenio.domain.composite.CaptacionComp;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;

/************************************************************************/
/* Nombre de la clase: CreditoController */
/* Funcionalidad : Clase que que tiene los parametros de busqueda */
/* y validaciones de Aportaciones */
/* Ref. : */
/* Autor : CDLRF */
/* Versión : V1 */
/* Fecha creación : 22/03/2012 */
/* ********************************************************************* */

public class MantenimientoCuentaController {
	protected  static Logger 	log 			= Logger.getLogger(MantenimientoCuentaController.class);
	private int 				rows = 5;
	private Captacion			beanCaptacion = new Captacion();
	private Condicion			beanCondicion = new Condicion();
	//private BeanSesion 		beanSesion = new BeanSesion();
	//Variables generales
	private Boolean 			formMantCuentaRendered = false;
	private Integer				intIdEstadoAportacion;
	private Integer				intIdTipoCuenta;
	private String				strNombreMantenimiento;
	private Boolean				chkNombreMant;
	private Integer				intIdCondicionMantenimiento;
	private Integer				intIdTipoConfig;
	private Integer				intIdCondicionLaboral;
	private Date				daFecIni;
	private Date				daFecFin;
	private Integer				intIdTipoPersona;
	private Boolean				chkMantVigentes;
	private Boolean				blnVigencia = true;
	private Integer				rbVigente;
	private String				rbCondicion;
	private String[]			lstCtasConsideradas;
	private String				strValAporte;
	private Boolean				enabDisabValImporte = true;
	private Boolean				enabDisabValPorcentaje = true;
	private Integer				intTipoFecha;
	
	//Variables de activación y desactivación de controles
	private Boolean				enabDisabNombAporte = false;
	private Boolean				chkFechas;
	private Boolean				enabDisabFechasAport = true;
	private String				rbFecIndeterm;
	private Boolean				fecFinAportacionRendered = true;
	private Date				daFechaIni;
	private Date				daFechaFin;
	
	private Boolean				chkProvision;
	private Boolean				enabDisabProvision = true;
	private Boolean				chkExtProvision;
	private Boolean				enabDisabExtProvision = true;
	private Boolean				chkCancelacion;
	private Boolean				enabDisabCancelacion = true;
	
	//Mensajes de Error
	private String				msgTxtDescripcion;
	private String				msgTxtFechaIni;
	private String				msgTxtEstadoAporte;
	private String				msgTxtTipoPersona;
	private String				msgTxtCondicion;
	private String				msgTxtTipoDscto;
	private String				msgTxtTipoConfig;
	private String				msgTxtTipoCondLaboral;
	private String				msgTxtMoneda;
	private String				msgTxtAplicacion;
	
	private String 				strAportacion;
	//
	private List<CaptacionComp>	listaCaptacionComp;
	private List<CondicionComp>	listaCondicionComp;
	
	//Getters y Setters
	public Captacion getBeanCaptacion() {
		return beanCaptacion;
	}
	public void setBeanCaptacion(Captacion beanCaptacion) {
		this.beanCaptacion = beanCaptacion;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public Condicion getBeanCondicion() {
		return beanCondicion;
	}
	public void setBeanCondicion(Condicion beanCondicion) {
		this.beanCondicion = beanCondicion;
	}
	public Boolean getFormMantCuentaRendered() {
		return formMantCuentaRendered;
	}
	public void setFormMantCuentaRendered(Boolean formMantCuentaRendered) {
		this.formMantCuentaRendered = formMantCuentaRendered;
	}
	public Integer getIntIdEstadoAportacion() {
		return intIdEstadoAportacion;
	}
	public void setIntIdEstadoAportacion(Integer intIdEstadoAportacion) {
		this.intIdEstadoAportacion = intIdEstadoAportacion;
	}
	public Integer getIntIdTipoCuenta() {
		return intIdTipoCuenta;
	}
	public void setIntIdTipoCuenta(Integer intIdTipoCuenta) {
		this.intIdTipoCuenta = intIdTipoCuenta;
	}
	public String getStrNombreMantenimiento() {
		return strNombreMantenimiento;
	}
	public void setStrNombreMantenimiento(String strNombreMantenimiento) {
		this.strNombreMantenimiento = strNombreMantenimiento;
	}
	public Boolean getChkNombreMant() {
		return chkNombreMant;
	}
	public void setChkNombreMant(Boolean chkNombreMant) {
		this.chkNombreMant = chkNombreMant;
	}
	public Integer getIntIdCondicionMantenimiento() {
		return intIdCondicionMantenimiento;
	}
	public void setIntIdCondicionMantenimiento(Integer intIdCondicionMantenimiento) {
		this.intIdCondicionMantenimiento = intIdCondicionMantenimiento;
	}
	public Integer getIntIdTipoConfig() {
		return intIdTipoConfig;
	}
	public void setIntIdTipoConfig(Integer intIdTipoConfig) {
		this.intIdTipoConfig = intIdTipoConfig;
	}
	public Integer getIntIdCondicionLaboral() {
		return intIdCondicionLaboral;
	}
	public void setIntIdCondicionLaboral(Integer intIdCondicionLaboral) {
		this.intIdCondicionLaboral = intIdCondicionLaboral;
	}
	public Integer getIntIdTipoPersona() {
		return intIdTipoPersona;
	}
	public void setIntIdTipoPersona(Integer intIdTipoPersona) {
		this.intIdTipoPersona = intIdTipoPersona;
	}
	public Boolean getChkMantVigentes() {
		return chkMantVigentes;
	}
	public void setChkMantVigentes(Boolean chkMantVigentes) {
		this.chkMantVigentes = chkMantVigentes;
	}
	public Boolean getBlnVigencia() {
		return blnVigencia;
	}
	public void setBlnVigencia(Boolean blnVigencia) {
		this.blnVigencia = blnVigencia;
	}
	public Integer getRbVigente() {
		return rbVigente;
	}
	public void setRbVigente(Integer rbVigente) {
		this.rbVigente = rbVigente;
	}
	public String getRbCondicion() {
		return rbCondicion;
	}
	public void setRbCondicion(String rbCondicion) {
		this.rbCondicion = rbCondicion;
	}
	public String[] getLstCtasConsideradas() {
		return lstCtasConsideradas;
	}
	public void setLstCtasConsideradas(String[] lstCtasConsideradas) {
		this.lstCtasConsideradas = lstCtasConsideradas;
	}
	public String getStrValAporte() {
		return strValAporte;
	}
	public void setStrValAporte(String strValAporte) {
		this.strValAporte = strValAporte;
	}
	public Boolean getEnabDisabValImporte() {
		return enabDisabValImporte;
	}
	public void setEnabDisabValImporte(Boolean enabDisabValImporte) {
		this.enabDisabValImporte = enabDisabValImporte;
	}
	public Boolean getEnabDisabValPorcentaje() {
		return enabDisabValPorcentaje;
	}
	public void setEnabDisabValPorcentaje(Boolean enabDisabValPorcentaje) {
		this.enabDisabValPorcentaje = enabDisabValPorcentaje;
	}
	public Integer getIntTipoFecha() {
		return intTipoFecha;
	}
	public void setIntTipoFecha(Integer intTipoFecha) {
		this.intTipoFecha = intTipoFecha;
	}
	public Boolean getEnabDisabNombAporte() {
		return enabDisabNombAporte;
	}
	public void setEnabDisabNombAporte(Boolean enabDisabNombAporte) {
		this.enabDisabNombAporte = enabDisabNombAporte;
	}
	public Boolean getChkFechas() {
		return chkFechas;
	}
	public void setChkFechas(Boolean chkFechas) {
		this.chkFechas = chkFechas;
	}
	public Boolean getEnabDisabFechasAport() {
		return enabDisabFechasAport;
	}
	public void setEnabDisabFechasAport(Boolean enabDisabFechasAport) {
		this.enabDisabFechasAport = enabDisabFechasAport;
	}
	public Date getDaFecIni() {
		return daFecIni;
	}
	public void setDaFecIni(Date daFecIni) {
		this.daFecIni = daFecIni;
	}
	public Date getDaFecFin() {
		return daFecFin;
	}
	public void setDaFecFin(Date daFecFin) {
		this.daFecFin = daFecFin;
	}
	public String getRbFecIndeterm() {
		return rbFecIndeterm;
	}
	public void setRbFecIndeterm(String rbFecIndeterm) {
		this.rbFecIndeterm = rbFecIndeterm;
	}
	public Boolean getFecFinAportacionRendered() {
		return fecFinAportacionRendered;
	}
	public void setFecFinAportacionRendered(Boolean fecFinAportacionRendered) {
		this.fecFinAportacionRendered = fecFinAportacionRendered;
	}
	public Date getDaFechaIni() {
		return daFechaIni;
	}
	public void setDaFechaIni(Date daFechaIni) {
		this.daFechaIni = daFechaIni;
	}
	public Date getDaFechaFin() {
		return daFechaFin;
	}
	public void setDaFechaFin(Date daFechaFin) {
		this.daFechaFin = daFechaFin;
	}
	public Boolean getChkProvision() {
		return chkProvision;
	}
	public void setChkProvision(Boolean chkProvision) {
		this.chkProvision = chkProvision;
	}
	public Boolean getEnabDisabProvision() {
		return enabDisabProvision;
	}
	public void setEnabDisabProvision(Boolean enabDisabProvision) {
		this.enabDisabProvision = enabDisabProvision;
	}
	public Boolean getChkExtProvision() {
		return chkExtProvision;
	}
	public void setChkExtProvision(Boolean chkExtProvision) {
		this.chkExtProvision = chkExtProvision;
	}
	public Boolean getEnabDisabExtProvision() {
		return enabDisabExtProvision;
	}
	public void setEnabDisabExtProvision(Boolean enabDisabExtProvision) {
		this.enabDisabExtProvision = enabDisabExtProvision;
	}
	public Boolean getChkCancelacion() {
		return chkCancelacion;
	}
	public void setChkCancelacion(Boolean chkCancelacion) {
		this.chkCancelacion = chkCancelacion;
	}
	public Boolean getEnabDisabCancelacion() {
		return enabDisabCancelacion;
	}
	public void setEnabDisabCancelacion(Boolean enabDisabCancelacion) {
		this.enabDisabCancelacion = enabDisabCancelacion;
	}
	public String getMsgTxtDescripcion() {
		return msgTxtDescripcion;
	}
	public void setMsgTxtDescripcion(String msgTxtDescripcion) {
		this.msgTxtDescripcion = msgTxtDescripcion;
	}
	public String getMsgTxtFechaIni() {
		return msgTxtFechaIni;
	}
	public void setMsgTxtFechaIni(String msgTxtFechaIni) {
		this.msgTxtFechaIni = msgTxtFechaIni;
	}
	public String getMsgTxtEstadoAporte() {
		return msgTxtEstadoAporte;
	}
	public void setMsgTxtEstadoAporte(String msgTxtEstadoAporte) {
		this.msgTxtEstadoAporte = msgTxtEstadoAporte;
	}
	public String getMsgTxtTipoPersona() {
		return msgTxtTipoPersona;
	}
	public void setMsgTxtTipoPersona(String msgTxtTipoPersona) {
		this.msgTxtTipoPersona = msgTxtTipoPersona;
	}
	public String getMsgTxtCondicion() {
		return msgTxtCondicion;
	}
	public void setMsgTxtCondicion(String msgTxtCondicion) {
		this.msgTxtCondicion = msgTxtCondicion;
	}
	public String getMsgTxtTipoDscto() {
		return msgTxtTipoDscto;
	}
	public void setMsgTxtTipoDscto(String msgTxtTipoDscto) {
		this.msgTxtTipoDscto = msgTxtTipoDscto;
	}
	public String getMsgTxtTipoConfig() {
		return msgTxtTipoConfig;
	}
	public void setMsgTxtTipoConfig(String msgTxtTipoConfig) {
		this.msgTxtTipoConfig = msgTxtTipoConfig;
	}
	public String getMsgTxtTipoCondLaboral() {
		return msgTxtTipoCondLaboral;
	}
	public void setMsgTxtTipoCondLaboral(String msgTxtTipoCondLaboral) {
		this.msgTxtTipoCondLaboral = msgTxtTipoCondLaboral;
	}
	public String getMsgTxtMoneda() {
		return msgTxtMoneda;
	}
	public void setMsgTxtMoneda(String msgTxtMoneda) {
		this.msgTxtMoneda = msgTxtMoneda;
	}
	public String getMsgTxtAplicacion() {
		return msgTxtAplicacion;
	}
	public void setMsgTxtAplicacion(String msgTxtAplicacion) {
		this.msgTxtAplicacion = msgTxtAplicacion;
	}
	public String getStrAportacion() {
		return strAportacion;
	}
	public void setStrAportacion(String strAportacion) {
		this.strAportacion = strAportacion;
	}
	public List<CaptacionComp> getListaCaptacionComp() {
		return listaCaptacionComp;
	}
	public void setListaCaptacionComp(List<CaptacionComp> listaCaptacionComp) {
		this.listaCaptacionComp = listaCaptacionComp;
	}
	public List<CondicionComp> getListaCondicionComp() {
		return listaCondicionComp;
	}
	public void setListaCondicionComp(List<CondicionComp> listaCondicionComp) {
		this.listaCondicionComp = listaCondicionComp;
	}
	// Métodos a implementar
	/**************************************************************/
	/*  Nombre :  habilitarGrabarAportaciones()     		      		*/
	/*                                                    	 		*/
	/*  Parametros. :  Ninguno					           	 		*/
	/*  Objetivo: Habilitar el Formulario para el llenado del mimso */
	/*  Retorno : El formulario habilitado para su respectivo llenado */
	/**************************************************************/
	public void habilitarGrabarAportaciones(ActionEvent event) {
		setFormMantCuentaRendered(true);
		limpiarMantCuenta();
		strAportacion = Constante.MANTENIMIENTO_GRABAR;
	}
	
	/**************************************************************/
	/*  Nombre :  limpiarAportaciones()     			      	*/
	/*                                                    	 	*/
	/*  Parametros. :  Ninguno					           	 	*/
	/*  Objetivo: Limpiar el Formulario de Aportaciones			*/
	/*  Retorno : El formulario de Aportaciones vacío 			*/
	/**************************************************************/
	public void limpiarMantCuenta(){
		Captacion aport = new Captacion();
		aport.setId(new CaptacionId());
		setBeanCaptacion(aport);
		setMsgTxtDescripcion("");
		setMsgTxtFechaIni("");
		setMsgTxtEstadoAporte("");
		setMsgTxtTipoPersona("");
		setMsgTxtCondicion("");
		setMsgTxtTipoDscto("");
		setMsgTxtTipoConfig("");
		setMsgTxtMoneda("");
		setMsgTxtAplicacion("");
		if(listaCondicionComp!=null){
			listaCondicionComp.clear();
		}
		if(lstCtasConsideradas!=null){
			setLstCtasConsideradas(null);
		}
		setEnabDisabValImporte(true);
		setEnabDisabValPorcentaje(true);
		setRbFecIndeterm("");
		setDaFechaIni(null);
		setDaFechaFin(null);
		setRbCondicion("");
		setStrValAporte("");
		setFecFinAportacionRendered(true);
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  cancelarGrabarMantCuenta() 		          		*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*                                                    	 		*/
	/*  Objetivo: Cancelar la nueva Hoja de Planeamiento     		*/
	/*            Poblacional.                               	  	*/
	/*  Retorno : Se oculta el Formulario de Aportaciones		 	*/
	/**************************************************************/
	public void cancelarGrabarMantCuenta(ActionEvent event) {
		setFormMantCuentaRendered(false);
		limpiarMantCuenta();
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  listarAportaciones()        						*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/
	/*  Objetivo: Listar las Hojas de Planeamiento     				*/
	/*  Retorno : Devuelve el listado de las Hojas de Planeamiento 	*/
	/**************************************************************/
	public void listarMantenimientoCta(ActionEvent event) {
		log.info("-----------------------Debugging CreditoController.listarAportaciones-----------------------------");
		CaptacionFacadeLocal facade = null;
		try {
			facade = (CaptacionFacadeLocal)EJBFactory.getLocal(CaptacionFacadeLocal.class);
			Captacion o = new Captacion();
			o.setId(new CaptacionId());
			o.setCondicion(new Condicion());
			o.getCondicion().setId(new CondicionId());
			o.getId().setIntPersEmpresaPk(Constante.PARAM_EMPRESASESION);
			o.getId().setIntParaTipoCaptacionCod(Constante.CAPTACION_MANT_CUENTA);
			if(intTipoFecha!=null && intTipoFecha!=0)
				o.setIntTipoFecha(intTipoFecha);
			if(intIdEstadoAportacion!=null && intIdEstadoAportacion!=0)
			o.setIntParaEstadoSolicitudCod(intIdEstadoAportacion);
			if(intIdTipoCuenta!=null && intIdTipoCuenta!=0)
				o.setIntTipoCuenta(intIdTipoCuenta);
			o.setStrDescripcion(strNombreMantenimiento);
			if(intIdCondicionMantenimiento!=null && intIdCondicionMantenimiento!=0)
			o.getCondicion().getId().setIntParaCondicionSocioCod(intIdCondicionMantenimiento);
			if(intIdTipoConfig!=null && intIdTipoConfig!=0)
			o.setIntParaTipoConfiguracionCod(intIdTipoConfig);
			if(intIdCondicionLaboral!=null && intIdCondicionLaboral!=0)
				o.setIntParaCondicionLaboralCod(intIdCondicionLaboral);
			if(intIdTipoPersona!=null &&intIdTipoPersona!=0)
				o.setIntParaTipopersonaCod(intIdTipoPersona);
			String strFechaIni = (getDaFecIni()!=null)?Constante.sdf.format(getDaFecIni()):null;
			String strFechaFin = (getDaFecFin()!=null)?Constante.sdf.format(getDaFecFin()):null;
			o.setStrDtFechaIni(strFechaIni);
			o.setStrDtFechaFin(strFechaFin);
			o.setDtInicio(daFecIni);
			o.setDtFin(daFecFin);
			o.setIntVigencia(chkMantVigentes==true?1:null);
			o.setIntAportacionVigente(getRbVigente());
			
			listaCaptacionComp = facade.getListaCaptacionCompDeBusquedaCaptacion(o);
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  enableDisableControls()        					*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*  Objetivo: Habilitar y Desabilitar determinados controles    */
	/*            de acuerdo a determinados cambios de estado. 		*/
	/*                         						     	 		*/
	/*  Retorno : Devuelve el listado de las Hojas de Planeamiento 	*/
	/**************************************************************/
	public void enableDisableControls(ActionEvent event) {
		setEnabDisabNombAporte(getChkNombreMant()==true);
		setStrNombreMantenimiento(getChkNombreMant()==true?"":getStrNombreMantenimiento());
		setEnabDisabFechasAport(getChkFechas()!=true);
		setDaFecIni(getChkFechas()!=true?null:getDaFecIni());
		setDaFecFin(getChkFechas()!=true?null:getDaFecFin());
		setBlnVigencia(chkMantVigentes!=true);
		setRbVigente(chkMantVigentes==true?1:null);
		
		setEnabDisabProvision(getChkProvision()!=null && getChkProvision()!=true);
		setEnabDisabExtProvision(getChkExtProvision()!=null && getChkExtProvision()!=true);
		setEnabDisabCancelacion(getChkCancelacion()!=null && getChkCancelacion()!=true);
		
		setEnabDisabValImporte(getStrValAporte()!=null && !getStrValAporte().equals("1"));
		setEnabDisabValPorcentaje(getStrValAporte()!=null && !getStrValAporte().equals("2"));
		
		if(getRbFecIndeterm()!=null){
			setFecFinAportacionRendered(getRbFecIndeterm().equals("1"));
			setDaFechaFin(null);
		}
		
		Captacion aport = new Captacion();
		aport = (Captacion) getBeanCaptacion();
		aport.setBdValorConfiguracion(getStrValAporte()!=null && getStrValAporte().equals("1")?aport.getBdValorConfiguracion():null);
		aport.setBdPorcConfiguracion(getStrValAporte()!=null && getStrValAporte().equals("2")?aport.getBdPorcConfiguracion():null);
		setBeanCaptacion(aport);
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  listarCondicion()        							*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*  Objetivo: Listar las Hojas de Planeamiento     				*/
	/*  Retorno : Devuelve el listado de las Hojas de Planeamiento 	*/
	/**
	 * @throws DaoException ************************************************************/
	public void listarCondicion(ActionEvent event){
		String strIdCodigo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAportacionModalPanel:hiddenIdCodigo");
		TablaFacadeRemote tablaFacade = null;
		List<Tabla> listaTabla = null;
		CondicionComp condicioncomp = null;
		List<CondicionComp> listCondicionComp = new ArrayList<CondicionComp>();
		
		if(getRbCondicion().equals("2")){
			try{
				tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
				listaTabla = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_CONDICIONSOCIO));
				for(int i=0;i<listaTabla.size(); i++){
					condicioncomp = new CondicionComp();
					condicioncomp.setTabla(new Tabla());
					condicioncomp.getTabla().setIntIdDetalle(listaTabla.get(i).getIntIdDetalle());
					condicioncomp.getTabla().setStrDescripcion(listaTabla.get(i).getStrDescripcion());
					listCondicionComp.add(condicioncomp);
				}
				listaCondicionComp = listCondicionComp;
			}catch(Exception e){
				e.printStackTrace();
			}
		}else {
			try{
				tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
				listaTabla = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_CONDICIONSOCIO));
				for(int i=0;i<listaTabla.size(); i++){
					condicioncomp = new CondicionComp();
					condicioncomp.setTabla(new Tabla());
					condicioncomp.getTabla().setIntIdDetalle(listaTabla.get(i).getIntIdDetalle());
					condicioncomp.getTabla().setStrDescripcion(listaTabla.get(i).getStrDescripcion());
					condicioncomp.setChkSocio(true);
					listCondicionComp.add(condicioncomp);
				}
				listaCondicionComp = listCondicionComp;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private Boolean isValidoAportacion(Captacion beanCaptacion){
		Boolean validCaptacion = true;
		if (beanCaptacion.getStrDescripcion().equals("")) {
			setMsgTxtDescripcion("* El campo Nombre del Aporte debe ser ingresado.");
			validCaptacion = false;
		} else {
			setMsgTxtDescripcion("");
		}
		Date daFecIni = getDaFechaIni();
		String daFechaIni = (daFecIni == null ? "" : Constante.sdf.format(daFecIni));
		beanCaptacion.setStrDtFechaIni(daFechaIni);
		Date daFecFin = getDaFechaFin();
		String daFechaFin = (daFecFin == null ? "" : Constante.sdf.format(daFecFin));
		//beanCaptacion.setStrDtFechaFin(daFechaFin);
		if (beanCaptacion.getStrDtFechaIni()==null) {
			setMsgTxtFechaIni("* El campo Fecha de Inicio debe ser ingresado.");
			validCaptacion = false;
		} else {
			setMsgTxtFechaIni("");
		}
		if(daFecIni.equals(daFecFin)){
			setMsgTxtFechaIni("* Las Fechas son iguales.");
			validCaptacion = false;
		}else{
			setMsgTxtFechaIni("");
		}
		if(daFecFin!=null){
			if(daFecIni.after(daFecFin)){
				setMsgTxtFechaIni("* La Fecha de Fin es menor a la Fecha de Inicio.");
				validCaptacion = false;
			}else{
				setMsgTxtFechaIni("");
			}
		}
		if (beanCaptacion.getIntParaTipoDescuentoCod()== 0) {
			setMsgTxtTipoDscto("* El campo Tipo de Descuento debe ser ingresado.");
			validCaptacion = false;
		} else {
			setMsgTxtTipoDscto("");
		}
		if (beanCaptacion.getIntParaTipoConfiguracionCod()== 0) {
			setMsgTxtTipoConfig("* El campo Tipo de Configuración debe ser ingresado.");
			validCaptacion = false;
		} else {
			setMsgTxtTipoConfig("");
		}
		if (beanCaptacion.getIntParaMonedaCod()== 0) {
			setMsgTxtMoneda("* El Tipo de Moneda debe ser ingresado.");
			validCaptacion = false;
		} else {
			setMsgTxtMoneda("");
		}
	    return validCaptacion;
	}
	
	/***********************************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  irModificarMantCta()      						*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/
	/*  Objetivo: Guardar los datos ingresados en la ventan de 		*/
	/*            Aportaciones 						     	 		*/
	/*                         						     	 		*/
	/*  Retorno : Datos grabados correctamente en la tabla de 	 	*/
	/*            CRE_M_CONFCAPTACION				     	 		*/
	/**
	 * @throws DaoException 
	 * @throws ParseException ************************************************************/
	
	public void irModificarMantCta(ActionEvent event) throws ParseException{
    	log.info("-----------------------Debugging MantenimientoCuentaController.modificarAportaciones-----------------------------");
    	CaptacionFacadeLocal captacionFacade = null;
    	String strIdEmpresa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmMantCuentaModalPanel:hiddenEmpresaId");
		String strIdTipoCaptacion = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmMantCuentaModalPanel:hiddenTipoCaptacionId");
		String strIdCodigo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmMantCuentaModalPanel:hiddenCodigoId");
		log.info("strIdEmpresa 		 : "+strIdEmpresa);
		log.info("strIdTipoCaptacion : "+strIdTipoCaptacion);
		log.info("strIdCodigo 		 : "+strIdCodigo);
		
		CaptacionId captacionId = new CaptacionId();
		captacionId.setIntPersEmpresaPk(new Integer(strIdEmpresa));
		captacionId.setIntParaTipoCaptacionCod(new Integer(strIdTipoCaptacion));
		captacionId.setIntItem(new Integer(strIdCodigo));
    	try {
    		if(strIdCodigo != null && !strIdCodigo.trim().equals("")){
    			captacionFacade = (CaptacionFacadeLocal)EJBFactory.getLocal(CaptacionFacadeLocal.class);
				beanCaptacion = captacionFacade.getCaptacionPorIdCaptacion(captacionId);
				
				log.info("beanCaptacion.getIntParaEstadoSolicitudCod(): "+beanCaptacion.getIntParaEstadoSolicitudCod());
				
				String daFecIni = "" + (beanCaptacion.getStrDtFechaIni() == null ? "" : beanCaptacion.getStrDtFechaIni());
				Date fecIni = (daFecIni == null || daFecIni.equals("") ? null : Constante.sdf.parse(daFecIni));
				setDaFechaIni(fecIni);
				
				String daFecFin = "" + (beanCaptacion.getStrDtFechaFin() == null ? "" : beanCaptacion.getStrDtFechaFin());
				Date fecFin = (daFecFin == null || daFecFin.equals("") ? null : Constante.sdf.parse(daFecFin));
				setDaFechaFin(fecFin);
				
				setFecFinAportacionRendered(fecFin!=null);
				setRbFecIndeterm(fecFin!=null?"1":"2");
				
				setStrValAporte(beanCaptacion.getBdValorConfiguracion()!=null?"1":"2");
				setEnabDisabValImporte(beanCaptacion.getBdValorConfiguracion()==null);
				setEnabDisabValPorcentaje(beanCaptacion.getBdPorcConfiguracion()==null);
				
				setRbCondicion(beanCaptacion.getListaCondicion().size()==4?"1":"2");
				listaCondicionComp = beanCaptacion.getListaCondicionComp();
				
				String[] listaAfecto = new String[beanCaptacion.getListaAfecto().size()];
				for(int i=0;i<beanCaptacion.getListaAfecto().size();i++){
					 listaAfecto[i] = ""+ beanCaptacion.getListaAfecto().get(i).getId().getIntTipoCaptacionAfecto();
				}
				lstCtasConsideradas = listaAfecto;
				
				setFormMantCuentaRendered(true);
				//strAportacion = Constante.MANTENIMIENTO_MODIFICAR;
				setStrAportacion((beanCaptacion.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO) || 
						beanCaptacion.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO) )?
						Constante.MANTENIMIENTO_ELIMINAR:Constante.MANTENIMIENTO_MODIFICAR);
				log.info("getStrAportacion(): "+getStrAportacion());
    		}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	/***********************************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  grabarAportaciones()        						*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/
	/*  Objetivo: Guardar los datos ingresados en la ventan de 		*/
	/*            Aportaciones 						     	 		*/
	/*                         						     	 		*/
	/*  Retorno : Datos grabados correctamente en la tabla de 	 	*/
	/*            CRE_M_CONFCAPTACION				     	 		*/
	/**/
	/***********************************************************************************/
	public void grabarMantCuenta(ActionEvent event){
		CaptacionFacadeLocal facade = null;
		List<Afecto> listaAfecto = new ArrayList<Afecto>();
		Afecto afecto = null;
	    if(isValidoAportacion(beanCaptacion) == false){
	    	log.info("Datos de Mantenimiento no válidos. Se aborta el proceso de grabación de Mantenimiento de Cuenta.");
	    	return;
	    }
	    //beanCaptacion.setId(new CaptacionId());
	    beanCaptacion.getId().setIntPersEmpresaPk(Constante.PARAM_EMPRESASESION);//beanSesion.getIntIdEmpresa()
	    beanCaptacion.getId().setIntParaTipoCaptacionCod(Constante.CAPTACION_MANT_CUENTA);
	    beanCaptacion.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_INT);
	    beanCaptacion.setIntParaEstadoSolicitudCod(Constante.PARAM_T_ESTADODOCUMENTO_PENDIENTE);
		
		Date daFecIni = getDaFechaIni();
		String daFechaIni = (daFecIni == null ? "" : Constante.sdf.format(daFecIni));
		beanCaptacion.setStrDtFechaIni(daFechaIni);
		beanCaptacion.setDtInicio(daFecIni);
		
		Date daFecFin = getDaFechaFin();
		String daFechaFin = (daFecFin == null ? "" : Constante.sdf.format(daFecFin));
		beanCaptacion.setStrDtFechaFin(daFechaFin);
		beanCaptacion.setDtFin(daFecFin);
		
		if(listaCondicionComp!=null){
			beanCaptacion.setListaCondicionComp(listaCondicionComp);
		}
		
		if(lstCtasConsideradas!=null && lstCtasConsideradas.length>0){
			for(int i=0;i<lstCtasConsideradas.length;i++){
				afecto = new Afecto();
				afecto.setId(new AfectoId());
				afecto.getId().setIntTipoCaptacionAfecto(new Integer(lstCtasConsideradas[i]));
				listaAfecto.add(afecto);
			}
			beanCaptacion.setListaAfecto(listaAfecto);
		}
	    
    	try {
			facade = (CaptacionFacadeLocal)EJBFactory.getLocal(CaptacionFacadeLocal.class);
			facade.grabarCaptacion(beanCaptacion);
			limpiarMantCuenta();
			listarMantenimientoCta(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	/***********************************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  modificarAportacion()        						*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/
	/*  Objetivo: Guardar los datos modificados de la ventana de	*/
	/*            Aportaciones 						     	 		*/
	/*                         						     	 		*/
	/*  Retorno : Datos grabados correctamente en la tabla de 	 	*/
	/*            CRE_M_CONFCAPTACION				     	 		*/
	/**/
	/***********************************************************************************/
	
	public void modificarMantCuenta(ActionEvent event){
    	CaptacionFacadeLocal facade = null;
    	List<Afecto> listaAfecto = new ArrayList<Afecto>();
		Afecto afecto = null;
    	if(isValidoAportacion(beanCaptacion) == false){
    		log.info("Datos de Captación no válidos. Se aborta el proceso de grabación de Aportación.");
    		return;
    	}
    	
    	Date daFecIni = getDaFechaIni();
		String daFechaIni = (daFecIni == null ? "" : Constante.sdf.format(daFecIni));
		//beanCaptacion.setStrDtFechaIni(daFechaIni);
		beanCaptacion.setDtInicio(daFecIni);
		
		Date daFecFin = getDaFechaFin();
		String daFechaFin = (daFecFin == null ? "" : Constante.sdf.format(daFecFin));
		//beanCaptacion.setStrDtFechaFin(daFechaFin);
		beanCaptacion.setDtFin(daFecFin);
		
		if(listaCondicionComp!=null){
			beanCaptacion.setListaCondicionComp(listaCondicionComp);
		}
		
		if(lstCtasConsideradas!=null && lstCtasConsideradas.length>0){
			for(int i=0;i<lstCtasConsideradas.length;i++){
				afecto = new Afecto();
				afecto.setId(new AfectoId());
				afecto.getId().setIntTipoCaptacionAfecto(new Integer(lstCtasConsideradas[i]));
				listaAfecto.add(afecto);
			}
			beanCaptacion.setListaAfecto(listaAfecto);
		}
    	
    	try {
			facade = (CaptacionFacadeLocal)EJBFactory.getLocal(CaptacionFacadeLocal.class);
			facade.modificarCaptacion(beanCaptacion);
			limpiarMantCuenta();
			listarMantenimientoCta(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
    }
	
	public void eliminarMantenimientoCta(ActionEvent event){
    	log.info("-----------------------Debugging EmpresaController.eliminarZonal-----------------------------");
    	String strIdEmpresa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAportacionModalPanel:hiddenIdEmpresa");
		String strIdTipoCaptacion = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAportacionModalPanel:hiddenIdTipoCaptacion");
		String strIdCodigo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAportacionModalPanel:hiddenIdCodigo");
		log.info("strIdEmpresa 		 : "+strIdEmpresa);
		log.info("strIdTipoCaptacion : "+strIdTipoCaptacion);
		log.info("strIdCodigo 		 : "+strIdCodigo);
		CaptacionFacadeLocal facade = null;
		Captacion captacion = null;
    	try {
    		captacion = new Captacion();
    		captacion.setId(new CaptacionId());
    		captacion.getId().setIntPersEmpresaPk(new Integer(strIdEmpresa));
    		captacion.getId().setIntParaTipoCaptacionCod(new Integer(strIdTipoCaptacion));
    		captacion.getId().setIntItem(new Integer(strIdCodigo));
			facade = (CaptacionFacadeLocal)EJBFactory.getLocal(CaptacionFacadeLocal.class);
			facade.eliminarCaptacion(captacion.getId());
			limpiarMantCuenta();
			listarMantenimientoCta(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
    }
}