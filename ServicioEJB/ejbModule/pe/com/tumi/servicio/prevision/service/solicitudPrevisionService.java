package pe.com.tumi.servicio.prevision.service;

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
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalleId;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalleId;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoId;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;
import pe.com.tumi.movimiento.concepto.domain.composite.CuentaConceptoComp;
//import pe.com.tumi.movimiento.concepto.facade.ConceptoFacade;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.TipoArchivo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalleId;
import pe.com.tumi.servicio.configuracion.domain.ConfServEstructuraDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServSolicitud;
import pe.com.tumi.servicio.configuracion.facade.ConfSolicitudFacadeLocal;
import pe.com.tumi.servicio.configuracion.facade.ConfSolicitudFacadeRemote;
import pe.com.tumi.servicio.prevision.bo.AutorizaPrevisionBO;
import pe.com.tumi.servicio.prevision.bo.AutorizaVerificaPrevisionBO;
import pe.com.tumi.servicio.prevision.bo.BeneficiarioPrevisionBO;
import pe.com.tumi.servicio.prevision.bo.EstadoPrevisionBO;
import pe.com.tumi.servicio.prevision.bo.ExpedientePrevisionBO;
import pe.com.tumi.servicio.prevision.bo.FallecidoPrevisionBO;
import pe.com.tumi.servicio.prevision.bo.RequisitoPrevisionBO;
import pe.com.tumi.servicio.prevision.domain.AutorizaPrevision;
import pe.com.tumi.servicio.prevision.domain.AutorizaPrevisionId;
import pe.com.tumi.servicio.prevision.domain.AutorizaVerificaPrevision;
import pe.com.tumi.servicio.prevision.domain.AutorizaVerificaPrevisionId;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioPrevision;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioPrevisionId;
import pe.com.tumi.servicio.prevision.domain.EstadoPrevision;
import pe.com.tumi.servicio.prevision.domain.EstadoPrevisionId;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevision;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevisionId;
import pe.com.tumi.servicio.prevision.domain.FallecidoPrevision;
import pe.com.tumi.servicio.prevision.domain.FallecidoPrevisionId;
import pe.com.tumi.servicio.prevision.domain.RequisitoPrevision;
import pe.com.tumi.servicio.prevision.domain.RequisitoPrevisionId;
import pe.com.tumi.servicio.prevision.domain.composite.ExpedientePrevisionComp;
import pe.com.tumi.servicio.prevision.domain.composite.RequisitoPrevisionComp;
import pe.com.tumi.servicio.prevision.facade.AutorizacionPrevisionFacadeRemote;
import pe.com.tumi.servicio.prevision.facade.PrevisionFacadeRemote;
//import pe.com.tumi.servicio.solicitudPrestamo.facade.GarantiaCreditoFacadeRemote;

public class solicitudPrevisionService {
protected static Logger log = Logger.getLogger(solicitudPrevisionService.class);
	
