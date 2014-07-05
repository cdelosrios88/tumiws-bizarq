package pe.com.tumi.cobranza.cierreoperacional.mensual.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranza;
import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranzaOperacion;
import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranzaOperacionId;
import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranzaPlanilla;
import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranzaPlanillaId;
import pe.com.tumi.cobranza.cierremensual.domain.composite.CierreCobranzaComp;
import pe.com.tumi.cobranza.cierremensual.domain.composite.CierreCobranzaOperacionComp;
import pe.com.tumi.cobranza.cierremensual.domain.composite.CierreCobranzaPlanillaComp;
import pe.com.tumi.cobranza.cierremensual.facade.CierreCobranzaFacadeLocal;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.FacesContextUtil;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.permiso.domain.Password;
import pe.com.tumi.seguridad.permiso.domain.PasswordId;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfil;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfilId;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeRemote;

/************************************************************************/
/* Nombre de la clase: CierreOperacionalController */
/* Funcionalidad : Clase que que tiene el proceso del cierre operacional */
/* 				   mensual de Cobranzas */
/* Autor : cdelosrios */
/* Versión : V1 */
/* Fecha creación : 24/06/2013 */
/* ********************************************************************* */

public class CierreOperacionalController {
	protected 	static Logger 	log;
	private CierreCobranza cierreCobranza;
	private Integer intMesBusq;
	private Integer intAnioBusq;
	private Integer intTipoOperacionBusq;
	private Integer intEstadoBusq;
	private List<CierreCobranzaComp> listaCierreCobranza;
	
	private Boolean	blnCreditos;
	private Boolean	blnCobranza;
	private Boolean	blnTesoreria;
	private Boolean	blnContabilidad;
	private List<SelectItem> listYears;
	private Boolean blnFormulario;
	private Boolean blnValidPass;
	private Boolean blnValidPassRendered;
	private Integer intMes;
	private Integer intAnio;
	private String strValidPass;
	private Boolean blnGrabar;
	private Boolean blnOperCtaCte;
	private Boolean blnOperPllaEnviada;
	private Boolean blnOperPllaEfectuada;
	private String strCierreCobranza;
	private List<CierreCobranzaOperacionComp> listaCierreOperacionComp;
	private List<CierreCobranzaPlanillaComp> listaCierrePlanillaCompSalud;
	private List<CierreCobranzaPlanillaComp> listaCierrePlanillaCompTercero;
	CierreCobranzaFacadeLocal cierreCobranzaFacade;
	PermisoFacadeRemote permisoFacade;
	TablaFacadeRemote tablaFacade;
	
