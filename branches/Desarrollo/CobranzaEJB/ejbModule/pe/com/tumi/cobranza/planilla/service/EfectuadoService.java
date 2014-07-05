package pe.com.tumi.cobranza.planilla.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

//import com.ibm.jsse2.util.e;

import pe.com.tumi.cobranza.planilla.bo.EfectuadoBO;
import pe.com.tumi.cobranza.planilla.bo.EfectuadoConceptoBO;
import pe.com.tumi.cobranza.planilla.bo.EfectuadoResumenBO;
import pe.com.tumi.cobranza.planilla.bo.EnvioconceptoBO;
import pe.com.tumi.cobranza.planilla.bo.EnviomontoBO;
import pe.com.tumi.cobranza.planilla.bo.EnvioresumenBO;
import pe.com.tumi.cobranza.planilla.domain.Efectuado;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoId;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoResumen;
//import pe.com.tumi.cobranza.planilla.domain.EfectuadoResumenId;
import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;
import pe.com.tumi.cobranza.planilla.domain.Enviomonto;
import pe.com.tumi.cobranza.planilla.domain.Envioresumen;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.framework.util.fecha.JFecha;
import pe.com.tumi.movimiento.concepto.domain.ConceptoDetallePago;
import pe.com.tumi.movimiento.concepto.domain.ConceptoDetallePagoId;
import pe.com.tumi.movimiento.concepto.domain.ConceptoPago;
import pe.com.tumi.movimiento.concepto.domain.ConceptoPagoId;
import pe.com.tumi.movimiento.concepto.domain.Cronograma;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalleId;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoId;
import pe.com.tumi.movimiento.concepto.domain.EstadoExpediente;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.movimiento.concepto.domain.ExpedienteId;
import pe.com.tumi.movimiento.concepto.domain.InteresCancelado;
import pe.com.tumi.movimiento.concepto.domain.InteresCanceladoId;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.contacto.domain.DocumentoPK;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaEmpresa;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.domain.PersonaRolPK;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
//import pe.com.tumi.riesgo.cartera.domain.CarteraCreditoDetalle;
//import pe.com.tumi.riesgo.cartera.facade.CarteraFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
//import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.facade.SolicitudPrestamoFacadeRemote;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoConcepto;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.UtilCobranza;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioId;
import pe.com.tumi.contabilidad.cierre.facade.LibroDiarioFacadeRemote;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalle;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivel;
import pe.com.tumi.contabilidad.core.facade.ModeloFacadeRemote;
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
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;


public class EfectuadoService {
	
	protected static Logger log = Logger.getLogger(EfectuadoService.class);
	
	private EfectuadoResumenBO boEfectuadoResumen 	= (EfectuadoResumenBO)TumiFactory.get(EfectuadoResumenBO.class);
	private EfectuadoBO boEfectuado 				= (EfectuadoBO)TumiFactory.get(EfectuadoBO.class);	
	private EfectuadoConceptoBO boEfectuadoConcepto = (EfectuadoConceptoBO)TumiFactory.get(EfectuadoConceptoBO.class);	
	private EnvioconceptoBO boEnvioconcepto  		= (EnvioconceptoBO)TumiFactory.get(EnvioconceptoBO.class);	
	private EnviomontoBO boEnviomonto 				= (EnviomontoBO)TumiFactory.get(EnviomontoBO.class);	
	private EnvioresumenBO boEnvioresumen   		= (EnvioresumenBO)TumiFactory.get(EnvioresumenBO.class);
	
