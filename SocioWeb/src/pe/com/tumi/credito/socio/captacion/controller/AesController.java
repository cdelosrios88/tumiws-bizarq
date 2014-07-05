package pe.com.tumi.credito.socio.captacion.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.captacion.domain.Concepto;
import pe.com.tumi.credito.socio.captacion.domain.Condicion;
import pe.com.tumi.credito.socio.captacion.domain.CondicionComp;
import pe.com.tumi.credito.socio.captacion.domain.CondicionId;
import pe.com.tumi.credito.socio.captacion.domain.Requisito;
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
/* y validaciones de Fondo de Sepelio */
/* Ref. : */
/* Autor : CDLRF */
/* Versión : V1 */
/* Fecha creación : 27/03/2012 */
/* ********************************************************************* */

public class AesController {
	protected  static Logger 	log 			= Logger.getLogger(AesController.class);
	private int 				rows = 5;
	private Captacion			beanCaptacion = new Captacion();
	private Condicion			beanCondicion = new Condicion();
	//private BeanSesion 		beanSesion = new BeanSesion();
	//Variables generales
	private Boolean 			formAesRendered = false;
	private Integer				intIdEstadoAportacion;
	private String				strNombreFondoSepelio;
	private Integer				intIdCondicionAportacion;
	private Integer				intIdTipoConfig;
	private Integer				intIdTipoRelacion;
	private Date				daFecIni;
	private Date				daFecFin;
	private Integer				intIdTipoPersona;
	private String				rbCondicion;
	private Boolean				chkIndeterminado;
	private Integer				intTipoFecha;
	
	//Variables de activación y desactivación de controles
	private Boolean				chkNombFondoSepelio;
	private Boolean				enabDisabNombFondoSepelio = false;
	private Boolean				enabDisabCondAporte = true;
	private Boolean				chkFechas;
	private Boolean				enabDisabFechasAport = true;
	private String				rbFecIndeterm;
	private Boolean				fecFinAportacionRendered = true;
	private Date				daFechaIni;
	private Date				daFechaFin;
	private Boolean				chkLimiteEdad;
	private Boolean				enabDisabLimiteEdad = true;
	private String				rbTasaInteres;
	private Integer				intTipoBeneficiario;
	private Integer				intRangoCuota;
	private Integer				intCuotaCancelada;
	private BigDecimal			bdBeneficio;
	private BigDecimal			bdGastoAdm;
	private Boolean				enabDisabValImporte = true;
	private Boolean				enabDisabValPorcentaje = true;
	private Boolean				chkAportVigentes;
	private Boolean				blnVigencia = true;
	private Integer				rbVigente;
	
	private Boolean				chkSolicitudBeneficio;
	private Boolean				enabDisabSolicitudBenef = true;
	private Boolean				chkAprobacBeneficio;
	private Boolean				enabDisabAprobacBenef = true;
	private Boolean				chkAnulRechazoBeneficio;
	private Boolean				enabDisabAnulRechazoBenef = true;
	private Boolean				chkGiroBeneficio;
	private Boolean				enabDisabGiroBenef = true;
	private Boolean				chkCancelacion;
	private Boolean				enabDisabCancelacion = true;
	private Boolean				enabDisabEstCaptacion = true;
	
	private Boolean				chkCuotaFondoSepelio;
	private Boolean				enabDisabCuotaFondoSepelio = true;
	private Boolean				chkTiempoAportacion;
	private Boolean				enabDisabTiempoAportacion = true;
	private Boolean				chkTiempoPresentSustento;
	private Boolean				enabDisabTiempoPresentSust = true;
	private Boolean				chkEscala;
	
	//Mensajes de Error
	private String				msgTxtDescripcion;
	private String				msgTxtFechaIni;
	private String				msgTxtEstadoAporte;
	private String				msgTxtTipoPersona;
	private String				msgTxtCondicion;
	
	private String 				strAportacion;
	//
	private List<Tabla>			listaTipoRelacion;
	private List<CaptacionComp>	listaCaptacionComp;
	private List<CondicionComp>	listaCondicionComp;
	private List<Requisito>		listaRequisitos = new ArrayList<Requisito>();
	private List<Concepto>		listaConceptos = new ArrayList<Concepto>();
	private List<Tabla>			listaPeriocidadAes;
	
