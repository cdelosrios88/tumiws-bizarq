package pe.com.tumi.cobranza.planilla.service;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import pe.com.tumi.cobranza.planilla.bo.EfectuadoBO;
import pe.com.tumi.cobranza.planilla.bo.EnvioconceptoBO;
import pe.com.tumi.cobranza.planilla.bo.EnviomontoBO;
import pe.com.tumi.cobranza.planilla.bo.EnvioresumenBO;
import pe.com.tumi.cobranza.planilla.domain.Efectuado;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoConcepto;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoResumen;
import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;
import pe.com.tumi.cobranza.planilla.domain.EnvioconceptoId;
import pe.com.tumi.cobranza.planilla.domain.Enviomonto;
import pe.com.tumi.cobranza.planilla.domain.EnviomontoId;
import pe.com.tumi.cobranza.planilla.domain.Envioresumen;
import pe.com.tumi.cobranza.planilla.domain.composite.EfectuadoConceptoComp;
import pe.com.tumi.cobranza.planilla.domain.composite.ItemPlanilla;
import pe.com.tumi.cobranza.planilla.domain.composite.PlanillaAdministra;
import pe.com.tumi.cobranza.planilla.facade.PlanillaFacadeLocal;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.FacesContextUtil;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegranteId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.facade.CuentaFacadeRemote;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.domain.SocioEstructuraPK;
import pe.com.tumi.credito.socio.core.domain.SocioPK;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.credito.socio.estructura.domain.AdminPadron;
import pe.com.tumi.credito.socio.estructura.domain.AdminPadronId;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.domain.Padron;
import pe.com.tumi.credito.socio.estructura.domain.Terceros;
import pe.com.tumi.credito.socio.estructura.domain.TercerosId;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;
import pe.com.tumi.empresa.domain.SubSucursalPK;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.contacto.facade.ContactoFacadeRemote;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CapacidadCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.facade.SolicitudPrestamoFacadeRemote;
import org.apache.log4j.Logger;

public class PlanillaService {
	
	private EnvioconceptoBO boEnvioconcepto   = (EnvioconceptoBO)TumiFactory.get(EnvioconceptoBO.class);
	private DescuentoService descuentoService = (DescuentoService)TumiFactory.get(DescuentoService.class);
	private EnviomontoBO  boEnviomonto 		  = (EnviomontoBO)TumiFactory.get(EnviomontoBO.class);
	private EfectuadoBO boEfectuado 		  = (EfectuadoBO)TumiFactory.get(EfectuadoBO.class);
	private EnvioresumenBO boEnvioresumen     = (EnvioresumenBO)TumiFactory.get(EnvioresumenBO.class);
	protected static Logger log = Logger.getLogger(PlanillaService.class);
	