	HashMap<String, BigDecimal> mapA = new HashMap<String, BigDecimal>();	
	HashMap<String, BigDecimal> mapI = new HashMap<String, BigDecimal>();
 
	
	public void grabarPlanillaEfectuadaResumen(List<EfectuadoResumen> listaEfectuadoResumen,
											   			   Usuario pUsuario) throws BusinessException
	{
		log.info("EfectuadoService.grabarPlanillaEfectuadaResumen()=====>INICIO v4");					
		EfectuadoResumen domain = null;
		
		try
		{	for (EfectuadoResumen efectuadoResumen : listaEfectuadoResumen) {
				domain = boEfectuadoResumen.grabar(efectuadoResumen);				
				
				if(efectuadoResumen.getListaEfectuado() != null && !efectuadoResumen.getListaEfectuado().isEmpty())
				{				
					for(Efectuado efectuado: efectuadoResumen.getListaEfectuado())
					{
						efectuado.setEfectuadoResumen(domain);
						//grabar un socio
						if(!efectuado.getBlnAgregarNoSocio())
						{
							grabandoUnSocio(domain, efectuado, pUsuario);
						}
						else
						{
						 	grabandoUnNoSocio(efectuado, pUsuario);
						}
					}					 
					 grabandoContabilidad(domain);
				}	
			}						
		log.debug("EfectuadoService.grabarPlanillaEfectuadaResumen()=====>FIN v4");
						
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		
	}
	
	private void grabandoContabilidad(EfectuadoResumen erContabilidad) throws BusinessException{

		log.debug("grabandoContabilidad INICIO v2");
		BigDecimal bdMonto = new BigDecimal(0);		
		BigDecimal bdMontoCPP = new BigDecimal(0);
		BigDecimal bdMontoByAdministra = new BigDecimal(0);	
		Integer intItemEfectuadoResumen = 0;
		Integer paraTipoConcepto = 0;
		Integer intConceptoGeneral = 0;	
		Integer codPersona = 0;
		Integer periodo = 0;
		Integer empresa = 0;
		Integer n = 1;	
		Integer intNumeroConRol10 = 0;
		Integer codigoModeloDebe = 0;
		String strNumeroCuenta = null;
		String strComentario = null;	
		String strComentarioCPP = null;
		String strComentarioDebe = null;
		String strNroCuentaCuentaPorPagar = null;
		EstructuraId id= null;
		LibroDiario libroDiario = null;
		ModeloDetalle modeloDetalleDebe = null;		
		List<EfectuadoConcepto> listaAmortizacion = null;
		List<EfectuadoConcepto> listaInteres = null;
		List<Efectuado> listaPorAdministra = null;
		List<EfectuadoConcepto> listaFinalInteres = null;
		List<EfectuadoConcepto> listaFinalAmortizacion = null;
		List<EfectuadoConcepto> listaPrestamosInteres = null;
		List<ModeloDetalleNivel> listaNivel =  null;
		LibroDiarioDetalle libroDiarioDetalle = null;
		mapA.clear();
		mapI.clear();
		try
		{
			EstructuraFacadeRemote remoteEstructura 	= (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			LibroDiarioFacadeRemote libroDiarioFacade 	=  (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			ModeloFacadeRemote 		modeloFacade 		=  (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);	
			
			intItemEfectuadoResumen = erContabilidad.getId().getIntItemEfectuadoResumen();
			listaPorAdministra = boEfectuado.getListaPorAdministra(intItemEfectuadoResumen);
				
				if(listaPorAdministra != null && !listaPorAdministra.isEmpty())
				{
					log.debug("cant listaPorAdministra"+listaPorAdministra.size());
					libroDiario = generarLibroDiario(erContabilidad);
					if(libroDiario != null)
					{
						periodo = new Integer(libroDiario.getId().getIntContPeriodoLibro().toString().substring(0, 4));
						empresa = erContabilidad.getId().getIntEmpresa();
						 
						id= new EstructuraId();
						id.setIntNivel(erContabilidad.getIntNivel());
						id.setIntCodigo(erContabilidad.getIntCodigo());
						
						Estructura estructura = remoteEstructura.getEstructuraPorPk(id);
						if(estructura!= null)
						{
							codPersona = estructura.getIntPersPersonaPk();
							log.debug("codPersona: "+codPersona);
						}
						for(Efectuado efectuado: listaPorAdministra)
						{
							log.debug("sucursal: "+efectuado.getIntIdsucursaladministraPk());
							log.debug("subSucursal: "+efectuado.getIntIdsubsucursaladministra());
							bdMontoByAdministra = new BigDecimal(0);
							//la suma de mis conceptos de todas las cuentas por efectuadoResumen
							while(n < 5)
							{
								bdMonto = new BigDecimal(0);
								//listaModeloDetalle =  null;
								if(Constante.PARAM_T_CUENTACONCEPTO_APORTES.compareTo(n)==0)
								{
									log.debug("APORTES");
									intConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_APORTACION;
									paraTipoConcepto = Constante.PARA_TIPO_CONCEPTO_APORTE;
									strComentario = "Aportes de Socios";
								}else if(Constante.PARAM_T_CUENTACONCEPTO_SEPELIO.compareTo(n)==0)
								{
									log.debug("SEPELIO");								
									intConceptoGeneral= Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDOSEPELIO;
									paraTipoConcepto = Constante.PARA_TIPO_CONCEPTO_SEPELIO;
									strComentario = "Fondo de Sepelio";
								}else if(Constante.PARAM_T_CUENTACONCEPTO_RETIRO.compareTo(n)==0)
								{
									log.debug("RETIRO");								
									intConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO;
									paraTipoConcepto = Constante.PARA_TIPO_CONCEPTO_RETIRO;
									strComentario = "Fondo de retiro Fonret";
								}else if(Constante.PARAM_T_CUENTACONCEPTO_MANTENIMIENTO_CUENTA.compareTo(n)==0)
								{
									log.debug("MANT CUENTA");								
									intConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_MANTENIMIENTO;
									paraTipoConcepto = Constante.PARA_TIPO_CONCEPTO_MANTENIMIENTO;
									strComentario = "Mantenimiento Cta.Cte Socios";
								}
									log.debug("empresa: "+empresa);
									log.debug("periodo: "+periodo);
									log.debug("itemEfectuadoResumen: "+intItemEfectuadoResumen);
									log.debug("paraTipoConcepto:"+paraTipoConcepto);
									
									strNumeroCuenta = boEfectuadoResumen.getNumeroCuenta(empresa,
																						 intItemEfectuadoResumen,
																						 periodo,
																						 paraTipoConcepto);
										if(strNumeroCuenta != null){
											if(strNumeroCuenta.length()> 0 )
											{
												
												log.debug("numero cuenta: "+strNumeroCuenta);											
												codigoModeloDebe = Constante.PARAM_T_CODIGOMODELO;																						
												strComentarioDebe = "Cuentas por cobrar pagadurias socios";
																							
													Efectuado efectuadoMonto = boEfectuado.getMontoTotalPorConcepto(intItemEfectuadoResumen,
																													efectuado.getIntIdsucursaladministraPk(),
																													efectuado.getIntIdsubsucursaladministra(),
																													intConceptoGeneral,
																													paraTipoConcepto,
																													empresa,
																													periodo,
																													Constante.PARAM_T_CODIGOMODELO,
																													strNumeroCuenta);
													
													if(efectuadoMonto.getBdMontoEfectuado() != null)
													{
														log.debug("montoTotal por Concepto:: "+efectuadoMonto.getBdMontoEfectuado());
														bdMonto = efectuadoMonto.getBdMontoEfectuado();
														bdMontoByAdministra = bdMontoByAdministra.add(bdMonto);
														libroDiarioDetalle = new LibroDiarioDetalle();							
														libroDiarioDetalle.setIntPersEmpresaCuenta(libroDiario.getId().getIntPersEmpresaLibro());
														libroDiarioDetalle.setIntContPeriodo(new Integer(libroDiario.getId().getIntContPeriodoLibro().toString().substring(0,4)));
														libroDiarioDetalle.setStrContNumeroCuenta(strNumeroCuenta);
														libroDiarioDetalle.setIntPersPersona(codPersona);//codPersona de la UnidadEjecutora
														libroDiarioDetalle.setIntParaDocumentoGeneral(libroDiario.getIntParaTipoDocumentoGeneral());
														libroDiarioDetalle.setStrSerieDocumento(null);
														libroDiarioDetalle.setStrNumeroDocumento(null);
														libroDiarioDetalle.setIntPersEmpresaSucursal(libroDiario.getId().getIntPersEmpresaLibro());
														libroDiarioDetalle.setIntSucuIdSucursal(efectuado.getIntIdsucursaladministraPk()); //sucursaladministra
														libroDiarioDetalle.setIntSudeIdSubSucursal(efectuado.getIntIdsubsucursaladministra()); //subsucursaladministra
														libroDiarioDetalle.setStrComentario(strComentario);
														libroDiarioDetalle.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
														libroDiarioDetalle.setBdHaberSoles(bdMonto);
														libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalle);
													}else{
														log.debug("no tiene monto conceptoGeneral: "+intConceptoGeneral); 
													}											
											}else{
													log.debug("no tiene monto paraTipoConcepto: "+paraTipoConcepto);
													continue;
												}
										}else{
											log.debug("no tiene strnumerocuenta...");
											continue;
										}										
								n++;
							}
														
						   // INICIO PRESTAMOS
							listaInteres = new ArrayList<EfectuadoConcepto>();
							listaAmortizacion = new ArrayList<EfectuadoConcepto>();
							listaPrestamosInteres = boEfectuadoConcepto.prestamoInteres(intItemEfectuadoResumen);
							if(listaPrestamosInteres != null && !listaPrestamosInteres.isEmpty())
							{
								log.debug("listaPrestamosInteres: "+listaPrestamosInteres.size());
								for(EfectuadoConcepto ec:listaPrestamosInteres)
								{
									log.debug("monto: "+ec.getBdMontoConcepto()+
											", conceptoGeneral: "+ec.getIntTipoConceptoGeneralCod()+
											  ", paratipoCredito: "+ec.getIntParaTipoConceptoCod()+
											  ", itemConcepto: "+ec.getIntItemConcepto()+
											  ", intCategoria: "+ec.getIntCategoria());
									if(ec.getIntParaTipoConceptoCod().compareTo(3) == 0
											||ec.getIntParaTipoConceptoCod().compareTo(5) == 0)
									{
										//no manda categoria										
										listaNivel = modeloFacade.getNroCtaPrestamoSinCategoria(empresa,
																								periodo,
																								ec.getIntParaTipoConceptoCod(),
																								ec.getIntItemConcepto(),																												  
																								ec.getIntTipoConceptoGeneralCod());
										if(listaNivel != null && !listaNivel.isEmpty())
										{
											for(ModeloDetalleNivel mod: listaNivel)
											{
												ec.setStrContNumeroCuenta(mod.getId().getStrContNumeroCuenta());
												log.debug("ec.strnrocuenta: "+ec.getStrContNumeroCuenta());
												break;
											}
										}else
										{
											log.debug("no hay lista nivel.....");
										}		
									}else
									{
										log.debug("manda categoria:");//manda categoria
										listaNivel = modeloFacade.getNumeroCuentaPrestamo(empresa,
																							periodo,
																							ec.getIntParaTipoConceptoCod(),
																							ec.getIntItemConcepto(),
																							ec.getIntCategoria(),
																							ec.getIntTipoConceptoGeneralCod());
										log.debug("emp: "+empresa+" ,periodo: "+periodo+" ,paraTipoConceptoCod: "+ec.getIntParaTipoConceptoCod()+
												" , itemConcepto: "+ec.getIntItemConcepto()+" ,intcategoria: "+ec.getIntCategoria()+
												" ,intTipoConceptoGeneralCod: "+ec.getIntTipoConceptoGeneralCod());
										if(listaNivel != null && !listaNivel.isEmpty())
										{
											for(ModeloDetalleNivel mod: listaNivel)
											{
												ec.setStrContNumeroCuenta(mod.getId().getStrContNumeroCuenta());
												log.debug("ec.strnrocuenta: "+ec.getStrContNumeroCuenta());
												break;
											}
										}else
										{
											log.debug("no hay lista nivel.....");
										}
									
									}
									
									if(ec.getIntTipoConceptoGeneralCod().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION) == 0)
									{
										listaAmortizacion.add(ec);
									}else if(ec.getIntTipoConceptoGeneralCod().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES) == 0)
									{
										listaInteres.add(ec);
									}
									
								}
								//aqui agrupar lo que tengo es decir si es la misma cuenta agrupar sus montos
							listaFinalAmortizacion = 	agrupandoAmortizacion(listaAmortizacion);
							listaFinalInteres = 	agrupandoInteres(listaInteres);
							for(EfectuadoConcepto ecA:listaFinalAmortizacion)
							{
								strComentario = "Amortizacion de prestamo";
								if(ecA.getStrContNumeroCuenta().length() > 0 )
								{
									agregarLibroDiarioDetallePrestamo(ecA.getStrContNumeroCuenta(),
																	  libroDiario,
																	  efectuado,
																	  codPersona,
																	  ecA.getBdMontoConcepto(),
																	  strComentario);
									bdMontoByAdministra = bdMontoByAdministra.add(ecA.getBdMontoConcepto());
								}else
								{
									throw new BusinessException("ERROR EN CONTABILIDAD PRESTAMO numeroCuenta es vacia");
								}								
							}
							for(EfectuadoConcepto ecI :listaFinalInteres)
							{								
								strComentario = "Interes de prestamo";
								if(ecI.getStrContNumeroCuenta().length()>0)
								{
									agregarLibroDiarioDetallePrestamo(ecI.getStrContNumeroCuenta(),
																	  libroDiario,
																	  efectuado,
																	  codPersona,
																	  ecI.getBdMontoConcepto(),
																	  strComentario);
									bdMontoByAdministra = bdMontoByAdministra.add(ecI.getBdMontoConcepto());
								}else
								{
									throw new BusinessException("ERROR EN CONTABILIDAD INTERES numeroCuenta es vacia");
								}								
							}
							}else
							{
								log.debug("lista de prestamos e interes es nulllllll");
							}														
							//FIN PRESTAMOS
							 
							//INICIO CUENTA POR PAGAR
							log.debug("cuenta por pagar 13");							
							List<EfectuadoConcepto> listaMontoCPP = boEfectuadoConcepto.montoCuentaPorPagar(intItemEfectuadoResumen);
							if(listaMontoCPP != null && !listaMontoCPP.isEmpty())
							{
								for(EfectuadoConcepto efectuadoConcepto: listaMontoCPP)
								{									
									if(efectuadoConcepto.getBdMontoConcepto() != null)
									{
										bdMontoCPP  = efectuadoConcepto.getBdMontoConcepto();
										log.debug("bdMontoCPP: " +bdMontoCPP+
												  "empresa: "    +empresa+ 
												  ", periodo: "  +periodo);										

										strNroCuentaCuentaPorPagar = modeloFacade.getCuentaPorPagar(empresa,
																									 periodo);
										if(strNroCuentaCuentaPorPagar != null)
										{
											if(strNroCuentaCuentaPorPagar.length() > 0 )
											{
												log.debug("CUENTAPORPAGAR: "+strNroCuentaCuentaPorPagar);
												strComentarioCPP = "Cuenta por pagar";
												
													agregarLibroDiarioDetallePrestamo(strNroCuentaCuentaPorPagar,
																					  libroDiario,
																					  efectuado,
																					  codPersona,
																					  bdMontoCPP,
																					  strComentarioCPP);
													bdMontoByAdministra = bdMontoByAdministra.add(bdMontoCPP);											
											}else
											{
												log.debug("strNroCuentaCuentaPorPagar is 0");
											}
										}else
										{
											log.debug("strNroCuentaCuentaPorPagar is null");
										}
										
										break;	
									}else
									{
										log.debug("no tiene cuenta por pagar en efectuadoconcepto");
									}
								 }
							  }
							//FIN CUENTA POR PAGAR
							
							if(codigoModeloDebe.compareTo(0) == 1)
							{
								if(id.getIntNivel().compareTo(Constante.PARAM_T_NIVEL) == 0 &&
										   id.getIntCodigo().compareTo(Constante.PARAM_T_CODIGO) == 0)
											{
												log.debug("iguales a cooperativa");
												intNumeroConRol10 = boEfectuado.getNumerodeRolesUsuarios(intItemEfectuadoResumen);
												if(erContabilidad.getIntNumeroAfectados().compareTo(intNumeroConRol10) == 0)
												{
													log.debug("todos con rol 10");
													List<ModeloDetalle> listaModeloDetalleConRol = modeloFacade.getListaDebeOfCobranzaUSUARIO10(empresa,
																																				periodo,
																																			    codigoModeloDebe,
																																			    id.getIntCodigo());
													if(listaModeloDetalleConRol!= null && !listaModeloDetalleConRol.isEmpty())
													{
														modeloDetalleDebe = listaModeloDetalleConRol.get(0);
														log.debug("NUMEROCUENTA  todos tienen rol 10: "+modeloDetalleDebe.getId().getStrContNumeroCuenta());
													}
												}else
												{
													log.debug("intNumeroConRol10: "+intNumeroConRol10);
													//	if(intNumeroConRol10.compareTo(0) == 0 || )
													//	{										
															log.debug("no tiene ni uno con rol 10");
															List<ModeloDetalle> listaModeloDetalleConRol = modeloFacade.getListaDebeOfCobranzaUSUARIONO10(empresa,
																																						  periodo,
																																					      codigoModeloDebe,
																																					      id.getIntCodigo());
															if(listaModeloDetalleConRol!= null && !listaModeloDetalleConRol.isEmpty())
															{
																modeloDetalleDebe = listaModeloDetalleConRol.get(0);
																log.debug("NUMEROCUENTA no rol 10: "+modeloDetalleDebe.getId().getStrContNumeroCuenta());
															}
														//}							
												}							
											}else
											{	
												log.debug("no iguales a cooperativa");
												log.debug("empresa: "+empresa);
												log.debug("codigoModeloDebe: "+codigoModeloDebe);
												log.debug("periodo: "+periodo);
															
												List<ModeloDetalle> listaModeloDetalleDebe = modeloFacade.getListaDebeOfCobranza(empresa,
																																 periodo,
																																 codigoModeloDebe);
												if(listaModeloDetalleDebe!= null && !listaModeloDetalleDebe.isEmpty())
												{
													modeloDetalleDebe = listaModeloDetalleDebe.get(0);								
												}
												
											}	
							}else
							{
								log.debug("codigoModeloDebe: "+codigoModeloDebe);
								return; 
							}

								//agregando el debe
								if(modeloDetalleDebe != null)
								{
									log.debug("numeroCuenta: "+modeloDetalleDebe.getId().getStrContNumeroCuenta());
									log.debug("OpcionDebeHaber: "+modeloDetalleDebe.getIntParaOpcionDebeHaber());
									LibroDiarioDetalle libroDiarioDetalleDebe = new LibroDiarioDetalle();							
									libroDiarioDetalleDebe.setIntPersEmpresaCuenta(libroDiario.getId().getIntPersEmpresaLibro());
									libroDiarioDetalleDebe.setIntContPeriodo(new Integer(libroDiario.getId().getIntContPeriodoLibro().toString().substring(0,4)));
									libroDiarioDetalleDebe.setStrContNumeroCuenta(modeloDetalleDebe.getId().getStrContNumeroCuenta());
									libroDiarioDetalleDebe.setIntPersPersona(codPersona);//codPersona de la UnidadEjecutora
									libroDiarioDetalleDebe.setIntParaDocumentoGeneral(libroDiario.getIntParaTipoDocumentoGeneral());
									libroDiarioDetalleDebe.setStrSerieDocumento(null);
									libroDiarioDetalleDebe.setStrNumeroDocumento(null);
									libroDiarioDetalleDebe.setIntPersEmpresaSucursal(libroDiario.getId().getIntPersEmpresaLibro());
									libroDiarioDetalleDebe.setIntSucuIdSucursal(efectuado.getIntIdsucursaladministraPk()); //sucursaladministra
									libroDiarioDetalleDebe.setIntSudeIdSubSucursal(efectuado.getIntIdsubsucursaladministra()); //subsucursaladministra
									libroDiarioDetalleDebe.setStrComentario(strComentarioDebe);
									libroDiarioDetalleDebe.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
									if(Constante.PARAM_T_OPCIONDEBEHABER_DEBE.compareTo(modeloDetalleDebe.getIntParaOpcionDebeHaber()) == 0)
										libroDiarioDetalleDebe.setBdDebeSoles(bdMontoByAdministra);
									else
										libroDiarioDetalleDebe.setBdHaberSoles(bdMontoByAdministra);
									libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleDebe);
								}
							
							
							log.debug("bdMontoByAdministra final:"+bdMontoByAdministra);
							for(LibroDiarioDetalle libroDiarioDetallea : libroDiario.getListaLibroDiarioDetalle())
							{
								log.debug(libroDiarioDetallea);
							}
							if(bdMontoByAdministra.compareTo(erContabilidad.getBdMontoTotal()) == 0)
							{
								log.debug("bdMontoByAdministra: "+bdMontoByAdministra+
										  " montoTotal efectuadoResumen: "+erContabilidad.getBdMontoTotal());
								libroDiario = libroDiarioFacade.grabarLibroDiario(libroDiario);
								//actualizando en efectuadoResumen
								log.debug("before: "+erContabilidad);
								erContabilidad.setIntPersEmpresaLibro(libroDiario.getId().getIntPersEmpresaLibro());
								erContabilidad.setIntPeriodoLibro(libroDiario.getId().getIntContPeriodoLibro());
								erContabilidad.setIntCodigoLibro(libroDiario.getId().getIntContCodigoLibro());
								boEfectuadoResumen.modificar(erContabilidad);
								log.debug("after: "+erContabilidad);
							}else
							{
								log.debug("bdMontoByAdministra: "+bdMontoByAdministra+
										  " montoTotal efectuadoResumen: "+erContabilidad.getBdMontoTotal());
								throw new BusinessException("ERROR EN CONTABILIDAD MONTOS EFECTUADORESUMEN DISTINTO DE LOS ASIENTOS");
							}
						}
					}
				}
				mapA.clear();
				mapI.clear();
			log.debug("grabandoContabilidad INICIO v2");
		
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}

	
	private List<EfectuadoConcepto> agrupandoAmortizacion(
			List<EfectuadoConcepto> listaAmortizacion) {
		List<EfectuadoConcepto> lista = null;
		lista = new ArrayList<EfectuadoConcepto>();		
		
		for(EfectuadoConcepto o :listaAmortizacion)
		{
			putAndIncrementA(o.getStrContNumeroCuenta(),o.getBdMontoConcepto());
		}
		for(Entry<String, BigDecimal> e:mapA.entrySet())
		{
			log.debug("key: "+e.getKey()+", value: "+e.getValue());
			EfectuadoConcepto ecA = new EfectuadoConcepto();
			ecA.setStrContNumeroCuenta(e.getKey());
			ecA.setBdMontoConcepto(e.getValue());
			lista.add(ecA);
		}
		return lista;
	}
	public void putAndIncrementA(String key, BigDecimal value)
	{
		BigDecimal prev = mapA.get(key);
		BigDecimal newValue = value;
		if(prev != null)
		{
			newValue = newValue.add(prev);
		}
		mapA.put(key, newValue);
	}

