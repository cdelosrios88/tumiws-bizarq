package pe.com.tumi.cobranza.cuentacte.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.cuentacte.facade.CuentacteFacadeRemote;
import pe.com.tumi.cobranza.planilla.bo.DescuentoIndebidoBO;
import pe.com.tumi.cobranza.planilla.bo.EnvioinfladaBO;
import pe.com.tumi.cobranza.planilla.bo.EnviomontoBO;
import pe.com.tumi.cobranza.planilla.bo.EnvioresumenBO;
import pe.com.tumi.cobranza.planilla.bo.EstadoSolicitudCtaCteBO;
import pe.com.tumi.cobranza.planilla.bo.SolicitudCtaCteBO;
import pe.com.tumi.cobranza.planilla.bo.SolicitudCtaCteBloqueoBO;
import pe.com.tumi.cobranza.planilla.bo.SolicitudCtaCteTipoBO;
import pe.com.tumi.cobranza.planilla.bo.TransferenciaBO;
import pe.com.tumi.cobranza.planilla.domain.DescuentoIndebido;
import pe.com.tumi.cobranza.planilla.domain.DescuentoIndebidoId;
import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;
import pe.com.tumi.cobranza.planilla.domain.Envioinflada;
import pe.com.tumi.cobranza.planilla.domain.EnvioinfladaId;
import pe.com.tumi.cobranza.planilla.domain.Enviomonto;
import pe.com.tumi.cobranza.planilla.domain.EnviomontoId;
import pe.com.tumi.cobranza.planilla.domain.Envioresumen;
import pe.com.tumi.cobranza.planilla.domain.EstadoSolicitudCtaCte;
import pe.com.tumi.cobranza.planilla.domain.EstadoSolicitudCtaCteId;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCte;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCteBloqueo;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCteId;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCteTipo;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCteTipoId;
import pe.com.tumi.cobranza.planilla.domain.Transferencia;
import pe.com.tumi.cobranza.planilla.domain.TransferenciaId;
import pe.com.tumi.cobranza.planilla.facade.PlanillaFacadeLocal;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.UtilCobranza;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalleId;
import pe.com.tumi.contabilidad.cierre.facade.LibroDiarioFacadeRemote;
import pe.com.tumi.contabilidad.core.domain.Modelo;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalle;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivel;
import pe.com.tumi.contabilidad.core.domain.ModeloId;
import pe.com.tumi.contabilidad.core.facade.ModeloFacadeRemote;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.facade.CuentaFacadeRemote;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.facade.CreditoFacadeRemote;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.empresa.domain.SucursalId;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.BloqueoCuenta;
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
import pe.com.tumi.movimiento.concepto.domain.EstadoExpedienteId;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.movimiento.concepto.domain.InteresCancelado;
import pe.com.tumi.movimiento.concepto.domain.InteresCanceladoId;
import pe.com.tumi.movimiento.concepto.domain.InteresProvisionado;
import pe.com.tumi.movimiento.concepto.domain.InteresProvisionadoId;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.parametro.general.domain.TipoCambio;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.contacto.facade.ContactoFacadeRemote;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaEmpresaPK;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.riesgo.cartera.domain.CarteraCredito;
import pe.com.tumi.riesgo.cartera.domain.CarteraCreditoDetalle;
import pe.com.tumi.riesgo.cartera.facade.CarteraFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.RequisitoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.ExpedienteCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.GarantiaCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.facade.GarantiaCreditoFacadeRemote;
import pe.com.tumi.servicio.solicitudPrestamo.facade.SolicitudPrestamoFacadeRemote;

public class CuentaCtaCteService {
	protected static Logger log = Logger.getLogger(CuentaCtaCteService.class);
	
	private SolicitudCtaCteBO 	     boSolicitudCtaCte = (SolicitudCtaCteBO)TumiFactory.get(SolicitudCtaCteBO.class);
	private SolicitudCtaCteTipoBO    boSolicitudCtaCteTipo = (SolicitudCtaCteTipoBO)TumiFactory.get(SolicitudCtaCteTipoBO.class);
	private SolicitudCtaCteBloqueoBO boSolicitudCtaCteBloqueo = (SolicitudCtaCteBloqueoBO)TumiFactory.get(SolicitudCtaCteBloqueoBO.class);
	