	public Integer getOtraModalidad(Integer intSegundaModalidad, SocioEstructura o) throws BusinessException
	{
		Integer intOtraModalidad = 0;
		List<SocioEstructura> listaSocioEstructura = null;
		try{
			SocioFacadeRemote remoteSocio = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);			
			
			listaSocioEstructura = remoteSocio.getListaXNivelCodigoNoCas(o);
			if(listaSocioEstructura != null && !listaSocioEstructura.isEmpty())
			{
				for(SocioEstructura so: listaSocioEstructura)
				{
					if(so.getIntModalidad().compareTo(intSegundaModalidad)==0)
					{
						intOtraModalidad++;
						break;
					}
				}
			}
		}
		catch(BusinessException e){
			throw e;
		} catch (EJBFactoryException e) {
			throw new BusinessException(e);
		} catch(Exception e){
			throw new BusinessException(e);
		}
		log.debug(intOtraModalidad);
		return intOtraModalidad;
	}
	
	public List<Socio> getListaSocio(EstructuraId pk, Integer intTipoSocio) throws BusinessException
	{
		List<Socio> listaSocio = null;
		CuentaIntegrante cuentaIntegranteRspta = null;		
		List<SocioEstructura> listaSocioEstructura = null;
		List<Socio> listaSocioTemporal = new ArrayList<Socio>();
		try{
			SocioFacadeRemote remoteSocio 		= (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			CuentaFacadeRemote remoteCuenta 	= (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			
			SocioEstructura soci = new SocioEstructura();
			soci.setIntNivel(pk.getIntNivel());
			soci.setIntCodigo(pk.getIntCodigo());
			soci.setId(new SocioEstructuraPK());
			soci.getId().setIntIdEmpresa(Constante.PARAM_EMPRESASESION);			
			soci.setIntTipoSocio(intTipoSocio);
			
			listaSocio = remoteSocio.getLPorIdEstructuraTSMAE(soci);
				
					
					if(listaSocio != null && !listaSocio.isEmpty())
					{
						for(Socio lSocio: listaSocio)
						{						
							cuentaIntegranteRspta = remoteCuenta.getCuentaIntegrantePorPKSocioYTipoCuentaYTipoIntegrante(lSocio.getId(),
																														Constante.PARAM_T_TIPOCUENTA_ACTIVA,																
																														Constante.PARAM_T_SITUACIONCUENTA_VIGENTE);
							if(cuentaIntegranteRspta != null)
							{
								listaSocioTemporal.add(lSocio);
							}
						}
						listaSocio = listaSocioTemporal;
						for(Socio s: listaSocio)
						{
							
							listaSocioEstructura = remoteSocio.getListaXSocioPKActivoTipoSocio(s.getId().getIntIdPersona(),
																							   s.getId().getIntIdEmpresa(),
																							   Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO,
																							   intTipoSocio,
																								soci.getIntNivel(),
																								soci.getIntCodigo() );
							if(listaSocioEstructura!= null && !listaSocioEstructura.isEmpty())
							{								
								s.setListSocioEstructura(listaSocioEstructura);
							}
						}						
					}		
					
		}catch(BusinessException e){
			throw e;
		} catch (EJBFactoryException e) {
			throw new BusinessException(e);
		} catch(Exception e){
			throw new BusinessException(e);
		}
		
		return listaSocio;
	}
	
	public List<PlanillaAdministra> getPlanillaPorIdEstructuraYTipoSocioYPeriodo(EstructuraId pk,
																				 Integer intTipoSocio, 
																				 Integer intPeriodo,
																				 List<Socio> socios)
																				 throws BusinessException{
		log.info("getPlanillaPorIdEstructuraYTipoSocioYPeriodo=INICIO v6");				
		
		ItemPlanilla lItemPlanilla = null;
		List<ItemPlanilla> lista = null;
		Socio lSocio = null;		
		Documento lDocumento = null;
		Cuenta cuenta = null;
		Socio unSocio = null;
		SocioEstructura soci = null;
		CuentaId  cuentaTemporal = null;
		CuentaIntegrante cuentaIntegranteRspta = null;	
		BigDecimal bdCero = new BigDecimal(0);	
		List<Socio> listaSocio = null;
		List<PlanillaAdministra> listaAdministra = null;		
		List<SocioEstructura> listaCodigoPersona = null;
		PlanillaAdministra planillaPorAdministra= null;
		List<SocioEstructura> listaSocioEstructuraSoloAdministra = null;
		try{
			SocioFacadeRemote remoteSocio 			= (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			ContactoFacadeRemote remoteContacto 	= (ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);						
			CuentaFacadeRemote remoteCuenta 		= (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);						
			/**
			 * SOLO HABER E INCENTIVO
			 */
			soci = new SocioEstructura();
			soci.setIntNivel(pk.getIntNivel());
			soci.setIntCodigo(pk.getIntCodigo());
			soci.setId(new SocioEstructuraPK());
			soci.getId().setIntIdEmpresa(Constante.PARAM_EMPRESASESION);
			soci.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			soci.setIntTipoSocio(intTipoSocio);
			log.debug("tiposocio: "+intTipoSocio);
			listaAdministra = new ArrayList<PlanillaAdministra>();
			
			listaSocioEstructuraSoloAdministra = remoteSocio.getListaXAdminySubAdminHABERINCENT(soci); //traigo solo la empresa, sucursal, subsucursal
			
			if(listaSocioEstructuraSoloAdministra != null && !listaSocioEstructuraSoloAdministra.isEmpty())
			{
				for(SocioEstructura porAdministra:listaSocioEstructuraSoloAdministra)
				{				    
					planillaPorAdministra = new PlanillaAdministra();
					SubSucursalPK subSucursalPK = new SubSucursalPK();					
					subSucursalPK.setIntPersEmpresaPk(porAdministra.getId().getIntIdEmpresa());
					subSucursalPK.setIntIdSucursal(porAdministra.getIntIdSucursalAdministra());
					subSucursalPK.setIntIdSubSucursal(porAdministra.getIntIdSubsucurAdministra());
					planillaPorAdministra.setSubSucursalPK(subSucursalPK);
					
					soci.setIntIdSucursalAdministra(porAdministra.getIntIdSucursalAdministra());
					soci.setIntIdSubsucurAdministra(porAdministra.getIntIdSubsucurAdministra());
					
					listaCodigoPersona = remoteSocio.getListaXNivelCodigosoloHaberIncentivo(soci); //lista de solo codigosPersona
					
					if(listaCodigoPersona != null && !listaCodigoPersona.isEmpty())
					{						
					    listaSocio = null;
						listaSocio = new ArrayList<Socio>();
						for(SocioEstructura socioEstructura: listaCodigoPersona)
						{
							SocioPK socioId = new SocioPK();
							socioId.setIntIdEmpresa(planillaPorAdministra.getSubSucursalPK().getIntPersEmpresaPk());
							socioId.setIntIdPersona(socioEstructura.getId().getIntIdPersona());
							unSocio = remoteSocio.getSocioPorPK(socioId);
							if(unSocio != null)
							{
								soci.getId().setIntIdPersona(unSocio.getId().getIntIdPersona());
								List<SocioEstructura> listaSociEstru = remoteSocio.getListaPorCodPersonaOfEnviado(soci);
								unSocio.setListSocioEstructura(listaSociEstru);
								listaSocio.add(unSocio);
							}
						}
						
						if(listaSocio != null && !listaSocio.isEmpty())
						{				
							lista = new ArrayList<ItemPlanilla>();						
							cuenta           = new Cuenta();
							cuentaTemporal   = new CuentaId();

							for(int i=0; i < listaSocio.size(); i++)
							{
								lItemPlanilla = new ItemPlanilla();
								lItemPlanilla.setBdHaberes(bdCero);
								lItemPlanilla.setBdIncentivos(bdCero);																																																																				
								lItemPlanilla.setBdCas(bdCero);
								lItemPlanilla.setBdEnvioTotal(bdCero);
								lItemPlanilla.setBdHaberesI(bdCero);
								lItemPlanilla.setBdIncentivosI(bdCero);																																																																				
								lItemPlanilla.setBdCasI(bdCero);
								lSocio = listaSocio.get(i);					
								lItemPlanilla.setSocio(lSocio); 
								lItemPlanilla.setBlnLIC(Boolean.FALSE);
								lItemPlanilla.setBlnCartaAutorizacion(Boolean.FALSE);
								lItemPlanilla.setBlnDJUD(Boolean.FALSE);
								lItemPlanilla.setBlnAgregoTipoPlanilla(Boolean.FALSE);
								lItemPlanilla.setBlnEnvioConcepto(Boolean.FALSE);
								lItemPlanilla.setBlnTieneHaber(Boolean.FALSE);
								lItemPlanilla.setBlnTieneIncentivo(Boolean.FALSE);					
								
								for(SocioEstructura sora: lSocio.getListSocioEstructura())
								{
									if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(sora.getIntModalidad()) == 0)
									{
										lItemPlanilla.setBlnTieneHaber(Boolean.TRUE);
									}
									else if(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(sora.getIntModalidad()) == 0)
									{
										lItemPlanilla.setBlnTieneIncentivo(Boolean.TRUE);
									}
								}
								log.debug("lSocio.getId().getIntIdPersona()::: "+lSocio.getId().getIntIdPersona());
								lDocumento = remoteContacto.getDocumentoPorIdPersonaYTipoIdentidad(lSocio.getId().getIntIdPersona(), 
												 												   new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI));
								if(lDocumento != null)
								{
									lItemPlanilla.setDocumento(lDocumento);									
									lItemPlanilla.setIntEmpresa(lSocio.getId().getIntIdEmpresa());
									lItemPlanilla.setIntPeriodo(intPeriodo);											
									lItemPlanilla.setIntCantModalidades(lSocio.getListSocioEstructura().size());
																		
									//Validacion de condicion socio y situacion de la cuenta que sea vigente									
									cuentaIntegranteRspta = remoteCuenta.getCuentaIntegrantePorPKSocioYTipoCuentaYTipoIntegrante(lSocio.getId(),
																																Constante.PARAM_T_TIPOCUENTA_ACTIVA,																
																																Constante.PARAM_T_SITUACIONCUENTA_VIGENTE);
									if(cuentaIntegranteRspta != null)
									{
										lItemPlanilla.setCuentaIntegrante(cuentaIntegranteRspta);
										lItemPlanilla.setStrCodigoPersona(cuentaIntegranteRspta.getId().getIntPersonaIntegrante().toString());
										mostrarMensajeSocio(lSocio, lItemPlanilla, intTipoSocio);
																		
										List<Envioconcepto> listaConcepto = boEnvioconcepto.getListaEnvioconceptoPorEmprPeriodoNroCtaNivelCodigo(lSocio.getId().getIntIdEmpresa(),
																																			     intPeriodo,
																																				 cuentaIntegranteRspta.getId().getIntCuenta(),
																																				 pk.getIntNivel(),
																																				 pk.getIntCodigo());													
												
										if(listaConcepto != null && !listaConcepto.isEmpty())
										{
											planillaPorAdministra = tieneListaConcepto(lItemPlanilla,
																					   listaConcepto,
																					   cuentaIntegranteRspta,
																					   cuentaTemporal,
																					   intTipoSocio,
																					   lSocio,
																					   cuenta,
																					   lista,
																					   planillaPorAdministra);
										}
										else
										{
											planillaPorAdministra = notieneListaConcepto(lItemPlanilla,
																			 			  lSocio,
																			 			  intTipoSocio,
																			 			  cuentaIntegranteRspta,
																			 			  cuentaTemporal,
																			 			  cuenta,
																			 			  lista,
																			 			  planillaPorAdministra);												
										}
										
							   		}else
							   		{
							   		 log.debug("no tiene cuenta...codPersona: "+lSocio.getId().getIntIdPersona());
							   		 lItemPlanilla = null; 
							   		 continue;
							   		}
								}else
						   		{
							   		lItemPlanilla = null; 
									log.debug("no tiene documento...");
							   		continue;
							    }								
				          }	
							
					   }else
				   		{
					   		 log.debug("lista socio vacia...");
					   		 continue;
					    }
					}
					if( planillaPorAdministra != null
						&& planillaPorAdministra.getListaPlanilla() != null 
						&& planillaPorAdministra.getListaPlanilla().size() > 0){
						listaAdministra.add(planillaPorAdministra);												
					}
					continue;
				
				}
			
			}			
			log.info("planilla service getPlanillaPorIdEstructuraYTipoSocioYPeriodo=FIN v6");
		}  catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
		
		return listaAdministra;
	}
	

	private PlanillaAdministra notieneListaConcepto(ItemPlanilla lItemPlanilla,
													  Socio lSocio,
													  Integer intTipoSocio,
													  CuentaIntegrante cuentaIntegranteRspta,
													  CuentaId cuentaTemporal,
													  Cuenta cuenta,
													  List<ItemPlanilla> lista,
													  PlanillaAdministra planillaPorAdministra)
	{		
		log.debug("notieneListaConcepto INICIO");
 		BigDecimal bdDescuento = null;
		List<Expediente> listaExpediente = null;
		List<CuentaConcepto> listaCuentaConcepto = null;
		try
		{
			ConceptoFacadeRemote remoteConcepto = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			CuentaFacadeRemote remoteCuenta 	= (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);	
			
			for(SocioEstructura socioE: lSocio.getListSocioEstructura())
			  {
					if(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN.compareTo(socioE.getIntTipoEstructura()) == 0)
					{																											
						lItemPlanilla.setStrCodigoPlanilla(socioE.getId().getIntIdPersona().toString());
						
						listaExpediente = remoteConcepto.getListaExpedienteConSaldoPorEmpresaYcuenta(lSocio.getId().getIntIdEmpresa(),
							 																		 cuentaIntegranteRspta.getId().getIntCuenta());																			  
																
							if(listaExpediente != null && !listaExpediente.isEmpty())
							{																			
								descuentoService.setDescuentoPorListaExpediente(lItemPlanilla,socioE, listaExpediente);												
							}
							log.debug("sigues leendo franko");
							log.debug("cuenta: "+cuentaIntegranteRspta.getId().getIntCuenta());
							//agrega conceptos
							//Aqui hace un   LEFT OUTER JOIN con  CMO_BLOQUEOCUENTA 
							listaCuentaConcepto = remoteConcepto.getListaCuentaConceptoEmpresaCuentaOfCob(lSocio.getId().getIntIdEmpresa(), 
													       												 cuentaIntegranteRspta.getId().getIntCuenta());									
							
							if(listaCuentaConcepto != null && !listaCuentaConcepto.isEmpty())
							{
								bdDescuento = descuentoService.getDescuentoPorListaConcepto(lItemPlanilla,												   
													             							cuentaIntegranteRspta,
													             							listaCuentaConcepto);																   
								if(bdDescuento != null)
								{
									log.debug("bdDescuento: "+bdDescuento);
									if(socioE.getIntModalidad().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)==0)
									{
										lItemPlanilla.setBdHaberes(lItemPlanilla.getBdHaberes().add(bdDescuento));													
									}
									else if(socioE.getIntModalidad().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS)==0)
									{
										lItemPlanilla.setBdIncentivos(lItemPlanilla.getBdIncentivos().add(bdDescuento));
									}
									else if(socioE.getIntModalidad().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_CAS)==0)
									{
										lItemPlanilla.setBdCas(lItemPlanilla.getBdCas().add(bdDescuento));
									}
									lItemPlanilla.setBdEnvioTotal(lItemPlanilla.getBdEnvioTotal().add(bdDescuento));
									
								}else
								{
									log.debug("no hay bddescuento");
								}
							}else
							{
								log.debug("no hay listaCuentaConcepto");
								lItemPlanilla = null;
							}
						}else if(Constante.PARAM_T_TIPOESTRUCTURA_PRESTAMO.compareTo(socioE.getIntTipoEstructura()) == 0)
						{											
							if(lItemPlanilla.getListaEnviomonto()!= null 
								&& !lItemPlanilla.getListaEnviomonto().isEmpty())
							{
								Enviomonto o = lItemPlanilla.getListaEnviomonto().get(0);
								Enviomonto ento = new Enviomonto();
								ento.getId().setIntEmpresacuentaPk(o.getId().getIntEmpresacuentaPk());
								
								if(o.getIntModalidadCod().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)==0)
								{
									ento.setIntModalidadCod(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS);
								}
								else
								{
									ento.setIntModalidadCod(Constante.PARAM_T_MODALIDADPLANILLA_HABERES);
								}
								ento.setIntTiposocioCod(intTipoSocio);
								ento.setBdMontoenvio(new BigDecimal(0));
								ento.setIntNivel(o.getIntNivel());
								ento.setIntCodigo(o.getIntCodigo());
								ento.setIntTipoestructuraCod(Constante.PARAM_T_TIPOESTRUCTURA_PRESTAMO);
								ento.setIntEmpresasucprocesaPk(o.getIntEmpresasucprocesaPk());
								ento.setIntIdsucursalprocesaPk(o.getIntIdsucursalprocesaPk());
								ento.setIntIdsubsucursalprocesaPk(o.getIntIdsubsucursalprocesaPk());
								ento.setIntEmpresasucadministraPk(o.getIntEmpresasucadministraPk());
								ento.setIntIdsucursaladministraPk(o.getIntIdsucursaladministraPk());
								ento.setIntIdsubsucursaladministra(o.getIntIdsubsucursaladministra());
								ento.setIntEstadoCod(o.getIntEstadoCod());
								lItemPlanilla.getListaEnviomonto().add(ento);
							}										
						}
					  }									
					  
			          if(lItemPlanilla != null){
			        	//Lo de la condicion de la cuenta
							//PARAM_T_CONDICIONSOCIO = "65";
							cuentaTemporal.setIntCuenta(cuentaIntegranteRspta.getId().getIntCuenta());
							cuentaTemporal.setIntPersEmpresaPk(cuentaIntegranteRspta.getId().getIntPersEmpresaPk());
							cuenta  = remoteCuenta.getCuentaPorId(cuentaTemporal);						
							if(cuenta != null)
							{
								condicionCuenta(lItemPlanilla, cuenta);
								//CODIGO DE LA PERSONA																			
								capacidad(lItemPlanilla, intTipoSocio);										
								lista.add(lItemPlanilla);
								if(lista != null && lista.size() > 0){
									planillaPorAdministra.setListaPlanilla(lista);	
								}else
								{
									planillaPorAdministra = null;								
								}					
							}  
			          }else
						{
							planillaPorAdministra = null;								
						}
									
																	
						
		log.debug("notieneListaConcepto FIN");
		} catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
		return planillaPorAdministra;
	}

	private PlanillaAdministra tieneListaConcepto(ItemPlanilla lItemPlanilla,
												  List<Envioconcepto> listaConcepto,									
												  CuentaIntegrante cuentaIntegranteRspta,
												  CuentaId cuentaTemporal,
												  Integer intTipoSocio,
												  Socio lSocio,
												  Cuenta cuenta,
												  List<ItemPlanilla> lista,
												  PlanillaAdministra planillaAdministra){
		log.debug("tieneListaEnvioConcepto INICIO");
		Integer intModalidad = 0;		
		List<Enviomonto> enviomonto = null;
		List<Expediente> listaExpediente = null;
		List<CuentaConcepto> listaCuentaConcepto = null;
		BigDecimal bdDescuento = null;
		BigDecimal totalConcepto = new BigDecimal(0);		
		BigDecimal montoDiferencia = new BigDecimal(0);
		try
		{			
			ConceptoFacadeRemote remoteConcepto = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			CuentaFacadeRemote remoteCuenta 	= (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);	
			
			for(Envioconcepto eo:listaConcepto)
			{
			List<Enviomonto> listaEnvio= boEnviomonto.getListaPorEnvioConcepto(eo);
				if(listaEnvio!=null && !listaEnvio.isEmpty())
				{
					for(Enviomonto en: listaEnvio)
					{
						if(intTipoSocio.compareTo(en.getIntTiposocioCod()) == 0)
						{										
							totalConcepto = new BigDecimal(0);
							for(Envioconcepto eto: listaConcepto)
							{											
								totalConcepto = totalConcepto.add(eto.getBdMontoconcepto());											
							}										
						
							for(Envioconcepto eto: listaConcepto)
							{
								EnviomontoId pId= new EnviomontoId();
								pId.setIntEmpresacuentaPk(new Integer(eto.getId().getIntEmpresacuentaPk()));
								pId.setIntItemenviomonto(new Integer(1));
								pId.setIntItemenvioconcepto(new Integer(eto.getId().getIntItemenvioconcepto()));
								log.debug(pId);
								enviomonto = boEnviomonto.getListaPorEnvioConcepto(eto);
								Enviomonto envioto = null;
								Enviomonto o = null;
								
								if(enviomonto != null && !enviomonto.isEmpty())
								{	
									o = enviomonto.get(0);																										
									lItemPlanilla.setBlnEnvioConcepto(Boolean.TRUE);
									lItemPlanilla.setIntItemEnvioConcepto(eto.getId().getIntItemenvioconcepto());
														
									if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(o.getIntModalidadCod()) == 0)
									{
										log.debug("aberes");
										lItemPlanilla.setBdHaberes(o.getBdMontoenvio());									
										montoDiferencia =totalConcepto.subtract(o.getBdMontoenvio());
										intModalidad = Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS;
										lItemPlanilla.setBdIncentivos(montoDiferencia);
										envioto = agregarMontoEnvio(o,montoDiferencia, intModalidad, intTipoSocio);
										log.debug(envioto);
										if(lItemPlanilla.getListaEnviomonto() == null)
										{
											lItemPlanilla.setListaEnviomonto(new ArrayList<Enviomonto>());
										}
										lItemPlanilla.getListaEnviomonto().add(envioto);
										
									}else if(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(o.getIntModalidadCod()) == 0)
									{
										log.debug("incentivops");
										lItemPlanilla.setBdIncentivos(o.getBdMontoenvio());
										
										montoDiferencia = totalConcepto.subtract(o.getBdMontoenvio());
										intModalidad = Constante.PARAM_T_MODALIDADPLANILLA_HABERES;
										lItemPlanilla.setBdHaberes(montoDiferencia);
										envioto = agregarMontoEnvio(o,montoDiferencia, intModalidad, intTipoSocio);
										if(lItemPlanilla.getListaEnviomonto() == null)
										{
											lItemPlanilla.setListaEnviomonto(new ArrayList<Enviomonto>());
										}
										lItemPlanilla.getListaEnviomonto().add(envioto);
									}									
									lItemPlanilla.getListaEnviomonto().add(o);									
									break;
								}													
							}
							lItemPlanilla.setListaEnvioConcepto(listaConcepto);
							planillaAdministra.getListaPlanilla().add(lItemPlanilla);
							lista.add(lItemPlanilla);
							continue;
						}
						else
						{
							 for(SocioEstructura socioE: lSocio.getListSocioEstructura())
							  {
									if(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN.compareTo(socioE.getIntTipoEstructura()) == 0)
									{																						
										lItemPlanilla.setStrCodigoPlanilla(socioE.getId().getIntIdPersona().toString());
										listaExpediente = remoteConcepto.getListaExpedienteConSaldoPorEmpresaYcuenta(lSocio.getId().getIntIdEmpresa(),
											 																		 cuentaIntegranteRspta.getId().getIntCuenta());																								  
																				
											if(listaExpediente != null && !listaExpediente.isEmpty())
											{																								
												descuentoService.setDescuentoPorListaExpediente(lItemPlanilla,socioE, listaExpediente);															
											}											
											//agrega conceptos
											//Aqui hace un   LEFT OUTER JOIN con  CMO_BLOQUEOCUENTA 
											listaCuentaConcepto = remoteConcepto.getListaCuentaConceptoPorEmpresaYCuenta(lSocio.getId().getIntIdEmpresa(), 
																														 cuentaIntegranteRspta.getId().getIntCuenta());
											
											if(listaCuentaConcepto != null && !listaCuentaConcepto.isEmpty())
											{
												bdDescuento = descuentoService.getDescuentoPorListaConcepto(lItemPlanilla,												   
																	   cuentaIntegranteRspta,listaCuentaConcepto);
																	   
												if(bdDescuento!=null)
												{
													if(socioE.getIntModalidad().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)==0)
													{
														lItemPlanilla.setBdHaberes(lItemPlanilla.getBdHaberes().add(bdDescuento));																	
													}
													else if(socioE.getIntModalidad().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS)==0)
													{
														lItemPlanilla.setBdIncentivos(lItemPlanilla.getBdIncentivos().add(bdDescuento));
													}
													else if(socioE.getIntModalidad().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_CAS)==0)
													{
														lItemPlanilla.setBdCas(lItemPlanilla.getBdCas().add(bdDescuento));
													}
													lItemPlanilla.setBdEnvioTotal(lItemPlanilla.getBdEnvioTotal().add(bdDescuento));
													
												}															
											}
										}	
									  }													
									  
									  for(SocioEstructura socioE: lSocio.getListSocioEstructura())
										{
											if(Constante.PARAM_T_TIPOESTRUCTURA_PRESTAMO.compareTo(socioE.getIntTipoEstructura()) == 0)
											{															
												if(lItemPlanilla.getListaEnviomonto()!= null 
														&& !lItemPlanilla.getListaEnviomonto().isEmpty())
												{
													Enviomonto o = lItemPlanilla.getListaEnviomonto().get(0);
													Enviomonto ento = new Enviomonto();
													ento.getId().setIntEmpresacuentaPk(o.getId().getIntEmpresacuentaPk());
													if(o.getIntModalidadCod().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)==0)
													{
														ento.setIntModalidadCod(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS);
													}
													else
													{
														ento.setIntModalidadCod(Constante.PARAM_T_MODALIDADPLANILLA_HABERES);
													}
													ento.setIntTiposocioCod(intTipoSocio);
													ento.setBdMontoenvio(new BigDecimal(0));
													ento.setIntNivel(o.getIntNivel());
													ento.setIntCodigo(o.getIntCodigo());
													ento.setIntTipoestructuraCod(Constante.PARAM_T_TIPOESTRUCTURA_PRESTAMO);
													ento.setIntEmpresasucprocesaPk(o.getIntEmpresasucprocesaPk());
													ento.setIntIdsucursalprocesaPk(o.getIntIdsucursalprocesaPk());
													ento.setIntIdsubsucursalprocesaPk(o.getIntIdsubsucursalprocesaPk());
													ento.setIntEmpresasucadministraPk(o.getIntEmpresasucadministraPk());
													ento.setIntIdsucursaladministraPk(o.getIntIdsucursaladministraPk());
													ento.setIntIdsubsucursaladministra(o.getIntIdsubsucursaladministra());
													ento.setIntEstadoCod(o.getIntEstadoCod());
													lItemPlanilla.getListaEnviomonto().add(ento);
												}
												
											}
										  }	
											log.debug(lItemPlanilla);
											
											//Lo de la condicion de la cuenta
											//PARAM_T_CONDICIONSOCIO = "65";
											cuentaTemporal.setIntCuenta(cuentaIntegranteRspta.getId().getIntCuenta());
											cuentaTemporal.setIntPersEmpresaPk(cuentaIntegranteRspta.getId().getIntPersEmpresaPk());
											cuenta  = remoteCuenta.getCuentaPorId(cuentaTemporal);														
											condicionCuenta(lItemPlanilla, cuenta);																								
											//CODIGO DE LA PERSONA													
											capacidad(lItemPlanilla, intTipoSocio);														
											lista.add(lItemPlanilla);
											planillaAdministra.getListaPlanilla().add(lItemPlanilla);
						}
					}
				}
			break;
			}
		log.debug("tieneListaEnvioConcepto FIN");
		} catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
		return planillaAdministra;
	}

	private void mostrarMensajeSocio(Socio lSocio, ItemPlanilla lItemPlanilla,
		Integer intTipoSocio) throws BusinessException {
		List<AdminPadron> listaAdminPadron = null;		 
		Padron padron = null;
		Terceros tercer = null;
		try
		{
			EstructuraFacadeRemote remoteEstructura = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			for(SocioEstructura socioE: lSocio.getListSocioEstructura())
			{
				//log.debug("cant en listasocioestructura="+lSocio.getListSocioEstructura().size());									
				if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(socioE.getIntModalidad()) == 0)
				{
					//log.debug("Haberes");
					AdminPadron adminP= new AdminPadron();
					adminP.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					adminP.setId(new AdminPadronId());											
					adminP.getId().setIntParaModalidadCod(Constante.PARAM_T_MODALIDADPLANILLA_HABERES);
					adminP.getId().setIntParaTipoSocioCod(intTipoSocio);				
					
					listaAdminPadron = remoteEstructura.getTipSocioModPeriodoMes(adminP);
					
					if(listaAdminPadron != null && !listaAdminPadron.isEmpty())
					{						
						for(AdminPadron aPatron : listaAdminPadron)
						{
							if(Constante.PARAM_T_TIPOARCHIVOS_PADRON.compareTo(aPatron.getId().getIntParaTipoArchivoPadronCod()) == 0)
							{																
								padron = remoteEstructura.getPadronSOLOPorLibElectoral(lItemPlanilla.getDocumento().getStrNumeroIdentidad(),
										 												aPatron.getId().getIntItemAdministraPadron());
								if(padron != null)
								{									
									lItemPlanilla.setBlnLIC(Boolean.TRUE);
									if(lItemPlanilla.getListaEnvioConcepto() != null && !lItemPlanilla.getListaEnvioConcepto().isEmpty())
									{
										for(Envioconcepto oi: lItemPlanilla.getListaEnvioConcepto())
										{											
											oi.setIntIndilicencia(1);
										}
									}								
								}
								else
								{
									if(lItemPlanilla.getListaEnvioConcepto() != null &&
									   !lItemPlanilla.getListaEnvioConcepto().isEmpty())
									{
										for(Envioconcepto oi: lItemPlanilla.getListaEnvioConcepto())
										{											
											oi.setIntIndilicencia(0);
										}
									}									
								}
							}
							else if(Constante.PARAM_T_TIPOARCHIVOS_DSCTOTERCEROS.compareTo(aPatron.getId().getIntParaTipoArchivoPadronCod()) == 0)
							{								
								Terceros ter = new Terceros();
								ter.setId(new TercerosId());
								ter.getId().setIntItemAdministraPadron(aPatron.getId().getIntItemAdministraPadron());
								ter.setStrLibeje(lItemPlanilla.getDocumento().getStrNumeroIdentidad());
								log.debug("dni: "+lItemPlanilla.getDocumento().getStrNumeroIdentidad());
								log.debug("itemAdministraPadron:"+aPatron.getId().getIntItemAdministraPadron());
								tercer = remoteEstructura.getPorItemDNI(ter);
								
								if(tercer != null)
								{									
									lItemPlanilla.setBlnDJUD(Boolean.TRUE);
									if(lItemPlanilla.getListaEnvioConcepto() != null && 
										!lItemPlanilla.getListaEnvioConcepto().isEmpty())
									{
										for(Envioconcepto entos: lItemPlanilla.getListaEnvioConcepto())
										{											
											entos.setIntIndidescjudi(1);
										}
									}									
								}
								else
								{
									if(lItemPlanilla.getListaEnvioConcepto() != null && 
											!lItemPlanilla.getListaEnvioConcepto().isEmpty())
										{
											for(Envioconcepto entos: lItemPlanilla.getListaEnvioConcepto())
											{												
												entos.setIntIndidescjudi(0);
											}
										}									
								}
							}
						}
						
					}
				}
				else if(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(socioE.getIntModalidad()) == 0)
				{
					//log.debug("incentivos");
					AdminPadron adminP= new AdminPadron();
					adminP.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					adminP.setId(new AdminPadronId());
					adminP.getId().setIntParaModalidadCod(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS);
					adminP.getId().setIntParaTipoSocioCod(intTipoSocio);											
										
					listaAdminPadron = remoteEstructura.getTipSocioModPeriodoMes(adminP);
					if(listaAdminPadron != null && !listaAdminPadron.isEmpty())
					{											
						//getSocioNatuPorLibElectoral											
						for(AdminPadron aPatron1:listaAdminPadron)
						{
							if(Constante.PARAM_T_TIPOARCHIVOS_PADRON.compareTo(aPatron1.getId().getIntParaTipoArchivoPadronCod()) == 0)
							{
								
								padron = remoteEstructura.getPadronSOLOPorLibElectoral(lItemPlanilla.getDocumento().getStrNumeroIdentidad(), 
								   		 											   aPatron1.getId().getIntItemAdministraPadron());
								if(padron != null)
								{
									//log.debug("son iguales");
									lItemPlanilla.setBlnLIC(Boolean.TRUE);
									if(lItemPlanilla.getListaEnvioConcepto() != null
										&& lItemPlanilla.getListaEnvioConcepto().isEmpty())
									{
										for(Envioconcepto oi: lItemPlanilla.getListaEnvioConcepto())
										{											
											oi.setIntIndilicencia(1);
										}
									}									
								}	
								else
								{
									if(lItemPlanilla.getListaEnvioConcepto() != null
											&& lItemPlanilla.getListaEnvioConcepto().isEmpty())
										{
											for(Envioconcepto oi: lItemPlanilla.getListaEnvioConcepto())
											{												
												oi.setIntIndilicencia(0);
											}
										}
									
								}
								
							}
							else if(Constante.PARAM_T_TIPOARCHIVOS_DSCTOTERCEROS.compareTo(aPatron1.getId().getIntParaTipoArchivoPadronCod()) == 0)
							{																
								Terceros ter = new Terceros();
								ter.setId(new TercerosId());
								ter.getId().setIntItemAdministraPadron(listaAdminPadron.get(0).getId().getIntItemAdministraPadron());
								ter.setStrLibeje(lItemPlanilla.getDocumento().getStrNumeroIdentidad());
							
								tercer = remoteEstructura.getPorItemDNI(ter);
								
								if(tercer != null)
								{
									//log.debug("hayTErcer"+tercer.getId().getIntItemDesctoTerceros());
									lItemPlanilla.setBlnDJUD(Boolean.TRUE);
									if(lItemPlanilla.getListaEnvioConcepto() != null
											&& lItemPlanilla.getListaEnvioConcepto().isEmpty())
										{
											for(Envioconcepto entos: lItemPlanilla.getListaEnvioConcepto())
											{												
												entos.setIntIndidescjudi(1);
											}
										}									
								}
								else
								{
									if(lItemPlanilla.getListaEnvioConcepto() != null
											&& lItemPlanilla.getListaEnvioConcepto().isEmpty())
										{
											for(Envioconcepto entos: lItemPlanilla.getListaEnvioConcepto())
											{												
												entos.setIntIndidescjudi(0);
											}
										}									
								}
							}
						}
						
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		} catch (EJBFactoryException e) {
			throw new BusinessException(e);
		} catch(Exception e){
			throw new BusinessException(e);
		}			
	}

	/**
	 * SOLO PARA CAS ENVIO DE PLANILLA CAS
	 * 
	 * 
	 */
		
	public List<PlanillaAdministra> getPlanillaPorIdEstructuraYTipoSocioYPeriodoCAS(EstructuraId pk,
																					Integer intTipoSocio,
																					Integer intPeriodo)
																					throws BusinessException {
		log.info("getPlanillaPorIdEstructuraYTipoSocioYPeriodo=INICIO v4");
		BigDecimal bdDescuento = null;
		SocioFacadeRemote remoteSocio = null;
		ContactoFacadeRemote remoteContacto = null;
		CuentaFacadeRemote remoteCuenta = null;
		ConceptoFacadeRemote remoteConcepto = null;
		ItemPlanilla lItemPlanilla = null;
		List<ItemPlanilla> lista = null;
		Socio lSocio = null;		
		List<Socio> listaSocio = null;
		Documento lDocumento = null;
		CuentaIntegrante cuentaIntegrante = null;
		Cuenta cuenta = null;
		CuentaId cuentaTemporal = null;
		CuentaIntegranteId idCuentaIntegrante = null;
		CuentaIntegrante cuentaIntegranteRspta = null;
		List<CuentaConcepto> listaCuentaConcepto = null;
		List<Expediente> listaExpediente = null;
		BigDecimal bdCero = new BigDecimal(0);		
		List<PlanillaAdministra> listaPlanillaPorAdministra = new ArrayList<PlanillaAdministra>();		
		List<SocioEstructura> listaSocioEstructura = null;
		List<SocioEstructura> listaSocioEstructuraSoloAdministra = null;
		Socio socio = null;
		
		try {
			remoteSocio = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);	
			
			SocioEstructura soci = new SocioEstructura();
			soci.setIntNivel(pk.getIntNivel());
			soci.setIntCodigo(pk.getIntCodigo());
			soci.setId(new SocioEstructuraPK());
			soci.getId().setIntIdEmpresa(Constante.PARAM_EMPRESASESION);
			soci.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			soci.setIntTipoSocio(intTipoSocio);
			
			listaSocioEstructuraSoloAdministra = remoteSocio.getListaXAdminySubAdminSOLOCAS(soci);
			
			if(listaSocioEstructuraSoloAdministra != null && !listaSocioEstructuraSoloAdministra.isEmpty())
			{
				for(SocioEstructura porAdministra:listaSocioEstructuraSoloAdministra)
				{
					PlanillaAdministra planillaPorAdministra= new PlanillaAdministra();
					SubSucursalPK subSucursalPK = new SubSucursalPK();
					subSucursalPK.setIntPersEmpresaPk(porAdministra.getId().getIntIdEmpresa());
					subSucursalPK.setIntIdSucursal(porAdministra.getIntIdSucursalAdministra());
					subSucursalPK.setIntIdSubSucursal(porAdministra.getIntIdSubsucurAdministra());
					planillaPorAdministra.setSubSucursalPK(subSucursalPK);
					
					soci.setIntIdSucursalAdministra(porAdministra.getIntIdSucursalAdministra());
					soci.setIntIdSubsucurAdministra(porAdministra.getIntIdSubsucurAdministra());
					
					listaSocioEstructura = remoteSocio.getListaXNivelCodigosoloCas(soci);
					
					if(listaSocioEstructura != null && !listaSocioEstructura.isEmpty())
					{
						listaSocio = null;
						listaSocio = new ArrayList<Socio>();
						for(SocioEstructura socioEstructura: listaSocioEstructura)
						{
							SocioPK o= new SocioPK();
							o.setIntIdEmpresa(socioEstructura.getId().getIntIdEmpresa());
							o.setIntIdPersona(socioEstructura.getId().getIntIdPersona());
							 socio =  remoteSocio.getSocioPorPK(o); 
							if(socio != null)
							{
								socio.setListSocioEstructura(new ArrayList<SocioEstructura>());
								socio.getListSocioEstructura().add(socioEstructura);
								listaSocio.add(socio);
							}							
						}						
						if (listaSocio != null && !listaSocio.isEmpty())
						{
							log.info("listasocio cant=" + listaSocio.size());
							lista = new ArrayList<ItemPlanilla>();
							
							remoteContacto = (ContactoFacadeRemote) EJBFactory.getRemote(ContactoFacadeRemote.class);						
							remoteConcepto = (ConceptoFacadeRemote) EJBFactory.getRemote(ConceptoFacadeRemote.class);						
							remoteCuenta = (CuentaFacadeRemote) EJBFactory.getRemote(CuentaFacadeRemote.class);
									
							cuentaIntegrante = new CuentaIntegrante();
							cuenta = new Cuenta();
							cuentaTemporal = new CuentaId();
							idCuentaIntegrante = new CuentaIntegranteId();
							cuentaIntegrante.setId(idCuentaIntegrante);

							for (int i = 0; i < listaSocio.size(); i++)
							{
								lItemPlanilla = new ItemPlanilla();
								lItemPlanilla.setBdHaberes(bdCero);
								lItemPlanilla.setBdIncentivos(bdCero);
								lItemPlanilla.setBdCas(bdCero);
								lItemPlanilla.setBdEnvioTotal(bdCero);
								lItemPlanilla.setBdHaberesI(bdCero);
								lItemPlanilla.setBdIncentivosI(bdCero);
								lItemPlanilla.setBdCasI(bdCero);
								lSocio = listaSocio.get(i);
								lItemPlanilla.setSocio(lSocio);
								lItemPlanilla.setBlnEnvioConcepto(Boolean.FALSE);
								lItemPlanilla.setBlnCartaAutorizacion(Boolean.FALSE);
								lItemPlanilla.setBlnDJUD(Boolean.FALSE);
								lItemPlanilla.setBlnLIC(Boolean.FALSE);
											
								lDocumento = remoteContacto.getDocumentoPorIdPersonaYTipoIdentidad(lSocio.getId().getIntIdPersona(),
																								   new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI));

								lItemPlanilla.setDocumento(lDocumento);
								lItemPlanilla.setIntEmpresa(lSocio.getId().getIntIdEmpresa());								
								lItemPlanilla.setIntPeriodo(intPeriodo);																							
								idCuentaIntegrante.setIntPersEmpresaPk(lSocio.getId().getIntIdEmpresa());								
								idCuentaIntegrante.setIntPersonaIntegrante(lSocio.getId().getIntIdPersona());								

								// Validacion de condicion socio y situacion de la
								// cuenta que sea vigente
								cuentaIntegranteRspta = remoteCuenta.getCuentaIntegrantePorPKSocioYTipoCuentaYTipoIntegrante(lSocio.getId(),
																															Constante.PARAM_T_TIPOCUENTA_ACTIVA,										
																															Constante.PARAM_T_SITUACIONCUENTA_VIGENTE);

								if (cuentaIntegranteRspta != null) 
								{
									lItemPlanilla.setCuentaIntegrante(cuentaIntegranteRspta);
									// 3) lista de expedienteCredito por cuenta y
									// empresa y
									// (saldocredito o saldoInteres o saldomora > 0)
									for (SocioEstructura socioE : lSocio.getListSocioEstructura())
									{											
										if (Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN.compareTo(socioE.getIntTipoEstructura()) == 0) 
										{												
											lItemPlanilla.setStrCodigoPlanilla(socioE.getId().getIntIdPersona().toString());
											listaExpediente = remoteConcepto.getListaExpedienteConSaldoPorEmpresaYcuenta(lSocio.getId().getIntIdEmpresa(),
																														 cuentaIntegranteRspta.getId().getIntCuenta());										
																																				
												if (listaExpediente != null && !listaExpediente.isEmpty()) 
												{										
													descuentoService.setDescuentoPorListaExpediente(lItemPlanilla,
																									socioE,
																									listaExpediente);																
												} 									
												
												listaCuentaConcepto = remoteConcepto.getListaCuentaConceptoPorEmpresaYCuenta(lSocio.getId().getIntIdEmpresa(),
																															cuentaIntegranteRspta.getId().getIntCuenta());																							
												
												if (listaCuentaConcepto != null && !listaCuentaConcepto.isEmpty())
												{											
													bdDescuento = descuentoService.getDescuentoPorListaConcepto(lItemPlanilla,
																												cuentaIntegranteRspta,
																												listaCuentaConcepto);
													if(bdDescuento!=null)
													{										
														lItemPlanilla.setBdCas(lItemPlanilla.getBdCas().add(bdDescuento));											
														lItemPlanilla.setBdEnvioTotal(lItemPlanilla.getBdEnvioTotal().add(bdDescuento));
													}
												}										
											}
										}

										// Lo de la condicion de la cuenta
										// PARAM_T_CONDICIONSOCIO = "65";
										cuentaTemporal.setIntCuenta(cuentaIntegranteRspta.getId().getIntCuenta());							
										cuentaTemporal.setIntPersEmpresaPk(cuentaIntegranteRspta.getId().getIntPersEmpresaPk());													
										cuenta = remoteCuenta.getCuentaPorId(cuentaTemporal);									
										condicionCuenta(lItemPlanilla, cuenta);							
										// CODIGO DE LA PERSONA
										lItemPlanilla.setIntCodigoPersona(cuentaIntegranteRspta.getId().getIntPersonaIntegrante());							
										lista.add(lItemPlanilla);
									}
							}						
						planillaPorAdministra.setListaPlanilla(lista);
						listaPlanillaPorAdministra.add(planillaPorAdministra);	
						}		
						else
						{
							listaPlanillaPorAdministra = new ArrayList<PlanillaAdministra>();
						}					
					}else
					{
						log.debug("listaSocioEstructura x administra es null");
					}
				}
			}else
			{
				log.debug("lista x administra es null");
			}
			
			log.info("getPlanillaPorIdEstructuraYTipoSocioYPeriodo=FIN v4");			
		} catch (BusinessException e) {
			throw e;
		} catch (EJBFactoryException e) {
			throw new BusinessException(e);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		for(PlanillaAdministra planillaadm:listaPlanillaPorAdministra)
		{
			log.debug("sucursal: "+planillaadm.getSubSucursalPK().getIntIdSucursal()+
					  "subSucursal: "+planillaadm.getSubSucursalPK().getIntIdSubSucursal());
			for(ItemPlanilla item: planillaadm.getListaPlanilla())
			{
				log.debug("codigo: "+item.getSocio().getId().getIntIdPersona() +
						  " paterno"+item.getSocio().getStrApePatSoc());
			}
		}
		return listaPlanillaPorAdministra;
	}
	
	/**
	 * EFECTUADO 
	 * 
	 * */
			
	
	public List<ItemPlanilla> getPlanillaDeEfectuadoPorIdEstructuraYTipoSocioYPeriodo(EstructuraId pk,Integer intTipoSocio, Integer intPeriodo) throws BusinessException{
		log.info("planillaservice.getPlanillaDeEfectuadoPorIdEstructuraYTipoSocioYPeriodo()");
		SocioFacadeRemote remoteSocio = null;
		ItemPlanilla lItemPlanilla = null;
		List<ItemPlanilla> lista = null;
		Socio lSocio = null;
		List<Socio> listaSocio = null;
		Integer intEmpresa = 2;
		Integer intModalidad = 1;
		String strINCuenta = null;
		try{
			strINCuenta = boEnvioconcepto.getCsvCuentaPorEmpresaYTipoSocioYModalidadYPeriodo(intEmpresa,intTipoSocio,intModalidad,intPeriodo);
			remoteSocio = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			listaSocio = remoteSocio.getListaSocioPorEmpresaYTipoIntegranteYINCuenta(intEmpresa,
																					 Constante.PARAM_T_TIPOINTEGRANTECUENTA_PRINCIPAL, 
																					 strINCuenta);
			if(listaSocio!= null && listaSocio.size()>0)
			{
				lista = new ArrayList<ItemPlanilla>();
				for(int i=0;i<listaSocio.size();i++){
					lItemPlanilla = new ItemPlanilla();
					lSocio = listaSocio.get(i);
					lItemPlanilla.setSocio(lSocio);
					lista.add(lItemPlanilla);
				}	
			}	
		} catch(BusinessException e){
			throw e;
		} catch (EJBFactoryException e) {
			throw new BusinessException(e);
		} catch(Exception e){
			throw new BusinessException(e);
		}
		
		return lista;
	}
	public List<Enviomonto> getPlanillaEfectuada(Enviomonto o, Integer intMaxEnviado)throws BusinessException
	{
		Socio socio = null;
		Documento lDocumento = null;
		List<Enviomonto> lista = null;
		List<Envioconcepto> lstEnvioC = null;	
		List<CuentaIntegrante> lstCuentaIntegrante = null;
		try
		{	
			PlanillaFacadeLocal localPlanilla = (PlanillaFacadeLocal)EJBFactory.getLocal(PlanillaFacadeLocal.class);
			CuentaFacadeRemote	cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			SocioFacadeRemote  socioFacade = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
			ContactoFacadeRemote remoteContacto 	= (ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);
						
			lista = localPlanilla.getListaEnvioMontoPlanillaEfectuada(o, intMaxEnviado);
						
			if(lista != null && !lista.isEmpty())
			{
				for(Enviomonto eo: lista)
				{
					EnvioconceptoId envio = new EnvioconceptoId();
					envio.setIntItemenvioconcepto(eo.getId().getIntItemenvioconcepto());
					envio.setIntEmpresacuentaPk(eo.getId().getIntEmpresacuentaPk());	

					lstEnvioC = boEnvioconcepto.getEnvioconceptoPorItemEnvioConcepto(envio);
					if(lstEnvioC != null && !lstEnvioC.isEmpty())
					{	
						eo.setListaEnvioConcepto(lstEnvioC);
						
							Envioconcepto envioconcepto = lstEnvioC.get(0);
							CuentaId cuentaId = new CuentaId();
							cuentaId.setIntCuenta(envioconcepto.getIntCuentaPk());
							cuentaId.setIntPersEmpresaPk(envioconcepto.getId().getIntEmpresacuentaPk());
							lstCuentaIntegrante = cuentaFacade.getListaCuentaIntegrantePorCuenta(cuentaId);
							if(lstCuentaIntegrante != null && !lstCuentaIntegrante.isEmpty())
							{
								for(CuentaIntegrante cuenta : lstCuentaIntegrante)
								{									
									lDocumento = remoteContacto.getDocumentoPorIdPersonaYTipoIdentidad(
																cuenta.getId().getIntPersonaIntegrante(),																	
																new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI));
									if(lDocumento != null)
									{
										eo.setDocumento(lDocumento);
									}
									SocioPK  socioPK = new SocioPK();
									socioPK.setIntIdEmpresa(cuenta.getId().getIntPersEmpresaPk());
									socioPK.setIntIdPersona(cuenta.getId().getIntPersonaIntegrante());	
									socio = socioFacade.getSocioPorPK(socioPK);
									if(socio != null )
									{
										eo.setSocio(socio);
									}
								}
							}						
					}
					eo.setBdMontoenvio(eo.getBdMontoenvio().setScale(2, BigDecimal.ROUND_HALF_UP));					
					eo.setBdMontoEfectuado(eo.getBdMontoenvio());
					eo.setBdDiferencia(eo.getBdMontoenvio().subtract(eo.getBdMontoEfectuado()));
					eo.setBdEnvioTotal(eo.getBdMontoEfectuado().subtract(eo.getBdDiferencia()));
				}
			}
		}catch(BusinessException e){
			throw e;
		} catch (EJBFactoryException e) {
			throw new BusinessException(e);
		} catch(Exception e){
			throw new BusinessException(e);
		}
		
		return lista;
	}

	
	public List<Efectuado> getListaEfectuadoXNiveCodigoModaliPeriodoTipoSocio(Integer intIdEmpresa,EstructuraId pId,
																			   Integer intTipoModalidad,
																			   Integer intPeriodo,
																			   Integer intTipoSocio)throws BusinessException{
		List<Efectuado> lista = null;
		try{
			lista= boEfectuado.getListaEfectuadoXNiveCodigoModaliPeriodoTipoSocio(intIdEmpresa,
																					pId, 
																					intTipoModalidad,
																					intPeriodo,
																					intTipoSocio);
		}catch(BusinessException e){
			throw e;
		}  catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/**
	 * Antes de agregar la planilla 
	 * tengo que ir nuevamente revisar si tiene expediente, si lo tiene voy Y QUE SEA TIPO PRESTAMO AHI VOY a capacidad 
	 * credito aqui obtengo el total de cuota fija esto lo comparo con mi total de conceptos, xpediente
	 * si son iguales se asigna a cada modalidad el monto cuota, si es diferente va igual en prestamo
	 * y en origen va la diferencia.
	 */
	
	public void capacidad(ItemPlanilla lItemPlanilla, Integer intTipoSocio) throws BusinessException
	{
		List<CapacidadCredito> listaCapacidad = null;
		SolicitudPrestamoFacadeRemote solicitudPres = null;
		BigDecimal sumaTotal = new BigDecimal(0); 
		BigDecimal bdMontoPrestamo = new BigDecimal(0);
		BigDecimal sumaTotalCapacidadCredito = new BigDecimal(0);
		SocioFacadeRemote remoteSocio = null;
		sumaTotal = lItemPlanilla.getBdHaberes().add(lItemPlanilla.getBdIncentivos());
		try
		{
			solicitudPres   = (SolicitudPrestamoFacadeRemote)EJBFactory.getRemote(SolicitudPrestamoFacadeRemote.class);
			remoteSocio = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			
			if(lItemPlanilla.getListaExpediente() != null && !lItemPlanilla.getListaExpediente().isEmpty())
			{
				for(Expediente ee: lItemPlanilla.getListaExpediente())
				{
					if(Constante.PARAM_T_TIPO_CREDITO_PRESTAMO.compareTo(ee.getIntParaTipoCreditoCod()) == 0)
					{
						//Ir a esquema Servicio a capacidad de credito 						
						//iendo a esquemq servicio a capacidad de credito
						ExpedienteCreditoId o = new ExpedienteCreditoId();
						o.setIntPersEmpresaPk(ee.getId().getIntPersEmpresaPk());
						o.setIntCuentaPk(ee.getId().getIntCuentaPk());
						o.setIntItemExpediente(ee.getId().getIntItemExpediente());
						o.setIntItemDetExpediente(ee.getId().getIntItemExpedienteDetalle());						
												
						listaCapacidad	= solicitudPres.getListaPorPkExpedienteCredito(o);
						if(listaCapacidad != null && !listaCapacidad.isEmpty())
						{
							for(CapacidadCredito co: listaCapacidad)
							{													
								sumaTotalCapacidadCredito = sumaTotalCapacidadCredito.add(co.getBdCuotaFija());	
								//ver cual es el monto en prestamo
								SocioEstructuraPK jh0 = new SocioEstructuraPK();
								jh0.setIntIdEmpresa(co.getId().getIntPersEmpresaPk());
								jh0.setIntIdPersona(co.getIntPersPersonaPk());
								jh0.setIntItemSocioEstructura(co.getIntItemSocioEstructura());
								SocioEstructura soraya1 = remoteSocio.getSocioEstructuraPorPKTipoSocio(jh0, intTipoSocio);
								
								if(soraya1 != null)
								{									
									if(Constante.PARAM_T_TIPOESTRUCTURA_PRESTAMO.compareTo(soraya1.getIntTipoEstructura()) == 0)
									{
										bdMontoPrestamo = co.getBdCuotaFija();										
									}									
								}
							}							
							//comparo sumatotal con sumatotal en capacidad de credito
							if(sumaTotal.compareTo(sumaTotalCapacidadCredito) == 0)
							{
								log.debug("sumaTotal = sumaTotalCapacidadCredito");
								//y si tiene dos modalidades va igual para cada quien y esto se asigna a litemplanilla
								for(CapacidadCredito co1: listaCapacidad)
								{													
									//nos vamos a socio para ver que modalidad es y si es origen o prestamo
									SocioEstructuraPK jh1 = new SocioEstructuraPK();
									jh1.setIntIdEmpresa(co1.getId().getIntPersEmpresaPk());
									jh1.setIntIdPersona(co1.getIntPersPersonaPk());
									jh1.setIntItemSocioEstructura(co1.getIntItemSocioEstructura());
									SocioEstructura soraya2 = remoteSocio.getSocioEstructuraPorPKTipoSocio(jh1, intTipoSocio);
									if(soraya2 != null)
									{
										
										if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(soraya2.getIntModalidad()) == 0)
										{												
											lItemPlanilla.setBdHaberes(co1.getBdCuotaFija());											
										}
										else if(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(soraya2.getIntModalidad()) == 0)
										{												
											lItemPlanilla.setBdIncentivos(co1.getBdCuotaFija());												
										}										
									}
								}
								
							}
							else if(sumaTotal.compareTo(sumaTotalCapacidadCredito) == 1 || sumaTotal.compareTo(sumaTotalCapacidadCredito) == -1)
							{
								log.debug("aca va igual la cantidad de prestamos y origen es la diferencia entre sumatotal con prestamo	lItemPlanilla");												
								
								for(CapacidadCredito co1: listaCapacidad)
								{													
									//nos vamos a socio para ver que modalidad es y si es origen o prestamo	
									SocioEstructuraPK jh2 = new SocioEstructuraPK();
									jh2.setIntIdEmpresa(co1.getId().getIntPersEmpresaPk());
									jh2.setIntIdPersona(co1.getIntPersPersonaPk());
									jh2.setIntItemSocioEstructura(co1.getIntItemSocioEstructura());
									SocioEstructura soraya3 = remoteSocio.getSocioEstructuraPorPKTipoSocio(jh2, intTipoSocio);
									if(soraya3 != null)
									{																
																						
											if(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN.compareTo(soraya3.getIntTipoEstructura()) == 0)
											{
												log.debug("origen");
												if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(soraya3.getIntModalidad()) == 0)
												{													
													lItemPlanilla.setBdHaberes(sumaTotal.subtract(bdMontoPrestamo));													
												}
												else if(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(soraya3.getIntModalidad()) == 0)
												{												
													lItemPlanilla.setBdIncentivos(sumaTotal.subtract(bdMontoPrestamo));													
												}
											}
											else if(Constante.PARAM_T_TIPOESTRUCTURA_PRESTAMO.compareTo(soraya3.getIntTipoEstructura()) == 0)
											{
												
												//aca va igual al monto de capacidad de credito
												if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(soraya3.getIntModalidad()) == 0)
												{																
													
													lItemPlanilla.setBdHaberes(co1.getBdCuotaFija());
													
												}
												else if(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(soraya3.getIntModalidad()) == 0)
												{
													
													lItemPlanilla.setBdIncentivos(co1.getBdCuotaFija());
													
												}
											}										
									}
								}
							}
							
							for(Enviomonto e: lItemPlanilla.getListaEnviomonto())
							{
								if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(e.getIntModalidadCod()) == 0)
								{
									e.setBdMontoenvio(lItemPlanilla.getBdHaberes());
								}
								else if(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(e.getIntModalidadCod()) == 0)
								{
									e.setBdMontoenvio(lItemPlanilla.getBdIncentivos());
								}
							}
						}
						
					}
				
				}
			}
		}catch(BusinessException e){
			throw e;
		}  catch(Exception e){
			throw new BusinessException(e);
		}
				
	}
	
	private void condicionCuenta(ItemPlanilla lItemPlanilla, Cuenta cuenta)throws BusinessException {
		// TODO Auto-generated method stub
		if(cuenta.getIntParaCondicionCuentaCod().compareTo(1) == 0){
			lItemPlanilla.setCondicionCuenta("HABIL");
		}else if(cuenta.getIntParaCondicionCuentaCod().compareTo(2) == 0){
			lItemPlanilla.setCondicionCuenta("EXCEPCIONAL");
		}else if(cuenta.getIntParaCondicionCuentaCod().compareTo(3) == 0){
			lItemPlanilla.setCondicionCuenta("COBRANZAADMINISTRATIVA");
		}else if(cuenta.getIntParaCondicionCuentaCod().compareTo(4) == 0){
			lItemPlanilla.setCondicionCuenta("COBRANZAMOROSA");
		}else if(cuenta.getIntParaCondicionCuentaCod().compareTo(5) == 0){
			lItemPlanilla.setCondicionCuenta("MOROSO");
		}else if(cuenta.getIntParaCondicionCuentaCod().compareTo(6) == 0){
			lItemPlanilla.setCondicionCuenta("INACTIVO");
		}else{
			lItemPlanilla.setCondicionCuenta("FALLECIDO");
		}
	}
	private Enviomonto agregarMontoEnvio(Enviomonto o,BigDecimal montoDiferencia, Integer intModalidad, Integer intTipoSocio)
	{
		Enviomonto ento = new Enviomonto();
		ento.getId().setIntEmpresacuentaPk(o.getId().getIntEmpresacuentaPk());
		ento.setIntModalidadCod(intModalidad);		
		ento.setIntTiposocioCod(intTipoSocio);
		ento.setBdMontoenvio(montoDiferencia);
		ento.setIntNivel(o.getIntNivel());
		ento.setIntCodigo(o.getIntCodigo());
		
		if(Constante.PARAM_T_TIPOESTRUCTURA_PRESTAMO.compareTo(o.getIntTipoestructuraCod()) == 0)
		{
			ento.setIntTipoestructuraCod(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN);
		}
		else if(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN.compareTo(o.getIntTipoestructuraCod()) == 0)
		{
			ento.setIntTipoestructuraCod(Constante.PARAM_T_TIPOESTRUCTURA_PRESTAMO);
		}
		ento.setIntEmpresasucprocesaPk(o.getIntEmpresasucprocesaPk());
		ento.setIntIdsucursalprocesaPk(o.getIntIdsucursalprocesaPk());
		ento.setIntIdsubsucursalprocesaPk(o.getIntIdsubsucursalprocesaPk());
		ento.setIntEmpresasucadministraPk(o.getIntEmpresasucadministraPk());
		ento.setIntIdsucursaladministraPk(o.getIntIdsucursaladministraPk());
		ento.setIntIdsubsucursaladministra(o.getIntIdsubsucursaladministra());
		ento.setIntEstadoCod(o.getIntEstadoCod());
		return ento;
	
	}
	
	public List<EfectuadoResumen> aCompletarEfectuado(List<EfectuadoConceptoComp> listaEfectuadoConceptoComp,
													  Enviomonto eo,
													  Integer intMaxEnviado) throws BusinessException
	{
		log.debug("aCompletarEfectuado INICIO");
		List<EfectuadoResumen> lista = new ArrayList<EfectuadoResumen>();
		List <Efectuado> listaEfectuado = new ArrayList<Efectuado>();
		BigDecimal ret = new BigDecimal(0);
		try
		{
			EfectuadoResumen er = new EfectuadoResumen();			
			er.getId().setIntEmpresa(eo.getId().getIntEmpresacuentaPk());
			er.setIntPeriodoPlanilla(intMaxEnviado);
			er.setIntTiposocioCod(eo.getIntTiposocioCod());
			er.setIntModalidadCod(eo.getIntModalidadCod());
			er.setIntNivel(eo.getIntNivel());
			er.setIntCodigo(eo.getIntCodigo());	
			er.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			er.setIntParaDocumentoGeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAEFECTUADA);
			
			for(EfectuadoConceptoComp ecomp: listaEfectuadoConceptoComp)
			{				
				if(ecomp.getEfectuado().getIntIdsubsucursaladministra() != null
					&& ecomp.getEfectuado().getIntIdsubsucursaladministra() != null)
				{
					er.setIntIdsucursaladministraPk(ecomp.getEfectuado().getIntIdsucursaladministraPk());
					er.setIntIdsubsucursaladministra(ecomp.getEfectuado().getIntIdsubsucursaladministra());
					er.setIntIdsucursalprocesaPk(ecomp.getEfectuado().getIntIdsucursalprocesaPk());
					er.setIntIdsubsucursalprocesaPk(ecomp.getEfectuado().getIntIdsubsucursalprocesaPk());
					er.setIntPersonausuarioPk(ecomp.getEfectuado().getIntPersonausuarioPk());
					break;
				}					
				
			}
			
			for(EfectuadoConceptoComp ecomp: listaEfectuadoConceptoComp){
				
				if(ecomp.getEfectuado() != null)
				{
					if(ecomp.getEfectuado().getBdMontoEfectuado() != null)
					{
						ret = ret.add(ecomp.getEfectuado().getBdMontoEfectuado());
						listaEfectuado.add(ecomp.getEfectuado());
					}					
				}
			}	
			er.setBdMontoTotal(ret);
			er.setIntNumeroAfectados(listaEfectuado.size());
			er.setListaEfectuado(listaEfectuado);
			er.setIntParaEstadoPago(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			lista.add(er);
			log.debug("FIN");
		}
				 
		catch(Exception e){
			throw new BusinessException(e);
		}
		
		return lista;
	}
	
	public List<EfectuadoResumen> aEfectuar(List<EfectuadoConceptoComp> listaEfectuadoConceptoComp,
										    Enviomonto eo,
										    Integer intMaxEnviado) throws BusinessException
	{
		List<EfectuadoResumen> lista = new ArrayList<EfectuadoResumen>();
		List <Efectuado> listaEfectuado = new ArrayList<Efectuado>();
		BigDecimal ret = new BigDecimal(0);
		try
		{
			EfectuadoResumen er = new EfectuadoResumen();			
			er.getId().setIntEmpresa(eo.getId().getIntEmpresacuentaPk());
			er.setIntPeriodoPlanilla(intMaxEnviado);
			er.setIntTiposocioCod(eo.getIntTiposocioCod());
			er.setIntModalidadCod(eo.getIntModalidadCod());
			er.setIntNivel(eo.getIntNivel());
			er.setIntCodigo(eo.getIntCodigo());	
			er.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			er.setIntParaDocumentoGeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAEFECTUADA);
			
			for(EfectuadoConceptoComp ecomp: listaEfectuadoConceptoComp)
			{
				if(ecomp.getEfectuado().getIntIdsucursaladministraPk() != null &&
				   ecomp.getEfectuado().getIntIdsubsucursaladministra() != null &&
				   ecomp.getEfectuado().getIntIdsucursalprocesaPk() != null &&
				   ecomp.getEfectuado().getIntIdsubsucursalprocesaPk() != null &&
				   ecomp.getEfectuado().getIntPersonausuarioPk()!= null)
				{
					er.setIntIdsucursaladministraPk(ecomp.getEfectuado().getIntIdsucursaladministraPk());
					er.setIntIdsubsucursaladministra(ecomp.getEfectuado().getIntIdsubsucursaladministra());
					er.setIntIdsucursalprocesaPk(ecomp.getEfectuado().getIntIdsucursalprocesaPk());
					er.setIntIdsubsucursalprocesaPk(ecomp.getEfectuado().getIntIdsubsucursalprocesaPk());
					er.setIntPersonausuarioPk(ecomp.getEfectuado().getIntPersonausuarioPk());
					break;
				}			
			}
			
			for(EfectuadoConceptoComp ecomp: listaEfectuadoConceptoComp){
				if(ecomp.getEfectuado() != null)
				{
					if(ecomp.getEfectuado().getBdMontoEfectuado() != null)
					{
						ret = ret.add(ecomp.getEfectuado().getBdMontoEfectuado());
						listaEfectuado.add(ecomp.getEfectuado());	
					}					
				}				
			}
			er.setBdMontoTotal(ret);
			er.setIntNumeroAfectados(listaEfectuado.size());
			er.setListaEfectuado(listaEfectuado);
			er.setIntParaEstadoPago(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			lista.add(er);
		}
				 
		catch(Exception e){
			throw new BusinessException(e);
		}
		
		return lista;
	}
	
	
	public List<EfectuadoConceptoComp> getEmpezandoEfectuado(Enviomonto o,
														     Integer intMaxEnviado,
														     Integer intSegundaModalidad) throws BusinessException
	{
		List<EfectuadoConceptoComp> lista = new ArrayList<EfectuadoConceptoComp>();
		List<Envioconcepto> listaEnvioConcepto = null;
		Envioconcepto envioconcepto = null;		
		Documento lDocumento = null;
		Socio socio = null;
		CuentaIntegrante cuentaIntegranteRspta = null;
		List<SocioEstructura> listaSocioEstructura = null;
		List<CuentaIntegrante> lstCuentaIntegrante = null;
		List<Enviomonto> listaEnviomonto=null;
		List<Enviomonto> listaItemConceptos = null; //solo itemEnvioConcepto		
		List<Efectuado> listaEfectuado = new ArrayList<Efectuado>();
		List<Expediente> listaExpediente = null;
		
		List<CapacidadCredito> listaCapacidadCre	= null;
		BigDecimal bdMontoTotal = new BigDecimal(0);
		Integer intNroAfectados = null;
		intNroAfectados = 0;
		try
		{
			CuentaFacadeRemote	cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			ContactoFacadeRemote remoteContacto = (ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);
			SocioFacadeRemote  socioFacade = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);			
			ConceptoFacadeRemote remoteConcepto = (ConceptoFacadeRemote) EJBFactory.getRemote(ConceptoFacadeRemote.class);
			SolicitudPrestamoFacadeRemote solicitudPres   	= (SolicitudPrestamoFacadeRemote)EJBFactory.getRemote(SolicitudPrestamoFacadeRemote.class);
			

					
					listaItemConceptos = boEnviomonto.getListaItemConcepto(o,intMaxEnviado);
					
					if(listaItemConceptos != null && !listaItemConceptos.isEmpty())
					{
						for(Enviomonto i: listaItemConceptos)
						{
							log.debug(i.getId().getIntItemenvioconcepto());
							EfectuadoConceptoComp efcomp = new EfectuadoConceptoComp();
							efcomp.setBlnAgregarSocio(Boolean.FALSE);
							efcomp.setBlnAgregarNoSocio(Boolean.FALSE);
							Envioconcepto envioCto = new Envioconcepto();
							envioCto.setId(new EnvioconceptoId());
							envioCto.getId().setIntEmpresacuentaPk(o.getId().getIntEmpresacuentaPk());
							envioCto.getId().setIntItemenvioconcepto(i.getId().getIntItemenvioconcepto());
							
							listaEnviomonto = boEnviomonto.getListaPorEnvioConcepto(envioCto);
							
							if(listaEnviomonto != null && !listaEnviomonto.isEmpty())
							{
								log.debug("cantDeEnviosMontos="+listaEnviomonto.size());
								List<EfectuadoConcepto> listaEfectuadoConcepto = new ArrayList<EfectuadoConcepto>();
								
								for(Enviomonto ento: listaEnviomonto)
								{
									if(ento.getIntModalidadCod().compareTo(o.getIntModalidadCod())== 0)
									{
										log.debug("modalidad");
										intNroAfectados++;								
										
										Efectuado e = new Efectuado();
										e.setEnvioMonto(ento);
										e.getId().setIntEmpresacuentaPk(ento.getId().getIntEmpresacuentaPk());
										e.setIntPeriodoPlanilla(intMaxEnviado);
										e.setIntTiposocioCod(ento.getIntTiposocioCod());
										e.setIntModalidadCod(ento.getIntModalidadCod());
										e.setIntNivel(ento.getIntNivel());
										e.setIntCodigo(ento.getIntCodigo());
										e.setIntTipoestructuraCod(ento.getIntTipoestructuraCod());
										e.setIntEmpresasucprocesaPk(ento.getIntEmpresasucprocesaPk());
										e.setIntIdsucursalprocesaPk(ento.getIntIdsucursalprocesaPk());
										e.setIntIdsubsucursalprocesaPk(ento.getIntIdsubsucursalprocesaPk());
										e.setIntIdsucursaladministraPk(ento.getIntIdsucursaladministraPk());
										e.setIntEmpresasucadministraPk(ento.getIntEmpresasucadministraPk());
										e.setIntIdsubsucursaladministra(ento.getIntIdsubsucursaladministra());
										e.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
										e.setIntEmpresausuarioPk(ento.getIntEmpresausuarioPk());
										e.setTsFecharegistro(ento.getTsFecharegistro());
										e.setIntPersonausuarioPk(ento.getIntPersonausuarioPk());
										e.setBdMontoEfectuado(ento.getBdMontoenvio());
										e.setBdMontoEfectuado(e.getBdMontoEfectuado().setScale(2,BigDecimal.ROUND_HALF_UP));
										e.setBdDiferencia(ento.getBdMontoenvio().subtract(e.getBdMontoEfectuado()));																
										e.setIntParaTipoIngresoDatoCod(1);
										e.setBlnLIC(Boolean.FALSE);
										e.setBlnCartaAutorizacion(Boolean.FALSE);
										e.setBlnDJUD(Boolean.FALSE);							
										e.setBlnListaEnvioConcepto(Boolean.TRUE);
										e.setBlnAgregarNoSocio(Boolean.FALSE);
										
										e.setBlnEfectuadoConcepto0(Boolean.FALSE);
																		
										EnvioconceptoId pId = new EnvioconceptoId();
										pId.setIntEmpresacuentaPk(o.getId().getIntEmpresacuentaPk());
										pId.setIntItemenvioconcepto(i.getId().getIntItemenvioconcepto());
										
										listaEnvioConcepto = boEnvioconcepto.getEnvioconceptoPorItemEnvioConcepto(pId);
										
										if(listaEnvioConcepto != null && !listaEnvioConcepto.isEmpty())
										{
											log.debug("cant listaenvioconcepto="+listaEnvioConcepto.size());
											for(Envioconcepto ec:listaEnvioConcepto)
											{
												EfectuadoConcepto efectuadoc = new EfectuadoConcepto();
												efectuadoc.getId().setIntEmpresaCuentaEnvioPk(ec.getId().getIntEmpresacuentaPk());
												efectuadoc.setIntItemCuentaConcepto(ec.getIntItemcuentaconcepto());
												efectuadoc.setIntItemExpediente(ec.getIntItemexpediente());
												efectuadoc.setIntItemDetExpediente(ec.getIntItemdetexpediente());
												efectuadoc.setIntTipoConceptoGeneralCod(ec.getIntTipoconceptogeneralCod());
												efectuadoc.setBdMontoConcepto(ec.getBdMontoconcepto());									
												efectuadoc.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
												listaEfectuadoConcepto.add(efectuadoc);	
												
												bdMontoTotal = bdMontoTotal.add(e.getBdMontoEfectuado());
												
											}
											log.debug("listaEnvioConcepto="+listaEfectuadoConcepto.size());
											Envioconcepto enconcepto = listaEnvioConcepto.get(0);
											
											if(enconcepto.getIntIndilicencia().compareTo(1)==0)
											{
												e.setBlnLIC(Boolean.TRUE);	
											}else if(enconcepto.getIntIndilicencia().compareTo(0)==0)
											{
												e.setBlnLIC(Boolean.FALSE);
											}
											if(enconcepto.getIntIndidescjudi().compareTo(1)==0)
											{
												e.setBlnDJUD(Boolean.TRUE);	
											}else if(enconcepto.getIntIndidescjudi().compareTo(0)==0)
											{
												e.setBlnDJUD(Boolean.FALSE);	
											}
											if(enconcepto.getIntIndicienpor().compareTo(1)==0)
											{
												e.setBlnCartaAutorizacion(Boolean.TRUE);	
											}else if(enconcepto.getIntIndicienpor().compareTo(0)==0)
											{
												e.setBlnCartaAutorizacion(Boolean.FALSE);
											}
											
											e.setIntCuentaPk(enconcepto.getIntCuentaPk());									
											e.setListaEfectuadoConcepto(listaEfectuadoConcepto);
											
											CuentaId cuentaId = new CuentaId();
											cuentaId.setIntCuenta(enconcepto.getIntCuentaPk());
											cuentaId.setIntPersEmpresaPk(enconcepto.getId().getIntEmpresacuentaPk());
											
											lstCuentaIntegrante = cuentaFacade.getListaCuentaIntegrantePorCuenta(cuentaId);
											
											if(lstCuentaIntegrante != null && !lstCuentaIntegrante.isEmpty())
											{
												for(CuentaIntegrante cuenta : lstCuentaIntegrante)
												{									
													lDocumento = remoteContacto.getDocumentoPorIdPersonaYTipoIdentidad(cuenta.getId().getIntPersonaIntegrante(),																	
																														new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI));
													if(lDocumento != null)
													{
														e.setDocumento(lDocumento);
													}
													SocioPK  socioPK = new SocioPK();
													socioPK.setIntIdEmpresa(cuenta.getId().getIntPersEmpresaPk());
													socioPK.setIntIdPersona(cuenta.getId().getIntPersonaIntegrante());
													
													socio = socioFacade.getSocioPorPK(socioPK);
													
													if(socio != null )
													{
														e.setSocio(socio);
														e.setIntPersonaIntegrante(socio.getId().getIntIdPersona());
														listaSocioEstructura = socioFacade.getListaSocioEstrucuraPorIdPersona(socio.getId().getIntIdPersona(), 
																															  socio.getId().getIntIdEmpresa());
														if(listaSocioEstructura!=null && !listaSocioEstructura.isEmpty())
														{
															e.getSocio().setListSocioEstructura(listaSocioEstructura);
														}
														
														cuentaIntegranteRspta = cuentaFacade.getCuentaIntegrantePorPKSocioYTipoCuentaYTipoIntegrante(e.getSocio().getId(),
																																					Constante.PARAM_T_TIPOCUENTA_ACTIVA,										
																																					Constante.PARAM_T_SITUACIONCUENTA_VIGENTE);
														
														if(cuentaIntegranteRspta != null)
														{
															e.setCuentaIntegrante(cuentaIntegranteRspta);
															log.debug(cuentaIntegranteRspta.getId());													
														}												
													}
												}
											}
											efcomp.setEfectuado(e);
										}
										listaEfectuado.add(e);		
									}
									else if(ento.getIntModalidadCod().compareTo(intSegundaModalidad)== 0)
									{								
										efcomp.setYaEfectuado(new Efectuado());								
										efcomp.getYaEfectuado().setEnvioMonto(new Enviomonto());
										efcomp.getYaEfectuado().setEnvioMonto(ento);								
									}							
								}						
							}
							
							EnvioconceptoId pId = new EnvioconceptoId();
							pId.setIntEmpresacuentaPk(o.getId().getIntEmpresacuentaPk());
							pId.setIntItemenvioconcepto(i.getId().getIntItemenvioconcepto());
							
							listaEnvioConcepto = boEnvioconcepto.getEnvioconceptoPorItemEnvioConcepto(pId);
							
							if(listaEnvioConcepto != null && !listaEnvioConcepto.isEmpty())
							{											
								envioconcepto = listaEnvioConcepto.get(0);						
								
								// aca tengo la cuenta con esto tener el codigo de persona												
								CuentaId cuentaId = new CuentaId();
								cuentaId.setIntCuenta(envioconcepto.getIntCuentaPk());
								cuentaId.setIntPersEmpresaPk(envioconcepto.getId().getIntEmpresacuentaPk());
								
								lstCuentaIntegrante = cuentaFacade.getListaCuentaIntegrantePorCuenta(cuentaId);
								
								if(lstCuentaIntegrante != null && !lstCuentaIntegrante.isEmpty())
								{
									for(CuentaIntegrante cuenta : lstCuentaIntegrante)
									{									
										lDocumento = remoteContacto.getDocumentoPorIdPersonaYTipoIdentidad(cuenta.getId().getIntPersonaIntegrante(),																	
																										   new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI));
										if(lDocumento != null)
										{
											efcomp.setDocumento(lDocumento);
										}
										SocioPK  socioPK = new SocioPK();
										socioPK.setIntIdEmpresa(cuenta.getId().getIntPersEmpresaPk());
										socioPK.setIntIdPersona(cuenta.getId().getIntPersonaIntegrante());
										
										socio = socioFacade.getSocioPorPK(socioPK);
										
										if(socio != null )
										{
											efcomp.setSocio(socio);
											listaSocioEstructura = socioFacade.getListaSocioEstrucuraPorIdPersona(socio.getId().getIntIdPersona(), 
																												  socio.getId().getIntIdEmpresa());
											if(listaSocioEstructura!=null && !listaSocioEstructura.isEmpty())
											{
												efcomp.getSocio().setListSocioEstructura(listaSocioEstructura);
											}									
											cuentaIntegranteRspta = cuentaFacade.getCuentaIntegrantePorPKSocioYTipoCuentaYTipoIntegrante(efcomp.getSocio().getId(),
																																		 Constante.PARAM_T_TIPOCUENTA_ACTIVA,										
																																		 Constante.PARAM_T_SITUACIONCUENTA_VIGENTE);									
											if(cuentaIntegranteRspta != null)
											{
												efcomp.setCuentaIntegrante(cuentaIntegranteRspta);
											}									
										}
									}
								}
							}
							lista.add(efcomp);					
						}
					}
					else
					{
						log.debug("listaconcptos es null");
					}
							
		}catch(Exception e)
		{
			log.error(e.getMessage(),e);
		}		
		return lista;
	}
	
	public List<EfectuadoConceptoComp> getCompletandoEfectuado(Enviomonto o,
			   												   Integer intMaxEnviado,
			   												   Integer intSegundaModalidad)
			   												   throws BusinessException
	{
		log.debug("getCompletandoEfectuado INICIO");
		List<EfectuadoConceptoComp> lista = new ArrayList<EfectuadoConceptoComp>();
		List<Envioconcepto> listaEnvioConcepto = null;
		Envioconcepto envioconcepto = null;		
		Documento lDocumento = null;
		Socio socio = null;
		CuentaIntegrante cuentaIntegranteRspta = null;
		List<SocioEstructura> listaSocioEstructura = null;
		List<CuentaIntegrante> lstCuentaIntegrante = null;
		List<Enviomonto> listaEnviomonto=null;
		List<Enviomonto> listaItemConceptos = null; 	
		List<Efectuado> listaEfectuado = new ArrayList<Efectuado>();
		List<Expediente> listaExpediente = null;
		List<CapacidadCredito> listaCapacidadCre	= null;
		EfectuadoConceptoComp efcomp = null;
		Efectuado e = null;
		Integer intNroAfectados = null;
		intNroAfectados = 0;
		Boolean blnFaltantemostrarMensaje = Boolean.FALSE;
		try
		{
			CuentaFacadeRemote	cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			ContactoFacadeRemote remoteContacto = (ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);
			SocioFacadeRemote  socioFacade = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
			ConceptoFacadeRemote remoteConcepto = (ConceptoFacadeRemote) EJBFactory.getRemote(ConceptoFacadeRemote.class);
			SolicitudPrestamoFacadeRemote solicitudPres   	= (SolicitudPrestamoFacadeRemote)EJBFactory.getRemote(SolicitudPrestamoFacadeRemote.class);
						
			listaItemConceptos = boEnviomonto.getListaItemConcepto(o,intMaxEnviado);
			
			if(listaItemConceptos != null && !listaItemConceptos.isEmpty())
			{
				for(Enviomonto i: listaItemConceptos)
				{
					log.debug("itemenvioconcepto: "+i.getId().getIntItemenvioconcepto());
					efcomp = new EfectuadoConceptoComp();
					efcomp.setBlnAgregarSocio(Boolean.FALSE);
					efcomp.setBlnAgregarNoSocio(Boolean.FALSE);
					Envioconcepto envioCto = new Envioconcepto();
					
					envioCto.setId(new EnvioconceptoId());
					envioCto.getId().setIntEmpresacuentaPk(o.getId().getIntEmpresacuentaPk());
					envioCto.getId().setIntItemenvioconcepto(i.getId().getIntItemenvioconcepto());
					
					listaEnviomonto = boEnviomonto.getListaPorEnvioConcepto(envioCto);
					
					if(listaEnviomonto != null && !listaEnviomonto.isEmpty())
					{
						log.debug("cantDeEnviosMontos="+listaEnviomonto.size());
						List<EfectuadoConcepto> listaEfectuadoConcepto = new ArrayList<EfectuadoConcepto>();										
						
						for(Enviomonto ento: listaEnviomonto)
						{
							if(ento.getIntModalidadCod().compareTo(o.getIntModalidadCod())== 0)
							{
								log.debug("modalidad"+o.getIntModalidadCod());
								intNroAfectados++;								
								
								e = new Efectuado();
								e.setEnvioMonto(ento);
								e.getId().setIntEmpresacuentaPk(ento.getId().getIntEmpresacuentaPk());
								e.setIntPeriodoPlanilla(intMaxEnviado);
								e.setIntTiposocioCod(ento.getIntTiposocioCod());
								e.setIntModalidadCod(ento.getIntModalidadCod());
								e.setIntNivel(ento.getIntNivel());
								e.setIntCodigo(ento.getIntCodigo());
								e.setIntTipoestructuraCod(ento.getIntTipoestructuraCod());
								e.setIntEmpresasucprocesaPk(ento.getIntEmpresasucprocesaPk());
								e.setIntIdsucursalprocesaPk(ento.getIntIdsucursalprocesaPk());
								e.setIntIdsubsucursalprocesaPk(ento.getIntIdsubsucursalprocesaPk());
								e.setIntIdsucursaladministraPk(ento.getIntIdsucursaladministraPk());
								e.setIntEmpresasucadministraPk(ento.getIntEmpresasucadministraPk());
								e.setIntIdsubsucursaladministra(ento.getIntIdsubsucursaladministra());
								e.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
								e.setIntEmpresausuarioPk(ento.getIntEmpresausuarioPk());
								e.setTsFecharegistro(ento.getTsFecharegistro());
								e.setIntPersonausuarioPk(ento.getIntPersonausuarioPk());
								e.setBdMontoEfectuado(ento.getBdMontoenvio());
								e.setBdMontoEfectuado(e.getBdMontoEfectuado().setScale(2,BigDecimal.ROUND_HALF_UP));								
								e.setBdDiferencia(ento.getBdMontoenvio().subtract(e.getBdMontoEfectuado()));
								e.setIntParaTipoIngresoDatoCod(1);
								e.setBlnLIC(Boolean.FALSE);
								e.setBlnCartaAutorizacion(Boolean.FALSE);
								e.setBlnDJUD(Boolean.FALSE);
								e.setBlnListaEnvioConcepto(Boolean.FALSE);
								e.setBlnAgregarNoSocio(Boolean.FALSE);
								
								e.setBlnEfectuadoConcepto0(Boolean.FALSE);
								
									 EnvioconceptoId pId = new EnvioconceptoId();
									 pId.setIntEmpresacuentaPk(o.getId().getIntEmpresacuentaPk());
									 pId.setIntItemenvioconcepto(i.getId().getIntItemenvioconcepto());
									 
									 listaEnvioConcepto = boEnvioconcepto.getEnvioconceptoPorItemEnvioConcepto(pId);
									 
									 if(listaEnvioConcepto != null && !listaEnvioConcepto.isEmpty())
										{
											log.debug("cant listaenvioconcepto="+listaEnvioConcepto.size());
											
											log.debug("listaEnvioConcepto="+listaEfectuadoConcepto.size());
											Envioconcepto enconcepto = listaEnvioConcepto.get(0);
											
											e.setIntCuentaPk(enconcepto.getIntCuentaPk());											
											
											if(enconcepto.getIntIndilicencia().compareTo(1)==0)
											{
												e.setBlnLIC(Boolean.TRUE);	
											}else if(enconcepto.getIntIndilicencia().compareTo(0)==0)
											{
												e.setBlnLIC(Boolean.FALSE);
											}
											if(enconcepto.getIntIndidescjudi().compareTo(1)==0)
											{
												e.setBlnDJUD(Boolean.TRUE);	
											}else if(enconcepto.getIntIndidescjudi().compareTo(0)==0)
											{
												e.setBlnDJUD(Boolean.FALSE);	
											}
											if(enconcepto.getIntIndicienpor().compareTo(1)==0)
											{
												e.setBlnCartaAutorizacion(Boolean.TRUE);	
											}else if(enconcepto.getIntIndicienpor().compareTo(0)==0)
											{
												e.setBlnCartaAutorizacion(Boolean.FALSE);
											}
											
											e.setBlnListaEnvioConcepto(Boolean.TRUE);
											
											/*if(listaEnviomonto.size() == 1)
											{
												e.setListaEfectuadoConcepto(listaEfectuadoConcepto);
												e.setBlnListaEnvioConcepto(Boolean.TRUE);
											}
											 else if(listaEnviomonto.size() == 2)
											{													
												Efectuado efectuado1 = boEfectuado.getEfectuadoPorItemEnvioConcepto(ento);
												if(efectuado1 != null)														
												{
													if(efectuado1.getIntModalidadCod().compareTo(intSegundaModalidad)==0)
													{
														e.getId().setIntItemefectuado(efectuado1.getId().getIntItemefectuado());
														e.setBlnListaEnvioConcepto(Boolean.FALSE);
													}														
												}
											}*/
											
											CuentaId cuentaId = new CuentaId();
											cuentaId.setIntCuenta(enconcepto.getIntCuentaPk());
											cuentaId.setIntPersEmpresaPk(enconcepto.getId().getIntEmpresacuentaPk());
											
											lstCuentaIntegrante = cuentaFacade.getListaCuentaIntegrantePorCuenta(cuentaId);
											
											if(lstCuentaIntegrante != null && !lstCuentaIntegrante.isEmpty())
											{
												for(CuentaIntegrante cuenta : lstCuentaIntegrante)
												{									
													lDocumento = remoteContacto.getDocumentoPorIdPersonaYTipoIdentidad(cuenta.getId().getIntPersonaIntegrante(),																	
																													   new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI));
													if(lDocumento != null)
													{
														e.setDocumento(lDocumento);														
													}
													
													SocioPK  socioPK = new SocioPK();
													socioPK.setIntIdEmpresa(cuenta.getId().getIntPersEmpresaPk());
													socioPK.setIntIdPersona(cuenta.getId().getIntPersonaIntegrante());
													
													socio = socioFacade.getSocioPorPK(socioPK);
													
													if(socio != null )
													{
														e.setIntPersonaIntegrante(socio.getId().getIntIdPersona());
														e.setSocio(socio);
														listaSocioEstructura = socioFacade.getListaSocioEstrucuraPorIdPersona(socio.getId().getIntIdPersona(), 
																															  socio.getId().getIntIdEmpresa());
														if(listaSocioEstructura!=null && !listaSocioEstructura.isEmpty())
														{
															e.getSocio().setListSocioEstructura(listaSocioEstructura);
														}
														
														cuentaIntegranteRspta = cuentaFacade.getCuentaIntegrantePorPKSocioYTipoCuentaYTipoIntegrante(e.getSocio().getId(),
																											 						  Constante.PARAM_T_TIPOCUENTA_ACTIVA,										
																										                        Constante.PARAM_T_SITUACIONCUENTA_VIGENTE);
														
														if(cuentaIntegranteRspta != null)
														{
															e.setCuentaIntegrante(cuentaIntegranteRspta);
														}
																												
														listaExpediente = remoteConcepto.getListaExpedienteConSaldoPorEmpresaYcuenta(e.getSocio().getId().getIntIdEmpresa(),
																																	 e.getCuentaIntegrante().getId().getIntCuenta());

														if(listaExpediente != null && !listaExpediente.isEmpty())
														{
															for(Expediente expediente :listaExpediente)	
															{
																Integer intCarta = 1;
																ExpedienteCreditoId expCre= new ExpedienteCreditoId();
																expCre.setIntCuentaPk(e.getCuentaIntegrante().getId().getIntCuenta());
																expCre.setIntPersEmpresaPk(expediente.getId().getIntPersEmpresaPk());
																expCre.setIntItemExpediente(expediente.getId().getIntItemExpediente());
																expCre.setIntItemDetExpediente(expediente.getId().getIntItemExpedienteDetalle());
																
																listaCapacidadCre = solicitudPres.getListaPorPkExpedienteCredito(expCre);
																if(listaCapacidadCre != null && !listaCapacidadCre.isEmpty())
																{
																	for(CapacidadCredito cato: listaCapacidadCre)
																	{						
																		if(cato.getIntCartaAutorizacion() != null)
																		{
																			if(intCarta.compareTo(cato.getIntCartaAutorizacion()) == 0)
																			{
																				e.setBlnCartaAutorizacion(Boolean.TRUE);
																			}
																		}
																		
																	}
																}
															}
														}
														
													}
												}
											}
											efcomp.setEfectuado(e);
											efcomp.getEfectuado().setEnvioMonto(new Enviomonto());											
											efcomp.getEfectuado().setEnvioMonto(ento);
											
										}
										listaEfectuado.add(e);						  
																	
							}
							else if(ento.getIntModalidadCod().compareTo(intSegundaModalidad)== 0)
							{
								log.debug("segunda modalidad: "+intSegundaModalidad);
								efcomp.setYaEfectuado(new Efectuado());								
								efcomp.getYaEfectuado().setEnvioMonto(new Enviomonto());
								
								Efectuado yaEfectuado = boEfectuado.getEfectuadoPorItemEnvioConcepto(ento);
								if(yaEfectuado != null)
								{
									log.debug(yaEfectuado);
									yaEfectuado.setEnvioMonto(ento);									
									yaEfectuado.setBdDiferencia(yaEfectuado.getEnvioMonto().getBdMontoenvio().subtract(yaEfectuado.getBdMontoEfectuado()));									
									efcomp.setYaEfectuado(yaEfectuado);
									blnFaltantemostrarMensaje = Boolean.TRUE;
								}
								
							}
							
						}
						
					}
					
					EnvioconceptoId pId = new EnvioconceptoId();
					pId.setIntEmpresacuentaPk(o.getId().getIntEmpresacuentaPk());
					pId.setIntItemenvioconcepto(i.getId().getIntItemenvioconcepto());
					
					listaEnvioConcepto = boEnvioconcepto.getEnvioconceptoPorItemEnvioConcepto(pId);
					
					if(listaEnvioConcepto != null && !listaEnvioConcepto.isEmpty())
					{
											
						envioconcepto = listaEnvioConcepto.get(0);						
						
						if(envioconcepto.getIntIndilicencia().compareTo(1)==0)
						{
							efcomp.getEfectuado().setBlnLIC(Boolean.TRUE);	
						}else if(envioconcepto.getIntIndilicencia().compareTo(0)==0)
						{
							efcomp.getEfectuado().setBlnLIC(Boolean.FALSE);
						}
						if(envioconcepto.getIntIndidescjudi().compareTo(1)==0)
						{
							efcomp.getEfectuado().setBlnDJUD(Boolean.TRUE);	
						}else if(envioconcepto.getIntIndidescjudi().compareTo(0)==0)
						{
							efcomp.getEfectuado().setBlnDJUD(Boolean.FALSE);	
						}
						if(envioconcepto.getIntIndicienpor().compareTo(1)==0)
						{
							efcomp.getEfectuado().setBlnCartaAutorizacion(Boolean.TRUE);	
						}else if(envioconcepto.getIntIndicienpor().compareTo(0)==0)
						{
							//e.setBlnCartaAutorizacion(Boolean.FALSE);
							efcomp.getEfectuado().setBlnCartaAutorizacion(Boolean.FALSE);
						}
						
						// aca tengo la cuenta con esto tener el codigo de persona												
						CuentaId cuentaId = new CuentaId();
						cuentaId.setIntCuenta(envioconcepto.getIntCuentaPk());
						cuentaId.setIntPersEmpresaPk(envioconcepto.getId().getIntEmpresacuentaPk());
						
						lstCuentaIntegrante = cuentaFacade.getListaCuentaIntegrantePorCuenta(cuentaId);
						
						if(lstCuentaIntegrante != null && !lstCuentaIntegrante.isEmpty())
						{
							for(CuentaIntegrante cuenta : lstCuentaIntegrante)
							{									
								lDocumento = remoteContacto.getDocumentoPorIdPersonaYTipoIdentidad(
															cuenta.getId().getIntPersonaIntegrante(),																	
															new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI));
								if(lDocumento != null)
								{
									efcomp.setDocumento(lDocumento);
								}
								SocioPK  socioPK = new SocioPK();
								socioPK.setIntIdEmpresa(cuenta.getId().getIntPersEmpresaPk());
								socioPK.setIntIdPersona(cuenta.getId().getIntPersonaIntegrante());
								
								socio = socioFacade.getSocioPorPK(socioPK);
								
								if(socio != null )
								{
									efcomp.setSocio(socio);
									listaSocioEstructura = socioFacade.getListaSocioEstrucuraPorIdPersona(socio.getId().getIntIdPersona(), 
																										  socio.getId().getIntIdEmpresa());
									if(listaSocioEstructura!=null && !listaSocioEstructura.isEmpty())
									{
										efcomp.getSocio().setListSocioEstructura(listaSocioEstructura);
									}
									
									cuentaIntegranteRspta = cuentaFacade.getCuentaIntegrantePorPKSocioYTipoCuentaYTipoIntegrante(										
																	efcomp.getSocio().getId(),Constante.PARAM_T_TIPOCUENTA_ACTIVA,										
																	Constante.PARAM_T_SITUACIONCUENTA_VIGENTE);
									
									if(cuentaIntegranteRspta != null)
									{
										efcomp.setCuentaIntegrante(cuentaIntegranteRspta);										
									}
								}
							}
						}
					}
					lista.add(efcomp);					
				}
			  }
			else
			{
				log.debug("listaconcptos es null");
			}
			
			log.debug("getCompletandoEfectuado FIN V3");
			
		}catch(BusinessException ee){
			throw ee;
		}
		 
		catch(Exception ee){
			throw new BusinessException(ee);
		}		
		
		return lista;
	}

	public List<EfectuadoResumen> getPlanillaEfectuadaResumen (Enviomonto o,
															   Integer periodo)throws BusinessException
	{
		log.debug("getPlanillaEfectuadaResumen INICIO");
		List<EfectuadoResumen> lista = new ArrayList<EfectuadoResumen>();
		List<Efectuado> listaEfectuado = null;		
		List<CuentaIntegrante> lstCuentaIntegrante = null;
		List<SocioEstructura> listaSocioEstructura = null;		
		CuentaIntegrante cuentaIntegranteRspta = null;
		List<Enviomonto> listaEnvioMonto = null; 
		List<Envioconcepto> lstEnvioC = null;
		Documento lDocumento = null;
		Socio socio = null;		
				
		BigDecimal bdMontoTotal = new BigDecimal(0);
		try
		{
			PlanillaFacadeLocal localPlanilla = (PlanillaFacadeLocal)EJBFactory.getLocal(PlanillaFacadeLocal.class);
			CuentaFacadeRemote	cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			ContactoFacadeRemote remoteContacto 	= (ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);
			SocioFacadeRemote  socioFacade = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
			
								
					EfectuadoResumen er = new EfectuadoResumen();			
					er.getId().setIntEmpresa(o.getId().getIntEmpresacuentaPk());
					er.setIntPeriodoPlanilla(periodo);
					er.setIntTiposocioCod(o.getIntTiposocioCod());
					er.setIntModalidadCod(o.getIntModalidadCod());
					er.setIntNivel(o.getIntNivel());
					er.setIntCodigo(o.getIntCodigo());					
					er.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					er.setIntParaDocumentoGeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAEFECTUADA);
					
					er.setIntIdsucursaladministraPk(o.getIntIdsucursaladministraPk());
					er.setIntIdsubsucursaladministra(o.getIntIdsubsucursaladministra());
					
					listaEnvioMonto = localPlanilla.getListaEnvioMontoPlanillaEfectuada(o, periodo);
					
					if(listaEnvioMonto != null && !listaEnvioMonto.isEmpty()) 
					{
						log.debug("listaenviomonto:"+listaEnvioMonto.size());
						Enviomonto eto = listaEnvioMonto.get(0);						
						er.setIntIdsucursalprocesaPk(eto.getIntIdsucursalprocesaPk());
						er.setIntIdsubsucursalprocesaPk(eto.getIntIdsubsucursalprocesaPk());
						er.setIntNumeroAfectados(listaEnvioMonto.size());
						er.setIntPersonausuarioPk(eto.getIntPersonausuarioPk());
						listaEfectuado = new ArrayList<Efectuado>();
						for(Enviomonto eo: listaEnvioMonto)
						{
							Efectuado e = new Efectuado();
							e.setEnvioMonto(eo);
							e.getId().setIntEmpresacuentaPk(eo.getId().getIntEmpresacuentaPk());
							e.setIntPeriodoPlanilla(periodo);
							e.setIntTiposocioCod(eo.getIntTiposocioCod());
							e.setIntModalidadCod(eo.getIntModalidadCod());
							e.setIntNivel(eo.getIntNivel());
							e.setIntCodigo(eo.getIntCodigo());
							e.setIntTipoestructuraCod(eo.getIntTipoestructuraCod());
							e.setIntEmpresasucprocesaPk(eo.getIntEmpresasucprocesaPk());
							e.setIntIdsucursalprocesaPk(eo.getIntIdsucursalprocesaPk());
							e.setIntIdsubsucursalprocesaPk(eo.getIntIdsubsucursalprocesaPk());
							e.setIntIdsucursaladministraPk(eo.getIntIdsucursaladministraPk());
							e.setIntEmpresasucadministraPk(eo.getIntEmpresasucadministraPk());					
							e.setIntIdsubsucursaladministra(eo.getIntIdsubsucursaladministra());					
							e.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
							e.setIntEmpresausuarioPk(eo.getIntEmpresausuarioPk());
							e.setTsFecharegistro(eo.getTsFecharegistro());
							e.setIntPersonausuarioPk(eo.getIntPersonausuarioPk());
							e.setBdMontoEfectuado(eo.getBdMontoenvio());
							e.setIntParaTipoIngresoDatoCod(1);
							e.setBlnAgregarNoSocio(Boolean.FALSE);		
							
							e.setBlnEfectuadoConcepto0(Boolean.FALSE);
							
							bdMontoTotal = bdMontoTotal.add(e.getBdMontoEfectuado());
							
							EnvioconceptoId envio = new EnvioconceptoId();					
							envio.setIntItemenvioconcepto(eo.getId().getIntItemenvioconcepto());
							envio.setIntEmpresacuentaPk(eo.getId().getIntEmpresacuentaPk());					

							lstEnvioC = boEnvioconcepto.getEnvioconceptoPorItemEnvioConcepto(envio);
							 
							if(lstEnvioC != null && !lstEnvioC.isEmpty())
							{						
								Envioconcepto envioconcepto = lstEnvioC.get(0);
								e.setBlnListaEnvioConcepto(Boolean.TRUE);						
								if(envioconcepto.getIntIndilicencia().compareTo(1)==0)
								{
									e.setBlnLIC(Boolean.TRUE);	
								}else if(envioconcepto.getIntIndilicencia().compareTo(0)==0)
								{
									e.setBlnLIC(Boolean.FALSE);
								}
								if(envioconcepto.getIntIndidescjudi().compareTo(1)==0)
								{
									e.setBlnDJUD(Boolean.TRUE);	
								}else if(envioconcepto.getIntIndidescjudi().compareTo(0)==0)
								{
									e.setBlnDJUD(Boolean.FALSE);	
								}
								if(envioconcepto.getIntIndicienpor().compareTo(1)==0)
								{
									e.setBlnCartaAutorizacion(Boolean.TRUE);	
								}else if(envioconcepto.getIntIndicienpor().compareTo(0)==0)
								{
									e.setBlnCartaAutorizacion(Boolean.FALSE);
								}
								e.setIntCuentaPk(envioconcepto.getIntCuentaPk());
								CuentaId cuentaId = new CuentaId();
								cuentaId.setIntCuenta(envioconcepto.getIntCuentaPk());
								cuentaId.setIntPersEmpresaPk(envioconcepto.getId().getIntEmpresacuentaPk());
								
								lstCuentaIntegrante = cuentaFacade.getListaCuentaIntegrantePorCuenta(cuentaId);
								
								if(lstCuentaIntegrante != null && !lstCuentaIntegrante.isEmpty())
								{
									for(CuentaIntegrante cuenta : lstCuentaIntegrante)
									{									
										lDocumento = remoteContacto.getDocumentoPorIdPersonaYTipoIdentidad(
																	cuenta.getId().getIntPersonaIntegrante(),																	
																	new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI));
										if(lDocumento != null)
										{
											e.setDocumento(lDocumento);
										}
										SocioPK  socioPK = new SocioPK();
										socioPK.setIntIdEmpresa(cuenta.getId().getIntPersEmpresaPk());
										socioPK.setIntIdPersona(cuenta.getId().getIntPersonaIntegrante());
										
										socio = socioFacade.getSocioPorPK(socioPK);
										
										if(socio != null )
										{
											e.setSocio(socio);
											e.setIntPersonaIntegrante(socio.getId().getIntIdPersona());
											listaSocioEstructura = socioFacade.getListaSocioEstrucuraPorIdPersona(socio.getId().getIntIdPersona(), 
																												  socio.getId().getIntIdEmpresa());
											if(listaSocioEstructura!=null && !listaSocioEstructura.isEmpty())
											{
												e.getSocio().setListSocioEstructura(listaSocioEstructura);
											}
											cuentaIntegranteRspta = cuentaFacade.getCuentaIntegrantePorPKSocioYTipoCuentaYTipoIntegrante(										
																													e.getSocio().getId(),
																													Constante.PARAM_T_TIPOCUENTA_ACTIVA,										
																													Constante.PARAM_T_SITUACIONCUENTA_VIGENTE);
											if(cuentaIntegranteRspta != null)
											{
												e.setIntPersonaIntegrante(cuentaIntegranteRspta.getId().getIntPersonaIntegrante());
												e.setCuentaIntegrante(cuentaIntegranteRspta);
											}
										}
									}
								}
							}
							e.setBdMontoEfectuado(e.getBdMontoEfectuado().setScale(2, BigDecimal.ROUND_HALF_UP));				
							e.setBdMontoEnvio(e.getBdMontoEfectuado());					
							e.setBdDiferencia(e.getBdMontoEnvio().subtract(e.getBdMontoEfectuado()));					
							e.setBdEfectuadoTotal(e.getBdMontoEfectuado().subtract(e.getBdDiferencia()));					
							listaEfectuado.add(e);
						}
						er.setBdMontoTotal(bdMontoTotal);
						er.setListaEfectuado(listaEfectuado);
						er.setIntParaEstadoPago(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					}
					else 
					{
						throw new BusinessException(FacesContextUtil.MESSAGE_ERROR_ONSAVE);					
					}
				
					lista.add(er);
						
			log.debug("getPlanillaEfectuadaResumen FIN");
		}
		catch(BusinessException e){			
			throw e;
		}
		catch (EJBFactoryException e) {			
			throw new BusinessException(e);
		} 
		catch(Exception e){			
			throw new BusinessException(e);
		}
		
		return lista;
	}
	
	

	

	
	/***
	 * LO DE TEXTO
	 */
	
}