	public CierreOperacionalController(){
		log = Logger.getLogger(this.getClass());
		blnFormulario = Boolean.FALSE;
		listYears = new ArrayList<SelectItem>();
		blnValidPass = Boolean.FALSE;
		blnValidPassRendered = Boolean.FALSE;
		blnGrabar = Boolean.FALSE;
		blnOperCtaCte = Boolean.FALSE;
		blnOperPllaEnviada = Boolean.FALSE;
		blnOperPllaEfectuada = Boolean.FALSE;
		cierreCobranza = new CierreCobranza();
		listaCierreCobranza = new ArrayList<CierreCobranzaComp>();
		listaCierreOperacionComp = new ArrayList<CierreCobranzaOperacionComp>();
		listaCierrePlanillaCompSalud = new ArrayList<CierreCobranzaPlanillaComp>();
		listaCierrePlanillaCompTercero = new ArrayList<CierreCobranzaPlanillaComp>();
		try {
			cierreCobranzaFacade = (CierreCobranzaFacadeLocal)EJBFactory.getLocal(CierreCobranzaFacadeLocal.class);
			permisoFacade = (PermisoFacadeRemote)EJBFactory.getRemote(PermisoFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
		}catch (EJBFactoryException e) {
			log.error(e);
		}
		inicio();
	}

	public void inicio(){
		PermisoPerfil permiso = null;
		PermisoPerfilId id = null;
		Usuario usuario = null;
		Integer MENU_CREDITO = 126;
		Integer MENU_COBRANZA = 127;
		Integer MENU_TESORERIA = 128;
		Integer MENU_CONTABILIDAD = 129;
		try{
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			if(usuario != null){
				id = new PermisoPerfilId();
				id.setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
				id.setIntIdTransaccion(MENU_CREDITO);
				id.setIntIdPerfil(usuario.getPerfil().getId().getIntIdPerfil());
				PermisoFacadeRemote remotePermiso = (PermisoFacadeRemote)EJBFactory.getRemote(PermisoFacadeRemote.class);
				permiso = remotePermiso.getPermisoPerfilPorPk(id);
				blnCreditos = (permiso == null);
				id.setIntIdTransaccion(MENU_COBRANZA);
				permiso = remotePermiso.getPermisoPerfilPorPk(id);
				blnCobranza = (permiso == null);
				id.setIntIdTransaccion(MENU_TESORERIA);
				permiso = remotePermiso.getPermisoPerfilPorPk(id);
				blnTesoreria = (permiso == null);
				id.setIntIdTransaccion(MENU_CONTABILIDAD);
				permiso = remotePermiso.getPermisoPerfilPorPk(id);
				blnContabilidad = (permiso == null);
			}else{
				blnCreditos = false;
				blnCobranza= false;
				blnTesoreria= false;
				blnContabilidad= false;
			}
		} catch (BusinessException e) {
			log.error(e);
		} catch (EJBFactoryException e) {
			log.error(e);
		}		
	}
	
	public void limpiarFormCierreCobranza(){
		this.strValidPass = "";
		this.listaCierreOperacionComp = new ArrayList<CierreCobranzaOperacionComp>();
		this.listaCierrePlanillaCompSalud = new ArrayList<CierreCobranzaPlanillaComp>();
		this.listaCierrePlanillaCompTercero = new ArrayList<CierreCobranzaPlanillaComp>();
		blnOperCtaCte = Boolean.FALSE;
		blnOperPllaEnviada = Boolean.FALSE;
		blnOperPllaEfectuada = Boolean.FALSE;
		cierreCobranza = new CierreCobranza();
	}
	
	public void cancelarCierreMensual() {
		blnFormulario = Boolean.FALSE;
		blnGrabar = Boolean.FALSE;
		limpiarFormCierreCobranza();
	}
	
	public void showFormCierreOperacional(){
		blnFormulario = Boolean.TRUE;
		blnValidPass = Boolean.FALSE;
		blnValidPassRendered = Boolean.TRUE;
		blnGrabar = Boolean.TRUE;
		this.strCierreCobranza = Constante.MANTENIMIENTO_GRABAR;
		limpiarFormCierreCobranza();
	}
	
	public void buscarCierreCobranza(){
		CierreCobranzaComp cierreCobranzaComp = null;
		try {
			intMesBusq = (intMesBusq == -1?0:intMesBusq);
			cierreCobranzaComp = new CierreCobranzaComp();
			cierreCobranzaComp.setCierreCobranza(new CierreCobranza());
			String strTmpVal = (intMesBusq < 10) ? "0" : "";
			cierreCobranzaComp.getCierreCobranza().setIntParaPeriodoCierre(new Integer(""+intAnioBusq+strTmpVal+intMesBusq));
			cierreCobranzaComp.getCierreCobranza().setIntParaTipoRegistro(this.intTipoOperacionBusq==-1?0:intTipoOperacionBusq);
			cierreCobranzaComp.setIntParaEstado(this.intEstadoBusq==-1?0:this.intEstadoBusq);
			listaCierreCobranza = cierreCobranzaFacade.getListaCierreCobranzaBusq(cierreCobranzaComp);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void validarPassPerfil(){
		Password passWord = null;
		Integer MENU_COBRANZA = 127;
		List<Tabla> listCierreCobranzaOperacion = null;
		List<Tabla> listModalidad = null;
		List<Tabla> listTipoSocio = null;
		//CierreCobranzaOperacion cierreCobranzaOperacion = null;
		List<CierreCobranzaOperacionComp> listaCierreOperacionComp = new ArrayList<CierreCobranzaOperacionComp>();
		List<CierreCobranzaPlanillaComp> listaCierrePlanillaCompSalud = null;
		List<CierreCobranzaPlanillaComp> listaCierrePlanillaCompTerceros = null;
		CierreCobranzaOperacionComp cobranzaOperacionComp = null;
		CierreCobranzaPlanillaComp cobranzaPlanillaComp = null;
		
		CierreCobranza cierreCobranzaTemp = null;
		CierreCobranzaPlanilla cierrePlanilla = null;
		List<CierreCobranzaOperacion> listaCierreOperacion = new ArrayList<CierreCobranzaOperacion>();
		List<CierreCobranzaPlanilla> listaCierrePlanillaSalud = new ArrayList<CierreCobranzaPlanilla>();
		List<CierreCobranzaPlanilla> listaCierrePlanillaTercero = new ArrayList<CierreCobranzaPlanilla>();
		
		try {
			passWord = new Password();
			passWord.setId(new PasswordId());
			passWord.getId().setIntEmpresaPk(Constante.PARAM_EMPRESASESION);
			passWord.getId().setIntIdTransaccion(MENU_COBRANZA);
			passWord.setStrContrasena(this.strValidPass);
			passWord = permisoFacade.getPasswordPorPkYPass(passWord);
			if(passWord!=null){
				listModalidad = tablaFacade.getListaTablaPorIdMaestroYNotInIdDetalle(new Integer(Constante.PARAM_T_MODALIDADPLANILLA), "-1");
				listTipoSocio = tablaFacade.getListaTablaPorIdMaestroYNotInIdDetalle(new Integer(Constante.PARAM_T_TIPOSOCIO), "-1");
				String strTmpVal = (intMesBusq < 10) ? "0" : "";
				String strPeriodoCierre = intAnio.toString().concat(strTmpVal+intMes.toString());
				
				if(cierreCobranza.getIntParaTipoRegistro().equals(Constante.PARAM_T_TIPO_CIERRE_COBRANZA_OPERACION)){
					cierreCobranzaTemp = new CierreCobranza();
					cierreCobranzaTemp.setIntEmpresaCierreCobranza(Constante.PARAM_EMPRESASESION);
					cierreCobranzaTemp.setIntParaPeriodoCierre(new Integer(strPeriodoCierre));
					cierreCobranzaTemp.setIntParaTipoRegistro(this.cierreCobranza.getIntParaTipoRegistro());
					listaCierreOperacion = cierreCobranzaFacade.getListaCierreCobranzaOperacion(cierreCobranzaTemp);
					
					if(listaCierreOperacion!=null && !listaCierreOperacion.isEmpty()){
						for (CierreCobranzaOperacion cierreCobranzaOperacion : listaCierreOperacion) {
							Tabla tipoSolicitudCtaCte = new Tabla();
							tipoSolicitudCtaCte.setIntIdDetalle(cierreCobranzaOperacion.getId().getIntParaTipoSolicitudCtaCte());
							cobranzaOperacionComp = new CierreCobranzaOperacionComp();
							cobranzaOperacionComp.setCierreCobranzaOperacion(cierreCobranzaOperacion);
							cobranzaOperacionComp.setParamCobranzaOperacion(tipoSolicitudCtaCte);
							listaCierreOperacionComp.add(cobranzaOperacionComp);
						}
						this.strCierreCobranza = Constante.MANTENIMIENTO_MODIFICAR;
					}else{
						listCierreCobranzaOperacion = tablaFacade.getListaTablaPorIdMaestroYNotInIdDetalle(new Integer(Constante.PARAM_T_TIPO_SOLICITUD_CTACTE),"-1");
						if(listCierreCobranzaOperacion!=null && listCierreCobranzaOperacion.size()>0){
							for(Tabla cobranzaOperacion : listCierreCobranzaOperacion){
								cobranzaOperacionComp = new CierreCobranzaOperacionComp();
								cobranzaOperacionComp.setCierreCobranzaOperacion(new CierreCobranzaOperacion());
								cobranzaOperacionComp.setParamCobranzaOperacion(cobranzaOperacion);
								listaCierreOperacionComp.add(cobranzaOperacionComp);
							}
						}
					}
					this.listaCierreOperacionComp = listaCierreOperacionComp;
					blnOperPllaEnviada = Boolean.FALSE;
					blnOperPllaEfectuada = Boolean.FALSE;
					blnOperCtaCte = Boolean.TRUE;
				//} else if(cierreCobranza.getIntParaTipoRegistro().equals(Constante.PARAM_T_TIPO_CIERRE_COBRANZA_PLANILLA_ENVIADA)){
				} else {
					listaCierrePlanillaCompSalud = new ArrayList<CierreCobranzaPlanillaComp>();
					
					cierrePlanilla = new CierreCobranzaPlanilla();
					cierrePlanilla.setId(new CierreCobranzaPlanillaId());
					cierrePlanilla.getId().setIntPersEmpresaCierreCob(Constante.PARAM_EMPRESASESION);
					cierrePlanilla.getId().setIntPeriodoCierre(new Integer(strPeriodoCierre));
					cierrePlanilla.getId().setIntParaTipoRegistro(this.cierreCobranza.getIntParaTipoRegistro());
					cierrePlanilla.getId().setIntEstrucGrupo(Constante.PARAM_T_TIPOENTIDAD_SALUD);
					listaCierrePlanillaSalud = cierreCobranzaFacade.getListaCierrePlanillaPorPkCierreCobranza(
							cierrePlanilla);
					
					if(listaCierrePlanillaSalud!=null && !listaCierrePlanillaSalud.isEmpty()){
						for (CierreCobranzaPlanilla cierreCobranzaPlanilla : listaCierrePlanillaSalud) {
							Tabla tipoSocio = new Tabla();
							Tabla modalidad = new Tabla();
							tipoSocio.setIntIdDetalle(cierreCobranzaPlanilla.getId().getIntParaTipoSocio());
							modalidad.setIntIdDetalle(cierreCobranzaPlanilla.getId().getIntParaModalidad());
							cobranzaPlanillaComp = new CierreCobranzaPlanillaComp();
							cobranzaPlanillaComp.setCierreCobranzaPlanilla(cierreCobranzaPlanilla);
							cobranzaPlanillaComp.setParamTipoSocio(tipoSocio);
							cobranzaPlanillaComp.setParamModalidad(modalidad);
							listaCierrePlanillaCompSalud.add(cobranzaPlanillaComp);
						}
						this.strCierreCobranza = Constante.MANTENIMIENTO_MODIFICAR;
					}else{
						for(Tabla modalidad : listModalidad){
							for(Tabla tipoSocio : listTipoSocio){
								cobranzaPlanillaComp = new CierreCobranzaPlanillaComp();
								cobranzaPlanillaComp.setCierreCobranzaPlanilla(new CierreCobranzaPlanilla());
								if(modalidad.getIntIdDetalle().equals(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)){
									cobranzaPlanillaComp.setParamModalidad(modalidad);
									cobranzaPlanillaComp.setParamTipoSocio(tipoSocio);
									listaCierrePlanillaCompSalud.add(cobranzaPlanillaComp);
								}else if(modalidad.getIntIdDetalle().equals(
										Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS)){
									if(tipoSocio.getIntIdDetalle().equals(
											Constante.PARAM_T_TIPOSOCIO_ACTIVO)){
										cobranzaPlanillaComp.setParamModalidad(modalidad);
										cobranzaPlanillaComp.setParamTipoSocio(tipoSocio);
										listaCierrePlanillaCompSalud.add(cobranzaPlanillaComp);
									}
									
								}else if(modalidad.getIntIdDetalle().equals(Constante.PARAM_T_MODALIDADPLANILLA_CAS)){
									if(tipoSocio.getIntIdDetalle().equals(Constante.PARAM_T_TIPOSOCIO_ACTIVO)){
										cobranzaPlanillaComp.setParamModalidad(modalidad);
										cobranzaPlanillaComp.setParamTipoSocio(tipoSocio);
										listaCierrePlanillaCompSalud.add(cobranzaPlanillaComp);
									}
								}
							}
						}
					}
					this.listaCierrePlanillaCompSalud = listaCierrePlanillaCompSalud;
					
					
					/*blnOperPllaEnviada = Boolean.TRUE;
					blnOperPllaEfectuada = Boolean.TRUE;
					blnOperCtaCte = Boolean.FALSE;*/
				//} else { //Planilla Efectuada
					listaCierrePlanillaCompTerceros = new ArrayList<CierreCobranzaPlanillaComp>();
					
					cierrePlanilla = new CierreCobranzaPlanilla();
					cierrePlanilla.setId(new CierreCobranzaPlanillaId());
					cierrePlanilla.getId().setIntPersEmpresaCierreCob(Constante.PARAM_EMPRESASESION);
					cierrePlanilla.getId().setIntPeriodoCierre(new Integer(strPeriodoCierre));
					cierrePlanilla.getId().setIntParaTipoRegistro(this.cierreCobranza.getIntParaTipoRegistro());
					cierrePlanilla.getId().setIntEstrucGrupo(Constante.PARAM_T_TIPOENTIDAD_SALUD);
					listaCierrePlanillaSalud = cierreCobranzaFacade.getListaCierrePlanillaPorPkCierreCobranza(cierrePlanilla);
					cierrePlanilla.getId().setIntEstrucGrupo(Constante.PARAM_T_TIPOENTIDAD_TERCEROS);
					listaCierrePlanillaTercero = cierreCobranzaFacade.getListaCierrePlanillaPorPkCierreCobranza(cierrePlanilla);
					
					if(listaCierrePlanillaTercero!=null && !listaCierrePlanillaTercero.isEmpty()){
						for (CierreCobranzaPlanilla cierreCobranzaPlanilla : listaCierrePlanillaTercero) {
							Tabla tipoSocio = new Tabla();
							Tabla modalidad = new Tabla();
							tipoSocio.setIntIdDetalle(cierreCobranzaPlanilla.getId().getIntParaTipoSocio());
							modalidad.setIntIdDetalle(cierreCobranzaPlanilla.getId().getIntParaModalidad());
							cobranzaPlanillaComp = new CierreCobranzaPlanillaComp();
							cobranzaPlanillaComp.setCierreCobranzaPlanilla(cierreCobranzaPlanilla);
							cobranzaPlanillaComp.setParamTipoSocio(tipoSocio);
							cobranzaPlanillaComp.setParamModalidad(modalidad);
							listaCierrePlanillaCompTerceros.add(cobranzaPlanillaComp);
						}
					}else{
						for(Tabla modalidad : listModalidad){
							for(Tabla tipoSocio : listTipoSocio){
								cobranzaPlanillaComp = new CierreCobranzaPlanillaComp();
								cobranzaPlanillaComp.setCierreCobranzaPlanilla(new CierreCobranzaPlanilla());
								if(modalidad.getIntIdDetalle().equals(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)){
									cobranzaPlanillaComp.setParamModalidad(modalidad);
									cobranzaPlanillaComp.setParamTipoSocio(tipoSocio);
									listaCierrePlanillaCompTerceros.add(cobranzaPlanillaComp);
								}else if(modalidad.getIntIdDetalle().equals(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS)){
									if(tipoSocio.getIntIdDetalle().equals(Constante.PARAM_T_TIPOSOCIO_ACTIVO)){
										cobranzaPlanillaComp.setParamModalidad(modalidad);
										cobranzaPlanillaComp.setParamTipoSocio(tipoSocio);
										listaCierrePlanillaCompTerceros.add(cobranzaPlanillaComp);
									}
									
								}else if(modalidad.getIntIdDetalle().equals(Constante.PARAM_T_MODALIDADPLANILLA_CAS)){
									if(tipoSocio.getIntIdDetalle().equals(Constante.PARAM_T_TIPOSOCIO_ACTIVO)){
										cobranzaPlanillaComp.setParamModalidad(modalidad);
										cobranzaPlanillaComp.setParamTipoSocio(tipoSocio);
										listaCierrePlanillaCompTerceros.add(cobranzaPlanillaComp);
									}
								}
							}
						}
					}
					this.listaCierrePlanillaCompTercero = listaCierrePlanillaCompTerceros;
					
					blnOperCtaCte = Boolean.FALSE;
					blnOperPllaEnviada = Boolean.TRUE;
					blnOperPllaEfectuada = Boolean.TRUE;
				}
				blnValidPass = Boolean.TRUE;
				blnValidPassRendered = Boolean.FALSE;
			}else{
				FacesContextUtil.setMessageError("La Contraseña no es válida.");
			}
		} catch (BusinessException e) {
			log.error(e);
		}
	}
	
	public void grabarCierreCobranza(){
		Usuario usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		String strTmpVal = (intMes < 10) ? "0" : "";
		String strPeriodoCierre = intAnio.toString().concat(strTmpVal+intMes.toString());
		cierreCobranza.setIntEmpresaCierreCobranza(Constante.PARAM_EMPRESASESION);
		cierreCobranza.setIntParaPeriodoCierre(new Integer(strPeriodoCierre));
		CierreCobranzaOperacion cierreCobranzaOperacion = null;
		CierreCobranzaPlanilla cierreCobranzaPlanilla = null;
		List<CierreCobranzaOperacion> listaCobranzaOperacion = null;
		List<CierreCobranzaPlanilla> listaCobranzaPlanilla = null;
		//List<CierreCobranzaPlanilla> listaCobranzaPlanillaEfectuada = null;
		if(listaCierreOperacionComp!=null && !listaCierreOperacionComp.isEmpty()){
			listaCobranzaOperacion = new ArrayList<CierreCobranzaOperacion>();
			for(CierreCobranzaOperacionComp cobranzaOperacionComp : listaCierreOperacionComp){
				cierreCobranzaOperacion = new CierreCobranzaOperacion();
				cierreCobranzaOperacion.setId(new CierreCobranzaOperacionId());
				cierreCobranzaOperacion.getId().setIntEmpresaCierreCob(cierreCobranza.getIntEmpresaCierreCobranza());
				cierreCobranzaOperacion.getId().setIntPeriodoCierre(cierreCobranza.getIntParaPeriodoCierre());
				cierreCobranzaOperacion.getId().setIntParaTipoRegistroCob(cierreCobranza.getIntParaTipoRegistro());
				cierreCobranzaOperacion.getId().setIntParaTipoSolicitudCtaCte(cobranzaOperacionComp.getParamCobranzaOperacion().getIntIdDetalle());
				cierreCobranzaOperacion.setIntParaEstadoCierre(cobranzaOperacionComp.getCierreCobranzaOperacion().getIntParaEstadoCierre());
				cierreCobranzaOperacion.setIntEmpresaRegistro(Constante.PARAM_EMPRESASESION);
				cierreCobranzaOperacion.setIntPersonaRegistro(usuario.getIntPersPersonaPk());
				listaCobranzaOperacion.add(cierreCobranzaOperacion);
			}
			cierreCobranza.setListCierreOperacion(listaCobranzaOperacion);
		}
		
		listaCobranzaPlanilla = new ArrayList<CierreCobranzaPlanilla>();
		if(listaCierrePlanillaCompSalud!=null && !listaCierrePlanillaCompSalud.isEmpty()){
			for(CierreCobranzaPlanillaComp cobranzaPlanillaComp : listaCierrePlanillaCompSalud){
				cierreCobranzaPlanilla = new CierreCobranzaPlanilla();
				cierreCobranzaPlanilla.setId(new CierreCobranzaPlanillaId());
				cierreCobranzaPlanilla.getId().setIntPersEmpresaCierreCob(cierreCobranza.getIntEmpresaCierreCobranza());
				cierreCobranzaPlanilla.getId().setIntParaTipoRegistro(cierreCobranza.getIntParaTipoRegistro());
				cierreCobranzaPlanilla.getId().setIntPeriodoCierre(cierreCobranza.getIntParaPeriodoCierre());
				cierreCobranzaPlanilla.getId().setIntEstrucGrupo(new Integer(Constante.PARAM_T_TIPOENTIDAD_SALUD));
				cierreCobranzaPlanilla.getId().setIntParaTipoSocio(cobranzaPlanillaComp.getParamTipoSocio().getIntIdDetalle());
				cierreCobranzaPlanilla.getId().setIntParaModalidad(cobranzaPlanillaComp.getParamModalidad().getIntIdDetalle());
				cierreCobranzaPlanilla.setIntParaEstadoCierre(cobranzaPlanillaComp.getCierreCobranzaPlanilla().getIntParaEstadoCierre());
				cierreCobranzaPlanilla.setIntEmpresaRegistro(Constante.PARAM_EMPRESASESION);
				cierreCobranzaPlanilla.setIntPersonaRegistro(usuario.getIntPersPersonaPk());
				listaCobranzaPlanilla.add(cierreCobranzaPlanilla);
			}
			//cierreCobranza.setListCierrePlanilla(listaCobranzaPlanillaEnviada);
		}
		
		if(listaCierrePlanillaCompTercero!=null && !listaCierrePlanillaCompTercero.isEmpty()){
			for(CierreCobranzaPlanillaComp cobranzaPlanillaComp : listaCierrePlanillaCompTercero){
				cierreCobranzaPlanilla = new CierreCobranzaPlanilla();
				cierreCobranzaPlanilla.setId(new CierreCobranzaPlanillaId());
				cierreCobranzaPlanilla.getId().setIntPersEmpresaCierreCob(cierreCobranza.getIntEmpresaCierreCobranza());
				cierreCobranzaPlanilla.getId().setIntParaTipoRegistro(cierreCobranza.getIntParaTipoRegistro());
				cierreCobranzaPlanilla.getId().setIntPeriodoCierre(cierreCobranza.getIntParaPeriodoCierre());
				cierreCobranzaPlanilla.getId().setIntEstrucGrupo(new Integer(Constante.PARAM_T_TIPOENTIDAD_TERCEROS));
				cierreCobranzaPlanilla.getId().setIntParaTipoSocio(cobranzaPlanillaComp.getParamTipoSocio().getIntIdDetalle());
				cierreCobranzaPlanilla.getId().setIntParaModalidad(cobranzaPlanillaComp.getParamModalidad().getIntIdDetalle());
				cierreCobranzaPlanilla.setIntParaEstadoCierre(cobranzaPlanillaComp.getCierreCobranzaPlanilla().getIntParaEstadoCierre());
				cierreCobranzaPlanilla.setIntEmpresaRegistro(Constante.PARAM_EMPRESASESION);
				cierreCobranzaPlanilla.setIntPersonaRegistro(usuario.getIntPersPersonaPk());
				listaCobranzaPlanilla.add(cierreCobranzaPlanilla);
			}
			cierreCobranza.setListCierrePlanilla(listaCobranzaPlanilla);
		}
		
		try {
			cierreCobranza = cierreCobranzaFacade.grabarCierreCobranza(cierreCobranza);
			limpiarFormCierreCobranza();
			blnFormulario = Boolean.FALSE;
			FacesContextUtil.setMessageSuccess("Operación Generada exitosamente.");
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			FacesContextUtil.setMessageError("Ocurrió un error mientras se intentaba Grabar los datos.");
		}
	}
	
	public void modificarCierreCobranza(){
		Usuario usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		CierreCobranzaOperacion cierreCobranzaOperacion = null;
		CierreCobranzaPlanilla cierreCobranzaPlanilla = null;
		List<CierreCobranzaOperacion> listaCobranzaOperacion = null;
		List<CierreCobranzaPlanilla> listaCobranzaPlanilla = null;
		if(listaCierreOperacionComp!=null && !listaCierreOperacionComp.isEmpty()){
			listaCobranzaOperacion = new ArrayList<CierreCobranzaOperacion>();
			for(CierreCobranzaOperacionComp cobranzaOperacionComp : listaCierreOperacionComp){
				cierreCobranzaOperacion = new CierreCobranzaOperacion();
				cierreCobranzaOperacion.setId(cobranzaOperacionComp.getCierreCobranzaOperacion().getId());
				cierreCobranzaOperacion.setIntParaEstadoCierre(cobranzaOperacionComp.getCierreCobranzaOperacion().getIntParaEstadoCierre());
				cierreCobranzaOperacion.setIntEmpresaRegistro(Constante.PARAM_EMPRESASESION);
				cierreCobranzaOperacion.setIntPersonaRegistro(usuario.getIntPersPersonaPk());
				listaCobranzaOperacion.add(cierreCobranzaOperacion);
			}
			cierreCobranza.setListCierreOperacion(listaCobranzaOperacion);
		}
		
		listaCobranzaPlanilla = new ArrayList<CierreCobranzaPlanilla>();
		if(listaCierrePlanillaCompSalud!=null && !listaCierrePlanillaCompSalud.isEmpty()){
			for(CierreCobranzaPlanillaComp cobranzaPlanillaComp : listaCierrePlanillaCompSalud){
				cierreCobranzaPlanilla = new CierreCobranzaPlanilla();
				cierreCobranzaPlanilla.setId(cobranzaPlanillaComp.getCierreCobranzaPlanilla().getId());
				cierreCobranzaPlanilla.setIntParaEstadoCierre(cobranzaPlanillaComp.getCierreCobranzaPlanilla().getIntParaEstadoCierre());
				cierreCobranzaPlanilla.setIntEmpresaRegistro(Constante.PARAM_EMPRESASESION);
				cierreCobranzaPlanilla.setIntPersonaRegistro(usuario.getIntPersPersonaPk());
				listaCobranzaPlanilla.add(cierreCobranzaPlanilla);
			}
			
			for(CierreCobranzaPlanillaComp cobranzaPlanillaComp : listaCierrePlanillaCompTercero){
				cierreCobranzaPlanilla = new CierreCobranzaPlanilla();
				cierreCobranzaPlanilla.setId(cobranzaPlanillaComp.getCierreCobranzaPlanilla().getId());
				cierreCobranzaPlanilla.setIntParaEstadoCierre(cobranzaPlanillaComp.getCierreCobranzaPlanilla().getIntParaEstadoCierre());
				cierreCobranzaPlanilla.setIntEmpresaRegistro(Constante.PARAM_EMPRESASESION);
				cierreCobranzaPlanilla.setIntPersonaRegistro(usuario.getIntPersPersonaPk());
				listaCobranzaPlanilla.add(cierreCobranzaPlanilla);
			}
			cierreCobranza.setListCierrePlanilla(listaCobranzaPlanilla);
		}
		
		try {
			cierreCobranza = cierreCobranzaFacade.modificarCierreCobranza(cierreCobranza);
			limpiarFormCierreCobranza();
			blnFormulario = Boolean.FALSE;
			FacesContextUtil.setMessageSuccess("Operación Generada exitosamente.");
			//listarCredito(event);
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			FacesContextUtil.setMessageError("Ocurrió un error mientras se intentaba Grabar los datos.");
		}
	}
	
	//Getters y Setters
	public Integer getIntMesBusq() {
		return intMesBusq;
	}

	public void setIntMesBusq(Integer intMesBusq) {
		this.intMesBusq = intMesBusq;
	}

	public Integer getIntAnioBusq() {
		return intAnioBusq;
	}

	public void setIntAnioBusq(Integer intAnioBusq) {
		this.intAnioBusq = intAnioBusq;
	}

	public Integer getIntTipoOperacionBusq() {
		return intTipoOperacionBusq;
	}

	public void setIntTipoOperacionBusq(Integer intTipoOperacionBusq) {
		this.intTipoOperacionBusq = intTipoOperacionBusq;
	}

	public Integer getIntEstadoBusq() {
		return intEstadoBusq;
	}

	public void setIntEstadoBusq(Integer intEstadoBusq) {
		this.intEstadoBusq = intEstadoBusq;
	}
	
	public List<CierreCobranzaComp> getListaCierreCobranza() {
		return listaCierreCobranza;
	}

	public void setListaCierreCobranza(List<CierreCobranzaComp> listaCierreCobranza) {
		this.listaCierreCobranza = listaCierreCobranza;
	}

	public Boolean getBlnCreditos() {
		return blnCreditos;
	}

	public void setBlnCreditos(Boolean blnCreditos) {
		this.blnCreditos = blnCreditos;
	}

	public Boolean getBlnTesoreria() {
		return blnTesoreria;
	}

	public void setBlnTesoreria(Boolean blnTesoreria) {
		this.blnTesoreria = blnTesoreria;
	}

	public Boolean getBlnContabilidad() {
		return blnContabilidad;
	}

	public void setBlnContabilidad(Boolean blnContabilidad) {
		this.blnContabilidad = blnContabilidad;
	}

	public Boolean getBlnCobranza() {
		return blnCobranza;
	}

	public void setBlnCobranza(Boolean blnCobranza) {
		this.blnCobranza = blnCobranza;
	}
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

	public Boolean getBlnFormulario() {
		return blnFormulario;
	}

	public void setBlnFormulario(Boolean blnFormulario) {
		this.blnFormulario = blnFormulario;
	}
	
	public List<SelectItem> getListYears(){
		List<SelectItem> listYearsTemp = new ArrayList<SelectItem>(); 
		try {
			for(int i=-1; i<=2; i++){
				Calendar cal=Calendar.getInstance();
				int year=cal.get(Calendar.YEAR)+i;
				listYearsTemp.add(i+1, new SelectItem(year));
			}	
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		this.listYears = listYearsTemp;
		
		return listYears;
	}

	public void setListYears(List<SelectItem> listYears) {
		this.listYears = listYears;
	}

	public Boolean getBlnValidPass() {
		return blnValidPass;
	}

	public void setBlnValidPass(Boolean blnValidPass) {
		this.blnValidPass = blnValidPass;
	}

	public Boolean getBlnValidPassRendered() {
		return blnValidPassRendered;
	}

	public void setBlnValidPassRendered(Boolean blnValidPassRendered) {
		this.blnValidPassRendered = blnValidPassRendered;
	}	

	public List<CierreCobranzaOperacionComp> getListaCierreOperacionComp() {
		return listaCierreOperacionComp;
	}

	public void setListaCierreOperacionComp(
			List<CierreCobranzaOperacionComp> listaCierreOperacionComp) {
		this.listaCierreOperacionComp = listaCierreOperacionComp;
	}

	public List<CierreCobranzaPlanillaComp> getListaCierrePlanillaCompSalud() {
		return listaCierrePlanillaCompSalud;
	}

	public void setListaCierrePlanillaCompSalud(
			List<CierreCobranzaPlanillaComp> listaCierrePlanillaCompSalud) {
		this.listaCierrePlanillaCompSalud = listaCierrePlanillaCompSalud;
	}

	public CierreCobranza getCierreCobranza() {
		return cierreCobranza;
	}

	public void setCierreCobranza(CierreCobranza cierreCobranza) {
		this.cierreCobranza = cierreCobranza;
	}

	public Integer getIntMes() {
		return intMes;
	}

	public void setIntMes(Integer intMes) {
		this.intMes = intMes;
	}

	public Integer getIntAnio() {
		return intAnio;
	}

	public void setIntAnio(Integer intAnio) {
		this.intAnio = intAnio;
	}

	public String getStrValidPass() {
		return strValidPass;
	}

	public void setStrValidPass(String strValidPass) {
		this.strValidPass = strValidPass;
	}

	public Boolean getBlnGrabar() {
		return blnGrabar;
	}

	public void setBlnGrabar(Boolean blnGrabar) {
		this.blnGrabar = blnGrabar;
	}

	public List<CierreCobranzaPlanillaComp> getListaCierrePlanillaCompTercero() {
		return listaCierrePlanillaCompTercero;
	}

	public void setListaCierrePlanillaCompTercero(
			List<CierreCobranzaPlanillaComp> listaCierrePlanillaCompTercero) {
		this.listaCierrePlanillaCompTercero = listaCierrePlanillaCompTercero;
	}

	public Boolean getBlnOperCtaCte() {
		return blnOperCtaCte;
	}

	public void setBlnOperCtaCte(Boolean blnOperCtaCte) {
		this.blnOperCtaCte = blnOperCtaCte;
	}

	public Boolean getBlnOperPllaEnviada() {
		return blnOperPllaEnviada;
	}

	public void setBlnOperPllaEnviada(Boolean blnOperPllaEnviada) {
		this.blnOperPllaEnviada = blnOperPllaEnviada;
	}

	public Boolean getBlnOperPllaEfectuada() {
		return blnOperPllaEfectuada;
	}

	public void setBlnOperPllaEfectuada(Boolean blnOperPllaEfectuada) {
		this.blnOperPllaEfectuada = blnOperPllaEfectuada;
	}

	public String getStrCierreCobranza() {
		return strCierreCobranza;
	}

	public void setStrCierreCobranza(String strCierreCobranza) {
		this.strCierreCobranza = strCierreCobranza;
	}
}