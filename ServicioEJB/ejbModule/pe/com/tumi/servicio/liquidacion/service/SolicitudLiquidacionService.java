package pe.com.tumi.servicio.liquidacion.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import pe.com.tumi.cobranza.cuentacte.facade.CuentacteFacadeRemote;
import pe.com.tumi.cobranza.planilla.domain.Devolucion;
import pe.com.tumi.cobranza.planilla.domain.DevolucionId;
import pe.com.tumi.cobranza.planilla.domain.EstadoSolicitudCtaCte;
import pe.com.tumi.cobranza.planilla.domain.EstadoSolicitudCtaCteId;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCte;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCteId;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCteTipo;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCteTipoId;
import pe.com.tumi.cobranza.planilla.domain.Transferencia;
import pe.com.tumi.cobranza.planilla.domain.TransferenciaId;
import pe.com.tumi.common.FileUtil;
import pe.com.tumi.common.MyFile;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalleId;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioId;
import pe.com.tumi.contabilidad.cierre.facade.LibroDiarioFacadeRemote;
import pe.com.tumi.contabilidad.core.domain.Modelo;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalle;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivel;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivelComp;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivelId;
import pe.com.tumi.contabilidad.core.domain.ModeloId;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;
import pe.com.tumi.contabilidad.core.facade.ModeloFacadeRemote;
import pe.com.tumi.contabilidad.core.facade.PlanCuentaFacadeRemote;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.facade.CuentaFacadeRemote;
import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.captacion.facade.CaptacionFacadeRemote;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.facade.CreditoFacadeRemote;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalleId;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalleId;
//import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoId;
import pe.com.tumi.movimiento.concepto.domain.CuentaDetalleBeneficio;
import pe.com.tumi.movimiento.concepto.domain.CuentaDetalleBeneficioId;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;
import pe.com.tumi.movimiento.concepto.domain.composite.CuentaConceptoComp;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.parametro.auditoria.domain.Auditoria;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.TipoArchivo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.contacto.facade.ContactoFacadeRemote;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalleId;
import pe.com.tumi.servicio.configuracion.domain.ConfServEstructuraDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServSolicitud;
import pe.com.tumi.servicio.configuracion.facade.ConfSolicitudFacadeLocal;
import pe.com.tumi.servicio.configuracion.facade.ConfSolicitudFacadeRemote;
import pe.com.tumi.servicio.liquidacion.bo.RequisitoLiquidacionBO;
import pe.com.tumi.servicio.prevision.bo.AutorizaLiquidacionBO;
import pe.com.tumi.servicio.prevision.bo.AutorizaVerificaLiquidacionBO;
import pe.com.tumi.servicio.prevision.bo.BeneficiarioLiquidacionBO;
import pe.com.tumi.servicio.prevision.bo.EstadoLiquidacionBO;
import pe.com.tumi.servicio.prevision.bo.EstadoPrevisionBO;
import pe.com.tumi.servicio.prevision.bo.ExpedienteLiquidacionBO;
import pe.com.tumi.servicio.prevision.bo.ExpedienteLiquidacionDetalleBO;
import pe.com.tumi.servicio.prevision.bo.ExpedientePrevisionBO;
import pe.com.tumi.servicio.prevision.domain.AutorizaLiquidacion;
import pe.com.tumi.servicio.prevision.domain.AutorizaLiquidacionId;
import pe.com.tumi.servicio.prevision.domain.AutorizaVerificaLiquidacion;
import pe.com.tumi.servicio.prevision.domain.AutorizaVerificaLiquidacionId;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioLiquidacion;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioLiquidacionId;
import pe.com.tumi.servicio.prevision.domain.EstadoLiquidacion;
import pe.com.tumi.servicio.prevision.domain.EstadoLiquidacionId;
import pe.com.tumi.servicio.prevision.domain.EstadoPrevision;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionComp;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionDetalle;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionDetalleId;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionId;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevision;
import pe.com.tumi.servicio.prevision.domain.RequisitoLiquidacion;
import pe.com.tumi.servicio.prevision.domain.RequisitoLiquidacionId;
import pe.com.tumi.servicio.prevision.domain.composite.ExpedientePrevisionComp;
import pe.com.tumi.servicio.prevision.domain.composite.RequisitoLiquidacionComp;
import pe.com.tumi.servicio.prevision.facade.AutorizacionLiquidacionFacadeRemote;
import pe.com.tumi.servicio.prevision.service.solicitudPrevisionService;


public class SolicitudLiquidacionService {
protected static Logger log = Logger.getLogger(solicitudPrevisionService.class);
	
	private ExpedientePrevisionBO boExpedientePrevision = (ExpedientePrevisionBO)TumiFactory.get(ExpedientePrevisionBO.class);
	private EstadoPrevisionBO boEstadoPrevision = (EstadoPrevisionBO)TumiFactory.get(EstadoPrevisionBO.class);
	private BeneficiarioLiquidacionBO boBeneficiarioLiquidacion = (BeneficiarioLiquidacionBO)TumiFactory.get(BeneficiarioLiquidacionBO.class);
	private ExpedienteLiquidacionBO boExpedienteLiquidacion = (ExpedienteLiquidacionBO)TumiFactory.get(ExpedienteLiquidacionBO.class);
	private ExpedienteLiquidacionDetalleBO boExpedienteLiquidacionDetalle = (ExpedienteLiquidacionDetalleBO)TumiFactory.get(ExpedienteLiquidacionDetalleBO.class);
	private EstadoLiquidacionBO boEstadoLiquidacion = (EstadoLiquidacionBO)TumiFactory.get(EstadoLiquidacionBO.class);
	private RequisitoLiquidacionBO boRequisitoLiquidacion = (RequisitoLiquidacionBO)TumiFactory.get(RequisitoLiquidacionBO.class);
	private ExpedienteLiquidacionDetalleBO boExpedienteLiquidacionDet = (ExpedienteLiquidacionDetalleBO)TumiFactory.get(ExpedienteLiquidacionDetalleBO.class);
	private AutorizaLiquidacionBO boAutorizaLiquidacion = (AutorizaLiquidacionBO)TumiFactory.get(AutorizaLiquidacionBO.class);
	private AutorizaVerificaLiquidacionBO boAutorizaVerificaLiquidacion = (AutorizaVerificaLiquidacionBO)TumiFactory.get(AutorizaVerificaLiquidacionBO.class);

	

	public ExpedienteLiquidacion grabarExpedienteLiquidacion(ExpedienteLiquidacion pExpedienteLiquidacion) throws BusinessException{
		ExpedienteLiquidacion expedienteLiquidacion = null;
		List<EstadoLiquidacion> listaEstadoLiquidacion = null;
		List<RequisitoLiquidacionComp> listaRequisitoLiquidacionComp = null;
//		EstadoLiquidacion estadoLiquidacion = null;
//		List<BeneficiarioLiquidacion> listaBeneficiarios = null;
		List<ExpedienteLiquidacionDetalle> listaDetalles = null;
		
		try{
			
			expedienteLiquidacion = boExpedienteLiquidacion.grabar(pExpedienteLiquidacion);

			listaEstadoLiquidacion = pExpedienteLiquidacion.getListaEstadoLiquidacion();
			
			// Si pasa directo a estado Solicitud, se genera un estado previo de requisito
			if(listaEstadoLiquidacion != null && !listaEstadoLiquidacion.isEmpty()){
				if(listaEstadoLiquidacion.get(0).getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD)==0){
					grabarEstadoRequisitoDefault(listaEstadoLiquidacion.get(0), expedienteLiquidacion.getId());
				}
			}
			
			//Grabar Lista Estado Crédito
			if(listaEstadoLiquidacion!=null){
				grabarListaDinamicaEstadoLiquidacion(listaEstadoLiquidacion, expedienteLiquidacion.getId());
			}

			
			// cgd - 06.06.2013
			listaDetalles = pExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle();
			if(listaDetalles != null && !listaDetalles.isEmpty()){
				expedienteLiquidacion.setListaExpedienteLiquidacionDetalle(grabarListaDinamicaDetalleLiquidacion(listaDetalles, expedienteLiquidacion.getId()));
				
			}
			//listaBeneficiarios = pExpedienteLiquidacion.getListaBeneficiarioLiquidacion();
			
			//Grabar Lista de requisitos de Credito
			listaRequisitoLiquidacionComp = pExpedienteLiquidacion.getListaRequisitoLiquidacionComp();
			if(listaRequisitoLiquidacionComp!=null){
				expedienteLiquidacion.setListaRequisitoLiquidacionComp(grabarListaDinamicaRequisitoLiquidacion(listaRequisitoLiquidacionComp, expedienteLiquidacion.getId()));
				//expedientePrevision.setListaRequisitoPrevisionComp(grabarListaDinamicaRequisitoCredito(listaRequisitoPrevisionComp, expedientePrevision.getId()));
			}
			
			
			// grabar detalleexpedientye
			/*if(lstCuentaConcepto != null){
				List<ExpedienteLiquidacionDetalle> lstDetalleLiquidacion = new ArrayList<ExpedienteLiquidacionDetalle>();
				for(int c=0;c<lstCuentaConcepto.size();c++){
					if((lstCuentaConcepto.get(c).getId().getIntItemCuentaConcepto().compareTo(Constante.PARAM_T_CUENTACONCEPTO_APORTES)==0)
							|| (lstCuentaConcepto.get(c).getId().getIntItemCuentaConcepto().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0)
							||(lstCuentaConcepto.get(c).getId().getIntItemCuentaConcepto().compareTo(Constante.PARAM_T_CUENTACONCEPTO_DEPOSITO)==0)
							||(lstCuentaConcepto.get(c).getId().getIntItemCuentaConcepto().compareTo(Constante.PARAM_T_CUENTACONCEPTO_AHORRO)==0)
					){
						ExpedienteLiquidacionDetalle detalleLiquidacion = new ExpedienteLiquidacionDetalle();
						detalleLiquidacion.setCuentaConcepto(lstCuentaConcepto.get(c));
						detalleLiquidacion.setBdSaldo(lstCuentaConcepto.get(c).getBdSaldo());
						lstDetalleLiquidacion.add(detalleLiquidacion);
					}	
				}
				expedienteLiquidacion.setListaExpedienteLiquidacionDetalle(grabarListaDinamicaDetalleLiquidacion(lstDetalleLiquidacion ,expedienteLiquidacion.getId()));

				//Grabar Lista de Benenficiarios
				//if(listaBeneficiarios!=null || !listaBeneficiarios.isEmpty()){
				List<BeneficiarioLiquidacion> listaBeneficiariosFinal = null;
				if(listaBeneficiarios!=null){	
					if(!listaBeneficiarios.isEmpty() ){
						// cargamos el id de la paersona a cada uno de los beneficiarios
						List<BeneficiarioLiquidacion> lstBeneficiariosFinal = new ArrayList<BeneficiarioLiquidacion>();
						ExpedienteLiquidacionDetalle detalle = null;
						for(int d=0; d<expedienteLiquidacion.getListaExpedienteLiquidacionDetalle().size();d++){
							
							detalle = expedienteLiquidacion.getListaExpedienteLiquidacionDetalle().get(d);
							BeneficiarioLiquidacion beneficiario = null;
							BeneficiarioLiquidacionId pk = null;
							
							for(int b=0;b<listaBeneficiarios.size();b++){
								System.out.println("beneficiarios --> "+listaBeneficiarios.size());
								
								// if(detalle.getCuentaConcepto().getId().getIntItemCuentaConcepto().compareTo(Constante.PARAM_T_CUENTACONCEPTO_APORTES)==0){
								if(detalle.getId().getIntItemCuentaConcepto().compareTo(Constante.PARAM_T_CUENTACONCEPTO_APORTES)==0){
									pk = new BeneficiarioLiquidacionId();
									beneficiario = 	new BeneficiarioLiquidacion();
										//listaBeneficiarios.get(b);
									beneficiario.setIntItemViculo(listaBeneficiarios.get(b).getIntItemViculo());
									beneficiario.setIntPersPersonaBeneficiario(listaBeneficiarios.get(b).getIntPersPersonaBeneficiario());
									beneficiario.setBdPorcentajeBeneficio(listaBeneficiarios.get(b).getBdPorcentajeBeneficioApo());
									pk.setIntCuenta(detalle.getId().getIntCuenta());
									pk.setIntItemCuentaConcepto(detalle.getId().getIntItemCuentaConcepto());
									pk.setIntItemExpediente(detalle.getId().getIntItemExpediente());
									pk.setIntPersEmpresa(detalle.getId().getIntPersEmpresa());
									pk.setIntPersEmpresaLiquidacion(detalle.getId().getIntPersEmpresaLiquidacion());
									beneficiario.setId(pk);
									lstBeneficiariosFinal.add(beneficiario);
									
								}

							}

						}
						System.out.println("beneficiarios bruto --> "+listaBeneficiarios.size());
						System.out.println("beneficiarios  final--<  "+lstBeneficiariosFinal.size());
						System.out.println("==============================================================");
						System.out.println("detalle.getId().getIntItemCuentaConcepto()--> "+detalle.getId().getIntItemCuentaConcepto());
						System.out.println("---------------------------------------------------------------------------------------------");
						System.out.println("lstBeneficiariosFinal--> "+lstBeneficiariosFinal);
						
						// cgd - 06.06.2013
						//expedienteLiquidacion.setListaBeneficiarioLiquidacion(grabarListaDinamicaBeneficiariosLiquidacion(lstBeneficiariosFinal));
						//expedienteLiquidacion.setListaBeneficiarioLiquidacion(grabarListaDinamicaBeneficiariosLiquidacion(lstBeneficiariosFinal, detalle));
					}
				}

			}*/
		}catch(BusinessException e){
			System.out.println("BusinessExceptionBusinessException--> "+e);
			throw e;
		}catch(Exception e1){
			System.out.println("ExceptionException--> "+e1);
			throw new BusinessException(e1);
		}
		return expedienteLiquidacion;
	}
	
	
	
	public ExpedienteLiquidacion modificarExpedienteLiquidacion(ExpedienteLiquidacion pExpedienteLiquidacion) throws BusinessException{
		ExpedienteLiquidacion expedienteLiquidacion = null;
		List<EstadoLiquidacion> listaEstadoLiquidacion = null;
		List<RequisitoLiquidacionComp> listaRequisitoLiquidacionComp = null;
//		EstadoLiquidacion estadoLiquidacion = null;
//		List<BeneficiarioLiquidacion> listaBeneficiarios = null;
		List<ExpedienteLiquidacionDetalle> listaDetalles = null;
		
		try{
			//1. expediente
			expedienteLiquidacion = boExpedienteLiquidacion.modificar(pExpedienteLiquidacion);
			
			// 2. estado
			listaEstadoLiquidacion = pExpedienteLiquidacion.getListaEstadoLiquidacion();
			
			if(listaEstadoLiquidacion!=null){
				grabarListaDinamicaEstadoLiquidacion(listaEstadoLiquidacion, expedienteLiquidacion.getId());
			}

			
			// 3. detalles
			listaDetalles = pExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle();
			if(listaDetalles != null && !listaDetalles.isEmpty()){
				boBeneficiarioLiquidacion.deletePorExpediente(expedienteLiquidacion.getId());
				expedienteLiquidacion.setListaExpedienteLiquidacionDetalle(grabarListaDinamicaDetalleLiquidacion(listaDetalles, expedienteLiquidacion.getId()));
				
			}
			
			//Grabar Lista de requisitos de Credito
			listaRequisitoLiquidacionComp = pExpedienteLiquidacion.getListaRequisitoLiquidacionComp();
			if(listaRequisitoLiquidacionComp!=null){
				expedienteLiquidacion.setListaRequisitoLiquidacionComp(grabarListaDinamicaRequisitoLiquidacion(listaRequisitoLiquidacionComp, expedienteLiquidacion.getId()));
				//expedientePrevision.setListaRequisitoPrevisionComp(grabarListaDinamicaRequisitoCredito(listaRequisitoPrevisionComp, expedientePrevision.getId()));
			}
			
			
		}catch(BusinessException e){
			System.out.println("BusinessExceptionBusinessException--> "+e);
			throw e;
		}catch(Exception e1){
			System.out.println("ExceptionException--> "+e1);
			throw new BusinessException(e1);
		}
		return expedienteLiquidacion;
	}
	
	
	public List<EstadoLiquidacion> grabarListaDinamicaEstadoLiquidacion(List<EstadoLiquidacion> lstEstadoLiquidacion, ExpedienteLiquidacionId pPK) throws BusinessException{
		EstadoLiquidacion estadoLiquidacion = null;
		EstadoLiquidacionId pk = null;
		EstadoLiquidacion estadoLiquidacionTemp = null;
		try{
			for(int i=0; i<lstEstadoLiquidacion.size(); i++){
				estadoLiquidacion = (EstadoLiquidacion) lstEstadoLiquidacion.get(i);
				if(estadoLiquidacion.getId()==null || estadoLiquidacion.getId().getIntItemEstado()==null){
					pk = new EstadoLiquidacionId();
					pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
					pk.setIntItemExpediente(pPK.getIntItemExpediente());
					//pk.setIntItemDetExpediente(pPK.get);
					estadoLiquidacion.setId(pk);
					estadoLiquidacion = boEstadoLiquidacion.grabar(estadoLiquidacion);
				}else{
					estadoLiquidacionTemp = boEstadoLiquidacion.getPorPk(estadoLiquidacion.getId());
					if(estadoLiquidacionTemp == null){
						estadoLiquidacion = boEstadoLiquidacion.grabar(estadoLiquidacion);
					}else{
						estadoLiquidacion = boEstadoLiquidacion.modificar(estadoLiquidacion);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstEstadoLiquidacion;
	}
	
	public List<BeneficiarioLiquidacion> grabarListaDinamicaBeneficiariosLiquidacion(List<BeneficiarioLiquidacion> lstBeneficiariosLiquidacion, ExpedienteLiquidacionDetalle pdetalle) throws BusinessException{
		BeneficiarioLiquidacion beneficiario = null;
		BeneficiarioLiquidacionId pk = null;
		BeneficiarioLiquidacion beneficiarioTemp = null;
//		List<ExpedienteLiquidacionDetalle> listaDetalleLiquidacion = null;
//		ExpedienteLiquidacionDetalle detalle = null;
		
		//listaDetalleLiquidacion = pExpediente.getListaExpedienteLiquidacionDetalle();
		
		try{

				System.out.println(""+lstBeneficiariosLiquidacion);
				for(int i=0; i<lstBeneficiariosLiquidacion.size(); i++){
					beneficiario = (BeneficiarioLiquidacion) lstBeneficiariosLiquidacion.get(i);
					
				//	for(int d=0; d<listaDetalleLiquidacion.size();d++){
						if(beneficiario.getId()==null || beneficiario.getId().getIntItemBeneficiario()==null){
							pk = new BeneficiarioLiquidacionId();
							
							pk.setIntCuenta(pdetalle.getId().getIntCuenta());
							pk.setIntItemCuentaConcepto(pdetalle.getId().getIntItemCuentaConcepto());
							pk.setIntItemExpediente(pdetalle.getId().getIntItemExpediente());
							pk.setIntPersEmpresa(pdetalle.getId().getIntPersEmpresa());
							pk.setIntPersEmpresaLiquidacion(pdetalle.getId().getIntPersEmpresaLiquidacion());
							
							beneficiario.setId(pk);
							beneficiario =  boBeneficiarioLiquidacion.grabar(beneficiario);
							
						}else{
							beneficiarioTemp = boBeneficiarioLiquidacion.getPorPk(beneficiario.getId());
							if(beneficiarioTemp == null){
								beneficiario = boBeneficiarioLiquidacion.grabar(beneficiario);
							}else{
								beneficiario = boBeneficiarioLiquidacion.modificar(beneficiario);
							}
						}
					//}
					
				}

		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstBeneficiariosLiquidacion;
	}
	
	public List<BeneficiarioLiquidacion> grabarListaDinamicaBeneficiariosLiquidacion(List<BeneficiarioLiquidacion> lstBeneficiariosLiquidacion) throws BusinessException{
		BeneficiarioLiquidacion beneficiario = null;
//		BeneficiarioLiquidacionId pk = null;
		BeneficiarioLiquidacion beneficiarioTemp = null;
//		List<ExpedienteLiquidacionDetalle> listaDetalleLiquidacion = null;
//		ExpedienteLiquidacionDetalle detalle = null;
				
		try{

				System.out.println(""+lstBeneficiariosLiquidacion);
				for(int i=0; i<lstBeneficiariosLiquidacion.size(); i++){
					beneficiario = (BeneficiarioLiquidacion) lstBeneficiariosLiquidacion.get(i);
					
						if(beneficiario.getId()==null || beneficiario.getId().getIntItemBeneficiario()==null){
							beneficiario =  boBeneficiarioLiquidacion.grabar(beneficiario);
							
						}else{
							beneficiarioTemp = boBeneficiarioLiquidacion.getPorPk(beneficiario.getId());
							if(beneficiarioTemp == null){
								beneficiario = boBeneficiarioLiquidacion.grabar(beneficiario);
							}else{
								beneficiario = boBeneficiarioLiquidacion.modificar(beneficiario);
							}
						}					
				}

		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstBeneficiariosLiquidacion;
	}
	
	public List<RequisitoLiquidacionComp> grabarListaDinamicaRequisitoLiquidacion(List<RequisitoLiquidacionComp> listaRequisitoLiquidacionComp, ExpedienteLiquidacionId pPK) throws BusinessException{
		RequisitoLiquidacion requisitoLiquidacion = null;
		RequisitoLiquidacionId pk = null;
		RequisitoLiquidacion requisitoLiquidacionTemp = null;
//		RequisitoLiquidacionId pkTemp = null;
		Archivo archivo = null;
		try{
			/*
			Date today = new Date();
			String strToday = Constante.sdf.format(today);
			Date dtToday = Constante.sdf.parse(strToday);
			*/
			GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			for(RequisitoLiquidacionComp requisitoLiquidacionComp : listaRequisitoLiquidacionComp){
				if(requisitoLiquidacionComp.getRequisitoLiquidacion()== null || requisitoLiquidacionComp.getRequisitoLiquidacion().getId()==null){
					//if(requisitoPrevisionComp.getRequisitoPrevision().getId()==null){
						requisitoLiquidacion = new RequisitoLiquidacion();
						pk = new RequisitoLiquidacionId();
						pk.setIntPersEmpresaLiquidacion(pPK.getIntPersEmpresaPk());
						//pk.setIntCuentaPk(pPK.getIntCuentaPk());
						pk.setIntItemExpediente(pPK.getIntItemExpediente());
						requisitoLiquidacion.setId(pk);
						
						if(requisitoLiquidacionComp.getArchivoAdjunto()==null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							if(requisitoLiquidacionComp.getArchivoAdjunto()==null){
								requisitoLiquidacionComp.setArchivoAdjunto(new Archivo());
								requisitoLiquidacionComp.getArchivoAdjunto().setId(new ArchivoId());
							}
							archivo.getId().setIntParaTipoCod(requisitoLiquidacionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
							archivo.setStrNombrearchivo(requisitoLiquidacionComp.getArchivoAdjunto().getStrNombrearchivo());
							archivo.setIntParaEstadoCod(new Integer(Constante.PARAM_T_ESTADOUNIVERSAL));
							archivo.setTsFechaRegistro(new Timestamp(new Date().getTime()));
							if(archivo.getId().getIntParaTipoCod()!=null){
								archivo = generalFacade.grabarArchivo(archivo);
								
								
							}else archivo = null;
							
							if(archivo==null){	
								archivo = new Archivo();
								archivo.setId(new ArchivoId());
							}
						}else{
							archivo = requisitoLiquidacionComp.getArchivoAdjunto();
							
						}
							requisitoLiquidacion.setIntPersEmpresaPk(requisitoLiquidacionComp.getDetalle().getId().getIntPersEmpresaPk());
							requisitoLiquidacion.setIntItemReqAut(requisitoLiquidacionComp.getDetalle().getId().getIntItemSolicitud());
							requisitoLiquidacion.setIntItemReqAutEstDetalle(requisitoLiquidacionComp.getDetalle().getId().getIntItemDetalle());
							requisitoLiquidacion.setIntParaTipoArchivo(requisitoLiquidacionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
							requisitoLiquidacion.setIntParaItemArchivo(archivo.getId().getIntItemArchivo());
							requisitoLiquidacion.setIntParaItemHistorico(archivo.getId().getIntItemHistorico());
							requisitoLiquidacion.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
							requisitoLiquidacion.setTsFechaRequisito(new Timestamp(new Date().getTime()));
							requisitoLiquidacion =   boRequisitoLiquidacion.grabar(requisitoLiquidacion);
							requisitoLiquidacionComp.setRequisitoLiquidacion(requisitoLiquidacion);
						//}
					//}
					
					
				}else{

					requisitoLiquidacionTemp = boRequisitoLiquidacion.getPorPk(requisitoLiquidacionComp.getRequisitoLiquidacion());

					if(requisitoLiquidacionTemp == null){
						requisitoLiquidacion = boRequisitoLiquidacion.grabar(requisitoLiquidacionComp.getRequisitoLiquidacion());
					}else{
						archivo = new Archivo();
						archivo.setId(new ArchivoId());
						requisitoLiquidacion = new RequisitoLiquidacion();
						pk = new RequisitoLiquidacionId();
						Integer intItemArchivo = null;
						Integer intParaTipoCod = null;
						
						if(requisitoLiquidacionComp.getArchivoAdjunto() != null){
							intItemArchivo = requisitoLiquidacionComp.getArchivoAdjunto().getId().getIntItemArchivo();
							intParaTipoCod = requisitoLiquidacionComp.getArchivoAdjunto().getId().getIntParaTipoCod();
							
							if(intItemArchivo != null && intParaTipoCod != null)
								archivo = generalFacade.getListaArchivoDeVersionFinalPorTipoYItem(intParaTipoCod, intItemArchivo);
						
							if(archivo != null || requisitoLiquidacionTemp.getIntParaItemArchivo() != null 
								|| requisitoLiquidacionTemp.getIntParaItemHistorico() != null
								|| requisitoLiquidacionTemp.getIntParaTipoArchivo() != null){
								
								requisitoLiquidacion.setId(requisitoLiquidacionComp.getRequisitoLiquidacion().getId());
								requisitoLiquidacion.setIntPersEmpresaPk(requisitoLiquidacionComp.getDetalle().getId().getIntPersEmpresaPk());
								requisitoLiquidacion.setIntItemReqAut(requisitoLiquidacionComp.getDetalle().getId().getIntItemSolicitud());
								requisitoLiquidacion.setIntItemReqAutEstDetalle(requisitoLiquidacionComp.getDetalle().getId().getIntItemDetalle());
								requisitoLiquidacion.setIntParaTipoArchivo(requisitoLiquidacionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
								requisitoLiquidacion.setIntParaItemArchivo(archivo.getId().getIntItemArchivo());
								requisitoLiquidacion.setIntParaItemHistorico(archivo.getId().getIntItemHistorico());
								requisitoLiquidacion.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
								requisitoLiquidacion.setTsFechaRequisito(new Timestamp(new Date().getTime()));
								requisitoLiquidacionComp.setRequisitoLiquidacion(requisitoLiquidacion);
								
								requisitoLiquidacion = boRequisitoLiquidacion.modificar(requisitoLiquidacionComp.getRequisitoLiquidacion());
								
							}else{
								
								requisitoLiquidacion = requisitoLiquidacionComp.getRequisitoLiquidacion();
							}
	
						}
	
					}
 
				}

			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaRequisitoLiquidacionComp;
	}
	
	/*public List<ExpedienteLiquidacionDetalle> grabarListaDinamicaDetalleLiquidacion(List<ExpedienteLiquidacionDetalle> lstDetalleLiquidacion, ExpedienteLiquidacionId pPK, CuentaConcepto cuentaAportes) throws BusinessException{
		ExpedienteLiquidacionDetalle detalle = null;
		ExpedienteLiquidacionDetalleId pk = null;
		ExpedienteLiquidacionDetalle detalleTemp = null;
		try{
			for(int i=0; i<lstDetalleLiquidacion.size(); i++){
				detalle = (ExpedienteLiquidacionDetalle) lstDetalleLiquidacion.get(i);
				if(detalle.getId()==null || detalle.getId().getIntItemExpediente()==null){
					pk = new ExpedienteLiquidacionDetalleId();
					pk.setIntPersEmpresaLiquidacion(pPK.getIntPersEmpresaPk());
					pk.setIntItemExpediente(pPK.getIntItemExpediente());
					
					pk.setIntItemCuentaConcepto(cuentaAportes.getId().getIntItemCuentaConcepto());
					pk.setIntPersEmpresa(cuentaAportes.getId().getIntPersEmpresaPk());
					pk.setIntCuenta(cuentaAportes.getId().getIntCuentaPk());
					
					detalle.setId(pk);
					detalle.setBdSaldo(cuentaAportes.getBdSaldo());
					detalle =  boExpedienteLiquidacionDetalle.grabar(detalle);
				}else{
					detalleTemp = boExpedienteLiquidacionDetalle.getPorPk(detalle.getId());
					if(detalleTemp == null){
						detalle = boExpedienteLiquidacionDetalle.grabar(detalle);
					}else{
						detalle = boExpedienteLiquidacionDetalle.modificar(detalle);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstDetalleLiquidacion;
	}*/
	
	public List<ExpedienteLiquidacionDetalle> grabarListaDinamicaDetalleLiquidacion(List<ExpedienteLiquidacionDetalle> lstDetalleLiquidacion, ExpedienteLiquidacionId pPK) throws BusinessException{
		ExpedienteLiquidacionDetalle detalle = null;
		ExpedienteLiquidacionDetalleId pk = null;
		ExpedienteLiquidacionDetalle detalleTemp = null;
		//modificarPorBeneficiario
		ConceptoFacadeRemote conceptofacade = null;
		
		try{
			conceptofacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			
			for(int i=0; i<lstDetalleLiquidacion.size(); i++){
				detalle = (ExpedienteLiquidacionDetalle) lstDetalleLiquidacion.get(i);
				if(detalle.getId()==null || detalle.getId().getIntItemExpediente()==null){
//					List<BeneficiarioLiquidacion> listaBeneficiarios = null;
					pk = new ExpedienteLiquidacionDetalleId();
					pk.setIntPersEmpresaLiquidacion(pPK.getIntPersEmpresaPk());
					pk.setIntItemExpediente(pPK.getIntItemExpediente());
					
					pk.setIntItemCuentaConcepto(lstDetalleLiquidacion.get(i).getCuentaConcepto().getId().getIntItemCuentaConcepto());
					pk.setIntPersEmpresa(lstDetalleLiquidacion.get(i).getCuentaConcepto().getId().getIntPersEmpresaPk());
					pk.setIntCuenta(lstDetalleLiquidacion.get(i).getCuentaConcepto().getId().getIntCuentaPk());
					
					detalle.setId(pk);
					detalle.setBdSaldo(lstDetalleLiquidacion.get(i).getCuentaConcepto().getBdSaldo());
					
					/*if(detalle.getListaBeneficiarioLiquidacion() != null && !detalle.getListaBeneficiarioLiquidacion().isEmpty()){
						listaBeneficiarios = new ArrayList<BeneficiarioLiquidacion>();
						listaBeneficiarios = detalle.getListaBeneficiarioLiquidacion();
					}*/
					detalle =  boExpedienteLiquidacionDetalle.grabar(detalle);
					
					if(detalle.getListaBeneficiarioLiquidacion()!=null && !detalle.getListaBeneficiarioLiquidacion().isEmpty()){
						//boBeneficiarioLiquidacion.deletePorExpediente(pPK);
						detalle.setListaBeneficiarioLiquidacion(grabarListaDinamicaBeneficiariosLiquidacion(detalle.getListaBeneficiarioLiquidacion(), detalle));
						if(detalle.getListaBeneficiarioLiquidacion() != null 
							&& !detalle.getListaBeneficiarioLiquidacion().isEmpty()){
							
							for (BeneficiarioLiquidacion beneficiarioLiquidacion : detalle.getListaBeneficiarioLiquidacion()) {
								CuentaDetalleBeneficio ctaDetBenf = new CuentaDetalleBeneficio();
								ctaDetBenf.setId(new CuentaDetalleBeneficioId());
								ctaDetBenf.getId().setIntCuentaPk(beneficiarioLiquidacion.getId().getIntCuenta());
								ctaDetBenf.getId().setIntItemCuentaConcepto(beneficiarioLiquidacion.getId().getIntItemCuentaConcepto());
								ctaDetBenf.getId().setIntPersEmpresaPk(beneficiarioLiquidacion.getId().getIntPersEmpresa());
								ctaDetBenf.setBdPorcentaje(beneficiarioLiquidacion.getBdPorcentajeBeneficio());
								ctaDetBenf.setIntItemVinculo(beneficiarioLiquidacion.getIntItemViculo());

								conceptofacade.modificarPorBeneficiario(ctaDetBenf);
								
							}
						}
						
					
					}
					
					
				}else{
					detalleTemp = boExpedienteLiquidacionDetalle.getPorPk(detalle.getId());
					if(detalleTemp == null){
						detalle = boExpedienteLiquidacionDetalle.grabar(detalle);
						if(detalle.getListaBeneficiarioLiquidacion()!=null && !detalle.getListaBeneficiarioLiquidacion().isEmpty()){
							//boBeneficiarioLiquidacion.deletePorExpediente(pPK);
							detalle.setListaBeneficiarioLiquidacion(grabarListaDinamicaBeneficiariosLiquidacion(detalle.getListaBeneficiarioLiquidacion(), detalle));
							
							if(detalle.getListaBeneficiarioLiquidacion() != null 
									&& !detalle.getListaBeneficiarioLiquidacion().isEmpty()){
									
									for (BeneficiarioLiquidacion beneficiarioLiquidacion : detalle.getListaBeneficiarioLiquidacion()) {
										CuentaDetalleBeneficio ctaDetBenf = new CuentaDetalleBeneficio();
										ctaDetBenf.setId(new CuentaDetalleBeneficioId());
										ctaDetBenf.getId().setIntCuentaPk(beneficiarioLiquidacion.getId().getIntCuenta());
										ctaDetBenf.getId().setIntItemCuentaConcepto(beneficiarioLiquidacion.getId().getIntItemCuentaConcepto());
										ctaDetBenf.getId().setIntPersEmpresaPk(beneficiarioLiquidacion.getId().getIntPersEmpresa());
										ctaDetBenf.setBdPorcentaje(beneficiarioLiquidacion.getBdPorcentajeBeneficio());
										ctaDetBenf.setIntItemVinculo(beneficiarioLiquidacion.getIntItemViculo());

										conceptofacade.modificarPorBeneficiario(ctaDetBenf);
										
									}
								}
						
						}
					}else{
						detalle = boExpedienteLiquidacionDetalle.modificar(detalle);
						if(detalle.getListaBeneficiarioLiquidacion()!=null && !detalle.getListaBeneficiarioLiquidacion().isEmpty()){
							//boBeneficiarioLiquidacion.deletePorExpediente(pPK);
							detalle.setListaBeneficiarioLiquidacion(grabarListaDinamicaBeneficiariosLiquidacion(detalle.getListaBeneficiarioLiquidacion(), detalle));
						
							if(detalle.getListaBeneficiarioLiquidacion() != null 
									&& !detalle.getListaBeneficiarioLiquidacion().isEmpty()){
									
									for (BeneficiarioLiquidacion beneficiarioLiquidacion : detalle.getListaBeneficiarioLiquidacion()) {
										CuentaDetalleBeneficio ctaDetBenf = new CuentaDetalleBeneficio();
										ctaDetBenf.setId(new CuentaDetalleBeneficioId());
										ctaDetBenf.getId().setIntCuentaPk(beneficiarioLiquidacion.getId().getIntCuenta());
										ctaDetBenf.getId().setIntItemCuentaConcepto(beneficiarioLiquidacion.getId().getIntItemCuentaConcepto());
										ctaDetBenf.getId().setIntPersEmpresaPk(beneficiarioLiquidacion.getId().getIntPersEmpresa());
										ctaDetBenf.setBdPorcentaje(beneficiarioLiquidacion.getBdPorcentajeBeneficio());
										ctaDetBenf.setIntItemVinculo(beneficiarioLiquidacion.getIntItemViculo());

										conceptofacade.modificarPorBeneficiario(ctaDetBenf);
										
									}
								}
						}
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstDetalleLiquidacion;
	}
	
	
	public ExpedienteLiquidacion getExpedienteLiquidacionCompletoPorIdExpedienteLiquidacion(ExpedienteLiquidacionId pId) throws BusinessException {
		ExpedienteLiquidacion expedienteLiquidacion = null;
		List<EstadoLiquidacion> listaEstadoLiquidacion = null;
		List<ExpedienteLiquidacionDetalle> listaDetalleExpediente = null;
		EstadoLiquidacion ultimoEstadoLiquidacion= null;
		EstadoLiquidacion primerEstadoLiquidacion= null;
//		List<BeneficiarioLiquidacion> listaBeneficiarios = null;
		
//		ExpedienteLiquidacionDetalle expedienteLiquidacionDet = null;
		//List<CronogramaCredito> listaCronogramaCredito = null;
		//List<GarantiaCredito> listaGarantiaCredito = null;
		List<RequisitoLiquidacion> listaRequisitoLiquidacion = null;
		List<RequisitoLiquidacionComp> listaRequisitoLiquidacionComp = null;
		RequisitoLiquidacionComp requisitoLiquidacionComp = null;
		ConfServDetalle detalle = null;
		Archivo archivo = null;
		TipoArchivo tipoArchivo = null;
		MyFile myFile = null;
//		SocioFacadeRemote socioFacade = null;
		ConfSolicitudFacadeLocal solicitudFacade = null;
		GeneralFacadeRemote generalFacade = null;
//		PersonaFacadeRemote personaFacade = null;
		try {
		
//				socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
				solicitudFacade = (ConfSolicitudFacadeLocal)EJBFactory.getLocal(ConfSolicitudFacadeLocal.class);
				generalFacade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
//				personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			
			
			expedienteLiquidacion = boExpedienteLiquidacion.getPorPk(pId);

			if(expedienteLiquidacion!=null){
				// 1. recuperamos estados
				listaEstadoLiquidacion = boEstadoLiquidacion.getPorExpediente(expedienteLiquidacion);
				if(listaEstadoLiquidacion!=null && listaEstadoLiquidacion.size()>0){
					expedienteLiquidacion.setListaEstadoLiquidacion(listaEstadoLiquidacion);
					
					// cargando el uiltimo estado
					ultimoEstadoLiquidacion = boEstadoLiquidacion.getMaxEstadoLiquidacionPorPkExpediente(expedienteLiquidacion);
					if(ultimoEstadoLiquidacion != null){
						expedienteLiquidacion.setEstadoLiquidacionUltimo(ultimoEstadoLiquidacion);
					}
					
					primerEstadoLiquidacion = obtenerPrimerEstadoLiquidacion(expedienteLiquidacion);
					if(primerEstadoLiquidacion != null){
						expedienteLiquidacion.setEstadoLiquidacionPrimero(primerEstadoLiquidacion);
					}
				}
				
				//2. Detalle de expediente
				
				listaDetalleExpediente = boExpedienteLiquidacionDet.getPorExpedienteLiquidacion(expedienteLiquidacion);
			
				
				
				if(listaDetalleExpediente != null && !listaDetalleExpediente.isEmpty()){
					List<ExpedienteLiquidacionDetalle> listaExpedienteDetalleReturn = null;
					listaExpedienteDetalleReturn = new ArrayList<ExpedienteLiquidacionDetalle>();
					
					for (ExpedienteLiquidacionDetalle expedienteDetalle : listaDetalleExpediente) {
						//ExpedienteLiquidacionDetalle expedienteLiquidacionDetalleReturn = new ExpedienteLiquidacionDetalle();
						List<BeneficiarioLiquidacion> listaBeneficiariosReturn = null;
						listaBeneficiariosReturn = boBeneficiarioLiquidacion.getPorExpedienteLiquidacionDetalle(expedienteDetalle);
						
						if(listaBeneficiariosReturn != null && !listaBeneficiariosReturn.isEmpty()){
							expedienteDetalle.setListaBeneficiarioLiquidacion(listaBeneficiariosReturn);	
						}
						listaExpedienteDetalleReturn.add(expedienteDetalle);
					}

					expedienteLiquidacion.setListaExpedienteLiquidacionDetalle(listaExpedienteDetalleReturn);


					// 3.beneficiarios
					//listaBeneficiarios = boBeneficiarioLiquidacion.getPorExpedienteLiquidacionDetalle(expedienteLiquidacionDet);
					//if(listaBeneficiarios != null || !listaBeneficiarios.isEmpty()){
					/*if(!listaBeneficiarios.isEmpty()){
						for(int k=0; k<listaBeneficiarios.size(); k++){
							if(listaBeneficiarios.get(k).getIntPersPersonaBeneficiario() != null){
								Integer intIdPersona = listaBeneficiarios.get(k).getIntPersPersonaBeneficiario();
								Persona persona = personaFacade.getPersonaNaturalPorIdPersona(intIdPersona);
								
								//getPersonaNaturalPorDocIdentidadYIdEmpresaYTipoVinculo(Integer intTipoIdentidad ,String strNroIdentidad, Integer intIdEmpresaSistema, Integer intTipoVinculo)
								// Integer intTipoIdentidad ,String strNroIdentidad, Integer intIdEmpresaSistema
								
								
								if (persona != null) {
									if (persona.getListaDocumento() != null
											&& persona.getListaDocumento().size() > 0) {
										for (Documento documento : persona.getListaDocumento()) {
											if (documento.getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))) {
												persona.setDocumento(documento);
												break;
											}
										}
									}
									listaBeneficiarios.get(k).setPersona(persona);
								}
							}	
						}
						// cgd - 06.06.2013
						// expedienteLiquidacion.setListaBeneficiarioLiquidacion(listaBeneficiarios);
					}*/

				}
				
				
				// 4. Requiisitos
				listaRequisitoLiquidacion = boRequisitoLiquidacion.getListaPorExpediente(expedienteLiquidacion.getId());
				
				if(listaRequisitoLiquidacion!=null && listaRequisitoLiquidacion.size()>0){
					listaRequisitoLiquidacionComp = new ArrayList<RequisitoLiquidacionComp>();
					for(RequisitoLiquidacion requisitoLiquidacion : listaRequisitoLiquidacion ){
							if(requisitoLiquidacion.getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
								requisitoLiquidacionComp = new RequisitoLiquidacionComp();
								detalle = new ConfServDetalle();
								detalle.setId(new ConfServDetalleId());
								archivo = new Archivo();
								archivo.setId(new ArchivoId());
								tipoArchivo = new TipoArchivo();
								myFile = new MyFile();
								requisitoLiquidacionComp.setRequisitoLiquidacion(requisitoLiquidacion);
								detalle.getId().setIntPersEmpresaPk(requisitoLiquidacion.getIntPersEmpresaPk());
								detalle.getId().setIntItemSolicitud(requisitoLiquidacion.getIntItemReqAut());
								detalle.getId().setIntItemDetalle(requisitoLiquidacion.getIntItemReqAutEstDetalle());
								detalle = solicitudFacade.getConfServDetallePorPk(detalle.getId());
								requisitoLiquidacionComp.setDetalle(detalle);
								archivo.getId().setIntParaTipoCod(requisitoLiquidacion.getIntParaTipoArchivo());
								archivo.getId().setIntItemArchivo(requisitoLiquidacion.getIntParaItemArchivo());
								archivo.getId().setIntItemHistorico(requisitoLiquidacion.getIntParaItemHistorico());
								
								archivo = generalFacade.getArchivoPorPK(archivo.getId());
								requisitoLiquidacionComp.setArchivoAdjunto(archivo);
								
								if(archivo !=null /*&& archivo.getFile() != null*/){
									tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoLiquidacion.getIntParaTipoArchivo());
									if(tipoArchivo!=null){								
										if(archivo.getId().getIntParaTipoCod()!= null && archivo.getId().getIntItemArchivo()!= null && 
												archivo.getId().getIntItemHistorico() != null/* && tipoArchivo.getStrRuta() != null && archivo.getStrNombrearchivo()!= null*/){
											
											byte[] byteImg = null;
											
											try {
												byteImg = FileUtil.getDataImage(tipoArchivo.getStrRuta()+ "\\" + archivo.getStrNombrearchivo());
												
											} catch (IOException e) {
												log.error("Nos e ubico los documentos adjuntos - getExpedienteLiquidacionCompletoPorIdExpedienteLiquidacion --> "+e);
												e.printStackTrace();
											}
											if(byteImg != null && byteImg.length != 0){
												myFile.setLength(byteImg.length);
												myFile.setName(archivo.getStrNombrearchivo());
												myFile.setData(byteImg);
												archivo.setRutaActual(tipoArchivo.getStrRuta());
												archivo.setStrNombrearchivo(archivo.getStrNombrearchivo());
												requisitoLiquidacionComp.setFileDocAdjunto(myFile);
												requisitoLiquidacionComp.setArchivoAdjunto(archivo);
											}
											
										}
			
									}
								}
								listaRequisitoLiquidacionComp.add(requisitoLiquidacionComp);
							}
						
						/*requisitoLiquidacionComp = new RequisitoLiquidacionComp();
						detalle = new ConfServDetalle();
						detalle.setId(new ConfServDetalleId());
						archivo = new Archivo();
						archivo.setId(new ArchivoId());
						tipoArchivo = new TipoArchivo();
						myFile = new MyFile();
						requisitoLiquidacionComp.setRequisitoLiquidacion(requisitoLiquidacion);
						detalle.getId().setIntPersEmpresaPk(requisitoLiquidacion.getIntPersEmpresaPk());
						detalle.getId().setIntItemSolicitud(requisitoLiquidacion.getIntItemReqAut());
						detalle.getId().setIntItemDetalle(requisitoLiquidacion.getIntItemReqAutEstDetalle());
						detalle = solicitudFacade.getConfServDetallePorPk(detalle.getId());
						
						requisitoLiquidacionComp.setDetalle(detalle);
						
						archivo.getId().setIntParaTipoCod(requisitoLiquidacion.getIntParaTipoArchivo());
						archivo.getId().setIntItemArchivo(requisitoLiquidacion.getIntParaItemArchivo());
						archivo.getId().setIntItemHistorico(requisitoLiquidacion.getIntParaItemHistorico());
						archivo = generalFacade.getArchivoPorPK(archivo.getId());
						
						requisitoLiquidacionComp.setArchivoAdjunto(archivo);
						if(archivo !=null){
							tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoLiquidacion.getIntParaTipoArchivo());
							if(tipoArchivo!=null){								
								if(archivo.getId().getIntParaTipoCod()!= null && archivo.getId().getIntItemArchivo()!= null && 
										archivo.getId().getIntItemHistorico() != null){
									
									byte[] byteImg = null;
									
									try {
										byteImg =  FileUtil.getDataImage(tipoArchivo.getStrRuta()+ "\\" + archivo.getStrNombrearchivo());
										
									} catch (IOException e1) {
										log.error(e1);
									}
									if(byteImg != null && byteImg.length != 0){
										myFile.setLength(byteImg.length);
										myFile.setName(archivo.getStrNombrearchivo());
										myFile.setData(byteImg);
										archivo.setRutaActual(tipoArchivo.getStrRuta());
										archivo.setStrNombrearchivo(archivo.getStrNombrearchivo());
										requisitoLiquidacionComp.setFileDocAdjunto(myFile);
										requisitoLiquidacionComp.setArchivoAdjunto(archivo);
									}
									
								}
							}
						}
						listaRequisitoLiquidacionComp.add(requisitoLiquidacionComp);*/
					}
					expedienteLiquidacion.setListaRequisitoLiquidacionComp(listaRequisitoLiquidacionComp);
				}
				
			}
		} catch(BusinessException e){
			log.error("BusinessException-getExpedienteLiquidacionCompletoPorIdExpedienteLiquidacion--->"+ e);
			throw e;
			
		} catch (EJBFactoryException e) {
			log.error("EJBFactoryException-getExpedienteLiquidacionCompletoPorIdExpedienteLiquidacion--->"+ e);
			e.printStackTrace();
		}
		
		return expedienteLiquidacion;
	}
	
	public List<ExpedienteLiquidacion> getListaExpedienteLiquidacionCompBusqueda() throws BusinessException{
		ExpedienteLiquidacion dto = null;
		List<ExpedienteLiquidacion> lista = null;
		List<ExpedienteLiquidacion> listaExpedienteLiquidacion = null;
		EstadoLiquidacion estadoLiquidacion;
		List<EstadoLiquidacion> listaEstadoLiquidacion = null;
		//List<CapacidadCredito> listaCapacidadCredito = null;
		SocioComp socioComp = null;
		Persona persona = null;
		Integer intIdPersona = null;
		PersonaFacadeRemote personaFacade = null;
		SocioFacadeRemote socioFacade = null;
		Natural natural = null;
		
		try{
			CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote) EJBFactory.getRemote(CuentaFacadeRemote.class);
			//private CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			
			listaExpedienteLiquidacion = boExpedienteLiquidacion.getListaCompleta();

			if(listaExpedienteLiquidacion != null && listaExpedienteLiquidacion.size()>0){
				lista = new ArrayList<ExpedienteLiquidacion>();
				for(ExpedienteLiquidacion expedienteLiquidacion : listaExpedienteLiquidacion){
					dto = new ExpedienteLiquidacion();
					// le cargamos el ultimo estado
					estadoLiquidacion = boEstadoLiquidacion.getMaxEstadoLiquidacionPorPkExpediente(expedienteLiquidacion);
					dto.setEstadoLiquidacionUltimo(estadoLiquidacion);

					listaEstadoLiquidacion = boEstadoLiquidacion.getPorExpediente(expedienteLiquidacion);
					expedienteLiquidacion.setListaEstadoLiquidacion(listaEstadoLiquidacion);

					if(!listaEstadoLiquidacion.isEmpty()){
						//cargamos el primer estado	
						//------------------------------------------------------------------!!
						Integer  menorItemEstado = listaEstadoLiquidacion.get(0).getId().getIntItemEstado();
						Integer intPosicion = 0;
						for(int i=0; i<listaEstadoLiquidacion.size();i++){
							if(listaEstadoLiquidacion.get(i).getId().getIntItemEstado().intValue() < menorItemEstado){
								//menorItemEstado = listaEstadoPrevision.get(i).get;
								intPosicion = i;
							}	
						}
						EstadoLiquidacion primerEstado = listaEstadoLiquidacion.get(intPosicion);
						dto.setEstadoLiquidacionPrimero(primerEstado);
						
						natural = personaFacade.getNaturalPorPK(primerEstado.getIntPersEmpresaEstado());
						if(natural != null){
							dto.setStrUserRegistro(natural.getStrApellidoPaterno()+" "+natural.getStrApellidoMaterno()+", "+natural.getStrNombres());
							dto.setStrFechaUserRegistro(""+(Date)primerEstado.getTsFechaEstado());	
						}else{
							dto.setStrUserRegistro("Usuario NO encontrado");
							dto.setStrFechaUserRegistro("Fecha NO encontrada");	
						}
						
						//---------------------------------------------------------|

					}

					if(expedienteLiquidacion.getId() != null){
						
						CuentaId cuentaIdSocio = new CuentaId();
						Cuenta cuentaSocio = null;
						List<CuentaIntegrante> listaCuentaIntegranteSocio = null;
						cuentaIdSocio.setIntPersEmpresaPk(expedienteLiquidacion.getId().getIntPersEmpresaPk());
						
						cuentaSocio = cuentaFacade.getCuentaPorId(cuentaIdSocio);
						//if(cuentaSocio != null){
							
							try {
								listaCuentaIntegranteSocio = cuentaFacade.getListaCuentaIntegrantePorPKCuenta(cuentaSocio.getId());

							} catch (Exception e) {
								log.info("listaCuentaIntegranteSociolistaCuentaIntegranteSocio---> "+e);
							}
							//listaCuentaIntegranteSocio = ;
							
							if(listaCuentaIntegranteSocio != null){
								//intIdPersona = beanExpedientePrevision.getIntPersEmpresa();
								intIdPersona = listaCuentaIntegranteSocio.get(0).getId().getIntPersonaIntegrante();
								persona = personaFacade.getPersonaNaturalPorIdPersona(intIdPersona);
								if (persona != null) {
									if (persona.getListaDocumento() != null
											&& persona.getListaDocumento().size() > 0) {
										for (Documento documento : persona.getListaDocumento()) {
											if (documento.getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))) {
												persona.setDocumento(documento);
												break;
											}
										}
									}							
									
									socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(
												new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
												persona.getDocumento().getStrNumeroIdentidad(),
												Constante.PARAM_EMPRESASESION);

									for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
										if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)) {
											socioComp.getSocio().setSocioEstructura(socioEstructura);
											dto.setSocioComp(socioComp);
										}
									}
									;
								}
							}	

					}
					
					//---------------------------------------------------------|

					lista.add(dto);
				}
				//dto.setListaExpedienteCredito(listaExpedienteCredito);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	public List<ExpedientePrevisionComp> getListaExpedientePrevisionCompBusqueda(ExpedientePrevisionComp o) throws BusinessException{
		ExpedientePrevisionComp dto = null;
		List<ExpedientePrevisionComp> lista = null;
		List<ExpedientePrevision> listaExpedientePrevision = null;
		EstadoPrevision estadoPrevision;
		List<EstadoPrevision> listaEstadoPrevision = null;
		//List<CapacidadCredito> listaCapacidadCredito = null;
		SocioComp socioComp = null;
		Persona persona = null;
		Integer intIdPersona = null;
		PersonaFacadeRemote personaFacade = null;
		SocioFacadeRemote socioFacade = null;
		Natural natural = null;
		
		try{
			CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote) EJBFactory.getRemote(CuentaFacadeRemote.class);
			//private CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			
			listaExpedientePrevision = boExpedientePrevision.getListaExpedientePrevisionBusqueda();

			if(listaExpedientePrevision != null && listaExpedientePrevision.size()>0){
				lista = new ArrayList<ExpedientePrevisionComp>();
				for(ExpedientePrevision expedientePrevision : listaExpedientePrevision){
					dto = new ExpedientePrevisionComp();
					// le cargamos el ultimo estado
					estadoPrevision = boEstadoPrevision.getMaxEstadoPrevisionPorPokExpediente(expedientePrevision);
					dto.setEstadoPrevision(estadoPrevision);
					
					
					listaEstadoPrevision = boEstadoPrevision.getPorExpediente(expedientePrevision);
					dto.setExpedientePrevision(expedientePrevision);
					expedientePrevision.setListaEstadoPrevision(listaEstadoPrevision);

					if(!listaEstadoPrevision.isEmpty()){
						//cargamos el primer estado	
						//------------------------------------------------------------------!!
						Integer  menorItemEstado = listaEstadoPrevision.get(0).getId().getIntItemEstado();
						Integer intPosicion = 0;
						for(int i=0; i<listaEstadoPrevision.size();i++){
							if(listaEstadoPrevision.get(i).getId().getIntItemEstado().intValue() < menorItemEstado){
								//menorItemEstado = listaEstadoPrevision.get(i).get;
								intPosicion = i;
							}	
						}
						EstadoPrevision primerEstado = listaEstadoPrevision.get(intPosicion);

						natural = personaFacade.getNaturalPorPK(primerEstado.getIntPersEmpresaEstado());
						if(natural != null){
							dto.setStrUserRegistro(natural.getStrApellidoPaterno()+" "+natural.getStrApellidoMaterno()+", "+natural.getStrNombres());
							dto.setStrFechaUserRegistro(""+(Date)primerEstado.getTsFechaEstado());	
						}
						
						//---------------------------------------------------------|

					}

					if(expedientePrevision.getId() != null){
						
						CuentaId cuentaIdSocio = new CuentaId();
						Cuenta cuentaSocio = null;
						List<CuentaIntegrante> listaCuentaIntegranteSocio = null;
						cuentaIdSocio.setIntPersEmpresaPk(expedientePrevision.getId().getIntPersEmpresaPk());
						cuentaIdSocio.setIntCuenta(expedientePrevision.getId().getIntCuentaPk());
						
						cuentaSocio = cuentaFacade.getCuentaPorId(cuentaIdSocio);
						//if(cuentaSocio != null){
							
							try {
								listaCuentaIntegranteSocio = cuentaFacade.getListaCuentaIntegrantePorPKCuenta(cuentaSocio.getId());

							} catch (Exception e) {
								log.info("listaCuentaIntegranteSociolistaCuentaIntegranteSocio---> "+e);
							}
							//listaCuentaIntegranteSocio = ;
							
							if(listaCuentaIntegranteSocio != null){
								//intIdPersona = beanExpedientePrevision.getIntPersEmpresa();
								intIdPersona = listaCuentaIntegranteSocio.get(0).getId().getIntPersonaIntegrante();
								persona = personaFacade.getPersonaNaturalPorIdPersona(intIdPersona);
								if (persona != null) {
									if (persona.getListaDocumento() != null
											&& persona.getListaDocumento().size() > 0) {
										for (Documento documento : persona.getListaDocumento()) {
											if (documento.getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))) {
												persona.setDocumento(documento);
												break;
											}
										}
									}							
									
									socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(
												new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
												persona.getDocumento().getStrNumeroIdentidad(),
												Constante.PARAM_EMPRESASESION);

									for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
										if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)) {
											socioComp.getSocio().setSocioEstructura(socioEstructura);
											dto.setSocioComp(socioComp);
										}
									}
									;
								}
							}	

					}
					
					//---------------------------------------------------------|

					lista.add(dto);
				}
				//dto.setListaExpedienteCredito(listaExpedienteCredito);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	

	public List<ExpedienteLiquidacion> buscarExpedienteParaLiquidacion(List<Persona> listaPersonaFiltro, Integer intTipoCreditoFiltro,
			EstadoLiquidacion estadoLiquidacionFiltro, Integer intItemExpedienteFiltro, Integer intTipoBusquedaSucursal, 
			Integer intIdSucursalFiltro, Integer intIdSubsucursalFiltro) throws BusinessException{
		
		List<ExpedienteLiquidacion> listaExpedienteLiquidacion = new ArrayList<ExpedienteLiquidacion>();
		try{
			CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote) EJBFactory.getRemote(CuentaFacadeRemote.class);
			EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			Natural natural = null;
//			EstadoLiquidacion primerEstado = null;
			
			Integer INT_SI_AÑADIR = 1;
//			Integer INT_NO_AÑADIR = 0;
			
			List<Cuenta> listaCuenta = new ArrayList<Cuenta>();			
			List<CuentaIntegrante> listaCuentaIntegrante = new ArrayList<CuentaIntegrante>();
			List<ExpedienteLiquidacionDetalle> listaExpedienteLiquidacionDetalle = new ArrayList<ExpedienteLiquidacionDetalle>();
			
			Integer intIdEmpresa = estadoLiquidacionFiltro.getId().getIntPersEmpresaPk();
//			Integer intParaTipoCreditoFiltro = intTipoCreditoFiltro;
//			Integer intParaEstadoCreditoFiltro = estadoLiquidacionFiltro.getIntParaEstado();
			
			
			//Si se ha hecho un filtro de personas
			if(listaPersonaFiltro != null && !listaPersonaFiltro.isEmpty()){
				for(Persona persona : listaPersonaFiltro){
					List<CuentaIntegrante> listaCuentaIntegranteTemp = cuentaFacade.getCuentaIntegrantePorIdPersona(persona.getIntIdPersona(), intIdEmpresa);
					if(listaCuentaIntegranteTemp != null)	listaCuentaIntegrante.addAll(listaCuentaIntegranteTemp);
				}
			//Si no se ha hecho un filtro de personas
			}else{
				listaCuentaIntegrante = cuentaFacade.getCuentaIntegrantePorIdPersona(null, intIdEmpresa);
				listaExpedienteLiquidacion = boExpedienteLiquidacion.getPorIdEmpresa(intIdEmpresa);
				for(ExpedienteLiquidacion expedienteLiquidacion : listaExpedienteLiquidacion){
					expedienteLiquidacion.setListaExpedienteLiquidacionDetalle(obtenerExpedienteLiquidacionDetalle(expedienteLiquidacion));					
				}
			}
			
			HashSet<Integer> hashSetIntCuenta = new HashSet<Integer>();
			for(CuentaIntegrante cuentaIntegrante : listaCuentaIntegrante){
				if(cuentaIntegrante.getIntParaTipoIntegranteCod().equals(Constante.TIPOINTEGRANTE_ADMINISTRADOR)){
					//log.info("CI per:"+cuentaIntegrante.getId().getIntPersonaIntegrante());
					hashSetIntCuenta.add(cuentaIntegrante.getId().getIntCuenta());
				}
			}
			
			for(Integer intCuenta : hashSetIntCuenta){
				//log.info(intCuenta);
				CuentaId cuentaId = new CuentaId();
				cuentaId.setIntPersEmpresaPk(intIdEmpresa);
				cuentaId.setIntCuenta(intCuenta);
				Cuenta cuenta = new Cuenta();
				cuenta.setId(cuentaId);
				cuenta = cuentaFacade.getCuentaPorIdCuenta(cuenta);
				if(cuenta!=null && cuenta.getId()!=null){
					//log.info("size:"+cuenta.getListaIntegrante().size());
					listaCuenta.add(cuenta);					
				}				
			}
			
			List<ExpedienteLiquidacionDetalle> listaExpedienteLiquidacionDetalleTemp = null;
			//log.info("intParaTipoCreditoFiltro:"+intParaTipoCreditoFiltro);
			for(Cuenta cuenta : listaCuenta){
				//log.info(cuenta);
				//log.info("size:"+cuenta.getListaIntegrante().size());
				listaExpedienteLiquidacionDetalleTemp = boExpedienteLiquidacionDetalle.getPorCuentaId(cuenta.getId());
				for(ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle : listaExpedienteLiquidacionDetalleTemp){
					expedienteLiquidacionDetalle.setCuenta(cuenta);
					listaExpedienteLiquidacionDetalle.add(expedienteLiquidacionDetalle);
				}
				
			}
			
			HashSet<Integer> hashSetIntItemExpediente = new HashSet<Integer>();			
			for(ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle : listaExpedienteLiquidacionDetalle){
				boolean pasaFiltroItem = Boolean.FALSE;
				//log.info(expedienteLiquidacionDetalle);
				if(intItemExpedienteFiltro!=null && expedienteLiquidacionDetalle.getId().getIntItemExpediente().equals(intItemExpedienteFiltro)){
					pasaFiltroItem = Boolean.TRUE;
				}else if(intItemExpedienteFiltro==null){
					pasaFiltroItem = Boolean.TRUE;
				}
				
				if(pasaFiltroItem){
					/*if(intParaTipoCreditoFiltro!=null && expedientePrevisionTemp.getIntParaDocumentoGeneral().equals(intParaTipoCreditoFiltro)){
						listaExpedienteLiquidacion.add(expedientePrevisionTemp);
					}else if(intParaTipoCreditoFiltro==null){
						listaExpedienteLiquidacion.add(expedientePrevisionTemp);
					}*/
					hashSetIntItemExpediente.add(expedienteLiquidacionDetalle.getId().getIntItemExpediente());
				}
			}
			
			if(listaExpedienteLiquidacion.isEmpty()){
				for(Integer intItemExpediente : hashSetIntItemExpediente){
					//log.info("intIdEmpresa:"+intIdEmpresa+" intItemExpediente:"+intItemExpediente);
					ExpedienteLiquidacion expedienteLiquidacion = boExpedienteLiquidacion.getPorPk(intIdEmpresa, intItemExpediente);
					List<ExpedienteLiquidacionDetalle> listaTemp = boExpedienteLiquidacionDetalle.getPorExpedienteLiquidacion(expedienteLiquidacion);
					for(ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle : listaTemp){
						for(Cuenta cuenta : listaCuenta){
							if(cuenta.getId().getIntCuenta().equals(expedienteLiquidacionDetalle.getId().getIntCuenta())
							&& cuenta.getId().getIntPersEmpresaPk().equals(expedienteLiquidacionDetalle.getId().getIntPersEmpresa())){
								expedienteLiquidacionDetalle.setCuenta(cuenta);
								break;
							}
						}
					}
					expedienteLiquidacion.setListaExpedienteLiquidacionDetalle(listaTemp);
					listaExpedienteLiquidacion.add(expedienteLiquidacion);
				}
			}			
			
			List<ExpedienteLiquidacion> listaTemp = new ArrayList<ExpedienteLiquidacion>();
			for(ExpedienteLiquidacion expedienteLiquidacion : listaExpedienteLiquidacion){
				
				EstadoLiquidacion estadoLiquidacionUltimo = obtenerUltimoEstadoLiquidacion(expedienteLiquidacion, INT_SI_AÑADIR);
				EstadoLiquidacion estadoLiquidacionPrimero = obtenerPrimerEstadoLiquidacion(expedienteLiquidacion);
				
				
				expedienteLiquidacion.setEstadoLiquidacionPrimero(estadoLiquidacionPrimero);
				
				natural = personaFacade.getNaturalPorPK(estadoLiquidacionPrimero.getIntPersUsuarioEstado());
				if(natural != null){
					expedienteLiquidacion.setStrUserRegistro(natural.getStrApellidoPaterno()+" "+natural.getStrApellidoMaterno()+", "+natural.getStrNombres());
					expedienteLiquidacion.setStrFechaUserRegistro(""+(Date)estadoLiquidacionPrimero.getTsFechaEstado());	
				}	

				boolean pasaFiltroEstado = Boolean.TRUE;
				//boolean pasaFiltroEstado = Boolean.FALSE;
				//Si se ha seleccionado un intParaEstadoCreditoFiltro en la busqueda
				// COMWENTADO PRO PREUBAS
				/*if(intParaEstadoCreditoFiltro!=null && estadoLiquidacionUltimo.getIntParaEstado().equals(intParaEstadoCreditoFiltro)){
					pasaFiltroEstado = Boolean.TRUE;
					
				//si no se a seleccionado un intParaEstadoCreditoFiltro en la busqueda, solo podemos traer registros en estado
				//aprobado o girado
				}else if(intParaEstadoCreditoFiltro==null){
					if(estadoLiquidacionUltimo.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO) 
					|| estadoLiquidacionUltimo.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO)){
						pasaFiltroEstado = Boolean.TRUE;
					}
				}*/
				
				if(pasaFiltroEstado){
					Sucursal sucursal = new Sucursal();
					sucursal.getId().setIntIdSucursal(estadoLiquidacionUltimo.getIntSucuIdSucursal());
					sucursal = empresaFacade.getSucursalPorPK(sucursal);
					//log.info(sucursal);
					estadoLiquidacionUltimo.setSucursal(sucursal);
					estadoLiquidacionUltimo.setSubsucursal(empresaFacade.getSubSucursalPorIdSubSucursal(estadoLiquidacionUltimo.getIntSucuIdSucursal()));
					expedienteLiquidacion.setEstadoLiquidacionUltimo(estadoLiquidacionUltimo);
					expedienteLiquidacion.setEstadoLiquidacionPrimero(estadoLiquidacionPrimero);

					//Necesitamos agregar para la IU el estado en el que se aprobo el expediente
					/*EstadoPrevision estadoPrevisionAprobado = null;
					if(estadoPrevisionUltimo.equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)){
						estadoPrevisionAprobado = estadoPrevisionUltimo;
					}else{
						estadoPrevisionAprobado = obtenerUltimoEstadoPrevision(expedientePrevision, Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO);
					}
					expedientePrevision.setEstadoPrevisionAprobado(estadoPrevisionAprobado);
					*/
					listaTemp.add(expedienteLiquidacion);
				}
			}
			System.out.println("aaaaaaaaaaaaaaaaa"+listaTemp.size());
			System.out.println("bbbbbbbbbbbbbbbbbb"+listaExpedienteLiquidacion.size());
			listaExpedienteLiquidacion = listaTemp;		
			
			
			
			if(intTipoBusquedaSucursal != null && intIdSucursalFiltro != null){
				log.info("--busqueda sucursal");
				listaExpedienteLiquidacion = manejarBusquedaSucursal(intTipoBusquedaSucursal, intIdSucursalFiltro, intIdSubsucursalFiltro, 
						intIdEmpresa, listaExpedienteLiquidacion);
			}
			//cae mal
			for(ExpedienteLiquidacion expedienteLiquidacion : listaExpedienteLiquidacion){
				CuentaIntegrante cuentaIntegrante = expedienteLiquidacion.getListaExpedienteLiquidacionDetalle().get(0).getCuenta().getListaIntegrante().get(0);
				Persona persona = personaFacade.getPersonaPorPK(cuentaIntegrante.getId().getIntPersonaIntegrante());
				//log.info(persona);
				expedienteLiquidacion.setPersonaAdministra(persona);
			}
			
			if(estadoLiquidacionFiltro.getDtFechaEstadoDesde()!=null || estadoLiquidacionFiltro.getDtFechaEstadoHasta()!=null){
				log.info("--busqueda fechas");
				listaExpedienteLiquidacion = manejarFiltroFechas(listaExpedienteLiquidacion, estadoLiquidacionFiltro);
			}
		}catch(BusinessException e){
			System.out.println("BusinessException--> "+e);
			throw e;
		}catch(Exception e){
			System.out.println("ExceptionException--> "+e);
			throw new BusinessException(e);
		}
		return listaExpedienteLiquidacion;
	}
	
	
	private List<ExpedienteLiquidacionDetalle> obtenerExpedienteLiquidacionDetalle(ExpedienteLiquidacion expedienteLiquidacion)throws Exception{
		CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote) EJBFactory.getRemote(CuentaFacadeRemote.class);
		
		List<ExpedienteLiquidacionDetalle> listaExpedienteLiquidacionDetalle = boExpedienteLiquidacionDetalle.getPorExpedienteLiquidacion(expedienteLiquidacion);
		for(ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle : listaExpedienteLiquidacionDetalle){
			Cuenta cuenta = new Cuenta();
			cuenta.setId(new CuentaId());
			cuenta.getId().setIntPersEmpresaPk(expedienteLiquidacionDetalle.getId().getIntPersEmpresa());
			cuenta.getId().setIntCuenta(expedienteLiquidacionDetalle.getId().getIntCuenta());
			expedienteLiquidacionDetalle.setCuenta(cuentaFacade.getCuentaPorIdCuenta(cuenta));
		}
		return listaExpedienteLiquidacionDetalle;
	}
	
	private EstadoLiquidacion obtenerUltimoEstadoLiquidacion(ExpedienteLiquidacion expedienteLiquidacion, Integer intAñadirLista)throws BusinessException{
		EstadoLiquidacion estadoLiquidacionUltimo = new EstadoLiquidacion();
		try{
			Integer INT_SI_AÑADIR = 1;
//			Integer INT_NO_AÑADIR = 0;
			if(intAñadirLista.equals(INT_SI_AÑADIR)){
				expedienteLiquidacion.setListaEstadoLiquidacion(boEstadoLiquidacion.getPorExpediente(expedienteLiquidacion));
			}			
			
			estadoLiquidacionUltimo.getId().setIntItemEstado(0);
			for(EstadoLiquidacion estadoLiquidacion : expedienteLiquidacion.getListaEstadoLiquidacion()){
				if(estadoLiquidacion.getId().getIntItemEstado().compareTo(estadoLiquidacionUltimo.getId().getIntItemEstado())>0){
					estadoLiquidacionUltimo = estadoLiquidacion;
				}
			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return estadoLiquidacionUltimo;
	}

	private EstadoLiquidacion obtenerPrimerEstadoLiquidacion(ExpedienteLiquidacion expedienteLiquidacion)throws BusinessException{
		List<EstadoLiquidacion> listaEstadoLiquidacion = null;
//		PersonaFacadeRemote personaFacade = null;
//		SocioFacadeRemote socioFacade = null;
//		Natural natural = null;
		EstadoLiquidacion primerEstado = null;
		try{
//			socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
//			personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			
			listaEstadoLiquidacion = boEstadoLiquidacion.getPorExpediente(expedienteLiquidacion);
		
			if(!listaEstadoLiquidacion.isEmpty()){
				//cargamos el primer estado	
				//------------------------------------------------------------------!!
				Integer  menorItemEstado = listaEstadoLiquidacion.get(0).getId().getIntItemEstado();
				Integer intPosicion = 0;
				for(int i=0; i<listaEstadoLiquidacion.size();i++){
					if(listaEstadoLiquidacion.get(i).getId().getIntItemEstado().intValue() < menorItemEstado){
						intPosicion = i;
					}	
				}
				primerEstado = listaEstadoLiquidacion.get(intPosicion);

			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return primerEstado;
	}
	
	
	private List<ExpedienteLiquidacion> manejarBusquedaSucursal(Integer intTipoBusquedaSucursal, Integer intIdSucursalFiltro, Integer intIdSubsucursalFiltro, 
			Integer intIdEmpresa, List<ExpedienteLiquidacion> listaExpedienteLiquidacion) throws Exception{
		
		SocioFacadeRemote socioFacade = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
		EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
		List<ExpedienteLiquidacion> listaTemp = new ArrayList<ExpedienteLiquidacion>();
		
		if(intTipoBusquedaSucursal.equals(Constante.PARAM_T_TIPOSUCURSALBUSQUEDA_SOCIO) && intIdSucursalFiltro!=null){
			for(ExpedienteLiquidacion expedienteLiquidacion : listaExpedienteLiquidacion){
				for(ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle : expedienteLiquidacion.getListaExpedienteLiquidacionDetalle()){
					for(CuentaIntegrante cuentaIntegrante : expedienteLiquidacionDetalle.getCuenta().getListaIntegrante()){
						SocioEstructura socioEstructura = (socioFacade.getListaSocioEstrucuraPorIdPersona(
								cuentaIntegrante.getIntPersonaUsuario(), intIdEmpresa)).get(0);
						if(intIdSucursalFiltro.intValue()>0){
							if(socioEstructura.getIntIdSucursalAdministra().equals(intIdSucursalFiltro)){
								if(intIdSubsucursalFiltro!=null && intIdSubsucursalFiltro.equals(socioEstructura.getIntIdSubsucurAdministra())){
									listaTemp.add(expedienteLiquidacion);
								}else if(intIdSubsucursalFiltro==null){
									listaTemp.add(expedienteLiquidacion);
								}
							}
						}else{
							Integer intTotalSucursal = intIdSucursalFiltro;
							Sucursal sucursal = new Sucursal();
							sucursal.getId().setIntPersEmpresaPk(intIdEmpresa);
							sucursal.getId().setIntIdSucursal(socioEstructura.getIntIdSucursalAdministra());
							sucursal = empresaFacade.getSucursalPorPK(sucursal);						
							if(validarTotalSucursal(sucursal.getIntIdTipoSucursal(), intTotalSucursal)){
								listaTemp.add(expedienteLiquidacion);
							}
						}
					}					
				}
			}
		
		}else if(intTipoBusquedaSucursal.equals(Constante.PARAM_T_TIPOSUCURSALBUSQUEDA_USUARIO) && intIdSucursalFiltro!=null){
			for(ExpedienteLiquidacion expedienteLiquidacion : listaExpedienteLiquidacion){
				EstadoLiquidacion estadoLiquidacionUltimo = expedienteLiquidacion.getEstadoLiquidacionUltimo();
				if(intIdSucursalFiltro.intValue()>0){
					if(estadoLiquidacionUltimo.getIntSucuIdSucursal().equals(intIdSucursalFiltro)){
						if(intIdSubsucursalFiltro!=null && intIdSubsucursalFiltro.equals(estadoLiquidacionUltimo.getIntSucuIdSucursal())){
							listaTemp.add(expedienteLiquidacion);
						}else if(intIdSubsucursalFiltro==null){
							listaTemp.add(expedienteLiquidacion);
						}
					}
				}else{
					Integer intTotalSucursal = intIdSucursalFiltro;
					Sucursal sucursal = new Sucursal();
					sucursal.getId().setIntPersEmpresaPk(intIdEmpresa);
					sucursal.getId().setIntIdSucursal(estadoLiquidacionUltimo.getIntSucuIdSucursal());
					sucursal = empresaFacade.getSucursalPorPK(sucursal);						
					if(validarTotalSucursal(sucursal.getIntIdTipoSucursal(), intTotalSucursal)){
						listaTemp.add(expedienteLiquidacion);
					}
				}
			}
		}
		
		return listaTemp;
	}
	
	private List<ExpedienteLiquidacion> manejarFiltroFechas(List<ExpedienteLiquidacion> listaExpedienteLiquidacion, EstadoLiquidacion estado)throws Exception{		
		if(estado.getDtFechaEstadoDesde()!=null){
			List<ExpedienteLiquidacion> listaTemp = new ArrayList<ExpedienteLiquidacion>();
			for(ExpedienteLiquidacion expedientePrevision : listaExpedienteLiquidacion){				
				if(expedientePrevision.getEstadoLiquidacionUltimo().getTsFechaEstado().compareTo(estado.getDtFechaEstadoDesde())>=0){
					listaTemp.add(expedientePrevision);
				}
			}
			listaExpedienteLiquidacion = listaTemp;
		}		
		if(estado.getDtFechaEstadoHasta()!=null){
			List<ExpedienteLiquidacion> listaTemp = new ArrayList<ExpedienteLiquidacion>();
			for(ExpedienteLiquidacion expedienteCredito : listaExpedienteLiquidacion){				
				if(expedienteCredito.getEstadoLiquidacionUltimo().getTsFechaEstado().compareTo(estado.getDtFechaEstadoHasta())<=0){
					listaTemp.add(expedienteCredito);
				}
			}
			listaExpedienteLiquidacion = listaTemp;
		}
		return listaExpedienteLiquidacion;
	}
	
	
	private boolean validarTotalSucursal(Integer intTipoSucursal, Integer intTotalSucursal){
		boolean exito = Boolean.FALSE;
		
		if(intTipoSucursal.equals(Constante.PARAM_T_TIPOSUCURSAL_AGENCIA)
		&& intTotalSucursal.equals(Constante.PARAM_T_TOTALESSUCURSALES_AGENCIAS)){
			exito = Boolean.TRUE;
		
		}else if(intTipoSucursal.equals(Constante.PARAM_T_TIPOSUCURSAL_FILIAL)
			&& intTotalSucursal.equals(Constante.PARAM_T_TOTALESSUCURSALES_FILIALES)){
			exito = Boolean.TRUE;
		
		}else if(intTipoSucursal.equals(Constante.PARAM_T_TIPOSUCURSAL_SEDECENTRAL)
			&& intTotalSucursal.equals(Constante.PARAM_T_TOTALESSUCURSALES_SEDE)){
			exito = Boolean.TRUE;
		
		}else if(intTipoSucursal.equals(Constante.PARAM_T_TIPOSUCURSAL_OFICINAPRINCIPAL)
			&& intTotalSucursal.equals(Constante.PARAM_T_TOTALESSUCURSALES_OFICINAPRINCIPAL)){
			exito = Boolean.TRUE;
		
		}else if(intTotalSucursal.equals(Constante.PARAM_T_TOTALESSUCURSALES_SUCURSALES)){
			exito = Boolean.TRUE;
		}
		
		return exito;
	}
	
	
	//---------------autorizacion -------------------------------|
	
	
public ExpedienteLiquidacion grabarAutorizacionLiquidacion(ExpedienteLiquidacion pExpedienteLiquidacion) throws BusinessException{
	List<AutorizaLiquidacion> listaAutorizaLiquidacion = null;
	List<AutorizaVerificaLiquidacion> listaAutorizaVerificacion = null;
//	List<AutorizaVerificaLiquidacion> listaAutorizaVerificacionTemp = null;
	try {
		listaAutorizaLiquidacion = pExpedienteLiquidacion.getListaAutorizaLiquidacion();
		if(listaAutorizaLiquidacion!=null && !listaAutorizaLiquidacion.isEmpty()){
			grabarListaDinamicaAutorizaLiquidacion(listaAutorizaLiquidacion, pExpedienteLiquidacion.getId());
		}
		
		listaAutorizaVerificacion = pExpedienteLiquidacion.getListaAutorizaVerificaLiquidacion();
		if(listaAutorizaVerificacion!=null && !listaAutorizaVerificacion.isEmpty() ){
			grabarListaDinamicaAutorizaVerificacionLiquidacion(listaAutorizaVerificacion, pExpedienteLiquidacion.getId());
		}
	} catch(BusinessException e){
		throw e;
	} catch(Exception e){
		throw new BusinessException(e);
	}
	return pExpedienteLiquidacion;
}



	public List<AutorizaLiquidacion> grabarListaDinamicaAutorizaLiquidacion(List<AutorizaLiquidacion> lstAutorizaLiquidacion, ExpedienteLiquidacionId pPK) throws BusinessException{
		AutorizaLiquidacion autorizaLiquidacion = null;
		AutorizaLiquidacionId pk = null;
		AutorizaLiquidacion autorizaLiquidacionTemp = null;
	try{
		for(int i=0; i<lstAutorizaLiquidacion.size(); i++){
			autorizaLiquidacion = (AutorizaLiquidacion) lstAutorizaLiquidacion.get(i);
			if(autorizaLiquidacion.getId()==null || autorizaLiquidacion.getId().getIntItemAutoriza()==null){
				pk = new AutorizaLiquidacionId();
				pk.setIntPersEmpresaLiquidacionPk(pPK.getIntPersEmpresaPk());
				pk.setIntItemExpedienteLiqui(pPK.getIntItemExpediente());
				autorizaLiquidacion.setId(pk);
				autorizaLiquidacion = boAutorizaLiquidacion.grabar(autorizaLiquidacion);
			}else{
				autorizaLiquidacionTemp = boAutorizaLiquidacion.getPorPk(autorizaLiquidacion.getId());
				if(autorizaLiquidacionTemp == null){
					autorizaLiquidacion= boAutorizaLiquidacion.grabar(autorizaLiquidacion);
				}else{
					autorizaLiquidacion = boAutorizaLiquidacion.modificar(autorizaLiquidacion);
				}
			}
		}
	}catch(BusinessException e){
		throw e;
	}catch(Exception e){
		throw new BusinessException(e);
	}
	return lstAutorizaLiquidacion;
}



	public List<AutorizaVerificaLiquidacion> grabarListaDinamicaAutorizaVerificacionLiquidacion(List<AutorizaVerificaLiquidacion> lstAutorizaVerificacion, ExpedienteLiquidacionId pPK) throws BusinessException{
		AutorizaVerificaLiquidacion autorizaVerificacion = null;
		AutorizaVerificaLiquidacionId pk = null;
		AutorizaVerificaLiquidacion autorizaVerificacionTemp = null;
	try{
		for(int i=0; i<lstAutorizaVerificacion.size(); i++){
			autorizaVerificacion = (AutorizaVerificaLiquidacion) lstAutorizaVerificacion.get(i);
			if(autorizaVerificacion.getId()==null || autorizaVerificacion.getId().getIntItemAutorizaVerifica()==null){
				pk = new AutorizaVerificaLiquidacionId();
				pk.setIntPersEmpresaLiquidacionPk(pPK.getIntPersEmpresaPk());
				pk.setIntItemExpedienteLiqui(pPK.getIntItemExpediente());
				autorizaVerificacion.setId(pk);
				autorizaVerificacion = boAutorizaVerificaLiquidacion.grabar(autorizaVerificacion);
			}else{
				autorizaVerificacionTemp = boAutorizaVerificaLiquidacion.getPorPk(autorizaVerificacion.getId());
				if(autorizaVerificacionTemp == null){
					autorizaVerificacion = boAutorizaVerificaLiquidacion.grabar(autorizaVerificacion);
				}else{
					autorizaVerificacion = boAutorizaVerificaLiquidacion.modificar(autorizaVerificacion);
				}
			}
		}
	}catch(BusinessException e){
		throw e;
	}catch(Exception e){
		throw new BusinessException(e);
	}
	return lstAutorizaVerificacion;
}
	
	
	//------------------------>
	
	
	public List<AutorizaVerificaLiquidacion> getListaVerificaLiquidacionPorPkExpediente(ExpedienteLiquidacionId pId) throws BusinessException{
		List<AutorizaVerificaLiquidacion> lista = null;
		try{
			lista = boAutorizaVerificaLiquidacion.getListaPorPkExpedienteLiquidacion(pId);
		}catch(BusinessException e){
			System.out.println("BusinessException ---> "+e);
			throw e;
		}catch(Exception e1){
			System.out.println("ExceptionException ---> "+e1);
			throw new BusinessException(e1);
		}
		return lista;
	}
	
	
	public List<AutorizaLiquidacion> getListaAutorizaLiquidacionPorPkExpediente(ExpedienteLiquidacionId pId) throws BusinessException{
		List<AutorizaLiquidacion> lista = null;
		try{
			lista = boAutorizaLiquidacion.getListaPorPkExpedienteLiquidacion(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}

	
	/*public Auditoria grabarAuditoriaLiquidacion(Auditoria o) throws BusinessException{
		Auditoria auditoria = null;
    	try{
    		auditoria = boAuditoria.grabarAuditoria(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return auditoria;
	}*/
	public Integer	obtenerPeriodoActual() throws Exception{
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
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}
	public Timestamp obtenerFechaActual(){
		return new Timestamp(new Date().getTime());
	}
	
	
	
	public LibroDiario generarLibroDiarioAutorizacion(ExpedienteLiquidacion expedienteLiquidacion) throws Exception{
		LibroDiarioFacadeRemote libroDiarioFacade = null;
//		EstadoLiquidacion estadoSolicitudUltimo = null;
		ModeloDetalle modeloDetalleExtornoDebe = null;
		ModeloDetalle modeloDetalleExtornoHaber = null;
		LibroDiario ultimoLibro = null;
		LibroDiario libroDiario = new LibroDiario();
		
		try {
			
			libroDiarioFacade = (LibroDiarioFacadeRemote)EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			Usuario usuario = (Usuario) getRequest().getSession().getAttribute("usuario");
			Integer intIdEmpresa = expedienteLiquidacion.getId().getIntPersEmpresaPk();
			Integer intIdUsuario = usuario.getIntPersPersonaPk();
			
			ModeloFacadeRemote modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
			Modelo modeloExtorno = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_LIQUIDACIONCUENTA, intIdEmpresa).get(0);

			for(ModeloDetalle modeloDetalle : modeloExtorno.getListModeloDetalle()){
				if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_DEBE)){
					modeloDetalleExtornoDebe = modeloDetalle;
				}else if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_HABER)){
					modeloDetalleExtornoHaber = modeloDetalle;
				}
			}

			LibroDiarioId libroDiarioId = new LibroDiarioId();
			libroDiarioId.setIntContCodigoLibro(null);
			libroDiarioId.setIntContPeriodoLibro(obtenerPeriodoActual());
			libroDiarioId.setIntPersEmpresaLibro(Constante.PARAM_EMPRESASESION);
			

			// recuperamos el ultimo registro del libro diario en el presente
			// periodo. Por el codigo de libro.
			ultimoLibro = libroDiarioFacade.getLibroDiarioUltimoPorPk(libroDiarioId);
			if(ultimoLibro == null){
				ultimoLibro = new LibroDiario();
				ultimoLibro.setId(new LibroDiarioId());
				ultimoLibro.getId().setIntContCodigoLibro(0);
				ultimoLibro.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
				ultimoLibro.getId().setIntPersEmpresaLibro(Constante.PARAM_EMPRESASESION);
			}
			
			//int maximo = expedienteLiquidacion.getListaEstadoLiquidacion().size();
			//estadoSolicitudUltimo = expedienteLiquidacion.getListaEstadoLiquidacion().get(maximo -1);
			
			//estadoSolicitudUltimo = expedienteLiquidacion.getEstadoLiquidacionUltimo();
			libroDiario.getId().setIntPersEmpresaLibro(ultimoLibro.getId().getIntPersEmpresaLibro());
			libroDiario.getId().setIntContPeriodoLibro(ultimoLibro.getId().getIntContPeriodoLibro());
			libroDiario.getId().setIntContCodigoLibro(ultimoLibro.getId().getIntContCodigoLibro()+1);
			
			libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACION_CUENTA);
			libroDiario.setStrGlosa("Liquidacion de Cuentas. Expediente: "+expedienteLiquidacion.getId().getIntItemExpediente());
			
			libroDiario.setTsFechaRegistro(obtenerFechaActual());
			libroDiario.setTsFechaDocumento(obtenerFechaActual());
			libroDiario.setIntPersEmpresaUsuario(intIdEmpresa);
			libroDiario.setIntPersPersonaUsuario(intIdUsuario);
			libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			
			// Generando el detalle
			LibroDiarioDetalle libroDiarioDetalleHaber = new LibroDiarioDetalle();
			libroDiarioDetalleHaber.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiarioDetalleHaber.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
			libroDiarioDetalleHaber.setIntPersEmpresaCuenta(modeloDetalleExtornoHaber.getId().getIntPersEmpresaCuenta());
			libroDiarioDetalleHaber.setIntContPeriodo(modeloDetalleExtornoHaber.getId().getIntContPeriodoCuenta());
			libroDiarioDetalleHaber.setStrContNumeroCuenta(modeloDetalleExtornoHaber.getId().getStrContNumeroCuenta());
			libroDiarioDetalleHaber.setIntPersPersona(Constante.PARAM_EMPRESASESION);
			libroDiarioDetalleHaber.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACION_CUENTA);

			libroDiarioDetalleHaber.setStrSerieDocumento("");
			libroDiarioDetalleHaber.setStrNumeroDocumento(expedienteLiquidacion.getId().getIntItemExpediente() +"-"+expedienteLiquidacion.getId().getIntItemExpediente());
			
			libroDiarioDetalleHaber.setIntPersEmpresaSucursal(usuario.getSucursal().getId().getIntPersEmpresaPk());
			libroDiarioDetalleHaber.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			libroDiarioDetalleHaber.setIntSudeIdSubSucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			libroDiarioDetalleHaber.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
			
			if(libroDiarioDetalleHaber.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
				libroDiarioDetalleHaber.setBdHaberSoles(expedienteLiquidacion.getBdMontoBrutoLiquidacion());
				libroDiarioDetalleHaber.setBdDebeSoles(null);			
			}else{
				libroDiarioDetalleHaber.setBdHaberExtranjero(expedienteLiquidacion.getBdMontoBrutoLiquidacion());
				libroDiarioDetalleHaber.setBdDebeExtranjero(null);			
			}
			
			LibroDiarioDetalle libroDiarioDetalleDebe = new LibroDiarioDetalle();
			libroDiarioDetalleDebe.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiarioDetalleDebe.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
			libroDiarioDetalleDebe.setIntPersEmpresaCuenta(modeloDetalleExtornoDebe.getId().getIntPersEmpresaCuenta());
			libroDiarioDetalleDebe.setIntContPeriodo(modeloDetalleExtornoDebe.getId().getIntContPeriodoCuenta());
			libroDiarioDetalleDebe.setStrContNumeroCuenta(modeloDetalleExtornoDebe.getId().getStrContNumeroCuenta());
			
			libroDiarioDetalleDebe.setIntPersPersona(Constante.PARAM_EMPRESASESION);
			
			
			libroDiarioDetalleDebe.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACION_CUENTA);
			libroDiarioDetalleDebe.setStrSerieDocumento("");
			libroDiarioDetalleDebe.setStrNumeroDocumento(expedienteLiquidacion.getId().getIntItemExpediente() +"-"+expedienteLiquidacion.getId().getIntItemExpediente());
			libroDiarioDetalleDebe.setIntPersEmpresaSucursal(usuario.getSucursal().getId().getIntPersEmpresaPk());
			libroDiarioDetalleDebe.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			libroDiarioDetalleDebe.setIntSudeIdSubSucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			libroDiarioDetalleDebe.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
			
			if(libroDiarioDetalleDebe.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
				libroDiarioDetalleDebe.setBdHaberSoles(null);
				libroDiarioDetalleDebe.setBdDebeSoles(expedienteLiquidacion.getBdMontoBrutoLiquidacion());
			}else{
				libroDiarioDetalleDebe.setBdHaberExtranjero(null);
				libroDiarioDetalleDebe.setBdDebeExtranjero(expedienteLiquidacion.getBdMontoBrutoLiquidacion());			
			}
			
			libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleHaber);
			libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleDebe);

		} catch (Exception e) {
			log.error("debug generarLibroDiarioAutorizacion --> "+e);
		}
		return libroDiario;	
			
	}
	
	
	
	public LibroDiario generarLibroDiarioAnulacionLiquidacion(ExpedienteLiquidacion expedienteLiquidacion) throws Exception{
		Usuario usuario = (Usuario) getRequest().getSession().getAttribute("PARAM_T_DOCUMENTOGENERAL_LIQUIDACION_CUENTA");
		Integer intIdEmpresa = expedienteLiquidacion.getId().getIntPersEmpresaPk();
		Integer intIdUsuario = usuario.getIntPersPersonaPk();
		
		EstadoLiquidacion estadoLiquidacionSolicitudUltimo = obtenerUltimoEstadoLiquidacion(expedienteLiquidacion, Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO);
		//EstadoCredito estadoCreditoSolicitudUltimo = obtenerUltimoEstadoCredito(expedienteCredito);
		
		LibroDiarioId libroDiarioId = new LibroDiarioId();
		libroDiarioId.setIntPersEmpresaLibro(estadoLiquidacionSolicitudUltimo.getIntPersEmpresaLibro());
		libroDiarioId.setIntContPeriodoLibro(estadoLiquidacionSolicitudUltimo.getIntContPeriodoLibro());
		libroDiarioId.setIntContCodigoLibro(estadoLiquidacionSolicitudUltimo.getIntContCodigoLibro());
		
		LibroDiarioFacadeRemote libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
		LibroDiario libroDiarioSolicitud = libroDiarioFacade.getLibroDiarioPorPk(libroDiarioId);
		log.info(libroDiarioSolicitud);
		
		List<LibroDiarioDetalle> listaLibroDiarioDetalleSolicitud = libroDiarioFacade.getListaLibroDiarioDetallePorLibroDiario(libroDiarioSolicitud);
		LibroDiarioDetalle libroDiarioDetalleSolicitudDebe = null;
		LibroDiarioDetalle libroDiarioDetalleSolicitudHaber = null;
		for(LibroDiarioDetalle libroDiarioDetalleTemp : listaLibroDiarioDetalleSolicitud){
			if(libroDiarioDetalleTemp.getBdDebeSoles()!=null || libroDiarioDetalleTemp.getBdDebeExtranjero()!=null)
				libroDiarioDetalleSolicitudDebe = libroDiarioDetalleTemp;			
			if(libroDiarioDetalleTemp.getBdHaberSoles()!=null || libroDiarioDetalleTemp.getBdHaberExtranjero()!=null)
				libroDiarioDetalleSolicitudHaber = libroDiarioDetalleTemp;			
		}
	
		ModeloFacadeRemote modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
		Modelo modeloExtorno = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_LIQUIDACIONCUENTA, intIdEmpresa).get(0);
		
		ModeloDetalle modeloDetalleExtornoDebe = null;
		ModeloDetalle modeloDetalleExtornoHaber = null;
		
		for(ModeloDetalle modeloDetalle : modeloExtorno.getListModeloDetalle()){
			if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_DEBE)){
				modeloDetalleExtornoDebe = modeloDetalle;
			}else if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_HABER)){
				modeloDetalleExtornoHaber = modeloDetalle;
			}
		}
		
		LibroDiario libroDiario = new LibroDiario();
		libroDiario.getId().setIntPersEmpresaLibro(intIdEmpresa);
		libroDiario.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
		libroDiario.setStrGlosa(libroDiarioSolicitud.getStrGlosa());
		//libroDiario.setStrGlosa("Prueba Provision Creditos... ");
		libroDiario.setTsFechaRegistro(obtenerFechaActual());
		libroDiario.setTsFechaDocumento(obtenerFechaActual());
		libroDiario.setIntPersEmpresaUsuario(intIdEmpresa);
		libroDiario.setIntPersPersonaUsuario(intIdUsuario);
		libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACION_CUENTA);
		
		LibroDiarioDetalle libroDiarioDetalleHaber = new LibroDiarioDetalle();
		libroDiarioDetalleHaber.getId().setIntPersEmpresaLibro(intIdEmpresa);
		libroDiarioDetalleHaber.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
		libroDiarioDetalleHaber.setIntPersEmpresaCuenta(modeloDetalleExtornoHaber.getId().getIntPersEmpresaCuenta());
		libroDiarioDetalleHaber.setIntContPeriodo(modeloDetalleExtornoHaber.getId().getIntContPeriodoCuenta());
		libroDiarioDetalleHaber.setStrContNumeroCuenta(modeloDetalleExtornoHaber.getId().getStrContNumeroCuenta());
		libroDiarioDetalleHaber.setIntPersPersona(expedienteLiquidacion.getEgreso().getIntPersPersonaGirado());
		libroDiarioDetalleHaber.setIntParaDocumentoGeneral(libroDiarioDetalleSolicitudHaber.getIntParaDocumentoGeneral());
		libroDiarioDetalleHaber.setStrSerieDocumento(libroDiarioDetalleSolicitudHaber.getStrSerieDocumento());
		libroDiarioDetalleHaber.setStrNumeroDocumento(libroDiarioDetalleSolicitudHaber.getStrNumeroDocumento());
		libroDiarioDetalleHaber.setIntPersEmpresaSucursal(libroDiarioDetalleSolicitudHaber.getIntPersEmpresaSucursal());
		libroDiarioDetalleHaber.setIntSucuIdSucursal(libroDiarioDetalleSolicitudHaber.getIntSucuIdSucursal());
		libroDiarioDetalleHaber.setIntSudeIdSubSucursal(libroDiarioDetalleSolicitudHaber.getIntSudeIdSubSucursal());
		libroDiarioDetalleHaber.setIntParaMonedaDocumento(libroDiarioDetalleSolicitudHaber.getIntParaMonedaDocumento());
		if(libroDiarioDetalleHaber.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
			libroDiarioDetalleHaber.setBdHaberSoles(expedienteLiquidacion.getBdMontoBrutoLiquidacion());
			libroDiarioDetalleHaber.setBdDebeSoles(null);			
		}else{
			libroDiarioDetalleHaber.setBdHaberExtranjero(expedienteLiquidacion.getBdMontoBrutoLiquidacion());
			libroDiarioDetalleHaber.setBdDebeExtranjero(null);			
		}
		
		LibroDiarioDetalle libroDiarioDetalleDebe = new LibroDiarioDetalle();
		libroDiarioDetalleDebe.getId().setIntPersEmpresaLibro(intIdEmpresa);
		libroDiarioDetalleDebe.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
		libroDiarioDetalleDebe.setIntPersEmpresaCuenta(modeloDetalleExtornoDebe.getId().getIntPersEmpresaCuenta());
		libroDiarioDetalleDebe.setIntContPeriodo(modeloDetalleExtornoDebe.getId().getIntContPeriodoCuenta());
		libroDiarioDetalleDebe.setStrContNumeroCuenta(modeloDetalleExtornoDebe.getId().getStrContNumeroCuenta());
		libroDiarioDetalleDebe.setIntPersPersona(expedienteLiquidacion.getEgreso().getIntPersPersonaGirado());
		libroDiarioDetalleDebe.setIntParaDocumentoGeneral(libroDiarioDetalleSolicitudDebe.getIntParaDocumentoGeneral());
		libroDiarioDetalleDebe.setStrSerieDocumento(libroDiarioDetalleSolicitudDebe.getStrSerieDocumento());
		libroDiarioDetalleDebe.setStrNumeroDocumento(libroDiarioDetalleSolicitudDebe.getStrNumeroDocumento());
		libroDiarioDetalleDebe.setIntPersEmpresaSucursal(libroDiarioDetalleSolicitudDebe.getIntPersEmpresaSucursal());
		libroDiarioDetalleDebe.setIntSucuIdSucursal(libroDiarioDetalleSolicitudDebe.getIntSucuIdSucursal());
		libroDiarioDetalleDebe.setIntSudeIdSubSucursal(libroDiarioDetalleSolicitudDebe.getIntSudeIdSubSucursal());
		libroDiarioDetalleDebe.setIntParaMonedaDocumento(libroDiarioDetalleSolicitudDebe.getIntParaMonedaDocumento());
		if(libroDiarioDetalleDebe.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
			libroDiarioDetalleDebe.setBdHaberSoles(null);
			libroDiarioDetalleDebe.setBdDebeSoles(expedienteLiquidacion.getBdMontoBrutoLiquidacion());
		}else{
			libroDiarioDetalleDebe.setBdHaberExtranjero(null);
			libroDiarioDetalleDebe.setBdDebeExtranjero(expedienteLiquidacion.getBdMontoBrutoLiquidacion());			
		}
		
		libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleHaber);
		libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleDebe);
		
		return libroDiario;		
	}

	/**
	 * 
	 * @param intTipoConsultaBusqueda
	 * @param strConsultaBusqueda
	 * @param strNumeroSolicitudBusq
	 * @param estadoCondicionFiltro
	 * @param intTipoCreditoFiltro
	 * @param estadoLiquidacionBusqueda
	 * @param intTipoBusquedaFechaFiltro
	 * @param estadoLiquidacionFechas
	 * @param estadoLiquidacionSuc
	 * @param intIdSubsucursalFiltro
	 * @return
	 * @throws BusinessException
	 */
	/*public List<ExpedienteLiquidacionComp> getListaExpedienteLiquidacionCompDeBusquedaFiltro(
			Integer intTipoConsultaBusqueda,String strConsultaBusqueda, 
			String strNumeroSolicitudBusq,
			EstadoLiquidacion estadoCondicionFiltro, 
			Integer intTipoCreditoFiltro, 
			EstadoLiquidacion estadoLiquidacionFechas,
			EstadoLiquidacion estadoLiquidacionSuc,
			Integer intIdSubsucursalFiltro)  throws BusinessException{
			
			List<ExpedienteLiquidacionComp> lista = null;
			List<ExpedienteLiquidacionComp> dto = null;
			List<ExpedienteLiquidacionComp> listaExpedienteLiquidacionComp = null;
			//EstadoLiquidacion estadoLiquidacion;
			//List<EstadoLiquidacion> listaEstadoCredito = null;
			//SocioComp socioComp = null;
			//Persona persona = null;
			//Integer intIdPersona = null;
			//PersonaFacadeRemote personaFacade = null;
			SocioFacadeRemote socioFacade = null;
			TablaFacadeRemote  tablaFacade = null;
			CreditoFacadeRemote creditoFacade = null;
			EmpresaFacadeRemote empresaFacade = null;
			CreditoId creditoId = null;
			List<Tabla> listaDescripcionExpedienteXredito= null;
			String strDescripcionExpedienteCredito = null;
			
			// Booleanos que me indican que aplicar
			Boolean blnAplicaTipoBusqueda = Boolean.FALSE; 		// Tipo de Busqueda  (1)
			Boolean blnAplicaNroSolicitud = Boolean.FALSE;		// Nro. Solicitud  (2)  *
			Boolean blnAplicaEstado = Boolean.FALSE; 			// Condición  (3)	*
			Boolean blnAplicaTipoLiquidacion = Boolean.FALSE; 	// Tipo  (4)		**
			Boolean blnAplicaFechas = Boolean.FALSE;  			//Fecha  inicio fin (5) *
			Boolean blnAplicaSucursal = Boolean.FALSE;  		// Sucursal(6)		*
			Boolean blnAplicaSubSucursal = Boolean.FALSE; 		// subsucursal(7)	*

			
			Integer intTipoPersona = 0; // 1 natural   2 juridica  // 3 documetno
			List<Sucursal> listaSucursales = null;
			List<Sucursal> listaSucursalesFiltradas = null;
			List<Subsucursal> listaSubSucursales = null;
			List<Subsucursal> listaSubSucursalesFiltradas = null;
			Boolean blnCumpleFiltros = Boolean.TRUE;
			List<ExpedienteLiquidacionComp> listaFinal = null;
			Integer intTipoValidacionFechas = 0;  // 0 no procede - 1 inicio  - 2 final  - 3 todo
			
			
			Integer intTipoBusqueda = 0; //  1 Natural, 2 Juridica, 3 Documento
			Boolean blnTraerEstados = Boolean.FALSE;
			Boolean blnTraerUltimoEstado = Boolean.FALSE;
			
			ExpedienteLiquidacionId liquidacionId = new ExpedienteLiquidacionId();
			ExpedienteLiquidacion expediente = new ExpedienteLiquidacion();
			expediente.setId(liquidacionId);
				
			try {
					socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
					tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
					creditoFacade = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
					empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
					
					if(intTipoConsultaBusqueda.compareTo(0)!=0){
						blnAplicaTipoBusqueda = Boolean.TRUE;
						if(intTipoConsultaBusqueda.compareTo(Constante.PARAM_T_TIPO_BUSQ_NOMBRES)==0 
						|| intTipoConsultaBusqueda.compareTo(Constante.PARAM_T_TIPO_BUSQ_DNI)==0){
							intTipoPersona = 1;
						}else{
							intTipoPersona = 2;
						}
					}
					
					// 0. Validando los foltros a aplicar
					if(!(strNumeroSolicitudBusq.equalsIgnoreCase("") || strNumeroSolicitudBusq.isEmpty())){
						blnAplicaNroSolicitud = Boolean.TRUE;	
					} 
					
					if(estadoCondicionFiltro.getIntParaEstado().compareTo(0)!=0){
						blnAplicaEstado = Boolean.TRUE;	
					} 

					if(estadoLiquidacionFechas.getIntParaEstado().compareTo(0)!=0){
						blnAplicaFechas = Boolean.TRUE;
						
						if(estadoLiquidacionFechas.getDtFechaEstadoDesde() != null 
							|| estadoLiquidacionFechas.getDtFechaEstadoHasta()!= null){
							blnAplicaFechas = Boolean.TRUE;
						}else{
							blnAplicaFechas = Boolean.FALSE;
						}
						
						if(blnAplicaFechas){
							if(estadoLiquidacionFechas.getDtFechaEstadoDesde()!= null
								&& estadoLiquidacionFechas.getDtFechaEstadoHasta()== null ){
								intTipoValidacionFechas = new Integer(1);
							} else if(estadoLiquidacionFechas.getDtFechaEstadoDesde()== null
								&& estadoLiquidacionFechas.getDtFechaEstadoHasta()!= null ){
								intTipoValidacionFechas = new Integer(2);
							} else if(estadoLiquidacionFechas.getDtFechaEstadoDesde()!= null
								&& estadoLiquidacionFechas.getDtFechaEstadoHasta()!= null ){
								intTipoValidacionFechas = new Integer(3);
							} else{
								blnAplicaFechas = Boolean.FALSE;
							}
							
						}
						
					}

					if(estadoLiquidacionSuc.getIntSucuIdSucursal().compareTo(new Integer(0)) != 0){
						blnAplicaSucursal = Boolean.TRUE;
							listaSucursalesFiltradas = cargarSucursalesValidas(estadoLiquidacionSuc.getIntSucuIdSucursal());
					}
					if(estadoLiquidacionSuc.getIntSucuIdSucursal().compareTo(new Integer(-2)) == 0){
						blnAplicaSucursal = Boolean.FALSE;
					}
		
					if(intIdSubsucursalFiltro.compareTo(0) != 0){
						blnAplicaSubSucursal = Boolean.TRUE;
					}
					
					
					
					if(!(strNumeroSolicitudBusq.equalsIgnoreCase("") || strNumeroSolicitudBusq.isEmpty())){
						strNumeroSolicitudBusq = strNumeroSolicitudBusq.trim();
						expediente.getId().setIntItemExpediente(new Integer(strNumeroSolicitudBusq));
					} 

					if(intTipoCreditoFiltro.compareTo(0)!=0){
						expediente.setIntParaSubTipoOperacion(intTipoCreditoFiltro);	
					}


					//----- > cuerpo del metodo
					listaExpedienteLiquidacionComp = getExpedientLiquidacionParaFiltros(expediente,blnAplicaEstado, blnAplicaFechas,blnAplicaTipoBusqueda, intTipoPersona );// -- Agregar tipo de liquidacion
					
					if(listaExpedienteLiquidacionComp != null && !listaExpedienteLiquidacionComp.isEmpty()){
						lista = new ArrayList<ExpedienteLiquidacionComp>();
						
						for (ExpedienteLiquidacionComp expComp : listaExpedienteLiquidacionComp) {
							blnCumpleFiltros = Boolean.TRUE;
							
							
							// *** Se valida strNumeroSolicitudBusq
							if(blnAplicaNroSolicitud){
								// Verificamos si contiene el guion...
								//if(strNumeroSolicitudBusq.contains("-")){
								//	if(expComp.getStrNroSolicitudBusqueda().equalsIgnoreCase(strNumeroSolicitudBusq)){
								//		blnCumpleFiltros = true;
								//	}else{
								//		blnCumpleFiltros = false;
								//	}
								//}else{
									if(expComp.getExpedienteLiquidacion().getId().getIntItemExpediente().toString().equalsIgnoreCase(strNumeroSolicitudBusq)){
										blnCumpleFiltros = true;
									}else{
										blnCumpleFiltros = false;
									}
								//}			
							}
							
							if(blnCumpleFiltros){
						// *** Se valida Estado de liquidacion
								if(blnAplicaEstado){
									Integer intEstadoFiltro = estadoCondicionFiltro.getIntParaEstado();
									Integer intEstadoExp = expComp.getExpedienteLiquidacion().getEstadoLiquidacionUltimo().getIntParaEstado();
		
									if(intEstadoExp.compareTo(intEstadoFiltro)==0){
										blnCumpleFiltros = true;
									}else{
										blnCumpleFiltros = false;
									}	
								}
							}

							if(blnCumpleFiltros){
						// ***  Valida fechas de inicio y fin +  esatdo
								if(blnAplicaFechas){
									List<EstadoLiquidacion> listEstados = new ArrayList<EstadoLiquidacion>();
									listEstados = expComp.getExpedienteLiquidacion().getListaEstadoLiquidacion();

									if(listEstados != null && !listEstados.isEmpty()){
										forEstados:
										for (EstadoLiquidacion estado : listEstados) {
											if(estadoLiquidacionFechas.getIntParaEstado().compareTo(estado.getIntParaEstado())==0){ 
										        switch (intTipoValidacionFechas) {
										            case 1: //Fech desde
										            		if(estado.getTsFechaEstado().after(estadoLiquidacionFechas.getDtFechaEstadoDesde())){
										            			blnCumpleFiltros = true;
															} 
											                break forEstados;
									 
										            case 2: //Fech hasta
											            	if(estado.getTsFechaEstado().before(estadoLiquidacionFechas.getDtFechaEstadoHasta())){
										            			blnCumpleFiltros = true;
															} 
											                break forEstados;
											                
										            case 3:  //Fech desde  + hasta
											            	if(estado.getTsFechaEstado().before(estadoLiquidacionFechas.getDtFechaEstadoHasta())
											            		&& estado.getTsFechaEstado().after(estadoLiquidacionFechas.getDtFechaEstadoDesde())){
										            			blnCumpleFiltros = true;
															} 
											                break forEstados;
										            
										            default:  //
										            		blnCumpleFiltros = false;
										            		break forEstados;
										        }
												
											}else{
												blnCumpleFiltros = false;
											}
										}
									}
		
								}
	
							}
							

							if(blnCumpleFiltros){
						// *** Se valida Sucursal
								if(blnAplicaSucursal){
									if(listaSucursalesFiltradas != null && !listaSucursalesFiltradas.isEmpty()){
										
										for (Sucursal sucursal : listaSucursalesFiltradas) {
											if(sucursal.getId().getIntIdSucursal().compareTo(expComp.getExpedienteLiquidacion().getEstadoLiquidacionUltimo().getIntSucuIdSucursal())==0){
												blnCumpleFiltros = true;
												break;
											}else{
												blnCumpleFiltros = false;
											}
										}
									}
			
								}
							}

							if(blnCumpleFiltros){
						// *** Se valida SubSucursal
								if(blnAplicaSubSucursal){
									List<Subsucursal> listaSubSucursal = null;
									listaSubSucursal = cargarListaTablaSubSucursal(expComp.getExpedienteLiquidacion().getEstadoLiquidacionUltimo().getIntSucuIdSucursal());
									
									if(listaSubSucursal != null && !listaSubSucursal.isEmpty()){
										for (Subsucursal subSucursal : listaSubSucursal) {
											if(subSucursal.getId().getIntIdSubSucursal().compareTo(expComp.getExpedienteLiquidacion().getEstadoLiquidacionUltimo().getIntSudeIdSubsucursal())==0){
												blnCumpleFiltros = true;
												break;
											}else{
												blnCumpleFiltros = false;
											}
										}
									}
			
								}
							}

							if(blnCumpleFiltros){
						// *** Se valida strNumeroSolicitudBusq
								if(blnAplicaTipoBusqueda){
									
									strConsultaBusqueda = strConsultaBusqueda.trim().toUpperCase();
									if(intTipoConsultaBusqueda.compareTo(Constante.PARAM_T_TIPO_BUSQ_NOMBRES)==0){
										String strNombresSocio = expComp.getSocioComp().getPersona().getNatural().getStrApellidoPaterno()+" "+
																 expComp.getSocioComp().getPersona().getNatural().getStrApellidoMaterno()+" "+
																 expComp.getSocioComp().getPersona().getNatural().getStrNombres();
										
										if( strNombresSocio.toUpperCase().contains(strConsultaBusqueda)){
											blnCumpleFiltros = true;
										}else{
											blnCumpleFiltros = false;
										}
									}
		
									if(intTipoConsultaBusqueda.compareTo(Constante.PARAM_T_TIPO_BUSQ_DNI)==0){
										String strDniSocio = expComp.getSocioComp().getPersona().getDocumento().getStrNumeroIdentidad();
										if( strDniSocio.equalsIgnoreCase(strConsultaBusqueda)){
											blnCumpleFiltros = true;
										}else{
											blnCumpleFiltros = false;
										}
									}
		
									if(intTipoConsultaBusqueda.compareTo(Constante.PARAM_T_TIPO_BUSQ_RUC)==0){
										String strRucSocio = expComp.getSocioComp().getPersona().getRuc().getStrNumeroIdentidad();
										if( strRucSocio.equalsIgnoreCase(strConsultaBusqueda)){
											blnCumpleFiltros = true;
										}else{
											blnCumpleFiltros = false;
										}
									}
									
									if(intTipoConsultaBusqueda.compareTo(Constante.PARAM_T_TIPO_BUSQ_RAZONSOCIAL)==0){
										String strRazonSocial = expComp.getSocioComp().getPersona().getJuridica().getStrRazonSocial();
										if( strRazonSocial.toUpperCase().contains(strConsultaBusqueda)){
											blnCumpleFiltros = true;
										}else{
											blnCumpleFiltros = false;
										}
									}
								}
							}
							
							
							if(blnCumpleFiltros){
								lista.add(expComp);
							}
		
						}	
					}
					
					if(lista != null && !lista.isEmpty()){
						dto = completarDatosDeLiquidacion(lista);
					}

				} catch (Exception e) {
					log.error("Error en getListaExpedienteLiquidacionCompDeBusquedaFiltro --> "+e);
				}

				return dto;
		}*/


	public List<ExpedienteLiquidacionComp> getListaExpedienteLiquidacionCompDeBusquedaFiltro(
		Integer intTipoConsultaBusqueda,String strConsultaBusqueda, 
		String strNumeroSolicitudBusq,
		EstadoLiquidacion estadoCondicionFiltro, 
		Integer intTipoCreditoFiltro, 
		EstadoLiquidacion estadoLiquidacionFechas,
		EstadoLiquidacion estadoLiquidacionSuc,
		Integer intIdSubsucursalFiltro)  throws BusinessException{
		
			List<ExpedienteLiquidacionComp> lista = null;
			List<ExpedienteLiquidacionComp> dto = null;
			
			List<ExpedienteLiquidacionComp> listaExpedienteLiquidacionComp = null;
//			EstadoLiquidacion estadoLiquidacion;
//			List<EstadoLiquidacion> listaEstadoCredito = null;
//			SocioComp socioComp = null;
//			Persona persona = null;
//			Integer intIdPersona = null;
//			PersonaFacadeRemote personaFacade = null;
//			SocioFacadeRemote socioFacade = null;
//			TablaFacadeRemote  tablaFacade = null;
//			CreditoFacadeRemote creditoFacade = null;
//			EmpresaFacadeRemote empresaFacade = null;
//			CreditoId creditoId = null;
//			List<Tabla> listaDescripcionExpedienteXredito= null;
//			String strDescripcionExpedienteCredito = null;
			
			// Booleanos que me indican que aplicar
			Boolean blnAplicaTipoBusqueda = Boolean.FALSE; 		// Tipo de Busqueda  (1)
			Boolean blnAplicaNroSolicitud = Boolean.FALSE;		// Nro. Solicitud  (2)  *
			Boolean blnAplicaEstado = Boolean.FALSE; 			// Condición  (3)	*
			Boolean blnAplicaTipoLiquidacion = Boolean.FALSE; 	// Tipo  (4)		**
			Boolean blnAplicaFechas = Boolean.FALSE;  			//Fecha  inicio fin (5) *
			Boolean blnAplicaSucursal = Boolean.FALSE;  		// Sucursal(6)		*
			Boolean blnAplicaSubSucursal = Boolean.FALSE; 		// subsucursal(7)	*
			
			Integer intTipoPersona = 0; // 1 natural   2 juridica
//			List<Sucursal> listaSucursales = null;
			List<Sucursal> listaSucursalesFiltradas = null;
//			List<Subsucursal> listaSubSucursales = null;
//			List<Subsucursal> listaSubSucursalesFiltradas = null;
			Boolean blnCumpleFiltros = Boolean.TRUE;
//			List<ExpedienteLiquidacionComp> listaFinal = null;
			Integer intTipoValidacionFechas = 0;  // 0 no procede - 1 inicio  - 2 final  - 3 todo

			try {
//				socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
//				tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
//				creditoFacade = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
//				empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);

				if(intTipoConsultaBusqueda.compareTo(0)!=0){
					blnAplicaTipoBusqueda = Boolean.TRUE;
					if(intTipoConsultaBusqueda.compareTo(Constante.PARAM_T_TIPO_BUSQ_NOMBRES)==0 
					|| intTipoConsultaBusqueda.compareTo(Constante.PARAM_T_TIPO_BUSQ_DNI)==0){
						intTipoPersona = 1;
					}else{
						intTipoPersona = 2;
					}
				}

				if(!(strNumeroSolicitudBusq.equalsIgnoreCase("") || strNumeroSolicitudBusq.isEmpty())){
					blnAplicaNroSolicitud = Boolean.TRUE;	 
				} 

				if(estadoCondicionFiltro.getIntParaEstado().compareTo(0)!=0){
					blnAplicaEstado = Boolean.TRUE;	
				} 

				if(intTipoCreditoFiltro.compareTo(0)!=0){
					blnAplicaTipoLiquidacion = Boolean.TRUE;	
				} 

				if(estadoLiquidacionFechas.getIntParaEstado().compareTo(0)!=0){
					blnAplicaFechas = Boolean.TRUE;
					
					if(estadoLiquidacionFechas.getDtFechaEstadoDesde() != null 
						|| estadoLiquidacionFechas.getDtFechaEstadoHasta()!= null){
						blnAplicaFechas = Boolean.TRUE;
					}else{
						blnAplicaFechas = Boolean.FALSE;
					}
					
					if(blnAplicaFechas){
						if(estadoLiquidacionFechas.getDtFechaEstadoDesde()!= null
							&& estadoLiquidacionFechas.getDtFechaEstadoHasta()== null ){
							intTipoValidacionFechas = new Integer(1);
						} else if(estadoLiquidacionFechas.getDtFechaEstadoDesde()== null
							&& estadoLiquidacionFechas.getDtFechaEstadoHasta()!= null ){
							intTipoValidacionFechas = new Integer(2);
						} else if(estadoLiquidacionFechas.getDtFechaEstadoDesde()!= null
							&& estadoLiquidacionFechas.getDtFechaEstadoHasta()!= null ){
							intTipoValidacionFechas = new Integer(3);
						} else{
							blnAplicaFechas = Boolean.FALSE;
						}
						
					}
					
				}

				if(estadoLiquidacionSuc.getIntSucuIdSucursal().compareTo(new Integer(0)) != 0){
					blnAplicaSucursal = Boolean.TRUE;
						listaSucursalesFiltradas = cargarSucursalesValidas(estadoLiquidacionSuc.getIntSucuIdSucursal());
				}
				if(estadoLiquidacionSuc.getIntSucuIdSucursal().compareTo(new Integer(-2)) == 0){
					blnAplicaSucursal = Boolean.FALSE;
				}
	
				if(intIdSubsucursalFiltro.compareTo(0) != 0){
					blnAplicaSubSucursal = Boolean.TRUE;
				}
				

				//----- > cuerpo del metodo
				//listaExpedienteLiquidacionComp = getExpedientLiquidacionParaFiltros(intTipoPersona,intTipoCreditoFiltro,strNumeroSolicitudBusq);// -- Agregar tipo de liquidacion
				listaExpedienteLiquidacionComp = getExpedientLiquidacionParaFiltros(intTipoPersona);

				if(listaExpedienteLiquidacionComp != null && !listaExpedienteLiquidacionComp.isEmpty()){
					lista = new ArrayList<ExpedienteLiquidacionComp>();
					
					for (ExpedienteLiquidacionComp expComp : listaExpedienteLiquidacionComp) {
						blnCumpleFiltros = Boolean.TRUE;
						
					// *** Se valida strNumeroSolicitudBusq
						if(blnAplicaNroSolicitud){
							strNumeroSolicitudBusq = strNumeroSolicitudBusq.trim();
								if(expComp.getExpedienteLiquidacion().getId().getIntItemExpediente().toString().equalsIgnoreCase(strNumeroSolicitudBusq)){
									blnCumpleFiltros = true;
								}else{
									blnCumpleFiltros = false;
								}
			
						}
						
						if(blnCumpleFiltros){
					// *** Se valida Estado de liquidacion
							if(blnAplicaEstado){
								Integer intEstadoFiltro = estadoCondicionFiltro.getIntParaEstado();
								Integer intEstadoExp = expComp.getExpedienteLiquidacion().getEstadoLiquidacionUltimo().getIntParaEstado();
	
								if(intEstadoExp.compareTo(intEstadoFiltro)==0){
									blnCumpleFiltros = true;
								}else{
									blnCumpleFiltros = false;
								}	
							}
						}
						
						if(blnCumpleFiltros){
					// *** Se valida Tipo de liquidacion
							//	PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_RENUNCIA = 1;
							//  PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_EXPULSION = 2;
							//  PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_FALLECIMIENTO = 3;
							if(blnAplicaTipoLiquidacion){
								//Integer intTipoPrestamoFiltro =  e.getIntPersEmpresaCreditoPk();
								Integer intTipoLiquidacion = expComp.getExpedienteLiquidacion().getIntParaSubTipoOperacion();
	
								if(intTipoCreditoFiltro.compareTo(intTipoLiquidacion)==0){
									blnCumpleFiltros = true;
								}else{
									blnCumpleFiltros = false;
								}	
							}
						}
						
					
						if(blnCumpleFiltros){
					// ***  Valida fechas de inicio y fin +  esatdo
							if(blnAplicaFechas){
								List<EstadoLiquidacion> listEstados = new ArrayList<EstadoLiquidacion>();
								listEstados = expComp.getExpedienteLiquidacion().getListaEstadoLiquidacion();

								if(listEstados != null && !listEstados.isEmpty()){
									forEstados:
									for (EstadoLiquidacion estado : listEstados) {
										if(estadoLiquidacionFechas.getIntParaEstado().compareTo(estado.getIntParaEstado())==0){ 
									        switch (intTipoValidacionFechas) {
									            case 1: //Fech desde
									            		if(estado.getTsFechaEstado().after(estadoLiquidacionFechas.getDtFechaEstadoDesde())){
									            			blnCumpleFiltros = true;
														} 
										                break forEstados;
								 
									            case 2: //Fech hasta
										            	if(estado.getTsFechaEstado().before(estadoLiquidacionFechas.getDtFechaEstadoHasta())){
									            			blnCumpleFiltros = true;
														} 
										                break forEstados;
										                
									            case 3:  //Fech desde  + hasta
										            	if(estado.getTsFechaEstado().before(estadoLiquidacionFechas.getDtFechaEstadoHasta())
										            		&& estado.getTsFechaEstado().after(estadoLiquidacionFechas.getDtFechaEstadoDesde())){
									            			blnCumpleFiltros = true;
														} 
										                break forEstados;
									            
									            default:  //
									            			blnCumpleFiltros = false;
									            			break forEstados;
									        }
											
										}else{
											blnCumpleFiltros = false;
										}
									}
								}
	
							}

							
						}
						

						if(blnCumpleFiltros){
					// *** Se valida Sucursal
							if(blnAplicaSucursal){
								if(listaSucursalesFiltradas != null && !listaSucursalesFiltradas.isEmpty()){
									
									for (Sucursal sucursal : listaSucursalesFiltradas) {
										System.out.println("======================================================");
										//System.out.println("sucursal.getJuridica().getIntIdPersona()-> "+sucursal.getJuridica().getIntIdPersona());
										System.out.println("sucursal.getId().getIntIdSucursal()-> "+sucursal.getId().getIntIdSucursal());
										System.out.println("---------- SE COMPARA CON ----------");
										System.out.println("expComp.getExpedienteCredito().getEstadoCreditoUltimo().getIntIdUsuSucursalPk() -> "+expComp.getExpedienteLiquidacion().getEstadoLiquidacionUltimo().getIntSucuIdSucursal());
										System.out.println("--------RESULTADO---------------");
										System.out.println(sucursal.getJuridica().getIntIdPersona().compareTo(expComp.getExpedienteLiquidacion().getEstadoLiquidacionUltimo().getIntSucuIdSucursal())==0);
	
										//if(sucursal.getJuridica().getIntIdPersona().compareTo(expComp.getExpedienteCredito().getEstadoCreditoUltimo().getIntIdUsuSucursalPk())==0){
										if(sucursal.getId().getIntIdSucursal().compareTo(expComp.getExpedienteLiquidacion().getEstadoLiquidacionUltimo().getIntSucuIdSucursal())==0){
											blnCumpleFiltros = true;
											break;
										}else{
											blnCumpleFiltros = false;
										}
									}
								}
		
							}
						}

						if(blnCumpleFiltros){
					// *** Se valida SubSucursal
							if(blnAplicaSubSucursal){
								List<Subsucursal> listaSubSucursal = null;
								listaSubSucursal = cargarListaTablaSubSucursal(expComp.getExpedienteLiquidacion().getEstadoLiquidacionUltimo().getIntSucuIdSucursal());
								
								if(listaSubSucursal != null && !listaSubSucursal.isEmpty()){
									for (Subsucursal subSucursal : listaSubSucursal) {
										System.out.println("======================================================");
										System.out.println("subSucursal.getId().getIntIdSubSucursal()-> "+subSucursal.getId().getIntIdSubSucursal());
										System.out.println("---------- SE COMPARA CON ----------");
										System.out.println("expComp.getExpedienteCredito().getEstadoCreditoUltimo().getIntSudeIdSubsucursal() -> "+expComp.getExpedienteLiquidacion().getEstadoLiquidacionUltimo().getIntSudeIdSubsucursal());
	
										if(subSucursal.getId().getIntIdSubSucursal().compareTo(expComp.getExpedienteLiquidacion().getEstadoLiquidacionUltimo().getIntSudeIdSubsucursal())==0){
											blnCumpleFiltros = true;
											break;
										}else{
											blnCumpleFiltros = false;
										}
									}
								}
		
							}
						}
						
						
						if(blnCumpleFiltros){
					// *** Se valida strNumeroSolicitudBusq
							if(blnAplicaTipoBusqueda){
								strConsultaBusqueda = strConsultaBusqueda.trim().toUpperCase();
								if(intTipoConsultaBusqueda.compareTo(Constante.PARAM_T_TIPO_BUSQ_NOMBRES)==0){
									String strNombresSocio = expComp.getSocioComp().getPersona().getNatural().getStrApellidoPaterno()+" "+
															 expComp.getSocioComp().getPersona().getNatural().getStrApellidoMaterno()+" "+
															 expComp.getSocioComp().getPersona().getNatural().getStrNombres();
									
									if( strNombresSocio.toUpperCase().contains(strConsultaBusqueda)){
										blnCumpleFiltros = true;
									}else{
										blnCumpleFiltros = false;
									}
								}
	
								if(intTipoConsultaBusqueda.compareTo(Constante.PARAM_T_TIPO_BUSQ_DNI)==0){
									String strDniSocio = expComp.getSocioComp().getPersona().getDocumento().getStrNumeroIdentidad();
									System.out.println("dCUENTA VALIDADA ---> "+expComp.getSocioComp().getCuenta().getId().getIntCuenta());
									System.out.println("DNI VALIDADO ---> "+expComp.getSocioComp().getPersona().getDocumento().getStrNumeroIdentidad());
									if( strDniSocio.equalsIgnoreCase(strConsultaBusqueda)){
										blnCumpleFiltros = true;
									}else{
										blnCumpleFiltros = false;
									}
								}
	
								if(intTipoConsultaBusqueda.compareTo(Constante.PARAM_T_TIPO_BUSQ_RUC)==0){
									String strRucSocio = expComp.getSocioComp().getPersona().getRuc().getStrNumeroIdentidad();
									if( strRucSocio.equalsIgnoreCase(strConsultaBusqueda)){
										blnCumpleFiltros = true;
									}else{
										blnCumpleFiltros = false;
									}
								}
								
								if(intTipoConsultaBusqueda.compareTo(Constante.PARAM_T_TIPO_BUSQ_RAZONSOCIAL)==0){
									String strRazonSocial = expComp.getSocioComp().getPersona().getJuridica().getStrRazonSocial();
									if( strRazonSocial.toUpperCase().contains(strConsultaBusqueda)){
										blnCumpleFiltros = true;
									}else{
										blnCumpleFiltros = false;
									}
								}
							}
						}
						
						if(blnCumpleFiltros){
							lista.add(expComp);
						}
	
					}	
				}
				
				if(lista != null && !lista.isEmpty()){
					dto = completarDatosDeLiquidacion(lista);
				}

			} catch (Exception e) {
				log.error("Error en getListaExpedienteLiquidacionCompDeBusquedaFiltro --> "+e);
			}

			return dto;
	}

	
	
	/**
	 * Carga las listas de las sucursales validas para la busqueda
	 * @return
	 */
	private List<Subsucursal> cargarListaTablaSubSucursal(Integer intSucursal) throws Exception{
		List<Subsucursal>listaSubSucursal = null;
		EmpresaFacadeRemote empresaFacade = null;
		try {
			empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
			listaSubSucursal = empresaFacade.getListaSubSucursalPorIdSucursal(intSucursal);
			
		} catch (Exception e) {
			log.error("Error en cargarListaTablaSucursal --> "+e);
		}
		return listaSubSucursal;
	}
	
	
	/**
	 * Carga las listas de las sucursales validas para la busqueda
	 * @return
	 */
	public List<Sucursal> cargarSucursalesValidas(Integer intSucursal){
		List<Sucursal> listaSucursalesValidas = null;
		EmpresaFacadeRemote empresaFacade = null;
		Integer intValorTipo = 0;
		List<Sucursal> lstSucursales = null;
		Boolean blnCargar = Boolean.TRUE;
		try {
			empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
			/*
			PARAM_T_TIPOSUCURSAL_AGENCIA = 1;
			PARAM_T_TIPOSUCURSAL_FILIAL = 2;
			PARAM_T_TIPOSUCURSAL_SEDECENTRAL = 3;
			PARAM_T_TIPOSUCURSAL_OFICINAPRINCIPAL = 4;
			
			PARAM_T_TOTALESSUCURSALES_SUCURSALES = -2 			---- *
			PARAM_T_TOTALESSUCURSALES_AGENCIAS = -3 			---- 1 
			PARAM_T_TOTALESSUCURSALES_FILIALES = -4				---- 2
			PARAM_T_TOTALESSUCURSALES_OFICINAPRINCIPAL = -5		---- 4
			PARAM_T_TOTALESSUCURSALES_SEDE = -6					---- 3
			*/
			if(intSucursal.compareTo(Constante.PARAM_T_TOTALESSUCURSALES_AGENCIAS)==0){
				blnCargar = Boolean.TRUE;
				intValorTipo = 1;
				
			}else if(intSucursal.compareTo(Constante.PARAM_T_TOTALESSUCURSALES_FILIALES)==0){
				blnCargar = Boolean.TRUE;
				intValorTipo = 2;		
			}else if(intSucursal.compareTo(Constante.PARAM_T_TOTALESSUCURSALES_OFICINAPRINCIPAL)==0){
				blnCargar = Boolean.TRUE;
				intValorTipo = 4;
			}else if(intSucursal.compareTo(Constante.PARAM_T_TOTALESSUCURSALES_SEDE)==0){
				blnCargar = Boolean.TRUE;
				intValorTipo = 3;
			}else if(intSucursal.compareTo(Constante.PARAM_T_TOTALESSUCURSALES_SUCURSALES)==0){
				blnCargar = Boolean.FALSE;
			}
			
			
			if(blnCargar){
				lstSucursales = empresaFacade.getListaSucursalPorPkEmpresa(Constante.PARAM_EMPRESASESION);
				
				if(lstSucursales != null && !lstSucursales.isEmpty()){
					listaSucursalesValidas = new ArrayList<Sucursal>();
					if(intValorTipo.compareTo(0)==0){
						if(lstSucursales != null && !lstSucursales.isEmpty()){
							for (Sucursal sucursal : lstSucursales) {
								if(sucursal.getId().getIntIdSucursal().compareTo(intSucursal)==0){
									listaSucursalesValidas.add(sucursal);
									break;
								}
							}	
						}
						
					}else{
						
						if(lstSucursales != null && !lstSucursales.isEmpty()){
							for (Sucursal sucursal : lstSucursales) {
								if(sucursal.getIntIdTipoSucursal().compareTo(intValorTipo)==0){
									listaSucursalesValidas.add(sucursal);
								}
							}	
						}
					}
				}	
			}
			
		} catch (Exception e) {
			log.error("Error en cargarSucursalesValidas ---> "+e);
		}
		
		return listaSucursalesValidas;

	}
	
	
	/**
	 * Completa los datos de la lista de credito. Se le agrrgn las fechas de los estados y la descripcion del prestamo
	 * @param lista
	 * @return
	 */
	public List<ExpedienteLiquidacionComp> completarDatosDeLiquidacion( List<ExpedienteLiquidacionComp> lista){
		List<ExpedienteLiquidacionComp> listaFinal = null;
		CreditoFacadeRemote creditoFacade = null;
		TablaFacadeRemote tablaFacade = null;
		
		try {
			creditoFacade = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			
			if(lista != null && !lista.isEmpty()){
				listaFinal = new ArrayList<ExpedienteLiquidacionComp>();
				
				for(ExpedienteLiquidacionComp expedienteLiquidacionComp : lista){

						CreditoId creditoId= null;
						String strDescripcionExpedienteCredito = "Desconocido";
						
						// Recuperamos el nombre general del prestamo asociado al expediente
						creditoId = new CreditoId();
						Credito creditoRec = null;
						
						creditoRec = creditoFacade.getCreditoPorIdCreditoDirecto(creditoId);
						
						List<Tabla> listaDescripcionExpedienteXredito = null;
						if(creditoRec != null){
							listaDescripcionExpedienteXredito = tablaFacade.getListaTablaPorIdMaestroYNotInIdDetalle(new Integer(Constante.PARAM_T_TIPOCREDITOEMPRESA), 
																													creditoRec.getId().getIntParaTipoCreditoCod().toString());
							if(!listaDescripcionExpedienteXredito.isEmpty()){
								for (Tabla tabla : listaDescripcionExpedienteXredito) {
									if(tabla.getIntIdDetalle().compareTo(creditoRec.getIntParaTipoCreditoEmpresa())==0){
										strDescripcionExpedienteCredito = tabla.getStrDescripcion();
										break;
									}
								}
							}
						}
						expedienteLiquidacionComp.setStrDescripcionExpedienteLiquidacion(strDescripcionExpedienteCredito);
						listaFinal.add(expedienteLiquidacionComp);
				}
			}

		} catch (Exception e) {
			log.error("Error en completarDatosDeLiquidacion --> "+e);
		}

		return listaFinal;
	}
	
	
	/**
	 * Recupera los expedientes de credito con los datos necesarios para validar filtros de grilla de busqueda
	 * de solicitud de credito. 1 nat / 2 jur / 0 nada
	 * @param intTipoPersona
	 * @return
	 */
//	public List<ExpedienteLiquidacionComp> getExpedientLiquidacionParaFiltrosOLD(Integer intTipoPersona, Integer intTipoLiquidacionFiltro, String strNumeroSolicitudBusq){
//		List<ExpedienteLiquidacion> lstExpedientesTemp = null;
//		List<ExpedienteLiquidacionComp> lstExpedientesComp = new ArrayList<ExpedienteLiquidacionComp>() ;
//		ExpedienteLiquidacionComp expedienteComp = null;
//		EstadoLiquidacion ultimoEstadoLiquidacion = null;
//		EstadoLiquidacion primerEstadoLiquidacion = null;
//		CuentaFacadeRemote cuentaFacade = null;
//		PersonaFacadeRemote personaFacade = null;
//		SocioFacadeRemote socioFacade = null;
////		CreditoFacadeRemote creditoFacade = null;
////		TablaFacadeRemote tablaFacade = null;
//		SocioComp socioComp = null;
//		String strConcatenadoSolicitud = "";
//		List<EstadoLiquidacion> listaEstadosLiquidacion = null;
//		Cuenta cuentaSocio = null;
//		
//		try {
//			
//			cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
//			personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
//			socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
////			creditoFacade = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
////			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
//			
//			//lstExpedientesTemp = boExpedienteLiquidacion.getListaCompleta(intTipoLiquidacionFiltro,strNumeroSolicitudBusq);
////			ExpedienteLiquidacion expedienteFiltros = new ExpedienteLiquidacion();
//			
//			
//			//lstExpedientesTemp = boExpedienteLiquidacion.getListaCompletaFiltros(intTipoLiquidacionFiltro,strNumeroSolicitudBusq);
//			
//			if(lstExpedientesTemp != null && !lstExpedientesTemp.isEmpty()){				
//				for (ExpedienteLiquidacion expedienteLiquidacion : lstExpedientesTemp) {
//					expedienteComp = new ExpedienteLiquidacionComp();
//					Integer intIdPersona = 0;
//					Persona persona = null;
//					Juridica juridica =null;
//					cuentaSocio = new Cuenta();
//					socioComp = new SocioComp();
//					
//				//A. cargando el uiltimo estado
//					ultimoEstadoLiquidacion = boEstadoLiquidacion.getMaxEstadoLiquidacionPorPkExpediente(expedienteLiquidacion);
//					if(ultimoEstadoLiquidacion != null){
//						expedienteLiquidacion.setEstadoLiquidacionUltimo(ultimoEstadoLiquidacion);
//						expedienteComp.setEstadoLiquidacion(ultimoEstadoLiquidacion);
//						expedienteComp.setExpedienteLiquidacion(expedienteLiquidacion);
//					}
//					
//				//A.1 cargando el primer estado
//					primerEstadoLiquidacion = boEstadoLiquidacion.getMinEstadoLiquidacionPorPkExpediente(expedienteLiquidacion);
//				if(ultimoEstadoLiquidacion != null){
//					expedienteLiquidacion.setEstadoLiquidacionPrimero(primerEstadoLiquidacion);
//				}
//
//					
//				//A.2 cargando todos los estado
//					listaEstadosLiquidacion = boEstadoLiquidacion.getPorExpediente(expedienteLiquidacion);
//					if(listaEstadosLiquidacion != null){
//						
//						for (EstadoLiquidacion estado : listaEstadosLiquidacion) {
//							if(expedienteLiquidacion.getEstadoLiquidacionPrimero() != null  ){
//								expedienteComp.setStrFechaRequisito(Constante.sdf2.format(expedienteLiquidacion.getEstadoLiquidacionPrimero().getTsFechaEstado()));
//							}else{
//								if(estado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO))
//									expedienteComp.setStrFechaRequisito(Constante.sdf2.format(estado.getTsFechaEstado()));
//							}
//							if(estado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD))
//								expedienteComp.setStrFechaSolicitud(Constante.sdf2.format(estado.getTsFechaEstado()));
//							if(estado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO))
//								expedienteComp.setStrFechaAutorizacion(Constante.sdf2.format(estado.getTsFechaEstado()));
//						}
//						
//						expedienteLiquidacion.setListaEstadoLiquidacion(listaEstadosLiquidacion);
//						expedienteComp.setExpedienteLiquidacion(expedienteLiquidacion);
//					}
//		
//					
//				//B. Recuperamos y cargamos el socioComp en base  a la cuenta de cada expediente
//					CuentaId cuentaId = new CuentaId();
//					List<CuentaIntegrante> lstCuentaIntegrante = null;
//					
//					cuentaSocio = recuperarCuentaLiquidacion(expedienteLiquidacion);
//					if(cuentaSocio != null){
//						
//						cuentaId.setIntCuenta(cuentaSocio.getId().getIntCuenta());
//						cuentaId.setIntPersEmpresaPk(expedienteLiquidacion.getId().getIntPersEmpresaPk());
//						
//						lstCuentaIntegrante = cuentaFacade.getListaCuentaIntegrantePorPKCuenta(cuentaId);
//						
//						if(lstCuentaIntegrante != null && !lstCuentaIntegrante.isEmpty()){
//							for (CuentaIntegrante cuentaIntegrante : lstCuentaIntegrante) {
//								if(cuentaIntegrante.getIntParaTipoIntegranteCod().compareTo(new Integer(1))==0){
//									intIdPersona = cuentaIntegrante.getId().getIntPersonaIntegrante();
//									break;
//								}
//							}
//							
//							persona = personaFacade.getPersonaNaturalPorIdPersona(intIdPersona);
//							
//							if(persona!=null){
//								if(persona.getListaDocumento()!=null && persona.getListaDocumento().size()>0){
//									for (Documento documento : persona.getListaDocumento()) {
//										if(documento.getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))
//										&& documento.getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
//											persona.setDocumento(documento);
//											//break;
//										}
//										if(documento.getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_RUC))
//											&& documento.getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
//											persona.setRuc(documento);
//											//break;
//										}
//									}
//								}
//								socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI), persona.getDocumento().getStrNumeroIdentidad(), Constante.PARAM_EMPRESASESION);
//								expedienteComp.setSocioComp(socioComp);
//							}
//							
//							if(intTipoPersona.compareTo(2)==0){
//								juridica = personaFacade.getJuridicaPorPK(intIdPersona);
//								if(juridica != null){
//									expedienteComp.getSocioComp().getPersona().setJuridica(juridica);
//								}
//							}
//								
//						}
//						
//					}
//					
//					
//					//B.1. Recuperamos y cargamos la persona que registro la liquidacion
//					/*CuentaId cuentaIdReg = new CuentaId();
//					List<CuentaIntegrante> lstCuentaIntegranteReg = null;
//					Persona personaRegistro = new Persona();
//					personaRegistro = personaFacade.getPersonaNaturalPorIdPersona(expedienteComp.getExpedienteLiquidacion().getEstadoLiquidacionPrimero().getIntPersEmpresaEstado());
//					if(personaRegistro != null){
//						expedienteComp.getExpedienteLiquidacion().setPersonaAdministra(personaRegistro);	
//					}
//					*/
//
//				// C. recuperamos el concatenado del nro de solicitud...
//					
//					strConcatenadoSolicitud= ""+expedienteLiquidacion.getId().getIntItemExpediente();
//					expedienteComp.setStrNroSolicitudBusqueda(strConcatenadoSolicitud);
//					
//					lstExpedientesComp.add(expedienteComp);
//				}
//				
//			}
//			
//		} catch (Exception e) {
//			log.error("Error en getExpedientLiquidacionParaFiltros --> "+e);
//		}
//		
//		return lstExpedientesComp;
//	}
//	
	
	
	
	/*public List<ExpedienteLiquidacionComp> getExpedientLiquidacionParaFiltros(ExpedienteLiquidacion expediente,Boolean blnAplicaEstado, Boolean blnAplicaFechas, Boolean blnAplicaTipoBusqueda, Integer intTipoPersona ){
		
		List<ExpedienteLiquidacion> lstExpedientesTemp = null;
		List<ExpedienteLiquidacionComp> lstExpedientesComp = new ArrayList<ExpedienteLiquidacionComp>() ;
		ExpedienteLiquidacionComp expedienteComp = null;
		EstadoLiquidacion ultimoEstadoLiquidacion = null;
		EstadoLiquidacion primerEstadoLiquidacion = null;
		CuentaFacadeRemote cuentaFacade = null;
		PersonaFacadeRemote personaFacade = null;
		SocioFacadeRemote socioFacade = null;
		CreditoFacadeRemote creditoFacade = null;
		TablaFacadeRemote tablaFacade = null;
		SocioComp socioComp = null;
		String strConcatenadoSolicitud = "";
		List<EstadoLiquidacion> listaEstadosLiquidacion = null;
		Cuenta cuentaSocio = null;
		ContactoFacadeRemote contactoFacade = null;
		
		try {
			
			cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			creditoFacade = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			contactoFacade = (ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);
			
			//getDocumentoPorIdPersonaYTipoIdentidad
			
			lstExpedientesTemp = boExpedienteLiquidacion.getListaCompleta();
			//lstExpedientesTemp = boExpedienteLiquidacion.getListaCompletaFiltros(expediente);

			if(lstExpedientesTemp != null && !lstExpedientesTemp.isEmpty()){
				
				//Boolean blnAplicaEstado, Boolean blnAplicaFechas, Boolean blnAplicaTipoBusqueda, Integer intTipoPersona 
				
				for (ExpedienteLiquidacion expedienteLiquidacion : lstExpedientesTemp) {
					expedienteComp = new ExpedienteLiquidacionComp();
					Integer intIdPersona = 0;
					Persona persona = null;
					Juridica juridica =null;
					Natural natural =null;
					Documento documento = null;
					cuentaSocio = new Cuenta();
					socioComp = new SocioComp();
					
					//if(blnAplicaEstado){
						//A. cargando el uiltimo estado
						ultimoEstadoLiquidacion = boEstadoLiquidacion.getMaxEstadoLiquidacionPorPkExpediente(expedienteLiquidacion);
						if(ultimoEstadoLiquidacion != null){
							expedienteLiquidacion.setEstadoLiquidacionUltimo(ultimoEstadoLiquidacion);
							expedienteComp.setEstadoLiquidacion(ultimoEstadoLiquidacion);
							expedienteComp.setExpedienteLiquidacion(expedienteLiquidacion);
						}	
					//}
					
					
				
					// moverse a completar dfatos...
					//-------------------------------------->
				//A.1 cargando el primer estado
					primerEstadoLiquidacion = boEstadoLiquidacion.getMinEstadoLiquidacionPorPkExpediente(expedienteLiquidacion);
				if(ultimoEstadoLiquidacion != null){
					expedienteLiquidacion.setEstadoLiquidacionPrimero(primerEstadoLiquidacion);
				}
					//------------------------------------------>
				
				
					//if(blnAplicaFechas){
						//A.2 cargando todos los estado
						listaEstadosLiquidacion = boEstadoLiquidacion.getPorExpediente(expedienteLiquidacion);
						if(listaEstadosLiquidacion != null){
							
							for (EstadoLiquidacion estado : listaEstadosLiquidacion) {
								if(expedienteLiquidacion.getEstadoLiquidacionPrimero() != null  ){
									expedienteComp.setStrFechaRequisito(Constante.sdf2.format(expedienteLiquidacion.getEstadoLiquidacionPrimero().getTsFechaEstado()));
								}else{
									if(estado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO))
										expedienteComp.setStrFechaRequisito(Constante.sdf2.format(estado.getTsFechaEstado()));
								}
								if(estado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD))
									expedienteComp.setStrFechaSolicitud(Constante.sdf2.format(estado.getTsFechaEstado()));
								if(estado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO))
									expedienteComp.setStrFechaAutorizacion(Constante.sdf2.format(estado.getTsFechaEstado()));
							}
							
							expedienteLiquidacion.setListaEstadoLiquidacion(listaEstadosLiquidacion);
							expedienteComp.setExpedienteLiquidacion(expedienteLiquidacion);
						}
					//}
				
		
					//if(blnAplicaTipoBusqueda){
				//	if(true){
						//B. Recuperamos y cargamos el socioComp en base  a la cuenta de cada expediente
						CuentaId cuentaId = new CuentaId();
						List<CuentaIntegrante> lstCuentaIntegrante = null;
						//Juridica juridica = null;
						//Natural natural = null;
						
						cuentaSocio = recuperarCuentaLiquidacion(expedienteLiquidacion);
						if(cuentaSocio != null){
							
							cuentaId.setIntCuenta(cuentaSocio.getId().getIntCuenta());
							cuentaId.setIntPersEmpresaPk(expedienteLiquidacion.getId().getIntPersEmpresaPk());
							
							lstCuentaIntegrante = cuentaFacade.getListaCuentaIntegrantePorPKCuenta(cuentaId);
							
							if(lstCuentaIntegrante != null && !lstCuentaIntegrante.isEmpty()){
								for (CuentaIntegrante cuentaIntegrante : lstCuentaIntegrante) {
									if(cuentaIntegrante.getIntParaTipoIntegranteCod().compareTo(new Integer(1))==0){
										intIdPersona = cuentaIntegrante.getId().getIntPersonaIntegrante();
										break;
									}
								}
								
								persona = personaFacade.getSoloPersonaPorPK(intIdPersona);
								
								if(persona!=null){
									juridica = personaFacade.getSoloPersonaJuridicaPorPersona(intIdPersona);
				            		if(juridica != null){
				            			persona.setJuridica(juridica);
				            		}
				            		natural = personaFacade.getSoloPersonaNaturalPorIdPersona(intIdPersona);
				            		if(natural != null){
				            			persona.setNatural(natural);
				            		}
				            		documento = contactoFacade.getDocumentoPorIdPersonaYTipoIdentidad(intIdPersona, new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI));
				            		if(documento != null){
				            			persona.setDocumento(documento);
				            		}
									
									//-----------------xxxxxxxxxxx
									 switch (intTipoPersona) {  // 1 natural   2 docuemnto  3 juridica 
							            case 1: // Se recupera NAtural
								            	juridica = personaFacade.getSoloPersonaJuridicaPorPersona(intIdPersona);
							            		if(juridica != null){
							            			persona.setJuridica(juridica);
							            		}
							            		natural = personaFacade.getSoloPersonaNaturalPorIdPersona(intIdPersona);
							            		if(natural != null){
							            			persona.setNatural(natural);
							            		}
							            		documento = contactoFacade.getDocumentoPorIdPersonaYTipoIdentidad(intIdPersona, new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI));
							            		if(documento != null){
							            			persona.setDocumento(documento);
							            		}
								                break;
						 
							            case 2: // Se recupera Documento
								            	juridica = personaFacade.getSoloPersonaJuridicaPorPersona(intIdPersona);
							            		if(juridica != null){
							            			persona.setJuridica(juridica);
							            		}
							            		natural = personaFacade.getSoloPersonaNaturalPorIdPersona(intIdPersona);
							            		if(natural != null){
							            			persona.setNatural(natural);
							            		}
							            		documento = contactoFacade.getDocumentoPorIdPersonaYTipoIdentidad(intIdPersona, new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI));
							            		if(documento != null){
							            			persona.setDocumento(documento);
						            		}
								                break;
								                
							            case 3:  //Se recupera Juridica
								            	juridica = personaFacade.getSoloPersonaJuridicaPorPersona(intIdPersona);
							            		if(juridica != null){
							            			persona.setJuridica(juridica);
							            		}
							            		natural = personaFacade.getSoloPersonaNaturalPorIdPersona(intIdPersona);
							            		if(natural != null){
							            			persona.setNatural(natural);
							            		}
							            		documento = contactoFacade.getDocumentoPorIdPersonaYTipoIdentidad(intIdPersona, new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI));
							            		if(documento != null){
							            			persona.setDocumento(documento);
							            		}
								                break;
							            case 0:  juridica = personaFacade.getSoloPersonaJuridicaPorPersona(intIdPersona);
							            		if(juridica != null){
							            			persona.setJuridica(juridica);
							            		}
							            		natural = personaFacade.getSoloPersonaNaturalPorIdPersona(intIdPersona);
							            		if(natural != null){
							            			persona.setNatural(natural);
							            		}
							            		documento = contactoFacade.getDocumentoPorIdPersonaYTipoIdentidad(intIdPersona, new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI));
							            		if(documento != null){
							            			persona.setDocumento(documento);
							            		}
							            		
								                break;      
									 			
									 }
										////////////--------------xxx
									 
									 
									 socioComp.setPersona(persona);
								}		
							}
							
						}
	
					//}
				
					//B.1. Recuperamos y cargamos la persona que registro la liquidacion
					//CuentaId cuentaIdReg = new CuentaId();
					//List<CuentaIntegrante> lstCuentaIntegranteReg = null;
					//Persona personaRegistro = new Persona();
					//personaRegistro = personaFacade.getPersonaNaturalPorIdPersona(expedienteComp.getExpedienteLiquidacion().getEstadoLiquidacionPrimero().getIntPersEmpresaEstado());
					//if(personaRegistro != null){
					//	expedienteComp.getExpedienteLiquidacion().setPersonaAdministra(personaRegistro);	
					//}
					
					
					expedienteComp.setSocioComp(socioComp);
					
				// C. recuperamos el concatenado del nro de solicitud...
					strConcatenadoSolicitud= ""+expedienteLiquidacion.getId().getIntItemExpediente();
					expedienteComp.setStrNroSolicitudBusqueda(strConcatenadoSolicitud);
					
					lstExpedientesComp.add(expedienteComp);
				}
				
			}
			
		} catch (Exception e) {
			log.error("Error en getExpedientLiquidacionParaFiltros --> "+e);
		}
		
		return lstExpedientesComp;
	}*/
	
	public List<ExpedienteLiquidacionComp> getExpedientLiquidacionParaFiltros(Integer intTipoPersona){
		List<ExpedienteLiquidacion> lstExpedientesTemp = null;
		List<ExpedienteLiquidacionComp> lstExpedientesComp = new ArrayList<ExpedienteLiquidacionComp>() ;
		ExpedienteLiquidacionComp expedienteComp = null;
		EstadoLiquidacion ultimoEstadoLiquidacion = null;
		EstadoLiquidacion primerEstadoLiquidacion = null;
		CuentaFacadeRemote cuentaFacade = null;
		PersonaFacadeRemote personaFacade = null;
//		SocioFacadeRemote socioFacade = null;
//		CreditoFacadeRemote creditoFacade = null;
//		TablaFacadeRemote tablaFacade = null;
		ContactoFacadeRemote contactoFacade= null;
		SocioComp socioComp = null;
		String strConcatenadoSolicitud = "";
		List<EstadoLiquidacion> listaEstadosLiquidacion = null;
		Cuenta cuentaSocio = null;
		
		try {
			
			cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
//			socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
//			creditoFacade = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
//			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			contactoFacade = (ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);
			
			lstExpedientesTemp = boExpedienteLiquidacion.getListaCompleta();
			
			if(lstExpedientesTemp != null && !lstExpedientesTemp.isEmpty()){
				
				for (ExpedienteLiquidacion expedienteLiquidacion : lstExpedientesTemp) {
					expedienteComp = new ExpedienteLiquidacionComp();
					Integer intIdPersona = 0;
					Persona persona = null;
					Juridica juridica =null;
					cuentaSocio = new Cuenta();
					socioComp = new SocioComp();
					Natural natural = null;
					Documento documento= null;
					
				//A. cargando el uiltimo estado
					ultimoEstadoLiquidacion = boEstadoLiquidacion.getMaxEstadoLiquidacionPorPkExpediente(expedienteLiquidacion);
					if(ultimoEstadoLiquidacion != null){
						expedienteLiquidacion.setEstadoLiquidacionUltimo(ultimoEstadoLiquidacion);
						expedienteComp.setEstadoLiquidacion(ultimoEstadoLiquidacion);
						expedienteComp.setExpedienteLiquidacion(expedienteLiquidacion);
					}
					
				//A.1 cargando el primer estado
					primerEstadoLiquidacion = boEstadoLiquidacion.getMinEstadoLiquidacionPorPkExpediente(expedienteLiquidacion);
				if(ultimoEstadoLiquidacion != null){
					expedienteLiquidacion.setEstadoLiquidacionPrimero(primerEstadoLiquidacion);
				}

					
				//A.2 cargando todos los estado
					listaEstadosLiquidacion = boEstadoLiquidacion.getPorExpediente(expedienteLiquidacion);
					if(listaEstadosLiquidacion != null){
						
						for (EstadoLiquidacion estado : listaEstadosLiquidacion) {
							if(expedienteLiquidacion.getEstadoLiquidacionPrimero() != null  ){
								expedienteComp.setStrFechaRequisito(Constante.sdf2.format(expedienteLiquidacion.getEstadoLiquidacionPrimero().getTsFechaEstado()));
							}else{
								if(estado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO))
									expedienteComp.setStrFechaRequisito(Constante.sdf2.format(estado.getTsFechaEstado()));
							}
							if(estado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD))
								expedienteComp.setStrFechaSolicitud(Constante.sdf2.format(estado.getTsFechaEstado()));
							if(estado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO))
								expedienteComp.setStrFechaAutorizacion(Constante.sdf2.format(estado.getTsFechaEstado()));
						}
						
						expedienteLiquidacion.setListaEstadoLiquidacion(listaEstadosLiquidacion);
						expedienteComp.setExpedienteLiquidacion(expedienteLiquidacion);
					}
		
					
				//B. Recuperamos y cargamos el socioComp en base  a la cuenta de cada expediente
					CuentaId cuentaId = new CuentaId();
					List<CuentaIntegrante> lstCuentaIntegrante = null;
					
					cuentaSocio = recuperarCuentaLiquidacion(expedienteLiquidacion);
					if(cuentaSocio != null){
						
						cuentaId.setIntCuenta(cuentaSocio.getId().getIntCuenta());
						cuentaId.setIntPersEmpresaPk(expedienteLiquidacion.getId().getIntPersEmpresaPk());
						
						lstCuentaIntegrante = cuentaFacade.getListaCuentaIntegrantePorPKCuenta(cuentaId);
						
						if(lstCuentaIntegrante != null && !lstCuentaIntegrante.isEmpty()){
							for (CuentaIntegrante cuentaIntegrante : lstCuentaIntegrante) {
								if(cuentaIntegrante.getIntParaTipoIntegranteCod().compareTo(new Integer(1))==0){
									intIdPersona = cuentaIntegrante.getId().getIntPersonaIntegrante();
									break;
								}
							}
							
							persona = personaFacade.getSoloPersonaPorPK(intIdPersona);
							
							if(persona!=null){
								
			            		natural = personaFacade.getSoloPersonaNaturalPorIdPersona(intIdPersona);
			            		if(natural != null){
			            			persona.setNatural(natural);
			            		}
		 
								documento = contactoFacade.getDocumentoPorIdPersonaYTipoIdentidad(intIdPersona, new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI));
			            		if(documento != null){
			            			persona.setDocumento(documento);
			            		}

			            		juridica = personaFacade.getSoloPersonaJuridicaPorPersona(intIdPersona);
			            		if(persona != null){
			            			persona.setJuridica(juridica);
			            		}
								socioComp =  new   SocioComp();
								socioComp.setPersona(persona);
								expedienteComp.setSocioComp(socioComp);
								System.out.println("expedienteComp.getSocioComp() ---> "+expedienteComp.getSocioComp());
								
								/*if(expedienteComp.getSocioComp().getCuenta().getId().getIntCuenta().compareTo(91)==0){
									System.out.println("expedienteComp.getSocioComp() ---> "+expedienteComp.getSocioComp());
									
								}*/
							}
							
							/*if(intTipoPersona.compareTo(2)==0){
								juridica = personaFacade.getJuridicaPorPK(intIdPersona);
								if(juridica != null){
									expedienteComp.getSocioComp().getPersona().setJuridica(juridica);
								}
							}*/
								
						}
						
						expedienteComp.getSocioComp().setCuenta(cuentaSocio);	
					}
					
					
					//B.1. Recuperamos y cargamos la persona que registro la liquidacion
					/*CuentaId cuentaIdReg = new CuentaId();
					List<CuentaIntegrante> lstCuentaIntegranteReg = null;
					Persona personaRegistro = new Persona();
					personaRegistro = personaFacade.getPersonaNaturalPorIdPersona(expedienteComp.getExpedienteLiquidacion().getEstadoLiquidacionPrimero().getIntPersEmpresaEstado());
					if(personaRegistro != null){
						expedienteComp.getExpedienteLiquidacion().setPersonaAdministra(personaRegistro);	
					}
					*/

				// C. recuperamos el concatenado del nro de solicitud...
					
					strConcatenadoSolicitud= ""+expedienteLiquidacion.getId().getIntItemExpediente();
					expedienteComp.setStrNroSolicitudBusqueda(strConcatenadoSolicitud);
					
					lstExpedientesComp.add(expedienteComp);
				}
				
			}
			
		} catch (Exception e) {
			log.error("Error en getExpedientLiquidacionParaFiltros --> "+e);
		}
		
		return lstExpedientesComp;
	}
	
	/**
	 * Recupera la Cuenta del socio a aprtir de l expediente de liquidacion
	 * @param expedienteLIquidacion
	 */
	public Cuenta recuperarCuentaLiquidacion (ExpedienteLiquidacion expedienteLiquidacion){
		
		CuentaFacadeRemote cuentaFacade = null;
//		PersonaFacadeRemote personaFacade = null;
//		SocioFacadeRemote socioFacade= null;
		
//		Integer intIdPersona = new Integer(0);
//		Persona persona = null;
//		SocioComp socioComp= null;
		Cuenta cuentaSocio = null;
		List<ExpedienteLiquidacionDetalle> listaExpLiquiDet = null;
		
		try {
			cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
//			personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
//			socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			// Recuperamos al Socio 
			if(expedienteLiquidacion.getId() != null){
				
				listaExpLiquiDet = boExpedienteLiquidacionDetalle.getPorExpedienteLiquidacion(expedienteLiquidacion);
				if(listaExpLiquiDet != null){
					cuentaSocio = new Cuenta();
					cuentaSocio.setId(new CuentaId());
					CuentaId cuentaIdSocio = new CuentaId();
					cuentaIdSocio.setIntPersEmpresaPk(expedienteLiquidacion.getId().getIntPersEmpresaPk());
					cuentaIdSocio.setIntCuenta(listaExpLiquiDet.get(0).getId().getIntCuenta());
					cuentaSocio.setId(cuentaIdSocio);
					//cuentaSocio = cuentaFacade.getCuentaPorId(cuentaIdSocio);
					cuentaSocio = cuentaFacade.getCuentaPorPkYSituacion(cuentaSocio);
					System.out.println("CUENTAS RECUPERADAS -------> "+cuentaSocio.getId().getIntCuenta());
				}

			}

			
		} catch (Exception e) {
			log.error("Error en recuperarCuentaLiquidacion ---> "+e);
		}
		
		return cuentaSocio;
	}
	
	
	/**
	 * Recupera lista con los expedientes de liquidacion ( + ultimo estado) en base a una cuenta.
	 * @param cuentaId
	 * @return
	 */
	public List<ExpedienteLiquidacion> getListaExpedienteLiquidacionMasEstadoPorCuenta(CuentaId cuentaId){
		List<ExpedienteLiquidacion> listaExpedientesLiquidacion = null;
		List<ExpedienteLiquidacionDetalle> listaDetalles = null;
		
		ExpedienteLiquidacion expedienteLiquidacion = null;
		
		try {
			
			//1. recuperamos los detalles en base a la cuenta...
			listaDetalles = boExpedienteLiquidacionDetalle.getPorCuentaId(cuentaId);
			if(listaDetalles != null && !listaDetalles.isEmpty()){
				listaExpedientesLiquidacion = new ArrayList<ExpedienteLiquidacion>();
				for (ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle : listaDetalles) {
					EstadoLiquidacion ultimoEstadoLiquidacion = null;
					expedienteLiquidacion = new ExpedienteLiquidacion();
					ExpedienteLiquidacionId id = new ExpedienteLiquidacionId();
					id.setIntItemExpediente(expedienteLiquidacionDetalle.getId().getIntItemExpediente());
					id.setIntPersEmpresaPk(expedienteLiquidacionDetalle.getId().getIntPersEmpresa());
					expedienteLiquidacion.setId(id);
					
					// 2. recuperamos los estados asociados y verificamos el ultimo
					ultimoEstadoLiquidacion = boEstadoLiquidacion.getMaxEstadoLiquidacionPorPkExpediente(expedienteLiquidacion);
					if(ultimoEstadoLiquidacion != null){
						expedienteLiquidacion.setEstadoLiquidacionUltimo(ultimoEstadoLiquidacion);
						
					}
					listaExpedientesLiquidacion.add(expedienteLiquidacion);
				}	
			}
			
		} catch (Exception e) {
			log.error("Error en getListaExpedienteLiquidacionMasEstadoPorCuenta ---> "+e);
		}

		return listaExpedientesLiquidacion;
	}
	
	/**
	 * Graba Estado default. Para el caso de que una solicitud de credito pase directamente a estado SOLICITUD. 
	 * Registra x default un estado previo de REQUISITO.
	 * @param lstEstadoCredito
	 * @param pPK
	 * @return
	 * @throws BusinessException
	 */
	public EstadoLiquidacion grabarEstadoRequisitoDefault(EstadoLiquidacion estadoLiquidacionSolicitud, ExpedienteLiquidacionId pPK) throws BusinessException{
		EstadoLiquidacion estadoLiquidacionReq = null;
		EstadoLiquidacionId pk = null;
		
		try{
			estadoLiquidacionReq = new EstadoLiquidacion();
			pk = new EstadoLiquidacionId();
			
			pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
			pk.setIntItemExpediente(pPK.getIntItemExpediente());
			//pk.set(pPK.getIntPersEmpresaPk());
			estadoLiquidacionReq.setId(pk);
			estadoLiquidacionReq.setIntPersEmpresaEstado(estadoLiquidacionSolicitud.getIntPersEmpresaEstado());
			estadoLiquidacionReq.setIntSudeIdSubsucursal(estadoLiquidacionSolicitud.getIntSudeIdSubsucursal());
			estadoLiquidacionReq.setIntSucuIdSucursal(estadoLiquidacionSolicitud.getIntSucuIdSucursal());
			estadoLiquidacionReq.setIntParaEstado(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO);// requisito
			estadoLiquidacionReq.setIntPersUsuarioEstado(estadoLiquidacionSolicitud.getIntPersUsuarioEstado());
			estadoLiquidacionReq.setSubsucursal(estadoLiquidacionSolicitud.getSubsucursal());
			estadoLiquidacionReq.setSucursal(estadoLiquidacionSolicitud.getSucursal());
			estadoLiquidacionReq.setTsFechaEstado(estadoLiquidacionSolicitud.getTsFechaEstado());
			estadoLiquidacionReq = boEstadoLiquidacion.grabar(estadoLiquidacionReq);

		}catch(BusinessException e){
			log.error("Error - BusinessException - en grabarEstadoRequisitoDefault ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en grabarEstadoRequisitoDefault ---> "+e);
			throw new BusinessException(e);
		}
		return estadoLiquidacionReq;
	}
	
	
	/**
	 * 
	 * @param intTipoConsultaBusqueda
	 * @param strConsultaBusqueda
	 * @param strNumeroSolicitudBusq
	 * @param estadoCondicionFiltro
	 * @param intTipoCreditoFiltro
	 * @param estadoLiquidacionBusqueda
	 * @param intTipoBusquedaFechaFiltro
	 * @param estadoLiquidacionFechas
	 * @param estadoLiquidacionSuc
	 * @param intIdSubsucursalFiltro
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteLiquidacionComp> getListaExpedienteLiquidacionAutorizacionCompDeBusquedaFiltro(
			Integer intTipoConsultaBusqueda,String strConsultaBusqueda, 
			String strNumeroSolicitudBusq,
			EstadoLiquidacion estadoCondicionFiltro, 
			Integer intTipoCreditoFiltro, 
			EstadoLiquidacion estadoLiquidacionFechas,
			EstadoLiquidacion estadoLiquidacionSuc,
			Integer intIdSubsucursalFiltro)  throws BusinessException{
			
				List<ExpedienteLiquidacionComp> lista = null;
				List<ExpedienteLiquidacionComp> dto = null;
				
				List<ExpedienteLiquidacionComp> listaExpedienteLiquidacionComp = null;
				//EstadoLiquidacion estadoLiquidacion;
				//List<EstadoLiquidacion> listaEstadoCredito = null;
				//SocioComp socioComp = null;
				//Persona persona = null;
				//Integer intIdPersona = null;
				//PersonaFacadeRemote personaFacade = null;
				//SocioFacadeRemote socioFacade = null;
//				TablaFacadeRemote  tablaFacade = null;
//				CreditoFacadeRemote creditoFacade = null;
//				EmpresaFacadeRemote empresaFacade = null;
//				CreditoId creditoId = null;
//				List<Tabla> listaDescripcionExpedienteXredito= null;
//				String strDescripcionExpedienteCredito = null;
				
				// Booleanos que me indican que aplicar
				Boolean blnAplicaTipoBusqueda = Boolean.FALSE; 		// Tipo de Busqueda  (1)
				Boolean blnAplicaNroSolicitud = Boolean.FALSE;		// Nro. Solicitud  (2)  *
				Boolean blnAplicaEstado = Boolean.FALSE; 			// Condición  (3)	*
				Boolean blnAplicaTipoLiquidacion = Boolean.FALSE; 	// Tipo  (4)		**
				Boolean blnAplicaFechas = Boolean.FALSE;  			//Fecha  inicio fin (5) *
				Boolean blnAplicaSucursal = Boolean.FALSE;  		// Sucursal(6)		*
				Boolean blnAplicaSubSucursal = Boolean.FALSE; 		// subsucursal(7)	*
				
				Integer intTipoPersona = 0; // 1 natural   2 juridica
//				List<Sucursal> listaSucursales = null;
				List<Sucursal> listaSucursalesFiltradas = null;
//				List<Subsucursal> listaSubSucursales = null;
//				List<Subsucursal> listaSubSucursalesFiltradas = null;
				Boolean blnCumpleFiltros = Boolean.TRUE;
//				List<ExpedienteLiquidacionComp> listaFinal = null;
				Integer intTipoValidacionFechas = 0;  // 0 no procede - 1 inicio  - 2 final  - 3 todo
				ExpedienteLiquidacion expedienteFiltro = null;

				try {
					expedienteFiltro = new ExpedienteLiquidacion();
					expedienteFiltro.setId(new ExpedienteLiquidacionId());
					
					//socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
//					tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
//					creditoFacade = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
					//empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);

					if(intTipoConsultaBusqueda.compareTo(0)!=0){
						blnAplicaTipoBusqueda = Boolean.TRUE;
						if(intTipoConsultaBusqueda.compareTo(Constante.PARAM_T_TIPO_BUSQ_NOMBRES)==0 
						|| intTipoConsultaBusqueda.compareTo(Constante.PARAM_T_TIPO_BUSQ_DNI)==0){
							intTipoPersona = 1;
						}else{
							intTipoPersona = 2;
						}
					}

					if(!(strNumeroSolicitudBusq.equalsIgnoreCase("") || strNumeroSolicitudBusq.isEmpty())){
						strNumeroSolicitudBusq = strNumeroSolicitudBusq.trim();
						blnAplicaNroSolicitud = Boolean.TRUE;
						
						expedienteFiltro.getId().setIntItemExpediente(new Integer(strNumeroSolicitudBusq));
					} 

					if(estadoCondicionFiltro.getIntParaEstado().compareTo(0)!=0){
						blnAplicaEstado = Boolean.TRUE;	
					} 

					if(intTipoCreditoFiltro.compareTo(0)!=0){
						//expedienteFiltro.setIntParaSubTipoOperacion(intTipoCreditoFiltro);
						blnAplicaTipoLiquidacion = Boolean.TRUE;	
					} 

					if(estadoLiquidacionFechas.getIntParaEstado().compareTo(0)!=0){
						blnAplicaFechas = Boolean.TRUE;
						
						if(estadoLiquidacionFechas.getDtFechaEstadoDesde() != null 
							|| estadoLiquidacionFechas.getDtFechaEstadoHasta()!= null){
							blnAplicaFechas = Boolean.TRUE;
						}else{
							blnAplicaFechas = Boolean.FALSE;
						}
						
						if(blnAplicaFechas){
							if(estadoLiquidacionFechas.getDtFechaEstadoDesde()!= null
								&& estadoLiquidacionFechas.getDtFechaEstadoHasta()== null ){
								intTipoValidacionFechas = new Integer(1);
							} else if(estadoLiquidacionFechas.getDtFechaEstadoDesde()== null
								&& estadoLiquidacionFechas.getDtFechaEstadoHasta()!= null ){
								intTipoValidacionFechas = new Integer(2);
							} else if(estadoLiquidacionFechas.getDtFechaEstadoDesde()!= null
								&& estadoLiquidacionFechas.getDtFechaEstadoHasta()!= null ){
								intTipoValidacionFechas = new Integer(3);
							} else{
								blnAplicaFechas = Boolean.FALSE;
							}
							
						}
						
					}

					if(estadoLiquidacionSuc.getIntSucuIdSucursal().compareTo(new Integer(0)) != 0){
						blnAplicaSucursal = Boolean.TRUE;
							listaSucursalesFiltradas = cargarSucursalesValidas(estadoLiquidacionSuc.getIntSucuIdSucursal());
					}
					if(estadoLiquidacionSuc.getIntSucuIdSucursal().compareTo(new Integer(-2)) == 0){
						blnAplicaSucursal = Boolean.FALSE;
					}
		
					if(intIdSubsucursalFiltro.compareTo(0) != 0){
						blnAplicaSubSucursal = Boolean.TRUE;
					}
					

					//----- > cuerpo del metodo
					listaExpedienteLiquidacionComp = getExpedientLiquidacionAutorizacionParaFiltros(expedienteFiltro, intTipoPersona);
					
					if(listaExpedienteLiquidacionComp != null && !listaExpedienteLiquidacionComp.isEmpty()){
						lista = new ArrayList<ExpedienteLiquidacionComp>();
						
						for (ExpedienteLiquidacionComp expComp : listaExpedienteLiquidacionComp) {
							blnCumpleFiltros = Boolean.TRUE;
							
						// *** Se valida strNumeroSolicitudBusq
							if(blnAplicaNroSolicitud){
								strNumeroSolicitudBusq = strNumeroSolicitudBusq.trim();
									if(expComp.getExpedienteLiquidacion().getId().getIntItemExpediente().toString().equalsIgnoreCase(strNumeroSolicitudBusq)){
										blnCumpleFiltros = true;
									}else{
										blnCumpleFiltros = false;
									}
				
							}
							
							if(blnCumpleFiltros){
						// *** Se valida Estado de liquidacion
								if(blnAplicaEstado){
									Integer intEstadoFiltro = estadoCondicionFiltro.getIntParaEstado();
									Integer intEstadoExp = expComp.getExpedienteLiquidacion().getEstadoLiquidacionUltimo().getIntParaEstado();
		
									if(intEstadoExp.compareTo(intEstadoFiltro)==0){
										blnCumpleFiltros = true;
									}else{
										blnCumpleFiltros = false;
									}	
								}
							}
							
							if(blnCumpleFiltros){
						// *** Se valida Tipo de liquidacion
								//	PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_RENUNCIA = 1;
								//  PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_EXPULSION = 2;
								//  PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_FALLECIMIENTO = 3;
								if(blnAplicaTipoLiquidacion){
									//Integer intTipoPrestamoFiltro =  e.getIntPersEmpresaCreditoPk();
									Integer intTipoLiquidacion = expComp.getExpedienteLiquidacion().getIntParaSubTipoOperacion();
		
									if(intTipoCreditoFiltro.compareTo(intTipoLiquidacion)==0){
										blnCumpleFiltros = true;
									}else{
										blnCumpleFiltros = false;
									}	
								}
							}
							
						
							if(blnCumpleFiltros){
						// ***  Valida fechas de inicio y fin +  esatdo
								if(blnAplicaFechas){
									List<EstadoLiquidacion> listEstados = new ArrayList<EstadoLiquidacion>();
									listEstados = expComp.getExpedienteLiquidacion().getListaEstadoLiquidacion();

									if(listEstados != null && !listEstados.isEmpty()){
										forEstados:
										for (EstadoLiquidacion estado : listEstados) {
											if(estadoLiquidacionFechas.getIntParaEstado().compareTo(estado.getIntParaEstado())==0){ 
										        switch (intTipoValidacionFechas) {
										            case 1: //Fech desde
										            		if(estado.getTsFechaEstado().after(estadoLiquidacionFechas.getDtFechaEstadoDesde())){
										            			blnCumpleFiltros = true;
															} 
											                break forEstados;
									 
										            case 2: //Fech hasta
											            	if(estado.getTsFechaEstado().before(estadoLiquidacionFechas.getDtFechaEstadoHasta())){
										            			blnCumpleFiltros = true;
															} 
											                break forEstados;
											                
										            case 3:  //Fech desde  + hasta
											            	if(estado.getTsFechaEstado().before(estadoLiquidacionFechas.getDtFechaEstadoHasta())
											            		&& estado.getTsFechaEstado().after(estadoLiquidacionFechas.getDtFechaEstadoDesde())){
										            			blnCumpleFiltros = true;
															} 
											                break forEstados;
										            
										            default:  //
										            			blnCumpleFiltros = false;
										            			break forEstados;
										        }
												
											}else{
												blnCumpleFiltros = false;
											}
										}
									}
		
								}

								
							}
							

							if(blnCumpleFiltros){
						// *** Se valida Sucursal
								if(blnAplicaSucursal){
									if(listaSucursalesFiltradas != null && !listaSucursalesFiltradas.isEmpty()){
										
										for (Sucursal sucursal : listaSucursalesFiltradas) {
											//System.out.println("======================================================");
											//System.out.println("sucursal.getJuridica().getIntIdPersona()-> "+sucursal.getJuridica().getIntIdPersona());
											//System.out.println("sucursal.getId().getIntIdSucursal()-> "+sucursal.getId().getIntIdSucursal());
											//System.out.println("---------- SE COMPARA CON ----------");
											//System.out.println("expComp.getExpedienteCredito().getEstadoCreditoUltimo().getIntIdUsuSucursalPk() -> "+expComp.getExpedienteLiquidacion().getEstadoLiquidacionUltimo().getIntSucuIdSucursal());
											//System.out.println("--------RESULTADO---------------");
											//System.out.println(sucursal.getJuridica().getIntIdPersona().compareTo(expComp.getExpedienteLiquidacion().getEstadoLiquidacionUltimo().getIntSucuIdSucursal())==0);
		
											//if(sucursal.getJuridica().getIntIdPersona().compareTo(expComp.getExpedienteCredito().getEstadoCreditoUltimo().getIntIdUsuSucursalPk())==0){
											if(sucursal.getId().getIntIdSucursal().compareTo(expComp.getExpedienteLiquidacion().getEstadoLiquidacionUltimo().getIntSucuIdSucursal())==0){
												blnCumpleFiltros = true;
												break;
											}else{
												blnCumpleFiltros = false;
											}
										}
									}
			
								}
							}

							if(blnCumpleFiltros){
						// *** Se valida SubSucursal
								if(blnAplicaSubSucursal){
									List<Subsucursal> listaSubSucursal = null;
									listaSubSucursal = cargarListaTablaSubSucursal(expComp.getExpedienteLiquidacion().getEstadoLiquidacionUltimo().getIntSucuIdSucursal());
									
									if(listaSubSucursal != null && !listaSubSucursal.isEmpty()){
										for (Subsucursal subSucursal : listaSubSucursal) {
											//System.out.println("======================================================");
											//System.out.println("subSucursal.getId().getIntIdSubSucursal()-> "+subSucursal.getId().getIntIdSubSucursal());
											//System.out.println("---------- SE COMPARA CON ----------");
											//System.out.println("expComp.getExpedienteCredito().getEstadoCreditoUltimo().getIntSudeIdSubsucursal() -> "+expComp.getExpedienteLiquidacion().getEstadoLiquidacionUltimo().getIntSudeIdSubsucursal());
		
											if(subSucursal.getId().getIntIdSubSucursal().compareTo(expComp.getExpedienteLiquidacion().getEstadoLiquidacionUltimo().getIntSudeIdSubsucursal())==0){
												blnCumpleFiltros = true;
												break;
											}else{
												blnCumpleFiltros = false;
											}
										}
									}
			
								}
							}
							
							
							if(blnCumpleFiltros){
						// *** Se valida strNumeroSolicitudBusq
								if(blnAplicaTipoBusqueda){

									strConsultaBusqueda = strConsultaBusqueda.trim().toUpperCase();
									if(intTipoConsultaBusqueda.compareTo(Constante.PARAM_T_TIPO_BUSQ_NOMBRES)==0){
										String strNombresSocio = expComp.getSocioComp().getPersona().getNatural().getStrApellidoPaterno()+" "+
																 expComp.getSocioComp().getPersona().getNatural().getStrApellidoMaterno()+" "+
																 expComp.getSocioComp().getPersona().getNatural().getStrNombres();
										
										if( strNombresSocio.toUpperCase().contains(strConsultaBusqueda)){
											blnCumpleFiltros = true;
										}else{
											blnCumpleFiltros = false;
										}
									}
		
									if(intTipoConsultaBusqueda.compareTo(Constante.PARAM_T_TIPO_BUSQ_DNI)==0){
										//System.out.println("ooooooooooooooo----> xxx "+expComp.getExpedienteLiquidacion().getId().getIntItemExpediente());
										//System.out.println("getIntIdPersona----> xxx "+expComp.getSocioComp().getPersona().getIntIdPersona());
										

										String strDniSocio = expComp.getSocioComp().getPersona().getDocumento().getStrNumeroIdentidad();
										if( strDniSocio.equalsIgnoreCase(strConsultaBusqueda)){
											blnCumpleFiltros = true;
										}else{
											blnCumpleFiltros = false;
										}
									}
		
									if(intTipoConsultaBusqueda.compareTo(Constante.PARAM_T_TIPO_BUSQ_RUC)==0){
										String strRucSocio = expComp.getSocioComp().getPersona().getStrRuc();
										if( strRucSocio.equalsIgnoreCase(strConsultaBusqueda)){
											blnCumpleFiltros = true;
										}else{
											blnCumpleFiltros = false;
										}
									}
									
									if(intTipoConsultaBusqueda.compareTo(Constante.PARAM_T_TIPO_BUSQ_RAZONSOCIAL)==0){
										String strRazonSocial = expComp.getSocioComp().getPersona().getJuridica().getStrRazonSocial();
										if( strRazonSocial.toUpperCase().contains(strConsultaBusqueda)){
											blnCumpleFiltros = true;
										}else{
											blnCumpleFiltros = false;
										}
									}
								}
							}
							
							if(blnCumpleFiltros){
								lista.add(expComp);
							}
		
						}	
					}
					
					if(lista != null && !lista.isEmpty()){
						dto = completarDatosDeLiquidacion(lista);
					}

				} catch (Exception e) {
					log.error("Error en getListaExpedienteLiquidacionCompDeBusquedaFiltro --> "+e);
				}

				return dto;
		}
	
	/*public List<ExpedienteLiquidacionComp> getListaExpedienteLiquidacionAutorizacionCompDeBusquedaFiltro(
		Integer intTipoConsultaBusqueda,String strConsultaBusqueda, 
		String strNumeroSolicitudBusq,
		EstadoLiquidacion estadoCondicionFiltro, 
		Integer intTipoCreditoFiltro, 
		EstadoLiquidacion estadoLiquidacionFechas,
		EstadoLiquidacion estadoLiquidacionSuc,
		Integer intIdSubsucursalFiltro)  throws BusinessException{
		
			List<ExpedienteLiquidacionComp> lista = null;
			List<ExpedienteLiquidacionComp> dto = null;
			
			List<ExpedienteLiquidacionComp> listaExpedienteLiquidacionComp = null;
			TablaFacadeRemote  tablaFacade = null;
			CreditoFacadeRemote creditoFacade = null;
			EmpresaFacadeRemote empresaFacade = null;
			CreditoId creditoId = null;
			List<Tabla> listaDescripcionExpedienteXredito= null;
			String strDescripcionExpedienteCredito = null;
			SocioFacadeRemote socioFacade = null;
			
			// Booleanos que me indican que aplicar
			Boolean blnAplicaTipoBusqueda = Boolean.FALSE; 		// Tipo de Busqueda  (1)
			Boolean blnAplicaNroSolicitud = Boolean.FALSE;		// Nro. Solicitud  (2)  *
			Boolean blnAplicaEstado = Boolean.FALSE; 			// Condición  (3)	*
			Boolean blnAplicaTipoLiquidacion = Boolean.FALSE; 	// Tipo  (4)		**
			Boolean blnAplicaFechas = Boolean.FALSE;  			//Fecha  inicio fin (5) *
			Boolean blnAplicaSucursal = Boolean.FALSE;  		// Sucursal(6)		*
			Boolean blnAplicaSubSucursal = Boolean.FALSE; 		// subsucursal(7)	*
			
			Integer intTipoPersona = 0; // 1 natural   2 docuemnto  3 juridica 
			List<Sucursal> listaSucursales = null;
			List<Sucursal> listaSucursalesFiltradas = null;
			List<Subsucursal> listaSubSucursales = null;
			List<Subsucursal> listaSubSucursalesFiltradas = null;
			Boolean blnCumpleFiltros = Boolean.TRUE;
			List<ExpedienteLiquidacionComp> listaFinal = null;
			Integer intTipoValidacionFechas = 0;  // 0 no procede - 1 inicio  - 2 final  - 3 todo
			ExpedienteLiquidacionId id = new ExpedienteLiquidacionId();
			ExpedienteLiquidacion expediente = new ExpedienteLiquidacion();
			expediente.setId(id);
			
			try {
				socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
				tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
				creditoFacade = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
				empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);

				if(intTipoConsultaBusqueda.compareTo(0)!=0){
					blnAplicaTipoBusqueda = Boolean.TRUE;
					if(intTipoConsultaBusqueda.compareTo(Constante.PARAM_T_TIPO_BUSQ_NOMBRES)==0){
						intTipoPersona = 1;
					}else if(intTipoConsultaBusqueda.compareTo(Constante.PARAM_T_TIPO_BUSQ_DNI)==0){
						intTipoPersona = 2;
					}else if(intTipoConsultaBusqueda.compareTo(Constante.PARAM_T_TIPO_BUSQ_RAZONSOCIAL)==0){
						intTipoPersona = 3;
					}
				}

				if(estadoCondicionFiltro.getIntParaEstado().compareTo(0)!=0){
					blnAplicaEstado = Boolean.TRUE;	
				} 

				if(estadoLiquidacionFechas.getIntParaEstado().compareTo(0)!=0){
					blnAplicaFechas = Boolean.TRUE;
					
					if(estadoLiquidacionFechas.getDtFechaEstadoDesde() != null 
						|| estadoLiquidacionFechas.getDtFechaEstadoHasta()!= null){
						blnAplicaFechas = Boolean.TRUE;
					}else{
						blnAplicaFechas = Boolean.FALSE;
					}
					
					if(blnAplicaFechas){
						if(estadoLiquidacionFechas.getDtFechaEstadoDesde()!= null
							&& estadoLiquidacionFechas.getDtFechaEstadoHasta()== null ){
							intTipoValidacionFechas = new Integer(1);
						} else if(estadoLiquidacionFechas.getDtFechaEstadoDesde()== null
							&& estadoLiquidacionFechas.getDtFechaEstadoHasta()!= null ){
							intTipoValidacionFechas = new Integer(2);
						} else if(estadoLiquidacionFechas.getDtFechaEstadoDesde()!= null
							&& estadoLiquidacionFechas.getDtFechaEstadoHasta()!= null ){
							intTipoValidacionFechas = new Integer(3);
						} else{
							blnAplicaFechas = Boolean.FALSE;
						}
						
					}
					
				}

				if(estadoLiquidacionSuc.getIntSucuIdSucursal().compareTo(new Integer(0)) != 0){
					blnAplicaSucursal = Boolean.TRUE;
						listaSucursalesFiltradas = cargarSucursalesValidas(estadoLiquidacionSuc.getIntSucuIdSucursal());
				}
				if(estadoLiquidacionSuc.getIntSucuIdSucursal().compareTo(new Integer(-2)) == 0){
					blnAplicaSucursal = Boolean.FALSE;
				}
	
				if(intIdSubsucursalFiltro.compareTo(0) != 0){
					blnAplicaSubSucursal = Boolean.TRUE;
				}
				
				
				
				if(!(strNumeroSolicitudBusq.equalsIgnoreCase("") || strNumeroSolicitudBusq.isEmpty())){
					strNumeroSolicitudBusq = strNumeroSolicitudBusq.trim();
					expediente.getId().setIntItemExpediente(new Integer(strNumeroSolicitudBusq));
				} 

				if(intTipoCreditoFiltro.compareTo(0)!=0){
					expediente.setIntParaSubTipoOperacion(intTipoCreditoFiltro);	
				}


				//----- > cuerpo del metodo
				listaExpedienteLiquidacionComp = getExpedientLiquidacionParaFiltros(expediente,blnAplicaEstado, blnAplicaFechas,blnAplicaTipoBusqueda, intTipoPersona );// -- Agregar tipo de liquidacion
				
				
				if(listaExpedienteLiquidacionComp != null && !listaExpedienteLiquidacionComp.isEmpty()){
					lista = new ArrayList<ExpedienteLiquidacionComp>();
					
					for (ExpedienteLiquidacionComp expComp : listaExpedienteLiquidacionComp) {
						blnCumpleFiltros = Boolean.TRUE;
						
						if(blnCumpleFiltros){
					// *** Se valida Estado de liquidacion
							if(blnAplicaEstado){
								Integer intEstadoFiltro = estadoCondicionFiltro.getIntParaEstado();
								Integer intEstadoExp = expComp.getExpedienteLiquidacion().getEstadoLiquidacionUltimo().getIntParaEstado();
	
								if(intEstadoExp.compareTo(intEstadoFiltro)==0){
									blnCumpleFiltros = true;
								}else{
									blnCumpleFiltros = false;
								}	
							}
						}

						if(blnCumpleFiltros){
					// ***  Valida fechas de inicio y fin +  esatdo
							if(blnAplicaFechas){
								List<EstadoLiquidacion> listEstados = new ArrayList<EstadoLiquidacion>();
								listEstados = expComp.getExpedienteLiquidacion().getListaEstadoLiquidacion();

								if(listEstados != null && !listEstados.isEmpty()){
									forEstados:
									for (EstadoLiquidacion estado : listEstados) {
										if(estadoLiquidacionFechas.getIntParaEstado().compareTo(estado.getIntParaEstado())==0){ 
									        switch (intTipoValidacionFechas) {
									            case 1: //Fech desde
									            		if(estado.getTsFechaEstado().after(estadoLiquidacionFechas.getDtFechaEstadoDesde())){
									            			blnCumpleFiltros = true;
														} 
										                break forEstados;
								 
									            case 2: //Fech hasta
										            	if(estado.getTsFechaEstado().before(estadoLiquidacionFechas.getDtFechaEstadoHasta())){
									            			blnCumpleFiltros = true;
														} 
										                break forEstados;
										                
									            case 3:  //Fech desde  + hasta
										            	if(estado.getTsFechaEstado().before(estadoLiquidacionFechas.getDtFechaEstadoHasta())
										            		&& estado.getTsFechaEstado().after(estadoLiquidacionFechas.getDtFechaEstadoDesde())){
									            			blnCumpleFiltros = true;
														} 
										                break forEstados;
									            
									            default:  //
									            			blnCumpleFiltros = false;
									            			break forEstados;
									        }
											
										}else{
											blnCumpleFiltros = false;
										}
									}
								}
	
							}

							
						}
						

						if(blnCumpleFiltros){
					// *** Se valida Sucursal
							if(blnAplicaSucursal){
								if(listaSucursalesFiltradas != null && !listaSucursalesFiltradas.isEmpty()){
									
									for (Sucursal sucursal : listaSucursalesFiltradas) {
										//if(sucursal.getJuridica().getIntIdPersona().compareTo(expComp.getExpedienteCredito().getEstadoCreditoUltimo().getIntIdUsuSucursalPk())==0){
										if(sucursal.getId().getIntIdSucursal().compareTo(expComp.getExpedienteLiquidacion().getEstadoLiquidacionUltimo().getIntSucuIdSucursal())==0){
											blnCumpleFiltros = true;
											break;
										}else{
											blnCumpleFiltros = false;
										}
									}
								}
		
							}
						}

						if(blnCumpleFiltros){
					// *** Se valida SubSucursal
							if(blnAplicaSubSucursal){
								List<Subsucursal> listaSubSucursal = null;
								listaSubSucursal = cargarListaTablaSubSucursal(expComp.getExpedienteLiquidacion().getEstadoLiquidacionUltimo().getIntSucuIdSucursal());
								
								if(listaSubSucursal != null && !listaSubSucursal.isEmpty()){
									for (Subsucursal subSucursal : listaSubSucursal) {
										if(subSucursal.getId().getIntIdSubSucursal().compareTo(expComp.getExpedienteLiquidacion().getEstadoLiquidacionUltimo().getIntSudeIdSubsucursal())==0){
											blnCumpleFiltros = true;
											break;
										}else{
											blnCumpleFiltros = false;
										}
									}
								}
		
							}
						}
						
						
						if(blnCumpleFiltros){
					// *** Se valida strNumeroSolicitudBusq
							if(blnAplicaTipoBusqueda){
								
								if(intTipoConsultaBusqueda.compareTo(Constante.PARAM_T_TIPO_BUSQ_NOMBRES)==0){
									strConsultaBusqueda = strConsultaBusqueda.trim().toUpperCase();
									String strNombresSocio = expComp.getSocioComp().getPersona().getNatural().getStrApellidoPaterno()+" "+
									 expComp.getSocioComp().getPersona().getNatural().getStrApellidoMaterno()+" "+
									 expComp.getSocioComp().getPersona().getNatural().getStrNombres();
			
									if( strNombresSocio.toUpperCase().contains(strConsultaBusqueda)){
										blnCumpleFiltros = true;
									}else{
										blnCumpleFiltros = false;
									}
															
								}
								
								if(intTipoConsultaBusqueda.compareTo(Constante.PARAM_T_TIPO_BUSQ_DNI)==0){
									System.out.println("expComp---Z "+expComp.getExpedienteLiquidacion().getId().getIntItemExpediente());
									System.out.println("expComp.getSocioComp().getPersona().getDocumento().getStrNumeroIdentidad()---Z "+expComp.getSocioComp().getPersona().getDocumento().getStrNumeroIdentidad());
									if(expComp.getSocioComp().getPersona().getDocumento().getStrNumeroIdentidad() != null){
										String strDniSocio = expComp.getSocioComp().getPersona().getDocumento().getStrNumeroIdentidad();
										if( strDniSocio.equalsIgnoreCase(strConsultaBusqueda)){
											blnCumpleFiltros = true;
										}else{
											blnCumpleFiltros = false;
										}
									}
									
								}
								
								if(intTipoConsultaBusqueda.compareTo(Constante.PARAM_T_TIPO_BUSQ_RUC)==0){
									String strRucSocio = expComp.getSocioComp().getPersona().getStrRuc();
									if( strRucSocio.equalsIgnoreCase(strConsultaBusqueda)){
										blnCumpleFiltros = true;
									}else{
										blnCumpleFiltros = false;
									}
								}

								if(intTipoConsultaBusqueda.compareTo(Constante.PARAM_T_TIPO_BUSQ_RAZONSOCIAL)==0){
									String strRazonSocial = expComp.getSocioComp().getPersona().getJuridica().getStrRazonSocial();
									if( strRazonSocial.toUpperCase().contains(strConsultaBusqueda)){
										blnCumpleFiltros = true;
									}else{
										blnCumpleFiltros = false;
									}
								}
							}
						}
						
						if(blnCumpleFiltros){
							lista.add(expComp);
						}
	
					}	
				}
				
				if(lista != null && !lista.isEmpty()){
					dto = completarDatosDeLiquidacion(lista);
				}

			} catch (Exception e) {
				log.error("Error en getListaExpedienteLiquidacionCompDeBusquedaFiltro --> "+e);
			}

			return dto;
	}*/
	
	
	/**
	 * Recupera los expedientes de credito con los datos necesarios para validar filtros de grilla de busqueda
	 * de autorizacion de credito. 1 nat / 2 jur / 0 nada
	 * @param intTipoPersona
	 * @return
	 */
	public List<ExpedienteLiquidacionComp> getExpedientLiquidacionAutorizacionParaFiltros(ExpedienteLiquidacion expLiquidacion, Integer intTipoPersona){
		List<ExpedienteLiquidacion> lstExpedientesTemp = null;
		List<ExpedienteLiquidacionComp> lstExpedientesComp = new ArrayList<ExpedienteLiquidacionComp>() ;
		ExpedienteLiquidacionComp expedienteComp = null;
		EstadoLiquidacion ultimoEstadoLiquidacion = null;
		EstadoLiquidacion primerEstadoLiquidacion = null;
		CuentaFacadeRemote cuentaFacade = null;
		PersonaFacadeRemote personaFacade = null;
//		SocioFacadeRemote socioFacade = null;
//		CreditoFacadeRemote creditoFacade = null;
//		TablaFacadeRemote tablaFacade = null;
		SocioComp socioComp = null;
		String strConcatenadoSolicitud = "";
		List<EstadoLiquidacion> listaEstadosLiquidacion = null;
		Cuenta cuentaSocio = null;
		ContactoFacadeRemote contactoFacade = null;
		
		try {
			
			cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
//			socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
//			creditoFacade = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
//			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			contactoFacade = (ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);
			//lstExpedientesTemp = boExpedienteLiquidacion.getListaCompletaFiltros(expLiquidacion);
			lstExpedientesTemp = boExpedienteLiquidacion.getListaCompleta();
			
			
			if(lstExpedientesTemp != null && !lstExpedientesTemp.isEmpty()){
				
				for (ExpedienteLiquidacion expedienteLiquidacion : lstExpedientesTemp) {
					expedienteComp = new ExpedienteLiquidacionComp();
					Integer intIdPersona = 0;
					Persona persona = null;
					Juridica juridica =null;
					Natural natural = null;
					Documento documento= null;
					cuentaSocio = new Cuenta();
					socioComp = new SocioComp();
					
				//A. cargando el uiltimo estado
					ultimoEstadoLiquidacion = boEstadoLiquidacion.getMaxEstadoLiquidacionPorPkExpediente(expedienteLiquidacion);
					if(ultimoEstadoLiquidacion != null){
						expedienteLiquidacion.setEstadoLiquidacionUltimo(ultimoEstadoLiquidacion);
						expedienteComp.setEstadoLiquidacion(ultimoEstadoLiquidacion);
						expedienteComp.setExpedienteLiquidacion(expedienteLiquidacion);
					}
					
					/*
					public static final Integer PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO = 1;
					public static final Integer PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD = 2;
					public static final Integer PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO = 3;
					public static final Integer PARAM_T_ESTADOSOLICPRESTAMO_APROBADO = 4;
					public static final Integer PARAM_T_ESTADOSOLICPRESTAMO_RECHAZADO = 5;
					public static final Integer PARAM_T_ESTADOSOLICPRESTAMO_GIRADO = 6;
					public static final Integer PARAM_T_ESTADOSOLICPRESTAMO_ANULADO = 7;
					public static final Integer PARAM_T_ESTADOSOLICPRESTAMO_CANCELADO = 8;
					*/
					if(ultimoEstadoLiquidacion.getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD)==0
					  || ultimoEstadoLiquidacion.getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)==0
					  || ultimoEstadoLiquidacion.getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_RECHAZADO)==0){
						
						//A.1 cargando el primer estado
						primerEstadoLiquidacion = boEstadoLiquidacion.getMinEstadoLiquidacionPorPkExpediente(expedienteLiquidacion);
						if(ultimoEstadoLiquidacion != null){
							expedienteLiquidacion.setEstadoLiquidacionPrimero(primerEstadoLiquidacion);
						}
						
						//A.2 cargando todos los estado
						listaEstadosLiquidacion = boEstadoLiquidacion.getPorExpediente(expedienteLiquidacion);
						if(listaEstadosLiquidacion != null){
							
							for (EstadoLiquidacion estado : listaEstadosLiquidacion) {
								if(expedienteLiquidacion.getEstadoLiquidacionPrimero() != null  ){
									expedienteComp.setStrFechaRequisito(Constante.sdf2.format(expedienteLiquidacion.getEstadoLiquidacionPrimero().getTsFechaEstado()));
								}else{
									if(estado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO))
										expedienteComp.setStrFechaRequisito(Constante.sdf2.format(estado.getTsFechaEstado()));
								}
								if(estado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD))
									expedienteComp.setStrFechaSolicitud(Constante.sdf2.format(estado.getTsFechaEstado()));
								if(estado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO))
									expedienteComp.setStrFechaAutorizacion(Constante.sdf2.format(estado.getTsFechaEstado()));
							}
							
							expedienteLiquidacion.setListaEstadoLiquidacion(listaEstadosLiquidacion);
							expedienteComp.setExpedienteLiquidacion(expedienteLiquidacion);
						}
						
						
						//B. Recuperamos y cargamos el socioComp en base  a la cuenta de cada expediente
						CuentaId cuentaId = new CuentaId();
						List<CuentaIntegrante> lstCuentaIntegrante = null;
						
						cuentaSocio = recuperarCuentaLiquidacion(expedienteLiquidacion);
						if(cuentaSocio != null){
							
							cuentaId.setIntCuenta(cuentaSocio.getId().getIntCuenta());
							cuentaId.setIntPersEmpresaPk(expedienteLiquidacion.getId().getIntPersEmpresaPk());
							
							lstCuentaIntegrante = cuentaFacade.getListaCuentaIntegrantePorPKCuenta(cuentaId);
							
							if(lstCuentaIntegrante != null && !lstCuentaIntegrante.isEmpty()){
								for (CuentaIntegrante cuentaIntegrante : lstCuentaIntegrante) {
									if(cuentaIntegrante.getIntParaTipoIntegranteCod().compareTo(new Integer(1))==0){
										intIdPersona = cuentaIntegrante.getId().getIntPersonaIntegrante();
										break;
									}
								}
								
								//persona = personaFacade.getPersonaNaturalPorIdPersona(intIdPersona);
								persona = personaFacade.getSoloPersonaPorPK(intIdPersona);
								if(persona!=null){
									
				            		natural = personaFacade.getSoloPersonaNaturalPorIdPersona(intIdPersona);
				            		if(natural != null){
				            			persona.setNatural(natural);
				            		}
			 
									documento = contactoFacade.getDocumentoPorIdPersonaYTipoIdentidad(intIdPersona, new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI));
				            		if(documento != null){
				            			persona.setDocumento(documento);
				            		}

				            		juridica = personaFacade.getSoloPersonaJuridicaPorPersona(intIdPersona);
				            		if(persona != null){
				            			persona.setJuridica(juridica);
				            		}
									socioComp =  new   SocioComp();
									socioComp.setPersona(persona);
									expedienteComp.setSocioComp(socioComp);
								}
								
								/*if(intTipoPersona.compareTo(2)==0){
									juridica = personaFacade.getJuridicaPorPK(intIdPersona);
									if(juridica != null){
										expedienteComp.getSocioComp().getPersona().setJuridica(juridica);
									}
								}*/
									
							}
							
						}

						//B.1. Recuperamos y cargamos la persona que registro la liquidacion
						/*CuentaId cuentaIdReg = new CuentaId();
						List<CuentaIntegrante> lstCuentaIntegranteReg = null;
						Persona personaRegistro = new Persona();
						personaRegistro = personaFacade.getPersonaNaturalPorIdPersona(expedienteComp.getExpedienteLiquidacion().getEstadoLiquidacionPrimero().getIntPersEmpresaEstado());
						if(personaRegistro != null){
							expedienteComp.getExpedienteLiquidacion().setPersonaAdministra(personaRegistro);	
						}
						*/

					// C. recuperamos el concatenado del nro de solicitud...
						
						strConcatenadoSolicitud= ""+expedienteLiquidacion.getId().getIntItemExpediente();
						expedienteComp.setStrNroSolicitudBusqueda(strConcatenadoSolicitud);
						
						lstExpedientesComp.add(expedienteComp);
	
					}
				}
				
			}
			
		} catch (Exception e) {
			log.error("Error en getExpedientLiquidacionParaFiltros --> "+e);
		}
		
		return lstExpedientesComp;
	}
	
	
	 /**
	  * Registra el proceso de liquidacion de cuentas.
	  * Cambio de Estado de la Cuenta.
	  * Cambio de estado de Cuentas concepto detalle.
	  * Generar asientos en libro diario.
	  * 
	  * @throws BusinessException
	  */
	/////////////////////////  METODO  ANTERIOR  - por modificaf 
	/*public void aprobarLiquidacionCuentas(Cuenta cuentaSocio, ExpedienteLiquidacion expediente ,Integer operacion,Integer intParaEstadoLiquidacionCod)throws BusinessException{
		 Boolean blnOk= Boolean.FALSE;
		 CuentaFacadeRemote cuentaFacade= null;
		 ConceptoFacadeRemote conceptoFacade = null;
		 LibroDiarioFacadeRemote libroDiarioFacade = null;
		 AutorizacionLiquidacionFacadeRemote autorizacionFacade = null;
		 
		 CuentaConceptoDetalle cuentaCtoDet = null;
		 CuentaConceptoDetalleId cuentaCtoDetId = null;
		 LibroDiario libroDiarioProvisionLiqui = null;
		 Usuario usuario;
		
		
		 try {
			 usuario = (Usuario) getRequest().getSession().getAttribute("usuario");
			 
			 cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			 conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			 libroDiarioFacade = (LibroDiarioFacadeRemote)EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			 autorizacionFacade = (AutorizacionLiquidacionFacadeRemote)EJBFactory.getRemote(AutorizacionLiquidacionFacadeRemote.class);
			 
			 // A. Cambio de estado de Cuenta. Por liquidar.
			 	cuentaSocio.setIntParaSituacionCuentaCod(Constante.PARAM_T_SITUACIONCUENTA_POR_LIQUIDAR);
				cuentaSocio = cuentaFacade.modificarCuenta(cuentaSocio);
				if(cuentaSocio.getIntParaSituacionCuentaCod().compareTo(Constante.PARAM_T_SITUACIONCUENTA_POR_LIQUIDAR)==0){
					String comment = "Todo Ok";
				}
			 
				
			 // B. Cambio de estado a cuentas concepto detalle.
				if( !expediente.getListaExpedienteLiquidacionDetalle().isEmpty()){
					List<ExpedienteLiquidacionDetalle> detalles = expediente.getListaExpedienteLiquidacionDetalle();
					for(int d=0; d<detalles.size();d++){
						cuentaCtoDetId = new CuentaConceptoDetalleId();
						cuentaCtoDetId.setIntCuentaPk(detalles.get(d).getId().getIntCuenta());
						cuentaCtoDetId.setIntItemCuentaConcepto(detalles.get(d).getId().getIntItemCuentaConcepto());
						cuentaCtoDetId.setIntPersEmpresaPk(detalles.get(d).getId().getIntPersEmpresa());
						cuentaCtoDet = new CuentaConceptoDetalle();
						cuentaCtoDet.setId(cuentaCtoDetId);
						conceptoFacade.modificarDetallePorConcepto(cuentaCtoDet);
					}
					
					
				}
			
				 // C. Asientos de Libro diario.
					if (operacion.compareTo(Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_AUTORIZAR_PRESTAMO)==0) {
						libroDiarioProvisionLiqui = autorizacionFacade.generarLibroDiarioLiquidacion(expediente);
						if(libroDiarioProvisionLiqui != null){
							libroDiarioProvisionLiqui = libroDiarioFacade.grabarLibroDiario(libroDiarioProvisionLiqui);
							
							
							// D. Cambiod e eetado del expediente de liquyidacion.
							EstadoLiquidacion estadoLiquidacionLibDiario = new EstadoLiquidacion();
							EstadoLiquidacionId estadoLiquidacionId = null;
							estadoLiquidacionLibDiario.setId(estadoLiquidacionId);
							estadoLiquidacionLibDiario.setTsFechaEstado(new Timestamp(new Date().getTime()));
							estadoLiquidacionLibDiario.setIntPersEmpresaEstado(usuario.getEmpresa().getIntIdEmpresa());
							estadoLiquidacionLibDiario.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
							estadoLiquidacionLibDiario.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
							estadoLiquidacionLibDiario.setIntPersUsuarioEstado(usuario.getIntPersPersonaPk());
							estadoLiquidacionLibDiario.setIntParaEstado(intParaEstadoLiquidacionCod);
							
							if(intParaEstadoLiquidacionCod.compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)==0){
								estadoLiquidacionLibDiario.setIntContCodigoLibro(libroDiarioProvisionLiqui.getId().getIntContCodigoLibro() +1);
								estadoLiquidacionLibDiario.setIntContPeriodoLibro(libroDiarioProvisionLiqui.getId().getIntContPeriodoLibro());
								estadoLiquidacionLibDiario.setIntPersEmpresaLibro(libroDiarioProvisionLiqui.getId().getIntPersEmpresaLibro());
								//solicitudPrestamoFacade.modificarExpedienteCredito(expedienteCredito);	
								expediente.getListaEstadoLiquidacion().add(estadoLiquidacionLibDiario);
								grabarListaDinamicaEstadoLiquidacion(expediente.getListaEstadoLiquidacion(), expediente.getId());
								//modificarExpedienteLiquidacion(expediente);
							}	
						}

					}
					
					
						
			}catch(BusinessException e){
				//blnOk = Boolean.FALSE;
				throw e;
			}catch(Exception e){
				throw new BusinessException(e);
			}
	 }*/
	
	/**
	 * 
	 */
//	private void recuperarModeloContableLiquidacionCuentas(){
//		SolicitudCtaCteTipo solicitudCtaCteTipo = null;
//		List<Modelo> listaModelo= null;
//		ModeloFacadeRemote modeloFacade = null;
//		try {
//			modeloFacade = (ModeloFacadeRemote)EJBFactory.getRemote(ModeloFacadeRemote.class);
//			
//			if (solicitudCtaCteTipo.getIntMotivoSolicitud().equals(Constante.PARAM_T_MOTIVO_SOLICITUD_CTACTE_LIQUIDACION_CUENTAS)){
//			    listaModelo = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_LIQUIDACIONCUENTA,2);
//			 }
//			
//			
//		} catch (Exception e) {
//			log.error("Error en recuperarModeloContableLiquidacionCuentas ---> "+e);
//		}
//		
//	}
	
	
	
	/**
	 * Inicia el proceso de liquidacion de cuentas.
	 * 1. Graba la Solicitud de ctacte, estados y tipo.
	 * @param socioComp
	 * @param strPeriodo
	 * @param requisitoLiq
	 * @param expedienteLiquidacionSeleccionado
	 * @return
	 * @throws BusinessException
	 */
	public LibroDiario generarProcesosDeLiquidacionCuentas_1(SocioComp socioComp, Integer intPeriodo, RequisitoLiquidacion requisitoLiq,
		ExpedienteLiquidacion expedienteLiquidacionSeleccionado,Usuario usuario,
		ExpedienteLiquidacion expedienteLiquidacion, Integer intEstadoAprobado, Integer
		intTipoCambio, Date dtNuevoFechaProgramacionPago, Integer intNuevoMotivoRenuncia,Auditoria auditoria) throws BusinessException {
		
		 AutorizacionLiquidacionFacadeRemote autorizacionLiqFacade = null;
//		 LiquidacionFacadeRemote liquidacionFacade = null;
//		 CuentacteFacadeRemote cuentaCteFacade = null;
		 LibroDiario libroDiario = null;
		 SolicitudCtaCteTipo solicitudCtaCteTipo = null; 
//		 Boolean blnTodoOk = Boolean.FALSE;
		 SolicitudCtaCte solicitudCtaCte= null;
		 SolicitudCtaCte solicitudCtaCteDevolucion= null;
		 List<Movimiento> listaMovientos = null;
		 Devolucion devolucionLiq = null;
		 ExpedienteLiquidacion expLiqAudi = null;
		 EstadoLiquidacion estadoFinalLiquidacion = null;
		 
		 List<CuentaConceptoComp> listaCuentaConceptosComp = null;
		 CuentaConceptoComp cuentaConceptoRetiroComp = null;
		 
		 
		 try {	
				autorizacionLiqFacade = (AutorizacionLiquidacionFacadeRemote)EJBFactory.getRemote(AutorizacionLiquidacionFacadeRemote.class);
				solicitudCtaCte =  autorizacionLiqFacade.grabarSolicitudCtaCteParaLiquidacion_1(socioComp, intPeriodo, requisitoLiq,usuario);
				ConceptoFacadeRemote conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
				
				if(solicitudCtaCte != null && solicitudCtaCte.getListaSolCtaCteTipo() != null 
					&& !solicitudCtaCte.getListaSolCtaCteTipo().isEmpty()){
					 
					 solicitudCtaCteTipo= solicitudCtaCte.getListaSolCtaCteTipo().get(0);
					 
					 
					// 1. Recuperamos todas las cuentas concepto del socio: retiro, aportes y agregamos la de interes de retiro.
						listaCuentaConceptosComp = recuperarCuentasConceptoXSocio(socioComp.getCuenta().getId());
						if(listaCuentaConceptosComp != null && !listaCuentaConceptosComp.isEmpty()){
							for (CuentaConceptoComp cuentaConceptoComp : listaCuentaConceptosComp) {
								//2. Guardamos en el objeto cuentaConceptoRetiroComp el registro correspondiente a fdo. retiro
								if(cuentaConceptoComp.getIntParaTipoCaptacionModelo().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0){														
									cuentaConceptoRetiroComp = new CuentaConceptoComp();
									cuentaConceptoRetiroComp = cuentaConceptoComp;
									//3. Agregamos las cuentas conceptos detalles
									cuentaConceptoRetiroComp.setLstCuentaConceptoDetalle(new ArrayList<CuentaConceptoDetalle>());
									List<CuentaConceptoDetalle> lstCtaCptoDet = conceptoFacade.getListaCuentaConceptoDetallePorCuentaConcepto(cuentaConceptoRetiroComp.getCuentaConcepto().getId());
									if (lstCtaCptoDet!=null && !lstCtaCptoDet.isEmpty()) {
										for (CuentaConceptoDetalle x : lstCtaCptoDet) {
											if (x.getTsFin()==null) {
												cuentaConceptoRetiroComp.getLstCuentaConceptoDetalle().add(x);
											}
										}
									}
									expedienteLiquidacionSeleccionado.setCuentaConceptoComp(cuentaConceptoRetiroComp);
									break;
									
								}
							}	
						}
						if (cuentaConceptoRetiroComp.getCuentaConcepto().getId().getIntCuentaPk()!=null) {
							
							//4. Generamos el movimiento del interes calculado
						 	Movimiento ultimoMovimientoInteresRetiro = recuperarUltimoMovimeintoInteresRetiro(cuentaConceptoRetiroComp, expedienteLiquidacion);
						 	String strFechaUltimoInteres = "";
//						 	BigDecimal bdMontoRegistro = BigDecimal.ZERO;
							if(ultimoMovimientoInteresRetiro != null){
								strFechaUltimoInteres = Constante.sdf.format(ultimoMovimientoInteresRetiro.getTsFechaMovimiento());
							}else{
								strFechaUltimoInteres = Constante.sdf.format(cuentaConceptoRetiroComp.getLstCuentaConceptoDetalle().get(0).getTsInicio());
							}
						 	
//						 	String strFechaUltimoInteres = Constante.sdf.format(ultimoMovimientoInteresRetiro.getTsFechaMovimiento());
							Date dtFechaUltimoInteres = Constante.sdf.parse(strFechaUltimoInteres);
							
							Date dtHoy = new Date();

							Integer nroDias =  obtenerDiasEntreFechas(dtHoy, dtFechaUltimoInteres);
							log.info("numero de dias calculados: "+nroDias);
							nroDias = Math.abs(nroDias);
							if(nroDias.compareTo(0)!= 0){
								//5. Realizamos el calculo del interes generado a la fecha de registro...
								BigDecimal bdMontoInteresCalculado = calcularInteresRetiroAcumulado(cuentaConceptoRetiroComp, expedienteLiquidacion);
								// Generamos el movimiento correspondiente al interes ganado
								Movimiento movInteresGanado = new Movimiento();
								movInteresGanado.setTsFechaMovimiento(new Timestamp(new Date().getTime()));
								movInteresGanado.setIntCuenta(socioComp.getCuenta().getId().getIntCuenta());
								movInteresGanado.setIntPersEmpresa(socioComp.getCuenta().getId().getIntPersEmpresaPk());					// persona
								movInteresGanado.setIntPersPersonaIntegrante(socioComp.getPersona().getIntIdPersona());// persona
								movInteresGanado.setIntItemCuentaConcepto(cuentaConceptoRetiroComp.getCuentaConcepto().getId().getIntItemCuentaConcepto());
								movInteresGanado.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO_INTERES);
								movInteresGanado.setIntParaTipoMovimiento(Constante.PARAM_T_TIPO_MOVIMIENTO_TRANSFERENCIA_PROVISIONES);
								movInteresGanado.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO);
								movInteresGanado.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
								movInteresGanado.setBdMontoSaldo(ultimoMovimientoInteresRetiro.getBdMontoSaldo()!=null?ultimoMovimientoInteresRetiro.getBdMontoSaldo():BigDecimal.ZERO.add(bdMontoInteresCalculado));
								movInteresGanado.setBdMontoMovimiento(bdMontoInteresCalculado);
								movInteresGanado.setIntPersEmpresaUsuario(usuario.getEmpresa().getIntIdEmpresa()); // usuario
								movInteresGanado.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk()); // usuario
								// Seteamos en el expediente el movimiento de interes registrado.
								expedienteLiquidacionSeleccionado.setMovimiento(movInteresGanado);
							}else{
								expedienteLiquidacionSeleccionado.setMovimiento(ultimoMovimientoInteresRetiro);
							}
						}					 	
						
						
					libroDiario = autorizacionLiqFacade.generarAsientoContableLiquidacionCuentasYTransferencia_2(solicitudCtaCteTipo, 
																											socioComp, 
																											usuario, 
																											expedienteLiquidacionSeleccionado);
					if(libroDiario != null && libroDiario.getId().getIntContCodigoLibro() != null){
						
						solicitudCtaCteDevolucion = autorizacionLiqFacade.grabarSolicitudCtaCteParaDevolucionLiquidacion(socioComp, intPeriodo, requisitoLiq,usuario, expedienteLiquidacionSeleccionado,libroDiario);
						// Si la solicitud de devolucion se genera correctaemte
						 if(solicitudCtaCteDevolucion != null && solicitudCtaCteDevolucion.getId() != null 
							&& solicitudCtaCteDevolucion.getId().getIntCcobItemsolctacte() != null 
							&& solicitudCtaCteDevolucion.getListaSolCtaCteTipo() != null 
							&& !solicitudCtaCteDevolucion.getListaSolCtaCteTipo().isEmpty()){

							// Validamos y generamos los registros en Movimeitno q cancelan las cuentas de retiro, aportes e interes...
							 listaMovientos  = autorizacionLiqFacade.validarYGenerarCuentaConceptoMovimentoYCuentaCtoDetalleActualizaciones_3(socioComp, libroDiario, usuario,expedienteLiquidacion);
							 if(listaMovientos != null && !listaMovientos.isEmpty()){
								 
								 devolucionLiq = generarDevolucionLiquidacion(solicitudCtaCteDevolucion, listaMovientos, socioComp);
								 
								 if(devolucionLiq.getId().getIntItemDevolucion() != null){
									 actualizarFechaFinCuentaConceptoDetalle(socioComp);
									 
										 if(auditoria != null){
											 expLiqAudi = autorizacionLiqFacade.modificarExpedienteLiquidacionParaAuditoria(expedienteLiquidacionSeleccionado, intTipoCambio, dtNuevoFechaProgramacionPago, intNuevoMotivoRenuncia);
											if(expLiqAudi != null){
												grabarAuditoria(auditoria);
											}else{
												throw new BusinessException("Error. No se registro la auditoria.");
											}
										 }

										estadoFinalLiquidacion = cambioEstadoLiquidacion(expedienteLiquidacionSeleccionado, intEstadoAprobado, usuario, libroDiario);
										if(estadoFinalLiquidacion != null && estadoFinalLiquidacion.getId().getIntItemEstado() != null){
											 grabarAutorizacionLiquidacion(expedienteLiquidacion);
											 cambiarSituacionCuenta(socioComp);
											 //JCHAVEZ 05.06.2014 Se actualiza el cuco_saldo de expediente liquidacion detalle, sumandole
											 //el interes calculado al monto de fdo de retiro
											 for (CuentaConceptoComp cuentaConceptoComp : listaCuentaConceptosComp) {
												if(cuentaConceptoComp.getIntParaTipoCaptacionModelo().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0){
													ExpedienteLiquidacionDetalleId expLiqDetId = new ExpedienteLiquidacionDetalleId();
													expLiqDetId.setIntPersEmpresa(expedienteLiquidacion.getId().getIntPersEmpresaPk());
													expLiqDetId.setIntPersEmpresaLiquidacion(expedienteLiquidacion.getId().getIntPersEmpresaPk());
													expLiqDetId.setIntCuenta(cuentaConceptoComp.getCuentaConcepto().getId().getIntCuentaPk());
													expLiqDetId.setIntItemCuentaConcepto(cuentaConceptoComp.getCuentaConcepto().getId().getIntItemCuentaConcepto());
													expLiqDetId.setIntItemExpediente(expedienteLiquidacion.getId().getIntItemExpediente());
													
													ExpedienteLiquidacionDetalle expLiqDetalle = boExpedienteLiquidacionDetalle.getPorPk(expLiqDetId);
													if (expLiqDetalle!=null && expLiqDetalle.getId().getIntCuenta()!=null) {
														expLiqDetalle.setBdSaldo(expLiqDetalle.getBdSaldo().add(expedienteLiquidacion.getMovimiento()!=null?expedienteLiquidacion.getMovimiento().getBdMontoSaldo():BigDecimal.ZERO));
														boExpedienteLiquidacionDetalle.modificar(expLiqDetalle);
														break;
													}else{
														throw new BusinessException("Error. No existe registro Expediente Liquidacion Detalle. Liquidacion.");
													}
												}
											 }
											 
										 }else{
											 throw new BusinessException("Error. No se pudo registrar el estado final. Liquidacion.");
										 }
									}else{
										throw new BusinessException("Error. No se genero el registro de Devolucion - Liquidacion.");
									}
								}else{
									 throw new BusinessException("Error. No se generaron los moviemitnos de liquidacion de cuenta.");
								 }
						 }else{
							 throw new BusinessException("Error. No se genero la Solicitud CtaCte Devolucion. Liquidacion.");
						 }
					}else{
						 throw new BusinessException("Error. No se genero el libro diario. Liquidacion Cta.");
					 }
				 }else{
					 throw new BusinessException("Error. No se genero la solicitud de ctacte, tipo y estados. Liquidacion Cta.");
				 }

			} catch (Exception e) {
				throw new BusinessException("Error. Error en generarProcesosDeLiquidacionCuentas_1. Se revierte todo. ---> "+e );
				//log.error("Error en generarSolicitudCtaCteRefinan --> "+e);
			}
		
		return libroDiario;
	}
	
	public BigDecimal calcularInteresRetiroAcumulado(CuentaConceptoComp cuentaConceptoComp, ExpedienteLiquidacion expedienteLiquidacion){
		List<Movimiento> listaMovimiento = null; 
		Movimiento movimientoInteresUltimo = null;
		BigDecimal bdInteresCalculado = BigDecimal.ZERO;
		Captacion beanCaptacion = null;
		CaptacionFacadeRemote captacionFacade = null;
		ConceptoFacadeRemote conceptoFacadeRemote= null;
		BigDecimal bdMontoInteresMasSaldoInteres = BigDecimal.ZERO;
		CuentaConceptoDetalle cuentaConceptoDetalleRetiro = new CuentaConceptoDetalle();
//		List<CuentaConceptoDetalle> lstCtaCtpoDet = null;
		try {
			CuentaConcepto cuentaConceptoRetiro = cuentaConceptoComp.getCuentaConcepto();
			cuentaConceptoDetalleRetiro = cuentaConceptoComp.getLstCuentaConceptoDetalle().get(0);
			
			captacionFacade = (CaptacionFacadeRemote)EJBFactory.getRemote(CaptacionFacadeRemote.class);
			conceptoFacadeRemote = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			
			CaptacionId captacionId = new CaptacionId();
			captacionId.setIntPersEmpresaPk(cuentaConceptoDetalleRetiro.getId().getIntPersEmpresaPk());
			captacionId.setIntParaTipoCaptacionCod(cuentaConceptoDetalleRetiro.getIntParaTipoConceptoCod());
			captacionId.setIntItem(cuentaConceptoDetalleRetiro.getIntItemConcepto());

			beanCaptacion = captacionFacade.getCaptacionPorIdCaptacion(captacionId);
						
						
			if(beanCaptacion != null){
				// 1. Recuperamos las llaves de la cuenta cto retiro, para obetener los movimientos de interes de retiro.
				//cuentaConceptoRetiro = recuperarCuentaConceptoBase(Constante.PARAM_T_CUENTACONCEPTO_RETIRO);
				if(cuentaConceptoRetiro != null){
					listaMovimiento = conceptoFacadeRemote.getListaMovimientoPorCtaItemConceptoTipoConceptoGeneral(cuentaConceptoRetiro.getId().getIntPersEmpresaPk(), 
							cuentaConceptoRetiro.getId().getIntCuentaPk(), 
							cuentaConceptoRetiro.getId().getIntItemCuentaConcepto(), 
							Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO_INTERES);
					
					if(listaMovimiento != null && !listaMovimiento.isEmpty()){
						//Ordenamos los subtipos por int
						Collections.sort(listaMovimiento, new Comparator<Movimiento>(){
							public int compare(Movimiento uno, Movimiento otro) {
								return uno.getIntItemMovimiento().compareTo(otro.getIntItemMovimiento());
							}
						});
						
						Integer tamanno = 0;
						tamanno = listaMovimiento.size();
						tamanno = tamanno -1;
						// recuperamos el ultimo registro y en base a le se realiza calculo...
						movimientoInteresUltimo = new Movimiento();
						movimientoInteresUltimo = listaMovimiento.get(tamanno);
						
						BigDecimal bdTotalBaseCtaMasInt = BigDecimal.ZERO;
						BigDecimal bdUltimoCapRetiro = BigDecimal.ZERO;
						
						// a. 	Calculamos el monto base para el calculo de interes
						bdTotalBaseCtaMasInt = movimientoInteresUltimo.getBdMontoSaldo();
						// Agregado 26.05.2014 jchavez. ademas del utimo interes se debe de recuperar de movimiento la ultima 
						// amortizacion de capital de fdo. retiro.

						List<Movimiento> lstMovCapital = null;
						
						lstMovCapital = conceptoFacadeRemote.getListaMovimientoPorCtaItemConceptoTipoConceptoGeneral(cuentaConceptoRetiro.getId().getIntPersEmpresaPk(),
								cuentaConceptoRetiro.getId().getIntCuentaPk(),cuentaConceptoRetiro.getId().getIntItemCuentaConcepto(),
								Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO);
						
						
						if (lstMovCapital!=null && !lstMovCapital.isEmpty()) {
							bdUltimoCapRetiro = lstMovCapital.get(0).getBdMontoSaldo();
						}
						// b. Recuperamos nro de dias entres fecha de movimeitno y hoy.
						Date dtFechaUltimoInteres = new Date();
						String strFechaUltimoInteres = Constante.sdf.format(movimientoInteresUltimo.getTsFechaMovimiento());
						dtFechaUltimoInteres = Constante.sdf.parse(strFechaUltimoInteres);
						
						Date dtHoy = new Date();

						Integer nroDias =  obtenerDiasEntreFechas(dtHoy, dtFechaUltimoInteres);
						nroDias = Math.abs(nroDias);
						
						
						if(nroDias.compareTo(0)!= 0){
							// c. Se aplica formula simple de interes --> Monto*Porc Interes*(dias/30)
							/*
							 * Modificacion 26.04.2014 jchavez
							 * Porc Interes beanCaptacion.getBdTem(). 
							 * Se agrega a la formula el *(nro dias)/30
							 */
							bdInteresCalculado =  (bdTotalBaseCtaMasInt.add(bdUltimoCapRetiro)).multiply(new BigDecimal(nroDias).multiply(beanCaptacion.getBdTem())).divide(new BigDecimal(3000),2,RoundingMode.HALF_UP);
							bdMontoInteresMasSaldoInteres = bdInteresCalculado.add(bdTotalBaseCtaMasInt);
						}else{
							bdInteresCalculado = BigDecimal.ZERO;
							bdMontoInteresMasSaldoInteres = BigDecimal.ZERO.add(bdTotalBaseCtaMasInt);
						}
					}
					//Agregado 22.04.2014 jchavez
					//En caso no exista movimiento anterior, usar las tablas cuenta concepto y cuenta concepto detalle
					else{
						cuentaConceptoDetalleRetiro.setId(new CuentaConceptoDetalleId());
						cuentaConceptoDetalleRetiro.getId().setIntPersEmpresaPk(cuentaConceptoRetiro.getId().getIntPersEmpresaPk());
						cuentaConceptoDetalleRetiro.getId().setIntCuentaPk(cuentaConceptoRetiro.getId().getIntCuentaPk());
						cuentaConceptoDetalleRetiro.getId().setIntItemCuentaConcepto(cuentaConceptoRetiro.getId().getIntItemCuentaConcepto());
						cuentaConceptoDetalleRetiro.setIntParaTipoConceptoCod(Constante.CAPTACION_FDO_RETIRO);
						cuentaConceptoDetalleRetiro = conceptoFacadeRemote.getCuentaConceptoDetallePorPkYTipoConcepto(cuentaConceptoDetalleRetiro);
						
						Date dtHoy = new Date();
						Integer nroDias =  obtenerDiasEntreFechas(convertirTimestampToDate(cuentaConceptoDetalleRetiro.getTsInicio()),dtHoy);
						
						bdInteresCalculado =  cuentaConceptoRetiro.getBdSaldo().multiply(new BigDecimal(nroDias).multiply(beanCaptacion.getBdTem())).divide(new BigDecimal(3000),2,RoundingMode.HALF_UP);
						bdMontoInteresMasSaldoInteres = bdInteresCalculado.add(BigDecimal.ZERO);
					}
				}
			}
			
		} catch (Exception e) {
			log.error("Error en calcularInteresRetiro ---> "+e);
		}
		return bdMontoInteresMasSaldoInteres;
	}
	
    private static Date convertirTimestampToDate(Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }	
    
	public Movimiento recuperarUltimoMovimeintoInteresRetiro(CuentaConceptoComp cuentaConceptoComp, ExpedienteLiquidacion expedienteLiquidacion){
		List<Movimiento> listaMovimiento = null; 
		///CuentaConcepto cuentaConceptoRetiro = null;
		Movimiento movimientoInteresUltimo = null;
//		BigDecimal bdMontoTotal = BigDecimal.ZERO;
//		Integer intNroDias = 0;
//		BigDecimal bdPorcentajeInteres = BigDecimal.ZERO;	
//		BigDecimal bdInteresCalculado = BigDecimal.ZERO;
		Captacion beanCaptacion = null;
		CaptacionFacadeRemote captacionFacade = null;
		ConceptoFacadeRemote conceptoFacadeRemote= null;

		try {
			CuentaConcepto cuentaConceptoRetiro = cuentaConceptoComp.getCuentaConcepto();
			CuentaConceptoDetalle cuentaConceptoDetalleRetiro = cuentaConceptoComp.getLstCuentaConceptoDetalle().get(0);
			captacionFacade = (CaptacionFacadeRemote)EJBFactory.getRemote(CaptacionFacadeRemote.class);
			conceptoFacadeRemote = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			
			CaptacionId captacionId = new CaptacionId();
			captacionId.setIntPersEmpresaPk(cuentaConceptoDetalleRetiro.getId().getIntPersEmpresaPk());
			captacionId.setIntParaTipoCaptacionCod(cuentaConceptoDetalleRetiro.getIntParaTipoConceptoCod());
			captacionId.setIntItem(cuentaConceptoDetalleRetiro.getIntItemConcepto());

			beanCaptacion = captacionFacade.getCaptacionPorIdCaptacion(captacionId);
						
						
			if(beanCaptacion != null){
				
				if(cuentaConceptoRetiro != null){
					listaMovimiento = conceptoFacadeRemote.getListaMovimientoPorCtaItemConceptoTipoConceptoGeneral(cuentaConceptoRetiro.getId().getIntPersEmpresaPk(), 
							cuentaConceptoRetiro.getId().getIntCuentaPk(), 
							cuentaConceptoRetiro.getId().getIntItemCuentaConcepto(), 
							Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO_INTERES);
					
					if(listaMovimiento != null && !listaMovimiento.isEmpty()){
						//Ordenamos los subtipos por int
						Collections.sort(listaMovimiento, new Comparator<Movimiento>(){
							public int compare(Movimiento uno, Movimiento otro) {
								return uno.getIntItemMovimiento().compareTo(otro.getIntItemMovimiento());
							}
						});

						Integer tamanno = 0;
						tamanno = listaMovimiento.size();
						tamanno = tamanno -1;
						// recuperamos el ultimo registro y en base a le se realiza calculo...
						movimientoInteresUltimo = new Movimiento();
						movimientoInteresUltimo = listaMovimiento.get(tamanno);

					}
				}
			}
			
		} catch (Exception e) {
			log.error("Error en recuperarUltimoMovimeintoInteresRetiro ---> "+e);
		}
		
		return movimientoInteresUltimo;
	}
	
	public List<CuentaConceptoComp> recuperarCuentasConceptoXSocio(CuentaId idCuenta){
		List<CuentaConcepto> listaCuentaConcepto = null;
		List<CuentaConceptoComp> listaCuentaConceptoComp = null;
		ConceptoFacadeRemote conceptoFacade = null;
		
		try {
			conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			
			if(idCuenta != null){
				
				// Recuperamos las cuentas concepto del socio.
				listaCuentaConcepto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(idCuenta);
				
				if(listaCuentaConcepto != null && !listaCuentaConcepto.isEmpty()){
					listaCuentaConceptoComp = new ArrayList<CuentaConceptoComp>();
					CuentaConceptoComp cuentaConceptoComp = null;
					Boolean blnAgregar = null;
					
					for (CuentaConcepto cuentaConcepto : listaCuentaConcepto) {
						cuentaConceptoComp = new CuentaConceptoComp();
						CuentaConceptoDetalle detalle = null;
						blnAgregar = Boolean.FALSE;
						
						if(cuentaConcepto.getListaCuentaConceptoDetalle() != null 
						&& !cuentaConcepto.getListaCuentaConceptoDetalle().isEmpty()){
							detalle = new CuentaConceptoDetalle();
							detalle = cuentaConcepto.getListaCuentaConceptoDetalle().get(0);
			
							if(detalle.getIntParaTipoConceptoCod().compareTo(Constante.PARAM_T_CUENTACONCEPTO_APORTES)==0){
								cuentaConceptoComp.setCuentaConcepto(cuentaConcepto);
								cuentaConceptoComp.setIntParaConceptoGeneralModelo(0);
								cuentaConceptoComp.setIntParaTipoCaptacionModelo(Constante.PARAM_T_CUENTACONCEPTO_APORTES);
								blnAgregar = Boolean.TRUE;
								
							}else if(detalle.getIntParaTipoConceptoCod().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0 ){
								cuentaConceptoComp.setCuentaConcepto(cuentaConcepto);
								cuentaConceptoComp.setIntParaConceptoGeneralModelo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO);
								cuentaConceptoComp.setIntParaTipoCaptacionModelo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO);
								blnAgregar = Boolean.TRUE;
							} else if(detalle.getIntParaTipoConceptoCod().compareTo(Constante.PARAM_T_CUENTACONCEPTO_SEPELIO)==0 ){
								cuentaConceptoComp.setCuentaConcepto(cuentaConcepto);
								cuentaConceptoComp.setIntParaConceptoGeneralModelo(Constante.PARAM_T_CUENTACONCEPTO_SEPELIO);
								cuentaConceptoComp.setIntParaTipoCaptacionModelo(Constante.PARAM_T_CUENTACONCEPTO_SEPELIO);
								blnAgregar = Boolean.TRUE;
							}
		
						}	
						
						if(blnAgregar){
							listaCuentaConceptoComp.add(cuentaConceptoComp);
						}
						
					}
				}

			}
			
		} catch (Exception e) {
			log.error("Error en recuperarCuentasConceptoXSocio ---> "+e);
		}
		
		return listaCuentaConceptoComp;
	}
	
	
	public void cambiarSituacionCuenta(SocioComp socioComp){
		Cuenta cuenta = null;
		CuentaFacadeRemote cuentaFacade= null;
		try {
			
			cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			
			cuenta = new Cuenta();
			cuenta = socioComp.getCuenta();
			cuenta.setIntParaSituacionCuentaCod(Constante.PARAM_T_SITUACIONCUENTA_POR_LIQUIDAR);
			cuenta = cuentaFacade.modificarCuenta(cuenta);
			if(cuenta != null){
				System.out.println("SITUACION DE LA CUENTA LIQUIDADA ---> "+cuenta.getIntParaSituacionCuentaCod());
				
			}
		} catch (Exception e) {
			log.error("Error. cambiarSituacionCuenta ---> "+e );
		}
	}
	
	private EstadoLiquidacion cambioEstadoLiquidacion(ExpedienteLiquidacion expedienteLiquidacion,Integer intParaEstadoLiquidacionCod, Usuario usuario, LibroDiario diario) throws Exception {
		EstadoLiquidacion estadoLiquidacion = null;
		
		try {
			
			estadoLiquidacion = new EstadoLiquidacion();
			EstadoLiquidacionId estadoLiquidacionId = new EstadoLiquidacionId();
			estadoLiquidacion.setId(estadoLiquidacionId);
			estadoLiquidacion.getId().setIntPersEmpresaPk(expedienteLiquidacion.getId().getIntPersEmpresaPk());
			estadoLiquidacion.getId().setIntItemExpediente(expedienteLiquidacion.getId().getIntItemExpediente());
			estadoLiquidacion.setTsFechaEstado(new Timestamp(new Date().getTime()));
			estadoLiquidacion.setIntPersEmpresaEstado(usuario.getEmpresa().getIntIdEmpresa());
			estadoLiquidacion.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			estadoLiquidacion.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			estadoLiquidacion.setIntPersUsuarioEstado(usuario.getIntPersPersonaPk());
			estadoLiquidacion.setIntParaEstado(intParaEstadoLiquidacionCod);
			
			
			//expedienteLiquidacion.getListaEstadoLiquidacion().add(estadoLiquidacion);
			//grabarListaDinamicaEstadoLiquidacion(expedienteLiquidacion.getListaEstadoLiquidacion(), expedienteLiquidacion.getId());
			
			if(diario != null){
				estadoLiquidacion.setIntPersEmpresaLibro(diario.getId().getIntPersEmpresaLibro());
				estadoLiquidacion.setIntContCodigoLibro(diario.getId().getIntContCodigoLibro());
				estadoLiquidacion.setIntContPeriodoLibro(diario.getId().getIntContPeriodoLibro());	
			}
			//expedientePrevision.getListaEstadoPrevision().add(estadoPrevision);
			estadoLiquidacion = boEstadoLiquidacion.grabar(estadoLiquidacion);
		} catch (Exception e) {
			log.error("Error en cambioEstadoLiquidacion ---> "+e);
		}
		return estadoLiquidacion;
		

	}
	
	/**
	 * 
	 * @param auditoria
	 * @return
	 * @throws BusinessException
	 */
	public Auditoria grabarAuditoria(Auditoria auditoria)throws BusinessException {
		GeneralFacadeRemote generalFacade= null;
		try {
			generalFacade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
				generalFacade.grabarAuditoria(auditoria);
		} catch (Exception e) {
			log.error("Error en generarAuditoria ---> "+e);
		}
		
		return auditoria;

	}
	
	
	/**
	 * Actualiza TODAS lsa cuenta concepto saldo y saldo detalle en  cero,  y la fecha fin de cta cto detalle.
	 * @param socioComp
	 * @param paramTCuentaconcepto
	 * @throws BusinessException
	 */
	 private void actualizarFechaFinCuentaConceptoDetalle(SocioComp socioComp) throws BusinessException {
		 List<CuentaConcepto> listaCuentaConcepto= null;
		 //CuentaConceptoComp ctaCtoComp = null;
		 List<CuentaConceptoDetalle> listaCtaConceptoDet= null;
		 ConceptoFacadeRemote conceptoFacade = null;
		 try {
			 conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
				listaCuentaConcepto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(socioComp.getCuenta().getId());
				
				
			 if(listaCuentaConcepto != null && !listaCuentaConcepto.isEmpty()){

				 for (CuentaConcepto cuentaCto : listaCuentaConcepto) {
					 cuentaCto.setBdSaldo(BigDecimal.ZERO);
					 conceptoFacade.modificarCuentaConcepto(cuentaCto);
					 
					 if(cuentaCto.getListaCuentaConceptoDetalle() != null
							    && !cuentaCto.getListaCuentaConceptoDetalle().isEmpty()){
								 
								 listaCtaConceptoDet = new ArrayList<CuentaConceptoDetalle>();
								 listaCtaConceptoDet = cuentaCto.getListaCuentaConceptoDetalle();
								 for (CuentaConceptoDetalle detalle : listaCtaConceptoDet) {
									 if(detalle.getTsFin()== null){
										 detalle.setBdSaldoDetalle(BigDecimal.ZERO);
										 detalle.setTsFin(new Timestamp(new Date().getTime()));
										 conceptoFacade.modificarCuentaConceptoDetalle(detalle);
									 }
								} 
							 } 
				}
			 }
			 
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		
	}
	
	
	
	/**
	  * Genera y registra la Devolucion, segun solciitud de Cta Cte.
	  * @param solicitudCtaCteDevolucion
	  * @return
	  */
	 public Devolucion generarDevolucionLiquidacion(SolicitudCtaCte solicitudCtaCte, List<Movimiento> listaMovientosLiquidacion, SocioComp socioComp)throws BusinessException {
		 Devolucion devolucionLiq = null;
		 DevolucionId devolucionId = null;
		 BigDecimal bdMontoDevolucion = BigDecimal.ZERO;
		 CuentacteFacadeRemote cuentaCteFacade = null;
		 try {
			 
			 cuentaCteFacade = (CuentacteFacadeRemote)EJBFactory.getRemote(CuentacteFacadeRemote.class);
			 if(listaMovientosLiquidacion != null && !listaMovientosLiquidacion.isEmpty()){
				 
				 for (Movimiento movimiento : listaMovientosLiquidacion) {
					if(movimiento.getIntParaTipoConceptoGeneral().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_CTAXPAGAR)==0){
						bdMontoDevolucion = movimiento.getBdMontoMovimiento();
						break;
					}
				}
				 
				 //movSaldoCuentaConcepto.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO);
				 
				 devolucionId = new DevolucionId();
				 devolucionLiq =  new Devolucion();
				 devolucionId.setIntPersEmpresaDevolucion(Constante.PARAM_EMPRESASESION);
				 devolucionLiq.setId(devolucionId);
				 devolucionLiq.setTsDevoFecha(new Timestamp((new Date()).getTime()));
				 devolucionLiq.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_DEVOLUCION);
				 devolucionLiq.setIntCuenta(socioComp.getCuenta().getId().getIntCuenta());
				 devolucionLiq.setBdDevoMonto(bdMontoDevolucion);
				 devolucionLiq.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				 devolucionLiq.setIntParaEstadoPago(Constante.PARAM_T_ESTADO_PAGO_PENDIENTE);
				 
				 devolucionLiq.setIntPersEmpresaSolCtaCte(solicitudCtaCte.getId().getIntEmpresasolctacte());
				 devolucionLiq.setIntCcobItemSolCtaCte(solicitudCtaCte.getId().getIntCcobItemsolctacte());
				 devolucionLiq.setIntParaTipoSolicitudCtaCte(Constante.PARAM_T_SOLICITUD_CTACTE_TIPO_DEVOLUCION);
				 
				 System.out.println("id getIntPersEmpresaDevolucion ---> "+devolucionLiq.getId().getIntPersEmpresaDevolucion());
				 System.out.println("id getIntItemDevolucion ---> "+devolucionLiq.getId().getIntItemDevolucion());					 
				 System.out.println("devolucion.getTsDevoFecha() ---> "+devolucionLiq.getTsDevoFecha());
				 System.out.println("getIntParaDocumentoGeneral() ---> "+devolucionLiq.getIntParaDocumentoGeneral());
				 System.out.println("getIntCuenta() ---> "+devolucionLiq.getIntCuenta());
				 System.out.println("getBdDevoMonto() ---> "+devolucionLiq.getBdDevoMonto());
				 System.out.println("getIntParaEstado() ---> "+devolucionLiq.getIntParaEstado());
				 System.out.println("getIntParaEstadoPago() ---> "+devolucionLiq.getIntParaEstadoPago());
				 System.out.println("getIntPersEmpresaSolCtaCte() ---> "+devolucionLiq.getIntPersEmpresaSolCtaCte());
				 System.out.println("getIntCcobItemSolCtaCte() ---> "+devolucionLiq.getIntCcobItemSolCtaCte());
				 System.out.println("getIntParaTipoSolicitudCtaCte() ---> "+devolucionLiq.getIntParaTipoSolicitudCtaCte());
				 
				 
				 devolucionLiq = cuentaCteFacade.grabarDevolucion(devolucionLiq); 
			 }
			 
			 
			
		} catch (Exception e) {
			log.error("Error en generarDevolucion ---> "+e);
		}
		 return devolucionLiq;
	 }
	 
	 
	/**
	 * Solo actualiza la tabla expediente liquidacion segun autorizacion de aliquidacion
	 * @param pExpedienteLiquidacion
	 * @return
	 * @throws BusinessException
	 */
	public ExpedienteLiquidacion modificarExpedienteLiquidacionParaAuditoria(ExpedienteLiquidacion pExpedienteLiquidacion, Integer intTipoCambio, Date dtNuevoFechaProgramacionPago, Integer intNuevoMotivoRenuncia) throws BusinessException{
		ExpedienteLiquidacion expedienteLiquidacionModificado = null;
		//Auditoria auditoria = null;
		//AuditoriaMotivo motivo =null;
//		GeneralFacadeRemote generalFacade = null;
		
		try{
//			generalFacade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
			
			if(pExpedienteLiquidacion != null){
				expedienteLiquidacionModificado = new ExpedienteLiquidacion();
				expedienteLiquidacionModificado = pExpedienteLiquidacion;
				
				if(intTipoCambio != null && intTipoCambio.compareTo(0)!=0){
					// PARAM_T_AUTORIZACION_LIQUIDACION_TIPO_CAMBIO_FECHA_PROGRAMACION_PAGO = 1;
					// PARAM_T_AUTORIZACION_LIQUIDACION_TIPO_CAMBIO_MOTIVO_RENUNCIA = 2;
					if(intTipoCambio.compareTo(Constante.PARAM_T_AUTORIZACION_LIQUIDACION_TIPO_CAMBIO_FECHA_PROGRAMACION_PAGO)==0){
						if(dtNuevoFechaProgramacionPago != null){
							expedienteLiquidacionModificado.setDtFechaProgramacion(dtNuevoFechaProgramacionPago);
						}
					}else if(intTipoCambio.compareTo(Constante.PARAM_T_AUTORIZACION_LIQUIDACION_TIPO_CAMBIO_MOTIVO_RENUNCIA)==0){
							if(intNuevoMotivoRenuncia != null && intNuevoMotivoRenuncia.compareTo(0)!= 0){
								expedienteLiquidacionModificado.setIntParaMotivoRenuncia(intNuevoMotivoRenuncia);	
							}
						
					}	
				}
				
				//1. expediente
				pExpedienteLiquidacion = boExpedienteLiquidacion.modificar(expedienteLiquidacionModificado);	
			}

		}catch(BusinessException e){
			System.out.println("BusinessExceptionBusinessException--> "+e);
			throw e;
		}catch(Exception e1){
			System.out.println("ExceptionException--> "+e1);
			throw new BusinessException(e1);
		}
		return pExpedienteLiquidacion;
	}
	
	
	
	
	 /**
	  * Genera la SolicitudCtaCte, los estados (pendiente y atendido) y solicitudCtacteTipo.
	  * Se devuelve el objeto  SolicitudCtaCte cargado.
	  * @param socioComp
	  * @param strPeriodo
	  * @param requisitoLiq
	  * @return
	  * @throws EJBFactoryException
	  * @throws BusinessException 
	  */
	 public SolicitudCtaCte grabarSolicitudCtaCteParaLiquidacion_1(SocioComp socioComp,Integer strPeriodo, RequisitoLiquidacion requisitoLiq, Usuario usuario) 
	 	throws EJBFactoryException, BusinessException {
		 SolicitudCtaCte solicitudCtaCte = null;
		 EstadoSolicitudCtaCte estadoPendiente = null;
		 EstadoSolicitudCtaCte estadoAtendido = null;
		 CuentacteFacadeRemote cuentaCteFacadeRemote= null;
		 
		 try {
			 //Autor: jchavez / Tarea: Modificación / Fecha: 12.08.2014 / 
//			 Calendar fecHoy = Calendar.getInstance();
//			 Date dtAhora = fecHoy.getTime();
			 Timestamp dtAhora = new Timestamp(new Date().getTime());				 
			 cuentaCteFacadeRemote = (CuentacteFacadeRemote)EJBFactory.getRemote(CuentacteFacadeRemote.class);
			 
				if(socioComp != null){
				// Generando la solciitud cta cte
					 solicitudCtaCte = new SolicitudCtaCte();
					 solicitudCtaCte.setId(new SolicitudCtaCteId());
					 solicitudCtaCte.getId().setIntEmpresasolctacte(socioComp.getSocio().getId().getIntIdEmpresa());
					 solicitudCtaCte.setIntPersEmpresa(socioComp.getCuenta().getId().getIntPersEmpresaPk());
					 solicitudCtaCte.setIntPersPersona(socioComp.getPersona().getNatural().getIntIdPersona());
					 solicitudCtaCte.setIntCsocCuenta(socioComp.getCuenta().getId().getIntCuenta());
					 solicitudCtaCte.setIntSucuIdsucursalsocio(socioComp.getCuenta().getIntIdUsuSucursal());
					 solicitudCtaCte.setIntSudeIdsubsucursalsocio(socioComp.getCuenta().getIntIdUsuSubSucursal());
					 solicitudCtaCte.setIntPeriodo(new Integer(strPeriodo));
					 solicitudCtaCte.setIntParaTipomodalidad(socioComp.getSocio().getSocioEstructura().getIntModalidad());
					 solicitudCtaCte.setIntParaTipo(0);
					 solicitudCtaCte.setIntMaeItemarchivo(0);
					 solicitudCtaCte.setIntMaeItemhistorico(0);
					 
					 solicitudCtaCte = cuentaCteFacadeRemote.grabarSolicitudCtaCte(solicitudCtaCte);
					 
				// Generando Estados de la solciitud cta cte (Pendiente y ATendido)
					 if(solicitudCtaCte.getId().getIntCcobItemsolctacte() != null){
						 List<EstadoSolicitudCtaCte> lstEstadosSolCta = new ArrayList<EstadoSolicitudCtaCte>();
						 estadoPendiente = new EstadoSolicitudCtaCte();
						 estadoPendiente.setId(new EstadoSolicitudCtaCteId());
						 estadoAtendido = new EstadoSolicitudCtaCte();
						 estadoAtendido.setId(new EstadoSolicitudCtaCteId());

						 estadoPendiente.getId().setIntPersEmpresaSolctacte(solicitudCtaCte.getId().getIntEmpresasolctacte());
						 estadoPendiente.getId().setIntCcobItemSolCtaCte(solicitudCtaCte.getId().getIntCcobItemsolctacte());
						 estadoPendiente.setIntParaEstadoSolCtaCte(Constante.PARAM_T_ESTADO_SOLCTACTE_PENDIENTE);
						 estadoPendiente.setDtEsccFechaEstado(dtAhora);
						 estadoPendiente.setIntPersEmpresaEstado(usuario.getEmpresa().getIntIdEmpresa());
						 estadoPendiente.setIntSucuIduSusucursal(usuario.getSucursal().getId().getIntIdSucursal());
						 estadoPendiente.setIntSudeIduSusubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
						 estadoPendiente.setIntPersUsuarioEstado(usuario.getIntPersPersonaPk());
						 estadoPendiente.setStrEsccObservacion("Liquidacion de Cuentas - Estado Pendiente.");
						 estadoPendiente = cuentaCteFacadeRemote.grabarEstadoSolicitudCtaCte(estadoPendiente);
						 lstEstadosSolCta.add(estadoPendiente);
						 
						 estadoAtendido.getId().setIntPersEmpresaSolctacte(solicitudCtaCte.getId().getIntEmpresasolctacte());
						 estadoAtendido.getId().setIntCcobItemSolCtaCte(solicitudCtaCte.getId().getIntCcobItemsolctacte());
						 estadoAtendido.setIntParaEstadoSolCtaCte(Constante.PARAM_T_ESTADO_SOLCTACTE_ATENDIDO);
						 estadoAtendido.setDtEsccFechaEstado(dtAhora);
						 estadoAtendido.setIntPersEmpresaEstado(usuario.getEmpresa().getIntIdEmpresa());
						 estadoAtendido.setIntSucuIduSusucursal(usuario.getSucursal().getId().getIntIdSucursal());
						 estadoAtendido.setIntSudeIduSusubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
						 estadoAtendido.setIntPersUsuarioEstado(usuario.getIntPersPersonaPk());
						 estadoAtendido.setStrEsccObservacion("Liquidacion de Cuentas - Estado Atendido.");
						 estadoAtendido = cuentaCteFacadeRemote.grabarEstadoSolicitudCtaCte(estadoAtendido);
						 lstEstadosSolCta.add(estadoAtendido);
						 
				//Generando la Solicitud Tipo
						 SolicitudCtaCteTipo solCtaCteTipo = new SolicitudCtaCteTipo();
						 SolicitudCtaCteTipoId solCtaCteTipoId = new SolicitudCtaCteTipoId();
						 
						 solCtaCteTipoId.setIntCcobItemsolctacte(solicitudCtaCte.getId().getIntCcobItemsolctacte());
						 solCtaCteTipoId.setIntPersEmpresasolctacte(solicitudCtaCte.getId().getIntEmpresasolctacte());
						 solCtaCteTipoId.setIntTipoSolicitudctacte(Constante.PARAM_T_SOLICITUD_CTACTE_TIPO_TRANSFERENCIAS);
						 solCtaCteTipo.setId(solCtaCteTipoId);
						 solCtaCteTipo.setIntTaraEstado(Constante.PARAM_T_ESTADO_SOLCTACTE_ATENDIDO);
						 solCtaCteTipo.setIntParaTipoorigen(Constante.PARAM_T_TIPO_ORIGEN_SOLICITUD_CUENTA_CORRIENTE);
						 solCtaCteTipo.setIntParaEstadoanalisis(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						 solCtaCteTipo.setStrScctObservacion("Generado por Liquidación de Cuentas. ");
						 solCtaCteTipo.setDtFechaDocumento(dtAhora);
						 solCtaCteTipo.setIntMotivoSolicitud(Constante.PARAM_T_MOTIVO_SOLICITUD_CTACTE_LIQUIDACION_CTA_CTO); // 249-22
						 solCtaCteTipo.setIntEmpresaLibro(0); 		// Temporalmente hasta q se genere el asiento contable
						 solCtaCteTipo.setIntContPeriodolibro(0);	// Temporalmente hasta q se genere el asiento contable
						 solCtaCteTipo.setIntCodigoLibro(0);		// Temporalmente hasta q se genere el asiento contable
						 solCtaCteTipo.setIntPersUsuario(usuario.getIntPersPersonaPk());
						 solCtaCteTipo.setIntCcobItemefectuado(null);
						 
						 solCtaCteTipo = cuentaCteFacadeRemote.grabarSolicitudCtaCteTipo(solCtaCteTipo);
						 if(solCtaCteTipo != null){
							 List<SolicitudCtaCteTipo> lstSolCtaCteTipo= new ArrayList<SolicitudCtaCteTipo>();
							 lstSolCtaCteTipo.add(solCtaCteTipo);
							 
							 solicitudCtaCte.setListaSolCtaCteTipo(lstSolCtaCteTipo);
						 }else{
							 throw new BusinessException("No se genero la solicitud cta cte tipo Inicial de liquidacion 2.");
						 }
					 }else{
						 throw new BusinessException("No se genero la solicitud cta cte tipo Inicial de liquidacion 1.");
					 }
				 }

		} catch (Exception e) {
			throw new BusinessException("No se genero la solicitud cta cte tipo Inicial de liquidacion");
		}
		 return solicitudCtaCte;
		 
	 }
	
	
	/**
	 * Cambia de estado al expediente, agregando un registro de Estado.
	 * @param expedienteCredito
	 * @throws Exception
	 */
	/* private EstadoLiquidacion cambioEstadoLiquidacion(ExpedienteLiquidacion expedienteLiquidacion,	Integer intParaEstadoLiquidacionCod, Usuario usuario, LibroDiario diario) throws Exception {
		EstadoLiquidacion estadoLiquidacion = null;
		EstadoLiquidacionId estadoEstadoLiquidacionId = null;
		SolicitudLiquidacionService solicitudLiquidacionService = null;
		try {
			solicitudLiquidacionService = (SolicitudLiquidacionService)TumiFactory.get(SolicitudLiquidacionService.class);
			
			estadoEstadoLiquidacionId = new EstadoLiquidacionId();
			estadoEstadoLiquidacionId.setIntCuentaPk(expedienteLiquidacion.getId().getIntCuentaPk());
			estadoEstadoLiquidacionId.setIntItemExpediente(expedientePrevision.getId().getIntItemExpediente());
			estadoPrevisionId.setIntPersEmpresaPk(expedientePrevision.getId().getIntPersEmpresaPk());
			estadoPrevision = new EstadoPrevision();
			estadoPrevision.setId(estadoPrevisionId);
			
			estadoPrevision.setTsFechaEstado(new Timestamp(new Date().getTime()));
			estadoPrevision.setIntPersEmpresaEstado(usuario.getEmpresa().getIntIdEmpresa());
			estadoPrevision.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			estadoPrevision.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			estadoPrevision.setIntPersUsuarioEstado(usuario.getIntPersPersonaPk());
			estadoPrevision.setIntParaEstado(intParaEstadoPrevisionCod);
			if(diario != null){
				estadoPrevision.setIntPersEmpresaLibro(diario.getId().getIntPersEmpresaLibro());
				estadoPrevision.setIntContCodigoLibro(diario.getId().getIntContCodigoLibro());
				estadoPrevision.setIntContPeriodoLibro(diario.getId().getIntContPeriodoLibro());	
			}
			//expedientePrevision.getListaEstadoPrevision().add(estadoPrevision);
			estadoPrevision = boEstadoPrevision.grabar(estadoPrevision);
			//solicitudPresvisionService.modificarExpedientePrevision(expedientePrevision);

		} catch (Exception e) {
			log.error("Error en cambioEstadoPrevision ---> "+e);
		}
		return estadoPrevision;
	}*/

	 
	 
	 /**
	  * Genera el lobro diario a partir de la solicitud tipo cta cte.
	  * @param expedienteCreditoCompSelected
	  * @param solicitudCtaCteTipo
	  * @param usuario
	  * @param expedienteCreditoNuevo
	  * @return
	  * @throws BusinessException
	  */
	public LibroDiario generarAsientoContableLiquidacionCuentasYTransferencia_2(SolicitudCtaCteTipo solicitudCtaCteTipo,SocioComp socioComp, Usuario usuario, ExpedienteLiquidacion expedienteLIquidacion) 
 		throws BusinessException{ 
//	 ConceptoFacadeRemote     conceptoFacade 	= null;
	 ModeloFacadeRemote       modeloFacade 		= null;
//	 LibroDiarioFacadeRemote  libroDiarioFacade = null;
//	 CreditoFacadeRemote      creditoFacade 	= null;
//	 GeneralFacadeRemote      generalFacade 	= null;
	 //CarteraFacadeRemote      carteraFacade 	= null;
	 //TransferenciaBO           boTransferencia   = null;
//	 SolicitudPrestamoFacadeRemote solicitudPrestamofacade = null;
	 
	 CuentacteFacadeRemote cuentaCteFacade = null;
	 
//	 Integer intPersona = null;
//	 Integer intCuenta  = null;
	 Integer intEmpresa = null;
	 LibroDiario diario = null;
	 List<Modelo>     listaModelo     = null;

     
     Modelo modeloSeleccionado = null;
     Transferencia transferencia = null;

      try{	 cuentaCteFacade = (CuentacteFacadeRemote)EJBFactory.getRemote(CuentacteFacadeRemote.class);
//    	     conceptoFacade    = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
    	     modeloFacade      = (ModeloFacadeRemote)EJBFactory.getRemote(ModeloFacadeRemote.class);
//    	     libroDiarioFacade = (LibroDiarioFacadeRemote)EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
//    	     creditoFacade     = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
//    	     generalFacade     = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class); 
    	     //carteraFacade     = (CarteraFacadeRemote)EJBFactory.getRemote(CarteraFacadeRemote.class); 
//    	     solicitudPrestamofacade = (SolicitudPrestamoFacadeRemote)EJBFactory.getRemote(SolicitudPrestamoFacadeRemote.class);
    	     //boTransferencia   = (TransferenciaBO)TumiFactory.get(TransferenciaBO.class);
		     //Datos del Socio
//    	     intPersona = socioComp.getSocio().getId().getIntIdPersona();
//    	     intCuenta  = socioComp.getCuenta().getId().getIntCuenta();
             intEmpresa = socioComp.getCuenta().getId().getIntPersEmpresaPk();
             
             
          //Obtiencion del Modelo Contable segun el tipo de transferencia.
			if (solicitudCtaCteTipo.getIntMotivoSolicitud().equals(Constante.PARAM_T_MOTIVO_SOLICITUD_CTACTE_LIQUIDACION_CTA_CTO)){
			    listaModelo = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_PROVISION_LIQUIDACION_DE_CUENTA_AL_AUTORIZAR,intEmpresa);
			 }
			 
			 if (listaModelo == null || listaModelo.size() == 0 || listaModelo.isEmpty()){
				  throw new BusinessException("No existe el modelo contable para generar el asiento.");
			 }else {

				if(listaModelo != null && !listaModelo.isEmpty()){
					
					if(listaModelo.get(0) != null){
						modeloSeleccionado = listaModelo.get(0);
					}
					
					
				}
				// CREAMOS Y GRABAMOS EL LIBRO DIARIO
				diario = obtieneLibroDiarioYDiarioDetalleLiquidacionCuentas_3(modeloSeleccionado.getId(), null,expedienteLIquidacion,solicitudCtaCteTipo, usuario,socioComp);

				 //Grabamos la transferencia			        
		        //Actualiza o se regisra el Tipo de Solicitud. 			  
		        if (solicitudCtaCteTipo.getIntMotivoSolicitud().equals(Constante.PARAM_T_MOTIVO_SOLICITUD_CTACTE_LIQUIDACION_CTA_CTO)){
		        	if(diario!=null){
		        		// actualizamos la solicitud de
		        		solicitudCtaCteTipo.setIntEmpresaLibro(diario.getId().getIntPersEmpresaLibro());
		        		solicitudCtaCteTipo.setIntCodigoLibro(diario.getId().getIntContCodigoLibro());
		        		solicitudCtaCteTipo.setIntContPeriodolibro(diario.getId().getIntContPeriodoLibro());
		        		solicitudCtaCteTipo = cuentaCteFacade.modificarSolicitudCuentaCorrienteTipo(solicitudCtaCteTipo);

		        		//Graba la transferencia;
		        		  transferencia = new Transferencia();
					      TransferenciaId transferenciaId = new TransferenciaId();
					      transferenciaId.setIntPersEmpresaTransferencia(solicitudCtaCteTipo.getId().getIntPersEmpresasolctacte());
					      transferencia.setId(transferenciaId);
					      transferencia.setIntParaDocumentoGeneral(diario.getIntParaTipoDocumentoGeneral());
					      transferencia.setTsTranFecha(obtieneFechaActualEnTimesTamp());
					      transferencia.setIntCcobItemSolctacte(solicitudCtaCteTipo.getId().getIntCcobItemsolctacte());
					      transferencia.setIntPersEmpresaSolctacte(solicitudCtaCteTipo.getId().getIntPersEmpresasolctacte());
					      transferencia.setIntParaTipoSolicitudctacte(solicitudCtaCteTipo.getId().getIntTipoSolicitudctacte());
					      transferencia.setIntPersEmpresaLibro(diario.getId().getIntPersEmpresaLibro());
					      transferencia.setIntContPeriodoLibro(diario.getId().getIntContPeriodoLibro());
					      transferencia.setIntContCodigoLibro(diario.getId().getIntContCodigoLibro());
					      transferencia.setIntTranPeriodo(diario.getId().getIntContPeriodoLibro());
				        
					      transferencia = cuentaCteFacade.grabarTransgerencia(transferencia);
					      //boTransferencia.grabarTransferencia(transferencia);
					      
					      // corregir segun caso
					      // solicitudPrestamofacade.generarExpedienteMovimiento(expedienteCreditoCompSelected);
			 	}
		       }
			 }
      }catch(BusinessException e){
    	  System.out.println("Error en generarAsientoContableLiquidacionCuentasYTransferencia_2 1 -->"+e);
    	  throw new BusinessException(e);
	  }catch(Exception e){
		  System.out.println("Error en generarAsientoContableLiquidacionCuentasYTransferencia_2 2 -->"+e);
		  throw new BusinessException(e);
	  }
	  return diario;
 }
	
	
	
	/**
	 * Forma el libro diario , libro diario detalle en base al modelo contable (38):
	 * Provisión de liquidación de cuenta al momento de autorizar.
	 * @param idModelo
	 * @param intTipoCreditoEmpresa
	 * @param expedienteLIquidacion
	 * @param solicitudCtaCteTipo
	 * @param usuario
	 * @param socioComp
	 * @return
	 */
	private LibroDiario obtieneLibroDiarioYDiarioDetalleLiquidacionCuentas_3( ModeloId idModelo, Integer intTipoCreditoEmpresa,
			ExpedienteLiquidacion expedienteLIquidacion, SolicitudCtaCteTipo solicitudCtaCteTipo, Usuario usuario, SocioComp socioComp) {

			BigDecimal bdValorColumna = null;
			LibroDiario diario = null;
			List<LibroDiarioDetalle> lstDiarioDetalle = new ArrayList<LibroDiarioDetalle>();
			List<ModeloDetalle> listaModDet = null;
			List<CuentaConceptoComp> listaCuentaConceptosComp = null;
//			List<CuentaConceptoComp> listaCuentaConceptosMasInteresComp = null;
			
			ModeloFacadeRemote	modeloFacade = null;
			LibroDiarioFacadeRemote libroDiarioFacade = null;
			/**
			 * jchavez 07.05.2014
			 * se le cambia el valor, pero no es usado para ninguna otra validacion
			 * linea 5085
			 */
//			Boolean blnTieneInteresRetiro= Boolean.FALSE;
			PlanCuentaFacadeRemote planCtaFacade= null;

			//jchavez 02.06.2014
			List<ModeloDetalleNivelComp> lstModeloProvisionRetiroInteres = null;
			List<ModeloDetalleNivelComp> lstModeloProvisionRetiroCapitalizacionInteres = null;
			ModeloDetalleNivel o1 = new ModeloDetalleNivel();
			ModeloDetalleNivel o2 = new ModeloDetalleNivel();
			Calendar cal = Calendar.getInstance();
			Integer anioActual = cal.get(Calendar.YEAR);
//			List<CuentaConceptoDetalle> lstCtaCtpoDet = null;
			Timestamp tsFechaUltimoMovFdoRetiroInteres = null;
			try{
				modeloFacade = (ModeloFacadeRemote)EJBFactory.getRemote(ModeloFacadeRemote.class);
				libroDiarioFacade = (LibroDiarioFacadeRemote)EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
				planCtaFacade= (PlanCuentaFacadeRemote)EJBFactory.getRemote(PlanCuentaFacadeRemote.class);
//				ConceptoFacadeRemote conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);

				
				// Recobramos valores de periodo actual
				Integer intPeriodoCuenta = Integer.parseInt(obtieneAnio(new Date()));
				Integer anio =  Integer.parseInt(obtieneAnio(new Date()));
				String  mes  = obtieneMesCadena(new Date());

				// Recuperamos las cuentas concepto de retiro, aportes y agregamos la de interes de retiro.
				// El interes se agrega a la cuenta concepto fdo de retiro
				
				listaCuentaConceptosComp = recuperarCuentasConceptoRetiroAportesXSocio(socioComp.getCuenta().getId());
				Timestamp tsFechaInicioCtaCptoDet = null;
				if(listaCuentaConceptosComp != null && !listaCuentaConceptosComp.isEmpty()){
					for (CuentaConceptoComp cuentaConceptoComp : listaCuentaConceptosComp) {
						if(cuentaConceptoComp.getIntParaTipoCaptacionModelo().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0){
							cuentaConceptoComp.getCuentaConcepto().setBdSaldo(cuentaConceptoComp.getCuentaConcepto().getBdSaldo().add(expedienteLIquidacion.getMovimiento()!=null?expedienteLIquidacion.getMovimiento().getBdMontoSaldo():BigDecimal.ZERO));
							tsFechaInicioCtaCptoDet = cuentaConceptoComp.getCuentaConcepto().getListaCuentaConceptoDetalle().get(0).getTsInicio(); 
						}
					}
				}
//					listaCuentaConceptosMasInteresComp =  new ArrayList<CuentaConceptoComp>(listaCuentaConceptosComp);
//
//					for (CuentaConceptoComp cuentaConceptoComp : listaCuentaConceptosComp) {
//						if(cuentaConceptoComp.getIntParaTipoCaptacionModelo().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0){
//							CuentaConceptoComp cuentaConceptoInteresComp = null;
//							
//							cuentaConceptoInteresComp = recuperarCuentasConceptoInteresRetiro(cuentaConceptoComp.getCuentaConcepto(),expedienteLIquidacion,socioComp);
//							if(cuentaConceptoInteresComp != null){
////								blnTieneInteresRetiro = Boolean.TRUE;
//								listaCuentaConceptosMasInteresComp.add(cuentaConceptoInteresComp);
//							}
//						}
//					}	
//				}

				// Recuperamos el detalle del modelo
				listaModDet =  modeloFacade.getListaModeloDetallePorModeloId(idModelo);
				if (listaModDet==null || listaModDet.isEmpty()) {
					throw new BusinessException("Error. No existe modelo contable para generar el asiento contable.");
				}
				//if(listaModDet != null && !listaModDet.isEmpty()){
					diario = new LibroDiario();
					diario.setId(new LibroDiarioId());
					diario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());

					diario.getId().setIntContPeriodoLibro(new Integer(anio+""+mes));
					diario.getId().setIntPersEmpresaLibro(socioComp.getCuenta().getId().getIntPersEmpresaPk());
					diario.setStrGlosa("Asiento de Liquidacion de Cuentas - Libro Diario - "+new Date().getTime());
					diario.setTsFechaRegistro(new Timestamp((new Date()).getTime())); //la fecha actual
					diario.setTsFechaDocumento(new Timestamp((new Date()).getTime()));
					diario.setIntPersEmpresaUsuario(solicitudCtaCteTipo.getIntEmpresa());
					diario.setIntPersPersonaUsuario(solicitudCtaCteTipo.getIntPersUsuario());
					diario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCGENERAL);
					diario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					diario.setIntPersEmpresaUsuario(usuario.getEmpresa().getIntIdEmpresa());
					diario.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());


					for (CuentaConceptoComp cuentaConceptoComp : listaCuentaConceptosComp) {
						bdValorColumna= BigDecimal.ZERO;
						//Recorremos el detalle
						for (ModeloDetalle modeloDetalle : listaModDet) { 
							bdValorColumna = cuentaConceptoComp.getCuentaConcepto().getBdSaldo();
							PlanCuenta planCta = new PlanCuenta();
							String strObservacionPlanCta = "";
							
							//CGD-26.12.2013

							if(modeloDetalle.getPlanCuenta() == null){
								PlanCuentaId ctaId = new PlanCuentaId();
								ctaId.setIntEmpresaCuentaPk(modeloDetalle.getId().getIntPersEmpresaCuenta());
								ctaId.setIntPeriodoCuenta(modeloDetalle.getId().getIntContPeriodoCuenta());
								ctaId.setStrNumeroCuenta(modeloDetalle.getId().getStrContNumeroCuenta());
								
								planCta = planCtaFacade.getPlanCuentaPorPk(ctaId);
								if(planCta != null){
									strObservacionPlanCta = planCta.getStrDescripcion();
								}
							}else{
								strObservacionPlanCta = modeloDetalle.getPlanCuenta().getStrDescripcion();
							}
							// modelo detalle
							if (modeloDetalle.getId().getIntContPeriodoCuenta().equals(intPeriodoCuenta)){
								LibroDiarioDetalle diarioDet = new LibroDiarioDetalle();
								diarioDet.setId(new LibroDiarioDetalleId());
	
								List<ModeloDetalleNivel> listaModDetNiv = null;
								listaModDetNiv = modeloFacade.getListaModeloDetNivelPorModeloDetalleId(modeloDetalle.getId());
															
								for (ModeloDetalleNivel modeloDetalleNivel : listaModDetNiv) {
									if(modeloDetalleNivel.getIntParaTipoRegistro().compareTo(Constante.PARAM_T_MODELO_TIPO_REGISTRO_TABLA)==0){
										//-- dato tablas  71  - TIPO DE CAPTACION--->	
										if(modeloDetalleNivel.getIntDatoTablas().compareTo(new Integer(Constante.PARAM_T_TIPOCUENTA))==0){
											if(modeloDetalleNivel.getIntDatoArgumento().compareTo(cuentaConceptoComp.getIntParaTipoCaptacionModelo())==0){
//												if (cuentaConceptoComp.getIntParaTipoCaptacionModelo().equals(Constante.PARAM_T_TIPOCUENTA_FONDORETIRO)) {//3
													diarioDet = new LibroDiarioDetalle();
													diarioDet.setId(new LibroDiarioDetalleId());
													diarioDet.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
													diarioDet.setIntContPeriodo(modeloDetalle.getId().getIntContPeriodoCuenta());
													diarioDet.setIntPersEmpresaCuenta(modeloDetalle.getId().getIntPersEmpresaCuenta());
													diarioDet.setIntPersPersona(usuario.getIntPersPersonaPk());
													diarioDet.setIntPersEmpresaSucursal(Constante.PARAM_EMPRESASESION);
													diarioDet.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
													diarioDet.setIntSudeIdSubSucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
													diarioDet.setStrContNumeroCuenta(modeloDetalle.getId().getStrContNumeroCuenta());
													//CGD-26.12.2013
													diarioDet.setStrComentario(strObservacionPlanCta);
													if(modeloDetalle.getIntParaOpcionDebeHaber().compareTo(Constante.PARAM_T_LIBRO_DIARIO_DEBE)==0){
														diarioDet.setIntParaDocumentoGeneral(modeloDetalle.getIntParaDocumentoGral());
														diarioDet.setBdDebeSoles(bdValorColumna);
														diarioDet.setBdHaberSoles(null);
													}else if(modeloDetalle.getIntParaOpcionDebeHaber().compareTo(Constante.PARAM_T_LIBRO_DIARIO_HABER)==0){
														diarioDet.setIntParaDocumentoGeneral(modeloDetalle.getIntParaDocumentoGral());
														diarioDet.setBdHaberSoles(bdValorColumna);
														diarioDet.setBdDebeSoles(null);
													}
													lstDiarioDetalle.add(diarioDet);
													break;
//												}
											}
										}
									}																		
								}
							}
						}
					}
								
								
								
								
//								COMENTADO JCHAVEZ 04.06.2014
//	
//								// Se deberia cargar cada una de las cuentas a liquidar.
//									Boolean blnAplicaConceptoGral = Boolean.FALSE;
//
//									// Cuentas concepto
//									Boolean blnCumpleTipoCaptacion = Boolean.FALSE;
//									Boolean blnCumpleConceptoGeneral = Boolean.FALSE;
//									
//									for (ModeloDetalleNivel modeloDetalleNivel : listaModDetNiv) {
//											if(modeloDetalleNivel.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_LIQUIDACION_CONCEPTOGENERAL_COD)){
//												blnAplicaConceptoGral = Boolean.TRUE;
//												break;
//											}
//										}
//									
//									
//									if(!blnAplicaConceptoGral){
//										blnCumpleConceptoGeneral = Boolean.TRUE;
//									}
//									
//										for (ModeloDetalleNivel modeloDetalleNivel : listaModDetNiv) {
//
//											/////////////////// Es valor fijo
//											if(modeloDetalleNivel.getIntParaTipoRegistro().compareTo(Constante.PARAM_T_MODELO_TIPO_REGISTRO_FIJO)==0){
//
//												if(modeloDetalleNivel.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_LIQUIDACION_CUCO_SALDO)
//													|| modeloDetalleNivel.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_LIQUIDACION_MONTO_SALDO)){
//													if(cuentaConceptoComp.getCuentaConcepto().getBdSaldo() != null){
//														bdValorColumna = cuentaConceptoComp.getCuentaConcepto().getBdSaldo();
//													}
//												}
//	
//											////////////////// Es tabla	
//											}else if(modeloDetalleNivel.getIntParaTipoRegistro().compareTo(Constante.PARAM_T_MODELO_TIPO_REGISTRO_TABLA)==0){
//	
//												//-- dato tablas  71  - TIPO DE CAPTACION--->	
//												if(modeloDetalleNivel.getIntDatoTablas().compareTo(new Integer(Constante.PARAM_T_TIPOCUENTA))==0){
//													if(modeloDetalleNivel.getIntDatoArgumento().compareTo(cuentaConceptoComp.getIntParaTipoCaptacionModelo())==0){ 
//														//System.out.println("blnCumpleConceptoGeneral ---> "+blnCumpleConceptoGeneral);
//														blnCumpleTipoCaptacion = Boolean.TRUE;
//														
//													}
//												}	
//												
//												//-- dato tablas  212  --->  
//													if(modeloDetalleNivel.getIntDatoTablas().compareTo(new Integer(Constante.PARAM_T_TIPOCONCEPTOGENERAL))==0){
//														if(modeloDetalleNivel.getIntDatoArgumento().compareTo(cuentaConceptoComp.getIntParaConceptoGeneralModelo())==0){
//															//System.out.println("blnCumpleTipoCaptacion ---> "+blnCumpleTipoCaptacion);
//																blnCumpleConceptoGeneral = Boolean.TRUE;
//														}
//													}
//											}
//										}
//										
//
//										// terminamos de formar el libro diario detalle
//										if(blnCumpleTipoCaptacion && blnCumpleConceptoGeneral){
//											if (!(bdValorColumna.equals(BigDecimal.ZERO))) {
//												diarioDet.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
//												diarioDet.setIntContPeriodo(modeloDetalle.getId().getIntContPeriodoCuenta());
//												diarioDet.setIntPersEmpresaCuenta(modeloDetalle.getId().getIntPersEmpresaCuenta());
//												diarioDet.setIntPersPersona(usuario.getIntPersPersonaPk());
//												diarioDet.setIntPersEmpresaSucursal(Constante.PARAM_EMPRESASESION);
//												diarioDet.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
//												diarioDet.setIntSudeIdSubSucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
//												diarioDet.setStrContNumeroCuenta(modeloDetalle.getId().getStrContNumeroCuenta());
//												//CGD-26.12.2013
//												diarioDet.setStrComentario(strObservacionPlanCta);
//												if(modeloDetalle.getIntParaOpcionDebeHaber().compareTo(Constante.PARAM_T_LIBRO_DIARIO_DEBE)==0){
//													diarioDet.setIntParaDocumentoGeneral(modeloDetalle.getIntParaDocumentoGral());
//													diarioDet.setBdDebeSoles(bdValorColumna);
//													diarioDet.setBdHaberSoles(null);
//												}else if(modeloDetalle.getIntParaOpcionDebeHaber().compareTo(Constante.PARAM_T_LIBRO_DIARIO_HABER)==0){
//													diarioDet.setIntParaDocumentoGeneral(modeloDetalle.getIntParaDocumentoGral());
//													diarioDet.setBdHaberSoles(bdValorColumna);
//													diarioDet.setBdDebeSoles(null);
//												}
//												lstDiarioDetalle.add(diarioDet);
//											}											
//										}
//							}
//							
//						}
//
//					}
					
					//1. Recobramos valores de periodo actual
//					Integer intPeriodoActual = new Integer(anio+""+mes);
//					log.info("periodo actual: "+intPeriodoActual);
//					Timestamp tsUltimoDiaDelPeriodo = obtenerUltimoDiaDelMesAnioPeriodo(intPeriodoActual);
//					log.info("ultimo dia del mes: "+tsUltimoDiaDelPeriodo);
//					//2. validamos el ultimo interes registrado en movimiento sea fecha fin de mes:
//					// Obtenemos el ultimo movimiento de capital y de interes...
////					CuentaConceptoDetalle ccd = new CuentaConceptoDetalle();
////					ccd.setId(new CuentaConceptoDetalleId());
////					ccd.getId().setIntPersEmpresaPk(socioComp.getCuenta().getId().getIntPersEmpresaPk());
////					ccd.getId().setIntCuentaPk(expedienteLIquidacion.getBeneficiarioLiquidacionGirar().getId().getIntCuenta());
////					ccd.setIntParaTipoConceptoCod(Constante.PARAM_T_CUENTACONCEPTO_RETIRO);
////					lstCtaCtpoDet = conceptoFacade.getCuentaConceptoDetallePorPKCuentaYTipoConcepto(ccd);
//					List<Movimiento> lstMovInteres = null;
////					CuentaConcepto ctaCpto = null;
////					if (lstCtaCtpoDet!=null && !lstCtaCtpoDet.isEmpty()) {
////						for (CuentaConceptoDetalle listCCD : lstCtaCtpoDet) {
////							//modificamos la ctacptodetalle con la fecha de cierre
////							CuentaConceptoId ccId = new CuentaConceptoId();
////							ccId.setIntPersEmpresaPk(listCCD.getId().getIntPersEmpresaPk());
////							ccId.setIntCuentaPk(listCCD.getId().getIntCuentaPk());
////							ccId.setIntItemCuentaConcepto(listCCD.getId().getIntItemCuentaConcepto());
////							ctaCpto = conceptoFacade.getCuentaConceptoPorPK(ccId);
////
//////							lstMovCapital = conceptoFacade.getListaMovimientoPorCtaItemConceptoTipoConceptoGeneral(listCCD.getId().getIntPersEmpresaPk(),
//////																listCCD.getId().getIntCuentaPk(),listCCD.getId().getIntItemCuentaConcepto(),
//////																Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO);
//							lstMovInteres = conceptoFacade.getListaMovimientoPorCtaItemConceptoTipoConceptoGeneral(expedienteLIquidacion.getCuentaConceptoComp().getLstCuentaConceptoDetalle().get(0).getId().getIntPersEmpresaPk(),
//																expedienteLIquidacion.getCuentaConceptoComp().getLstCuentaConceptoDetalle().get(0).getId().getIntCuentaPk(),
//																expedienteLIquidacion.getCuentaConceptoComp().getLstCuentaConceptoDetalle().get(0).getId().getIntItemCuentaConcepto(),
//																Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO_INTERES);
////							break;
////						}
////					}
//					// Captuaramos la fecha del ultimo movimiento interes
//					if (lstMovInteres!=null && !lstMovInteres.isEmpty()) tsFechaUltimoMovFdoRetiroInteres = lstMovInteres.get(0).getTsFechaMovimiento();
//					Movimiento ultimoMovimientoInteresRetiro = recuperarUltimoMovimeintoInteresRetiro(cuentaConceptoRetiroComp, expedienteLiquidacion);
					tsFechaUltimoMovFdoRetiroInteres = expedienteLIquidacion.getMovimiento()!=null?expedienteLIquidacion.getMovimiento().getTsFechaMovimiento():tsFechaInicioCtaCptoDet;
					String strFechaUltimoInteres = Constante.sdf.format(tsFechaUltimoMovFdoRetiroInteres);
					Date dtFechaUltimoInteres = Constante.sdf.parse(strFechaUltimoInteres);					
					Date dtHoy = new Date();
					Integer nroDias =  obtenerDiasEntreFechas(dtHoy, dtFechaUltimoInteres);
					nroDias = Math.abs(nroDias);
					
					
					//comparamos la fecha del ultimo interes generado con la del fin de mes...
					if (nroDias.compareTo(0)!=0) {
						o1.setId(new ModeloDetalleNivelId());
						o1.getId().setIntEmpresaPk(socioComp.getCuenta().getId().getIntPersEmpresaPk());
						o1.getId().setIntPersEmpresaCuenta(socioComp.getCuenta().getId().getIntPersEmpresaPk());
						o1.getId().setIntContPeriodoCuenta(anioActual);
						o1.setIntTipoModeloContable(Constante.PARAM_T_TIPOMODELOCONTABLE_PROVISION_RETIRO_INTERES);
						//
						o2.setId(new ModeloDetalleNivelId());
						o2.getId().setIntEmpresaPk(socioComp.getCuenta().getId().getIntPersEmpresaPk());
						o2.getId().setIntPersEmpresaCuenta(socioComp.getCuenta().getId().getIntPersEmpresaPk());
						o2.getId().setIntContPeriodoCuenta(anioActual);
						o2.setIntTipoModeloContable(Constante.PARAM_T_TIPOMODELOCONTABLE_PROVISION_RETIRO_CAPITALIZACIONINTERES);
						//
						lstModeloProvisionRetiroInteres = modeloFacade.getModeloProvRetiroInteres(o1);
						lstModeloProvisionRetiroCapitalizacionInteres = modeloFacade.getModeloProvRetiroInteres(o2);
					}
					if (lstModeloProvisionRetiroInteres!=null && !lstModeloProvisionRetiroInteres.isEmpty()) {
						for (ModeloDetalleNivelComp modProvRetInt : lstModeloProvisionRetiroInteres) {
							LibroDiarioDetalle diarioDet = new LibroDiarioDetalle();
							diarioDet.setId(new LibroDiarioDetalleId());
							diarioDet.setStrContNumeroCuenta(modProvRetInt.getStrNumeroCuenta());
							diarioDet.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
							diarioDet.setIntContPeriodo(modProvRetInt.getIntPeriodoCuenta());
							diarioDet.setIntPersEmpresaCuenta(modProvRetInt.getIntEmpresaCuenta());
							diarioDet.setIntPersPersona(socioComp.getPersona().getIntIdPersona());
							diarioDet.setIntPersEmpresaSucursal(Constante.PARAM_EMPRESASESION);
							diarioDet.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
							diarioDet.setIntSudeIdSubSucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
							diarioDet.setStrComentario(modProvRetInt.getStrDescCuenta());
							diarioDet.setStrNumeroDocumento(socioComp.getPersona().getIntIdPersona()+"-"+expedienteLIquidacion.getId().getIntItemExpediente());
							
							//diarioDet.set
							if(modProvRetInt.getIntParamDebeHaber().compareTo(Constante.PARAM_T_LIBRO_DIARIO_DEBE)==0){
								diarioDet.setIntParaDocumentoGeneral(null);
								diarioDet.setBdDebeSoles(expedienteLIquidacion.getMovimiento().getBdMontoMovimiento());
								diarioDet.setBdHaberSoles(null);
							}else if(modProvRetInt.getIntParamDebeHaber().compareTo(Constante.PARAM_T_LIBRO_DIARIO_HABER)==0){
								diarioDet.setIntParaDocumentoGeneral(null);
								diarioDet.setBdHaberSoles(expedienteLIquidacion.getMovimiento().getBdMontoMovimiento());
								diarioDet.setBdDebeSoles(null);
							}
							lstDiarioDetalle.add(diarioDet);
						}
					}
					
					if (lstModeloProvisionRetiroCapitalizacionInteres!=null && !lstModeloProvisionRetiroCapitalizacionInteres.isEmpty()) {
						for (ModeloDetalleNivelComp modProvRetCapInt : lstModeloProvisionRetiroCapitalizacionInteres) {
							LibroDiarioDetalle diarioDet = new LibroDiarioDetalle();
							diarioDet.setId(new LibroDiarioDetalleId());
							diarioDet.setStrContNumeroCuenta(modProvRetCapInt.getStrNumeroCuenta());
							diarioDet.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
							diarioDet.setIntContPeriodo(modProvRetCapInt.getIntPeriodoCuenta());
							diarioDet.setIntPersEmpresaCuenta(modProvRetCapInt.getIntEmpresaCuenta());
							diarioDet.setIntPersPersona(socioComp.getPersona().getIntIdPersona());
							diarioDet.setIntPersEmpresaSucursal(Constante.PARAM_EMPRESASESION);
							diarioDet.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
							diarioDet.setIntSudeIdSubSucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
							diarioDet.setStrComentario(modProvRetCapInt.getStrDescCuenta());
							diarioDet.setStrNumeroDocumento(socioComp.getPersona().getIntIdPersona()+"-"+expedienteLIquidacion.getId().getIntItemExpediente());
							
							//diarioDet.set
							if(modProvRetCapInt.getIntParamDebeHaber().compareTo(Constante.PARAM_T_LIBRO_DIARIO_DEBE)==0){
								diarioDet.setIntParaDocumentoGeneral(null);
								diarioDet.setBdDebeSoles(expedienteLIquidacion.getMovimiento().getBdMontoMovimiento());
								diarioDet.setBdHaberSoles(null);
							}else if(modProvRetCapInt.getIntParamDebeHaber().compareTo(Constante.PARAM_T_LIBRO_DIARIO_HABER)==0){
								diarioDet.setIntParaDocumentoGeneral(null);
								diarioDet.setBdHaberSoles(expedienteLIquidacion.getMovimiento().getBdMontoMovimiento());
								diarioDet.setBdDebeSoles(null);
							}
							lstDiarioDetalle.add(diarioDet);
						}
					}		
					
					diario.setListaLibroDiarioDetalle(lstDiarioDetalle); 
					System.out.println("===============================================================");
					for (LibroDiarioDetalle detalle : lstDiarioDetalle) {
						System.out.println("NRO CUENTA ---> "+detalle.getStrContNumeroCuenta());
						System.out.println("DEBE ---> "+detalle.getBdDebeSoles());
						System.out.println("HABER ---> "+detalle.getBdHaberSoles());
					}
					System.out.println("===============================================================");
					diario = libroDiarioFacade.grabarLibroDiario(diario);
				//}

			}catch(BusinessException e){
				System.out.println("Error en obtieneLibroDiarioDetalleRefinanciamiento 1 --> "+e);
				//throw e;
			}catch(Exception e){
				System.out.println("Error en obtieneLibroDiarioDetalleRefinanciamiento 2 --> "+e);
				//throw new BusinessException(e);
			}
			
			//lstDiarioDetalle
			return diario;
		}
	
	
	 public static Timestamp obtenerUltimoDiaDelMesAnioPeriodo(Integer periodo){
    	 java.sql.Timestamp timeStampDate = null;    	
 		String dateStop = periodo.toString();    	 
 		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
 		try{
 			Date d2 = null;
 			d2 = format.parse(dateStop);
 			DateTime fecha = new DateTime(d2);
 			DateTime fechaFinMes=fecha.dayOfMonth().withMaximumValue(); 
 			timeStampDate = new Timestamp(fechaFinMes.getMillis());	 			
 		} catch (Exception e) {
 			e.printStackTrace();
 		 }
    	 return timeStampDate;
     }
	 
	  public static String  obtieneAnio(Date date){
			
			java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("yyyy");
			String anio = sdf.format(date);
			return anio;
		}
		
		
		
		 public static final String obtieneMesCadena(Date date){
	  		
	  		java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("MM");
	  		String minuto = sdf.format(date);
	  		return minuto;
	  	}
		 
		 
			public static final Timestamp obtieneFechaActualEnTimesTamp(){
				
			    java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
				java.sql.Date sqlDate1 = new java.sql.Date(utilDate.getTime());
				utilDate = new java.util.Date(System.currentTimeMillis());
				java.sql.Date sqlDate2 = new java.sql.Date(utilDate.getTime());
				java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate1.getTime());
				System.out.println(ts);
				ts = new java.sql.Timestamp(sqlDate2.getTime());
			
			 return ts;
			}
	
	/**
	 * Se recupera cuentas conceptos comp de retiro y aportes.
	 * Para el proceso de liquidacion de cuentas.
	 * @param idCuenta
	 * @return
	 */
	public List<CuentaConceptoComp> recuperarCuentasConceptoRetiroAportesXSocio(CuentaId idCuenta){
		List<CuentaConcepto> listaCuentaConcepto = null;
		List<CuentaConceptoComp> listaCuentaConceptoComp = null;
		ConceptoFacadeRemote conceptoFacade = null;
		
		try {
			conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			
			if(idCuenta != null){
				
				// Recuperamos las cuentas concepto del socio.
				listaCuentaConcepto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(idCuenta);
				
				if(listaCuentaConcepto != null && !listaCuentaConcepto.isEmpty()){
					listaCuentaConceptoComp = new ArrayList<CuentaConceptoComp>();
					CuentaConceptoComp cuentaConceptoComp = null;
					//CuentaConcepto cuentaConcepto = null;
					Boolean blnAgregar = null;
					
					for (CuentaConcepto cuentaConcepto : listaCuentaConcepto) {
						cuentaConceptoComp = new CuentaConceptoComp();
						CuentaConceptoDetalle detalle = null;
						blnAgregar = Boolean.FALSE;
						
						if(cuentaConcepto.getListaCuentaConceptoDetalle() != null 
						&& !cuentaConcepto.getListaCuentaConceptoDetalle().isEmpty()){
							detalle = new CuentaConceptoDetalle();
							detalle = cuentaConcepto.getListaCuentaConceptoDetalle().get(0);
							//Agregar solo las cuentas concepto con saldo diferente a zero.
							if(detalle.getIntParaTipoConceptoCod().compareTo(Constante.PARAM_T_CUENTACONCEPTO_APORTES)==0){
								if (!(cuentaConcepto.getBdSaldo().equals(BigDecimal.ZERO))) {
									cuentaConceptoComp.setCuentaConcepto(cuentaConcepto);
									cuentaConceptoComp.setIntParaConceptoGeneralModelo(0);
									cuentaConceptoComp.setIntParaTipoCaptacionModelo(Constante.PARAM_T_CUENTACONCEPTO_APORTES);
									blnAgregar = Boolean.TRUE;
								}			
								
							}else if(detalle.getIntParaTipoConceptoCod().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0 ){
								if (!(cuentaConcepto.getBdSaldo().equals(BigDecimal.ZERO))) {
									cuentaConceptoComp.setCuentaConcepto(cuentaConcepto);
									cuentaConceptoComp.setIntParaConceptoGeneralModelo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO);
									cuentaConceptoComp.setIntParaTipoCaptacionModelo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO);
									blnAgregar = Boolean.TRUE;
								}
							}
		
						}	
						
						if(blnAgregar){
							listaCuentaConceptoComp.add(cuentaConceptoComp);
						}
						
					}
				}

			}
			
		} catch (Exception e) {
			log.error("Error en recuperarCuentasConceptoXSocio ---> "+e);
		}
		
		return listaCuentaConceptoComp;
	}
	
	
	/**
	 * Recupera las cuentas concepto de aoprte retiro y si tuviera la de interes
	 * @return
	 */
	public List<CuentaConceptoComp> recuperarCuentasConceptoAporteRetiroEInteres(SocioComp socioComp, ExpedienteLiquidacion expedienteLiquidacion)throws BusinessException{
		
		List<CuentaConceptoComp> listaCuentaConceptosComp = null;
		List<CuentaConceptoComp> listaCuentaConceptosMasInteresComp = null;
		try {
			
			// Recuperamos las cuentas concepto de retiro, aportes y agregamos la de interes de retiro.
			listaCuentaConceptosComp = recuperarCuentasConceptoRetiroAportesXSocio(socioComp.getCuenta().getId());
			if(listaCuentaConceptosComp != null && !listaCuentaConceptosComp.isEmpty()){
				listaCuentaConceptosMasInteresComp =  new ArrayList<CuentaConceptoComp>(listaCuentaConceptosComp);

				for (CuentaConceptoComp cuentaConceptoComp : listaCuentaConceptosComp) {
					if(cuentaConceptoComp.getIntParaTipoCaptacionModelo().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0){
						CuentaConceptoComp cuentaConceptoInteresComp = null;
						
						cuentaConceptoInteresComp = recuperarCuentasConceptoInteresRetiro(cuentaConceptoComp.getCuentaConcepto(),expedienteLiquidacion, socioComp);
						if(cuentaConceptoInteresComp != null){
							listaCuentaConceptosMasInteresComp.add(cuentaConceptoInteresComp);
						}
					}
				}	
			}
			
			
		} catch (Exception e) {
			log.error("Error en recuperarCuentasConceptoAporteRetiroEInteres ---> "+e);
		}
		
		
		return listaCuentaConceptosMasInteresComp;
	}
	
	
	/**
	 * Le adiciona el concepto de INTERES a la lista de cuentas concepto del socio. Solo para fines practicos.
	 * Ademas recalcula el monto del ineteres diario.
	 * Interes no es cuenta concepto
	 * @param cuentaConceptoInteresRetiro
	 * @return
	 */
	public CuentaConceptoComp recuperarCuentasConceptoInteresRetiro(CuentaConcepto cuentaConceptoRetiro, ExpedienteLiquidacion expedienteLiquidacion, SocioComp socioComp){
//		List<CuentaConcepto> listaCuentaConcepto = null;
//		List<CuentaConceptoComp> listaCuentaConceptoComp = null;
		List<Movimiento> listaMovimiento = null;
		CuentaConcepto cuentaConceptoInteres = null;
		CuentaConceptoComp cuentaConceptoInteresComp = null;
		
		ConceptoFacadeRemote conceptoFacade = null;
		BigDecimal bdMonto= BigDecimal.ZERO;
		
		try {
			conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			
			if(cuentaConceptoRetiro != null){
				
				//intPersEmpresa, intCuenta, intItemCuentaConcepto, intTipoConceptoGeneral
				listaMovimiento = conceptoFacade.getListaMovimientoPorCtaItemConceptoTipoConceptoGeneral(cuentaConceptoRetiro.getId().getIntPersEmpresaPk(), 
						cuentaConceptoRetiro.getId().getIntCuentaPk(), 
						cuentaConceptoRetiro.getId().getIntItemCuentaConcepto(), 
						Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO_INTERES);
				
				if(listaMovimiento != null && !listaMovimiento.isEmpty()){
					cuentaConceptoInteres = new CuentaConcepto();
					cuentaConceptoInteresComp = new CuentaConceptoComp();
					
					for (Movimiento movimiento : listaMovimiento) {
						bdMonto = bdMonto.add(movimiento.getBdMontoMovimiento());
					}
					
					//cuentaConceptoInteres = new  CuentaConcepto();//cuentaConceptoRetiro;
					//cuentaConceptoInteres.getId().setIntItemCuentaConcepto(null);
					//jchavez 04.06.2014 calculo del interes de fdo de retiro
					cuentaConceptoInteres.setBdSaldo(expedienteLiquidacion.getMovimiento().getBdMontoSaldo());//calcularInteresRetiroAcumulado(expedienteLiquidacion, socioComp));
					cuentaConceptoInteresComp.setCuentaConcepto(cuentaConceptoInteres);
					cuentaConceptoInteresComp.setIntParaConceptoGeneralModelo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO_INTERES);
					cuentaConceptoInteresComp.setIntParaTipoCaptacionModelo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO);
				}else{
					throw new BusinessException("No se recuperaron los moviemintos de interes.");
				}

			}
			
		} catch (Exception e) {
			log.error("Error en recuperarCuentasConceptoInteresRetiro ---> "+e);
		}
		
		return cuentaConceptoInteresComp;
	}
	
	
	
	/**
	 * Calcula el interes generado por el fondo de retiro MAs el saldo actual del ineteres ganado. 
	 * @param cuentaConceptoRetiro
	 * @param expedientePrevision
	 * @return
	 */
	public BigDecimal calcularInteresRetiroAcumulado(ExpedienteLiquidacion expedienteLiquidacion, SocioComp socioComp){
		List<Movimiento> listaMovimiento = null; 
		///CuentaConcepto cuentaConceptoRetiro = null;
		Movimiento movimientoInteresUltimo = null;
		//BigDecimal bdMontoTotal = BigDecimal.ZERO;
//		Integer intNroDias = 0;
		//BigDecimal bdPorcentajeInteres = BigDecimal.ZERO;	
		BigDecimal bdInteresCalculado = BigDecimal.ZERO;
		Captacion beanCaptacion = null;
		CaptacionFacadeRemote captacionFacade = null;
		ConceptoFacadeRemote conceptoFacadeRemote= null;
		BigDecimal bdMontoInteresMasSaldoInteres = BigDecimal.ZERO;
		CuentaConceptoComp ctaCtoRetiroComp = null;
		CuentaConceptoDetalle ctaCtoDetalle = null;
		CuentaConcepto cuentaConceptoRetiro = null;

		try {
			captacionFacade = (CaptacionFacadeRemote)EJBFactory.getRemote(CaptacionFacadeRemote.class);
			conceptoFacadeRemote = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			
			// se recupera la ctacto de retiro y desde su detalle se obtiene las llaves de la captacion a fin de saber cual es porcentaje
			// a aplicar en el calculo.
			
			ctaCtoRetiroComp = recuperarCuentaConceptoPorTipo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO, socioComp);
			if(ctaCtoRetiroComp != null && ctaCtoRetiroComp.getCuentaConcepto() != null){
				cuentaConceptoRetiro = ctaCtoRetiroComp.getCuentaConcepto();
				
				ctaCtoDetalle = new CuentaConceptoDetalle();
				ctaCtoDetalle = ctaCtoRetiroComp.getCuentaConcepto().getListaCuentaConceptoDetalle().get(0);
				CaptacionId captacionId = new CaptacionId();
				captacionId.setIntPersEmpresaPk(ctaCtoDetalle.getId().getIntPersEmpresaPk());
				captacionId.setIntParaTipoCaptacionCod(ctaCtoDetalle.getIntParaTipoConceptoCod());
				captacionId.setIntItem(ctaCtoDetalle.getIntItemConcepto());

				beanCaptacion = captacionFacade.getCaptacionPorIdCaptacion(captacionId);
							
							
				if(beanCaptacion != null){
					
					// 1. Se recupera el porcentaje del ineters a aplicar.
					//beanCaptacion.getIntTasaInteres();
					
					// 2. Recuperamos las llaves de la cuenta cto retiro, para obetener los movimientos de interes de retiro.
					//cuentaConceptoRetiro = recuperarCuentaConceptoBase(Constante.PARAM_T_CUENTACONCEPTO_RETIRO);
					if(cuentaConceptoRetiro != null){
						listaMovimiento = conceptoFacadeRemote.getListaMovimientoPorCtaItemConceptoTipoConceptoGeneral(cuentaConceptoRetiro.getId().getIntPersEmpresaPk(), 
								cuentaConceptoRetiro.getId().getIntCuentaPk(), 
								cuentaConceptoRetiro.getId().getIntItemCuentaConcepto(), 
								Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO_INTERES);
						
						if(listaMovimiento != null && !listaMovimiento.isEmpty()){
							//Ordenamos los subtipos por int
							Collections.sort(listaMovimiento, new Comparator<Movimiento>(){
								public int compare(Movimiento uno, Movimiento otro) {
									return uno.getIntItemMovimiento().compareTo(otro.getIntItemMovimiento());
								}
							});
							
							Integer tamanno = 0;
							tamanno = listaMovimiento.size();
							tamanno = tamanno -1;
							// recuperamos el ultimo registro y en base a le se realiza calculo...
							movimientoInteresUltimo = new Movimiento();
							movimientoInteresUltimo = listaMovimiento.get(tamanno);
							
							BigDecimal bdTotalBaseCtaMasInt = BigDecimal.ZERO;
							
							
							// a. 	Calculamos el monto base para el calculo de interes
							//bdTotalBaseCtaMasInt = movimientoInteresUltimo.getBdMontoSaldo().add(movimientoInteresUltimo.getBdMontoSaldo());
							bdTotalBaseCtaMasInt = movimientoInteresUltimo.getBdMontoSaldo();
							
							// b. Recuperamos nro de dias entres fecha de movimeitno y hoy.
							Date dtFechaUltimoInteres = new Date();
							String strFechaUltimoInteres = Constante.sdf.format(movimientoInteresUltimo.getTsFechaMovimiento());
							dtFechaUltimoInteres = Constante.sdf.parse(strFechaUltimoInteres);
							
							Date dtHoy = new Date();

							Integer nroDias =  obtenerDiasEntreFechas(dtHoy, dtFechaUltimoInteres);
							nroDias = Math.abs(nroDias);
							
							
							if(nroDias.compareTo(0)!= 0){
								// c. Se aplica formula simple de interes --> Monto*Porc Interes*(dias/30)
								bdInteresCalculado =  bdTotalBaseCtaMasInt.multiply(new BigDecimal(nroDias).multiply(beanCaptacion.getBdTem())).divide(new BigDecimal(30),2,RoundingMode.HALF_UP);
								bdMontoInteresMasSaldoInteres = bdInteresCalculado.add(bdTotalBaseCtaMasInt);
							}else{
								bdInteresCalculado = BigDecimal.ZERO;
								bdInteresCalculado = BigDecimal.ZERO.add(bdTotalBaseCtaMasInt);
							}
						}
					}
				}		
			}
			
			
			
		} catch (Exception e) {
			log.error("Error en calcularInteresRetiroAcumulado ---> "+e);
		}
		return bdMontoInteresMasSaldoInteres;
	}
	
	
	/**
	 * 
	 * @param dtFechaInicio
	 * @param dtFechaFin
	 * @return
	 * @throws Exception
	 */
//	public static Integer obtenerDiasEntreFechas(Date dtFechaInicio, Date dtFechaFin)throws Exception{
//		return (int)( (dtFechaFin.getTime() - dtFechaInicio.getTime()) / (1000 * 60 * 60 * 24) );
//	}
	
	public static Integer obtenerDiasEntreFechas(Date dtFechaInicio, Date dtFechaFin)throws Exception{
		SimpleDateFormat strEnlace = new SimpleDateFormat("dd/MM/yyyy");
		Date dtFecIni = strEnlace.parse(strEnlace.format(dtFechaInicio));
		Date dtFecFin = strEnlace.parse(strEnlace.format(dtFechaFin));
		return (int)( (dtFecFin.getTime() - dtFecIni.getTime()) / (1000 * 60 * 60 * 24) );
	} 
	
	public CuentaConceptoComp recuperarCuentaConceptoPorTipo(Integer intCuentaConcepto, SocioComp socioComp){
		//Boolean blnContinuaAporte = Boolean.TRUE;
//		Boolean blnContinuaRetiro = Boolean.TRUE;
		List<CuentaConcepto> listaCuentaConcepto= null;
//		List<CuentaConceptoComp> listaCuentaConceptoComp = null;
		CuentaConceptoComp cuentaConceptoComp = null;
		ConceptoFacadeRemote conceptoFacade = null;
		
		try {
			conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			
			listaCuentaConcepto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(socioComp.getCuenta().getId());

			// Solo se deben visualizar 4 cuentas: Aporte, Retiro, Ahoroo y Depaosito
//			listaCuentaConceptoComp = new ArrayList<CuentaConceptoComp>();

			for (CuentaConcepto cuentaConcepto : listaCuentaConcepto) {
				
				CuentaConceptoDetalle detalle = null;
				
				if(cuentaConcepto.getListaCuentaConceptoDetalle() != null 
				&& !cuentaConcepto.getListaCuentaConceptoDetalle().isEmpty()){
					detalle = new CuentaConceptoDetalle();
					detalle = cuentaConcepto.getListaCuentaConceptoDetalle().get(0);

					if(detalle.getIntParaTipoConceptoCod().compareTo(intCuentaConcepto)==0){
						cuentaConceptoComp = new CuentaConceptoComp();
						cuentaConceptoComp.setCuentaConcepto(cuentaConcepto);
						break;
					}
				}	
			}
		}
			 catch (Exception e) {
			log.error("Error en recuperarCuentaConceptoPorTipo ---> "+e);
		}	
		return cuentaConceptoComp;
	}
	

	/**
	 * Genera la SolicitudCtaCte, los estados (pendiente y atendido),solicitudCtacteTipo y la Devolucion
	 * Se devuelve el objeto  SolicitudCtaCte cargado.
	 * Del tipo Devolucion
	 * @param socioComp
	 * @param strPeriodo
	 * @param requisitoPrev
	 * @param usuario
	 * @param expedientePrevision
	 * @param libroDiario
	 * @return
	 * @throws EJBFactoryException
	 * @throws BusinessException
	 */
	 public SolicitudCtaCte grabarSolicitudCtaCteParaDevolucionLiquidacion(SocioComp socioComp,Integer strPeriodo, RequisitoLiquidacion requisitoLiq, Usuario usuario,
			 ExpedienteLiquidacion expedienteLiquidacion, LibroDiario libroDiario) 
	 	throws EJBFactoryException, BusinessException {
		 SolicitudCtaCte solicitudCtaCteDevolucion = null;
		 EstadoSolicitudCtaCte estadoPendiente = null;
		 EstadoSolicitudCtaCte estadoAtendido = null;

		 CuentacteFacadeRemote cuentaCteFacadeRemote= null;
		 
		 try {
			 //Autor: jchavez / Tarea: Modificación / Fecha: 12.08.2014 / 
//			 Calendar fecHoy = Calendar.getInstance();
//			 Date dtAhora = fecHoy.getTime();
			 Timestamp dtAhora = new Timestamp(new Date().getTime());				 
			 cuentaCteFacadeRemote = (CuentacteFacadeRemote)EJBFactory.getRemote(CuentacteFacadeRemote.class);
			 
				if(socioComp != null){
				
				// Generando la solciitud cta cte para prevision - devolucion
					solicitudCtaCteDevolucion = new SolicitudCtaCte();
					solicitudCtaCteDevolucion.setId(new SolicitudCtaCteId());
					solicitudCtaCteDevolucion.getId().setIntEmpresasolctacte(socioComp.getSocio().getId().getIntIdEmpresa());
					solicitudCtaCteDevolucion.setIntPersEmpresa(socioComp.getCuenta().getId().getIntPersEmpresaPk());
					solicitudCtaCteDevolucion.setIntPersPersona(socioComp.getPersona().getNatural().getIntIdPersona());
					solicitudCtaCteDevolucion.setIntCsocCuenta(socioComp.getCuenta().getId().getIntCuenta());
					solicitudCtaCteDevolucion.setIntSucuIdsucursalsocio(socioComp.getCuenta().getIntIdUsuSucursal());
					solicitudCtaCteDevolucion.setIntSudeIdsubsucursalsocio(socioComp.getCuenta().getIntIdUsuSubSucursal());
					solicitudCtaCteDevolucion.setIntPeriodo(new Integer(strPeriodo));
					solicitudCtaCteDevolucion.setIntParaTipomodalidad(socioComp.getSocio().getSocioEstructura().getIntModalidad());
					solicitudCtaCteDevolucion.setIntParaTipo(0);
					solicitudCtaCteDevolucion.setIntMaeItemarchivo(0);		
					solicitudCtaCteDevolucion.setIntMaeItemhistorico(0);
					 
					solicitudCtaCteDevolucion = cuentaCteFacadeRemote.grabarSolicitudCtaCte(solicitudCtaCteDevolucion);
					 
				// Generando Estados de la solciitud cta cte (Pendiente y ATendido)
					 if(solicitudCtaCteDevolucion.getId().getIntCcobItemsolctacte() != null){
						 List<EstadoSolicitudCtaCte> lstEstadosSolCta = new ArrayList<EstadoSolicitudCtaCte>();
						 estadoPendiente = new EstadoSolicitudCtaCte();
						 estadoPendiente.setId(new EstadoSolicitudCtaCteId());
						 estadoAtendido = new EstadoSolicitudCtaCte();
						 estadoAtendido.setId(new EstadoSolicitudCtaCteId());

						 estadoPendiente.getId().setIntPersEmpresaSolctacte(solicitudCtaCteDevolucion.getId().getIntEmpresasolctacte());
						 estadoPendiente.getId().setIntCcobItemSolCtaCte(solicitudCtaCteDevolucion.getId().getIntCcobItemsolctacte());
						 estadoPendiente.setIntParaEstadoSolCtaCte(Constante.PARAM_T_ESTADO_SOLCTACTE_PENDIENTE);
						 estadoPendiente.setDtEsccFechaEstado(dtAhora);
						 estadoPendiente.setIntPersEmpresaEstado(usuario.getEmpresa().getIntIdEmpresa());
						 estadoPendiente.setIntSucuIduSusucursal(usuario.getSucursal().getId().getIntIdSucursal());
						 estadoPendiente.setIntSudeIduSusubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
						 estadoPendiente.setIntPersUsuarioEstado(usuario.getIntPersPersonaPk());
						 estadoPendiente.setStrEsccObservacion("Registro Automatico por Devolucion de Liquidacion - Estado Pendiente. "+
								 usuario.getSucursal().getId().getIntIdSucursal()+"-"+usuario.getSubSucursal().getId().getIntIdSubSucursal()+""+
								 usuario.getIntPersPersonaPk());
						 estadoPendiente = cuentaCteFacadeRemote.grabarEstadoSolicitudCtaCte(estadoPendiente);
						 lstEstadosSolCta.add(estadoPendiente);
						 
						 estadoAtendido.getId().setIntPersEmpresaSolctacte(solicitudCtaCteDevolucion.getId().getIntEmpresasolctacte());
						 estadoAtendido.getId().setIntCcobItemSolCtaCte(solicitudCtaCteDevolucion.getId().getIntCcobItemsolctacte());
						 estadoAtendido.setIntParaEstadoSolCtaCte(Constante.PARAM_T_ESTADO_SOLCTACTE_ATENDIDO);
						 estadoAtendido.setDtEsccFechaEstado(dtAhora);
						 estadoAtendido.setIntPersEmpresaEstado(usuario.getEmpresa().getIntIdEmpresa());
						 estadoAtendido.setIntSucuIduSusucursal(usuario.getSucursal().getId().getIntIdSucursal());
						 estadoAtendido.setIntSudeIduSusubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
						 estadoAtendido.setIntPersUsuarioEstado(usuario.getIntPersPersonaPk());
						 estadoAtendido.setStrEsccObservacion("Registro Automatico por Devolucion de Liquidacion - Estado Atendido. "+
								 usuario.getSucursal().getId().getIntIdSucursal()+"-"+usuario.getSubSucursal().getId().getIntIdSubSucursal()+""+
								 usuario.getIntPersPersonaPk());
						 estadoPendiente = cuentaCteFacadeRemote.grabarEstadoSolicitudCtaCte(estadoAtendido);
						 lstEstadosSolCta.add(estadoAtendido);
						 
				//Generando la Solicitud Tipo
						 SolicitudCtaCteTipo solCtaCteTipo = new SolicitudCtaCteTipo();
						 SolicitudCtaCteTipoId solCtaCteTipoId = new SolicitudCtaCteTipoId();
						 
						 solCtaCteTipoId.setIntCcobItemsolctacte(solicitudCtaCteDevolucion.getId().getIntCcobItemsolctacte());
						 solCtaCteTipoId.setIntPersEmpresasolctacte(solicitudCtaCteDevolucion.getId().getIntEmpresasolctacte());
						 solCtaCteTipoId.setIntTipoSolicitudctacte(Constante.PARAM_T_SOLICITUD_CTACTE_TIPO_DEVOLUCION);
						 solCtaCteTipo.setId(solCtaCteTipoId);
						 solCtaCteTipo.setIntTaraEstado(Constante.PARAM_T_ESTADO_SOLCTACTE_ATENDIDO);
						 solCtaCteTipo.setIntParaTipoorigen(Constante.PARAM_T_TIPO_ORIGEN_SOLICITUD_CUENTA_CORRIENTE);
						 solCtaCteTipo.setIntParaEstadoanalisis(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						 solCtaCteTipo.setStrScctObservacion("Generado por autorizacion de Liquidacion de cta -Devolucion. ");
						 solCtaCteTipo.setDtFechaDocumento(dtAhora);
						 solCtaCteTipo.setIntMotivoSolicitud(Constante.PARAM_T_MOTIVO_SOLICITUD_CTACTE_LIQUIDACION_CTA_CTO); 
						 solCtaCteTipo.setIntPersUsuario(usuario.getIntPersPersonaPk());
						 solCtaCteTipo.setIntCcobItemefectuado(null);
						 
						 solCtaCteTipo = cuentaCteFacadeRemote.grabarSolicitudCtaCteTipo(solCtaCteTipo);
						 if(solCtaCteTipo != null){
							 List<SolicitudCtaCteTipo> lstSolCtaCteTipo= new ArrayList<SolicitudCtaCteTipo>();
							 lstSolCtaCteTipo.add(solCtaCteTipo);
							 solicitudCtaCteDevolucion.setListaSolCtaCteTipo(lstSolCtaCteTipo);
						 }else{
							 throw new BusinessException("No se genero la solicitud cta cte tipo Inicial.");
						 }
					 }
				 }
				
				
				

		} catch (Exception e) {
			throw new BusinessException("No se genero la solicitud cta cte tipo Inicial.");
		}
		 return solicitudCtaCteDevolucion; 
	 }
	 
	 

		/**
		 * 
		 * @param socioComp
		 * @param libroDiarioLiquidacion
		 * @param usuario
		 * @param expedienteLiquidacion
		 * @return
		 * @throws BusinessException
		 */
		public List<Movimiento> validarYGenerarCuentaConceptoMovimentoYCuentaCtoDetalleActualizaciones_3 (SocioComp socioComp,LibroDiario libroDiarioLiquidacion, Usuario usuario, ExpedienteLiquidacion expedienteLiquidacion)throws BusinessException{
			List<CuentaConceptoComp> listaCuentasConceptoComp = null;
			//List<Movimiento> listaMovientoLiquidacionCuentas = null;
			ConceptoFacadeRemote conceptoFacadeRemote = null;
			Boolean blnHasAporte = Boolean.FALSE;
			Boolean blnHasRetiro = Boolean.FALSE;
			Boolean blnHasInteres = Boolean.FALSE;
			Boolean blnCumpleAporte = Boolean.FALSE;
			Boolean blnCumpleRetiro = Boolean.FALSE;
			Boolean blnCumpleInteres = Boolean.FALSE;
//			CuentaConcepto cuentaConceptoInteres = null;
			
//			Boolean blnOK = Boolean.FALSE;
			List<Movimiento> listMovimeintosGenerados = null;
			
			try {
				conceptoFacadeRemote = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
				if(libroDiarioLiquidacion != null){
					listaCuentasConceptoComp = recuperarCuentasConceptoAporteRetiroEInteres(socioComp,expedienteLiquidacion);
					if(listaCuentasConceptoComp != null && !listaCuentasConceptoComp.isEmpty()){

						// definimos la existencia de Aportes, Retiro e Inetres de retiro.
						for (CuentaConceptoComp ctaCtoComp : listaCuentasConceptoComp) {
							if(ctaCtoComp.getIntParaTipoCaptacionModelo().compareTo(Constante.PARAM_T_CUENTACONCEPTO_APORTES)==0){
								blnHasAporte = Boolean.TRUE;
							}else if(ctaCtoComp.getIntParaTipoCaptacionModelo().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0){
									if(ctaCtoComp.getIntParaConceptoGeneralModelo().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO)==0){
										blnHasRetiro = Boolean.TRUE;
									}else if(ctaCtoComp.getIntParaConceptoGeneralModelo().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO_INTERES)==0){
//										cuentaConceptoInteres = new CuentaConcepto();
//										cuentaConceptoInteres = ctaCtoComp.getCuentaConcepto();
										blnHasInteres= Boolean.TRUE;
									}
							}
						}

						// Si no existe retiro, no se valida y pasa
						if(!blnHasRetiro){
							blnCumpleRetiro = Boolean.TRUE;
						}
						// Si no existe interes, no se valida y pasa
						if(!blnHasInteres){
							blnCumpleInteres = Boolean.TRUE;
						}
						
						
						// Validar montos de acuerdo a MovimeitnoCtacte - Cuenta concepto - Cuenta Conceptodetalle
						for (CuentaConceptoComp cuentaConceptoComp : listaCuentasConceptoComp) {
							if(blnHasAporte){
								if(cuentaConceptoComp.getIntParaTipoCaptacionModelo().compareTo(Constante.PARAM_T_CUENTACONCEPTO_APORTES)==0){
									List<Movimiento> listaMov = null;
									
									
									/*public static final Integer PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION   = 1;
									public static final Integer PARAM_T_TIPOCONCEPTOGENERAL_INTERES 	   = 2;
									public static final Integer PARAM_T_TIPOCONCEPTOGENERAL_MORA 	       = 3;
									public static final Integer PARAM_T_TIPOCONCEPTOGENERAL_APORTACION 	   = 4;
									public static final Integer PARAM_T_TIPOCONCEPTOGENERAL_MANTENIMIENTO  = 5;
									public static final Integer PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO      = 7;
									public static final Integer PARAM_T_TIPOCONCEPTOGENERAL_FDOSEPELIO     = 6;
									public static final Integer PARAM_T_TIPOCONCEPTOGENERAL_CTAXPAGAR 	   = 13;
									public static final Integer PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO_INTERES     = 14;*/
									
									// intPersEmpresa, intCuenta, intItemCuentaConcepto
									listaMov = conceptoFacadeRemote.getListaMaximoMovimientoPorCuentaConcepto(cuentaConceptoComp.getCuentaConcepto().getId().getIntPersEmpresaPk(), 
																											cuentaConceptoComp.getCuentaConcepto().getId().getIntCuentaPk(), 
																											cuentaConceptoComp.getCuentaConcepto().getId().getIntItemCuentaConcepto(),
																											Constante.PARAM_T_TIPOCONCEPTOGENERAL_APORTACION);
									
									if(listaMov!= null && !listaMov.isEmpty()){
										List<CuentaConceptoDetalle> listaCtaCtoDetalle = null;
										
										listaCtaCtoDetalle = conceptoFacadeRemote.getMaxCuentaConceptoDetPorPKCuentaConcepto(cuentaConceptoComp.getCuentaConcepto().getId());
										if(listaCtaCtoDetalle != null && !listaCtaCtoDetalle.isEmpty()){
											
											if(cuentaConceptoComp.getCuentaConcepto().getBdSaldo().compareTo(listaMov.get(0).getBdMontoSaldo())==0
												&& cuentaConceptoComp.getCuentaConcepto().getBdSaldo().compareTo(listaCtaCtoDetalle.get(0).getBdSaldoDetalle())==0){
												blnCumpleAporte = Boolean.TRUE;
											}
											
										}
									}	
								}
							}
							
							if(blnHasRetiro){
								if(cuentaConceptoComp.getIntParaTipoCaptacionModelo().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0
									&& cuentaConceptoComp.getIntParaConceptoGeneralModelo().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO)==0){
									
									List<Movimiento> listaMov = null;
									
									listaMov = conceptoFacadeRemote.getListaMaximoMovimientoPorCuentaConcepto(cuentaConceptoComp.getCuentaConcepto().getId().getIntPersEmpresaPk(), 
											cuentaConceptoComp.getCuentaConcepto().getId().getIntCuentaPk(), 
											cuentaConceptoComp.getCuentaConcepto().getId().getIntItemCuentaConcepto(),
											Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO);
									
									if(listaMov!= null && !listaMov.isEmpty()){
										List<CuentaConceptoDetalle> listaCtaCtoDetalle = null;
										
										listaCtaCtoDetalle = conceptoFacadeRemote.getMaxCuentaConceptoDetPorPKCuentaConcepto(cuentaConceptoComp.getCuentaConcepto().getId());
										if(listaCtaCtoDetalle != null && !listaCtaCtoDetalle.isEmpty()){
											
											if(cuentaConceptoComp.getCuentaConcepto().getBdSaldo().compareTo(listaMov.get(0).getBdMontoSaldo())==0
												&& cuentaConceptoComp.getCuentaConcepto().getBdSaldo().compareTo(listaCtaCtoDetalle.get(0).getBdSaldoDetalle())==0){
												blnCumpleRetiro = Boolean.TRUE;
											}
											
										}
									}
								}
							}
							
							if(blnHasInteres){
								/*if(cuentaConceptoComp.getIntParaTipoCaptacionModelo().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0
									&& cuentaConceptoComp.getIntParaConceptoGeneralModelo().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO_INTERES)==0){
										
									CuentaConcepto  cuentaConceptoRetiro = cuentaConceptoComp.getCuentaConcepto();
									List<Movimiento> listaMovimiento = null;
									
									listaMovimiento = conceptoFacadeRemote.getListaMovimientoPorCtaItemConceptoTipoConceptoGeneral(Constante.PARAM_EMPRESASESION, 
											cuentaConceptoRetiro.getId().getIntCuentaPk(), 
											cuentaConceptoRetiro.getId().getIntItemCuentaConcepto(), 
											Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO_INTERES);
									
									if(listaMovimiento != null && !listaMovimiento.isEmpty()){
										if(listaMovimiento.get(0).getBdMontoSaldo().compareTo(cuentaConceptoInteres.getBdSaldo())==0){
											blnCumpleInteres = Boolean.TRUE;
										}
									}	
								}*/
								blnCumpleInteres = Boolean.TRUE;
							}
							
						}
						
						//
						System.out.println("blnHasAporte ********************* "+blnHasAporte);
						System.out.println("blnHasRetiro ********************* "+blnHasRetiro);
						System.out.println("blnHasInteres ******************** "+blnHasInteres);
						System.out.println("blnCumpleAporte------------------>"+blnCumpleAporte);
						System.out.println("blnCumpleRetiro------------------>"+blnCumpleRetiro);
						System.out.println("blnCumpleInteres----------------->"+blnCumpleInteres);
						
						if(blnCumpleAporte && blnCumpleRetiro && blnCumpleInteres){
							// listaCuentasConceptoComp
							listMovimeintosGenerados =  generarRegistrosMovimientoLiquidacionCuentas_4(listaCuentasConceptoComp,socioComp,usuario,expedienteLiquidacion);
							
						}else{
							//blnOK = Boolean.FALSE;
							throw new BusinessException("No Cumple las validaciones de Cuenta Concepto, Movimiento y cuanta concepto detalle. Se revierte Todo.");
						}
					}
				}
				
				
			} catch (Exception e) {
				//blnOK = Boolean.FALSE;
				throw new BusinessException("Error en validarCuentaConceptoMovimentoYCuentaCtoDetalle_3 --->" +e);
			}

			return listMovimeintosGenerados;
	
		}
		
		
		
		/**
		 * Se forman y graban los registros movimeinto para aporte, retiro, interes y el cat x pagar
		 * @param listaCuentasConceptoComp
		 * @return
		 */
		public List<Movimiento> generarRegistrosMovimientoLiquidacionCuentas_4(List<CuentaConceptoComp> listaCuentasConceptoComp, SocioComp socioComp, Usuario usuario, ExpedienteLiquidacion expedienteLiquidacion)  throws BusinessException{
			List<Movimiento> listaMovimientos = null;
			List<CuentaIntegrante> lstCuentaIntegrante = null;
//			ConceptoFacadeRemote conceptoFacade= null;
			CuentaFacadeRemote cuentaFacade = null;
			Integer intIdPersona = new Integer(0);
//			Boolean blnTodoOK = Boolean.FALSE;
			
			try {
//				conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
				cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
				// recuperamos las cuenta integrante de la cuenta del Socio...
				
				lstCuentaIntegrante = cuentaFacade.getListaCuentaIntegrantePorPKCuenta(socioComp.getCuenta().getId());
				
				
				if(lstCuentaIntegrante != null && !lstCuentaIntegrante.isEmpty()){
					for (CuentaIntegrante cuentaIntegrante : lstCuentaIntegrante) {
						if(cuentaIntegrante.getIntParaTipoIntegranteCod().compareTo(new Integer(1))==0){
							intIdPersona = cuentaIntegrante.getId().getIntPersonaIntegrante();
							break;
						}
					}
				}

				if(listaCuentasConceptoComp != null && !listaCuentasConceptoComp.isEmpty()){
					listaMovimientos = new ArrayList<Movimiento>();
					
					// Identificamos y definimos Aportes, Retiro e Interes
					BigDecimal bdMontoAbonoCtaXPagar = BigDecimal.ZERO;
					for (CuentaConceptoComp cuentaConceptoComp : listaCuentasConceptoComp) {

						if(cuentaConceptoComp.getIntParaTipoCaptacionModelo().compareTo(Constante.PARAM_T_CUENTACONCEPTO_APORTES)==0){
							// a. es aporte
							Movimiento movAporte = new Movimiento();
							movAporte.setTsFechaMovimiento(new Timestamp(new Date().getTime()));
							movAporte.setIntCuenta(socioComp.getCuenta().getId().getIntCuenta());
							movAporte.setIntPersEmpresa(socioComp.getCuenta().getId().getIntPersEmpresaPk());					// persona
							movAporte.setIntPersPersonaIntegrante(intIdPersona);// persona
							movAporte.setIntItemCuentaConcepto(Constante.PARAM_T_CUENTACONCEPTO_APORTES);
							movAporte.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_APORTACION);
							movAporte.setIntParaTipoMovimiento(Constante.PARAM_T_TIPOMOVIMIENTO_TRANSFERENCIA);
							movAporte.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACION_CUENTA);
							movAporte.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_CARGO);
							movAporte.setBdMontoMovimiento(cuentaConceptoComp.getCuentaConcepto().getBdSaldo());
							movAporte.setBdMontoSaldo(BigDecimal.ZERO);
							movAporte.setIntPersEmpresaUsuario(usuario.getEmpresa().getIntIdEmpresa()); // usuario
							movAporte.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk()); // usuario
							bdMontoAbonoCtaXPagar = bdMontoAbonoCtaXPagar.add(cuentaConceptoComp.getCuentaConcepto().getBdSaldo());
							listaMovimientos.add(movAporte);
							
						}else if(cuentaConceptoComp.getIntParaTipoCaptacionModelo().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0){
							
								if(cuentaConceptoComp.getIntParaConceptoGeneralModelo().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO)==0){
									// b. es retiro
									Movimiento movRetiro = new Movimiento();
									movRetiro.setTsFechaMovimiento(new Timestamp(new Date().getTime()));
									movRetiro.setIntCuenta(socioComp.getCuenta().getId().getIntCuenta());
									movRetiro.setIntPersEmpresa(socioComp.getCuenta().getId().getIntPersEmpresaPk());					// persona
									movRetiro.setIntPersPersonaIntegrante(intIdPersona);// persona
									movRetiro.setIntItemCuentaConcepto(Constante.PARAM_T_CUENTACONCEPTO_RETIRO);
									movRetiro.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO);
									movRetiro.setIntParaTipoMovimiento(Constante.PARAM_T_TIPOMOVIMIENTO_TRANSFERENCIA);
									movRetiro.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACION_CUENTA);
									movRetiro.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_CARGO);
									movRetiro.setBdMontoMovimiento(cuentaConceptoComp.getCuentaConcepto().getBdSaldo());
									movRetiro.setBdMontoSaldo(BigDecimal.ZERO);
									movRetiro.setIntPersEmpresaUsuario(usuario.getEmpresa().getIntIdEmpresa()); // usuario
									movRetiro.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk()); // usuario
									bdMontoAbonoCtaXPagar = bdMontoAbonoCtaXPagar.add(cuentaConceptoComp.getCuentaConcepto().getBdSaldo());
									listaMovimientos.add(movRetiro);
									
								}else if(cuentaConceptoComp.getIntParaConceptoGeneralModelo().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO_INTERES)==0){
										// c. es interes										
										Movimiento movInteres= new Movimiento();
										movInteres.setTsFechaMovimiento(new Timestamp(new Date().getTime()));
										movInteres.setIntCuenta(socioComp.getCuenta().getId().getIntCuenta());
										movInteres.setIntPersEmpresa(socioComp.getCuenta().getId().getIntPersEmpresaPk());					// persona
										movInteres.setIntPersPersonaIntegrante(intIdPersona);// persona
										movInteres.setIntItemCuentaConcepto(Constante.PARAM_T_CUENTACONCEPTO_RETIRO);
										movInteres.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO_INTERES);
										movInteres.setIntParaTipoMovimiento(Constante.PARAM_T_TIPOMOVIMIENTO_TRANSFERENCIA);
										movInteres.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACION_CUENTA);
										movInteres.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_CARGO);
										movInteres.setBdMontoMovimiento(cuentaConceptoComp.getCuentaConcepto().getBdSaldo());
										movInteres.setBdMontoSaldo(BigDecimal.ZERO);
										movInteres.setIntPersEmpresaUsuario(usuario.getEmpresa().getIntIdEmpresa()); // usuario
										movInteres.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk()); // usuario
										bdMontoAbonoCtaXPagar = bdMontoAbonoCtaXPagar.add(cuentaConceptoComp.getCuentaConcepto().getBdSaldo());
										listaMovimientos.add(movInteres);
									}
						}	
					}

					// se le agrega el movimeinto de abono cta x pagar...
					Movimiento movAbonoXPagar= new Movimiento();
					movAbonoXPagar.setTsFechaMovimiento(new Timestamp(new Date().getTime()));
					movAbonoXPagar.setIntCuenta(socioComp.getCuenta().getId().getIntCuenta());
					movAbonoXPagar.setIntPersEmpresa(socioComp.getCuenta().getId().getIntPersEmpresaPk());					// persona
					movAbonoXPagar.setIntPersPersonaIntegrante(intIdPersona);// persona
					movAbonoXPagar.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_CTAXPAGAR);
					movAbonoXPagar.setIntParaTipoMovimiento(Constante.PARAM_T_TIPOMOVIMIENTO_TRANSFERENCIA);
					movAbonoXPagar.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACION_CUENTA);
					movAbonoXPagar.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
					movAbonoXPagar.setBdMontoMovimiento(bdMontoAbonoCtaXPagar);
					movAbonoXPagar.setBdMontoSaldo(bdMontoAbonoCtaXPagar);
					movAbonoXPagar.setIntPersEmpresaUsuario(usuario.getEmpresa().getIntIdEmpresa()); // usuario
					movAbonoXPagar.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk()); // usuario
					movAbonoXPagar.setIntItemExpediente(expedienteLiquidacion.getId().getIntItemExpediente()!=null?expedienteLiquidacion.getId().getIntItemExpediente():null);
					listaMovimientos.add(movAbonoXPagar);
					
					
					for (Movimiento movimientos : listaMovimientos) {
						System.out.println("*********************************************************************");
						System.out.println("getIntItemMovimiento ---> "+movimientos.getIntItemMovimiento());
						System.out.println("getTsFechaMovimiento ---> "+movimientos.getTsFechaMovimiento());
						System.out.println("getIntPersEmpresa ---> "+movimientos.getIntPersEmpresa());
						System.out.println("getIntPersPersonaIntegrante ---> "+movimientos.getIntPersPersonaIntegrante());
						System.out.println("getIntItemCuentaConcepto ---> "+movimientos.getIntItemCuentaConcepto());
						
						System.out.println("getIntParaTipoConceptoGeneral ---> "+movimientos.getIntParaTipoConceptoGeneral());
						System.out.println("getIntParaTipoMovimiento ---> "+movimientos.getIntParaTipoMovimiento());
						System.out.println("getIntParaDocumentoGeneral ---> "+movimientos.getIntParaDocumentoGeneral());
						
						System.out.println("getIntParaTipoCargoAbono ---> "+movimientos.getIntParaTipoCargoAbono());
						
						System.out.println("getBdMontoMovimiento ---> "+movimientos.getBdMontoMovimiento());
						System.out.println("getBdMontoSaldo ---> "+movimientos.getBdMontoSaldo());
						System.out.println("getIntPersEmpresaUsuario ---> "+movimientos.getIntPersEmpresaUsuario());
						System.out.println("getIntPersPersonaUsuario ---> "+movimientos.getIntPersPersonaUsuario());

					}
					System.out.println("*********************************************************************");

					listaMovimientos = grabarYActualizarMovimiento_CuentaConcepto_Detalle_5(listaMovimientos, listaCuentasConceptoComp, socioComp);

				}
				
			} catch (Exception e) {
//				blnTodoOK = Boolean.FALSE;
				throw new BusinessException("Error en generarRegistrosMovimientoLiquidacionCuentas_4 --->" +e);
			}
			
			return listaMovimientos; 
			
		}
		
		
		/**
		 * 
		 * @param listaMovimientos
		 * @param listaCuentasConceptoComp
		 * @throws BusinessException
		 */
		private List<Movimiento> grabarYActualizarMovimiento_CuentaConcepto_Detalle_5( List<Movimiento> listaMovimientos,List<CuentaConceptoComp> listaCuentasConceptoComp, SocioComp socioComp)throws BusinessException {
			ConceptoFacadeRemote conceptoFacadeRemote= null;
//			CuentaFacadeRemote cuentaFacade = null;
			List<Movimiento> lstMovimientoResult = null;
//			Boolean blnOk= Boolean.FALSE;
			List<CuentaConcepto> listaCuentaConcepto = null;
			List<CuentaConceptoDetalle> listaCtaConceptoDet= null;
			try {
				conceptoFacadeRemote = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
//				cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
				
				if(listaMovimientos != null && !listaMovimientos.isEmpty()){
					
					lstMovimientoResult = new ArrayList<Movimiento>();
					
					for (Movimiento movimiento : listaMovimientos) {
						movimiento = conceptoFacadeRemote.grabarMovimiento(movimiento);
						lstMovimientoResult.add(movimiento);						
					}
					
					//actualizarFechaFinCuentaConceptoDetalle(socioComp);
					listaCuentaConcepto = conceptoFacadeRemote.getListaCuentaConceptoPorPkCuenta(socioComp.getCuenta().getId());
					
					
					 if(listaCuentaConcepto != null && !listaCuentaConcepto.isEmpty()){

						 for (CuentaConcepto cuentaCto : listaCuentaConcepto) {
							 cuentaCto.setBdSaldo(BigDecimal.ZERO);
							 conceptoFacadeRemote.modificarCuentaConcepto(cuentaCto);
							 
							 if(cuentaCto.getListaCuentaConceptoDetalle() != null
									    && !cuentaCto.getListaCuentaConceptoDetalle().isEmpty()){
										 
										 listaCtaConceptoDet = new ArrayList<CuentaConceptoDetalle>();
										 listaCtaConceptoDet = cuentaCto.getListaCuentaConceptoDetalle();
										 for (CuentaConceptoDetalle detalle : listaCtaConceptoDet) {
											 if(detalle.getTsFin()== null){
												 detalle.setBdSaldoDetalle(BigDecimal.ZERO);
												 detalle.setTsFin(new Timestamp(new Date().getTime()));
												 conceptoFacadeRemote.modificarCuentaConceptoDetalle(detalle);
											 }
										} 
									 } 
						}
					 }
				}

			} catch (Exception e) {
				log.error("Error en grabarYActualizarMovimiento_CuentaConcepto_Detalle_5 ---> "+e);
			}
			return lstMovimientoResult;
		}
		
		
		/**
		 * Recupera los expedientes de liquyidacion en base a la cuenta de los detalles.
		 * Ademas se carga con el ultimo estado y la lista completa de sus estados.
		 * @param intEmpresa
		 * @param intCuenta
		 * @return
		 * @throws BusinessException
		 */
		public List<ExpedienteLiquidacion> getListaPorEmpresaYCuenta(Integer intEmpresa, Integer intCuenta)throws BusinessException{
			 List<ExpedienteLiquidacion> lstLiquidacion = null;
			 List<ExpedienteLiquidacion> lstLiquidacionCargada = null;
			 
			try {
				lstLiquidacion= boExpedienteLiquidacion.getListaPorEmpresaYCuenta(intEmpresa, intCuenta);
				if(lstLiquidacion != null && !lstLiquidacion.isEmpty()){
					lstLiquidacionCargada = new ArrayList<ExpedienteLiquidacion>();
					
					for (ExpedienteLiquidacion expedienteLiquidacion : lstLiquidacion) {
						EstadoLiquidacion ultimoEstado = null;
						List<EstadoLiquidacion> lstEstados = null;
						expedienteLiquidacion.setListaEstadoLiquidacion(new ArrayList<EstadoLiquidacion>());
						
						ultimoEstado = boEstadoLiquidacion.getMaxEstadoLiquidacionPorPkExpediente(expedienteLiquidacion);
						if(ultimoEstado != null){
							expedienteLiquidacion.setEstadoLiquidacionUltimo(ultimoEstado);
						}
						
						lstEstados= boEstadoLiquidacion.getPorExpediente(expedienteLiquidacion);
						if(lstEstados != null && !lstEstados.isEmpty()){
							expedienteLiquidacion.setListaEstadoLiquidacion(lstEstados);	
						}
						lstLiquidacionCargada.add(expedienteLiquidacion);
					}
					
				}
			} catch (Exception e) {
				log.error("Error en getListaPorEmpresaYCuenta ---> "+e);
			}
			return lstLiquidacionCargada ;
		}
		
		
		/**
		 * Recupera los expedientes de liquidacion segun filtros de busqeda
		 * @param expCmp
		 * @return
		 * @throws BusinessException
		 */
		public List<ExpedienteLiquidacionComp> getListaBusqExpLiqFiltros(ExpedienteLiquidacionComp expCmp) throws BusinessException{
			List<ExpedienteLiquidacionComp> lstLiquidacionComp= null;
			List<ExpedienteLiquidacion> lstLiquidacion = null;
			try {
				lstLiquidacion = boExpedienteLiquidacion.getListaBusqExpLiqFiltros(expCmp);
				if(lstLiquidacion != null && !lstLiquidacion.isEmpty()){
					lstLiquidacionComp = completarDatosExpLiquidacion_BusqFiltros(lstLiquidacion);
				}
				
			} catch (Exception e) {
				log.error("Error en getListaBusqExpLiqFiltros ---> "+e);
			}
			return lstLiquidacionComp;
		}
		
		
		public List<ExpedienteLiquidacionComp> completarDatosExpLiquidacion_BusqFiltros(List<ExpedienteLiquidacion> lstLiquidacion){
			List<ExpedienteLiquidacionComp> lstLiqComp = null;
			try {
				
				// Estados Req, Sol y Aut
				if(lstLiquidacion != null && !lstLiquidacion.isEmpty()){
					lstLiqComp = new ArrayList<ExpedienteLiquidacionComp>();
					
					for (ExpedienteLiquidacion expedienteLiquidacion : lstLiquidacion) {
						List<EstadoLiquidacion> listaEstadoLiq = null;

						ExpedienteLiquidacionComp expLiquidacionComp = new ExpedienteLiquidacionComp();
						expLiquidacionComp.setExpedienteLiquidacion(expedienteLiquidacion);
						
						// Estados
						listaEstadoLiq = boEstadoLiquidacion.getPorExpediente(expedienteLiquidacion);
						if(listaEstadoLiq!=null && !listaEstadoLiq.isEmpty()){
							for (EstadoLiquidacion estado : listaEstadoLiq) {
								if(estado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO))
									expLiquidacionComp.setStrFechaRequisito(Constante.sdf.format(estado.getTsFechaEstado()));
								if(estado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD))
									expLiquidacionComp.setStrFechaSolicitud(Constante.sdf.format(estado.getTsFechaEstado()));
								if(estado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)){
									expLiquidacionComp.setStrFechaAutorizacion(Constante.sdf.format(estado.getTsFechaEstado()));
								}
								
								
							}
						}

						lstLiqComp.add(expLiquidacionComp);
					}
				}

			} catch (Exception e) {
				log.error("Error en completarDatosExpLiquidacion_BusqFiltros ---> "+e);
			}
			return lstLiqComp;
		}
		
		
		/**
		 * Recupera los expedientes de liquidacion segun filtros de busqeda
		 * @param expCmp
		 * @return
		 * @throws BusinessException
		 */
		public List<ExpedienteLiquidacionComp> getListaBusqAutLiqFiltros(ExpedienteLiquidacionComp expCmp) throws BusinessException{
			List<ExpedienteLiquidacionComp> lstLiquidacionComp= null;
			List<ExpedienteLiquidacion> lstLiquidacion = null;
			try {
				lstLiquidacion = boExpedienteLiquidacion.getListaBusqAutLiqFiltros(expCmp);
				if(lstLiquidacion != null && !lstLiquidacion.isEmpty()){
					lstLiquidacionComp = completarDatosExpLiquidacion_BusqFiltros(lstLiquidacion);
				}
				
			} catch (Exception e) {
				log.error("Error en getListaBusqExpLiqFiltros ---> "+e);
			}
			return lstLiquidacionComp;
		}
		
	/**
	 * Cambia los estados a los autorizaCredito, Archivos Infocorp, reniec, AutorizaVerfifica y
	 * a los requiisitos de solicitud asociados al expediente.
	 * Se aplicara cuando el expediente pase a aestado Observado.
	 * @param pExpedienteCredito
	 * @return
	 * @throws BusinessException
	 */
	public void eliminarVerificaAutorizacionAdjuntosPorObservacion(ExpedienteLiquidacion pExpedienteLiquidacion) throws BusinessException{
		List<AutorizaLiquidacion> listaAutorizaLiquidacion = null;
		List<AutorizaVerificaLiquidacion> listaAutorizaVerificacion = null;
		List<AutorizaLiquidacion> listaAutorizaLiquidacionModif = null;
		List<AutorizaVerificaLiquidacion> listaAutorizaVerificacionModif = null;
		List<RequisitoLiquidacion> listaRequisitosSolicitud = null;
		List<RequisitoLiquidacionComp> listRequisitosComp = null;
		GeneralFacadeRemote generalFacade = null;
//			PrevisionFacadeRemote previsionFacade = null;
		AutorizacionLiquidacionFacadeRemote autorizacionLiquidacionFacade = null;
		SocioComp socioComp = null;
		List<RequisitoLiquidacionComp> listaNuevosRequisitosSolicitud = null;
		
		try {
			generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
//				previsionFacade = (PrevisionFacadeRemote)EJBFactory.getRemote(PrevisionFacadeRemote.class);
			autorizacionLiquidacionFacade = (AutorizacionLiquidacionFacadeRemote)EJBFactory.getRemote(AutorizacionLiquidacionFacadeRemote.class);
			// RECUPERAR TODOS LOS AUTORIZAR PREVISION

			listaAutorizaLiquidacion = autorizacionLiquidacionFacade.getListaAutorizaLiquidacionPorPkExpediente(pExpedienteLiquidacion.getId());

			if(listaAutorizaLiquidacion!=null && !listaAutorizaLiquidacion.isEmpty()){
				listaAutorizaLiquidacionModif = new ArrayList<AutorizaLiquidacion>();
				for (AutorizaLiquidacion autorizaLiquidacion : listaAutorizaLiquidacion) {
					autorizaLiquidacion.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					listaAutorizaLiquidacionModif.add(autorizaLiquidacion);	
				}
				grabarListaDinamicaAutorizaLiquidacion(listaAutorizaLiquidacionModif, pExpedienteLiquidacion.getId());
			}
			
			listaAutorizaVerificacion = pExpedienteLiquidacion.getListaAutorizaVerificaLiquidacion();
			if(listaAutorizaVerificacion!=null && !listaAutorizaVerificacion.isEmpty()){
				listaAutorizaVerificacionModif = new ArrayList<AutorizaVerificaLiquidacion>();
				for (AutorizaVerificaLiquidacion autorizaVerificacion : listaAutorizaVerificacion) {
						autorizaVerificacion.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
						autorizaVerificacion.setTsFechaRegistro((new Timestamp(new Date().getTime())));
						listaAutorizaVerificacionModif.add(autorizaVerificacion);				
					}
				
				
				grabarListaDinamicaAutorizaVerificacionLiquidacion(listaAutorizaVerificacionModif, pExpedienteLiquidacion.getId());
			}
			
			listaRequisitosSolicitud = boRequisitoLiquidacion.getListaPorExpediente(pExpedienteLiquidacion.getId());
			
			if(listaRequisitosSolicitud != null && !listaRequisitosSolicitud.isEmpty()){
				for (RequisitoLiquidacion requisitoLiquidacion : listaRequisitosSolicitud) {
					requisitoLiquidacion.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					boRequisitoLiquidacion.modificar(requisitoLiquidacion);	
				}
				
				// recuperar los requisiitoscomp del expediente y   generalfacade.eliminarArchivo
				listRequisitosComp = recuperarRequisitoCompExpediente(pExpedienteLiquidacion);
				if(listRequisitosComp!= null && !listRequisitosComp.isEmpty()){
					for (RequisitoLiquidacionComp requisitoLiquidacionComp : listRequisitosComp) {
						Archivo archivo = new Archivo();
						archivo = requisitoLiquidacionComp.getArchivoAdjunto();
						generalFacade.eliminarArchivo(archivo);
					}
					
				}
				
				socioComp = recuperarSocioCompXCuenta(pExpedienteLiquidacion);
				if(socioComp != null){
					
					listaNuevosRequisitosSolicitud = recuperarArchivosAdjuntos(pExpedienteLiquidacion, socioComp);
					if(listaNuevosRequisitosSolicitud!= null){
						grabarListaDinamicaRequisitoLiquidacion(listaNuevosRequisitosSolicitud, pExpedienteLiquidacion.getId());
					}
					
				}

			}

			
		} catch(BusinessException e){
			log.error("Error - BusinessException - en eliminarVerificaAutorizacionAdjuntosPorObservacion ---> "+e);
			throw e;
		} catch(Exception e){
			log.error("Error - Exception - en eliminarVerificaAutorizacionAdjuntosPorObservacion ---> "+e);
			throw new BusinessException(e);
		}
	}
		

	/**
	 * 
	 * @param expedienteLiquidacion
	 * @return
	 * @throws BusinessException
	 */
	public List<RequisitoLiquidacionComp> recuperarRequisitoCompExpediente(ExpedienteLiquidacion expedienteLiquidacion)throws BusinessException{
		List<RequisitoLiquidacionComp> listaRequisitoLiquidacionComp = null;
		List<RequisitoLiquidacion> listaRequisitoLiquidacion = null;
		RequisitoLiquidacionComp requisitoLiquidacionComp = null;
		ConfServDetalle detalle= null;
		Archivo archivo = null;
		TipoArchivo tipoArchivo = null;
		MyFile myFile = null;
		ConfSolicitudFacadeLocal solicitudFacade = null;
		GeneralFacadeRemote generalFacade = null;
		
		try {
			solicitudFacade = (ConfSolicitudFacadeLocal)EJBFactory.getLocal(ConfSolicitudFacadeLocal.class);
			generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			
			listaRequisitoLiquidacion = boRequisitoLiquidacion.getListaPorExpediente(expedienteLiquidacion.getId());
			if(listaRequisitoLiquidacion!=null && !listaRequisitoLiquidacion.isEmpty()){
				listaRequisitoLiquidacionComp = new ArrayList<RequisitoLiquidacionComp>();
				for(RequisitoLiquidacion requisitoLiquidacion : listaRequisitoLiquidacion){
					if(requisitoLiquidacion.getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
						requisitoLiquidacionComp = new RequisitoLiquidacionComp();
						detalle = new ConfServDetalle();
						detalle.setId(new ConfServDetalleId());
						archivo = new Archivo();
						archivo.setId(new ArchivoId());
						tipoArchivo = new TipoArchivo();
						myFile = new MyFile();
						requisitoLiquidacionComp.setRequisitoLiquidacion(requisitoLiquidacion);
						detalle.getId().setIntPersEmpresaPk(requisitoLiquidacion.getIntPersEmpresaPk());
						detalle.getId().setIntItemSolicitud(requisitoLiquidacion.getIntItemReqAut());
						detalle.getId().setIntItemDetalle(requisitoLiquidacion.getIntItemReqAutEstDetalle());
						detalle = solicitudFacade.getConfServDetallePorPk(detalle.getId());
						requisitoLiquidacionComp.setDetalle(detalle);
						archivo.getId().setIntParaTipoCod(requisitoLiquidacion.getIntParaTipoArchivo());
						archivo.getId().setIntItemArchivo(requisitoLiquidacion.getIntParaItemArchivo());
						archivo.getId().setIntItemHistorico(requisitoLiquidacion.getIntParaItemHistorico());
						
						archivo = generalFacade.getArchivoPorPK(archivo.getId());
						requisitoLiquidacionComp.setArchivoAdjunto(archivo);
						
						if(archivo !=null && archivo.getFile() != null){
							tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoLiquidacion.getIntParaTipoArchivo());
							if(tipoArchivo!=null){								
								if(archivo.getId().getIntParaTipoCod()!= null && archivo.getId().getIntItemArchivo()!= null && 
										archivo.getId().getIntItemHistorico() != null/* && tipoArchivo.getStrRuta() != null && archivo.getStrNombrearchivo()!= null*/){
									
									byte[] byteImg = null;
									
									try {
										byteImg = FileUtil.getDataImage(tipoArchivo.getStrRuta()+ "\\" + archivo.getStrNombrearchivo());
										
									} catch (IOException e) {
										e.printStackTrace();
									}
									if(byteImg != null && byteImg.length != 0){
										myFile.setLength(byteImg.length);
										myFile.setName(archivo.getStrNombrearchivo());
										myFile.setData(byteImg);
										archivo.setRutaActual(tipoArchivo.getStrRuta());
										archivo.setStrNombrearchivo(archivo.getStrNombrearchivo());
										requisitoLiquidacionComp.setFileDocAdjunto(myFile);
										requisitoLiquidacionComp.setArchivoAdjunto(archivo);
									}
									
								}
	
							}
						}
						listaRequisitoLiquidacionComp.add(requisitoLiquidacionComp);
					}
				}
			}
		} catch (Exception e) {
			log.error("Error en recuperarRequisitoCompExpediente ---> "+e);
		}
		return listaRequisitoLiquidacionComp;
	}
	
	/**
	 * Se recupera el sociocomp en base al nro de cuenta
	 * @param expedienteCredito
	 * @return
	 */
	public SocioComp recuperarSocioCompXCuenta(ExpedienteLiquidacion expedienteLiquidacion)throws BusinessException{
		
		CuentaId cuentaId = null;
		Integer intIdPersona = 0;
		CuentaFacadeRemote cuentaFacade = null;
		PersonaFacadeRemote personaFacade = null;
		SocioFacadeRemote socioFacade = null;
		Persona persona = null;
		SocioComp socioComp = null;
		try{
			cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			
			 cuentaId = new CuentaId();
						List<CuentaIntegrante> lstCuentaIntegrante = null;
						//se obtiene  de la cuenta
						cuentaId.setIntCuenta(expedienteLiquidacion.getListaExpedienteLiquidacionDetalle().get(0).getId().getIntCuenta());
						cuentaId.setIntPersEmpresaPk(expedienteLiquidacion.getId().getIntPersEmpresaPk());

						// recuperamos el socio comp en base a la cuenta...
						lstCuentaIntegrante = cuentaFacade.getListaCuentaIntegrantePorPKCuenta(cuentaId);
						if(lstCuentaIntegrante != null && !lstCuentaIntegrante.isEmpty()){
							
							for (CuentaIntegrante cuentaIntegrante : lstCuentaIntegrante) {
								if(cuentaIntegrante.getIntParaTipoIntegranteCod().compareTo(new Integer(1))==0){
									intIdPersona = cuentaIntegrante.getId().getIntPersonaIntegrante();
									break;
								}
							}
							personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
							persona = personaFacade.getPersonaNaturalPorIdPersona(intIdPersona);
							if(persona!=null){
								if(persona.getListaDocumento()!=null && persona.getListaDocumento().size()>0){
									for (Documento documento : persona.getListaDocumento()) {
										if(documento.getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))){
											persona.setDocumento(documento);
											break;
										}
									}
								}
								socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI), persona.getDocumento().getStrNumeroIdentidad(), Constante.PARAM_EMPRESASESION);

								if (socioComp != null && socioComp.getPersona()!= null) {
								// 1. Validamos el estado de la persona (fallecido , activo)
									//if(socioComp.getPersona().getIntEstadoCod().compareTo(Constante.PARAM_PERSONA_ESTADO_ACTIVO)==0){
										//strMsgErrorValidarDatos = "";
										if (socioComp.getCuenta() != null) {
											
										// 2. Validamos la situacion de la cuenta - vigente
											if(socioComp.getCuenta().getIntParaSituacionCuentaCod().compareTo(Constante.PARAM_T_SITUACIONCUENTA_VIGENTE)==0){
												
												//Setemos el socioestructura.
												for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
													if(socioEstructura.getIntEstadoCod().intValue() == Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO.intValue()){
														if (socioEstructura.getIntTipoEstructura().intValue() == Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN.intValue()) {
															socioComp.getSocio().setSocioEstructura(socioEstructura);
															break;
														}
													}
												}

												
											}	

										}

									//}
								}
	
							}
							
						}
		
		} catch (Exception e1) {
			log.error("Error ParseException enrecuperar SocioCOmp ---> "+e1);
		}
		
		return socioComp;
	
	}

	/**
	 * Muestra dinamicamente los ventanas para adjuntar documentos de Solicitud
	 * de Credito. En base a la cofiguracion del credito.
	 * 
	 * @param event
	 * @throws ParseException 
	 */
	public List<RequisitoLiquidacionComp> recuperarArchivosAdjuntos(ExpedienteLiquidacion expedienteLiquidacion, SocioComp beanSocioComp) throws ParseException {
		log.info("-----------------------Debugging SolicitudLiquidacionController.mostrarArchivosAdjuntos-----------------------------");
		ConfSolicitudFacadeRemote facade = null;
		TablaFacadeRemote tablaFacade = null;
		EstructuraFacadeRemote estructuraFacade = null;
		ConfServSolicitud confServSolicitud = null;
//		String strToday = Constante.sdf.format(new Date());
//		Date dtToday = null;
		List<ConfServSolicitud> listaDocAdjuntos = new ArrayList<ConfServSolicitud>();
		EstructuraDetalle estructuraDet = null;
		List<EstructuraDetalle> listaEstructuraDet = new ArrayList<EstructuraDetalle>();
//		List<RequisitoCreditoComp> listaRequisitoCreditoComp = null;
//		RequisitoCreditoComp requisitoCreditoComp;
		Integer intTIpoOperacion =  0;
		Integer intReqDesc =  0;
		RequisitoLiquidacionComp requisitoLiquidacionComp;
		List<RequisitoLiquidacionComp> listaRequisitoLiquidacionComp = new ArrayList<RequisitoLiquidacionComp>();
		try {
//			dtToday = Constante.sdf.parse(strToday);
		
			facade = (ConfSolicitudFacadeRemote) EJBFactory.getRemote(ConfSolicitudFacadeRemote.class);
			estructuraFacade = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			confServSolicitud = new ConfServSolicitud();

			intTIpoOperacion = Constante.PARAM_T_TIPOOPERACION_LIQUIDACIONDECUENTA;
			intReqDesc = new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_LIQUIDACION);
			
			confServSolicitud.setIntParaTipoOperacionCod(intTIpoOperacion);
			confServSolicitud.setIntParaSubtipoOperacionCod(expedienteLiquidacion.getIntParaSubTipoOperacion());
			confServSolicitud.setIntParaTipoRequertoAutorizaCod(Constante.PARAM_T_TIPOREQAUT_REQUISITO);

			listaDocAdjuntos = facade.buscarConfSolicitudRequisitoOptimizado(confServSolicitud, Constante.PARAM_T_TIPOREQAUT_REQUISITO, null);
			
			if (listaDocAdjuntos != null && listaDocAdjuntos.size() > 0) {
				forSolicitud: for (ConfServSolicitud solicitud : listaDocAdjuntos) {
					if(solicitud.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
						if (solicitud.getIntParaTipoOperacionCod().compareTo(intTIpoOperacion)==0) {
							if (solicitud.getIntParaSubtipoOperacionCod().equals(expedienteLiquidacion.getIntParaSubTipoOperacion())) {
								if (solicitud.getListaEstructuraDetalle() != null) {
									
									for (ConfServEstructuraDetalle estructuraDetalle : solicitud.getListaEstructuraDetalle()) {
										estructuraDet = new EstructuraDetalle();
										estructuraDet.setId(new EstructuraDetalleId());
										estructuraDet.getId().setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
										estructuraDet.getId().setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
										
										
										listaEstructuraDet = estructuraFacade.getListaEstructuraDetallePorEstructuraYTipoSocioYTipoModalidad(
														estructuraDet.getId(),beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio(),
														beanSocioComp.getSocio().getSocioEstructura().getIntModalidad());										
										
										if (listaEstructuraDet != null && listaEstructuraDet.size() > 0) {
											for (EstructuraDetalle estructDetalle : listaEstructuraDet) {
												if(estructDetalle.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
													if (estructuraDetalle.getIntCodigoPk().equals(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo())
														&& estructuraDetalle.getIntNivelPk().equals(beanSocioComp.getSocio().getSocioEstructura().getIntNivel())
														&& estructuraDetalle.getIntCaso().equals(estructDetalle.getId().getIntCaso())
														&& estructuraDetalle.getIntItemCaso().equals(estructDetalle.getId().getIntItemCaso())) {
														
															if (solicitud.getListaDetalle() != null && solicitud.getListaDetalle().size() > 0) {
																
																List<RequisitoLiquidacionComp> listaRequisitoLiquidacionCompTemp = new ArrayList<RequisitoLiquidacionComp>();
																for (ConfServDetalle detalle : solicitud.getListaDetalle()) {
																	if(detalle.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
																		if (detalle.getId().getIntPersEmpresaPk().equals(estructuraDetalle.getId().getIntPersEmpresaPk())
																				&& detalle.getId().getIntItemSolicitud().equals(estructuraDetalle.getId().getIntItemSolicitud())) {
																				
																				requisitoLiquidacionComp = new RequisitoLiquidacionComp();
																				requisitoLiquidacionComp.setDetalle(detalle);
																				listaRequisitoLiquidacionCompTemp.add(requisitoLiquidacionComp);
																			}
																	}
																	
																}
																													
																List<Tabla> listaTablaRequisitos = new ArrayList<Tabla>();

																//if(intTipoSolicitud.equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)){
																	// validamos que solo se muestre las de agrupamioento A.
																	listaTablaRequisitos = tablaFacade.getListaTablaPorAgrupamientoA(intReqDesc,"A");
																		for(int i=0;i<listaTablaRequisitos.size();i++){	
																			for(int j=0 ; j<listaRequisitoLiquidacionCompTemp.size();j++){
																 				if((listaRequisitoLiquidacionCompTemp.get(j).getDetalle().getIntParaTipoDescripcion().intValue()) ==
																					(listaTablaRequisitos.get(i).getIntIdDetalle().intValue())){
																					listaRequisitoLiquidacionComp.add(listaRequisitoLiquidacionCompTemp.get(j));
																				}
																			}
																		}

																		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
																		for (RequisitoLiquidacionComp requLiquidacionComp : listaRequisitoLiquidacionComp) {
																			System.out.println("requPrevComp.getDetalle().getId().getIntItemSolicitud()---> "+requLiquidacionComp.getDetalle().getId().getIntItemSolicitud());
																			System.out.println("requPrevComp.getDetalle().getId().getIntItemDetalle() ---> "+requLiquidacionComp.getDetalle().getId().getIntItemDetalle());
																			System.out.println("requPrevComp.getDetalle().getIntParaTipoDescripcion() ---> "+requLiquidacionComp.getDetalle().getIntParaTipoDescripcion());
																			System.out.println("requPrevComp.getDetalle().getIntParaTipoPersonaOperacionCod() ---> "+requLiquidacionComp.getDetalle().getIntParaTipoPersonaOperacionCod());
																			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
																		}
																break forSolicitud;
																
															}
														}
													}

												}
	
										}
									}
								}
							}
						}

					}


				}

			}
		} catch (BusinessException e2) {
			log.error("Error BusinessException en mostrarArchivosAdjuntos ---> "+e2);
			e2.printStackTrace();
		} catch (EJBFactoryException e3) {
			log.error("Error EJBFactoryException en mostrarArchivosAdjuntos ---> "+e3);
			e3.printStackTrace();
			}
		
		return listaRequisitoLiquidacionComp;
	}
}