	private EstadoSolicitudCtaCteBO  boEstadoSolicitudCtaCte = (EstadoSolicitudCtaCteBO)TumiFactory.get(EstadoSolicitudCtaCteBO.class);
	private DescuentoIndebidoBO      boDescuentoIndebido = (DescuentoIndebidoBO)TumiFactory.get(DescuentoIndebidoBO.class);

	
public List<SolicitudCtaCte> buscarMovimientoCtaCte(Integer intEmpresasolctacte,Integer intSucuIdsucursalsocio,Integer intTipoSolicitud,Integer intEstadoSolicitud,String nroDni,Integer intCboParaTipoEstado,
		Date    dtFechaInicio,Date dtFechaFin) throws BusinessException{
		
		List<SolicitudCtaCte> listaSolicitudCtaCteTemp = null;
		List<SolicitudCtaCte> listaSolicitudCtaCte = new ArrayList<SolicitudCtaCte>();
		
		
		try{
			
			PersonaFacadeRemote  personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			EmpresaFacadeRemote  empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			ContactoFacadeRemote contactoFacade = (ContactoFacadeRemote) EJBFactory.getRemote(ContactoFacadeRemote.class);
			  
			
			
			listaSolicitudCtaCteTemp = boSolicitudCtaCte.getListaSolicitudCtaCtePorEmpYSucTipSolYEstSol(intEmpresasolctacte, intSucuIdsucursalsocio, intTipoSolicitud, intEstadoSolicitud);
			
			for (SolicitudCtaCte solicitudCtaCte : listaSolicitudCtaCteTemp) {
				boolean existeDni = false;
				boolean existeEstadoSol = false;
				boolean existeEstadoSolEsp = false;
				
				boolean existeFechInicio = false;
				boolean existeFechFin = false;
				
				
				
				
				Sucursal sucursal = new Sucursal();
				sucursal.getId().setIntIdSucursal(solicitudCtaCte.getIntSucuIdsucursalsocio());
				sucursal.getId().setIntPersEmpresaPk(solicitudCtaCte.getIntPersEmpresa());
				sucursal = empresaFacade.getSucursalPorPK(sucursal);
				
				Juridica juridica = personaFacade.getJuridicaPorPK(sucursal.getIntPersPersonaPk());
				solicitudCtaCte.setSucursal(juridica);
				
					Integer intPersEmpresasolctacte = solicitudCtaCte.getId().getIntEmpresasolctacte();
					Integer intCcobItemsolctacte = solicitudCtaCte.getId().getIntCcobItemsolctacte();
					List<SolicitudCtaCteTipo> listaSolTipoCtaCte =  boSolicitudCtaCteTipo.getListaPorSolicitudCtacte(intPersEmpresasolctacte, intCcobItemsolctacte);
					
					
					
					solicitudCtaCte.setListaSolCtaCteTipo(listaSolTipoCtaCte);
				
				
				Integer intPersEmpresasolctacte2 = solicitudCtaCte.getId().getIntEmpresasolctacte();
				Integer intCcobItemsolctacte2 = solicitudCtaCte.getId().getIntCcobItemsolctacte();
			
				List<EstadoSolicitudCtaCte> listaEstadoSolicitudCtaCte  = boEstadoSolicitudCtaCte.getListaPorSolicitudCtacte(intPersEmpresasolctacte2, intCcobItemsolctacte2);
				List<EstadoSolicitudCtaCte> listaEstSolCtaCteTemp = new ArrayList<EstadoSolicitudCtaCte>();
				
				
				for (EstadoSolicitudCtaCte estadoSolicitudCtaCte : listaEstadoSolicitudCtaCte) {
					
					if (intEstadoSolicitud == 0) {
						if (estadoSolicitudCtaCte.getIntParaEstadoSolCtaCte().equals(Constante.PARAM_T_TIPESTADOSOLCITUD_APROBADO) ||
						    estadoSolicitudCtaCte.getIntParaEstadoSolCtaCte().equals(Constante.PARAM_T_TIPESTADOSOLCITUD_RECHAZADO))
						{	
							Natural usuario  = personaFacade.getNaturalPorPK(estadoSolicitudCtaCte.getIntPersUsuarioEstado());
							estadoSolicitudCtaCte.setUsuarioAtencion(usuario);
							listaEstSolCtaCteTemp.add(estadoSolicitudCtaCte);
							existeEstadoSol = true;
							
						}
						else
						if (estadoSolicitudCtaCte.getIntParaEstadoSolCtaCte().equals(Constante.PARAM_T_TIPESTADOSOLCITUD_PENDIENTE)){
							Natural usuario  = personaFacade.getNaturalPorPK(estadoSolicitudCtaCte.getIntPersUsuarioEstado());
							estadoSolicitudCtaCte.setUsuarioSolicitud(usuario);
							listaEstSolCtaCteTemp.add(estadoSolicitudCtaCte);
							existeEstadoSol = true;
						}
						else
						if (estadoSolicitudCtaCte.getIntParaEstadoSolCtaCte().equals(Constante.PARAM_T_TIPESTADOSOLCITUD_ANULADO)){
							existeEstadoSol = false;
						}
						
					}
					
				}	
					 //Buscamos la fecha del estado segun el rango dada.
				if (intCboParaTipoEstado == 1){	
						for (EstadoSolicitudCtaCte estSolCtaCte : listaEstSolCtaCteTemp) {
							
							if (estSolCtaCte.getIntParaEstadoSolCtaCte().equals(Constante.PARAM_T_TIPESTADOSOLCITUD_PENDIENTE))
							 {
								if ((estSolCtaCte.getDtEsccFechaEstado().after(dtFechaInicio) || estSolCtaCte.getDtEsccFechaEstado().equals(dtFechaInicio)) &&
								    (estSolCtaCte.getDtEsccFechaEstado().before(dtFechaFin)   || estSolCtaCte.getDtEsccFechaEstado().equals(dtFechaFin))	
								   ){
									existeFechInicio = true;
									existeFechFin = true;
								 }
							
							 }	
						}	
				}	
				else
					 if (intCboParaTipoEstado == 2){
						 
						 for (EstadoSolicitudCtaCte estSolCtaCte : listaEstSolCtaCteTemp) {
						    if (estSolCtaCte.getIntParaEstadoSolCtaCte().equals(Constante.PARAM_T_TIPESTADOSOLCITUD_APROBADO) ||
						    		estSolCtaCte.getIntParaEstadoSolCtaCte().equals(Constante.PARAM_T_TIPESTADOSOLCITUD_RECHAZADO))
							 {
						    	  if ((estSolCtaCte.getDtEsccFechaEstado().after(dtFechaInicio) || estSolCtaCte.getDtEsccFechaEstado().equals(dtFechaInicio)) &&
									    (estSolCtaCte.getDtEsccFechaEstado().before(dtFechaFin) || estSolCtaCte.getDtEsccFechaEstado().equals(dtFechaFin))	
									   ){
										existeFechInicio = true;
										existeFechFin = true;
								  }
							 }	
						 }  
				 }
					
				
				solicitudCtaCte.setListaEstSolCtaCte(listaEstSolCtaCteTemp);
				
				//Busca Estado Especifico de LA Cta Cte
				for (EstadoSolicitudCtaCte estadoSolicitudCtaCte2 : listaEstadoSolicitudCtaCte) {
					
					if (estadoSolicitudCtaCte2.getIntParaEstadoSolCtaCte().equals(intEstadoSolicitud)){
						existeEstadoSolEsp = true;
						existeEstadoSol = true;
						break;
					}
					
				}
				
				
				Natural socio  = personaFacade.getNaturalPorPK(solicitudCtaCte.getIntPersPersona());
				solicitudCtaCte.setSocio(socio);
				solicitudCtaCte.setDocumentoSocio(contactoFacade.getDocumentoPorIdPersonaYTipoIdentidad(solicitudCtaCte.getIntPersPersona(), 1));
				
				PersonaEmpresaPK pk = new PersonaEmpresaPK();
			        pk.setIntIdEmpresa(solicitudCtaCte.getIntPersEmpresa());
			        pk.setIntIdPersona(solicitudCtaCte.getIntPersPersona());
			        
			    List<PersonaRol>  listaPersonaSocioRol =   personaFacade.getListaPersonaRolPorPKPersonaEmpresa(pk);
			    solicitudCtaCte.setListaPersonaSocioRol(listaPersonaSocioRol);
				
				
				
				
				if (nroDni != null) nroDni.trim();  
				if (!nroDni.equals("")){
					
					Persona persona = personaFacade.getPersonaPorPK(solicitudCtaCte.getIntPersPersona());
					
					if (persona != null){
						
						List<Documento> listDoc = persona.getListaDocumento();
						for (Documento documento : listDoc) {
							
							if (documento.getIntTipoIdentidadCod().equals(1) && documento.getStrNumeroIdentidad().equals(nroDni)){
								 Natural natural = personaFacade.getNaturalPorPK(solicitudCtaCte.getIntPersPersona());
								 solicitudCtaCte.setSocio(persona.getNatural());
								 existeDni = true;
								 break;
							}
							
						}
					  
					}else
					{
					   break;
					}
				}
				
				
				if (existeEstadoSol == true){
						//VVV
						if ((nroDni == null ||  nroDni.equals("")) && intEstadoSolicitud == 0  && intCboParaTipoEstado == 0){
							listaSolicitudCtaCte.add(solicitudCtaCte);
						}
						//fvv
						else
						if ((!nroDni.trim().equals("")) && intEstadoSolicitud == 0  && intCboParaTipoEstado == 0){
							if(existeDni){
								listaSolicitudCtaCte.add(solicitudCtaCte);
								break;
							}
						}
						//ffv
						else
						if ((!nroDni.trim().equals("")) && intEstadoSolicitud != 0  && intCboParaTipoEstado == 0){
							if(existeDni && existeEstadoSolEsp)
							listaSolicitudCtaCte.add(solicitudCtaCte);
						}
						//vvv
						else
						if ((!nroDni.trim().equals("")) && intEstadoSolicitud != 0  && intCboParaTipoEstado != 0){
							if(existeDni && existeEstadoSolEsp &&(existeFechInicio || existeFechFin))
							listaSolicitudCtaCte.add(solicitudCtaCte);
						}
						//fvf
						else
						if ((!nroDni.trim().equals("")) && intEstadoSolicitud == 0  && intCboParaTipoEstado != 0){
							if(existeDni && (existeFechInicio || existeFechFin))
							listaSolicitudCtaCte.add(solicitudCtaCte);
						}
						//vfv
						else
						if ((nroDni == null ||  nroDni.equals("")) && intEstadoSolicitud != 0  && intCboParaTipoEstado == 0){
								if(existeEstadoSolEsp)
								listaSolicitudCtaCte.add(solicitudCtaCte);
						}
						//vvf
						else
						if ((nroDni == null ||  nroDni.equals("")) && intEstadoSolicitud == 0  && intCboParaTipoEstado != 0){
							 if(existeFechInicio || existeFechFin)
							 listaSolicitudCtaCte.add(solicitudCtaCte);
						}
						//vff
						else
						if ((nroDni == null ||  nroDni.equals("")) && intEstadoSolicitud != 0  && intCboParaTipoEstado != 0){
							 if(existeEstadoSolEsp && (existeFechInicio || existeFechFin))
							 listaSolicitudCtaCte.add(solicitudCtaCte);
						}
				}
			}
			return listaSolicitudCtaCte;
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	
/**
 * 
 * @param socioComp
 * @param strPeriodo
 * @param requisitoCred
 * @param usuario
 * @param expedienteMovAnterior
 * @param expedienteCredito
 * @throws BusinessException
 */


	public LibroDiario generarProcesosDeRefinanciamiento_1(SocioComp socioComp, String strPeriodo, RequisitoCredito requisitoCred, Usuario usuario,
			Expediente expedienteMovAnterior, ExpedienteCredito expedienteCreditoNuevo, ExpedienteCreditoComp expedienteCreditoCompSelected) throws BusinessException {
		
		 SolicitudCtaCte solicitudCtaCte = null;
		 SolicitudCtaCteId solCtaCteId = null;
		 EstadoSolicitudCtaCte estadoPendiente = null;
		 EstadoSolicitudCtaCte estadoAtendido = null;
		 EstadoSolicitudCtaCteId estadoPendId = null;
		 EstadoSolicitudCtaCteId estadoAtenId = null;
		 Calendar fecHoy = Calendar.getInstance();
		 Date dtAhora = fecHoy.getTime();
		 CuentacteFacadeRemote cuentaCteFacade = null;
		 LibroDiario libroDiario = null;
		 
		 try {
				 cuentaCteFacade = (CuentacteFacadeRemote)EJBFactory.getRemote(CuentacteFacadeRemote.class);
				 if(socioComp != null){
					 solCtaCteId = new SolicitudCtaCteId();
					 solicitudCtaCte = new SolicitudCtaCte();
					 
					 solCtaCteId.setIntEmpresasolctacte(socioComp.getSocio().getId().getIntIdEmpresa());
					 solicitudCtaCte.setId(solCtaCteId);
					 solicitudCtaCte.setIntPersEmpresa(socioComp.getCuenta().getId().getIntPersEmpresaPk());
					 solicitudCtaCte.setIntPersPersona(socioComp.getPersona().getNatural().getIntIdPersona());
					 solicitudCtaCte.setIntCsocCuenta(socioComp.getCuenta().getId().getIntCuenta());
					 solicitudCtaCte.setIntSucuIdsucursalsocio(socioComp.getCuenta().getIntIdUsuSucursal());
					 solicitudCtaCte.setIntSudeIdsubsucursalsocio(socioComp.getCuenta().getIntIdUsuSubSucursal());
					 solicitudCtaCte.setIntPeriodo(new Integer(strPeriodo));
					 solicitudCtaCte.setIntParaTipomodalidad(socioComp.getSocio().getSocioEstructura().getIntModalidad());
					 solicitudCtaCte.setIntParaTipo(Constante.PARAM_T_TIPO_ORIGEN_REFINANCIAMIENTO_POR_PRESTAMO);
					 solicitudCtaCte.setIntMaeItemarchivo(requisitoCred.getIntParaItemArchivo());
					 solicitudCtaCte.setIntMaeItemhistorico(requisitoCred.getIntParaItemHistorico());
					 
					 //	1. Grabamos la Solicitud de CtaCte y dos estados (Pendiente y Atendido).
					 solicitudCtaCte = cuentaCteFacade.grabarSolicitudCtaCte(solicitudCtaCte);
					 
					 if(solicitudCtaCte.getId().getIntCcobItemsolctacte() != null){
						 List<EstadoSolicitudCtaCte> lstEstadosSolCta = new ArrayList<EstadoSolicitudCtaCte>();
						 estadoPendiente = new EstadoSolicitudCtaCte();
						 estadoAtendido = new EstadoSolicitudCtaCte();
						 estadoPendId = new EstadoSolicitudCtaCteId();
						 estadoAtenId = new EstadoSolicitudCtaCteId() ;
						 
						 estadoPendId.setIntPersEmpresaSolctacte(solicitudCtaCte.getId().getIntEmpresasolctacte());
						 estadoPendId.setIntCcobItemSolCtaCte(solicitudCtaCte.getId().getIntCcobItemsolctacte());
						 estadoPendiente.setId(estadoPendId);
						 estadoPendiente.setIntParaEstadoSolCtaCte(Constante.PARAM_T_ESTADO_SOLCTACTE_PENDIENTE);
						 estadoPendiente.setDtEsccFechaEstado(dtAhora);
						 estadoPendiente.setIntPersEmpresaEstado(usuario.getEmpresa().getIntIdEmpresa());
						 estadoPendiente.setIntSucuIduSusucursal(usuario.getSucursal().getId().getIntIdSucursal());
						 estadoPendiente.setIntSudeIduSusubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
						 estadoPendiente.setIntPersUsuarioEstado(usuario.getIntPersPersonaPk());
						 estadoPendiente.setStrEsccObservacion("registro Automatico por Refinanciamiento - Estado Pendiente.");
						 estadoPendiente = cuentaCteFacade.grabarEstadoSolicitudCtaCte(estadoPendiente);
						 lstEstadosSolCta.add(estadoPendiente);
						 
						 estadoAtenId.setIntPersEmpresaSolctacte(solicitudCtaCte.getId().getIntEmpresasolctacte());
						 estadoAtenId.setIntCcobItemSolCtaCte(solicitudCtaCte.getId().getIntCcobItemsolctacte());
						 estadoAtendido.setId(estadoAtenId);
						 estadoAtendido.setIntParaEstadoSolCtaCte(Constante.PARAM_T_ESTADO_SOLCTACTE_ATENDIDO);
						 estadoAtendido.setDtEsccFechaEstado(dtAhora);
						 estadoAtendido.setIntPersEmpresaEstado(usuario.getEmpresa().getIntIdEmpresa());
						 estadoAtendido.setIntSucuIduSusucursal(usuario.getSucursal().getId().getIntIdSucursal());
						 estadoAtendido.setIntSudeIduSusubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
						 estadoAtendido.setIntPersUsuarioEstado(usuario.getIntPersPersonaPk());
						 estadoAtendido.setStrEsccObservacion("registro Automatico por Refinanciamiento - Estado Atendido.");
						 estadoAtendido = cuentaCteFacade.grabarEstadoSolicitudCtaCte(estadoAtendido);
						 lstEstadosSolCta.add(estadoAtendido);
						 
						 //solicitudCtaCte.setListaEstSolCtaCte(listaEstSolCtaCte)
						 SolicitudCtaCteTipo solCtaCteTipo = new SolicitudCtaCteTipo();
						 SolicitudCtaCteTipoId solCtaCteTipoId = new SolicitudCtaCteTipoId();
						 
						 solCtaCteTipoId.setIntCcobItemsolctacte(solicitudCtaCte.getId().getIntCcobItemsolctacte());
						 solCtaCteTipoId.setIntPersEmpresasolctacte(solicitudCtaCte.getId().getIntEmpresasolctacte());
						 solCtaCteTipoId.setIntTipoSolicitudctacte(Constante.PARAM_T_SOLICITUD_CTACTE_TIPO_TRANSFERENCIAS);
						 solCtaCteTipo.setId(solCtaCteTipoId);
						 solCtaCteTipo.setIntTaraEstado(Constante.PARAM_T_ESTADO_SOLCTACTE_ATENDIDO);
						 solCtaCteTipo.setIntParaTipoorigen(Constante.PARAM_T_TIPO_ORIGEN_REFINANCIAMIENTO_POR_PRESTAMO);
						 solCtaCteTipo.setIntParaEstadoanalisis(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						 solCtaCteTipo.setStrScctObservacion("Generado por Refinanciamiento. ");
						 solCtaCteTipo.setDtFechaDocumento(dtAhora);
						 solCtaCteTipo.setIntMotivoSolicitud(Constante.PARAM_T_MOTIVO_SOLICITUD_CTACTE_REFINANCIAMIENTO);
						 solCtaCteTipo.setIntEmpresaLibro(0);
						 solCtaCteTipo.setIntContPeriodolibro(0);
						 solCtaCteTipo.setIntCodigoLibro(0);
						 solCtaCteTipo.setIntPersUsuario(usuario.getIntPersPersonaPk());
						 solCtaCteTipo.setIntCcobItemefectuado(null);
						 
						 solCtaCteTipo = cuentaCteFacade.grabarSolicitudCtaCteTipo(solCtaCteTipo);
						 
						 List<Expediente> lstExp = new ArrayList<Expediente>();
						 lstExp.add(expedienteMovAnterior);
						 solCtaCteTipo.setListaExpediente(lstExp);
						 solCtaCteTipo.setSocioComp(socioComp);
						 
						 // 2. Generamos el libro diario y la posterior transferencia
					 libroDiario = generarAsientoContableRefinanciamientoYTransferencia_2(expedienteCreditoCompSelected,solCtaCteTipo, usuario, expedienteCreditoNuevo);
						 //generarLibroDiarioYTransferenciaRefinanciamiento(solCtaCteTipo, usuario, expedienteMovAnterior, expedienteCredito);
						 //libroDiario = cuentaCteFacade.generarTransferenciaRefinanciamiento(solCtaCteTipo, usuario,expedientePorRefinanciar, expedienteCredito);
						 
						 //cuentaCteFacade.generarTransferenciaRefinanciamiento(solCtaCteTipo, usuario,expedientePorRefinanciar, expedienteCredito);
					 
					 
					 
					 }
				 
				 }

			} catch (Exception e) {
				log.error("Error en generarSolicitudCtaCteRefinan --> "+e);
			}
		
		return libroDiario;
		
		
		
		
	}
	
	/*
	 public LibroDiario generarSolicitudCtaCteRefinan(SocioComp socioComp, Usuario usuario, String strPeriodo, RequisitoCredito requisitoCred, 
			 Expediente expedientePorRefinanciar, ExpedienteCredito expedienteCredito ) throws BusinessException{
		 
		 SolicitudCtaCte solicitudCtaCte = null;
		 SolicitudCtaCteId solCtaCteId = null;
		 EstadoSolicitudCtaCte estadoPendiente = null;
		 EstadoSolicitudCtaCte estadoAtendido = null;
		 EstadoSolicitudCtaCteId estadoPendId = null;
		 EstadoSolicitudCtaCteId estadoAtenId = null;
		 Calendar fecHoy = Calendar.getInstance();
		 Date dtAhora = fecHoy.getTime();
		 CuentacteFacadeRemote cuentaCteFacade = null;
		 LibroDiario libroDiario = null;
		 
		 try {
			 cuentaCteFacade = (CuentacteFacadeRemote)EJBFactory.getRemote(CuentacteFacadeRemote.class);
			 if(socioComp != null){
				 solCtaCteId = new SolicitudCtaCteId();
				 solicitudCtaCte = new SolicitudCtaCte();
				 
				 solCtaCteId.setIntEmpresasolctacte(socioComp.getSocio().getId().getIntIdEmpresa());
				 solicitudCtaCte.setId(solCtaCteId);
				 solicitudCtaCte.setIntPersEmpresa(socioComp.getCuenta().getId().getIntPersEmpresaPk());
				 solicitudCtaCte.setIntPersPersona(socioComp.getPersona().getNatural().getIntIdPersona());
				 solicitudCtaCte.setIntCsocCuenta(socioComp.getCuenta().getId().getIntCuenta());
				 solicitudCtaCte.setIntSucuIdsucursalsocio(socioComp.getCuenta().getIntIdUsuSucursal());
				 solicitudCtaCte.setIntSudeIdsubsucursalsocio(socioComp.getCuenta().getIntIdUsuSubSucursal());
				 solicitudCtaCte.setIntPeriodo(new Integer(strPeriodo));
				 solicitudCtaCte.setIntParaTipomodalidad(socioComp.getSocio().getSocioEstructura().getIntModalidad());
				 solicitudCtaCte.setIntParaTipo(Constante.PARAM_T_TIPO_ORIGEN_REFINANCIAMIENTO_POR_PRESTAMO);
				 solicitudCtaCte.setIntMaeItemarchivo(requisitoCred.getIntParaItemArchivo());
				 solicitudCtaCte.setIntMaeItemhistorico(requisitoCred.getIntParaItemHistorico());

				 
				 solicitudCtaCte = cuentaCteFacade.grabarSolicitudCtaCte(solicitudCtaCte);
				 //solicitudCtaCte = boSolicitudCtaCte.grabarSolicitudCtaCte(solicitudCtaCte);
				 if(solicitudCtaCte.getId().getIntCcobItemsolctacte() != null){
					 
					 estadoPendiente = new EstadoSolicitudCtaCte();
					 estadoAtendido = new EstadoSolicitudCtaCte();
					 estadoPendId = new EstadoSolicitudCtaCteId();
					 estadoAtenId = new EstadoSolicitudCtaCteId() ;
					 
					 estadoPendId.setIntPersEmpresaSolctacte(solicitudCtaCte.getId().getIntEmpresasolctacte());
					 estadoPendId.setIntCcobItemSolCtaCte(solicitudCtaCte.getId().getIntCcobItemsolctacte());
					 estadoPendiente.setId(estadoPendId);
					 estadoPendiente.setIntParaEstadoSolCtaCte(Constante.PARAM_T_ESTADO_SOLCTACTE_PENDIENTE);
					 estadoPendiente.setDtEsccFechaEstado(dtAhora);
					 estadoPendiente.setIntPersEmpresaEstado(usuario.getEmpresa().getIntIdEmpresa());
					 estadoPendiente.setIntSucuIduSusucursal(usuario.getSucursal().getId().getIntIdSucursal());
					 estadoPendiente.setIntSudeIduSusubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
					 estadoPendiente.setIntPersUsuarioEstado(usuario.getIntPersPersonaPk());
					 estadoPendiente.setStrEsccObservacion("registro Automatico por Refinanciamiento - Estado Pendiente.");
					 //boEstadoCtacte.grabarEstadoSolicitudCtaCte(estadoPendiente);
					 cuentaCteFacade.grabarEstadoSolicitudCtaCte(estadoPendiente);
					 
					 //-----------------------------------------------------------------------------------------------------
					 estadoAtenId.setIntPersEmpresaSolctacte(solicitudCtaCte.getId().getIntEmpresasolctacte());
					 estadoAtenId.setIntCcobItemSolCtaCte(solicitudCtaCte.getId().getIntCcobItemsolctacte());
					 estadoAtendido.setId(estadoAtenId);
					 estadoAtendido.setIntParaEstadoSolCtaCte(Constante.PARAM_T_ESTADO_SOLCTACTE_ATENDIDO);
					 estadoAtendido.setDtEsccFechaEstado(dtAhora);
					 estadoAtendido.setIntPersEmpresaEstado(usuario.getEmpresa().getIntIdEmpresa());
					 estadoAtendido.setIntSucuIduSusucursal(usuario.getSucursal().getId().getIntIdSucursal());
					 estadoAtendido.setIntSudeIduSusubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
					 estadoAtendido.setIntPersUsuarioEstado(usuario.getIntPersPersonaPk());
					 estadoAtendido.setStrEsccObservacion("registro Automatico por Refinanciamiento - Estado Atendido.");
					 //boEstadoCtacte.grabarEstadoSolicitudCtaCte(estadoAtendido);
					 cuentaCteFacade.grabarEstadoSolicitudCtaCte(estadoAtendido);
					 
					 
					 SolicitudCtaCteTipo solCtaCteTipo = new SolicitudCtaCteTipo();
					 SolicitudCtaCteTipoId solCtaCteTipoId = new SolicitudCtaCteTipoId();
					 
					 solCtaCteTipoId.setIntCcobItemsolctacte(solicitudCtaCte.getId().getIntCcobItemsolctacte());
					 solCtaCteTipoId.setIntPersEmpresasolctacte(solicitudCtaCte.getId().getIntEmpresasolctacte());
					 solCtaCteTipoId.setIntTipoSolicitudctacte(Constante.PARAM_T_SOLICITUD_CTACTE_TIPO_TRANSFERENCIAS);
					 solCtaCteTipo.setId(solCtaCteTipoId);
					 
					 solCtaCteTipo.setIntTaraEstado(Constante.PARAM_T_ESTADO_SOLCTACTE_ATENDIDO);
					 solCtaCteTipo.setIntParaTipoorigen(Constante.PARAM_T_TIPO_ORIGEN_REFINANCIAMIENTO_POR_PRESTAMO);
					 solCtaCteTipo.setIntParaEstadoanalisis(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					 solCtaCteTipo.setStrScctObservacion("Generado por Refinanciamiento. ");
					 solCtaCteTipo.setDtFechaDocumento(dtAhora);
					 solCtaCteTipo.setIntMotivoSolicitud(Constante.PARAM_T_MOTIVO_SOLICITUD_CTACTE_REFINANCIAMIENTO);
					 solCtaCteTipo.setIntEmpresaLibro(0);
					 solCtaCteTipo.setIntContPeriodolibro(0);
					 solCtaCteTipo.setIntCodigoLibro(0);
					 solCtaCteTipo.setIntPersUsuario(usuario.getIntPersPersonaPk());
					 solCtaCteTipo.setIntCcobItemefectuado(null);
					 
					 solCtaCteTipo = cuentaCteFacade.grabarSolicitudCtaCteTipo(solCtaCteTipo);
					 
					// if(solCtaCteTipo.getListaExpediente() != null && !solCtaCteTipo.getListaExpediente().isEmpty()){
					List<Expediente> lstExp = new ArrayList<Expediente>();
					lstExp.add(expedientePorRefinanciar);
					solCtaCteTipo.setListaExpediente(lstExp);

					 solCtaCteTipo.setSocioComp(socioComp);
					 libroDiario = cuentaCteFacade.generarTransferenciaRefinanciamiento(solCtaCteTipo, usuario,expedientePorRefinanciar, expedienteCredito);

				 }
			 }
		} catch (Exception e) {
			log.error("Error en generarSolicitudCtaCteRefinan --> "+e);
		}
		 return libroDiario;
		 
	 }
	 */
	 
	
			  
	public SolicitudCtaCte grabarSolicitudCtaCteAntedido(SolicitudCtaCte o) throws BusinessException {
		SolicitudCtaCte solCtaCte = null;
		try {
			List<SolicitudCtaCteTipo> solCtaCteTipo =  o.getListaSolCtaCteTipo();    
			
			for (SolicitudCtaCteTipo solicitudCtaCteTipo : solCtaCteTipo) {
		    	  
				solicitudCtaCteTipo.getId().setIntPersEmpresasolctacte(o.getId().getIntEmpresasolctacte());
				solicitudCtaCteTipo.getId().setIntCcobItemsolctacte(o.getId().getIntCcobItemsolctacte());

				if (solicitudCtaCteTipo.getId().getIntTipoSolicitudctacte().equals(Constante.PARAM_T_TIPOSOLCITUD_LICENCIA)){	
					solicitudCtaCteTipo.getMovimiento().setIntPersEmpresaUsuario(o.getEstSolCtaCte().getIntPersUsuarioEstado());
					solicitudCtaCteTipo.getMovimiento().setIntPersEmpresa(o.getEstSolCtaCte().getIntPersEmpresaEstado());

					if (solicitudCtaCteTipo.getIntParaEstadoanalisis().equals(Constante.PARAM_T_ESTADOANALISIS_APROBAR)){
						bloquearTipoSolicitudLicencia(solicitudCtaCteTipo);
					} else {
						boSolicitudCtaCteTipo.modificarSolicitudCtaCteTipo(solicitudCtaCteTipo);
					}
				} else if (solicitudCtaCteTipo.getId().getIntTipoSolicitudctacte().equals(Constante.PARAM_T_TIPOSOLCITUD_CAMBIOGARANTE)){	
					if (solicitudCtaCteTipo.getIntParaEstadoanalisis().equals(Constante.PARAM_T_ESTADOANALISIS_APROBAR)){
						cambiarGarante(solicitudCtaCteTipo);
					}else{
						boSolicitudCtaCteTipo.modificarSolicitudCtaCteTipo(solicitudCtaCteTipo);
					}
				} else if (solicitudCtaCteTipo.getId().getIntTipoSolicitudctacte().equals(Constante.PARAM_T_TIPOSOLCITUD_CAMBIOENTIDAD)){	
					if (solicitudCtaCteTipo.getIntParaEstadoanalisis().equals(Constante.PARAM_T_ESTADOANALISIS_APROBAR)){
						cambiarEntidad(solicitudCtaCteTipo);
					}else{
						boSolicitudCtaCteTipo.modificarSolicitudCtaCteTipo(solicitudCtaCteTipo);
					}
				} else if (solicitudCtaCteTipo.getId().getIntTipoSolicitudctacte().equals(Constante.PARAM_T_TIPOSOLCITUD_CAMBIOCONDICION)){	
					solicitudCtaCteTipo.getMovimiento().setIntPersEmpresaUsuario(o.getEstSolCtaCte().getIntPersUsuarioEstado());
					solicitudCtaCteTipo.getMovimiento().setIntPersEmpresa(o.getEstSolCtaCte().getIntPersEmpresaEstado());
					
					if (solicitudCtaCteTipo.getIntParaEstadoanalisis().equals(Constante.PARAM_T_ESTADOANALISIS_APROBAR)){
						bloquearCambioCondicion(solicitudCtaCteTipo);
					} else {
						boSolicitudCtaCteTipo.modificarSolicitudCtaCteTipo(solicitudCtaCteTipo);
					}
				} else if (solicitudCtaCteTipo.getId().getIntTipoSolicitudctacte().equals(Constante.PARAM_T_TIPOSOLCITUD_CAMBIOCONDICIONLABORAL)){	
			    	 if (solicitudCtaCteTipo.getIntParaEstadoanalisis().equals(Constante.PARAM_T_ESTADOANALISIS_APROBAR)){
			    		 cambiarCambiarCondicionLaboral(solicitudCtaCteTipo);
			    	 }else{
			    		 boSolicitudCtaCteTipo.modificarSolicitudCtaCteTipo(solicitudCtaCteTipo);
			    	 }
				} else if (solicitudCtaCteTipo.getId().getIntTipoSolicitudctacte().equals(Constante.PARAM_T_TIPOSOLCITUD_DESCUENTO_INDEBIDO)){	
					if (solicitudCtaCteTipo.getIntParaEstadoanalisis().equals(Constante.PARAM_T_ESTADOANALISIS_APROBAR)){
						generarDescuentoIndebido(solicitudCtaCteTipo);
					}else{
						boSolicitudCtaCteTipo.modificarSolicitudCtaCteTipo(solicitudCtaCteTipo);
					}
				} else if (solicitudCtaCteTipo.getId().getIntTipoSolicitudctacte().equals(Constante.PARAM_T_TIPOSOLCITUD_TRANSFERENCIA)){	
					if (solicitudCtaCteTipo.getIntParaEstadoanalisis().equals(Constante.PARAM_T_ESTADOANALISIS_APROBAR)){
						generarTransferencia(solicitudCtaCteTipo);
					}else{
						boSolicitudCtaCteTipo.modificarSolicitudCtaCteTipo(solicitudCtaCteTipo);
					}
				}
			}
		      
		      o.getEstSolCtaCte().getId().setIntCcobItemSolCtaCte(o.getId().getIntCcobItemsolctacte());
		      o.getEstSolCtaCte().getId().setIntPersEmpresaSolctacte(o.getId().getIntEmpresasolctacte());
		      
		      if (!(o.getEstSolCtaCte().getIntParaEstadoSolCtaCte() != null && o.getEstSolCtaCte().getIntParaEstadoSolCtaCte().equals(Constante.PARAM_T_TIPESTADOSOLCITUD_ATENDIDO)))
		            o.getEstSolCtaCte().setIntParaEstadoSolCtaCte(Constante.PARAM_T_TIPESTADOSOLCITUD_ATENDIDO);
		      
		      o.getEstSolCtaCte().setDtEsccFechaEstado(new Date());
		      
		      
		      
		      boEstadoSolicitudCtaCte.grabarEstadoSolicitudCtaCte(o.getEstSolCtaCte());
		      solCtaCte = o;
		      
		  }catch(BusinessException e){
				throw e;
		  }catch(Exception e){
				throw new BusinessException(e);
	      }
			
	  return solCtaCte;
   }	
	 private void cambiarEntidad(SolicitudCtaCteTipo solicitudCtaCteTipo) throws BusinessException{
	      try{
	    	  
	    	  SocioFacadeRemote 	socioFacade  = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
	    	  PlanillaFacadeLocal  planillaFacade  = (PlanillaFacadeLocal)EJBFactory.getLocal(PlanillaFacadeLocal.class);
	    	  
	    	  SocioEstructura socioEstructuraOrigen  = null;
	    	  SocioEstructura socioEstructuraOrigenNueva  = null;
	    	  
	    	  for (int x = 0;x < 2;x++){
	    		  
	    	  
		    	 if  (x == 1) {
		    		  //socioEstructuraOrigen 
			    	  socioEstructuraOrigen      = solicitudCtaCteTipo.getSocioEstructuraOrigen();
			    	  socioEstructuraOrigenNueva = solicitudCtaCteTipo.getSocioEstructuraOrigenNueva();
		    	  } 
		    	  else{ 
		    		  //socioEstructuraPrestamo
		    		  socioEstructuraOrigen      = solicitudCtaCteTipo.getSocioEstructuraPrestamo();
		    		  socioEstructuraOrigenNueva = solicitudCtaCteTipo.getSocioEstructuraPrestamoNueva();
		    	  } 
	    	  
	    	  
	    	  //Cambiar socio al nueva  estructura de la planilla enviada
	    	  
	    	  if (socioEstructuraOrigenNueva != null){
	    		  //se eliminar
		    	  socioEstructuraOrigen.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
		    	  socioEstructuraOrigen.setIntPersonaEliminar(socioEstructuraOrigenNueva.getIntPersonaUsuario());
		    	  socioEstructuraOrigen.setIntEmpresaEliminar(socioEstructuraOrigenNueva.getIntEmpresaUsuario());
		    	  socioEstructuraOrigen.setTsFechaEliminacion(UtilCobranza.obtieneFechaActualEnTimesTamp());
		    	  socioFacade.modificarSocioEstructura(socioEstructuraOrigen);
		    	  //se crea
		    	  socioEstructuraOrigenNueva.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		    	  socioEstructuraOrigenNueva.setDtFechaRegistro(new Date());
		    	  socioFacade.grabarSocioEstructura(socioEstructuraOrigenNueva);
		    	  
		    	  //Caso Uno
		    	  if (solicitudCtaCteTipo.getStrCasoEnvioPlanilla().equalsIgnoreCase("CasoUno")){
		    		  
		    		  if (solicitudCtaCteTipo.getStrSiNoEnvioPlanilla().equals("S")){
		    			  
		    			  Integer intEmpresa              = solicitudCtaCteTipo.getSocioComp().getCuenta().getId().getIntPersEmpresaPk();
		    			  Integer intCuenta				  = solicitudCtaCteTipo.getSocioComp().getCuenta().getId().getIntCuenta();
		    			  Integer intPeriodo              = solicitudCtaCteTipo.getIntPeriodoEnvioPlanilla(); 
		    			  
		    			 List<Envioconcepto>  lista = planillaFacade.getListaEnvioconceptoPorEmprPeriodoNroCta(intEmpresa, intPeriodo, intCuenta);
		    			 
		    			 
		    			 if (lista == null){
	    				     throw new BusinessException("No existe envio concepto para la cuenta:"+intCuenta);
	    				 }
		    			 EnviomontoBO boEnvioMonto = (EnviomontoBO)TumiFactory.get(EnviomontoBO.class);
		    				 
		    			 for (Envioconcepto envioconcepto : lista) {
		    				 
		    				 
		    				 
		    				 Integer  pIntTiposocioCod	  = socioEstructuraOrigen.getIntTipoSocio();
		    				 Integer  pIntModalidadCod 	  = socioEstructuraOrigen.getIntModalidad(); 
		    				 Integer  pIntPeriodoplanilla = intPeriodo;
		    				 Integer  pIntEstadoCod 	  = Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO;
		    				  
		    				 SucursalId idSucursal = new  SucursalId();
		    				 
		    				 //Lista Envio de planilla de  la actual Entidad.
		    				List<Enviomonto> listaEnvioMonto =  boEnvioMonto.getListaEnviomontoDeBuscar(idSucursal, pIntTiposocioCod, pIntModalidadCod, pIntPeriodoplanilla, pIntEstadoCod);
		    				
		    				
		    				if (listaEnvioMonto == null){
		    				     throw new BusinessException("No existe data para el Envio Monto para entidad.");
		    				}
		    				
		    				 Integer  pIntTiposocioCodNueva	   = socioEstructuraOrigenNueva.getIntTipoSocio();
		    				 Integer  pIntModalidadCodNueva    = socioEstructuraOrigenNueva.getIntModalidad(); 
		    				 Integer  pIntPeriodoplanillaNueva = intPeriodo;
		    				 Integer  pIntEstadoCodNueva 	    = Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO;
		    				  
		    				 SucursalId idSucursalNueva = new  SucursalId();
		    				//Lista Envio de Planilla para la nueva Entidad.
		    				List<Enviomonto> listaEnvioMontoNueva =  boEnvioMonto.getListaEnviomontoDeBuscar(idSucursalNueva, pIntTiposocioCodNueva, pIntModalidadCodNueva, pIntPeriodoplanillaNueva, pIntEstadoCodNueva);
		    				
		    				
		    				if (listaEnvioMontoNueva == null){
		    				     throw new BusinessException("No existe data para el Envio Monto para la nueva entidad nueva.");
		    				 }
		    				
		    				BigDecimal bdMontoResumen = new BigDecimal(0);
		    				BigDecimal bdMontoInfladaResumen = null;
		    				
		    				
		    				for (Enviomonto enviomonto : listaEnvioMonto) {
								  boolean existeEnvioPlanillaNueva = false;
		    					  Integer intItemenvioconcepto     = envioconcepto.getId().getIntItemenvioconcepto();
		    					  Integer intItemenvioconceptoMto  = enviomonto.getId().getIntItemenvioconcepto();
		    					  Integer intTipoEstructura        = enviomonto.getIntTipoestructuraCod();
		    					  Integer intCodigoEnvAnt          = enviomonto.getIntCodigo();
	    						  Integer intNivelEnvAnt		   = enviomonto.getIntNivel();
	    						  Integer intNivelAnt		       = socioEstructuraOrigen.getIntNivel();
	    						  Integer intCodigoAnt    		   = socioEstructuraOrigen.getIntCodigo();
	    							
		    					  
		    					if(intItemenvioconcepto.equals(intItemenvioconceptoMto) && intTipoEstructura.equals(socioEstructuraOrigen.getIntTipoEstructura())&&
		    						intNivelEnvAnt.equals(intNivelAnt) && intCodigoEnvAnt.equals(intCodigoAnt)){
		    						
		    						//Eliminamos 
		    						enviomonto.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
		    						enviomonto.setIntPersonaeliminaPk(socioEstructuraOrigenNueva.getIntPersonaUsuario());
		    						enviomonto.setIntEmpresaeliminaPk(socioEstructuraOrigenNueva.getIntEmpresaUsuario());
		    						enviomonto.setTsFechaeliminacion(UtilCobranza.obtieneFechaActualEnTimesTamp());
		    						boEnvioMonto.modificarEnviomonto(enviomonto);
		    						
		    						//Verificamos si existe el concepteo en envio monto para la nueva entidad
		    						
		    						Enviomonto enviomontoNuevaTemp = null;
		    						
		    						for (Enviomonto enviomontoNueva : listaEnvioMontoNueva) {
		    							Integer intItemenvioconceptoMtoNueva = enviomontoNueva.getId().getIntItemenvioconcepto();
		    							Integer intTipoEstructuraNueva       = enviomontoNueva.getIntTipoestructuraCod();
		    							Integer intCodigoEnv       	 = enviomontoNueva.getIntCodigo();
		    							Integer intNivelEnv			 = enviomontoNueva.getIntNivel();
		    							Integer intNivel			 = socioEstructuraOrigenNueva.getIntNivel();
		    							Integer intCodigo    		 = socioEstructuraOrigenNueva.getIntCodigo();
		    							
		    							
		    							
		    							
				    					  	
		    							if(intItemenvioconcepto.equals(intItemenvioconceptoMtoNueva) && intTipoEstructuraNueva.equals(socioEstructuraOrigenNueva.getIntTipoEstructura())
		    								&& intNivelEnv.equals(intNivel) && intCodigoEnv.equals(intCodigo))
		    							{
		    								existeEnvioPlanillaNueva = true;
		    								break;
		    							}	
		    							
		    							enviomontoNuevaTemp = enviomontoNueva;
									}
		    						
		    						if (!existeEnvioPlanillaNueva){
		    						//Agregamos el socio a la nueva entidad
			    						Enviomonto envioMontoNueva = new Enviomonto(); 
			    						
			    						EnviomontoId idEnvioMto = new EnviomontoId();
			    						idEnvioMto.setIntEmpresacuentaPk(intEmpresa);
			    						idEnvioMto.setIntItemenvioconcepto(intItemenvioconcepto);
			    						
			    						envioMontoNueva.setId(idEnvioMto);
			    						envioMontoNueva.setBdMontoenvio(enviomonto.getBdMontoenvio());
			    						envioMontoNueva.setIntTiposocioCod(socioEstructuraOrigenNueva.getIntTipoSocio());
			    						envioMontoNueva.setIntModalidadCod(socioEstructuraOrigenNueva.getIntModalidad());
			    						envioMontoNueva.setIntNivel(socioEstructuraOrigenNueva.getIntNivel());
			    						envioMontoNueva.setIntCodigo(socioEstructuraOrigenNueva.getIntCodigo());
			    						envioMontoNueva.setIntTipoestructuraCod(socioEstructuraOrigenNueva.getIntTipoEstructura());
			    						
			    						envioMontoNueva.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			    						
			    						envioMontoNueva.setIntEmpresasucprocesaPk(enviomontoNuevaTemp.getIntEmpresasucprocesaPk());
			    						envioMontoNueva.setIntIdsucursalprocesaPk(enviomontoNuevaTemp.getIntIdsucursaladministraPk());
			    						envioMontoNueva.setIntIdsubsucursalprocesaPk(enviomontoNuevaTemp.getIntIdsubsucursalprocesaPk());
			    						envioMontoNueva.setIntEmpresasucadministraPk(enviomontoNuevaTemp.getIntEmpresasucadministraPk());
			    						envioMontoNueva.setIntIdsucursaladministraPk(enviomontoNuevaTemp.getIntIdsucursaladministraPk());
			    						envioMontoNueva.setIntIdsubsucursaladministra(enviomontoNuevaTemp.getIntIdsubsucursaladministra());
			    						
			    						envioMontoNueva.setTsFecharegistro(UtilCobranza.obtieneFechaActualEnTimesTamp());
			    						envioMontoNueva.setIntEmpresausuarioPk(socioEstructuraOrigenNueva.getIntEmpresaUsuario());
			    						envioMontoNueva.setIntPersonausuarioPk(socioEstructuraOrigenNueva.getIntPersonaUsuario());
			    						
			    						envioMontoNueva.setIntIndiestructuraagregada(enviomonto.getIntIndiestructuraagregada());
			    						envioMontoNueva.setBdMontooriginal(enviomonto.getBdMontooriginal());
			    						
			    						//Grabar envio monto
			    						 Enviomonto envioMontoNuevaResult = boEnvioMonto.grabarEnviomonto(envioMontoNueva);
			    						
			    						 bdMontoResumen = enviomonto.getBdMontoenvio(); 
			    						 
			    						EnvioinfladaBO boEnvioinflada = (EnvioinfladaBO)TumiFactory.get(EnvioinfladaBO.class);
			    						Integer pIntItemenvioconcepto = enviomonto.getId().getIntItemenvioconcepto();
			    						Integer pIntItemenviomonto    = enviomonto.getId().getIntItemenviomonto();
			    						Integer pIntEmpresacuentaPk   = enviomonto.getId().getIntEmpresacuentaPk();
			    						
			    						List<Envioinflada> listaEnvioinflada = boEnvioinflada.getListaPorEnvioMonto(pIntItemenvioconcepto, pIntItemenviomonto, pIntEmpresacuentaPk);
			    						
			    						
			    						if (listaEnvioinflada != null && listaEnvioinflada.size() > 1){
					    				     throw new BusinessException("Hay mas de un registro en tabla Envio Inflada. ItemEnvioConcepto:"+pIntItemenvioconcepto);
					    				 }
			    						//Elimiando monto inflado
			    						
					    				 for (Envioinflada envioinflada : listaEnvioinflada) {
											  
					    					 //Eliminais Monto Inflado
						    					 envioinflada.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
						    					 boEnvioinflada.modificarEnvioinflada(envioinflada);
					    					 
					    					 //Creais Monto Inflado
						    					 Envioinflada envioinfladaNueva = new Envioinflada();
						    					 EnvioinfladaId id = new EnvioinfladaId();
						    					 id.setIntEmpresacuentaPk(envioMontoNuevaResult.getId().getIntEmpresacuentaPk());
						    					 id.setIntItemenvioconcepto(envioMontoNuevaResult.getId().getIntItemenvioconcepto());
						    					 id.setIntItemenviomonto(envioMontoNuevaResult.getId().getIntItemenviomonto());
						    					 envioinfladaNueva.setId(id);
						    					 envioinfladaNueva.setIntTipoinfladaCod(envioinflada.getIntTipoinfladaCod());
						    					 envioinfladaNueva.setBdMonto(envioinflada.getBdMonto());
						    					 envioinfladaNueva.setStrObservacion(envioinflada.getStrObservacion());
						    					 envioinfladaNueva.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						    					 envioinfladaNueva.setTsFecharegistro(UtilCobranza.obtieneFechaActualEnTimesTamp());
						    					 envioinfladaNueva.setIntPersonausuarioPk(socioEstructuraOrigenNueva.getIntPersonaUsuario());
						    					 envioinfladaNueva.setIntEmpresausuarioPk(socioEstructuraOrigenNueva.getIntEmpresaUsuario());
						    					 
						    				     boEnvioinflada.grabarEnvioinflada(envioinfladaNueva);
						    				     
						    				     bdMontoInfladaResumen = envioinflada.getBdMonto();
										}
					    				 
					    				 
					    				 //Restammos monto resumen y el monto inflado del anterior entidad
					    				 EnvioresumenBO boEnvioresumen = (EnvioresumenBO)TumiFactory.get(EnvioresumenBO.class);
					    				   Integer pIntEmpresaPk 	   = socioEstructuraOrigenNueva.getIntEmpresaUsuario();
					    			       Integer pIntPeriodoplanillaOrigen = intPeriodo;
					    			       Integer pIntTiposocioCodOrigen    = socioEstructuraOrigen.getIntTipoSocio();
					    				   Integer pIntModalidadCodOrigen    = socioEstructuraOrigen.getIntModalidad();
					    				   Integer pIntNivelOrigen           = socioEstructuraOrigen.getIntNivel();
					    				   Integer pIntCodigo                = socioEstructuraOrigen.getIntCodigo();
					    				  
					    				 Integer nroAfectados = null;
					    				 if (envioMontoNuevaResult == null){
					    					 throw new BusinessException("Error al grabar en el envio monto para nueva entidad.");
					    				 }
					    					    					 
					    				 List<Envioresumen> listaEnvResumen = boEnvioresumen.getListaPorEnitdadyPeriodo(pIntEmpresaPk, pIntPeriodoplanillaOrigen, pIntTiposocioCodOrigen, pIntModalidadCodOrigen, pIntNivelOrigen, pIntCodigo);
					    				 
					    				 if (listaEnvResumen == null || listaEnvResumen.size() == 0){
					    				     throw new BusinessException("No existe el envio resumen para la entidad.");
					    				 }
					    				 
					    				 if (listaEnvResumen != null && listaEnvResumen.size() > 1){
					    				     throw new BusinessException("Existe mas de un registro envio resumen para la entidad.");
					    				 }
					    				 
					    				 
					    				 for (Envioresumen envioresumen : listaEnvResumen) {
					    					  BigDecimal bdMontototalNuevo = envioresumen.getBdMontototal().subtract(bdMontoResumen);
					    					  nroAfectados = envioresumen.getIntNumeroafectados()-1;
					    					  if (bdMontoInfladaResumen != null){
					    						  if (envioresumen.getBdMontototalinflada() != null){
					    							     //Restamos en monto total del envio resumen
					    						         BigDecimal    bdMontototalinflada = envioresumen.getBdMontototalinflada().subtract(bdMontoInfladaResumen);
					    					             envioresumen.setBdMontototalinflada(bdMontototalinflada);
					    					   
					    						  }else{
					    							  throw new BusinessException("Data Incosistente Monto Inflada no existe en el envio Resumen.");
					    						  }
					    					  }
					    					  //Restamos los numero de afectados
					    					  envioresumen.setIntNumeroafectados(nroAfectados);
					    					  envioresumen.setBdMontototal(bdMontototalNuevo);
					    					  
					    					  Envioresumen envRes = boEnvioresumen.modificarEnvioresumen(envioresumen);
					    					 
					    					  
					    					  
										 }	
					    				
					    				 //Le suma al monto resumen y al monto inflado para la nueva entidad 
						    				
					    				   Integer pIntEmpresaPkNueva 	   = socioEstructuraOrigenNueva.getIntEmpresaUsuario();
					    			       Integer pIntPeriodoplanillaOrigenNueva = intPeriodo;
					    			       Integer pIntTiposocioCodOrigenNueva    = socioEstructuraOrigenNueva.getIntTipoSocio();
					    				   Integer pIntModalidadCodOrigenNueva    = socioEstructuraOrigenNueva.getIntModalidad();
					    				   Integer pIntNivelOrigenNueva           = socioEstructuraOrigenNueva.getIntNivel();
					    				   Integer pIntCodigoNueva                = socioEstructuraOrigenNueva.getIntCodigo();
					    				   
					    					    					 
					    				 List<Envioresumen> listaEnvResumenNueva = boEnvioresumen.getListaPorEnitdadyPeriodo(pIntEmpresaPkNueva, pIntPeriodoplanillaOrigenNueva, pIntTiposocioCodOrigenNueva, pIntModalidadCodOrigenNueva, pIntNivelOrigenNueva, pIntCodigoNueva);
					    				 
					    				 if (listaEnvResumenNueva == null || listaEnvResumenNueva.size() == 0){
					    				     throw new BusinessException("No existe el envio resumen para la nueva entidad.");
					    				 }
					    				 
					    				 if (listaEnvResumenNueva != null && listaEnvResumenNueva.size() > 1){
					    				     throw new BusinessException("Existe mas de un registro envio resumen para la nueva entidad.");
					    				 }
					    				 
					    				 
					    				 for (Envioresumen envioresumen : listaEnvResumenNueva) {
					    					  BigDecimal bdMontototalNuevo = envioresumen.getBdMontototal().add(bdMontoResumen);
					    					  nroAfectados = envioresumen.getIntNumeroafectados() + 1;
					    					  BigDecimal bdMontototalinflada = null;
					    					  if (bdMontoInfladaResumen != null){
					    						  if (envioresumen.getBdMontototalinflada() == null){
					    							  bdMontototalinflada = bdMontoInfladaResumen;
					    						  }else{
					    					          bdMontototalinflada = envioresumen.getBdMontototalinflada().add(bdMontoInfladaResumen);
					    						  } 
					    						  
					    						  
					    						  envioresumen.setBdMontototalinflada(bdMontototalinflada);
					    					  }
					    					  envioresumen.setIntNumeroafectados(nroAfectados);
					    					  envioresumen.setBdMontototal(bdMontototalNuevo);
					    					  Envioresumen envRes =  boEnvioresumen.modificarEnvioresumen(envioresumen);
					    					  
					    					  envioMontoNuevaResult.setEnvioresumen(envRes);
					    					  boEnvioMonto.modificarEnviomonto(envioMontoNuevaResult);
										 }	
		    					    }	 
		    					}
		    					
							}
							
						}
		    		  }
		    		  
		    	  }
		    	  else 
			      if (solicitudCtaCteTipo.getStrCasoEnvioPlanilla().equalsIgnoreCase("CasoDos")){
			    	  
			    	 
			    		  
			      }
			      else
			      if (solicitudCtaCteTipo.getStrCasoEnvioPlanilla().equalsIgnoreCase("CasoTres")){
			    		
                      if (solicitudCtaCteTipo.getStrSiNoEnvioPlanilla().equals("S")){
                    	  
                    	  Integer intEmpresa              = solicitudCtaCteTipo.getSocioComp().getCuenta().getId().getIntPersEmpresaPk();
		    			  Integer intCuenta				  = solicitudCtaCteTipo.getSocioComp().getCuenta().getId().getIntCuenta();
		    			  Integer intPeriodo              = solicitudCtaCteTipo.getIntPeriodoEnvioPlanilla(); 
		    			  List<Envioconcepto>  listaEnvioConcepto = planillaFacade.getListaEnvioconceptoPorEmprPeriodoNroCta(intEmpresa, intPeriodo, intCuenta);
		    			
                    	  eliminarSocioDelEnvioPlnailla(socioEstructuraOrigen, socioEstructuraOrigenNueva, listaEnvioConcepto, intPeriodo);
			    		  
			    	  }
				  }	  
		    	 
	    	    }  
	    	  }	  
	    	  
	    	  PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			
	    	  //Grabando Datos Laborales de la Persona.
	    	  personaFacade.modificarPersonaNaturalTotal(solicitudCtaCteTipo.getSocioComp().getPersona());
	    	  
	    	  
	    	  
	    	  SolicitudCtaCteTipo resSolCtaTip = boSolicitudCtaCteTipo.getSolicitudCtaCteTipoPorPk(solicitudCtaCteTipo.getId());
			     if (resSolCtaTip == null){
			        	 solicitudCtaCteTipo.setIntParaTipoorigen(Constante.PARAM_T_TIPOORIGEN_ATENCION);
			        	 boSolicitudCtaCteTipo.grabarSolicitudCtaCteTipo(solicitudCtaCteTipo);
			     }
			     else{
			             boSolicitudCtaCteTipo.modificarSolicitudCtaCteTipo(solicitudCtaCteTipo);
			     }
			     
	      }catch(BusinessException e){
				throw e;
		  }catch(Exception e){
				throw new BusinessException(e);
		  }
	 }
	 
	 
	 private void cambiarCambiarCondicionLaboral(SolicitudCtaCteTipo solicitudCtaCteTipo) throws BusinessException{
	      try{
	    	  
	    	  PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			
	    	  //Grabando Datos Laborales de la Persona.
	    	  personaFacade.modificarPersonaNaturalTotal(solicitudCtaCteTipo.getSocioComp().getPersona());
	    	  
	    	  SolicitudCtaCteTipo resSolCtaTip = boSolicitudCtaCteTipo.getSolicitudCtaCteTipoPorPk(solicitudCtaCteTipo.getId());
			     if (resSolCtaTip == null){
			        	 solicitudCtaCteTipo.setIntParaTipoorigen(Constante.PARAM_T_TIPOORIGEN_ATENCION);
			        	 boSolicitudCtaCteTipo.grabarSolicitudCtaCteTipo(solicitudCtaCteTipo);
			     }
			     else{
			             boSolicitudCtaCteTipo.modificarSolicitudCtaCteTipo(solicitudCtaCteTipo);
			     }
			     
	      }catch(BusinessException e){
				throw e;
		  }catch(Exception e){
				throw new BusinessException(e);
		  }
	 }
	 
	 private void generarDescuentoIndebido(SolicitudCtaCteTipo solicitudCtaCteTipo) throws BusinessException{
	      try{
	    	  
	    	  List<DescuentoIndebido> listDesIndebidoOrigen =  solicitudCtaCteTipo.getListaDesctoIndebidoOrigen();
	    	  if (listDesIndebidoOrigen != null)
	    	  for (DescuentoIndebido descuentoIndebido : listDesIndebidoOrigen) {
	    		  if (descuentoIndebido.getId() == null){
	    			  
	    			  DescuentoIndebidoId id = new DescuentoIndebidoId();
	    			  
	    			  id.setIntPersEmpresaDsctoindeb(solicitudCtaCteTipo.getId().getIntPersEmpresasolctacte());
	    			  descuentoIndebido.setId(id);
	    			  descuentoIndebido.setTsDeinFecha(UtilCobranza.obtieneFechaActualEnTimesTamp());
	    			  descuentoIndebido.setIntCsocCuenta(solicitudCtaCteTipo.getSocioComp().getCuenta().getId().getIntCuenta());
	    			  descuentoIndebido.setIntPersEmpresaCuentaenv(solicitudCtaCteTipo.getSocioComp().getCuenta().getId().getIntPersEmpresaPk());
	    			  descuentoIndebido.setIntParaEstadocod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
	    			  descuentoIndebido.setIntParaEstadoPagadocod(Constante.PARAM_T_ESTADO_PAGO_PENDIENTE);
	    			  descuentoIndebido.setIntParaDocumentoGeneralcod(Constante.PARAM_T_DOCGENERAL_DEV_DSTO_INDEBIDO);
	    			 
	    			  descuentoIndebido.setIntParaTiposolicitudCtactecod(solicitudCtaCteTipo.getId().getIntTipoSolicitudctacte());
	    			  descuentoIndebido.setIntPersEmpresaSolctacte(solicitudCtaCteTipo.getId().getIntPersEmpresasolctacte());
	    			  descuentoIndebido.setIntCcobItemsolCtacte(solicitudCtaCteTipo.getId().getIntCcobItemsolctacte());
	    			  
	    		      boDescuentoIndebido.grabarDescuentoIndebido(descuentoIndebido);
	    		  }  
			  }
	    	    
              List<DescuentoIndebido> listDesIndebidoPres =  solicitudCtaCteTipo.getListaDesctoIndebidoPrestamo();
	    	  if (listDesIndebidoPres != null)
	    	  for (DescuentoIndebido descuentoIndebido : listDesIndebidoPres) {
	    		  if (descuentoIndebido.getId() == null){
	    			  
	    			  DescuentoIndebidoId id = new DescuentoIndebidoId();
	    			  id.setIntPersEmpresaDsctoindeb(solicitudCtaCteTipo.getId().getIntPersEmpresasolctacte());
	    			  descuentoIndebido.setId(id);
	    			  descuentoIndebido.setTsDeinFecha(UtilCobranza.obtieneFechaActualEnTimesTamp());
	    			  descuentoIndebido.setIntCsocCuenta(solicitudCtaCteTipo.getSocioComp().getCuenta().getId().getIntCuenta());
	    			  descuentoIndebido.setIntPersEmpresaCuentaenv(solicitudCtaCteTipo.getSocioComp().getCuenta().getId().getIntPersEmpresaPk());
	    			  descuentoIndebido.setIntParaEstadocod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
	    			  descuentoIndebido.setIntParaEstadoPagadocod(Constante. PARAM_T_ESTADO_PAGO_PENDIENTE);
	    			  descuentoIndebido.setIntParaDocumentoGeneralcod(Constante.PARAM_T_DOCGENERAL_DEV_DSTO_INDEBIDO);
		    			 
	    			  descuentoIndebido.setIntParaTiposolicitudCtactecod(solicitudCtaCteTipo.getId().getIntTipoSolicitudctacte());
	    			  descuentoIndebido.setIntPersEmpresaSolctacte(solicitudCtaCteTipo.getId().getIntPersEmpresasolctacte());
	    			  descuentoIndebido.setIntCcobItemsolCtacte(solicitudCtaCteTipo.getId().getIntCcobItemsolctacte());
	    		   	}
			  }
	    	  
			  
	    	  SolicitudCtaCteTipo resSolCtaTip = boSolicitudCtaCteTipo.getSolicitudCtaCteTipoPorPk(solicitudCtaCteTipo.getId());
			  if (resSolCtaTip == null){
			        	 solicitudCtaCteTipo.setIntParaTipoorigen(Constante.PARAM_T_TIPOORIGEN_ATENCION);
			        	 boSolicitudCtaCteTipo.grabarSolicitudCtaCteTipo(solicitudCtaCteTipo);
			  }
			  else{
			             boSolicitudCtaCteTipo.modificarSolicitudCtaCteTipo(solicitudCtaCteTipo);
			  }
	      }catch(BusinessException e){
				throw e;
		  }catch(Exception e){
				throw new BusinessException(e);
		  }
	 }
	 
	 public void generarTransferencia(SolicitudCtaCteTipo solicitudCtaCteTipo) throws BusinessException{
	      try{
	    	    PersonaFacadeRemote       personaFacade     = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
	    	    TransferenciaBO           boTransferencia   = (TransferenciaBO)TumiFactory.get(TransferenciaBO.class);
				 	
	    	    LibroDiario diarioResult = null;
	    	    Transferencia transferencia     = new Transferencia();
			    
		        
		        //Actualiza o se regisra el Tipo de Solicitud. 
		        SolicitudCtaCteTipo resSolCtaTip = boSolicitudCtaCteTipo.getSolicitudCtaCteTipoPorPk(solicitudCtaCteTipo.getId());
				if (resSolCtaTip == null){
				   solicitudCtaCteTipo.setIntParaTipoorigen(Constante.PARAM_T_TIPOORIGEN_ATENCION);
				   boSolicitudCtaCteTipo.grabarSolicitudCtaCteTipo(solicitudCtaCteTipo);
				}
				else{
				   boSolicitudCtaCteTipo.modificarSolicitudCtaCteTipo(solicitudCtaCteTipo);
				}
			  
		        if (solicitudCtaCteTipo.getIntMotivoSolicitud().equals(Constante.PARAM_T_TIPOTRANSFERENCIA_FALLECIMIENTO_SOCIO)){
		        	diarioResult = generarAsientoContable(solicitudCtaCteTipo);
		        	
		        	//Se desactiva al Socio.
			    	  Integer idPersona = solicitudCtaCteTipo.getSocioComp().getSocio().getId().getIntIdPersona();
			    	  Persona persona = personaFacade.getPersonaPorPK(idPersona);
			    	  persona.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO);
			    	  personaFacade.modificarPersona(persona);
		        	
		        	
		        }else
		        if (solicitudCtaCteTipo.getIntMotivoSolicitud().equals(Constante.PARAM_T_TIPOTRANSFERENCIA_SOCIO_NO_LABORA)){
		        	diarioResult = generarAsientoContable(solicitudCtaCteTipo);
		        }
	            else
		        if (solicitudCtaCteTipo.getIntMotivoSolicitud().equals(Constante.PARAM_T_TIPOTRANSFERENCIA_LICENCIA)){
		        	diarioResult = generarAsientoContable(solicitudCtaCteTipo);
		        }
		        
			      //Graba la transferencia;
			      TransferenciaId transferenciaId = new TransferenciaId();
			      transferenciaId.setIntPersEmpresaTransferencia(solicitudCtaCteTipo.getIntEmpresa());
			      transferencia.setId(transferenciaId);
			      transferencia.setIntParaDocumentoGeneral(diarioResult.getIntParaTipoDocumentoGeneral());
			      transferencia.setTsTranFecha(UtilCobranza.obtieneFechaActualEnTimesTamp());
			      transferencia.setIntCcobItemSolctacte(resSolCtaTip.getId().getIntCcobItemsolctacte());
			      transferencia.setIntPersEmpresaSolctacte(resSolCtaTip.getId().getIntPersEmpresasolctacte());
			      transferencia.setIntParaTipoSolicitudctacte(resSolCtaTip.getId().getIntTipoSolicitudctacte());
			      transferencia.setIntPersEmpresaLibro(diarioResult.getId().getIntPersEmpresaLibro());
			      transferencia.setIntContPeriodoLibro(diarioResult.getId().getIntContPeriodoLibro());
			      transferencia.setIntContCodigoLibro(diarioResult.getId().getIntContCodigoLibro());
			      transferencia.setIntTranPeriodo(diarioResult.getId().getIntContPeriodoLibro());
		        
			      boTransferencia.grabarTransferencia(transferencia);
			        
		         
		    	  
		    	
	      }catch(BusinessException e){
				throw e;
		  }catch(Exception e){
				throw new BusinessException(e);
		  }
	 }
	 
	 private LibroDiario generarAsientoContable(SolicitudCtaCteTipo solicitudCtaCteTipo) throws BusinessException{
		 
		 ConceptoFacadeRemote     conceptoFacade 	= null;
		 ModeloFacadeRemote       modeloFacade 		= null;
		 LibroDiarioFacadeRemote  libroDiarioFacade = null;
		 CreditoFacadeRemote      creditoFacade 	= null;
		 GeneralFacadeRemote      generalFacade 	= null;
		 CarteraFacadeRemote      carteraFacade 	= null;
		 
		 Integer intPersona = null;
		 Integer intCuenta  = null;
		 Integer intEmpresa = null;
		 LibroDiario diario = null;
		 List<Expediente> listaExpediente = null;
		 List<Modelo>     listaModelo     = null;
		 
		 Integer intCuentaTitularOGarante = null;    
   	     Integer intPersonaTitularOGarante = null;    
	     Integer intEmpresaTitularOGarante = null;	  
   	   	 Movimiento movimientoTemporal 		= null;
		 
	      try{
	    	     conceptoFacade    = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
	    	     modeloFacade      = (ModeloFacadeRemote)EJBFactory.getRemote(ModeloFacadeRemote.class);
	    	     libroDiarioFacade = (LibroDiarioFacadeRemote)EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
	    	     creditoFacade     = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
	    	     generalFacade     = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class); 
	    	     carteraFacade     = (CarteraFacadeRemote)EJBFactory.getRemote(CarteraFacadeRemote.class); 
	    	     
			     //Datos del Socio
	    	     intPersona = solicitudCtaCteTipo.getSocioComp().getSocio().getId().getIntIdPersona();
	    	     intCuenta  = solicitudCtaCteTipo.getSocioComp().getCuenta().getId().getIntCuenta();
	             intEmpresa = solicitudCtaCteTipo.getSocioComp().getCuenta().getId().getIntPersEmpresaPk();
	    	       
                 //Obtencion del Modelo Contable segun el tipo de transferencia.
				 if (solicitudCtaCteTipo.getIntMotivoSolicitud().equals(Constante.PARAM_T_TIPOTRANSFERENCIA_FALLECIMIENTO_SOCIO)){
				    listaModelo = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_SOCIOFALLECIDO,intEmpresa);
				 }else
				 if (solicitudCtaCteTipo.getIntMotivoSolicitud().equals(Constante.PARAM_T_TIPOTRANSFERENCIA_SOCIO_NO_LABORA)){
					 if (solicitudCtaCteTipo.getRadioOpcionTransferencias().equals(0)){
					   listaModelo = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_SOCIONOLABORA_ENTRE_CTAS,intEmpresa);
					 }else{
					   listaModelo = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_SOCIONOLABORA,intEmpresa);
					 }
				 }
				 else
				 if (solicitudCtaCteTipo.getIntMotivoSolicitud().equals(Constante.PARAM_T_TIPOTRANSFERENCIA_LICENCIA)){	 
					listaModelo = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_LICENCIA,intEmpresa);
				 }
				 
				 if (listaModelo == null || listaModelo.size() == 0){
					  throw new BusinessException("No existe el modelo contable para generar el asiento.");
				 }
	             
				//Generación del Libro Diario.
		    	 diario = new LibroDiario();
