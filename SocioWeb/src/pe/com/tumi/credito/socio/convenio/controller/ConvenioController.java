package pe.com.tumi.credito.socio.convenio.controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.PermisoUtil;
import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.captacion.domain.Condicion;
import pe.com.tumi.credito.socio.captacion.domain.CondicionId;
import pe.com.tumi.credito.socio.captacion.facade.CaptacionFacadeLocal;
import pe.com.tumi.credito.socio.convenio.domain.Adenda;
import pe.com.tumi.credito.socio.convenio.domain.AdendaCaptacion;
import pe.com.tumi.credito.socio.convenio.domain.AdendaCredito;
import pe.com.tumi.credito.socio.convenio.domain.AdendaId;
import pe.com.tumi.credito.socio.convenio.domain.Adjunto;
import pe.com.tumi.credito.socio.convenio.domain.DonacionRegalia;
import pe.com.tumi.credito.socio.convenio.domain.DonacionRegaliaId;
import pe.com.tumi.credito.socio.convenio.domain.Firmante;
import pe.com.tumi.credito.socio.convenio.domain.RetencionPlanilla;
import pe.com.tumi.credito.socio.convenio.domain.RetencionPlanillaId;
import pe.com.tumi.credito.socio.convenio.domain.composite.CaptacionComp;
import pe.com.tumi.credito.socio.convenio.domain.composite.ConvenioComp;
import pe.com.tumi.credito.socio.convenio.domain.composite.FirmanteComp;
import pe.com.tumi.credito.socio.convenio.facade.ConvenioFacadeLocal;
import pe.com.tumi.credito.socio.convenio.facade.HojaPlaneamientoFacadeLocal;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.credito.domain.composite.CreditoComp;
import pe.com.tumi.credito.socio.creditos.domain.CondicionCredito;
import pe.com.tumi.credito.socio.creditos.domain.CondicionCreditoId;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.facade.CreditoFacadeLocal;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.composite.ConvenioEstructuraDetalleComp;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class ConvenioController{
	protected  static Logger 	log 			= Logger.getLogger(ConvenioController.class);
	private int 						rows = 5;
	private Adenda						beanAdenda = null;
	private SocioComp					beanSocioComp = null;
	private Usuario						usuario;
	//Modificado por cdelosrios, 08/12/2013
	/*private Integer						intTipoConvenio;
	private Integer						intEstadoConvenio;
	private Boolean						chkRenovacion;
	private Boolean						chkDonacionRegalia;
	private Integer						intTipoSucursal;
	private Boolean						chkDocAdjunto;*/
	private Boolean						chkRetencionPlla;
	private Integer						intIdConvenio;
	private Integer						intSucursalConv;
	private String						strEntidad;
	private Boolean						chkVigencia;
	private Integer						intVigenciaConvenio;
	//Fin modificado por cdelosrios, 08/12/2013
	private List<ConvenioComp> 			listaConvenioComp;
	private List<ConvenioComp> 			listaConvenioDetComp;
	private List<ConvenioComp> 			listaHojaPlaneamientoComp;
	private List<ConvenioEstructuraDetalleComp> listaConvenioEstructuraDet;
	private List<Sucursal> 				listJuridicaSucursal = null;
	private List<RetencionPlanilla>		listaRetencPlla;
	private List<DonacionRegalia>		listaDonacionRegalia;
	private Firmante					beanFirmante;
	private String 						strConvenio;
	private Integer						intRbTipoConvenio;
	private Integer						intItemConvenio;
	private Boolean						enabRbVigencia = true;
	
	private String						hiddenConvenio;
	private String						hiddenItemConvenio;
	private String						strCartaPresentacion;
	private String						strConvenioSugerido;
	private String						strAdendaSugerida;
	
	private Boolean						formConvenioRendered = false;
	private Boolean						formTipoConvEnabled = false;
	private Boolean						btnBuscarEnabled = true;
	private Boolean						formRetenPlla = true;
	
	private String						strDtFecInicio;
	private String						strDtFecFin;
	private String						strDtFecSuscripcion;
	private String						strPlazoConvenio;
	private Integer						intSucursalHojaPlan;
	private List<Sucursal>				listSucursalHojaPlan;
	private Integer						intParaRetencionPlla;
	private BigDecimal					bdRetencion;
	private Integer						intParaTipoRetencion;
	private String[] 					lstModalidadTipoSocio;
	private Integer						intParaTipoCreditoCod;
	private Boolean						chkRenovacionAutomatica;
	private List<FirmanteComp>			listaRepresentanteLegal;
	private List<Firmante>				listaFirmantes;
	private List<CaptacionComp>			listaAportacionComp;
	private List<CaptacionComp>			listaFondoSepelioComp;
	private List<CaptacionComp>			listaFondoRetiroComp;
	private List<CaptacionComp>			listaMantCtaComp;
	private List<CreditoComp>			listaCreditoComp;
	
	private List<AdendaCaptacion>		listaAdendaAportacion;
	private List<AdendaCaptacion>		listaAdendaFondoSepelio;
	private List<AdendaCaptacion>		listaAdendaFondoRetiro;
	private List<AdendaCaptacion>		listaAdendaMantCta;
	//private List<Credito>				listaCredito;
	
	private List<AdendaCredito> 		listaAdendaCredito;
	
	//Mensajes de Error
	private String						msgTipoConvenio;
	private String						msgTxtNuevaAdenda;
	private String						msgListaFirmantes;
	private String						msgTxtRetencionPlla;
	
	private Integer 					sesionIntIdEmpresa;
	
	HojaPlaneamientoFacadeLocal facadeHojaPlaneamiento = null;
	ConvenioFacadeLocal facadeConvenio = null;
	PersonaFacadeRemote facadePersona = null;
	CaptacionFacadeLocal facadeCaptacion = null;
	CreditoFacadeLocal facadeCredito = null;
	GeneralFacadeRemote generalFacade = null;
	
	//Agregado por cdelosrios, 08/12/2013
	private boolean						poseePermiso;
	Integer MENU_CONVENIO = 105;
	//Fin agregado por cdelosrios, 08/12/2013
	
	public ConvenioController() throws BusinessException{
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(MENU_CONVENIO);
		if(usuario!=null && poseePermiso){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}
	}
	
	public void cargarValoresIniciales(){
		try {
			listJuridicaSucursal = new ArrayList<Sucursal>();
			listaDonacionRegalia = new ArrayList<DonacionRegalia>();
			listaRepresentanteLegal = new ArrayList<FirmanteComp>();
			listaFirmantes = new ArrayList<Firmante>();
			listaAdendaAportacion = new ArrayList<AdendaCaptacion>();
			listaAdendaFondoSepelio = new ArrayList<AdendaCaptacion>();
			listaAdendaFondoRetiro = new ArrayList<AdendaCaptacion>();
			listaAdendaMantCta = new ArrayList<AdendaCaptacion>();
			//listaCredito = new ArrayList<Credito>();
			listaAdendaCredito = new ArrayList<AdendaCredito>();
			facadeHojaPlaneamiento = (HojaPlaneamientoFacadeLocal)EJBFactory.getLocal(HojaPlaneamientoFacadeLocal.class);
			facadeConvenio = (ConvenioFacadeLocal)EJBFactory.getLocal(ConvenioFacadeLocal.class);
			facadePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			facadeCaptacion = (CaptacionFacadeLocal)EJBFactory.getLocal(CaptacionFacadeLocal.class);
			facadeCredito = (CreditoFacadeLocal)EJBFactory.getLocal(CreditoFacadeLocal.class);
			generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
		} catch(EJBFactoryException e){
			log.error("error: "+e);
		}
	}
	
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public Adenda getBeanAdenda() {
		return beanAdenda;
	}
	public void setBeanAdenda(Adenda beanAdenda) {
		this.beanAdenda = beanAdenda;
	}
	public SocioComp getBeanSocioComp() {
		return beanSocioComp;
	}
	public void setBeanSocioComp(SocioComp beanSocioComp) {
		this.beanSocioComp = beanSocioComp;
	} 
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Integer getIntSucursalConv() {
		return intSucursalConv;
	}
	public void setIntSucursalConv(Integer intSucursalConv) {
		this.intSucursalConv = intSucursalConv;
	}
	public Boolean getChkRetencionPlla() {
		return chkRetencionPlla;
	}
	public void setChkRetencionPlla(Boolean chkRetencionPlla) {
		this.chkRetencionPlla = chkRetencionPlla;
	}
	public Integer getIntIdConvenio() {
		return intIdConvenio;
	}
	public void setIntIdConvenio(Integer intIdConvenio) {
		this.intIdConvenio = intIdConvenio;
	}
	public String getStrEntidad() {
		return strEntidad;
	}
	public void setStrEntidad(String strEntidad) {
		this.strEntidad = strEntidad;
	}
	public Boolean getChkVigencia() {
		return chkVigencia;
	}
	public void setChkVigencia(Boolean chkVigencia) {
		this.chkVigencia = chkVigencia;
	}
	public Integer getIntVigenciaConvenio() {
		return intVigenciaConvenio;
	}
	public void setIntVigenciaConvenio(Integer intVigenciaConvenio) {
		this.intVigenciaConvenio = intVigenciaConvenio;
	}
	public Boolean getChkRenovacionAutomatica() {
		return chkRenovacionAutomatica;
	}
	public void setChkRenovacionAutomatica(Boolean chkRenovacionAutomatica) {
		this.chkRenovacionAutomatica = chkRenovacionAutomatica;
	}
	public List<ConvenioComp> getListaConvenioComp() {
		return listaConvenioComp;
	}
	public void setListaConvenioComp(List<ConvenioComp> listaConvenioComp) {
		this.listaConvenioComp = listaConvenioComp;
	}
	public List<ConvenioComp> getListaConvenioDetComp() {
		return listaConvenioDetComp;
	}
	public void setListaConvenioDetComp(List<ConvenioComp> listaConvenioDetComp) {
		this.listaConvenioDetComp = listaConvenioDetComp;
	}
	public List<ConvenioComp> getListaHojaPlaneamientoComp() {
		return listaHojaPlaneamientoComp;
	}
	public void setListaHojaPlaneamientoComp(
			List<ConvenioComp> listaHojaPlaneamientoComp) {
		this.listaHojaPlaneamientoComp = listaHojaPlaneamientoComp;
	}
	public List<ConvenioEstructuraDetalleComp> getListaConvenioEstructuraDet() {
		return listaConvenioEstructuraDet;
	}
	public void setListaConvenioEstructuraDet(
			List<ConvenioEstructuraDetalleComp> listaConvenioEstructuraDet) {
		this.listaConvenioEstructuraDet = listaConvenioEstructuraDet;
	}
	public List<Sucursal> getListJuridicaSucursal() {
		log.info("sesionIntIdEmpresa: "+sesionIntIdEmpresa);
		try {
			if(listJuridicaSucursal != null){
				EmpresaFacadeRemote facade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				this.listJuridicaSucursal = facade.getListaSucursalPorPkEmpresa(sesionIntIdEmpresa);
			}
			
			//Ordenamos por nombre
			Collections.sort(listJuridicaSucursal, new Comparator<Sucursal>(){
				public int compare(Sucursal uno, Sucursal otro) {
					return (uno.getJuridica().getStrSiglas().trim().toUpperCase()).
							compareTo(otro.getJuridica().getStrSiglas().trim().toUpperCase());
				}
			});	
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return this.listJuridicaSucursal;
	}
	public void setListJuridicaSucursal(List<Sucursal> listJuridicaSucursal) {
		this.listJuridicaSucursal = listJuridicaSucursal;
	}
	public List<RetencionPlanilla> getListaRetencPlla() {
		return listaRetencPlla;
	}
	public void setListaRetencPlla(List<RetencionPlanilla> listaRetencPlla) {
		this.listaRetencPlla = listaRetencPlla;
	}
	public List<DonacionRegalia> getListaDonacionRegalia() {
		return listaDonacionRegalia;
	}
	public void setListaDonacionRegalia(List<DonacionRegalia> listaDonacionRegalia) {
		this.listaDonacionRegalia = listaDonacionRegalia;
	}
	public Firmante getBeanFirmante() {
		return beanFirmante;
	}
	public void setBeanFirmante(Firmante beanFirmante) {
		this.beanFirmante = beanFirmante;
	}
	public String getStrConvenio() {
		return strConvenio;
	}
	public void setStrConvenio(String strConvenio) {
		this.strConvenio = strConvenio;
	}
	public Integer getIntRbTipoConvenio() {
		return intRbTipoConvenio;
	}
	public void setIntRbTipoConvenio(Integer intRbTipoConvenio) {
		this.intRbTipoConvenio = intRbTipoConvenio;
	}
	public Integer getIntItemConvenio() {
		return intItemConvenio;
	}
	public void setIntItemConvenio(Integer intItemConvenio) {
		this.intItemConvenio = intItemConvenio;
	}
	public Boolean getEnabRbVigencia() {
		return enabRbVigencia;
	}
	public void setEnabRbVigencia(Boolean enabRbVigencia) {
		this.enabRbVigencia = enabRbVigencia;
	}
	public String getHiddenConvenio() {
		return hiddenConvenio;
	}
	public void setHiddenConvenio(String hiddenConvenio) {
		this.hiddenConvenio = hiddenConvenio;
	}
	public String getHiddenItemConvenio() {
		return hiddenItemConvenio;
	}
	public void setHiddenItemConvenio(String hiddenItemConvenio) {
		this.hiddenItemConvenio = hiddenItemConvenio;
	}
	public String getStrCartaPresentacion() {
		return strCartaPresentacion;
	}
	public void setStrCartaPresentacion(String strCartaPresentacion) {
		this.strCartaPresentacion = strCartaPresentacion;
	}
	public String getStrConvenioSugerido() {
		return strConvenioSugerido;
	}
	public void setStrConvenioSugerido(String strConvenioSugerido) {
		this.strConvenioSugerido = strConvenioSugerido;
	}
	public String getStrAdendaSugerida() {
		return strAdendaSugerida;
	}
	public void setStrAdendaSugerida(String strAdendaSugerida) {
		this.strAdendaSugerida = strAdendaSugerida;
	}
	public Boolean getFormConvenioRendered() {
		return formConvenioRendered;
	}
	public void setFormConvenioRendered(Boolean formConvenioRendered) {
		this.formConvenioRendered = formConvenioRendered;
	}
	public Boolean getFormTipoConvEnabled() {
		return formTipoConvEnabled;
	}
	public void setFormTipoConvEnabled(Boolean formTipoConvEnabled) {
		this.formTipoConvEnabled = formTipoConvEnabled;
	}
	public Boolean getBtnBuscarEnabled() {
		return btnBuscarEnabled;
	}
	public void setBtnBuscarEnabled(Boolean btnBuscarEnabled) {
		this.btnBuscarEnabled = btnBuscarEnabled;
	}
	public Boolean getFormRetenPlla() {
		return formRetenPlla;
	}
	public void setFormRetenPlla(Boolean formRetenPlla) {
		this.formRetenPlla = formRetenPlla;
	}
	public String getStrDtFecInicio() {
		return strDtFecInicio;
	}
	public void setStrDtFecInicio(String strDtFecInicio) {
		this.strDtFecInicio = strDtFecInicio;
	}
	public String getStrDtFecFin() {
		return strDtFecFin;
	}
	public void setStrDtFecFin(String strDtFecFin) {
		this.strDtFecFin = strDtFecFin;
	}
	public String getStrDtFecSuscripcion() {
		return strDtFecSuscripcion;
	}
	public void setStrDtFecSuscripcion(String strDtFecSuscripcion) {
		this.strDtFecSuscripcion = strDtFecSuscripcion;
	}
	public String getStrPlazoConvenio() {
		return strPlazoConvenio;
	}
	public void setStrPlazoConvenio(String strPlazoConvenio) {
		this.strPlazoConvenio = strPlazoConvenio;
	}
	public Integer getIntSucursalHojaPlan() {
		return intSucursalHojaPlan;
	}
	public void setIntSucursalHojaPlan(Integer intSucursalHojaPlan) {
		this.intSucursalHojaPlan = intSucursalHojaPlan;
	}
	public List<Sucursal> getListSucursalHojaPlan() {
		log.info("-------------------------------------Debugging EstructuraOrganicaController.getListJuridicaSucursal-------------------------------------");
		log.info("sesionIntIdEmpresa: "+sesionIntIdEmpresa);
		try {
			if(listSucursalHojaPlan == null){
				EmpresaFacadeRemote facade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				this.listSucursalHojaPlan = facade.getListaSucursalPorPkEmpresa(sesionIntIdEmpresa);
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return this.listSucursalHojaPlan;
	}
	public void setListSucursalHojaPlan(List<Sucursal> listSucursalHojaPlan) {
		this.listSucursalHojaPlan = listSucursalHojaPlan;
	}
	public Integer getIntParaRetencionPlla() {
		return intParaRetencionPlla;
	}
	public void setIntParaRetencionPlla(Integer intParaRetencionPlla) {
		this.intParaRetencionPlla = intParaRetencionPlla;
	}
	public BigDecimal getBdRetencion() {
		return bdRetencion;
	}
	public void setBdRetencion(BigDecimal bdRetencion) {
		this.bdRetencion = bdRetencion;
	}
	public Integer getIntParaTipoRetencion() {
		return intParaTipoRetencion;
	}
	public void setIntParaTipoRetencion(Integer intParaTipoRetencion) {
		this.intParaTipoRetencion = intParaTipoRetencion;
	}
	public String[] getLstModalidadTipoSocio() {
		return lstModalidadTipoSocio;
	}
	public void setLstModalidadTipoSocio(String[] lstModalidadTipoSocio) {
		this.lstModalidadTipoSocio = lstModalidadTipoSocio;
	}
	public Integer getIntParaTipoCreditoCod() {
		return intParaTipoCreditoCod;
	}
	public void setIntParaTipoCreditoCod(Integer intParaTipoCreditoCod) {
		this.intParaTipoCreditoCod = intParaTipoCreditoCod;
	}
	public List<FirmanteComp> getListaRepresentanteLegal() {
		return listaRepresentanteLegal;
	}
	public void setListaRepresentanteLegal(
			List<FirmanteComp> listaRepresentanteLegal) {
		this.listaRepresentanteLegal = listaRepresentanteLegal;
	}
	public List<Firmante> getListaFirmantes() {
		return listaFirmantes;
	}
	public void setListaFirmantes(List<Firmante> listaFirmantes) {
		this.listaFirmantes = listaFirmantes;
	}
	public List<CaptacionComp> getListaAportacionComp() {
		return listaAportacionComp;
	}
	public void setListaAportacionComp(List<CaptacionComp> listaAportacionComp) {
		this.listaAportacionComp = listaAportacionComp;
	}
	public List<CaptacionComp> getListaFondoSepelioComp() {
		return listaFondoSepelioComp;
	}
	public void setListaFondoSepelioComp(List<CaptacionComp> listaFondoSepelioComp) {
		this.listaFondoSepelioComp = listaFondoSepelioComp;
	}
	public List<CaptacionComp> getListaFondoRetiroComp() {
		return listaFondoRetiroComp;
	}
	public void setListaFondoRetiroComp(List<CaptacionComp> listaFondoRetiroComp) {
		this.listaFondoRetiroComp = listaFondoRetiroComp;
	}
	public List<CaptacionComp> getListaMantCtaComp() {
		return listaMantCtaComp;
	}
	public void setListaMantCtaComp(List<CaptacionComp> listaMantCtaComp) {
		this.listaMantCtaComp = listaMantCtaComp;
	}
	public List<CreditoComp> getListaCreditoComp() {
		return listaCreditoComp;
	}
	public void setListaCreditoComp(List<CreditoComp> listaCreditoComp) {
		this.listaCreditoComp = listaCreditoComp;
	}
	public List<AdendaCaptacion> getListaAdendaAportacion() {
		return listaAdendaAportacion;
	}
	public void setListaAdendaAportacion(List<AdendaCaptacion> listaAdendaAportacion) {
		this.listaAdendaAportacion = listaAdendaAportacion;
	}
	public List<AdendaCaptacion> getListaAdendaFondoSepelio() {
		return listaAdendaFondoSepelio;
	}
	public void setListaAdendaFondoSepelio(
			List<AdendaCaptacion> listaAdendaFondoSepelio) {
		this.listaAdendaFondoSepelio = listaAdendaFondoSepelio;
	}
	public List<AdendaCaptacion> getListaAdendaFondoRetiro() {
		return listaAdendaFondoRetiro;
	}
	public void setListaAdendaFondoRetiro(
			List<AdendaCaptacion> listaAdendaFondoRetiro) {
		this.listaAdendaFondoRetiro = listaAdendaFondoRetiro;
	}
	public List<AdendaCaptacion> getListaAdendaMantCta() {
		return listaAdendaMantCta;
	}
	public void setListaAdendaMantCta(List<AdendaCaptacion> listaAdendaMantCta) {
		this.listaAdendaMantCta = listaAdendaMantCta;
	}
	public List<AdendaCredito> getListaAdendaCredito() {
		return listaAdendaCredito;
	}
	public void setListaAdendaCredito(List<AdendaCredito> listaAdendaCredito) {
		this.listaAdendaCredito = listaAdendaCredito;
	}
	public String getMsgTipoConvenio() {
		return msgTipoConvenio;
	}
	public void setMsgTipoConvenio(String msgTipoConvenio) {
		this.msgTipoConvenio = msgTipoConvenio;
	}
	public String getMsgTxtNuevaAdenda() {
		return msgTxtNuevaAdenda;
	}
	public void setMsgTxtNuevaAdenda(String msgTxtNuevaAdenda) {
		this.msgTxtNuevaAdenda = msgTxtNuevaAdenda;
	}
	public String getMsgListaFirmantes() {
		return msgListaFirmantes;
	}
	public void setMsgListaFirmantes(String msgListaFirmantes) {
		this.msgListaFirmantes = msgListaFirmantes;
	}
	public String getMsgTxtRetencionPlla() {
		return msgTxtRetencionPlla;
	}
	public void setMsgTxtRetencionPlla(String msgTxtRetencionPlla) {
		this.msgTxtRetencionPlla = msgTxtRetencionPlla;
	}
	public Integer getSesionIntIdEmpresa() {
		return sesionIntIdEmpresa;
	}
	public void setSesionIntIdEmpresa(Integer sesionIntIdEmpresa) {
		this.sesionIntIdEmpresa = sesionIntIdEmpresa;
	}
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
	}
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return sesion.getAttribute(beanName);
	}
	public HojaPlaneamientoFacadeLocal getFacadeHojaPlaneamiento() {
		return facadeHojaPlaneamiento;
	}
	public void setFacadeHojaPlaneamiento(
			HojaPlaneamientoFacadeLocal facadeHojaPlaneamiento) {
		this.facadeHojaPlaneamiento = facadeHojaPlaneamiento;
	}
	public ConvenioFacadeLocal getFacadeConvenio() {
		return facadeConvenio;
	}
	public void setFacadeConvenio(ConvenioFacadeLocal facadeConvenio) {
		this.facadeConvenio = facadeConvenio;
	}
	public PersonaFacadeRemote getFacadePersona() {
		return facadePersona;
	}
	public void setFacadePersona(PersonaFacadeRemote facadePersona) {
		this.facadePersona = facadePersona;
	}
	public CaptacionFacadeLocal getFacadeCaptacion() {
		return facadeCaptacion;
	}
	public void setFacadeCaptacion(CaptacionFacadeLocal facadeCaptacion) {
		this.facadeCaptacion = facadeCaptacion;
	}
	public CreditoFacadeLocal getFacadeCredito() {
		return facadeCredito;
	}
	public void setFacadeCredito(CreditoFacadeLocal facadeCredito) {
		this.facadeCredito = facadeCredito;
	}
	public GeneralFacadeRemote getGeneralFacade() {
		return generalFacade;
	}
	public void setGeneralFacade(GeneralFacadeRemote generalFacade) {
		this.generalFacade = generalFacade;
	}
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

	/**************************************************************/
	/*  Nombre :  habilitarFormConvenio()           		 		*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                                                    	 		*/
	/*  Objetivo: Habilitar el Formulario del Convenio 				*/
	/*                                           					*/
	/*  Retorno : El Formulario del Convenio habilitado   	 		*/
	/**************************************************************/
	public void habilitarGrabarFormConvenio(ActionEvent event){
		setFormConvenioRendered(true);
		limpiarConvenio();
		strConvenio = Constante.MANTENIMIENTO_GRABAR;
	}
	
	public void limpiarConvenio(){
		Adenda adenda = new Adenda();
		adenda.setId(new AdendaId());
		setBeanAdenda(adenda);
		setBeanSocioComp(new SocioComp());
		setFormTipoConvEnabled(false);
		setChkRenovacionAutomatica(null);
		intRbTipoConvenio = null;
		intItemConvenio = null;
		if(listaConvenioEstructuraDet!=null){
			listaConvenioEstructuraDet.clear();
		}
		setStrDtFecInicio("");
		setStrDtFecFin("");
		setStrDtFecSuscripcion("");
		setStrPlazoConvenio("");
		setBdRetencion(null);
		setIntParaTipoRetencion(null);
		setStrCartaPresentacion(null);
		setStrConvenioSugerido(null);
		setStrAdendaSugerida(null);
		chkRetencionPlla = false;
		formRetenPlla = true;
		if(listSucursalHojaPlan!=null){
			listSucursalHojaPlan.clear();
		}
		if(lstModalidadTipoSocio!=null){
			setLstModalidadTipoSocio(null);
		}
		if(listaRetencPlla!=null){
			listaRetencPlla.clear();
		}
		if(listaDonacionRegalia!=null){
			listaDonacionRegalia.clear();
		}
		if(listaFirmantes!=null){
			listaFirmantes.clear();
		}
		if(listaAdendaAportacion!=null){
			listaAdendaAportacion.clear();
		}
		if(listaAdendaFondoSepelio!=null){
			listaAdendaFondoSepelio.clear();
		}
		if(listaAdendaFondoRetiro!=null){
			listaAdendaFondoRetiro.clear();
		}
		if(listaAdendaMantCta!=null){
			listaAdendaMantCta.clear();
		}
		if(listaAdendaCredito!=null){
			listaAdendaCredito.clear();
		}
	}
	
	public void cancelarGrabarConvenio(ActionEvent event){
		setFormConvenioRendered(false);
		setStrConvenio(null);
		limpiarConvenio();
	}
	
	public void enableDisableControls(ActionEvent event){
		setBtnBuscarEnabled(intRbTipoConvenio==null);
		setEnabRbVigencia(chkVigencia!=true);
		setIntVigenciaConvenio(chkVigencia!=true?null:getIntVigenciaConvenio());
		setFormRetenPlla(chkRetencionPlla!=true);
		if(chkRetencionPlla==false){
			setLstModalidadTipoSocio(null);
			listaConvenioEstructuraDet = new ArrayList<ConvenioEstructuraDetalleComp>();
			beanAdenda.setBdRetencion(null);
		}
	}
	

	/**
	 * Nombre :  listarConvenios() 
	 * Parametros :  event       descripcion
	 * Objetivo: Realizar el listado de la Hoja de Planeamiento
	 * Retorno : Listado de Hoja de Planeamiento
	 */

	public void listarConvenios(ActionEvent event) {
		log.info("--------------------Debugging ConvenioController.listarConvenio------------------------");
		List<Juridica> listaJuridica = null;
		String csvPkPersona = null;
		Juridica juridica = null;
		List<ConvenioComp> lista = null;
		try {
			ConvenioComp o = new ConvenioComp();
			o.setAdenda(new Adenda());
			o.getAdenda().setId(new AdendaId());
			o.setEstructuraDetalle(new EstructuraDetalle());
			o.getEstructuraDetalle().setEstructura(new Estructura());
			o.getEstructuraDetalle().getEstructura().setJuridica(new Juridica());
			//Modificado por cdelosrios, 08/12/2013
			/*if(intTipoConvenio!=null && intTipoConvenio!=0)
				o.getAdenda().getId().setIntItemConvenio(intTipoConvenio);
			if(intEstadoConvenio!=null && intEstadoConvenio!=-1)
				o.getAdenda().setIntParaEstadoConvenioCod(intEstadoConvenio);
			if(chkRenovacion!=null)
				o.getAdenda().setIntRenovacion(chkRenovacion==true?1:0);
			
			o.setIntRetencionPlanilla(chkRetencionPlla==true?1:0);
			o.setIntDonacionRegalia(chkDonacionRegalia==true?1:0);
			o.setIntDocAdjunto(chkDocAdjunto==true?1:0);
			*/
			if(intIdConvenio!=null){
				o.getAdenda().getId().setIntConvenio(intIdConvenio);
			}
			if(intSucursalConv!=null && intSucursalConv!=0)
				o.getAdenda().setIntSeguSucursalPk(intSucursalConv);
			if(strEntidad!=null){
				o.getEstructuraDetalle().getEstructura().getJuridica().setStrRazonSocial(strEntidad);
			}
			//Fin modificado por cdelosrios, 08/12/2013
			if(intVigenciaConvenio!=null)
			o.setIntVigenciaConvenio(intVigenciaConvenio);
			
			listaConvenioComp = facadeHojaPlaneamiento.getListaConvenioCompDeBusqueda(o);
			//Agregado por cdelosrios, 05/12/2013
			if(strEntidad!=null && !strEntidad.trim().equals("")){
				if(listaConvenioComp!=null && !listaConvenioComp.isEmpty()){
					for (ConvenioComp convenioComp : listaConvenioComp) {
						if(csvPkPersona == null)
							csvPkPersona = String.valueOf(convenioComp.getEstructuraDetalle().getEstructura().getJuridica().getIntIdPersona()); 
						else	
							csvPkPersona = csvPkPersona + "," +convenioComp.getEstructuraDetalle().getEstructura().getJuridica().getIntIdPersona();
					}
					juridica = o.getEstructuraDetalle().getEstructura().getJuridica();
					if(juridica.getStrRazonSocial()!= null && juridica.getStrRazonSocial().trim().equals("")){
						listaJuridica = facadePersona.getListaJuridicaPorInPk(csvPkPersona);
					}else{
						listaJuridica = facadePersona.getListaJuridicaPorInPkLikeRazon(csvPkPersona,juridica.getStrRazonSocial());
					}
					if(listaJuridica != null && listaJuridica.size()>0){
						lista = new ArrayList<ConvenioComp>();
						for(Juridica jur : listaJuridica){
							for(ConvenioComp dto : listaConvenioComp){
								if(jur.getIntIdPersona().equals(dto.getEstructuraDetalle().getEstructura().getJuridica().getIntIdPersona())){
									dto.getEstructuraDetalle().getEstructura().setJuridica(jur);
									lista.add(dto);
								}
							}
						}
					}
					listaConvenioComp = lista;
				}
				//Fin agregado por cdelosrios, 05/12/2013
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		} 
	}
	
	public void listarConvenioDet(ActionEvent event){
		log.info("--------------------Debugging ConvenioController.listarConvenioDet------------------------");
		String strIdConvenio = getRequestParameter("intIdConvenio");
		String strItemConvenio = getRequestParameter("intItemConvenio");
		setHiddenConvenio(strIdConvenio);
		setHiddenItemConvenio(strItemConvenio);
		try {
			ConvenioComp o = new ConvenioComp();
			o.setAdenda(new Adenda());
			o.getAdenda().setId(new AdendaId());
			o.getAdenda().getId().setIntConvenio(new Integer(strIdConvenio));
			
			listaConvenioDetComp = facadeHojaPlaneamiento.getListaConvenioDet(o);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	public void listarHojaPlaneamiento(ActionEvent event) throws EJBFactoryException {
		log.info("--------------------Debugging ConvenioController.listarHojaPlaneamiento------------------------");
		try {
			ConvenioComp o = new ConvenioComp();
			o.setAdenda(new Adenda());
			o.getAdenda().setId(new AdendaId());
			o.getAdenda().getId().setIntItemConvenio(intRbTipoConvenio);
			
			listaHojaPlaneamientoComp = facadeHojaPlaneamiento.getListaConvenioPorTipoConvenio(o);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	public void getHojaPlaneamiento(ActionEvent event) {
		log.info("--------------------Debugging ConvenioController.getHojaPlaneamiento------------------------");
		String strIdConvenio = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmHojaPlaneamiento:intIdConvenio");
		String strIdItemConvenio = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmHojaPlaneamiento:intIdItemConvenio");
		Archivo archivo = null;
		try {
			//adenda = new Adenda();
			//adenda.setId(new AdendaId());
			beanAdenda.getId().setIntConvenio(new Integer(strIdConvenio));
			beanAdenda.getId().setIntItemConvenio(new Integer(strIdItemConvenio));
			
			listaConvenioEstructuraDet = facadeConvenio.getListConvenioEstructuraDetalle(beanAdenda.getId());
			if(listaConvenioEstructuraDet!=null && listaConvenioEstructuraDet.size()>0){
				chkRetencionPlla = true;
			}
			beanAdenda = facadeHojaPlaneamiento.getAdendaPorIdAdenda(beanAdenda.getId());
			log.info("beanAdenda.getId().getIntItemConvenio(): "+beanAdenda.getId().getIntItemConvenio());
			setIntItemConvenio(beanAdenda.getId().getIntItemConvenio());
			
			//CGD-18.12.2013
			long diff = 0;
			strPlazoConvenio = "";
			
			if(beanAdenda.getDtInicio()!= null){
				strDtFecInicio = Constante.sdf.format(beanAdenda.getDtInicio());
			}else{
				strDtFecInicio = "";
			}
			if(beanAdenda.getDtCese()!= null){
				strDtFecFin = Constante.sdf.format(beanAdenda.getDtCese());
				/*diff = beanAdenda.getDtCese().getTime() - beanAdenda.getDtInicio().getTime();
				xxxxxxxxxxxx
				strPlazoConvenio = ""+ (diff / (1000 * 60 * 60 * 24))+ " días";*/
				
				strPlazoConvenio = convertirAnnosMesesDias(beanAdenda.getDtCese(), beanAdenda.getDtInicio());
				log.error("strPlazoConveniostrPlazoConveniostrPlazoConveniostrPlazoConvenio-----------> "+strPlazoConvenio);
			}else{
				strDtFecFin = "";
			}
			if(beanAdenda.getDtSuscripcion()!= null){
				strDtFecSuscripcion = Constante.sdf.format(beanAdenda.getDtSuscripcion());
			}else{
				strDtFecSuscripcion = "";
			}
			if(beanAdenda.getBdRetencion()!= null){
				bdRetencion = beanAdenda.getBdRetencion();
			}else{
				bdRetencion = BigDecimal.ZERO;
			}

			intSucursalHojaPlan = beanAdenda.getIntSeguSucursalPk();
			intParaTipoRetencion = beanAdenda.getIntParaTipoRetencionCod();
			
			
			
			if(beanAdenda.getListaAdjunto()!=null && beanAdenda.getListaAdjunto().size()>0){
				for(Adjunto adjunto : beanAdenda.getListaAdjunto()){
					archivo = new Archivo();
					archivo.setId(new ArchivoId());
					if(adjunto.getIntParaTipoArchivoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CARTAPRESENTACION)){
						archivo.getId().setIntParaTipoCod(adjunto.getIntParaTipoArchivoCod());
						archivo.getId().setIntItemArchivo(adjunto.getIntItemArchivo());
						archivo.getId().setIntItemHistorico(adjunto.getIntParaItemHistorico());
						archivo = generalFacade.getArchivoPorPK(archivo.getId());
						setStrCartaPresentacion(archivo.getStrNombrearchivo());
						beanAdenda.setArchivoDocumentoCartaPresent(archivo);
					}
					if(adjunto.getIntParaTipoArchivoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CONVENIOSUGERIDO)){
						archivo.getId().setIntParaTipoCod(adjunto.getIntParaTipoArchivoCod());
						archivo.getId().setIntItemArchivo(adjunto.getIntItemArchivo());
						archivo.getId().setIntItemHistorico(adjunto.getIntParaItemHistorico());
						archivo = generalFacade.getArchivoPorPK(archivo.getId());
						setStrConvenioSugerido(archivo.getStrNombrearchivo());
						beanAdenda.setArchivoDocumentoConvSugerido(archivo);
					}
					if(adjunto.getIntParaTipoArchivoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_ADENDASUGERIDA)){
						archivo.getId().setIntParaTipoCod(adjunto.getIntParaTipoArchivoCod());
						archivo.getId().setIntItemArchivo(adjunto.getIntItemArchivo());
						archivo.getId().setIntItemHistorico(adjunto.getIntParaItemHistorico());
						archivo = generalFacade.getArchivoPorPK(archivo.getId());
						setStrAdendaSugerida(archivo.getStrNombrearchivo());
						beanAdenda.setArchivoDocumentoAdendaSugerida(archivo);
					}
				}
			}else{
				setStrCartaPresentacion("");
				setStrConvenioSugerido("");
				setStrAdendaSugerida("");
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	
	

	private String convertirAnnosMesesDias(Date dtCese, Date dtInicio){
		
		String salida ="";
		String salidaAnnos ="";
		String salidaMeses ="";
			
			 String fechaInicial = Constante.sdf2.format(dtInicio);      //"31-12-2008 09:45:00";
		     String fechaFinal   = Constante.sdf2.format(dtCese); 								   //"30-01-2009 12:27:00";

		        java.util.GregorianCalendar jCal = new java.util.GregorianCalendar();
		        java.util.GregorianCalendar jCal2 = new java.util.GregorianCalendar();
		        //jCal.set(year, month, date, hourOfDay, minute)
		        jCal.set(Integer.parseInt(fechaInicial.substring(6,10)), Integer.parseInt(fechaInicial.substring(3,5))-1, Integer.parseInt(fechaInicial.substring(0,2)), Integer.parseInt(fechaInicial.substring(11,13)),Integer.parseInt(fechaInicial.substring(14,16)), Integer.parseInt(fechaInicial.substring(17,19)));
		        jCal2.set(Integer.parseInt(fechaFinal.substring(6,10)), Integer.parseInt(fechaFinal.substring(3,5))-1, Integer.parseInt(fechaFinal.substring(0,2)), Integer.parseInt(fechaFinal.substring(11,13)),Integer.parseInt(fechaFinal.substring(14,16)), Integer.parseInt(fechaFinal.substring(17,19)));
		 
		    long diferencia = jCal2.getTime().getTime()-jCal.getTime().getTime();
		    //long diferencia = 20736*1000000;
		    System.out.println("diferencia diferencia diferencia ---> "+diferencia);    
	        double minutos = diferencia / (1000 * 60);
	        long horas = (long) (minutos / 60);
	        //long minuto = (long) (minutos%60);
	        //long segundos = (long) diferencia % 1000;
	        long dias = horas/24;
	        
	        /////////////////////////////////////////////////////////////////
	        BigDecimal bdDias = new BigDecimal(dias);
	        BigDecimal bdResiduoDias = BigDecimal.ZERO;
	        BigDecimal bdMeses = BigDecimal.ZERO;
	        
	        
	        ////////////////////////////////////////////////////////////////
	        
	        long annos = dias/365;
	        BigDecimal bdAnnos = new BigDecimal(annos);
	        long XXXXannos = dias%365;
	        System.out.println("annosannosannos -----> "+annos);
	        System.out.println("XXXXannosXXXXannosXXXXannos -----> "+XXXXannos);
	        
	        if(annos > 0){
	        	// existen años
	        	salidaAnnos = annos + " año(s)";
	        	
	        	// diferencia de dias 
	        	MathContext mc1 = new MathContext(2, RoundingMode.HALF_UP);  
	        	BigDecimal bdDiasAvanzados = bdAnnos.multiply(new BigDecimal(365), mc1);
	        	bdResiduoDias = bdDias.subtract(bdDiasAvanzados);
	        	if(bdResiduoDias.compareTo(BigDecimal.ZERO)>0){
	        		BigDecimal bdDiasMes = new BigDecimal(30);
	        		bdMeses = bdResiduoDias.divide(bdDiasMes,2,RoundingMode.HALF_UP);
	        		bdMeses=  bdMeses.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
	        		salidaMeses = bdMeses + " mes(es)";
	        	}else{
	        		salidaMeses = "";
	        	}
	        	
	        }else{
	        	bdMeses = bdDias.divide(new BigDecimal(30),2,RoundingMode.HALF_UP);
        		bdMeses=  bdMeses.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
        		salidaMeses = bdMeses + " mes(es)";
	        }
	        /*if(annos >0){
	        	//long annoMil = 315569; //00000;
	        	salidaAnnos = annos + " año(s) ";
	        	long anoscorreidos = annos *(365*24*60*60*1000);
	        	
	        	//se le resta los años al totald e diferencia
	        	long residuoDeAnnos = 0;
	        		 residuoDeAnnos = diferencia - anoscorreidos;
	        		 System.out.println("diferenciadiferencia -----> "+diferencia);
	        		 System.out.println("anoscorreidosanoscorreidosanoscorreidosanoscorreidos -----> "+anoscorreidos);
	        		 System.out.println("residuoDeAnnosresiduoDeAnnosresiduoDeAnnos -----> "+residuoDeAnnos);
	        	if(residuoDeAnnos>0){
		        	long xmeses = 	residuoDeAnnos/(10000*60*60*24*30) ;
		        	System.out.println("xmesesxmesesxmesesxmeses -----> "+xmeses);
		        	Integer intMeses = Math.abs((int)xmeses);
		        	System.out.println("intMesesintMesesintMesesintMeses -----> "+intMeses);
		        	
		        	if(intMeses > 0){
		        		salidaMeses = intMeses + " mes(es) ";
		        	}else{
		        		salidaMeses = "";
		        	}
	
	        	}else{
	        		
	        	}
	        }else{
	        	salidaAnnos = "";
	        	long xmeses = 	diferencia/(1000*60*60*24*30) ;
	        	Integer intMeses = Math.abs((int)xmeses);
	        	
	        	if(intMeses > 0){
	        		salidaMeses = intMeses + " mes(es) ";
	        	}else{
	        		salidaMeses = "";
	        	}
	        	
	        }*/
	        
	        salida = salidaAnnos + salidaMeses;
	        System.out.println("xxxxxxxxxxxxxxxxxxxx   "+beanAdenda.getIntOpcionFiltroCredito() );
	        return salida;
	        }
	
	
	
	
	public String recuperarCadenaDiferenciaFechas(Date dtCese, Date dtInicio){
		String strCadena = "";
		 {
		        // Crear 2 instancias de Calendar
		        Calendar cal1 = Calendar.getInstance();

		        Calendar cal2 = Calendar.getInstance();
		        // Establecer las fechas
		        cal1.set(2006, 12, 30);
		        cal2.set(2007, 5, 3);
		        // conseguir la representacion de la fecha en milisegundos
		        long milis1 = cal1.getTimeInMillis();
		        long milis2 = cal2.getTimeInMillis();
		        
		        // diferencia en ms
		        long diff = milis2 - milis1;
		        
		        // calcular la diferencia en dias
		        long diffDays = diff / (24 * 60 * 60 * 1000);
				
				// Calcluo de años
				long diffAnnos = diff / (24 * 60 * 60 * 1000 * 360);
				if(diffAnnos < 0){
					String strAnnos = "año(s)";
				}else{
					
				}

		        System.out.println("En milisegundos: " + diff + " milisegundos.");
		        //System.out.println("En segundos: " + diffSeconds + " segundos.");
		        //System.out.println("En minutos: " + diffMinutes + " minutos.");
		        //System.out.println("En horas: " + diffHours + " horas.");
		        System.out.println("En dias: " + diffDays + " dias.");

		        return "";
		    }
	}
				
				
				
	public void generarCronograma(ActionEvent event){
		List<RetencionPlanilla> listRetencPlla = null;
		RetencionPlanilla retencionPlanilla = null;
		Date dtToday = new Date();
		String strAnioFin = Constante.sdfAnio.format(beanAdenda.getDtCese()==null?dtToday:beanAdenda.getDtCese());
		String strAnioIni = Constante.sdfAnio.format(beanAdenda.getDtInicio());
		String strMesFin = Constante.sdfMes.format(beanAdenda.getDtCese()==null?dtToday:beanAdenda.getDtCese());
		String strMesIni = Constante.sdfMes.format(beanAdenda.getDtInicio());
		int diffAnios = new Integer(strAnioFin) - new Integer(strAnioIni);
		int mesInicial = new Integer(strMesIni);
		int mesFinal = new Integer(strMesFin);
		try{
			listaRetencPlla = new ArrayList<RetencionPlanilla>();
			listRetencPlla = new ArrayList<RetencionPlanilla>();
			if(lstModalidadTipoSocio!=null && lstModalidadTipoSocio.length>0){
				for(int k=0;k<lstModalidadTipoSocio.length;k++){
					for(ConvenioEstructuraDetalleComp convDetComp : listaConvenioEstructuraDet){
						if(lstModalidadTipoSocio[k].equals(convDetComp.getEstructuraDetalle().getIntParaModalidadCod()+","+convDetComp.getEstructuraDetalle().getIntParaTipoSocioCod())){
							for(int i=0; i<=diffAnios; i++){
								for(int j=mesInicial;j<=12;j++){
									if(diffAnios>=1 && mesInicial==1){
										if(i==diffAnios && j==mesFinal ){
											retencionPlanilla = new RetencionPlanilla();
											retencionPlanilla.setId(new RetencionPlanillaId());
											retencionPlanilla.setIntNivel(listaConvenioEstructuraDet.get(0).getEstructura().getId().getIntNivel());
											retencionPlanilla.setIntCodigo(listaConvenioEstructuraDet.get(0).getEstructura().getId().getIntCodigo());
											retencionPlanilla.setIntParaMesCod(j);
											retencionPlanilla.setIntAnio(new Integer(strAnioIni)+i);
											retencionPlanilla.setIntParaModalidadCod(convDetComp.getEstructuraDetalle().getIntParaModalidadCod());
											retencionPlanilla.setIntParaTipoSocioCod(convDetComp.getEstructuraDetalle().getIntParaTipoSocioCod());
											retencionPlanilla.setIntParaEstPagoCod(Constante.PARAM_T_ESTADOSOLICPAGO_PENDIENTE);
											listRetencPlla.add(retencionPlanilla);
											setListaRetencPlla(listRetencPlla);
											break;
										}
									}
									retencionPlanilla = new RetencionPlanilla();
									retencionPlanilla.setId(new RetencionPlanillaId());
									retencionPlanilla.setIntNivel(listaConvenioEstructuraDet.get(0).getEstructura().getId().getIntNivel());
									retencionPlanilla.setIntCodigo(listaConvenioEstructuraDet.get(0).getEstructura().getId().getIntCodigo());
									retencionPlanilla.setIntParaMesCod(j);
									retencionPlanilla.setIntAnio(new Integer(strAnioIni)+i);
									retencionPlanilla.setIntParaModalidadCod(convDetComp.getEstructuraDetalle().getIntParaModalidadCod());
									retencionPlanilla.setIntParaTipoSocioCod(convDetComp.getEstructuraDetalle().getIntParaTipoSocioCod());
									retencionPlanilla.setIntParaEstPagoCod(Constante.PARAM_T_ESTADOSOLICPAGO_PENDIENTE);
									listRetencPlla.add(retencionPlanilla);
									//setListaRetencPlla(listRetencPlla);
								}
								mesInicial = 1;
							}
						}
					}
				}
			
			}
		}catch(Exception e){
			log.error("error: " + e);
		}
	}
	
	public void addDonacionRegalia(ActionEvent event){
		log.info("-------------Entrando a ConvenioController.addDonacionRegalia-------------");
		DonacionRegalia donacionRegalia = null;
		try{
			donacionRegalia = new DonacionRegalia();
			donacionRegalia.setId(new DonacionRegaliaId());
			listaDonacionRegalia.add(donacionRegalia);
		}catch(Exception e){
			log.error("error: " + e);
		}
	}
	
	public void removeDonacionRegalia(ActionEvent event){
		String rowKey = getRequestParameter("rowKeyDonacionRegalia");
		DonacionRegalia donacionRegaliaTmp = null;
		if(listaDonacionRegalia!=null){
			for(int i=0; i<listaDonacionRegalia.size(); i++){
				if(Integer.parseInt(rowKey)==i){
					DonacionRegalia donacionRegalia = listaDonacionRegalia.get(i);
					if(donacionRegalia.getId()!=null && donacionRegalia.getId().getIntItemDonacion()!=null){
						donacionRegaliaTmp = listaDonacionRegalia.get(i);
						donacionRegaliaTmp.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					}
					listaDonacionRegalia.remove(i);
					break;
				}
			}
			if(donacionRegaliaTmp!=null){
				listaDonacionRegalia.add(donacionRegaliaTmp);
			}
		}
	}
	
	public void addFirmante(ActionEvent event) throws EJBFactoryException{
		List<Persona> listaRepresentanteLegalEmpresa = null;
		List<Persona> listaRepresentanteLegalTumi = null;
		FirmanteComp firmanteComp = null;
		List<FirmanteComp> listaFirmanteComp = new ArrayList<FirmanteComp>();
		try {
			log.info("listaConvenioEstructuraDet.get(0).getEstructura().getIntPersPersonaPk(): "+ listaConvenioEstructuraDet.get(0).getEstructura().getIntPersPersonaPk());
			listaRepresentanteLegalEmpresa = facadeConvenio.getListaRepLegalPorIdPerNaturalYIdPerJuridica(listaConvenioEstructuraDet.get(0).getEstructura().getIntPersPersonaPk(), Constante.PARAM_T_TIPOVINCULO_REPRESENTANTELEGAL, sesionIntIdEmpresa);
			if(listaRepresentanteLegalEmpresa!=null && listaRepresentanteLegalEmpresa.size()>0){
				for(Persona persona : listaRepresentanteLegalEmpresa){
					firmanteComp = new FirmanteComp();
					firmanteComp.setFirmante(new Firmante());
					firmanteComp.getFirmante().setPersona(persona);
					listaFirmanteComp.add(firmanteComp);
				}
			}
			listaRepresentanteLegalTumi = facadeConvenio.getListaRepLegalPorIdPerNaturalYIdPerJuridica(sesionIntIdEmpresa, Constante.PARAM_T_TIPOVINCULO_REPRESENTANTELEGAL, sesionIntIdEmpresa);
			if(listaRepresentanteLegalTumi!= null){
				for(Persona persona : listaRepresentanteLegalTumi){
					firmanteComp = new FirmanteComp();
					firmanteComp.setFirmante(new Firmante());
					firmanteComp.getFirmante().setPersona(persona);
					listaFirmanteComp.add(firmanteComp);
				}
			}
			setListaRepresentanteLegal(listaFirmanteComp);
		} catch (BusinessException e) {
			log.error("error: " + e);
		}
	}
	
	public void getFirmantes(ActionEvent event){
		//listaFirmantes = new ArrayList<Firmante>();
		try{
			if(listaRepresentanteLegal!=null){
				for(FirmanteComp firmante : listaRepresentanteLegal){
					if(firmante.getChkFirmante()==true){
						listaFirmantes.add(firmante.getFirmante());
					}
				}
			}
		}catch(Exception e){
			log.error("error: "+e);
		}
	}
	
	public void removeFirmante(ActionEvent event){
		String rowKey = getRequestParameter("rowKeyFirmante");
		Firmante firmanteTmp = null;
		if(listaFirmantes!=null){
			for(int i=0; i<listaFirmantes.size(); i++){
				if(Integer.parseInt(rowKey)==i){
					Firmante firmante = listaFirmantes.get(i);
					if(firmante.getId() !=null && firmante.getId().getIntItemAdendaFirmante()!=null){
						firmanteTmp = listaFirmantes.get(i);
						firmanteTmp.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					}
					listaFirmantes.remove(i);
					break;
				}
			}
			if(firmanteTmp!=null){
				listaFirmantes.add(firmanteTmp);
			}
		}
	}
	
	public void listarCaptacion(ActionEvent event){
		String idBtnCaptacion = event.getComponent().getId();
		log.info("idBtnCaptacion: " + idBtnCaptacion);
		try {
			Captacion o = new Captacion();
			o.setId(new CaptacionId());
			o.setCondicion(new Condicion());
			o.getCondicion().setId(new CondicionId());
			o.getId().setIntPersEmpresaPk(sesionIntIdEmpresa);
			if(idBtnCaptacion.equals("btnAportacion"))
				o.getId().setIntParaTipoCaptacionCod(Constante.CAPTACION_APORTACIONES);
			else if(idBtnCaptacion.equals("btnFondoSepelio"))
				o.getId().setIntParaTipoCaptacionCod(Constante.CAPTACION_FDO_SEPELIO);
			else if(idBtnCaptacion.equals("btnFondoRetiro"))
				o.getId().setIntParaTipoCaptacionCod(Constante.CAPTACION_FDO_RETIRO);
			else if(idBtnCaptacion.equals("btnMantCuenta"))
				o.getId().setIntParaTipoCaptacionCod(Constante.CAPTACION_MANT_CUENTA);
			o.setIntVigencia(1);
			o.setIntAportacionVigente(1);
			if(idBtnCaptacion.equals("btnAportacion"))
				listaAportacionComp = facadeCaptacion.getListaCaptacionCompDeBusquedaCaptacion(o);
			else if(idBtnCaptacion.equals("btnFondoSepelio"))
				listaFondoSepelioComp = facadeCaptacion.getListaCaptacionCompDeBusquedaCaptacion(o);
			else if(idBtnCaptacion.equals("btnFondoRetiro"))
				listaFondoRetiroComp = facadeCaptacion.getListaCaptacionCompDeBusquedaCaptacion(o);
			else if(idBtnCaptacion.equals("btnMantCuenta"))
				listaMantCtaComp = facadeCaptacion.getListaCaptacionCompDeBusquedaCaptacion(o);
		}catch (BusinessException e) {
			log.error("error: " + e);
		}
	}
	
	public void getListaAportaciones(ActionEvent event){
		log.info("-------Debugging ConvenioController.getListaAportaciones-------");
		//listaAportacion = new ArrayList<Captacion>();
		AdendaCaptacion adendaCaptacion = null;
		try{
			if(listaAportacionComp!=null){
				for(CaptacionComp captacionComp : listaAportacionComp){
					if(captacionComp.getChkCaptacion()==true){
						adendaCaptacion = new AdendaCaptacion();
						adendaCaptacion.setCaptacion(captacionComp.getCaptacion());
						listaAdendaAportacion.add(adendaCaptacion);
					}
				}
			}
		}catch(Exception e){
			log.error("error: "+e);
		}
	}
	
	public void removeAportacion(ActionEvent event){
		String rowKey = getRequestParameter("rowKeyAportacion");
		AdendaCaptacion captacionTmp = null;
		if(listaAdendaAportacion!=null){
			for(int i=0; i<listaAdendaAportacion.size(); i++){
				if(Integer.parseInt(rowKey)==i){
					AdendaCaptacion captacion = listaAdendaAportacion.get(i);
					if(captacion.getId()!=null && captacion.getId().getIntItem()!=null){
						captacionTmp = listaAdendaAportacion.get(i);
						captacionTmp.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					}
					listaAdendaAportacion.remove(i);
					break;
				}
			}
			if(captacionTmp!=null){
				listaAdendaAportacion.add(captacionTmp);
			}
		}
	}
	
	public void getListaFondoSepelio(ActionEvent event){
		//listaFondoSepelio = new ArrayList<Captacion>();
		AdendaCaptacion adendaCaptacion = null;
		try{
			if(listaFondoSepelioComp!=null){
				for(CaptacionComp captacionComp : listaFondoSepelioComp){
					if(captacionComp.getChkCaptacion()==true){
						adendaCaptacion = new AdendaCaptacion();
						adendaCaptacion.setCaptacion(captacionComp.getCaptacion());
						listaAdendaFondoSepelio.add(adendaCaptacion);
					}
				}
			}
		}catch(Exception e){
			log.error("error: "+e);
		}
	}
	
	public void removeFondoSepelio(ActionEvent event){
		String rowKey = getRequestParameter("rowKeyFondoSepelio");
		AdendaCaptacion captacionTmp = null;
		if(listaAdendaFondoSepelio!=null){
			for(int i=0; i<listaAdendaFondoSepelio.size(); i++){
				if(Integer.parseInt(rowKey)==i){
					AdendaCaptacion captacion = listaAdendaFondoSepelio.get(i);
					if(captacion.getId()!=null && captacion.getId().getIntItem()!=null){
						captacionTmp = listaAdendaFondoSepelio.get(i);
						captacionTmp.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					}
					listaAdendaFondoSepelio.remove(i);
					break;
				}
			}
			if(captacionTmp!=null){
				listaAdendaFondoSepelio.add(captacionTmp);
			}
		}
	}
	
	public void getListaFondoRetiro(ActionEvent event){
		//listaFondoRetiro = new ArrayList<Captacion>();
		AdendaCaptacion adendaCaptacion = null;
		try{
			if(listaFondoRetiroComp!=null){
				for(CaptacionComp captacionComp : listaFondoRetiroComp){
					if(captacionComp.getChkCaptacion()==true){
						adendaCaptacion = new AdendaCaptacion();
						adendaCaptacion.setCaptacion(captacionComp.getCaptacion());
						listaAdendaFondoRetiro.add(adendaCaptacion);
					}
				}
			}
		}catch(Exception e){
			log.error("error: "+e);
		}
	}
	
	public void removeFondoRetiro(ActionEvent event){
		String rowKey = getRequestParameter("rowKeyFondoRetiro");
		AdendaCaptacion captacionTmp = null;
		if(listaAdendaFondoRetiro!=null){
			for(int i=0; i<listaAdendaFondoRetiro.size(); i++){
				if(Integer.parseInt(rowKey)==i){
					AdendaCaptacion captacion = listaAdendaFondoRetiro.get(i);
					if(captacion.getId()!=null && captacion.getId().getIntItem()!=null){
						captacionTmp = listaAdendaFondoRetiro.get(i);
						captacionTmp.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					}
					listaAdendaFondoRetiro.remove(i);
					break;
				}
			}
			if(captacionTmp!=null){
				listaAdendaFondoRetiro.add(captacionTmp);
			}
		}
	}
	
	public void getListaMantCuenta(ActionEvent event){
		//listaMantCta = new ArrayList<Captacion>();
		AdendaCaptacion adendaCaptacion = null;
		try{
			if(listaMantCtaComp!=null){
				for(CaptacionComp captacionComp : listaMantCtaComp){
					if(captacionComp.getChkCaptacion()==true){
						adendaCaptacion = new AdendaCaptacion();
						adendaCaptacion.setCaptacion(captacionComp.getCaptacion());
						listaAdendaMantCta.add(adendaCaptacion);
					}
				}
			}
		}catch(Exception e){
			log.error("error: "+e);
		}
	}
	
	public void removeMantCuenta(ActionEvent event){
		String rowKey = getRequestParameter("rowKeyMantCuenta");
		AdendaCaptacion captacionTmp = null;
		if(listaAdendaMantCta!=null){
			for(int i=0; i<listaAdendaMantCta.size(); i++){
				if(Integer.parseInt(rowKey)==i){
					AdendaCaptacion captacion = listaAdendaMantCta.get(i);
					if(captacion.getId()!=null && captacion.getId().getIntItem()!=null){
						captacionTmp = listaAdendaMantCta.get(i);
						captacionTmp.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					}
					listaAdendaMantCta.remove(i);
					break;
				}
			}
			if(captacionTmp!=null){
				listaAdendaMantCta.add(captacionTmp);
			}
		}
	}
	
	public void listarCreditosPorTipoCredito(ActionEvent event){
		try {
			Credito o = new Credito();
			o.setId(new CreditoId());
			o.setCondicionCredito(new CondicionCredito());
			o.getCondicionCredito().setId(new CondicionCreditoId());
			o.getId().setIntPersEmpresaPk(sesionIntIdEmpresa);
			o.getId().setIntParaTipoCreditoCod(intParaTipoCreditoCod);
			o.setIntActivo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			o.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			o.setIntParaEstadoSolicitudCod(Constante.PARAM_T_ESTADODOCUMENTO_PENDIENTE);
			listaCreditoComp = facadeCredito.getListaCreditoCompDeBusquedaCredito(o);
		}catch (BusinessException e) {
			log.error("error: " + e);
		}
	}
	
	public void getListaCreditos(ActionEvent event){
		//listaCredito = new ArrayList<Credito>();
		//listaAdendaCredito = new ArrayList<AdendaCredito>();
		AdendaCredito adendaCredito = null;
		try{
			if(listaCreditoComp!=null){
				for(CreditoComp creditoComp : listaCreditoComp){
					if(creditoComp.getChkCredito()==true){
						adendaCredito = new AdendaCredito();
						adendaCredito.setCredito(creditoComp.getCredito());
						listaAdendaCredito.add(adendaCredito);
						//listaCredito.add(creditoComp.getCredito());
					}
				}
			}
		}catch(Exception e){
			log.error("error: "+e);
		}
	}
	
	public void removeCredito(ActionEvent event){
		String rowKey = getRequestParameter("rowKeyCredito");
		AdendaCredito creditoTmp = null;
		if(listaAdendaCredito!=null){
			for(int i=0; i<listaAdendaCredito.size(); i++){
				if(Integer.parseInt(rowKey)==i){
					AdendaCredito credito = listaAdendaCredito.get(i);
					if(credito.getId()!=null && credito.getId().getIntItemCredito()!=null){
						creditoTmp = listaAdendaCredito.get(i);
						creditoTmp.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					}
					listaAdendaCredito.remove(i);
					break;
				}
			}
			if(creditoTmp!=null){
				listaAdendaCredito.add(creditoTmp);
			}
		}
	}
	
	private Boolean isValidoConvenio(Adenda beanAdenda){
		Boolean validAdenda = true;
		if(chkRetencionPlla==true){
			if(beanAdenda.getBdRetencion()==null || beanAdenda.getBdRetencion().compareTo(BigDecimal.ZERO)==0){
				validAdenda = false;
				setMsgTxtRetencionPlla("Debe ingresar una cantidad de la Retención de Planilla.");
			}else{
				setMsgTxtRetencionPlla("");
			}
		}
		if(beanAdenda.getListaFirmante()!=null && beanAdenda.getListaFirmante().size()==0){
			validAdenda = false;
			setMsgListaFirmantes("Se debe ingresar por lo menos un Firmante");
		}else{
			setMsgListaFirmantes("");
		}
		
	    return validAdenda;
	}
	
	public void grabarConvenio(ActionEvent event){
		if(isValidoConvenio(beanAdenda) == false){
	    	log.info("Datos de Convenio no válidos. Se aborta el proceso de grabación de Convenio.");
	    	return;
	    }
		
		beanAdenda.setIntRenovacion(chkRenovacionAutomatica==true?1:0);
		beanAdenda.getId().setIntItemConvenio(intItemConvenio);
		
		if(listaRetencPlla!=null && listaRetencPlla.size()>0){
			beanAdenda.setListaRetencionPlla(listaRetencPlla);
		}
		if(listaDonacionRegalia!=null && listaDonacionRegalia.size()>0){
			for(DonacionRegalia donacion : listaDonacionRegalia){
				donacion.setIntPersonaRegistraPk(usuario.getIntPersPersonaPk());
				donacion.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				donacion.setTsFechaRegistra(new Timestamp(new Date().getTime()));
				beanAdenda.setListaDonacion(listaDonacionRegalia);
			}
		}
		if(listaFirmantes!=null && listaFirmantes.size()>0){
			for(Firmante firmante : listaFirmantes){
				firmante.setIntPersEmpresaPk(listaConvenioEstructuraDet.get(0).getEstructura().getIntPersEmpresaPk());
				firmante.setIntPersPersonaEntidadPk(listaConvenioEstructuraDet.get(0).getEstructura().getIntPersPersonaPk());
				firmante.setIntPersPersonaFirmantePk(firmante.getPersona().getIntIdPersona());
				firmante.setIntPersonaRegistraPk(usuario.getIntPersPersonaPk());
				firmante.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				firmante.setTsFechaRegistra(new Timestamp(new Date().getTime()));
				beanAdenda.setListaFirmante(listaFirmantes);
			}
		}
		
		if(listaAdendaAportacion!=null && listaAdendaAportacion.size()>0){
			for(AdendaCaptacion aportacion : listaAdendaAportacion){
				aportacion.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				aportacion.setIntPersPersonaRegistraPk(usuario.getIntPersPersonaPk());
			}
			beanAdenda.setListaAdendaCaptacionAportacion(listaAdendaAportacion);
		}
		if(listaAdendaFondoSepelio!=null && listaAdendaFondoSepelio.size()>0){
			for(AdendaCaptacion fondoSepelio : listaAdendaFondoSepelio){
				fondoSepelio.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				fondoSepelio.setIntPersPersonaRegistraPk(usuario.getIntPersPersonaPk());
			}
			beanAdenda.setListaAdendaCaptacionFondoSepelio(listaAdendaFondoSepelio);
		}
		if(listaAdendaFondoRetiro!=null && listaAdendaFondoRetiro.size()>0){
			for(AdendaCaptacion fondoRetiro : listaAdendaFondoRetiro){
				fondoRetiro.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				fondoRetiro.setIntPersPersonaRegistraPk(usuario.getIntPersPersonaPk());
			}
			beanAdenda.setListaAdendaCaptacionFondoRetiro(listaAdendaFondoRetiro);
		}
		if(listaAdendaMantCta!=null && listaAdendaMantCta.size()>0){
			for(AdendaCaptacion mantCuenta : listaAdendaMantCta){
				mantCuenta.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				mantCuenta.setIntPersPersonaRegistraPk(usuario.getIntPersPersonaPk());
				//listaCaptacionMantCuenta.add(adendaCaptacion);
			}
			beanAdenda.setListaAdendaCaptacionMantCuenta(listaAdendaMantCta);
		}
		if(listaAdendaCredito!=null && listaAdendaCredito.size()>0){
			for(AdendaCredito adendaCredito : listaAdendaCredito){
				//adendaCredito = new AdendaCredito();
				adendaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				adendaCredito.setIntPersPersonaRegistraPk(usuario.getIntPersPersonaPk());
				adendaCredito.setTsFechaRegistra(new Timestamp(new Date().getTime()));
			}
			beanAdenda.setListaAdendaCreditos(listaAdendaCredito);
		}
		
		try {
			facadeConvenio = (ConvenioFacadeLocal)EJBFactory.getLocal(ConvenioFacadeLocal.class);
			facadeConvenio.grabarConvenio(beanAdenda);
			limpiarConvenio();
			listarConvenios(event);
			formConvenioRendered = false;
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	public void irModificarConvenio(ActionEvent event){
		log.info("getHiddenConvenio(): " + getHiddenConvenio());
		log.info("getHiddenItemConvenio(): " + getHiddenItemConvenio());
		AdendaId adendaId = new AdendaId();
		adendaId.setIntConvenio(new Integer(getHiddenConvenio()));
		adendaId.setIntItemConvenio(new Integer(getHiddenItemConvenio()));
		Captacion captacion = null;
		Credito credito = null;
		/*List<Captacion> listaCaptacionAportacion = null;
		List<Captacion> listaCaptacionFondoSepelio = null;
		List<Captacion> listaCaptacionFondoRetiro = null;
		List<Captacion> listaCaptacionMantCuenta = null;*/
		Archivo archivo = null;
		try {
    		//if(adendaId.getIntConvenio() != null && !adendaId.getIntConvenio().equals("")){
				beanAdenda   = facadeConvenio.getConvenioPorIdConvenio(adendaId);
				setIntItemConvenio(beanAdenda.getId().getIntItemConvenio());
				intRbTipoConvenio = beanAdenda.getId().getIntItemConvenio().equals(0)?1:2;
				setChkRenovacionAutomatica(beanAdenda.getIntRenovacion()!=null && beanAdenda.getIntRenovacion()==1);
				
				listaConvenioEstructuraDet = facadeConvenio.getListConvenioEstructuraDetalle(beanAdenda.getId());
				strDtFecInicio = Constante.sdf.format(beanAdenda.getDtInicio());
				strDtFecFin = (beanAdenda.getDtCese()==null?"":Constante.sdf.format(beanAdenda.getDtCese()));
				strDtFecSuscripcion = Constante.sdf.format(beanAdenda.getDtSuscripcion());
				intSucursalHojaPlan = beanAdenda.getIntSeguSucursalPk();
				if(beanAdenda.getDtCese()!=null){
					long diff = beanAdenda.getDtCese().getTime() - beanAdenda.getDtInicio().getTime();
					strPlazoConvenio = ""+ (diff / (1000 * 60 * 60 * 24))+ " días";
					bdRetencion = beanAdenda.getBdRetencion();
					intParaTipoRetencion = beanAdenda.getIntParaTipoRetencionCod();
					
						strDtFecFin = Constante.sdf.format(beanAdenda.getDtCese());
						/*diff = beanAdenda.getDtCese().getTime() - beanAdenda.getDtInicio().getTime();
						xxxxxxxxxxxx
						strPlazoConvenio = ""+ (diff / (1000 * 60 * 60 * 24))+ " días";*/
						
						strPlazoConvenio = convertirAnnosMesesDias(beanAdenda.getDtCese(), beanAdenda.getDtInicio());
						log.error("strPlazoConveniostrPlazoConveniostrPlazoConveniostrPlazoConvenio-----------> "+strPlazoConvenio);
					
					
				}
				
				if(beanAdenda.getListaAdjunto()!=null && beanAdenda.getListaAdjunto().size()>0){
					for(Adjunto adjunto : beanAdenda.getListaAdjunto()){
						archivo = new Archivo();
						archivo.setId(new ArchivoId());
						if(adjunto.getIntParaTipoArchivoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CARTAPRESENTACION)){
							archivo.getId().setIntParaTipoCod(adjunto.getIntParaTipoArchivoCod());
							archivo.getId().setIntItemArchivo(adjunto.getIntItemArchivo());
							archivo.getId().setIntItemHistorico(adjunto.getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							setStrCartaPresentacion(archivo.getStrNombrearchivo());
							beanAdenda.setArchivoDocumentoCartaPresent(archivo);
						}
						if(adjunto.getIntParaTipoArchivoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CONVENIOSUGERIDO)){
							archivo.getId().setIntParaTipoCod(adjunto.getIntParaTipoArchivoCod());
							archivo.getId().setIntItemArchivo(adjunto.getIntItemArchivo());
							archivo.getId().setIntItemHistorico(adjunto.getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							setStrConvenioSugerido(archivo.getStrNombrearchivo());
							beanAdenda.setArchivoDocumentoConvSugerido(archivo);
						}
						if(adjunto.getIntParaTipoArchivoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_ADENDASUGERIDA)){
							archivo.getId().setIntParaTipoCod(adjunto.getIntParaTipoArchivoCod());
							archivo.getId().setIntItemArchivo(adjunto.getIntItemArchivo());
							archivo.getId().setIntItemHistorico(adjunto.getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							setStrAdendaSugerida(archivo.getStrNombrearchivo());
							beanAdenda.setArchivoDocumentoAdendaSugerida(archivo);
						}
					}
				}else{
					setStrCartaPresentacion("");
					setStrConvenioSugerido("");
					setStrAdendaSugerida("");
				}
				
				if(beanAdenda.getListaRetencionPlla()!=null && beanAdenda.getListaRetencionPlla().size()>0){
					listaRetencPlla = beanAdenda.getListaRetencionPlla();
				}
				if(beanAdenda.getListaDonacion()!=null && beanAdenda.getListaDonacion().size()>0){
					listaDonacionRegalia = beanAdenda.getListaDonacion();
				}
				
				if(beanAdenda.getListaFirmante()!= null && beanAdenda.getListaFirmante().size()>0){
					listaFirmantes = beanAdenda.getListaFirmante();
				}
				
				if(beanAdenda.getListaAdendaCaptacionAportacion()!=null && beanAdenda.getListaAdendaCaptacionAportacion().size()>0){
					listaAdendaAportacion = beanAdenda.getListaAdendaCaptacionAportacion();
					for(AdendaCaptacion aportacion :  beanAdenda.getListaAdendaCaptacionAportacion()){
						captacion = new Captacion();
						captacion.setId(new CaptacionId());
						captacion.getId().setIntPersEmpresaPk(aportacion.getId().getIntPersEmpresaPk());
						captacion.getId().setIntParaTipoCaptacionCod(aportacion.getId().getIntParaTipoCaptacionCod());
						captacion.getId().setIntItem(aportacion.getId().getIntItem());
						captacion = facadeCaptacion.getCaptacionPorIdCaptacion(captacion.getId());
						aportacion.setCaptacion(captacion);
					}
				}
				
				if(beanAdenda.getListaAdendaCaptacionFondoSepelio()!=null && beanAdenda.getListaAdendaCaptacionFondoSepelio().size()>0){
					listaAdendaFondoSepelio = beanAdenda.getListaAdendaCaptacionFondoSepelio();
					for(AdendaCaptacion fondoSepelio :  beanAdenda.getListaAdendaCaptacionFondoSepelio()){
						captacion = new Captacion();
						captacion.setId(new CaptacionId());
						captacion.getId().setIntPersEmpresaPk(fondoSepelio.getId().getIntPersEmpresaPk());
						captacion.getId().setIntParaTipoCaptacionCod(fondoSepelio.getId().getIntParaTipoCaptacionCod());
						captacion.getId().setIntItem(fondoSepelio.getId().getIntItem());
						captacion = facadeCaptacion.getCaptacionPorIdCaptacion(captacion.getId());
						fondoSepelio.setCaptacion(captacion);
					}
				}
				
				if(beanAdenda.getListaAdendaCaptacionFondoRetiro()!=null && beanAdenda.getListaAdendaCaptacionFondoRetiro().size()>0){
					listaAdendaFondoRetiro = beanAdenda.getListaAdendaCaptacionFondoRetiro();
					for(AdendaCaptacion fondoRetiro :  beanAdenda.getListaAdendaCaptacionFondoRetiro()){
						captacion = new Captacion();
						captacion.setId(new CaptacionId());
						captacion.getId().setIntPersEmpresaPk(fondoRetiro.getId().getIntPersEmpresaPk());
						captacion.getId().setIntParaTipoCaptacionCod(fondoRetiro.getId().getIntParaTipoCaptacionCod());
						captacion.getId().setIntItem(fondoRetiro.getId().getIntItem());
						captacion = facadeCaptacion.getCaptacionPorIdCaptacion(captacion.getId());
						fondoRetiro.setCaptacion(captacion);
					}
				}
				
				if(beanAdenda.getListaAdendaCaptacionMantCuenta()!=null && beanAdenda.getListaAdendaCaptacionMantCuenta().size()>0){
					listaAdendaMantCta = beanAdenda.getListaAdendaCaptacionMantCuenta();
					for(AdendaCaptacion mantCuenta :  beanAdenda.getListaAdendaCaptacionMantCuenta()){
						captacion = new Captacion();
						captacion.setId(new CaptacionId());
						captacion.getId().setIntPersEmpresaPk(mantCuenta.getId().getIntPersEmpresaPk());
						captacion.getId().setIntParaTipoCaptacionCod(mantCuenta.getId().getIntParaTipoCaptacionCod());
						captacion.getId().setIntItem(mantCuenta.getId().getIntItem());
						captacion = facadeCaptacion.getCaptacionPorIdCaptacion(captacion.getId());
						mantCuenta.setCaptacion(captacion);
					}
				}
				
				if(beanAdenda.getListaAdendaCreditos()!=null && beanAdenda.getListaAdendaCreditos().size()>0){
					listaAdendaCredito = beanAdenda.getListaAdendaCreditos();
					for(AdendaCredito adendaCredito : beanAdenda.getListaAdendaCreditos()){
						credito = new Credito();
						credito.setId(new CreditoId());
						credito.getId().setIntPersEmpresaPk(adendaCredito.getId().getIntPersEmpresaPk());
						credito.getId().setIntParaTipoCreditoCod(adendaCredito.getId().getIntParaTipoCreditoCod());
						credito.getId().setIntItemCredito(adendaCredito.getId().getIntItemCredito());
						credito = facadeCredito.getCreditoPorIdCredito(credito.getId());
						adendaCredito.setCredito(credito);
					}
				}
				
				setBtnBuscarEnabled(beanAdenda.getId().getIntItemConvenio()==null);
				
				setStrConvenio(beanAdenda.getIntParaEstadoConvenioCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO) ? Constante.MANTENIMIENTO_ELIMINAR:Constante.MANTENIMIENTO_MODIFICAR);
				setFormTipoConvEnabled(true);
				setFormConvenioRendered(true);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	public void modificarConvenio(ActionEvent event){
		log.info("----------------ConvenioController.modificarConvenio----------------");
		AdendaCaptacion adendaCaptacion = null;
		List<AdendaCaptacion> listaCaptacionMantCuenta = new ArrayList<AdendaCaptacion>();
		if(isValidoConvenio(beanAdenda) == false){
	    	log.info("Datos de Convenio no válidos. Se aborta el proceso de grabación de Convenio.");
	    	return;
	    }
		beanAdenda.setIntRenovacion(chkRenovacionAutomatica==true?1:0);
		
		beanAdenda.getId().setIntItemConvenio(intItemConvenio);
		
		if(listaRetencPlla!=null && listaRetencPlla.size()>0){
			beanAdenda.setListaRetencionPlla(listaRetencPlla);
		}
		if(listaDonacionRegalia!=null && listaDonacionRegalia.size()>0){
			for(DonacionRegalia donacion : listaDonacionRegalia){
				donacion.setIntPersonaRegistraPk(usuario.getIntPersPersonaPk());
				
				donacion.setTsFechaRegistra(new Timestamp(new Date().getTime()));
				beanAdenda.setListaDonacion(listaDonacionRegalia);
			}
		}
		if(listaFirmantes!=null && listaFirmantes.size()>0){
			for(Firmante firmante : listaFirmantes){
				firmante.setIntPersEmpresaPk(listaConvenioEstructuraDet.get(0).getEstructura().getIntPersEmpresaPk());
				firmante.setIntPersPersonaEntidadPk(listaConvenioEstructuraDet.get(0).getEstructura().getIntPersPersonaPk());
				firmante.setIntPersPersonaFirmantePk(firmante.getPersona().getIntIdPersona());
				firmante.setIntPersonaRegistraPk(usuario.getIntPersPersonaPk());
				beanAdenda.setListaFirmante(listaFirmantes);
			}
		}
		
		if(listaAdendaAportacion!=null && listaAdendaAportacion.size()>0){
			for(AdendaCaptacion aportacion : listaAdendaAportacion){
				aportacion.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				aportacion.setIntPersPersonaRegistraPk(usuario.getIntPersPersonaPk());
			}
			beanAdenda.setListaAdendaCaptacionAportacion(listaAdendaAportacion);
		}
		if(listaAdendaFondoSepelio!=null && listaAdendaFondoSepelio.size()>0){
			for(AdendaCaptacion fondoSepelio : listaAdendaFondoSepelio){
				fondoSepelio.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				fondoSepelio.setIntPersPersonaRegistraPk(usuario.getIntPersPersonaPk());
			}
			beanAdenda.setListaAdendaCaptacionFondoSepelio(listaAdendaFondoSepelio);
		}
		if(listaAdendaFondoRetiro!=null && listaAdendaFondoRetiro.size()>0){
			for(AdendaCaptacion fondoRetiro : listaAdendaFondoRetiro){
				fondoRetiro.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				fondoRetiro.setIntPersPersonaRegistraPk(usuario.getIntPersPersonaPk());
			}
			beanAdenda.setListaAdendaCaptacionFondoRetiro(listaAdendaFondoRetiro);
		}
		if(listaAdendaMantCta!=null && listaAdendaMantCta.size()>0){
			for(AdendaCaptacion mantCuenta : listaAdendaMantCta){
				mantCuenta.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				mantCuenta.setIntPersPersonaRegistraPk(usuario.getIntPersPersonaPk());
			}
			beanAdenda.setListaAdendaCaptacionMantCuenta(listaAdendaMantCta);
		}
		if(listaAdendaCredito!=null && listaAdendaCredito.size()>0){
			for(AdendaCredito adendaCredito : listaAdendaCredito){
				if(adendaCredito==null)adendaCredito = new AdendaCredito();
				adendaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				adendaCredito.setIntPersPersonaRegistraPk(usuario.getIntPersPersonaPk());
			}
			beanAdenda.setListaAdendaCreditos(listaAdendaCredito);
		}
		
		try {
			facadeConvenio = (ConvenioFacadeLocal)EJBFactory.getLocal(ConvenioFacadeLocal.class);
			facadeConvenio.modificarConvenio(beanAdenda);
			limpiarConvenio();
			listarConvenios(event);
			formConvenioRendered = false;
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	/*public void descargarCartaPresentacion(ActionEvent event){
		String fileName = getRequestParameter("fileCartaPresentacion");
		log.info("fileName: " + fileName);
		TipoArchivo tipoarchivo = null;
		Integer intCartaPresentacion = Constante.PARAM_T_TIPOARCHIVOADJUNTO_CARTAPRESENTACION;
		try {
			tipoarchivo = generalFacade.getTipoArchivoPorPk(intCartaPresentacion);
			DownloadFile.downloadFile(tipoarchivo.getStrRuta() + "\\" + fileName);
		} catch (BusinessException e) {
			log.error("error: " + e);
		}
	}
	
	public void descargarConvenioOAdendaSugerida(ActionEvent event){
		String idArchivo = event.getComponent().getId();
		String fileName = getRequestParameter("fileConvSugerido");
		log.info("fileName: " + fileName);
		TipoArchivo tipoarchivo = null;
		Integer intTipoArchivo = null;
		if(idArchivo.equals("idConvSugerido")){
			intTipoArchivo = Constante.PARAM_T_TIPOARCHIVOADJUNTO_CONVENIOSUGERIDO;
		}else if(idArchivo.equals("")){
			intTipoArchivo = Constante.PARAM_T_TIPOARCHIVOADJUNTO_ADENDASUGERIDA;
		}
		
		try {
			tipoarchivo = generalFacade.getTipoArchivoPorPk(intTipoArchivo);
			DownloadFile.downloadFile(tipoarchivo.getStrRuta() + "\\" + fileName);
		} catch (BusinessException e) {
			log.error("error: " + e);
		}
	}*/
	
	//Agregado por cdelosrios, 08/12/2013
	public String getLimpiarConvenio(){
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(MENU_CONVENIO);
		log.info("POSEE PERMISO" + poseePermiso);
		if(usuario!=null && poseePermiso){
			cargarValoresIniciales();
			formConvenioRendered = false;
			limpiarConvenio();
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}
		return "";
	}
	private void cargarUsuario(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		sesionIntIdEmpresa = usuario.getPerfil().getId().getIntPersEmpresaPk();
	}
	public boolean isPoseePermiso() {
		return poseePermiso;
	}
	public void setPoseePermiso(boolean poseePermiso) {
		this.poseePermiso = poseePermiso;
	}
	//Fin agregado por cdelosrios, 08/12/2013
}