	private ExpedientePrevisionBO boExpedientePrevision = (ExpedientePrevisionBO)TumiFactory.get(ExpedientePrevisionBO.class);
	private BeneficiarioPrevisionBO boBeneficiarioPrevision = (BeneficiarioPrevisionBO)TumiFactory.get(BeneficiarioPrevisionBO.class);
	private EstadoPrevisionBO boEstadoPrevision = (EstadoPrevisionBO)TumiFactory.get(EstadoPrevisionBO.class);
	private RequisitoPrevisionBO boRequisitoPrevision = (RequisitoPrevisionBO)TumiFactory.get(RequisitoPrevisionBO.class);
	private FallecidoPrevisionBO boFallecidoPrevision = (FallecidoPrevisionBO)TumiFactory.get(FallecidoPrevisionBO.class);
	private AutorizaPrevisionBO boAutorizaPrevision = (AutorizaPrevisionBO)TumiFactory.get(AutorizaPrevisionBO.class);
	private AutorizaVerificaPrevisionBO boAutorizaVerificaPrevision = (AutorizaVerificaPrevisionBO)TumiFactory.get(AutorizaVerificaPrevisionBO.class);
	
	
	public List<ExpedientePrevision> getListaExpedientePrevisionBusqueda() throws BusinessException{
//		ExpedientePrevision dto = null;
//		List<ExpedientePrevision> lista = null;
		List<ExpedientePrevision> listaExpedientePrevision = null;
//		EstadoPrevision estadoPrevision;
		
//		List<EstadoPrevision> listaEstadoPrevision = null;
		//List<CapacidadCredito> listaCapacidadCredito = null;
//		SocioComp socioComp = null;
//		Persona persona = null;
//		Integer intIdPersona = null;
//		PersonaFacadeRemote personaFacade = null;
//		SocioFacadeRemote socioFacade = null;
		
		try{
//			socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			listaExpedientePrevision = boExpedientePrevision.getListaExpedientePrevisionBusqueda();
			
			/*if(listaExpedientePrevision != null && listaExpedientePrevision.size()>0){
				lista = new ArrayList<ExpedientePrevision>();
				for(ExpedientePrevision expedientePrevision : listaExpedientePrevision){
					dto = new ExpedienteCreditoComp();
					estadoCredito = boEstadoCredito.getMaxEstadoCreditoPorPokExpedienteCredito(expedienteCredito.getId());
					dto.setEstadoCredito(estadoCredito);
					listaEstadoCredito = boEstadoCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
					if(listaEstadoCredito!=null){
						for (EstadoCredito $estadoCredito : listaEstadoCredito) {
							if($estadoCredito.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO))
							dto.setStrFechaRequisito(Constante.sdf2.format($estadoCredito.getTsFechaEstado()));
							if($estadoCredito.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD))
								dto.setStrFechaSolicitud(Constante.sdf2.format($estadoCredito.getTsFechaEstado()));
							if($estadoCredito.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO))
								dto.setStrFechaAutorizacion(Constante.sdf2.format($estadoCredito.getTsFechaEstado()));
						}
					}
					expedienteCredito.setListaEstadoCredito(listaEstadoCredito);
					listaCapacidadCredito = boCapacidadCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
					if(listaCapacidadCredito!=null){
						for (CapacidadCredito capacidadCredito : listaCapacidadCredito) {
							intIdPersona = capacidadCredito.getIntPersPersonaPk();
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
							dto.setSocioComp(socioComp);
						}
					}
					expedienteCredito.setListaCapacidadCredito(listaCapacidadCredito);
					dto.setExpedienteCredito(expedienteCredito);
					lista.add(dto);
				}
				//dto.setListaExpedienteCredito(listaExpedienteCredito);
			}*/
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		//return lista;
		return listaExpedientePrevision;
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
	
	
	public ExpedientePrevision grabarExpedientePrevision(ExpedientePrevision pExpedientePrevision) throws BusinessException{
		ExpedientePrevision expedientePrevision = null;
		List<EstadoPrevision> listaEstadoPrevision = null;
		List<RequisitoPrevisionComp> listaRequisitoPrevisionComp = null;
//		EstadoPrevision estadoPrevision = null;
		List<BeneficiarioPrevision> listaBeneficiarios = null;
		List<FallecidoPrevision> listaFallecidos = null;
		
		try{
			
			expedientePrevision = boExpedientePrevision.grabar(pExpedientePrevision);

			listaEstadoPrevision = pExpedientePrevision.getListaEstadoPrevision();
						
			// Si pasa directo a estado Solicitud, se genera un estado previo de requisito
			if(listaEstadoPrevision != null && !listaEstadoPrevision.isEmpty()){
				if(listaEstadoPrevision.get(0).getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD)==0){
					grabarEstadoRequisitoDefault(listaEstadoPrevision.get(0), expedientePrevision.getId());
				}
			}

			if(listaEstadoPrevision!=null){
				grabarListaDinamicaEstadoPrevision(listaEstadoPrevision, expedientePrevision.getId());
			}
			
			listaRequisitoPrevisionComp = pExpedientePrevision.getListaRequisitoPrevisionComp();
			listaBeneficiarios = pExpedientePrevision.getListaBeneficiarioPrevision();
			listaFallecidos = pExpedientePrevision.getListaFallecidoPrevision();
			
			//Grabar Lista de requisitos de Credito
			if(listaRequisitoPrevisionComp!=null){
				expedientePrevision.setListaRequisitoPrevisionComp(grabarListaDinamicaRequisitoPrevision(listaRequisitoPrevisionComp, expedientePrevision.getId()));
				//expedientePrevision.setListaRequisitoPrevisionComp(grabarListaDinamicaRequisitoCredito(listaRequisitoPrevisionComp, expedientePrevision.getId()));
			}
			//Grabar Lista de Benenficiarios
			//if(listaBeneficiarios!=null || !listaBeneficiarios.isEmpty()){
			if(listaBeneficiarios!=null){	
				if(!listaBeneficiarios.isEmpty() ){
					// cargamos el id de la paersona a cada uno de los beneficiarios
					for(int k=0; k<listaBeneficiarios.size();k++){ //beneficiarioDefault.setIntPersPersonaBeneficiario
						//listaBeneficiarios.get(k).setIntPersPersonaBeneficiario(listaBeneficiarios.get(k).getPersona().getIntIdPersona());	
						listaBeneficiarios.get(k).setIntPersPersonaBeneficiario(listaBeneficiarios.get(k).getIntPersPersonaBeneficiario());
					}
					expedientePrevision.setListaBeneficiarioPrevision(grabarListaDinamicaBeneficiariosPrevision(listaBeneficiarios, expedientePrevision.getId()));
					//expedientePrevision.setListaRequisitoPrevisionComp(grabarListaDinamicaRequisitoCredito(listaRequisitoPrevisionComp, expedientePrevision.getId()));
				}
			}
			
			//Grabar Lista de Fallecidos
			//if(listaBeneficiarios!=null || !listaBeneficiarios.isEmpty()){
			if(listaFallecidos!=null){	
				if(!listaFallecidos.isEmpty() ){
					// cargamos el id de la paersona a cada uno de los beneficiarios
					for(int k=0; k<listaFallecidos.size();k++){ //beneficiarioDefault.setIntPersPersonaBeneficiario
						//listaBeneficiarios.get(k).setIntPersPersonaBeneficiario(listaBeneficiarios.get(k).getPersona().getIntIdPersona());	
						listaFallecidos.get(k).setIntPersPersonaFallecido(listaFallecidos.get(k).getIntPersPersonaFallecido());
					}
					expedientePrevision.setListaFallecidoPrevision(grabarListaDinamicaFallecidosPrevision(listaFallecidos, expedientePrevision.getId()));
				}
			}	
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return expedientePrevision;
	}
	
	
	
	public ExpedientePrevision modificarExpedientePrevision(ExpedientePrevision pExpedientePrevision) throws BusinessException{
		ExpedientePrevision expedientePrevision= null;
		List<EstadoPrevision> listaEstadoPrevision = null;
		List<BeneficiarioPrevision> listaBeneficiarioPrevision = null;
		List<RequisitoPrevisionComp> listaRequisitoPrevisionComp = null;
		
		try{
			//1. expediente
			expedientePrevision = boExpedientePrevision.modificar(pExpedientePrevision);
			
			// 2. estado
			listaEstadoPrevision = pExpedientePrevision.getListaEstadoPrevision();
			if(listaEstadoPrevision!=null){
				grabarListaDinamicaEstadoPrevision(listaEstadoPrevision, expedientePrevision.getId());
			}
			
			// 3. beneficiario
			listaBeneficiarioPrevision = pExpedientePrevision.getListaBeneficiarioPrevision();
			if(listaBeneficiarioPrevision!=null){
				grabarListaDinamicaBeneficiariosPrevision(listaBeneficiarioPrevision, expedientePrevision.getId());
			}
			
			//4. requisitos
			listaRequisitoPrevisionComp = pExpedientePrevision.getListaRequisitoPrevisionComp();
			if(listaRequisitoPrevisionComp!=null){
				expedientePrevision.setListaRequisitoPrevisionComp(grabarListaDinamicaRequisitoPrevision(listaRequisitoPrevisionComp, expedientePrevision.getId()));
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return expedientePrevision;
	}
	
	
	
	private EstadoPrevision generarEstadoPrevision(ExpedientePrevision expedientePrevision) throws BusinessException {
		EstadoPrevision estadoPrevision = new EstadoPrevision();
		estadoPrevision.getId().setIntPersEmpresaPk(expedientePrevision.getId().getIntPersEmpresaPk());
		estadoPrevision.getId().setIntCuentaPk(expedientePrevision.getId().getIntCuentaPk());
		estadoPrevision.getId().setIntItemExpediente(expedientePrevision.getId().getIntItemExpediente());
		estadoPrevision.getId().setIntItemEstado(null);
		//estadoPrevision.setIntParaEstado(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO);
		//estadoPrevision.setTsFechaEstado(expedientePrevision.getEgreso().getTsFechaProceso());
		estadoPrevision.setIntPersEmpresaEstado(expedientePrevision.getId().getIntPersEmpresaPk());
		estadoPrevision.setIntSucuIdSucursal(expedientePrevision.getEgreso().getIntSucuIdSucursal());
		estadoPrevision.setIntSudeIdSubsucursal(expedientePrevision.getEgreso().getIntSudeIdSubsucursal());
		estadoPrevision.setIntPersUsuarioEstado(expedientePrevision.getEgreso().getIntPersPersonaUsuario());		
		
		return estadoPrevision;
	}
	
	/**
	 * Devuelve los expedientes de prevision en base a la Empresa y a la Cuenta
	 * @param o
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedientePrevisionComp> getListaExpedienteCreditoPorEmpresaYCuenta(ExpedientePrevisionId expedienteId) throws BusinessException{
		ExpedientePrevisionComp dto = null;
		List<ExpedientePrevisionComp> lista = null;
		List<ExpedientePrevision> listaExpedientePrevision = null;
		EstadoPrevision estadoPrevision;
		List<EstadoPrevision> listaEstadoPrevision = null;
		//List<CapacidadCredito> listaCapacidadCredito = null;
		//SocioComp socioComp = null;
		//Persona persona = null;
		///Integer intIdPersona = null;
//		PersonaFacadeRemote personaFacade = null;
//		SocioFacadeRemote socioFacade = null;
//		CuentaFacadeRemote cuentaFacade = null;
		Cuenta cuenta = null;
		CuentaId cuentaId = null;
		
		try{
//			cuentaFacade = (CuentaFacadeRemote) EJBFactory.getRemote(CuentaFacadeRemote.class);
//			socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
//			personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			
			cuentaId = new CuentaId();
			cuenta = new Cuenta();
			
			cuentaId.setIntCuenta(expedienteId.getIntCuentaPk());
			cuentaId.setIntPersEmpresaPk(expedienteId.getIntPersEmpresaPk());
			cuenta.setId(cuentaId);
			
			listaExpedientePrevision = boExpedientePrevision.getListaPorCuenta(cuenta);
			
			if(listaExpedientePrevision != null && listaExpedientePrevision.size()>0){
				lista = new ArrayList<ExpedientePrevisionComp>();
				for(ExpedientePrevision expedientePrevision : listaExpedientePrevision){
					dto = new ExpedientePrevisionComp();
					// le cargamos el ultimo estado
					estadoPrevision = boEstadoPrevision.getMaxEstadoPrevisionPorPokExpediente(expedientePrevision);
					dto.setEstadoPrevision(estadoPrevision);
					
					listaEstadoPrevision = boEstadoPrevision.getPorExpediente(expedientePrevision);
					expedientePrevision.setListaEstadoPrevision(listaEstadoPrevision);
					
					dto.setExpedientePrevision(expedientePrevision);
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
	
	
	public List<EstadoPrevision> grabarListaDinamicaEstadoPrevision(List<EstadoPrevision> lstEstadoPrevision, ExpedientePrevisionId pPK) throws BusinessException{
		EstadoPrevision estadoPrevision = null;
		EstadoPrevisionId pk = null;
		EstadoPrevision estadoPrevisionTemp = null;
		try{
			for(int i=0; i<lstEstadoPrevision.size(); i++){
				estadoPrevision = (EstadoPrevision) lstEstadoPrevision.get(i);
				if(estadoPrevision.getId()==null || estadoPrevision.getId().getIntItemEstado()==null){
					pk = new EstadoPrevisionId();
					pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
					pk.setIntCuentaPk(pPK.getIntCuentaPk());
					pk.setIntItemExpediente(pPK.getIntItemExpediente());
					//pk.setIntItemDetExpediente(pPK.get);
					estadoPrevision.setId(pk);
					estadoPrevision = boEstadoPrevision.grabar(estadoPrevision);
				}else{
					estadoPrevisionTemp = boEstadoPrevision.getPorPk(estadoPrevision.getId());
					if(estadoPrevisionTemp == null){
						estadoPrevision = boEstadoPrevision.grabar(estadoPrevision);
					}else{
						estadoPrevision = boEstadoPrevision.modificar(estadoPrevision);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstEstadoPrevision;
	}
	
	public List<BeneficiarioPrevision> grabarListaDinamicaBeneficiariosPrevision(List<BeneficiarioPrevision> lstBeneficiariosPrevision, ExpedientePrevisionId pPK) throws BusinessException{
		BeneficiarioPrevision beneficiario = null;
		BeneficiarioPrevisionId pk = null;
		BeneficiarioPrevision beneficiarioTemp = null;
		try{
			for(int i=0; i<lstBeneficiariosPrevision.size(); i++){
				beneficiario = (BeneficiarioPrevision) lstBeneficiariosPrevision.get(i);
				if(beneficiario.getId()==null || beneficiario.getId().getIntItemBeneficiario()==null){
					pk = new BeneficiarioPrevisionId();
					pk.setIntPersEmpresaPrevision(pPK.getIntPersEmpresaPk());
					pk.setIntCuenta(pPK.getIntCuentaPk());
					pk.setIntItemExpediente(pPK.getIntItemExpediente());
					//pk.setIntItemDetExpediente(pPK.get);
					beneficiario.setId(pk);
					beneficiario =  boBeneficiarioPrevision.grabar(beneficiario);
				}else{
					beneficiarioTemp = boBeneficiarioPrevision.getPorPk(beneficiario.getId());
					if(beneficiarioTemp == null){
						beneficiario = boBeneficiarioPrevision.grabar(beneficiario);
					}else{
						beneficiario = boBeneficiarioPrevision.modificar(beneficiario);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstBeneficiariosPrevision;
	}
	
	public List<FallecidoPrevision> grabarListaDinamicaFallecidosPrevision(List<FallecidoPrevision> lstFallecidosPrevision, ExpedientePrevisionId pPK) throws BusinessException{
		FallecidoPrevision fallecido = null;
		FallecidoPrevisionId pk = null;
		FallecidoPrevision fallecidoTemp = null;
		try{
			for(int i=0; i<lstFallecidosPrevision.size(); i++){
				fallecido = (FallecidoPrevision) lstFallecidosPrevision.get(i);
				if(fallecido.getId()==null ||   fallecido.getId().getIntItemFallecido() == null){
					pk = new FallecidoPrevisionId();
					pk.setIntPersEmpresaPrevision(pPK.getIntPersEmpresaPk());
					pk.setIntCuenta(pPK.getIntCuentaPk());
					pk.setIntItemExpediente(pPK.getIntItemExpediente());
					//pk.setIntItemDetExpediente(pPK.get);
					fallecido.setId(pk);
					fallecido =  boFallecidoPrevision.grabar(fallecido); //
				}else{
					fallecidoTemp = boFallecidoPrevision.getPorPk(fallecido.getId());
					if(fallecidoTemp == null){
						fallecido = boFallecidoPrevision.grabar(fallecido);
					}else{
						fallecido = boFallecidoPrevision.modificar(fallecido);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstFallecidosPrevision;
	}
	
	
	
	public List<EstadoPrevision> getListaEstadoPrevisionPorExpedienteId(ExpedientePrevision o) throws BusinessException{
		List<EstadoPrevision> listaEstadoPrevision = null;

		try{		
			listaEstadoPrevision = boEstadoPrevision.getPorExpediente(o);

		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaEstadoPrevision;
	}
	
	
	public List<RequisitoPrevisionComp> grabarListaDinamicaRequisitoPrevision(List<RequisitoPrevisionComp> listaRequisitoPrevisionComp, ExpedientePrevisionId pPK) throws BusinessException{
		RequisitoPrevision requisitoPrevision = null;
		RequisitoPrevisionId pk = null;
		RequisitoPrevision requisitoPrevisionTemp = null;
//		RequisitoPrevisionId pkTemp = null;
		Archivo archivo = null;
		try{
			/*
			Date today = new Date();
			String strToday = Constante.sdf.format(today);
			Date dtToday = Constante.sdf.parse(strToday);
			*/
			GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			for(RequisitoPrevisionComp requisitoPrevisionComp : listaRequisitoPrevisionComp){
				if(requisitoPrevisionComp.getRequisitoPrevision()== null || requisitoPrevisionComp.getRequisitoPrevision().getId()==null){
					//if(requisitoPrevisionComp.getRequisitoPrevision().getId()==null){
						requisitoPrevision = new RequisitoPrevision();
						pk = new RequisitoPrevisionId();
						pk.setIntPersEmpresaPrevision(pPK.getIntPersEmpresaPk());
						pk.setIntCuentaPk(pPK.getIntCuentaPk());
						pk.setIntItemExpediente(pPK.getIntItemExpediente());
						requisitoPrevision.setId(pk);
						
						if(requisitoPrevisionComp.getArchivoAdjunto()==null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							if(requisitoPrevisionComp.getArchivoAdjunto()==null){
								requisitoPrevisionComp.setArchivoAdjunto(new Archivo());
								requisitoPrevisionComp.getArchivoAdjunto().setId(new ArchivoId());
							}
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
							archivo.setStrNombrearchivo(requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo());
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
							archivo = requisitoPrevisionComp.getArchivoAdjunto();
							
						}
							requisitoPrevision.setIntPersEmpresaPk(requisitoPrevisionComp.getDetalle().getId().getIntPersEmpresaPk());
							requisitoPrevision.setIntItemReqAut(requisitoPrevisionComp.getDetalle().getId().getIntItemSolicitud());
							requisitoPrevision.setIntItemReqAutEstDetalle(requisitoPrevisionComp.getDetalle().getId().getIntItemDetalle());
							requisitoPrevision.setIntParaTipoArchivo(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
							requisitoPrevision.setIntParaItemArchivo(archivo.getId().getIntItemArchivo());
							requisitoPrevision.setIntParaItemHistorico(archivo.getId().getIntItemHistorico());
							requisitoPrevision.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
							requisitoPrevision.setTsFechaRequisito(new Timestamp(new Date().getTime()));
							requisitoPrevision =   boRequisitoPrevision.grabar(requisitoPrevision);
							requisitoPrevisionComp.setRequisitoPrevision(requisitoPrevision);
						//}
					//}
					
					
				}else{

					requisitoPrevisionTemp = boRequisitoPrevision.getPorPk(requisitoPrevisionComp.getRequisitoPrevision());

					if(requisitoPrevisionTemp == null){
						requisitoPrevision = boRequisitoPrevision.grabar(requisitoPrevisionComp.getRequisitoPrevision());
					}else{
						archivo = new Archivo();
						archivo.setId(new ArchivoId());
						requisitoPrevision = new RequisitoPrevision();
						pk = new RequisitoPrevisionId();
						Integer intItemArchivo = null;
						Integer intParaTipoCod = null;
						
						if(requisitoPrevisionComp.getArchivoAdjunto() != null){
							intItemArchivo = requisitoPrevisionComp.getArchivoAdjunto().getId().getIntItemArchivo();
							intParaTipoCod = requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod();
							
							if(intItemArchivo != null && intParaTipoCod != null)
								archivo = generalFacade.getListaArchivoDeVersionFinalPorTipoYItem(intParaTipoCod, intItemArchivo);
						
							if(archivo != null || requisitoPrevisionTemp.getIntParaItemArchivo() != null 
								|| requisitoPrevisionTemp.getIntParaItemHistorico() != null
								|| requisitoPrevisionTemp.getIntParaTipoArchivo() != null){
								
								requisitoPrevision.setId(requisitoPrevisionComp.getRequisitoPrevision().getId());
								requisitoPrevision.setIntPersEmpresaPk(requisitoPrevisionComp.getDetalle().getId().getIntPersEmpresaPk());
								requisitoPrevision.setIntItemReqAut(requisitoPrevisionComp.getDetalle().getId().getIntItemSolicitud());
								requisitoPrevision.setIntItemReqAutEstDetalle(requisitoPrevisionComp.getDetalle().getId().getIntItemDetalle());
								requisitoPrevision.setIntParaTipoArchivo(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
								requisitoPrevision.setIntParaItemArchivo(archivo.getId().getIntItemArchivo());
								requisitoPrevision.setIntParaItemHistorico(archivo.getId().getIntItemHistorico());
								requisitoPrevision.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
								requisitoPrevision.setTsFechaRequisito(new Timestamp(new Date().getTime()));
								requisitoPrevisionComp.setRequisitoPrevision(requisitoPrevision);
								
								requisitoPrevision = boRequisitoPrevision.modificar(requisitoPrevisionComp.getRequisitoPrevision());
								
							}else{
								
								requisitoPrevision = requisitoPrevisionComp.getRequisitoPrevision();
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
		return listaRequisitoPrevisionComp;
	}
	
	
	public ExpedientePrevision getExpedientePrevisionCompletoPorIdExpedientePrevision(ExpedientePrevisionId pId) throws BusinessException {
		ExpedientePrevision expedientePrevision = null;
		List<EstadoPrevision> listaEstadoPrevision = null;
		EstadoPrevision ultimoEstadoPrevision= null;
		EstadoPrevision primerEstadoPrevision= null;
		//CapacidadCreditoComp capacidadCreditoComp = null;
		//SocioEstructura socioEstructura = null;
		///List<CapacidadCreditoComp> listaCapacidadCreditoComp = null;
		//List<CapacidadCredito> listaCapacidadCredito = null;
		//List<CapacidadDescuento> listaCapacidadDscto = null;
		List<BeneficiarioPrevision> listaBeneficiarios = null;
		List<FallecidoPrevision> listaFallecidos = null;
		//List<CronogramaCredito> listaCronogramaCredito = null;
		//List<GarantiaCredito> listaGarantiaCredito = null;
		List<RequisitoPrevision> listaRequisitoPrevision = null;
		List<RequisitoPrevisionComp> listaRequisitoPrevisionComp = null;
		RequisitoPrevisionComp requisitoPrevisionComp = null;
		ConfServDetalle detalle = null;
		Archivo archivo = null;
		TipoArchivo tipoArchivo = null;
		MyFile myFile = null;
//		SocioFacadeRemote socioFacade = null;
		ConfSolicitudFacadeLocal solicitudFacade = null;
		GeneralFacadeRemote generalFacade = null;
		PersonaFacadeRemote personaFacade = null;
		try {
			try {
//				socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
				solicitudFacade = (ConfSolicitudFacadeLocal)EJBFactory.getLocal(ConfSolicitudFacadeLocal.class);
				generalFacade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
				personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			} catch (EJBFactoryException e) {
				log.info(" err -> solicitudPrevisionService.getExpedientePrevisionPorIdExpedientePrevision --> "+e);
			}
			
			expedientePrevision = boExpedientePrevision.getPorPk(pId);
			
			
			if(expedientePrevision!=null){
				// 1. recuperamos estados
				listaEstadoPrevision = boEstadoPrevision.getPorExpediente(expedientePrevision);
				if(listaEstadoPrevision!=null && listaEstadoPrevision.size()>0){
					expedientePrevision.setListaEstadoPrevision(listaEstadoPrevision);
					
					// cargando el uiltimo estado
					ultimoEstadoPrevision = boEstadoPrevision.getMaxEstadoPrevisionPorPokExpediente(expedientePrevision);
					if(ultimoEstadoPrevision != null){
						expedientePrevision.setEstadoPrevisionUltimo(ultimoEstadoPrevision);
					}
					
					primerEstadoPrevision = obtenerPrimerEstadoPrevision(expedientePrevision);
					if(primerEstadoPrevision != null){
						expedientePrevision.setEstadoPrevisionPrimero(primerEstadoPrevision);
					}
				}
				
				// 2.beneficiarios
				listaBeneficiarios = boBeneficiarioPrevision.getPorExpediente(expedientePrevision);
				//if(listaBeneficiarios != null || !listaBeneficiarios.isEmpty()){
				if(!listaBeneficiarios.isEmpty()){
					for(int k=0; k<listaBeneficiarios.size(); k++){
						if(listaBeneficiarios.get(k).getIntPersPersonaBeneficiario() != null){
							Integer intIdPersona = listaBeneficiarios.get(k).getIntPersPersonaBeneficiario();
							Persona persona = personaFacade.getPersonaNaturalPorIdPersona(intIdPersona);
							
							//getPersonaNaturalPorDocIdentidadYIdEmpresaYTipoVinculo(Integer intTipoIdentidad ,String strNroIdentidad, Integer intIdEmpresaSistema, Integer intTipoVinculo)
							// Integer intTipoIdentidad ,String strNroIdentidad, Integer intIdEmpresaSistema
							
							/*Persona personaTemp = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(persona.getDocumento().getIntTipoIdentidadCod(), 
																											persona.getDocumento().getStrNumeroIdentidad(), 
																										Constante.PARAM_EMPRESASESION);
							*/
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
					expedientePrevision.setListaBeneficiarioPrevision(listaBeneficiarios);
				}
				
				
				// 3.Fallecidosa
				listaFallecidos =  boFallecidoPrevision.getPorExpediente(expedientePrevision);
				//if(listaBeneficiarios != null || !listaBeneficiarios.isEmpty()){
				if(!listaFallecidos.isEmpty()){
					for(int k=0; k<listaFallecidos.size(); k++){
						if(listaFallecidos.get(k).getIntPersPersonaFallecido() != null){
							Integer intIdPersona = listaFallecidos.get(k).getIntPersPersonaFallecido();
							//Integer intIdPersona = listaFallecidos.get(k).getIntItemViculo();
							Persona persona = personaFacade.getPersonaNaturalPorIdPersona(intIdPersona);
							
							//getPersonaNaturalPorDocIdentidadYIdEmpresaYTipoVinculo(Integer intTipoIdentidad ,String strNroIdentidad, Integer intIdEmpresaSistema, Integer intTipoVinculo)
							// Integer intTipoIdentidad ,String strNroIdentidad, Integer intIdEmpresaSistema
							
							/*Persona personaTemp = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(persona.getDocumento().getIntTipoIdentidadCod(), 
																											persona.getDocumento().getStrNumeroIdentidad(), 
																										Constante.PARAM_EMPRESASESION);
							*/
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
								listaFallecidos.get(k).setPersona(persona);
							}
							
							
						}	
					}
					expedientePrevision.setListaFallecidoPrevision(listaFallecidos);
				}
				
				// 4. Requiisitos
				listaRequisitoPrevision = boRequisitoPrevision.getPorExpediente(expedientePrevision.getId());
				
				if(listaRequisitoPrevision!=null && listaRequisitoPrevision.size()>0){
					listaRequisitoPrevisionComp = new ArrayList<RequisitoPrevisionComp>();
					for(RequisitoPrevision requisitoPrevision : listaRequisitoPrevision ){
						if(requisitoPrevision.getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
						//}
						
						requisitoPrevisionComp = new RequisitoPrevisionComp();
						detalle = new ConfServDetalle();
						detalle.setId(new ConfServDetalleId());
						archivo = new Archivo();
						archivo.setId(new ArchivoId());
						tipoArchivo = new TipoArchivo();
						myFile = new MyFile();
						requisitoPrevisionComp.setRequisitoPrevision(requisitoPrevision);
						detalle.getId().setIntPersEmpresaPk(requisitoPrevision.getIntPersEmpresaPk());
						detalle.getId().setIntItemSolicitud(requisitoPrevision.getIntItemReqAut());
						detalle.getId().setIntItemDetalle(requisitoPrevision.getIntItemReqAutEstDetalle());
						detalle = solicitudFacade.getConfServDetallePorPk(detalle.getId());
						
						requisitoPrevisionComp.setDetalle(detalle);
						
						archivo.getId().setIntParaTipoCod(requisitoPrevision.getIntParaTipoArchivo());
						archivo.getId().setIntItemArchivo(requisitoPrevision.getIntParaItemArchivo());
						archivo.getId().setIntItemHistorico(requisitoPrevision.getIntParaItemHistorico());
						archivo = generalFacade.getArchivoPorPK(archivo.getId());
						
						requisitoPrevisionComp.setArchivoAdjunto(archivo);
						if(archivo !=null){
							tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevision.getIntParaTipoArchivo());
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
										requisitoPrevisionComp.setFileDocAdjunto(myFile);
										requisitoPrevisionComp.setArchivoAdjunto(archivo);
									}
									
								}
	
							}
						}
						listaRequisitoPrevisionComp.add(requisitoPrevisionComp);
					}
					}
					expedientePrevision.setListaRequisitoPrevisionComp(listaRequisitoPrevisionComp);
				}
			}
		} catch(BusinessException e){
			log.error("BusinessException-getExpedientePrevisionCompletoPorIdExpedientePrevision--->"+ e);
			throw e;
			
		}
		return expedientePrevision;
	}
	
	
	
	public List<ExpedientePrevision> getListaExpedientePrevisionPorCuenta(Cuenta cuenta) throws BusinessException{
//		ExpedientePrevision dto = null;
		List<ExpedientePrevision> lista = null;
		List<ExpedientePrevision> listaExpedientePrevision = null;
//		EstadoPrevision estadoPrevision;
		
//		List<EstadoPrevision> listaEstadoPrevision = null;
		//List<CapacidadCredito> listaCapacidadCredito = null;
//		SocioComp socioComp = null;
//		Persona persona = null;
//		Integer intIdPersona = null;
//		PersonaFacadeRemote personaFacade = null;
//		SocioFacadeRemote socioFacade = null;
		
		try{
//			socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			
			
			listaExpedientePrevision =   boExpedientePrevision.getListaPorCuenta(cuenta);
			if(listaExpedientePrevision != null && !listaExpedientePrevision.isEmpty()){
				lista = new ArrayList<ExpedientePrevision>();
				
				for (ExpedientePrevision prevision : listaExpedientePrevision) {
					EstadoPrevision ultimoEstado = null;
					
					ultimoEstado = boEstadoPrevision.getMaxEstadoPrevisionPorPokExpediente(prevision);
					if(ultimoEstado != null){
						prevision.setEstadoPrevisionUltimo(ultimoEstado);
					}
					lista.add(prevision);
				}
			}
			
				//boExpedientePrevision.getListaExpedientePrevisionBusqueda();
			
			/*if(listaExpedientePrevision != null && listaExpedientePrevision.size()>0){
				lista = new ArrayList<ExpedientePrevision>();
				for(ExpedientePrevision expedientePrevision : listaExpedientePrevision){
					dto = new ExpedienteCreditoComp();
					estadoCredito = boEstadoCredito.getMaxEstadoCreditoPorPokExpedienteCredito(expedienteCredito.getId());
					dto.setEstadoCredito(estadoCredito);
					listaEstadoCredito = boEstadoCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
					if(listaEstadoCredito!=null){
						for (EstadoCredito $estadoCredito : listaEstadoCredito) {
							if($estadoCredito.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO))
							dto.setStrFechaRequisito(Constante.sdf2.format($estadoCredito.getTsFechaEstado()));
							if($estadoCredito.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD))
								dto.setStrFechaSolicitud(Constante.sdf2.format($estadoCredito.getTsFechaEstado()));
							if($estadoCredito.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO))
								dto.setStrFechaAutorizacion(Constante.sdf2.format($estadoCredito.getTsFechaEstado()));
						}
					}
					expedienteCredito.setListaEstadoCredito(listaEstadoCredito);
					listaCapacidadCredito = boCapacidadCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
					if(listaCapacidadCredito!=null){
						for (CapacidadCredito capacidadCredito : listaCapacidadCredito) {
							intIdPersona = capacidadCredito.getIntPersPersonaPk();
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
							dto.setSocioComp(socioComp);
						}
					}
					expedienteCredito.setListaCapacidadCredito(listaCapacidadCredito);
					dto.setExpedienteCredito(expedienteCredito);
					lista.add(dto);
				}
				//dto.setListaExpedienteCredito(listaExpedienteCredito);
			}*/
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		//return lista;
		return lista;
	}
	
	
	public EstadoPrevision getUltimoEstadoExpedientePrevision(ExpedientePrevision expedientePrevision) throws BusinessException{
	EstadoPrevision ultimoEstado = null;
	try{
		ultimoEstado = boEstadoPrevision.getMaxEstadoPrevisionPorPokExpediente(expedientePrevision);
	
	} catch(BusinessException e){
		throw e;
	}
	
	return ultimoEstado;
	}
	
	
	
	/**
	 * 
	 * @param intBusqTipo
	 * @param strBusqCadena
	 * @param strBusqNroSol
	 * @param intTipoCreditoFiltro
	 * @param intSubTipoCreditoFiltro
	 * @param intBusqEstado
	 * @param dtBusqFechaEstadoDesde
	 * @param dtBusqFechaEstadoHasta
	 * @param intBusqSucursal
	 * @param intBusqSubSucursal
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedientePrevisionComp> buscarExpedientePrevisionFiltro(Integer intBusqTipo, String strBusqCadena, String strBusqNroSol, Integer intTipoCreditoFiltro, 
			Integer intSubTipoCreditoFiltro, Integer intBusqEstado, Date dtBusqFechaEstadoDesde, Date dtBusqFechaEstadoHasta, Integer intBusqSucursal, 
			Integer intBusqSubSucursal)throws BusinessException{
		List<ExpedientePrevisionComp> lstExpedientePrevComp = null;
		try {
			
			
			
			
		} catch (Exception e) {
			log.error("Error en buscarExpedientePrevisionFiltro ---> "+e);
		}
		return lstExpedientePrevComp;
		
	}
	public List<ExpedientePrevision> buscarExpedientePrevisionFiltro(List<Persona> listaPersonaFiltro, Integer intTipoCreditoFiltro, Integer intSubTipoCreditoFiltro,
			EstadoPrevision estadoPrevisionFiltro, Integer intItemExpedienteFiltro, Integer intTipoBusquedaSucursal, 
			Integer intIdSucursalFiltro, Integer intIdSubsucursalFiltro) throws BusinessException{
		
		List<ExpedientePrevision> listaExpedientePrevision = new ArrayList<ExpedientePrevision>();
		try{
			CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote) EJBFactory.getRemote(CuentaFacadeRemote.class);
			EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			
			
			List<Cuenta> listaCuenta = new ArrayList<Cuenta>();			
			List<CuentaIntegrante> listaCuentaIntegrante = new ArrayList<CuentaIntegrante>();
			
			Integer intIdEmpresa = estadoPrevisionFiltro.getId().getIntPersEmpresaPk();
			Integer intParaTipoCreditoFiltro = intTipoCreditoFiltro;
//			Integer intParaSubTipoCreditoFiltro = intSubTipoCreditoFiltro;
			Integer intParaEstadoCreditoFiltro = estadoPrevisionFiltro.getIntParaEstado();
			
			
			if(listaPersonaFiltro != null && !listaPersonaFiltro.isEmpty()){
				for(Persona persona : listaPersonaFiltro){
					List<CuentaIntegrante> listaCuentaIntegranteTemp = cuentaFacade.getCuentaIntegrantePorIdPersona(persona.getIntIdPersona(), intIdEmpresa);
					if(listaCuentaIntegranteTemp != null)	listaCuentaIntegrante.addAll(listaCuentaIntegranteTemp);
				}
			}else{
				listaCuentaIntegrante = cuentaFacade.getCuentaIntegrantePorIdPersona(null, intIdEmpresa);
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
			
			
			for(Cuenta cuenta : listaCuenta){
				List<ExpedientePrevision> listaExpedientePrevisionTemp = boExpedientePrevision.getListaPorCuenta(cuenta);
				for(ExpedientePrevision expedientePrevisionTemp : listaExpedientePrevisionTemp){
					boolean pasaFiltroItem = Boolean.FALSE;
					//log.info(expedientePrevisionTemp);
					expedientePrevisionTemp.setCuenta(cuenta);
					if(intItemExpedienteFiltro!=null && expedientePrevisionTemp.getId().getIntItemExpediente().equals(intItemExpedienteFiltro)){
						pasaFiltroItem = Boolean.TRUE;
					}else if(intItemExpedienteFiltro==null){
						pasaFiltroItem = Boolean.TRUE;
					}
					
					if(pasaFiltroItem){
						
						if(intParaTipoCreditoFiltro!=null && expedientePrevisionTemp.getIntParaDocumentoGeneral().equals(intParaTipoCreditoFiltro)){
							listaExpedientePrevision.add(expedientePrevisionTemp);
						}else if(intParaTipoCreditoFiltro==null){
							listaExpedientePrevision.add(expedientePrevisionTemp);
						}
					}
				}
			}
			
			List<ExpedientePrevision> listaTemp = new ArrayList<ExpedientePrevision>();
			for(ExpedientePrevision expedientePrevision : listaExpedientePrevision){
				//log.info(expedientePrevision);				
				
				EstadoPrevision estadoPrevisionUltimo  =  obtenerUltimoEstadoPrevision(expedientePrevision);
				EstadoPrevision estadoPrevisionPrimero =  obtenerPrimerEstadoPrevision(expedientePrevision);
				expedientePrevision.setEstadoPrevisionPrimero(estadoPrevisionPrimero);
				//log.info("estadoCreditoUltimo:"+estadoPrevisionUltimo);
				
				boolean pasaFiltroEstado = Boolean.FALSE;
				//Si se ha seleccionado un intParaEstadoCreditoFiltro en la busqueda
				if(intParaEstadoCreditoFiltro!=null && estadoPrevisionUltimo.getIntParaEstado().equals(intParaEstadoCreditoFiltro)){
					pasaFiltroEstado = Boolean.TRUE;
					
				//si no se a seleccionado un intParaEstadoCreditoFiltro en la busqueda, solo podemos traer registros en estado
				//aprobado o girado
				}else if(intParaEstadoCreditoFiltro==null){
				
						pasaFiltroEstado = Boolean.TRUE;
				}
				
				//comentado por pruebas
				if(pasaFiltroEstado){
				//if(true){
					Sucursal sucursal = new Sucursal();
					sucursal.getId().setIntIdSucursal(estadoPrevisionUltimo.getIntSucuIdSucursal());
					sucursal = empresaFacade.getSucursalPorPK(sucursal);
					//log.info(sucursal);
					estadoPrevisionUltimo.setSucursal(sucursal);
					estadoPrevisionUltimo.setSubsucursal(empresaFacade.getSubSucursalPorIdSubSucursal(estadoPrevisionUltimo.getIntSucuIdSucursal()));
					expedientePrevision.setEstadoPrevisionUltimo(estadoPrevisionUltimo);
					
					//Necesitamos agregar para la IU el estado en el que se aprobo el expediente
					/*EstadoPrevision estadoPrevisionAprobado = null;
					if(estadoPrevisionUltimo.equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)){
						estadoPrevisionAprobado = estadoPrevisionUltimo;
					}else{
						estadoPrevisionAprobado = obtenerUltimoEstadoPrevision(expedientePrevision, Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO);
					}
					expedientePrevision.setEstadoPrevisionAprobado(estadoPrevisionAprobado);
					*/
					listaTemp.add(expedientePrevision);
				}
			}
			//COMENTADO POR PRUEBAS
			listaExpedientePrevision = listaTemp;
			
			if(intTipoBusquedaSucursal != null && intIdSucursalFiltro != null){
				log.info("--busqueda sucursal");
				listaExpedientePrevision = manejarBusquedaSucursal(intTipoBusquedaSucursal, intIdSucursalFiltro, intIdSubsucursalFiltro, 
						intIdEmpresa, listaExpedientePrevision);
			}
			
			if(estadoPrevisionFiltro.getDtFechaEstadoDesde()!=null || estadoPrevisionFiltro.getDtFechaEstadoHasta()!=null){
				log.info("--busqueda fechas");
				listaExpedientePrevision = manejarFiltroFechas(listaExpedientePrevision, estadoPrevisionFiltro);
			}
			
		}catch(BusinessException e){
			log.error(e.getMessage(),e);
			throw e;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new BusinessException(e);
		}
		return listaExpedientePrevision;
	}
	
	private EstadoPrevision obtenerUltimoEstadoPrevision(ExpedientePrevision expedientePrevision)throws BusinessException{
		EstadoPrevision estadoPrevisionUltimo = new EstadoPrevision();
		try{
			List<EstadoPrevision> listaEstadoPrevision = boEstadoPrevision.getPorExpediente(expedientePrevision);
			
			estadoPrevisionUltimo.getId().setIntItemEstado(0);
			for(EstadoPrevision estadoPrevision : listaEstadoPrevision){
				if(estadoPrevision.getId().getIntItemEstado().compareTo(estadoPrevisionUltimo.getId().getIntItemEstado())>0){
					estadoPrevisionUltimo = estadoPrevision;
				}
			}
			
			expedientePrevision.setListaEstadoPrevision(listaEstadoPrevision);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return estadoPrevisionUltimo;
	}
	

	private List<ExpedientePrevision> manejarBusquedaSucursal(Integer intTipoBusquedaSucursal, Integer intIdSucursalFiltro, Integer intIdSubsucursalFiltro, 
			Integer intIdEmpresa, List<ExpedientePrevision> listaExpedientePrevision) throws Exception{
		
		SocioFacadeRemote socioFacade = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
		EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
		List<ExpedientePrevision> listaTemp = new ArrayList<ExpedientePrevision>();
		
		if(intTipoBusquedaSucursal.equals(Constante.PARAM_T_TIPOSUCURSALBUSQUEDA_SOCIO) && intIdSucursalFiltro!=null){
			for(ExpedientePrevision expedientePrevision : listaExpedientePrevision){
				for(CuentaIntegrante cuentaIntegrante : expedientePrevision.getCuenta().getListaIntegrante()){
					SocioEstructura socioEstructura = (socioFacade.getListaSocioEstrucuraPorIdPersona(
							cuentaIntegrante.getIntPersonaUsuario(), intIdEmpresa)).get(0);
					if(intIdSucursalFiltro.intValue()>0){
						if(socioEstructura.getIntIdSucursalAdministra().equals(intIdSucursalFiltro)){
							if(intIdSubsucursalFiltro!=null && intIdSubsucursalFiltro.equals(socioEstructura.getIntIdSubsucurAdministra())){
								listaTemp.add(expedientePrevision);
							}else if(intIdSubsucursalFiltro==null){
								listaTemp.add(expedientePrevision);
							}
						}
					}else{
						Integer intTotalSucursal = intIdSucursalFiltro;
						Sucursal sucursal = new Sucursal();
						sucursal.getId().setIntPersEmpresaPk(intIdEmpresa);
						sucursal.getId().setIntIdSucursal(socioEstructura.getIntIdSucursalAdministra());
						sucursal = empresaFacade.getSucursalPorPK(sucursal);						
						if(validarTotalSucursal(sucursal.getIntIdTipoSucursal(), intTotalSucursal)){
							listaTemp.add(expedientePrevision);
						}
					}
				}
			}
		
		}else if(intTipoBusquedaSucursal.equals(Constante.PARAM_T_TIPOSUCURSALBUSQUEDA_USUARIO) && intIdSucursalFiltro!=null){
			for(ExpedientePrevision expedientePrevision : listaExpedientePrevision){
				EstadoPrevision estadoPrevisionUltimo = expedientePrevision.getEstadoPrevisionUltimo();
				if(intIdSucursalFiltro.intValue()>0){
					if(estadoPrevisionUltimo.getIntSucuIdSucursal().equals(intIdSucursalFiltro)){
						if(intIdSubsucursalFiltro!=null && intIdSubsucursalFiltro.equals(estadoPrevisionUltimo.getIntSucuIdSucursal())){
							listaTemp.add(expedientePrevision);
						}else if(intIdSubsucursalFiltro==null){
							listaTemp.add(expedientePrevision);
						}
					}
				}else{
					Integer intTotalSucursal = intIdSucursalFiltro;
					Sucursal sucursal = new Sucursal();
					sucursal.getId().setIntPersEmpresaPk(intIdEmpresa);
					sucursal.getId().setIntIdSucursal(estadoPrevisionUltimo.getIntSucuIdSucursal());
					sucursal = empresaFacade.getSucursalPorPK(sucursal);						
					if(validarTotalSucursal(sucursal.getIntIdTipoSucursal(), intTotalSucursal)){
						listaTemp.add(expedientePrevision);
					}
				}
			}
		}
		
		return listaTemp;
	}
	
	
	private boolean validarTotalSucursal(Integer intTipoSucursal, Integer intTotalSucursal)throws BusinessException{
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
	
	private List<ExpedientePrevision> manejarFiltroFechas(List<ExpedientePrevision> listaExpedientePrevision, EstadoPrevision estado)throws Exception{		
		if(estado.getDtFechaEstadoDesde()!=null){
			List<ExpedientePrevision> listaTemp = new ArrayList<ExpedientePrevision>();
			for(ExpedientePrevision expedientePrevision : listaExpedientePrevision){				
				if(expedientePrevision.getEstadoPrevisionUltimo().getTsFechaEstado().compareTo(estado.getDtFechaEstadoDesde())>=0){
					listaTemp.add(expedientePrevision);
				}
			}
			listaExpedientePrevision = listaTemp;
		}		
		if(estado.getDtFechaEstadoHasta()!=null){
			List<ExpedientePrevision> listaTemp = new ArrayList<ExpedientePrevision>();
			for(ExpedientePrevision expedienteCredito : listaExpedientePrevision){				
				if(expedienteCredito.getEstadoPrevisionUltimo().getTsFechaEstado().compareTo(estado.getDtFechaEstadoHasta())<=0){
					listaTemp.add(expedienteCredito);
				}
			}
			listaExpedientePrevision = listaTemp;
		}
		return listaExpedientePrevision;
	}
	
	private EstadoPrevision obtenerPrimerEstadoPrevision(ExpedientePrevision expedientePrevision)throws BusinessException{
		EstadoPrevision estadoPrevisionInicial = new EstadoPrevision();
		try{
			List<EstadoPrevision> listaEstadoPrevision = boEstadoPrevision.getPorExpediente(expedientePrevision);
			
			if(listaEstadoPrevision.get(0).getId().getIntItemEstado() != null){
				Integer  menorItemEstado = listaEstadoPrevision.get(0).getId().getIntItemEstado();
				Integer intPosicion = 0;
				for(int i=0; i<listaEstadoPrevision.size();i++){
					if(listaEstadoPrevision.get(i).getId().getIntItemEstado().intValue() < menorItemEstado){
						intPosicion = i;
					}	
				}
				EstadoPrevision primerEstado = listaEstadoPrevision.get(intPosicion);
				if(primerEstado != null){
					estadoPrevisionInicial = primerEstado;
				}
			}
			//expedientePrevision.setListaEstadoPrevision(listaEstadoPrevision);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return estadoPrevisionInicial;
		
		/*
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
		 
		 */
	}
	

	
	/*public List<ExpedientePrevisionComp> getListaAutorizacionCreditoCompDeBusqueda(ExpedientePrevisionComp o) throws BusinessException{
		ExpedientePrevisionComp dto = null;
		List<ExpedientePrevisionComp> lista = null;
		List<ExpedientePrevision> listaExpedientePrevision = null;
		List<EstadoPrevision> listaEstadoPrevision = null;
		//List<CapacidadCredito> listaCapacidadCredito = null;
		SocioComp socioComp = null;
		Persona persona = null;
		Integer intIdPersona = null;
		PersonaFacadeRemote personaFacade = null;
		SocioFacadeRemote socioFacade = null;
		
		try{
			socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			listaExpedientePrevision = boExpedientePrevision.getListaBusquedaAutorizacionPorExpCredComp();
			if(listaExpedientePrevision != null && listaExpedientePrevision.size()>0){
				lista = new ArrayList<ExpedientePrevisionComp>();
				for(ExpedientePrevision expedientePrevision : listaExpedientePrevision){
					dto = new ExpedientePrevisionComp();
					listaEstadoPrevision = boEstadoPrevision.getPorExpediente(expedientePrevision);
					if(listaEstadoPrevision!=null){
						for (EstadoPrevision $estadoPrevision : listaEstadoPrevision) {
							if($estadoPrevision.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO))
								dto.setStrFechaRequisito(Constante.sdf2.format($estadoPrevision.getTsFechaEstado()));
							if($estadoPrevision.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD))
								dto.setStrFechaSolicitud(Constante.sdf2.format($estadoPrevision.getTsFechaEstado()));
							if($estadoPrevision.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO))
								dto.setStrFechaAutorizacion(Constante.sdf2.format($estadoPrevision.getTsFechaEstado()));
						}
						// seteamos el ultimo estado al expedienteCreditoComp
						int cantEstados=listaEstadoPrevision.size();
						dto.setEstadoPrevision(listaEstadoPrevision.get(cantEstados-1));
					}
					
					expedientePrevision.setListaEstadoPrevision(listaEstadoPrevision);
					dto.setExpedientePrevision(expedientePrevision);
					lista.add(dto);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}*/
	
	
	/**
	 * 
	 */
	public ExpedientePrevision grabarAutorizacionPrevision(ExpedientePrevision pExpedientePrevision) throws BusinessException{
		List<AutorizaPrevision> listaAutorizaPrevision = null;
		List<AutorizaVerificaPrevision> listaAutorizaVerificacion = null;
//		List<AutorizaVerificaPrevision> listaAutorizaVerificacionTemp = null;
		try {
			listaAutorizaPrevision = pExpedientePrevision.getListaAutorizaPrevision();
			if(listaAutorizaPrevision!=null){
				grabarListaDinamicaAutorizaPrevision(listaAutorizaPrevision, pExpedientePrevision.getId());
			}
			
			listaAutorizaVerificacion = pExpedientePrevision.getListaAutorizaVerificaPrevision();
			if(listaAutorizaVerificacion!=null){
				grabarListaDinamicaAutorizaVerificacion(listaAutorizaVerificacion, pExpedientePrevision.getId());
			}
		} catch(BusinessException e){
			throw e;
		} catch(Exception e){
			throw new BusinessException(e);
		}
		return pExpedientePrevision;
	}
	
	
	
		public List<AutorizaPrevision> grabarListaDinamicaAutorizaPrevision(List<AutorizaPrevision> lstAutorizaPrevision, ExpedientePrevisionId pPK) throws BusinessException{
		AutorizaPrevision autorizaPrevision = null;
		AutorizaPrevisionId pk = null;
		AutorizaPrevision autorizaPrevisionTemp = null;
		try{
			for(int i=0; i<lstAutorizaPrevision.size(); i++){
				autorizaPrevision = (AutorizaPrevision) lstAutorizaPrevision.get(i);
				if(autorizaPrevision.getId()==null || autorizaPrevision.getId().getIntItemAutoriza()==null){
					pk = new AutorizaPrevisionId();
					pk.setIntPersEmpresaPrevisionPk(pPK.getIntPersEmpresaPk());
					pk.setIntCuentaPk(pPK.getIntCuentaPk());
					pk.setIntItemExpediente(pPK.getIntItemExpediente());
					autorizaPrevision.setId(pk);
					autorizaPrevision = boAutorizaPrevision.grabar(autorizaPrevision);
				}else{
					autorizaPrevisionTemp = boAutorizaPrevision.getPorPk(autorizaPrevision.getId());
					if(autorizaPrevisionTemp == null){
						autorizaPrevision = boAutorizaPrevision.grabar(autorizaPrevision);
					}else{
						autorizaPrevision = boAutorizaPrevision.modificar(autorizaPrevision);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstAutorizaPrevision;
	}
	
	
	
		public List<AutorizaVerificaPrevision> grabarListaDinamicaAutorizaVerificacion(List<AutorizaVerificaPrevision> lstAutorizaVerificacion, ExpedientePrevisionId pPK) throws BusinessException{
		AutorizaVerificaPrevision autorizaVerificacion = null;
		AutorizaVerificaPrevisionId pk = null;
		AutorizaVerificaPrevision autorizaVerificacionTemp = null;
		try{
			for(int i=0; i<lstAutorizaVerificacion.size(); i++){
				autorizaVerificacion = (AutorizaVerificaPrevision) lstAutorizaVerificacion.get(i);
				if(autorizaVerificacion.getId()==null || autorizaVerificacion.getId().getIntItemAutorizaVerifica()==null){
					pk = new AutorizaVerificaPrevisionId();
					pk.setIntPersEmpresaPrevisionPk(pPK.getIntPersEmpresaPk());
					pk.setIntCuentaPk(pPK.getIntCuentaPk());
					pk.setIntItemExpediente(pPK.getIntItemExpediente());
					autorizaVerificacion.setId(pk);
					autorizaVerificacion = boAutorizaVerificaPrevision.grabar(autorizaVerificacion);
				}else{
					autorizaVerificacionTemp = boAutorizaVerificaPrevision.getPorPk(autorizaVerificacion.getId());
					if(autorizaVerificacionTemp == null){
						autorizaVerificacion = boAutorizaVerificaPrevision.grabar(autorizaVerificacion);
					}else{
						autorizaVerificacion = boAutorizaVerificaPrevision.modificar(autorizaVerificacion);
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
		
		
		public List<AutorizaVerificaPrevision> getListaVerificaPrevisionPorPkExpediente(ExpedientePrevisionId pId) throws BusinessException{
			List<AutorizaVerificaPrevision> lista = null;
			try{
				lista = boAutorizaVerificaPrevision.getListaPorPkExpedientePrevision(pId);
			}catch(BusinessException e){
				System.out.println("BusinessException ---> "+e);
				throw e;
			}catch(Exception e1){
				System.out.println("ExceptionException ---> "+e1);
				throw new BusinessException(e1);
			}
			return lista;
		}
		
		
		public List<AutorizaPrevision> getListaAutorizaPrevisionPorPkExpediente(ExpedientePrevisionId pId) throws BusinessException{
			List<AutorizaPrevision> lista = null;
			try{
				lista = boAutorizaPrevision.getListaPorPkExpedientePrevision(pId);
			}catch(BusinessException e){
				throw e;
			}catch(Exception e){
				throw new BusinessException(e);
			}
			return lista;
		}
		
		
		/**
		 * Graba el estadp Requisito x defecto al pasar directo a solicutd
		 * @param estadoPrevisionSolicitud
		 * @param pPK
		 * @return
		 * @throws BusinessException
		 */
		public EstadoPrevision grabarEstadoRequisitoDefault(EstadoPrevision estadoPrevisionSolicitud, ExpedientePrevisionId pPK) throws BusinessException{
			EstadoPrevision estadoPrevisionReq = null;
			EstadoPrevisionId pk = null;
			
			try{
				estadoPrevisionReq = new EstadoPrevision();
				pk = new EstadoPrevisionId();
				
				pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
				pk.setIntCuentaPk(pPK.getIntCuentaPk());
				pk.setIntItemExpediente(pPK.getIntItemExpediente());
				//pk.set(pPK.getIntPersEmpresaPk());
				estadoPrevisionReq.setId(pk);
				estadoPrevisionReq.setIntPersEmpresaEstado(estadoPrevisionSolicitud.getIntPersEmpresaEstado());
				estadoPrevisionReq.setIntSudeIdSubsucursal(estadoPrevisionSolicitud.getIntSudeIdSubsucursal());
				estadoPrevisionReq.setIntSucuIdSucursal(estadoPrevisionSolicitud.getIntSucuIdSucursal());
				estadoPrevisionReq.setIntParaEstado(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO);// requisito
				estadoPrevisionReq.setIntPersUsuarioEstado(estadoPrevisionSolicitud.getIntPersUsuarioEstado());
				estadoPrevisionReq.setSubsucursal(estadoPrevisionSolicitud.getSubsucursal());
				estadoPrevisionReq.setSucursal(estadoPrevisionSolicitud.getSucursal());
				estadoPrevisionReq.setTsFechaEstado(estadoPrevisionSolicitud.getTsFechaEstado());
				estadoPrevisionReq = boEstadoPrevision.grabar(estadoPrevisionReq);

			}catch(BusinessException e){
				log.error("Error - BusinessException - en grabarEstadoRequisitoDefault ---> "+e);
				throw e;
			}catch(Exception e){
				log.error("Error - Exception - en grabarEstadoRequisitoDefault ---> "+e);
				throw new BusinessException(e);
			}
			return estadoPrevisionReq;
		}
		
		
		
		/**
		 * Cambia los estados a los autorizaCredito, Archivos Infocorp, reniec, AutorizaVerfifica y
		 * a los requiisitos de solicitud asociados al expediente.
		 * Se aplicara cuando el expediente pase a aestado Observado.
		 * @param pExpedienteCredito
		 * @return
		 * @throws BusinessException
		 */
		public void eliminarVerificaAutorizacionAdjuntosPorObservacion(ExpedientePrevision pExpedientePrev) throws BusinessException{
			List<AutorizaPrevision> listaAutorizaPrevision = null;
			List<AutorizaVerificaPrevision> listaAutorizaVerificacion = null;
			List<AutorizaPrevision> listaAutorizaPrevisionModif = null;
			List<AutorizaVerificaPrevision> listaAutorizaVerificacionModif = null;
			List<RequisitoPrevision> listaRequisitosSolicitud = null;
			List<RequisitoPrevisionComp> listRequisitosComp = null;
			GeneralFacadeRemote generalFacade = null;
//			PrevisionFacadeRemote previsionFacade = null;
			AutorizacionPrevisionFacadeRemote autorizacionPrevisionFacade = null;
			SocioComp socioComp = null;
			List<RequisitoPrevisionComp> listaNuevosRequisitosSolicitud = null;
			
			try {
				generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
//				previsionFacade = (PrevisionFacadeRemote)EJBFactory.getRemote(PrevisionFacadeRemote.class);
				autorizacionPrevisionFacade = (AutorizacionPrevisionFacadeRemote)EJBFactory.getRemote(AutorizacionPrevisionFacadeRemote.class);
				// RECUPERAR TODOS LOS AUTORIZAR PREVISION

				listaAutorizaPrevision = autorizacionPrevisionFacade.getListaAutorizaPrevisionPorPkExpediente(pExpedientePrev.getId());

				if(listaAutorizaPrevision!=null && !listaAutorizaPrevision.isEmpty()){
					listaAutorizaPrevisionModif = new ArrayList<AutorizaPrevision>();
					for (AutorizaPrevision autorizaPrevision : listaAutorizaPrevision) {
						autorizaPrevision.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
						listaAutorizaPrevisionModif.add(autorizaPrevision);	
					}
					grabarListaDinamicaAutorizaPrevision(listaAutorizaPrevisionModif, pExpedientePrev.getId());
				}
				
				listaAutorizaVerificacion = pExpedientePrev.getListaAutorizaVerificaPrevision();
				if(listaAutorizaVerificacion!=null && !listaAutorizaVerificacion.isEmpty()){
					listaAutorizaVerificacionModif = new ArrayList<AutorizaVerificaPrevision>();
					for (AutorizaVerificaPrevision autorizaVerificacion : listaAutorizaVerificacion) {
							autorizaVerificacion.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
							autorizaVerificacion.setTsFechaRegistro((new Timestamp(new Date().getTime())));
							listaAutorizaVerificacionModif.add(autorizaVerificacion);				
						}
					
					
					grabarListaDinamicaAutorizaVerificacion(listaAutorizaVerificacionModif, pExpedientePrev.getId());
				}
				
				listaRequisitosSolicitud = boRequisitoPrevision.getPorExpediente(pExpedientePrev.getId());
				
				if(listaRequisitosSolicitud != null && !listaRequisitosSolicitud.isEmpty()){
					for (RequisitoPrevision requisitoPrevision : listaRequisitosSolicitud) {
						requisitoPrevision.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
						boRequisitoPrevision.modificar(requisitoPrevision);	
					}
					
					// recuperar los requisiitoscomp del expediente y   generalfacade.eliminarArchivo
					listRequisitosComp = recuperarRequisitoCompExpediente(pExpedientePrev);
					if(listRequisitosComp!= null && !listRequisitosComp.isEmpty()){
						for (RequisitoPrevisionComp requisitoPrevisionComp : listRequisitosComp) {
							Archivo archivo = new Archivo();
							archivo = requisitoPrevisionComp.getArchivoAdjunto();
							generalFacade.eliminarArchivo(archivo);
						}
						
					}
					
					socioComp = recuperarSocioCompXCuenta(pExpedientePrev);
					if(socioComp != null){
						
						listaNuevosRequisitosSolicitud = recuperarArchivosAdjuntos(pExpedientePrev, socioComp);
						if(listaNuevosRequisitosSolicitud!= null){
							grabarListaDinamicaRequisitoPrevision(listaNuevosRequisitosSolicitud, pExpedientePrev.getId());
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
		 * @param expedientePrevision
		 * @return
		 * @throws BusinessException
		 */
		public List<RequisitoPrevisionComp> recuperarRequisitoCompExpediente(ExpedientePrevision expedientePrevision)throws BusinessException{
			List<RequisitoPrevisionComp> listaRequisitoPrevisionComp = null;
			List<RequisitoPrevision> listaRequisitoPrevision = null;
			RequisitoPrevisionComp requisitoPrevisionComp = null;
			ConfServDetalle detalle= null;
			Archivo archivo = null;
			TipoArchivo tipoArchivo = null;
			MyFile myFile = null;
			ConfSolicitudFacadeLocal solicitudFacade = null;
			GeneralFacadeRemote generalFacade = null;
			
			try {
				solicitudFacade = (ConfSolicitudFacadeLocal)EJBFactory.getLocal(ConfSolicitudFacadeLocal.class);
				generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
				
				listaRequisitoPrevision = boRequisitoPrevision.getPorExpediente(expedientePrevision.getId());
				if(listaRequisitoPrevision!=null && !listaRequisitoPrevision.isEmpty()){
					listaRequisitoPrevisionComp = new ArrayList<RequisitoPrevisionComp>();
					for(RequisitoPrevision requisitoPrevision : listaRequisitoPrevision){
						if(requisitoPrevision.getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
							requisitoPrevisionComp = new RequisitoPrevisionComp();
							detalle = new ConfServDetalle();
							detalle.setId(new ConfServDetalleId());
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							tipoArchivo = new TipoArchivo();
							myFile = new MyFile();
							requisitoPrevisionComp.setRequisitoPrevision(requisitoPrevision);
							detalle.getId().setIntPersEmpresaPk(requisitoPrevision.getIntPersEmpresaPk());
							detalle.getId().setIntItemSolicitud(requisitoPrevision.getIntItemReqAut());
							detalle.getId().setIntItemDetalle(requisitoPrevision.getIntItemReqAutEstDetalle());
							detalle = solicitudFacade.getConfServDetallePorPk(detalle.getId());
							requisitoPrevisionComp.setDetalle(detalle);
							archivo.getId().setIntParaTipoCod(requisitoPrevision.getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevision.getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevision.getIntParaItemHistorico());
							
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							requisitoPrevisionComp.setArchivoAdjunto(archivo);
							
							if(archivo !=null && archivo.getFile() != null){
								tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevision.getIntParaTipoArchivo());
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
											requisitoPrevisionComp.setFileDocAdjunto(myFile);
											requisitoPrevisionComp.setArchivoAdjunto(archivo);
										}
										
									}
		
								}
							}
							listaRequisitoPrevisionComp.add(requisitoPrevisionComp);
						}
					}
				}
			} catch (Exception e) {
				log.error("Error en recuperarRequisitoCompExpediente ---> "+e);
			}
			return listaRequisitoPrevisionComp;
		}
		
		
		
		
		/**
		 * Se recupera el sociocomp en base al nro de cuenta
		 * @param expedienteCredito
		 * @return
		 */
		public SocioComp recuperarSocioCompXCuenta(ExpedientePrevision expedientePrev)throws BusinessException{
			
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
							cuentaId.setIntCuenta(expedientePrev.getId().getIntCuentaPk());
							cuentaId.setIntPersEmpresaPk(expedientePrev.getId().getIntPersEmpresaPk());

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
		public List<RequisitoPrevisionComp> recuperarArchivosAdjuntos(ExpedientePrevision expedientePrevision, SocioComp beanSocioComp) throws ParseException {
			log.info("-----------------------Debugging SolicitudPrestamoController.mostrarArchivosAdjuntos-----------------------------");
			ConfSolicitudFacadeRemote facade = null;
			TablaFacadeRemote tablaFacade = null;
			EstructuraFacadeRemote estructuraFacade = null;
			ConfServSolicitud confServSolicitud = null;
//			String strToday = Constante.sdf.format(new Date());
//			Date dtToday = null;
			List<ConfServSolicitud> listaDocAdjuntos = new ArrayList<ConfServSolicitud>();
			EstructuraDetalle estructuraDet = null;
			List<EstructuraDetalle> listaEstructuraDet = new ArrayList<EstructuraDetalle>();
//			List<RequisitoCreditoComp> listaRequisitoCreditoComp = null;
//			RequisitoCreditoComp requisitoCreditoComp;
			Integer intTIpoOperacion =  0;
			Integer intReqDesc =  0;
			RequisitoPrevisionComp requisitoPrevisionComp;
			List<RequisitoPrevisionComp> listaRequisitoPrevisionComp = new ArrayList<RequisitoPrevisionComp>();
			try {
//				dtToday = Constante.sdf.parse(strToday);
			
				facade = (ConfSolicitudFacadeRemote) EJBFactory.getRemote(ConfSolicitudFacadeRemote.class);
				estructuraFacade = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
				tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
				confServSolicitud = new ConfServSolicitud();

				if(expedientePrevision.getIntParaTipoCaptacion().compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0){
					intTIpoOperacion = Constante.PARAM_T_TIPOOPERACION_AES;
					intReqDesc = new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_AES);	
				}else if(expedientePrevision.getIntParaTipoCaptacion().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0){
					intTIpoOperacion = Constante.PARAM_T_TIPOOPERACION_FONDORETIRO;
					intReqDesc = new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_FONDORETIRO);	
				}else if(expedientePrevision.getIntParaTipoCaptacion().compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
					intTIpoOperacion = Constante.PARAM_T_TIPOOPERACION_FONDOSEPELIO;
					intReqDesc = new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_FONDOSEPELIO);	
				}
				
				confServSolicitud.setIntParaTipoOperacionCod(intTIpoOperacion);
				confServSolicitud.setIntParaSubtipoOperacionCod(expedientePrevision.getIntParaSubTipoOperacion());
				confServSolicitud.setIntParaTipoRequertoAutorizaCod(Constante.PARAM_T_TIPOREQAUT_REQUISITO);

				listaDocAdjuntos = facade.buscarConfSolicitudRequisitoOptimizado(confServSolicitud, Constante.PARAM_T_TIPOREQAUT_REQUISITO, null);
				
				if (listaDocAdjuntos != null && listaDocAdjuntos.size() > 0) {
					forSolicitud: for (ConfServSolicitud solicitud : listaDocAdjuntos) {
						if(solicitud.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
							if (solicitud.getIntParaTipoOperacionCod().compareTo(intTIpoOperacion)==0) {
								if (solicitud.getIntParaSubtipoOperacionCod().equals(expedientePrevision.getIntParaSubTipoOperacion())) {
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
																	
																	List<RequisitoPrevisionComp> listaRequisitoPrevisionCompTemp = new ArrayList<RequisitoPrevisionComp>();
																	for (ConfServDetalle detalle : solicitud.getListaDetalle()) {
																		if(detalle.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
																			if (detalle.getId().getIntPersEmpresaPk().equals(estructuraDetalle.getId().getIntPersEmpresaPk())
																					&& detalle.getId().getIntItemSolicitud().equals(estructuraDetalle.getId().getIntItemSolicitud())) {
																					
																					requisitoPrevisionComp = new RequisitoPrevisionComp();
																					requisitoPrevisionComp.setDetalle(detalle);
																					listaRequisitoPrevisionCompTemp.add(requisitoPrevisionComp);
																				}
																		}
																		
																	}
																														
																	List<Tabla> listaTablaRequisitos = new ArrayList<Tabla>();

																	//if(intTipoSolicitud.equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)){
																		// validamos que solo se muestre las de agrupamioento A.
																		listaTablaRequisitos = tablaFacade.getListaTablaPorAgrupamientoA(intReqDesc,"A");
																			for(int i=0;i<listaTablaRequisitos.size();i++){	
																				for(int j=0 ; j<listaRequisitoPrevisionCompTemp.size();j++){
																	 				if((listaRequisitoPrevisionCompTemp.get(j).getDetalle().getIntParaTipoDescripcion().intValue()) ==
																						(listaTablaRequisitos.get(i).getIntIdDetalle().intValue())){
																						listaRequisitoPrevisionComp.add(listaRequisitoPrevisionCompTemp.get(j));
																					}
																				}
																			}

																			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
																			for (RequisitoPrevisionComp requPrevComp : listaRequisitoPrevisionComp) {
																				System.out.println("requPrevComp.getDetalle().getId().getIntItemSolicitud()---> "+requPrevComp.getDetalle().getId().getIntItemSolicitud());
																				System.out.println("requPrevComp.getDetalle().getId().getIntItemDetalle() ---> "+requPrevComp.getDetalle().getId().getIntItemDetalle());
																				System.out.println("requPrevComp.getDetalle().getIntParaTipoDescripcion() ---> "+requPrevComp.getDetalle().getIntParaTipoDescripcion());
																				System.out.println("requPrevComp.getDetalle().getIntParaTipoPersonaOperacionCod() ---> "+requPrevComp.getDetalle().getIntParaTipoPersonaOperacionCod());
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
			
			return listaRequisitoPrevisionComp;
			}
		
		
		
	 /**
	 * Inicia el proceso de liquidacion de cuentas.--no se usa aca
	 * Inicia el proceso de autorizacion prevision
	 * 1. AES: se cambia estado de expediente (Esquema Servicio).
	 * 2. SEPELIO: genera y graba libro diario segun modelo contable, cambia estado de expediente utilizando datos del libro diario y
	 *    coloca fecha de fin en cta cto detalle de sepelio. (Contabilidad, Servicio y Movimiento). Solo para el subtipo Titular.
	 * 3. RETIRO:  genera solicitud de cta cte (solicitud, estados y tipo), genera transferecnia. Genera y graba libro diario segun modelo contable.
	 * 		
	 * @param socioComp
	 * @param strPeriodo
	 * @param requisitoLiq
	 * @param expedienteLiquidacionSeleccionado
	 * @return
	 * @throws BusinessException
	 */
	 public LibroDiario generarProcesosDePrevision_1(SocioComp socioComp, Integer intPeriodo, RequisitoPrevision requisitoPrev,
		 ExpedientePrevision expedientePrevisionSeleccionado,Usuario usuario,
		 ExpedientePrevision expedientePrevision, Integer intEstadoAprobado) throws BusinessException {
		 SolicitudCtaCte solicitudCtaCte = null;
		 LibroDiario libroDiario = null;
		 EstadoPrevision estadoFinalPrevision = null;
		 AutorizacionPrevisionFacadeRemote autorizacionPrevFacade = null;
		 SolicitudCtaCte solicitudCtaCteDevolucion = null;
		 List<Movimiento> listMovimiento= null;
		 Devolucion devolucion = null;
		 
		 solicitudPrevisionService solicitudPresvisionService = null;
		 List<FallecidoPrevision> lstPersonasMotivo = null;
	

//		 List<Movimiento> listaMovimientos = new ArrayList<Movimiento>();

		 try {
			 ConceptoFacadeRemote conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			 solicitudPresvisionService = (solicitudPrevisionService)TumiFactory.get(solicitudPrevisionService.class);
			 autorizacionPrevFacade = (AutorizacionPrevisionFacadeRemote)EJBFactory.getRemote(AutorizacionPrevisionFacadeRemote.class);
			 if(expedientePrevisionSeleccionado.getIntParaTipoCaptacion() != null){
				 
			// 1. AES
				 if(expedientePrevisionSeleccionado.getIntParaTipoCaptacion().compareTo(Constante.CAPTACION_AES)==0){
					 estadoFinalPrevision = cambioEstadoPrevision(expedientePrevisionSeleccionado, intEstadoAprobado, usuario, null);
					 if(estadoFinalPrevision != null && estadoFinalPrevision.getId().getIntItemEstado() != null){
						 solicitudPresvisionService.grabarAutorizacionPrevision(expedientePrevision);
					 }else{
						 throw new BusinessException("Error. No se pudo generar el estado Final de prevision AES.");
					 }
					 
	
			// 2. SEPELIO	 
				 }else if(expedientePrevisionSeleccionado.getIntParaTipoCaptacion().compareTo(Constante.CAPTACION_FDO_SEPELIO)==0){
					 	/*if(expedientePrevisionSeleccionado.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)==0){*/
					 		libroDiario = generarLibroDiarioYDiarioDetallePrevisionSepelio(expedientePrevisionSeleccionado, usuario, socioComp);
							 if(libroDiario != null && libroDiario.getId().getIntContCodigoLibro() != null){
								 
								 solicitudCtaCteDevolucion = autorizacionPrevFacade.grabarSolicitudCtaCteParaPrevisionDevolucionSepelio(socioComp, intPeriodo, requisitoPrev,usuario, expedientePrevisionSeleccionado,libroDiario);
									
									// Si la solicitud de devolucion se genera correctaemte
									 if(solicitudCtaCteDevolucion != null && solicitudCtaCteDevolucion.getId() != null 
										&& solicitudCtaCteDevolucion.getId().getIntCcobItemsolctacte() != null 
										&& solicitudCtaCteDevolucion.getListaSolCtaCteTipo() != null 
										&& !solicitudCtaCteDevolucion.getListaSolCtaCteTipo().isEmpty()){
										 
												devolucion = generarDevolucionSepelio(solicitudCtaCteDevolucion, listMovimiento, socioComp, expedientePrevision);
												if(devolucion.getId().getIntItemDevolucion() != null){
													listMovimiento = generarRegistrosMovimientoPrevisionSepelio(socioComp, usuario, expedientePrevision);
													 if(listMovimiento != null && !listMovimiento.isEmpty()){
														 estadoFinalPrevision = cambioEstadoPrevision(expedientePrevisionSeleccionado, intEstadoAprobado, usuario,libroDiario);
														 if(estadoFinalPrevision != null && estadoFinalPrevision.getId().getIntItemEstado() != null){
															 solicitudPresvisionService.grabarAutorizacionPrevision(expedientePrevision);
															 actualizarFechaFinCuentaConceptoDetalle(socioComp,Constante.PARAM_T_CUENTACONCEPTO_SEPELIO);
															 
															 lstPersonasMotivo = recuperarPersonasMotivo(expedientePrevision);
															 if(lstPersonasMotivo != null && !lstPersonasMotivo.isEmpty()){
																 
															 }else{
																 throw new BusinessException("Error. No se pudo cambiar de estadoa  a las personas fallecidas. ");
															 }
														 }else{
															 throw new BusinessException("Error. No se pudo generar el estado final de prevision Sepelio. * ");
														 }
														 
													 }else{
														 throw new BusinessException("Error. No se pudo generar los registros de Movimeinto de prevision Sepelio.");
													 }
												} 
									 }
							 }else{
								 throw new BusinessException("Error. Libro Diario No generado - Prevision Sepelio.");
							 }
					 	
			// 3. RETIRO
				 }else if(expedientePrevisionSeleccionado.getIntParaTipoCaptacion().compareTo(Constante.CAPTACION_FDO_RETIRO)==0){
						
					 if(expedientePrevisionSeleccionado.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_SUBTIPO_OPERACION_RETIRO_FALLECIMIENTO_TITULAR)==0){

						 // Generamos y grabamos la solicitud de ctacte, tipo y estados.	
						 solicitudCtaCte = autorizacionPrevFacade.grabarSolicitudCtaCteParaPrevision(socioComp, intPeriodo, requisitoPrev,usuario, expedientePrevisionSeleccionado);
							 
							 if(solicitudCtaCte != null && solicitudCtaCte.getListaSolCtaCteTipo() != null 
								&& !solicitudCtaCte.getListaSolCtaCteTipo().isEmpty()){
								 List<CuentaConceptoComp> listaCuentaConceptosComp = null;
								 CuentaConceptoComp cuentaConceptoRetiroComp = null;
								 //Agregado 27.05.2014 jchavez
								 //Generamos el nuevo movimiento de interes generado a la fecha en caso o exista (caso 99.9% de las veces pasa) 
									//**************************** CALCULO DEL INTERES GANADO ****************************//
								 	// 1. Recuperamos todas las cuentas concepto del socio: retiro, aportes y agregamos la de interes de retiro.
									listaCuentaConceptosComp = recuperarCuentasConceptoXSocio(socioComp.getCuenta().getId());
									if(listaCuentaConceptosComp != null && !listaCuentaConceptosComp.isEmpty()){
										for (CuentaConceptoComp cuentaConceptoComp : listaCuentaConceptosComp) {
											//2. Guardamos en el objeto cuentaConceptoRetiroComp el registro correspondiente a fdo. retiro
											if(cuentaConceptoComp.getIntParaTipoCaptacionModelo().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0){														
												cuentaConceptoRetiroComp = new CuentaConceptoComp();
												cuentaConceptoRetiroComp = cuentaConceptoComp;
												break;
												
											}
										}	
									}
								 	Movimiento movInteresGanado = new Movimiento();
								 	//3. Realizamos el calculo del interes generado a la fecha de registro...
								 	BigDecimal bdMontoInteresCalculado = calcularInteresRetiroAcumulado(cuentaConceptoRetiroComp.getCuentaConcepto(), expedientePrevision);
								 	//4. Generamos el movimiento del interes calculado
								 	Movimiento ultimoMovimientoInteresRetiro = recuperarUltimoMovimeintoInteresRetiro(cuentaConceptoRetiroComp.getCuentaConcepto(), expedientePrevision);
									movInteresGanado.setTsFechaMovimiento(new Timestamp(new Date().getTime()));
									movInteresGanado.setIntCuenta(socioComp.getCuenta().getId().getIntCuenta());
									movInteresGanado.setIntPersEmpresa(socioComp.getCuenta().getId().getIntPersEmpresaPk());					// persona
									movInteresGanado.setIntPersPersonaIntegrante(socioComp.getPersona().getIntIdPersona());// persona
									movInteresGanado.setIntItemCuentaConcepto(cuentaConceptoRetiroComp.getCuentaConcepto().getId().getIntItemCuentaConcepto());
									movInteresGanado.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO_INTERES);
									movInteresGanado.setIntParaTipoMovimiento(Constante.PARAM_T_TIPO_MOVIMIENTO_TRANSFERENCIA_PROVISIONES);
									movInteresGanado.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO);
									movInteresGanado.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
									movInteresGanado.setBdMontoSaldo(ultimoMovimientoInteresRetiro.getBdMontoSaldo().add(bdMontoInteresCalculado));
									movInteresGanado.setBdMontoMovimiento(bdMontoInteresCalculado);
									movInteresGanado.setIntPersEmpresaUsuario(usuario.getEmpresa().getIntIdEmpresa()); // usuario
									movInteresGanado.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk()); // usuario
									//5. Seteamos en el expediente el movimiento de interes registrado.
									expedientePrevisionSeleccionado.setMovimiento(movInteresGanado);
								 
								libroDiario = autorizacionPrevFacade.generarAsientoContablePrevisionRetiroYTransferencia(solicitudCtaCte, 
											socioComp, usuario, expedientePrevisionSeleccionado);
								
								if(libroDiario != null && libroDiario.getId().getIntContCodigoLibro() != null){
										
									// generamos la solicutd ctacte - devolucion
									solicitudCtaCteDevolucion = autorizacionPrevFacade.grabarSolicitudCtaCteParaPrevisionDevolucionRetiro(socioComp, intPeriodo, requisitoPrev,usuario, expedientePrevisionSeleccionado,libroDiario);

									// Si la solicitud de devolucion se genera correctaemte
									 if(solicitudCtaCteDevolucion != null && solicitudCtaCteDevolucion.getId() != null 
										&& solicitudCtaCteDevolucion.getId().getIntCcobItemsolctacte() != null 
										&& solicitudCtaCteDevolucion.getListaSolCtaCteTipo() != null 
										&& !solicitudCtaCteDevolucion.getListaSolCtaCteTipo().isEmpty()){
										 
										// Generamos los registros en movieminto
										 listMovimiento= generarRegistrosMovimientoPrevisionRetiro(socioComp, usuario, expedientePrevision);
										 listMovimiento.add(movInteresGanado);
										 if(listMovimiento != null && !listMovimiento.isEmpty()){
											for (Movimiento x : listMovimiento) {
												conceptoFacade.grabarMovimiento(x);
											}
											devolucion = generarDevolucionRetiro(solicitudCtaCteDevolucion, listMovimiento, socioComp);
											if(devolucion.getId().getIntItemDevolucion() != null){
												actualizarFechaFinCuentaConceptoDetalle(socioComp,Constante.PARAM_T_CUENTACONCEPTO_RETIRO);
												estadoFinalPrevision = cambioEstadoPrevision(expedientePrevisionSeleccionado, intEstadoAprobado, usuario,libroDiario);
												if(estadoFinalPrevision != null && estadoFinalPrevision.getId().getIntItemEstado() != null){
													 solicitudPresvisionService.grabarAutorizacionPrevision(expedientePrevision);
												 }else{
													 throw new BusinessException("Error. No se pudo registrar el estado final. Retiro.");
												 }
											}else{
												throw new BusinessException("Error. No se genero el registro de Devolucion.");
											}
												
										 } else{
												throw new BusinessException("Error. No se generaron los Movimientos de Prevision. Retiro.");
											    	}
									 }else{
										 throw new BusinessException("Error. No se genero la Solicitud CtaCte Devolucion. Retiro.");
										 }
									 
								 }else{
									 libroDiario = null;
									 throw new BusinessException("Error. Libro Diario No generado. Retiro.");
												 }
						 }else{
							 throw new BusinessException("Error. la solicitud de ctacte, tipo y estados. Retiro.");
						 }
							
						} else{	
								expedientePrevision= solicitudPresvisionService.grabarAutorizacionPrevision(expedientePrevision);
								 estadoFinalPrevision = cambioEstadoPrevision(expedientePrevisionSeleccionado, intEstadoAprobado, usuario,null);
								 if(estadoFinalPrevision != null && estadoFinalPrevision.getId().getIntItemEstado() != null){
									 solicitudPresvisionService.grabarAutorizacionPrevision(expedientePrevision);
								 }else{
									 throw new BusinessException("Error. la solicitud de ctacte, tipo y estados. Retiro. *");
								 }
						}
				 	}
	
			 } 
		 }catch (Exception e) {
			log.error("Error en generarProcesosDePrevision_1 ---> "+e);
			throw new BusinessException(e);
		}
		return libroDiario;
	}	
//		
//
//		public BigDecimal calcularInteresRetiro(SocioComp socioComp, ExpedientePrevision expedientePrevision){
//			List<Movimiento> listaMovimiento = null; 
//			CuentaConcepto cuentaConceptoRetiro = null;
//			Movimiento movimientoInteresUltimo = null;
////			BigDecimal bdMontoTotal = BigDecimal.ZERO;
////			Integer intNroDias = 0;
////			BigDecimal bdPorcentajeInteres = BigDecimal.ZERO;	
//			BigDecimal bdInteresCalculado = BigDecimal.ZERO;
//			BigDecimal bdInteresAcumulado = BigDecimal.ZERO;
//			CuentaConceptoDetalle cuentaConceptoDetalleRetiro = new CuentaConceptoDetalle();
//			List<CuentaConceptoDetalle> lstCtaCtpoDet = null;
//			CuentaConceptoDetalle ctaCtoDetalle = null;
//			Captacion beanCaptacion = null;
//			try {
//				CaptacionFacadeRemote captacionFacade = (CaptacionFacadeRemote)EJBFactory.getRemote(CaptacionFacadeRemote.class);
//				ConceptoFacadeRemote conceptoFacadeRemote = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
//				
//				// se recupera la ctacto de retiro y desde su detalle se obtiene las llaves de la captacion a fin de saber cual es porcentaje
//				// a aplicar en el calculo.
//				CuentaConceptoDetalle ccd = new CuentaConceptoDetalle();
//				ccd.setId(new CuentaConceptoDetalleId());
//				ccd.getId().setIntPersEmpresaPk(socioComp.getCuenta().getId().getIntPersEmpresaPk());
//				ccd.getId().setIntCuentaPk(expedientePrevision.getId().getIntCuentaPk());
//				ccd.setIntParaTipoConceptoCod(Constante.PARAM_T_CUENTACONCEPTO_RETIRO);
//				lstCtaCtpoDet = conceptoFacadeRemote.getCuentaConceptoDetallePorPKCuentaYTipoConcepto(ccd);
//				
//				CuentaConceptoId ccId = new CuentaConceptoId();
//				ccId.setIntPersEmpresaPk(lstCtaCtpoDet.get(0).getId().getIntPersEmpresaPk());
//				ccId.setIntCuentaPk(lstCtaCtpoDet.get(0).getId().getIntCuentaPk());
//				ccId.setIntItemCuentaConcepto(lstCtaCtpoDet.get(0).getId().getIntItemCuentaConcepto());
//				cuentaConceptoRetiro = conceptoFacadeRemote.getCuentaConceptoPorPK(ccId);
//
//				
//				if(cuentaConceptoRetiro != null){
//					ctaCtoDetalle = new CuentaConceptoDetalle();
//					ctaCtoDetalle = cuentaConceptoRetiro.getListaCuentaConceptoDetalle().get(0);
//					CaptacionId captacionId = new CaptacionId();
//					captacionId.setIntPersEmpresaPk(ctaCtoDetalle.getId().getIntPersEmpresaPk());
//					captacionId.setIntParaTipoCaptacionCod(ctaCtoDetalle.getIntParaTipoConceptoCod());
//					captacionId.setIntItem(ctaCtoDetalle.getIntItemConcepto());
//
//					beanCaptacion = captacionFacade.getCaptacionPorIdCaptacion(captacionId);
//								
//								
//					if(beanCaptacion != null){
//						
//						// 1. Se recupera el porcentaje del ineters a aplicar.
//						//beanCaptacion.getIntTasaInteres();
//						
//						// 2. Recuperamos las llaves de la cuenta cto retiro, para obetener los movimientos de interes de retiro.
//						
//
//			
//						if(cuentaConceptoRetiro != null){
//							listaMovimiento = conceptoFacadeRemote.getListaMovimientoPorCtaItemConceptoTipoConceptoGeneral(cuentaConceptoRetiro.getId().getIntPersEmpresaPk(), 
//									cuentaConceptoRetiro.getId().getIntCuentaPk(), 
//									cuentaConceptoRetiro.getId().getIntItemCuentaConcepto(), 
//									Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO_INTERES);
//							
//							if(listaMovimiento != null && !listaMovimiento.isEmpty()){
//								//Ordenamos los subtipos por int
//								Collections.sort(listaMovimiento, new Comparator<Movimiento>(){
//									public int compare(Movimiento uno, Movimiento otro) {
//										return uno.getIntItemMovimiento().compareTo(otro.getIntItemMovimiento());
//									}
//								});
//		
//								Integer tamanno = 0;
//								tamanno = listaMovimiento.size();
//								tamanno = tamanno -1;
//								// recuperamos el ultimo registro y en base a le se realiza calculo...
//								movimientoInteresUltimo = new Movimiento();
//								movimientoInteresUltimo = listaMovimiento.get(tamanno);
//								
//								BigDecimal bdTotalBaseCtaMasInt = BigDecimal.ZERO;
//								BigDecimal bdUltimoCapRetiro = BigDecimal.ZERO;
//								
//								
//								// a. 	Calculamos el monto base para el calculo de interes
//								bdTotalBaseCtaMasInt = movimientoInteresUltimo.getBdMontoSaldo();
//								// Agregado 23.05.2014 jchavez. ademas del utimo interes se debe de recuperar de movimiento la ultima 
//								// amortizacion de capital de fdo. retiro.
//		//						CuentaConceptoDetalle ccd = new CuentaConceptoDetalle();
//		//							Integer intItemCtaCpto = null;
//		//						ccd.setId(new CuentaConceptoDetalleId());
//		//						ccd.getId().setIntPersEmpresaPk(cuentaConceptoRetiro.getId().getIntPersEmpresaPk());
//		//						ccd.getId().setIntCuentaPk(cuentaConceptoRetiro.getId().getIntCuentaPk());
//		//						ccd.setIntParaTipoConceptoCod(Constante.PARAM_T_CUENTACONCEPTO_RETIRO);
//		//						lstCtaCtpoDet = conceptoFacadeRemote.getCuentaConceptoDetallePorPKCuentaYTipoConcepto(ccd);
//								List<Movimiento> lstMovCapital = null;
//								if (lstCtaCtpoDet!=null && !lstCtaCtpoDet.isEmpty()) {
//									for (CuentaConceptoDetalle listCCD : lstCtaCtpoDet) {
//		//									intItemCtaCpto = listCCD.getId().getIntItemCuentaConcepto();
//										lstMovCapital = conceptoFacadeRemote.getListaMovimientoPorCtaItemConceptoTipoConceptoGeneral(listCCD.getId().getIntPersEmpresaPk(),
//																			listCCD.getId().getIntCuentaPk(),listCCD.getId().getIntItemCuentaConcepto(),
//																			Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO);
//										break;
//									}
//								}
//								if (lstMovCapital!=null && !lstMovCapital.isEmpty()) {
//									bdUltimoCapRetiro = lstMovCapital.get(0).getBdMontoSaldo();
//								}
//								
//								// b. Recuperamos nro de dias entres fecha de movimeitno y hoy.
//								Date dtFechaUltimoInteres = new Date();
//								String strFechaUltimoInteres = Constante.sdf.format(movimientoInteresUltimo.getTsFechaMovimiento());
//								dtFechaUltimoInteres = Constante.sdf.parse(strFechaUltimoInteres);
//								
//								Date dtHoy = new Date();
//		
//								Integer nroDias =  obtenerDiasEntreFechas(dtFechaUltimoInteres,dtHoy);
//								nroDias = Math.abs(nroDias);
//								
//								
//								if(nroDias.compareTo(0)!= 0){
//									// c. Se aplica formula simple de interes --> Monto*Porc Interes
//									/*
//									 * Modificacion 22.04.2014 jchavez
//									 * Porc Interes beanCaptacion.getBdTem(). 
//									 * Se agrega a la formula el *(nro dias)/30
//									 */
//									bdInteresCalculado =  (bdTotalBaseCtaMasInt.add(bdUltimoCapRetiro)).multiply(new BigDecimal(nroDias).multiply(beanCaptacion.getBdTem())).divide(new BigDecimal(3000),2,RoundingMode.HALF_UP);
//									bdInteresAcumulado = bdInteresCalculado.add(bdTotalBaseCtaMasInt);
//								}else{
//									bdInteresCalculado = BigDecimal.ZERO;
//									bdInteresAcumulado= BigDecimal.ZERO;
//								}
//							}
//							//Agregado 22.04.2014 jchavez
//							//En caso no exista movimiento anterior, usar las tablas cuenta concepto y cuenta concepto detalle
//							else{
//								cuentaConceptoDetalleRetiro.setId(new CuentaConceptoDetalleId());
//								cuentaConceptoDetalleRetiro.getId().setIntPersEmpresaPk(cuentaConceptoRetiro.getId().getIntPersEmpresaPk());
//								cuentaConceptoDetalleRetiro.getId().setIntCuentaPk(cuentaConceptoRetiro.getId().getIntCuentaPk());
//								cuentaConceptoDetalleRetiro.getId().setIntItemCuentaConcepto(cuentaConceptoRetiro.getId().getIntItemCuentaConcepto());
//								cuentaConceptoDetalleRetiro.setIntParaTipoConceptoCod(Constante.CAPTACION_FDO_RETIRO);
//								cuentaConceptoDetalleRetiro = conceptoFacadeRemote.getCuentaConceptoDetallePorPkYTipoConcepto(cuentaConceptoDetalleRetiro);
//								
//								Date dtHoy = new Date();
//								Integer nroDias =  obtenerDiasEntreFechas(convertirTimestampToDate(cuentaConceptoDetalleRetiro.getTsInicio()),dtHoy);
//								
//								bdInteresCalculado =  cuentaConceptoRetiro.getBdSaldo().multiply(new BigDecimal(nroDias).multiply(beanCaptacion.getBdTem())).divide(new BigDecimal(3000),2,RoundingMode.HALF_UP);
//								bdInteresAcumulado = bdInteresCalculado.add(BigDecimal.ZERO);
//							}
//						}
//					}
//				}				
//			} catch (Exception e) {
//				log.error("Error en calcularInteresRetiro ---> "+e);
//			}	
//			
//			return bdInteresAcumulado;
//		}
		
		
		/**
		 * Recupera lista de las personas que "motivaron" la solicitud 
		 * @param expedientePrevision
		 * @return
		 */
		public List<FallecidoPrevision> recuperarPersonasMotivo (ExpedientePrevision expedientePrevision){
			
			List<FallecidoPrevision> lstPersonasMotivo = null;
			PrevisionFacadeRemote previsionFacade = null;
			try {
				previsionFacade = (PrevisionFacadeRemote)EJBFactory.getRemote(PrevisionFacadeRemote.class);
				
				lstPersonasMotivo = previsionFacade.getListaFallecidosPrevisionPorExpediente(expedientePrevision);
				if(lstPersonasMotivo != null && !lstPersonasMotivo.isEmpty()){
					cambioEstadoPersonaFallecida(lstPersonasMotivo);	
				}
			} catch (Exception e) {
				log.error("Error en recuperarPersonasMotivo ---> ");
			}
			return lstPersonasMotivo; 
		}
		
		/**
		 * Cambia de estado  ala persona que ha sido declarada fallecida
		 * @param lstFallecido
		 */
		public void cambioEstadoPersonaFallecida(List<FallecidoPrevision> lstFallecido){
			Persona persona = null;
			PersonaFacadeRemote personaFacade = null;
			try {
				personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				
				if(lstFallecido != null && !lstFallecido.isEmpty()){
					for(int k=0;k<lstFallecido.size();k++){
						persona= personaFacade.getPersonaPorPK(lstFallecido.get(k).getIntPersPersonaFallecido());
						persona.setIntEstadoCod(Constante.PARAM_PERSONA_ESTADO_FALLECIDO);
						personaFacade.modificarPersona(persona);
					}
				}
				
			} catch (Exception e) {
				System.out.println("Error en cambioEstadoPersonaFallecida --> "+e);
			}

		}

		
		/**
		 * Actualiza la cuenta concepto saldo y saldo detalle en  cero,  y la fecha fin de cta cto detalle del tipo de cta cto deseada.
		 * @param socioComp
		 * @param paramTCuentaconcepto
		 * @throws BusinessException
		 */
		 private void actualizarFechaFinCuentaConceptoDetalle(SocioComp socioComp, Integer paramTCuentaconcepto) throws BusinessException {
			 List<CuentaConceptoComp> listaCuentaConceptoComp= null;
			 CuentaConceptoComp ctaCtoComp = null;
			 List<CuentaConceptoDetalle> listaCtaConceptoDet= null;
			 ConceptoFacadeRemote conceptoFacade = null;
			 try {
				 conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
				 
				 listaCuentaConceptoComp = recuperarCuentasConceptoXCuentaYTipo(socioComp.getCuenta().getId(),paramTCuentaconcepto);
				 if(listaCuentaConceptoComp != null && !listaCuentaConceptoComp.isEmpty()){
					 ctaCtoComp = new CuentaConceptoComp();
					 ctaCtoComp = listaCuentaConceptoComp.get(0);
					 
					 if(ctaCtoComp != null){
						 CuentaConcepto ctaCto = new CuentaConcepto();
						 ctaCto= ctaCtoComp.getCuentaConcepto();
						 ctaCto.setBdSaldo(BigDecimal.ZERO);
						 conceptoFacade.modificarCuentaConcepto(ctaCto);
						 
						 if(ctaCtoComp.getCuentaConcepto().getListaCuentaConceptoDetalle() != null
								    && !ctaCtoComp.getCuentaConcepto().getListaCuentaConceptoDetalle().isEmpty()){
									 
									 listaCtaConceptoDet = new ArrayList<CuentaConceptoDetalle>();
									 listaCtaConceptoDet = ctaCtoComp.getCuentaConcepto().getListaCuentaConceptoDetalle();
									 for (CuentaConceptoDetalle detalle : listaCtaConceptoDet) {
										 if(detalle.getTsFin()== null){
											 detalle.setBdSaldoDetalle(BigDecimal.ZERO);
											 detalle.setTsFin(new Timestamp(new Date().getTime()));
											 detalle.setIntParaTipoFinConcepto(Constante.PARAM_T_TIPOFINCONCEPTO_DEVOLUCION);
											 conceptoFacade.modificarCuentaConceptoDetalle(detalle);
										 }
									} 
								 }  
					 	} 
				 }
				 
			} catch (Exception e) {
				log.error("Error en actualizarFechaFinCuentaConceptoDetalle ---> "+e);
				throw new BusinessException(e);
			}
			
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
		 public SolicitudCtaCte grabarSolicitudCtaCteParaPrevision(SocioComp socioComp,Integer strPeriodo, RequisitoPrevision requisitoPrev, Usuario usuario,
				 ExpedientePrevision expedientePrevision) 
		 	throws EJBFactoryException, BusinessException {
			 SolicitudCtaCte solicitudCtaCte = null;
			 EstadoSolicitudCtaCte estadoPendiente = null;
			 EstadoSolicitudCtaCte estadoAtendido = null;

			 CuentacteFacadeRemote cuentaCteFacadeRemote= null;
			 
			 try {
				 Calendar fecHoy = Calendar.getInstance();
				 Date dtAhora = fecHoy.getTime();
				 cuentaCteFacadeRemote = (CuentacteFacadeRemote)EJBFactory.getRemote(CuentacteFacadeRemote.class);
				 
					if(socioComp != null){
					
					// Generando la solciitud cta cte para prevision
						 solicitudCtaCte = new SolicitudCtaCte();
						 solicitudCtaCte.setId(new SolicitudCtaCteId());
						 solicitudCtaCte.getId().setIntEmpresasolctacte(Constante.PARAM_EMPRESASESION);
						 solicitudCtaCte.setIntPersEmpresa(socioComp.getCuenta().getId().getIntPersEmpresaPk());
						 solicitudCtaCte.setIntPersPersona(socioComp.getPersona().getNatural().getIntIdPersona());
						 solicitudCtaCte.setIntCsocCuenta(socioComp.getCuenta().getId().getIntCuenta());
						 solicitudCtaCte.setIntSucuIdsucursalsocio(socioComp.getCuenta().getIntIdUsuSucursal());
						 solicitudCtaCte.setIntSudeIdsubsucursalsocio(socioComp.getCuenta().getIntIdUsuSubSucursal());
						 solicitudCtaCte.setIntPeriodo(new Integer(strPeriodo));
						 solicitudCtaCte.setIntParaTipomodalidad(socioComp.getSocio().getSocioEstructura().getIntModalidad());
						 //solicitudCtaCte.setIntParaTipo(Constante.PARAM_T_TIPO_ORIGEN_SOLICITUD_CUENTA_CORRIENTE);   // cte 1

						 solicitudCtaCte.setIntParaTipo(requisitoPrev.getIntParaTipoArchivo());
						 solicitudCtaCte.setIntMaeItemarchivo(requisitoPrev.getIntParaItemArchivo());		
						 solicitudCtaCte.setIntMaeItemhistorico(requisitoPrev.getIntParaItemHistorico());
						 
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
							 estadoPendiente.setStrEsccObservacion("Prevision Retiro - Estado Pendiente.");
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
							 estadoAtendido.setStrEsccObservacion("Prevision Retiro - Estado Atendido.");
							 estadoAtendido = cuentaCteFacadeRemote.grabarEstadoSolicitudCtaCte(estadoAtendido);
							 lstEstadosSolCta.add(estadoAtendido);
							 
					//Generando la Solicitud Tipo
							 SolicitudCtaCteTipo solCtaCteTipo = new SolicitudCtaCteTipo();
							 SolicitudCtaCteTipoId solCtaCteTipoId = new SolicitudCtaCteTipoId();
							 
							 solCtaCteTipoId.setIntCcobItemsolctacte(solicitudCtaCte.getId().getIntCcobItemsolctacte());
							 solCtaCteTipoId.setIntPersEmpresasolctacte(solicitudCtaCte.getId().getIntEmpresasolctacte());
							 solCtaCteTipoId.setIntTipoSolicitudctacte(Constante.PARAM_T_SOLICITUD_CTACTE_TIPO_TRANSFERENCIAS);
							 solCtaCteTipo.setId(solCtaCteTipoId);
							 solCtaCteTipo.setIntTaraEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
							 solCtaCteTipo.setIntParaTipoorigen(Constante.PARAM_T_TIPO_ORIGEN_SOLICITUD_CUENTA_CORRIENTE);
							 solCtaCteTipo.setIntParaEstadoanalisis(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
							 solCtaCteTipo.setStrScctObservacion("Prevision Retiro. ");
							 solCtaCteTipo.setDtFechaDocumento(dtAhora);
							 
							 if(expedientePrevision.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)==0){
								 solCtaCteTipo.setIntMotivoSolicitud(Constante.PARAM_T_MOTIVO_SOLICITUD_CTACTE_SOCIO_NO_LABORA); // 249-22
							 }else{
								 solCtaCteTipo.setIntMotivoSolicitud(Constante.PARAM_T_MOTIVO_SOLICITUD_CTACTE_SOCIO_NO_LABORA); // 249-22
							 }
							 
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
							 }
						 }
					 }

			} catch (Exception e) {
				log.error("Error en grabarSolicitudCtaCteParaLiquidacion ---> "+e);
			}
			 return solicitudCtaCte; 
		 }
		 


		/**
		 * Cambia de estado al expediente, agregando un registro de Estado.
		 * @param expedienteCredito
		 * @throws Exception
		 */
		private EstadoPrevision cambioEstadoPrevision(ExpedientePrevision expedientePrevision,	Integer intParaEstadoPrevisionCod, Usuario usuario, LibroDiario diario) throws Exception {
			EstadoPrevision estadoPrevision = null;
			EstadoPrevisionId estadoPrevisionId = null;
			try {
				
				estadoPrevisionId = new EstadoPrevisionId();
				estadoPrevisionId.setIntCuentaPk(expedientePrevision.getId().getIntCuentaPk());
				estadoPrevisionId.setIntItemExpediente(expedientePrevision.getId().getIntItemExpediente());
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
		}
		
		
		
	/**
	 * Permite recuperar el modelo contable, y generar y grbar el libro diario para previsio de Sepelio
	 * @return
	 */
	private LibroDiario generarLibroDiarioYDiarioDetallePrevisionSepelio(ExpedientePrevision expedientePrevision, Usuario usuario, SocioComp socioComp)
	throws BusinessException{

		ModeloFacadeRemote modeloFacade = null;
		List<Modelo> listaModelo = null;
		Modelo modeloSeleccionado = null;
		BigDecimal bdValorColumna = BigDecimal.ZERO;
		LibroDiario diario = null;
		List<LibroDiarioDetalle> lstDiarioDetalle = new ArrayList<LibroDiarioDetalle>();
		List<ModeloDetalle> listaModDet = null;
		LibroDiarioFacadeRemote libroDiarioFacade = null;
		PlanCuentaFacadeRemote planCtaFacade = null;


	try {
				modeloFacade =  (ModeloFacadeRemote)EJBFactory.getRemote(ModeloFacadeRemote.class);
				libroDiarioFacade = (LibroDiarioFacadeRemote)EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
				planCtaFacade= (PlanCuentaFacadeRemote)EJBFactory.getRemote(PlanCuentaFacadeRemote.class);

				// Recuperamos el modelo a  Obtiencion del Modelo Contable segun el tipo de transferencia.
				listaModelo = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_PROVISION_SEPELIO,Constante.PARAM_EMPRESASESION);

				if (listaModelo == null || listaModelo.size() == 0 || listaModelo.isEmpty()){
					throw new BusinessException("No existe el modelo contable para generar el asiento de Prevision de Sepelio.");
				}else {

					if(listaModelo.get(0) != null){
						modeloSeleccionado = listaModelo.get(0);

						// Recobramos valores de periodo actual
						Integer intPeriodoCuenta = new Integer(obtieneAnio(new Date()));
						Integer anio =  new Integer(obtieneAnio(new Date()));
						String  mes  =  obtieneMesCadena(new Date());
						//bdValorColumna = expedientePrevision.getBdMontoNetoBeneficio();

						// Recuperamos el detalle del modelo
						listaModDet =  modeloFacade.getListaModeloDetallePorModeloId(modeloSeleccionado.getId());
						
						// por cada detalle del modelo se genera un registro de libro diario detalle:
						if(listaModDet != null && !listaModDet.isEmpty()){
							diario = new LibroDiario();
							diario.setId(new LibroDiarioId());
							diario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
							diario.getId().setIntContPeriodoLibro(new Integer(anio+""+mes));
							diario.getId().setIntPersEmpresaLibro(socioComp.getCuenta().getId().getIntPersEmpresaPk());
							diario.setStrGlosa("Prevision Sepelio - Libro Diario - "+new Date().getTime());
							diario.setTsFechaRegistro(new Timestamp((new Date()).getTime())); //la fecha actual
							diario.setTsFechaDocumento(new Timestamp((new Date()).getTime()));
							diario.setIntPersEmpresaUsuario(usuario.getEmpresa().getIntIdEmpresa());
							diario.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
							diario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO);
							diario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);

							//Recorremos el detalle
							// CGD-26.12.2013
							String strObservacionPlanCta = "";
							for (ModeloDetalle modeloDetalle : listaModDet) {    
								PlanCuenta planCta = new PlanCuenta();
								// modelo detalle
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
								
								
								
								if (modeloDetalle.getId().getIntContPeriodoCuenta().equals(intPeriodoCuenta)){
									LibroDiarioDetalle diarioDet = new LibroDiarioDetalle();
									diarioDet.setId(new LibroDiarioDetalleId());
									
									// recuperamos los niveles del detalle
									List<ModeloDetalleNivel> listaModDetNiv = null;
									listaModDetNiv = modeloFacade.getListaModeloDetNivelPorModeloDetalleId(modeloDetalle.getId());
	
									Boolean blnCumpleSubTipo = Boolean.FALSE;
									
									for (ModeloDetalleNivel modDetNivel : listaModDetNiv) {
									//////// Es valor fijo -  se define el monto a utilizar.
										if(modDetNivel.getIntParaTipoRegistro().compareTo(Constante.PARAM_T_MODELO_TIPO_REGISTRO_FIJO)==0){

											if(modDetNivel.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_SEPELIO_MONTO_NETO_BENEFICIO)){
												if(expedientePrevision.getBdMontoGastosADM() != null){
													bdValorColumna = expedientePrevision.getBdMontoNetoBeneficio();
												}else{
													bdValorColumna = BigDecimal.ZERO;
												}
											}else if(modDetNivel.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_SEPELIO_MONTO_BRUTO_BENEFICIO)){
												if(expedientePrevision.getBdMontoGastosADM() != null){
													bdValorColumna = expedientePrevision.getBdMontoBrutoBeneficio();
												}else{
													bdValorColumna = BigDecimal.ZERO;
												}
											}else {
												bdValorColumna = expedientePrevision.getBdMontoGastosADM();
												blnCumpleSubTipo = Boolean.TRUE;
											}

									///////// Es tabla	- se validan los tipos
										}else if(modDetNivel.getIntParaTipoRegistro().compareTo(Constante.PARAM_T_MODELO_TIPO_REGISTRO_TABLA)==0){

											//-- dato tablas  154  - TIPO DE solicitud de sepelio	
											if(modDetNivel.getIntDatoTablas().compareTo(new Integer(Constante.PARAM_T_SUBOPERACIONFONDOSEPELIO))==0){
												if(modDetNivel.getIntDatoArgumento().compareTo(expedientePrevision.getIntParaSubTipoOperacion())==0){ 
													blnCumpleSubTipo = Boolean.TRUE;
												}
											}	
										}
									}
	
									//diarioDet.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
	
									// Solo se valida el sub tipo de operacion 
									if(blnCumpleSubTipo){
										diarioDet.setStrContNumeroCuenta(modeloDetalle.getId().getStrContNumeroCuenta());
										diarioDet.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
										diarioDet.setIntContPeriodo(modeloDetalle.getId().getIntContPeriodoCuenta());
										diarioDet.setIntPersEmpresaCuenta(modeloDetalle.getId().getIntPersEmpresaCuenta());
										//CGD-24.01.2014
										diarioDet.setIntPersPersona(socioComp.getPersona().getIntIdPersona());
										diarioDet.setIntPersEmpresaSucursal(Constante.PARAM_EMPRESASESION);
										diarioDet.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
										diarioDet.setIntSudeIdSubSucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
										//CGD-26.12.2013
										diarioDet.setStrComentario(strObservacionPlanCta);	
										diarioDet.setStrNumeroDocumento(socioComp.getPersona().getIntIdPersona()+"-"+expedientePrevision.getId().getIntItemExpediente());
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
									}
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

						}
					
					}
				}

		} catch (Exception e) {
		log.error("Error en generarLibroDiarioYDiarioDetallePrevisionSepelio ---> "+e);
		throw new BusinessException(e);
		}
		return diario;
	}


		/**
		 * Se recupera cuentas conceptos comp segun el tipo.
		 * Para el proceso de Autorizacion de prevision.
		 * @param idCuenta
		 * @param intTipoCuentaConcepto
		 * @return
		 */
		public List<CuentaConceptoComp> recuperarCuentasConceptoXCuentaYTipo(CuentaId idCuenta, Integer intTipoCuentaConcepto)throws BusinessException{
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
				
								if(detalle.getIntParaTipoConceptoCod().compareTo(intTipoCuentaConcepto)==0){
									cuentaConceptoComp.setCuentaConcepto(cuentaConcepto);
									cuentaConceptoComp.setIntParaConceptoGeneralModelo(0);
									cuentaConceptoComp.setIntParaTipoCaptacionModelo(intTipoCuentaConcepto);
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
				log.error("Error en recuperarCuentasConceptoXCuentaYTipo ---> "+e);
			}
			
			return listaCuentaConceptoComp;
		}

		
		 

	/**
	 * Genera el lobro diario a partir de la solicitud tipo cta cte.
	 * @param solicitudCtaCteTipo
	 * @param socioComp
	 * @param usuario
	 * @param expedientePrevision
	 * @return
	 * @throws BusinessException
	 */
	public LibroDiario generarAsientoContablePrevisionRetiroYTransferencia(SolicitudCtaCte solicitudCtaCte,SocioComp socioComp, Usuario usuario, ExpedientePrevision expedientePrevision) throws BusinessException{ 
//	 ModeloFacadeRemote       modeloFacade 		= null;
	 CuentacteFacadeRemote cuentaCteFacade = null;
	 
//	 Integer intPersona = null;
//	 Integer intCuenta  = null;
//	 Integer intEmpresa = null;
	 LibroDiario diario = null;
//	 List<Modelo>     listaModelo     = null;
	 SolicitudCtaCteTipo solicitudCtaCteTipo = null;

//     Modelo modeloSeleccionado = null;
     Transferencia transferencia = null;
     
      try{	 cuentaCteFacade = (CuentacteFacadeRemote)EJBFactory.getRemote(CuentacteFacadeRemote.class);
//    	     modeloFacade      = (ModeloFacadeRemote)EJBFactory.getRemote(ModeloFacadeRemote.class);
//    	     intPersona = socioComp.getSocio().getId().getIntIdPersona();
//    	     intCuenta  = socioComp.getCuenta().getId().getIntCuenta();
//             intEmpresa = socioComp.getCuenta().getId().getIntPersEmpresaPk();
             
             if(solicitudCtaCte != null){
            	 if(solicitudCtaCte.getListaSolCtaCteTipo() != null && !solicitudCtaCte.getListaSolCtaCteTipo().isEmpty()){
            		 solicitudCtaCteTipo = new SolicitudCtaCteTipo();
            		 solicitudCtaCteTipo = solicitudCtaCte.getListaSolCtaCteTipo().get(0);
            	 }

            	 
				// CREAMOS Y GRABAMOS EL LIBRO DIARIO para prevision de retiro...
            	 //modeloSeleccionado.getId()
				diario = obtieneLibroDiarioYDiarioDetallePrevisionRetiro(null, null,expedientePrevision,solicitudCtaCteTipo, usuario,socioComp, expedientePrevision.getMovimiento());

				if(diario != null){
					
		        		// actualizamos la solicitud cta cte tipo
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
			 	}
			 }
			 
//            }
      }catch(BusinessException e){
    	  System.out.println("Error en generarAsientoContableRefinanciamiento 1 -->"+e);
    	  throw new BusinessException(e);
	  }catch(Exception e){
		  System.out.println("Error en generarAsientoContableRefinanciamiento 2 -->"+e);
			throw new BusinessException(e);
	  }
	  return diario;
 }
//	public LibroDiario generarAsientoContablePrevisionRetiroYTransferencia(SolicitudCtaCte solicitudCtaCte,SocioComp socioComp, Usuario usuario, ExpedientePrevision expedientePrevision) 
//	 		throws BusinessException{ 
//		 ModeloFacadeRemote       modeloFacade 		= null;
//		 CuentacteFacadeRemote cuentaCteFacade = null;
//		 
//	//	 Integer intPersona = null;
//	//	 Integer intCuenta  = null;
//		 Integer intEmpresa = null;
//		 LibroDiario diario = null;
//		 List<Modelo>     listaModelo     = null;
//		 SolicitudCtaCteTipo solicitudCtaCteTipo = null;
//	
//	     Modelo modeloSeleccionado = null;
//	     Transferencia transferencia = null;
//	     
//	      try{	 cuentaCteFacade = (CuentacteFacadeRemote)EJBFactory.getRemote(CuentacteFacadeRemote.class);
//	    	     modeloFacade      = (ModeloFacadeRemote)EJBFactory.getRemote(ModeloFacadeRemote.class);
//	//    	     intPersona = socioComp.getSocio().getId().getIntIdPersona();
//	//    	     intCuenta  = socioComp.getCuenta().getId().getIntCuenta();
//	             intEmpresa = socioComp.getCuenta().getId().getIntPersEmpresaPk();
//	             
//	             if(solicitudCtaCte != null){
//	            	 if(solicitudCtaCte.getListaSolCtaCteTipo() != null && !solicitudCtaCte.getListaSolCtaCteTipo().isEmpty()){
//	            		 solicitudCtaCteTipo = new SolicitudCtaCteTipo();
//	            		 solicitudCtaCteTipo = solicitudCtaCte.getListaSolCtaCteTipo().get(0);
//	            	 }
//	
//				    listaModelo = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_PROVISION_RETIRO,intEmpresa);
//				 
//				 if (listaModelo == null || listaModelo.size() == 0 || listaModelo.isEmpty()){
//					  throw new BusinessException("No existe el modelo contable para generar el asiento.");
//				 }else {
//	
//					if(listaModelo != null && !listaModelo.isEmpty()){
//						if(listaModelo.get(0) != null){
//							modeloSeleccionado = listaModelo.get(0);
//						}
//					}
//					// CREAMOS Y GRABAMOS EL LIBRO DIARIO para prevision de retiro...
//					diario = obtieneLibroDiarioYDiarioDetallePrevisionRetiro(modeloSeleccionado.getId(), null,expedientePrevision,solicitudCtaCteTipo, usuario,socioComp);
//	
//					if(diario != null){
//						
//			        		// actualizamos la solicitud cta cte tipo
//							solicitudCtaCteTipo.setIntEmpresaLibro(diario.getId().getIntPersEmpresaLibro());
//			        		solicitudCtaCteTipo.setIntCodigoLibro(diario.getId().getIntContCodigoLibro());
//			        		solicitudCtaCteTipo.setIntContPeriodolibro(diario.getId().getIntContPeriodoLibro());
//			        		
//			        		solicitudCtaCteTipo = cuentaCteFacade.modificarSolicitudCuentaCorrienteTipo(solicitudCtaCteTipo);
//	
//			        		//Graba la transferencia;
//			        		  transferencia = new Transferencia();
//						      TransferenciaId transferenciaId = new TransferenciaId();
//						      transferenciaId.setIntPersEmpresaTransferencia(solicitudCtaCteTipo.getId().getIntPersEmpresasolctacte());
//						      transferencia.setId(transferenciaId);
//						      transferencia.setIntParaDocumentoGeneral(diario.getIntParaTipoDocumentoGeneral());
//						      transferencia.setTsTranFecha(obtieneFechaActualEnTimesTamp());
//						      transferencia.setIntCcobItemSolctacte(solicitudCtaCteTipo.getId().getIntCcobItemsolctacte());
//						      transferencia.setIntPersEmpresaSolctacte(solicitudCtaCteTipo.getId().getIntPersEmpresasolctacte());
//						      transferencia.setIntParaTipoSolicitudctacte(solicitudCtaCteTipo.getId().getIntTipoSolicitudctacte());
//						      transferencia.setIntPersEmpresaLibro(diario.getId().getIntPersEmpresaLibro());
//						      transferencia.setIntContPeriodoLibro(diario.getId().getIntContPeriodoLibro());
//						      transferencia.setIntContCodigoLibro(diario.getId().getIntContCodigoLibro());
//						      transferencia.setIntTranPeriodo(diario.getId().getIntContPeriodoLibro());
//	
//						      transferencia = cuentaCteFacade.grabarTransgerencia(transferencia);
//				 	}
//				 }
//				 
//	            }
//	      }catch(BusinessException e){
//	    	  System.out.println("Error en generarAsientoContableRefinanciamiento 1 -->"+e);
//	    	  throw new BusinessException(e);
//		  }catch(Exception e){
//			  System.out.println("Error en generarAsientoContableRefinanciamiento 2 -->"+e);
//				throw new BusinessException(e);
//		  }
//		  return diario;
//	 }
//		


	/**
	 * En el caso de Prevision Retiro se generara 4 registros de movieminto:
	 * 1. Cálculo de Interes ganado.
	 * 2. Saldo de la cuenta concepto retiro.
	 * 3. Cálculo Negativo de Interes ganado.
	 * 4. Cuenta x Pagar (sumatoria de 2 y 3). 
	 * Y coloca en cero ctacto  y ctactodet
	 * @param cuentaConceptoRetiro
	 * @param socioComp
	 * @param usuario
	 * @return
	 * @throws BusinessException
	 */
	public List<Movimiento> generarRegistrosMovimientoPrevisionRetiro(SocioComp socioComp, Usuario usuario, ExpedientePrevision expedientePrevision)  throws BusinessException{
		List<Movimiento> listaMovimientos = null;
		List<CuentaIntegrante> lstCuentaIntegrante = null; 
//		ConceptoFacadeRemote conceptoFacade= null;
		CuentaFacadeRemote cuentaFacade = null;
		Integer intIdPersona = new Integer(0);
//		Boolean blnTodoOK = Boolean.TRUE;
		List<CuentaConceptoComp> listaCuentaConceptosComp = null;
		CuentaConceptoComp cuentaConceptoRetiroComp = null;
//		Movimiento movInteresGanado = new Movimiento();
		Movimiento movSaldoCuentaConcepto = new Movimiento();
		Movimiento movInteresNegativo = new Movimiento();
		Movimiento movCuentaPorPagar = new Movimiento();
		BigDecimal bdMontoInteresCalculado = BigDecimal.ZERO;
		Movimiento ultimoMovimientoInteresRetiro = null;
		
		try {
//			conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);

			
			// Recuperamos todas las cuentas concepto del socio: retiro, aportes y agregamos la de interes de retiro.
			listaCuentaConceptosComp = recuperarCuentasConceptoXSocio(socioComp.getCuenta().getId());
			if(listaCuentaConceptosComp != null && !listaCuentaConceptosComp.isEmpty()){
				//listaCuentaConceptosMasInteresComp =  new ArrayList<CuentaConceptoComp>(listaCuentaConceptosComp);

				for (CuentaConceptoComp cuentaConceptoComp : listaCuentaConceptosComp) {
					if(cuentaConceptoComp.getIntParaTipoCaptacionModelo().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0){
						
						cuentaConceptoRetiroComp = new CuentaConceptoComp();
						cuentaConceptoRetiroComp = cuentaConceptoComp;
						break;
						
					}
				}	
			}
			
			lstCuentaIntegrante = cuentaFacade.getListaCuentaIntegrantePorPKCuenta(socioComp.getCuenta().getId());

			if(lstCuentaIntegrante != null && !lstCuentaIntegrante.isEmpty()){
				for (CuentaIntegrante cuentaIntegrante : lstCuentaIntegrante) {
					if(cuentaIntegrante.getIntParaTipoIntegranteCod().compareTo(new Integer(1))==0){
						intIdPersona = cuentaIntegrante.getId().getIntPersonaIntegrante();
						break;
					}
				}
			}
	
				listaMovimientos = new ArrayList<Movimiento>();
				bdMontoInteresCalculado = calcularInteresRetiroAcumulado(cuentaConceptoRetiroComp.getCuentaConcepto(), expedientePrevision);
			 	ultimoMovimientoInteresRetiro = recuperarUltimoMovimeintoInteresRetiro(cuentaConceptoRetiroComp.getCuentaConcepto(), expedientePrevision);
			 	
				//3. Cálculo Negativo de Interes ganado.
				movInteresNegativo.setTsFechaMovimiento(new Timestamp(new Date().getTime()));
				movInteresNegativo.setIntCuenta(socioComp.getCuenta().getId().getIntCuenta());
				movInteresNegativo.setIntPersEmpresa(socioComp.getCuenta().getId().getIntPersEmpresaPk());					// persona
				movInteresNegativo.setIntPersPersonaIntegrante(intIdPersona);// persona
				movInteresNegativo.setIntItemCuentaConcepto(cuentaConceptoRetiroComp.getCuentaConcepto().getId().getIntItemCuentaConcepto());
				movInteresNegativo.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO_INTERES);
				movInteresNegativo.setIntParaTipoMovimiento(Constante.PARAM_T_TIPO_MOVIMIENTO_TRANSFERENCIA_PROVISIONES);
				movInteresNegativo.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO);
				movInteresNegativo.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_CARGO);
				movInteresNegativo.setBdMontoSaldo(BigDecimal.ZERO);
				movInteresNegativo.setBdMontoMovimiento(ultimoMovimientoInteresRetiro.getBdMontoSaldo().add(bdMontoInteresCalculado));
				movInteresNegativo.setIntPersEmpresaUsuario(usuario.getEmpresa().getIntIdEmpresa()); // usuario
				movInteresNegativo.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk()); // usuario
				listaMovimientos.add(movInteresNegativo);
				

				// 2. Saldo de la cuenta concepto retiro
				movSaldoCuentaConcepto.setTsFechaMovimiento(new Timestamp(new Date().getTime()));
				movSaldoCuentaConcepto.setIntCuenta(socioComp.getCuenta().getId().getIntCuenta());
				movSaldoCuentaConcepto.setIntPersEmpresa(socioComp.getCuenta().getId().getIntPersEmpresaPk());					// persona
				movSaldoCuentaConcepto.setIntPersPersonaIntegrante(intIdPersona);// persona
				movSaldoCuentaConcepto.setIntItemCuentaConcepto(cuentaConceptoRetiroComp.getCuentaConcepto().getId().getIntItemCuentaConcepto());
				movSaldoCuentaConcepto.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO);
				movSaldoCuentaConcepto.setIntParaTipoMovimiento(Constante.PARAM_T_TIPO_MOVIMIENTO_TRANSFERENCIA_PROVISIONES);
				movSaldoCuentaConcepto.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO);
				movSaldoCuentaConcepto.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_CARGO);
				movSaldoCuentaConcepto.setBdMontoMovimiento(cuentaConceptoRetiroComp.getCuentaConcepto().getBdSaldo());
				movSaldoCuentaConcepto.setBdMontoSaldo(BigDecimal.ZERO);
				movSaldoCuentaConcepto.setIntPersEmpresaUsuario(usuario.getEmpresa().getIntIdEmpresa()); // usuario
				movSaldoCuentaConcepto.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk()); // usuario
				listaMovimientos.add(movSaldoCuentaConcepto);

	
				// 4. Cuenta x Pagar (sumatoria de 2 y 3). 
				movCuentaPorPagar.setTsFechaMovimiento(new Timestamp(new Date().getTime()));
				movCuentaPorPagar.setIntCuenta(socioComp.getCuenta().getId().getIntCuenta());
				movCuentaPorPagar.setIntPersEmpresa(socioComp.getCuenta().getId().getIntPersEmpresaPk());					// persona
				movCuentaPorPagar.setIntPersPersonaIntegrante(intIdPersona);// persona
				movCuentaPorPagar.setIntItemCuentaConcepto(cuentaConceptoRetiroComp.getCuentaConcepto().getId().getIntItemCuentaConcepto());
				////16.05.2014 jchavez se agrega expediente en movimiento cuenta por pagar
				movCuentaPorPagar.setIntItemExpediente(expedientePrevision.getId().getIntItemExpediente());
				movCuentaPorPagar.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_CTAXPAGAR);
				movCuentaPorPagar.setIntParaTipoMovimiento(Constante.PARAM_T_TIPO_MOVIMIENTO_TRANSFERENCIA_PROVISIONES);
				movCuentaPorPagar.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO);
				movCuentaPorPagar.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
				movCuentaPorPagar.setBdMontoMovimiento(cuentaConceptoRetiroComp.getCuentaConcepto().getBdSaldo().add(ultimoMovimientoInteresRetiro.getBdMontoSaldo().add(bdMontoInteresCalculado)));
				movCuentaPorPagar.setBdMontoSaldo(cuentaConceptoRetiroComp.getCuentaConcepto().getBdSaldo().add(ultimoMovimientoInteresRetiro.getBdMontoSaldo().add(bdMontoInteresCalculado)));
				movCuentaPorPagar.setIntPersEmpresaUsuario(usuario.getEmpresa().getIntIdEmpresa()); // usuario
				movCuentaPorPagar.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk()); // usuario
				listaMovimientos.add(movCuentaPorPagar);
				/*
				System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
				for (Movimiento movimientos : listaMovimientos) {
					movimientos = conceptoFacade.grabarMovimiento(movimientos);
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
					System.out.println("*********************************************************************");
				}
				System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");*/
				//blnTodoOK = grabarYActualizarMovimiento_CuentaConcepto_Detalle_5(listaMovimientos, listaCuentasConceptoComp, socioComp);
	
			//}
			
		} catch (Exception e) {
//			blnTodoOK = Boolean.FALSE;
			log.error("Error en generarRegistrosMovimientoPrevision ---> "+e);
		}
		
		return listaMovimientos; 
		
	}
	
	
	
	/**
	 * En el caso de Prevision Retiro se generara 1 registro de movieminto:
	 * 1. Cuenta x Pagar.
	 * Modificacion jchavez 16.05.2014
	 * se agrega el expediente para su uso en giro.
	 * @param cuentaConceptoRetiro
	 * @param socioComp
	 * @param usuario
	 * @return
	 * @throws BusinessException
	 */
	public List<Movimiento> generarRegistrosMovimientoPrevisionSepelio(SocioComp socioComp, Usuario usuario, ExpedientePrevision expedientePrevision)  throws BusinessException{
		List<Movimiento> listaMovimientos = null;
		List<CuentaIntegrante> lstCuentaIntegrante = null;
		ConceptoFacadeRemote conceptoFacade= null;
		CuentaFacadeRemote cuentaFacade = null;
		Integer intIdPersona = new Integer(0);
		List<CuentaConceptoComp> listaCuentaConceptosComp = null;
		CuentaConceptoComp cuentaConceptoSepelioComp = null;
		Movimiento movCuentaPorPagar = new Movimiento();
		
		try {
			conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);

			// Recuperamos todas las cuentas concepto del socio: retiro, aportes y agregamos la de interes de retiro.
			listaCuentaConceptosComp = recuperarCuentasConceptoXSocio(socioComp.getCuenta().getId());
			if(listaCuentaConceptosComp != null && !listaCuentaConceptosComp.isEmpty()){

				for (CuentaConceptoComp cuentaConceptoComp : listaCuentaConceptosComp) {
					if(cuentaConceptoComp.getIntParaTipoCaptacionModelo().compareTo(Constante.PARAM_T_CUENTACONCEPTO_SEPELIO)==0){
						cuentaConceptoSepelioComp = new CuentaConceptoComp();
						cuentaConceptoSepelioComp = cuentaConceptoComp;
						break;
					}
				}	
			}
			
			lstCuentaIntegrante = cuentaFacade.getListaCuentaIntegrantePorPKCuenta(socioComp.getCuenta().getId());

			if(lstCuentaIntegrante != null && !lstCuentaIntegrante.isEmpty()){
				for (CuentaIntegrante cuentaIntegrante : lstCuentaIntegrante) {
					if(cuentaIntegrante.getIntParaTipoIntegranteCod().compareTo(new Integer(1))==0){
						intIdPersona = cuentaIntegrante.getId().getIntPersonaIntegrante();
						break;
					}
				}
			}
	
				listaMovimientos = new ArrayList<Movimiento>();

				// 1. Cuenta x Pagar.
				movCuentaPorPagar.setTsFechaMovimiento(new Timestamp(new Date().getTime()));
				movCuentaPorPagar.setIntCuenta(socioComp.getCuenta().getId().getIntCuenta());
				movCuentaPorPagar.setIntPersEmpresa(socioComp.getCuenta().getId().getIntPersEmpresaPk());					// persona
				movCuentaPorPagar.setIntPersPersonaIntegrante(intIdPersona);// persona
				movCuentaPorPagar.setIntItemCuentaConcepto(cuentaConceptoSepelioComp.getCuentaConcepto().getId().getIntItemCuentaConcepto());
				//jchavez 16.05.2014 se agrega el expediente de prevision para saber sobre que cuenta por pagar estoy 
				//realizando el giro.
				movCuentaPorPagar.setIntItemExpediente(expedientePrevision.getId().getIntItemExpediente());
				//
				movCuentaPorPagar.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_CTAXPAGAR);
				movCuentaPorPagar.setIntParaTipoMovimiento(Constante.PARAM_T_TIPO_MOVIMIENTO_TRANSFERENCIA_PROVISIONES);
				movCuentaPorPagar.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO);
				movCuentaPorPagar.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
				movCuentaPorPagar.setBdMontoMovimiento(expedientePrevision.getBdMontoNetoBeneficio());
				movCuentaPorPagar.setBdMontoSaldo(expedientePrevision.getBdMontoNetoBeneficio());
				movCuentaPorPagar.setIntPersEmpresaUsuario(usuario.getEmpresa().getIntIdEmpresa()); // usuario
				movCuentaPorPagar.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk()); // usuario
				listaMovimientos.add(movCuentaPorPagar);
				if(listaMovimientos != null && !listaMovimientos.isEmpty()){
					for (Movimiento movimientos : listaMovimientos) {
						movimientos = conceptoFacade.grabarMovimiento(movimientos);
					}
				}

			
		} catch (Exception e) {
			log.error("Error en generarRegistrosMovimientoPrevisionSepelio ---> "+e);
		}
		
		return listaMovimientos; 
		
	}

/**
 * Forma el libro diario , libro diario detalle en base al modelo contable (38):
 * Provisión de liquidación de cuenta al momento de autorizar.
 * modificado 27.05.2014 jchavez
 * @param idModelo
 * @param intTipoCreditoEmpresa
 * @param expedienteLIquidacion
 * @param solicitudCtaCteTipo
 * @param usuario
 * @param socioComp
 * @return
 */