//				 Integer anio = UtilCobranza.obtieneAnio(new Date()); //fredy
				 java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("yyyy");
				String anioTemporal = sdf.format(new Timestamp(solicitudCtaCteTipo.getDtFechaDocumento().getTime()));
				Integer anio = new Integer(anioTemporal);
//				 String  mes  = UtilCobranza.obtieneMesCadena(new Date());
				 
				 java.text.SimpleDateFormat sdfk=new java.text.SimpleDateFormat("MM");
			  	String mes = sdfk.format(new Timestamp(solicitudCtaCteTipo.getDtFechaDocumento().getTime()));
				 
				 diario.getId().setIntContPeriodoLibro(new Integer(anio+""+mes));
				 diario.getId().setIntPersEmpresaLibro(intEmpresa);
				 diario.setStrGlosa("Transferencia...");
				 diario.setTsFechaRegistro(new Timestamp(solicitudCtaCteTipo.getDtFechaDocumento().getTime())); //fecha futura
				 diario.setTsFechaDocumento(new Timestamp((new Date()).getTime()));//la fecha actual
				 diario.setIntPersEmpresaUsuario(solicitudCtaCteTipo.getIntEmpresa());
				 diario.setIntPersPersonaUsuario(solicitudCtaCteTipo.getIntPersUsuario());
				 diario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCGENERAL);
				 diario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				 
				 
				 listaExpediente = solicitudCtaCteTipo.getListaExpediente();
			     Integer intItemDetalle = new Integer(0);
			     
	    	     for (Expediente expediente : listaExpediente) { 
	    	    	 
				    	        Integer intMiniValor     	  = null;
				    	        Integer intTipoCptoGeneral    = null;
				    	        Integer intParaTipoConcepto   = null;
				    	        Integer intTipoCreditoEmpresa = null;
						        Integer intTipoCategRiesgo    = null;
						         
						        intItemDetalle = intItemDetalle + 1;
								LibroDiarioDetalle diarioDetalle = new LibroDiarioDetalle();
								 
								LibroDiarioDetalleId id = new LibroDiarioDetalleId();
								id.setIntContCodigoLibro(diario.getId().getIntContCodigoLibro());
								id.setIntContPeriodoLibro(diario.getId().getIntContPeriodoLibro());
								id.setIntPersEmpresaLibro(diario.getId().getIntPersEmpresaLibro());
								id.setIntContItemLibro(intItemDetalle);
								
								diarioDetalle.setId(id);
								
								if (expediente.getStrDescripcion() != null && (expediente.getStrDescripcion().equals("Aporte")
										                                    ||  expediente.getStrDescripcion().equals("Seguro Desgravamen"))){
									 intMiniValor = Constante.PARAM_T_TIPOCREDITO_TOTAL_TRANSFERENCIA;
									 diarioDetalle.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
										
				    	        }else{
									//Obtiene el tipo de concepto de la cuenta.
									if (expediente.getIntParaTipoCreditoCod() == null && expediente.getId().getIntItemExpediente() != null){ 
											
										CuentaConceptoId ctaCptoId = new CuentaConceptoId();
										ctaCptoId.setIntCuentaPk(intCuenta);
										ctaCptoId.setIntItemCuentaConcepto(expediente.getId().getIntItemExpediente());
										ctaCptoId.setIntPersEmpresaPk(intEmpresa);
										
										diarioDetalle.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
										
										if (ctaCptoId.getIntItemCuentaConcepto().equals(Constante.CAPTACION_FDO_SEPELIO)){
						     				intTipoCptoGeneral    = Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDOSEPELIO;
						    			
										}else
						     			if (ctaCptoId.getIntItemCuentaConcepto().equals(Constante.CAPTACION_FDO_RETIRO)){
							     			intTipoCptoGeneral    = Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO;
							     		}
							   			else
							   			if (ctaCptoId.getIntItemCuentaConcepto().equals(Constante.CAPTACION_MANT_CUENTA)){
								     		intTipoCptoGeneral    = Constante.PARAM_T_TIPOCONCEPTOGENERAL_MANTENIMIENTO;
								     	}
										
										List<CuentaConceptoDetalle> listCtaCptoDet = conceptoFacade.getListaCuentaConceptoDetallePorCuentaConcepto(ctaCptoId);
										
										for (CuentaConceptoDetalle cuentaConceptoDetalle : listCtaCptoDet) {
											intParaTipoConcepto = cuentaConceptoDetalle.getIntParaTipoConceptoCod();
											break;
										}
									}	
									else{
										
										Credito credito = null;
									    CreditoId creditoId = new CreditoId();
										creditoId.setIntItemCredito(expediente.getIntItemCredito());
										creditoId.setIntParaTipoCreditoCod(expediente.getIntParaTipoCreditoCod());
										creditoId.setIntPersEmpresaPk(expediente.getIntPersEmpresaCreditoPk());
										
									    //Busca la categoria de riesgo para cada concepto 
										CarteraCredito carteraCredito = carteraFacade.getCarteraCreditoPorMaxPeriodo(intEmpresa); 
										List<CarteraCreditoDetalle> lista = carteraCredito.getListaCarteraCreDetalle(); 
										for (CarteraCreditoDetalle carCredDet : lista) {
										     if (carCredDet.getIntPersPersona().equals(intPersona) &&
												carCredDet.getIntPersEmpresa().equals(intEmpresa) &&
												carCredDet.getIntCsocCuenta().equals(intCuenta)){
													if(expediente.getStrDescripcion() != null 
															&&(expediente.getStrDescripcion().equals("Amortización")
													     ||expediente.getStrDescripcion().equals("Interes")
													     ||expediente.getStrDescripcion().equals("Mora"))){
											  			if (expediente.getId().getIntItemExpediente().equals(carCredDet.getIntCserItemexpediente())&&
														    expediente.getId().getIntItemExpedienteDetalle().equals(carCredDet.getIntCserItemdetexpediente())){
															credito = creditoFacade.getCreditoPorIdCredito(creditoId);
															
														    if (credito == null){
															   throw new BusinessException("No existe la configuración de crédito para el expediente.");
															}
															    
														    
														    if (expediente.getStrDescripcion().equals("Amortización")){
														    	intTipoCptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION;
														    }
														    else
														    if (expediente.getStrDescripcion().equals("Interes")){
															    intTipoCptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES;
															}
														    else
														    if (expediente.getStrDescripcion().equals("Mora")){
															    intTipoCptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_MORA;
															}
														    
														    intTipoCategRiesgo    = carCredDet.getIntParaTipocategoriariesgo();
														    intTipoCreditoEmpresa = credito.getIntParaTipoCreditoEmpresa();
														    diarioDetalle.setIntParaMonedaDocumento(credito.getIntParaMonedaCod());
														 }
												  }
											 }
										}
									}
				    	        } 	   
					 
							   ModeloDetalle modeloDet = null;
							   for (Modelo modelo : listaModelo) {
										modeloDet = obtieneElCodigoAsientoyNroCtaDelPlanCuentaActual(modelo.getId(),
												                                                     intTipoCategRiesgo,
												                                                     intMiniValor,
												                                                     intTipoCptoGeneral,
												                                                     intParaTipoConcepto,
												                                                     intTipoCreditoEmpresa);
									if (modeloDet == null){
											  throw new BusinessException("No existe el Número de Cuenta en el Modelo Contable(CodModelo:"+modelo.getId().getIntCodigoModelo()+",intParaTipoConcepto="+intParaTipoConcepto+
													                      ",intTipoCategRiesgo = "+intTipoCategRiesgo+",intMiniValor = "+intMiniValor+",intTipoCptoGeneral="+intTipoCptoGeneral+", intTipoCreditoEmpresa="+intTipoCreditoEmpresa+")");
									}
							    }	
									
								diarioDetalle.setIntPersEmpresaCuenta(modeloDet.getId().getIntEmpresaPk());
//								diarioDetalle.setIntContPeriodo(modeloDet.getId().getIntContPeriodoCuenta()); //FREDY
								java.text.SimpleDateFormat anioDetalle=new java.text.SimpleDateFormat("yyyy"); //cambiolibrodiarioDetalleCon_Periodo_N10/042013
								String minutoDetalle = anioDetalle.format(new Timestamp(solicitudCtaCteTipo.getDtFechaDocumento().getTime()));
								//en caso la transferencia se hiciera de 
								//	diciembre 2013  a enero 2014 se debe considerara nuevo año
								Integer anioDiarioDetalle = new Integer(minutoDetalle);
								diarioDetalle.setIntContPeriodo(anioDiarioDetalle);
								diarioDetalle.setStrContNumeroCuenta(modeloDet.getId().getStrContNumeroCuenta());
								diarioDetalle.setIntPersPersona(solicitudCtaCteTipo.getIntPersUsuario());
							    
							    if (intMiniValor != null && intMiniValor.equals(Constante.PARAM_T_TIPOCREDITO_TOTAL_TRANSFERENCIA)){
							    	    diarioDetalle.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
							    	if (modeloDet.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_DEBE)){
//							    		diarioDetalle.setBdDebeSoles(expediente.getBdSaldoCredito());
							    		diarioDetalle.setBdDebeSoles(expediente.getBdSaldoCreditoSoles());
							    	}else{
							    		diarioDetalle.setBdHaberSoles(expediente.getBdSaldoCredito());
							    	}
							    }else{
							        if (expediente.getId().getIntItemExpediente() != null){
									  diarioDetalle.setStrNumeroDocumento(expediente.getId().getIntItemExpediente()+"-"+expediente.getId().getIntItemExpedienteDetalle());
								    }
									
									if (modeloDet.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_DEBE)){
										   if (diarioDetalle.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
											     diarioDetalle.setBdDebeSoles(expediente.getBdMontoAbono());
										   }
										   else{
											   TipoCambio tipoCambio =   generalFacade.getTipoCambioActualPorClaseYMoneda(diarioDetalle.getIntPersEmpresaCuenta(), Constante.PARAM_T_TIPOCAMBIO_CLASE_BANCARIA,diarioDetalle.getIntParaMonedaDocumento());
											   BigDecimal bdSaldoEstranjero   = expediente.getBdMontoAbono().divide(tipoCambio.getBdPromedio());
											   diarioDetalle.setBdDebeSoles(expediente.getBdMontoAbono());
											   diarioDetalle.setBdDebeExtranjero(bdSaldoEstranjero);
											   diarioDetalle.setIntTipoCambio(tipoCambio.getId().getIntParaClaseTipoCambio());
										   }
									}
									else 
		                            if (modeloDet.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_HABER)){
		                            	   
		                            	if (diarioDetalle.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
											   diarioDetalle.setBdHaberSoles(expediente.getBdMontoAbono());
//											   
										   }
										   else{
											   TipoCambio tipoCambio =   generalFacade.getTipoCambioActualPorClaseYMoneda(diarioDetalle.getIntPersEmpresaCuenta(),Constante.PARAM_T_TIPOCAMBIO_CLASE_BANCARIA,diarioDetalle.getIntParaMonedaDocumento());
											   BigDecimal bdSaldoEstranjero   = expediente.getBdMontoAbono().divide(tipoCambio.getBdPromedio());
											   diarioDetalle.setBdHaberSoles(expediente.getBdMontoAbono());
											   diarioDetalle.setBdHaberExtranjero(bdSaldoEstranjero);
											   diarioDetalle.setIntTipoCambio(tipoCambio.getId().getIntParaClaseTipoCambio());
										  }
		                        	}
							    }	
								
								diarioDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
								diarioDetalle.setStrComentario("Transferencia.. ");
								diarioDetalle.setIntPersEmpresaSucursal(solicitudCtaCteTipo.getIntEmpresa());
								diarioDetalle.setIntSucuIdSucursal(solicitudCtaCteTipo.getIdSucursalUsuario());
								diarioDetalle.setIntSudeIdSubSucursal(solicitudCtaCteTipo.getIdSubSucursalUsuario());
								
								//se graba el libro diario detalle. 
