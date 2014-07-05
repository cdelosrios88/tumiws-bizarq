
package pe.com.tumi.cobranza.planilla.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Date;
import org.apache.log4j.Logger;
import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;
import pe.com.tumi.cobranza.planilla.domain.EnvioconceptoId;
import pe.com.tumi.cobranza.planilla.domain.Enviomonto;
import pe.com.tumi.cobranza.planilla.domain.EnviomontoId;
import pe.com.tumi.cobranza.planilla.domain.composite.ItemPlanilla;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.domain.SocioPK;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.movimiento.concepto.domain.BloqueoCuenta;
import pe.com.tumi.movimiento.concepto.domain.ConceptoPago;
import pe.com.tumi.movimiento.concepto.domain.Cronograma;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalleId;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoId;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.movimiento.concepto.domain.ExpedienteId;
import pe.com.tumi.movimiento.concepto.domain.InteresCancelado;
import pe.com.tumi.movimiento.concepto.domain.InteresProvisionado;
import pe.com.tumi.movimiento.concepto.domain.InteresProvisionadoId;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CapacidadCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.facade.SolicitudPrestamoFacadeRemote;
import pe.com.tumi.common.util.UtilCobranza;

public class DescuentoService {
	
  protected static Logger log = Logger.getLogger(DescuentoService.class);
  