		private LibroDiario obtieneLibroDiarioYDiarioDetallePrevisionRetiro( ModeloId idModelo, Integer intTipoCreditoEmpresa,
				ExpedientePrevision expedientePrevision, SolicitudCtaCteTipo solicitudCtaCteTipo, Usuario usuario, SocioComp socioComp, Movimiento movInteresCalculado) {

//				BigDecimal bdValorColumna = null;
				LibroDiario diario = null;
				List<LibroDiarioDetalle> lstDiarioDetalle = new ArrayList<LibroDiarioDetalle>();
//				List<ModeloDetalle> listaModDet = null;
//				List<CuentaConceptoComp> listaCuentaConceptosComp = null;
				//List<CuentaConceptoComp> listaCuentaConceptosMasInteresComp = null;
				
				ModeloFacadeRemote	modeloFacade = null;
				LibroDiarioFacadeRemote libroDiarioFacade = null;
				//Boolean blnTieneInteresRetiro= Boolean.FALSE;
//				CuentaConceptoComp cuentaConceptoRetiroComp = null;
//				BigDecimal bdMontoTotal = BigDecimal.ZERO;
				
//				Boolean blnDetalleTieneValorFijo = null;
//				PlanCuentaFacadeRemote planCtaFacade= null;
				//Agregado 27.05.2014 jchavez
				List<ModeloDetalleNivelComp> lstModeloProvisionRetiro = null;
				List<ModeloDetalleNivelComp> lstModeloProvisionRetiroInteres = null;
				List<ModeloDetalleNivelComp> lstModeloProvisionRetiroCapitalizacionInteres = null;
				ModeloDetalleNivel o = new ModeloDetalleNivel();
				ModeloDetalleNivel o1 = new ModeloDetalleNivel();
				ModeloDetalleNivel o2 = new ModeloDetalleNivel();
				Calendar cal = Calendar.getInstance();
				Integer anioActual = cal.get(Calendar.YEAR);
				List<CuentaConceptoDetalle> lstCtaCtpoDet = null;
				Timestamp tsFechaUltimoMovFdoRetiroInteres = null;
				try{
					modeloFacade = (ModeloFacadeRemote)EJBFactory.getRemote(ModeloFacadeRemote.class);
					libroDiarioFacade = (LibroDiarioFacadeRemote)EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
//					planCtaFacade= (PlanCuentaFacadeRemote)EJBFactory.getRemote(PlanCuentaFacadeRemote.class);
					ConceptoFacadeRemote conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
					
					// Recobramos valores de periodo actual
//					Integer intPeriodoCuenta = new Integer(obtieneAnio(new Date()));
					Integer anio = new Integer(obtieneAnio(new Date()));
					String  mes  = obtieneMesCadena(new Date());
					Integer intPeriodoActual = new Integer(anio+""+mes);
					log.info("periodo actual: "+intPeriodoActual);
					Timestamp tsUltimoDiaDelPeriodo = obtenerUltimoDiaDelMesAnioPeriodo(intPeriodoActual);
					log.info("ultimo dia del mes: "+tsUltimoDiaDelPeriodo);
					
					//1. Obtenemos el modelo provision de fdo de retiro y seteamos cabecera libro diario
					o.setId(new ModeloDetalleNivelId());
					o.getId().setIntEmpresaPk(socioComp.getCuenta().getId().getIntPersEmpresaPk());
					o.getId().setIntPersEmpresaCuenta(socioComp.getCuenta().getId().getIntPersEmpresaPk());
					o.getId().setIntContPeriodoCuenta(anioActual);
					lstModeloProvisionRetiro = modeloFacade.getModeloProvisionRetiro(o);
					//seteando cabecera libro diario...
					diario = new LibroDiario();
					diario.setId(new LibroDiarioId());
					diario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());

					diario.getId().setIntContPeriodoLibro(new Integer(anio+""+mes));
					diario.getId().setIntPersEmpresaLibro(socioComp.getCuenta().getId().getIntPersEmpresaPk());
					diario.setStrGlosa("Prevision Retiro - Libro Diario - "+new Date().getTime());
					diario.setTsFechaRegistro(new Timestamp((new Date()).getTime())); 
					diario.setTsFechaDocumento(new Timestamp((new Date()).getTime()));
					diario.setIntPersEmpresaUsuario(Constante.PARAM_EMPRESASESION);
					diario.setIntPersPersonaUsuario(solicitudCtaCteTipo.getIntPersUsuario());
					diario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO);
					diario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					
					//2. validamos el ultimo interes registrado en movimiento sea fecha fin de mes:
					// Obtenemos el ultimo movimiento de capital y de interes...
					CuentaConceptoDetalle ccd = new CuentaConceptoDetalle();
					ccd.setId(new CuentaConceptoDetalleId());
					ccd.getId().setIntPersEmpresaPk(socioComp.getCuenta().getId().getIntPersEmpresaPk());
					ccd.getId().setIntCuentaPk(expedientePrevision.getId().getIntCuentaPk());
					ccd.setIntParaTipoConceptoCod(Constante.PARAM_T_CUENTACONCEPTO_RETIRO);
					lstCtaCtpoDet = conceptoFacade.getCuentaConceptoDetallePorPKCuentaYTipoConcepto(ccd);
//					List<Movimiento> lstMovCapital = null;
					List<Movimiento> lstMovInteres = null;
//					BigDecimal bdCucoSaldo = BigDecimal.ZERO;
					CuentaConcepto ctaCpto = null;
					if (lstCtaCtpoDet!=null && !lstCtaCtpoDet.isEmpty()) {
						for (CuentaConceptoDetalle listCCD : lstCtaCtpoDet) {
							//modificamos la ctacptodetalle con la fecha de cierre
							CuentaConceptoId ccId = new CuentaConceptoId();
							ccId.setIntPersEmpresaPk(listCCD.getId().getIntPersEmpresaPk());
							ccId.setIntCuentaPk(listCCD.getId().getIntCuentaPk());
							ccId.setIntItemCuentaConcepto(listCCD.getId().getIntItemCuentaConcepto());
							ctaCpto = conceptoFacade.getCuentaConceptoPorPK(ccId);

//							lstMovCapital = conceptoFacade.getListaMovimientoPorCtaItemConceptoTipoConceptoGeneral(listCCD.getId().getIntPersEmpresaPk(),
//																listCCD.getId().getIntCuentaPk(),listCCD.getId().getIntItemCuentaConcepto(),
//																Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO);
							lstMovInteres = conceptoFacade.getListaMovimientoPorCtaItemConceptoTipoConceptoGeneral(listCCD.getId().getIntPersEmpresaPk(),
																listCCD.getId().getIntCuentaPk(),listCCD.getId().getIntItemCuentaConcepto(),
																Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO_INTERES);
							break;
						}
					}
					// Captuaramos la fecha del ultimo movimiento interes
					if (lstMovInteres!=null && !lstMovInteres.isEmpty()) tsFechaUltimoMovFdoRetiroInteres = lstMovInteres.get(0).getTsFechaMovimiento();
					