//								libroDiarioFacade.grabarLibroDiarioDetalle(diarioDetalle);
								diario.getListaLibroDiarioDetalle().add(diarioDetalle);
						
								//Se actualiza los saldos de Prestamo Credito, Interes y Mora
								if(expediente.getStrDescripcion() != null && !expediente.getStrDescripcion().equalsIgnoreCase("Aporte")){
									
									if (expediente.getId().getIntItemExpediente() != null && expediente.getId().getIntItemExpedienteDetalle() != null){
									  Expediente expUpdate  =  conceptoFacade.getExpedientePorPK(expediente.getId());
									   if(expediente.getStrDescripcion() != null && expediente.getStrDescripcion().equalsIgnoreCase("Amortización")){
										   if (expUpdate.getBdSaldoCredito() == null) expUpdate.setBdSaldoCredito(new BigDecimal(0));
										   BigDecimal bdSaldoCredito = expUpdate.getBdSaldoCredito().subtract(expediente.getBdMontoAbono() != null?expediente.getBdMontoAbono():new BigDecimal(0));
										   expUpdate.setBdSaldoCredito(bdSaldoCredito);
									   }
									   else
									   if(expediente.getStrDescripcion() != null && expediente.getStrDescripcion().equalsIgnoreCase("Interes")){
										   if (expUpdate.getBdSaldoInteres() == null) expUpdate.setBdSaldoInteres(new BigDecimal(0));
										   BigDecimal bdSaldoInteres = expUpdate.getBdSaldoInteres().subtract(expediente.getBdMontoAbono() != null?expediente.getBdMontoAbono():new BigDecimal(0));
										   expUpdate.setBdSaldoInteres(bdSaldoInteres);
									   }
									   else
									   if(expediente.getStrDescripcion() != null && expediente.getStrDescripcion().equalsIgnoreCase("Mora")){
										   if (expUpdate.getBdSaldoMora() == null) expUpdate.setBdSaldoMora(new BigDecimal(0));
										   BigDecimal bdSaldoMora    = expUpdate.getBdSaldoMora().subtract(expediente.getBdMontoAbono() != null?expediente.getBdMontoAbono():new BigDecimal(0));
										   expUpdate.setBdSaldoMora(bdSaldoMora);
								       }
									   conceptoFacade.modificarExpediente(expUpdate);
									   
									 //Se guarda en EstadoExpediente si los saldos tanto credito,
									//    de expediente y del cronograma su saldodetalleCredito son 0
									   Expediente expEstado  =  conceptoFacade.getExpedientePorPK(expediente.getId());
							    	     if(expEstado != null){
								    	     if(expEstado.getBdSaldoCredito().compareTo(new BigDecimal(0))== 0 
								    	    	//&& expEstado.getBdSaldoInteres().compareTo(new BigDecimal(0))== 0 &&
								    	       // expEstado.getBdSaldoMora().compareTo(new BigDecimal(0))   == 0 
								    	        ){
								    	    	 								    	    
												EstadoExpediente estadoExpediente  = new EstadoExpediente();
												
												EstadoExpedienteId estadoExpedienteId = new EstadoExpedienteId();
												estadoExpedienteId.setIntEmpresaEstado(expEstado.getId().getIntPersEmpresaPk());
												estadoExpedienteId.setIntItemEstado(null);
												estadoExpediente.setId(estadoExpedienteId);
												
												estadoExpediente.setIntEmpresa(expEstado.getId().getIntPersEmpresaPk());
												estadoExpediente.setIntCuenta(expEstado.getId().getIntCuentaPk());
												estadoExpediente.setIntItemCuentaConcepto(null);
												estadoExpediente.setIntItemExpediente(expEstado.getId().getIntItemExpediente());
												estadoExpediente.setIntItemDetExpediente(expEstado.getId().getIntItemExpedienteDetalle());
												estadoExpediente.setIntParaEstadoExpediente(Constante.PARAM_T_ESTADO_EXPEDIENTE_CANCELADO);	
												estadoExpediente = conceptoFacade.grabarEstado(estadoExpediente);
								    	     }
							    	     }
									}   
			 	               }
								
			 }
	    	   
				 
				 LibroDiario diarioResult = libroDiarioFacade.grabarLibroDiario(diario); 
	    	     
	    	
	    	     
	    	   //Modificar el Aporte del Socio Titular o del Garante.
	    	   for (Expediente expediente : listaExpediente) { 
	    	      if(expediente.getStrDescripcion() != null && expediente.getStrDescripcion().equals("Aporte")){
	    	    	  actualizarMontoAporteDelSocioTitularOGarante(expediente,solicitudCtaCteTipo);
	    	      }
	    	   }
	    	  
	    	   //Obtiene Datos del Socio Titular o Garante.
  	    	   if (solicitudCtaCteTipo.getRadioOpcionTransferencias().equals(0)){
		       		intCuentaTitularOGarante  = solicitudCtaCteTipo.getSocioCompGarante().getCuenta().getId().getIntCuenta();
		       		intPersonaTitularOGarante = solicitudCtaCteTipo.getSocioCompGarante().getSocio().getId().getIntIdPersona();
		       		intEmpresaTitularOGarante = solicitudCtaCteTipo.getSocioCompGarante().getCuenta().getId().getIntPersEmpresaPk();
		       }
		       else{
		       		intCuentaTitularOGarante  =  solicitudCtaCteTipo.getSocioComp().getCuenta().getId().getIntCuenta();
		       		intPersonaTitularOGarante =  solicitudCtaCteTipo.getSocioComp().getSocio().getId().getIntIdPersona();
		    		intEmpresaTitularOGarante =  solicitudCtaCteTipo.getSocioComp().getCuenta().getId().getIntPersEmpresaPk();
			   }
  	    	   

	    	    for (Expediente expediente : listaExpediente) {	    		  
  
	    	    	generarMovimientoParaCadaTipoCptoGnral(expediente, solicitudCtaCteTipo, intPersona, intEmpresa, intCuenta, intPersonaTitularOGarante, intEmpresaTitularOGarante, intCuentaTitularOGarante);
    	    	 				     	
	    	    }
	    	  
		       //Actualiza los monto saldos del cronograma  del Socio
		     	if (solicitudCtaCteTipo.getIntMotivoSolicitud().equals(Constante.PARAM_T_TIPOTRANSFERENCIA_FALLECIMIENTO_SOCIO)){	   
			    	for (Expediente exp : listaExpediente) {
			    		     actualizaMontoSaldoCronogramaEnCero(exp,solicitudCtaCteTipo);
			    	}
			    }	
		    	else
		    	if (solicitudCtaCteTipo.getIntMotivoSolicitud().equals(Constante.PARAM_T_TIPOTRANSFERENCIA_SOCIO_NO_LABORA)){	   
				    for (Expediente exp : listaExpediente) {
				    		 actualizaMontoSaldoCronoPorCoutas(exp,solicitudCtaCteTipo);
				    }
				}			
		    	else	   
		    	if (solicitudCtaCteTipo.getIntMotivoSolicitud().equals(Constante.PARAM_T_TIPOTRANSFERENCIA_LICENCIA))	{   
		    		
		    		for (Expediente exp : listaExpediente) {			    				    			
			    			actualizaMontoSaldoCronogramaDelSocio(exp,solicitudCtaCteTipo);
			    		}
		    		}
	    		   	    	
		   return diarioResult;
		   
	      }catch(BusinessException e){
				throw e;
		  }catch(Exception e){
				throw new BusinessException(e);
		  }
	 }
	 
	
	
	 
	 private void generarMovimientoParaCadaTipoCptoGnral(Expediente expediente,
             SolicitudCtaCteTipo solicitudCtaCteTipo,
             Integer intPersona, 
             Integer intEmpresa,
             Integer intCuenta,
             Integer intPersonaTitularOGarante,
             Integer intEmpresaTitularOGarante,
             Integer intCuentaTitularOGarante) throws BusinessException{
		 
		 	 ConceptoFacadeRemote conceptoFacade = null;
		 	 CuentaConceptoId cuentaConceptoId = null;		 	 
		 	 CuentaConcepto cuentaConcepto = null;
		 	 CuentaConcepto cuentaConceptoTemporal = null;
		 	 CuentaConceptoDetalle conceptoDetalle = null;
		 	 BigDecimal a = new BigDecimal(0);
			 BigDecimal b = new BigDecimal(0);
			 List<ConceptoPago> listaConceptoPago = null;
			 EstadoExpediente estado = null;
		 try{
			 	conceptoFacade  = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);

			if (expediente.getStrDescripcion() != null && expediente.getStrDescripcion().equalsIgnoreCase("Aporte")){
			    Movimiento mov = new Movimiento();
			    
			    mov.setIntItemCuentaConcepto(Constante.CAPTACION_APORTACIONES);
			    mov.setTsFechaMovimiento(UtilCobranza.obtieneFechaActualEnTimesTamp());
			    mov.setIntParaTipoMovimiento(Constante.PARAM_T_TIPOMOVIMIENTO_TRANSFERENCIA);
			    mov.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCGENERAL); //?
			    mov.setIntPersPersonaIntegrante(intPersonaTitularOGarante);
			    mov.setIntPersEmpresa(intEmpresaTitularOGarante);
			    mov.setIntCuenta(intCuentaTitularOGarante);
			    mov.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_APORTACION);
			    mov.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_CARGO);
			    mov.setIntPersPersonaUsuario(solicitudCtaCteTipo.getIntPersUsuario());
			    mov.setIntPersEmpresaUsuario(solicitudCtaCteTipo.getIntEmpresa());			    
		        mov.setBdMontoMovimiento(expediente.getBdSaldoCreditoSoles());
		        
		        
		        BigDecimal bdSaldoAporte = new BigDecimal(0);
		        CuentaId cuentaId = new CuentaId();
		        cuentaId.setIntCuenta(intCuentaTitularOGarante);
		        cuentaId.setIntPersEmpresaPk(intEmpresaTitularOGarante);
		        List<CuentaConcepto> listaCtaCtp =  conceptoFacade.getListaCuentaConceptoPorPkCuenta(cuentaId);
		        if(listaCtaCtp != null && !listaCtaCtp.isEmpty()){
			        for (CuentaConcepto cuentaC : listaCtaCtp) {
						if (cuentaC.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_APORTACIONES)){
							if (cuentaC.getBdSaldo() == null ) cuentaC.setBdSaldo(new BigDecimal(0));
							bdSaldoAporte  = cuentaC.getBdSaldo().subtract(expediente.getBdSaldoCreditoSoles());
							break;
						}
					} 
		        }
		        mov.setBdMontoSaldo(bdSaldoAporte);
		       conceptoFacade.grabarMovimiento(mov);
		        
	   }	 

			 if (expediente.getIntParaTipoCreditoCod() == null){
				   Movimiento mov = new Movimiento();
	    			    mov.setTsFechaMovimiento(UtilCobranza.obtieneFechaActualEnTimesTamp());
					    mov.setIntParaTipoMovimiento(Constante.PARAM_T_TIPOMOVIMIENTO_TRANSFERENCIA);
					    mov.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCGENERAL); //?
					    mov.setIntPersPersonaUsuario(solicitudCtaCteTipo.getIntPersUsuario());
					    mov.setIntPersEmpresaUsuario(solicitudCtaCteTipo.getIntEmpresa());
				 if (expediente.getId().getIntItemExpediente() != null && expediente.getId().getIntItemExpediente().equals(Constante.CAPTACION_MANT_CUENTA)){
					    mov.setIntItemCuentaConcepto(Constante.CAPTACION_MANT_CUENTA); 
					    mov.setIntPersPersonaIntegrante(intPersona);
					    mov.setIntPersEmpresa(intEmpresa);
					    mov.setIntCuenta(intCuenta);
				        mov.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_MANTENIMIENTO);
				        mov.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
				        mov.setBdMontoMovimiento(expediente.getBdMontoAbono());				        
				        mov.setBdMontoSaldo(expediente.getBdSaldoCredito());       
				        mov = conceptoFacade.grabarMovimiento(mov);
				        expediente.setMovimiento(mov);
				        
				        CuentaId cuentaId = new CuentaId();
				        cuentaId.setIntCuenta(intCuentaTitularOGarante);
				        cuentaId.setIntPersEmpresaPk(intEmpresaTitularOGarante);
				        List<CuentaConcepto> listaCtaCtp =  conceptoFacade.getListaCuentaConceptoPorPkCuenta(cuentaId);
				        if(listaCtaCtp != null && !listaCtaCtp.isEmpty()){
				        	for (CuentaConcepto cuentaC : listaCtaCtp) {
									if (cuentaC.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_MANT_CUENTA)){
										 cuentaC.setBdSaldo(cuentaC.getBdSaldo().add(mov.getBdMontoMovimiento()));
										 cuentaConceptoTemporal = conceptoFacade.modificarCuentaConcepto(cuentaC);
										 if(cuentaConceptoTemporal != null){
											 	conceptoDetalle = new CuentaConceptoDetalle();
											 	conceptoDetalle.setId(new CuentaConceptoDetalleId());					
												conceptoDetalle.getId().setIntPersEmpresaPk(intEmpresa);
												conceptoDetalle.getId().setIntCuentaPk(intCuenta);
												conceptoDetalle.getId().setIntItemCuentaConcepto(cuentaConceptoTemporal.getId().getIntItemCuentaConcepto());
												conceptoDetalle.setIntParaTipoConceptoCod(Constante.CAPTACION_AHORROS);	
												conceptoDetalle = conceptoFacade.getCuentaConceptoDetallePorPkYTipoConcepto(conceptoDetalle);
												if(conceptoDetalle!=null){
													conceptoDetalle.setBdSaldoDetalle(cuentaConceptoTemporal.getBdSaldo());
													conceptoFacade.modificarCuentaConceptoDetalle(conceptoDetalle);
												} 
										 }
									}	
								
								}
				        	  	
				        }

				}  else
					   if (expediente.getId().getIntItemExpediente() != null && expediente.getId().getIntItemExpediente().equals(Constante.CAPTACION_FDO_RETIRO)){
					   		mov.setIntItemCuentaConcepto(Constante.CAPTACION_FDO_RETIRO);
					   		mov.setIntPersPersonaIntegrante(intPersona);
						    mov.setIntPersEmpresa(intEmpresa);
						    mov.setIntCuenta(intCuenta);
					        mov.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO);
					        mov.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
					        mov.setBdMontoMovimiento(expediente.getBdMontoAbono());
					        mov.setBdMontoSaldo(expediente.getBdSaldoCredito());					        
					        mov =  conceptoFacade.grabarMovimiento(mov);					        
					        expediente.setMovimiento(mov);
					        
					        CuentaId cuentaId = new CuentaId();
					        cuentaId.setIntCuenta(intCuentaTitularOGarante);
					        cuentaId.setIntPersEmpresaPk(intEmpresaTitularOGarante);
					        List<CuentaConcepto> listaCtaCtp =  conceptoFacade.getListaCuentaConceptoPorPkCuenta(cuentaId);
					        if(listaCtaCtp != null && !listaCtaCtp.isEmpty()){
					        	for (CuentaConcepto cuentaC : listaCtaCtp) {
										if (cuentaC.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_FDO_RETIRO)){
											 cuentaC.setBdSaldo(cuentaC.getBdSaldo().add(mov.getBdMontoMovimiento()));
											 cuentaConceptoTemporal = conceptoFacade.modificarCuentaConcepto(cuentaC);
											 if(cuentaConceptoTemporal != null){
												 	conceptoDetalle = new CuentaConceptoDetalle();
												 	conceptoDetalle.setId(new CuentaConceptoDetalleId());					
													conceptoDetalle.getId().setIntPersEmpresaPk(intEmpresa);
													conceptoDetalle.getId().setIntCuentaPk(intCuenta);
													conceptoDetalle.getId().setIntItemCuentaConcepto(cuentaConceptoTemporal.getId().getIntItemCuentaConcepto());
													conceptoDetalle.setIntParaTipoConceptoCod(Constante.CAPTACION_FDO_RETIRO);	
													conceptoDetalle = conceptoFacade.getCuentaConceptoDetallePorPkYTipoConcepto(conceptoDetalle);
													if(conceptoDetalle!=null){
														conceptoDetalle.setBdSaldoDetalle(cuentaConceptoTemporal.getBdSaldo());
														conceptoFacade.modificarCuentaConceptoDetalle(conceptoDetalle);
													} 
											 }
										}	
									
									}
					        	
					        }
					        

				}  else
					   if (expediente.getId().getIntItemExpediente() != null && expediente.getId().getIntItemExpediente().equals(Constante.CAPTACION_FDO_SEPELIO)){
					   		mov.setIntItemCuentaConcepto(Constante.CAPTACION_FDO_SEPELIO);
					   		mov.setIntPersPersonaIntegrante(intPersona);
						    mov.setIntPersEmpresa(intEmpresa);
						    mov.setIntCuenta(intCuenta);
					        mov.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDOSEPELIO);
					        mov.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
					        mov.setBdMontoMovimiento(expediente.getBdMontoAbono());
					        mov.setBdMontoSaldo(expediente.getBdSaldoCredito());
					        mov =  conceptoFacade.grabarMovimiento(mov);
					        expediente.setMovimiento(mov);
					        
					        CuentaId cuentaId = new CuentaId();
					        cuentaId.setIntCuenta(intCuentaTitularOGarante);
					        cuentaId.setIntPersEmpresaPk(intEmpresaTitularOGarante);
					        List<CuentaConcepto> listaCtaCtp =  conceptoFacade.getListaCuentaConceptoPorPkCuenta(cuentaId);
					        if(listaCtaCtp != null && !listaCtaCtp.isEmpty()){
					        	for (CuentaConcepto cuentaC : listaCtaCtp) {
										if (cuentaC.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_FDO_SEPELIO)){
											 cuentaC.setBdSaldo(cuentaC.getBdSaldo().add(mov.getBdMontoMovimiento()));
											 cuentaConceptoTemporal = conceptoFacade.modificarCuentaConcepto(cuentaC);
											 if(cuentaConceptoTemporal != null){
												 	conceptoDetalle = new CuentaConceptoDetalle();
												 	conceptoDetalle.setId(new CuentaConceptoDetalleId());					
													conceptoDetalle.getId().setIntPersEmpresaPk(intEmpresa);
													conceptoDetalle.getId().setIntCuentaPk(intCuenta);
													conceptoDetalle.getId().setIntItemCuentaConcepto(cuentaConceptoTemporal.getId().getIntItemCuentaConcepto());
													conceptoDetalle.setIntParaTipoConceptoCod(Constante.CAPTACION_FDO_SEPELIO);	
													conceptoDetalle = conceptoFacade.getCuentaConceptoDetallePorPkYTipoConcepto(conceptoDetalle);
													if(conceptoDetalle!=null){
														conceptoDetalle.setBdSaldoDetalle(cuentaConceptoTemporal.getBdSaldo());
														conceptoFacade.modificarCuentaConceptoDetalle(conceptoDetalle);
													}	 
											 }
										}	
									
									}
					        	  	
					        }
					      
				}
				 if(mov != null && mov.getIntItemCuentaConcepto()!= null ){						
						//grabado de conceptoPago y conceptopagodetalle
						java.sql.Date     fechaTrans    = new java.sql.Date(mov.getTsFechaMovimiento().getTime());
				         String  mesTransf     = UtilCobranza.obtieneMesCadena(fechaTrans);
				       	 String  anioTransf    = UtilCobranza.obtieneAnioCadena(fechaTrans);
				       	 Integer periodoTransf = new Integer(anioTransf+mesTransf);
						
						ConceptoPago conceptoPagoX = new ConceptoPago();
						conceptoPagoX.setId(new ConceptoPagoId());
						conceptoPagoX.getId().setIntPersEmpresaPk(intEmpresa);
						conceptoPagoX.getId().setIntCuentaPk(intCuenta);
						conceptoPagoX.getId().setIntItemCuentaConcepto(conceptoDetalle.getId().getIntItemCuentaConcepto());
						conceptoPagoX.getId().setIntItemCtaCptoDet(conceptoDetalle.getId().getIntItemCtaCptoDet());
						conceptoPagoX.setIntPeriodo(periodoTransf);
						conceptoPagoX.setBdMontoPago(new BigDecimal(0));
						conceptoPagoX.setBdMontoSaldo(conceptoDetalle.getBdMontoConcepto());
						conceptoPagoX.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						conceptoPagoX = conceptoFacade.grabarConceptoPago(conceptoPagoX);
						
						 a = mov.getBdMontoMovimiento();
						 
						 listaConceptoPago =  conceptoFacade.getListaConceptoPagoPorCuentaConceptoDet(conceptoDetalle.getId());
						if(listaConceptoPago != null && !listaConceptoPago.isEmpty()){
							for(ConceptoPago conceptoPago:listaConceptoPago){
								if(a.compareTo(new BigDecimal(0))==1){
									 if(conceptoPagoX.getIntPeriodo().compareTo(conceptoPago.getIntPeriodo())==1 || conceptoPagoX.getIntPeriodo().compareTo(conceptoPago.getIntPeriodo())==0){
										 if(conceptoPago.getBdMontoSaldo().compareTo(new BigDecimal(0)) == 1){
											 if(a.compareTo(conceptoPago.getBdMontoSaldo()) == 1){
												 a =a.subtract(conceptoPago.getBdMontoSaldo());
												 b = conceptoPago.getBdMontoSaldo();
												    conceptoPago.setBdMontoSaldo(new BigDecimal(0));
												    conceptoPago.setBdMontoPago(conceptoDetalle.getBdMontoConcepto());				              					
					              					conceptoFacade.modificarConceptoPago(conceptoPago);
					              					//grabar un conceptoDetallePago
					              					generarConceptoDetallePago(mov, conceptoPagoX, b);
											 }else if(a.compareTo(conceptoPago.getBdMontoSaldo()) == -1){											
												 conceptoPago.setBdMontoSaldo(conceptoPago.getBdMontoSaldo().subtract(a));
												 conceptoPago.setBdMontoPago(conceptoPago.getBdMontoPago().add(a));
												 conceptoFacade.modificarConceptoPago(conceptoPago);
												 b = a;
					              					a=new BigDecimal(0);
					              				//grabar un conceptoDetallePago
					              					generarConceptoDetallePago(mov, conceptoPagoX, b);
					              					break;
											 }else if(a.compareTo(conceptoPago.getBdMontoSaldo()) == 0){										 
					              				conceptoPago.setBdMontoPago(conceptoPago.getBdMontoPago().add(a));
					              				conceptoPago.setBdMontoSaldo(new BigDecimal(0));	
					              				conceptoFacade.modificarConceptoPago(conceptoPago);
					              				b = a;
					              				//grabar un conceptoDetallePago
					              				generarConceptoDetallePago(mov, conceptoPagoX, b);
					              					a=new BigDecimal(0);
					              					break;
											 }
										 }
									 }
								 }
							}							 
						}
			 }
			}else{
				if (expediente.getId().getIntItemExpediente()!=null && expediente.getId().getIntItemExpedienteDetalle() != null &&  expediente.getIntParaTipoCreditoCod() != null){
					   
								 Movimiento mov = new Movimiento();
								 mov.setIntItemExpediente(expediente.getId().getIntItemExpediente());
								 mov.setIntItemExpedienteDetalle(expediente.getId().getIntItemExpedienteDetalle());
								 mov.setTsFechaMovimiento(UtilCobranza.obtieneFechaActualEnTimesTamp());
								 mov.setIntPersEmpresa(intEmpresa);
								 mov.setIntCuenta(intCuenta);
								 mov.setIntPersPersonaIntegrante(intPersona);
								 mov.setIntParaTipoMovimiento(Constante.PARAM_T_TIPOMOVIMIENTO_TRANSFERENCIA);
								 mov.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCGENERAL); //?
								 mov.setStrNumeroDocumento(expediente.getId().getIntItemExpediente()+"-"+expediente.getId().getIntItemExpedienteDetalle());//?
								 mov.setIntPersPersonaUsuario(solicitudCtaCteTipo.getIntPersUsuario());
								 mov.setIntPersEmpresaUsuario(solicitudCtaCteTipo.getIntEmpresa());
								 
								if(expediente.getStrDescripcion()  != null && expediente.getStrDescripcion().equals("Amortización")){
												   mov.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION);
												   mov.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
												   mov.setBdMontoMovimiento(expediente.getBdMontoAbono());
												   BigDecimal bdMontoSaldo = expediente.getBdSaldoCredito().subtract(expediente.getBdMontoAbono());
												   mov.setBdMontoSaldo(bdMontoSaldo);
												   mov = conceptoFacade.grabarMovimiento(mov);
												   expediente.setMovimiento(mov);
										  }
								else
								if (expediente.getStrDescripcion() != null && expediente.getStrDescripcion().equals("Interes")){
										    	   mov.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES);
												   mov.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
												   mov.setBdMontoMovimiento(expediente.getBdMontoAbono());
												   BigDecimal bdMontoSaldo = expediente.getBdSaldoCredito().subtract(expediente.getBdMontoAbono());
												   mov.setBdMontoSaldo(bdMontoSaldo);
												   mov = conceptoFacade.grabarMovimiento(mov);
												   expediente.setMovimiento(mov);
								}else
								if (expediente.getStrDescripcion() != null && expediente.getStrDescripcion().equals("Mora")){
												   mov.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_MORA);
												   mov.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
												   mov.setBdMontoMovimiento(expediente.getBdMontoAbono());
												   BigDecimal bdMontoSaldo = expediente.getBdSaldoCredito().subtract(expediente.getBdMontoAbono());
												   mov.setBdMontoSaldo(bdMontoSaldo);
												   mov = conceptoFacade.grabarMovimiento(mov);
												   expediente.setMovimiento(mov);
								}
								
								if(mov.getIntParaTipoConceptoGeneral().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION) == 0){
									 if(mov.getIntParaTipoCargoAbono().compareTo(Constante.PARAM_T_CARGOABONO_CARGO) == 0){
										 expediente.setBdSaldoCredito(expediente.getBdSaldoCredito().add(mov.getBdMontoMovimiento()));
									 }else if(mov.getIntParaTipoCargoAbono().compareTo(Constante.PARAM_T_CARGOABONO_ABONO) == 0){
										 expediente.setBdSaldoCredito(expediente.getBdSaldoCredito().subtract(mov.getBdMontoMovimiento()));
									 } 
								 }else if(mov.getIntParaTipoConceptoGeneral().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES) == 0){
									 if(mov.getIntParaTipoCargoAbono().compareTo(Constante.PARAM_T_CARGOABONO_CARGO) == 0){
										 expediente.setBdSaldoInteres(expediente.getBdSaldoInteres().add(mov.getBdMontoMovimiento()));
									 }else if(mov.getIntParaTipoCargoAbono().compareTo(Constante.PARAM_T_CARGOABONO_ABONO) == 0){
										 expediente.setBdSaldoInteres(expediente.getBdSaldoInteres().subtract(mov.getBdMontoMovimiento()));
									 }
								 }				 
								 conceptoFacade.modificarExpediente(expediente);
								 
								 if(expediente.getBdSaldoCredito().compareTo(new BigDecimal(0)) == 0 										 
										 //&&	 expediente.getBdSaldoInteres().compareTo(new BigDecimal(0)) == 0 &&
										 //expediente.getBdSaldoMora().compareTo(new BigDecimal(0)) == 0
										 ){
									 	estado = new EstadoExpediente();
									 	estado.setId(new EstadoExpedienteId());
										estado.getId().setIntEmpresaEstado(intEmpresa);
										estado.setIntEmpresa(intEmpresa);
										estado.setIntCuenta(intCuenta);
										estado.setIntItemExpediente(expediente.getId().getIntItemExpediente());
										estado.setIntItemDetExpediente(expediente.getId().getIntItemExpedienteDetalle());
										estado.setIntParaEstadoExpediente(Constante.PARAM_T_ESTADO_EXPEDIENTE_VIGENTE);
										conceptoFacade.grabarEstado(estado);	
								 }
														  
					   }				    
			  }
			 }
			 catch(BusinessException e){
					throw e;
			 }catch(Exception e){
					throw new BusinessException(e);
			 }
			
		}									
			
	 
	private void actualizaMontoSaldoCronogramaDelSocio(Expediente exp,SolicitudCtaCteTipo solCtaCteTipo) throws BusinessException{
		ConceptoFacadeRemote conceptoFacade 	= null;
		Timestamp tsFechaInicio 				= null; 
		Timestamp tsFechaFin					= null;
		SolicitudPrestamoFacadeRemote solicitudPrestamofacade = null;
		List<EstadoCredito>  listaEstadoCredito = null;
		InteresProvisionado interesProvisionado = null;
		BigDecimal bdMontoInteres 				= null;
		Timestamp tsFechaFinal 					= null; 
		Expediente expedienteTemporal           = null;
		try{
			 conceptoFacade  = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			 solicitudPrestamofacade = (SolicitudPrestamoFacadeRemote)EJBFactory.getRemote(SolicitudPrestamoFacadeRemote.class);  
			 
		 if (exp.getStrDescripcion() != null){
			   Integer tipConceptoGeneral = 0;
			   if (exp.getStrDescripcion().equalsIgnoreCase("Amortización")){
				   tipConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION;//1
			   }
			   else
			   if (exp.getStrDescripcion().equalsIgnoreCase("Interes")){
				   tipConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES;   //2
			   }   
               if (exp.getStrDescripcion().equalsIgnoreCase("Mora")){
          	       tipConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_MORA;
			   }
             
	           Date    fechaTrans    = solCtaCteTipo.getDtFechaDocumento();
	           String  mesTransf     = UtilCobranza.obtieneMesCadena(fechaTrans);
	       	   String  anioTransf    = UtilCobranza.obtieneAnioCadena(fechaTrans);
	       	   Integer periodoTransf = new Integer(anioTransf+mesTransf);
	           BigDecimal a 	 = new BigDecimal(0); 
	           BigDecimal montoI = new BigDecimal(0); 
	         	 a = exp.getBdMontoAbono();
	         	 
	           if (exp.getId().getIntItemExpediente() != null && exp.getId().getIntItemExpedienteDetalle() != null){
	        	  
	        	 
	          	  List<Cronograma> lista = conceptoFacade.getListaCronogramaPorPkExpediente(exp.getId());
	          	  for (Cronograma cronograma : lista) {
	          		  if(a.compareTo(new BigDecimal(0))==1){
	          			  
	          		if (cronograma.getIntParaTipoConceptoCreditoCod().equals(tipConceptoGeneral)){ 
	          		  String  mesCron         = UtilCobranza.obtieneMesCadena(cronograma.getTsFechaVencimiento());
	           		  String  anioCron        = UtilCobranza.obtieneAnioCadena(cronograma.getTsFechaVencimiento());
	           		  Integer periodoCron  = new Integer(anioCron+mesCron);
	           		 	           			           		  
	           		  if (periodoCron.compareTo(periodoTransf) == -1 || periodoTransf.compareTo(periodoCron) == 0){	              		  
	              			  if(cronograma.getBdSaldoDetalleCredito().compareTo((new BigDecimal(0)))==1){
	              				  
	              				  if(a.compareTo(cronograma.getBdSaldoDetalleCredito())==1){
	              					
	              					a =a.subtract(cronograma.getBdSaldoDetalleCredito());
	              					cronograma.setBdSaldoDetalleCredito(new BigDecimal(0));
	              					conceptoFacade.modificarCronograma(cronograma);
	              				  }
	              				  else if(a.compareTo(cronograma.getBdSaldoDetalleCredito())==-1){
	              					cronograma.setBdSaldoDetalleCredito(cronograma.getBdSaldoDetalleCredito().subtract(a));
	              					conceptoFacade.modificarCronograma(cronograma);
	              					a=new BigDecimal(0);
	              					break;
	              				  }
	              				  else if(a.compareTo(cronograma.getBdSaldoDetalleCredito())==0){
	              					cronograma.setBdSaldoDetalleCredito(new BigDecimal(0));
	              					conceptoFacade.modificarCronograma(cronograma);
	              					a=new BigDecimal(0);
	              					break;
	              				  }
	              			  cronograma.setBdSaldoDetalleCredito(cronograma.getBdSaldoDetalleCredito().subtract(exp.getBdMontoAbono()));
	              			  conceptoFacade.modificarCronograma(cronograma);
	              			  } 
	              		  }
	           		  }	
	           		  
				  }
	           }
	          	  
	           }	  
	           //grabado en interescancelado    
	           	   if(exp.getMovimiento() != null){	
	        	   if(exp.getMovimiento().getIntParaTipoConceptoGeneral().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES)==0){
	        		   if (exp.getStrDescripcion().equalsIgnoreCase("Interes")){	        			   
	        			 				
	        			  tsFechaFin = exp.getMovimiento().getTsFechaMovimiento();	        			   
	        			  List<InteresCancelado> listaIC =  conceptoFacade.getListaInteresCanceladoPorExpedienteCredito(exp.getId());
	        			  if(listaIC != null && !listaIC.isEmpty()){
	        				  for(InteresCancelado interesCancelado: listaIC){
	        					  tsFechaInicio = interesCancelado.getTsFechaMovimiento();
	        					//sumar un dia
	      						tsFechaInicio = UtilCobranza.sumarUnDiaAFecha(tsFechaInicio);
	        				  }
	        			  }else{
	        				  	ExpedienteCreditoId pId = new ExpedienteCreditoId();
	        				  	pId.setIntPersEmpresaPk(exp.getId().getIntPersEmpresaPk());
	      						pId.setIntCuentaPk(exp.getId().getIntCuentaPk());
	      						pId.setIntItemExpediente(exp.getId().getIntItemExpediente());
	      						pId.setIntItemDetExpediente(exp.getId().getIntItemExpedienteDetalle());
	      						listaEstadoCredito = solicitudPrestamofacade.getListaEstadosPorExpedienteCreditoId(pId);
	      						if(listaEstadoCredito != null && !listaEstadoCredito.isEmpty()){
	        					  for(EstadoCredito estadoCredito:listaEstadoCredito){
	        						  if(estadoCredito.getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO) == 0){
	      								tsFechaInicio =  estadoCredito.getTsFechaEstado();
	      								break;
	      							}  
	        					  }
	        				  }
	        			  }  
	        			  java.sql.Date      dateFechaInicio   = new  java.sql.Date(tsFechaInicio.getTime());  
	        			  java.sql.Date      dateFechaFin      = new  java.sql.Date(tsFechaInicio.getTime());  
	        			  int intNumeroDias = UtilCobranza.obtenerDiasEntreFechas(dateFechaInicio, dateFechaFin);
			        	   InteresCancelado canceladoI = new InteresCancelado();
			        	   canceladoI.setId(new InteresCanceladoId());
			        	   canceladoI.getId().setIntPersEmpresaPk(exp.getId().getIntPersEmpresaPk());
			        	   canceladoI.getId().setIntCuentaPk(exp.getId().getIntCuentaPk());
			        	   canceladoI.getId().setIntItemExpediente(exp.getId().getIntItemExpediente());
			        	   canceladoI.getId().setIntItemExpedienteDetalle(exp.getId().getIntItemExpedienteDetalle());
			        	   canceladoI.getId().setIntItemMovCtaCte(exp.getMovimiento().getIntItemMovimiento());
			        	   canceladoI.setIntParaTipoFormaPago(Constante.PARAM_T_TIPOMOVIMIENTO_TRANSFERENCIA);
			        	   canceladoI.setBdSaldoCredito(exp.getBdSaldoCredito());
			        	   canceladoI.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			        	   canceladoI.setTsFechaInicio(tsFechaInicio);
			        	   canceladoI.setTsFechaMovimiento(tsFechaFin);
			        	   canceladoI.setIntDias(intNumeroDias);
			        	   canceladoI.setBdTasa(exp.getBdPorcentajeInteres());
			        	   montoI = exp.getBdMontoSaldoDetalle();
			        	   canceladoI.setBdMontoInteres(montoI);
			        	   conceptoFacade.grabarInteresCancelado(canceladoI);
			        	   
			        	   
			        	 //grabado en InteresProvisionado
			        	   tsFechaFinal =  UtilCobranza.obtieneFechaActualEnTimesTamp();
			        	   java.sql.Date      dateFechaInicioProvisionado   = new  java.sql.Date(tsFechaInicio.getTime());  
		        		   java.sql.Date      dateFechaFinProvisionado      = new  java.sql.Date(tsFechaFinal.getTime());  
		        		   int intNumeroDiasProvisionado = UtilCobranza.obtenerDiasEntreFechas(dateFechaInicioProvisionado, dateFechaFinProvisionado);
		        		   		        		   
		        			interesProvisionado = new InteresProvisionado();
		       				interesProvisionado.setId(new InteresProvisionadoId());
		       				interesProvisionado.getId().setIntPersEmpresaPk(exp.getId().getIntPersEmpresaPk());
		       				interesProvisionado.getId().setIntCuentaPk(exp.getId().getIntCuentaPk());
		       				interesProvisionado.getId().setIntItemExpediente(exp.getId().getIntItemExpediente());
		       				interesProvisionado.getId().setIntItemExpedienteDetalle(exp.getId().getIntItemExpedienteDetalle());
		       				interesProvisionado.setIntParaTipoMovInt(Constante.PARAM_T_ENVIADO_POR_PLANILLA);
		       				interesProvisionado.setTsFechaInicio(tsFechaInicio);
		       				interesProvisionado.setTsFechaFin(tsFechaFinal); 					
		       				interesProvisionado.setIntNumeroDias(intNumeroDiasProvisionado);
		       				interesProvisionado.setBdSaldoPrestamo(exp.getBdSaldoCredito());
		       				interesProvisionado.setBdTasaInteres(exp.getBdPorcentajeInteres());
		       			
		       				bdMontoInteres = exp.getBdPorcentajeInteres().multiply(exp.getBdSaldoCredito()).multiply(new BigDecimal(intNumeroDiasProvisionado));
		       				bdMontoInteres = exp.getBdPorcentajeInteres().multiply(exp.getBdSaldoCredito()).multiply(new BigDecimal(intNumeroDiasProvisionado));
							bdMontoInteres = bdMontoInteres.divide(new BigDecimal(3000),2,RoundingMode.HALF_UP); //a.divide(b, 2, RoundingMode.HALF_UP)	
		       				interesProvisionado.setBdMontoInteres(bdMontoInteres); 
		    				interesProvisionado.setBdMontoAtrasadoInteres(null);
		    				interesProvisionado.setBdMontoTotalInteres(null);
		    				interesProvisionado.setBdMontoSaldo(null);			
		    				interesProvisionado.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			        	    conceptoFacade.grabarInteresProvisionado(interesProvisionado);
			           }
	        	   }
	           	 }	

		   }
		 
	   }catch(BusinessException e){
			throw e;
	   }catch(Exception e){
			throw new BusinessException(e);
	   }
	}
	
	private void actualizaMontoSaldoCronogramaEnCero(Expediente exp,SolicitudCtaCteTipo solCtaCteTipo) throws BusinessException{
		ConceptoFacadeRemote conceptoFacade = null;
		
		try{
			 conceptoFacade  = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
	  	       
		 if (exp.getStrDescripcion() != null){
			   Integer tipConceptoGeneral = 0;
			   if (exp.getStrDescripcion().equalsIgnoreCase("Amortización")){
				   tipConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION;
			   }
		           
	           if (exp.getId().getIntItemExpediente() != null && exp.getId().getIntItemExpedienteDetalle() != null){
	          	  List<Cronograma> lista = conceptoFacade.getListaCronogramaPorPkExpediente(exp.getId());
	          	  for (Cronograma cronograma : lista) {
	          		 if (cronograma.getIntParaTipoConceptoCreditoCod().equals(tipConceptoGeneral)){
	              		  cronograma.setBdSaldoDetalleCredito(new BigDecimal(0));
	              		  conceptoFacade.modificarCronograma(cronograma);
	              	  }
	           		  	  
				  }
	           }
		  }
		 
	   }catch(BusinessException e){
			throw e;
	   }catch(Exception e){
			throw new BusinessException(e);
	   }
	}
	
	private void actualizaMontoSaldoCronoPorCoutas(Expediente exp,SolicitudCtaCteTipo solCtaCteTipo) throws BusinessException{
		ConceptoFacadeRemote conceptoFacade = null;
		try{
			 conceptoFacade  = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
		 if (exp.getStrDescripcion() != null){
			   Integer tipConceptoGeneral = 0;
			   if (exp.getStrDescripcion().equalsIgnoreCase("Amortización")){
				       tipConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION;
			   }
			   else
			   if (exp.getStrDescripcion().equalsIgnoreCase("Interes")){
					   tipConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES;   
			   }
			   else
	           if (exp.getStrDescripcion().equalsIgnoreCase("Mora")){
	          	       tipConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_MORA;
			   }
			   
			   if (exp.getId().getIntItemExpediente() != null && exp.getId().getIntItemExpedienteDetalle() != null){
	          	  List<Cronograma> lista = conceptoFacade.getListaCronogramaPorPkExpediente(exp.getId());
	          	  
	          	  
	          	//Ordena la lista por el numero de la cuota.
		        Collections.sort(lista, new Comparator() {  
		            public int compare(Object o1, Object o2) {  
		            	Cronograma e1 = (Cronograma) o1;  
		            	Cronograma e2 = (Cronograma) o2;  
		                return e1.getIntNumeroCuota().compareTo(e2.getIntNumeroCuota());
		            }  
		        });  
	          	  
		        BigDecimal montoAbono = exp.getBdMontoAbono();
	          	  
	          	for (Cronograma cronograma : lista) {
	          		 if (cronograma.getIntParaTipoConceptoCreditoCod().equals(tipConceptoGeneral)){
	          			  if (montoAbono.compareTo(new BigDecimal(0)) == 1){
	          				if (montoAbono.compareTo(cronograma.getBdSaldoDetalleCredito()) == 1 
	          					||montoAbono.compareTo(cronograma.getBdSaldoDetalleCredito()) == 0) {
	          					montoAbono  = montoAbono.subtract(cronograma.getBdSaldoDetalleCredito());
	          					cronograma.setBdSaldoDetalleCredito(new BigDecimal(0));
		              		    conceptoFacade.modificarCronograma(cronograma);
	          				}else{
	          					BigDecimal saldoDetCredito = cronograma.getBdSaldoDetalleCredito().subtract(montoAbono);
	          					montoAbono = new BigDecimal(0);
	          					cronograma.setBdSaldoDetalleCredito(saldoDetCredito);
		              		    conceptoFacade.modificarCronograma(cronograma);
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
	}
	 
	private void actualizarMontoAporteDelSocioTitularOGarante(Expediente exp,
			                                              SolicitudCtaCteTipo solCtaCteTipo
			                                              ) throws BusinessException{
		
		ConceptoFacadeRemote conceptoFacade = null;
       	List<CuentaConcepto> lista          = null;
       	CuentaConcepto cuentaConceptoo      = null;
       	Integer intCuentaTitularOGarante    = null;    
  	    Integer intPersonaTitularOGarante   = null;    
	    Integer intEmpresaTitularOGarante   = null;	  
  	   
       try{
    	   conceptoFacade  = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
  	       	//0 es Transfereencia entre cuentas y 1 es transferencia entre conceptos.
	    	   
	       	if (solCtaCteTipo.getRadioOpcionTransferencias().equals(0)){
	       		intCuentaTitularOGarante  = solCtaCteTipo.getSocioCompGarante().getCuenta().getId().getIntCuenta();
	       		intPersonaTitularOGarante = solCtaCteTipo.getSocioCompGarante().getSocio().getId().getIntIdPersona();
	       		intEmpresaTitularOGarante = solCtaCteTipo.getSocioCompGarante().getCuenta().getId().getIntPersEmpresaPk();
	            lista = conceptoFacade.getListaCuentaConceptoPorPkCuenta(solCtaCteTipo.getSocioCompGarante().getCuenta().getId());
	       	}
	       	else{
	       		
	       		intCuentaTitularOGarante  =   solCtaCteTipo.getSocioComp().getCuenta().getId().getIntCuenta();
	       		intPersonaTitularOGarante =  solCtaCteTipo.getSocioComp().getSocio().getId().getIntIdPersona();
	    		intEmpresaTitularOGarante =  solCtaCteTipo.getSocioComp().getCuenta().getId().getIntPersEmpresaPk();
			       
	            lista = conceptoFacade.getListaCuentaConceptoPorPkCuenta(solCtaCteTipo.getSocioComp().getCuenta().getId());
		    }
	       	
	       	for (CuentaConcepto cuentaConcepto : lista) {
	    		if (cuentaConcepto.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_APORTACIONES)){
	       			cuentaConceptoo = cuentaConcepto;
	       			break;
	       		}
			}
	       	if (cuentaConceptoo == null){
	       		throw new BusinessException("No existe el Concepto Aporte.");
	       	}
	       	
	       	if (cuentaConceptoo != null && cuentaConceptoo.getBdSaldo() != null && 
	       		(cuentaConceptoo.getBdSaldo().compareTo(exp.getBdSaldoCreditoSoles()) == 1) || 
	       		 cuentaConceptoo.getBdSaldo().compareTo(exp.getBdSaldoCreditoSoles()) == 0){
	       		BigDecimal bdSaldo = cuentaConceptoo.getBdSaldo().subtract(exp.getBdSaldoCreditoSoles());
	       		cuentaConceptoo.setBdSaldo(bdSaldo);
	       		conceptoFacade.modificarCuentaConcepto(cuentaConceptoo);
	       		//Actualizar cuentaconceptoDetalle su saldoDetalle
				//1 traer el objeto por cuenta, empresa, itemcuentaconcepto
				CuentaConceptoId  cuentaConceptoId = new CuentaConceptoId();
				cuentaConceptoId.setIntCuentaPk(cuentaConceptoo.getId().getIntCuentaPk());
				cuentaConceptoId.setIntPersEmpresaPk(cuentaConceptoo.getId().getIntPersEmpresaPk());
				cuentaConceptoId.setIntItemCuentaConcepto(Constante.CAPTACION_APORTACIONES);
				List<CuentaConceptoDetalle>	cuentaConceptoDetalle = conceptoFacade.getListaCuentaConceptoDetallePorCuentaConcepto(cuentaConceptoId); 
				CuentaConceptoDetalle cuentaConceptoDetallebb = cuentaConceptoDetalle.get(0);
				//2 modificar CuentaConceptoDetalle 
				cuentaConceptoDetallebb.setBdSaldoDetalle(bdSaldo);
				conceptoFacade.modificarCuentaConceptoDetalle(cuentaConceptoDetallebb);
	       		
	       	}
       	
		 }catch(BusinessException e){
				throw e;
		 }catch(Exception e){
				throw new BusinessException(e);
	  }
	}
 	 
	private ModeloDetalle obtieneElCodigoAsientoyNroCtaDelPlanCuentaActual(ModeloId idModelo,
			                                                               Integer  intTipoCategRiesgo, 
			                                                               Integer  valorFijo,
			                                                               Integer  intTipoCptoGeneral,
			                                                               Integer  intParaTipoConcepto,
			                                                               Integer  intTipoCreditoEmpresa) throws BusinessException{
		ModeloDetalle modeloDetalleResult = null;
		
		boolean existeModeloContablePorCptoGnral      = false;
		boolean existeModeloContablePorTipoCreEmp     = false;
		boolean existeModeloContablePorTipoCpto  = false;
		boolean existeModeloContablePorCategRiesgo    = false;
		try{
		        ModeloFacadeRemote	modeloFacade  = (ModeloFacadeRemote)EJBFactory.getRemote(ModeloFacadeRemote.class);
		        
				Integer intPeriodoCuenta = UtilCobranza.obtieneAnio(new Date());
				List<ModeloDetalle> listaModDet =  modeloFacade.getListaModeloDetallePorModeloId(idModelo);

				//Busca model contable por categoria el tipo concepto general.
				for (ModeloDetalle modeloDetalle : listaModDet) {
					if (modeloDetalle.getId().getIntContPeriodoCuenta().equals(intPeriodoCuenta)){
		    			List<ModeloDetalleNivel> listaModDetNiv = modeloFacade.getListaModeloDetNivelPorModeloDetalleId(modeloDetalle.getId());
						for (ModeloDetalleNivel modeloDetalleNivel : listaModDetNiv) {
							
							if (modeloDetalleNivel.getIntValor() != null && valorFijo != null && valorFijo.equals(modeloDetalleNivel.getIntValor())){
								existeModeloContablePorCptoGnral = true;
								break;
							}
							else
							if (modeloDetalleNivel.getIntDatoTablas()    != null && modeloDetalleNivel.getIntDatoTablas().equals(new Integer(Constante.PARAM_T_TIPOCONCEPTOGENERAL)) &&
								modeloDetalleNivel.getIntDatoArgumento() != null && modeloDetalleNivel.getIntDatoArgumento().equals(intTipoCptoGeneral)){
								existeModeloContablePorCptoGnral = true;
								break;
							}
						}
						
						if (existeModeloContablePorCptoGnral){
							modeloDetalleResult = modeloDetalle;
							
							if (valorFijo != null){
								modeloDetalleResult = modeloDetalle;
							}
							else
					        if (intTipoCptoGeneral != null && intParaTipoConcepto != null){
									List<ModeloDetalleNivel> listaModDetNiv2 = modeloFacade.getListaModeloDetNivelPorModeloDetalleId(modeloDetalleResult.getId());
									for (ModeloDetalleNivel modeloDetalleNivel : listaModDetNiv2) {
										if (modeloDetalleNivel.getIntDatoTablas()    != null && modeloDetalleNivel.getIntDatoTablas().equals(new Integer(Constante.PARAM_T_TIPOCUENTA)) &&
											modeloDetalleNivel.getIntDatoArgumento() != null && modeloDetalleNivel.getIntDatoArgumento().equals(intParaTipoConcepto)){
											existeModeloContablePorTipoCpto = true;
											break;
										}
									}
									
									if (existeModeloContablePorCptoGnral && existeModeloContablePorTipoCpto){
									}else{
											modeloDetalleResult = null;
									}
							}
							else
							{
								if (valorFijo == null &&  modeloDetalleResult != null){
									//Busca modelo contable por Tipo Credito de Empresa
									if (intTipoCreditoEmpresa != null){
										List<ModeloDetalleNivel> listaModDetNiv2 = modeloFacade.getListaModeloDetNivelPorModeloDetalleId(modeloDetalleResult.getId());
										for (ModeloDetalleNivel modeloDetalleNivel : listaModDetNiv2) {
											if (modeloDetalleNivel.getIntDatoTablas()    != null && modeloDetalleNivel.getIntDatoTablas().equals(new Integer(Constante.PARAM_T_TIPOCREDITOEMPRESA)) &&
												modeloDetalleNivel.getIntDatoArgumento() != null && modeloDetalleNivel.getIntDatoArgumento().equals(intTipoCreditoEmpresa)){
												existeModeloContablePorTipoCreEmp = true;
												break;
											}
										}
										
									}
								    //Busca modelo contable por Categoria de Riesgo
									List<ModeloDetalleNivel> listaModDetNivel = modeloFacade.getListaModeloDetNivelPorModeloDetalleId(modeloDetalleResult.getId());
									for (ModeloDetalleNivel modeloDetalleNivel : listaModDetNivel) {
										if (modeloDetalleNivel.getIntDatoTablas()    != null && modeloDetalleNivel.getIntDatoTablas().equals(new Integer(Constante.PARAM_T_TIPOCATEGORIADERIESGO)) &&
											modeloDetalleNivel.getIntDatoArgumento() != null && modeloDetalleNivel.getIntDatoArgumento().equals(intTipoCategRiesgo)){
											existeModeloContablePorCategRiesgo = true;
											break;
										}
									}
									if (existeModeloContablePorTipoCreEmp && existeModeloContablePorCptoGnral && existeModeloContablePorCategRiesgo){
									}else{
											modeloDetalleResult = null;
									}
								}	
							}
					        
					        if (modeloDetalleResult != null){
								break;
							}
						}
						
						
					}
				}
				
				
				
		 }catch(BusinessException e){
				throw e;
		 }catch(Exception e){
				throw new BusinessException(e);
	     }
		  
		return modeloDetalleResult;
	}
	
	
	/**
	 * Genara los Libro Diario Detalle en base a los modelos detalle
	 * @param idModelo
	 * @param intTipoCategRiesgo
	 * @param valorFijo
	 * @param intTipoCptoGeneral
	 * @param intParaTipoConcepto
	 * @param intTipoCreditoEmpresa
	 * @param expediente
	 * @return
	 * @throws BusinessException
	 */
	//diario = obtieneLibroDiarioYDiarioDetalleRefinanciamiento(modelo.getId(), intTipoCategRiesgo, intTipoCreditoEmpresa, expedienteMovAnterior,expedienteCreditoNuevo,intEmpresa);

	private LibroDiario obtieneLibroDiarioYDiarioDetalleRefinanciamiento_3(ModeloId idModelo, Integer  intTipoCategRiesgo,  
																		Integer intTipoCreditoEmpresa, Expediente expedienteMovAnterior, 
																		ExpedienteCredito expedienteCreditoNuevo,
																		SolicitudCtaCteTipo solicitudCtaCteTipo, Usuario usuario) throws BusinessException{

		BigDecimal bdValorColumna = null;
		LibroDiario diario = null;
		List<LibroDiarioDetalle> lstDiarioDetalle = new ArrayList<LibroDiarioDetalle>();
		
		try{
			ModeloFacadeRemote	modeloFacade  = (ModeloFacadeRemote)EJBFactory.getRemote(ModeloFacadeRemote.class);
			LibroDiarioFacadeRemote libroDiarioFacade= (LibroDiarioFacadeRemote)EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			Integer intPeriodoCuenta = UtilCobranza.obtieneAnio(new Date());
			Integer anio = UtilCobranza.obtieneAnio(new Date());
			String  mes  = UtilCobranza.obtieneMesCadena(new Date());
			
			List<ModeloDetalle> listaModDet =  modeloFacade.getListaModeloDetallePorModeloId(idModelo);

			diario = new LibroDiario();
			diario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());

			diario.getId().setIntContPeriodoLibro(new Integer(anio+""+mes));
			diario.getId().setIntPersEmpresaLibro(solicitudCtaCteTipo.getSocioComp().getCuenta().getId().getIntPersEmpresaPk());
			diario.setStrGlosa("Asiento PRUEBA de Transferencia - Libro Diario - "+new Date().getTime());
			diario.setTsFechaRegistro(new Timestamp((new Date()).getTime())); //la fecha actual
			diario.setTsFechaDocumento(new Timestamp((new Date()).getTime()));
			diario.setIntPersEmpresaUsuario(solicitudCtaCteTipo.getIntEmpresa());
			diario.setIntPersPersonaUsuario(solicitudCtaCteTipo.getIntPersUsuario());
			diario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCGENERAL);
			diario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			diario.setIntPersEmpresaUsuario(usuario.getEmpresa().getIntIdEmpresa());
			diario.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			 
			 
			//Modelo detalle
			for (ModeloDetalle modeloDetalle : listaModDet) {
				bdValorColumna= BigDecimal.ZERO;
				boolean blnExisteRiesgo = Boolean.FALSE;
				boolean blnExisteCredito = Boolean.FALSE;
				// modelo detalle
				if (modeloDetalle.getId().getIntContPeriodoCuenta().equals(intPeriodoCuenta)){
					LibroDiarioDetalle diarioDet = new LibroDiarioDetalle();
					diarioDet.setId(new LibroDiarioDetalleId());

					List<ModeloDetalleNivel> listaModDetNiv = null;
					listaModDetNiv = modeloFacade.getListaModeloDetNivelPorModeloDetalleId(modeloDetalle.getId());
					
					if(listaModDetNiv != null){
						// Niveles del detalle modelo
						for (ModeloDetalleNivel modeloDetalleNivel : listaModDetNiv) {

							// Es valor fijo
							if(modeloDetalleNivel.getIntParaTipoRegistro().compareTo(Constante.PARAM_T_MODELO_TIPO_REGISTRO_FIJO)==0){

								if(modeloDetalleNivel.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_REFINANCIAMIENTO_MONTO_SOLICITADO)){
									if(expedienteMovAnterior.getBdMontoSolicitado() != null){
										bdValorColumna = expedienteMovAnterior.getBdMontoSolicitado();	
										blnExisteRiesgo = Boolean.TRUE;
										//diarioDet = new LibroDiarioDetalle();
									}
									
								}
								if(modeloDetalleNivel.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_REFINANCIAMIENTO_MONTO_TOTAL)){
									if(expedienteMovAnterior.getBdMontoTotal() != null){
										bdValorColumna = expedienteCreditoNuevo.getBdMontoTotal();
										blnExisteRiesgo = Boolean.TRUE;
										//diarioDet = new LibroDiarioDetalle();
									}
									
								}
								if(modeloDetalleNivel.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_REFINANCIAMIENTO_MONTO_INTERES_ATRASADO)){
									if(expedienteMovAnterior.getBdMontoInteresAtrazado()!= null){
										bdValorColumna = expedienteMovAnterior.getBdMontoInteresAtrazado();
										//diarioDet = new LibroDiarioDetalle();
									}
									
								}
								if(modeloDetalleNivel.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_REFINANCIAMIENTO_MONTO_MORA_ATRASADA)){
									if(expedienteMovAnterior.getBdMontoMoraAtrazado()!=null){
										bdValorColumna = expedienteMovAnterior.getBdMontoMoraAtrazado();
										//diarioDet = new LibroDiarioDetalle();
									}
									
								}
								
								
							// Es tabla	
							}else if(modeloDetalleNivel.getIntParaTipoRegistro().compareTo(Constante.PARAM_T_MODELO_TIPO_REGISTRO_TABLA)==0){
								//-- dato tablas  140 169    datoargumaneto  123456879   valor 1  tiporegistro 1 2 
									if(modeloDetalleNivel.getIntDatoTablas().compareTo(new Integer(Constante.PARAM_T_TIPOCATEGORIADERIESGO))==0){
										if(modeloDetalleNivel.getIntDatoArgumento().compareTo(intTipoCategRiesgo)==0){
											// existe riesgo
											blnExisteRiesgo = Boolean.TRUE;
										}
										
									}
									if(modeloDetalleNivel.getIntDatoTablas().compareTo(new Integer(Constante.PARAM_T_TIPOCREDITOEMPRESA))==0){
										if(modeloDetalleNivel.getIntDatoArgumento().compareTo(intTipoCreditoEmpresa)==0){
											// existe credito empresa
											blnExisteCredito = Boolean.TRUE;
										}
										
									}	
							}
						}

						diarioDet.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
						
						// termionamos de formar el libro diario detalle
						if(blnExisteRiesgo && blnExisteCredito){
							diarioDet.setStrContNumeroCuenta(modeloDetalle.getId().getStrContNumeroCuenta());
							//diarioDet.set
							

							if(modeloDetalle.getIntParaOpcionDebeHaber().compareTo(Constante.PARAM_T_LIBRO_DIARIO_DEBE)==0){
								diarioDet.setIntParaDocumentoGeneral(modeloDetalle.getIntParaDocumentoGral());
								diarioDet.setBdDebeSoles(bdValorColumna);
								diarioDet.setBdHaberSoles(null);
								// debe  bdValorColumna
							}else if(modeloDetalle.getIntParaOpcionDebeHaber().compareTo(Constante.PARAM_T_LIBRO_DIARIO_HABER)==0){
								// haber   bdValorColumna
								diarioDet.setIntParaDocumentoGeneral(modeloDetalle.getIntParaDocumentoGral());
								diarioDet.setBdHaberSoles(bdValorColumna);
								diarioDet.setBdDebeSoles(null);
							}
							lstDiarioDetalle.add(diarioDet);

						}else{
								System.out.println("No recupera el riesgo y/o credito.  ");
							// No cumple con el riesgo y el tipo de credito
							
						}
						
						
					}else{
						throw new BusinessException("No existen los niveles.");
					}
	

				}
			}
			
			diario.setListaLibroDiarioDetalle(lstDiarioDetalle);
			diario = libroDiarioFacade.grabarLibroDiario(diario);

		}catch(BusinessException e){
			System.out.println("Error en obtieneLibroDiarioDetalleRefinanciamiento 1 --> "+e);
			throw e;
		}catch(Exception e){
			System.out.println("Error en obtieneLibroDiarioDetalleRefinanciamiento 2 --> "+e);
			throw new BusinessException(e);
		}
		
		//lstDiarioDetalle
		return diario;
	}
	
	
	private void eliminarSocioDelEnvioPlnailla(SocioEstructura socioEstructuraOrigen,
			                                   SocioEstructura socioEstructuraOrigenNueva, 
			                                   List<Envioconcepto>  lista,
			                                   Integer intPeriodo) throws BusinessException{
		
		
		 for (Envioconcepto envioconcepto : lista) {
			 EnviomontoBO boEnvioMonto = (EnviomontoBO)TumiFactory.get(EnviomontoBO.class);
			 
			 Integer  pIntTiposocioCod	  = socioEstructuraOrigen.getIntTipoSocio();
			 Integer  pIntModalidadCod 	  = socioEstructuraOrigen.getIntModalidad(); 
			 Integer  pIntPeriodoplanilla = intPeriodo;
			 Integer  pIntEstadoCod 	  = Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO;
			  
			 SucursalId idSucursal = new  SucursalId();
			 
			 //Lista Envio de planilla de  la actual Entidad.
			List<Enviomonto> listaEnvioMonto =  boEnvioMonto.getListaEnviomontoDeBuscar(idSucursal, pIntTiposocioCod, pIntModalidadCod, pIntPeriodoplanilla, pIntEstadoCod);
			
			
			if (listaEnvioMonto == null){
			     throw new BusinessException("No existe data para el Envio Monto para entidad.");
			}
			
			BigDecimal bdMontoResumen = new BigDecimal(0);
			BigDecimal bdMontoInfladaResumen = null;
			
			
			for (Enviomonto enviomonto : listaEnvioMonto) {
				  Integer intItemenvioconcepto    = envioconcepto.getId().getIntItemenvioconcepto();
				  Integer intItemenvioconceptoMto = enviomonto.getId().getIntItemenvioconcepto();
				  Integer intTipoEstructura       = enviomonto.getIntTipoestructuraCod();
				  Integer intNivelOrigen 		  = socioEstructuraOrigen.getIntNivel();
				  Integer intCodigoOrigen         = socioEstructuraOrigen.getIntCodigo();
				  Integer intNivelEnv 		      = enviomonto.getIntNivel();
				  Integer intCodigoEnv            = enviomonto.getIntCodigo();
				  
			if(intItemenvioconcepto.equals(intItemenvioconceptoMto) && intTipoEstructura.equals(socioEstructuraOrigen.getIntTipoEstructura())&&
						intNivelOrigen.equals(intNivelEnv) && intCodigoEnv.equals(intCodigoOrigen)){
					
					//Eliminamos 
					enviomonto.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					enviomonto.setIntPersonaeliminaPk(socioEstructuraOrigenNueva.getIntPersonaUsuario());
					enviomonto.setIntEmpresaeliminaPk(socioEstructuraOrigenNueva.getIntEmpresaUsuario());
					enviomonto.setTsFechaeliminacion(UtilCobranza.obtieneFechaActualEnTimesTamp());
					boEnvioMonto.modificarEnviomonto(enviomonto);
					
					
						
					bdMontoResumen = enviomonto.getBdMontoenvio(); 
						 
						EnvioinfladaBO boEnvioinflada = (EnvioinfladaBO)TumiFactory.get(EnvioinfladaBO.class);
						Integer pIntItemenvioconcepto = enviomonto.getId().getIntItemenvioconcepto();
						Integer pIntItemenviomonto    = enviomonto.getId().getIntItemenviomonto();
						Integer pIntEmpresacuentaPk   = enviomonto.getId().getIntEmpresacuentaPk();
						
						List<Envioinflada> listaEnvioinflada = boEnvioinflada.getListaPorEnvioMonto(pIntItemenvioconcepto, pIntItemenviomonto, pIntEmpresacuentaPk);
						
						
						if (listaEnvioinflada != null && listaEnvioinflada.size() > 1){
	    				     throw new BusinessException("Hay mas de un registro en tabla Envio Inflada. ItemEnvioConcepto:"+pIntItemenvioconcepto);
	    				 }
						//Elimiando monto inflado
						
	    				 for (Envioinflada envioinflada : listaEnvioinflada) {
							  
	    					 //Eliminais Monto Inflado
		    				 envioinflada.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
		    				 boEnvioinflada.modificarEnvioinflada(envioinflada);
		    				 bdMontoInfladaResumen = envioinflada.getBdMonto();
						}
	    				 
	    				 
	    				 //Restammos monto resumen y el monto inflado del anterior entidad
	    				 EnvioresumenBO boEnvioresumen = (EnvioresumenBO)TumiFactory.get(EnvioresumenBO.class);
	    				   Integer pIntEmpresaPk 	   = socioEstructuraOrigenNueva.getIntEmpresaUsuario();
	    			       Integer pIntPeriodoplanillaOrigen = intPeriodo;
	    			       Integer pIntTiposocioCodOrigen    = socioEstructuraOrigen.getIntTipoSocio();
	    				   Integer pIntModalidadCodOrigen    = socioEstructuraOrigen.getIntModalidad();
	    				   Integer pIntNivelOrigen           = socioEstructuraOrigen.getIntNivel();
	    				   Integer pIntCodigo                = socioEstructuraOrigen.getIntCodigo();
	    				  
	    				 Integer nroAfectados = null;
	    				     					 
	    				 List<Envioresumen> listaEnvResumen = boEnvioresumen.getListaPorEnitdadyPeriodo(pIntEmpresaPk, pIntPeriodoplanillaOrigen, pIntTiposocioCodOrigen, pIntModalidadCodOrigen, pIntNivelOrigen, pIntCodigo);
	    				 
	    				 if (listaEnvResumen == null || listaEnvResumen.size() == 0){
	    				     throw new BusinessException("No existe el envio resumen para la entidad.");
	    				 }
	    				 
	    				 if (listaEnvResumen != null && listaEnvResumen.size() > 1){
	    				     throw new BusinessException("Existe mas de un registro envio resumen para la entidad.");
	    				 }
	    				 
	    				 
	    				 for (Envioresumen envioresumen : listaEnvResumen) {
	    					  BigDecimal bdMontototalNuevo = envioresumen.getBdMontototal().subtract(bdMontoResumen);
	    					  nroAfectados = envioresumen.getIntNumeroafectados()-1;
	    					  
	    					  if (bdMontoInfladaResumen != null){
	    						  if (envioresumen.getBdMontototalinflada() != null){
	    							     //Restamos en monto total del envio resumen
	    						         BigDecimal    bdMontototalinflada = envioresumen.getBdMontototalinflada().subtract(bdMontoInfladaResumen);
	    					             envioresumen.setBdMontototalinflada(bdMontototalinflada);
	    					             //Restamos los numero de afectados
	    						  }else{
	    							  throw new BusinessException("Data Incosistente Monto Inflada no existe en el envio Resumen.");
	    						  }
	    					  }
	    					  
	    					  envioresumen.setBdMontototal(bdMontototalNuevo);
	    					  envioresumen.setIntNumeroafectados(nroAfectados);
	    					  
	    					  boEnvioresumen.modificarEnvioresumen(envioresumen);
	    					  
						 }	
	    			}
				
			}
			
				
		}
		
		
	}
	 
	
    private void cambiarGarante(SolicitudCtaCteTipo solicitudCtaCteTipo) throws BusinessException{
      try{
    	
    	    GarantiaCreditoFacadeRemote garantiaCreditoFacade = (GarantiaCreditoFacadeRemote)EJBFactory.getRemote(GarantiaCreditoFacadeRemote.class);
    	 
    	     List<GarantiaCreditoComp> listGarantiaCre =  solicitudCtaCteTipo.getListaGarantiaCreditoComp();
    	  
    	     for (GarantiaCreditoComp garantiaCreditoComp : listGarantiaCre) {
    	    	 
    	    	 GarantiaCreditoId garantiaCreditoId = new  GarantiaCreditoId();
    	    	 garantiaCreditoId = garantiaCreditoComp.getGarantiaCredito().getId();
    	    	 GarantiaCredito garantiaCredito = garantiaCreditoFacade.getGarantiaCredito(garantiaCreditoId);
    	    	 
    	    	 if(garantiaCreditoComp.getChecked() != null && garantiaCreditoComp.getChecked()){
    	    		 
    	    		 //Eliminado Garante.
    	    		 if (garantiaCreditoComp.getSocioComp() == null && garantiaCreditoComp.getGaranteComp() == null){
    	    			 
    	    			 garantiaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
    	    			 Timestamp tsFechaEliminacion = UtilCobranza.obtieneFechaActualEnTimesTamp();
    	    			 garantiaCredito.setTsFechaEliminacion(tsFechaEliminacion);
    	    			 garantiaCredito.setIntPersPersonaEliminaPk(solicitudCtaCteTipo.getIntPersUsuario());
    	    			 garantiaCreditoFacade.modificarGarantiaCredito(garantiaCredito);
    	    			 
    	    		 }
    	    		 
    	    		 //Reemplaza Garante por Otro.
					 if (garantiaCreditoComp.getSocioComp() != null && garantiaCreditoComp.getGaranteComp() != null){
						  //Eliminar Garante
						  garantiaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
						  Timestamp tsFechaEliminacion = UtilCobranza.obtieneFechaActualEnTimesTamp();
	    	    		  garantiaCredito.setTsFechaEliminacion(tsFechaEliminacion);
	    	    		  garantiaCredito.setIntPersPersonaEliminaPk(solicitudCtaCteTipo.getIntPersUsuario());
    	    			  garantiaCreditoFacade.modificarGarantiaCredito(garantiaCredito);
    	    			  //Crear Garante
    	    			  garantiaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
    	    			  garantiaCredito.setTsFechaEliminacion(null);
	    	    		  garantiaCredito.setIntPersPersonaEliminaPk(null);
    	    			  garantiaCredito.setIntPersPersonaGarantePk(garantiaCreditoComp.getGaranteComp().getSocio().getId().getIntIdPersona());
    	    			  garantiaCredito.setIntPersCuentaGarantePk(garantiaCreditoComp.getGaranteComp().getCuenta().getId().getIntCuenta());
    	    			  Timestamp tsFechaRegistro = UtilCobranza.obtieneFechaActualEnTimesTamp();
	    	    		  garantiaCredito.setTsFechaRegistro(tsFechaRegistro);
	    	    		  garantiaCredito.setIntPersPersonaUsuarioPk(solicitudCtaCteTipo.getIntPersUsuario());
    	    			  garantiaCreditoFacade.grabarGarantiaCredito(garantiaCredito);
					 }
    	    		 
    	    		 
    	    	 }
    	    	 
    	    	 SolicitudCtaCteTipo resSolCtaTip = boSolicitudCtaCteTipo.getSolicitudCtaCteTipoPorPk(solicitudCtaCteTipo.getId());
			     if (resSolCtaTip == null){
			        	 solicitudCtaCteTipo.setIntParaTipoorigen(0);
			        	 boSolicitudCtaCteTipo.grabarSolicitudCtaCteTipo(solicitudCtaCteTipo);
			     }
			     else{
			             boSolicitudCtaCteTipo.modificarSolicitudCtaCteTipo(solicitudCtaCteTipo);
			     }
    	    	 
   		   }
    	    
         }catch(BusinessException e){
				throw e;
		 }catch(Exception e){
				throw new BusinessException(e);
		 }
    	
    }
	
   private boolean bloquearTipoSolicitudLicencia(SolicitudCtaCteTipo solicitudCtaCteTipo) throws BusinessException{
	   
	   try{
		   
		   ConceptoFacadeRemote conceptoFacadeRemote = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			
		   List<CuentaConcepto> lista = solicitudCtaCteTipo.getCuenta().getListaConcepto();
		   for (CuentaConcepto cuentaConcepto : lista) {
			   
			   if (cuentaConcepto.getChecked()){
				   
				   BloqueoCuenta bloqueCuenta = new BloqueoCuenta();
				   
				   bloqueCuenta.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				   
				   bloqueCuenta.setIntPersEmpresaPk(solicitudCtaCteTipo.getMovimiento().getIntPersEmpresa());
				   bloqueCuenta.setIntPersEmpresaUsuarioPk(solicitudCtaCteTipo.getMovimiento().getIntPersEmpresa());
				   bloqueCuenta.setIntPersPersonaUsuarioPk(solicitudCtaCteTipo.getMovimiento().getIntPersEmpresaUsuario());
				   
				   
				   java.sql.Date dtFechaInicio = new java.sql.Date(solicitudCtaCteTipo.getMovimiento().getDtFechaInicio().getTime());
				   java.sql.Timestamp tsFechaInicio = new java.sql.Timestamp(dtFechaInicio.getTime());
				   bloqueCuenta.setTsFechaInicio(tsFechaInicio);
				   
				   java.sql.Date dtFechaFin = new java.sql.Date(solicitudCtaCteTipo.getMovimiento().getDtFechaFin().getTime());
				   java.sql.Timestamp tsFechaFin= new java.sql.Timestamp(dtFechaFin.getTime());
				   bloqueCuenta.setTsFechaFin(tsFechaFin);
			  
				   bloqueCuenta.setIntCuentaPk(solicitudCtaCteTipo.getCuenta().getId().getIntCuenta());
				   bloqueCuenta.setIntItemCuentaConcepto(cuentaConcepto.getId().getIntItemCuentaConcepto());
				   bloqueCuenta.setIntParaCodigoMotivoCod(solicitudCtaCteTipo.getIntMotivoSolicitud());
				   
				   bloqueCuenta.setIntParaTipoBloqueoCod(Constante.PARAM_T_TIPOBLOQEOCTACONCEPTO_NOENVIOPLANILLA);
				   
				     
					    java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
						java.sql.Date sqlDate1 = new java.sql.Date(utilDate.getTime());
						Thread.sleep(20L);
						utilDate = new java.util.Date(System.currentTimeMillis());
						java.sql.Date sqlDate2 = new java.sql.Date(utilDate.getTime());
						System.out.println(sqlDate1 + " equals " + sqlDate2 + " ?  " + sqlDate1.equals(sqlDate2));
						
						java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate1.getTime());
						System.out.println(ts);
						ts = new java.sql.Timestamp(sqlDate2.getTime());
						System.out.println(ts);
				 	bloqueCuenta.setTsFechaRegistro(ts);
				   
				 	
				    BloqueoCuenta blqCtaRs = 	conceptoFacadeRemote.grabarBloqueoCuenta(bloqueCuenta);
				    
				    SolicitudCtaCteTipo resSolCtaTip = boSolicitudCtaCteTipo.getSolicitudCtaCteTipoPorPk(solicitudCtaCteTipo.getId());
			        if (resSolCtaTip == null){
			        	 solicitudCtaCteTipo.setIntParaTipoorigen(0);
			        	 boSolicitudCtaCteTipo.grabarSolicitudCtaCteTipo(solicitudCtaCteTipo);
			        }
			        else{
			             boSolicitudCtaCteTipo.modificarSolicitudCtaCteTipo(solicitudCtaCteTipo);
			        }
				    
				    SolicitudCtaCteBloqueo solBloqueo = new SolicitudCtaCteBloqueo();
				    
				    solBloqueo.getId().setIntPersEmpresasolctacte(solicitudCtaCteTipo.getId().getIntPersEmpresasolctacte());
				    solBloqueo.getId().setIntCcobItemsolctacte(solicitudCtaCteTipo.getId().getIntCcobItemsolctacte());
				    solBloqueo.getId().setIntTipoSolicitudctacte(solicitudCtaCteTipo.getId().getIntTipoSolicitudctacte());
				    
				    solBloqueo.setIntCmovItemblcu(blqCtaRs.getIntItemBloqueoCuenta());
				    
				    //grabar grabarSolicitudCtaCteBloqueo
				    boSolicitudCtaCteBloqueo.grabarSolicitudCtaCteBloqueo(solBloqueo);
			   }
		     }
			   
			   List<CuentaConcepto> listaCtaCptoExp = solicitudCtaCteTipo.getListaCtaCptoExpCredito();
			   for (CuentaConcepto ctaCptoExp : listaCtaCptoExp) {
			    if (ctaCptoExp.getChecked()){
			    
			    	   BloqueoCuenta bloqueCuenta = new BloqueoCuenta();
					   
					   bloqueCuenta.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					   
					   bloqueCuenta.setIntPersEmpresaPk(solicitudCtaCteTipo.getIntEmpresa());
					   bloqueCuenta.setIntPersEmpresaUsuarioPk(solicitudCtaCteTipo.getIntEmpresa());
					   bloqueCuenta.setIntPersPersonaUsuarioPk(solicitudCtaCteTipo.getIntPersUsuario());
					   java.sql.Date dtFechaInicio = new java.sql.Date(solicitudCtaCteTipo.getMovimiento().getDtFechaInicio().getTime());
					   java.sql.Timestamp tsFechaInicio = new java.sql.Timestamp(dtFechaInicio.getTime());
					   bloqueCuenta.setTsFechaInicio(tsFechaInicio);
					   java.sql.Date dtFechaFin = new java.sql.Date(solicitudCtaCteTipo.getMovimiento().getDtFechaFin().getTime());
					   java.sql.Timestamp tsFechaFin= new java.sql.Timestamp(dtFechaFin.getTime());
					   bloqueCuenta.setTsFechaFin(tsFechaFin);
				       bloqueCuenta.setIntParaCodigoMotivoCod(solicitudCtaCteTipo.getIntMotivoSolicitud());
					   bloqueCuenta.setIntParaTipoBloqueoCod(Constante.PARAM_T_TIPOBLOQEOCTACONCEPTO_NOENVIOPLANILLA);
					   
					     
						    java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
							java.sql.Date sqlDate1 = new java.sql.Date(utilDate.getTime());
							Thread.sleep(20L);
							utilDate = new java.util.Date(System.currentTimeMillis());
							java.sql.Date sqlDate2 = new java.sql.Date(utilDate.getTime());
							System.out.println(sqlDate1 + " equals " + sqlDate2 + " ?  " + sqlDate1.equals(sqlDate2));
							
							java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate1.getTime());
							System.out.println(ts);
							ts = new java.sql.Timestamp(sqlDate2.getTime());
							System.out.println(ts);
					   bloqueCuenta.setTsFechaRegistro(ts);
					 	     
					   Integer pEmpresa = ctaCptoExp.getId().getIntPersEmpresaPk();
					   Integer pCuenta = ctaCptoExp.getId().getIntCuentaPk();
					   Integer pTipoCredito = ctaCptoExp .getId().getIntItemCuentaConcepto();
					   
					   bloqueCuenta.setIntCuentaPk(pCuenta);
						   
						   List<Expediente> listExpCredito = listaExpPorCtaYTipoCredito(pEmpresa, pCuenta, pTipoCredito);
						   
						   for (Expediente expediente : listExpCredito) {
							   
							    bloqueCuenta.setIntItemExpediente(expediente.getId().getIntItemExpediente());
							    bloqueCuenta.setIntItemExpedienteDetalle(expediente.getId().getIntItemExpedienteDetalle());
						        BloqueoCuenta blqCtaRs = conceptoFacadeRemote.grabarBloqueoCuenta(bloqueCuenta);
						        
						        SolicitudCtaCteTipo resSolCtaTip = boSolicitudCtaCteTipo.getSolicitudCtaCteTipoPorPk(solicitudCtaCteTipo.getId());
						        if (resSolCtaTip == null){
						        	 solicitudCtaCteTipo.setIntParaTipoorigen(0);
						        	 boSolicitudCtaCteTipo.grabarSolicitudCtaCteTipo(solicitudCtaCteTipo);
						        }
						        else{
						             boSolicitudCtaCteTipo.modificarSolicitudCtaCteTipo(solicitudCtaCteTipo);
						        }
						        
							    SolicitudCtaCteBloqueo solBloqueo = new SolicitudCtaCteBloqueo();
							    
							    solBloqueo.getId().setIntPersEmpresasolctacte(solicitudCtaCteTipo.getId().getIntPersEmpresasolctacte());
							    solBloqueo.getId().setIntCcobItemsolctacte(solicitudCtaCteTipo.getId().getIntCcobItemsolctacte());
							    solBloqueo.getId().setIntTipoSolicitudctacte(solicitudCtaCteTipo.getId().getIntTipoSolicitudctacte());
							    
							    solBloqueo.setIntCmovItemblcu(blqCtaRs.getIntItemBloqueoCuenta());
							    
							    //grabar grabarSolicitudCtaCteBloqueo
							    boSolicitudCtaCteBloqueo.grabarSolicitudCtaCteBloqueo(solBloqueo);
						  }
			    		  
			      }
			   }
			   
		 }catch(BusinessException e){
				throw e;
		 }catch(Exception e){
				throw new BusinessException(e);
		 }
	   
	   return true;
   }
   
 private boolean bloquearCambioCondicion(SolicitudCtaCteTipo solicitudCtaCteTipo) throws BusinessException{
	   
	   try{
		   
		   ConceptoFacadeRemote conceptoFacadeRemote = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
		   CuentaFacadeRemote cuentaFacadeRemote = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
		   if (solicitudCtaCteTipo.getCuenta() != null){	
		      List<CuentaConcepto> lista = solicitudCtaCteTipo.getCuenta().getListaConcepto();
		      for (CuentaConcepto cuentaConcepto : lista) {
			       BloqueoCuenta bloqueCuenta = new BloqueoCuenta();
				   bloqueCuenta.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				   
				   bloqueCuenta.setIntPersEmpresaPk(solicitudCtaCteTipo.getMovimiento().getIntPersEmpresa());
				   bloqueCuenta.setIntPersEmpresaUsuarioPk(solicitudCtaCteTipo.getMovimiento().getIntPersEmpresa());
				   bloqueCuenta.setIntPersPersonaUsuarioPk(solicitudCtaCteTipo.getMovimiento().getIntPersEmpresaUsuario());
				   
				   java.sql.Date dtFechaInicio = new java.sql.Date(solicitudCtaCteTipo.getMovimiento().getDtFechaInicio().getTime());
				   java.sql.Timestamp tsFechaInicio = new java.sql.Timestamp(dtFechaInicio.getTime());
				   bloqueCuenta.setTsFechaInicio(tsFechaInicio);
				   
				    if (solicitudCtaCteTipo.getMovimiento().getDtFechaFin() != null){
				    java.sql.Date dtFechaFin = new java.sql.Date(solicitudCtaCteTipo.getMovimiento().getDtFechaFin().getTime());
				    java.sql.Timestamp tsFechaFin= new java.sql.Timestamp(dtFechaFin.getTime());
				    bloqueCuenta.setTsFechaFin(tsFechaFin);
				    }
				    
				    bloqueCuenta.setIntCuentaPk(solicitudCtaCteTipo.getCuenta().getId().getIntCuenta());
				    bloqueCuenta.setIntItemCuentaConcepto(cuentaConcepto.getId().getIntItemCuentaConcepto());
				    bloqueCuenta.setIntParaCodigoMotivoCod(solicitudCtaCteTipo.getIntMotivoSolicitud());
				   
				    bloqueCuenta.setIntParaTipoBloqueoCod(Constante.PARAM_T_TIPOBLOQEOCTACONCEPTO_NOENVIOPLANILLA);
					bloqueCuenta.setTsFechaRegistro(UtilCobranza.obtieneFechaActualEnTimesTamp());
					
					
					
				   
				    BloqueoCuenta blqCtaRs = 	conceptoFacadeRemote.grabarBloqueoCuenta(bloqueCuenta);
				    
				    SolicitudCtaCteTipo resSolCtaTip = boSolicitudCtaCteTipo.getSolicitudCtaCteTipoPorPk(solicitudCtaCteTipo.getId());
			        if (resSolCtaTip == null){
			        	 solicitudCtaCteTipo.setIntParaTipoorigen(0);
			        	 boSolicitudCtaCteTipo.grabarSolicitudCtaCteTipo(solicitudCtaCteTipo);
			        }
			        else{
			             boSolicitudCtaCteTipo.modificarSolicitudCtaCteTipo(solicitudCtaCteTipo);
			        }
				    
				    SolicitudCtaCteBloqueo solBloqueo = new SolicitudCtaCteBloqueo();
				    
				    solBloqueo.getId().setIntPersEmpresasolctacte(solicitudCtaCteTipo.getId().getIntPersEmpresasolctacte());
				    solBloqueo.getId().setIntCcobItemsolctacte(solicitudCtaCteTipo.getId().getIntCcobItemsolctacte());
				    solBloqueo.getId().setIntTipoSolicitudctacte(solicitudCtaCteTipo.getId().getIntTipoSolicitudctacte());
				    
				    solBloqueo.setIntCmovItemblcu(blqCtaRs.getIntItemBloqueoCuenta());
				    
				    //grabar grabarSolicitudCtaCteBloqueo
				    boSolicitudCtaCteBloqueo.grabarSolicitudCtaCteBloqueo(solBloqueo);
			   
		     }
		   }
			   
		       if (solicitudCtaCteTipo.getListaExpediente() != null){
		                List<Expediente> listaCtaCptoExp = solicitudCtaCteTipo.getListaExpediente();
				   for (Expediente ctaCptoExp : listaCtaCptoExp) {
				    
				    	   BloqueoCuenta bloqueCuenta = new BloqueoCuenta();
						   
				    	   bloqueCuenta.setIntCuentaPk(solicitudCtaCteTipo.getCuenta().getId().getIntCuenta());
				    	   bloqueCuenta.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						   bloqueCuenta.setIntPersEmpresaPk(solicitudCtaCteTipo.getIntEmpresa());
						   bloqueCuenta.setIntPersEmpresaUsuarioPk(solicitudCtaCteTipo.getIntEmpresa());
						   bloqueCuenta.setIntPersPersonaUsuarioPk(solicitudCtaCteTipo.getIntPersUsuario());
						   java.sql.Date dtFechaInicio = new java.sql.Date(solicitudCtaCteTipo.getMovimiento().getDtFechaInicio().getTime());
						   java.sql.Timestamp tsFechaInicio = new java.sql.Timestamp(dtFechaInicio.getTime());
						   bloqueCuenta.setTsFechaInicio(tsFechaInicio);
						   if (solicitudCtaCteTipo.getMovimiento().getDtFechaFin() != null){
						   java.sql.Date dtFechaFin = new java.sql.Date(solicitudCtaCteTipo.getMovimiento().getDtFechaFin().getTime());
						   java.sql.Timestamp tsFechaFin= new java.sql.Timestamp(dtFechaFin.getTime());
						   bloqueCuenta.setTsFechaFin(tsFechaFin);
						   }
					       bloqueCuenta.setIntParaCodigoMotivoCod(solicitudCtaCteTipo.getIntMotivoSolicitud());
						   bloqueCuenta.setIntParaTipoBloqueoCod(Constante.PARAM_T_TIPOBLOQEOCTACONCEPTO_NOENVIOPLANILLA);
						   bloqueCuenta.setTsFechaRegistro(UtilCobranza.obtieneFechaActualEnTimesTamp());
						   bloqueCuenta.setIntItemExpediente(ctaCptoExp.getId().getIntItemExpediente());
						   bloqueCuenta.setIntItemExpedienteDetalle(ctaCptoExp.getId().getIntItemExpedienteDetalle());
						   
						  if (ctaCptoExp.getIntParaTipoCreditoCod().equals(Constante.PARAM_T_TIPO_CREDITO_PRESTAMO)){
							  bloqueCuenta.setIntParaTipoBloqueoCod(Constante.PARAM_T_TIPOCONCEPTO_CREDITO_INTERES);
						  }
						   
						   BloqueoCuenta blqCtaRs = conceptoFacadeRemote.grabarBloqueoCuenta(bloqueCuenta);
							        
						    SolicitudCtaCteTipo resSolCtaTip = boSolicitudCtaCteTipo.getSolicitudCtaCteTipoPorPk(solicitudCtaCteTipo.getId());
						    if (resSolCtaTip == null){
							  solicitudCtaCteTipo.setIntParaTipoorigen(0);
							  boSolicitudCtaCteTipo.grabarSolicitudCtaCteTipo(solicitudCtaCteTipo);
							}
						    else{
							  boSolicitudCtaCteTipo.modificarSolicitudCtaCteTipo(solicitudCtaCteTipo);
						 	}
							        
							SolicitudCtaCteBloqueo solBloqueo = new SolicitudCtaCteBloqueo();
								    
							solBloqueo.getId().setIntPersEmpresasolctacte(solicitudCtaCteTipo.getId().getIntPersEmpresasolctacte());
							solBloqueo.getId().setIntCcobItemsolctacte(solicitudCtaCteTipo.getId().getIntCcobItemsolctacte());
							solBloqueo.getId().setIntTipoSolicitudctacte(solicitudCtaCteTipo.getId().getIntTipoSolicitudctacte());
								    
							solBloqueo.setIntCmovItemblcu(blqCtaRs.getIntItemBloqueoCuenta());
								    
							//grabar grabarSolicitudCtaCteBloqueo
							boSolicitudCtaCteBloqueo.grabarSolicitudCtaCteBloqueo(solBloqueo);
						 
				   }
		       }
			   //Realiza el cambio de condicion.
			   
		       if (solicitudCtaCteTipo.getCuenta() != null){
			       Cuenta cuenta =  solicitudCtaCteTipo.getCuenta();
				   Cuenta cuentaResult = cuentaFacadeRemote.getCuentaPorId(cuenta.getId());
				   cuentaResult.setIntParaCondicionCuentaCod(solicitudCtaCteTipo.getIntParaCondicionCuentaFinal());
				   cuentaFacadeRemote.modificarCuenta(cuentaResult);
		       }
		       
		 }catch(BusinessException e){
				throw e;
		 }catch(Exception e){
				throw new BusinessException(e);
		 }
	   
	   return true;
   }
	
   private List<Expediente> listaExpPorCtaYTipoCredito(Integer pEmpresa,Integer pCuenta,Integer pTipoCredito)throws BusinessException,Exception{
	   
	   List<Expediente> listExpCredito = null;
	   
	   ConceptoFacadeRemote conceptoFacadeRemote = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
		
	   List<Expediente> lista = conceptoFacadeRemote.getListaExpedienteConSaldoPorEmpresaYcuenta(pEmpresa, pCuenta);
	   if (lista != null) listExpCredito = new ArrayList<Expediente>();
	   for (Expediente expediente : lista) {
		   
		   if(expediente.getIntParaTipoCreditoCod().equals(pTipoCredito)){
			   listExpCredito.add(expediente);
		   }
		
	   }
	   
	   
	   return listExpCredito;
   }
   
   
   
   
   /**
    * 
    */
	/* public void generarSolicitudCtaCteRefinan(SocioComp socioComp, Usuario usuario, String strPeriodo, RequisitoCredito requisitoCred, 
			 Expediente expedientePorRefinanciar ) throws BusinessException{
		 
		 SolicitudCtaCte solicitudCtaCte = null;
		 SolicitudCtaCteId solCtaCteId = null;
		 EstadoSolicitudCtaCte estadoPendiente = null;
		 EstadoSolicitudCtaCte estadoAtendido = null;
		 EstadoSolicitudCtaCteId estadoPendId = null;
		 EstadoSolicitudCtaCteId estadoAtenId = null;
		 Calendar fecHoy = Calendar.getInstance();
		 Date dtAhora = fecHoy.getTime();
		 
		 try {
			//Usuario usuario = new Usuario();
			 //SolicitudCtaCteBO boSolicitudCtaCte = (SolicitudCtaCteBO)TumiFactory.get(SolicitudCtaCteBO.class);
			 //SolicitudCtaCteTipoBO boSolicitudCtaCteTipo = (SolicitudCtaCteTipoBO)TumiFactory.get(SolicitudCtaCteTipoBO.class);
			 //EstadoSolicitudCtaCteBO boEstadoCtacte = (EstadoSolicitudCtaCteBO)TumiFactory.get(EstadoSolicitudCtaCteBO.class);
			 
			 if(socioComp != null){
				 solCtaCteId = new SolicitudCtaCteId();
				 solicitudCtaCte = new SolicitudCtaCte();
				 
				 solCtaCteId.setIntEmpresasolctacte(socioComp.getSocio().getId().getIntIdEmpresa());
				 solicitudCtaCte.setId(solCtaCteId);
				 solicitudCtaCte.setIntPersEmpresa(socioComp.getCuenta().getId().getIntPersEmpresaPk());
				 solicitudCtaCte.setIntPersPersona(socioComp.getPersona().getNatural().getIntIdPersona());
				 solicitudCtaCte.setIntCsocCuenta(socioComp.getCuenta().getId().getIntCuenta());
				 solicitudCtaCte.setIntSucuIdsucursalsocio(socioComp.getCuenta().getIntIdUsuSucursal());
				 solicitudCtaCte.setIntSudeIdsubsucursalsocio(socioComp.getCuenta().getIntIdUsuSubSucursal());
				 solicitudCtaCte.setIntPeriodo(new Integer(strPeriodo));
				 solicitudCtaCte.setIntParaTipomodalidad(socioComp.getSocio().getSocioEstructura().getIntModalidad());
				 solicitudCtaCte.setIntParaTipo(Constante.PARAM_T_TIPO_ORIGEN_REFINANCIAMIENTO_POR_PRESTAMO);
				 solicitudCtaCte.setIntMaeItemarchivo(requisitoCred.getIntParaItemArchivo());
				 solicitudCtaCte.setIntMaeItemhistorico(requisitoCred.getIntParaItemHistorico());

				 solicitudCtaCte = boSolicitudCtaCte.grabarSolicitudCtaCte(solicitudCtaCte);
				 if(solicitudCtaCte.getId().getIntCcobItemsolctacte() != null){
					 
					 estadoPendiente = new EstadoSolicitudCtaCte();
					 estadoAtendido = new EstadoSolicitudCtaCte();
					 estadoPendId = new EstadoSolicitudCtaCteId();
					 estadoAtenId = new EstadoSolicitudCtaCteId() ;
					 
					 estadoPendId.setIntPersEmpresaSolctacte(solicitudCtaCte.getId().getIntEmpresasolctacte());
					 estadoPendId.setIntCcobItemSolCtaCte(solicitudCtaCte.getId().getIntCcobItemsolctacte());
					 estadoPendiente.setId(estadoPendId);
					 estadoPendiente.setIntParaEstadoSolCtaCte(Constante.PARAM_T_ESTADO_SOLCTACTE_PENDIENTE);
					 estadoPendiente.setDtEsccFechaEstado(dtAhora);
					 estadoPendiente.setIntPersEmpresaEstado(usuario.getEmpresa().getIntIdEmpresa());
					 estadoPendiente.setIntSucuIduSusucursal(usuario.getSucursal().getId().getIntIdSucursal());
					 estadoPendiente.setIntSudeIduSusubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
					 estadoPendiente.setIntPersUsuarioEstado(usuario.getIntPersPersonaPk());
					 estadoPendiente.setStrEsccObservacion("registro Automatico por Refinanciamiento - Estado Pendiente.");
					 boEstadoSolicitudCtaCte.grabarEstadoSolicitudCtaCte(estadoPendiente);
					 
					 //-----------------------------------------------------------------------------------------------------
					 estadoAtenId.setIntPersEmpresaSolctacte(solicitudCtaCte.getId().getIntEmpresasolctacte());
					 estadoAtenId.setIntCcobItemSolCtaCte(solicitudCtaCte.getId().getIntCcobItemsolctacte());
					 estadoAtendido.setId(estadoAtenId);
					 estadoAtendido.setIntParaEstadoSolCtaCte(Constante.PARAM_T_ESTADO_SOLCTACTE_ATENDIDO);
					 estadoAtendido.setDtEsccFechaEstado(dtAhora);
					 estadoAtendido.setIntPersEmpresaEstado(usuario.getEmpresa().getIntIdEmpresa());
					 estadoAtendido.setIntSucuIduSusucursal(usuario.getSucursal().getId().getIntIdSucursal());
					 estadoAtendido.setIntSudeIduSusubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
					 estadoAtendido.setIntPersUsuarioEstado(usuario.getIntPersPersonaPk());
					 estadoAtendido.setStrEsccObservacion("registro Automatico por Refinanciamiento - Estado Atendido.");
					 boEstadoSolicitudCtaCte.grabarEstadoSolicitudCtaCte(estadoAtendido);
					 
					 
					 SolicitudCtaCteTipo solCtaCteTipo = new SolicitudCtaCteTipo();
					 SolicitudCtaCteTipoId solCtaCteTipoId = new SolicitudCtaCteTipoId();
					 
					 solCtaCteTipoId.setIntCcobItemsolctacte(solicitudCtaCte.getId().getIntCcobItemsolctacte());
					 solCtaCteTipoId.setIntPersEmpresasolctacte(solicitudCtaCte.getId().getIntEmpresasolctacte());
					 solCtaCteTipoId.setIntTipoSolicitudctacte(Constante.PARAM_T_SOLICITUD_CTACTE_TIPO_TRANSFERENCIAS);
					 solCtaCteTipo.setId(solCtaCteTipoId);
					 
					 solCtaCteTipo.setIntTaraEstado(Constante.PARAM_T_ESTADO_SOLCTACTE_ATENDIDO);
					 solCtaCteTipo.setIntParaTipoorigen(Constante.PARAM_T_TIPO_ORIGEN_REFINANCIAMIENTO_POR_PRESTAMO);
					 solCtaCteTipo.setIntParaEstadoanalisis(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					 solCtaCteTipo.setStrScctObservacion("Generado por Refinanciamiento");
					 solCtaCteTipo.setDtFechaDocumento(dtAhora);
					 solCtaCteTipo.setIntMotivoSolicitud(Constante.PARAM_T_MOTIVO_SOLICITUD_CTACTE_REFINANCIAMIENTO);
					 solCtaCteTipo.setIntEmpresaLibro(0);
					 solCtaCteTipo.setIntContPeriodolibro(0);
					 solCtaCteTipo.setIntCodigoLibro(0);
					 solCtaCteTipo.setIntCcobItemefectuado(null);
					 
					 solCtaCteTipo = boSolicitudCtaCteTipo.grabarSolicitudCtaCteTipo(solCtaCteTipo);
					 solCtaCteTipo.getListaExpediente().add(expedientePorRefinanciar);
					 solCtaCteTipo.setSocioComp(socioComp);
					 generarTransferencia(solCtaCteTipo);
 
				 }
			 }
		} catch (Exception e) {
			System.out.println("Error en generarSolicitudCtaCteRefinan --> "+e);
		}
		 
		 
	 } */
	 
	 
	 
	/* public LibroDiario generarLibroDiarioYTransferenciaRefinanciamiento(SolicitudCtaCteTipo solicitudCtaCteTipo,Usuario usuario, ExpedienteCredito expedienteCreditoNuevo)throws BusinessException{
		 LibroDiario libDiario= null;
		 
		 try {
			 if (solicitudCtaCteTipo.getIntMotivoSolicitud().equals(Constante.PARAM_T_MOTIVO_SOLICITUD_CTACTE_REFINANCIAMIENTO)){
				 // aca won
				 libDiario=generarAsientoContableRefinanciamientoYTransferencia(solicitudCtaCteTipo, usuario, expedienteCreditoNuevo);
				 
			 }	
		} catch (BusinessException e) {
			// TODO: handle exception
		}
		 
		 
		 return libDiario;
	 }*/
	 
	 
		 
	 
	 /**
	  * Genera el libro diario (1), graba la transferencia generada y actualizamontos (2)
	  * @use1: generarAsientoContableRefinanciamiento(solicitudCtaCteTipo, usuario)
	  * @use2: actualizaMontoSaldoCronogramaEnCeroRefinanciamiento(expedientePorRefinanciar, solicitudCtaCteTipo)
	  * @param solicitudCtaCteTipo
	  * @param usuario
	  * @param expedientePorRefinanciar
	  * @throws BusinessException
	  */
	 	/*public LibroDiario generarTransferenciaRefinanciamiento(SolicitudCtaCteTipo solicitudCtaCteTipo, Usuario usuario, Expediente expedientePorRefinanciar,
			 ExpedienteCredito expedienteCredito) throws BusinessException{

		 try{
	    	    TransferenciaBO           boTransferencia   = (TransferenciaBO)TumiFactory.get(TransferenciaBO.class);
	    	    LibroDiario diarioResult = null;
	    	    Transferencia transferencia     = new Transferencia();
		        
		        //Actualiza o se regisra el Tipo de Solicitud. 			  
		        if (solicitudCtaCteTipo.getIntMotivoSolicitud().equals(Constante.PARAM_T_MOTIVO_SOLICITUD_CTACTE_REFINANCIAMIENTO)){
		        	diarioResult = generarAsientoContableRefinanciamiento(solicitudCtaCteTipo, usuario, expedienteCredito);	
		        
		        	if(diarioResult!=null){
		        		//Graba la transferencia;
					      TransferenciaId transferenciaId = new TransferenciaId();
					      transferenciaId.setIntPersEmpresaTransferencia(solicitudCtaCteTipo.getId().getIntPersEmpresasolctacte());
					      transferencia.setId(transferenciaId);
					      transferencia.setIntParaDocumentoGeneral(diarioResult.getIntParaTipoDocumentoGeneral());
					      transferencia.setTsTranFecha(UtilCobranza.obtieneFechaActualEnTimesTamp());
					      transferencia.setIntCcobItemSolctacte(solicitudCtaCteTipo.getId().getIntCcobItemsolctacte());
					      transferencia.setIntPersEmpresaSolctacte(solicitudCtaCteTipo.getId().getIntPersEmpresasolctacte());
					      transferencia.setIntParaTipoSolicitudctacte(solicitudCtaCteTipo.getId().getIntTipoSolicitudctacte());
					      transferencia.setIntPersEmpresaLibro(diarioResult.getId().getIntPersEmpresaLibro());
					      transferencia.setIntContPeriodoLibro(diarioResult.getId().getIntContPeriodoLibro());
					      transferencia.setIntContCodigoLibro(diarioResult.getId().getIntContCodigoLibro());
					      transferencia.setIntTranPeriodo(diarioResult.getId().getIntContPeriodoLibro());
				        
					      boTransferencia.grabarTransferencia(transferencia);	
					      
			        }else{
			        	throw new BusinessException("No se recupero diarioResult.");
			        }
		        }
			     // comentado x pruebas internas 
			     //actualizaMontoSaldoCronogramaEnCeroRefinanciamiento(expedientePorRefinanciar, solicitudCtaCteTipo);
		        return diarioResult;
	      }catch(BusinessException e){
	    	  System.out.println("ERROR EN GENERAR TRANSFERENCIA ---> "+e);
				throw e;
		  }catch(Exception e){
			  System.out.println("ERROR EN GENERAR TRANSFERENCIA ---> "+e);
				throw new BusinessException(e);
		  }
	 }*/
	 
	/**
	 * 
	 */
	 private LibroDiario generarAsientoContableRefinanciamientoYTransferencia_2(ExpedienteCreditoComp expedienteCreditoCompSelected, SolicitudCtaCteTipo solicitudCtaCteTipo, Usuario usuario, ExpedienteCredito expedienteCreditoNuevo) 
	 		throws BusinessException{ 
		 ConceptoFacadeRemote     conceptoFacade 	= null;
		 ModeloFacadeRemote       modeloFacade 		= null;
		 LibroDiarioFacadeRemote  libroDiarioFacade = null;
		 CreditoFacadeRemote      creditoFacade 	= null;
		 GeneralFacadeRemote      generalFacade 	= null;
		 CarteraFacadeRemote      carteraFacade 	= null;
		 TransferenciaBO           boTransferencia   = null;
		 SolicitudPrestamoFacadeRemote solicitudPrestamofacade = null;
		 
		 Integer intPersona = 0;
		 Integer intCuenta  = 0;
		 Integer intEmpresa = 0;
		 LibroDiario diario = null;
		 List<Expediente> listaExpediente = null;
		 List<Modelo>     listaModelo     = null;
		 
		 //Integer intCuentaTitularOGarante = null;    
   	     //Integer intPersonaTitularOGarante = null;    
	     //Integer intEmpresaTitularOGarante = null;	  

	      try{
	    	     conceptoFacade    = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
	    	     modeloFacade      = (ModeloFacadeRemote)EJBFactory.getRemote(ModeloFacadeRemote.class);
	    	     libroDiarioFacade = (LibroDiarioFacadeRemote)EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
	    	     creditoFacade     = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
	    	     generalFacade     = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class); 
	    	     carteraFacade     = (CarteraFacadeRemote)EJBFactory.getRemote(CarteraFacadeRemote.class); 
	    	     solicitudPrestamofacade = (SolicitudPrestamoFacadeRemote)EJBFactory.getRemote(SolicitudPrestamoFacadeRemote.class);
	    	     boTransferencia   = (TransferenciaBO)TumiFactory.get(TransferenciaBO.class);
			     //Datos del Socio
	    	     intPersona = solicitudCtaCteTipo.getSocioComp().getSocio().getId().getIntIdPersona();
	    	     intCuenta  = solicitudCtaCteTipo.getSocioComp().getCuenta().getId().getIntCuenta();
	             intEmpresa = solicitudCtaCteTipo.getSocioComp().getCuenta().getId().getIntPersEmpresaPk();
	    	       
                 //Obtiencion del Modelo Contable segun el tipo de transferencia.
				if (solicitudCtaCteTipo.getIntMotivoSolicitud().equals(Constante.PARAM_T_MOTIVO_SOLICITUD_CTACTE_REFINANCIAMIENTO)){
				    listaModelo = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_REFINANCIAMIENTO,intEmpresa);
				 }
				 
				 if (listaModelo == null || listaModelo.size() == 0 || listaModelo.isEmpty()){
					  throw new BusinessException("No existe el modelo contable para generar el asiento.");
				 }else {
						
					 listaExpediente = solicitudCtaCteTipo.getListaExpediente();
					 Expediente expedienteMovAnterior = listaExpediente.get(0);
				     //Integer intItemDetalle = new Integer(0);
		    	     Integer intTipoCreditoEmpresa = null;
				     Integer intTipoCategRiesgo    = null;
				         
				    // Obtenemos el credtio
					Credito credito = null;
				    CreditoId creditoId = new CreditoId();
					creditoId.setIntItemCredito(expedienteMovAnterior.getIntItemCredito());
					creditoId.setIntParaTipoCreditoCod(expedienteMovAnterior.getIntParaTipoCreditoCod());
					creditoId.setIntPersEmpresaPk(expedienteMovAnterior.getIntPersEmpresaCreditoPk());
					
				    //Busca la categoria de riesgo para cada concepto 
					CarteraCredito carteraCredito = carteraFacade.getCarteraCreditoPorMaxPeriodo(intEmpresa);
					List<CarteraCreditoDetalle> lista = carteraCredito.getListaCarteraCreDetalle();
					
					//Ordenamos los subtipos por int
					Collections.sort(lista, new Comparator<CarteraCreditoDetalle>(){
						public int compare(CarteraCreditoDetalle uno, CarteraCreditoDetalle otro) {
							return uno.getId().getIntCrieIitemcarteracredito().compareTo(otro.getId().getIntCrieIitemcarteracredito());
						}
					});
						
						
					// Recuperamos el tipo de riesgo 
					for (CarteraCreditoDetalle carCredDet : lista) {
						 if (carCredDet.getIntPersPersona().equals(intPersona) &&
							carCredDet.getIntPersEmpresa().equals(intEmpresa) &&
							carCredDet.getIntCsocCuenta().equals(intCuenta)){
							 
								if (expedienteMovAnterior.getId().getIntItemExpediente().equals(carCredDet.getIntCserItemexpediente())&&
									expedienteMovAnterior.getId().getIntItemExpedienteDetalle().equals(carCredDet.getIntCserItemdetexpediente())){
									credito = creditoFacade.getCreditoPorIdCredito(creditoId);
									
								    if (credito == null){
									   throw new BusinessException("No existe la configuración de crédito para el expediente.");
								    }
									intTipoCategRiesgo    = carCredDet.getIntParaTipocategoriariesgo();
									intTipoCreditoEmpresa = credito.getIntParaTipoCreditoEmpresa();
								}else{
									throw new BusinessException("No se pudo recuperar Riesgo.");
									// No se recuperar Riesgo 
								}
						 }
					}

					Modelo modelo = listaModelo.get(0);

					diario = obtieneLibroDiarioYDiarioDetalleRefinanciamiento_3(modelo.getId(), intTipoCategRiesgo, intTipoCreditoEmpresa, 
							expedienteMovAnterior,expedienteCreditoNuevo,solicitudCtaCteTipo, usuario);

					 //Grabamos la transferencia
		    	    Transferencia transferencia     = null;
			        
			        //Actualiza o se regisra el Tipo de Solicitud. 			  
			        if (solicitudCtaCteTipo.getIntMotivoSolicitud().equals(Constante.PARAM_T_MOTIVO_SOLICITUD_CTACTE_REFINANCIAMIENTO)){
			        	//diarioResult = generarAsientoContableRefinanciamiento(solicitudCtaCteTipo, usuario, expedienteCredito);	
			        
			        	if(diario!=null){
			        		//Graba la transferencia;
			        		transferencia     = new Transferencia();
						      TransferenciaId transferenciaId = new TransferenciaId();
						      transferenciaId.setIntPersEmpresaTransferencia(solicitudCtaCteTipo.getId().getIntPersEmpresasolctacte());
						      transferencia.setId(transferenciaId);
						      transferencia.setIntParaDocumentoGeneral(diario.getIntParaTipoDocumentoGeneral());
						      transferencia.setTsTranFecha(UtilCobranza.obtieneFechaActualEnTimesTamp());
						      transferencia.setIntCcobItemSolctacte(solicitudCtaCteTipo.getId().getIntCcobItemsolctacte());
						      transferencia.setIntPersEmpresaSolctacte(solicitudCtaCteTipo.getId().getIntPersEmpresasolctacte());
						      transferencia.setIntParaTipoSolicitudctacte(solicitudCtaCteTipo.getId().getIntTipoSolicitudctacte());
						      transferencia.setIntPersEmpresaLibro(diario.getId().getIntPersEmpresaLibro());
						      transferencia.setIntContPeriodoLibro(diario.getId().getIntContPeriodoLibro());
						      transferencia.setIntContCodigoLibro(diario.getId().getIntContCodigoLibro());
						      transferencia.setIntTranPeriodo(diario.getId().getIntContPeriodoLibro());
					        
						      boTransferencia.grabarTransferencia(transferencia);
						      
						      
						      solicitudPrestamofacade.generarExpedienteMovimiento(expedienteCreditoCompSelected);
				 	}
			       }
				 }
	      }catch(BusinessException e){
	    	  System.out.println("Error en generarAsientoContableRefinanciamiento 1 -->"+e);
				throw e;
		  }catch(Exception e){
			  System.out.println("Error en generarAsientoContableRefinanciamiento 2 -->"+e);
				throw new BusinessException(e);
		  }
		  return diario;
	 }
	 

	 
	 /**
	  * Actualiza los montos de los saldos de las cuotas en 0.00 
	  * @param expedientePorRefinanciar
	  * @param solCtaCteTipo
	  * @throws BusinessException
	  */
		private void actualizaMontoSaldoCronogramaEnCeroRefinanciamiento(Expediente expedientePorRefinanciar,SolicitudCtaCteTipo solCtaCteTipo) throws BusinessException{
			ConceptoFacadeRemote conceptoFacade = null;
			
			try{
				 conceptoFacade  = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
				 List<Cronograma> lista = conceptoFacade.getListaCronogramaPorPkExpediente(expedientePorRefinanciar.getId()); 
				
				 //Ordena la lista por el numero de la cuota.
			        Collections.sort(lista, new Comparator() {  
			            public int compare(Object o1, Object o2) {  
			            	Cronograma e1 = (Cronograma) o1;  
			            	Cronograma e2 = (Cronograma) o2;  
			                return e1.getIntNumeroCuota().compareTo(e2.getIntNumeroCuota());
			            }  
			        });  
		          	  
			       // BigDecimal montoAbono = expedientePorRefinanciar.getBdMontoAbono();
		          	  
		          	for (Cronograma cronograma : lista) {		          		

		      					cronograma.setBdSaldoDetalleCredito(new BigDecimal(0));
		              		    conceptoFacade.modificarCronograma(cronograma);

					}
			 
		   }catch(BusinessException e){
				throw e;
		   }catch(Exception e){
				throw new BusinessException(e);
		   }
		}
		
		 public List<SolicitudCtaCte> SolicitudesTipoTransf(Integer empresasolctacte,  Integer intCsocCuenta) throws EJBFactoryException, BusinessException {
			 CuentacteFacadeRemote  cuentacteFacade            = (CuentacteFacadeRemote)EJBFactory.getRemote(CuentacteFacadeRemote.class);
			 TransferenciaBO          boTransferencia          = (TransferenciaBO)TumiFactory.get(TransferenciaBO.class);
			 SolicitudCtaCteTipoBO    boSolicitudCtaCteTip = (SolicitudCtaCteTipoBO)TumiFactory.get(SolicitudCtaCteTipoBO.class);
			 List<SolicitudCtaCte> lstctas = null;
			 List<SolicitudCtaCteTipo> lstctasTipo = null;
			 List<Transferencia> lsttrasnf = null;			  
							  
			 try{
				
				 
				 lstctas= cuentacteFacade.getListaPorCuenta(empresasolctacte, intCsocCuenta);				 
				 if(lstctas != null && !lstctas.isEmpty()){					
					for ( SolicitudCtaCte solici : lstctas){					
						lstctasTipo = boSolicitudCtaCteTip.getListaPorSolCtacteSinEstado(solici.getId().getIntEmpresasolctacte(), solici.getId().getIntCcobItemsolctacte());
						for (SolicitudCtaCteTipo soliciTipo : lstctasTipo){
							lsttrasnf = boTransferencia.getListaTransferencias(soliciTipo.getId().getIntPersEmpresasolctacte(),soliciTipo.getId().getIntCcobItemsolctacte(), soliciTipo.getId().getIntTipoSolicitudctacte());
							
							if(lsttrasnf != null && !lsttrasnf.isEmpty()){							
								soliciTipo.setListaTransferencias(lsttrasnf);
							}						
						}						
						 solici.setListaSolCtaCteTipo(lstctasTipo);					
					}			
					
				 }
			 }catch(BusinessException e){
					throw e;
			   }catch(Exception e){
					throw new BusinessException(e);
			   }
			return lstctas;
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
		/* public SolicitudCtaCte grabarSolicitudCtaCteParaLiquidacion_1(SocioComp socioComp,Integer strPeriodo, RequisitoLiquidacion requisitoLiq, Usuario usuario) 
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
						 solicitudCtaCte.setIntParaTipo(requisitoLiq.getIntParaTipoArchivo());
						 solicitudCtaCte.setIntMaeItemarchivo(requisitoLiq.getIntParaItemArchivo());
						 solicitudCtaCte.setIntMaeItemhistorico(requisitoLiq.getIntParaItemHistorico());
						 
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
							 estadoPendiente.setStrEsccObservacion("Registro Automatico por Liquidacion de Cuentas - Estado Pendiente.");
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
							 estadoAtendido.setStrEsccObservacion("Registro Automatico por Liquidacion de Cuentas - Estado Atendido.");
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
							 }
						 }
					 }

			} catch (Exception e) {
				log.error("Error en grabarSolicitudCtaCteParaLiquidacion ---> "+e);
			}
			 return solicitudCtaCte;
			 
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
		/*public LibroDiario generarAsientoContableLiquidacionCuentasYTransferencia_2(SolicitudCtaCteTipo solicitudCtaCteTipo,SocioComp socioComp, Usuario usuario, ExpedienteLiquidacion expedienteLIquidacion) 
	 		throws BusinessException{ 
		 ConceptoFacadeRemote     conceptoFacade 	= null;
		 ModeloFacadeRemote       modeloFacade 		= null;
		 LibroDiarioFacadeRemote  libroDiarioFacade = null;
		 CreditoFacadeRemote      creditoFacade 	= null;
		 GeneralFacadeRemote      generalFacade 	= null;
		 CarteraFacadeRemote      carteraFacade 	= null;
		 TransferenciaBO           boTransferencia   = null;
		 SolicitudPrestamoFacadeRemote solicitudPrestamofacade = null;
		 
		 CuentacteFacadeRemote cuentaCteFacade = null;
		 
		 Integer intPersona = null;
		 Integer intCuenta  = null;
		 Integer intEmpresa = null;
		 LibroDiario diario = null;
		 List<Modelo>     listaModelo     = null;

	     
	     Modelo modeloSeleccionado = null;
	     Transferencia transferencia = null;

	      try{	 cuentaCteFacade = (CuentacteFacadeRemote)EJBFactory.getRemote(CuentacteFacadeRemote.class);
	    	     conceptoFacade    = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
	    	     modeloFacade      = (ModeloFacadeRemote)EJBFactory.getRemote(ModeloFacadeRemote.class);
	    	     libroDiarioFacade = (LibroDiarioFacadeRemote)EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
	    	     creditoFacade     = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
	    	     generalFacade     = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class); 
	    	     carteraFacade     = (CarteraFacadeRemote)EJBFactory.getRemote(CarteraFacadeRemote.class); 
	    	     solicitudPrestamofacade = (SolicitudPrestamoFacadeRemote)EJBFactory.getRemote(SolicitudPrestamoFacadeRemote.class);
	    	     boTransferencia   = (TransferenciaBO)TumiFactory.get(TransferenciaBO.class);
			     //Datos del Socio
	    	     //intPersona = solicitudCtaCteTipo.getSocioComp().getSocio().getId().getIntIdPersona();
	    	     //intCuenta  = solicitudCtaCteTipo.getSocioComp().getCuenta().getId().getIntCuenta();
	             //intEmpresa = solicitudCtaCteTipo.getSocioComp().getCuenta().getId().getIntPersEmpresaPk();
	    	     intPersona = socioComp.getSocio().getId().getIntIdPersona();
	    	     intCuenta  = socioComp.getCuenta().getId().getIntCuenta();
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

					if(diario != null){
						diario = libroDiarioFacade.grabarLibroDiario(diario); 
	
					}
					
					
					 //Grabamos la transferencia			        
			        //Actualiza o se regisra el Tipo de Solicitud. 			  
			        if (solicitudCtaCteTipo.getIntMotivoSolicitud().equals(Constante.PARAM_T_MOTIVO_SOLICITUD_CTACTE_LIQUIDACION_CTA_CTO)){
			        	if(diario!=null){
			        		// actualizamos la solicitud de  	
			        		solicitudCtaCteTipo.setIntCodigoLibro(diario.getId().getIntContCodigoLibro());
			        		solicitudCtaCteTipo.setIntContPeriodolibro(diario.getId().getIntContPeriodoLibro());
			        		
			        		solicitudCtaCteTipo = cuentaCteFacade.modificarSolicitudCuentaCorrienteTipo(solicitudCtaCteTipo);

			        		//Graba la transferencia;
			        		  transferencia = new Transferencia();
						      TransferenciaId transferenciaId = new TransferenciaId();
						      transferenciaId.setIntPersEmpresaTransferencia(solicitudCtaCteTipo.getId().getIntPersEmpresasolctacte());
						      transferencia.setId(transferenciaId);
						      transferencia.setIntParaDocumentoGeneral(diario.getIntParaTipoDocumentoGeneral());
						      transferencia.setTsTranFecha(UtilCobranza.obtieneFechaActualEnTimesTamp());
						      transferencia.setIntCcobItemSolctacte(solicitudCtaCteTipo.getId().getIntCcobItemsolctacte());
						      transferencia.setIntPersEmpresaSolctacte(solicitudCtaCteTipo.getId().getIntPersEmpresasolctacte());
						      transferencia.setIntParaTipoSolicitudctacte(solicitudCtaCteTipo.getId().getIntTipoSolicitudctacte());
						      transferencia.setIntPersEmpresaLibro(diario.getId().getIntPersEmpresaLibro());
						      transferencia.setIntContPeriodoLibro(diario.getId().getIntContPeriodoLibro());
						      transferencia.setIntContCodigoLibro(diario.getId().getIntContCodigoLibro());
						      transferencia.setIntTranPeriodo(diario.getId().getIntContPeriodoLibro());
					        
						      boTransferencia.grabarTransferencia(transferencia);
						      
						      // corregir segun caso
						      // solicitudPrestamofacade.generarExpedienteMovimiento(expedienteCreditoCompSelected);
				 	}
			       }
				 }
	      }catch(BusinessException e){
	    	  System.out.println("Error en generarAsientoContableRefinanciamiento 1 -->"+e);
				throw e;
		  }catch(Exception e){
			  System.out.println("Error en generarAsientoContableRefinanciamiento 2 -->"+e);
				throw new BusinessException(e);
		  }
		  return diario;
	 }*/

		
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
/*
		private LibroDiario obtieneLibroDiarioYDiarioDetalleLiquidacionCuentas_3( ModeloId idModelo, Integer intTipoCreditoEmpresa,
				ExpedienteLiquidacion expedienteLIquidacion, SolicitudCtaCteTipo solicitudCtaCteTipo, Usuario usuario, SocioComp socioComp) {

				BigDecimal bdValorColumna = null;
				LibroDiario diario = null;
				List<LibroDiarioDetalle> lstDiarioDetalle = new ArrayList<LibroDiarioDetalle>();
				List<ModeloDetalle> listaModDet = null;
				List<CuentaConceptoComp> listaCuentaConceptosComp = null;
				List<CuentaConceptoComp> listaCuentaConceptosMasInteresComp = null;
				
				ModeloFacadeRemote	modeloFacade = null;
				LibroDiarioFacadeRemote libroDiarioFacade = null;
				Boolean blnTieneInteresRetiro= Boolean.FALSE;

				try{
					modeloFacade = (ModeloFacadeRemote)EJBFactory.getRemote(ModeloFacadeRemote.class);
					libroDiarioFacade = (LibroDiarioFacadeRemote)EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
					
					// Recobramos valores de periodo actual
					Integer intPeriodoCuenta = UtilCobranza.obtieneAnio(new Date());
					Integer anio = UtilCobranza.obtieneAnio(new Date());
					String  mes  = UtilCobranza.obtieneMesCadena(new Date());

					// Recuperamos las cuentas concepto de retiro, aportes y agregamos la de interes de retiro.
					listaCuentaConceptosComp = recuperarCuentasConceptoRetiroAportesXSocio(socioComp.getCuenta().getId());
					if(listaCuentaConceptosComp != null && !listaCuentaConceptosComp.isEmpty()){
						listaCuentaConceptosMasInteresComp =  new ArrayList<CuentaConceptoComp>(listaCuentaConceptosComp);

						for (CuentaConceptoComp cuentaConceptoComp : listaCuentaConceptosComp) {
							if(cuentaConceptoComp.getIntParaTipoCaptacionModelo().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0){
								CuentaConceptoComp cuentaConceptoInteresComp = null;
								
								cuentaConceptoInteresComp = recuperarCuentasConceptoInteresRetiro(cuentaConceptoComp.getCuentaConcepto());
								if(cuentaConceptoInteresComp != null){
									blnTieneInteresRetiro = Boolean.TRUE;
									listaCuentaConceptosMasInteresComp.add(cuentaConceptoInteresComp);
								}
							}
						}	
					}

					// Recuperamos el detalle del modelo
					listaModDet =  modeloFacade.getListaModeloDetallePorModeloId(idModelo);

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


						for (CuentaConceptoComp cuentaConceptoComp : listaCuentaConceptosMasInteresComp) {
							//Recorremos el detalle
							for (ModeloDetalle modeloDetalle : listaModDet) {                             
								bdValorColumna= BigDecimal.ZERO;
		
								// modelo detalle
								if (modeloDetalle.getId().getIntContPeriodoCuenta().equals(intPeriodoCuenta)){
									LibroDiarioDetalle diarioDet = new LibroDiarioDetalle();
									diarioDet.setId(new LibroDiarioDetalleId());
		
									List<ModeloDetalleNivel> listaModDetNiv = null;
									listaModDetNiv = modeloFacade.getListaModeloDetNivelPorModeloDetalleId(modeloDetalle.getId());
		
									// Se deberia cargar cada una de las cuentas a liquidar.
										Boolean blnAplicaConceptoGral = Boolean.FALSE;

										// Cuentas concepto
										Boolean blnCumpleTipoCaptacion = Boolean.FALSE;
										Boolean blnCumpleConceptoGeneral = Boolean.FALSE;
										
										for (ModeloDetalleNivel modeloDetalleNivel : listaModDetNiv) {
												if(modeloDetalleNivel.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_LIQUIDACION_CONCEPTOGENERAL_COD)){
													blnAplicaConceptoGral = Boolean.TRUE;
												}
											}
										
										
										if(!blnAplicaConceptoGral){
											blnCumpleConceptoGeneral = Boolean.TRUE;
										}
										
											for (ModeloDetalleNivel modeloDetalleNivel : listaModDetNiv) {

												/////////////////// Es valor fijo
												if(modeloDetalleNivel.getIntParaTipoRegistro().compareTo(Constante.PARAM_T_MODELO_TIPO_REGISTRO_FIJO)==0){

													if(modeloDetalleNivel.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_LIQUIDACION_CUCO_SALDO)
														|| modeloDetalleNivel.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_LIQUIDACION_MONTO_SALDO)){
														if(cuentaConceptoComp.getCuentaConcepto().getBdSaldo() != null){
															bdValorColumna = cuentaConceptoComp.getCuentaConcepto().getBdSaldo();
														}
													}
		
												////////////////// Es tabla	
												}else if(modeloDetalleNivel.getIntParaTipoRegistro().compareTo(Constante.PARAM_T_MODELO_TIPO_REGISTRO_TABLA)==0){
		
													//-- dato tablas  71  - TIPO DE CAPTACION--->	
													if(modeloDetalleNivel.getIntDatoTablas().compareTo(new Integer(Constante.PARAM_T_TIPOCUENTA))==0){
														if(modeloDetalleNivel.getIntDatoArgumento().compareTo(cuentaConceptoComp.getIntParaTipoCaptacionModelo())==0){ 
															//System.out.println("blnCumpleConceptoGeneral ---> "+blnCumpleConceptoGeneral);
															blnCumpleTipoCaptacion = Boolean.TRUE;
															
														}
													}	
													
													//-- dato tablas  212  --->  
														if(modeloDetalleNivel.getIntDatoTablas().compareTo(new Integer(Constante.PARAM_T_TIPOCONCEPTOGENERAL))==0){
															if(modeloDetalleNivel.getIntDatoArgumento().compareTo(cuentaConceptoComp.getIntParaConceptoGeneralModelo())==0){
																//System.out.println("blnCumpleTipoCaptacion ---> "+blnCumpleTipoCaptacion);
																	blnCumpleConceptoGeneral = Boolean.TRUE;
															}
														}
												}
											}
											
											diarioDet.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);

											// terminamos de formar el libro diario detalle
											if(blnCumpleTipoCaptacion && blnCumpleConceptoGeneral){
											//if(blnExisteRiesgo && blnExisteCredito){
												diarioDet.setStrContNumeroCuenta(modeloDetalle.getId().getStrContNumeroCuenta());
												//diarioDet.set
												if(modeloDetalle.getIntParaOpcionDebeHaber().compareTo(Constante.PARAM_T_LIBRO_DIARIO_DEBE)==0){
													diarioDet.setIntParaDocumentoGeneral(modeloDetalle.getIntParaDocumentoGral());
													diarioDet.setBdDebeSoles(bdValorColumna);
													diarioDet.setBdHaberSoles(null);
													// debe  bdValorColumna
												}else if(modeloDetalle.getIntParaOpcionDebeHaber().compareTo(Constante.PARAM_T_LIBRO_DIARIO_HABER)==0){
													// haber   bdValorColumna
													diarioDet.setIntParaDocumentoGeneral(modeloDetalle.getIntParaDocumentoGral());
													diarioDet.setBdHaberSoles(bdValorColumna);
													diarioDet.setBdDebeSoles(null);
												}
												lstDiarioDetalle.add(diarioDet);
											}
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
			}*/
		
		
	/**
	 * Se recupera cuentas conceptos comp de retiro y aportes.
	 * Para el proceso de liquidacion de cuentas.
	 * @param idCuenta
	 * @return
	 */
	/*public List<CuentaConceptoComp> recuperarCuentasConceptoRetiroAportesXSocio(CuentaId idCuenta){
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
			
							if(detalle.getIntParaTipoConceptoCod().compareTo(Constante.PARAM_T_CUENTACONCEPTO_APORTES)==0){
								cuentaConceptoComp.setCuentaConcepto(cuentaConcepto);
								cuentaConceptoComp.setIntParaConceptoGeneralModelo(0);
								cuentaConceptoComp.setIntParaTipoCaptacionModelo(Constante.PARAM_T_CUENTACONCEPTO_APORTES);
								blnAgregar = Boolean.TRUE;
								
							}else if(detalle.getIntParaTipoConceptoCod().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0 ){
								cuentaConceptoComp.setCuentaConcepto(cuentaConcepto);
								cuentaConceptoComp.setIntParaConceptoGeneralModelo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO);
								cuentaConceptoComp.setIntParaTipoCaptacionModelo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO);
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
	}*/
		
		
		/**
		 * Le adiciona el concepto de INTERES a la lista de cuentas concepto del socio. Solo para fines practicos.
		 * Interes no es cuenta concepto
		 * @param cuentaConceptoInteresRetiro
		 * @return
		 */
		/*public CuentaConceptoComp recuperarCuentasConceptoInteresRetiro(CuentaConcepto cuentaConceptoRetiro){
			List<CuentaConcepto> listaCuentaConcepto = null;
			List<CuentaConceptoComp> listaCuentaConceptoComp = null;
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
						cuentaConceptoInteres.setBdSaldo(bdMonto);
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
		}*/
		
		
		
		/**
		 * Recupera las cuentas concepto de aoprte retiro y si tuviera la de interes
		 * @return
		 */
		/*public List<CuentaConceptoComp> recuperarCuentasConceptoAporteRetiroEInteres(SocioComp socioComp)throws BusinessException{
			
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
							
							cuentaConceptoInteresComp = recuperarCuentasConceptoInteresRetiro(cuentaConceptoComp.getCuentaConcepto());
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
		}*/
		
		
		
		/**
		 * 
		 * @param socioComp
		 * @param libroDiarioLiquidacion
		 * @throws BusinessException
		 */
		
		/*public Boolean validarYGenerarCuentaConceptoMovimentoYCuentaCtoDetalleActualizaciones_3 (SocioComp socioComp,LibroDiario libroDiarioLiquidacion, Usuario usuario)throws BusinessException{
			List<CuentaConceptoComp> listaCuentasConceptoComp = null;
			//List<Movimiento> listaMovientoLiquidacionCuentas = null;
			ConceptoFacadeRemote conceptoFacadeRemote = null;
			Boolean blnHasAporte = Boolean.FALSE;
			Boolean blnHasRetiro = Boolean.FALSE;
			Boolean blnHasInteres = Boolean.FALSE;
			Boolean blnCumpleAporte = Boolean.FALSE;
			Boolean blnCumpleRetiro = Boolean.FALSE;
			Boolean blnCumpleInteres = Boolean.FALSE;
			CuentaConcepto cuentaConceptoInteres = null;
			
			Boolean blnOK = Boolean.FALSE;
			
			try {
				conceptoFacadeRemote = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
				if(libroDiarioLiquidacion != null){
					listaCuentasConceptoComp = recuperarCuentasConceptoAporteRetiroEInteres(socioComp);
					if(listaCuentasConceptoComp != null && !listaCuentasConceptoComp.isEmpty()){

						// definimos la existencia de Aportes, Retiro e Inetres de retiro.
						for (CuentaConceptoComp ctaCtoComp : listaCuentasConceptoComp) {
							if(ctaCtoComp.getIntParaTipoCaptacionModelo().compareTo(Constante.PARAM_T_CUENTACONCEPTO_APORTES)==0){
								blnHasAporte = Boolean.TRUE;
							}else if(ctaCtoComp.getIntParaTipoCaptacionModelo().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0){
									if(ctaCtoComp.getIntParaConceptoGeneralModelo().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO)==0){
										blnHasRetiro = Boolean.TRUE;
									}else if(ctaCtoComp.getIntParaConceptoGeneralModelo().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO_INTERES)==0){
										cuentaConceptoInteres = new CuentaConcepto();
										cuentaConceptoInteres = ctaCtoComp.getCuentaConcepto();
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
									
									// intPersEmpresa, intCuenta, intItemCuentaConcepto
									listaMov = conceptoFacadeRemote.getListaMaximoMovimientoPorCuentaConcepto(cuentaConceptoComp.getCuentaConcepto().getId().getIntPersEmpresaPk(), 
																											cuentaConceptoComp.getCuentaConcepto().getId().getIntCuentaPk(), 
																											cuentaConceptoComp.getCuentaConcepto().getId().getIntItemCuentaConcepto());
									
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
											cuentaConceptoComp.getCuentaConcepto().getId().getIntItemCuentaConcepto());
									
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
								if(cuentaConceptoComp.getIntParaTipoCaptacionModelo().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0
									&& cuentaConceptoComp.getIntParaConceptoGeneralModelo().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO)==0){
										
									CuentaConcepto  cuentaConceptoRetiro = cuentaConceptoComp.getCuentaConcepto();
									List<Movimiento> listaMovimiento = null;
									
									listaMovimiento = conceptoFacadeRemote.getListaMovimientoPorCtaItemConceptoTipoConceptoGeneral(cuentaConceptoRetiro.getId().getIntPersEmpresaPk(), 
											cuentaConceptoRetiro.getId().getIntCuentaPk(), 
											cuentaConceptoRetiro.getId().getIntItemCuentaConcepto(), 
											Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO_INTERES);
									
									if(listaMovimiento != null && !listaMovimiento.isEmpty()){
										if(listaMovimiento.get(0).getBdMontoSaldo().compareTo(cuentaConceptoInteres.getBdSaldo())==0){
											blnCumpleInteres = Boolean.TRUE;
										}
									}	
								}
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
							blnOK =  generarRegistrosMovimientoLiquidacionCuentas_4(listaCuentasConceptoComp,socioComp,usuario );
							
						}else{
							blnOK = Boolean.FALSE;
							throw new BusinessException("No Cumple las validaciones de Cuenta Concepto, Movimiento y cuanta concepto detalle. Se revierte Todo.");
						}
					}
				}
				
				
			} catch (Exception e) {
				blnOK = Boolean.FALSE;
				throw new BusinessException("Error en validarCuentaConceptoMovimentoYCuentaCtoDetalle_3 --->" +e);
			}

			return blnOK;
	
		}*/
		
		
		/**
		 * Se forman y graban los registros movimeinto para aporte, retiro, interes y el cat x pagar
		 * @param listaCuentasConceptoComp
		 * @return
		 */
	/*	public Boolean generarRegistrosMovimientoLiquidacionCuentas_4(List<CuentaConceptoComp> listaCuentasConceptoComp, SocioComp socioComp, Usuario usuario)  throws BusinessException{
			List<Movimiento> listaMovimientos = null;
			List<CuentaIntegrante> lstCuentaIntegrante = null;
			ConceptoFacadeRemote conceptoFacade= null;
			CuentaFacadeRemote cuentaFacade = null;
			Integer intIdPersona = new Integer(0);
			Boolean blnTodoOK = Boolean.FALSE;
			
			try {
				conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
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
							movAporte.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCGENERAL);
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
									movRetiro.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCGENERAL);
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
										movInteres.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCGENERAL);
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
					movAbonoXPagar.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCGENERAL);
					movAbonoXPagar.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
					movAbonoXPagar.setBdMontoMovimiento(bdMontoAbonoCtaXPagar);
					movAbonoXPagar.setBdMontoSaldo(bdMontoAbonoCtaXPagar);
					movAbonoXPagar.setIntPersEmpresaUsuario(usuario.getEmpresa().getIntIdEmpresa()); // usuario
					movAbonoXPagar.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk()); // usuario
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
					blnTodoOK = grabarYActualizarMovimiento_CuentaConcepto_Detalle_5(listaMovimientos, listaCuentasConceptoComp, socioComp);

				}
				
			} catch (Exception e) {
				blnTodoOK = Boolean.FALSE;
				throw new BusinessException("Error en generarRegistrosMovimientoLiquidacionCuentas_4 --->" +e);
			}
			
			return blnTodoOK; 
			
		}*/

		
		/**
		 * 
		 * @param listaMovimientos
		 * @param listaCuentasConceptoComp
		 * @throws BusinessException
		 */
		/*private Boolean grabarYActualizarMovimiento_CuentaConcepto_Detalle_5( List<Movimiento> listaMovimientos,List<CuentaConceptoComp> listaCuentasConceptoComp, SocioComp socioComp)throws BusinessException {
			ConceptoFacadeRemote conceptoFacadeRemote= null;
			CuentaFacadeRemote cuentaFacade = null;
			List<Movimiento> lstMovimientoResult = null;
			Boolean blnOk= Boolean.FALSE;
			try {
				conceptoFacadeRemote = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
				cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
				
				if(listaMovimientos != null && !listaMovimientos.isEmpty())
					//&& listaCuentasConceptoComp != null && !listaCuentasConceptoComp.isEmpty())
						{
					
					lstMovimientoResult = new ArrayList<Movimiento>();
					
					for (Movimiento movimiento : listaMovimientos) {
						movimiento = conceptoFacadeRemote.grabarMovimiento(movimiento);
						lstMovimientoResult.add(movimiento);						
					}
					
					
					// ACTUALIZAMOS LAS CUENTAS CONCEPTO, DETALLE Y SITUACION DE CUENTA
					
					for (CuentaConceptoComp cuentaCtoComp : listaCuentasConceptoComp) {
						
						//for (CuentaConceptoComp cuentaConceptoComp : listaCuentasConceptoComp) {

							if(cuentaCtoComp.getIntParaTipoCaptacionModelo().compareTo(Constante.PARAM_T_CUENTACONCEPTO_APORTES)==0){
								// a. es aporte
								 List<CuentaConceptoDetalle> listaDetalle = null;
								 listaDetalle = conceptoFacadeRemote.getMaxCuentaConceptoDetPorPKCuentaConcepto(cuentaCtoComp.getCuentaConcepto().getId());
									
									if(listaDetalle != null && !listaDetalle.isEmpty()){
										CuentaConceptoDetalle ctaDet = new CuentaConceptoDetalle();
										ctaDet = listaDetalle.get(0);
										ctaDet.setBdSaldoDetalle(BigDecimal.ZERO);
										ctaDet.setTsFin(UtilCobranza.obtieneFechaActualEnTimesTamp());
										
										ctaDet = conceptoFacadeRemote.modificarCuentaConceptoDetalle(ctaDet);
										if(ctaDet.getBdSaldoDetalle().compareTo(BigDecimal.ZERO)==0){
											cuentaCtoComp.getCuentaConcepto().setBdSaldo(BigDecimal.ZERO);
											conceptoFacadeRemote.modificarCuentaConcepto(cuentaCtoComp.getCuentaConcepto());
										}
										
									}

							}else if(cuentaCtoComp.getIntParaTipoCaptacionModelo().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0){
								
									if(cuentaCtoComp.getIntParaConceptoGeneralModelo().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO)==0){
										// b. es retiro
										 List<CuentaConceptoDetalle> listaDetalle = null;
										 listaDetalle = conceptoFacadeRemote.getMaxCuentaConceptoDetPorPKCuentaConcepto(cuentaCtoComp.getCuentaConcepto().getId());
											
											if(listaDetalle != null && !listaDetalle.isEmpty()){
												CuentaConceptoDetalle ctaDet = new CuentaConceptoDetalle();
												ctaDet = listaDetalle.get(0);
												ctaDet.setBdSaldoDetalle(BigDecimal.ZERO);
												ctaDet.setTsFin(UtilCobranza.obtieneFechaActualEnTimesTamp());
												
												ctaDet = conceptoFacadeRemote.modificarCuentaConceptoDetalle(ctaDet);
												if(ctaDet.getBdSaldoDetalle().compareTo(BigDecimal.ZERO)==0){
													cuentaCtoComp.getCuentaConcepto().setBdSaldo(BigDecimal.ZERO);
													conceptoFacadeRemote.modificarCuentaConcepto(cuentaCtoComp.getCuentaConcepto());
												}
											}
										
									}
							}	
						//}
						
						
						
					}
					
					
					/// situaciond e cuenta
					// PARAM_T_SITUACIONCUENTA_POR_LIQUIDAR
					Cuenta cuenta = new Cuenta();
					cuenta = socioComp.getCuenta();
					cuenta.setIntParaSituacionCuentaCod(Constante.PARAM_T_SITUACIONCUENTA_POR_LIQUIDAR);
					cuenta = cuentaFacade.modificarCuenta(cuenta);
					if(cuenta != null){
						
						if(cuenta.getIntParaSituacionCuentaCod().compareTo(Constante.PARAM_T_SITUACIONCUENTA_POR_LIQUIDAR)==0){
							blnOk = Boolean.TRUE;
						}
						System.out.println("SITUACION DE LA CUENTA LIQUIDADA ---> "+cuenta.getIntParaSituacionCuentaCod());
						
					}

				}

			} catch (Exception e) {
				blnOk = Boolean.FALSE;
				log.error("Error en grabarYActualizarMovimiento_CuentaConcepto_Detalle_5 ---> "+e);
				// TODO: handle exception
			}
			return blnOk;
			
		}*/
		
		 	 
		
		 private void generarConceptoDetallePago(Movimiento movimientoTemporal, ConceptoPago conceptoPagoX, BigDecimal b) throws BusinessException{
			 ConceptoFacadeRemote conceptoFacade = null;
			 try{
				 	conceptoFacade  = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
				 	ConceptoDetallePago detalle = new ConceptoDetallePago();
					detalle.setId(new ConceptoDetallePagoId());
					detalle.getId().setIntPersEmpresaPk(conceptoPagoX.getId().getIntPersEmpresaPk());
					detalle.getId().setIntCuentaPk(conceptoPagoX.getId().getIntCuentaPk());
					detalle.getId().setIntItemCuentaConcepto(conceptoPagoX.getId().getIntItemCuentaConcepto());
					detalle.getId().setIntItemCtaCptoDet(conceptoPagoX.getId().getIntItemCtaCptoDet());
					detalle.getId().setIntItemConceptoPago(conceptoPagoX.getId().getIntItemConceptoPago());
					detalle.getId().setIntItemMovCtaCte(movimientoTemporal.getIntItemMovimiento());
					detalle.setIntTipoCargoAbono(movimientoTemporal.getIntParaTipoCargoAbono());
					detalle.setBdMonto(b);
					conceptoFacade.grabarConceptoDetallePago(detalle);
			 }catch(BusinessException e){
					throw e;
			 }catch(Exception e){
					throw new BusinessException(e);
			 }
		 }
		 
}