	//Getters y Setters
	
	public Captacion getBeanCaptacion() {
		return beanCaptacion;
	}
	public void setListaPeriocidadAes(List<Tabla> listaPeriocidadAes) {
		this.listaPeriocidadAes = listaPeriocidadAes;
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
	public Boolean getFormAesRendered() {
		return formAesRendered;
	}
	public void setFormAesRendered(Boolean formAesRendered) {
		this.formAesRendered = formAesRendered;
	}
	public Integer getIntIdEstadoAportacion() {
		return intIdEstadoAportacion;
	}
	public void setIntIdEstadoAportacion(Integer intIdEstadoAportacion) {
		this.intIdEstadoAportacion = intIdEstadoAportacion;
	}
	public String getStrNombreFondoSepelio() {
		return strNombreFondoSepelio;
	}
	public void setStrNombreFondoSepelio(String strNombreFondoSepelio) {
		this.strNombreFondoSepelio = strNombreFondoSepelio;
	}
	public Integer getIntIdCondicionAportacion() {
		return intIdCondicionAportacion;
	}
	public void setIntIdCondicionAportacion(Integer intIdCondicionAportacion) {
		this.intIdCondicionAportacion = intIdCondicionAportacion;
	}
	public Integer getIntIdTipoConfig() {
		return intIdTipoConfig;
	}
	public void setIntIdTipoConfig(Integer intIdTipoConfig) {
		this.intIdTipoConfig = intIdTipoConfig;
	}
	public Integer getIntIdTipoRelacion() {
		return intIdTipoRelacion;
	}
	public void setIntIdTipoRelacion(Integer intIdTipoRelacion) {
		this.intIdTipoRelacion = intIdTipoRelacion;
	}
	public Integer getIntIdTipoPersona() {
		return intIdTipoPersona;
	}
	public void setIntIdTipoPersona(Integer intIdTipoPersona) {
		this.intIdTipoPersona = intIdTipoPersona;
	}
	public String getRbCondicion() {
		return rbCondicion;
	}
	public void setRbCondicion(String rbCondicion) {
		this.rbCondicion = rbCondicion;
	}
	public Boolean getChkIndeterminado() {
		return chkIndeterminado;
	}
	public void setChkIndeterminado(Boolean chkIndeterminado) {
		this.chkIndeterminado = chkIndeterminado;
	}
	public Integer getIntTipoFecha() {
		return intTipoFecha;
	}
	public void setIntTipoFecha(Integer intTipoFecha) {
		this.intTipoFecha = intTipoFecha;
	}
	public Boolean getChkNombFondoSepelio() {
		return chkNombFondoSepelio;
	}
	public void setChkNombFondoSepelio(Boolean chkNombFondoSepelio) {
		this.chkNombFondoSepelio = chkNombFondoSepelio;
	}
	public Boolean getEnabDisabNombFondoSepelio() {
		return enabDisabNombFondoSepelio;
	}
	public void setEnabDisabNombFondoSepelio(Boolean enabDisabNombFondoSepelio) {
		this.enabDisabNombFondoSepelio = enabDisabNombFondoSepelio;
	}
	public Boolean getEnabDisabCondAporte() {
		return enabDisabCondAporte;
	}
	public void setEnabDisabCondAporte(Boolean enabDisabCondAporte) {
		this.enabDisabCondAporte = enabDisabCondAporte;
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
	public Boolean getChkLimiteEdad() {
		return chkLimiteEdad;
	}
	public void setChkLimiteEdad(Boolean chkLimiteEdad) {
		this.chkLimiteEdad = chkLimiteEdad;
	}
	public Boolean getEnabDisabLimiteEdad() {
		return enabDisabLimiteEdad;
	}
	public void setEnabDisabLimiteEdad(Boolean enabDisabLimiteEdad) {
		this.enabDisabLimiteEdad = enabDisabLimiteEdad;
	}
	public String getRbTasaInteres() {
		return rbTasaInteres;
	}
	public void setRbTasaInteres(String rbTasaInteres) {
		this.rbTasaInteres = rbTasaInteres;
	}
	public Integer getIntTipoBeneficiario() {
		return intTipoBeneficiario;
	}
	public void setIntTipoBeneficiario(Integer intTipoBeneficiario) {
		this.intTipoBeneficiario = intTipoBeneficiario;
	}
	public Integer getIntRangoCuota() {
		return intRangoCuota;
	}
	public void setIntRangoCuota(Integer intRangoCuota) {
		this.intRangoCuota = intRangoCuota;
	}
	public Integer getIntCuotaCancelada() {
		return intCuotaCancelada;
	}
	public void setIntCuotaCancelada(Integer intCuotaCancelada) {
		this.intCuotaCancelada = intCuotaCancelada;
	}
	public BigDecimal getBdBeneficio() {
		return bdBeneficio;
	}
	public void setBdBeneficio(BigDecimal bdBeneficio) {
		this.bdBeneficio = bdBeneficio;
	}
	public BigDecimal getBdGastoAdm() {
		return bdGastoAdm;
	}
	public void setBdGastoAdm(BigDecimal bdGastoAdm) {
		this.bdGastoAdm = bdGastoAdm;
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
	public Boolean getChkAportVigentes() {
		return chkAportVigentes;
	}
	public void setChkAportVigentes(Boolean chkAportVigentes) {
		this.chkAportVigentes = chkAportVigentes;
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
	public Boolean getChkSolicitudBeneficio() {
		return chkSolicitudBeneficio;
	}
	public void setChkSolicitudBeneficio(Boolean chkSolicitudBeneficio) {
		this.chkSolicitudBeneficio = chkSolicitudBeneficio;
	}
	public Boolean getEnabDisabSolicitudBenef() {
		return enabDisabSolicitudBenef;
	}
	public void setEnabDisabSolicitudBenef(Boolean enabDisabSolicitudBenef) {
		this.enabDisabSolicitudBenef = enabDisabSolicitudBenef;
	}
	public Boolean getChkAprobacBeneficio() {
		return chkAprobacBeneficio;
	}
	public void setChkAprobacBeneficio(Boolean chkAprobacBeneficio) {
		this.chkAprobacBeneficio = chkAprobacBeneficio;
	}
	public Boolean getEnabDisabAprobacBenef() {
		return enabDisabAprobacBenef;
	}
	public void setEnabDisabAprobacBenef(Boolean enabDisabAprobacBenef) {
		this.enabDisabAprobacBenef = enabDisabAprobacBenef;
	}
	public Boolean getChkAnulRechazoBeneficio() {
		return chkAnulRechazoBeneficio;
	}
	public void setChkAnulRechazoBeneficio(Boolean chkAnulRechazoBeneficio) {
		this.chkAnulRechazoBeneficio = chkAnulRechazoBeneficio;
	}
	public Boolean getEnabDisabAnulRechazoBenef() {
		return enabDisabAnulRechazoBenef;
	}
	public void setEnabDisabAnulRechazoBenef(Boolean enabDisabAnulRechazoBenef) {
		this.enabDisabAnulRechazoBenef = enabDisabAnulRechazoBenef;
	}
	public Boolean getChkGiroBeneficio() {
		return chkGiroBeneficio;
	}
	public void setChkGiroBeneficio(Boolean chkGiroBeneficio) {
		this.chkGiroBeneficio = chkGiroBeneficio;
	}
	public Boolean getEnabDisabGiroBenef() {
		return enabDisabGiroBenef;
	}
	public void setEnabDisabGiroBenef(Boolean enabDisabGiroBenef) {
		this.enabDisabGiroBenef = enabDisabGiroBenef;
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
	public Boolean getEnabDisabEstCaptacion() {
		return enabDisabEstCaptacion;
	}
	public void setEnabDisabEstCaptacion(Boolean enabDisabEstCaptacion) {
		this.enabDisabEstCaptacion = enabDisabEstCaptacion;
	}
	public Boolean getChkCuotaFondoSepelio() {
		return chkCuotaFondoSepelio;
	}
	public void setChkCuotaFondoSepelio(Boolean chkCuotaFondoSepelio) {
		this.chkCuotaFondoSepelio = chkCuotaFondoSepelio;
	}
	public Boolean getEnabDisabCuotaFondoSepelio() {
		return enabDisabCuotaFondoSepelio;
	}
	public void setEnabDisabCuotaFondoSepelio(Boolean enabDisabCuotaFondoSepelio) {
		this.enabDisabCuotaFondoSepelio = enabDisabCuotaFondoSepelio;
	}
	public Boolean getChkTiempoAportacion() {
		return chkTiempoAportacion;
	}
	public void setChkTiempoAportacion(Boolean chkTiempoAportacion) {
		this.chkTiempoAportacion = chkTiempoAportacion;
	}
	public Boolean getEnabDisabTiempoAportacion() {
		return enabDisabTiempoAportacion;
	}
	public void setEnabDisabTiempoAportacion(Boolean enabDisabTiempoAportacion) {
		this.enabDisabTiempoAportacion = enabDisabTiempoAportacion;
	}
	public Boolean getChkTiempoPresentSustento() {
		return chkTiempoPresentSustento;
	}
	public void setChkTiempoPresentSustento(Boolean chkTiempoPresentSustento) {
		this.chkTiempoPresentSustento = chkTiempoPresentSustento;
	}
	public Boolean getEnabDisabTiempoPresentSust() {
		return enabDisabTiempoPresentSust;
	}
	public void setEnabDisabTiempoPresentSust(Boolean enabDisabTiempoPresentSust) {
		this.enabDisabTiempoPresentSust = enabDisabTiempoPresentSust;
	}
	public Boolean getChkEscala() {
		return chkEscala;
	}
	public void setChkEscala(Boolean chkEscala) {
		this.chkEscala = chkEscala;
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
	public String getStrAportacion() {
		return strAportacion;
	}
	public void setStrAportacion(String strAportacion) {
		this.strAportacion = strAportacion;
	}
	public List<Tabla> getListaTipoRelacion() {
		TablaFacadeRemote remote;
		try {
			remote = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			try {
				listaTipoRelacion = remote.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_TIPOROL), "C");
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (EJBFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaTipoRelacion;
	}
	
	public List<Tabla> getListaPeriocidadAes() {
		TablaFacadeRemote remote;
		try {
			remote = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			try {
				listaPeriocidadAes = remote.getListaTablaPorIdMaestro(Constante.PARAM_T_PERIOCIDAD_AES);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (EJBFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaPeriocidadAes;
	}
	
	public void setListaTipoRelacion(List<Tabla> listaTipoRelacion) {
		this.listaTipoRelacion = listaTipoRelacion;
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
	public List<Requisito> getListaRequisitos() {
		return listaRequisitos;
	}
	public void setListaRequisitos(List<Requisito> listaRequisitos) {
		this.listaRequisitos = listaRequisitos;
	}
	public List<Concepto> getListaConceptos() {
		return listaConceptos;
	}
	public void setListaConceptos(List<Concepto> listaConceptos) {
		this.listaConceptos = listaConceptos;
	}
	// Métodos a implementar
	/**************************************************************/
	/*  Nombre :  habilitarGrabarAes()     		      				*/
	/*                                                    	 		*/
	/*  Parametros. :  Ninguno					           	 		*/
	/*  Objetivo: Habilitar el Formulario para el llenado del mimso */
	/*  Retorno : El formulario habilitado para su respectivo llenado */
	/**************************************************************/
	public void habilitarGrabarAes(ActionEvent event) {
		setFormAesRendered(true);
		limpiarAes(event);
		listarConceptos(event);
		enabDisabEstCaptacion = true;
		strAportacion = Constante.MANTENIMIENTO_GRABAR;
	}
	
	/**************************************************************/
	/*  Nombre :  limpiarAportaciones()     			      	*/
	/*                                                    	 	*/
	/*  Parametros. :  Ninguno					           	 	*/
	/*  Objetivo: Limpiar el Formulario de Aportaciones			*/
	/*  Retorno : El formulario de Aportaciones vacío 			*/
	/**************************************************************/
	public void limpiarAes(ActionEvent event){
		Captacion aport = new Captacion();
		aport.setId(new CaptacionId());
		setBeanCaptacion(aport);
		setMsgTxtDescripcion("");
		setMsgTxtFechaIni("");
		setMsgTxtEstadoAporte("");
		setMsgTxtTipoPersona("");
		setMsgTxtCondicion("");
		if(listaCondicionComp!=null){
			listaCondicionComp.clear();
		}
		if(listaRequisitos!=null){
			listaRequisitos.clear();
		}
		setRbFecIndeterm("");
		setDaFechaIni(null);
		setDaFechaFin(null);
		setRbCondicion("");
		setRbTasaInteres("");
		setFecFinAportacionRendered(true);
		setChkLimiteEdad(false);
		setEnabDisabLimiteEdad(true);
		setChkCuotaFondoSepelio(false);
		setChkTiempoAportacion(false);
		setChkTiempoPresentSustento(false);
		
		setEnabDisabTiempoPresentSust(true);
		setChkAprobacBeneficio(false);
		setChkAnulRechazoBeneficio(false);
		setChkGiroBeneficio(false);
		setChkCancelacion(false);
		setEnabDisabAprobacBenef(true);
		setEnabDisabAnulRechazoBenef(true);
		setEnabDisabGiroBenef(true);
		setEnabDisabCancelacion(true);
		
		listarConceptos(event);
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  cancelarGrabarAportaciones() 		          		*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*                                                    	 		*/
	/*  Objetivo: Cancelar la nueva Hoja de Planeamiento     		*/
	/*            Poblacional.                               	  	*/
	/*  Retorno : Se oculta el Formulario de Aportaciones		 	*/
	/**************************************************************/
	public void cancelarGrabarFondoSepelio(ActionEvent event) {
		setFormAesRendered(false);
		limpiarAes(event);
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  listarAes()        								*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/
	/*  Objetivo: Listar las Hojas de Planeamiento     				*/
	/*  Retorno : Devuelve el listado de las Hojas de Planeamiento 	*/
	/**************************************************************/
	public void listarAes(ActionEvent event) {
		log.info("-----------------------Debugging FondoSepelioController.listarAes-----------------------------");
		CaptacionFacadeLocal facade = null;
		try {
			facade = (CaptacionFacadeLocal)EJBFactory.getLocal(CaptacionFacadeLocal.class);
			Captacion o = new Captacion();
			o.setId(new CaptacionId());
			o.setCondicion(new Condicion());
			o.getCondicion().setId(new CondicionId());
			o.getId().setIntPersEmpresaPk(Constante.PARAM_EMPRESASESION);
			o.getId().setIntParaTipoCaptacionCod(Constante.CAPTACION_AES);
			if(intTipoFecha!=null && intTipoFecha!=0)
				o.setIntTipoFecha(intTipoFecha);
			if(intIdEstadoAportacion!=null && intIdEstadoAportacion!=0)
			o.setIntParaEstadoSolicitudCod(intIdEstadoAportacion);
			o.setStrDescripcion(strNombreFondoSepelio);
			if(intIdCondicionAportacion!=null && intIdCondicionAportacion!=0)
			o.getCondicion().getId().setIntParaCondicionSocioCod(intIdCondicionAportacion);
			if(intIdTipoRelacion!=null && intIdTipoRelacion!=0)
				o.setIntParaRolPk(intIdTipoRelacion);
			
			String strFechaIni = (getDaFecIni()!=null)?Constante.sdf.format(getDaFecIni()):null;
			String strFechaFin = (getDaFecFin()!=null)?Constante.sdf.format(getDaFecFin()):null;
			log.info("strFechaIni: "+ strFechaIni);
			log.info("strFechaFin: "+ strFechaFin);
			o.setStrDtFechaIni(strFechaIni);
			o.setStrDtFechaFin(strFechaFin);
			
			o.setDtInicio(daFecIni);
			o.setDtFin(daFecFin);
			if(intIdTipoPersona!=null && intIdTipoPersona!=0)
				o.setIntParaTipopersonaCod(intIdTipoPersona);
			//o.setIntIndeterminado(getChkIndeterminado() ==true?1:0);
			o.setIntVigencia(chkAportVigentes==true?1:null);
			o.setIntAportacionVigente(getRbVigente());
			//o.setIntAportacionCaduco(getChkAportCaduco()==true?1:0);
			
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
	/*  Retorno : Devuelve habilitado o deshabilitado algún control	*/
	/*            seleccionado 						     	 		*/
	/**************************************************************/
	public void enableDisableControls(ActionEvent event) {
		setEnabDisabNombFondoSepelio(getChkNombFondoSepelio()==true);
		setStrNombreFondoSepelio(getChkNombFondoSepelio()==true?"":getStrNombreFondoSepelio());
		setEnabDisabFechasAport(getChkFechas()!=true);
		setDaFecIni(getChkFechas()!=true?null:getDaFecIni());
		setDaFecFin(getChkFechas()!=true?null:getDaFecFin());
		setBlnVigencia(chkAportVigentes!=true);
		setRbVigente(chkAportVigentes==true?1:null);
		setEnabDisabLimiteEdad(getChkLimiteEdad()!=null && getChkLimiteEdad()!=true);
		
		setEnabDisabCuotaFondoSepelio(getChkCuotaFondoSepelio()!=null && getChkCuotaFondoSepelio()!=true);
		setEnabDisabTiempoAportacion(getChkTiempoAportacion()!=null && getChkTiempoAportacion()!=true);
		setEnabDisabTiempoPresentSust(getChkTiempoPresentSustento()!=null && getChkTiempoPresentSustento()!=true);
		
		setEnabDisabSolicitudBenef(getChkSolicitudBeneficio()!=true);
		setEnabDisabAprobacBenef(getChkAprobacBeneficio()!=true);
		setEnabDisabAnulRechazoBenef(getChkAnulRechazoBeneficio()!=true);
		setEnabDisabGiroBenef(getChkGiroBeneficio()!=true);
		
		if(getRbFecIndeterm()!=null){
			setFecFinAportacionRendered(getRbFecIndeterm().equals("1"));
			setDaFechaFin(null);
		}
		
		Captacion aport = new Captacion();
		aport = (Captacion) getBeanCaptacion();
		
		if(getChkLimiteEdad()!=null && getChkLimiteEdad()==false){
			aport.setIntEdadLimite(null);
		}
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
	    return validCaptacion;
	}
	
	/***********************************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  irModificarAes()      							*/
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
	
	public void irModificarAes(ActionEvent event) throws ParseException{
    	log.info("-----------------------Debugging AesController.irModificarAes-----------------------------");
    	CaptacionFacadeLocal captacionFacade = null;
    	String strIdEmpresa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAesModalPanel:hiddenIdEmpresa");
		String strIdTipoCaptacion = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAesModalPanel:hiddenIdTipoCaptacion");
		String strIdCodigo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAesModalPanel:hiddenIdCodigo");
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
				
				enabDisabEstCaptacion = false;
				String daFecIni = "" + (beanCaptacion.getStrDtFechaIni() == null ? "" : beanCaptacion.getStrDtFechaIni());
				Date fecIni = (daFecIni == null || daFecIni.equals("") ? null : Constante.sdf.parse(daFecIni));
				setDaFechaIni(fecIni);
				
				String daFecFin = "" + (beanCaptacion.getStrDtFechaFin() == null ? "" : beanCaptacion.getStrDtFechaFin());
				Date fecFin = (daFecFin == null || daFecFin.equals("") ? null : Constante.sdf.parse(daFecFin));
				setDaFechaFin(fecFin);
				
				setFecFinAportacionRendered(fecFin!=null);
				setRbFecIndeterm(fecFin!=null?"1":"2");
				
				setEnabDisabLimiteEdad(beanCaptacion.getIntEdadLimite()==null);
				setChkLimiteEdad(beanCaptacion.getIntEdadLimite()!=null);
				
				setEnabDisabCuotaFondoSepelio(beanCaptacion.getIntCuota()!=null && beanCaptacion.getIntCuota()==0);
				setChkCuotaFondoSepelio(beanCaptacion.getIntCuota()!=null && beanCaptacion.getIntCuota()!=0);
				setEnabDisabTiempoAportacion(beanCaptacion.getIntTiempoAportacion()!= null && beanCaptacion.getIntTiempoAportacion()==0);
				setChkTiempoAportacion(beanCaptacion.getIntTiempoAportacion()!=null && beanCaptacion.getIntTiempoAportacion()!=0);
				setEnabDisabTiempoPresentSust(beanCaptacion.getIntTiempoSustento()!=null && beanCaptacion.getIntTiempoSustento()==0);
				setChkTiempoPresentSustento(beanCaptacion.getIntTiempoSustento()!=null && beanCaptacion.getIntTiempoSustento()!=0);
				
				setRbCondicion(beanCaptacion.getListaCondicion().size()==4?"1":"2");
				if(beanCaptacion.getListaCondicionComp()!=null && beanCaptacion.getListaCondicionComp().size()>0){
					listaCondicionComp = beanCaptacion.getListaCondicionComp();
				}
				
				if(beanCaptacion.getListaConcepto()!=null && beanCaptacion.getListaConcepto().size()>0){
					listaConceptos = beanCaptacion.getListaConcepto();
				}
				
				setFormAesRendered(true);
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
	/*  Nombre :  grabarAes()        								*/
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
	public void grabarAes(ActionEvent event){
		CaptacionFacadeLocal facade = null;
		
	    if(isValidoAportacion(beanCaptacion) == false){
	    	log.info("Datos de AES no válidos. Se aborta el proceso de grabación de AES.");
	    	return;
	    }
	    //beanCaptacion.setId(new CaptacionId());
	    beanCaptacion.getId().setIntPersEmpresaPk(Constante.PARAM_EMPRESASESION);//beanSesion.getIntIdEmpresa()
	    beanCaptacion.getId().setIntParaTipoCaptacionCod(Constante.CAPTACION_AES);
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
		
		beanCaptacion.setIntParaPeriodicCuotasCod(Constante.PARAM_T_MES);
		beanCaptacion.setIntPeriodicAportacionCod(Constante.PARAM_T_MES);
		beanCaptacion.setIntPeriodicSustento(Constante.PARAM_T_DIA);
		
		if(listaCondicionComp!=null){
			beanCaptacion.setListaCondicionComp(listaCondicionComp);
		}
		
		if(listaConceptos!=null){
			beanCaptacion.setListaConcepto(listaConceptos);
		}
	    
    	try {
			facade = (CaptacionFacadeLocal)EJBFactory.getLocal(CaptacionFacadeLocal.class);
			facade.grabarCaptacion(beanCaptacion);
			limpiarAes(event);
			listarAes(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	/***********************************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  modificarAes()        							*/
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
	
	public void modificarAes(ActionEvent event){
    	CaptacionFacadeLocal facade = null;
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
		
		beanCaptacion.setIntParaPeriodicCuotasCod(Constante.PARAM_T_MES);
		beanCaptacion.setIntPeriodicAportacionCod(Constante.PARAM_T_MES);
		beanCaptacion.setIntPeriodicSustento(Constante.PARAM_T_DIA);
		
		if(listaCondicionComp!=null){
			beanCaptacion.setListaCondicionComp(listaCondicionComp);
		}
		
		if(listaConceptos!=null){
			beanCaptacion.setListaConcepto(listaConceptos);
		}
    	
    	try {
			facade = (CaptacionFacadeLocal)EJBFactory.getLocal(CaptacionFacadeLocal.class);
			facade.modificarCaptacion(beanCaptacion);
			limpiarAes(event);
			listarAes(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
    }
	
	public void eliminarAes(ActionEvent event){
    	log.info("-----------------------Debugging FondoSepelioController.eliminarFondoSepelio-----------------------------");
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
			limpiarAes(event);
			listarAes(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
    }
	
	public void listarConceptos(ActionEvent event) {
		log.info("--------------------Debugging AesController.listarConceptos--------------------");
		TablaFacadeRemote facade = null;
		List<Tabla> listaTabla = null;
		Concepto concepto = null;
		List<Concepto> listConcepto = new ArrayList<Concepto>();
		try {
			facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listaTabla = facade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_AES));

			for(int i=0;i<listaTabla.size(); i++){
				concepto = new Concepto();
				concepto.setTabla(new Tabla());
				concepto.getTabla().setIntIdDetalle(listaTabla.get(i).getIntIdDetalle());
				concepto.getTabla().setStrDescripcion(listaTabla.get(i).getStrDescripcion());
				listConcepto.add(concepto);
			}
			listaConceptos = listConcepto;
			
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
	}
}