					//comparamos la fecha del ultimo interes generado con la del fin de mes...
					if (!(tsFechaUltimoMovFdoRetiroInteres!=null && (tsFechaUltimoMovFdoRetiroInteres.equals(tsUltimoDiaDelPeriodo)))) {
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
					
					BigDecimal bdMontoProvision = BigDecimal.ZERO;
					if (lstModeloProvisionRetiro!=null && !lstModeloProvisionRetiro.isEmpty()) {
						Integer cont = 0;
						ModeloDetalleNivelComp modeloProvRetiroAnt = null;
						for (ModeloDetalleNivelComp modeloProvRetiro : lstModeloProvisionRetiro) {
							if (cont==0 || cont==2) {
								if (modeloProvRetiro.getStrCampoConsumir().equalsIgnoreCase("CuCo_Saldo_N")) {
									bdMontoProvision = bdMontoProvision.add(ctaCpto.getBdSaldo());
								}else if (modeloProvRetiro.getStrCampoConsumir().equalsIgnoreCase("Mocc_MontoSaldo_N")) {
									bdMontoProvision = bdMontoProvision.add(movInteresCalculado.getBdMontoSaldo());
								}
								modeloProvRetiroAnt = new ModeloDetalleNivelComp();
								modeloProvRetiroAnt = modeloProvRetiro;
							}
							if (cont==1 || cont==3) {
								if (modeloProvRetiro.getStrCampoConsumir().equalsIgnoreCase("CuCo_Saldo_N")) {
									bdMontoProvision = bdMontoProvision.add(ctaCpto.getBdSaldo());
								}else if (modeloProvRetiro.getStrCampoConsumir().equalsIgnoreCase("Mocc_MontoSaldo_N")) {
									bdMontoProvision = bdMontoProvision.add(movInteresCalculado.getBdMontoSaldo());
								}
								LibroDiarioDetalle diarioDet = new LibroDiarioDetalle();
								diarioDet.setId(new LibroDiarioDetalleId());
								diarioDet.setStrContNumeroCuenta(modeloProvRetiroAnt.getStrNumeroCuenta());
								diarioDet.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
								diarioDet.setIntContPeriodo(modeloProvRetiroAnt.getIntPeriodoCuenta());
								diarioDet.setIntPersEmpresaCuenta(modeloProvRetiroAnt.getIntEmpresaCuenta());
								diarioDet.setIntPersPersona(socioComp.getPersona().getIntIdPersona());
								diarioDet.setIntPersEmpresaSucursal(Constante.PARAM_EMPRESASESION);
								diarioDet.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
								diarioDet.setIntSudeIdSubSucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
								diarioDet.setStrComentario(modeloProvRetiroAnt.getStrDescCuenta());
								diarioDet.setStrNumeroDocumento(socioComp.getPersona().getIntIdPersona()+"-"+expedientePrevision.getId().getIntItemExpediente());
								
								//diarioDet.set
								if(modeloProvRetiroAnt.getIntParamDebeHaber().compareTo(Constante.PARAM_T_LIBRO_DIARIO_DEBE)==0){
									diarioDet.setIntParaDocumentoGeneral(null);
									diarioDet.setBdDebeSoles(bdMontoProvision);
									diarioDet.setBdHaberSoles(null);
								}else if(modeloProvRetiroAnt.getIntParamDebeHaber().compareTo(Constante.PARAM_T_LIBRO_DIARIO_HABER)==0){
									diarioDet.setIntParaDocumentoGeneral(null);
									diarioDet.setBdHaberSoles(bdMontoProvision);
									diarioDet.setBdDebeSoles(null);
								}
//								strNroCuenta = modeloProvRetiro.getStrNumeroCuenta();
								bdMontoProvision = BigDecimal.ZERO;
								lstDiarioDetalle.add(diarioDet);
							}
							cont++;
						}						
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
							diarioDet.setStrNumeroDocumento(socioComp.getPersona().getIntIdPersona()+"-"+expedientePrevision.getId().getIntItemExpediente());
							
							//diarioDet.set
							if(modProvRetInt.getIntParamDebeHaber().compareTo(Constante.PARAM_T_LIBRO_DIARIO_DEBE)==0){
								diarioDet.setIntParaDocumentoGeneral(null);
								diarioDet.setBdDebeSoles(movInteresCalculado.getBdMontoMovimiento());
								diarioDet.setBdHaberSoles(null);
							}else if(modProvRetInt.getIntParamDebeHaber().compareTo(Constante.PARAM_T_LIBRO_DIARIO_HABER)==0){
								diarioDet.setIntParaDocumentoGeneral(null);
								diarioDet.setBdHaberSoles(movInteresCalculado.getBdMontoMovimiento());
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
							diarioDet.setStrNumeroDocumento(socioComp.getPersona().getIntIdPersona()+"-"+expedientePrevision.getId().getIntItemExpediente());
							
							//diarioDet.set
							if(modProvRetCapInt.getIntParamDebeHaber().compareTo(Constante.PARAM_T_LIBRO_DIARIO_DEBE)==0){
								diarioDet.setIntParaDocumentoGeneral(null);
								diarioDet.setBdDebeSoles(movInteresCalculado.getBdMontoMovimiento());
								diarioDet.setBdHaberSoles(null);
							}else if(modProvRetCapInt.getIntParamDebeHaber().compareTo(Constante.PARAM_T_LIBRO_DIARIO_HABER)==0){
								diarioDet.setIntParaDocumentoGeneral(null);
								diarioDet.setBdHaberSoles(movInteresCalculado.getBdMontoMovimiento());
								diarioDet.setBdDebeSoles(null);
							}
							lstDiarioDetalle.add(diarioDet);
						}
					}
					diario.setListaLibroDiarioDetalle(lstDiarioDetalle);
					diario = libroDiarioFacade.grabarLibroDiario(diario);
					
//					COMENTADO 27.05.2014 JCHAVEZ
//					// Recuperamos todas las cuentas concepto del socio: retiro, aportes y agregamos la de interes de retiro.
//					listaCuentaConceptosComp = recuperarCuentasConceptoXSocio(socioComp.getCuenta().getId());
//					if(listaCuentaConceptosComp != null && !listaCuentaConceptosComp.isEmpty()){
//
//						for (CuentaConceptoComp cuentaConceptoComp : listaCuentaConceptosComp) {
//							if(cuentaConceptoComp.getIntParaTipoCaptacionModelo().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0){
//								
//								cuentaConceptoRetiroComp = new CuentaConceptoComp();
//								cuentaConceptoRetiroComp = cuentaConceptoComp;
//								bdMontoTotal = bdMontoTotal.add(cuentaConceptoRetiroComp.getCuentaConcepto().getBdSaldo()).add(calcularInteresRetiroAcumulado(cuentaConceptoRetiroComp.getCuentaConcepto(), expedientePrevision));
//								break;
//
//							}
//						}	
//					}
//
//					// Recuperamos el detalle del modelo
//					listaModDet =  modeloFacade.getListaModeloDetallePorModeloId(idModelo);
//
//					//if(listaModDet != null && !listaModDet.isEmpty()){
//						diario = new LibroDiario();
//						diario.setId(new LibroDiarioId());
//						diario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
//
//						diario.getId().setIntContPeriodoLibro(new Integer(anio+""+mes));
//						diario.getId().setIntPersEmpresaLibro(socioComp.getCuenta().getId().getIntPersEmpresaPk());
//						diario.setStrGlosa("Prevision Retiro - Libro Diario - "+new Date().getTime());
//						diario.setTsFechaRegistro(new Timestamp((new Date()).getTime())); //la fecha actual
//						diario.setTsFechaDocumento(new Timestamp((new Date()).getTime()));
//						diario.setIntPersEmpresaUsuario(Constante.PARAM_EMPRESASESION);
//						diario.setIntPersPersonaUsuario(solicitudCtaCteTipo.getIntPersUsuario());
//						diario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO);
//						diario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
//
//						//Recorremos el detalle
//							for (ModeloDetalle modeloDetalle : listaModDet) { 
//								bdValorColumna= BigDecimal.ZERO;
//								// CGD 12.08.2013
////								blnDetalleTieneValorFijo = Boolean.FALSE;
//								PlanCuenta planCta = new PlanCuenta();
//								String strObservacionPlanCta = "";
//
//								///////////////////////////////////////////
//
//								if(modeloDetalle.getPlanCuenta() == null){
//									PlanCuentaId ctaId = new PlanCuentaId();
//									ctaId.setIntEmpresaCuentaPk(modeloDetalle.getId().getIntPersEmpresaCuenta());
//									ctaId.setIntPeriodoCuenta(modeloDetalle.getId().getIntContPeriodoCuenta());
//									ctaId.setStrNumeroCuenta(modeloDetalle.getId().getStrContNumeroCuenta());
//									
//									planCta = planCtaFacade.getPlanCuentaPorPk(ctaId);
//									if(planCta != null){
//										strObservacionPlanCta = planCta.getStrDescripcion();
//									}
//								}else{
//									strObservacionPlanCta = modeloDetalle.getPlanCuenta().getStrDescripcion();
//								}
//								
//								///////////////////////////////////////////
//		
//								// modelo detalle
//								if (modeloDetalle.getId().getIntContPeriodoCuenta().equals(intPeriodoCuenta)){
//									LibroDiarioDetalle diarioDet = new LibroDiarioDetalle();
//									diarioDet.setId(new LibroDiarioDetalleId());
//		
//									List<ModeloDetalleNivel> listaModDetNiv = null;
//									listaModDetNiv = modeloFacade.getListaModeloDetNivelPorModeloDetalleId(modeloDetalle.getId());
//									
//									// CGD 12.08.2013
//									if(listaModDetNiv != null && !listaModDetNiv.isEmpty()){
//										for (ModeloDetalleNivel modeloDetalleNivel : listaModDetNiv) {
//											if(modeloDetalleNivel.getIntParaTipoRegistro().compareTo(Constante.PARAM_T_MODELO_TIPO_REGISTRO_FIJO)==0){
////												blnDetalleTieneValorFijo = Boolean.TRUE;
//												bdValorColumna = bdMontoTotal;
//											}
//										}
//										
//									}
//									
//									// Cuentas concepto
//									Boolean blnCumpleSubTipoOperacion = Boolean.FALSE;
//									//Boolean blnCumpleSiEsInteres = Boolean.FALSE;
//									for (ModeloDetalleNivel modeloDetalleNivel : listaModDetNiv) {
//
//										// es tabla????...
//										if(modeloDetalleNivel.getIntParaTipoRegistro().compareTo(Constante.PARAM_T_MODELO_TIPO_REGISTRO_TABLA)==0){
//
//											//-- dato tablas  155  - TIPO DE CAPTACION--->	
//											if(modeloDetalleNivel.getIntDatoTablas().compareTo(new Integer(Constante.PARAM_T_TIPOCONCEPTO_RETIRO))==0){												
//												if(modeloDetalleNivel.getIntDatoArgumento().compareTo(expedientePrevision.getIntParaSubTipoOperacion())==0){ 
//													blnCumpleSubTipoOperacion = Boolean.TRUE;
//												}
//											}	
//											
//											//-- dato tablas  212  --->  
//											if(modeloDetalleNivel.getIntDatoTablas().compareTo(new Integer(Constante.PARAM_T_TIPOCONCEPTOGENERAL))==0){
//												if(modeloDetalleNivel.getIntDatoArgumento().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO_INTERES)==0){
//													blnCumpleSubTipoOperacion = Boolean.TRUE;
//													bdValorColumna = calcularInteresRetiroAcumulado(cuentaConceptoRetiroComp.getCuentaConcepto(), expedientePrevision);
//												}
//											}
//										}
//
//										/////////////////// Es valor fijo
//										else if(modeloDetalleNivel.getIntParaTipoRegistro().compareTo(Constante.PARAM_T_MODELO_TIPO_REGISTRO_FIJO)==0){
//
//											if(modeloDetalleNivel.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_RETIRO_MONTO_CUENTACONCEPTORETIRO)){
//												if(cuentaConceptoRetiroComp.getCuentaConcepto().getBdSaldo() != null){
//													bdValorColumna = cuentaConceptoRetiroComp.getCuentaConcepto().getBdSaldo();
//													blnCumpleSubTipoOperacion = Boolean.TRUE;
//													//blnCumpleSubTipoOperacion = Boolean.TRUE;
//													// CGD 12.08.2013
//													}
//												}
//											}
//										//SE MUEVE DE POSICION
//										if (blnCumpleSubTipoOperacion && bdValorColumna!=null) {
//											break;
//										}
//										
//										
//										}
//									
//									// terminamos de formar el libro diario detalle
//									if(blnCumpleSubTipoOperacion){
//									//if(blnExisteRiesgo && blnExisteCredito){
//										diarioDet.setStrContNumeroCuenta(modeloDetalle.getId().getStrContNumeroCuenta());
//										diarioDet.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
//										diarioDet.setIntContPeriodo(modeloDetalle.getId().getIntContPeriodoCuenta());
//										diarioDet.setIntPersEmpresaCuenta(modeloDetalle.getId().getIntPersEmpresaCuenta());
//										diarioDet.setIntPersPersona(socioComp.getPersona().getIntIdPersona());
//										diarioDet.setIntPersEmpresaSucursal(Constante.PARAM_EMPRESASESION);
//										diarioDet.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
//										diarioDet.setIntSudeIdSubSucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
//										//CGD-26.12.2013
//										diarioDet.setStrComentario(strObservacionPlanCta);
//										//CGD-24.01.2014
//										diarioDet.setStrNumeroDocumento(socioComp.getPersona().getIntIdPersona()+"-"+expedientePrevision.getId().getIntItemExpediente());
//										
//										//diarioDet.set
//										if(modeloDetalle.getIntParaOpcionDebeHaber().compareTo(Constante.PARAM_T_LIBRO_DIARIO_DEBE)==0){
//											diarioDet.setIntParaDocumentoGeneral(modeloDetalle.getIntParaDocumentoGral());
//											diarioDet.setBdDebeSoles(bdValorColumna);
//											diarioDet.setBdHaberSoles(null);
//											// debe  bdValorColumna
//										}else if(modeloDetalle.getIntParaOpcionDebeHaber().compareTo(Constante.PARAM_T_LIBRO_DIARIO_HABER)==0){
//											// haber   bdValorColumna
//											diarioDet.setIntParaDocumentoGeneral(modeloDetalle.getIntParaDocumentoGral());
//											diarioDet.setBdHaberSoles(bdMontoTotal);
//											diarioDet.setBdDebeSoles(null);
//										}
//										lstDiarioDetalle.add(diarioDet);
//									}
//
//										
//									}
//								
//								}
//
//						//}
//						
//						diario.setListaLibroDiarioDetalle(lstDiarioDetalle);
//						/*System.out.println("===============================================================");
//						for (LibroDiarioDetalle detalle : lstDiarioDetalle) {
//							System.out.println("NRO CUENTA ---> "+detalle.getStrContNumeroCuenta());
//							System.out.println("DEBE ---> "+detalle.getBdDebeSoles());
//							System.out.println("HABER ---> "+detalle.getBdHaberSoles());
//						}
//						System.out.println("===============================================================");*/
//						diario = libroDiarioFacade.grabarLibroDiario(diario);
//					//}

				}catch(BusinessException e){
					System.out.println("Error en obtieneLibroDiarioDetalleRefinanciamiento 1 --> "+e);
				}catch(Exception e){
					System.out.println("Error en obtieneLibroDiarioDetalleRefinanciamiento 2 --> "+e);
				}
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
		