	private List<EfectuadoConcepto> agrupandoInteres(
			List<EfectuadoConcepto> listaInteres) {
		List<EfectuadoConcepto> lista = null;
		lista = new ArrayList<EfectuadoConcepto>();		
		
		for(EfectuadoConcepto o :listaInteres)
		{
			putAndIncrementI(o.getStrContNumeroCuenta(),o.getBdMontoConcepto());
		}
		for(Entry<String, BigDecimal> e:mapI.entrySet())
		{
			EfectuadoConcepto ecI = new EfectuadoConcepto();
			log.debug("key: "+e.getKey()+", value: "+e.getValue());
			ecI.setStrContNumeroCuenta(e.getKey());
			ecI.setBdMontoConcepto(e.getValue());
			lista.add(ecI);
		}
		return lista;
	}
	public void putAndIncrementI(String key, BigDecimal value)
	{
		BigDecimal prev = mapI.get(key);
		BigDecimal newValue = value;
		if(prev != null)
		{
			newValue = newValue.add(prev);
		}
		mapI.put(key, newValue);
	}
	public void agregarLibroDiarioDetallePrestamo(String strNumeroCuentaEXPEDIENTE,
												  LibroDiario libroDiario,
												  Efectuado efectuado,
												  Integer codPersona,
												  BigDecimal bdMontoPrestamo,
												  String comentario) throws BusinessException
	{
		log.debug("grabandoContabilidad INICIO v0");
		LibroDiarioDetalle libroDiarioDetalle = null;
		try
		{			
			libroDiarioDetalle = new LibroDiarioDetalle();							
			libroDiarioDetalle.setIntPersEmpresaCuenta(libroDiario.getId().getIntPersEmpresaLibro());
			libroDiarioDetalle.setIntContPeriodo(new Integer(libroDiario.getId().getIntContPeriodoLibro().toString().substring(0,4)));
			libroDiarioDetalle.setStrContNumeroCuenta(strNumeroCuentaEXPEDIENTE);
			libroDiarioDetalle.setIntPersPersona(codPersona);//codPersona de la UnidadEjecutora
			libroDiarioDetalle.setIntParaDocumentoGeneral(libroDiario.getIntParaTipoDocumentoGeneral());
			libroDiarioDetalle.setStrSerieDocumento(null);
			libroDiarioDetalle.setStrNumeroDocumento(null);
			libroDiarioDetalle.setIntPersEmpresaSucursal(libroDiario.getId().getIntPersEmpresaLibro());
			libroDiarioDetalle.setIntSucuIdSucursal(efectuado.getIntIdsucursaladministraPk()); //sucursaladministra
			libroDiarioDetalle.setIntSudeIdSubSucursal(efectuado.getIntIdsubsucursaladministra()); //subsucursaladministra
			libroDiarioDetalle.setStrComentario(comentario);
			libroDiarioDetalle.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
			libroDiarioDetalle.setBdHaberSoles(bdMontoPrestamo);
			libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalle);	
	    log.debug("grabandoContabilidad FIN v0");	
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	


	private LibroDiario generarLibroDiario(EfectuadoResumen efectuadoResumen) throws BusinessException {
		log.debug("generarLibroDiario INICIO v1");
		LibroDiario libroDiario = null; 
		try
		{
			Integer intPeriodo = null;
			String strMes = null;
			Integer intAnio = UtilCobranza.obtieneAnio(UtilCobranza.obtieneFechaActualEnTimesTamp());
			String strAnio = intAnio.toString();
			Integer intMes =  UtilCobranza.obtieneMes(UtilCobranza.obtieneFechaActualEnTimesTamp());
			if(intMes < 10)
			{
				strMes = "0" + intMes.toString();			
			}else
			{
				strMes = intMes.toString();			
			}
			intPeriodo = Integer.parseInt(strAnio+strMes);
			
			libroDiario = new LibroDiario();
			libroDiario.setId(new LibroDiarioId());
			libroDiario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
			libroDiario.getId().setIntPersEmpresaLibro(efectuadoResumen.getId().getIntEmpresa());
			libroDiario.getId().setIntContPeriodoLibro(intPeriodo);
			libroDiario.setStrGlosa("Asiento automatico, planilla efectuada");
			libroDiario.setTsFechaRegistro(UtilCobranza.obtieneFechaActualEnTimesTamp());
			libroDiario.setTsFechaDocumento(UtilCobranza.obtieneFechaActualEnTimesTamp());
			libroDiario.setIntPersEmpresaUsuario(efectuadoResumen.getId().getIntEmpresa());
			libroDiario.setIntPersPersonaUsuario(efectuadoResumen.getIntPersonausuarioPk());
			libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAEFECTUADA);
			log.debug("generarLibroDiario FIN v1");	
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return libroDiario;	
	}
	
	
	private void grabandoUnSocio(EfectuadoResumen efectuadoResumen,
								 Efectuado efectuado,
								 Usuario pUsuario) throws BusinessException {
		log.debug("EfectuadoService.grabandoUnSocio() =====> INICIO v1");
		BigDecimal bdSumaTotalMontoConcepto= new BigDecimal(0);		
		EfectuadoConcepto efectuadoCon = null;		
		BigDecimal bdDiferencia = new BigDecimal(0);
		try
		{ 
		List<Envioconcepto> listaEnvio =  boEnvioconcepto.getListaEnvioconceptoPorEmprPeriodoNroCtaNivelCodigo(efectuado.getId().getIntEmpresacuentaPk(),
																											   efectuado.getIntPeriodoPlanilla(),
																											   efectuado.getIntCuentaPk(),
																											   efectuado.getIntNivel(),
																											   efectuado.getIntCodigo());
			if(listaEnvio != null && !listaEnvio.isEmpty())
			{
				if(efectuado.getBlnListaEnvioConcepto())
				{					
					efectuado = boEfectuado.grabarEfectuado(efectuado);					 
					bdSumaTotalMontoConcepto = new BigDecimal(0);
					if(efectuado.getListaEfectuadoConcepto() != null && !efectuado.getListaEfectuadoConcepto().isEmpty())
					{						
						for(int j=0; j < efectuado.getListaEfectuadoConcepto().size(); j++)
						{										
							efectuadoCon = efectuado.getListaEfectuadoConcepto().get(j);
							bdSumaTotalMontoConcepto =  bdSumaTotalMontoConcepto.add(efectuadoCon.getBdMontoConcepto());
							efectuadoCon.getId().setIntItemEfectuado(efectuado.getId().getIntItemefectuado());
							efectuadoCon.setEfectuado(efectuado);
							if(j == 0)
							{											
								boEfectuadoConcepto.grabarEfectuadoConcepto(efectuadoCon);
								if(efectuadoCon.getIntItemCuentaConcepto() != null)
								{									
								    	grabandoEnEsquemaMovimientoConceptos(efectuadoCon);
								}
								else if(efectuadoCon.getIntItemExpediente()!= null 
										&& efectuadoCon.getIntItemDetExpediente() != null)
								{
								   	grabandoEnEsquemaMovimientoPrestamos(efectuadoCon);									
								}																						
							}
							else
							{		
								boEfectuadoConcepto.grabarSubEfectuadoConcepto(efectuadoCon);
								if(efectuadoCon .getIntItemCuentaConcepto() != null )
								{
								   	grabandoEnEsquemaMovimientoConceptos(efectuadoCon);
								}
								else if(efectuadoCon.getIntItemExpediente()!=null && efectuadoCon.getIntItemDetExpediente() != null)
								{
								   	grabandoEnEsquemaMovimientoPrestamos(efectuadoCon);									
								}											
							}										
						}
						/**
						 * ver nuevamente lo de efectuado su montoEfectuado 
						 * con la sumatoria de mis montosconceptos de EfectuadoConcepto
						 * si mi montoEfectuado es mayor que mi sumatoria de conceptos
						 * genero una cuenta por pagar.
						 */						
						if(efectuado.getBdMontoEfectuado().compareTo(bdSumaTotalMontoConcepto) == 1)
						{
							 bdDiferencia = efectuado.getBdMontoEfectuado().subtract(bdSumaTotalMontoConcepto);													
							  cuentaPorPagaryMovimiento(efectuado, bdDiferencia);						
						}
					
					}else
					{
						throw new BusinessException("ERROR NO TIENE LISTA DE EFECTUADOCONCEPTOS.");
					}					
					
				} else  
				{
					log.debug("BlnListaEnvioConcepto() is false"); 					
				} 
			}else
			{
				log.debug("no tiene lista de envio");
				noTieneListaEnvio(efectuadoResumen, efectuado);
			}
			log.debug("EfectuadoService.grabandoUnSocio()=====>FIN v1");
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}		
	}

	private void noTieneListaEnvio(EfectuadoResumen efectuadoResumen,
								   Efectuado efectuado) throws BusinessException {
		log.debug("EfectuadoService.noTieneListaEnvio()=====>INICIO");
		log.debug("efectuadocuenta:"+efectuado.getIntCuentaPk());		
		BigDecimal bdDiferencia = new BigDecimal(0);
		Expediente expPrioridad  = null;
		EfectuadoConcepto efectuadoConceptoModificado = null;
		Envioconcepto lEnvioConcepto = null;
		Integer intItemEnvioConcepto = 0;
		List<Envioresumen> listaO = null;
		BigDecimal bdMontoTotal= new BigDecimal(0);
		EfectuadoConcepto efectuadoCon = null;
		Envioresumen envioresumen = null;
		BigDecimal bdSumaTotalMontoConcepto= new BigDecimal(0);
		Boolean blnTieneExpediente =Boolean.FALSE; 
		Integer intNumero = 0;
		
		try
		{
			ConceptoFacadeRemote remoteConcepto = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			//grabar enviomonto y su lista de envioconcepto actualizar envioresumen sumar															
			for(int j=0; j < efectuado.getEnvioMonto().getListaEnvioConcepto().size(); j++)
			{
				lEnvioConcepto = efectuado.getEnvioMonto().getListaEnvioConcepto().get(j);
				if(j == 0)
				{
				 	boEnvioconcepto.grabarEnvioconcepto(lEnvioConcepto);
					intItemEnvioConcepto = lEnvioConcepto.getId().getIntItemenvioconcepto();
					log.debug("itemconcepto="+intItemEnvioConcepto);
				}
				else
				{
					lEnvioConcepto.getId().setIntItemenvioconcepto(intItemEnvioConcepto);
				 	boEnvioconcepto.grabarSubEnvioconcepto(lEnvioConcepto);
				}
			}
			//grabar el enviomonto
			efectuado.getEnvioMonto().getId().setIntItemenvioconcepto(intItemEnvioConcepto);			
			if(efectuado.getEnvioMonto().getBlnTieneNuevoEnvioresumen() != null &&
				efectuado.getEnvioMonto().getBlnTieneNuevoEnvioresumen()){
				envioresumen = boEnvioresumen.grabarEnvioresumen(efectuado.getEnvioMonto().getEnvioresumen());
				if(envioresumen != null){
					efectuado.getEnvioMonto().setEnvioresumen(envioresumen);
				}
			}
			
			Enviomonto enviomonto =  boEnviomonto.grabarEnviomonto(efectuado.getEnvioMonto());
			
			Integer intPeriodo = enviomonto.getListaEnvioConcepto().get(0).getIntPeriodoplanilla();
			listaO = boEnvioresumen.getListEnvRes(enviomonto.getId().getIntEmpresacuentaPk(),
												  intPeriodo,
												  enviomonto.getIntTiposocioCod(),
												  enviomonto.getIntModalidadCod(),
												  enviomonto.getIntNivel(),
												  enviomonto.getIntCodigo());
			for(Envioresumen er: listaO)
			{
				intNumero = er.getIntNumeroafectados();
				intNumero++;
				er.setIntNumeroafectados(intNumero);
				bdMontoTotal = er.getBdMontototal().add(enviomonto.getBdMontoenvio());
				er.setBdMontototal(bdMontoTotal);
				boEnvioresumen.modificarEnvioresumen(er);
				break;
			}							
			
			efectuado.setEnvioMonto(enviomonto);
			
			if(efectuado.getBlnListaEnvioConcepto())
			{
				log.debug("blnlistaenvioconcepto TRUE");
				efectuado.setIntParaTipoIngresoDatoCod(Constante.PARAM_ParaTipoIngresoDatoSocio);
				boEfectuado.grabarEfectuado(efectuado);
				log.debug("efectuado:"+efectuado.getId().getIntItemefectuado());
				bdSumaTotalMontoConcepto = new BigDecimal(0);
				blnTieneExpediente=Boolean.FALSE;									
				for(int j=0; j < efectuado.getListaEfectuadoConcepto().size(); j++)
				{										
					efectuadoCon = efectuado.getListaEfectuadoConcepto().get(j);
					bdSumaTotalMontoConcepto =  bdSumaTotalMontoConcepto.add(efectuadoCon.getBdMontoConcepto());
					efectuadoCon.getId().setIntItemEfectuado(efectuado.getId().getIntItemefectuado());
					efectuadoCon.setEfectuado(efectuado);
					if(j == 0)
					{											
						log.debug(efectuadoCon);
						boEfectuadoConcepto.grabarEfectuadoConcepto(efectuadoCon);											
						//grabar lo que corresponde en movimiento
						if(efectuadoCon.getIntItemCuentaConcepto() != null )
						{
						 	grabandoEnEsquemaMovimientoConceptos(efectuadoCon);
						}
						else if(efectuadoCon.getIntItemExpediente()!=null && efectuadoCon.getIntItemDetExpediente() !=null)
						{
						 	grabandoEnEsquemaMovimientoPrestamos(efectuadoCon);
							blnTieneExpediente=Boolean.TRUE;
						}																						
					}
					else
					{											
					  boEfectuadoConcepto.grabarSubEfectuadoConcepto(efectuadoCon);										  
						//grabar lo que corresponde en movimiento
						if(efectuadoCon .getIntItemCuentaConcepto() != null )
						{
						 	grabandoEnEsquemaMovimientoConceptos(efectuadoCon);
						}
						else if(efectuadoCon.getIntItemExpediente()!=null && efectuadoCon.getIntItemDetExpediente() !=null)
						{
						 	grabandoEnEsquemaMovimientoPrestamos(efectuadoCon);
							blnTieneExpediente=Boolean.TRUE;
						}											
					}										
				}								
				 // ver nuevamente lo de efectuado su montoEfectuado con la sumatoria
				 // de mis montosconceptos de EfectuadoConcepto
				 // si mi montoEfectuado es mayor que mi sumatoria de conceptos verifico en expedienteCredito 
				// su saldocredito si es mayor que 0 sigo haciendo todo el proceso es decir grabar ese movimiento
				 // grabar en movimiento, cronograma si es igual a 0 crear una cuenta por pagar
				 
				if(efectuado.getBdMontoEfectuado().compareTo(bdSumaTotalMontoConcepto) == 1)
				{
					bdDiferencia = efectuado.getBdMontoEfectuado().subtract(bdSumaTotalMontoConcepto);
					if(blnTieneExpediente)
					{
						for(EfectuadoConcepto efeconcepto:efectuado.getListaEfectuadoConcepto())
						{
							if(efeconcepto.getIntItemExpediente() != null && efeconcepto.getIntItemDetExpediente() != null)
							{
								ExpedienteId exId = new ExpedienteId();
								exId.setIntPersEmpresaPk(efectuadoCon.getEfectuado().getIntEmpresausuarioPk());
								exId.setIntCuentaPk(efectuadoCon.getEfectuado().getIntCuentaPk());
								exId.setIntItemExpediente(efectuadoCon.getIntItemExpediente());
								exId.setIntItemExpedienteDetalle(efectuadoCon.getIntItemDetExpediente());
								
								expPrioridad  =  remoteConcepto.getExpedientePorPK(exId);
									if(expPrioridad != null)
									{
										if(expPrioridad.getBdSaldoCredito().compareTo(new BigDecimal(0)) == 1)
										{																
											List<EfectuadoConcepto> listaEfectuadoConcepto = boEfectuadoConcepto.getListaPorEfectuado(efectuado.getId());
											if(listaEfectuadoConcepto!=null && !listaEfectuadoConcepto.isEmpty())
											{
												for(EfectuadoConcepto efectuadoo:listaEfectuadoConcepto)
												{
													if(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION.compareTo(efectuadoo.getIntTipoConceptoGeneralCod()) == 0)
													{
														if(efectuadoo.getBdMontoConcepto() == null) efectuadoo.setBdMontoConcepto(new BigDecimal(0));
														
														efectuadoo.setBdMontoConcepto(efectuadoo.getBdMontoConcepto().add(bdDiferencia));
														boEfectuadoConcepto.modificarEfectuado(efectuadoo);						
														
														 // Actualizar efectuado, EfectuadoResumen
														 																			
														efectuado.setBdMontoEfectuado(efectuado.getBdMontoEfectuado().add(bdDiferencia));
														boEfectuado.modificarEfectuado(efectuado);
														
														 // actualizar efectuadoResumen
														 
														efectuadoResumen.setBdMontoTotal(efectuadoResumen.getBdMontoTotal().add(bdDiferencia));
														boEfectuadoResumen.modificar(efectuadoResumen);
														
														efectuadoConceptoModificado = new EfectuadoConcepto();
														efectuadoConceptoModificado.setId(efectuadoo.getId());																			
														efectuadoConceptoModificado.setIntItemExpediente(efectuadoo.getIntItemExpediente());
														efectuadoConceptoModificado.setIntItemDetExpediente(efectuadoo.getIntItemDetExpediente());
														efectuadoConceptoModificado.setIntTipoConceptoGeneralCod(efectuadoo.getIntTipoConceptoGeneralCod());
														efectuadoConceptoModificado.setBdMontoConcepto(bdDiferencia);
														efectuadoConceptoModificado.setIntEstadoCod(efectuadoo.getIntEstadoCod());
														efectuadoConceptoModificado.setEfectuado(efectuado);
														
														grabandoEnEsquemaMovimientoPrestamosLaDiferencia(efectuadoConceptoModificado, bdDiferencia);
														break;
													}
												}
											}
											
										}else if(expPrioridad.getBdSaldoCredito().compareTo(new BigDecimal(0)) == 0)
										{
										 	cuentaPorPagaryMovimiento(efectuado,bdDiferencia);
										}
									}
								break;
							}
						}
						
					}else
					{
					 	cuentaPorPagaryMovimiento(efectuado, bdDiferencia);																						
					}
				}
			}
			log.debug("EfectuadoService.noTieneListaEnvio()=====>FIN");
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}		
	}


	public void grabandoUnNoSocio(Efectuado efectuado, Usuario pUsuario)throws BusinessException
	{
		log.debug("grabandoUnNoSocio INICIO");		
		Socio socio = null;
		try
		{			
			if(efectuado.getPersona().getIntIdPersona() != null)
			{				
				if(efectuado.getBlnTieneSocio())
				{
					//actualizar en socio
					modificarSocio(efectuado.getSocio());
					if(efectuado.getCuentaIntegrante() != null)
					{
						/**
						 *grabando en cobranza 
						 */
						grabandoEnCobranza(efectuado, pUsuario);
						
						/**
						 *grabando en movimiento 
						 */
					 	 grabandoEnMovimiento(efectuado);	
					}
					else 
					{
						//grabar en cuenta cuentaIntegrante
					 	grabandoEnCuentayCuentaIntegrante(efectuado, pUsuario);
						
						/**
						 *grabando en cobranza 
						 */
						grabandoEnCobranza(efectuado, pUsuario);
						
						/**
						 *grabando en movimiento 
						 */
					  	grabandoEnMovimiento(efectuado);	
					}
				}
				else if(efectuado.getBlnNoTieneSocio())
				{
					//grabar en socio y socioEstructura
					socio = grabandoEnSocioySocioEstructura(efectuado, pUsuario);
					
					actualizandoCobranza(efectuado, socio);
					
					if(efectuado.getCuentaIntegrante() != null)
					{	
						/**
						 *grabando en cobranza 
						 */
						grabandoEnCobranza(efectuado, pUsuario);
						
						/**
						 *grabando en movimiento 
						 */
					  	grabandoEnMovimiento(efectuado);	
					}
					else 
					{
						//grabar en cuenta cuentaIntegrante
						grabandoEnCuentayCuentaIntegrante(efectuado, pUsuario);
						
						/**
						 *grabando en cobranza 
						 */
						grabandoEnCobranza(efectuado, pUsuario);
						
						/**
						 *grabando en movimiento 
						 */
					  	grabandoEnMovimiento(efectuado);	
					}
				}
			}else
			{
				//grabar todo lo de persona
				grabandoEnPersona(efectuado);				
				
				//grabar en socio y socioEstructura
				socio = grabandoEnSocioySocioEstructura(efectuado, pUsuario);				
				
				actualizandoCobranza(efectuado, socio);
				
				//grabar en cuenta cuentaIntegrante
				grabandoEnCuentayCuentaIntegrante(efectuado, pUsuario);	
				
				/**
				 *grabando en cobranza 
				 */
				grabandoEnCobranza(efectuado, pUsuario);
				
				/**
				 *grabando en movimiento 
				 */
			  	grabandoEnMovimiento(efectuado);
			}			
		log.debug("grabandoUnNoSocio FIN");	
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	


	
	public void actualizandoCobranza(Efectuado efectuado,Socio socio)throws BusinessException
	{
		log.debug("actualizandoCobranza INICIO");
		SocioEstructura socioE = null;
		try
		{
			socioE = socio.getListSocioEstructura().get(0);
			efectuado.getEnvioMonto().setIntTipoestructuraCod(socioE.getIntTipoEstructura());
			efectuado.getEnvioMonto().setIntEmpresasucadministraPk(socioE.getIntEmpresaSucAdministra());
			efectuado.getEnvioMonto().setIntIdsucursaladministraPk(socioE.getIntIdSucursalAdministra());
			efectuado.getEnvioMonto().setIntIdsubsucursaladministra(socioE.getIntIdSubsucurAdministra());	
			efectuado.getEnvioMonto().setIntEmpresausuarioPk(socioE.getIntEmpresaUsuario());
			efectuado.getEnvioMonto().setIntPersonausuarioPk(socioE.getIntPersonaUsuario());
			
			log.debug("actualizandoCobranza FIN");
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	public void modificarSocio(Socio o)throws BusinessException
	{
		log.debug("modificarSocio INICIO");
		try
		{
			SocioFacadeRemote socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			
			socioFacade.modificarSocio(o);
			
			log.debug("modificarSocio FIN");
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	public void grabandoEnCuentayCuentaIntegrante(Efectuado efectuado, Usuario pUsuario)throws BusinessException
	{
		log.debug("grabandoEnCuentayCuentaIntegrante INICIO");
		CuentaIntegrante cuentaIntegrante = null;		
		try
		{
			CuentaFacadeRemote   cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			//grabar en cuenta y cuenta Integrante caso B
 			int cntIntegrantes = 1;
 			
 			Cuenta cuenta = new Cuenta();
 			cuenta.setId(new CuentaId());
 			cuenta.getId().setIntPersEmpresaPk(efectuado.getId().getIntEmpresacuentaPk());
 			cuenta.setIntIntegrantes(cntIntegrantes);
 			cuenta.setIntParaTipoCuentaCod(Constante.PARAM_TIPOCUENTA_NOSOCIO);
 			cuenta.setIntParaTipoConformacionCod(1);
 			cuenta.setIntParaTipoMonedaCod(Constante.PARAM_T_TIPOMONEDA_SOLES);
 			cuenta.setIntParaSituacionCuentaCod(Constante.PARAM_T_SITUACIONCUENTA_VIGENTE);
 			cuenta.setIntParaCondicionCuentaCod(Constante.PARAM_T_CONDICIONSOCIO_HABIL);
 			cuenta.setIntParaSubTipoCuentaCod(0);
 			cuenta.setIntSecuenciaCuenta(1);
 			cuenta.setIntParaSubCondicionCuentaCod(Constante.PARAM_T_TIPO_CONDSOCIO_REGULAR);
 			cuenta.setBdMontoPlanilla(new BigDecimal(0));
 			
 			cuenta.setIntIdUsuSucursal(pUsuario.getSucursal().getId().getIntIdSucursal());
 			cuenta.setIntIdUsuSubSucursal(pUsuario.getSubSucursal().getId().getIntIdSubSucursal());
 			cuenta.setIntPersonaUsuSucursal(pUsuario.getSucursal().getIntPersPersonaPk());
 			cuenta.setTsFecRegUsuSucursal(new Timestamp(new Date().getTime()));
 			cuenta.setTsCuentFecRegistro(new Timestamp(new Date().getTime()));
 			cuenta = cuentaFacade.grabandoCuenta(cuenta);
 			
 			cuentaIntegrante = new CuentaIntegrante();
 			cuentaIntegrante.setId(new CuentaIntegranteId());
 			cuentaIntegrante.getId().setIntCuenta(cuenta.getId().getIntCuenta());
 			cuentaIntegrante.getId().setIntPersEmpresaPk(cuenta.getId().getIntPersEmpresaPk());
 			if(efectuado.getPersona() != null && efectuado.getPersona().getIntIdPersona() != null)
 			{
 				cuentaIntegrante.getId().setIntPersonaIntegrante(efectuado.getPersona().getIntIdPersona());
 			}else 
 			{
 				cuentaIntegrante.getId().setIntPersonaIntegrante(efectuado.getSocio().getId().getIntIdPersona());
 			}
 			
 			cuentaIntegrante.setIntParaTipoIntegranteCod(Constante.PARAM_T_TIPOINTEGRANTECUENTA_PRINCIPAL);
 			cuentaIntegrante.setTsFechaIngreso(new Timestamp(new Date().getTime()));
 			cuentaIntegrante.setIntPersonaUsuario(pUsuario.getIntPersPersonaPk());
 			cuentaIntegrante = cuentaFacade.grabandoCuentaIntegrante(cuentaIntegrante);
 			
 			efectuado.setCuentaIntegrante(new CuentaIntegrante());
 			efectuado.setCuentaIntegrante(cuentaIntegrante);
 			log.debug("cuenta creada ultima: "+efectuado.getCuentaIntegrante().getId().getIntCuenta());
 			log.debug("grabandoEnCuentayCuentaIntegrante FIN");	
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	public Socio grabandoEnSocioySocioEstructura(Efectuado efectuado, Usuario pUsuario) throws BusinessException
	{
		log.debug("grabandoEnSocioySocioEstructura INICIO");
		Socio socio = null;
		try
		{
			SocioFacadeRemote socioFacade 	  = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			
			socio = socioFacade.grabarSocio(efectuado.getSocio());
			
			SocioEstructura se = new SocioEstructura();
			se.setId(new SocioEstructuraPK());
			se.getId().setIntIdEmpresa(efectuado.getId().getIntEmpresacuentaPk());
			if(efectuado.getPersona()!= null && efectuado.getPersona().getIntIdPersona()!= null)
			{
				se.getId().setIntIdPersona(efectuado.getPersona().getIntIdPersona());	
			}else 
			{
				se.getId().setIntIdPersona(socio.getId().getIntIdPersona());	
			}
			
			se.setIntEmpresaSucUsuario(pUsuario.getSucursal().getId().getIntPersEmpresaPk());
			se.setIntIdSucursalUsuario(pUsuario.getSucursal().getId().getIntIdSucursal());
			se.setIntIdSubSucursalUsuario(pUsuario.getSubSucursal().getId().getIntIdSubSucursal());
			se.setIntEmpresaSucAdministra(null);
			se.setIntIdSucursalAdministra(efectuado.getEfectuadoResumen().getIntIdsucursaladministraPk());
			se.setIntIdSubsucurAdministra(efectuado.getEfectuadoResumen().getIntIdsubsucursaladministra());
			se.setIntTipoSocio(efectuado.getEfectuadoResumen().getIntTiposocioCod());
			se.setIntModalidad(efectuado.getEfectuadoResumen().getIntModalidadCod());	
			se.setIntNivel(efectuado.getEfectuadoResumen().getIntNivel());
			se.setIntCodigo(efectuado.getEfectuadoResumen().getIntCodigo());
			se.setStrCodigoPlanilla(null);		
			se.setIntTipoEstructura(new Integer(Constante.PARAM_T_TIPOESTRUCTURA_PRESTAMO));
			se.setIntEmpresaUsuario(pUsuario.getEmpresaUsuario().getId().getIntPersEmpresaPk());
			se.setIntPersonaUsuario(pUsuario.getIntPersPersonaPk());
			se.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			se.setDtFechaRegistro(new Date());
			socio.setListSocioEstructura(new ArrayList<SocioEstructura>());
		
			se = socioFacade.grabarSocioEstructura(se);
			socio.getListSocioEstructura().add(se);
			
		log.debug("grabandoEnSocioySocioEstructura FIN");	
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return socio;
	}
	
	public void grabandoEnPersona(Efectuado efectuado) throws BusinessException
	{
		log.debug("grabandoEnPersona INICIO");
		Persona newPersona = null;
		try
		{
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			
			Persona persona = new Persona();
			persona.setIntTipoPersonaCod(Constante.PARAM_T_TIPOPERSONA_NATURAL);
			persona.setIntEstadoCod(new Integer(Constante.PARAM_T_ESTADOUNIVERSAL));
			persona.setNatural(new Natural());
			persona.getNatural().setStrApellidoPaterno(efectuado.getSocio().getStrApePatSoc());
			persona.getNatural().setStrApellidoMaterno(efectuado.getSocio().getStrApeMatSoc());
			persona.getNatural().setStrNombres(efectuado.getSocio().getStrNombreSoc());
			persona.setPersonaEmpresa(new PersonaEmpresa());
			
			PersonaRol personaRol = new PersonaRol();
			
			PersonaRolPK personaRolPK = new PersonaRolPK();
			personaRolPK.setIntIdEmpresa(efectuado.getId().getIntEmpresacuentaPk());
			personaRolPK.setIntParaRolPk(Constante.PARAM_T_PERSONA_ROL);
			personaRolPK.setDtFechaInicio(new Date());			
			personaRol.setId(new PersonaRolPK());
			personaRol.setId(personaRolPK);
			
			persona.getPersonaEmpresa().setPersonaRol(new PersonaRol());
			persona.getPersonaEmpresa().setPersonaRol(personaRol);
			persona.setDocumento(new Documento());
			persona.getDocumento().setId(new DocumentoPK());
			newPersona = personaFacade.grabarPersonaNatural(persona);
			log.debug("persona:"+newPersona.getIntIdPersona());
			persona.getDocumento().getId().setIntIdPersona(newPersona.getIntIdPersona());
			persona.getDocumento().setIntTipoIdentidadCod(1);
			persona.getDocumento().setIntEstadoCod(new Integer(Constante.PARAM_T_ESTADOUNIVERSAL));
			personaFacade.grabarDocumento(persona.getDocumento());
			efectuado.setPersona(new Persona());
			efectuado.setPersona(persona);
			//Actualizada en socio
			efectuado.getSocio().setId(new SocioPK());
			efectuado.getSocio().getId().setIntIdPersona(newPersona.getIntIdPersona());
			efectuado.getSocio().getId().setIntIdEmpresa(efectuado.getId().getIntEmpresacuentaPk());
		log.debug("grabandoEnPersona FIN");	
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
		
	public void grabandoEnCobranza(Efectuado efectuado, Usuario pUsuario) throws BusinessException
	{
		log.debug("grabandoEnCobranza INICIO v1");
		
		Timestamp tsFechaHoraActual  = null;	
		Envioconcepto lEnvioConcepto = null;	
		Integer intItemEnvioConcepto = null; 
		List<Envioresumen> listaO = null;
		Integer intNumero = null;
		BigDecimal bdMontoTotal = new BigDecimal(0);
		Enviomonto enviomonto =  null;
		Envioresumen envioresumen = null;
		tsFechaHoraActual = JFecha.obtenerTimestampDeFechayHoraActual();
		log.debug("cuenta creada ultima: "+efectuado.getCuentaIntegrante().getId().getIntCuenta());
		try
		{
			for(int j=0; j < efectuado.getEnvioMonto().getListaEnvioConcepto().size(); j++)
			{
				lEnvioConcepto = efectuado.getEnvioMonto().getListaEnvioConcepto().get(j);
				if(lEnvioConcepto.getIntCuentaPk() == null)
				{
					lEnvioConcepto.setIntCuentaPk(efectuado.getCuentaIntegrante().getId().getIntCuenta());
				}
				if(j == 0)
				{
					boEnvioconcepto.grabarEnvioconcepto(lEnvioConcepto);
					intItemEnvioConcepto = lEnvioConcepto.getId().getIntItemenvioconcepto();
					log.debug("itemconcepto="+intItemEnvioConcepto);
				}
				else
				{
					lEnvioConcepto.getId().setIntItemenvioconcepto(intItemEnvioConcepto);
					boEnvioconcepto.grabarSubEnvioconcepto(lEnvioConcepto);
				}
			}
			//grabar el enviomonto
			/**
			 * pUsuario.getSucursal().getId().getIntPersEmpresaPk());
			   se.setIntIdSucursalUsuario(pUsuario.getSucursal().getId().getIntIdSucursal());
			 */	
			if(efectuado.getEnvioMonto().getBlnTieneNuevoEnvioresumen() != null && 
					efectuado.getEnvioMonto().getBlnTieneNuevoEnvioresumen()){
				envioresumen  = boEnvioresumen.grabarEnvioresumen(efectuado.getEnvioMonto().getEnvioresumen());
				efectuado.getEnvioMonto().setEnvioresumen(envioresumen);
			}
			
			efectuado.getEnvioMonto().setTsFecharegistro(tsFechaHoraActual);			 
			efectuado.getEnvioMonto().getId().setIntItemenvioconcepto(intItemEnvioConcepto);			
			 
			enviomonto =  boEnviomonto.grabarEnviomonto(efectuado.getEnvioMonto());
			if(!efectuado.getEnvioMonto().getBlnTieneNuevoEnvioresumen()){
				Integer intPeriodo = enviomonto.getListaEnvioConcepto().get(0).getIntPeriodoplanilla();			 
				listaO = boEnvioresumen.getListEnvRes(enviomonto.getId().getIntEmpresacuentaPk(),
													  intPeriodo,
													  enviomonto.getIntTiposocioCod(),
													  enviomonto.getIntModalidadCod(),
													  enviomonto.getIntNivel(),
													  enviomonto.getIntCodigo());
				if(listaO != null && !listaO.isEmpty()){
					for(Envioresumen er: listaO)
					{    
						intNumero = er.getIntNumeroafectados();
						intNumero++;
						er.setIntNumeroafectados(intNumero);
						bdMontoTotal = er.getBdMontototal().add(enviomonto.getBdMontoenvio());
						er.setBdMontototal(bdMontoTotal);
						boEnvioresumen.modificarEnvioresumen(er);
						break;
					}			
				}
			}
			
			efectuado.setEnvioMonto(enviomonto);				 
			/**
			 * En efectuado, efectuadoconcepto
			 */
			grabandoEnEfectuadoEfectuadoConcepto(efectuado);
		log.debug("grabandoEnCobranza FIN v1");	
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	public void grabandoEnEfectuadoEfectuadoConcepto(Efectuado efectuado)throws BusinessException
	{
		log.debug("grabandoEnEfectuadoEfectuadoConcepto INICIO");
		EfectuadoConcepto efectuadoCon = null;	
		Envioconcepto envioconcepto = null;
		Enviomonto enviomonto = null;
		try
		{
			envioconcepto = efectuado.getEnvioMonto().getListaEnvioConcepto().get(0);
			enviomonto = efectuado.getEnvioMonto();
			efectuado.setId(new EfectuadoId());
			efectuado.getId().setIntEmpresacuentaPk(enviomonto.getId().getIntEmpresacuentaPk());
			efectuado.setIntTiposocioCod(enviomonto.getIntTiposocioCod());
			efectuado.setIntModalidadCod(enviomonto.getIntModalidadCod());
			efectuado.setIntPeriodoPlanilla(envioconcepto.getIntPeriodoplanilla());
			efectuado.setIntNivel(enviomonto.getIntNivel());
			efectuado.setIntCuentaPk(efectuado.getCuentaIntegrante().getId().getIntCuenta());
			efectuado.setIntCodigo(enviomonto.getIntCodigo());
			efectuado.setIntParaTipoIngresoDatoCod(Constante.PARAM_ParaTipoIngresoDatoNoSocio);
			efectuado.setIntTipoestructuraCod(enviomonto.getIntTipoestructuraCod());
			efectuado.setIntEmpresasucprocesaPk(enviomonto.getIntEmpresasucprocesaPk());
			efectuado.setIntIdsucursalprocesaPk(enviomonto.getIntIdsucursalprocesaPk());
			efectuado.setIntIdsubsucursalprocesaPk(enviomonto.getIntIdsubsucursalprocesaPk());
			efectuado.setIntEmpresasucadministraPk(enviomonto.getIntEmpresasucadministraPk());
			efectuado.setIntIdsucursaladministraPk(enviomonto.getIntIdsucursaladministraPk());
			efectuado.setIntIdsubsucursaladministra(enviomonto.getIntIdsubsucursaladministra());
			efectuado.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			efectuado.setIntEmpresausuarioPk(enviomonto.getIntEmpresausuarioPk());
			efectuado.setTsFecharegistro(enviomonto.getTsFecharegistro());
			efectuado.setIntPersonausuarioPk(enviomonto.getIntPersonausuarioPk());
			
			efectuado = boEfectuado.grabarEfectuado(efectuado);
			log.debug("efectuado:"+efectuado.getId().getIntItemefectuado());			
			
			for(int j=0; j < efectuado.getListaEfectuadoConcepto().size(); j++)
			{										
				efectuadoCon = efectuado.getListaEfectuadoConcepto().get(j);
				
				efectuadoCon.getId().setIntItemEfectuado(efectuado.getId().getIntItemefectuado());
				efectuadoCon.setEfectuado(efectuado);
				if(j == 0)
				{											
					log.debug(efectuadoCon);
					boEfectuadoConcepto.grabarEfectuadoConcepto(efectuadoCon);																												
				}
				else
				{											
					boEfectuadoConcepto.grabarSubEfectuadoConcepto(efectuadoCon);									
				}
				
			}
			
			log.debug("grabandoEnEfectuadoEfectuadoConcepto FIN");	
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	public void grabandoEnMovimiento(Efectuado efectuado) throws BusinessException
	{
		log.debug("grabandoEnMovimiento INICIO");
		try
		{
			ConceptoFacadeRemote remoteConcepto = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			
			Movimiento mov = new Movimiento();
			mov.setTsFechaMovimiento(UtilCobranza.obtieneFechaActualEnTimesTamp());
		    mov.setIntParaTipoMovimiento(Constante.PARAM_T_TIPOMOVIMIENTO_PROCESOPORPLANILLA);
		    mov.setIntParaDocumentoGeneral(Constante.PARAM_T_PLANILLAEFECTUADA); 
		    mov.setIntPersPersonaUsuario(efectuado.getEnvioMonto().getIntPersonausuarioPk());
		    mov.setIntPersEmpresaUsuario(efectuado.getEnvioMonto().getIntEmpresausuarioPk());
		    if(efectuado.getPersona() != null)
		    {
		    	mov.setIntPersPersonaIntegrante(efectuado.getPersona().getIntIdPersona());
		    }else if(efectuado.getIntPersonaIntegrante() != null)
		    {
		    	mov.setIntPersPersonaIntegrante(efectuado.getIntPersonaIntegrante());
		    }		    
		    mov.setIntPersEmpresa(efectuado.getId().getIntEmpresacuentaPk());
		    mov.setIntCuenta(efectuado.getCuentaIntegrante().getId().getIntCuenta());
	        mov.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_CTAXPAGAR);
	        mov.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
	        mov.setIntPeriodoPlanilla(efectuado.getEnvioMonto().getListaEnvioConcepto().get(0).getIntPeriodoplanilla());
	        mov.setBdMontoMovimiento(efectuado.getBdMontoEfectuado());
	        mov.setStrNumeroDocumento(efectuado.getId().getIntItemefectuado().toString());
	        List<Movimiento> listaMovimiento = remoteConcepto.getListaMovimientoPorCtaPersonaConceptoGeneral(efectuado.getId().getIntEmpresacuentaPk(),
												        													 efectuado.getIntCuentaPk(),
												        													 efectuado.getIntPersonaIntegrante(),
												        													 new Integer(Constante.PARAM_T_TIPOCONCEPTOGENERAL_CTAXPAGAR));
	        if(listaMovimiento != null && !listaMovimiento.isEmpty())
	        {
	        	for(Movimiento ento: listaMovimiento)
	        	{
	        		mov.setBdMontoSaldo(ento.getBdMontoSaldo().add(mov.getBdMontoMovimiento()));
	        		break;
	        	}
	        }else
	        {
	        	 mov.setBdMontoSaldo(mov.getBdMontoMovimiento());
	        }			       			         		      
	       remoteConcepto.grabarMovimiento(mov);
			
			
		log.debug("grabandoEnMovimiento FIN");	
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	public void cuentaPorPagaryMovimiento(Efectuado efectuado,
										  BigDecimal bdDiferencia) throws BusinessException
	{
		log.debug("cuentaPorPagaryMovimiento INICIO");
		try
		{
			ConceptoFacadeRemote remoteConcepto = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			
			/**
			 * creo un efectuadoConcepto por cuentaporpagar
			 */
			EfectuadoConcepto oo = new EfectuadoConcepto();
			oo.getId().setIntItemEfectuado(efectuado.getId().getIntItemefectuado());
			oo.getId().setIntEmpresaCuentaEnvioPk(efectuado.getId().getIntEmpresacuentaPk());
			oo.setIntTipoConceptoGeneralCod(Constante.PARAM_T_TIPOCONCEPTOGENERAL_CTAXPAGAR);
			oo.setBdMontoConcepto(bdDiferencia);
			oo.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			oo.setEfectuado(efectuado);
			if(efectuado.getListaEfectuadoConcepto() != null && !efectuado.getListaEfectuadoConcepto().isEmpty())
			{
				boEfectuadoConcepto.grabarSubEfectuadoConcepto(oo);
			}
			else
			{
				boEfectuadoConcepto.grabarEfectuadoConcepto(oo);
			}
						
			/**
			 * grabar en movimiento
			 */
			Movimiento mov = new Movimiento();
			mov.setTsFechaMovimiento(UtilCobranza.obtieneFechaActualEnTimesTamp());
		    mov.setIntParaTipoMovimiento(Constante.PARAM_T_TIPOMOVIMIENTO_PROCESOPORPLANILLA);
		    mov.setIntParaDocumentoGeneral(Constante.PARAM_T_PLANILLAEFECTUADA); 
		    mov.setIntPersPersonaUsuario(efectuado.getIntPersonausuarioPk());
		    mov.setIntPersEmpresaUsuario(efectuado.getIntEmpresausuarioPk());		   
		    mov.setIntPersPersonaIntegrante(efectuado.getIntPersonaIntegrante());
		    mov.setIntPersEmpresa(efectuado.getId().getIntEmpresacuentaPk());
		    mov.setIntCuenta(efectuado.getIntCuentaPk());
	        mov.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_CTAXPAGAR);
	        mov.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
	        mov.setIntPeriodoPlanilla(efectuado.getIntPeriodoPlanilla());
	        mov.setBdMontoMovimiento(bdDiferencia);
	        mov.setStrNumeroDocumento(efectuado.getId().getIntItemefectuado().toString());
	        List<Movimiento> listaMovimiento = remoteConcepto.getListaMovimientoPorCtaPersonaConceptoGeneral(efectuado.getId().getIntEmpresacuentaPk(),
												        												     efectuado.getIntCuentaPk(),
												        												     efectuado.getIntPersonaIntegrante(),
												        												     new Integer(Constante.PARAM_T_TIPOCONCEPTOGENERAL_CTAXPAGAR));
	        if(listaMovimiento != null && !listaMovimiento.isEmpty())
	        {
	        	for(Movimiento ento: listaMovimiento)
	        	{
	        		mov.setBdMontoSaldo(ento.getBdMontoSaldo().add(mov.getBdMontoMovimiento()));
	        		break;
	        	}
	        }else
	        {
	        	 mov.setBdMontoSaldo(mov.getBdMontoMovimiento());
	        }	
	        
	       remoteConcepto.grabarMovimiento(mov);
	       log.debug("cuentaPorPagaryMovimiento FIN");
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	/**	
	 * GRABA MOVIMIENTO
	 * ACTUALIZA EXPEDIENTECREDITO(amortizacion solo actualiza bdsaldocredito,en interes actualiza saldoInteres)
	 * ACTUALIZAR CRONOGRAMA CAMPO SALDODETALLE CREDITO,  SOLO SE ACTUALIZA CUANDO SE PAGUE TIPCONCEPTOCR =1 AMORTIZACION
	 * GRABA INTERESCANCELADO  SOLO EN EL CASO QUE SE PAGUE INTERES
	 * CMO_ESTADOCPTOCEXPE SE ACTUALIZA SE CAMBIARÁ A 2 CANCELADO, SIEMPRE QUE DESPUÉS DE HABER ACTUALIZADO	
	 * EL CAMPO SALDOCREDITO EN LA TABLA EXPEDIENTECREDITO SEA IAGUAL A CERO
	 */
	public void grabandoEnEsquemaMovimientoPrestamos(EfectuadoConcepto efectuadoCon) throws BusinessException
	{
		log.debug("grabandoEnEsquemaMovimientoPrestamos INICIO");
		BigDecimal bdmontoConcepto = new BigDecimal(0);
		BigDecimal bdSaldoCredtiInicial = new BigDecimal(0);
		List<EstadoCredito>  listaEstadoCredito = null;
		Timestamp tsFechaInicio 				= null; 
		Timestamp tsFechaFin					= null;
		Expediente expUpdate  					= null;
		BigDecimal bdMontoInteres = null;
		try{
			ConceptoFacadeRemote remoteConcepto = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			SolicitudPrestamoFacadeRemote solicitudPrestamofacade = (SolicitudPrestamoFacadeRemote)EJBFactory.getRemote(SolicitudPrestamoFacadeRemote.class);
			/**
			 * graba en movimiento
			 */
			Movimiento mov = new Movimiento();
			mov.setTsFechaMovimiento(UtilCobranza.obtieneFechaActualEnTimesTamp());
		    mov.setIntParaTipoMovimiento(Constante.PARAM_T_TIPOMOVIMIENTO_PROCESOPORPLANILLA);
		    mov.setIntParaDocumentoGeneral(Constante.PARAM_T_PLANILLAEFECTUADA); 
		    mov.setIntPersPersonaUsuario(efectuadoCon.getEfectuado().getIntPersonausuarioPk());
		    mov.setIntPersEmpresaUsuario(efectuadoCon.getEfectuado().getIntEmpresausuarioPk());		    
		    mov.setIntItemExpediente(efectuadoCon.getIntItemExpediente());
		    mov.setIntItemExpedienteDetalle(efectuadoCon.getIntItemDetExpediente());
		    mov.setIntPersPersonaIntegrante(efectuadoCon.getEfectuado().getIntPersonaIntegrante());
		    mov.setIntPersEmpresa(efectuadoCon.getId().getIntEmpresaCuentaEnvioPk());
		    mov.setIntCuenta(efectuadoCon.getEfectuado().getIntCuentaPk());
	        mov.setIntParaTipoConceptoGeneral(efectuadoCon.getIntTipoConceptoGeneralCod());
	        mov.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
	        mov.setIntPeriodoPlanilla(efectuadoCon.getEfectuado().getIntPeriodoPlanilla());
	        mov.setBdMontoMovimiento(efectuadoCon.getBdMontoConcepto());
	        mov.setStrNumeroDocumento(efectuadoCon.getId().getIntItemEfectuado().toString());
	        /**
	         * Si es una amortizacion actualizo el monto saldo que es el registro anterior menos montoMovimiento
	         * el interes solo se graba el bdmovimiento no graba nada en monto saldo 
	         */
	        if(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION.compareTo(efectuadoCon.getIntTipoConceptoGeneralCod()) == 0)
	        {
	        		        	
	        	Movimiento movimiento = remoteConcepto.getMaxXExpYCtoGral(mov.getIntPersEmpresa(),
	        															  mov.getIntCuenta(),
	        															  mov.getIntItemExpediente(), 
	        															  mov.getIntItemExpedienteDetalle(),
	        															  Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION);				
	        	if(movimiento != null)
				{				
					if(movimiento.getIntParaTipoCargoAbono().compareTo(Constante.PARAM_T_CARGOABONO_CARGO) == 0)
					{
						//solo de montomovimiento
						if(movimiento.getBdMontoSaldo() != null)
						{
							mov.setBdMontoSaldo(movimiento.getBdMontoSaldo().subtract(mov.getBdMontoMovimiento()));
						}else
						{
							mov.setBdMontoSaldo(movimiento.getBdMontoMovimiento().subtract(mov.getBdMontoMovimiento()));	
						}						
					}
					else if(movimiento.getIntParaTipoCargoAbono().compareTo(Constante.PARAM_T_CARGOABONO_ABONO) == 0)
					{
						if(movimiento.getBdMontoSaldo() !=null)
						{
							mov.setBdMontoSaldo(movimiento.getBdMontoSaldo().subtract(mov.getBdMontoMovimiento()));
						}						
					}						        		
				}       	
				else
				{
					mov.setBdMontoSaldo(mov.getBdMontoMovimiento());
				}
	        }	        	       			         		      
			mov = remoteConcepto.grabarMovimiento(mov);
			
			/**
			 *actualiza en expediente credito, amortizacion solo actualiza bdsaldocredito,
			 *en interes actualiza saldoInteres 
			 */
			bdmontoConcepto = new BigDecimal(0);
			ExpedienteId exId = new ExpedienteId();
			exId.setIntPersEmpresaPk(efectuadoCon.getEfectuado().getIntEmpresausuarioPk());
			exId.setIntCuentaPk(efectuadoCon.getEfectuado().getIntCuentaPk());
			exId.setIntItemExpediente(efectuadoCon.getIntItemExpediente());
			exId.setIntItemExpedienteDetalle(efectuadoCon.getIntItemDetExpediente());
			
			 expUpdate  =  remoteConcepto.getExpedientePorPK(exId);
				if(expUpdate != null)
				{					
					 if(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION.compareTo(efectuadoCon.getIntTipoConceptoGeneralCod())==0)
					 {
						 if (expUpdate.getBdSaldoCredito() == null) expUpdate.setBdSaldoCredito(new BigDecimal(0));
							bdSaldoCredtiInicial = expUpdate.getBdSaldoCredito();
							BigDecimal bdSaldoCredito = expUpdate.getBdSaldoCredito().subtract(efectuadoCon.getBdMontoConcepto() != null?
																					efectuadoCon.getBdMontoConcepto():new BigDecimal(0));
							expUpdate.setBdSaldoCredito(bdSaldoCredito); 
					 }
					 else if(Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES.compareTo(efectuadoCon.getIntTipoConceptoGeneralCod())==0)
					{
						 if(expUpdate.getBdSaldoInteres()== null) expUpdate.setBdSaldoInteres(new BigDecimal(0));						
						 expUpdate.setBdSaldoInteres(expUpdate.getBdSaldoInteres().subtract(efectuadoCon.getBdMontoConcepto()));
					}
					 	expUpdate = remoteConcepto.modificarExpediente(expUpdate);
					 
					 /**
					 *ACTUALIZAR CRONOGRAMA CAMPO SALDODETALLE CREDITO,
					 *SOLO SE ACTUALIZA CUANDO SE PAGUE TIPCONCEPTOCR =1 AMORTIZACION 
					*/
							log.debug("efectuadoCon.getIntTipoConceptoGeneralCod(): "+efectuadoCon.getIntTipoConceptoGeneralCod());
							if(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION.compareTo(efectuadoCon.getIntTipoConceptoGeneralCod()) == 0)
							{
								bdmontoConcepto = efectuadoCon.getBdMontoConcepto();
								List<Cronograma> lista = remoteConcepto.getListaCronogramaPorPkExpediente(exId);
								if(lista != null && !lista.isEmpty())
								  {
									  for(Cronograma cro:lista)
									  {
										  if(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION.compareTo(cro.getIntParaTipoConceptoCreditoCod()) == 0)
										  {
											  String  mesCron         = UtilCobranza.obtieneMesCadena(cro.getTsFechaVencimiento());
							           		  String  anioCron        = UtilCobranza.obtieneAnioCadena(cro.getTsFechaVencimiento());
							           		  Integer periodoCron  	  = new Integer(anioCron+mesCron);
							           	   
							           		 if (periodoCron.compareTo(efectuadoCon.getEfectuado().getIntPeriodoPlanilla()) == -1 || 
							           			  efectuadoCon.getEfectuado().getIntPeriodoPlanilla().compareTo(periodoCron) == 0){	
							           			 
							           			 if(bdmontoConcepto.compareTo(cro.getBdSaldoDetalleCredito())==1)
												  {
													  bdmontoConcepto= bdmontoConcepto.subtract(cro.getBdSaldoDetalleCredito());
													  cro.setBdSaldoDetalleCredito(new BigDecimal(0));
													  remoteConcepto.modificarCronograma(cro);
												  }else if(bdmontoConcepto.compareTo(cro.getBdSaldoDetalleCredito()) == -1)
												  {
													  cro.setBdSaldoDetalleCredito(cro.getBdSaldoDetalleCredito().subtract(bdmontoConcepto));
													  remoteConcepto.modificarCronograma(cro);
													  bdmontoConcepto = new BigDecimal(0);
													  break;
												  }
												  else if(bdmontoConcepto.compareTo(cro.getBdSaldoDetalleCredito()) == 0)
												  {
													 cro.setBdSaldoDetalleCredito(new BigDecimal(0));
													 remoteConcepto.modificarCronograma(cro);
													 bdmontoConcepto = new BigDecimal(0);
													 break;
												  }
							           		 }								 
											  
										  }
									  }
								  }
							}else if(Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES.compareTo(efectuadoCon.getIntTipoConceptoGeneralCod()) == 0)
							{
								tsFechaFin = UtilCobranza.obtieneFechaActualEnTimesTamp();
								log.debug("tsFechaInicio: "+tsFechaFin);
								 List<InteresCancelado> listaIC =  remoteConcepto.getListaInteresCanceladoPorExpedienteCredito(exId);
								 if(listaIC != null && !listaIC.isEmpty())
								 {
									log.debug("TIENE LISTA EN INTERES CANCELADO:"+listaIC.size());
									 for(InteresCancelado interes:listaIC)
									{
										tsFechaInicio = interes.getTsFechaMovimiento();
										//sumar un dia
										tsFechaInicio = UtilCobranza.sumarUnDiaAFecha(tsFechaInicio);								
									}
								 }
								 else 
								 {								 
									 ExpedienteCreditoId pId = new ExpedienteCreditoId();
			        				 pId.setIntPersEmpresaPk(efectuadoCon.getEfectuado().getIntEmpresausuarioPk());
			      					 pId.setIntCuentaPk(efectuadoCon.getEfectuado().getIntCuentaPk());
			      					 pId.setIntItemExpediente(efectuadoCon.getIntItemExpediente());
			      					 pId.setIntItemDetExpediente(efectuadoCon.getIntItemDetExpediente());
			      						
									 listaEstadoCredito = solicitudPrestamofacade.getListaEstadosPorExpedienteCreditoId(pId);
									 
									 if(listaEstadoCredito != null && !listaEstadoCredito.isEmpty()){
			        					  for(EstadoCredito estadoCredito:listaEstadoCredito){
			        						  if(estadoCredito.getIntParaEstadoCreditoCod()
			        								  .compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO) == 0){
			      								tsFechaInicio =  estadoCredito.getTsFechaEstado();
			      								
			      								break;
			      							}  
			        					  }
			        				  }
								 }
								 
								   java.sql.Date      dateFechaInicio   = new  java.sql.Date(tsFechaInicio.getTime());  
			        			   java.sql.Date      dateFechaFin      = new  java.sql.Date(tsFechaFin.getTime());  
			        			   int intNumeroDias = UtilCobranza.obtenerDiasEntreFechasPlanilla(dateFechaInicio,
			        					   														   dateFechaFin);
					        	   InteresCancelado canceladoI = new InteresCancelado();
					        	   canceladoI.setId(new InteresCanceladoId());
					        	   canceladoI.getId().setIntPersEmpresaPk(efectuadoCon.getEfectuado().getIntEmpresausuarioPk());
					        	   canceladoI.getId().setIntCuentaPk(efectuadoCon.getEfectuado().getIntCuentaPk());
					        	   canceladoI.getId().setIntItemExpediente(efectuadoCon.getIntItemExpediente());
					        	   canceladoI.getId().setIntItemExpedienteDetalle(efectuadoCon.getIntItemDetExpediente());
					        	   canceladoI.getId().setIntItemMovCtaCte(mov.getIntItemMovimiento());
					        	   canceladoI.setIntParaTipoFormaPago(Constante.PARAM_T_TIPOMOVIMIENTO_PLANILLA);
					        	   canceladoI.setBdSaldoCredito(expUpdate.getBdSaldoCredito());
					        	   canceladoI.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					        	   canceladoI.setTsFechaInicio(tsFechaInicio);
					        	   canceladoI.setTsFechaMovimiento(tsFechaFin);
					        	   canceladoI.setIntDias(intNumeroDias);
					        	   log.debug("intNumeroDias: "+intNumeroDias);
					        	   canceladoI.setBdTasa(expUpdate.getBdPorcentajeInteres());
					        	   bdMontoInteres = expUpdate.getBdSaldoCredito()
					        	   				    .multiply(expUpdate.getBdPorcentajeInteres()).multiply(new BigDecimal(intNumeroDias));
					        	   bdMontoInteres = bdMontoInteres.divide(new BigDecimal(3000),2,RoundingMode.HALF_UP);
					        		
					        	   log.debug("***bdMontoInteres grabado en efectuadoservice: "+bdMontoInteres);
					        	   canceladoI.setBdMontoInteres(bdMontoInteres);
					        	   remoteConcepto.grabarInteresCancelado(canceladoI);
							}
							/**
							 * CMO_ESTADOCPTOCEXPE SE ACTUALIZA SE CAMBIARÁ A 2 CANCELADO, SIEMPRE QUE DESPUÉS DE HABER ACTUALIZADO	
							 * EL CAMPO SALDOCREDITO EN LA TABLA EXPEDIENTECREDITO SEA IAGUAL A CERO
							 */
							if(expUpdate.getBdSaldoCredito().compareTo(new BigDecimal(0)) == 0)
							{
								//traer el estadoExpediente					
								EstadoExpediente estadoExpediente = remoteConcepto.getMaxEstadoExpPorPkExpediente(exId);
								if(estadoExpediente != null)
								{
									estadoExpediente.setIntParaEstadoExpediente(Constante.PARAM_T_ESTADO_EXPEDIENTE_CANCELADO);
									//modificar el estadoExpediente
									remoteConcepto.modificarEstadoExpediente(estadoExpediente);
								}					
							}
				}				
			}catch(BusinessException e){
				throw e;
			}catch(Exception e){
				throw new BusinessException(e);
			}
	}                         
	public void grabandoEnEsquemaMovimientoPrestamosLaDiferencia(EfectuadoConcepto efectuadoConceptoModificado,
																 BigDecimal bdDiferencia)
																throws BusinessException
	{
		log.debug("grabandoEnEsquemaMovimientoPrestamosLaDiferencia INICIO");
		BigDecimal bdmontoConcepto = new BigDecimal(0);
		Expediente expUpdate  					= null;
		try{
			ConceptoFacadeRemote remoteConcepto = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			
			//graba en movimiento
			Movimiento mov = new Movimiento();
			mov.setTsFechaMovimiento(UtilCobranza.obtieneFechaActualEnTimesTamp());
		    mov.setIntParaTipoMovimiento(Constante.PARAM_T_TIPOMOVIMIENTO_PROCESOPORPLANILLA);
		    mov.setIntParaDocumentoGeneral(Constante.PARAM_T_PLANILLAEFECTUADA); 
		    mov.setIntPersPersonaUsuario(efectuadoConceptoModificado.getEfectuado().getIntPersonausuarioPk());
		    mov.setIntPersEmpresaUsuario(efectuadoConceptoModificado.getEfectuado().getIntEmpresausuarioPk());		    
		    mov.setIntItemExpediente(efectuadoConceptoModificado.getIntItemExpediente());
		    mov.setIntItemExpedienteDetalle(efectuadoConceptoModificado.getIntItemDetExpediente());
		    mov.setIntPersPersonaIntegrante(efectuadoConceptoModificado.getEfectuado().getIntPersonaIntegrante());
		    mov.setIntPersEmpresa(efectuadoConceptoModificado.getId().getIntEmpresaCuentaEnvioPk());
		    mov.setIntCuenta(efectuadoConceptoModificado.getEfectuado().getIntCuentaPk());
	        mov.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION);
	        mov.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
	        mov.setIntPeriodoPlanilla(efectuadoConceptoModificado.getEfectuado().getIntPeriodoPlanilla());
	        mov.setBdMontoMovimiento(bdDiferencia);	        
	        mov.setStrNumeroDocumento(efectuadoConceptoModificado.getId().getIntItemEfectuado().toString());	     	        		        	
	        Movimiento movimiento = remoteConcepto.getMaxXExpYCtoGral(mov.getIntPersEmpresa(),
	        														  mov.getIntCuenta(),
	        														  mov.getIntItemExpediente(), 
	        														  mov.getIntItemExpedienteDetalle(),
	        														  Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION);				
	        	if(movimiento != null)
				{				
					if(movimiento.getIntParaTipoCargoAbono().compareTo(Constante.PARAM_T_CARGOABONO_CARGO) == 0)
					{
						//solo de montomovimiento
						if(movimiento.getBdMontoSaldo() != null)
						{
							mov.setBdMontoSaldo(movimiento.getBdMontoSaldo().subtract(mov.getBdMontoMovimiento()));
						}else
						{
							mov.setBdMontoSaldo(movimiento.getBdMontoMovimiento().subtract(mov.getBdMontoMovimiento()));	
						}						
					}
					else if(movimiento.getIntParaTipoCargoAbono().compareTo(Constante.PARAM_T_CARGOABONO_ABONO) == 0)
					{
						if(movimiento.getBdMontoSaldo() != null)
						{
							mov.setBdMontoSaldo(movimiento.getBdMontoSaldo().subtract(mov.getBdMontoMovimiento()));	
						}						
					}					        		
				}       	
				else
				{
					mov.setBdMontoSaldo(mov.getBdMontoMovimiento());
				}	        
	       			         		      
			remoteConcepto.grabarMovimiento(mov);
									
			/**
			 *actualiza en expediente credito
			 *amortizacion solo actualiza bdsaldocredito 
			 */
			ExpedienteId exId = new ExpedienteId();
			exId.setIntPersEmpresaPk(efectuadoConceptoModificado.getEfectuado().getIntEmpresausuarioPk());
			exId.setIntCuentaPk(efectuadoConceptoModificado.getEfectuado().getIntCuentaPk());
			exId.setIntItemExpediente(efectuadoConceptoModificado.getIntItemExpediente());
			exId.setIntItemExpedienteDetalle(efectuadoConceptoModificado.getIntItemDetExpediente());
			
			 expUpdate  =  remoteConcepto.getExpedientePorPK(exId);
				if(expUpdate != null)
				{					
					 if (expUpdate.getBdSaldoCredito() == null) expUpdate.setBdSaldoCredito(new BigDecimal(0));
						
						BigDecimal bdSaldoCredito = expUpdate.getBdSaldoCredito().subtract(bdDiferencia);
						expUpdate.setBdSaldoCredito(bdSaldoCredito); 					 
					 
					    expUpdate = remoteConcepto.modificarExpediente(expUpdate);
				}				
					
				    /**
				     *ACTUALIZAR CRONOGRAMA CAMPO SALDODETALLE CREDITO,
				     *SOLO SE ACTUALIZA CUANDO SE PAGUE TIPCONCEPTOCR =1 AMORTIZACION 
				     */
						bdmontoConcepto = bdDiferencia;
						List<Cronograma> lista = remoteConcepto.getListaCronogramaPorPkExpediente(exId);
						if(lista != null && !lista.isEmpty())
						  {
							  for(Cronograma cro:lista)
							  {								  
					           			if(bdmontoConcepto.compareTo(cro.getBdSaldoDetalleCredito())==1)
										  {
											  bdmontoConcepto= bdmontoConcepto.subtract(cro.getBdSaldoDetalleCredito());
											  cro.setBdSaldoDetalleCredito(new BigDecimal(0));
											  remoteConcepto.modificarCronograma(cro);
										  }else if(bdmontoConcepto.compareTo(cro.getBdSaldoDetalleCredito()) == -1)
										  {
											  cro.setBdSaldoDetalleCredito(cro.getBdSaldoDetalleCredito().subtract(bdmontoConcepto));
											  remoteConcepto.modificarCronograma(cro);
											  bdmontoConcepto = new BigDecimal(0);
											  break;
										  }
										  else if(bdmontoConcepto.compareTo(cro.getBdSaldoDetalleCredito()) == 0)
										  {
											 cro.setBdSaldoDetalleCredito(new BigDecimal(0));
											 remoteConcepto.modificarCronograma(cro);
											 bdmontoConcepto = new BigDecimal(0);
											 break;
										  }							 
							  }
						  }
				
				 /**
				  *CMO_ESTADOCPTOCEXPE SE ACTUALIZA SE CAMBIARÁ A 2  CANCELADO, SIEMPRE QUE DESPUÉS DE HABER ACTUALIZADO
				  *EL CAMPO SALDOCREDITO EN LA TABLA EXPEDIENTECREDITO SEA IAGUAL A CERO 		
				  */
				if(expUpdate.getBdSaldoCredito().compareTo(new BigDecimal(0))==0)
				{
					//traer el estadoExpediente					
					EstadoExpediente estadoExpediente = remoteConcepto.getMaxEstadoExpPorPkExpediente(exId);
					if(estadoExpediente != null)
					{
						estadoExpediente.setIntParaEstadoExpediente(Constante.PARAM_T_ESTADO_EXPEDIENTE_CANCELADO);
						//modificar el estadoExpediente
						remoteConcepto.modificarEstadoExpediente(estadoExpediente);
					}					
				}
				log.debug("grabandoEnEsquemaMovimientoPrestamosLaDiferencia FIN");
			}catch(BusinessException e){
				throw e;
			}catch(Exception e){
				throw new BusinessException(e);
			}
	}
	/**
	 *  grabo en movimiento
	 *  actualizo en cuentaconcepto	
	 *  actualizo cuentaconceptodetalle
		grabo en conceptoPago			
		grabo conceptopagodetalle
	 * @param efectuadoCon
	 * @throws BusinessException
	 */
	public void grabandoEnEsquemaMovimientoConceptos(EfectuadoConcepto efectuadoCon) throws BusinessException
	{		
		log.debug("grabandoEnEsquemaMovimientoConceptos INICIO");
		List<CuentaConceptoDetalle> listaCuentaConceptoDetalle = null;
		CuentaConceptoId cuentaId = null;
		BigDecimal bdTotal = new BigDecimal(0);
		Integer intUltimoPeriodo = 0;
		Integer intPeriodoInicial = 0;
		Movimiento mov = null;
		CuentaConcepto cuentaConcepto = null;
		log.debug(efectuadoCon);
		try{			
			ConceptoFacadeRemote remoteConcepto =(ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
						
			/**
			 * traendo cuentaconceptodetalle
			 */
			if (efectuadoCon != null) {
				if(efectuadoCon.getEfectuado() != null){
					cuentaId = new CuentaConceptoId();
					cuentaId.setIntCuentaPk(efectuadoCon.getEfectuado().getIntCuentaPk());
					cuentaId.setIntItemCuentaConcepto(efectuadoCon.getIntItemCuentaConcepto());
					cuentaId.setIntPersEmpresaPk(efectuadoCon.getId().getIntEmpresaCuentaEnvioPk());
					 
					listaCuentaConceptoDetalle = remoteConcepto.getListaCuentaConceptoDetallePorCuentaConcepto(cuentaId);
					
					if(listaCuentaConceptoDetalle != null && !listaCuentaConceptoDetalle.isEmpty())
					{ 
						for(CuentaConceptoDetalle cuentaConceptoDetalle: listaCuentaConceptoDetalle)
						{
							//grabo en movimiento
							mov = new Movimiento();
							mov.setTsFechaMovimiento(UtilCobranza.obtieneFechaActualEnTimesTamp());
						    mov.setIntParaTipoMovimiento(Constante.PARAM_T_TIPOMOVIMIENTO_PROCESOPORPLANILLA);
						    mov.setIntParaDocumentoGeneral(Constante.PARAM_T_PLANILLAEFECTUADA); 
						    mov.setIntPersPersonaUsuario(efectuadoCon.getEfectuado().getIntPersonausuarioPk());
						    mov.setIntPersEmpresaUsuario(efectuadoCon.getEfectuado().getIntEmpresausuarioPk());
						    mov.setIntItemCuentaConcepto(efectuadoCon.getIntItemCuentaConcepto()); 
						    mov.setIntPersPersonaIntegrante(efectuadoCon.getEfectuado().getIntPersonaIntegrante());
						    mov.setIntPersEmpresa(efectuadoCon.getId().getIntEmpresaCuentaEnvioPk());
						    mov.setIntCuenta(efectuadoCon.getEfectuado().getIntCuentaPk()); 
					        mov.setIntParaTipoConceptoGeneral(efectuadoCon.getIntTipoConceptoGeneralCod());
					        mov.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
					        mov.setIntPeriodoPlanilla(efectuadoCon.getEfectuado().getIntPeriodoPlanilla());
					        mov.setBdMontoMovimiento(efectuadoCon.getBdMontoConcepto());
					        mov.setStrNumeroDocumento(efectuadoCon.getId().getIntItemEfectuado().toString());
					        List<Movimiento> listaMovimiento = remoteConcepto.getListaMaximoMovimientoPorCuentaConcepto(mov.getIntPersEmpresa(),
															        											       mov.getIntCuenta(),
															        				               					   mov.getIntItemCuentaConcepto(),
															        				               					   mov.getIntParaTipoConceptoGeneral());
					        if(listaMovimiento != null && !listaMovimiento.isEmpty())
					        { 
					        	for(Movimiento ento: listaMovimiento)
					        	{
					        		if(ento.getBdMontoSaldo() != null)
					        		{	mov.setBdMontoSaldo(ento.getBdMontoSaldo().add(mov.getBdMontoMovimiento()));
						        		break;
					        		}
					        		else
					        		{ 
					        			mov.setBdMontoSaldo(mov.getBdMontoMovimiento());
					        			break;
					        		}			        		
					        	}
					        }else
					        { 
					        	 mov.setBdMontoSaldo(mov.getBdMontoMovimiento());
					        }
					        log.debug(mov); 
					        mov = remoteConcepto.grabarMovimiento(mov);
					       				
							//actualizar cuentaconcepto				
							cuentaConcepto = remoteConcepto.getCuentaConceptoPorPK(cuentaId);
							 
							if(cuentaConcepto != null)
							{ 
								cuentaConcepto.setBdSaldo(cuentaConcepto.getBdSaldo().add(efectuadoCon.getBdMontoConcepto()));
								remoteConcepto.modificarCuentaConcepto(cuentaConcepto);	
							}else{
								log.debug("cuenta: "+cuentaId.getIntCuentaPk());
							}
							 
							//actualizando cuentaconceptoDetalle
							cuentaConceptoDetalle.setBdSaldoDetalle(cuentaConceptoDetalle.getBdSaldoDetalle()
																	.add(efectuadoCon.getBdMontoConcepto()));									
							remoteConcepto.modificarCuentaConceptoDetalle(cuentaConceptoDetalle);
							 
							//grabar en conceptoPago
							if(Constante.PARAM_T_TIPODSCTOAPORT_ACUMULATIVO.compareTo(cuentaConceptoDetalle.getIntParaTipoDescuentoCod()) == 0)
							{ 
								log.debug("ACUMULATIVO");
								grabandoUnAcumulativoConceptoPago(efectuadoCon, 
														          cuentaConceptoDetalle,
														          mov,
														          intPeriodoInicial,
														          intUltimoPeriodo,
														          bdTotal);
								 
							}else if(Constante.PARAM_T_TIPODSCTOAPORT_CANCELATORIO.compareTo(cuentaConceptoDetalle.getIntParaTipoDescuentoCod()) == 0)
							{ 
								log.debug("CANCELATORIO");
								grabandoUnCancelatorioConceptoPago(mov,
																   efectuadoCon,
																   cuentaConceptoDetalle,
																   bdTotal);
								 
							} 															
						}
					}else{
						log.debug("listaCuentaConceptoDetalle is null");
					}
				}else{
					log.debug("efectuadoCon.Efectuado is null");
				}
			} else {
				log.debug("efectuadoCon is null");
			}			
			log.debug("grabandoEnEsquemaMovimientoConceptos FIN");	
		} 
		catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	public void grabarConceptoDetallePago(ConceptoPago conceptoPago,
										  Movimiento mov ) throws BusinessException
	{
		try
		{
			ConceptoFacadeRemote remoteConcepto =(ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			
			ConceptoDetallePago detalle = new ConceptoDetallePago();
			detalle.setId(new ConceptoDetallePagoId());
			detalle.getId().setIntPersEmpresaPk(conceptoPago.getId().getIntPersEmpresaPk());
			detalle.getId().setIntCuentaPk(conceptoPago.getId().getIntCuentaPk());
			detalle.getId().setIntItemCuentaConcepto(conceptoPago.getId().getIntItemCuentaConcepto());
			detalle.getId().setIntItemCtaCptoDet(conceptoPago.getId().getIntItemCtaCptoDet());
			detalle.getId().setIntItemConceptoPago(conceptoPago.getId().getIntItemConceptoPago());
			detalle.getId().setIntItemMovCtaCte(mov.getIntItemMovimiento());
			detalle.setIntTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
			detalle.setBdMonto(conceptoPago.getBdMontoPago());
			log.debug(detalle);
			remoteConcepto.grabarConceptoDetallePago(detalle);
		}
		catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	public Integer aumentarPeriodoInicial(Integer intPeriodoInicial) throws BusinessException
	{
		log.debug("aumentarPeriodoInicial INICIO");
		String strPeriodoAumentado = null;
		Integer intPeriodo=0;
		
		Integer intMes = Integer.parseInt(intPeriodoInicial.toString().substring(4));
		Integer intAnio = Integer.parseInt(intPeriodoInicial.toString().substring(0,4));
		try
		{
			if(intMes.compareTo(12) == 0)
			{
				intMes = 1;
				intAnio = intAnio+1;
				
				strPeriodoAumentado = intAnio.toString()+ ""+String.format("%02d",intMes);
				intPeriodo = Integer.parseInt(strPeriodoAumentado);
			}
			else
			{
				intMes = intMes+1;
				strPeriodoAumentado = intAnio.toString()+ ""+String.format("%02d",intMes);
				intPeriodo = Integer.parseInt(strPeriodoAumentado);				
			}			
			log.debug("intPeriodo: "+intPeriodo);
			log.debug("aumentarPeriodoInicial FIN");
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return intPeriodo;
	}
	
	public void grabandoUnAcumulativoConceptoPago(EfectuadoConcepto efectuadoCon,
												  CuentaConceptoDetalle cuentaConceptoDetalle,
												  Movimiento mov,
												  Integer intPeriodoInicial,
												  Integer intUltimoPeriodo,
												  BigDecimal bdTotal)
	{
		log.debug("grabandoUnAcumulativoConceptoPago INICIO");
		try
		{
			ConceptoFacadeRemote remoteConcepto =(ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			
			CuentaConceptoDetalleId id = new CuentaConceptoDetalleId();
			id.setIntPersEmpresaPk(efectuadoCon.getId().getIntEmpresaCuentaEnvioPk());
			id.setIntCuentaPk(efectuadoCon.getEfectuado().getIntCuentaPk());
			id.setIntItemCuentaConcepto(efectuadoCon.getIntItemCuentaConcepto());
			id.setIntItemCtaCptoDet(cuentaConceptoDetalle.getId().getIntItemCtaCptoDet());
			
			List<ConceptoPago> listaConPago =  remoteConcepto.getListaConceptoPagoToCobranza(id);
			
			bdTotal = efectuadoCon.getBdMontoConcepto();
			if(listaConPago != null && !listaConPago.isEmpty())
			{					
					for(ConceptoPago concepPago: listaConPago)
					{
						if(concepPago.getBdMontoSaldo().compareTo(new BigDecimal(0)) == 1)
						{
							log.debug("idconceptopago: "+concepPago.getId().getIntItemConceptoPago());
							log.debug("periodo: "+concepPago.getIntPeriodo());
							if(bdTotal.compareTo(concepPago.getBdMontoSaldo()) == -1)
							{
								concepPago.setBdMontoSaldo(concepPago.getBdMontoSaldo().subtract(bdTotal));
								concepPago.setBdMontoPago(concepPago.getBdMontoPago().add(bdTotal));
								bdTotal = new BigDecimal(0);									
							}
							else if(bdTotal.compareTo(concepPago.getBdMontoSaldo()) == 1)
							{
								concepPago.setBdMontoSaldo(new BigDecimal(0));
								concepPago.setBdMontoPago(cuentaConceptoDetalle.getBdMontoConcepto());									
								bdTotal = bdTotal.subtract(cuentaConceptoDetalle.getBdMontoConcepto());
							}
							else if(bdTotal.compareTo(concepPago.getBdMontoSaldo()) == 0)
							{
								concepPago.setBdMontoSaldo(new BigDecimal(0));
								concepPago.setBdMontoPago(cuentaConceptoDetalle.getBdMontoConcepto());
								bdTotal = new BigDecimal(0);									
							}
							//actualizar conceptoPago y grabo el conceptopagodetalle
							remoteConcepto.modificarConceptoPago(concepPago);
							//grabo en conceptodetallepago								
							grabarConceptoDetallePago(concepPago, mov);
						}
						
						if(concepPago.getIntPeriodo().compareTo(intUltimoPeriodo) == 1)
						{
							intUltimoPeriodo = concepPago.getIntPeriodo();								
						}
					}
					intUltimoPeriodo = aumentarPeriodoInicial(intUltimoPeriodo);
					log.debug("ultimoPeriodo: "+intUltimoPeriodo);
					//luego de grabar los pendientes grabo el periodo actual 
					//y si mi total aun tiene grabo en conceptodetallepago
					while(mov.getIntPeriodoPlanilla().compareTo(intUltimoPeriodo) == 1 ||
							mov.getIntPeriodoPlanilla().compareTo(intUltimoPeriodo) == 0)
					{					
						ConceptoPago conceptoPago = new ConceptoPago();
						conceptoPago.setId(new ConceptoPagoId());
						conceptoPago.getId().setIntPersEmpresaPk(efectuadoCon.getId().getIntEmpresaCuentaEnvioPk());
						conceptoPago.getId().setIntCuentaPk(efectuadoCon.getEfectuado().getIntCuentaPk());
						conceptoPago.getId().setIntItemCuentaConcepto(efectuadoCon.getIntItemCuentaConcepto());
						conceptoPago.getId().setIntItemCtaCptoDet(cuentaConceptoDetalle.getId().getIntItemCtaCptoDet());
						conceptoPago.setIntPeriodo(intUltimoPeriodo);
						
						if(bdTotal.compareTo(cuentaConceptoDetalle.getBdMontoConcepto()) == 1 ||
								bdTotal.compareTo(cuentaConceptoDetalle.getBdMontoConcepto()) == 0)
						{
							conceptoPago.setBdMontoPago(cuentaConceptoDetalle.getBdMontoConcepto());
							conceptoPago.setBdMontoSaldo(conceptoPago.getBdMontoPago()
									 				  .subtract(cuentaConceptoDetalle.getBdMontoConcepto()));							
							bdTotal = bdTotal.subtract(conceptoPago.getBdMontoPago());
							
						}else if(bdTotal.compareTo(cuentaConceptoDetalle.getBdMontoConcepto()) == -1)
						{
							conceptoPago.setBdMontoPago(bdTotal);
							conceptoPago.setBdMontoSaldo(cuentaConceptoDetalle.getBdMontoConcepto()
										.subtract(bdTotal));
							bdTotal = new BigDecimal(0);
						}
						conceptoPago.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						conceptoPago = remoteConcepto.grabarConceptoPago(conceptoPago);
						
						if(conceptoPago.getBdMontoPago().compareTo(new BigDecimal(0)) == 1)
						{
							//grabo en conceptodetallepago								
							grabarConceptoDetallePago(conceptoPago, mov);							
						}
						//aumentar periodoInicial						
						intUltimoPeriodo = aumentarPeriodoInicial(intUltimoPeriodo);
					}				
			}
			else
			{
				ConceptoPago conceptoPago = new ConceptoPago();
				conceptoPago.setId(new ConceptoPagoId());
				conceptoPago.getId().setIntPersEmpresaPk(efectuadoCon.getId().getIntEmpresaCuentaEnvioPk());
				conceptoPago.getId().setIntCuentaPk(efectuadoCon.getEfectuado().getIntCuentaPk());
				conceptoPago.getId().setIntItemCuentaConcepto(efectuadoCon.getIntItemCuentaConcepto());
				conceptoPago.getId().setIntItemCtaCptoDet(cuentaConceptoDetalle.getId().getIntItemCtaCptoDet());
				conceptoPago.setIntPeriodo(mov.getIntPeriodoPlanilla());
				
				if(bdTotal.compareTo(cuentaConceptoDetalle.getBdMontoConcepto()) == 1 ||
						bdTotal.compareTo(cuentaConceptoDetalle.getBdMontoConcepto()) == 0)
				{
					conceptoPago.setBdMontoPago(cuentaConceptoDetalle.getBdMontoConcepto());
					conceptoPago.setBdMontoSaldo(conceptoPago.getBdMontoPago()
							 				  .subtract(cuentaConceptoDetalle.getBdMontoConcepto()));							
					bdTotal = bdTotal.subtract(conceptoPago.getBdMontoPago());
					
				}else if(bdTotal.compareTo(cuentaConceptoDetalle.getBdMontoConcepto()) == -1)
				{
					conceptoPago.setBdMontoPago(bdTotal);
					conceptoPago.setBdMontoSaldo(cuentaConceptoDetalle.getBdMontoConcepto()
								.subtract(bdTotal));
					bdTotal = new BigDecimal(0);
				}
				conceptoPago.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				conceptoPago = remoteConcepto.grabarConceptoPago(conceptoPago);
				
				if(conceptoPago.getBdMontoPago().compareTo(new BigDecimal(0)) == 1)
				{
					//grabo en conceptodetallepago
					log.debug("iendo a grabar conceptodetallepago");
					grabarConceptoDetallePago(conceptoPago, mov);							
				}				
			}
			log.debug("grabandoUnAcumulativoConceptoPago FIN");
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}		
	}
	/*
	 * si esta en conceptopago el registro del periodo a efectuar actualizo conceptopago
	 * y grabo en conceptodetallepago
	 * si no esta en conceptoPago creo el registro solo para ese periodo a efectuar y grabo en conceptoDetallePago  
	 */
	public void grabandoUnCancelatorioConceptoPago(Movimiento mov,
												   EfectuadoConcepto efectuadoCon,
												   CuentaConceptoDetalle cuentaConceptoDetalle,
												   BigDecimal bdTotal)
	{
		log.debug("grabandoUnCancelatorioConceptoPago INICIO");
		Boolean blnEncontro = Boolean.FALSE;
		try
		{
			ConceptoFacadeRemote remoteConcepto =(ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			
			CuentaConceptoDetalleId id = new CuentaConceptoDetalleId();
			id.setIntPersEmpresaPk(efectuadoCon.getId().getIntEmpresaCuentaEnvioPk());
			id.setIntCuentaPk(efectuadoCon.getEfectuado().getIntCuentaPk());
			id.setIntItemCuentaConcepto(efectuadoCon.getIntItemCuentaConcepto());
			id.setIntItemCtaCptoDet(cuentaConceptoDetalle.getId().getIntItemCtaCptoDet());
			List<ConceptoPago> listaConPago =  remoteConcepto.getListaConceptoPagoToCobranza(id);
			
			bdTotal = efectuadoCon.getBdMontoConcepto();
			if(listaConPago != null && !listaConPago.isEmpty())
			{				
				for(ConceptoPago xx: listaConPago)
				{
					if(xx.getIntPeriodo().compareTo(mov.getIntPeriodoPlanilla()) == 0)
					{
						if(xx.getBdMontoSaldo().compareTo(new BigDecimal(0)) == 1)
						{
							if(bdTotal.compareTo(xx.getBdMontoSaldo()) == -1)
							{
								xx.setBdMontoSaldo(xx.getBdMontoSaldo().subtract(bdTotal));
								xx.setBdMontoPago(xx.getBdMontoPago().add(bdTotal));
								bdTotal = new BigDecimal(0);									
							}
							else if(bdTotal.compareTo(xx.getBdMontoSaldo()) == 1)
							{
								xx.setBdMontoSaldo(new BigDecimal(0));
								xx.setBdMontoPago(cuentaConceptoDetalle.getBdMontoConcepto());									
								bdTotal = bdTotal.subtract(cuentaConceptoDetalle.getBdMontoConcepto());
							}
							else if(bdTotal.compareTo(xx.getBdMontoSaldo()) == 0)
							{
								xx.setBdMontoSaldo(new BigDecimal(0));
								xx.setBdMontoPago(cuentaConceptoDetalle.getBdMontoConcepto());
								bdTotal = new BigDecimal(0);									
							}
							blnEncontro = Boolean.TRUE;
							//actualizar conceptoPago y grabo el conceptopagodetalle
							remoteConcepto.modificarConceptoPago(xx);
							//grabo en conceptodetallepago								
							grabarConceptoDetallePago(xx, mov);
						}
					}
				}
			}else
			{
				nuevoConceptoPago(efectuadoCon,
								  bdTotal,
								  mov,
								  cuentaConceptoDetalle);
				blnEncontro = Boolean.TRUE;
			}
			if(!blnEncontro)
			{
				nuevoConceptoPago(efectuadoCon,
						  		  bdTotal,
						  		  mov,
						  		  cuentaConceptoDetalle);	
			}
			log.debug("grabandoUnCancelatorioConceptoPago FIN");
		}
		catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}		
	}
	
	public void nuevoConceptoPago(EfectuadoConcepto efectuadoCon,
							      BigDecimal bdTotal,
							      Movimiento mov,
							      CuentaConceptoDetalle cuentaConceptoDetalle)
	{
		log.debug("nuevoConceptoPago INICIO");
		try
		{
			ConceptoFacadeRemote remoteConcepto =(ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			
			ConceptoPago conceptoPago = new ConceptoPago();
			conceptoPago.setId(new ConceptoPagoId());
			conceptoPago.getId().setIntPersEmpresaPk(efectuadoCon.getId().getIntEmpresaCuentaEnvioPk());
			conceptoPago.getId().setIntCuentaPk(efectuadoCon.getEfectuado().getIntCuentaPk());
			conceptoPago.getId().setIntItemCuentaConcepto(efectuadoCon.getIntItemCuentaConcepto());
			conceptoPago.getId().setIntItemCtaCptoDet(cuentaConceptoDetalle.getId().getIntItemCtaCptoDet());
			conceptoPago.setIntPeriodo(mov.getIntPeriodoPlanilla());
			
			if(bdTotal.compareTo(cuentaConceptoDetalle.getBdMontoConcepto()) == 1 ||
					bdTotal.compareTo(cuentaConceptoDetalle.getBdMontoConcepto()) == 0)
			{
				conceptoPago.setBdMontoPago(cuentaConceptoDetalle.getBdMontoConcepto());
				conceptoPago.setBdMontoSaldo(conceptoPago.getBdMontoPago()
						 				  .subtract(cuentaConceptoDetalle.getBdMontoConcepto()));							
				bdTotal = bdTotal.subtract(conceptoPago.getBdMontoPago());
				
			}else if(bdTotal.compareTo(cuentaConceptoDetalle.getBdMontoConcepto()) == -1)
			{
				conceptoPago.setBdMontoPago(bdTotal);
				conceptoPago.setBdMontoSaldo(cuentaConceptoDetalle.getBdMontoConcepto().subtract(bdTotal));
				bdTotal = new BigDecimal(0);
			}
			conceptoPago.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			conceptoPago = remoteConcepto.grabarConceptoPago(conceptoPago);
			
			if(conceptoPago.getBdMontoPago().compareTo(new BigDecimal(0)) == 1)
			{
				//grabo en conceptodetallepago
				log.debug("iendo a grabar en conceptodetallepago ");
				grabarConceptoDetallePago(conceptoPago, mov);							
			}
			log.debug("nuevoConceptoPago FIN");
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
}