  public void setDescuentoPorListaExpediente(ItemPlanilla lItemPlanilla,
		  									SocioEstructura lSocioEstructuraOrigen,
		  									List<Expediente> listaExpediente)
  		  throws BusinessException
  {
		log.info("setDescuentoPorListaExpediente==>INICIO");
	  	Timestamp tsFechaInicio 				= null; 
		InteresProvisionado interesProvisionado = null;
		List<Cronograma> listaCronograma 		= null;
		List<EstadoCredito>  listaEstadoCredito = null;
		Envioconcepto lEnvioconceptoA 			= null;
		Envioconcepto lEnvioconceptoI 			= null;
		List<BloqueoCuenta>  listaBloqueoCuenta = null;	
		Integer intBloqueoFechaInicio  			= null;
		Integer intBloqueoFechaFin   			= null;
		intBloqueoFechaInicio           		= 0;	
		intBloqueoFechaFin          			= 0;
		
		Calendar calendario	 			 = GregorianCalendar.getInstance();
		java.util.Date fechaActual		 = calendario.getTime();
		java.text.SimpleDateFormat sdf 	 = new java.text.SimpleDateFormat("yyyyMMdd");
		String fechaA 					 = sdf.format(fechaActual);
		Integer intFechaActual 			 = Integer.parseInt(fechaA);
		BigDecimal bdCero 				 = new BigDecimal(0);
		BigDecimal bdMontoMora 						= bdCero;		
		List<InteresCancelado> listaInteresCancelado = null;
		Timestamp tsFechaFin 						= null;		
		BigDecimal bdMontoInteres 					= bdCero;
		BigDecimal bdMontoInteresAtrasado			= bdCero;
		bdMontoMora = bdCero;
		bdMontoInteresAtrasado = bdCero;		
		Enviomonto lEnviomonto 						= null; 	
		EstructuraDetalle lEstructuraDetalle		= null;
		EstructuraId lEstructuraId 					= null;
		List<CapacidadCredito> listaCapacidadCre	= null;
		BigDecimal  bdTotalMontoMora 		= null;
		BigDecimal bdTotalMontoInteres		= null;
		BigDecimal bdTotalInteresAtrasado	= null;
		
		try{			
			//ver lo de bloqueo
			
			ConceptoFacadeRemote remoteConcepto 			= (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			EstructuraFacadeRemote remoteEstructura 		= (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			SolicitudPrestamoFacadeRemote facadeSolPres 	= (SolicitudPrestamoFacadeRemote)EJBFactory.getRemote(SolicitudPrestamoFacadeRemote.class);
			SolicitudPrestamoFacadeRemote solicitudPres   	= (SolicitudPrestamoFacadeRemote)EJBFactory.getRemote(SolicitudPrestamoFacadeRemote.class);
			
			lEstructuraId 		= new EstructuraId();
			lEstructuraId.setIntCodigo(lSocioEstructuraOrigen.getIntCodigo());
			lEstructuraId.setIntNivel(lSocioEstructuraOrigen.getIntNivel());
			
			lEstructuraDetalle = remoteEstructura.getEstructuraDetallePorPkEstructuraYCasoYTipoSocioYModalidad(lEstructuraId,
															 						Constante.PARAM_T_CASOESTRUCTURA_PROCESAPLANILLA,							
															 						lSocioEstructuraOrigen.getIntTipoSocio(),
															 						lSocioEstructuraOrigen.getIntModalidad());
			bdTotalMontoMora 		= bdCero;
			bdTotalMontoInteres		= bdCero;
			bdTotalInteresAtrasado	= bdCero;
			log.debug("cant listaexpediente="+listaExpediente.size());			
			Boolean blnError = Boolean.FALSE;
			for(Expediente expediente :listaExpediente)	
			{				
				bdMontoMora = bdCero;
				bdMontoInteres = bdCero;
				bdMontoInteresAtrasado = bdCero;				
				Boolean blnAplicaDsctoA = Boolean.TRUE;
				Boolean blnAplicaDsctoI = Boolean.TRUE;
				
				log.debug("item="+expediente.getId().getIntItemExpediente()+
						  "detalleExp="+expediente.getId().getIntItemExpedienteDetalle()+
						  "intparatipocredito="+expediente.getIntParaTipoCreditoCod());				
				// SOLO PARA EL (100%)				
				Integer intCarta = 1;
				ExpedienteCreditoId expCre= new ExpedienteCreditoId();
				expCre.setIntCuentaPk(lItemPlanilla.getCuentaIntegrante().getId().getIntCuenta());
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
								lItemPlanilla.setBlnCartaAutorizacion(Boolean.TRUE);
								if( lItemPlanilla.getListaEnvioConcepto() != null && ! lItemPlanilla.getListaEnvioConcepto().isEmpty())
								{
									for(Envioconcepto env: lItemPlanilla.getListaEnvioConcepto())
									{										
										env.setIntIndicienpor(1);
									}
								}								
							}
							else
							{
								lItemPlanilla.setBlnCartaAutorizacion(Boolean.FALSE);
								if( lItemPlanilla.getListaEnvioConcepto() != null && ! lItemPlanilla.getListaEnvioConcepto().isEmpty())
								{
									for(Envioconcepto env: lItemPlanilla.getListaEnvioConcepto())
									{										
										env.setIntIndicienpor(0);
									}
								}
							}
						}
						
					}
				}
							
				listaBloqueoCuenta = remoteConcepto.getListaBloqueoCuentaPorNroCuenta(expediente.getId().getIntPersEmpresaPk(), 
									 												  expediente.getId().getIntCuentaPk());
				//TRAIGO MI LISTA DE BLOQUEOCUENTA solo bloqueando a interes y amortizacion
				if(listaBloqueoCuenta != null && !listaBloqueoCuenta.isEmpty())
				{
					log.info("listaBloqueoCuenta cant"+listaBloqueoCuenta.size());
						for(BloqueoCuenta bloqueoCuenta :listaBloqueoCuenta)
						{					
							if(bloqueoCuenta.getIntItemExpediente() != null && bloqueoCuenta.getIntParaTipoConceptoCre() != null)
							{		
								if(bloqueoCuenta.getIntParaTipoConceptoCre()
										.compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION) == 0)
								{
									if(bloqueoCuenta.getTsFechaFin() == null)
									{
										log.debug("no tiene fecha fin");
										Date dateBloqueInicio       = new Date(bloqueoCuenta.getTsFechaInicio().getTime()); 									
										String fechaBloqueInicio 	= sdf.format(dateBloqueInicio);
										intBloqueoFechaInicio		= Integer.parseInt(fechaBloqueInicio);	
										if(intBloqueoFechaInicio < intFechaActual)
										{
											log.debug("bloqueando.");
											blnAplicaDsctoA = Boolean.FALSE; //se bloquea ya que esta dentro del rango de fechas y no mando nada de amortizacion
										}
										else if(intFechaActual < intBloqueoFechaInicio)
										{
											blnAplicaDsctoA = Boolean.TRUE; //mando amortizacion
										}
									}
									else
									{
										Date dateBloqueFin  = new Date(bloqueoCuenta.getTsFechaFin().getTime());											
										String fechaBloqueFin = sdf.format(dateBloqueFin);
										intBloqueoFechaFin = Integer.parseInt(fechaBloqueFin);	
											if(intFechaActual < intBloqueoFechaFin && intFechaActual > intBloqueoFechaInicio)
											{
												blnAplicaDsctoA = Boolean.FALSE; //se bloquea y no mando nada de amortizacion
											}
											else
											{
												blnAplicaDsctoA = Boolean.TRUE; //mando amortizacion
											}
										}																			
									}
								else if(bloqueoCuenta.getIntParaTipoConceptoCre()
										.compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES) == 0)
								{
									if(bloqueoCuenta.getTsFechaFin() == null)
									{
										Date dateBloqueInicio       = new Date(bloqueoCuenta.getTsFechaInicio().getTime()); 									
										String fechaBloqueInicio 	= sdf.format(dateBloqueInicio);
										intBloqueoFechaInicio		= Integer.parseInt(fechaBloqueInicio);	
										if(intBloqueoFechaInicio < intFechaActual)
										{
											blnAplicaDsctoI = Boolean.FALSE; //se bloquea no mando interes
										}
										else if(intFechaActual < intBloqueoFechaInicio)
										{
											blnAplicaDsctoI = Boolean.TRUE; //mando interes
										}
									}
									else
									{
										Date dateBloqueFin  = new Date(bloqueoCuenta.getTsFechaFin().getTime());											
										String fechaBloqueFin = sdf.format(dateBloqueFin);
										intBloqueoFechaFin = Integer.parseInt(fechaBloqueFin);	
											if(intFechaActual < intBloqueoFechaFin && intFechaActual > intBloqueoFechaInicio)
											{
												blnAplicaDsctoI = Boolean.FALSE; //se bloquea no mando interes
											}
											else
											{
												blnAplicaDsctoI = Boolean.TRUE; //mando interes
											}
									}
										
								}
								
							}
					}
				}
				log.debug("exp: "+expediente.getId().getIntItemExpediente());
				log.debug("expdet: "+expediente.getId().getIntItemExpedienteDetalle());
				
				listaCronograma = remoteConcepto.getListaCronogramaPorPkExpediente(expediente.getId());
						
				
				//con el fin de tener la sumatoria de saldodetallecredito y la fechaVencimiento de mi periodo
				//a enviar para la fechafin		
				if(listaCronograma != null && !listaCronograma.isEmpty())
				{
					//log.debug("hay lista de cronograma");
					for(Cronograma cronograma:listaCronograma)
					{
						if(cronograma.getIntParaTipoConceptoCreditoCod()
								.compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION) == 0)
						{
							if(lItemPlanilla.getIntPeriodo() >= cronograma.getIntPeriodoPlanilla())
							{
								if(blnAplicaDsctoA)
								{
									bdMontoMora = bdMontoMora.add(cronograma.getBdSaldoDetalleCredito());
									
								}						
							}								
							if(lItemPlanilla.getIntPeriodo().compareTo(cronograma.getIntPeriodoPlanilla()) == 0)
							{	
								tsFechaFin = cronograma.getTsFechaVencimiento();
							}		
							
						}
						
					}
					bdTotalMontoMora = bdTotalMontoMora.add(bdMontoMora);
					
					if(tsFechaFin == null)
					{
						//obtengo mi periodo y de este periodo  su ultimo dia del mes								
						tsFechaFin = UtilCobranza.obtenerUltimoDiaDelMesAnioPeriodo(lItemPlanilla.getIntPeriodo());
						//log.debug("fechaFin="+tsFechaFin);
					}					
					
					//Amortizacion
					//if(blnAplicaDsctoA)
					//{
						log.debug("blnAplicaDsctoA entro");
						lEnvioconceptoA = new Envioconcepto();
						if(lItemPlanilla.getBlnCartaAutorizacion())
						{
							lEnvioconceptoA.setIntIndicienpor(1);
						}else if(!lItemPlanilla.getBlnCartaAutorizacion())
						{
							lEnvioconceptoA.setIntIndicienpor(0);
						}
						if(lItemPlanilla.getBlnDJUD())
						{
							lEnvioconceptoA.setIntIndidescjudi(1);	
						}
						else if(!lItemPlanilla.getBlnDJUD())
						{
							lEnvioconceptoA.setIntIndidescjudi(0);
						}
						if(lItemPlanilla.getBlnLIC())
						{
							lEnvioconceptoA.setIntIndilicencia(1);	
						}else if(!lItemPlanilla.getBlnLIC())
						{
							lEnvioconceptoA.setIntIndilicencia(0);
						}
						lEnvioconceptoA.setId(new EnvioconceptoId());
						lEnvioconceptoA.getId().setIntEmpresacuentaPk(expediente.getId().getIntPersEmpresaPk());
						lEnvioconceptoA.setIntPeriodoplanilla(lItemPlanilla.getIntPeriodo());
						lEnvioconceptoA.setIntCuentaPk(expediente.getId().getIntCuentaPk());									
						lEnvioconceptoA.setIntItemexpediente(expediente.getId().getIntItemExpediente());
						lEnvioconceptoA.setIntItemdetexpediente(expediente.getId().getIntItemExpedienteDetalle());
						lEnvioconceptoA.setIntTipoconceptogeneralCod(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION);	
						lEnvioconceptoA.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						if(blnAplicaDsctoA)
						{
							lEnvioconceptoA.setBdMontoconcepto(bdMontoMora);
						}else{
							lEnvioconceptoA.setBdMontoconcepto(new BigDecimal(0));
						}
						
						
						if(lItemPlanilla.getListaEnvioConcepto() == null)
						{
							lItemPlanilla.setListaEnvioConcepto(new ArrayList<Envioconcepto>());
						}
						lItemPlanilla.getListaEnvioConcepto().add(lEnvioconceptoA);
					//}
					
					log.debug("bdMontoAmortizacion="+bdMontoMora);
					/**
					 * solo para prestamo relacionado al interesprovisionado el porcentaje de interes
					 * 
					 */
					if(Constante.PARAM_T_TIPO_CREDITO_PRESTAMO.compareTo(expediente.getIntParaTipoCreditoCod()) == 0)
					{						
						
						//interesProvisionado conseguir fechaInicio
						ExpedienteId icId = new ExpedienteId();
						icId.setIntCuentaPk(expediente.getId().getIntCuentaPk());
						icId.setIntPersEmpresaPk(expediente.getId().getIntPersEmpresaPk());
						icId.setIntItemExpediente(expediente.getId().getIntItemExpediente());
						icId.setIntItemExpedienteDetalle(expediente.getId().getIntItemExpedienteDetalle());
						
						listaInteresCancelado =  remoteConcepto.getListaInteresCanceladoPorExpedienteCredito(icId);
						
						if(listaInteresCancelado != null && !listaInteresCancelado.isEmpty())
						{
							for(InteresCancelado interesCancelado: listaInteresCancelado)
							{
								tsFechaInicio = interesCancelado.getTsFechaMovimiento();
								//sumo un dia
								tsFechaInicio = UtilCobranza.sumarUnDiaAFecha(tsFechaInicio);
								log.debug("fechaINicio="+tsFechaInicio);
								break;
							}
							
						}
						//Si mi lista de interesCancelado es null traigo mi fechainicio de estadoCredito
						else
						{
							log.debug("listaInteresCancelado is null");
							ExpedienteCreditoId pId = new ExpedienteCreditoId();
							pId.setIntPersEmpresaPk(expediente.getId().getIntPersEmpresaPk());
							pId.setIntCuentaPk(expediente.getId().getIntCuentaPk());
							pId.setIntItemExpediente(expediente.getId().getIntItemExpediente());
							pId.setIntItemDetExpediente(expediente.getId().getIntItemExpedienteDetalle());
							//log.debug(pId);
							
							listaEstadoCredito = facadeSolPres.getListaEstadosPorExpedienteCreditoId(pId);
							
							if(listaEstadoCredito != null && !listaEstadoCredito.isEmpty())
							{
								log.info("listaEstadoCredito cant="+listaEstadoCredito.size());
								for(EstadoCredito estadoCredito:listaEstadoCredito)
								{
									if(estadoCredito.getIntParaEstadoCreditoCod()
											.compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO) == 0)
									{
										tsFechaInicio =  estadoCredito.getTsFechaEstado();
										log.debug("fechainicio de estadoCredito="+tsFechaInicio);
										break;
									}
								}
							}
							else
							{
								log.info("listaEstadoCredito es="+listaEstadoCredito);	
							}
						}
						//fin de fechainicio				
						java.sql.Date      dateFechaInicio      = new           java.sql.Date(tsFechaInicio.getTime()); 
						java.sql.Date      dateFechaFin         = new           java.sql.Date(tsFechaFin.getTime());
						int intNumerosDias = UtilCobranza.obtenerDiasEntreFechasPlanilla(dateFechaInicio, dateFechaFin);
				       
						interesProvisionado = new InteresProvisionado();
						interesProvisionado.setId(new InteresProvisionadoId());
						interesProvisionado.getId().setIntPersEmpresaPk(expediente.getId().getIntPersEmpresaPk());
						interesProvisionado.getId().setIntCuentaPk(expediente.getId().getIntCuentaPk());
						interesProvisionado.getId().setIntItemExpediente(expediente.getId().getIntItemExpediente());
						interesProvisionado.getId()
						.setIntItemExpedienteDetalle(expediente.getId().getIntItemExpedienteDetalle());
						interesProvisionado.setIntParaTipoMovInt(Constante.PARAM_T_ENVIADO_POR_PLANILLA);
						interesProvisionado.setTsFechaInicio(tsFechaInicio);
						interesProvisionado.setTsFechaFin(tsFechaFin); 					
						interesProvisionado.setIntNumeroDias(intNumerosDias);
						interesProvisionado.setBdSaldoPrestamo(expediente.getBdSaldoCredito());
						interesProvisionado.setBdTasaInteres(expediente.getBdPorcentajeInteres());
						
						//calculando el interes esto sale de la formula I= saldo*tasa*dias/100*30
						bdMontoInteres = bdCero;
												
						if(blnAplicaDsctoI)
						{							
							bdMontoInteres = expediente.getBdPorcentajeInteres()
											.multiply(expediente.getBdSaldoCredito())
											.multiply(new BigDecimal(intNumerosDias));
							bdMontoInteres = bdMontoInteres.divide(new BigDecimal(3000),2,RoundingMode.HALF_UP); 
							bdTotalMontoInteres = bdTotalMontoInteres.add(bdMontoInteres);
							
						}	
						if(expediente.getBdMontoInteresAtrazado() != null)
						{
							bdMontoInteresAtrasado = expediente.getBdMontoInteresAtrazado();
							bdTotalInteresAtrasado = bdTotalInteresAtrasado.add(bdMontoInteresAtrasado);						
						}
						else
						{
							bdMontoInteresAtrasado = bdCero;
							bdTotalInteresAtrasado = bdTotalInteresAtrasado.add(bdMontoInteresAtrasado);							
						}
						interesProvisionado.setBdMontoInteres(bdMontoInteres); 
						interesProvisionado.setBdMontoAtrasadoInteres(null);
						interesProvisionado.setBdMontoTotalInteres(null);
						interesProvisionado.setBdMontoSaldo(null);			
						interesProvisionado.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);						
						
						expediente.getListaInteresProvisionado().add(interesProvisionado);
						log.debug("cant listainteresprovisionado="+expediente.getListaInteresProvisionado().size()); 
					
						
						//if(blnAplicaDsctoI)
						//{
							log.debug("blnAplicaDsctoI entro");
							lEnvioconceptoI = new Envioconcepto();
							if(lItemPlanilla.getBlnCartaAutorizacion())
							{
								lEnvioconceptoI.setIntIndicienpor(1);
							}else if(!lItemPlanilla.getBlnCartaAutorizacion())
							{
								lEnvioconceptoI.setIntIndicienpor(0);
							}
							if(lItemPlanilla.getBlnDJUD())
							{
								lEnvioconceptoI.setIntIndidescjudi(1);	
							}
							else if(!lItemPlanilla.getBlnDJUD())
							{
								lEnvioconceptoI.setIntIndidescjudi(0);
							}
							if(lItemPlanilla.getBlnLIC())
							{
								lEnvioconceptoI.setIntIndilicencia(1);	
							}else if(!lItemPlanilla.getBlnLIC())
							{
								lEnvioconceptoI.setIntIndilicencia(0);
							}
							lEnvioconceptoI.setId(new EnvioconceptoId());					
							lEnvioconceptoI.getId().setIntEmpresacuentaPk(expediente.getId().getIntPersEmpresaPk());
							lEnvioconceptoI.setIntPeriodoplanilla(lItemPlanilla.getIntPeriodo());
							lEnvioconceptoI.setIntCuentaPk(expediente.getId().getIntCuentaPk());										
							lEnvioconceptoI.setIntItemexpediente(expediente.getId().getIntItemExpediente());
							lEnvioconceptoI.setIntItemdetexpediente(expediente.getId().getIntItemExpedienteDetalle());							
							lEnvioconceptoI.setIntTipoconceptogeneralCod(Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES);
							lEnvioconceptoI.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);	
							if(blnAplicaDsctoI){
								lEnvioconceptoI.setBdMontoconcepto(bdMontoInteres);
							}else{
								lEnvioconceptoI.setBdMontoconcepto(new BigDecimal(0));
							}
							
							
							if(lItemPlanilla.getListaEnvioConcepto() == null)
							{
								lItemPlanilla.setListaEnvioConcepto(new ArrayList<Envioconcepto>());
							}
							lItemPlanilla.getListaEnvioConcepto().add(lEnvioconceptoI);
						//}
						
						if(lItemPlanilla.getListaExpediente() == null)
						{
							lItemPlanilla.setListaExpediente(new ArrayList<Expediente>());
						}
						lItemPlanilla.getListaExpediente().add(expediente);
						
					}else
					{
						if(lItemPlanilla.getListaExpediente() == null)
						{
							lItemPlanilla.setListaExpediente(new ArrayList<Expediente>());
						}
						lItemPlanilla.getListaExpediente().add(expediente);
					}				
				 
				
				 }else
				 {
					 log.debug("listaCronograma is null.");
					// blnError  = Boolean.TRUE;
					 continue;
				 }
			}				
				//if(!blnError){
			if(lItemPlanilla.getListaEnvioConcepto() != null && !lItemPlanilla.getListaEnvioConcepto().isEmpty())
			{
				if(lItemPlanilla.getListaEnviomonto() == null)
				{
					lItemPlanilla.setListaEnviomonto(new ArrayList<Enviomonto>());
				}
				
				lEnviomonto = new Enviomonto();
		    	lEnviomonto.setId(new EnviomontoId());
		    	lEnviomonto.getId().setIntEmpresacuentaPk(lSocioEstructuraOrigen.getId().getIntIdEmpresa());		    	
		    	lEnviomonto.setBdMontoenvio(bdTotalMontoMora.add(bdTotalMontoInteres).add(bdTotalInteresAtrasado));
		    	lEnviomonto.setIntTiposocioCod(lSocioEstructuraOrigen.getIntTipoSocio());
		    	lEnviomonto.setIntModalidadCod(lSocioEstructuraOrigen.getIntModalidad());
		    	lEnviomonto.setIntNivel(lSocioEstructuraOrigen.getIntNivel());
		    	lEnviomonto.setIntCodigo(lSocioEstructuraOrigen.getIntCodigo());
		    	lEnviomonto.setIntTipoestructuraCod(lSocioEstructuraOrigen.getIntTipoEstructura());
		    	
		    	lEnviomonto.setIntEmpresasucprocesaPk(lEstructuraDetalle.getIntPersEmpresaPk());
		    	lEnviomonto.setIntIdsucursalprocesaPk(lEstructuraDetalle.getIntSeguSucursalPk());
		    	lEnviomonto.setIntIdsubsucursalprocesaPk(lEstructuraDetalle.getIntSeguSubSucursalPk());
		    	
		    	lEnviomonto.setIntEmpresasucadministraPk(lSocioEstructuraOrigen.getIntEmpresaSucAdministra());
		    	lEnviomonto.setIntIdsucursaladministraPk(lSocioEstructuraOrigen.getIntIdSucursalAdministra());
		    	lEnviomonto.setIntIdsubsucursaladministra(lSocioEstructuraOrigen.getIntIdSubsucurAdministra());
		    	lEnviomonto.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		    	lItemPlanilla.getListaEnviomonto().add(lEnviomonto);	
				
			//la amortizacion+el interes+montointeresatrasado
			if (lSocioEstructuraOrigen != null) {
				if (lSocioEstructuraOrigen.getIntModalidad().compareTo(
						Constante.PARAM_T_MODALIDADPLANILLA_HABERES) == 0) {
					lItemPlanilla.setBdHaberes(lEnviomonto.getBdMontoenvio());

				} else if (lSocioEstructuraOrigen.getIntModalidad().compareTo(
						Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS) == 0) {
					lItemPlanilla
							.setBdIncentivos(lEnviomonto.getBdMontoenvio());
				} else if (lSocioEstructuraOrigen.getIntModalidad().compareTo(
						Constante.PARAM_T_MODALIDADPLANILLA_CAS) == 0) {
					lItemPlanilla.setBdCas(lEnviomonto.getBdMontoenvio());
				}
			}
			}					
			//}
			
			log.info("setDescuentoPorListaExpediente==>FIN");
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
  
  
  public BigDecimal getDescuentoPorListaConcepto(ItemPlanilla lItemPlanilla,
		  							 		     CuentaIntegrante cuentaIntegranteRspta,
		  							 		     List<CuentaConcepto> listaCuentaConcepto)
  												 throws BusinessException
	{
		log.debug("getDescuentoPorListaConcepto INICIO v1");	  	
		EstructuraFacadeRemote remoteEstructura 	= null;
		CuentaConceptoDetalle conceptoDetalleTemp 	= null;	
		ConceptoFacadeRemote     conceptoFacade 	= null;		
		BigDecimal bdDescuento 						= null;			
		BigDecimal  totalMontoConcepto   			= null;
		BigDecimal  bdEnvioTemporal   			= null;
		CuentaConcepto dto 						= null;
		SocioEstructura lSocioEstructura		 = null;		
		SocioPK lSocioPk						 = null;
		EstructuraId lEstructuraId 				= null;
		EstructuraDetalle lEstructuraDetalle	 = null;
		Envioconcepto lEnvioconcepto 			= null;
		Enviomonto lEnviomonto 					= null; 
		Integer intDetalleIncio 				= null;
		Integer intDetalleFin   				= null;	
		Integer intBloqueoFechaInicio  			 = null;
		Integer intBloqueoFechaFin   			 = null;
		intDetalleIncio        					 = 0;	
		intDetalleFin          					 = 0;			
		intBloqueoFechaInicio           		 = 0;	
		intBloqueoFechaFin          			 = 0;
		
		
		Boolean blnAplicaDscto = Boolean.FALSE;
		List<CuentaConceptoDetalle> listaCuentaConceptoDetalle = null;
		try{			
			remoteEstructura 	= (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			conceptoFacade      = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);			
			lSocioPk 			= new SocioPK();
			lSocioPk.setIntIdEmpresa(cuentaIntegranteRspta.getId().getIntPersEmpresaPk());
			lSocioPk.setIntIdPersona(cuentaIntegranteRspta.getId().getIntPersonaIntegrante());
					
			for(SocioEstructura sura:lItemPlanilla.getSocio().getListSocioEstructura())
			{
				if(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN.compareTo(sura.getIntTipoEstructura()) == 0)
				{
					lSocioEstructura = sura;
				}
			}
			
			lEstructuraId 		= new EstructuraId();
			lEstructuraId.setIntCodigo(lSocioEstructura.getIntCodigo());
			lEstructuraId.setIntNivel(lSocioEstructura.getIntNivel());
			
			lEstructuraDetalle = remoteEstructura.getEstructuraDetallePorPkEstructuraYCasoYTipoSocioYModalidad(
												lEstructuraId,Constante.PARAM_T_CASOESTRUCTURA_PROCESAPLANILLA,							
												lSocioEstructura.getIntTipoSocio(),
												lSocioEstructura.getIntModalidad());
		
			
			bdDescuento						 = new BigDecimal(0);					 
			totalMontoConcepto			     = new BigDecimal(0); 
			Calendar calendario	 			 = GregorianCalendar.getInstance();
			java.util.Date fechaActual		 = calendario.getTime();
			java.text.SimpleDateFormat sdf 	 = new java.text.SimpleDateFormat("yyyyMMdd");
			String fechaA 					 = sdf.format(fechaActual);
			Integer intFechaActual 			= Integer.parseInt(fechaA);		
			Integer n 						= 0;
			Integer m 						= 0;
			Integer o 						= 0;
			Integer p 						= 0;					
			
					for(int i = 0; i<listaCuentaConcepto.size(); i++)
					{
						Boolean blnEncontro = Boolean.FALSE;
						Boolean blnEncontroAcumulativo = Boolean.FALSE;
						dto = listaCuentaConcepto.get(i);
						log.debug("paratipoconcepto="+dto.getDetalle().getIntParaTipoConceptoCod());
						log.debug("tipodescuento="+dto.getDetalle().getIntParaTipoDescuentoCod());
						log.debug("m="+m);
						if(dto.getDetalle().getIntParaTipoDescuentoCod() != null)
						{	
							
								if(lItemPlanilla.getListaEnvioConcepto() == null)
								{
									lItemPlanilla.setListaEnvioConcepto(new ArrayList<Envioconcepto>());
								}
								
								if(dto.getDetalle().getIntParaTipoConceptoCod().compareTo(Constante.CAPTACION_APORTACIONES) == 0 && m < 1)
								{
								log.info("CAPTACION_APORTACIONES");
								if(dto.getDetalle().getIntParaTipoDescuentoCod().compareTo(Constante.PARAM_T_TIPODSCTOAPORT_CANCELATORIO) == 0)
								{	
									log.debug("CAPTACION_APORTACIONES CANCELATORIO");
									if(dto.getDetalle().getTsInicio() != null)
									{
										Date dateDetalleInicio  			= new Date(dto.getDetalle().getTsInicio().getTime());												
										String fechaDetalleInicio 			= sdf.format(dateDetalleInicio);
										intDetalleIncio 					= Integer.parseInt(fechaDetalleInicio);
										 
										if(dto.getDetalle().getTsFin() == null)
										{
											if(intDetalleIncio.compareTo(intFechaActual) == 0 || intDetalleIncio.compareTo(intFechaActual) == -1)
											{
												if(dto.getBloqueo() != null)
												{	
													//BI bloqueo fecha de inicio
													Date dateBloqueInicio       = new Date(dto.getBloqueo().getTsFechaInicio().getTime()); 									
													String fechaBloqueInicio 	= sdf.format(dateBloqueInicio);
													intBloqueoFechaInicio		 = Integer.parseInt(fechaBloqueInicio);			
													if(dto.getBloqueo().getIntItemBloqueoCuenta()== null)
													{ 
														blnAplicaDscto = Boolean.TRUE;
														m++;
													}
													else
													{
															if(dto.getBloqueo().getTsFechaFin() == null)
															{	
																if(intBloqueoFechaInicio < intFechaActual)
																{
																	blnAplicaDscto = Boolean.FALSE;
																	m++;
																}else if(intFechaActual < intBloqueoFechaInicio)
																{
																	blnAplicaDscto = Boolean.TRUE;
																	m++;
																}
															} 
															else
															{
																Date dateBloqueFin  = new Date(dto.getBloqueo().getTsFechaFin().getTime());											
																String fechaBloqueFin = sdf.format(dateBloqueFin);
																intBloqueoFechaFin = Integer.parseInt(fechaBloqueFin);					
																if(intFechaActual < intBloqueoFechaFin && intFechaActual > intBloqueoFechaInicio)
																{
																	blnAplicaDscto = Boolean.FALSE;
																}
																else
																{
																	blnAplicaDscto = Boolean.TRUE;
																	m++;
																}										
															}
													}
												}
												else
												{
													blnAplicaDscto = Boolean.TRUE;
													m++;
												}
											}
										}
										else
										{
											Date dateDetalleFin      	= new Date(dto.getDetalle().getTsFin().getTime()); 							
											String fechaDetalleFin 		= sdf.format(dateDetalleFin);
											intDetalleFin 				= Integer.parseInt(fechaDetalleFin);
											if(intFechaActual< intDetalleFin && intDetalleIncio < intFechaActual)
											{
												if(dto.getBloqueo() != null)
												{																														
													if(dto.getBloqueo().getIntItemBloqueoCuenta()== null)
													{ 
														blnAplicaDscto = Boolean.TRUE;
														m++;
													}
													else
													{
														if(dto.getBloqueo().getTsFechaFin() == null)
														{	
															if(intBloqueoFechaInicio < intFechaActual)
															{
																blnAplicaDscto = Boolean.FALSE;
															}	
															else if(intBloqueoFechaInicio > intFechaActual)
															{
																blnAplicaDscto = Boolean.TRUE;
																m++;
															}
														}
														else if(dto.getBloqueo().getTsFechaFin() != null)
														{											
															//BF bloqueo fecha fin											
															Date dateBloqueFin  = new Date(dto.getBloqueo().getTsFechaFin().getTime());											
															String fechaBloqueFin = sdf.format(dateBloqueFin);
															intBloqueoFechaFin = Integer.parseInt(fechaBloqueFin);	
															if(intFechaActual < intBloqueoFechaFin && intFechaActual > intBloqueoFechaInicio)
															{
																blnAplicaDscto = Boolean.FALSE;
															}
															else
															{
																blnAplicaDscto = Boolean.TRUE;
																m++;
															}
														}
													}
												}
												else
												{
													blnAplicaDscto = Boolean.TRUE;
													m++;
												}
											}
											else
											{
												blnAplicaDscto = Boolean.FALSE;
											}
										}
										
										totalMontoConcepto = new BigDecimal(0);										
										List<CuentaConceptoDetalle> listaConceptoDetalle = null;
										conceptoDetalleTemp = null;
										conceptoDetalleTemp = new CuentaConceptoDetalle();
										conceptoDetalleTemp.setId(new CuentaConceptoDetalleId());
										conceptoDetalleTemp.getId().setIntPersEmpresaPk(lSocioEstructura.getId().getIntIdEmpresa());
										conceptoDetalleTemp.getId().setIntCuentaPk(dto.getId().getIntCuentaPk());
										conceptoDetalleTemp.setIntParaTipoConceptoCod(Constante.CAPTACION_APORTACIONES);
										listaConceptoDetalle = conceptoFacade.getCuentaConceptoDetallePorPKCuentaYTipoConcepto(conceptoDetalleTemp);
										if(listaConceptoDetalle != null && !listaConceptoDetalle.isEmpty())
										{
											for(CuentaConceptoDetalle xx: listaConceptoDetalle)
											{
												List<ConceptoPago> listaConceptoPago = null;
												listaConceptoPago = conceptoFacade.getListaConceptoPagoPorCuentaConceptoDet(xx.getId());
												if(listaConceptoPago != null && !listaConceptoPago.isEmpty())
												{													
													for(ConceptoPago conceptoPago : listaConceptoPago)
													{
														if(conceptoPago.getIntPeriodo().compareTo(lItemPlanilla.getIntPeriodo()) == 0)
														{
															if(conceptoPago.getBdMontoSaldo().compareTo(new BigDecimal(0))==0){
																totalMontoConcepto = conceptoPago.getBdMontoPago();															
															}else{
																totalMontoConcepto = conceptoPago.getBdMontoSaldo();																
															}
															blnEncontro = Boolean.TRUE;
															break;															
														}
													}
													if(!blnEncontro)
													{
														totalMontoConcepto =  dto.getDetalle().getBdMontoConcepto();
													}
												}else
												{
													totalMontoConcepto = dto.getDetalle().getBdMontoConcepto();
												}
											}
										}
										//if(blnAplicaDscto)
										//{
											lEnvioconcepto = new Envioconcepto();
											lEnvioconcepto.setId(new EnvioconceptoId());
											if(lItemPlanilla.getBlnCartaAutorizacion() != null)
											{
												if(lItemPlanilla.getBlnCartaAutorizacion())
												{
													lEnvioconcepto.setIntIndicienpor(1);
												}else if(!lItemPlanilla.getBlnCartaAutorizacion())
												{
													lEnvioconcepto.setIntIndicienpor(0);
												}
											}
											if(lItemPlanilla.getBlnDJUD() != null)
											{
												if(lItemPlanilla.getBlnDJUD())
												{
													lEnvioconcepto.setIntIndidescjudi(1);	
												}
												else if(!lItemPlanilla.getBlnDJUD())
												{
													lEnvioconcepto.setIntIndidescjudi(0);
												}
											}
											if(lItemPlanilla.getBlnLIC() != null)
											{
												if(lItemPlanilla.getBlnLIC())
												{
													lEnvioconcepto.setIntIndilicencia(1);	
												}else if(!lItemPlanilla.getBlnLIC())
												{
													lEnvioconcepto.setIntIndilicencia(0);
												}
											}																						
											lEnvioconcepto.getId().setIntEmpresacuentaPk(lSocioEstructura.getId().getIntIdEmpresa());
											lEnvioconcepto.setIntPeriodoplanilla(lItemPlanilla.getIntPeriodo());
											lEnvioconcepto.setIntCuentaPk(dto.getId().getIntCuentaPk());
											lEnvioconcepto.setIntItemcuentaconcepto(dto.getId().getIntItemCuentaConcepto());
											lEnvioconcepto.setIntTipoconceptogeneralCod(Constante.PARAM_T_TIPOCONCEPTOGENERAL_APORTACION);
											lEnvioconcepto.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);											
											if(blnAplicaDscto){
												bdDescuento = bdDescuento.add(totalMontoConcepto);
												lEnvioconcepto.setBdMontoconcepto(totalMontoConcepto);
											}else{
												lEnvioconcepto.setBdMontoconcepto(new BigDecimal(0));
											}
											//bdDescuento = bdDescuento.add(totalMontoConcepto);
											//lEnvioconcepto.setBdMontoconcepto(totalMontoConcepto);
											lItemPlanilla.getListaEnvioConcepto().add(lEnvioconcepto);
											log.info("montoAporte="+totalMontoConcepto);
										//}
									}									
																		
								}
								else
								{	
									log.info("CAPTACION_APORTACIONES ACUMULATIVO");
									if(dto.getDetalle().getTsInicio() != null)
									{
										if(dto.getDetalle().getTsInicio() != null)
										{
											Date dateDetalleInicio  			= new Date(dto.getDetalle().getTsInicio().getTime());												
											String fechaDetalleInicio 			= sdf.format(dateDetalleInicio);
											intDetalleIncio 					= Integer.parseInt(fechaDetalleInicio);
												 
												if(dto.getDetalle().getTsFin() == null)
												{
													if(intDetalleIncio.compareTo(intFechaActual) == 0 || intDetalleIncio.compareTo(intFechaActual) == -1)
													{
														if(dto.getBloqueo() != null)
														{	
															//BI bloqueo fecha de inicio
															Date dateBloqueInicio       = new Date(dto.getBloqueo().getTsFechaInicio().getTime()); 									
															String fechaBloqueInicio 	= sdf.format(dateBloqueInicio);
															intBloqueoFechaInicio		 = Integer.parseInt(fechaBloqueInicio);			
															if(dto.getBloqueo().getIntItemBloqueoCuenta()== null)
															{ 
																blnAplicaDscto = Boolean.TRUE;
																m++;
															}
															else
															{
																	if(dto.getBloqueo().getTsFechaFin() == null)
																	{	
																		if(intBloqueoFechaInicio < intFechaActual)
																		{
																		blnAplicaDscto = Boolean.FALSE;
																		m++;
																		}
																		else if(intFechaActual < intBloqueoFechaInicio)
																		{
																			blnAplicaDscto = Boolean.TRUE;
																			m++;
																		}
																	}
																	else
																	{
																		Date dateBloqueFin  = new Date(dto.getBloqueo().getTsFechaFin().getTime());											
																		String fechaBloqueFin = sdf.format(dateBloqueFin);
																		intBloqueoFechaFin = Integer.parseInt(fechaBloqueFin);					
																		if(intFechaActual < intBloqueoFechaFin && intFechaActual > intBloqueoFechaInicio)
																		{
																			blnAplicaDscto = Boolean.FALSE;
																		}
																		else
																		{
																			blnAplicaDscto = Boolean.TRUE;
																			m++;
																		}										
																	}
																}
														}
														else
														{
															blnAplicaDscto = Boolean.TRUE;
															m++;
														}
													}
												}
												else
												{
													Date dateDetalleFin      	= new Date(dto.getDetalle().getTsFin().getTime()); 							
													String fechaDetalleFin 		= sdf.format(dateDetalleFin);
													intDetalleFin 				= Integer.parseInt(fechaDetalleFin);
													if(intFechaActual< intDetalleFin && intDetalleIncio < intFechaActual)
													{
														if(dto.getBloqueo() != null)
														{																														
															if(dto.getBloqueo().getIntItemBloqueoCuenta()== null)
															{ 
																blnAplicaDscto = Boolean.TRUE;
																m++;
															}
															else
															{
																if(dto.getBloqueo().getTsFechaFin() == null)
																{	
																	if(intBloqueoFechaInicio < intFechaActual)
																	{
																		blnAplicaDscto = Boolean.FALSE;
																	}	
																	else if(intBloqueoFechaInicio > intFechaActual)
																	{
																		blnAplicaDscto = Boolean.TRUE;
																		m++;
																	}
																}
																else if(dto.getBloqueo().getTsFechaFin() != null)
																{											
																	//BF bloqueo fecha fin											
																	Date dateBloqueFin  = new Date(dto.getBloqueo().getTsFechaFin().getTime());											
																	String fechaBloqueFin = sdf.format(dateBloqueFin);
																	intBloqueoFechaFin = Integer.parseInt(fechaBloqueFin);	
																	if(intFechaActual < intBloqueoFechaFin && intFechaActual > intBloqueoFechaInicio)
																	{
																		blnAplicaDscto = Boolean.FALSE;
																	}
																	else
																	{
																		blnAplicaDscto = Boolean.TRUE;
																		m++;
																	}
																}
															}
														}
														else
														{
															blnAplicaDscto = Boolean.TRUE;
															m++;
														}
													}
													else
													{
														blnAplicaDscto = Boolean.FALSE;
													}
												}
											}
									}
																	
									totalMontoConcepto   = new BigDecimal(0);									
									List<CuentaConceptoDetalle> listaConceptoDetalle= null;
									//conceptoPago
																		
										conceptoDetalleTemp = new CuentaConceptoDetalle();
										conceptoDetalleTemp.setId(new CuentaConceptoDetalleId());
										conceptoDetalleTemp.getId().setIntPersEmpresaPk(lSocioEstructura.getId().getIntIdEmpresa());
										conceptoDetalleTemp.getId().setIntCuentaPk(dto.getId().getIntCuentaPk());
										conceptoDetalleTemp.setIntParaTipoConceptoCod(Constante.CAPTACION_APORTACIONES); 									
										listaConceptoDetalle = conceptoFacade.getCuentaConceptoDetallePorPKCuentaYTipoConcepto(conceptoDetalleTemp);
										if(listaConceptoDetalle != null && !listaConceptoDetalle.isEmpty())
										{
											for(CuentaConceptoDetalle ccDetalle: listaConceptoDetalle)
											{
												List<ConceptoPago> listaConceptoPago = null;
												listaConceptoPago = conceptoFacade.getListaConceptoPagoPorCuentaConceptoDet(ccDetalle.getId());
												
												if(listaConceptoPago != null && !listaConceptoPago.isEmpty())
												{
													BigDecimal bdSumaPorPeriodo = BigDecimal.ZERO;
													for (ConceptoPago conceptoPago : listaConceptoPago)
													{														
														if(conceptoPago.getIntPeriodo().compareTo(lItemPlanilla.getIntPeriodo()) == -1)
														{
															bdSumaPorPeriodo = bdSumaPorPeriodo.add(conceptoPago.getBdMontoSaldo());
														}
														else if(conceptoPago.getIntPeriodo().compareTo(lItemPlanilla.getIntPeriodo()) == 0)
														{
															if(conceptoPago.getBdMontoSaldo().compareTo(new BigDecimal(0)) == 0){
																bdSumaPorPeriodo = bdSumaPorPeriodo.add(conceptoPago.getBdMontoPago());	
															}else{
																bdSumaPorPeriodo = bdSumaPorPeriodo.add(conceptoPago.getBdMontoSaldo());	
															}															
															blnEncontroAcumulativo = Boolean.TRUE;															
														}															
													}
													if(!blnEncontroAcumulativo)
													{
														bdSumaPorPeriodo = ccDetalle.getBdMontoConcepto();
													}
													
													totalMontoConcepto = bdSumaPorPeriodo;													
												}
												else
												{
													totalMontoConcepto =  ccDetalle.getBdMontoConcepto();													
												}												
											}																							
										}
									
									//if(blnAplicaDscto)
										//{
												lEnvioconcepto = new Envioconcepto();
												if(lItemPlanilla.getBlnCartaAutorizacion() != null)
												{
													if(lItemPlanilla.getBlnCartaAutorizacion())
													{
														lEnvioconcepto.setIntIndicienpor(1);
													}else if(!lItemPlanilla.getBlnCartaAutorizacion())
													{
														lEnvioconcepto.setIntIndicienpor(0);
													}
												}
												if(lItemPlanilla.getBlnDJUD() != null)
												{
													if(lItemPlanilla.getBlnDJUD())
													{
														lEnvioconcepto.setIntIndidescjudi(1);	
													}
													else if(!lItemPlanilla.getBlnDJUD())
													{
														lEnvioconcepto.setIntIndidescjudi(0);
													}
												}
												if(lItemPlanilla.getBlnLIC() != null)
												{
													if(lItemPlanilla.getBlnLIC())
													{
														lEnvioconcepto.setIntIndilicencia(1);	
													}else if(!lItemPlanilla.getBlnLIC())
													{
														lEnvioconcepto.setIntIndilicencia(0);
													}
												}								
												lEnvioconcepto.setId(new EnvioconceptoId());
												lEnvioconcepto.getId().setIntEmpresacuentaPk(lSocioEstructura.getId().getIntIdEmpresa());
												lEnvioconcepto.setIntPeriodoplanilla(lItemPlanilla.getIntPeriodo());
												lEnvioconcepto.setIntCuentaPk(dto.getId().getIntCuentaPk());
												lEnvioconcepto.setIntItemcuentaconcepto(dto.getId().getIntItemCuentaConcepto());												
												lEnvioconcepto.setIntTipoconceptogeneralCod(Constante.PARAM_T_TIPOCONCEPTOGENERAL_APORTACION);
												lEnvioconcepto.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);											
												if(blnAplicaDscto)
												{
													bdDescuento = bdDescuento.add(totalMontoConcepto);
													lEnvioconcepto.setBdMontoconcepto(totalMontoConcepto);
												}else{
													lEnvioconcepto.setBdMontoconcepto(new BigDecimal(0));
												}
												
												lItemPlanilla.getListaEnvioConcepto().add(lEnvioconcepto);
										//}
									}
							}  

						else if(dto.getDetalle().getIntParaTipoConceptoCod().compareTo(Constante.CAPTACION_FDO_RETIRO) == 0 && n<1)
						{
							log.info("CAPTACION_FDO_RETIRO");	
							if(dto.getDetalle().getIntParaTipoDescuentoCod().compareTo(Constante.PARAM_T_TIPODSCTOAPORT_CANCELATORIO) == 0)
								{
									log.info("CAPTACION_FDO_RETIRO CANCELATORIO");
									if(dto.getDetalle().getTsInicio() != null)
									{
										Date dateDetalleInicio  			= new Date(dto.getDetalle().getTsInicio().getTime());												
										String fechaDetalleInicio 			= sdf.format(dateDetalleInicio);
										intDetalleIncio 					= Integer.parseInt(fechaDetalleInicio);
											 
											if(dto.getDetalle().getTsFin() == null)
											{
												if(intDetalleIncio.compareTo(intFechaActual) == 0 || intDetalleIncio.compareTo(intFechaActual) == -1)
												{
													if(dto.getBloqueo() != null)
													{	
														//BI bloqueo fecha de inicio
														Date dateBloqueInicio       = new Date(dto.getBloqueo().getTsFechaInicio().getTime()); 									
														String fechaBloqueInicio 	= sdf.format(dateBloqueInicio);
														intBloqueoFechaInicio		 = Integer.parseInt(fechaBloqueInicio);			
														if(dto.getBloqueo().getIntItemBloqueoCuenta()== null)
														{ 
															blnAplicaDscto = Boolean.TRUE;
															n++;
														}
														else
														{
																if(dto.getBloqueo().getTsFechaFin() == null)
																{	
																	if(intBloqueoFechaInicio < intFechaActual)
																	{
																	blnAplicaDscto = Boolean.FALSE;
																	n++;
																	}
																	else if(intFechaActual < intBloqueoFechaInicio)
																	{
																		blnAplicaDscto = Boolean.TRUE;
																		n++;
																	}
																}
																else
																{
																	Date dateBloqueFin  = new Date(dto.getBloqueo().getTsFechaFin().getTime());											
																	String fechaBloqueFin = sdf.format(dateBloqueFin);
																	intBloqueoFechaFin = Integer.parseInt(fechaBloqueFin);					
																	if(intFechaActual < intBloqueoFechaFin && intFechaActual > intBloqueoFechaInicio)
																	{
																		blnAplicaDscto = Boolean.FALSE;
																	}
																	else
																	{
																		blnAplicaDscto = Boolean.TRUE;
																		n++;
																	}										
																}
														}
													}
													else
													{
														blnAplicaDscto = Boolean.TRUE;
														n++;
													}
												}
											}
											else
											{
												Date dateDetalleFin      	= new Date(dto.getDetalle().getTsFin().getTime()); 							
												String fechaDetalleFin 		= sdf.format(dateDetalleFin);
												intDetalleFin 				= Integer.parseInt(fechaDetalleFin);
												if(intFechaActual< intDetalleFin && intDetalleIncio < intFechaActual)
												{
													if(dto.getBloqueo() != null)
													{																														
														if(dto.getBloqueo().getIntItemBloqueoCuenta()== null)
														{ 
															blnAplicaDscto = Boolean.TRUE;
															n++;
														}
														else
														{
															if(dto.getBloqueo().getTsFechaFin() == null)
															{	
																if(intBloqueoFechaInicio < intFechaActual)
																{
																	blnAplicaDscto = Boolean.FALSE;
																}
																else if(intBloqueoFechaInicio > intFechaActual)
																{
																	blnAplicaDscto = Boolean.TRUE;
																	n++;
																}
															}
															else if(dto.getBloqueo().getTsFechaFin() != null)
															{											
																//BF bloqueo fecha fin											
																Date dateBloqueFin  = new Date(dto.getBloqueo().getTsFechaFin().getTime());											
																String fechaBloqueFin = sdf.format(dateBloqueFin);
																intBloqueoFechaFin = Integer.parseInt(fechaBloqueFin);	
																if(intFechaActual < intBloqueoFechaFin && intFechaActual > intBloqueoFechaInicio)
																{
																	blnAplicaDscto = Boolean.FALSE;
																}
																else
																{
																	blnAplicaDscto = Boolean.TRUE;
																	n++;
																}
															}
														}
													}
													else
													{
														blnAplicaDscto = Boolean.TRUE;
														n++;
													}
												}
												else
												{
													blnAplicaDscto = Boolean.FALSE;
												}
											}
										}									
									
									totalMontoConcepto = new BigDecimal(0);									
									List<CuentaConceptoDetalle> listaConceptoDetalle = null;
									
									conceptoDetalleTemp = new CuentaConceptoDetalle();
									conceptoDetalleTemp.setId(new CuentaConceptoDetalleId());
									conceptoDetalleTemp.getId().setIntPersEmpresaPk(lSocioEstructura.getId().getIntIdEmpresa());
									conceptoDetalleTemp.getId().setIntCuentaPk(dto.getId().getIntCuentaPk());
									conceptoDetalleTemp.setIntParaTipoConceptoCod(Constante.CAPTACION_FDO_RETIRO);
									listaConceptoDetalle = conceptoFacade.getCuentaConceptoDetallePorPKCuentaYTipoConcepto(conceptoDetalleTemp);
									if(listaConceptoDetalle != null && !listaConceptoDetalle.isEmpty())
									{
										for(CuentaConceptoDetalle xx: listaConceptoDetalle)
										{
											List<ConceptoPago> listaConceptoPago = null;
											listaConceptoPago = conceptoFacade.getListaConceptoPagoPorCuentaConceptoDet(xx.getId());
											if(listaConceptoPago != null && !listaConceptoPago.isEmpty())
											{													
												for(ConceptoPago conceptoPago : listaConceptoPago)
												{
													if(conceptoPago.getIntPeriodo().compareTo(lItemPlanilla.getIntPeriodo()) == 0)
													{
														totalMontoConcepto = conceptoPago.getBdMontoSaldo();
														blnEncontro = Boolean.TRUE;
														break;
													}
												}
												if(!blnEncontro)
												{
													totalMontoConcepto =  dto.getDetalle().getBdMontoConcepto();
												}
											}else
											{
												totalMontoConcepto = dto.getDetalle().getBdMontoConcepto();
											}
										}
									}
									
									//if(blnAplicaDscto)
									//{
										lEnvioconcepto = new Envioconcepto();
										if(lItemPlanilla.getBlnCartaAutorizacion() != null)
										{
											if(lItemPlanilla.getBlnCartaAutorizacion())
											{
												lEnvioconcepto.setIntIndicienpor(1);
											}else if(!lItemPlanilla.getBlnCartaAutorizacion())
											{
												lEnvioconcepto.setIntIndicienpor(0);
											}
										}
										if(lItemPlanilla.getBlnDJUD() != null)
										{
											if(lItemPlanilla.getBlnDJUD())
											{
												lEnvioconcepto.setIntIndidescjudi(1);	
											}
											else if(!lItemPlanilla.getBlnDJUD())
											{
												lEnvioconcepto.setIntIndidescjudi(0);
											}
										}
										if(lItemPlanilla.getBlnLIC() != null)
										{
											if(lItemPlanilla.getBlnLIC())
											{
												lEnvioconcepto.setIntIndilicencia(1);	
											}else if(!lItemPlanilla.getBlnLIC())
											{
												lEnvioconcepto.setIntIndilicencia(0);
											}
										}								
										lEnvioconcepto.setId(new EnvioconceptoId());
										lEnvioconcepto.getId().setIntEmpresacuentaPk(lSocioEstructura.getId().getIntIdEmpresa());
										lEnvioconcepto.setIntPeriodoplanilla(lItemPlanilla.getIntPeriodo());
										lEnvioconcepto.setIntCuentaPk(dto.getId().getIntCuentaPk());
										lEnvioconcepto.setIntItemcuentaconcepto(dto.getId().getIntItemCuentaConcepto());
										lEnvioconcepto.setIntTipoconceptogeneralCod(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO);
										lEnvioconcepto.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);									
										if(blnAplicaDscto)
										{
											bdDescuento = bdDescuento.add(totalMontoConcepto);																	
											lEnvioconcepto.setBdMontoconcepto(totalMontoConcepto);	
										}else{
											lEnvioconcepto.setBdMontoconcepto(new BigDecimal(0));	
										}
										
										lItemPlanilla.getListaEnvioConcepto().add(lEnvioconcepto);
										log.info("monto fdoRetiro cancelatorio ="+dto.getDetalle().getBdMontoConcepto());
									//}
									//fdodeRetiroseaAcumulativo
								}
								else
								{
									log.info("CAPTACION_FDO_RETIRO ACUMULATIVO");
									if(dto.getDetalle().getTsInicio() != null)
									{
										if(dto.getDetalle().getTsInicio() != null)
										{
											Date dateDetalleInicio  			= new Date(dto.getDetalle().getTsInicio().getTime());												
											String fechaDetalleInicio 			= sdf.format(dateDetalleInicio);
											intDetalleIncio 					= Integer.parseInt(fechaDetalleInicio);
												 
												if(dto.getDetalle().getTsFin() == null)
												{
													if(intDetalleIncio.compareTo(intFechaActual) == 0 || intDetalleIncio.compareTo(intFechaActual) == -1)
													{
														if(dto.getBloqueo() != null)
														{	
															//BI bloqueo fecha de inicio
															Date dateBloqueInicio       = new Date(dto.getBloqueo().getTsFechaInicio().getTime()); 									
															String fechaBloqueInicio 	= sdf.format(dateBloqueInicio);
															intBloqueoFechaInicio		= Integer.parseInt(fechaBloqueInicio);			
															if(dto.getBloqueo().getIntItemBloqueoCuenta()== null)
															{ 
																blnAplicaDscto = Boolean.TRUE;
																n++;
															}
															else
															{
																	if(dto.getBloqueo().getTsFechaFin() == null)
																	{	
																		if(intBloqueoFechaInicio < intFechaActual)
																		{
																			blnAplicaDscto = Boolean.FALSE;
																			n++;
																		}
																		else if(intFechaActual < intBloqueoFechaInicio)
																		{
																			blnAplicaDscto = Boolean.TRUE;
																			n++;
																		}
																	}
																	else
																	{
																		Date dateBloqueFin  = new Date(dto.getBloqueo().getTsFechaFin().getTime());											
																		String fechaBloqueFin = sdf.format(dateBloqueFin);
																		intBloqueoFechaFin = Integer.parseInt(fechaBloqueFin);					
																		if(intFechaActual < intBloqueoFechaFin && intFechaActual > intBloqueoFechaInicio)
																		{
																			blnAplicaDscto = Boolean.FALSE;
																		}
																		else
																		{
																			blnAplicaDscto = Boolean.TRUE;
																			n++;
																		}										
																	}
															}
														}
														else
														{
															blnAplicaDscto = Boolean.TRUE;
															n++;
														}
													}
												}
												else
												{
													Date dateDetalleFin      	= new Date(dto.getDetalle().getTsFin().getTime()); 							
													String fechaDetalleFin 		= sdf.format(dateDetalleFin);
													intDetalleFin 				= Integer.parseInt(fechaDetalleFin);
													if(intFechaActual< intDetalleFin && intDetalleIncio < intFechaActual)
													{
														if(dto.getBloqueo() != null)
														{																														
															if(dto.getBloqueo().getIntItemBloqueoCuenta()== null)
															{ 
																blnAplicaDscto = Boolean.TRUE;
																n++;
															}
															else
															{
																if(dto.getBloqueo().getTsFechaFin() == null)
																{	
																	if(intBloqueoFechaInicio < intFechaActual)
																	{
																		blnAplicaDscto = Boolean.FALSE;
																	}	
																	else if(intBloqueoFechaInicio > intFechaActual)
																	{
																		blnAplicaDscto = Boolean.TRUE;
																		n++;
																	}
																}
																else if(dto.getBloqueo().getTsFechaFin() != null)
																{											
																	//BF bloqueo fecha fin											
																	Date dateBloqueFin  	= new Date(dto.getBloqueo().getTsFechaFin().getTime());											
																	String fechaBloqueFin 	= sdf.format(dateBloqueFin);
																	intBloqueoFechaFin 		= Integer.parseInt(fechaBloqueFin);	
																	if(intFechaActual < intBloqueoFechaFin && intFechaActual > intBloqueoFechaInicio)
																	{
																		blnAplicaDscto = Boolean.FALSE;
																	}
																	else
																	{
																		blnAplicaDscto = Boolean.TRUE;
																		n++;
																	}
																}
															}
														}
														else
														{
															blnAplicaDscto = Boolean.TRUE;
															n++;
														}
													}
													else
													{
															blnAplicaDscto = Boolean.FALSE;
													}
												}
											}
									}
																		
									totalMontoConcepto   = new BigDecimal(0);									
									List<CuentaConceptoDetalle> listaccDetalle = null;
									//conceptoPago
																		
									conceptoDetalleTemp = new CuentaConceptoDetalle();
									conceptoDetalleTemp.setId(new CuentaConceptoDetalleId());
									conceptoDetalleTemp.getId().setIntPersEmpresaPk(lSocioEstructura.getId().getIntIdEmpresa());
									conceptoDetalleTemp.getId().setIntCuentaPk(dto.getId().getIntCuentaPk());
									conceptoDetalleTemp.setIntParaTipoConceptoCod(Constante.CAPTACION_FDO_RETIRO);
									listaccDetalle = conceptoFacade.getCuentaConceptoDetallePorPKCuentaYTipoConcepto(conceptoDetalleTemp);
									if(listaccDetalle != null && !listaccDetalle.isEmpty())
									{
										for(CuentaConceptoDetalle ccDetalle: listaccDetalle)
										{
											List<ConceptoPago> listaConceptoPago = null;
											listaConceptoPago = conceptoFacade.getListaConceptoPagoPorCuentaConceptoDet(ccDetalle.getId());
											
											if(listaConceptoPago != null && !listaConceptoPago.isEmpty())
											{
												BigDecimal bdSumaPorPeriodo = BigDecimal.ZERO;
												for (ConceptoPago conceptoPago : listaConceptoPago) 
												{
													if(conceptoPago.getIntPeriodo().compareTo(lItemPlanilla.getIntPeriodo()) == -1)
													{
														bdSumaPorPeriodo = bdSumaPorPeriodo.add(conceptoPago.getBdMontoSaldo());	
													}else if(conceptoPago.getIntPeriodo().compareTo(lItemPlanilla.getIntPeriodo()) == 0)
													{
														bdSumaPorPeriodo = bdSumaPorPeriodo.add(conceptoPago.getBdMontoSaldo());
														blnEncontroAcumulativo = Boolean.TRUE;
													}
												}
												if(!blnEncontroAcumulativo)
												{
													bdSumaPorPeriodo = 	bdSumaPorPeriodo.add(ccDetalle.getBdMontoConcepto());
												}
												
												totalMontoConcepto = bdSumaPorPeriodo;
											}
											else
											{
												totalMontoConcepto = ccDetalle.getBdMontoConcepto();												
											}											
										}																						
									}								 
										//if(blnAplicaDscto)
										//{
												lEnvioconcepto = new Envioconcepto();
												if(lItemPlanilla.getBlnCartaAutorizacion() != null)
												{
													if(lItemPlanilla.getBlnCartaAutorizacion())
													{
														lEnvioconcepto.setIntIndicienpor(1);
													}else if(!lItemPlanilla.getBlnCartaAutorizacion())
													{
														lEnvioconcepto.setIntIndicienpor(0);
													}
												}
												if(lItemPlanilla.getBlnDJUD() != null)
												{
													if(lItemPlanilla.getBlnDJUD())
													{
														lEnvioconcepto.setIntIndidescjudi(1);	
													}
													else if(!lItemPlanilla.getBlnDJUD())
													{
														lEnvioconcepto.setIntIndidescjudi(0);
													}
												}
												if(lItemPlanilla.getBlnLIC() != null)
												{
													if(lItemPlanilla.getBlnLIC())
													{
														lEnvioconcepto.setIntIndilicencia(1);	
													}else if(!lItemPlanilla.getBlnLIC())
													{
														lEnvioconcepto.setIntIndilicencia(0);
													}
												}								
												lEnvioconcepto.setId(new EnvioconceptoId());
												lEnvioconcepto.getId().setIntEmpresacuentaPk(lSocioEstructura.getId().getIntIdEmpresa());
												lEnvioconcepto.setIntPeriodoplanilla(lItemPlanilla.getIntPeriodo());
												lEnvioconcepto.setIntCuentaPk(dto.getId().getIntCuentaPk());
												lEnvioconcepto.setIntItemcuentaconcepto(dto.getId().getIntItemCuentaConcepto());												
												lEnvioconcepto.setIntTipoconceptogeneralCod(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO);
												lEnvioconcepto.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);											
												if(blnAplicaDscto)
												{
													bdDescuento = bdDescuento.add(totalMontoConcepto);
													lEnvioconcepto.setBdMontoconcepto(totalMontoConcepto);	
												}else{
													lEnvioconcepto.setBdMontoconcepto(new BigDecimal(0));	
												}												
												
												lItemPlanilla.getListaEnvioConcepto().add(lEnvioconcepto);
										//}
									}
							} 
						 
							else  if(dto.getId().getIntItemCuentaConcepto().compareTo(Constante.CAPTACION_FDO_SEPELIO) == 0 && o<1)
							{
									log.info("CAPTACION_FDO_SEPELIO");
									if(dto.getDetalle().getIntParaTipoDescuentoCod().compareTo(Constante.PARAM_T_TIPODSCTOAPORT_ACUMULATIVO) == 0)
										{
											log.info("CAPTACION_FDO_SEPELIO ACUMULATIVO");
											if(dto.getDetalle().getTsInicio() != null)
												{
														Date dateDetalleInicio  			= new Date(dto.getDetalle().getTsInicio().getTime());												
														String fechaDetalleInicio 			= sdf.format(dateDetalleInicio);
														 intDetalleIncio 					= Integer.parseInt(fechaDetalleInicio);
														 
														if(dto.getDetalle().getTsFin() == null)
														{
															if(intDetalleIncio.compareTo(intFechaActual) == 0 || intDetalleIncio.compareTo(intFechaActual) == -1)
															{
																if(dto.getBloqueo() != null)
																{	
																	//BI bloqueo fecha de inicio
																	Date dateBloqueInicio       = new Date(dto.getBloqueo().getTsFechaInicio().getTime()); 									
																	String fechaBloqueInicio 	= sdf.format(dateBloqueInicio);
																	intBloqueoFechaInicio		 = Integer.parseInt(fechaBloqueInicio);			
																	if(dto.getBloqueo().getIntItemBloqueoCuenta()== null)
																	{ 
																		blnAplicaDscto = Boolean.TRUE;
																		o++;
																	}
																	else
																	{
																			if(dto.getBloqueo().getTsFechaFin() == null)
																			{	
																				if(intBloqueoFechaInicio < intFechaActual)
																				{
																					blnAplicaDscto = Boolean.FALSE;
																					o++;
																				}
																				else if(intFechaActual < intBloqueoFechaInicio)
																				{
																					blnAplicaDscto = Boolean.TRUE;
																					o++;
																				}
																			}
																			else
																			{
																				Date dateBloqueFin  = new Date(dto.getBloqueo().getTsFechaFin().getTime());											
																				String fechaBloqueFin = sdf.format(dateBloqueFin);
																				intBloqueoFechaFin = Integer.parseInt(fechaBloqueFin);					
																				if(intFechaActual < intBloqueoFechaFin && intFechaActual > intBloqueoFechaInicio)
																				{
																					blnAplicaDscto = Boolean.FALSE;
																				}
																				else
																				{
																					blnAplicaDscto = Boolean.TRUE;
																					o++;
																				}									
																			}
																	}
																}
																else
																{
																	blnAplicaDscto = Boolean.TRUE;
																	o++;
																}
															}
														}
														else
														{
															Date dateDetalleFin      	= new Date(dto.getDetalle().getTsFin().getTime()); 							
															String fechaDetalleFin 		= sdf.format(dateDetalleFin);
															intDetalleFin 				= Integer.parseInt(fechaDetalleFin);
															if(intFechaActual< intDetalleFin && intDetalleIncio < intFechaActual)
															{
																if(dto.getBloqueo() != null)
																{																														
																	if(dto.getBloqueo().getIntItemBloqueoCuenta()== null)
																	{ 
																		blnAplicaDscto = Boolean.TRUE;
																		o++;
																	}
																	else
																	{
																		if(dto.getBloqueo().getTsFechaFin() == null)
																		{	
																			if(intBloqueoFechaInicio < intFechaActual)
																			{
																				blnAplicaDscto = Boolean.FALSE;
																			}	
																			else if(intBloqueoFechaInicio > intFechaActual)
																			{
																				blnAplicaDscto = Boolean.TRUE;
																				o++;
																			}
																		}
																		else if(dto.getBloqueo().getTsFechaFin() != null)
																		{											
																			//BF bloqueo fecha fin											
																			Date dateBloqueFin  	= new Date(dto.getBloqueo().getTsFechaFin().getTime());											
																			String fechaBloqueFin 	= sdf.format(dateBloqueFin);
																			intBloqueoFechaFin 		= Integer.parseInt(fechaBloqueFin);	
																			
																			if(intFechaActual < intBloqueoFechaFin && intFechaActual > intBloqueoFechaInicio)
																			{
																				blnAplicaDscto = Boolean.FALSE;
																			}
																			else
																			{
																				blnAplicaDscto = Boolean.TRUE;
																				o++;
																			}
																		}
																	}
																}
																else
																{
																	blnAplicaDscto = Boolean.TRUE;
																	o++;
																}
															}
															else
															{
																blnAplicaDscto = Boolean.FALSE;
															}
														}
													}										
																					
											totalMontoConcepto   = new BigDecimal(0);
											
											conceptoDetalleTemp = new CuentaConceptoDetalle();
											conceptoDetalleTemp.setId(new CuentaConceptoDetalleId());
											conceptoDetalleTemp.getId().setIntPersEmpresaPk(lSocioEstructura.getId().getIntIdEmpresa());
											conceptoDetalleTemp.getId().setIntCuentaPk(dto.getId().getIntCuentaPk());
											conceptoDetalleTemp.setIntParaTipoConceptoCod(Constante.CAPTACION_FDO_SEPELIO);
											
											CuentaConceptoId pId = new CuentaConceptoId();
											pId.setIntCuentaPk(dto.getId().getIntCuentaPk());
											pId.setIntItemCuentaConcepto(Constante.CAPTACION_FDO_SEPELIO);
											pId.setIntPersEmpresaPk(lSocioEstructura.getId().getIntIdEmpresa());
											//getListaCuentaConceptoPorPKCuenta
											listaCuentaConceptoDetalle = conceptoFacade.getCuentaConceptoDetallePorPKCuentaYTipoConcepto(conceptoDetalleTemp);
										
											//listaCuentaConceptoDetalle = conceptoFacade.getListaCuentaConceptoDetallePorCuentaConcepto(pId);
											
											if(listaCuentaConceptoDetalle != null && !listaCuentaConceptoDetalle.isEmpty())
											{
												log.debug("cantidad listaCuentaConceptoDetalle "+listaCuentaConceptoDetalle.size());
												java.sql.Timestamp tsHoy = UtilCobranza.obtieneFechaActualEnTimesTamp();
												java.sql.Date dateHoy = new java.sql.Date(tsHoy.getTime());
												
												for(CuentaConceptoDetalle cuentaConceptoDetalle: listaCuentaConceptoDetalle)
												{												
													if(cuentaConceptoDetalle.getTsFin() != null)
													{
														log.debug("cuentaConceptoDetalle.getTsFin() "+cuentaConceptoDetalle.getTsFin());
														
														if (UtilCobranza.esDentroRangoFechas(new Date(cuentaConceptoDetalle.getTsInicio().getTime()),new Date(cuentaConceptoDetalle.getTsFin().getTime()), dateHoy) )
														{
															log.debug("dentro del rango");
															conceptoDetalleTemp = cuentaConceptoDetalle;
														}
															
													}else
													{
														log.debug("no tengo fecha fin");
														if(dateHoy.after(new Date(cuentaConceptoDetalle.getTsInicio().getTime())))
														{
															conceptoDetalleTemp = cuentaConceptoDetalle;
															
														}
													}
													
												}
											}
											if(conceptoDetalleTemp != null && conceptoDetalleTemp.getTsInicio() != null)
											{	
												log.debug("conceptoDetalleTemp es diferente que null "+conceptoDetalleTemp.getTsInicio());
												List<ConceptoPago> listaConceptoPago = null;
												listaConceptoPago = conceptoFacade.getListaConceptoPagoPorCuentaConceptoDet(conceptoDetalleTemp.getId());
												
												if(listaConceptoPago != null && !listaConceptoPago.isEmpty())
												{	
													//log.info("listaConceptoPago es diferente que null");
													BigDecimal bdSumaPorPeriodo = BigDecimal.ZERO;
													for (ConceptoPago conceptoPago : listaConceptoPago)
													{
														if(conceptoPago.getIntPeriodo().compareTo(lItemPlanilla.getIntPeriodo())== -1)
														{
															bdSumaPorPeriodo = bdSumaPorPeriodo.add(conceptoPago.getBdMontoSaldo());
														}else if(conceptoPago.getIntPeriodo().compareTo(lItemPlanilla.getIntPeriodo())== 0)
														{
															bdSumaPorPeriodo = bdSumaPorPeriodo.add(conceptoPago.getBdMontoSaldo());
															blnEncontroAcumulativo = Boolean.TRUE;
														}
													}
													if(!blnEncontroAcumulativo)
													{
														bdSumaPorPeriodo = bdSumaPorPeriodo.add(conceptoDetalleTemp.getBdMontoConcepto());
													}				
													
													totalMontoConcepto = bdSumaPorPeriodo;
												}else
												{
													totalMontoConcepto = conceptoDetalleTemp.getBdMontoConcepto();
													
												}
												
											}
																			
											 
													//if(blnAplicaDscto)
													//{
														lEnvioconcepto = new Envioconcepto();
														if(lItemPlanilla.getBlnCartaAutorizacion() != null)
														{
															if(lItemPlanilla.getBlnCartaAutorizacion())
															{
																lEnvioconcepto.setIntIndicienpor(1);
															}else if(!lItemPlanilla.getBlnCartaAutorizacion())
															{
																lEnvioconcepto.setIntIndicienpor(0);
															}
														}
														if(lItemPlanilla.getBlnDJUD() != null)
														{
															if(lItemPlanilla.getBlnDJUD())
															{
																lEnvioconcepto.setIntIndidescjudi(1);	
															}
															else if(!lItemPlanilla.getBlnDJUD())
															{
																lEnvioconcepto.setIntIndidescjudi(0);
															}
														}
														if(lItemPlanilla.getBlnLIC() != null)
														{
															if(lItemPlanilla.getBlnLIC())
															{
																lEnvioconcepto.setIntIndilicencia(1);	
															}else if(!lItemPlanilla.getBlnLIC())
															{
																lEnvioconcepto.setIntIndilicencia(0);
															}
														}								
														lEnvioconcepto.setId(new EnvioconceptoId());
														lEnvioconcepto.getId().setIntEmpresacuentaPk(lSocioEstructura.getId().getIntIdEmpresa());
														lEnvioconcepto.setIntPeriodoplanilla(lItemPlanilla.getIntPeriodo());
														lEnvioconcepto.setIntCuentaPk(dto.getId().getIntCuentaPk());
														lEnvioconcepto.setIntItemcuentaconcepto(dto.getId().getIntItemCuentaConcepto());
														lEnvioconcepto.setIntTipoconceptogeneralCod(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDOSEPELIO);
														lEnvioconcepto.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);											
														if(blnAplicaDscto)
														{
															bdDescuento = bdDescuento.add(totalMontoConcepto);
															lEnvioconcepto.setBdMontoconcepto(totalMontoConcepto);
														}else{
															lEnvioconcepto.setBdMontoconcepto(new BigDecimal(0));
														}
															
														
														lItemPlanilla.getListaEnvioConcepto().add(lEnvioconcepto);
														log.info("totalMontoConcepto="+totalMontoConcepto);
													//}
										
										}
										else
										{
											log.info("CAPTACION_FDO_SEPELIO CANCELATORIO");
											if(dto.getDetalle().getTsInicio() != null)
											{
												Date dateDetalleInicio  			= new Date(dto.getDetalle().getTsInicio().getTime());												
												String fechaDetalleInicio 			= sdf.format(dateDetalleInicio);
												intDetalleIncio 					= Integer.parseInt(fechaDetalleInicio);
													 
													if(dto.getDetalle().getTsFin() == null)
													{
														if(intDetalleIncio.compareTo(intFechaActual) == 0 || intDetalleIncio.compareTo(intFechaActual) == -1)
														{
															if(dto.getBloqueo() != null)
															{	
																//BI bloqueo fecha de inicio
																Date dateBloqueInicio        = new Date(dto.getBloqueo().getTsFechaInicio().getTime()); 									
																String fechaBloqueInicio 	 = sdf.format(dateBloqueInicio);
																intBloqueoFechaInicio		 = Integer.parseInt(fechaBloqueInicio);	
																
																if(dto.getBloqueo().getIntItemBloqueoCuenta()== null)
																{ 
																	blnAplicaDscto = Boolean.TRUE;
																	o++;
																}
																else
																{
																		if(dto.getBloqueo().getTsFechaFin() == null)
																		{	
																			if(intBloqueoFechaInicio < intFechaActual)
																			{
																				blnAplicaDscto = Boolean.FALSE;
																				o++;
																			}
																			else if(intFechaActual < intBloqueoFechaInicio)
																			{
																				blnAplicaDscto = Boolean.TRUE;
																				o++;
																			}
																		}
																		else
																		{
																			Date dateBloqueFin  = new Date(dto.getBloqueo().getTsFechaFin().getTime());											
																			String fechaBloqueFin = sdf.format(dateBloqueFin);
																			intBloqueoFechaFin = Integer.parseInt(fechaBloqueFin);					
																			if(intFechaActual < intBloqueoFechaFin && intFechaActual > intBloqueoFechaInicio)
																			{
																				blnAplicaDscto = Boolean.FALSE;
																			}
																			else
																			{
																				blnAplicaDscto = Boolean.TRUE;
																				o++;
																			}										
																		}
																}
															}
															else
															{
																blnAplicaDscto = Boolean.TRUE;
																o++;
															}
														}
													}
													else
													{
														Date dateDetalleFin      	= new Date(dto.getDetalle().getTsFin().getTime()); 							
														String fechaDetalleFin 		= sdf.format(dateDetalleFin);
														intDetalleFin 				= Integer.parseInt(fechaDetalleFin);
														if(intFechaActual< intDetalleFin && intDetalleIncio < intFechaActual)
														{
															if(dto.getBloqueo() != null)
															{																														
																if(dto.getBloqueo().getIntItemBloqueoCuenta()== null)
																{ 
																	blnAplicaDscto = Boolean.TRUE;
																	o++;
																}
																else
																{
																	if(dto.getBloqueo().getTsFechaFin() == null)
																	{	
																		if(intBloqueoFechaInicio < intFechaActual)
																		{
																			blnAplicaDscto = Boolean.FALSE;
																		}
																		else if(intBloqueoFechaInicio > intFechaActual)
																		{
																			blnAplicaDscto = Boolean.TRUE;
																			o++;
																		}
																	}
																	else if(dto.getBloqueo().getTsFechaFin() != null)
																	{											
																		//BF bloqueo fecha fin											
																		Date dateBloqueFin  = new Date(dto.getBloqueo().getTsFechaFin().getTime());											
																		String fechaBloqueFin = sdf.format(dateBloqueFin);
																		intBloqueoFechaFin = Integer.parseInt(fechaBloqueFin);	
																		if(intFechaActual < intBloqueoFechaFin && intFechaActual > intBloqueoFechaInicio)
																		{
																			blnAplicaDscto = Boolean.FALSE;
																		}
																		else
																		{
																			blnAplicaDscto = Boolean.TRUE;
																			o++;
																		}
																	}
																}
															}
															else
															{
																blnAplicaDscto = Boolean.TRUE;
																o++;
															}
														}
														else
														{
															blnAplicaDscto = Boolean.FALSE;
														}
													}
												}
											totalMontoConcepto = new BigDecimal(0);
											
											List<CuentaConceptoDetalle> listaConceptoDetalle = null;
											
											conceptoDetalleTemp = new CuentaConceptoDetalle();
											conceptoDetalleTemp.setId(new CuentaConceptoDetalleId());
											conceptoDetalleTemp.getId().setIntPersEmpresaPk(lSocioEstructura.getId().getIntIdEmpresa());
											conceptoDetalleTemp.getId().setIntCuentaPk(dto.getId().getIntCuentaPk());
											conceptoDetalleTemp.setIntParaTipoConceptoCod(Constante.CAPTACION_FDO_SEPELIO);
											listaConceptoDetalle = conceptoFacade.getCuentaConceptoDetallePorPKCuentaYTipoConcepto(conceptoDetalleTemp);
											
											if(listaConceptoDetalle != null && !listaConceptoDetalle.isEmpty())
											{
												for(CuentaConceptoDetalle xx: listaConceptoDetalle)
												{
													List<ConceptoPago> listaConceptoPago = null;
													listaConceptoPago = conceptoFacade.getListaConceptoPagoPorCuentaConceptoDet(xx.getId());
													if(listaConceptoPago != null && !listaConceptoPago.isEmpty())
													{													
														for(ConceptoPago conceptoPago : listaConceptoPago)
														{
															if(conceptoPago.getIntPeriodo().compareTo(lItemPlanilla.getIntPeriodo()) == 0)
															{
																totalMontoConcepto = conceptoPago.getBdMontoSaldo();
																blnEncontro = Boolean.TRUE;
																break;
															}
														}
														if(!blnEncontro)
														{
															totalMontoConcepto =  dto.getDetalle().getBdMontoConcepto();
														}
													}else
													{
														totalMontoConcepto = dto.getDetalle().getBdMontoConcepto();
													}
												}
											}
											
												//if(blnAplicaDscto)
												//{
													lEnvioconcepto = new Envioconcepto();
													if(lItemPlanilla.getBlnCartaAutorizacion() != null)
													{
														if(lItemPlanilla.getBlnCartaAutorizacion())
														{
															lEnvioconcepto.setIntIndicienpor(1);
														}else if(!lItemPlanilla.getBlnCartaAutorizacion())
														{
															lEnvioconcepto.setIntIndicienpor(0);
														}
													}
													if(lItemPlanilla.getBlnDJUD() != null)
													{
														if(lItemPlanilla.getBlnDJUD())
														{
															lEnvioconcepto.setIntIndidescjudi(1);	
														}
														else if(!lItemPlanilla.getBlnDJUD())
														{
															lEnvioconcepto.setIntIndidescjudi(0);
														}
													}
													if(lItemPlanilla.getBlnLIC() != null)
													{
														if(lItemPlanilla.getBlnLIC())
														{
															lEnvioconcepto.setIntIndilicencia(1);	
														}else if(!lItemPlanilla.getBlnLIC())
														{
															lEnvioconcepto.setIntIndilicencia(0);
														}
													}								
													lEnvioconcepto.setId(new EnvioconceptoId());
													lEnvioconcepto.getId().setIntEmpresacuentaPk(lSocioEstructura.getId().getIntIdEmpresa());
													lEnvioconcepto.setIntPeriodoplanilla(lItemPlanilla.getIntPeriodo());
													lEnvioconcepto.setIntCuentaPk(dto.getId().getIntCuentaPk());
													lEnvioconcepto.setIntItemcuentaconcepto(dto.getId().getIntItemCuentaConcepto());
													lEnvioconcepto.setIntTipoconceptogeneralCod(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDOSEPELIO);
													lEnvioconcepto.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);									
													if(blnAplicaDscto)
													{
														bdDescuento = bdDescuento.add(totalMontoConcepto);																
														lEnvioconcepto.setBdMontoconcepto(totalMontoConcepto);	
													}else{
														lEnvioconcepto.setBdMontoconcepto(new BigDecimal(0));
													}
													
													lItemPlanilla.getListaEnvioConcepto().add(lEnvioconcepto);
												//}
										}
									} 					 
							 
					else if(dto.getDetalle().getIntParaTipoConceptoCod().compareTo(Constante.CAPTACION_AHORROS) == 0 && p<1)
					{
								//log.info("CAPTACION_AHORROS");
								if(dto.getDetalle().getIntParaTipoDescuentoCod().compareTo(Constante.PARAM_T_TIPODSCTOAPORT_ACUMULATIVO) == 0)
								{
									log.info("CAPTACION_AHORROS ACUMULATIVO");
									if(dto.getDetalle().getTsInicio() != null)
									{
										if(dto.getDetalle().getTsInicio() != null)
										{
											Date dateDetalleInicio  			= new Date(dto.getDetalle().getTsInicio().getTime());												
											String fechaDetalleInicio 			= sdf.format(dateDetalleInicio);
											intDetalleIncio 					= Integer.parseInt(fechaDetalleInicio);
												 
												if(dto.getDetalle().getTsFin() == null)
												{
													if(intDetalleIncio.compareTo(intFechaActual) == 0 || intDetalleIncio.compareTo(intFechaActual) == -1)
													{
														if(dto.getBloqueo() != null)
														{	
															//BI bloqueo fecha de inicio
															Date dateBloqueInicio       = new Date(dto.getBloqueo().getTsFechaInicio().getTime()); 									
															String fechaBloqueInicio 	= sdf.format(dateBloqueInicio);
															intBloqueoFechaInicio		= Integer.parseInt(fechaBloqueInicio);	
															
															if(dto.getBloqueo().getIntItemBloqueoCuenta()== null)
															{ 
																blnAplicaDscto = Boolean.TRUE;
																p++;
															}
															else
															{
																	if(dto.getBloqueo().getTsFechaFin() == null)
																	{	
																		if(intBloqueoFechaInicio < intFechaActual)
																		{
																			blnAplicaDscto = Boolean.FALSE;
																			p++;
																		}
																		else if(intFechaActual < intBloqueoFechaInicio)
																		{
																			blnAplicaDscto = Boolean.TRUE;
																			p++;
																		}
																	}
																	else
																	{
																		Date dateBloqueFin  = new Date(dto.getBloqueo().getTsFechaFin().getTime());											
																		String fechaBloqueFin = sdf.format(dateBloqueFin);
																		intBloqueoFechaFin = Integer.parseInt(fechaBloqueFin);					
																		if(intFechaActual < intBloqueoFechaFin && intFechaActual > intBloqueoFechaInicio)
																		{
																			blnAplicaDscto = Boolean.FALSE;
																		}
																		else
																		{
																			blnAplicaDscto = Boolean.TRUE;
																			p++;
																		}										
																	}
															}
														}
														else
														{
															blnAplicaDscto = Boolean.TRUE;
															p++;
														}
													}
												}
												else
												{
													Date dateDetalleFin      	= new Date(dto.getDetalle().getTsFin().getTime()); 							
													String fechaDetalleFin 		= sdf.format(dateDetalleFin);
													intDetalleFin 				= Integer.parseInt(fechaDetalleFin);
													if(intFechaActual< intDetalleFin && intDetalleIncio < intFechaActual)
													{
														if(dto.getBloqueo() != null)
														{																														
															if(dto.getBloqueo().getIntItemBloqueoCuenta()== null)
															{ 
																blnAplicaDscto = Boolean.TRUE;
																p++;
															}
															else
															{
																if(dto.getBloqueo().getTsFechaFin() == null)
																{	
																	if(intBloqueoFechaInicio < intFechaActual)
																	{
																		blnAplicaDscto = Boolean.FALSE;
																	}	
																	else if(intBloqueoFechaInicio > intFechaActual)
																	{
																		blnAplicaDscto = Boolean.TRUE;
																		p++;
																	}
																}
																else if(dto.getBloqueo().getTsFechaFin() != null)
																{											
																	//BF bloqueo fecha fin											
																	Date dateBloqueFin  	= new Date(dto.getBloqueo().getTsFechaFin().getTime());											
																	String fechaBloqueFin 	= sdf.format(dateBloqueFin);
																	intBloqueoFechaFin 		= Integer.parseInt(fechaBloqueFin);
																	
																	if(intFechaActual < intBloqueoFechaFin && intFechaActual > intBloqueoFechaInicio)
																	{
																		blnAplicaDscto = Boolean.FALSE;
																	}
																	else
																	{
																		blnAplicaDscto = Boolean.TRUE;
																		p++;
																	}
																}
															}
														}
														else
														{
															blnAplicaDscto = Boolean.TRUE;
															p++;
														}
													}
													else
													{
														blnAplicaDscto = Boolean.FALSE;
													}
												}
											}
									}
																		
									totalMontoConcepto   = new BigDecimal(0);									
									List<CuentaConceptoDetalle> listaccDetalle = null;
									
									//conceptoPago
									conceptoDetalleTemp = new CuentaConceptoDetalle();
									conceptoDetalleTemp.setId(new CuentaConceptoDetalleId());
									conceptoDetalleTemp.getId().setIntPersEmpresaPk(lSocioEstructura.getId().getIntIdEmpresa());
									conceptoDetalleTemp.getId().setIntCuentaPk(dto.getId().getIntCuentaPk());
									conceptoDetalleTemp.setIntParaTipoConceptoCod(Constante.CAPTACION_AHORROS);
									listaccDetalle = conceptoFacade.getCuentaConceptoDetallePorPKCuentaYTipoConcepto(conceptoDetalleTemp);
									
									if(listaccDetalle != null && !listaccDetalle.isEmpty())
									{
										for(CuentaConceptoDetalle ccDetalle: listaccDetalle)
										{
											List<ConceptoPago> listaConceptoPago = null;
											listaConceptoPago = conceptoFacade.getListaConceptoPagoPorCuentaConceptoDet(ccDetalle.getId());
																						
											if(listaConceptoPago != null && !listaConceptoPago.isEmpty())
											{
												//log.info("listaConceptoPago cant="+listaConceptoPago.size());
												BigDecimal bdSumaPorPeriodo = BigDecimal.ZERO;
												for (ConceptoPago conceptoPago : listaConceptoPago) 
												{
													if(conceptoPago.getIntPeriodo().compareTo(lItemPlanilla.getIntPeriodo())== -1)
													{
														bdSumaPorPeriodo = bdSumaPorPeriodo.add(conceptoPago.getBdMontoSaldo());	
													}else if(conceptoPago.getIntPeriodo().compareTo(lItemPlanilla.getIntPeriodo())== 0)
													{
														bdSumaPorPeriodo = bdSumaPorPeriodo.add(conceptoPago.getBdMontoSaldo());	
														blnEncontroAcumulativo = Boolean.TRUE;
													}
												}
												if(!blnEncontroAcumulativo)
												{
													bdSumaPorPeriodo = bdSumaPorPeriodo.add(ccDetalle.getBdMontoConcepto());
												}
												
												totalMontoConcepto = bdSumaPorPeriodo;
											}
											else
											{												
												totalMontoConcepto =  ccDetalle.getBdMontoConcepto();
												
											}
										}			
										
									}
									
											//if(blnAplicaDscto)
											//{
												lEnvioconcepto = new Envioconcepto();
												if(lItemPlanilla.getBlnCartaAutorizacion() != null)
												{
													if(lItemPlanilla.getBlnCartaAutorizacion())
													{
														lEnvioconcepto.setIntIndicienpor(1);
													}else if(!lItemPlanilla.getBlnCartaAutorizacion())
													{
														lEnvioconcepto.setIntIndicienpor(0);
													}
												}
												if(lItemPlanilla.getBlnDJUD() != null)
												{
													if(lItemPlanilla.getBlnDJUD())
													{
														lEnvioconcepto.setIntIndidescjudi(1);	
													}
													else if(!lItemPlanilla.getBlnDJUD())
													{
														lEnvioconcepto.setIntIndidescjudi(0);
													}
												}
												if(lItemPlanilla.getBlnLIC() != null)
												{
													if(lItemPlanilla.getBlnLIC())
													{
														lEnvioconcepto.setIntIndilicencia(1);	
													}else if(!lItemPlanilla.getBlnLIC())
													{
														lEnvioconcepto.setIntIndilicencia(0);
													}
												}								
												lEnvioconcepto.setId(new EnvioconceptoId());
												lEnvioconcepto.getId().setIntEmpresacuentaPk(lSocioEstructura.getId().getIntIdEmpresa());
												lEnvioconcepto.setIntPeriodoplanilla(lItemPlanilla.getIntPeriodo());
												lEnvioconcepto.setIntCuentaPk(dto.getId().getIntCuentaPk());
												lEnvioconcepto.setIntItemcuentaconcepto(dto.getId().getIntItemCuentaConcepto());
												lEnvioconcepto.setIntTipoconceptogeneralCod(Constante.PARAM_T_TIPOCONCEPTOGENERAL_MANTENIMIENTO);
												lEnvioconcepto.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);											
												if(blnAplicaDscto)
												{
													bdDescuento = bdDescuento.add(totalMontoConcepto);																			
													lEnvioconcepto.setBdMontoconcepto(totalMontoConcepto);	
												}else{
													lEnvioconcepto.setBdMontoconcepto(new BigDecimal(0));	
												}
													
												
												lItemPlanilla.getListaEnvioConcepto().add(lEnvioconcepto);
												
											//}
							//mantnimientodecuentaseatambiencancelatorio
								}
								else
								{
									log.info("CAPTACION_AHORROS CANCELATORIO");
									if(dto.getDetalle().getTsInicio() != null)
									{
										Date dateDetalleInicio  			= new Date(dto.getDetalle().getTsInicio().getTime());												
										String fechaDetalleInicio 			= sdf.format(dateDetalleInicio);
										intDetalleIncio 					= Integer.parseInt(fechaDetalleInicio);
											 
											if(dto.getDetalle().getTsFin() == null)
											{
												if(intDetalleIncio.compareTo(intFechaActual) == 0 || intDetalleIncio.compareTo(intFechaActual) == -1)
												{
													if(dto.getBloqueo() != null)
													{	
														//BI bloqueo fecha de inicio
														Date dateBloqueInicio       = new Date(dto.getBloqueo().getTsFechaInicio().getTime()); 									
														String fechaBloqueInicio 	= sdf.format(dateBloqueInicio);
														intBloqueoFechaInicio		= Integer.parseInt(fechaBloqueInicio);	
														
														if(dto.getBloqueo().getIntItemBloqueoCuenta()== null)
														{ 
															blnAplicaDscto = Boolean.TRUE;
															p++;
														}
														else{
																if(dto.getBloqueo().getTsFechaFin() == null)
																{	
																	if(intBloqueoFechaInicio < intFechaActual)
																	{
																		blnAplicaDscto = Boolean.FALSE;
																		p++;
																	}
																	else if(intFechaActual < intBloqueoFechaInicio)
																	{
																		blnAplicaDscto = Boolean.TRUE;
																		p++;
																	}
																}
																else
																{
																	Date dateBloqueFin  = new Date(dto.getBloqueo().getTsFechaFin().getTime());											
																	String fechaBloqueFin = sdf.format(dateBloqueFin);
																	intBloqueoFechaFin = Integer.parseInt(fechaBloqueFin);					
																	if(intFechaActual < intBloqueoFechaFin && intFechaActual > intBloqueoFechaInicio)
																	{
																		blnAplicaDscto = Boolean.FALSE;
																	}
																	else
																	{
																		blnAplicaDscto = Boolean.TRUE;
																		p++;
																	}										
																}
															}
													}
													else
													{
														blnAplicaDscto = Boolean.TRUE;
														p++;
													}
												}
											}
											else
											{
												Date dateDetalleFin      	= new Date(dto.getDetalle().getTsFin().getTime()); 							
												String fechaDetalleFin 		= sdf.format(dateDetalleFin);
												intDetalleFin 				= Integer.parseInt(fechaDetalleFin);
												
												if(intFechaActual< intDetalleFin && intDetalleIncio < intFechaActual)
												{
													if(dto.getBloqueo() != null)
													{																														
														if(dto.getBloqueo().getIntItemBloqueoCuenta()== null)
														{ 
															blnAplicaDscto = Boolean.TRUE;
															p++;
														}
														else
														{
															if(dto.getBloqueo().getTsFechaFin() == null)
															{	
																if(intBloqueoFechaInicio < intFechaActual)
																{
																	blnAplicaDscto = Boolean.FALSE;
																}	
																else if(intBloqueoFechaInicio > intFechaActual)
																{
																	blnAplicaDscto = Boolean.TRUE;
																	p++;
																}
															}
															else if(dto.getBloqueo().getTsFechaFin() != null)
															{											
																//BF bloqueo fecha fin											
																Date dateBloqueFin  = new Date(dto.getBloqueo().getTsFechaFin().getTime());											
																String fechaBloqueFin = sdf.format(dateBloqueFin);
																intBloqueoFechaFin = Integer.parseInt(fechaBloqueFin);	
																if(intFechaActual < intBloqueoFechaFin && intFechaActual > intBloqueoFechaInicio)
																{
																	blnAplicaDscto = Boolean.FALSE;
																}
																else
																{
																	blnAplicaDscto = Boolean.TRUE;
																	p++;
																}
															}
														}
													}
													else
													{
														blnAplicaDscto = Boolean.TRUE;
														p++;
													}
												}
												else
												{
													blnAplicaDscto = Boolean.FALSE;
												}
											}
										}									
									totalMontoConcepto = new BigDecimal(0);									
									List<CuentaConceptoDetalle> listaConceptoDetalle = null;
									conceptoDetalleTemp = null;
									conceptoDetalleTemp = new CuentaConceptoDetalle();
									conceptoDetalleTemp.setId(new CuentaConceptoDetalleId());
									conceptoDetalleTemp.getId().setIntPersEmpresaPk(lSocioEstructura.getId().getIntIdEmpresa());
									conceptoDetalleTemp.getId().setIntCuentaPk(dto.getId().getIntCuentaPk());
									conceptoDetalleTemp.setIntParaTipoConceptoCod(Constante.CAPTACION_AHORROS);
									listaConceptoDetalle = conceptoFacade.getCuentaConceptoDetallePorPKCuentaYTipoConcepto(conceptoDetalleTemp);
									if(listaConceptoDetalle != null && !listaConceptoDetalle.isEmpty())
									{
										for(CuentaConceptoDetalle xx: listaConceptoDetalle)
										{
											List<ConceptoPago> listaConceptoPago = null;
											listaConceptoPago = conceptoFacade.getListaConceptoPagoPorCuentaConceptoDet(xx.getId());
											if(listaConceptoPago != null && !listaConceptoPago.isEmpty())
											{													
												for(ConceptoPago conceptoPago : listaConceptoPago)
												{
													if(conceptoPago.getIntPeriodo().compareTo(lItemPlanilla.getIntPeriodo()) == 0)
													{
														totalMontoConcepto = conceptoPago.getBdMontoSaldo();
														blnEncontro = Boolean.TRUE;
														break;
													}
												}
												if(!blnEncontro)
												{
													totalMontoConcepto =  dto.getDetalle().getBdMontoConcepto();
												}
											}else
											{
												totalMontoConcepto = dto.getDetalle().getBdMontoConcepto();
											}
										}
									}
										
									
										//if(blnAplicaDscto)
										//{
											lEnvioconcepto = new Envioconcepto();
											if(lItemPlanilla.getBlnCartaAutorizacion() != null)
											{
												if(lItemPlanilla.getBlnCartaAutorizacion())
												{
													lEnvioconcepto.setIntIndicienpor(1);
												}else if(!lItemPlanilla.getBlnCartaAutorizacion())
												{
													lEnvioconcepto.setIntIndicienpor(0);
												}
											}
											if(lItemPlanilla.getBlnDJUD() != null)
											{
												if(lItemPlanilla.getBlnDJUD())
												{
													lEnvioconcepto.setIntIndidescjudi(1);	
												}
												else if(!lItemPlanilla.getBlnDJUD())
												{
													lEnvioconcepto.setIntIndidescjudi(0);
												}
											}
											if(lItemPlanilla.getBlnLIC() != null)
											{
												if(lItemPlanilla.getBlnLIC())
												{
													lEnvioconcepto.setIntIndilicencia(1);	
												}else if(!lItemPlanilla.getBlnLIC())
												{
													lEnvioconcepto.setIntIndilicencia(0);
												}
											}								
											lEnvioconcepto.setId(new EnvioconceptoId());
											lEnvioconcepto.getId().setIntEmpresacuentaPk(lSocioEstructura.getId().getIntIdEmpresa());
											lEnvioconcepto.setIntPeriodoplanilla(lItemPlanilla.getIntPeriodo());
											lEnvioconcepto.setIntCuentaPk(dto.getId().getIntCuentaPk());
											lEnvioconcepto.setIntItemcuentaconcepto(dto.getId().getIntItemCuentaConcepto());
											lEnvioconcepto.setIntTipoconceptogeneralCod(Constante.PARAM_T_TIPOCONCEPTOGENERAL_MANTENIMIENTO);
											lEnvioconcepto.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);									
											if(blnAplicaDscto)
											{
												bdDescuento = bdDescuento.add(totalMontoConcepto);																
												lEnvioconcepto.setBdMontoconcepto(totalMontoConcepto);	
											}else{
												lEnvioconcepto.setBdMontoconcepto(new BigDecimal(0));	
											}
											
											lItemPlanilla.getListaEnvioConcepto().add(lEnvioconcepto);
										//}
									}
							}
							
						}
					}
					
						if(lItemPlanilla.getListaEnviomonto() == null)
						{
							lItemPlanilla.setListaEnviomonto(new ArrayList<Enviomonto>());
							lEnviomonto = new Enviomonto();
					    	lEnviomonto.setId(new EnviomontoId());
					    	lEnviomonto.getId().setIntEmpresacuentaPk(lSocioEstructura.getId().getIntIdEmpresa());
					    	lEnviomonto.setBdMontoenvio(bdDescuento);
					    	lEnviomonto.setIntTiposocioCod(lSocioEstructura.getIntTipoSocio());
					    	lEnviomonto.setIntModalidadCod(lSocioEstructura.getIntModalidad());
					    	lEnviomonto.setIntNivel(lSocioEstructura.getIntNivel());
					    	lEnviomonto.setIntCodigo(lSocioEstructura.getIntCodigo());
					    	lEnviomonto.setIntTipoestructuraCod(lSocioEstructura.getIntTipoEstructura());
					    	
					    	lEnviomonto.setIntEmpresasucprocesaPk(lEstructuraDetalle.getIntPersEmpresaPk());
					    	lEnviomonto.setIntIdsucursalprocesaPk(lEstructuraDetalle.getIntSeguSucursalPk());
					    	lEnviomonto.setIntIdsubsucursalprocesaPk(lEstructuraDetalle.getIntSeguSubSucursalPk());
					    	
					    	lEnviomonto.setIntEmpresasucadministraPk(lSocioEstructura.getIntEmpresaSucAdministra());
					    	lEnviomonto.setIntIdsucursaladministraPk(lSocioEstructura.getIntIdSucursalAdministra());
					    	lEnviomonto.setIntIdsubsucursaladministra(lSocioEstructura.getIntIdSubsucurAdministra());
					    	lEnviomonto.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					    	lItemPlanilla.getListaEnviomonto().add(lEnviomonto);
						}else
						{
							for(Enviomonto enviomonto:lItemPlanilla.getListaEnviomonto())
							{
								bdEnvioTemporal = enviomonto.getBdMontoenvio();
								bdEnvioTemporal =bdEnvioTemporal.add(bdDescuento);
								enviomonto.setBdMontoenvio(bdEnvioTemporal);
							}
						}	
						log.info("bdDescuento="+bdDescuento);
						log.info("getDescuentoPorListaConcepto FIN v1");			
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return bdDescuento;
	}
  
}