		/**
		 * Calcula el interes generado por el fondo de retiro MAs el saldo actual del ineteres ganado. 
		 * Modificado 26.04.2014
		 * Calcula el interes generado por el fondo de retiro, mas el interes ya generado. Para la solicitud de prevision Retiro.
		 * @param cuentaConceptoRetiro
		 * @param expedientePrevision
		 * @return
		 */
		public BigDecimal calcularInteresRetiroAcumulado(CuentaConcepto cuentaConceptoRetiro, ExpedientePrevision expedientePrevision){
			List<Movimiento> listaMovimiento = null; 
			Movimiento movimientoInteresUltimo = null;
			BigDecimal bdInteresCalculado = BigDecimal.ZERO;
			Captacion beanCaptacion = null;
			CaptacionFacadeRemote captacionFacade = null;
			ConceptoFacadeRemote conceptoFacadeRemote= null;
			BigDecimal bdMontoInteresMasSaldoInteres = BigDecimal.ZERO;
			CuentaConceptoDetalle cuentaConceptoDetalleRetiro = new CuentaConceptoDetalle();
//			List<CuentaConceptoDetalle> lstCtaCtpoDet = null;
			try {
				
				captacionFacade = (CaptacionFacadeRemote)EJBFactory.getRemote(CaptacionFacadeRemote.class);
				conceptoFacadeRemote = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
				
				CaptacionId captacionId = new CaptacionId();
				captacionId.setIntPersEmpresaPk(expedientePrevision.getIntPersEmpresa());
				captacionId.setIntParaTipoCaptacionCod(expedientePrevision.getIntParaTipoCaptacion());
				captacionId.setIntItem(expedientePrevision.getIntItem());

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
//							CuentaConceptoDetalle ccd = new CuentaConceptoDetalle();
////							Integer intItemCtaCpto = null;
//							ccd.setId(new CuentaConceptoDetalleId());
//							ccd.getId().setIntPersEmpresaPk(cuentaConceptoRetiro.getId().getIntPersEmpresaPk());
//							ccd.getId().setIntCuentaPk(cuentaConceptoRetiro.getId().getIntCuentaPk());
//							ccd.setIntParaTipoConceptoCod(Constante.PARAM_T_CUENTACONCEPTO_RETIRO);
//							lstCtaCtpoDet = conceptoFacadeRemote.getCuentaConceptoDetallePorPKCuentaYTipoConcepto(ccd);
							List<Movimiento> lstMovCapital = null;
//							if (lstCtaCtpoDet!=null && !lstCtaCtpoDet.isEmpty()) {
//								for (CuentaConceptoDetalle listCCD : lstCtaCtpoDet) {
//									lstMovCapital = conceptoFacadeRemote.getListaMovimientoPorCtaItemConceptoTipoConceptoGeneral(listCCD.getId().getIntPersEmpresaPk(),
//																		listCCD.getId().getIntCuentaPk(),listCCD.getId().getIntItemCuentaConcepto(),
//																		Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO);
//									break;
//								}
//							}
							
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
		
//
//		/**
//		 * Calcula el interes generado por el fondo de retiro. Para lasolicitud de prevision Retiro.
//		 * @param cuentaConceptoRetiro
//		 * @param expedientePrevision
//		 * @return
//		 */
//		public BigDecimal calcularInteresRetiro(CuentaConcepto cuentaConceptoRetiro, ExpedientePrevision expedientePrevision){
//			List<Movimiento> listaMovimiento = null; 
//			Movimiento movimientoInteresUltimo = null;
//			BigDecimal bdInteresCalculado = BigDecimal.ZERO;
//			Captacion beanCaptacion = null;
//			CaptacionFacadeRemote captacionFacade = null;
//			ConceptoFacadeRemote conceptoFacadeRemote= null;
//			List<CuentaConceptoDetalle> lstCtaCtpoDet = null;
//			
//			try {
//				
//				captacionFacade = (CaptacionFacadeRemote)EJBFactory.getRemote(CaptacionFacadeRemote.class);
//				conceptoFacadeRemote = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
//				
//				CaptacionId captacionId = new CaptacionId();
//				captacionId.setIntPersEmpresaPk(expedientePrevision.getIntPersEmpresa());
//				captacionId.setIntParaTipoCaptacionCod(expedientePrevision.getIntParaTipoCaptacion());
//				captacionId.setIntItem(expedientePrevision.getIntItem());
//
//				beanCaptacion = captacionFacade.getCaptacionPorIdCaptacion(captacionId);
//							
//							
//				if(beanCaptacion != null){
//					
//					// 1. Se recupera el porcentaje del ineters a aplicar.
//					//beanCaptacion.getIntTasaInteres();
//					
//					// 2. Recuperamos las llaves de la cuenta cto retiro, para obetener los movimientos de interes de retiro.
//					//cuentaConceptoRetiro = recuperarCuentaConceptoBase(Constante.PARAM_T_CUENTACONCEPTO_RETIRO);
//					if(cuentaConceptoRetiro != null){
//						listaMovimiento = conceptoFacadeRemote.getListaMovimientoPorCtaItemConceptoTipoConceptoGeneral(cuentaConceptoRetiro.getId().getIntPersEmpresaPk(), 
//								cuentaConceptoRetiro.getId().getIntCuentaPk(), 
//								cuentaConceptoRetiro.getId().getIntItemCuentaConcepto(), 
//								Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO_INTERES);
//						
//						if(listaMovimiento != null && !listaMovimiento.isEmpty()){
//							//Ordenamos los subtipos por int
//							Collections.sort(listaMovimiento, new Comparator<Movimiento>(){
//								public int compare(Movimiento uno, Movimiento otro) {
//									return uno.getIntItemMovimiento().compareTo(otro.getIntItemMovimiento());
//								}
//							});
//							
//							System.out.println("************************************************************");
//							for(int k=0; k<listaMovimiento.size();k++){
//								System.out.println("LIST MOVMIENTOS ---> "+listaMovimiento.get(k).getIntItemMovimiento());	
//								System.out.println("LIST FECHA DE MOVIMIENTO---> "+listaMovimiento.get(k).getTsFechaMovimiento());	
//								System.out.println("MONTO SALDO ---> "+listaMovimiento.get(k).getBdMontoSaldo());	
//								System.out.println("MONTO MOVIMIENTO ---> "+listaMovimiento.get(k).getBdMontoMovimiento());	
//							}
//							System.out.println("************************************************************");	
//							
//							Integer tamanno = 0;
//							tamanno = listaMovimiento.size();
//							tamanno = tamanno -1;
//							// recuperamos el ultimo registro y en base a le se realiza calculo...
//							movimientoInteresUltimo = new Movimiento();
//							movimientoInteresUltimo = listaMovimiento.get(tamanno);
//							
//							BigDecimal bdTotalBaseCtaMasInt = BigDecimal.ZERO;
//							BigDecimal bdUltimoCapRetiro = BigDecimal.ZERO;
//							
//							// a. 	Calculamos el monto base para el calculo de interes
//							//bdTotalBaseCtaMasInt = movimientoInteresUltimo.getBdMontoSaldo().add(movimientoInteresUltimo.getBdMontoSaldo());
//							bdTotalBaseCtaMasInt = movimientoInteresUltimo.getBdMontoSaldo();
//							// Agregado 23.05.2014 jchavez. ademas del utimo interes se debe de recuperar de movimiento la ultima 
//							// amortizacion de capital de fdo. retiro.
//							CuentaConceptoDetalle ccd = new CuentaConceptoDetalle();
////							Integer intItemCtaCpto = null;
//							ccd.setId(new CuentaConceptoDetalleId());
//							ccd.getId().setIntPersEmpresaPk(cuentaConceptoRetiro.getId().getIntPersEmpresaPk());
//							ccd.getId().setIntCuentaPk(cuentaConceptoRetiro.getId().getIntCuentaPk());
//							ccd.setIntParaTipoConceptoCod(Constante.PARAM_T_CUENTACONCEPTO_RETIRO);
//							lstCtaCtpoDet = conceptoFacadeRemote.getCuentaConceptoDetallePorPKCuentaYTipoConcepto(ccd);
//							List<Movimiento> lstMovCapital = null;
//							if (lstCtaCtpoDet!=null && !lstCtaCtpoDet.isEmpty()) {
//								for (CuentaConceptoDetalle listCCD : lstCtaCtpoDet) {
////									intItemCtaCpto = listCCD.getId().getIntItemCuentaConcepto();
//									lstMovCapital = conceptoFacadeRemote.getListaMovimientoPorCtaItemConceptoTipoConceptoGeneral(listCCD.getId().getIntPersEmpresaPk(),
//																		listCCD.getId().getIntCuentaPk(),listCCD.getId().getIntItemCuentaConcepto(),
//																		Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO);
//									break;
//								}
//							}
//							if (lstMovCapital!=null && !lstMovCapital.isEmpty()) {
//								bdUltimoCapRetiro = lstMovCapital.get(0).getBdMontoSaldo();
//							}
//							// b. Recuperamos nro de dias entres fecha de movimeitno y hoy.
//							Date dtFechaUltimoInteres = new Date();
//							String strFechaUltimoInteres = Constante.sdf.format(movimientoInteresUltimo.getTsFechaMovimiento());
//							dtFechaUltimoInteres = Constante.sdf.parse(strFechaUltimoInteres);
//							
//							Date dtHoy = new Date();
//
//							Integer nroDias =  obtenerDiasEntreFechas(dtFechaUltimoInteres,dtHoy);
//							nroDias = Math.abs(nroDias);
//							
//							
//							if(nroDias.compareTo(0)!= 0){
//								// c. Se aplica formula simple de interes --> Monto*Porc Interes*(dias/30)
////								bdInteresCalculado =  bdTotalBaseCtaMasInt.multiply(new BigDecimal(nroDias).multiply(beanCaptacion.getBdTem())).divide(new BigDecimal(30),2,RoundingMode.HALF_UP);
//								bdInteresCalculado =  (bdTotalBaseCtaMasInt.add(bdUltimoCapRetiro)).multiply(new BigDecimal(nroDias).multiply(beanCaptacion.getBdTem())).divide(new BigDecimal(3000),2,RoundingMode.HALF_UP);
////								bdInteresAcumulado = bdInteresCalculado.add(bdTotalBaseCtaMasInt);
//							}else{
////								bdInteresCalculado = BigDecimal.ZERO;
//								bdInteresCalculado = BigDecimal.ZERO;
////								bdInteresAcumulado= BigDecimal.ZERO;
//							}
//						}
//					}
//				}
//				
//			} catch (Exception e) {
//				log.error("Error en calcularInteresRetiro ---> "+e);
//			}
//			return bdInteresCalculado;
//		}
//		
		
		/**
		 * Recuperar ultimo movimeinto de interes de retiro
		 * @param cuentaConceptoRetiro
		 * @param expedientePrevision
		 * @return
		 */
		public Movimiento recuperarUltimoMovimeintoInteresRetiro(CuentaConcepto cuentaConceptoRetiro, ExpedientePrevision expedientePrevision){
			List<Movimiento> listaMovimiento = null; 
			///CuentaConcepto cuentaConceptoRetiro = null;
			Movimiento movimientoInteresUltimo = null;
//			BigDecimal bdMontoTotal = BigDecimal.ZERO;
//			Integer intNroDias = 0;
//			BigDecimal bdPorcentajeInteres = BigDecimal.ZERO;	
//			BigDecimal bdInteresCalculado = BigDecimal.ZERO;
			Captacion beanCaptacion = null;
			CaptacionFacadeRemote captacionFacade = null;
			ConceptoFacadeRemote conceptoFacadeRemote= null;

			try {
				
				captacionFacade = (CaptacionFacadeRemote)EJBFactory.getRemote(CaptacionFacadeRemote.class);
				conceptoFacadeRemote = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
				
				CaptacionId captacionId = new CaptacionId();
				captacionId.setIntPersEmpresaPk(expedientePrevision.getIntPersEmpresa());
				captacionId.setIntParaTipoCaptacionCod(expedientePrevision.getIntParaTipoCaptacion());
				captacionId.setIntItem(expedientePrevision.getIntItem());

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
		
		/**
		 * 
		 * @param dtFechaInicio
		 * @param dtFechaFin
		 * @return
		 * @throws Exception
		 */
		public static Integer obtenerDiasEntreFechas(Date dtFechaInicio, Date dtFechaFin)throws Exception{
			return (int)( (dtFechaFin.getTime() - dtFechaInicio.getTime()) / (1000 * 60 * 60 * 24) );
		}
		
		
		/**
		 * Se recupera cuentas conceptos comp de retiro , aportes , sepelio,etc.
		 * Para el proceso de liquidacion de cuentas.
		 * @param idCuenta
		 * @return
		 */
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
		
		
		/**
		 * Le adiciona el concepto de INTERES a la lista de cuentas concepto del socio. Solo para fines practicos.
		 * Interes no es cuenta concepto
		 * @param cuentaConceptoInteresRetiro
		 * @return
		 */
		public CuentaConceptoComp recuperarCuentasConceptoInteresRetiro(CuentaConcepto cuentaConceptoRetiro){
//			List<CuentaConcepto> listaCuentaConcepto = null;
//			List<CuentaConceptoComp> listaCuentaConceptoComp = null;
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
							null);
					//Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO_INTERES);
					
					if(listaMovimiento != null && !listaMovimiento.isEmpty()){
						cuentaConceptoInteres = new CuentaConcepto();
						cuentaConceptoInteresComp = new CuentaConceptoComp();
						
						for (Movimiento movimiento : listaMovimiento) {
							bdMonto = bdMonto.add(movimiento.getBdMontoMovimiento());
						}

						cuentaConceptoInteres.setBdSaldo(bdMonto);
						cuentaConceptoInteresComp.setCuentaConcepto(cuentaConceptoInteres);
						cuentaConceptoInteresComp.setIntParaTipoCaptacionModelo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO);
					}
	
				}
				
			} catch (Exception e) {
				log.error("Error en recuperarCuentasConceptoInteresRetiro ---> "+e);
			}
			
			return cuentaConceptoInteresComp;
		}

		/**
		 * Recupera el año actual (String)
		 * @param date
		 * @return
		 */
	   public static String  obtieneAnio(Date date){
			java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("yyyy");
			String anio = sdf.format(date);
			return anio;
		}
		
		
		
	   /**
	    * Recupera el mes actual (String)
	    * @param date
	    * @return
	    */
		 public static final String obtieneMesCadena(Date date){
	  		java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("MM");
	  		String minuto = sdf.format(date);
	  		return minuto;
	  	}
					 
		
		 /**
		  * Recupera fecha actual en timestamp
		  * @return
		  */
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
		 public SolicitudCtaCte grabarSolicitudCtaCteParaPrevisionDevolucionRetiro(SocioComp socioComp,Integer strPeriodo, RequisitoPrevision requisitoPrev, Usuario usuario,
				 ExpedientePrevision expedientePrevision, LibroDiario libroDiario) 
		 	throws EJBFactoryException, BusinessException {
			 SolicitudCtaCte solicitudCtaCteDevolucion = null;
			 EstadoSolicitudCtaCte estadoPendiente = null;
			 EstadoSolicitudCtaCte estadoAtendido = null;

			 CuentacteFacadeRemote cuentaCteFacadeRemote= null;
			 
			 try {
				 Calendar fecHoy = Calendar.getInstance();
				 Date dtAhora = fecHoy.getTime();
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
						solicitudCtaCteDevolucion.setIntParaTipo(requisitoPrev.getIntParaTipoArchivo());
						solicitudCtaCteDevolucion.setIntMaeItemarchivo(requisitoPrev.getIntParaItemArchivo());		
						solicitudCtaCteDevolucion.setIntMaeItemhistorico(requisitoPrev.getIntParaItemHistorico());
						 
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
							 estadoPendiente.setStrEsccObservacion("Devolucion de Prevision Retiro - Estado Pendiente. "+
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
							 estadoAtendido.setStrEsccObservacion("Devolucion de Prevision Retiro - Estado Atendido. "+
											 usuario.getSucursal().getId().getIntIdSucursal()+"-"+usuario.getSubSucursal().getId().getIntIdSubSucursal()+""+
											 usuario.getIntPersPersonaPk());
							 estadoAtendido = cuentaCteFacadeRemote.grabarEstadoSolicitudCtaCte(estadoAtendido);
							 lstEstadosSolCta.add(estadoAtendido);
							 
					//Generando la Solicitud Tipo
							 SolicitudCtaCteTipo solCtaCteTipo = new SolicitudCtaCteTipo();
							 SolicitudCtaCteTipoId solCtaCteTipoId = new SolicitudCtaCteTipoId();
							 
							 solCtaCteTipoId.setIntCcobItemsolctacte(solicitudCtaCteDevolucion.getId().getIntCcobItemsolctacte());
							 solCtaCteTipoId.setIntPersEmpresasolctacte(solicitudCtaCteDevolucion.getId().getIntEmpresasolctacte());
							 solCtaCteTipoId.setIntTipoSolicitudctacte(Constante.PARAM_T_SOLICITUD_CTACTE_TIPO_DEVOLUCION);
							 solCtaCteTipo.setId(solCtaCteTipoId);
							 solCtaCteTipo.setIntTaraEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
							 solCtaCteTipo.setIntParaTipoorigen(Constante.PARAM_T_TIPO_ORIGEN_SOLICITUD_CUENTA_CORRIENTE);
							 solCtaCteTipo.setIntParaEstadoanalisis(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
							 solCtaCteTipo.setStrScctObservacion("Prevision Retiro - Devolucion. ");
							 solCtaCteTipo.setDtFechaDocumento(dtAhora);
							 solCtaCteTipo.setIntMotivoSolicitud(Constante.PARAM_T_MOTIVO_SOLICITUD_CTACTE_PROVISION_FONDO_RETIRO); // 249-22
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
			 public SolicitudCtaCte grabarSolicitudCtaCteParaPrevisionDevolucionSepelio(SocioComp socioComp,Integer strPeriodo, RequisitoPrevision requisitoPrev, Usuario usuario,
					 ExpedientePrevision expedientePrevision, LibroDiario libroDiario) 
			 	throws EJBFactoryException, BusinessException {
				 SolicitudCtaCte solicitudCtaCteDevolucion = null;
				 EstadoSolicitudCtaCte estadoPendiente = null;
				 EstadoSolicitudCtaCte estadoAtendido = null;

				 CuentacteFacadeRemote cuentaCteFacadeRemote= null;
				 
				 try {
					 Calendar fecHoy = Calendar.getInstance();
					 Date dtAhora = fecHoy.getTime();
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
							solicitudCtaCteDevolucion.setIntParaTipo(requisitoPrev.getIntParaTipoArchivo());
							solicitudCtaCteDevolucion.setIntMaeItemarchivo(requisitoPrev.getIntParaItemArchivo());		
							solicitudCtaCteDevolucion.setIntMaeItemhistorico(requisitoPrev.getIntParaItemHistorico());
							 
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
								 estadoPendiente.setStrEsccObservacion("Por Devolucion de Prevision Retiro - Estado Pendiente. "+
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
								 estadoAtendido.setStrEsccObservacion("Por Devolucion de Prevision Retiro - Estado Atendido. "+
												 usuario.getSucursal().getId().getIntIdSucursal()+"-"+usuario.getSubSucursal().getId().getIntIdSubSucursal()+""+
												 usuario.getIntPersPersonaPk());
								 estadoAtendido = cuentaCteFacadeRemote.grabarEstadoSolicitudCtaCte(estadoAtendido);
								 lstEstadosSolCta.add(estadoAtendido);
								 
						//Generando la Solicitud Tipo
								 SolicitudCtaCteTipo solCtaCteTipo = new SolicitudCtaCteTipo();
								 SolicitudCtaCteTipoId solCtaCteTipoId = new SolicitudCtaCteTipoId();
								 
								 solCtaCteTipoId.setIntCcobItemsolctacte(solicitudCtaCteDevolucion.getId().getIntCcobItemsolctacte());
								 solCtaCteTipoId.setIntPersEmpresasolctacte(solicitudCtaCteDevolucion.getId().getIntEmpresasolctacte());
								 solCtaCteTipoId.setIntTipoSolicitudctacte(Constante.PARAM_T_SOLICITUD_CTACTE_TIPO_DEVOLUCION);
								 solCtaCteTipo.setId(solCtaCteTipoId);
								 solCtaCteTipo.setIntTaraEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
								 solCtaCteTipo.setIntParaTipoorigen(Constante.PARAM_T_TIPO_ORIGEN_SOLICITUD_CUENTA_CORRIENTE);
								 solCtaCteTipo.setIntParaEstadoanalisis(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
								 solCtaCteTipo.setStrScctObservacion("Generado por autorizacion de Prevision Sepelio - Devolucion. ");
								 solCtaCteTipo.setDtFechaDocumento(dtAhora);
								 solCtaCteTipo.setIntMotivoSolicitud(Constante.PARAM_T_MOTIVO_SOLICITUD_CTACTE_PROVISION_FONDO_SEPELIO); // 249-24
								 //solCtaCteTipo.setIntEmpresaLibro(0); 		// Temporalmente hasta q se genere el asiento contable
								 //solCtaCteTipo.setIntContPeriodolibro(0);	// Temporalmente hasta q se genere el asiento contable
								 //solCtaCteTipo.setIntCodigoLibro(0);		// Temporalmente hasta q se genere el asiento contable
								 solCtaCteTipo.setIntPersUsuario(usuario.getIntPersPersonaPk());
								 solCtaCteTipo.setIntCcobItemefectuado(null);
								 
								 solCtaCteTipo = cuentaCteFacadeRemote.grabarSolicitudCtaCteTipo(solCtaCteTipo);
								 if(solCtaCteTipo != null){
									 List<SolicitudCtaCteTipo> lstSolCtaCteTipo= new ArrayList<SolicitudCtaCteTipo>();
									 lstSolCtaCteTipo.add(solCtaCteTipo);
									 solicitudCtaCteDevolucion.setListaSolCtaCteTipo(lstSolCtaCteTipo);
								 }else{
									 throw new BusinessException("No se genero la solicitud cta cte tipo Sepilo.");
								 }
							 }else{
								 throw new BusinessException("No se genero la solicitud cta cte Sepilo.");
							 }
						 }

				} catch (Exception e) {
					log.error("Error en grabarSolicitudCtaCteParaPrevisionDevolucionSepelio ---> "+e);
					throw new BusinessException("No se genero la solicitud cta cte tipo Sepilo.");
				}
				 return solicitudCtaCteDevolucion; 
			 }
		 
			 
		 /**
		  * Genera y registra la Devolucion, segun solciitud de Cta Cte.
		  * @param solicitudCtaCteDevolucion
		  * @return
		  */
		 public Devolucion generarDevolucionRetiro(SolicitudCtaCte solicitudCtaCte, List<Movimiento> listaMovientosPrevision, SocioComp socioComp)throws BusinessException {
			 Devolucion devolucion = null;
			 DevolucionId devolucionId = null;
			 BigDecimal bdMontoDevolucion = BigDecimal.ZERO;
			 CuentacteFacadeRemote cuentaCteFacade = null;
			 try {
				 
				 cuentaCteFacade = (CuentacteFacadeRemote)EJBFactory.getRemote(CuentacteFacadeRemote.class);
				 if(listaMovientosPrevision != null && !listaMovientosPrevision.isEmpty()){
					 
					 for (Movimiento movimiento : listaMovientosPrevision) {
						if(movimiento.getIntParaTipoConceptoGeneral().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_CTAXPAGAR)==0){
							bdMontoDevolucion = movimiento.getBdMontoMovimiento();
							break;
						}
					}
					 
					 //movSaldoCuentaConcepto.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO);
					 
					 devolucionId = new DevolucionId();
					 devolucion =  new Devolucion();
					 devolucionId.setIntPersEmpresaDevolucion(Constante.PARAM_EMPRESASESION);
					 devolucion.setId(devolucionId);
					 devolucion.setTsDevoFecha(new Timestamp((new Date()).getTime()));
					 devolucion.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_DEVOLUCION);
					 devolucion.setIntCuenta(socioComp.getCuenta().getId().getIntCuenta());
					 devolucion.setBdDevoMonto(bdMontoDevolucion);
					 devolucion.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					 devolucion.setIntParaEstadoPago(Constante.PARAM_T_ESTADO_PAGO_PENDIENTE);
					 
					 devolucion.setIntPersEmpresaSolCtaCte(solicitudCtaCte.getId().getIntEmpresasolctacte());
					 devolucion.setIntCcobItemSolCtaCte(solicitudCtaCte.getId().getIntCcobItemsolctacte());
					 devolucion.setIntParaTipoSolicitudCtaCte(Constante.PARAM_T_SOLICITUD_CTACTE_TIPO_DEVOLUCION);
					 /*
					 System.out.println("id getIntPersEmpresaDevolucion ---> "+devolucion.getId().getIntPersEmpresaDevolucion());
					 System.out.println("id getIntItemDevolucion ---> "+devolucion.getId().getIntItemDevolucion());					 
					 System.out.println("devolucion.getTsDevoFecha() ---> "+devolucion.getTsDevoFecha());
					 System.out.println("getIntParaDocumentoGeneral() ---> "+devolucion.getIntParaDocumentoGeneral());
					 System.out.println("getIntCuenta() ---> "+devolucion.getIntCuenta());
					 System.out.println("getBdDevoMonto() ---> "+devolucion.getBdDevoMonto());
					 System.out.println("getIntParaEstado() ---> "+devolucion.getIntParaEstado());
					 System.out.println("getIntParaEstadoPago() ---> "+devolucion.getIntParaEstadoPago());
					 System.out.println("getIntPersEmpresaSolCtaCte() ---> "+devolucion.getIntPersEmpresaSolCtaCte());
					 System.out.println("getIntCcobItemSolCtaCte() ---> "+devolucion.getIntCcobItemSolCtaCte());
					 System.out.println("getIntParaTipoSolicitudCtaCte() ---> "+devolucion.getIntParaTipoSolicitudCtaCte());
					 */
					 
					 devolucion = cuentaCteFacade.grabarDevolucion(devolucion); 
				 }

			} catch (Exception e) {
				log.error("Error en generarDevolucionRetiro ---> "+e);
			}
			 return devolucion;
		 }
		 
		 
		 /**
		  * Genera y registra la Devolucion, segun solciitud de Cta Cte.
		  * @param solicitudCtaCteDevolucion
		  * @return
		  */
		 public Devolucion generarDevolucionSepelio(SolicitudCtaCte solicitudCtaCte, List<Movimiento> listaMovientosPrevision, SocioComp socioComp, ExpedientePrevision expedientePrevision)throws BusinessException {
			 Devolucion devolucion = null;
			 DevolucionId devolucionId = null;
			 BigDecimal bdMontoDevolucion = BigDecimal.ZERO;
			 CuentacteFacadeRemote cuentaCteFacade = null;
			 try {
				 
				 cuentaCteFacade = (CuentacteFacadeRemote)EJBFactory.getRemote(CuentacteFacadeRemote.class);
					bdMontoDevolucion = expedientePrevision.getBdMontoNetoBeneficio();

					 devolucionId = new DevolucionId();
					 devolucion =  new Devolucion();
					 devolucionId.setIntPersEmpresaDevolucion(Constante.PARAM_EMPRESASESION);
					 devolucion.setId(devolucionId);
					 devolucion.setTsDevoFecha(new Timestamp((new Date()).getTime()));
					 devolucion.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_DEVOLUCION);
					 devolucion.setIntCuenta(socioComp.getCuenta().getId().getIntCuenta());
					 devolucion.setBdDevoMonto(bdMontoDevolucion);
					 devolucion.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					 devolucion.setIntParaEstadoPago(Constante.PARAM_T_ESTADO_PAGO_PENDIENTE);
					 devolucion.setIntPersEmpresaSolCtaCte(solicitudCtaCte.getId().getIntEmpresasolctacte());
					 devolucion.setIntCcobItemSolCtaCte(solicitudCtaCte.getId().getIntCcobItemsolctacte());
					 devolucion.setIntParaTipoSolicitudCtaCte(Constante.PARAM_T_SOLICITUD_CTACTE_TIPO_DEVOLUCION);
					 devolucion = cuentaCteFacade.grabarDevolucion(devolucion); 

			} catch (Exception e) {
				log.error("Error en generarDevolucionSepelio ---> "+e);
			}
			 return devolucion;
		 }

		 
		 /**
		  * 
		  * @param intBusqTipo
		  * @param strBusqCadena
		  * @param strBusqNroSol
		  * @param intTipoCreditoFiltro
		  * @param intSubTipoCreditoFiltro
		  * @param intBusqEstado
		  * @param intBusqSucursal
		  * @param intBusqSubSucursal
		  * @return
		  */
		 public List<ExpedientePrevisionComp> getListaBusqExpPrevFiltros(ExpedientePrevisionComp expPrevComp) throws BusinessException{
			 List<ExpedientePrevision> lstPrevisionTemp=null;
			 List<ExpedientePrevisionComp> lstPrevision= null;
			 
			 try {
				 lstPrevisionTemp = boExpedientePrevision.getListaBusqExpPrevFiltros(expPrevComp);
				 
				 if(lstPrevisionTemp != null && !lstPrevisionTemp.isEmpty()){
					 lstPrevision = completarDatosExpPrevision_BusqFiltros(lstPrevisionTemp);

				 }
			} catch (Exception e) {
				log.error("Error en getListaBusqExpPrevFiltros ---> "+e);			
			}
			return lstPrevision;
		 }
		 
		 
		 /**
		  * Completa los datos faltantes de la lista recuperada de la consulta en base a filtros de prevision
		  * @param lstExpediente
		  * @return
		  */
		 public List<ExpedientePrevisionComp> completarDatosExpPrevision_BusqFiltros(List<ExpedientePrevision> lstExpediente){
				List<ExpedientePrevisionComp> lstExpComp = null;
				try {
					
					// Estados Req, Sol y Aut
					if(lstExpediente != null && !lstExpediente.isEmpty()){
						lstExpComp = new ArrayList<ExpedientePrevisionComp>();
						
						for (ExpedientePrevision expedientePrevision : lstExpediente) {
							List<EstadoPrevision> listaEstadoPrevision = null;
//							List<ExpedientePrevision> listaExpedientePrevision = null;

							ExpedientePrevisionComp expPrevisionComp = new ExpedientePrevisionComp();
							expPrevisionComp.setExpedientePrevision(expedientePrevision);
							
							// Estados
							listaEstadoPrevision = boEstadoPrevision.getPorExpediente(expedientePrevision);
							if(listaEstadoPrevision!=null && !listaEstadoPrevision.isEmpty()){
								for (EstadoPrevision estado : listaEstadoPrevision) {
									if(estado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO))
										expPrevisionComp.setStrFechaRequisito(Constante.sdf.format(estado.getTsFechaEstado()));
									if(estado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD))
										expPrevisionComp.setStrFechaSolicitud(Constante.sdf.format(estado.getTsFechaEstado()));
									if(estado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)){
										expPrevisionComp.setStrFechaAutorizacion(Constante.sdf.format(estado.getTsFechaEstado()));
									}
								}
							}
							lstExpComp.add(expPrevisionComp);
						}
					}

				} catch (Exception e) {
					log.error("Error en completarDatosExpPrevision_BusqFiltros ---> "+e);
				}
				return lstExpComp;
			}
		 
		/* public List<ExpedientePrevisionComp> getBuscarRefinanciamientoCompFiltros(Integer intBusqTipo,String strBusqCadena,String strBusqNroSol,
				 																	Integer intTipoCreditoFiltro, Integer intSubTipoCreditoFiltro, 
				 																	Integer intBusqEstado, Integer intBusqSucursal , Integer intBusqSubSucursal,
				 																	Date dtBusqFechaEstadoDesde, Date dtBusqFechaEstadoHasta) throws BusinessException{
			List<ExpedientePrevisionComp> listaPrevisionComp = null;
			List<ExpedientePrevision> lstExpedientePrevision = null;
			ExpedientePrevisionComp expPrevisionBusq= null;
				try {
					expPrevisionBusq = new ExpedientePrevisionComp();
					if(intBusqTipo != null ){
						expPrevisionBusq.setIntBusquedaTipo(intBusqTipo);
					}
					if(strBusqCadena.trim().length()==0){
						expPrevisionBusq.setStrBusqCadena("");
					}else{
						expPrevisionBusq.setStrBusqCadena(strBusqCadena.trim());
					}
					if(strBusqNroSol.trim().length()==0){
						expPrevisionBusq.setStrBusqNroSol("");
					}else{
						expPrevisionBusq.setStrBusqNroSol(strBusqNroSol.trim());
					}
					if(intTipoCreditoFiltro != null){
						expPrevisionBusq.setIntTipoCreditoFiltro(intTipoCreditoFiltro);
					}
					if(intSubTipoCreditoFiltro != null){
						expPrevisionBusq.setIntSubTipoCreditoFiltro(intSubTipoCreditoFiltro);
					}
					if(intBusqEstado!= null){
						expPrevisionBusq.setIntBusqEstado(intBusqEstado);
					}
					if(intBusqSucursal!= null){
						expPrevisionBusq.setIntBusqSucursal(intBusqSucursal);
					}
					if(intBusqSubSucursal!= null){
						expPrevisionBusq.setIntBusqSubSucursal(intBusqSubSucursal);
					}
					if(dtBusqFechaEstadoDesde != null){
						expPrevisionBusq.setDtBusqFechaEstadoDesde(dtBusqFechaEstadoDesde);	
					}else{
						expPrevisionBusq.setDtBusqFechaEstadoDesde(null);
					}
					if(dtBusqFechaEstadoHasta != null){
						expPrevisionBusq.setDtBusqFechaEstadoHasta(dtBusqFechaEstadoHasta);	
					}else{
						expPrevisionBusq.setDtBusqFechaEstadoHasta(null);
					}

					lstExpedientePrevision = boExpedientePrevision.getListaBusqPrevisionFiltros(expPrevisionBusq);
					if(lstExpedientePrevision != null && !lstExpedientePrevision.isEmpty()){
						listaPrevisionComp = new ArrayList<ExpedientePrevisionComp>();
						for (ExpedientePrevision expedientePrevision : lstExpedientePrevision) {
							ExpedientePrevisionComp expPrevComp = new ExpedientePrevisionComp();
							expPrevComp = cargarDatosComplementarios(expedientePrevision);
							expPrevComp.setExpedientePrevision(expedientePrevision);
							listaPrevisionComp.add(expPrevComp);	
						}
					}

				} catch (Exception e) {
					log.error("Error en getBuscarRefinanciamientoCompFiltros --> "+e);
				}
				return listaPrevisionComp;
				
		 }	*/

		 
		 /**
		  * Rercupera datos complementarios de los expedientees recueprados en la busqeda de bd
		  * @param expedientePrev
		  * @return
		  */
		 public ExpedientePrevisionComp cargarDatosComplementarios (ExpedientePrevision expedientePrev){
			 CuentaFacadeRemote cuentaFacade = null;
			 PersonaFacadeRemote personaFacade = null;
			 SocioFacadeRemote socioFacade= null;
			 
			 ExpedientePrevisionComp expPrevisionComp = new ExpedientePrevisionComp();
			 Integer intIdPersona = 0;
			 Persona persona = null;
			 SocioComp socioComp= null;
			 List<EstadoPrevision> listaEstados = null;
			 EstadoPrevision ultimoEstado = null;
			 
			 try { 
				 cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
				 personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				 socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
				 
				 // Recuperar persona administra
				 	CuentaId cuentaId = new CuentaId();
					List<CuentaIntegrante> lstCuentaIntegrante = null;
					cuentaId.setIntCuenta(expedientePrev.getId().getIntCuentaPk());
					cuentaId.setIntPersEmpresaPk(expedientePrev.getId().getIntPersEmpresaPk());

					// recuperamos el socio comp en base a la cuenta...
					lstCuentaIntegrante = cuentaFacade.getListaCuentaIntegrantePorPKCuenta(cuentaId);
					if(lstCuentaIntegrante != null && !lstCuentaIntegrante.isEmpty()){
						
						for (CuentaIntegrante cuentaIntegrante : lstCuentaIntegrante) {
							if(cuentaIntegrante.getIntParaTipoIntegranteCod().compareTo(new Integer(1))==0){
								intIdPersona = cuentaIntegrante.getId().getIntPersonaIntegrante();
								break;
							}
						}
						
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
							expPrevisionComp.setSocioComp(socioComp);
						}
						
					}

				 // SUcursal
				 
					
					
				 // estado						
						ultimoEstado = boEstadoPrevision.getMaxEstadoPrevisionPorPokExpediente(expedientePrev);
						expPrevisionComp.setEstadoPrevision(ultimoEstado);
						listaEstados = boEstadoPrevision.getPorExpediente(expedientePrev);
						if(listaEstados!=null && !listaEstados.isEmpty()){
							for (EstadoPrevision estadoPrev : listaEstados) {
								if(estadoPrev.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO))
									expPrevisionComp.setStrFechaUserRegistro(Constante.sdf.format(estadoPrev.getTsFechaEstado()));
								if(estadoPrev.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD))
									expPrevisionComp.setStrFechaSolicitud(Constante.sdf.format(estadoPrev.getTsFechaEstado()));
								if(estadoPrev.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO))
									expPrevisionComp.setStrFechaAutorizacion(Constante.sdf.format(estadoPrev.getTsFechaEstado()));
							}
						}
						expedientePrev.setListaEstadoPrevision(listaEstados);
					
				 // user registro
				 
				 //fecha registro
				 
				
			} catch (Exception e) {
				log.error("Error en cargarDatosComplementarios ---> "+e);
			}
			 return expPrevisionComp;
			 
			 
		 }
		 
		 
		 /**
		  * 
		  * @param expPrevComp
		  * @return
		  * @throws BusinessException
		  */
		 public List<ExpedientePrevisionComp> getListaBusqAutExpPrevFiltros(ExpedientePrevisionComp expPrevComp) throws BusinessException{
			 List<ExpedientePrevision> lstPrevisionTemp=null;
			 List<ExpedientePrevisionComp> lstPrevision= null;
			 
			 try {
				 lstPrevisionTemp = boExpedientePrevision.getListaBusqAutExpPrevFiltros(expPrevComp);
				 
				 if(lstPrevisionTemp != null && !lstPrevisionTemp.isEmpty()){
					 lstPrevision = completarDatosExpPrevision_BusqFiltros(lstPrevisionTemp);
				 }
			} catch (Exception e) {
				log.error("Error en getListaBusqAutExpPrevFiltros ---> "+e);			
			}
			return lstPrevision;
		 }
		 
		 
		 
		/**
		 * Graba estado observado / rechazado  en procesod e autorizacion
		 * @param estadoPrevisionSolicitud
		 * @param pPK
		 * @return
		 * @throws BusinessException
		 */
			public EstadoPrevision grabarEstadoPrevision(EstadoPrevision estadoPrevisionSolicitud, ExpedientePrevisionId pPK) throws BusinessException{
				EstadoPrevision estadoPrevision= null;
				EstadoPrevisionId pk = null;
				
				try{
					estadoPrevision = new EstadoPrevision();
					pk = new EstadoPrevisionId();
					
					pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
					pk.setIntCuentaPk(pPK.getIntCuentaPk());
					pk.setIntItemExpediente(pPK.getIntItemExpediente());
					//pk.set(pPK.getIntPersEmpresaPk());
					estadoPrevision.setId(pk);
					estadoPrevision.setIntPersEmpresaEstado(estadoPrevisionSolicitud.getIntPersEmpresaEstado());
					estadoPrevision.setIntSudeIdSubsucursal(estadoPrevisionSolicitud.getIntSudeIdSubsucursal());
					estadoPrevision.setIntSucuIdSucursal(estadoPrevisionSolicitud.getIntSucuIdSucursal());
					estadoPrevision.setIntParaEstado(estadoPrevisionSolicitud.getIntParaEstado());
					estadoPrevision.setIntPersUsuarioEstado(estadoPrevisionSolicitud.getIntPersUsuarioEstado());
					estadoPrevision.setSubsucursal(estadoPrevisionSolicitud.getSubsucursal());
					estadoPrevision.setSucursal(estadoPrevisionSolicitud.getSucursal());
					estadoPrevision.setTsFechaEstado(estadoPrevisionSolicitud.getTsFechaEstado());
					estadoPrevision = boEstadoPrevision.grabar(estadoPrevision);

				}catch(BusinessException e){
					log.error("Error - BusinessException - en grabarEstadoPrevision ---> "+e);
					throw e;
				}catch(Exception e){
					log.error("Error - Exception - en grabarEstadoPrevision ---> "+e);
					throw new BusinessException(e);
				}
				return estadoPrevision;
			}
}
