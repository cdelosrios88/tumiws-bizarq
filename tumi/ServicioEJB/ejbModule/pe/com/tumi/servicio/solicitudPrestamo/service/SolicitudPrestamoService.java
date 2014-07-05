package pe.com.tumi.servicio.solicitudPrestamo.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;
import pe.com.tumi.cobranza.planilla.facade.PlanillaFacadeRemote;
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
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;
import pe.com.tumi.contabilidad.core.facade.ModeloFacadeRemote;
import pe.com.tumi.contabilidad.core.facade.PlanCuentaFacadeRemote;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.facade.CuentaFacadeRemote;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.domain.SocioEstructuraPK;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoInteres;
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
import pe.com.tumi.movimiento.concepto.domain.Cronograma;
import pe.com.tumi.movimiento.concepto.domain.CronogramaId;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;
import pe.com.tumi.movimiento.concepto.domain.EstadoExpediente;
import pe.com.tumi.movimiento.concepto.domain.EstadoExpedienteId;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.movimiento.concepto.domain.ExpedienteId;
import pe.com.tumi.movimiento.concepto.domain.InteresCancelado;
import pe.com.tumi.movimiento.concepto.domain.InteresCanceladoId;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;
import pe.com.tumi.movimiento.concepto.domain.composite.ExpedienteComp;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.TipoArchivo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.riesgo.cartera.domain.CarteraCreditoDetalle;
import pe.com.tumi.riesgo.cartera.facade.CarteraFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.configuracion.bo.ConfServDetalleBO;
import pe.com.tumi.servicio.configuracion.domain.ConfServCredito;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalleId;
import pe.com.tumi.servicio.configuracion.domain.ConfServEstructuraDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServPerfil;
import pe.com.tumi.servicio.configuracion.domain.ConfServSolicitud;
import pe.com.tumi.servicio.configuracion.domain.ConfServUsuario;
import pe.com.tumi.servicio.configuracion.facade.ConfSolicitudFacadeLocal;
import pe.com.tumi.servicio.configuracion.facade.ConfSolicitudFacadeRemote;
import pe.com.tumi.servicio.solicitudPrestamo.bo.AutorizaCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.AutorizaVerificacionBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.CancelacionCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.CapacidadCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.CapacidadDescuentoBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.CronogramaCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.EstadoCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.ExpedienteActividadBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.ExpedienteCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.GarantiaCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.RequisitoCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaVerificacion;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaVerificacionId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CancelacionCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CancelacionCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CapacidadCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CapacidadCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CapacidadDescuento;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CapacidadDescuentoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CronogramaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CronogramaCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteActividad;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteActividadId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.RequisitoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.RequisitoCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.CapacidadCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.CronogramaCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.ExpedienteCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.RequisitoCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.facade.PrestamoFacadeLocal;
import pe.com.tumi.servicio.solicitudPrestamo.facade.PrestamoFacadeRemote;
import pe.com.tumi.servicio.solicitudPrestamo.facade.SolicitudPrestamoFacadeLocal;
import pe.com.tumi.servicio.solicitudPrestamo.facade.SolicitudPrestamoFacadeRemote;

public class SolicitudPrestamoService {
	
	protected static Logger log = Logger.getLogger(SolicitudPrestamoService.class);
	
	private ExpedienteCreditoBO boExpedienteCredito = (ExpedienteCreditoBO)TumiFactory.get(ExpedienteCreditoBO.class);
	private CapacidadCreditoBO boCapacidadCredito = (CapacidadCreditoBO)TumiFactory.get(CapacidadCreditoBO.class);
	private CapacidadDescuentoBO boCapacidadDescuento = (CapacidadDescuentoBO)TumiFactory.get(CapacidadDescuentoBO.class);
	private EstadoCreditoBO boEstadoCredito = (EstadoCreditoBO)TumiFactory.get(EstadoCreditoBO.class);
	private CronogramaCreditoBO boCronogramaCredito = (CronogramaCreditoBO)TumiFactory.get(CronogramaCreditoBO.class);
	private GarantiaCreditoBO boGarantiaCredito = (GarantiaCreditoBO)TumiFactory.get(GarantiaCreditoBO.class);
	private AutorizaCreditoBO boAutorizaCredito = (AutorizaCreditoBO)TumiFactory.get(AutorizaCreditoBO.class);
	private AutorizaVerificacionBO boAutorizaVerificacion = (AutorizaVerificacionBO)TumiFactory.get(AutorizaVerificacionBO.class);
	private RequisitoCreditoBO boRequisitoCredito = (RequisitoCreditoBO)TumiFactory.get(RequisitoCreditoBO.class);
	private ExpedienteActividadBO boExpedienteActividad = (ExpedienteActividadBO)TumiFactory.get(ExpedienteActividadBO.class);
	private CancelacionCreditoBO boCancelacionCredito = (CancelacionCreditoBO)TumiFactory.get(CancelacionCreditoBO.class);
	private ConfServDetalleBO boConfServDetalle = (ConfServDetalleBO)TumiFactory.get(ConfServDetalleBO.class);
	
	/**
	 * Realiza la busqueda de todos los expedientes. Para la grilla de busqueda.
	 * PENDIENTE MODIFICACION PARA ACEPTAR LOS PARAMETROS DE LA BUSQUEDA.
	 * @param o
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCreditoComp> getListaExpedienteCreditoCompDeBusqueda(ExpedienteCreditoComp o) throws BusinessException{
		ExpedienteCreditoComp dto = null;
		List<ExpedienteCreditoComp> lista = null;
		List<ExpedienteCredito> listaExpedienteCredito = null;
		EstadoCredito estadoCredito;
		List<EstadoCredito> listaEstadoCredito = null;
		List<CapacidadCredito> listaCapacidadCredito = null;
		SocioComp socioComp = null;
		Persona persona = null;
		Integer intIdPersona = null;
		PersonaFacadeRemote personaFacade = null;
		SocioFacadeRemote socioFacade = null;
		TablaFacadeRemote  tablaFacade = null;
		CreditoFacadeRemote creditoFacade = null;
		CreditoId creditoId = null;
		List<Tabla> listaDescripcionExpedienteXredito= null;
		String strDescripcionExpedienteCredito = null;
		
		try{
			
			socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			creditoFacade = (CreditoFacadeRemote) EJBFactory.getRemote(CreditoFacadeRemote.class);
			
			listaExpedienteCredito = boExpedienteCredito.getListaBusquedaPorExpCredComp(Constante.PARAM_T_TIPO_CREDITO_PRESTAMO);
			if(listaExpedienteCredito != null && listaExpedienteCredito.size()>0){
				lista = new ArrayList<ExpedienteCreditoComp>();
				for(ExpedienteCredito expedienteCredito : listaExpedienteCredito){
					// solo deben mostarse las 1ras versiones
					if(expedienteCredito.getId().getIntItemDetExpediente().compareTo(1)==0){
					
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
					
					
					// Recuperamos el nombre general del prestamo asociado al expediente
					creditoId = new CreditoId();
					creditoId.setIntItemCredito(expedienteCredito.getIntItemCredito());
					creditoId.setIntParaTipoCreditoCod(expedienteCredito.getIntParaTipoCreditoCod());
					creditoId.setIntPersEmpresaPk(expedienteCredito.getIntPersEmpresaCreditoPk());
					Credito creditoRec = null;
					strDescripcionExpedienteCredito = "Desconocido";
					creditoRec = creditoFacade.getCreditoPorIdCreditoDirecto(creditoId);
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
					
					dto.setStrDescripcionExpedienteCredito(strDescripcionExpedienteCredito);
					lista.add(dto);
				}
			}
		}catch(BusinessException e){
			log.error("Error - BusinessException - en getListaExpedienteCreditoCompDeBusqueda --> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en getListaExpedienteCreditoCompDeBusqueda --> "+e);
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	/**
	 * Realiza la busqueda de todas las autoriuzaciones de credito. Para la grilla de busqueda.
	 * PENDIENTE MODIFICACION PARA ACEPTAR LOS PARAMETROS DE LA BUSQUEDA.
	 * @param o
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCreditoComp> getListaAutorizacionCreditoCompDeBusqueda(ExpedienteCreditoComp o) throws BusinessException{
		ExpedienteCreditoComp dto = null;
		List<ExpedienteCreditoComp> lista = null;
		List<ExpedienteCredito> listaExpedienteCredito = null;
		List<EstadoCredito> listaEstadoCredito = null;
		List<CapacidadCredito> listaCapacidadCredito = null;
		SocioComp socioComp = null;
		Persona persona = null;
		Integer intIdPersona = null;
		PersonaFacadeRemote personaFacade = null;
		SocioFacadeRemote socioFacade = null;
		
		try{
			socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			listaExpedienteCredito = boExpedienteCredito.getListaBusquedaAutorizacionPorExpCredComp();
			if(listaExpedienteCredito != null && listaExpedienteCredito.size()>0){
				lista = new ArrayList<ExpedienteCreditoComp>();
				for(ExpedienteCredito expedienteCredito : listaExpedienteCredito){
					dto = new ExpedienteCreditoComp();
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
						// seteamos el ultimo estado al expedienteCreditoComp
						int cantEstados=listaEstadoCredito.size();
						dto.setEstadoCredito(listaEstadoCredito.get(cantEstados-1));
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
			}
		}catch(BusinessException e){
			log.error("Error - BusinessException - en getListaAutorizacionCreditoCompDeBusqueda --> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en getListaAutorizacionCreditoCompDeBusqueda --> "+e);
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	/**
	 * Recupera todo el Expediente de Credito
	 * @param ExpedienteCreditoId pId
	 * @return expedienteCredito
	 * @throws BusinessException
	 */
	public ExpedienteCredito getExpedienteCreditoPorIdExpedienteCredito(ExpedienteCreditoId pId) throws BusinessException {
		ExpedienteCredito expedienteCredito = null;
		List<EstadoCredito> listaEstadoCredito = null;
		CapacidadCreditoComp capacidadCreditoComp = null;
		SocioEstructura socioEstructura = null;
		List<CapacidadCreditoComp> listaCapacidadCreditoComp = null;
		List<CapacidadCredito> listaCapacidadCredito = null;
		List<CapacidadDescuento> listaCapacidadDscto = null;
		List<CronogramaCredito> listaCronogramaCredito = null;
		List<GarantiaCredito> listaGarantiaCredito = null;
		List<RequisitoCredito> listaRequisitoCredito = null;
		List<RequisitoCreditoComp> listaRequisitoCreditoComp = null;
		RequisitoCreditoComp requisitoCreditoComp = null;
		ConfServDetalle detalle = null;
		Archivo archivo = null;
		TipoArchivo tipoArchivo = null;
		MyFile myFile = null;
		SocioFacadeRemote socioFacade = null;
		ConfSolicitudFacadeLocal solicitudFacade = null;
		GeneralFacadeRemote generalFacade = null;
		EstadoCredito estadoCreditoUltimo = null;
		EstadoCredito primerEstadoCredito = null;
		
		List<CancelacionCredito> listaCancelacionCredito = null;

		try {
				socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
				solicitudFacade = (ConfSolicitudFacadeLocal)EJBFactory.getLocal(ConfSolicitudFacadeLocal.class);
				generalFacade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
				
				expedienteCredito = boExpedienteCredito.getPorPk(pId);
				
				if(expedienteCredito!=null){
					listaEstadoCredito = boEstadoCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
					if(listaEstadoCredito!=null && listaEstadoCredito.size()>0){
						expedienteCredito.setListaEstadoCredito(listaEstadoCredito);
					}
					
					
					estadoCreditoUltimo= obtenerUltimoEstadoCredito(expedienteCredito);
					if(estadoCreditoUltimo != null){
						expedienteCredito.setEstadoCreditoUltimo(estadoCreditoUltimo);
					}
					
					primerEstadoCredito = boEstadoCredito.getMinEstadoCreditoPorPkExpedienteCredito (expedienteCredito.getId());
					if(primerEstadoCredito != null){
						expedienteCredito.setEstadoCreditoPrimero(primerEstadoCredito);
					}
					
					listaCapacidadCredito = boCapacidadCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
					if(listaCapacidadCredito!=null && listaCapacidadCredito.size()>0){
						listaCapacidadCreditoComp = new ArrayList<CapacidadCreditoComp>();
						for (CapacidadCredito capacidadCredito : listaCapacidadCredito) {
							if(capacidadCredito.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){

								capacidadCreditoComp = new CapacidadCreditoComp();
								socioEstructura = new SocioEstructura();
								socioEstructura.setId(new SocioEstructuraPK());
								listaCapacidadDscto = boCapacidadDescuento.getListaPorCapacidadCreditoPk(capacidadCredito.getId());
								if(listaCapacidadDscto != null && listaCapacidadDscto.size()>0){
									capacidadCredito.setListaCapacidadDscto(boCapacidadDescuento.getListaPorCapacidadCreditoPk(capacidadCredito.getId()));
								}
								
								socioEstructura.getId().setIntIdEmpresa(capacidadCredito.getId().getIntPersEmpresaPk());
								socioEstructura.getId().setIntIdPersona(capacidadCredito.getIntPersPersonaPk());
								socioEstructura.getId().setIntItemSocioEstructura(capacidadCredito.getIntItemSocioEstructura());
								socioEstructura = socioFacade.getSocioEstructuraPorPk(socioEstructura.getId());
								capacidadCreditoComp.setCapacidadCredito(capacidadCredito);
								capacidadCreditoComp.setSocioEstructura(socioEstructura);
								listaCapacidadCreditoComp.add(capacidadCreditoComp);
							}
							
						}
						expedienteCredito.setListaCapacidadCreditoComp(listaCapacidadCreditoComp);
						expedienteCredito.setListaCapacidadCredito(listaCapacidadCredito);
					}
					
					listaCronogramaCredito = boCronogramaCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
					if(listaCronogramaCredito!=null && listaCronogramaCredito.size()>0){
						expedienteCredito.setListaCronogramaCredito(listaCronogramaCredito);
					}
					
					listaGarantiaCredito = boGarantiaCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
					if(listaGarantiaCredito!=null && listaGarantiaCredito.size()>0){
						List<GarantiaCredito> listaGarantiaFiltrada = new ArrayList<GarantiaCredito>();
						for (GarantiaCredito garantia : listaGarantiaCredito) {
							if(garantia.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
								listaGarantiaFiltrada.add(garantia);	
							}
						}
						expedienteCredito.setListaGarantiaCredito(listaGarantiaCredito);
					}
					
					listaRequisitoCredito = boRequisitoCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
					if(listaRequisitoCredito!=null && listaRequisitoCredito.size()>0){
						listaRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
						ConfServDetalle confServDetalle = new ConfServDetalle();
						for(RequisitoCredito requisitoCredito : listaRequisitoCredito){
							//jchavez 23.06.2014 se quita de la lista el adjunto de giro para mostrar en la solicitud
							ConfServDetalleId id = new ConfServDetalleId();
							id.setIntPersEmpresaPk(requisitoCredito.getIntPersEmpresaRequisitoPk());
							id.setIntItemSolicitud(requisitoCredito.getIntItemReqAut());
							id.setIntItemDetalle(requisitoCredito.getIntItemReqAutDet());
							confServDetalle = boConfServDetalle.getPorPk(id);

							if(requisitoCredito.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0 
									&& !(confServDetalle.getIntParaTipoDescripcion().compareTo(22)==0)){
								requisitoCreditoComp = new RequisitoCreditoComp();
								detalle = new ConfServDetalle();
								detalle.setId(new ConfServDetalleId());
								archivo = new Archivo();
								archivo.setId(new ArchivoId());
								tipoArchivo = new TipoArchivo();
								myFile = new MyFile();
								requisitoCreditoComp.setRequisitoCredito(requisitoCredito);
								detalle.getId().setIntPersEmpresaPk(requisitoCredito.getIntPersEmpresaRequisitoPk());
								detalle.getId().setIntItemSolicitud(requisitoCredito.getIntItemReqAut());
								detalle.getId().setIntItemDetalle(requisitoCredito.getIntItemReqAutDet());
								detalle = solicitudFacade.getConfServDetallePorPk(detalle.getId());
								requisitoCreditoComp.setDetalle(detalle);
								archivo.getId().setIntParaTipoCod(requisitoCredito.getIntParaTipoArchivoCod());
								archivo.getId().setIntItemArchivo(requisitoCredito.getIntParaItemArchivo());
								archivo.getId().setIntItemHistorico(requisitoCredito.getIntParaItemHistorico());
								
								archivo = generalFacade.getArchivoPorPK(archivo.getId());
								requisitoCreditoComp.setArchivoAdjunto(archivo);
								
								if(archivo !=null /*&& archivo.getFile() != null*/){
									tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoCredito.getIntParaTipoArchivoCod());
									if(tipoArchivo!=null){								
										if(archivo.getId().getIntParaTipoCod()!= null && archivo.getId().getIntItemArchivo()!= null && 
												archivo.getId().getIntItemHistorico() != null/* && tipoArchivo.getStrRuta() != null && archivo.getStrNombrearchivo()!= null*/){
											
											byte[] byteImg = null;
											
											try {
												if (existFile(tipoArchivo.getStrRuta()+ "\\" + archivo.getStrNombrearchivo())) {
													byteImg = FileUtil.getDataImage(tipoArchivo.getStrRuta()+ "\\" + archivo.getStrNombrearchivo());
												}												
											} catch (IOException e) {
												e.printStackTrace();
											}
											if(byteImg != null && byteImg.length != 0){
												myFile.setLength(byteImg.length);
												myFile.setName(archivo.getStrNombrearchivo());
												myFile.setData(byteImg);
												archivo.setRutaActual(tipoArchivo.getStrRuta());
												archivo.setStrNombrearchivo(archivo.getStrNombrearchivo());
												requisitoCreditoComp.setFileDocAdjunto(myFile);
												requisitoCreditoComp.setArchivoAdjunto(archivo);
											}
											
										}
			
									}
								}
								listaRequisitoCreditoComp.add(requisitoCreditoComp);
							}
						}
						expedienteCredito.setListaRequisitoCreditoComp(listaRequisitoCreditoComp);
					}
					
					// CGD - 07.11.2013
					listaCancelacionCredito = boCancelacionCredito.getListaPorExpedienteCredito(expedienteCredito);
					if(listaCancelacionCredito != null && !listaCancelacionCredito.isEmpty()){
						expedienteCredito.setListaCancelacionCredito(listaCancelacionCredito);
					}
				}
		} catch(BusinessException e){
			log.error("Error - BusinessException - en getExpedienteCreditoPorIdExpedienteCredito ---> "+e);
			throw e;
		} catch (EJBFactoryException e) {
			log.error("Error - EJBFactoryException - en getExpedienteCreditoPorIdExpedienteCredito ---> "+e);
			e.printStackTrace();
		}
		return expedienteCredito;
	}
	
	public static boolean existFile(String path) {
		boolean exist = false;

		File fichero = new File(path);
		if (fichero.exists())
			exist = true;

		return exist;
	}
	
	/**
	 * 
	 * @param stream
	 * @param object
	 * @throws IOException
	 */
	public void paintImage(OutputStream stream, Object object)
	throws IOException {
		stream.write(((MyFile) object).getData());
	}
	
	
	/**
	 * Metodo que graba el Expediente de Credito. Estados, Capacidad, Cronograma, Garantias, Requisitos, Actividades y Cancelacion.
	 * Tambien usado para Expediente de Actividad.
	 * @param pExpedienteCredito
	 * @return
	 * @throws BusinessException
	 */
	public ExpedienteCredito grabarExpedienteCredito(ExpedienteCredito pExpedienteCredito) throws BusinessException{
		ExpedienteCredito expedienteCredito = null;
		List<EstadoCredito> listaEstadoCredito = null;
		List<CapacidadCreditoComp> listaCapacidadCredito = null;
		List<CronogramaCredito> listaCronogramaCredito = null;
		List<GarantiaCredito> listaGarantiaCredito = null;
		List<RequisitoCreditoComp> listaRequisitoCreditoComp = null;
		List<ExpedienteActividad> listaExpedienetActividad = null;
		List<CancelacionCredito> listaCancelacioCredito = null;
		
		try{			
			expedienteCredito = boExpedienteCredito.grabar(pExpedienteCredito);
			
			listaEstadoCredito = pExpedienteCredito.getListaEstadoCredito();
			
			// Si pasa directo a estado Solicitud, se genera un estado previo de requisito
			if(listaEstadoCredito != null && !listaEstadoCredito.isEmpty()){
				if(listaEstadoCredito.get(0).getIntParaEstadoCreditoCod().intValue() == Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD.intValue()){
					grabarEstadoRequisitoDefault(listaEstadoCredito.get(0), expedienteCredito.getId());
				}
			}
			
			//Grabar Lista Estado Crédito
			if(listaEstadoCredito!=null && !listaEstadoCredito.isEmpty()){
				grabarListaDinamicaEstadoCredito(listaEstadoCredito, expedienteCredito.getId());
			}
			
			listaCapacidadCredito = pExpedienteCredito.getListaCapacidadCreditoComp();
			//Grabar Lista Capacidad Crédito
			if(listaCapacidadCredito!=null &&!listaCapacidadCredito.isEmpty()){
				// Se valida que se haya grabado anteriormente el credito
				if(expedienteCredito.getId().getIntItemExpediente() != null
					&& expedienteCredito.getId().getIntItemDetExpediente() != null){
					listaCapacidadCredito = grabarListaDinamicaSocioEstructuraParaCredito(listaCapacidadCredito, pExpedienteCredito.getSocioComp());
				}
				grabarListaDinamicaCapacidadCreditoComp(listaCapacidadCredito, expedienteCredito.getId());

			}
			
			listaCronogramaCredito = pExpedienteCredito.getListaCronogramaCredito();
			//Graba Cronograma
			if(listaCronogramaCredito!=null && !listaCronogramaCredito.isEmpty()){
				grabarListaDinamicaCronogramaCredito(listaCronogramaCredito, expedienteCredito.getId());
			}
			
			listaGarantiaCredito = pExpedienteCredito.getListaGarantiaCredito();
			//Grabar Lista de Garantias de Credito
			if(listaGarantiaCredito!=null && !listaGarantiaCredito.isEmpty()){
				grabarListaDinamicaGarantiaCredito(listaGarantiaCredito, expedienteCredito.getId());
			}
			
			listaRequisitoCreditoComp = pExpedienteCredito.getListaRequisitoCreditoComp();
			//Grabar Lista de requisitos de Credito
			if(listaRequisitoCreditoComp!=null && !listaRequisitoCreditoComp.isEmpty()){
				expedienteCredito.setListaRequisitoCreditoComp(grabarListaDinamicaRequisitoCredito(listaRequisitoCreditoComp, expedienteCredito.getId()));
			}
			
			// lista de actividades
			listaExpedienetActividad = pExpedienteCredito.getListaExpedienteActividad();
			if(listaExpedienetActividad != null && !listaExpedienetActividad.isEmpty()){
				//if(listaEstadoCredito.get(0).getIntParaEstadoCreditoCod().intValue() == Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD.intValue()){
					grabarListaDinamicaExpedienteActividad(listaExpedienetActividad, expedienteCredito.getId());				
				//}
			}
			
			// CGD - 07.11.2013 - REPRESTAMO
			//Grabar Lista Cancelacion
			listaCancelacioCredito = pExpedienteCredito.getListaCancelacionCredito();
			if(listaCancelacioCredito!=null && !listaCancelacioCredito.isEmpty()){
				grabarListaDinamicaCancelacionCredito(listaCancelacioCredito, expedienteCredito.getId());
			}
			
		}catch(BusinessException e){
			log.error("Error - BusinessException - en grabarExpedienteCredito ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en grabarExpedienteCredito ---> "+e);
			throw new BusinessException(e);
		}
		return expedienteCredito;
	}
	
	
	/**
	 * Se utiliza para borrar el cronograma de un credito cuyo monto y/o numero de cuotas ha cambiado.
	 * @param pCronogramaCreditoId
	 * @throws BusinessException
	 */
	public void eliminarCronogramaCredito(CronogramaCreditoId pCronogramaCreditoId)throws BusinessException{
		try {
			boCronogramaCredito.deletePorPk(pCronogramaCreditoId);
		} catch (Exception e) {
			log.error("no se pudo eliminar el Cronogra. "+e);
		}
		
	}	
	/**
	 * Modifica de expediente de Credito
	 * @param pExpedienteCredito
	 * @return
	 * @throws BusinessException
	 */
	public ExpedienteCredito modificarExpedienteCredito(ExpedienteCredito pExpedienteCredito) throws BusinessException{
		ExpedienteCredito expedienteCredito = null;
		List<EstadoCredito> listaEstadoCredito = null;
		List<CapacidadCreditoComp> listaCapacidadCredito = null;
		List<CronogramaCredito> listaCronogramaCredito = null;
		List<GarantiaCredito> listaGarantiaCredito = null;
		List<RequisitoCreditoComp> listaRequisitoCreditoComp = null;
		CronogramaCreditoId pk = new CronogramaCreditoId();
		pk.setIntCuentaPk(pExpedienteCredito.getId().getIntCuentaPk());
		pk.setIntPersEmpresaPk(pExpedienteCredito.getId().getIntPersEmpresaPk());
		pk.setIntItemExpediente(pExpedienteCredito.getId().getIntItemExpediente());
		pk.setIntItemDetExpediente(pExpedienteCredito.getId().getIntItemDetExpediente());
		CapacidadCreditoId pkCap = new CapacidadCreditoId();
		pkCap.setIntCuentaPk(pExpedienteCredito.getId().getIntCuentaPk());
		pkCap.setIntPersEmpresaPk(pExpedienteCredito.getId().getIntPersEmpresaPk());
		pkCap.setIntItemExpediente(pExpedienteCredito.getId().getIntItemExpediente());
		pkCap.setIntItemDetExpediente(pExpedienteCredito.getId().getIntItemDetExpediente());
		CapacidadDescuentoId pkCapDesc = new CapacidadDescuentoId();
		pkCapDesc.setIntCuentaPk(pExpedienteCredito.getId().getIntCuentaPk());
		pkCapDesc.setIntPersEmpresaPk(pExpedienteCredito.getId().getIntPersEmpresaPk());
		pkCapDesc.setIntItemExpediente(pExpedienteCredito.getId().getIntItemExpediente());
		pkCapDesc.setIntItemDetExpediente(pExpedienteCredito.getId().getIntItemDetExpediente());
		
		try{
			expedienteCredito = boExpedienteCredito.modificar(pExpedienteCredito);
			
			listaEstadoCredito = pExpedienteCredito.getListaEstadoCredito();
			
			if(listaEstadoCredito!=null && !listaEstadoCredito.isEmpty()){
				grabarListaDinamicaEstadoCredito(listaEstadoCredito, expedienteCredito.getId());
			}
			
			listaCapacidadCredito = pExpedienteCredito.getListaCapacidadCreditoComp();
			//Grabar Lista Capacidad Crédito
			if(listaCapacidadCredito!=null &&!listaCapacidadCredito.isEmpty()){
				// Se valida que se haya grabado anteriormente el credito
				if(expedienteCredito.getId().getIntItemExpediente() != null
					&& expedienteCredito.getId().getIntItemDetExpediente() != null){
					listaCapacidadCredito = grabarListaDinamicaSocioEstructuraParaCredito(listaCapacidadCredito, pExpedienteCredito.getSocioComp());
				}
				grabarListaDinamicaCapacidadCreditoComp(listaCapacidadCredito, expedienteCredito.getId());

			}
			
			listaGarantiaCredito = pExpedienteCredito.getListaGarantiaCredito();
			if(listaGarantiaCredito!=null && !listaGarantiaCredito.isEmpty()){
				grabarListaDinamicaGarantiaCredito(listaGarantiaCredito, expedienteCredito.getId());
			}

			listaCronogramaCredito = pExpedienteCredito.getListaCronogramaCredito();
			if(listaCronogramaCredito!=null && !listaCronogramaCredito.isEmpty()){
				eliminarCronogramaCredito(pk);
				grabarListaDinamicaCronogramaCredito(listaCronogramaCredito, expedienteCredito.getId());
			}
			
			listaRequisitoCreditoComp = pExpedienteCredito.getListaRequisitoCreditoComp();
			if(listaRequisitoCreditoComp!=null && !listaRequisitoCreditoComp.isEmpty()){
				expedienteCredito.setListaRequisitoCreditoComp(grabarListaDinamicaRequisitoCredito(listaRequisitoCreditoComp, expedienteCredito.getId()));
			}
		}catch(BusinessException e){
			log.equals("Error - BusinessException - en modificarExpedienteCredito ---> "+e);
			throw e;
		}catch(Exception e){
			log.equals("Error - Exception - en modificarExpedienteCredito ---> "+e);
			throw new BusinessException(e);
		}
		return expedienteCredito;
	}

	/**
	 * Registra el estado de Anulado para el expediuente de credito.
	 * @param pExpedienteCredito
	 * @return
	 * @throws BusinessException
	 */
	public ExpedienteCredito eliminarExpedienteCredito(ExpedienteCredito pExpedienteCredito) throws BusinessException{
		ExpedienteCredito expedienteCredito = null;
		List<EstadoCredito> listaEstadoCredito = null;
		
		try{
			listaEstadoCredito = pExpedienteCredito.getListaEstadoCredito();

			if(listaEstadoCredito!=null && !listaEstadoCredito.isEmpty()){
				grabarListaDinamicaEstadoCredito(listaEstadoCredito, pExpedienteCredito.getId());
			}

		}catch(BusinessException e){
			log.error("Error - BusinessException - en eliminarExpedienteCredito ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en eliminarExpedienteCredito ---> "+e);
			throw new BusinessException(e);
		}
		return expedienteCredito;
	}
		
	/**
	 * Graba la Autorizacion de Prestamo. Graba la lista de Autorizados y Verificacion.
	 * @param pExpedienteCredito
	 * @return
	 * @throws BusinessException
	 */
	public ExpedienteCredito grabarAutorizacionPrestamo(ExpedienteCredito pExpedienteCredito) throws BusinessException{
		List<AutorizaCredito> listaAutorizaCredito = null;
		List<AutorizaVerificacion> listaAutorizaVerificacion = null;
		//List<AutorizaVerificacion> listaAutorizaVerificacionTemp = null;
		
		try {
			
			listaAutorizaCredito = pExpedienteCredito.getListaAutorizaCredito();
			if(listaAutorizaCredito!=null && !listaAutorizaCredito.isEmpty()){
				grabarListaDinamicaAutorizaCredito(listaAutorizaCredito, pExpedienteCredito.getId());
			}
			
			listaAutorizaVerificacion = pExpedienteCredito.getListaAutorizaVerificacion();
			if(listaAutorizaVerificacion!=null && !listaAutorizaVerificacion.isEmpty()){
				grabarListaDinamicaAutorizaVerificacion(listaAutorizaVerificacion, pExpedienteCredito.getId());
			}
			
		} catch(BusinessException e){
			log.error("Error - BusinessException - en grabarAutorizacionPrestamo ---> "+e);
			throw e;
		} catch(Exception e){
			log.error("Error - Exception - en grabarAutorizacionPrestamo ---> "+e);
			throw new BusinessException(e);
		}
		return pExpedienteCredito;
	}
		
	/**
	 * Recupera los requisitos de un credito que pasa a estado observado, para que opere como uno ene el esatdo requisito.
	 * @param expedienteCredito
	 * @return
	 */
	public List<RequisitoCreditoComp> recuperarRequisitoCompExpediente(ExpedienteCredito expedienteCredito){
		List<RequisitoCreditoComp> listaRequisitoCreditoComp = null;
		List<RequisitoCredito> listaRequisitoCredito = null;
		RequisitoCreditoComp requisitoCreditoComp = null;
		ConfServDetalle detalle= null;
		Archivo archivo = null;
		TipoArchivo tipoArchivo = null;
		MyFile myFile = null;
		ConfSolicitudFacadeLocal solicitudFacade = null;
		GeneralFacadeRemote generalFacade = null;
		
		try {
			solicitudFacade = (ConfSolicitudFacadeLocal)EJBFactory.getLocal(ConfSolicitudFacadeLocal.class);
			generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			
			listaRequisitoCredito = boRequisitoCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
			if(listaRequisitoCredito!=null && !listaRequisitoCredito.isEmpty()){
				listaRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
				for(RequisitoCredito requisitoCredito : listaRequisitoCredito){
					if(requisitoCredito.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
						requisitoCreditoComp = new RequisitoCreditoComp();
						detalle = new ConfServDetalle();
						detalle.setId(new ConfServDetalleId());
						archivo = new Archivo();
						archivo.setId(new ArchivoId());
						tipoArchivo = new TipoArchivo();
						myFile = new MyFile();
						requisitoCreditoComp.setRequisitoCredito(requisitoCredito);
						detalle.getId().setIntPersEmpresaPk(requisitoCredito.getIntPersEmpresaRequisitoPk());
						detalle.getId().setIntItemSolicitud(requisitoCredito.getIntItemReqAut());
						detalle.getId().setIntItemDetalle(requisitoCredito.getIntItemReqAutDet());
						detalle = solicitudFacade.getConfServDetallePorPk(detalle.getId());
						requisitoCreditoComp.setDetalle(detalle);
						archivo.getId().setIntParaTipoCod(requisitoCredito.getIntParaTipoArchivoCod());
						archivo.getId().setIntItemArchivo(requisitoCredito.getIntParaItemArchivo());
						archivo.getId().setIntItemHistorico(requisitoCredito.getIntParaItemHistorico());
						
						archivo = generalFacade.getArchivoPorPK(archivo.getId());
						requisitoCreditoComp.setArchivoAdjunto(archivo);
						
						if(archivo !=null && archivo.getFile() != null){
							tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoCredito.getIntParaTipoArchivoCod());
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
										requisitoCreditoComp.setFileDocAdjunto(myFile);
										requisitoCreditoComp.setArchivoAdjunto(archivo);
									}
									
								}
	
							}
						}
						listaRequisitoCreditoComp.add(requisitoCreditoComp);
					}
				}
			}
		} catch (Exception e) {
			log.error("Error en recuperarRequisitoCompExpediente ---> "+e);
		}
		return listaRequisitoCreditoComp;
	}
	
	/**
	 * Cambia los estados a los autorizaCredito, Archivos Infocorp, reniec, AutorizaVerfifica y
	 * a los requiisitos de solicitud asociados al expediente.
	 * Se aplicara cuando el expediente pase a aestado Observado.
	 * @param pExpedienteCredito
	 * @return
	 * @throws BusinessException
	 */
	public void eliminarVerificaAutorizacionAdjuntosPorObservacion(ExpedienteCredito pExpedienteCredito, Integer intTipoCredito, Integer intSubTipoCredito) throws BusinessException{
		List<AutorizaCredito> listaAutorizaCredito = null;
		List<AutorizaVerificacion> listaAutorizaVerificacion = null;
		List<AutorizaCredito> listaAutorizaCreditoModif = null;
		List<AutorizaVerificacion> listaAutorizaVerificacionModif = null;
		List<RequisitoCredito> listaRequisitosSolicitud = null;
		List<RequisitoCreditoComp> listRequisitosComp = null;
		GeneralFacadeRemote generalFacade = null;
		CreditoFacadeRemote creditoFacade = null;
		SolicitudPrestamoFacadeRemote solicitudPrestamoFacade = null;
		SocioComp socioComp = null;
		List<RequisitoCreditoComp> listaNuevosRequisitosSolicitud = null;
		//Integer intTipo = 0;
		
		try {
			generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			creditoFacade = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
			solicitudPrestamoFacade = (SolicitudPrestamoFacadeRemote)EJBFactory.getRemote(SolicitudPrestamoFacadeRemote.class);

			
			// RECUPERAR TODOS LOS AUTORIZAR CREDITO
			//listaAutorizaCredito = pExpedienteCredito.getListaAutorizaCredito();
			listaAutorizaCredito = solicitudPrestamoFacade.getListaAutorizaCreditoPorPkExpediente(pExpedienteCredito.getId());

			if(listaAutorizaCredito!=null && !listaAutorizaCredito.isEmpty()){
				listaAutorizaCreditoModif = new ArrayList<AutorizaCredito>();
				for (AutorizaCredito autorizaCredito : listaAutorizaCredito) {
					autorizaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					listaAutorizaCreditoModif.add(autorizaCredito);	
				}
				grabarListaDinamicaAutorizaCredito(listaAutorizaCreditoModif, pExpedienteCredito.getId());
			}
			
			listaAutorizaVerificacion = pExpedienteCredito.getListaAutorizaVerificacion();
			if(listaAutorizaVerificacion!=null && !listaAutorizaVerificacion.isEmpty()){
				listaAutorizaVerificacionModif = new ArrayList<AutorizaVerificacion>();
				for (AutorizaVerificacion autorizaVerificacion : listaAutorizaVerificacion) {
						autorizaVerificacion.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
						autorizaVerificacion.setTsFechaRegistro((new Timestamp(new Date().getTime())));
						listaAutorizaVerificacionModif.add(autorizaVerificacion);				
					}
				
				
				grabarListaDinamicaAutorizaVerificacion(listaAutorizaVerificacionModif, pExpedienteCredito.getId());
			}
			
			listaRequisitosSolicitud = boRequisitoCredito.getListaPorPkExpedienteCredito(pExpedienteCredito.getId());
			
			if(listaRequisitosSolicitud != null && !listaRequisitosSolicitud.isEmpty()){
				for (RequisitoCredito requisitoCredito : listaRequisitosSolicitud) {
					requisitoCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					boRequisitoCredito.modificar(requisitoCredito);	
				}
				
				// recuperar los requisiitoscomp del expediente y   generalfacade.eliminarArchivo
				listRequisitosComp = recuperarRequisitoCompExpediente(pExpedienteCredito);
				if(listRequisitosComp!= null && !listRequisitosComp.isEmpty()){
					for (RequisitoCreditoComp requisitoCreditoComp : listRequisitosComp) {
						Archivo archivo = new Archivo();
						archivo = requisitoCreditoComp.getArchivoAdjunto();
						generalFacade.eliminarArchivo(archivo);
					}
					
				}
				
				socioComp = recuperarSocioCompXCuenta(pExpedienteCredito);
				if(socioComp != null){
					Credito credito = null;
					CreditoId creditoId = new CreditoId();
					
					creditoId.setIntItemCredito(pExpedienteCredito.getIntItemCredito());
					creditoId.setIntParaTipoCreditoCod(pExpedienteCredito.getIntParaTipoCreditoCod());
					creditoId.setIntPersEmpresaPk(pExpedienteCredito.getIntPersEmpresaCreditoPk());

					credito = creditoFacade.getCreditoPorIdCredito(creditoId);
					listaNuevosRequisitosSolicitud = recuperarArchivosAdjuntos(credito, socioComp, intTipoCredito, intSubTipoCredito);
					if(listaNuevosRequisitosSolicitud!= null){
						grabarListaDinamicaRequisitoCredito(listaNuevosRequisitosSolicitud, pExpedienteCredito.getId());
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
	 * Graba Dinamicamente la Lista de Estados de Credito.
	 * @param lstEstadoCredito
	 * @param pPK
	 * @return
	 * @throws BusinessException
	 */
	public List<EstadoCredito> grabarListaDinamicaEstadoCredito(List<EstadoCredito> lstEstadoCredito, ExpedienteCreditoId pPK) throws BusinessException{
		EstadoCredito estadoCredito = null;
		EstadoCreditoId pk = null;
		EstadoCredito estadoCreditoTemp = null;
		try{
			for(int i=0; i<lstEstadoCredito.size(); i++){
				estadoCredito = (EstadoCredito) lstEstadoCredito.get(i);
				if(estadoCredito.getId()== null || estadoCredito.getId().getIntItemEstado()==null){ // raro
					pk = new EstadoCreditoId();
					pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
					pk.setIntCuentaPk(pPK.getIntCuentaPk());
					pk.setIntItemExpediente(pPK.getIntItemExpediente());
					pk.setIntItemDetExpediente(pPK.getIntItemDetExpediente());
					estadoCredito.setId(pk);
					estadoCredito = boEstadoCredito.grabar(estadoCredito);
				}else{
					estadoCreditoTemp = boEstadoCredito.getPorPk(estadoCredito.getId());
					if(estadoCreditoTemp == null){
						estadoCredito = boEstadoCredito.grabar(estadoCredito);
					}else{
						estadoCredito = boEstadoCredito.modificar(estadoCredito);
					}
				}
			}
		}catch(BusinessException e){
			log.error("Error - BusinessException - en grabarListaDinamicaEstadoCredito ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en grabarListaDinamicaEstadoCredito ---> "+e);
			throw new BusinessException(e);
		}
		return lstEstadoCredito;
	}
	
	
	/**
	 * Graba Dinamicamente la Lista de Capacidades del Credito
	 * @param listCapacidadCreditoComp
	 * @param pPK
	 * @return
	 * @throws BusinessException
	 */
	public List<CapacidadCreditoComp> grabarListaDinamicaCapacidadCreditoComp(List<CapacidadCreditoComp> listCapacidadCreditoComp, ExpedienteCreditoId pPK) throws BusinessException{
		CapacidadCredito capacidadCredito = null;
		CapacidadCreditoComp capacidadCreditoComp = null;
		CapacidadCreditoId pk = null;
		CapacidadCredito capacidadCreditoTemp = null;
		List<CapacidadCreditoComp> listaOriginalCapacidad = null;
		Integer intSizeOriginal = 0;
		List<CapacidadCreditoComp> listaModificadaCapacidad = null;
		Integer intSizeModificada = 0;
		try{
			
			listaOriginalCapacidad = getListaCapcidadCreditoPorCredito(pPK);
			if(listaOriginalCapacidad == null) intSizeOriginal = 0;
			
			for(int i=0; i<listCapacidadCreditoComp.size(); i++){
				capacidadCreditoComp = (CapacidadCreditoComp) listCapacidadCreditoComp.get(i);
				capacidadCredito = new CapacidadCredito();
				if(capacidadCreditoComp.getCapacidadCredito()==null)
					capacidadCreditoComp.setCapacidadCredito(new CapacidadCredito());
				
				if(capacidadCreditoComp.getCapacidadCredito().getId()==null){
					pk = new CapacidadCreditoId();
					pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
					pk.setIntCuentaPk(pPK.getIntCuentaPk());
					pk.setIntItemExpediente(pPK.getIntItemExpediente());

					pk.setIntItemDetExpediente(pPK.getIntItemDetExpediente());
					capacidadCreditoComp.getCapacidadCredito().setId(pk);
					capacidadCredito = capacidadCreditoComp.getCapacidadCredito();
					capacidadCredito.setIntItemSocioEstructura(capacidadCreditoComp.getSocioEstructura().getId().getIntItemSocioEstructura());
					capacidadCredito.setIntPersPersonaPk(capacidadCreditoComp.getSocioEstructura().getId().getIntIdPersona());
					capacidadCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					capacidadCredito.setTsFechaRegistro(new Timestamp(new Date().getTime()));
					capacidadCredito = boCapacidadCredito.grabar(capacidadCredito);
					
					if(capacidadCredito.getListaCapacidadDscto()!=null && capacidadCredito.getListaCapacidadDscto().size()>0){
						grabarListaDinamicaCapacidadDescuento(capacidadCredito.getListaCapacidadDscto(), capacidadCredito.getId());
					}
					
				}else{
					capacidadCreditoTemp = boCapacidadCredito.getPorPk(capacidadCreditoComp.getCapacidadCredito().getId());
					if(capacidadCreditoTemp == null){
						capacidadCredito = capacidadCreditoComp.getCapacidadCredito();
						capacidadCredito.setIntItemSocioEstructura(capacidadCreditoComp.getSocioEstructura().getId().getIntItemSocioEstructura());
						capacidadCredito.setIntPersPersonaPk(capacidadCreditoComp.getSocioEstructura().getId().getIntIdPersona());
						capacidadCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						capacidadCredito.setTsFechaRegistro(new Timestamp(new Date().getTime()));
						capacidadCredito = boCapacidadCredito.grabar(capacidadCredito);
						
						if(capacidadCredito.getListaCapacidadDscto()!=null && capacidadCredito.getListaCapacidadDscto().size()>0){
							grabarListaDinamicaCapacidadDescuento(capacidadCredito.getListaCapacidadDscto(), capacidadCredito.getId());
						}
					}else{
						capacidadCredito = boCapacidadCredito.modificar(capacidadCreditoComp.getCapacidadCredito());
						if(capacidadCredito.getListaCapacidadDscto()!=null && capacidadCredito.getListaCapacidadDscto().size()>0){
							grabarListaDinamicaCapacidadDescuento(capacidadCredito.getListaCapacidadDscto(), capacidadCredito.getId());
						}
					}
				}	
			}
			
			// CGD - 05.05.2013
			// Si el tamaño de la lista ha variado, cambiar el estado a las q ya no existen
			if(listCapacidadCreditoComp != null && !listCapacidadCreditoComp.isEmpty()){
				listaModificadaCapacidad = new ArrayList<CapacidadCreditoComp>();
				listaModificadaCapacidad = listCapacidadCreditoComp;
				intSizeModificada = listaModificadaCapacidad.size();
			}

			if(intSizeOriginal.compareTo(intSizeModificada)!=0){
				cambioEstadoCapacidadCredito(listCapacidadCreditoComp, listaModificadaCapacidad);	
			}
	
		}catch(BusinessException e){
			log.error("Error - BusinessException - en grabarListaDinamicaCapacidadCreditoComp ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en grabarListaDinamicaCapacidadCreditoComp ---> "+e);
			throw new BusinessException(e);
		}
		return listCapacidadCreditoComp;
	}
		
	/**
	 * Cambia los estados de las capacidades del credito, las originales pasana  aestado 3 y se graban las modificadas.
	 * @param listaOriginal
	 * @param listaModificada
	 */
	public void cambioEstadoCapacidadCredito(List<CapacidadCreditoComp> listaOriginal, List<CapacidadCreditoComp> listaModificada){

		try {
			if(listaOriginal != null && !listaOriginal.isEmpty()){
				Boolean blnExiste = Boolean.TRUE;
				
				for (CapacidadCreditoComp original : listaOriginal) {
					for (CapacidadCreditoComp modificada : listaModificada) {
						if(original.getCapacidadCredito().getId().equals(modificada.getCapacidadCredito().getId())){
							blnExiste = Boolean.FALSE;
						}	
					}
					
					if(blnExiste){
						List<CapacidadDescuento> listaCapacidadDescuento = null;
						
						listaCapacidadDescuento = boCapacidadDescuento.getListaPorCapacidadCreditoPk(original.getCapacidadCredito().getId());
						if(listaCapacidadDescuento != null && !listaCapacidadDescuento.isEmpty()){
							for (CapacidadDescuento capacidadDescuento : listaCapacidadDescuento) {
								capacidadDescuento.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
								boCapacidadDescuento.modificar(capacidadDescuento);
							}
						}
						
						original.getCapacidadCredito().setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
						boCapacidadCredito.modificar(original.getCapacidadCredito());
					}		
				}	
			}

			
		} catch (Exception e) {
			log.error("Error en cambioEstadoCapacidadCredito ---> "+e);
		}
	}
	
	/**
	 * Recupera las capacidades del credito
	 * @param ExpedienteCreditoId
	 * @return
	 */
	private List<CapacidadCreditoComp> getListaCapcidadCreditoPorCredito(ExpedienteCreditoId ExpedienteCreditoId){
		List<CapacidadCredito> listaCapacidadCredito = null;
		List<CapacidadCreditoComp> listaCapacidadCreditoComp = null;
		CapacidadCreditoComp capacidadCreditoComp = null;
		SocioEstructura socioEstructura = null;
		List<CapacidadDescuento> listaCapacidadDscto = null;
		SocioFacadeRemote socioFacade = null;
		
		try {
			listaCapacidadCredito = boCapacidadCredito.getListaPorPkExpedienteCredito(ExpedienteCreditoId);
			socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			
			if(listaCapacidadCredito!=null && !listaCapacidadCredito.isEmpty()){
				listaCapacidadCreditoComp = new ArrayList<CapacidadCreditoComp>();
				for (CapacidadCredito capacidadCredito : listaCapacidadCredito) {
					capacidadCreditoComp = new CapacidadCreditoComp();
					socioEstructura = new SocioEstructura();
					socioEstructura.setId(new SocioEstructuraPK());
					listaCapacidadDscto = boCapacidadDescuento.getListaPorCapacidadCreditoPk(capacidadCredito.getId());
					if(listaCapacidadDscto != null && listaCapacidadDscto.size()>0){
						capacidadCredito.setListaCapacidadDscto(boCapacidadDescuento.getListaPorCapacidadCreditoPk(capacidadCredito.getId()));
					}
					
					socioEstructura.getId().setIntIdEmpresa(capacidadCredito.getId().getIntPersEmpresaPk());
					socioEstructura.getId().setIntIdPersona(capacidadCredito.getIntPersPersonaPk());
					socioEstructura.getId().setIntItemSocioEstructura(capacidadCredito.getIntItemSocioEstructura());
					socioEstructura = socioFacade.getSocioEstructuraPorPk(socioEstructura.getId());
					capacidadCreditoComp.setCapacidadCredito(capacidadCredito);
					capacidadCreditoComp.setSocioEstructura(socioEstructura);
					listaCapacidadCreditoComp.add(capacidadCreditoComp);
				}
			}
			
		} catch (BusinessException e) {
			log.error("Error BusinessException en getListaCapcidadCreditoPorCredito ---> "+e);
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			log.error("Error EJBFactoryException en getListaCapcidadCreditoPorCredito ---> "+e);
			e.printStackTrace();
		}
		
		return listaCapacidadCreditoComp;
	}
		
	/**
	 * 
	 * @param listCapacidadCreditoComp
	 * @param pPK
	 * @return
	 * @throws BusinessException
	 */
	public List<CapacidadCreditoComp> grabarListaDinamicaSocioEstructuraParaCredito(List<CapacidadCreditoComp> listCapacidadCreditoComp, SocioComp socioComp) throws BusinessException{

		SocioEstructura socioEstructuraTemp = null;
		SocioEstructura socioEstructura = null;
		SocioEstructuraPK pk = null;
		SocioFacadeRemote socioFacade = null;
		Usuario usuario;
		List<CapacidadCreditoComp> listaCapacidadFinal = new ArrayList<CapacidadCreditoComp>();
		
		try{
			usuario = (Usuario) getRequest().getSession().getAttribute("usuario");
			socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			
			for (CapacidadCreditoComp capacidadCreditoComp : listCapacidadCreditoComp) {
				//blnCambia = Boolean.FALSE;
				socioEstructura = new SocioEstructura();
				socioEstructura = capacidadCreditoComp.getSocioEstructura();

					if(capacidadCreditoComp.getSocioEstructura().getId() == null){
						pk = new SocioEstructuraPK();
						pk.setIntIdEmpresa(socioComp.getSocio().getId().getIntIdEmpresa());
						pk.setIntIdPersona(socioComp.getSocio().getId().getIntIdPersona());

						capacidadCreditoComp.getSocioEstructura().setId(pk);
						socioEstructura = capacidadCreditoComp.getSocioEstructura();
						socioEstructura.setIntEmpresaUsuario(usuario.getSucursal().getId().getIntIdSucursal());
						socioEstructura.setDtFechaRegistro(new Timestamp(new Date().getTime()));
						socioEstructura.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						socioEstructura.setIntEmpresaUsuario(usuario.getIntPersPersonaPk());

						socioEstructura = socioFacade.grabarSocioEstructura(socioEstructura);
						capacidadCreditoComp.setSocioEstructura(socioEstructura);
						listaCapacidadFinal.add(capacidadCreditoComp);
						
					}else{
						socioEstructuraTemp = socioFacade.getSocioEstructuraPorPk(socioEstructura.getId());
						if(socioEstructuraTemp == null){
							socioEstructura = capacidadCreditoComp.getSocioEstructura();
							socioEstructura = capacidadCreditoComp.getSocioEstructura();
							socioEstructura.setIntEmpresaUsuario(usuario.getSucursal().getId().getIntIdSucursal());
							socioEstructura.setDtFechaRegistro(new Timestamp(new Date().getTime()));
							socioEstructura.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
							socioEstructura.setIntEmpresaUsuario(usuario.getIntPersPersonaPk());
							
							socioEstructura = socioFacade.grabarSocioEstructura(socioEstructura);
							capacidadCreditoComp.setSocioEstructura(socioEstructura);
							listaCapacidadFinal.add(capacidadCreditoComp);

						}else{
							
							//blnCambia = verificarSiCambiaEstado(socioComp, socioEstructuraTemp);
							//if(blnCambia) capacidadCreditoComp.getSocioEstructura().setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
							socioEstructuraTemp = socioFacade.modificarSocioEstructura(capacidadCreditoComp.getSocioEstructura());
							capacidadCreditoComp.setSocioEstructura(socioEstructura);
							listaCapacidadFinal.add(capacidadCreditoComp);
						}
					}
			}
			
			// Se cambia el estado d
		}catch(BusinessException e){
			log.error("Error - BusinessException - en grabarListaDinamicaSocioEstructuraParaCredito ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en grabarListaDinamicaSocioEstructuraParaCredito ---> "+e);
			throw new BusinessException(e);
		}
		return listaCapacidadFinal;
	}
	
	/**
	 * Verifica si un socio estructura se debe eliminar(Cambiod e estado).
	 * @param socioComp
	 * @param socioEstructura
	 * @return
	 */
	public boolean verificarSiCambiaEstadoSocioEstructura( SocioComp socioComp, SocioEstructura socioEstructura){
		List<SocioEstructura> listaSocioEStructura = null;
		Boolean blnCambiaEstado = Boolean.TRUE;
		try {
			if(socioComp.getSocio().getListSocioEstructura() != null && !socioComp.getSocio().getListSocioEstructura().isEmpty()
				&& socioEstructura != null ){
				listaSocioEStructura = new ArrayList<SocioEstructura>();
				listaSocioEStructura = socioComp.getSocio().getListSocioEstructura();
				
				
				for (SocioEstructura socioEstructDefecto : listaSocioEStructura) {
				
					if(socioEstructDefecto.getIntCodigo().compareTo(socioEstructura.getIntCodigo())==0
						&& socioEstructDefecto.getIntEmpresaSucAdministra().compareTo(socioEstructura.getIntEmpresaSucAdministra())==0
						&& socioEstructDefecto.getIntModalidad().compareTo(socioEstructura.getIntModalidad())==0
						&& socioEstructDefecto.getIntNivel().compareTo(socioEstructura.getIntNivel())==0
						&& socioEstructDefecto.getIntPersonaUsuario().compareTo(socioEstructura.getIntPersonaUsuario())==0
						&& socioEstructDefecto.getIntTipoSocio().compareTo(socioEstructura.getIntTipoSocio())==0){
						
						blnCambiaEstado = Boolean.FALSE;
						break;	
					}					
				}			
			}			
		} catch (Exception e) {
			log.error("Error en verificarSiExisteEstructura");
		}		
		return blnCambiaEstado;		
	}
	
	/**
	 * Graba Dinamicamente la Lista de CapacidadDescuento del Credito
	 * @param lstCapacidadDescuento
	 * @param pPK
	 * @return
	 * @throws BusinessException
	 */
	public List<CapacidadDescuento> grabarListaDinamicaCapacidadDescuento(List<CapacidadDescuento> lstCapacidadDescuento, CapacidadCreditoId pPK) throws BusinessException{
		CapacidadDescuento capacidadDescuento = null;
		CapacidadDescuentoId pk = null;
		CapacidadDescuento capacidadDescuentoTemp = null;
		try{
			for(int i=0; i<lstCapacidadDescuento.size(); i++){
				capacidadDescuento = (CapacidadDescuento) lstCapacidadDescuento.get(i);
				if(capacidadDescuento.getId().getIntItemCapacidadDescuento()==null){
					pk = new CapacidadDescuentoId();
					pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
					pk.setIntCuentaPk(pPK.getIntCuentaPk());
					pk.setIntItemExpediente(pPK.getIntItemExpediente());
					pk.setIntItemDetExpediente(pPK.getIntItemDetExpediente());
					pk.setIntItemCapacidad(pPK.getIntItemCapacidad());
					capacidadDescuento.setId(pk);
					capacidadDescuento = boCapacidadDescuento.grabar(capacidadDescuento);
				}else{
					capacidadDescuentoTemp = boCapacidadDescuento.getPorPk(capacidadDescuento.getId());
					if(capacidadDescuentoTemp == null){
						capacidadDescuento = boCapacidadDescuento.grabar(capacidadDescuento);
					}else{
						capacidadDescuento = boCapacidadDescuento.modificar(capacidadDescuento);
					}
				}
			}
		}catch(BusinessException e){
			log.error("Error - BusinessException - en grabarListaDinamicaCapacidadDescuento ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en grabarListaDinamicaCapacidadDescuento ---> "+e);
			throw new BusinessException(e);
		}
		return lstCapacidadDescuento;
	}
		
	/**
	 * Graba Dinamicamente la Lista de CRonogramas del crdito.
	 * @param lstCronogramaCredito
	 * @param pPK
	 * @return
	 * @throws BusinessException
	 */
	public List<CronogramaCredito> grabarListaDinamicaCronogramaCredito(List<CronogramaCredito> lstCronogramaCredito, ExpedienteCreditoId pPK) throws BusinessException{
		CronogramaCredito cronogramaCredito = null;
		CronogramaCreditoId pk = null;
		//CronogramaCredito cronogramaCreditoTemp = null;
		try{
			for(int i=0; i<lstCronogramaCredito.size(); i++){
				cronogramaCredito = (CronogramaCredito) lstCronogramaCredito.get(i);
				
				//cronogramaCredito = boCronogramaCredito.grabar(cronogramaCredito);
				if(cronogramaCredito.getId().getIntItemCronograma()== null ){
					pk = new CronogramaCreditoId();
					pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
					pk.setIntCuentaPk(pPK.getIntCuentaPk());
					pk.setIntItemExpediente(pPK.getIntItemExpediente());
					pk.setIntItemDetExpediente(pPK.getIntItemDetExpediente());
					cronogramaCredito.setId(pk);
					cronogramaCredito = boCronogramaCredito.grabar(cronogramaCredito);
				}/*else{
					cronogramaCreditoTemp = boCronogramaCredito.getPorPk(cronogramaCredito.getId());
					if(cronogramaCreditoTemp == null){
						cronogramaCredito = boCronogramaCredito.grabar(cronogramaCredito);
					}else{
						cronogramaCredito = boCronogramaCredito.modificar(cronogramaCredito);
					}
				}*/
			}
		}catch(BusinessException e){
			log.error("Error - BusinessException - en grabarListaDinamicaCronogramaCredito ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en grabarListaDinamicaCronogramaCredito ---> "+e);
			throw new BusinessException(e);
		}
		return lstCronogramaCredito;
	}
		
	/**
	 * Graba Dinamicamente la Lista de GarantiaCredito.
	 * @param lstGarantiaCredito
	 * @param pPK
	 * @return
	 * @throws BusinessException
	 */
	public List<GarantiaCredito> grabarListaDinamicaGarantiaCredito(List<GarantiaCredito> lstGarantiaCredito, ExpedienteCreditoId pPK) throws BusinessException{
		GarantiaCredito garantiaCredito = null;
		GarantiaCreditoId pk = null;
		GarantiaCredito garantiaCreditoTemp = null;
		List<GarantiaCredito> lstGarantiaCreditoAnterior = null;
		Usuario usuario;
		try{
			usuario = (Usuario) getRequest().getSession().getAttribute("usuario");
			lstGarantiaCreditoAnterior = boGarantiaCredito.getListaPorPkExpedienteCredito(pPK);
			
			if (lstGarantiaCreditoAnterior!=null && !lstGarantiaCreditoAnterior.isEmpty()) {
//				if (lstGarantiaCredito!=null && !lstGarantiaCredito.isEmpty()) {
					for (GarantiaCredito lstGarantiaCredAnt : lstGarantiaCreditoAnterior) {
						Boolean blnExiste = false;
						for (GarantiaCredito lstGarantiaCredAct : lstGarantiaCredito) {								
							if (lstGarantiaCredAct.getIntPersPersonaGarantePk().equals(lstGarantiaCredAnt.getIntPersPersonaGarantePk())) {
								blnExiste = true;
								break;
							}							
						}
						if (!blnExiste) {
							lstGarantiaCredAnt.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO);
							lstGarantiaCredAnt.setIntPersPersonaEliminaPk(usuario.getIntPersPersonaPk());
							lstGarantiaCredAnt.setTsFechaEliminacion((new Timestamp(new Date().getTime())));
							boGarantiaCredito.modificar(lstGarantiaCredAnt);
						}
					}					
//				}
			}		
			
			for(int i=0; i<lstGarantiaCredito.size(); i++){
				garantiaCredito = (GarantiaCredito) lstGarantiaCredito.get(i);
				//if(garantiaCredito.getId()==null){
				if(garantiaCredito.getId()==null || garantiaCredito.getId().getIntItemGarantia()==null){	
					pk = new GarantiaCreditoId();
					pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
					pk.setIntCuentaPk(pPK.getIntCuentaPk());
					pk.setIntItemExpediente(pPK.getIntItemExpediente());
					pk.setIntItemDetExpediente(pPK.getIntItemDetExpediente());
					garantiaCredito.setId(pk);
					garantiaCredito = boGarantiaCredito.grabar(garantiaCredito);
				}else{
					garantiaCreditoTemp = boGarantiaCredito.getPorPk(garantiaCredito.getId());
					if(garantiaCreditoTemp == null){
						garantiaCredito = boGarantiaCredito.grabar(garantiaCredito);
					}else{
						garantiaCredito = boGarantiaCredito.modificar(garantiaCredito);
					}
				}
			}
		}catch(BusinessException e){
			log.error("Error - BusinessException - en grabarListaDinamicaGarantiaCredito ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en grabarListaDinamicaGarantiaCredito ---> "+e);
			throw new BusinessException(e);
		}
		return lstGarantiaCredito;
	}
	
	/**
	 * Graba Dinamicamente la Lista de RequisitoCreditoComp de Refinanciamiento.
	 * @param listaRequisitoCreditoComp
	 * @param pPK
	 * @return
	 * @throws BusinessException
	 */
	public List<RequisitoCreditoComp> grabarListaDinamicaRequisitoRefinan(List<RequisitoCreditoComp> listaRequisitoCreditoComp, ExpedienteCreditoId pPK) throws BusinessException{
		RequisitoCredito requisitoCredito = null;
		RequisitoCreditoId pk = null;
		RequisitoCredito requisitoCreditoTemp = null;
		//RequisitoCreditoId pkTemp = null;
		Archivo archivo = null;
		try{
			GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			for(RequisitoCreditoComp requisitoCreditoComp : listaRequisitoCreditoComp){
				if(requisitoCreditoComp.getRequisitoCredito()==null || requisitoCreditoComp.getRequisitoCredito().getId()==null ){
					requisitoCredito = new RequisitoCredito();
					pk = new RequisitoCreditoId();
					pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
					pk.setIntCuentaPk(pPK.getIntCuentaPk());
					pk.setIntItemExpediente(pPK.getIntItemExpediente());
					pk.setIntItemDetExpediente(pPK.getIntItemDetExpediente());
					requisitoCredito.setId(pk);
					
					//if(requisitoCreditoComp.getArchivoAdjunto()!=null){
					archivo = new Archivo();
					archivo.setId(new ArchivoId());
					if(requisitoCreditoComp.getArchivoAdjunto()==null){
						requisitoCreditoComp.setArchivoAdjunto(new Archivo());
						requisitoCreditoComp.getArchivoAdjunto().setId(new ArchivoId());
					}
					archivo.getId().setIntParaTipoCod(requisitoCreditoComp.getArchivoAdjunto().getId().getIntParaTipoCod());
					archivo.setStrNombrearchivo(requisitoCreditoComp.getArchivoAdjunto().getStrNombrearchivo());
					archivo.setIntParaEstadoCod(new Integer(Constante.PARAM_T_ESTADOUNIVERSAL));
					archivo.setTsFechaRegistro(new Timestamp(new Date().getTime()));
					if(archivo.getId().getIntParaTipoCod()!=null){
						archivo = generalFacade.grabarArchivo(archivo);
					}else archivo = null;
					
					if(archivo==null){	
						archivo = new Archivo();
						archivo.setId(new ArchivoId());
					}
					
					requisitoCredito.setIntPersEmpresaRequisitoPk(requisitoCreditoComp.getDetalle().getId().getIntPersEmpresaPk());
					requisitoCredito.setIntItemReqAut(requisitoCreditoComp.getDetalle().getId().getIntItemSolicitud());
					requisitoCredito.setIntItemReqAutDet(requisitoCreditoComp.getDetalle().getId().getIntItemDetalle());
					requisitoCredito.setIntParaTipoArchivoCod(requisitoCreditoComp.getArchivoAdjunto().getId().getIntParaTipoCod());
					requisitoCredito.setIntParaItemArchivo(archivo.getId().getIntItemArchivo());
					requisitoCredito.setIntParaItemHistorico(archivo.getId().getIntItemHistorico());
					requisitoCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					requisitoCredito.setTsFechaRequisito(new Timestamp(new Date().getTime()));
					requisitoCredito = boRequisitoCredito.grabar(requisitoCredito);
					requisitoCreditoComp.setRequisitoCredito(requisitoCredito);
					//}
				}else{

					requisitoCreditoTemp = boRequisitoCredito.getPorPk(requisitoCreditoComp.getRequisitoCredito().getId());

					if(requisitoCreditoTemp == null){
						requisitoCredito = boRequisitoCredito.grabar(requisitoCreditoComp.getRequisitoCredito());
					}else{
						archivo = new Archivo();
						archivo.setId(new ArchivoId());
						requisitoCredito = new RequisitoCredito();
						pk = new RequisitoCreditoId();
						Integer intItemArchivo = null;
						Integer intParaTipoCod = null;
						
						if(requisitoCreditoComp.getArchivoAdjunto() != null){
							intItemArchivo = requisitoCreditoComp.getArchivoAdjunto().getId().getIntItemArchivo();
							intParaTipoCod = requisitoCreditoComp.getArchivoAdjunto().getId().getIntParaTipoCod();
							
							if(intItemArchivo != null && intParaTipoCod != null)
								archivo = generalFacade.getListaArchivoDeVersionFinalPorTipoYItem(intParaTipoCod, intItemArchivo);
						
							if(archivo != null || requisitoCreditoTemp.getIntParaItemArchivo() != null 
									|| requisitoCreditoTemp.getIntParaItemHistorico() != null
									|| requisitoCreditoTemp.getIntParaTipoArchivoCod() != null){
								
								requisitoCredito.setId(requisitoCreditoComp.getRequisitoCredito().getId());
								requisitoCredito.setIntPersEmpresaRequisitoPk(requisitoCreditoComp.getDetalle().getId().getIntPersEmpresaPk());
								requisitoCredito.setIntItemReqAut(requisitoCreditoComp.getDetalle().getId().getIntItemSolicitud());
								requisitoCredito.setIntItemReqAutDet(requisitoCreditoComp.getDetalle().getId().getIntItemDetalle());
								requisitoCredito.setIntParaTipoArchivoCod(requisitoCreditoComp.getArchivoAdjunto().getId().getIntParaTipoCod());
								requisitoCredito.setIntParaItemArchivo(archivo.getId().getIntItemArchivo());
								requisitoCredito.setIntParaItemHistorico(archivo.getId().getIntItemHistorico());
								requisitoCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
								requisitoCredito.setTsFechaRequisito(new Timestamp(new Date().getTime()));
								requisitoCreditoComp.setRequisitoCredito(requisitoCredito);
								
								requisitoCredito = boRequisitoCredito.modificar(requisitoCreditoComp.getRequisitoCredito());
								
							}else{
								
								requisitoCredito = requisitoCreditoComp.getRequisitoCredito();
							}	
						}	
					} 
				}
			}
		}catch(BusinessException e){
			log.error("Error - BusinessException - en grabarListaDinamicaRequisitoRefinan ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en grabarListaDinamicaRequisitoRefinan ---> "+e);
			throw new BusinessException(e);
		}
		return listaRequisitoCreditoComp;
	}

	/**
	 * Graba Dinamicamente la Lista de RequisitoCreditoComp de Credito.
	 * @param listaRequisitoCreditoComp
	 * @param pPK
	 * @return
	 * @throws BusinessException
	 */
	public List<RequisitoCreditoComp> grabarListaDinamicaRequisitoCredito(List<RequisitoCreditoComp> listaRequisitoCreditoComp, ExpedienteCreditoId pPK) throws BusinessException{
		RequisitoCredito requisitoCredito = null;
		RequisitoCreditoId pk = null;
		RequisitoCredito requisitoCreditoTemp = null;
		Archivo archivo = null;
		try{
			GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			for(RequisitoCreditoComp requisitoCreditoComp : listaRequisitoCreditoComp){
				if(requisitoCreditoComp.getRequisitoCredito()==null || requisitoCreditoComp.getRequisitoCredito().getId()==null ){
					requisitoCredito = new RequisitoCredito();
					pk = new RequisitoCreditoId();
					pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
					pk.setIntCuentaPk(pPK.getIntCuentaPk());
					pk.setIntItemExpediente(pPK.getIntItemExpediente());
					pk.setIntItemDetExpediente(pPK.getIntItemDetExpediente());
					requisitoCredito.setId(pk);
					
					//if(requisitoCreditoComp.getArchivoAdjunto()!=null){
					archivo = new Archivo();
					archivo.setId(new ArchivoId());
					if(requisitoCreditoComp.getArchivoAdjunto()==null){
						requisitoCreditoComp.setArchivoAdjunto(new Archivo());
						requisitoCreditoComp.getArchivoAdjunto().setId(new ArchivoId());
					}
					archivo.getId().setIntParaTipoCod(requisitoCreditoComp.getArchivoAdjunto().getId().getIntParaTipoCod());
					archivo.setStrNombrearchivo(requisitoCreditoComp.getArchivoAdjunto().getStrNombrearchivo());
					archivo.setIntParaEstadoCod(new Integer(Constante.PARAM_T_ESTADOUNIVERSAL));
					archivo.setTsFechaRegistro(new Timestamp(new Date().getTime()));
					if(archivo.getId().getIntParaTipoCod()!=null){
						archivo = generalFacade.grabarArchivo(archivo);
					}else archivo = null;
					
					if(archivo==null){	
						archivo = new Archivo();
						archivo.setId(new ArchivoId());
					}
					
					requisitoCredito.setIntPersEmpresaRequisitoPk(requisitoCreditoComp.getDetalle().getId().getIntPersEmpresaPk());
					requisitoCredito.setIntItemReqAut(requisitoCreditoComp.getDetalle().getId().getIntItemSolicitud());
					requisitoCredito.setIntItemReqAutDet(requisitoCreditoComp.getDetalle().getId().getIntItemDetalle());
					requisitoCredito.setIntParaTipoArchivoCod(requisitoCreditoComp.getArchivoAdjunto().getId().getIntParaTipoCod());
					requisitoCredito.setIntParaItemArchivo(archivo.getId().getIntItemArchivo());
					requisitoCredito.setIntParaItemHistorico(archivo.getId().getIntItemHistorico());
					requisitoCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					requisitoCredito.setTsFechaRequisito(new Timestamp(new Date().getTime()));
					requisitoCredito = boRequisitoCredito.grabar(requisitoCredito);
					requisitoCreditoComp.setRequisitoCredito(requisitoCredito);
					//}
				}else{

					requisitoCreditoTemp = boRequisitoCredito.getPorPk(requisitoCreditoComp.getRequisitoCredito().getId());

					if(requisitoCreditoTemp == null){
						requisitoCredito = boRequisitoCredito.grabar(requisitoCreditoComp.getRequisitoCredito());
					}else{
						archivo = new Archivo();
						archivo.setId(new ArchivoId());
						requisitoCredito = new RequisitoCredito();
						pk = new RequisitoCreditoId();
						Integer intItemArchivo = null;
						Integer intParaTipoCod = null;
						
						if(requisitoCreditoComp.getArchivoAdjunto() != null){
							intItemArchivo = requisitoCreditoComp.getArchivoAdjunto().getId().getIntItemArchivo();
							intParaTipoCod = requisitoCreditoComp.getArchivoAdjunto().getId().getIntParaTipoCod();
							
							if(intItemArchivo != null && intParaTipoCod != null)
								archivo = generalFacade.getListaArchivoDeVersionFinalPorTipoYItem(intParaTipoCod, intItemArchivo);
						
							if(archivo != null || requisitoCreditoTemp.getIntParaItemArchivo() != null 
									|| requisitoCreditoTemp.getIntParaItemHistorico() != null
									|| requisitoCreditoTemp.getIntParaTipoArchivoCod() != null){
								
								requisitoCredito.setId(requisitoCreditoComp.getRequisitoCredito().getId());
								requisitoCredito.setIntPersEmpresaRequisitoPk(requisitoCreditoComp.getDetalle().getId().getIntPersEmpresaPk());
								requisitoCredito.setIntItemReqAut(requisitoCreditoComp.getDetalle().getId().getIntItemSolicitud());
								requisitoCredito.setIntItemReqAutDet(requisitoCreditoComp.getDetalle().getId().getIntItemDetalle());
								requisitoCredito.setIntParaTipoArchivoCod(requisitoCreditoComp.getArchivoAdjunto().getId().getIntParaTipoCod());
								requisitoCredito.setIntParaItemArchivo(archivo.getId().getIntItemArchivo());
								requisitoCredito.setIntParaItemHistorico(archivo.getId().getIntItemHistorico());
								requisitoCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
								requisitoCredito.setTsFechaRequisito(new Timestamp(new Date().getTime()));
								requisitoCreditoComp.setRequisitoCredito(requisitoCredito);
								
								requisitoCredito = boRequisitoCredito.modificar(requisitoCreditoComp.getRequisitoCredito());
								
							}else{
								
								requisitoCredito = requisitoCreditoComp.getRequisitoCredito();
							}	
						}	
					} 
				}
			}
		}catch(BusinessException e){
			log.error("Error - BusinessException - en grabarListaDinamicaRequisitoCredito ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en grabarListaDinamicaRequisitoCredito ---> "+e);
			throw new BusinessException(e);
		}
		return listaRequisitoCreditoComp;
	}
	
	/**
	 * Graba dinamicamente la lista de AutorizaCredito.
	 * @param lstAutorizaCredito
	 * @param pPK
	 * @return
	 * @throws BusinessException
	 */
	public List<AutorizaCredito> grabarListaDinamicaAutorizaCredito(List<AutorizaCredito> lstAutorizaCredito, ExpedienteCreditoId pPK) throws BusinessException{
	log.info("SolicitudPrestamoService.grabarListaDinamicaAutorizaCredito");	
		AutorizaCredito autorizaCredito = null;
		AutorizaCreditoId pk = null;
		AutorizaCredito autorizaCreditoTemp = null;
		try{
			for(int i=0; i<lstAutorizaCredito.size(); i++){
				autorizaCredito = (AutorizaCredito) lstAutorizaCredito.get(i);
				if(autorizaCredito.getId()==null || autorizaCredito.getId().getIntItemAutoriza()==null){
					pk = new AutorizaCreditoId();
					pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
					pk.setIntCuentaPk(pPK.getIntCuentaPk());
					pk.setIntItemExpediente(pPK.getIntItemExpediente());
					pk.setIntItemDetExpediente(pPK.getIntItemDetExpediente());
					autorizaCredito.setId(pk);
					autorizaCredito = boAutorizaCredito.grabar(autorizaCredito);
				}else{
					autorizaCreditoTemp = boAutorizaCredito.getPorPk(autorizaCredito.getId());
					if(autorizaCreditoTemp == null){
						autorizaCredito = boAutorizaCredito.grabar(autorizaCredito);
					}else{
						autorizaCredito = boAutorizaCredito.modificar(autorizaCredito);
					}
				}
			}
		}catch(BusinessException e){
			log.error("Error - BusinessException - en grabarListaDinamicaAutorizaCredito ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en grabarListaDinamicaAutorizaCredito ---> "+e);
			throw new BusinessException(e);
		}
		return lstAutorizaCredito;
	}
	
	/**
	 * Graba dinamicamente la lista de AutorizaVerificacion.
	 * @param lstAutorizaVerificacion
	 * @param pPK
	 * @return
	 * @throws BusinessException
	 */
	public List<AutorizaVerificacion> grabarListaDinamicaAutorizaVerificacion(List<AutorizaVerificacion> lstAutorizaVerificacion, ExpedienteCreditoId pPK) throws BusinessException{
		AutorizaVerificacion autorizaVerificacion = null;
		AutorizaVerificacionId pk = null;
		AutorizaVerificacion autorizaVerificacionTemp = null;
		try{
			for(int i=0; i<lstAutorizaVerificacion.size(); i++){
				autorizaVerificacion = (AutorizaVerificacion) lstAutorizaVerificacion.get(i);
				if(autorizaVerificacion.getId()==null || autorizaVerificacion.getId().getIntItemAutorizaVerifica()==null){
					pk = new AutorizaVerificacionId();
					pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
					pk.setIntCuentaPk(pPK.getIntCuentaPk());
					pk.setIntItemExpediente(pPK.getIntItemExpediente());
					pk.setIntItemDetExpediente(pPK.getIntItemDetExpediente());
					autorizaVerificacion.setId(pk);
					autorizaVerificacion = boAutorizaVerificacion.grabar(autorizaVerificacion);
				}else{
					autorizaVerificacionTemp = boAutorizaVerificacion.getPorPk(autorizaVerificacion.getId());
					if(autorizaVerificacionTemp == null){
						autorizaVerificacion = boAutorizaVerificacion.grabar(autorizaVerificacion);
					}else{
						autorizaVerificacion = boAutorizaVerificacion.modificar(autorizaVerificacion);
					}
				}
			}
		}catch(BusinessException e){
			log.error("Error - BusinessException - en grabarListaDinamicaAutorizaVerificacion ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en grabarListaDinamicaAutorizaVerificacion ---> "+e);
			throw new BusinessException(e);
		}
		return lstAutorizaVerificacion;
	}	

	/**
	 * Recupera el Ultimo etsado registrado del expediente de credito.
	 * Recupera todo, ordena y recupera el ultimo.
	 * @param expedienteCredito
	 * @return
	 * @throws BusinessException
	 */
	public EstadoCredito obtenerUltimoEstadoCredito(ExpedienteCredito expedienteCredito)throws BusinessException{
		EstadoCredito estadoCreditoUltima = new EstadoCredito();
		try{
			List<EstadoCredito> listaEstadoCredito = boEstadoCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
			
			estadoCreditoUltima.setId(new EstadoCreditoId());
			estadoCreditoUltima.getId().setIntItemEstado(0);
			for(EstadoCredito estadoCredito : listaEstadoCredito){
				if(estadoCredito.getId().getIntItemEstado().compareTo(estadoCreditoUltima.getId().getIntItemEstado())>0){
					estadoCreditoUltima = estadoCredito;
				}
			}
			expedienteCredito.setEstadoCreditoUltimo(estadoCreditoUltima);
		}catch(BusinessException e){
			log.error("Error - BusinessException - en obtenerUltimoEstadoCredito ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en obtenerUltimoEstadoCredito ---> "+e);
			throw new BusinessException(e);
		}
		return estadoCreditoUltima;
	}	
	
	/**
	 * Genera el Libro diario en base a un modelo contable para autorizacion de prestamo.
	 * @param expedienteCredito
	 * @return
	 * @throws Exception
	 */
	public LibroDiario generarLibroDiarioAutorizacion(ExpedienteCredito expedienteCredito) throws Exception{
		Usuario usuario = (Usuario) getRequest().getSession().getAttribute("usuario");
		Integer intIdEmpresa = expedienteCredito.getId().getIntPersEmpresaPk();
		Integer intIdUsuario = usuario.getIntPersPersonaPk();
		//EstadoCredito estadoCreditoSolicitudUltimo = null;
		ModeloFacadeRemote modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
		Modelo modeloExtorno = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_PROVISION_CREDITO, intIdEmpresa).get(0);
		
		ModeloDetalle modeloDetalleExtornoDebe = null;
		ModeloDetalle modeloDetalleExtornoHaber = null;
		LibroDiario libroDiario = null;
		
		SocioFacadeRemote socioFacadeRem = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
		List<CuentaIntegrante> lstCuentaIntegrante = null;
		Integer intPersonaSocio = 0;
		
		for(ModeloDetalle modeloDetalle : modeloExtorno.getListModeloDetalle()){
			if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_DEBE)){
				modeloDetalleExtornoDebe = modeloDetalle;
			}else if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_HABER)){
				modeloDetalleExtornoHaber = modeloDetalle;
			}
		}

		
		
		try {
			//int maximo = expedienteCredito.getListaEstadoCredito().size();
			//estadoCreditoSolicitudUltimo = expedienteCredito.getListaEstadoCredito().get(maximo -1);
			// CGD -11.10.2013
			// CGD - 26.12.2013
			lstCuentaIntegrante = socioFacadeRem.getListaCuentaIntegrantePorCuenta(expedienteCredito.getId().getIntPersEmpresaPk(), expedienteCredito.getId().getIntCuentaPk());
			if(lstCuentaIntegrante != null && !lstCuentaIntegrante.isEmpty()){
				for (CuentaIntegrante cuentaIntegrante : lstCuentaIntegrante) {
					if(cuentaIntegrante.getIntParaTipoIntegranteCod().compareTo(Constante.TIPOINTEGRANTE_ADMINISTRADOR)==0){
						intPersonaSocio = cuentaIntegrante.getId().getIntPersonaIntegrante();
					}
				}
			}
			//estadoCreditoSolicitudUltimo = obtenerUltimoEstadoCredito(expedienteCredito);
			//if(estadoCreditoSolicitudUltimo != null){
			//if(estadoCreditoSolicitudUltimo != null){
				libroDiario = new LibroDiario();
				libroDiario.setId(new LibroDiarioId());
				
				libroDiario.getId().setIntPersEmpresaLibro(Constante.PARAM_EMPRESASESION);
				libroDiario.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
				//libroDiario.getId().setIntContCodigoLibro();
				
				libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
				libroDiario.setStrGlosa("Provisión de  Créditos. Expediente: "+expedienteCredito.getId().getIntItemExpediente() +"-"+expedienteCredito.getId().getIntItemDetExpediente());
				
				libroDiario.setTsFechaRegistro(obtenerFechaActual());
				libroDiario.setTsFechaDocumento(obtenerFechaActual());
				libroDiario.setIntPersEmpresaUsuario(intIdEmpresa);
				libroDiario.setIntPersPersonaUsuario(intIdUsuario);
				libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				
				// Generando el detalle
				LibroDiarioDetalle libroDiarioDetalleHaber = new LibroDiarioDetalle();
				libroDiarioDetalleHaber.setId(new LibroDiarioDetalleId());
				libroDiarioDetalleHaber.getId().setIntPersEmpresaLibro(intIdEmpresa);
				libroDiarioDetalleHaber.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
				libroDiarioDetalleHaber.setIntPersEmpresaCuenta(modeloDetalleExtornoHaber.getId().getIntPersEmpresaCuenta());
				libroDiarioDetalleHaber.setIntContPeriodo(modeloDetalleExtornoHaber.getId().getIntContPeriodoCuenta());
				libroDiarioDetalleHaber.setStrContNumeroCuenta(modeloDetalleExtornoHaber.getId().getStrContNumeroCuenta());
				libroDiarioDetalleHaber.setIntPersPersona(intPersonaSocio);
				libroDiarioDetalleHaber.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
				//CGD-26.12.2013
				libroDiarioDetalleHaber.setStrComentario(modeloDetalleExtornoHaber.getPlanCuenta().getStrDescripcion());
				libroDiarioDetalleHaber.setStrSerieDocumento("");
				libroDiarioDetalleHaber.setStrNumeroDocumento(expedienteCredito.getId().getIntItemExpediente() +"-"+expedienteCredito.getId().getIntItemDetExpediente());
				libroDiarioDetalleHaber.setIntPersEmpresaSucursal(usuario.getSucursal().getId().getIntPersEmpresaPk());
				libroDiarioDetalleHaber.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
				libroDiarioDetalleHaber.setIntSudeIdSubSucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
				libroDiarioDetalleHaber.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
				
				if(libroDiarioDetalleHaber.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
					libroDiarioDetalleHaber.setBdHaberSoles(expedienteCredito.getBdMontoTotal());
					libroDiarioDetalleHaber.setBdDebeSoles(null);			
				}else{
					libroDiarioDetalleHaber.setBdHaberExtranjero(expedienteCredito.getBdMontoTotal());
					libroDiarioDetalleHaber.setBdDebeExtranjero(null);			
				}
				
				LibroDiarioDetalle libroDiarioDetalleDebe = new LibroDiarioDetalle();
				libroDiarioDetalleDebe.setId(new LibroDiarioDetalleId());
				libroDiarioDetalleDebe.getId().setIntPersEmpresaLibro(intIdEmpresa);
				libroDiarioDetalleDebe.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
				libroDiarioDetalleDebe.setIntPersEmpresaCuenta(modeloDetalleExtornoDebe.getId().getIntPersEmpresaCuenta());
				libroDiarioDetalleDebe.setIntContPeriodo(modeloDetalleExtornoDebe.getId().getIntContPeriodoCuenta());
				libroDiarioDetalleDebe.setStrContNumeroCuenta(modeloDetalleExtornoDebe.getId().getStrContNumeroCuenta());
				libroDiarioDetalleDebe.setIntPersPersona(intPersonaSocio);
				libroDiarioDetalleDebe.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
				//CGD-26.12.2013
				libroDiarioDetalleDebe.setStrComentario(modeloDetalleExtornoHaber.getPlanCuenta().getStrDescripcion());
				libroDiarioDetalleDebe.setStrSerieDocumento("");
				libroDiarioDetalleDebe.setStrNumeroDocumento(expedienteCredito.getId().getIntItemExpediente() +"-"+expedienteCredito.getId().getIntItemDetExpediente());
				libroDiarioDetalleDebe.setIntPersEmpresaSucursal(usuario.getSucursal().getId().getIntPersEmpresaPk());
				libroDiarioDetalleDebe.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
				libroDiarioDetalleDebe.setIntSudeIdSubSucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
				libroDiarioDetalleDebe.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
				
				if(libroDiarioDetalleDebe.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
					libroDiarioDetalleDebe.setBdHaberSoles(null);
					libroDiarioDetalleDebe.setBdDebeSoles(expedienteCredito.getBdMontoTotal());
				}else{
					libroDiarioDetalleDebe.setBdHaberExtranjero(null);
					libroDiarioDetalleDebe.setBdDebeExtranjero(expedienteCredito.getBdMontoTotal());			
				}
				libroDiario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
				libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleHaber);
				libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleDebe);

			//}

		} catch (Exception e) {
			log.error("Error - Exception - en generarLibroDiarioAutorizacion ---> "+e);
			libroDiario = null;
			throw e;
		}
		return libroDiario;	
			
	}
	
	
	/**
	 * Recupera el ultimo estado de un expediente en base a un estado deseado.
	 * @param expedienteCredito
	 * @param intTipoEstado
	 * @return
	 * @throws BusinessException
	 */
	private EstadoCredito obtenerUltimoEstadoCredito(ExpedienteCredito expedienteCredito, Integer intTipoEstado)throws BusinessException{
		EstadoCredito estadoCreditoUltima = new EstadoCredito();
		try{
			List<EstadoCredito> listaEstadoCredito = boEstadoCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
			
			estadoCreditoUltima.setId(new EstadoCreditoId());
			estadoCreditoUltima.getId().setIntItemEstado(0);
			for(EstadoCredito estadoCredito : listaEstadoCredito){
				if(estadoCredito.getIntParaEstadoCreditoCod().equals(intTipoEstado)
				&& estadoCredito.getId().getIntItemEstado().compareTo(estadoCreditoUltima.getId().getIntItemEstado())>0){
					estadoCreditoUltima = estadoCredito;
				}
			}
			
		}catch(BusinessException e){
			log.error("Error - BusinessException - en obtenerUltimoEstadoCredito ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en obtenerUltimoEstadoCredito ---> "+e);
			throw new BusinessException(e);
		}
		return estadoCreditoUltima;
	}
	
	
	/**
	 * 
	 * @param expedienteCredito
	 * @return
	 * @throws Exception
	 */
	public LibroDiario generarLibroDiarioAnulacion(ExpedienteCredito expedienteCredito) throws Exception{

			Usuario usuario = (Usuario) getRequest().getSession().getAttribute("usuario");
			Integer intIdEmpresa = expedienteCredito.getId().getIntPersEmpresaPk();
			Integer intIdUsuario = usuario.getIntPersPersonaPk();
			
			EstadoCredito estadoCreditoSolicitudUltimo = obtenerUltimoEstadoCredito(expedienteCredito, Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO);
			LibroDiarioId libroDiarioId = new LibroDiarioId();
			libroDiarioId.setIntPersEmpresaLibro(estadoCreditoSolicitudUltimo.getIntPersEmpresaLibro());
			libroDiarioId.setIntContPeriodoLibro(estadoCreditoSolicitudUltimo.getIntPeriodoLibro());
			libroDiarioId.setIntContCodigoLibro(estadoCreditoSolicitudUltimo.getIntCodigoLibro());
			
			LibroDiario libroDiario = null;
			
			LibroDiarioFacadeRemote libroDiarioFacade = null;
			LibroDiario libroDiarioSolicitud = null;
			
			try {
				libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
				libroDiarioSolicitud = libroDiarioFacade.getLibroDiarioPorPk(libroDiarioId);
				
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
				Modelo modeloExtorno = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_PROVISION_CREDITO, intIdEmpresa).get(0);
				
				ModeloDetalle modeloDetalleExtornoDebe = null;
				ModeloDetalle modeloDetalleExtornoHaber = null;
				
				for(ModeloDetalle modeloDetalle : modeloExtorno.getListModeloDetalle()){
					if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_DEBE)){
						modeloDetalleExtornoDebe = modeloDetalle;
					}else if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_HABER)){
						modeloDetalleExtornoHaber = modeloDetalle;
					}
				}
				
				libroDiario = new LibroDiario();
				libroDiario.getId().setIntPersEmpresaLibro(intIdEmpresa);
				libroDiario.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
				libroDiario.setStrGlosa(libroDiarioSolicitud.getStrGlosa());
				//libroDiario.setStrGlosa("Prueba Provision Creditos... ");
				libroDiario.setTsFechaRegistro(obtenerFechaActual());
				libroDiario.setTsFechaDocumento(obtenerFechaActual());
				libroDiario.setIntPersEmpresaUsuario(intIdEmpresa);
				libroDiario.setIntPersPersonaUsuario(intIdUsuario);
				libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
				
				LibroDiarioDetalle libroDiarioDetalleHaber = new LibroDiarioDetalle();
				libroDiarioDetalleHaber.getId().setIntPersEmpresaLibro(intIdEmpresa);
				libroDiarioDetalleHaber.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
				libroDiarioDetalleHaber.setIntPersEmpresaCuenta(modeloDetalleExtornoHaber.getId().getIntPersEmpresaCuenta());
				libroDiarioDetalleHaber.setIntContPeriodo(modeloDetalleExtornoHaber.getId().getIntContPeriodoCuenta());
				libroDiarioDetalleHaber.setStrContNumeroCuenta(modeloDetalleExtornoHaber.getId().getStrContNumeroCuenta());
				libroDiarioDetalleHaber.setIntPersPersona(expedienteCredito.getEgreso().getIntPersPersonaGirado());
				libroDiarioDetalleHaber.setIntParaDocumentoGeneral(libroDiarioDetalleSolicitudHaber.getIntParaDocumentoGeneral());
				libroDiarioDetalleHaber.setStrSerieDocumento(libroDiarioDetalleSolicitudHaber.getStrSerieDocumento());
				libroDiarioDetalleHaber.setStrNumeroDocumento(libroDiarioDetalleSolicitudHaber.getStrNumeroDocumento());
				libroDiarioDetalleHaber.setIntPersEmpresaSucursal(libroDiarioDetalleSolicitudHaber.getIntPersEmpresaSucursal());
				libroDiarioDetalleHaber.setIntSucuIdSucursal(libroDiarioDetalleSolicitudHaber.getIntSucuIdSucursal());
				libroDiarioDetalleHaber.setIntSudeIdSubSucursal(libroDiarioDetalleSolicitudHaber.getIntSudeIdSubSucursal());
				libroDiarioDetalleHaber.setIntParaMonedaDocumento(libroDiarioDetalleSolicitudHaber.getIntParaMonedaDocumento());
				//CGD-26.12.2013
				libroDiarioDetalleHaber.setStrComentario(modeloDetalleExtornoHaber.getPlanCuenta().getStrDescripcion());
				if(libroDiarioDetalleHaber.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
					libroDiarioDetalleHaber.setBdHaberSoles(expedienteCredito.getBdMontoTotal());
					libroDiarioDetalleHaber.setBdDebeSoles(null);			
				}else{
					libroDiarioDetalleHaber.setBdHaberExtranjero(expedienteCredito.getBdMontoTotal());
					libroDiarioDetalleHaber.setBdDebeExtranjero(null);			
				}
				
				LibroDiarioDetalle libroDiarioDetalleDebe = new LibroDiarioDetalle();
				libroDiarioDetalleDebe.getId().setIntPersEmpresaLibro(intIdEmpresa);
				libroDiarioDetalleDebe.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
				libroDiarioDetalleDebe.setIntPersEmpresaCuenta(modeloDetalleExtornoDebe.getId().getIntPersEmpresaCuenta());
				libroDiarioDetalleDebe.setIntContPeriodo(modeloDetalleExtornoDebe.getId().getIntContPeriodoCuenta());
				libroDiarioDetalleDebe.setStrContNumeroCuenta(modeloDetalleExtornoDebe.getId().getStrContNumeroCuenta());
				libroDiarioDetalleDebe.setIntPersPersona(expedienteCredito.getEgreso().getIntPersPersonaGirado());
				libroDiarioDetalleDebe.setIntParaDocumentoGeneral(libroDiarioDetalleSolicitudDebe.getIntParaDocumentoGeneral());
				libroDiarioDetalleDebe.setStrSerieDocumento(libroDiarioDetalleSolicitudDebe.getStrSerieDocumento());
				libroDiarioDetalleDebe.setStrNumeroDocumento(libroDiarioDetalleSolicitudDebe.getStrNumeroDocumento());
				libroDiarioDetalleDebe.setIntPersEmpresaSucursal(libroDiarioDetalleSolicitudDebe.getIntPersEmpresaSucursal());
				libroDiarioDetalleDebe.setIntSucuIdSucursal(libroDiarioDetalleSolicitudDebe.getIntSucuIdSucursal());
				libroDiarioDetalleDebe.setIntSudeIdSubSucursal(libroDiarioDetalleSolicitudDebe.getIntSudeIdSubSucursal());
				libroDiarioDetalleDebe.setIntParaMonedaDocumento(libroDiarioDetalleSolicitudDebe.getIntParaMonedaDocumento());
				//CGD-26.12.2013
				libroDiarioDetalleDebe.setStrComentario(modeloDetalleExtornoDebe.getPlanCuenta().getStrDescripcion());
				if(libroDiarioDetalleDebe.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
					libroDiarioDetalleDebe.setBdHaberSoles(null);
					libroDiarioDetalleDebe.setBdDebeSoles(expedienteCredito.getBdMontoTotal());
				}else{
					libroDiarioDetalleDebe.setBdHaberExtranjero(null);
					libroDiarioDetalleDebe.setBdDebeExtranjero(expedienteCredito.getBdMontoTotal());			
				}
				
				libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleHaber);
				libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleDebe);
			} catch (Exception e) {
				log.error("Error - Exception - en generarLibroDiarioAnulacion ---> "+e);
				e.printStackTrace();
			}
			
			return libroDiario;		
		}
	
	
	/**
	 * Obtiene el periodo YYYYMM de la fecha actual.
	 * @return
	 * @throws Exception
	 */
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
	
		
	/**
	 * Obtiene la fecha actual en Timestamp
	 * @return
	 */
	public Timestamp obtenerFechaActual(){
		return new Timestamp(new Date().getTime());
	}
		
    /**
     * 
     * @param timestamp
     * @return
     */
    private static Date convertirTimestampToDate(Timestamp timestamp) {
        return new Date(timestamp.getTime());
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
	 * 
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}
	
	
	/**
	 * Graba Estado default. Para el caso de que una solicitud de credito pase directamente a estado SOLICITUD. 
	 * Registra x default un estado previo de REQUISITO.
	 * @param lstEstadoCredito
	 * @param pPK
	 * @return
	 * @throws BusinessException
	 */
	public EstadoCredito grabarEstadoRequisitoDefault(EstadoCredito estadoCreditoSolicitud, ExpedienteCreditoId pPK) throws BusinessException{
		EstadoCredito estadoCreditoReq = null;
		EstadoCreditoId pk = null;
		
		try{
			estadoCreditoReq = new EstadoCredito();
			pk = new EstadoCreditoId();
			
			pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
			pk.setIntCuentaPk(pPK.getIntCuentaPk());
			pk.setIntItemExpediente(pPK.getIntItemExpediente());
			pk.setIntItemDetExpediente(pPK.getIntItemDetExpediente());
			estadoCreditoReq.setId(pk);
			estadoCreditoReq.setIntPersEmpresaEstadoCod(estadoCreditoSolicitud.getIntPersEmpresaEstadoCod());
			estadoCreditoReq.setIntIdUsuSubSucursalPk(estadoCreditoSolicitud.getIntIdUsuSubSucursalPk());
			estadoCreditoReq.setIntIdUsuSucursalPk(estadoCreditoSolicitud.getIntIdUsuSucursalPk());
			estadoCreditoReq.setIntParaEstadoCreditoCod(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO);// requisito
			estadoCreditoReq.setIntPersUsuarioEstadoPk(estadoCreditoSolicitud.getIntPersUsuarioEstadoPk());
			estadoCreditoReq.setSubsucursal(estadoCreditoSolicitud.getSubsucursal());
			estadoCreditoReq.setSucursal(estadoCreditoSolicitud.getSucursal());
			estadoCreditoReq.setTsFechaEstado(estadoCreditoSolicitud.getTsFechaEstado());
			estadoCreditoReq = boEstadoCredito.grabar(estadoCreditoReq);

		}catch(BusinessException e){
			log.error("Error - BusinessException - en grabarEstadoRequisitoDefault ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en grabarEstadoRequisitoDefault ---> "+e);
			throw new BusinessException(e);
		}
		return estadoCreditoReq;
	}
	
	
	/**
	 * Recupera la lista de Expedientes de Credito en base a la Cuenta.
	 * @param o
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCredito> getListaExpedienteCreditoPorCuenta(Cuenta o) throws BusinessException{
		List<ExpedienteCredito> listaExpedienteCredito = null;
		
		try{
			//socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			listaExpedienteCredito = boExpedienteCredito.getListaPorCuenta(o);

		}catch(BusinessException e){
			log.error("Error - BusinessException - en getListaExpedienteCreditoPorCuenta ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en getListaExpedienteCreditoPorCuenta ---> "+e);
			throw new BusinessException(e);
		}
		return listaExpedienteCredito;
	
	}
	
	
	/**
	 * Recupera lista de GarantiaCredito de un espediente de credito. 
	 * @param expedienteCreditoId
	 * @return
	 * @throws BusinessException
	 */
	public List<GarantiaCredito> getListaGarantiaCreditoPorId (ExpedienteCreditoId expedienteCreditoId) throws BusinessException{
		List<GarantiaCredito> lista = null;
		
		try {
			lista = boGarantiaCredito.getListaPorPkExpedienteCredito(expedienteCreditoId);
		}catch(BusinessException e){
			log.error("Error - BusinessException - en getListaGarantiaCreditoPorId ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en getListaGarantiaCreditoPorId ---> "+e);
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	/**
	 * 
	 * @param pExpedienteCredito
	 * @return
	 * @throws BusinessException
	 */
	public ExpedienteCredito grabarExpedienteRefinanciamiento(ExpedienteCredito pExpedienteCredito) throws BusinessException{
		ExpedienteCredito expedienteCredito = null;
		List<EstadoCredito> listaEstadoCredito = null;
		List<CapacidadCreditoComp> listaCapacidadCredito = null;
		List<CronogramaCredito> listaCronogramaCredito = null;
		List<GarantiaCredito> listaGarantiaCredito = null;
		List<RequisitoCreditoComp> listaRequisitoCreditoComp = null;
		
		try{
			expedienteCredito = boExpedienteCredito.grabarRefinanciamiento(pExpedienteCredito);
			
			listaEstadoCredito = pExpedienteCredito.getListaEstadoCredito();
			if(listaEstadoCredito != null && !listaEstadoCredito.isEmpty()){
				if(listaEstadoCredito.get(0).getIntParaEstadoCreditoCod().intValue() == Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD.intValue()){
					grabarEstadoRequisitoDefault(listaEstadoCredito.get(0), expedienteCredito.getId());
				}
			}
			
			//Grabar Lista Estado Crédito		
			if(listaEstadoCredito!=null && !listaEstadoCredito.isEmpty()){
				grabarListaDinamicaEstadoCredito(listaEstadoCredito, expedienteCredito.getId());
			}
			
			listaCapacidadCredito = pExpedienteCredito.getListaCapacidadCreditoComp();
			//Grabar Lista Capacidad Crédito
			if(listaCapacidadCredito!=null && !listaCapacidadCredito.isEmpty()){
				grabarListaDinamicaCapacidadCreditoComp(listaCapacidadCredito, expedienteCredito.getId());
			}
			
			listaCronogramaCredito = pExpedienteCredito.getListaCronogramaCredito();
			//Graba Cronograma
			if(listaCronogramaCredito!=null && !listaCronogramaCredito.isEmpty()){
				grabarListaDinamicaCronogramaCredito(listaCronogramaCredito, expedienteCredito.getId());
			}
			
			listaGarantiaCredito = pExpedienteCredito.getListaGarantiaCredito();
			//Grabar Lista de Garantias de Credito
			if(listaGarantiaCredito!=null && !listaGarantiaCredito.isEmpty()){
				grabarListaDinamicaGarantiaCredito(listaGarantiaCredito, expedienteCredito.getId());
			}
			
			listaRequisitoCreditoComp = pExpedienteCredito.getListaRequisitoCreditoComp();
			//Grabar Lista de requisitos de Credito
			if(listaRequisitoCreditoComp!=null && !listaRequisitoCreditoComp.isEmpty()){
				expedienteCredito.setListaRequisitoCreditoComp(grabarListaDinamicaRequisitoRefinan(listaRequisitoCreditoComp, expedienteCredito.getId()));
			}
		}catch(BusinessException e){
			log.error("Error - BusinessException - en grabarExpedienteRefinanciamiento ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en grabarExpedienteRefinanciamiento ---> "+e);
			throw new BusinessException(e);
		}
		return expedienteCredito;
	}
	
	
	/**
	 * recupera la lista compoleta de los expedientos de refinanciamiento en la grilla de busqueda.
	 * PENDIENTE AGREGAR LOS PARAMETROS DE LA BUSQUEDA.
	 * @param o
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCreditoComp> getListaExpedienteRefinanciamientoCompDeBusqueda(ExpedienteCreditoComp o) throws BusinessException{
		ExpedienteCreditoComp dto = null;
		List<ExpedienteCreditoComp> lista = null;
		List<ExpedienteCredito> listaExpedienteCredito = null;
		EstadoCredito estadoCredito;
		List<EstadoCredito> listaEstadoCredito = null;
		List<CapacidadCredito> listaCapacidadCredito = null;
		SocioComp socioComp = null;
		Persona persona = null;
		Integer intIdPersona = null;
		PersonaFacadeRemote personaFacade = null;
		SocioFacadeRemote socioFacade = null;
		List<CronogramaCredito> listaCronograma = null;
		//Expediente expedientePorRefinanciar = null;
		//Integer intDetExp = null;
		CreditoFacadeRemote creditoFacade = null;
		
		try{
			socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			creditoFacade = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
			
			listaExpedienteCredito = boExpedienteCredito.getListaBusquedaPorExpRefComp();
			
			if(listaExpedienteCredito != null && listaExpedienteCredito.size()>0){
				lista = new ArrayList<ExpedienteCreditoComp>();
				
				for(ExpedienteCredito expedienteCredito : listaExpedienteCredito){
					Credito credito = null;
					CreditoId creditoId = new CreditoId();

					creditoId.setIntItemCredito(expedienteCredito.getIntItemCredito());
					creditoId.setIntParaTipoCreditoCod(expedienteCredito.getIntParaTipoCreditoCod());
					creditoId.setIntPersEmpresaPk(expedienteCredito.getIntPersEmpresaCreditoPk());

					credito = creditoFacade.getCreditoPorIdCreditoDirecto(creditoId);
					
					dto = new ExpedienteCreditoComp();
					estadoCredito = boEstadoCredito.getMaxEstadoCreditoPorPokExpedienteCredito(expedienteCredito.getId());
					
					// solo los girados se refinancian
					if(estadoCredito.getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO)!=0){
						dto.setCredito(credito);
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
						
						listaCronograma = boCronogramaCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
						
						//Ordenamos el cronograma por el iitemcronograma 
						Collections.sort(listaCronograma, new Comparator<CronogramaCredito>(){
							public int compare(CronogramaCredito uno, CronogramaCredito otro) {
								return uno.getId().getIntItemCronograma().compareTo(otro.getId().getIntItemCronograma());
							}
						});
						
						if(listaCronograma != null){
							BigDecimal bdCuotaFija = BigDecimal.ZERO;
							BigDecimal bdMontoUno = BigDecimal.ZERO;
							BigDecimal bdMontoDos = BigDecimal.ZERO;
							
							for (CronogramaCredito cronogramaCredito : listaCronograma) {
								if(cronogramaCredito.getId().getIntItemCronograma().compareTo(new Integer(1))==0){
									bdMontoUno = cronogramaCredito.getBdMontoConcepto();
								}
								if(cronogramaCredito.getId().getIntItemCronograma().compareTo(new Integer(2))==0){
									bdMontoDos = cronogramaCredito.getBdMontoConcepto();
								}
							}
							if(bdMontoUno.compareTo(BigDecimal.ZERO)!=0 && bdMontoDos.compareTo(BigDecimal.ZERO)!=0){
								bdCuotaFija = bdMontoUno.add(bdMontoDos);
							}
							//bdCuotaFija = listaCronograma.get(0).getBdMontoConcepto().add(listaCronograma.get(1).getBdMontoConcepto());
							dto.setBdCuotaFija(bdCuotaFija);
						}
						dto.setExpedienteCredito(expedienteCredito);
						lista.add(dto);

					}
					
				}
				//dto.setListaExpedienteCredito(listaExpedienteCredito);
			}
		}catch(BusinessException e){
			log.error("Error - BusinessException - en getListaExpedienteRefinanciamientoCompDeBusqueda ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en getListaExpedienteRefinanciamientoCompDeBusqueda ---> "+e);
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	/**
	 * Recupera los creditos por expediente sin importar el itemDETALLEexpediente
	 * @param o
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCredito> getListaPorExpediente(ExpedienteCredito o) throws BusinessException{
		List<ExpedienteCredito> listaExpedienteCredito = null;

		try{
			listaExpedienteCredito = boExpedienteCredito.getListaPorExpediente(o);
			if(listaExpedienteCredito != null){
				for (ExpedienteCredito expedienteCredito : listaExpedienteCredito) {
					List<EstadoCredito> lstEstadoCredito = null;
					lstEstadoCredito = boEstadoCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
					if(lstEstadoCredito != null){
						expedienteCredito.setListaEstadoCredito(lstEstadoCredito);
					}	
				}
			}
			
		}catch(BusinessException e){
			log.error("Error - BusinessException - en getListaPorExpediente ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en getListaPorExpediente ---> "+e);
			throw new BusinessException(e);
		}
		return listaExpedienteCredito;
	
	}

	
	/**
	 * 
	 */
	public LibroDiario generarLibroDiarioAutorizacionRefinanciamiento(ExpedienteCredito expedienteCredito) throws Exception{
		Usuario usuario = (Usuario) getRequest().getSession().getAttribute("usuario");
		Integer intIdEmpresa = expedienteCredito.getId().getIntPersEmpresaPk();
		Integer intIdUsuario = usuario.getIntPersPersonaPk();
		EstadoCredito estadoCreditoSolicitudUltimo = null;
		ModeloFacadeRemote modeloFacade = null;
		Modelo modeloExtorno = null;		
		ModeloDetalle modeloDetalleExtornoDebe = null;
		ModeloDetalle modeloDetalleExtornoHaber = null;
		LibroDiario libroDiario = null;
		
		try {
			
			modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
			modeloExtorno = modeloFacade.obtenerTipoModeloActual (Constante.PARAM_T_TIPOMODELOCONTABLE_REFINANCIAMIENTO, intIdEmpresa).get(0);
			
			if(modeloExtorno != null){
				
				for(ModeloDetalle modeloDetalle : modeloExtorno.getListModeloDetalle()){
					if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_DEBE)){
						modeloDetalleExtornoDebe = modeloDetalle;
					}else if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_HABER)){
						modeloDetalleExtornoHaber = modeloDetalle;
					}
				}
		
				libroDiario = new LibroDiario();
			
				int maximo = expedienteCredito.getListaEstadoCredito().size();
				estadoCreditoSolicitudUltimo = expedienteCredito.getListaEstadoCredito().get(maximo -1);
				
				libroDiario.getId().setIntPersEmpresaLibro(estadoCreditoSolicitudUltimo.getIntPersEmpresaLibro());
				libroDiario.getId().setIntContPeriodoLibro(estadoCreditoSolicitudUltimo.getIntPeriodoLibro());
				libroDiario.getId().setIntContCodigoLibro(estadoCreditoSolicitudUltimo.getIntCodigoLibro());
				
				libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
				libroDiario.setStrGlosa("Prueba Provision Creditos.");
				
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
				libroDiarioDetalleHaber.setIntPersPersona(estadoCreditoSolicitudUltimo.getIntPersEmpresaLibro());
				libroDiarioDetalleHaber.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);

				libroDiarioDetalleHaber.setStrSerieDocumento("");
				libroDiarioDetalleHaber.setStrNumeroDocumento(expedienteCredito.getId().getIntItemExpediente() +"-"+expedienteCredito.getId().getIntItemDetExpediente());
				
				libroDiarioDetalleHaber.setIntPersEmpresaSucursal(usuario.getSucursal().getId().getIntPersEmpresaPk());
				libroDiarioDetalleHaber.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
				libroDiarioDetalleHaber.setIntSudeIdSubSucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
				libroDiarioDetalleHaber.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
				//CGD-26.12.2013
				libroDiarioDetalleHaber.setStrComentario(modeloDetalleExtornoHaber.getPlanCuenta().getStrDescripcion());
				if(libroDiarioDetalleHaber.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
					libroDiarioDetalleHaber.setBdHaberSoles(expedienteCredito.getBdMontoTotal());
					libroDiarioDetalleHaber.setBdDebeSoles(null);			
				}else{
					libroDiarioDetalleHaber.setBdHaberExtranjero(expedienteCredito.getBdMontoTotal());
					libroDiarioDetalleHaber.setBdDebeExtranjero(null);			
				}
				
				LibroDiarioDetalle libroDiarioDetalleDebe = new LibroDiarioDetalle();
				libroDiarioDetalleDebe.getId().setIntPersEmpresaLibro(intIdEmpresa);
				libroDiarioDetalleDebe.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
				libroDiarioDetalleDebe.setIntPersEmpresaCuenta(modeloDetalleExtornoDebe.getId().getIntPersEmpresaCuenta());
				libroDiarioDetalleDebe.setIntContPeriodo(modeloDetalleExtornoDebe.getId().getIntContPeriodoCuenta());
				libroDiarioDetalleDebe.setStrContNumeroCuenta(modeloDetalleExtornoDebe.getId().getStrContNumeroCuenta());
				
				libroDiarioDetalleDebe.setIntPersPersona(estadoCreditoSolicitudUltimo.getIntPersEmpresaLibro());
				
				
				libroDiarioDetalleDebe.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
				libroDiarioDetalleDebe.setStrSerieDocumento("");
				libroDiarioDetalleDebe.setStrNumeroDocumento(expedienteCredito.getId().getIntItemExpediente() +"-"+expedienteCredito.getId().getIntItemDetExpediente());
				libroDiarioDetalleDebe.setIntPersEmpresaSucursal(usuario.getSucursal().getId().getIntPersEmpresaPk());
				libroDiarioDetalleDebe.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
				libroDiarioDetalleDebe.setIntSudeIdSubSucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
				libroDiarioDetalleDebe.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
				//CGD-26.12.2013
				libroDiarioDetalleDebe.setStrComentario(modeloDetalleExtornoDebe.getPlanCuenta().getStrDescripcion());
				if(libroDiarioDetalleDebe.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
					libroDiarioDetalleDebe.setBdHaberSoles(null);
					libroDiarioDetalleDebe.setBdDebeSoles(expedienteCredito.getBdMontoTotal());
				}else{
					libroDiarioDetalleDebe.setBdHaberExtranjero(null);
					libroDiarioDetalleDebe.setBdDebeExtranjero(expedienteCredito.getBdMontoTotal());			
				}
				
				libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleHaber);
				libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleDebe);
	
			}
			
		} catch (Exception e) {
			log.error("Error - Exception - en debug generarLibroDiarioAutorizacionRefinanciamiento --> "+e);
		}
		return libroDiario;	
			
	}

	/**
	 * 
	 * @param expediente
	 * @return
	 * @throws BusinessException
	 */
	public RequisitoCredito recuperarRequisitoRefinanciamiento(ExpedienteCredito expediente) throws BusinessException{
		List<RequisitoCredito> listaRequisitos  = null;
		RequisitoCredito requisito = null;
		
		try {
			listaRequisitos = boRequisitoCredito.getListaPorPkExpedienteCredito(expediente.getId());
			
			if(listaRequisitos != null){
				for (int j = 0; j < listaRequisitos.size(); j++) {
					//Ordenamos los subtipos por int
					Collections.sort(listaRequisitos, new Comparator<RequisitoCredito>(){
						public int compare(RequisitoCredito uno, RequisitoCredito otro) {
							return uno.getId().getIntItemRequisito().compareTo(otro.getId().getIntItemRequisito());
						}
					});
				}

				for (RequisitoCredito requisitoCredito : listaRequisitos) {
					if(requisitoCredito.getIntParaTipoArchivoCod() != null 
						&&  requisitoCredito.getIntParaItemArchivo() != null 
						&& requisitoCredito.getIntParaItemHistorico() != null){
							requisito = requisitoCredito;
							break;
					}
				}
			}
			
		} catch (BusinessException e) {
			log.error("Error - BusinessException - en recuperarRequisitoRefinanciamiento --> "+e);
			e.printStackTrace();
		}
		
		return requisito;
	}
	 
	/**
	 * Genera el coronograma de movimiento. 
	 * Recuperando el cronograma desde servicio.
	 * @param expedienteId
	 * @return
	 * @throws BusinessException
	 */
	public List<Cronograma> generarCronogramaMovimiento(ExpedienteCreditoId expedienteId)throws BusinessException{
		List<CronogramaCredito> listaCronogramaCredito = null;
		 List<Cronograma> lstCronograma = null;
		 Cronograma cronograma = null;
		 CronogramaId cronogramaId = null;
		
		try {
			listaCronogramaCredito = boCronogramaCredito.getListaPorPkExpedienteCredito(expedienteId);
			if(listaCronogramaCredito != null){
				lstCronograma = new ArrayList<Cronograma>();
				
				for (CronogramaCredito cronogramaCredito : listaCronogramaCredito) {
					if(cronogramaCredito.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
						cronogramaId=new CronogramaId();
						cronograma=new Cronograma();
						
						cronogramaId.setIntCuentaPk(cronogramaCredito.getId().getIntCuentaPk());
						cronogramaId.setIntItemCronograma(cronogramaCredito.getId().getIntItemCronograma());
						cronogramaId.setIntItemExpediente(cronogramaCredito.getId().getIntItemExpediente());
						cronogramaId.setIntItemExpedienteDetalle(cronogramaCredito.getId().getIntItemDetExpediente());
						cronogramaId.setIntPersEmpresaPk(cronogramaCredito.getId().getIntPersEmpresaPk());
						cronograma.setId(cronogramaId);
						
						cronograma.setIntNumeroCuota(cronogramaCredito.getIntNroCuota());
						cronograma.setIntParaTipoCuotaCod(cronogramaCredito.getIntParaTipoCuotaCod());
						cronograma.setIntParaFormaPagoCod(cronogramaCredito.getIntParaFormaPagoCod());
						cronograma.setIntParaTipoConceptoCreditoCod(cronogramaCredito.getIntParaTipoConceptoCod());
						cronograma.setBdMontoConcepto(cronogramaCredito.getBdMontoConcepto());
						cronograma.setBdMontoCapital(cronogramaCredito.getBdMontoCapital());
						cronograma.setTsFechaVencimiento(cronogramaCredito.getTsFechaVencimiento());
						cronograma.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						cronograma.setBdSaldoDetalleCredito(cronogramaCredito.getBdMontoConcepto());
						cronograma.setIntPeriodoPlanilla(cronogramaCredito.getIntPeriodoPlanilla());

						lstCronograma.add(cronograma);
					}
				}
			}
			
			
		} catch (BusinessException e) {
			log.error("Error - BusinessException - en generarCronogramaMovimiento --> "+e);
			throw e;
		}

		return lstCronograma;
	}
			
		

	/**
	 * Genera y graba el nuevo expediente en movimeinto, asi como su cronograma y movimientoctacte
	 */

	public Expediente generarExpedienteMovimiento(ExpedienteCreditoComp expedienteCreditoCompSelected) throws Exception{
		ExpedienteCredito exp = expedienteCreditoCompSelected.getExpedienteCredito();
			
			Expediente expedienteRefinanciado = null;
			ExpedienteId expedienteRefinanciadoId = null;
			List<Cronograma> lstCronogramaMov = null;
			ConceptoFacadeRemote conceptofacade =null;
			SolicitudPrestamoFacadeRemote solicitudPrestamoFacade = null;
			
			try {
				conceptofacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
				solicitudPrestamoFacade = (SolicitudPrestamoFacadeRemote)EJBFactory.getRemote(SolicitudPrestamoFacadeRemote.class);
				if(exp != null){
					expedienteRefinanciadoId = new ExpedienteId();
					expedienteRefinanciado = new Expediente();
					expedienteRefinanciado.setListaCronograma(new ArrayList<Cronograma>());
					
					expedienteRefinanciadoId.setIntCuentaPk(exp.getId().getIntCuentaPk());
					expedienteRefinanciadoId.setIntPersEmpresaPk(exp.getId().getIntPersEmpresaPk());
					expedienteRefinanciadoId.setIntItemExpediente(exp.getId().getIntItemExpediente());
					expedienteRefinanciadoId.setIntItemExpedienteDetalle(exp.getId().getIntItemDetExpediente());
					expedienteRefinanciado.setId(expedienteRefinanciadoId);
					expedienteRefinanciado.setIntPersEmpresaCreditoPk(exp.getIntPersEmpresaCreditoPk()); //  5
					expedienteRefinanciado.setIntParaTipoCreditoCod(exp.getIntParaTipoCreditoCod()); //  6
					expedienteRefinanciado.setIntItemCredito(exp.getIntItemCredito()); //  7
					expedienteRefinanciado.setBdPorcentajeInteres(exp.getBdPorcentajeInteres()); //  8
					expedienteRefinanciado.setBdPorcentajeGravamen(exp.getBdPorcentajeGravamen()); //  9
					expedienteRefinanciado.setBdPorcentajeAporte(exp.getBdPorcentajeAporte()); //  10
					expedienteRefinanciado.setBdMontoInteresAtrazado(BigDecimal.ZERO);
					expedienteRefinanciado.setBdMontoMoraAtrazado(BigDecimal.ZERO);
					expedienteRefinanciado.setBdMontoSolicitado(exp.getBdMontoSolicitado()); //  
					expedienteRefinanciado.setBdMontoTotal(exp.getBdMontoTotal()); //  5
					expedienteRefinanciado.setIntNumeroCuota(exp.getIntNumeroCuota()); //  5
					expedienteRefinanciado.setBdSaldoCredito(exp.getBdMontoTotal()); //  5
					
					// 18.09.2013 - CGD
					
					// 18.09.2013 - CGD
					expedienteRefinanciado.setIntPersEmpresaSucAdministra(exp.getIntPersEmpresaSucAdministra());
					expedienteRefinanciado.setIntSucuIdSucursalAdministra(exp.getIntSucuIdSucursalAdministra());
					expedienteRefinanciado.setIntSudeIdSubSucursalAdministra(exp.getIntSudeIdSubSucursalAdministra());
					expedienteRefinanciado = conceptofacade.grabarExpediente(expedienteRefinanciado);
												
					if(expedienteRefinanciado.getId() != null){
						lstCronogramaMov = solicitudPrestamoFacade.generarCronogramaMovimiento(exp.getId());
						List<Cronograma> lstTemp = null;
						if(lstCronogramaMov !=null){
							lstTemp = new ArrayList<Cronograma>();
							for (Cronograma cronograma : lstCronogramaMov) {
								try {
									cronograma =conceptofacade.grabarCronograma(cronograma);
									lstTemp.add(cronograma);
								} catch (Exception e) {
									log.error("Error al grabar cronograma - generarExpedienteMovimiento() ---> "+e);
									throw new BusinessException(e);
									
								}
							}
						}
						
						expedienteRefinanciado.setListaCronograma(lstTemp);
						// colocar metodo de creacion de tres registros i/m/x
						//generarExpedienteMovimiento(expedienteCreditoCompSelected)
						
					}

				}
			} catch (Exception e) {
				log.error("Error - Exception - en generarExpedienteMovimiento --> "+e);
				e.printStackTrace();
			}
		return expedienteRefinanciado;
	}

	/**
	 * 
	 */
	public Integer getNumeroCreditosGarantizadosPorPersona(Integer intPersEmpresaPk, Integer idPersona)throws Exception{
		List<GarantiaCredito> listaGarantias = null;
		//List<EstadoCredito> listaEstados = null;
		ConceptoFacadeRemote conceptoFacade = null;
		Integer contador = 0;
		
		try {
			conceptoFacade    = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			listaGarantias=boGarantiaCredito.getListaGarantiasPorPkPersona(null, idPersona);
			if(listaGarantias != null){
				
				for (GarantiaCredito garantia : listaGarantias) {
					ExpedienteCreditoId id = new ExpedienteCreditoId();
					Integer intCuenta = 			garantia.getId().getIntCuentaPk();
					Integer intItemExpediente = 	garantia.getId().getIntItemExpediente();
					Integer intItemDetExpediente = 	garantia.getId().getIntItemDetExpediente();
					Integer intPersEmpresa = 		garantia.getId().getIntPersEmpresaPk();

					id.setIntCuentaPk(intCuenta);
					id.setIntItemDetExpediente(intItemDetExpediente);
					id.setIntItemExpediente(intItemExpediente);
					id.setIntPersEmpresaPk(intPersEmpresa);
					
					EstadoCredito estado = null;
					estado = boEstadoCredito.getMaxEstadoCreditoPorPokExpedienteCredito(id);
					
					if(estado != null){
						if(estado.getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO)==0){
							Expediente expedienteMovimiento = null;
							
							ExpedienteId idMov = new ExpedienteId();
							idMov.setIntCuentaPk(intCuenta);
							idMov.setIntItemExpediente(intItemExpediente);
							idMov.setIntItemExpedienteDetalle(intItemDetExpediente);
							idMov.setIntPersEmpresaPk(intPersEmpresa);
							
							expedienteMovimiento = conceptoFacade.getExpedientePorPK(idMov);
							if(expedienteMovimiento != null){
								if(expedienteMovimiento.getBdSaldoCredito() != null){
									if(expedienteMovimiento.getBdSaldoCredito().compareTo(BigDecimal.ZERO)!=0){
										contador ++;
									}	
								}
							}
						}
					}
				}

			}
		} catch (Exception e) {
			log.error("Error - Exception - en getNumeroCreditosGarantizadosPorPersona --> "+e);
			throw new BusinessException(e);
		}
		System.out.println(""+contador);
		return contador;
		
	}
	
		
	/**
	 * Recupera la lista de todas las solicituddes de actividades para la grilla de busqueda.
	 * PENDIENTE: AGREGAR LOS PARAMETROS DE BUSQUEDA DE LA GRILLA.
	 * @param o
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCreditoComp> getListaExpedienteActividadCompDeBusqueda(ExpedienteCreditoComp o) throws BusinessException{
		ExpedienteCreditoComp dto = null;
		List<ExpedienteCreditoComp> lista = null;
		List<ExpedienteCredito> listaExpedienteCredito = null;
		EstadoCredito estadoCredito;
		List<EstadoCredito> listaEstadoCredito = null;
		//List<CapacidadCredito> listaCapacidadCredito = null;
		SocioComp socioComp = null;
		Persona persona = null;
		Integer intIdPersona = null;
		PersonaFacadeRemote personaFacade = null;
		SocioFacadeRemote socioFacade = null;
		List<CronogramaCredito> listaCronograma = null;
		//Expediente expedientePorRefinanciar = null;
		//Integer intDetExp = null;
		CreditoFacadeRemote creditoFacade = null;
		CuentaFacadeRemote cuentaFacade = null;
		List<ExpedienteActividad> lstAct = null;
		ExpedienteActividad expAct = null;
		
		try{
			socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			creditoFacade = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
			cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			
			listaExpedienteCredito = boExpedienteCredito.getListaBusquedaPorExpActComp();
			if(listaExpedienteCredito != null && listaExpedienteCredito.size()>0){
				lista = new ArrayList<ExpedienteCreditoComp>();
				
				for(ExpedienteCredito expedienteCredito : listaExpedienteCredito){
					Credito credito = new Credito();
					CreditoId creditoId = new CreditoId();
					List<Credito> lstCreditos = null;

					creditoId.setIntItemCredito(expedienteCredito.getIntItemCredito());
					//creditoId.setIntParaTipoCreditoCod(expedienteCredito.getIntParaTipoCreditoCod());
					//creditoId.setIntPersEmpresaPk(expedienteCredito.getIntPersEmpresaCreditoPk());
					credito.setId(creditoId);
					//credito = creditoFacade.getCreditoPorIdCreditoDirecto(creditoId);
					lstCreditos = creditoFacade.getlistaCreditoPorCredito(credito);
					if(lstCreditos != null && !lstCreditos.isEmpty()){

						credito = lstCreditos.get(0);
						
						dto = new ExpedienteCreditoComp();
						estadoCredito = boEstadoCredito.getMaxEstadoCreditoPorPokExpedienteCredito(expedienteCredito.getId());
						
						// solo los girados se refinancian
						//if(estadoCredito.getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO)!=0){
							dto.setCredito(credito);
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
							
							lstAct = boExpedienteActividad.getListaPorPkExpedienteCredito(expedienteCredito.getId());
							if(lstAct !=null && !lstAct.isEmpty()){
								expAct = lstAct.get(0);
								dto.setActividad(expAct);
							}
							
							
							CuentaId cuentaId = new CuentaId();
							List<CuentaIntegrante> lstCuentaIntegrante = null;
							cuentaId.setIntCuenta(expedienteCredito.getId().getIntCuentaPk());
							cuentaId.setIntPersEmpresaPk(expedienteCredito.getId().getIntPersEmpresaPk());

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
									dto.setSocioComp(socioComp);
								}
								
							}
							
							listaCronograma = boCronogramaCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
							
							//Ordenamos el cronograma por el iitemcronograma 
							Collections.sort(listaCronograma, new Comparator<CronogramaCredito>(){
								public int compare(CronogramaCredito uno, CronogramaCredito otro) {
									return uno.getId().getIntItemCronograma().compareTo(otro.getId().getIntItemCronograma());
								}
							});
							
							if(listaCronograma != null){
								BigDecimal bdCuotaFija = BigDecimal.ZERO;
								BigDecimal bdMontoUno = BigDecimal.ZERO;
								BigDecimal bdMontoDos = BigDecimal.ZERO;
								
								for (CronogramaCredito cronogramaCredito : listaCronograma) {
									if(cronogramaCredito.getIntNroCuota().compareTo(new Integer(1))==0){
										bdMontoUno = cronogramaCredito.getBdMontoConcepto();
									}
									/*if(cronogramaCredito.getId().getIntItemCronograma().compareTo(new Integer(2))==0){
										bdMontoDos = cronogramaCredito.getBdMontoConcepto();
									}*/
								}
								//if(bdMontoUno.compareTo(BigDecimal.ZERO)!=0 && bdMontoDos.compareTo(BigDecimal.ZERO)!=0){
									bdCuotaFija = bdMontoUno.add(bdMontoDos);
								//}
								//bdCuotaFija = listaCronograma.get(0).getBdMontoConcepto().add(listaCronograma.get(1).getBdMontoConcepto());
								dto.setBdCuotaFija(bdCuotaFija);
							}
							dto.setExpedienteCredito(expedienteCredito);
							lista.add(dto);
	
						//}
					}
				}
				//dto.setListaExpedienteCredito(listaExpedienteCredito);
			}
		}catch(BusinessException e){
			log.error("Error - BusinessException - en getListaExpedienteActividadCompDeBusqueda --> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en getListaExpedienteActividadCompDeBusqueda --> "+e);
			throw new BusinessException(e);
		}
		return lista;
	}		
	
	/**
	 * 
	 * @param pId
	 * @return
	 * @throws BusinessException
	 */
	public ExpedienteCredito getExpedienteActividadCompletoPorIdExpedienteActividad(ExpedienteCreditoId pId) throws BusinessException {
		//ExpedienteCreditoComp expedienteCreditoComp = null;
		ExpedienteCredito expedienteCredito = null;
		List<EstadoCredito> listaEstadoCredito = null;
		EstadoCredito ultimoEstadoCredito= null;
		EstadoCredito primerEstadoCredito= null;
		List<RequisitoCredito> listaRequisitoCredito = null;
		List<RequisitoCreditoComp> listaRequisitoCreditoComp = null;
		RequisitoCreditoComp requisitoCreditoComp = null;
		ConfServDetalle detalle = null;
		Archivo archivo = null;
		TipoArchivo tipoArchivo = null;
		MyFile myFile = null;
		//SocioFacadeRemote socioFacade = null;
		ConfSolicitudFacadeLocal solicitudFacade = null;
		GeneralFacadeRemote generalFacade = null;
		//PersonaFacadeRemote personaFacade = null;
		List<ExpedienteActividad> listaExpedienteActividad = null;
		
		try {
			
			//socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			solicitudFacade = (ConfSolicitudFacadeLocal)EJBFactory.getLocal(ConfSolicitudFacadeLocal.class);
			generalFacade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
			//personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);

			expedienteCredito = boExpedienteCredito.getPorPk(pId);

			if(expedienteCredito!=null){
				// 1. recuperamos estados
				listaEstadoCredito = boEstadoCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
				if(listaEstadoCredito!=null && listaEstadoCredito.size()>0){
					expedienteCredito.setListaEstadoCredito(listaEstadoCredito);
					
					// cargando el uiltimo estado
					ultimoEstadoCredito = boEstadoCredito.getMaxEstadoCreditoPorPokExpedienteCredito(expedienteCredito.getId());
					if(ultimoEstadoCredito != null){
						expedienteCredito.setEstadoCreditoUltimo(ultimoEstadoCredito);
					}
					
					primerEstadoCredito = boEstadoCredito.getMinEstadoCreditoPorPkExpedienteCredito (expedienteCredito.getId());
					if(primerEstadoCredito != null){
						expedienteCredito.setEstadoCreditoPrimero(primerEstadoCredito);
					}	
				}
				
				// 2.Expdiente - Actividad
				listaExpedienteActividad = boExpedienteActividad.getListaPorPkExpedienteCredito(expedienteCredito.getId());
				if(listaExpedienteActividad!=null && listaExpedienteActividad.size()>0){
					expedienteCredito.setListaExpedienteActividad(listaExpedienteActividad);
				}

				// 4. Requiisitos
				listaRequisitoCredito = boRequisitoCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
				
				if(listaRequisitoCredito!=null && listaRequisitoCredito.size()>0){
					listaRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
					for(RequisitoCredito requisitoCredito : listaRequisitoCredito ){
						requisitoCreditoComp = new RequisitoCreditoComp();
						detalle = new ConfServDetalle();
						detalle.setId(new ConfServDetalleId());
						archivo = new Archivo();
						archivo.setId(new ArchivoId());
						tipoArchivo = new TipoArchivo();
						myFile = new MyFile();
						requisitoCreditoComp.setRequisitoCredito(requisitoCredito);
						detalle.getId().setIntPersEmpresaPk(requisitoCredito.getIntPersEmpresaRequisitoPk());
						detalle.getId().setIntItemSolicitud(requisitoCredito.getIntItemReqAut());
						detalle.getId().setIntItemDetalle(requisitoCredito.getIntItemReqAutDet());
						detalle = solicitudFacade.getConfServDetallePorPk(detalle.getId());
						
						requisitoCreditoComp.setDetalle(detalle);
						
						archivo.getId().setIntParaTipoCod(requisitoCredito.getIntParaTipoArchivoCod());
						archivo.getId().setIntItemArchivo(requisitoCredito.getIntParaItemArchivo());
						archivo.getId().setIntItemHistorico(requisitoCredito.getIntParaItemHistorico());
						archivo = generalFacade.getArchivoPorPK(archivo.getId());
						
						requisitoCreditoComp.setArchivoAdjunto(archivo);
						if(archivo !=null){
							tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoCredito.getIntParaTipoArchivoCod());
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
										requisitoCreditoComp.setFileDocAdjunto(myFile);
										requisitoCreditoComp.setArchivoAdjunto(archivo);
									}
								}	
							}
						}
						listaRequisitoCreditoComp.add(requisitoCreditoComp);
					}
					expedienteCredito.setListaRequisitoCreditoComp(listaRequisitoCreditoComp);
				}
			}
		} catch(BusinessException e){
			log.error("Error - BusinessException - getExpedienteActividadCompletoPorIdExpedienteActividad--->"+ e);
			throw e;
			
		} catch (EJBFactoryException e) {
			log.info(" Error - EJBFactoryException - getExpedienteActividadCompletoPorIdExpedienteActividad --> "+e);
		}
		return expedienteCredito;
	}	
	
	/**
	 * 
	 * @param lstExpedienteActividad
	 * @param pPK
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteActividad> grabarListaDinamicaExpedienteActividad(List<ExpedienteActividad> lstExpedienteActividad, ExpedienteCreditoId pPK) throws BusinessException{
		ExpedienteActividad expedienteActividad = null;
		ExpedienteActividadId pk = null;
		//ExpedienteActividad expedienteActividadTemp = null;
		try{
			for(int i=0; i<lstExpedienteActividad.size(); i++){
				expedienteActividad = (ExpedienteActividad) lstExpedienteActividad.get(i);
				//if(garantiaCredito.getId()==null){
				if(expedienteActividad.getId()==null){	
					pk = new ExpedienteActividadId();
					pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
					pk.setIntCuentaPk(pPK.getIntCuentaPk());
					pk.setIntItemExpediente(pPK.getIntItemExpediente());
					pk.setIntItemDetExpediente(pPK.getIntItemDetExpediente());
					expedienteActividad.setId(pk);
					expedienteActividad = boExpedienteActividad.grabar(expedienteActividad);
				}else{
					//expedienteActividadTemp = boExpedienteActividad.getListaPorExpedienteCredito(expedienteActividad.getId());
					//if(expedienteActividadTemp == null){
					//	expedienteActividad = boExpedienteActividad.grabar(expedienteActividad);
					//}else{
						expedienteActividad = boExpedienteActividad.modificar(expedienteActividad);
					//}
				}
			}
		}catch(BusinessException e){
			log.error("Error - BusinessException - grabarListaDinamicaExpedienteActividad ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - grabarListaDinamicaExpedienteActividad ---> "+e);
			throw new BusinessException(e);
		}
		return lstExpedienteActividad;
	}	
	
	/**
	 * 
	 * @param o
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCreditoComp> getListaExpedienteActividadCompPorAutorizar(ExpedienteCreditoComp o) throws BusinessException{
		ExpedienteCreditoComp dto = null;
		List<ExpedienteCreditoComp> lista = null;
		List<ExpedienteCredito> listaExpedienteCredito = null;
		EstadoCredito estadoCredito;
		List<EstadoCredito> listaEstadoCredito = null;
		SocioComp socioComp = null;
		Persona persona = null;
		Integer intIdPersona = null;
		PersonaFacadeRemote personaFacade = null;
		SocioFacadeRemote socioFacade = null;
		List<CronogramaCredito> listaCronograma = null;
		CreditoFacadeRemote creditoFacade = null;
		CuentaFacadeRemote cuentaFacade = null;
		List<ExpedienteActividad> lstAct = null;
		ExpedienteActividad expAct = null;
		
		try{
			socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			creditoFacade = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
			cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			
			listaExpedienteCredito = boExpedienteCredito.getListaBusquedaPorExpActComp();
			if(listaExpedienteCredito != null && listaExpedienteCredito.size()>0){
				lista = new ArrayList<ExpedienteCreditoComp>();
				
				for(ExpedienteCredito expedienteCredito : listaExpedienteCredito){
					Credito credito = null;
					CreditoId creditoId = new CreditoId();

					creditoId.setIntItemCredito(expedienteCredito.getIntItemCredito());
					creditoId.setIntParaTipoCreditoCod(expedienteCredito.getIntParaTipoCreditoCod());
					creditoId.setIntPersEmpresaPk(expedienteCredito.getIntPersEmpresaCreditoPk());

					credito = creditoFacade.getCreditoPorIdCreditoDirecto(creditoId);

					estadoCredito = boEstadoCredito.getMaxEstadoCreditoPorPokExpedienteCredito(expedienteCredito.getId());
					
					// Solo se deberan visualizar las actividades en esatdo SOLICITUD	
					if(estadoCredito != null){
						if(estadoCredito.getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD)==0
							|| estadoCredito.getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)==0
							|| estadoCredito.getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO)==0
							|| estadoCredito.getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_RECHAZADO)==0){
							dto = new ExpedienteCreditoComp();
								dto.setCredito(credito);
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
								
								lstAct = boExpedienteActividad.getListaPorPkExpedienteCredito(expedienteCredito.getId());
								if(lstAct !=null && !lstAct.isEmpty()){
									expAct = lstAct.get(0);
									dto.setActividad(expAct);
								}
								
								CuentaId cuentaId = new CuentaId();
								List<CuentaIntegrante> lstCuentaIntegrante = null;
								cuentaId.setIntCuenta(expedienteCredito.getId().getIntCuentaPk());
								cuentaId.setIntPersEmpresaPk(expedienteCredito.getId().getIntPersEmpresaPk());
								
								
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
										dto.setSocioComp(socioComp);
									}
									
								}
								
								listaCronograma = boCronogramaCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
								
								//Ordenamos el cronograma por el iitemcronograma 
								Collections.sort(listaCronograma, new Comparator<CronogramaCredito>(){
									public int compare(CronogramaCredito uno, CronogramaCredito otro) {
										return uno.getId().getIntItemCronograma().compareTo(otro.getId().getIntItemCronograma());
									}
								});
								
								if(listaCronograma != null){
									BigDecimal bdCuotaFija = BigDecimal.ZERO;
									BigDecimal bdMontoUno = BigDecimal.ZERO;
									BigDecimal bdMontoDos = BigDecimal.ZERO;
									
									for (CronogramaCredito cronogramaCredito : listaCronograma) {
										if(cronogramaCredito.getId().getIntItemCronograma().compareTo(new Integer(1))==0){
											bdMontoUno = cronogramaCredito.getBdMontoConcepto();
										}
										if(cronogramaCredito.getId().getIntItemCronograma().compareTo(new Integer(2))==0){
											bdMontoDos = cronogramaCredito.getBdMontoConcepto();
										}
									}
									if(bdMontoUno.compareTo(BigDecimal.ZERO)!=0 && bdMontoDos.compareTo(BigDecimal.ZERO)!=0){
										bdCuotaFija = bdMontoUno.add(bdMontoDos);
									}
									//bdCuotaFija = listaCronograma.get(0).getBdMontoConcepto().add(listaCronograma.get(1).getBdMontoConcepto());
									dto.setBdCuotaFija(bdCuotaFija);
								}
								dto.setExpedienteCredito(expedienteCredito);
								lista.add(dto);
						}						
					}					
				}
			}
		}catch(BusinessException e){
			log.error("Error - BusinessException - getListaExpedienteActividadCompPorAutorizar ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - getListaExpedienteActividadCompPorAutorizar ---> "+e);
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	/**
	 * 
	 * @param expedienteCredito
	 * @return
	 * @throws Exception
	 */
	public LibroDiario generarLibroDiarioAutorizacionActividad(ExpedienteCredito expedienteCredito) throws Exception{
		EstadoCredito estadoCreditoSolicitudUltimo = null;
		ModeloFacadeRemote modeloFacade = null;
		Modelo modeloContable = null;
		LibroDiario libroDiario = null;
		
		try {
			Usuario usuario 	 = (Usuario) getRequest().getSession().getAttribute("usuario");
			Integer intIdEmpresa = expedienteCredito.getId().getIntPersEmpresaPk();
			Integer intIdUsuario = usuario.getIntPersPersonaPk();
			
			modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
			
			modeloContable = modeloFacade.obtenerTipoModeloActual (Constante.PARAM_T_TIPOMODELOCONTABLE_ACTIVIDAD, intIdEmpresa).get(0);
			
			if(modeloContable != null){
				ModeloDetalle modeloDetalleDebe = null;
				ModeloDetalle modeloDetalleHaber = null;
				
				for(ModeloDetalle modeloDetalle : modeloContable.getListModeloDetalle()){
					if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_DEBE)){
						modeloDetalleDebe = modeloDetalle;
					}else if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_HABER)){
						modeloDetalleHaber = modeloDetalle;
					}
				}

				estadoCreditoSolicitudUltimo = obtenerUltimoEstadoCredito(expedienteCredito);
				
				if(estadoCreditoSolicitudUltimo != null){
					if(estadoCreditoSolicitudUltimo.getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)==0){
						libroDiario = new LibroDiario();
						libroDiario.setId(new LibroDiarioId());
						
						libroDiario.getId().setIntPersEmpresaLibro(estadoCreditoSolicitudUltimo.getIntPersEmpresaLibro());
						libroDiario.getId().setIntContPeriodoLibro(estadoCreditoSolicitudUltimo.getIntPeriodoLibro());
						libroDiario.getId().setIntContCodigoLibro(estadoCreditoSolicitudUltimo.getIntCodigoLibro());
						
						libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_ACTIVIDAD);
						libroDiario.setStrGlosa("Prueba Actividades.");
						
						libroDiario.setTsFechaRegistro(obtenerFechaActual());
						libroDiario.setTsFechaDocumento(obtenerFechaActual());
						libroDiario.setIntPersEmpresaUsuario(intIdEmpresa);
						libroDiario.setIntPersPersonaUsuario(intIdUsuario);
						libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						
						// Generando el detalle
						LibroDiarioDetalle libroDiarioDetalleHaber = new LibroDiarioDetalle();
						libroDiarioDetalleHaber.getId().setIntPersEmpresaLibro(intIdEmpresa);
						libroDiarioDetalleHaber.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
						libroDiarioDetalleHaber.setIntPersEmpresaCuenta(modeloDetalleHaber.getId().getIntPersEmpresaCuenta());
						libroDiarioDetalleHaber.setIntContPeriodo(modeloDetalleHaber.getId().getIntContPeriodoCuenta());
						libroDiarioDetalleHaber.setStrContNumeroCuenta(modeloDetalleHaber.getId().getStrContNumeroCuenta());
						libroDiarioDetalleHaber.setIntPersPersona(estadoCreditoSolicitudUltimo.getIntPersEmpresaLibro());
						libroDiarioDetalleHaber.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_ACTIVIDAD);

						libroDiarioDetalleHaber.setStrSerieDocumento("");
						libroDiarioDetalleHaber.setStrNumeroDocumento(expedienteCredito.getId().getIntItemExpediente() +"-"+expedienteCredito.getId().getIntItemDetExpediente());
						
						libroDiarioDetalleHaber.setIntPersEmpresaSucursal(usuario.getSucursal().getId().getIntPersEmpresaPk());
						libroDiarioDetalleHaber.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
						libroDiarioDetalleHaber.setIntSudeIdSubSucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
						libroDiarioDetalleHaber.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
						//CGD-26.12.2013
						libroDiarioDetalleHaber.setStrComentario(modeloDetalleHaber.getPlanCuenta().getStrDescripcion());
						if(libroDiarioDetalleHaber.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
							libroDiarioDetalleHaber.setBdHaberSoles(expedienteCredito.getBdMontoTotal());
							libroDiarioDetalleHaber.setBdDebeSoles(null);			
						}else{
							libroDiarioDetalleHaber.setBdHaberExtranjero(expedienteCredito.getBdMontoTotal());
							libroDiarioDetalleHaber.setBdDebeExtranjero(null);			
						}
						
						LibroDiarioDetalle libroDiarioDetalleDebe = new LibroDiarioDetalle();
						libroDiarioDetalleDebe.getId().setIntPersEmpresaLibro(intIdEmpresa);
						libroDiarioDetalleDebe.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
						libroDiarioDetalleDebe.setIntPersEmpresaCuenta(modeloDetalleDebe.getId().getIntPersEmpresaCuenta());
						libroDiarioDetalleDebe.setIntContPeriodo(modeloDetalleDebe.getId().getIntContPeriodoCuenta());
						libroDiarioDetalleDebe.setStrContNumeroCuenta(modeloDetalleDebe.getId().getStrContNumeroCuenta());
						libroDiarioDetalleDebe.setIntPersPersona(estadoCreditoSolicitudUltimo.getIntPersEmpresaLibro());
						libroDiarioDetalleDebe.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_ACTIVIDAD);
						libroDiarioDetalleDebe.setStrSerieDocumento("");
						libroDiarioDetalleDebe.setStrNumeroDocumento(expedienteCredito.getId().getIntItemExpediente() +"-"+expedienteCredito.getId().getIntItemDetExpediente());
						libroDiarioDetalleDebe.setIntPersEmpresaSucursal(usuario.getSucursal().getId().getIntPersEmpresaPk());
						libroDiarioDetalleDebe.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
						libroDiarioDetalleDebe.setIntSudeIdSubSucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
						libroDiarioDetalleDebe.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
						//CGD-26.12.2013
						libroDiarioDetalleDebe.setStrComentario(modeloDetalleDebe.getPlanCuenta().getStrDescripcion());
						if(libroDiarioDetalleDebe.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
							libroDiarioDetalleDebe.setBdHaberSoles(null);
							libroDiarioDetalleDebe.setBdDebeSoles(expedienteCredito.getBdMontoTotal());
						}else{
							libroDiarioDetalleDebe.setBdHaberExtranjero(null);
							libroDiarioDetalleDebe.setBdDebeExtranjero(expedienteCredito.getBdMontoTotal());			
						}
						
						libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleHaber);
						libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleDebe);
					}
				}	
			}
		} catch (Exception e) {
			log.error("Error - Exception - generarLibroDiarioAutorizacionActividad ---> "+e);
			throw e;
		}
		return libroDiario;				
	}
	
	/**
	 * Genera los procesos de autorizacionde  actividad.
	 * @param expedienteCredito
	 * @param usuario
	 * @return
	 * @throws BusinessException
	 */
	public LibroDiario generarProcesosAutorizacionActividad(ExpedienteCredito expedienteCredito, Usuario usuario)  throws BusinessException{
		
			BigDecimal bdValorColumna = null;
			LibroDiario diario = null;
			ModeloFacadeRemote	modeloFacade  = null;
			ConceptoFacadeRemote conceptoFacace = null;
			LibroDiarioFacadeRemote libroDiarioFacade = null;
			CreditoFacadeRemote creditoFacade = null;
			TablaFacadeRemote tablaFacade = null;
			
			List<LibroDiarioDetalle> lstDiarioDetalle = null;
			List<ModeloDetalle> listaModDet = null;
			
			Expediente expedienteMovimiento = null;
			//EstadoCredito ultimoEstado = null;
			//EstadoCredito estadoGirado = null;
			List<ExpedienteActividad> lstAct = null;
			ExpedienteActividad expAct = null;
			String strTipoDeSolicitud = "";
			List<Modelo> lstModelos = null;
			Modelo modeloContable = null;
			CreditoId creditoId = null;
			Credito beanCredito = null;
			List<Tabla> listaTipoSolicitud = null;
//			EstadoCredito estadoAprobado = null;
			Movimiento movimientoActividad = null;
			PlanCuentaFacadeRemote planCtaFacade = null;
			
			Date dtHoy = null;
			Calendar fecHoy = Calendar.getInstance();
			dtHoy = fecHoy.getTime();
			SocioComp beanSocioComp= null;
			EstructuraDetalle estructuraDetalle = null;
			
			try{
				modeloFacade  = (ModeloFacadeRemote)EJBFactory.getRemote(ModeloFacadeRemote.class);
				libroDiarioFacade= (LibroDiarioFacadeRemote)EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
				conceptoFacace = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
				creditoFacade  = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
				tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
				planCtaFacade= (PlanCuentaFacadeRemote)EJBFactory.getRemote(PlanCuentaFacadeRemote.class);
				
				
				beanSocioComp = recuperarSocioCompXCuenta(expedienteCredito);
				if(beanSocioComp.getSocio().getSocioEstructura() != null){
					estructuraDetalle = recuperarEstructuraDetalle(beanSocioComp.getSocio().getSocioEstructura());
				}
				
				//Integer intPeriodoCuenta = obtieneAnio(new Date());
				//Integer anio = obtieneAnio(new Date());
				//String  mes  = obtieneMesCadena(new Date());
				
				Integer intPeriodoCuenta = obtieneAnio(new Date());
				// POR PRUEBAS
				Integer anio =  obtieneAnio(new Date());
				String  mes  = obtieneMesCadena(new Date());
				
				lstDiarioDetalle = new ArrayList<LibroDiarioDetalle>();
				// Recuperamos el modelo a aplicar
				lstModelos = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_ACTIVIDAD, expedienteCredito.getId().getIntPersEmpresaPk());
				if(lstModelos != null && !lstModelos.isEmpty()){
					modeloContable = new Modelo();
					modeloContable = lstModelos.get(0);
				}
				
				if(modeloContable != null){
					listaModDet =  modeloFacade.getListaModeloDetallePorModeloId(modeloContable.getId());
					
					
					// recuperamos el credito a evaluar
					creditoId = new CreditoId();
					creditoId.setIntItemCredito(expedienteCredito.getIntItemCredito());
					creditoId.setIntParaTipoCreditoCod(expedienteCredito.getIntParaTipoCreditoCod());
					creditoId.setIntPersEmpresaPk(expedienteCredito.getIntPersEmpresaCreditoPk());

					beanCredito = creditoFacade.getCreditoPorIdCredito(creditoId);
					
					if(modeloContable != null &&  listaModDet!= null){
						diario = new LibroDiario();
						
						lstAct = boExpedienteActividad.getListaPorPkExpedienteCredito(expedienteCredito.getId());
						
						if(lstAct !=null && !lstAct.isEmpty()){
							expAct = lstAct.get(0);
							listaTipoSolicitud = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_SUBOPERACIONACTIVIDADES));
							
							if(listaTipoSolicitud != null && !listaTipoSolicitud.isEmpty()){
								
								for (Tabla tabla : listaTipoSolicitud) {
									if(tabla.getIntIdDetalle().compareTo(expAct.getIntParaTipoSolicitudCod())==0){
										strTipoDeSolicitud = tabla.getStrDescripcion();
										break;
									}
								}

							}

						}
						
				// 1. Cambiamos el estado del expediente(servicio) a APROBADO
						//estadoAprobado = cambioEstadoActividad(expedienteCredito, usuario);
						
						//if(estadoAprobado != null){
							diario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
							diario.setId(new LibroDiarioId());
							diario.getId().setIntContPeriodoLibro(new Integer(anio+""+mes));
							diario.getId().setIntPersEmpresaLibro(expedienteCredito.getId().getIntPersEmpresaPk());
							diario.setStrGlosa("Asiento de Autorizacion de Actividad: "+beanCredito.getStrDescripcion()+" - Tipo: "+strTipoDeSolicitud +". ");
							diario.setTsFechaRegistro(new Timestamp((new Date()).getTime())); //la fecha actual
							diario.setTsFechaDocumento(new Timestamp((new Date()).getTime()));
							diario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_ACTIVIDAD);
							diario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
							diario.setIntPersEmpresaUsuario(usuario.getEmpresa().getIntIdEmpresa());
							diario.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());

							//Modelo detalle
							for (ModeloDetalle modeloDetalle : listaModDet) {
								bdValorColumna= BigDecimal.ZERO;						
								
								boolean blnExisteTipoCredito      = Boolean.FALSE;	// Para_TipoCredito_N_Cod    140 - 3  
								boolean blnExisteItemCredito      = Boolean.FALSE;	// Csoc_ItemCredito_N		vf 35
								boolean blnExisteTipoSolicitudAct = Boolean.FALSE;	//
								
								//CGD-26.12.2013
								PlanCuenta planCta = null;
								String strObservacionPlanCta = "";							

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
									
									if(listaModDetNiv != null){
										
										// Niveles del detalle modelo
										for (ModeloDetalleNivel modeloDetalleNivel : listaModDetNiv) {
											// Es valor fijo
											if(modeloDetalleNivel.getIntParaTipoRegistro().compareTo(Constante.PARAM_T_MODELO_TIPO_REGISTRO_FIJO)==0){
												if(modeloDetalleNivel.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_ACTIVIDAD_ITEMCREDITO)){
													if(expedienteCredito.getIntItemCredito().compareTo(modeloDetalleNivel.getIntValor())==0 ){
														bdValorColumna = expedienteCredito.getBdMontoSolicitado();	
														blnExisteItemCredito = Boolean.TRUE;
													}
												}	
											}

											// Es tabla	
											else if(modeloDetalleNivel.getIntParaTipoRegistro().compareTo(Constante.PARAM_T_MODELO_TIPO_REGISTRO_TABLA)==0){
											//-- dato tablas  140 169    datoargumaneto  123456879   valor 1  tiporegistro 1 2 
												if(modeloDetalleNivel.getIntDatoTablas().compareTo(new Integer(Constante.PARAM_T_TIPOCREDITOEMPRESA))==0){
													// 140 creditos
													if(modeloDetalleNivel.getIntDatoArgumento().compareTo(expedienteCredito.getIntParaTipoCreditoCod())==0){
														bdValorColumna = expedienteCredito.getBdMontoSolicitado();
														blnExisteTipoCredito = Boolean.TRUE;
													}
												}
												
												if(modeloDetalleNivel.getIntDatoTablas().compareTo(new Integer(Constante.PARAM_T_SUBOPERACIONACTIVIDADES))==0){
													// 153  Contado / Credito
													if(modeloDetalleNivel.getIntDatoArgumento().compareTo(expAct.getIntParaTipoSolicitudCod())==0){
														bdValorColumna = expedienteCredito.getBdMontoSolicitado();
														blnExisteTipoSolicitudAct = Boolean.TRUE;
													}
												}
												
											}
										}
										
										
										// termionamos de formar el libro diario detalle
										if(blnExisteItemCredito && blnExisteTipoCredito && blnExisteTipoSolicitudAct){
											diarioDet.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
											diarioDet.setIntContPeriodo(modeloDetalle.getId().getIntContPeriodoCuenta());
											diarioDet.setIntPersEmpresaCuenta(modeloDetalle.getId().getIntPersEmpresaCuenta());
											diarioDet.setIntPersPersona(usuario.getIntPersPersonaPk());
											diarioDet.setIntPersEmpresaSucursal(Constante.PARAM_EMPRESASESION);
											diarioDet.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
											diarioDet.setIntSudeIdSubSucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
											diarioDet.setStrContNumeroCuenta(modeloDetalle.getId().getStrContNumeroCuenta());
											diarioDet.setStrSerieDocumento(expedienteCredito.getId().getIntItemExpediente()+"-"+expedienteCredito.getId().getIntItemDetExpediente());
											//CGD-26.12.2013
											diarioDet.setStrComentario(strObservacionPlanCta);
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
									
									}else{
										throw new BusinessException("No se recupero Modelo niveles.");
									}
								}
							}

							diario.setListaLibroDiarioDetalle(lstDiarioDetalle);
					
					// 2. Grabamos el libro diario generado
							diario = libroDiarioFacade.grabarLibroDiario(diario);
							
					// 3. Generamos el expediente (movimeinto) + cronograma y estado VIGENTE.
							if(diario.getId().getIntContCodigoLibro() != null){
					// 3.x validamos si la fecha de registro del expediente es difererente a la fecha actual
					// si es diferente se regenera el cronograma de servicio.
								EstadoCredito primerEstadoCredito = null;
								Date dtFechaRegistro = new Date();
			
								primerEstadoCredito = boEstadoCredito.getMinEstadoCreditoPorPkExpedienteCredito (expedienteCredito.getId());
								if(primerEstadoCredito != null){
									dtFechaRegistro= convertirTimestampToDate(primerEstadoCredito.getTsFechaEstado());
								}
								Boolean blnProcede = Boolean.FALSE;
								blnProcede = comparaDatesDDMMYYYY(dtFechaRegistro, dtHoy, null);
			
							// si son disitintos se realiza recalculo
								if(!blnProcede){
									
									List<CronogramaCredito> lstCronogramaCredito = null;
									lstCronogramaCredito = boCronogramaCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
									if(lstCronogramaCredito != null && !lstCronogramaCredito.isEmpty()){
										cambiarEstadoCronograma(lstCronogramaCredito, Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
									}
									
									List<CronogramaCredito> lstNuevoCronograma = null;
									List<CronogramaCredito> lstNuevoCronogramaFinal = null;
									// se vuelve a generar el cronogrma
									lstNuevoCronograma = reCalcularCronogramaActividad(expedienteCredito, estructuraDetalle, beanSocioComp);
									for (CronogramaCredito cron : lstNuevoCronograma) {
										System.out.println("CUOTA - "+cron.getIntNroCuota());
									}
									// grabamos en servicio el cronograma recientemente creado
									if(lstNuevoCronograma != null && !lstNuevoCronograma.isEmpty()){
										lstNuevoCronogramaFinal = new ArrayList<CronogramaCredito>();
										for (CronogramaCredito nuevoCronograma : lstNuevoCronograma) {
											nuevoCronograma = boCronogramaCredito.grabar(nuevoCronograma);
											lstNuevoCronogramaFinal.add(nuevoCronograma);
										}
									}
								}

								expedienteMovimiento = generarExpedienteMovimientoActividad(expedienteCredito);
								
								if(expedienteMovimiento != null){
									expedienteMovimiento = conceptoFacace.grabarExpedienteCompleto(expedienteMovimiento);
									
									if(expedienteMovimiento != null){
										
										movimientoActividad= generarMovimientoActividad(expedienteCredito, diario);
										if(movimientoActividad == null || movimientoActividad.getIntItemMovimiento() == null){
											throw new BusinessException("Error en movimientoActividad. ");
										}
									}else{
										throw new BusinessException("Error en expedienteMovimiento. ");
									}
								}else{
									throw new BusinessException("No se genero expedienteMovimiento.");
								}
							}	
						/*}else{
							throw new BusinessException("Error en cambioEstadoActividad .");
						}*/
					}else{
						throw new BusinessException("No se recupero Modelo Detalle.");
					}
				}else{
					throw new BusinessException("No se recupero Modelo.");
				}

			}catch(BusinessException e){
				log.error("Error - BusinessException - en obtieneLibroDiarioYDiarioDetalleParaActividad --> "+e);
				throw e;
			}catch(Exception e){
				log.error("Error - Exception - en obtieneLibroDiarioYDiarioDetalleParaActividad --> "+e);
				throw new BusinessException(e);
			}
		return diario;
	}
	
	
	/**
	 * 
	 * @param expedienteCredito
	 * @param estructuraDetalle
	 * @param beanSocioComp
	 * @return
	 */
	private List<CronogramaCredito> reCalcularCronogramaActividad(
			ExpedienteCredito expedienteCredito,
			EstructuraDetalle estructuraDetalle, SocioComp beanSocioComp) {
//			Integer intTipoSolicitudSubActividad = 0;
			Credito creditoBusq = null;
			Credito beanCredito = null;
			List<Credito> lstCreditos = null;
			Integer intNroCuotas = 0 ;
			List<ExpedienteActividad> lstActividad = null;
			ExpedienteActividad expActividad = null;
			String strMontoACancelar = "";
			BigDecimal bdMontoCancelar = BigDecimal.ZERO;
			Calendar fecHoy = Calendar.getInstance();
			CreditoFacadeRemote creditoFacade = null;
			CreditoInteres creditoInteresExpediente = null;
			List<CronogramaCredito> lstNuevoCronograma = null;
		try {
			
			creditoFacade = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
			
			intNroCuotas = expedienteCredito.getIntNumeroCuota();
			lstActividad = boExpedienteActividad.getListaPorPkExpedienteCredito(expedienteCredito.getId());
			if(lstActividad != null && !lstActividad.isEmpty()){
				expActividad= new ExpedienteActividad();
				expActividad = lstActividad.get(0);
				if(expActividad.getBdMontoCancelado()!= null){
					strMontoACancelar = expActividad.getBdMontoCancelado().toString();
					bdMontoCancelar = expActividad.getBdMontoCancelado();
				}
			}
				
			creditoBusq = new Credito();
			creditoBusq.setId(new CreditoId());
			creditoBusq.getId().setIntItemCredito(expedienteCredito.getIntItemCredito());
			creditoBusq.getId().setIntParaTipoCreditoCod(expedienteCredito.getIntParaTipoCreditoCod());
			creditoBusq.getId().setIntPersEmpresaPk(expedienteCredito.getIntPersEmpresaCreditoPk());
			
			lstCreditos = creditoFacade.getlistaCreditoPorCredito(creditoBusq);
			if(lstCreditos != null && !lstCreditos.isEmpty()){
				beanCredito = new Credito();
				beanCredito = lstCreditos.get(0);
				
				if(beanCredito.getListaCreditoInteres()!= null && !beanCredito.getListaCreditoInteres().isEmpty()){

					for (CreditoInteres interes : beanCredito.getListaCreditoInteres()) {
						if(interes.getIntParaTipoSocio().compareTo(beanSocioComp.getSocio().getSocioEstructura()!=null?beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio():1)==0
								&& interes.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
							creditoInteresExpediente = new CreditoInteres();
							creditoInteresExpediente = interes;
							break;
						}	
					}
				}
				
				// verifivacomos el tipo de actividad
				if(expedienteCredito.getIntParaTipoCreditoCod().compareTo(Constante.PARAM_T_TIPO_CREDITO_ACTIVIDAD)==0){
					// es contado
					if(expedienteCredito.getIntParaSubTipoOperacionCod().compareTo(Constante.PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_CONTADO)==0){
						lstNuevoCronograma= generarCronogramaContado(beanCredito, estructuraDetalle,fecHoy,expedienteCredito, creditoInteresExpediente);
						
					}else if(expedienteCredito.getIntParaSubTipoOperacionCod().compareTo(Constante.PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_CREDITO)==0){
					// es credito
						Boolean blnCronogramaNormal= recuperarTipoCronograma(strMontoACancelar);
						if(blnCronogramaNormal){
							//Integer intRecalculo = 0;
							lstNuevoCronograma = generarCronogramaNormal(beanCredito, estructuraDetalle, intNroCuotas, fecHoy, beanSocioComp, expedienteCredito, strMontoACancelar);
						}else{
							// Puede darse el caso en que se cancele el integro en base a al adelanto...
							if(bdMontoCancelar.compareTo(creditoInteresExpediente.getBdMontoMaximo())==0){
								lstNuevoCronograma = generarCronogramaAdelantoTotal(beanCredito, estructuraDetalle,fecHoy,expedienteCredito, strMontoACancelar);
							}else{
								lstNuevoCronograma = generarCronogramaAdelanto(beanCredito, estructuraDetalle, intNroCuotas,fecHoy,beanSocioComp,expedienteCredito, strMontoACancelar);
							}
						}
						
						
					}
				}
			}
			
			
		} catch (Exception e) {
			log.error("Error en reCalcularCronogramaActividad ---> "+e);
		}
		
		
		return lstNuevoCronograma;
	}


	/**
	 * Genera el Expedienete de Movmineto en base al expedientede servicio.
	 * Ademas copia cronograma y estado vigente
	 * @param expedienteCredito
	 * @return
	 * @throws Exception
	 */
	public Expediente generarExpedienteMovimientoActividad(ExpedienteCredito expedienteCredito) throws Exception{
			
			Expediente expediente = null;
			ExpedienteId expedienteId = null;
			List<Cronograma> lstCronogramaMov = null;
			//ConceptoFacadeRemote conceptofacade =null;
			EstadoExpediente estadoVigente =null;
			SolicitudPrestamoFacadeRemote solicitudPrestamoFacade = null;
			
			try {
				
				//conceptofacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
				solicitudPrestamoFacade = (SolicitudPrestamoFacadeRemote)EJBFactory.getRemote(SolicitudPrestamoFacadeRemote.class);
				
				if(expedienteCredito != null){
					expedienteId = new ExpedienteId();
					expediente	 = new Expediente();
					expediente.setListaCronograma(new ArrayList<Cronograma>());
					
					expedienteId.setIntCuentaPk(expedienteCredito.getId().getIntCuentaPk());
					expedienteId.setIntPersEmpresaPk(expedienteCredito.getId().getIntPersEmpresaPk());
					expedienteId.setIntItemExpediente(expedienteCredito.getId().getIntItemExpediente());
					expedienteId.setIntItemExpedienteDetalle(expedienteCredito.getId().getIntItemDetExpediente());
					expediente.setId(expedienteId);
					
					expediente.setIntPersEmpresaCreditoPk(expedienteCredito.getIntPersEmpresaCreditoPk()); //  5
					expediente.setIntParaTipoCreditoCod(expedienteCredito.getIntParaTipoCreditoCod()); //  6
					expediente.setIntItemCredito(expedienteCredito.getIntItemCredito()); //  7
					expediente.setBdPorcentajeInteres(expedienteCredito.getBdPorcentajeInteres()); //  8
					expediente.setBdPorcentajeGravamen(expedienteCredito.getBdPorcentajeGravamen()); //  9
					expediente.setBdPorcentajeAporte(expedienteCredito.getBdPorcentajeAporte()); //  10
					expediente.setBdMontoInteresAtrazado(BigDecimal.ZERO);
					expediente.setBdMontoMoraAtrazado(BigDecimal.ZERO);
					expediente.setBdMontoSolicitado(expedienteCredito.getBdMontoSolicitado()); //  
					expediente.setBdMontoTotal(expedienteCredito.getBdMontoTotal()); //  5
					expediente.setIntNumeroCuota(expedienteCredito.getIntNumeroCuota()); //  5
					expediente.setBdSaldoCredito(expedienteCredito.getBdMontoTotal()); //  5
					
					//expediente.setBdMontoSaldoDetalle(BigDecimal.ZERO);
					//expediente.setBdMontoAbono(BigDecimal.ZERO);
					
					expediente.setListaCronograma(new ArrayList<Cronograma>());	
					
					lstCronogramaMov = solicitudPrestamoFacade.generarCronogramaMovimiento(expedienteCredito.getId());
					if(lstCronogramaMov != null && !lstCronogramaMov.isEmpty()){
						expediente.setListaCronograma(lstCronogramaMov);
					}

					
					expediente.setListaEstadosExpediente(new ArrayList<EstadoExpediente>());		
					// generamos el 1er estado vigente
					estadoVigente = new EstadoExpediente();
					EstadoExpedienteId estadoId = new EstadoExpedienteId();
					
					estadoId.setIntEmpresaEstado(expediente.getId().getIntPersEmpresaPk());
					estadoVigente.setId(estadoId);
					estadoVigente.setIntCuenta(expediente.getId().getIntCuentaPk());
					estadoVigente.setIntEmpresa(expediente.getId().getIntPersEmpresaPk());
					//estadoVigente.setIntIndicadorEntrega(intIndicadorEntrega);
					//estadoVigente.setIntItemCuentaConcepto(intItemCuentaConcepto);
					estadoVigente.setIntItemDetExpediente(expediente.getId().getIntItemExpedienteDetalle());
					estadoVigente.setIntItemExpediente(expediente.getId().getIntItemExpediente());
					estadoVigente.setIntParaEstadoExpediente(Constante.PARAM_T_ESTADOS_EXPEDIENTE_MOVIMIENTO_VIGENTE);

					expediente.getListaEstadosExpediente().add(estadoVigente);
				}
			} catch (Exception e) {
				log.error("Error - Exception - en generarExpedienteMovimientoActividad --> "+e);
				throw e;
			}
			return expediente;
		}
	
	
	
	/**
	 * Genera el registrod e Movimieto para la autorizaciond e actividad
	 * @param expedienteCredito
	 * @return
	 * @throws Exception
	 */
	public Movimiento generarMovimientoActividad(ExpedienteCredito expedienteCredito, LibroDiario diario) throws Exception{
		
		Movimiento movActividad = null;
		CuentaFacadeRemote cuentaFacade = null;
		CuentaId cuentaId = null; 
		Integer intIdPersona= 0;
		BigDecimal bdMontoOperacion = BigDecimal.ZERO;
		ConceptoFacadeRemote conceptoFacade =null;
		
		try {
			
			//conceptofacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			//solicitudPrestamoFacade = (SolicitudPrestamoFacadeRemote)EJBFactory.getRemote(SolicitudPrestamoFacadeRemote.class);
			cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			//socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			
			if(expedienteCredito != null){
				 cuentaId = new CuentaId();
				List<CuentaIntegrante> lstCuentaIntegrante = null;
				cuentaId.setIntCuenta(expedienteCredito.getId().getIntCuentaPk());
				cuentaId.setIntPersEmpresaPk(expedienteCredito.getId().getIntPersEmpresaPk());

				// recuperamos el socio comp en base a la cuenta...
				lstCuentaIntegrante = cuentaFacade.getListaCuentaIntegrantePorPKCuenta(cuentaId);
				if(lstCuentaIntegrante != null && !lstCuentaIntegrante.isEmpty()){
					for (CuentaIntegrante cuentaIntegrante : lstCuentaIntegrante) {
						if(cuentaIntegrante.getIntParaTipoIntegranteCod().compareTo(new Integer(1))==0){
							intIdPersona = cuentaIntegrante.getId().getIntPersonaIntegrante();
							break;
						}
					}
				}
			
				movActividad	 = new Movimiento();
				movActividad.setTsFechaMovimiento(obtieneFechaActualEnTimesTamp());
				movActividad.setIntPersEmpresa(Constante.PARAM_EMPRESASESION);
				movActividad.setIntCuenta(expedienteCredito.getId().getIntCuentaPk());
				movActividad.setIntPersPersonaIntegrante(intIdPersona);
				movActividad.setIntItemExpediente(expedienteCredito.getId().getIntItemExpediente());
				movActividad.setIntItemExpedienteDetalle(expedienteCredito.getId().getIntItemDetExpediente());
				movActividad.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_AMORTIZACION);
				movActividad.setIntParaTipoMovimiento(Constante.PARAM_T_FORMADEPAGO_PROCESO_AUTOMATICO);
				movActividad.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_ACTIVIDAD);
				//Agregado 21.03.2014
				movActividad.setStrNumeroDocumento(expedienteCredito.getId().getIntItemExpediente()+"-"+expedienteCredito.getId().getIntItemDetExpediente());
				movActividad.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_CARGO);
				
				for (LibroDiarioDetalle detalle : diario.getListaLibroDiarioDetalle()) {
					if(detalle.getBdDebeSoles() != null){
						bdMontoOperacion = bdMontoOperacion.add(detalle.getBdDebeSoles());
					}
				}
				movActividad.setBdMontoMovimiento(bdMontoOperacion);
				movActividad.setBdMontoSaldo(bdMontoOperacion);
				movActividad.setIntPersEmpresaUsuario(diario.getIntPersEmpresaUsuario());
				movActividad.setIntPersPersonaUsuario(diario.getIntPersPersonaUsuario());
				
				movActividad = conceptoFacade.grabarMovimiento(movActividad);
				
			}
		} catch (Exception e) {
			log.error("Error - Exception - en generarMovimientoActividad --> "+e);
		}
		
		return movActividad;
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 */
    public static final Integer obtieneAnio(Date date){
		java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("yyyy");
		String minuto = sdf.format(date);
		return new Integer(minuto);
	}
	
	
    /**
     * 
     * @param date
     * @return
     */
    public static final String obtieneMesCadena(Date date){
  		java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("MM");
 		String minuto = sdf.format(date);
 		return minuto;
 	}
	
    
	/**
	 * 
	 * @param date
	 * @return
	 */
    public static String  obtieneAnioCadena(Date date){
		java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("yyyy");
		String anio = sdf.format(date);
		return anio;
	}
	
	
    /**
     * 
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
	 * Genera y braba el estado APROBADO segun expediente
	 * @param expedienteCredito
	 * @param usuario
	 * @return
	 * @throws Exception
	 */
	private EstadoCredito cambioEstadoActividad(ExpedienteCredito expedienteCredito,
			Usuario usuario, LibroDiario libroDiario) throws Exception {
			EstadoCredito estadoCredito = new EstadoCredito();
			EstadoCreditoId estadoCreditoId = null;
			Integer intParaEstadoCreditoCod = null;

			try {
				intParaEstadoCreditoCod = Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO;
				estadoCreditoId = new EstadoCreditoId();
				estadoCreditoId.setIntCuentaPk(expedienteCredito.getId().getIntCuentaPk());
				estadoCreditoId.setIntItemDetExpediente(expedienteCredito.getId().getIntItemDetExpediente());
				estadoCreditoId.setIntItemExpediente(expedienteCredito.getId().getIntItemExpediente());
				estadoCreditoId.setIntPersEmpresaPk(expedienteCredito.getId().getIntPersEmpresaPk());

				estadoCredito.setId(estadoCreditoId);
				estadoCredito.setTsFechaEstado(new Timestamp(new Date().getTime()));
				estadoCredito.setIntPersEmpresaEstadoCod(usuario.getEmpresa().getIntIdEmpresa());
				estadoCredito.setIntIdUsuSucursalPk(usuario.getSucursal().getId().getIntIdSucursal());
				estadoCredito.setIntIdUsuSubSucursalPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
				estadoCredito.setIntPersUsuarioEstadoPk(usuario.getIntPersPersonaPk());
				/* Agregado 12.03.2014 - JCHAVEZ
				 * Datos del Libro Diario */
				estadoCredito.setIntPersEmpresaLibro(libroDiario.getId().getIntPersEmpresaLibro());
				estadoCredito.setIntPeriodoLibro(libroDiario.getId().getIntContPeriodoLibro());
				estadoCredito.setIntCodigoLibro(libroDiario.getId().getIntContCodigoLibro());
				//Fin datos Libro Diario
				estadoCredito.setIntParaEstadoCreditoCod(intParaEstadoCreditoCod);

				estadoCredito = boEstadoCredito.grabar(estadoCredito);
			} catch (Exception e) {
				log.error("Error - Exception - en cambioEstadoActividad --> "+e);
				throw e;
			}
			return estadoCredito;
		}
	/**
	 * JCHAVEZ 09.04.2014 
	 * Se graba el estado de girado cuando se realiza un refinanciamiento
	 * @param expedienteCredito
	 * @param usuario
	 * @param libroDiario
	 * @return
	 * @throws Exception
	 */
	private EstadoCredito cambioEstadoRefinanciamientoAGirado(ExpedienteCredito expedienteCredito,
			Usuario usuario, LibroDiario libroDiario) throws Exception {
			EstadoCredito estadoCredito = new EstadoCredito();
			EstadoCreditoId estadoCreditoId = null;
			Integer intParaEstadoCreditoCod = null;

			try {
				intParaEstadoCreditoCod = Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO;
				estadoCreditoId = new EstadoCreditoId();
				estadoCreditoId.setIntCuentaPk(expedienteCredito.getId().getIntCuentaPk());
				estadoCreditoId.setIntItemDetExpediente(expedienteCredito.getId().getIntItemDetExpediente());
				estadoCreditoId.setIntItemExpediente(expedienteCredito.getId().getIntItemExpediente());
				estadoCreditoId.setIntPersEmpresaPk(expedienteCredito.getId().getIntPersEmpresaPk());

				estadoCredito.setId(estadoCreditoId);
				estadoCredito.setTsFechaEstado(new Timestamp(new Date().getTime()));
				estadoCredito.setIntPersEmpresaEstadoCod(usuario.getEmpresa().getIntIdEmpresa());
				estadoCredito.setIntIdUsuSucursalPk(usuario.getSucursal().getId().getIntIdSucursal());
				estadoCredito.setIntIdUsuSubSucursalPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
				estadoCredito.setIntPersUsuarioEstadoPk(usuario.getIntPersPersonaPk());
				/* Agregado 12.03.2014 - JCHAVEZ
				 * Datos del Libro Diario */
				estadoCredito.setIntPersEmpresaLibro(null);
				estadoCredito.setIntPeriodoLibro(null);
				estadoCredito.setIntCodigoLibro(null);
				//Fin datos Libro Diario
				estadoCredito.setIntParaEstadoCreditoCod(intParaEstadoCreditoCod);

				estadoCredito = boEstadoCredito.grabar(estadoCredito);
			} catch (Exception e) {
				log.error("Error - Exception - en cambioEstadoActividad --> "+e);
				throw e;
			}
			return estadoCredito;
		}
	
	
	/**
	 * Recupera los expedientres de credito de acuerdo a los filtros de la grilla de busqueda.
	 * @param intTipoConsultaBusqueda
	 * @param strConsultaBusqueda
	 * @param strNumeroSolicitudBusq
	 * @param estadoCreditoBusqueda
	 * @param expCreditoBusqueda
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCreditoComp> getListaExpedienteCreditoCompDeBusquedaFiltro(Integer intTipoConsultaBusqueda, String strConsultaBusqueda, 
		String strNumeroSolicitudBusq, EstadoCredito estadoCreditoBusqueda, ExpedienteCredito expCreditoBusqueda) throws BusinessException{
			List<ExpedienteCreditoComp> lista = null;
			List<ExpedienteCreditoComp> dto = null;
			List<ExpedienteCreditoComp> listaExpedienteCreditoComp = null;
			
			Boolean blnAplicaTipoBusqueda = Boolean.FALSE; // asociado a strConsultaBusqueda
			Boolean blnAplicaNroSolicitud = Boolean.FALSE; // asociado a strNumeroSolicitudBusq
			Boolean blnAplicaEstado = Boolean.FALSE;
			Boolean blnAplicaTipoPrestamo = Boolean.FALSE;
			Boolean blnAplicaSucursal = Boolean.FALSE;
			Integer intTipoPersona = 0; // 1 natural   2 juridica
			List<Sucursal> listaSucursalesFiltradas = null;
			Boolean blnCumpleFiltros = Boolean.TRUE;

			try{
				
				//socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
				//tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
				//creditoFacade = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
				//empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);

				// 0. Validando los foltros a aplicar
				if(!(strNumeroSolicitudBusq.equalsIgnoreCase("") || strNumeroSolicitudBusq.isEmpty())){
					blnAplicaNroSolicitud = Boolean.TRUE;	
				} 
				
				if(estadoCreditoBusqueda.getIntParaEstadoCreditoCod().compareTo(0)!=0){
					blnAplicaEstado = Boolean.TRUE;	
				} 
				
				if(expCreditoBusqueda.getIntPersEmpresaCreditoPk().compareTo(0)!=0){
					blnAplicaTipoPrestamo = Boolean.TRUE;	
				} 
				
				if(intTipoConsultaBusqueda.compareTo(0)!=0){
					blnAplicaTipoBusqueda = Boolean.TRUE;
					if(intTipoConsultaBusqueda.compareTo(Constante.PARAM_T_TIPO_BUSQ_NOMBRES)==0 
					|| intTipoConsultaBusqueda.compareTo(Constante.PARAM_T_TIPO_BUSQ_DNI)==0){
						intTipoPersona = 1;
					}else{
						intTipoPersona = 2;
					}
				}
				
				if(estadoCreditoBusqueda.getIntIdUsuSucursalPk().compareTo(new Integer(0)) != 0){
					blnAplicaSucursal = Boolean.TRUE;
						listaSucursalesFiltradas = cargarSucursalesValidas(estadoCreditoBusqueda.getIntIdUsuSucursalPk());
				} 
				if(estadoCreditoBusqueda.getIntIdUsuSucursalPk().compareTo(new Integer(-2)) == 0){
					blnAplicaSucursal = Boolean.FALSE;
				}

				// listaExpedienteCredito = boExpedienteCredito.getListaBusquedaPorExpCredComp();
				listaExpedienteCreditoComp = getExpedienteCreditoParaFiltros(intTipoPersona);
				
				if(listaExpedienteCreditoComp != null && !listaExpedienteCreditoComp.isEmpty()){
					lista = new ArrayList<ExpedienteCreditoComp>();
					
					for (ExpedienteCreditoComp expComp : listaExpedienteCreditoComp) {
						blnCumpleFiltros = Boolean.TRUE;
						
					// *** Se valida strNumeroSolicitudBusq
						if(blnAplicaNroSolicitud){
							// Verificamos si contiene el guion...
							if(strNumeroSolicitudBusq.contains("-")){
								if(expComp.getStrNroSolicitudBusqueda().equalsIgnoreCase(strNumeroSolicitudBusq)){
									blnCumpleFiltros = true;
								}else{
									blnCumpleFiltros = false;
								}
							}else{
								if(expComp.getExpedienteCredito().getId().getIntItemExpediente().toString().equalsIgnoreCase(strNumeroSolicitudBusq)){
									blnCumpleFiltros = true;
								}else{
									blnCumpleFiltros = false;
								}
							}			
						}
						
						if(blnCumpleFiltros){
					// *** Se valida Estado de expediente
							if(blnAplicaEstado){
								Integer intEstadoFiltro = estadoCreditoBusqueda.getIntParaEstadoCreditoCod();
								Integer intEstadoExp = expComp.getExpedienteCredito().getEstadoCreditoUltimo().getIntParaEstadoCreditoCod();

								if(intEstadoExp.compareTo(intEstadoFiltro)==0){
									blnCumpleFiltros = true;
								}else{
									blnCumpleFiltros = false;
								}	
							}
						}
						
						if(blnCumpleFiltros){
					// *** Se valida strNumeroSolicitudBusq
							if(blnAplicaTipoPrestamo){
								Integer intTipoPrestamoFiltro =  expCreditoBusqueda.getIntPersEmpresaCreditoPk();
								Integer intCreditoEmpresa = expComp.getIntParaCreditoEmpresa();

								if(intTipoPrestamoFiltro.compareTo(intCreditoEmpresa)==0){
									blnCumpleFiltros = true;
								}else{
									blnCumpleFiltros = false;
								}	
							}
						}
					
						if(blnCumpleFiltros){
					// *** Se valida strNumeroSolicitudBusq
							if(blnAplicaSucursal){
								if(listaSucursalesFiltradas != null && !listaSucursalesFiltradas.isEmpty()){
									
									for (Sucursal sucursal : listaSucursalesFiltradas) {
										//System.out.println("======================================================");
										//System.out.println("sucursal.getJuridica().getIntIdPersona()-> "+sucursal.getJuridica().getIntIdPersona());
										//System.out.println("sucursal.getId().getIntIdSucursal()-> "+sucursal.getId().getIntIdSucursal());
										//System.out.println("---------- SE COMPARA CON ----------");
										//System.out.println("expComp.getExpedienteCredito().getEstadoCreditoUltimo().getIntIdUsuSucursalPk() -> "+expComp.getExpedienteCredito().getEstadoCreditoUltimo().getIntIdUsuSucursalPk());
										//System.out.println("--------RESULTADO---------------");
										//System.out.println(sucursal.getJuridica().getIntIdPersona().compareTo(expComp.getExpedienteCredito().getEstadoCreditoUltimo().getIntIdUsuSucursalPk())==0);

										//if(sucursal.getJuridica().getIntIdPersona().compareTo(expComp.getExpedienteCredito().getEstadoCreditoUltimo().getIntIdUsuSucursalPk())==0){
										if(sucursal.getId().getIntIdSucursal().compareTo(expComp.getExpedienteCredito().getEstadoCreditoUltimo().getIntIdUsuSucursalPk())==0){
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
					dto = completarDatosDeCreditos(lista);
				}
				
			}catch(Exception e){
				log.error("Error - Exception - en getListaExpedienteCreditoCompDeBusqueda --> "+e);
				throw new BusinessException(e);
			}
			return dto;
	}
	
	
	/**
	 * Completa los datos de la lista de credito. Se le agrrgn las fechas de los estados y la descripcion del prestamo
	 * @param lista
	 * @return
	 */
	public List<ExpedienteCreditoComp> completarDatosDeCreditos( List<ExpedienteCreditoComp> lista){
		List<ExpedienteCreditoComp> listaFinal = null;
		CreditoFacadeRemote creditoFacade = null;
		TablaFacadeRemote tablaFacade = null;
		CuentaFacadeRemote cuentaFacade = null;
		
		try {
			creditoFacade = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			
			if(lista != null && !lista.isEmpty()){
				listaFinal = new ArrayList<ExpedienteCreditoComp>();
				
				for(ExpedienteCreditoComp expedienteCreditoComp : lista){
					List<EstadoCredito> listaEstadoCredito = null;
					listaEstadoCredito = boEstadoCredito.getListaPorPkExpedienteCredito(expedienteCreditoComp.getExpedienteCredito().getId());
					if(listaEstadoCredito!=null && !listaEstadoCredito.isEmpty()){
						for (EstadoCredito estado : listaEstadoCredito) {
							if(estado.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO))
								expedienteCreditoComp.setStrFechaRequisito(Constante.sdf2.format(estado.getTsFechaEstado()));
							if(estado.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD))
								expedienteCreditoComp.setStrFechaSolicitud(Constante.sdf2.format(estado.getTsFechaEstado()));
							if(estado.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO))
								expedienteCreditoComp.setStrFechaAutorizacion(Constante.sdf2.format(estado.getTsFechaEstado()));
						}
					}

					expedienteCreditoComp.getExpedienteCredito().setListaEstadoCredito(listaEstadoCredito);
					
					CreditoId creditoId= null;
					String strDescripcionExpedienteCredito = "Desconocido";
					// Recuperamos el nombre general del prestamo asociado al expediente
					creditoId = new CreditoId();
					creditoId.setIntItemCredito(expedienteCreditoComp.getExpedienteCredito().getIntItemCredito());
					creditoId.setIntParaTipoCreditoCod(expedienteCreditoComp.getExpedienteCredito().getIntParaTipoCreditoCod());
					creditoId.setIntPersEmpresaPk(expedienteCreditoComp.getExpedienteCredito().getIntPersEmpresaCreditoPk());
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
					//  getCuentaPorId(CuentaId cuentaId)
					// Recuperamos la cuenta relacionada con la solicutd - 28.08.2013
					Cuenta ctaSolicitud = null;
					CuentaId ctaId = new CuentaId();
					ctaId.setIntPersEmpresaPk(expedienteCreditoComp.getExpedienteCredito().getId().getIntPersEmpresaPk());
					ctaId.setIntCuenta(expedienteCreditoComp.getExpedienteCredito().getId().getIntCuentaPk());
					
					ctaSolicitud = cuentaFacade.getListaCuentaPorPkTodoEstado(ctaId);
					if(ctaSolicitud != null){
						expedienteCreditoComp.setStrNumeroCuentaSegunCaso(ctaSolicitud.getStrNumeroCuenta());
					}
					expedienteCreditoComp.setStrDescripcionExpedienteCredito(strDescripcionExpedienteCredito);
					listaFinal.add(expedienteCreditoComp);
				}
			}

		} catch (Exception e) {
			log.error("Error en completarDatosDeCreditos ---> "+e);
		}

		return listaFinal;
	}
	
	/**
	 * Recupera los expedientes de credito con los datos necesarios para validar filtros de grilla de busqueda
	 * de solicitud de credito. 1 nat / 2 jur / 0 nada
	 * @param intTipoPersona
	 * @return
	 */
	public List<ExpedienteCreditoComp> getExpedienteCreditoParaFiltros(Integer intTipoPersona){
		List<ExpedienteCredito> lstExpedientesTemp = null;
		List<ExpedienteCreditoComp> lstExpedientesComp = new ArrayList<ExpedienteCreditoComp>() ;
		ExpedienteCreditoComp expedienteComp = null;
		EstadoCredito ultimoEstadoCredito = null;
		CuentaFacadeRemote cuentaFacade = null;
		PersonaFacadeRemote personaFacade = null;
		SocioFacadeRemote socioFacade = null;
		CreditoFacadeRemote creditoFacade = null;
		TablaFacadeRemote tablaFacade = null;
		SocioComp socioComp = null;
		String strConcatenadoSolicitud = "";
		//List<EstadoCredito> listaEstadoCredito = null;
		
		try {
			
			cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			creditoFacade = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			
			lstExpedientesTemp = boExpedienteCredito.getListaBusquedaPorExpCredComp(Constante.PARAM_T_TIPO_CREDITO_PRESTAMO);
			
			if(lstExpedientesTemp != null && !lstExpedientesTemp.isEmpty()){
				
				for (ExpedienteCredito expedienteCredito : lstExpedientesTemp) {
					expedienteComp = new ExpedienteCreditoComp();
					Integer intIdPersona = 0;
					Persona persona = null;
					Juridica juridica =null;
					socioComp = new SocioComp();
					
					//A. cargando el uiltimo estado
					ultimoEstadoCredito = boEstadoCredito.getMaxEstadoCreditoPorPokExpedienteCredito(expedienteCredito.getId());
					if(ultimoEstadoCredito != null){
						expedienteCredito.setEstadoCreditoUltimo(ultimoEstadoCredito);
						expedienteComp.setEstadoCredito(ultimoEstadoCredito);
						expedienteComp.setExpedienteCredito(expedienteCredito);
					}
					
					//B. Recuperamos y cargamos el socioComp en base  a la cuenta de cada expediente
					CuentaId cuentaId = new CuentaId();
					List<CuentaIntegrante> lstCuentaIntegrante = null;
					cuentaId.setIntCuenta(expedienteCredito.getId().getIntCuentaPk());
					cuentaId.setIntPersEmpresaPk(expedienteCredito.getId().getIntPersEmpresaPk());

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
									if(documento.getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))
									&& documento.getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
										persona.setDocumento(documento);
										//break;
									}
									if(documento.getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_RUC))
										&& documento.getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
										persona.setRuc(documento);
										//break;
									}
								}
							}
							socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI), persona.getDocumento().getStrNumeroIdentidad(), Constante.PARAM_EMPRESASESION);
							expedienteComp.setSocioComp(socioComp);
						}
						
						if(intTipoPersona.compareTo(2)==0){
							juridica = personaFacade.getJuridicaPorPK(intIdPersona);
							if(juridica != null){
								expedienteComp.getSocioComp().getPersona().setJuridica(juridica);
							}
						}
							
					}
					
					// C. recuperamos el concatenado del nro de solicitud...
					strConcatenadoSolicitud= expedienteCredito.getId().getIntItemExpediente()+"-"+expedienteCredito.getId().getIntItemDetExpediente();
					expedienteComp.setStrNroSolicitudBusqueda(strConcatenadoSolicitud);

					// D. Recuperamos el tipo de empresa credito
					CreditoId creditoId= null;
					String strDescripcionExpedienteCredito = "Desconocido";
					Integer intParaCreditoEmpresa =0;
					
					// Recuperamos el nombre general del prestamo asociado al expediente
					creditoId = new CreditoId();
					creditoId.setIntItemCredito(expedienteComp.getExpedienteCredito().getIntItemCredito());
					creditoId.setIntParaTipoCreditoCod(expedienteComp.getExpedienteCredito().getIntParaTipoCreditoCod());
					creditoId.setIntPersEmpresaPk(expedienteComp.getExpedienteCredito().getIntPersEmpresaCreditoPk());
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
									intParaCreditoEmpresa = creditoRec.getIntParaTipoCreditoEmpresa();
									break;
								}
							}
						}
					}
					expedienteComp.setStrDescripcionExpedienteCredito(strDescripcionExpedienteCredito);
					expedienteComp.setIntParaCreditoEmpresa(intParaCreditoEmpresa);
					lstExpedientesComp.add(expedienteComp);
				}
				
			}
			
		} catch (Exception e) {
			log.error("Error en getExpedienteCreditoParaFiltros --> "+e);
		}
		
		return lstExpedientesComp;
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
	 * Devuleve los expedientes de una cuenta y cuyo ultimo estado.
	 * @param cuenta
	 * @param intEstadoSol
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCredito> getListaExpedienteCreditoPorCuentaYUltimoEstado(Cuenta cuenta, Integer intEstadoSol ) throws BusinessException{
		//List<ExpedienteCredito> lista = null;
		List<ExpedienteCredito> listaExpedienteCredito = null;
		List<ExpedienteCredito> listaExpedienteCreditoFiltrado = null;
		
		try{
			listaExpedienteCredito = boExpedienteCredito.getListaPorCuenta(cuenta);
			
			if(listaExpedienteCredito != null && !listaExpedienteCredito.isEmpty()){
				listaExpedienteCreditoFiltrado = new ArrayList<ExpedienteCredito>();
				
				for (ExpedienteCredito expedienteCredito : listaExpedienteCredito) {
					EstadoCredito ultimoEstado = null;
					
					ultimoEstado = boEstadoCredito.getMaxEstadoCreditoPorPokExpedienteCredito(expedienteCredito.getId());
					if(ultimoEstado != null){
						if(ultimoEstado.getIntParaEstadoCreditoCod().compareTo(intEstadoSol)==0){
							listaExpedienteCreditoFiltrado.add(expedienteCredito);
						}
					}
					
				}
			}
		}catch(BusinessException e){
			log.error("Error - BusinessException - en getListaExpedienteCreditoPorCuentaYUltimoEstado ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en getListaExpedienteCreditoPorCuentaYUltimoEstado ---> "+e);
			throw new BusinessException(e);
		}
		return listaExpedienteCreditoFiltrado;
	}

	
	/**
	 * Se utiliza para borrar las capacidades de un credito.
	 * Previamente borra los descuentos ya que dependen de las capacidades
	 * @param pCronogramaCreditoId
	 * @throws BusinessException
	 */
	public void eliminarCapacidadCredito(CapacidadCreditoId pCapacidadCreditoId, CapacidadDescuentoId pkCapDesc)throws BusinessException{
		try {
			boCapacidadDescuento.deletePorPk(pkCapDesc);
			boCapacidadCredito.deletePorPk(pCapacidadCreditoId);
		} catch (Exception e) {
			log.error("Error en eliminarCapacidadCredito --->  "+e);
		}
		
	}
	
	/**
	 * Recupera la lista de los ReqAuts en donde se ubica el usuario logueado tanto x usuario como por perfil.
	 * @param listaReqAuts
	 * @return
	 */
	public ConfServSolicitud recuperarReqAutsParaBusqueda() {
		ConfServSolicitud ReqAutFinalMontos = null;
		List<ConfServSolicitud> listaConfiguraciondAut2 = null;
		//List<ConfServCreditoEmpresa> listaConfCreditoEmpresa = null;
		//List<ConfServPerfil> listaConfPerfil = null;
		//List<ConfServUsuario> listaConfUsuario = null;
		//Boolean blnPasoValidaciones = Boolean.FALSE;
		Usuario usuario = new Usuario();
		List<ConfServSolicitud> listaConfiguraciondAut = null;
		ConfSolicitudFacadeRemote solicitudFacade = null;
		List<ConfServSolicitud> listaAutorizacionConfigurada = null;
		Date today = new Date();
		String strToday = "";
		Boolean blnContinua = Boolean.FALSE;
		BigDecimal bdMontoMin = BigDecimal.ZERO;
		BigDecimal bdMontoMax = BigDecimal.ZERO;
		
		try {
			
			strToday = Constante.sdf.format(today);
			Date dtToday = Constante.sdf.parse(strToday);
			
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			solicitudFacade = (ConfSolicitudFacadeRemote)EJBFactory.getRemote(ConfSolicitudFacadeRemote.class);
			
			// Recuperamos todos los reqauts.
			ConfServSolicitud confServSolicitud = new ConfServSolicitud();
			confServSolicitud.setIntParaTipoOperacionCod(Constante.PARAM_T_TIPOOPERACION_PRESTAMO);
			listaConfiguraciondAut = solicitudFacade.buscarConfSolicitudAutorizacion (confServSolicitud, null, null, null);

			if (listaConfiguraciondAut != null && listaConfiguraciondAut.size() > 0) {
				listaAutorizacionConfigurada = new ArrayList<ConfServSolicitud>();
				
				// Validamos su estado, vigencia, operacion y tipo de reqaut:
				for (ConfServSolicitud solicitud : listaConfiguraciondAut) {
					if(solicitud.getIntParaTipoRequertoAutorizaCod().compareTo(Constante.PARAM_T_TIPOREQAUT_AUTORIZACION)==0){
						if (solicitud.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO) == 0) {
							if(solicitud.getIntParaTipoOperacionCod().compareTo(Constante.PARAM_T_TIPOOPERACION_PRESTAMO)==0){
								if (solicitud.getDtDesde().before(dtToday)) {
									blnContinua = false;
									if(solicitud.getDtHasta() == null){
										blnContinua = true;
									}else{
										if(dtToday.before(solicitud.getDtHasta())){
											blnContinua = true;
										}else{	
											blnContinua = false;
										}
									}
									
									if(blnContinua){
										listaAutorizacionConfigurada.add(solicitud);
									}	
								}	
							}	
						}
					}
				}
			}

			// La lista recuperada y validada es recorrida para ver en cuales existe el usuario logueado (perfil y/o usuario)
			if(listaAutorizacionConfigurada != null && !listaAutorizacionConfigurada.isEmpty()){
				listaConfiguraciondAut2 = new ArrayList<ConfServSolicitud>();
				
				for (ConfServSolicitud reqAut : listaAutorizacionConfigurada) {
					reqAut.setBlnPerfil(Boolean.FALSE);
					reqAut.setBlnUsuario(Boolean.FALSE);
					reqAut.setBlnConfigurado(Boolean.FALSE);
					
					if(reqAut.getListaUsuario() != null && !reqAut.getListaUsuario().isEmpty()){
						for (ConfServUsuario confServUsuario : reqAut.getListaUsuario()) {
							if(confServUsuario.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
								if(confServUsuario.getIntPersUsuarioPk().compareTo(usuario.getIntPersPersonaPk())==0){
									reqAut.setBlnUsuario(Boolean.TRUE);
									break;
								}
							}	
						}
					}
					
					if(reqAut.getListaPerfil() != null && !reqAut.getListaPerfil().isEmpty()){
						for (ConfServPerfil confServPerfil : reqAut.getListaPerfil()) {
							if(confServPerfil.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
								if(confServPerfil.getIntIdPerfilPk().compareTo(usuario.getPerfil().getId().getIntIdPerfil())==0){
									reqAut.setBlnPerfil(Boolean.TRUE);
									break;
								}
							}	
						}	
					}
					
					// Al menos esta en usuario o perfil:
					if(reqAut.getBlnPerfil() || reqAut.getBlnUsuario()){
						reqAut.setBlnConfigurado(Boolean.TRUE);
						listaConfiguraciondAut2.add(reqAut);
					}
				}


				// Luego recobramos los montos minimos y maximos de los credito s a mostar...
				if(listaConfiguraciondAut2 != null && !listaConfiguraciondAut2.isEmpty()){
					ReqAutFinalMontos = new ConfServSolicitud();
					bdMontoMin = listaConfiguraciondAut2.get(0).getBdMontoDesde();
					bdMontoMax = listaConfiguraciondAut2.get(0).getBdMontoHasta();
					
					for (int i = 0; i < listaConfiguraciondAut2.size(); i++) {
						ConfServSolicitud configuracion = new ConfServSolicitud();
						configuracion = listaConfiguraciondAut2.get(i);
						/*System.out.println("*******************************************************************************");
						System.out.println("SOLICITUD ---> "+configuracion.getId().getIntItemSolicitud());
						System.out.println("MINIMUS ---> "+configuracion.getBdMontoDesde());
						System.out.println("MAXIMUS ---> "+configuracion.getBdMontoHasta());*/
						
						if(bdMontoMin.compareTo(configuracion.getBdMontoDesde())> 0){
							bdMontoMin = configuracion.getBdMontoDesde();
						}
						if(bdMontoMax.compareTo(configuracion.getBdMontoHasta())< 0){
							bdMontoMax = configuracion.getBdMontoHasta();
						}
					}
					
					/*System.out.println("MINIMUS final ---> "+bdMontoMin);
					System.out.println("MAXIMUS final  ---> "+bdMontoMax);
					System.out.println("*******************************************************************************");*/
					
					ReqAutFinalMontos.setBdMontoDesde(bdMontoMin);
					ReqAutFinalMontos.setBdMontoHasta(bdMontoMax);	
				}
			}else{
				ReqAutFinalMontos = null;
			}
		} catch (Exception e) {
			log.error(""+e);
		}
		return ReqAutFinalMontos;
	}

	/**
	 * Recupera la lista de expedientes para ser visualizados en la grilla de autorizacion de expedientes.
	 * En base a los filtyros definidos en la grilla.
	 * @param intTipoConsultaBusqueda
	 * @param strConsultaBusqueda
	 * @param strNumeroSolicitudBusq
	 * @param estadoCondicionFiltro
	 * @param intTipoCreditoFiltro
	 * @param estadoAutorizacionFechas
	 * @param estadoAutorizacionSuc
	 * @param intIdSubsucursalFiltro
	 * @return
	 * @throws BusinessException
	 */

	public List<ExpedienteCreditoComp> getListaExpedienteCreditoAutorizacionCompDeBusquedaFiltro(
		Integer intTipoConsultaBusqueda,String strConsultaBusqueda, 
		String strNumeroSolicitudBusq,
		EstadoCredito estadoCondicionFiltro, 
		Integer intTipoCreditoFiltro, 
		EstadoCredito estadoAutorizacionFechas,
		EstadoCredito estadoAutorizacionSuc,
		Integer intIdSubsucursalFiltro)  throws BusinessException{

		Usuario usuario;
		Integer intTipoPersona = 0;
		Integer intTipoValidacionFechas = 0;  // 0 no procede - 1 inicio  - 2 final  - 3 todo
		List<Sucursal> listaSucursalesFiltradas = null;
		List<ExpedienteCreditoComp> listaGeneralExpComp = null;
		List<ExpedienteCreditoComp> lista = null;
		List<ExpedienteCreditoComp> dto = null;
		
		// Booleanos que me indican que aplicar
		Boolean blnAplicaTipoBusqueda = Boolean.FALSE; 	// Tipo de Busqueda  (1)
		Boolean blnAplicaNroSolicitud = Boolean.FALSE;	// Nro. Solicitud  (2)
		Boolean blnAplicaSucursal = Boolean.FALSE;  	// Sucursal(3)
		Boolean blnAplicaSubSucursal = Boolean.FALSE; 	// subsucursal(4)
		Boolean blnAplicaFechas = Boolean.FALSE;  		//Fecha  inicio fin (5)
		Boolean blnAplicaEstado = Boolean.FALSE; 		// Estado  (6)
		Boolean blnAplicaTipoCredito = Boolean.FALSE; 	// Tipo  (7)
		Boolean blnCumpleFiltros = Boolean.TRUE;
		
		Boolean blnAplicaUsuarioMontos = Boolean.FALSE;
		ConfServSolicitud confServMontos = null;
		
		
		try {
			usuario = (Usuario) getRequest().getSession().getAttribute("usuario");
			usuario.getPerfil().getId().getIntIdPerfil();
			usuario.getIntPersPersonaPk();
			
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

			if(estadoCondicionFiltro.getIntParaEstadoCreditoCod().compareTo(0)!=0){
				blnAplicaEstado = Boolean.TRUE;	
			} 

			if(intTipoCreditoFiltro.compareTo(0)!=0){
				blnAplicaTipoCredito = Boolean.TRUE;	
			} 

			if(estadoAutorizacionFechas.getIntParaEstadoCreditoCod().compareTo(0)!=0){
				blnAplicaFechas = Boolean.TRUE;
				
				if(estadoAutorizacionFechas.getDtFechaEstadoDesde() != null 
					|| estadoAutorizacionFechas.getDtFechaEstadoHasta()!= null){
					blnAplicaFechas = Boolean.TRUE;
				}else{
					blnAplicaFechas = Boolean.FALSE;
				}
				
				if(blnAplicaFechas){
					if(estadoAutorizacionFechas.getDtFechaEstadoDesde()!= null
						&& estadoAutorizacionFechas.getDtFechaEstadoHasta()== null ){
						intTipoValidacionFechas = new Integer(1);
					} else if(estadoAutorizacionFechas.getDtFechaEstadoDesde()== null
						&& estadoAutorizacionFechas.getDtFechaEstadoHasta()!= null ){
						intTipoValidacionFechas = new Integer(2);
					} else if(estadoAutorizacionFechas.getDtFechaEstadoDesde()!= null
						&& estadoAutorizacionFechas.getDtFechaEstadoHasta()!= null ){
						intTipoValidacionFechas = new Integer(3);
					} else{
						blnAplicaFechas = Boolean.FALSE;
					}
				}
			}

			if(estadoAutorizacionSuc.getIntIdUsuSucursalPk().compareTo(new Integer(0)) != 0){
				blnAplicaSucursal = Boolean.TRUE;
					listaSucursalesFiltradas = cargarSucursalesValidas(estadoAutorizacionSuc.getIntIdUsuSucursalPk());
			}
			if(estadoAutorizacionSuc.getIntIdUsuSucursalPk().compareTo(new Integer(-2)) == 0){
				blnAplicaSucursal = Boolean.FALSE;
			}

			if(intIdSubsucursalFiltro.compareTo(0) != 0){
				blnAplicaSubSucursal = Boolean.TRUE;
			}
			
			confServMontos = recuperarReqAutsParaBusqueda();
			if(confServMontos != null){
				blnAplicaUsuarioMontos = Boolean.TRUE;	
			}
			
			
			listaGeneralExpComp = getExpedientCreditoParaAutorizacionFiltros(intTipoPersona);
			if(listaGeneralExpComp != null && !listaGeneralExpComp.isEmpty()){
				lista = new ArrayList<ExpedienteCreditoComp>();
				
				for (ExpedienteCreditoComp expComp : listaGeneralExpComp) {
					blnCumpleFiltros = Boolean.TRUE;
					
				// *** Se valida strNumeroSolicitudBusq
					if(blnAplicaNroSolicitud){
						// Verificamos si contiene el guion...
						if(strNumeroSolicitudBusq.contains("-")){
							if(expComp.getStrNroSolicitudBusqueda().equalsIgnoreCase(strNumeroSolicitudBusq)){
								blnCumpleFiltros = true;
							}else{
								blnCumpleFiltros = false;
							}
						}else{
							if(expComp.getExpedienteCredito().getId().getIntItemExpediente().toString().equalsIgnoreCase(strNumeroSolicitudBusq)){
								blnCumpleFiltros = true;
							}else{
								blnCumpleFiltros = false;
							}
						}			
					}
					
					if(blnCumpleFiltros){
				// *** Se valida Estado de expediente
						if(blnAplicaEstado){
							Integer intEstadoFiltro = estadoCondicionFiltro.getIntParaEstadoCreditoCod();
							Integer intEstadoExp = expComp.getExpedienteCredito().getEstadoCreditoUltimo().getIntParaEstadoCreditoCod();

							if(intEstadoExp.compareTo(intEstadoFiltro)==0){
								blnCumpleFiltros = true;
							}else{
								blnCumpleFiltros = false;
							}	
						}
					}
					
					// revisar traer el tipod e credito no el credito empresa..
					if(blnCumpleFiltros){
				// *** Se valida strNumeroSolicitudBusq
						if(blnAplicaTipoCredito){
							//Integer intTipoPrestamoFiltro =  expComp.getExpedienteCredito().getIntPersEmpresaCreditoPk();
							Integer intCreditoEmpresa = expComp.getIntParaCreditoEmpresa();

							if(intTipoCreditoFiltro.compareTo(intCreditoEmpresa)==0){
								blnCumpleFiltros = true;
							}else{
								blnCumpleFiltros = false;
							}	
						}
					}
				
					if(blnCumpleFiltros){
				// *** Se valida strNumeroSolicitudBusq
						if(blnAplicaSucursal){
							if(listaSucursalesFiltradas != null && !listaSucursalesFiltradas.isEmpty()){
								
								for (Sucursal sucursal : listaSucursalesFiltradas) {
									/*
									System.out.println("======================================================");
									//System.out.println("sucursal.getJuridica().getIntIdPersona()-> "+sucursal.getJuridica().getIntIdPersona());
									System.out.println("sucursal.getId().getIntIdSucursal()-> "+sucursal.getId().getIntIdSucursal());
									System.out.println("---------- SE COMPARA CON ----------");
									System.out.println("expComp.getExpedienteCredito().getEstadoCreditoUltimo().getIntIdUsuSucursalPk() -> "+expComp.getExpedienteCredito().getEstadoCreditoUltimo().getIntIdUsuSucursalPk());
									System.out.println("--------RESULTADO---------------");
									System.out.println(sucursal.getJuridica().getIntIdPersona().compareTo(expComp.getExpedienteCredito().getEstadoCreditoUltimo().getIntIdUsuSucursalPk())==0);
									*/
									//if(sucursal.getJuridica().getIntIdPersona().compareTo(expComp.getExpedienteCredito().getEstadoCreditoUltimo().getIntIdUsuSucursalPk())==0){
									if(sucursal.getId().getIntIdSucursal().compareTo(expComp.getExpedienteCredito().getEstadoCreditoUltimo().getIntIdUsuSucursalPk())==0){
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
							listaSubSucursal = cargarListaTablaSubSucursal(expComp.getExpedienteCredito().getEstadoCreditoUltimo().getIntIdUsuSucursalPk());
							
							if(listaSubSucursal != null && !listaSubSucursal.isEmpty()){
								for (Subsucursal subSucursal : listaSubSucursal) {
									/*
									System.out.println("======================================================");
									System.out.println("subSucursal.getId().getIntIdSubSucursal()-> "+subSucursal.getId().getIntIdSubSucursal());
									System.out.println("---------- SE COMPARA CON ----------");
									System.out.println("expComp.getExpedienteCredito().getEstadoCreditoUltimo().getIntSudeIdSubsucursal() -> "+expComp.getExpedienteCredito().getEstadoCreditoUltimo().getIntIdUsuSubSucursalPk());
									*/
									if(subSucursal.getId().getIntIdSubSucursal().compareTo(expComp.getExpedienteCredito().getEstadoCreditoUltimo().getIntIdUsuSubSucursalPk())==0){
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
						// ***  Valida fechas de inicio y fin +  esatdo
						if(blnAplicaFechas){
							List<EstadoCredito> listEstados = new ArrayList<EstadoCredito>();
							listEstados = expComp.getExpedienteCredito().getListaEstadoCredito();

							if(listEstados != null && !listEstados.isEmpty()){
								forEstados:
								for (EstadoCredito estado : listEstados) {
									if( estadoAutorizacionFechas.getIntParaEstadoCreditoCod().compareTo(estado.getIntParaEstadoCreditoCod())==0){ 
								        switch (intTipoValidacionFechas) {
								            case 1: //Fech desde
								            		if(estado.getTsFechaEstado().after(estadoAutorizacionFechas.getDtFechaEstadoDesde())){
								            			blnCumpleFiltros = true;
													} 
									                break forEstados;
							 
								            case 2: //Fech hasta
									            	if(estado.getTsFechaEstado().before(estadoAutorizacionFechas.getDtFechaEstadoHasta())){
								            			blnCumpleFiltros = true;
													} 
									                break forEstados;
									                
								            case 3:  //Fech desde  + hasta
									            	if(estado.getTsFechaEstado().before(estadoAutorizacionFechas.getDtFechaEstadoHasta())
									            		&& estado.getTsFechaEstado().after(estadoAutorizacionFechas.getDtFechaEstadoDesde())){
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
					
					
					// Se valida montos dependienedo el perfil y suuario
					if(blnCumpleFiltros){
					// *** Se valida strNumeroSolicitudBusq
						if(blnAplicaUsuarioMontos){
							BigDecimal bdMontoTotalCredito = expComp.getExpedienteCredito().getBdMontoTotal();
							System.out.println("bdMontoTotalCreditobdMontoTotalCredito------> "+bdMontoTotalCredito);
							if(bdMontoTotalCredito.compareTo(confServMontos.getBdMontoDesde())>=0
								&& bdMontoTotalCredito.compareTo(confServMontos.getBdMontoHasta())<=0){
								blnCumpleFiltros = true;
							}else{
								blnCumpleFiltros = false;
							}	
						}
					}

					
					if(blnCumpleFiltros){
						lista.add(expComp);
					}

				}	
			}
			
			if(lista != null && !lista.isEmpty()){
				dto = completarDatosDeCreditos(lista);
			}

		} catch (Exception e) {
			log.error("Error en getListaExpedienteCreditoAutorizacionCompDeBusquedaFiltro ---> "+e);
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
	 * Recupera los expedientes de credito con los datos necesarios para validar filtros de grilla de busqueda
	 * de solicitud de credito. 1 nat / 2 jur / 0 nada
	 * @param intTipoPersona
	 * @return
	 */
	public List<ExpedienteCreditoComp> getExpedientCreditoParaAutorizacionFiltros(Integer intTipoPersona){
		List<ExpedienteCredito> lstExpedientesTemp = null;
		List<ExpedienteCreditoComp> lstExpedientesComp = new ArrayList<ExpedienteCreditoComp>() ;
		ExpedienteCreditoComp expedienteComp = null;
		EstadoCredito ultimoEstadoCredito = null;
		EstadoCredito primerEstadoCredito= null;
		CuentaFacadeRemote cuentaFacade = null;
		PersonaFacadeRemote personaFacade = null;
		SocioFacadeRemote socioFacade = null;
		CreditoFacadeRemote creditoFacade = null;
		TablaFacadeRemote tablaFacade = null;
		SocioComp socioComp = null;
		String strConcatenadoSolicitud = "";
		List<EstadoCredito> listaEstadosCredito = null;
		//Cuenta cuentaSocio = null;

		try {
				cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
				personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
				creditoFacade = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
				tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
	
				lstExpedientesTemp = boExpedienteCredito.getListaBusquedaPorExpCredComp(Constante.PARAM_T_TIPO_CREDITO_PRESTAMO);
	
				if(lstExpedientesTemp != null && !lstExpedientesTemp.isEmpty()){
					for (ExpedienteCredito expedienteCredito : lstExpedientesTemp) {
						expedienteComp = new ExpedienteCreditoComp();
						Integer intIdPersona = 0;
						Persona persona = null;
						Juridica juridica =null;
						//cuentaSocio = new Cuenta();
						socioComp = new SocioComp();
	
						//A. cargando el uiltimo estado
						ultimoEstadoCredito = boEstadoCredito.getMaxEstadoCreditoPorPokExpedienteCredito(expedienteCredito.getId());
						if(ultimoEstadoCredito != null){
							expedienteCredito.setEstadoCreditoUltimo(ultimoEstadoCredito);
							expedienteComp.setEstadoCredito(ultimoEstadoCredito);//c3jcn
						}
						
						// C. recuperamos el concatenado del nro de solicitud...
						strConcatenadoSolicitud= expedienteCredito.getId().getIntItemExpediente()+"-"+expedienteCredito.getId().getIntItemDetExpediente();
						expedienteComp.setStrNroSolicitudBusqueda(strConcatenadoSolicitud);
						
	
						// VERIFICAR SOLO ESTADOS SOLIC + APROB + RECHAZ		
						if(ultimoEstadoCredito != null){
							if(ultimoEstadoCredito.getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD)==0
							|| ultimoEstadoCredito.getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)==0
							|| ultimoEstadoCredito.getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_RECHAZADO)==0){
	
								//A.1 cargando el primer estado
								primerEstadoCredito = boEstadoCredito.getMinEstadoCreditoPorPkExpedienteCredito(expedienteCredito.getId());//jv2ry
								if(primerEstadoCredito != null){
									expedienteCredito.setEstadoCreditoPrimero(primerEstadoCredito);
								}
	
								//A.2 cargando todos los estado
								listaEstadosCredito = boEstadoCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
								if(listaEstadosCredito != null){
	
									for (EstadoCredito estado : listaEstadosCredito) {
										if(expedienteCredito.getEstadoCreditoPrimero() != null  ){
											expedienteComp.setStrFechaRequisito(Constante.sdf2.format(expedienteCredito.getEstadoCreditoPrimero().getTsFechaEstado()));
										}else{//9cqbr
											if(estado.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO))
												expedienteComp.setStrFechaRequisito(Constante.sdf2.format(estado.getTsFechaEstado()));
										}
										if(estado.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD))
											expedienteComp.setStrFechaSolicitud(Constante.sdf2.format(estado.getTsFechaEstado()));
										if(estado.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO))
											expedienteComp.setStrFechaAutorizacion(Constante.sdf2.format(estado.getTsFechaEstado()));
									}
	
									expedienteCredito.setListaEstadoCredito(listaEstadosCredito);
									expedienteComp.setExpedienteCredito(expedienteCredito);
								}
	
	
								//B. Recuperamos y cargamos el socioComp en base  a la cuenta de cada expediente
								CuentaId cuentaId = new CuentaId();
								List<CuentaIntegrante> lstCuentaIntegrante = null;
								cuentaId.setIntCuenta(expedienteCredito.getId().getIntCuentaPk());
								cuentaId.setIntPersEmpresaPk(expedienteCredito.getId().getIntPersEmpresaPk());
	
								lstCuentaIntegrante = cuentaFacade.getListaCuentaIntegrantePorPKCuenta(cuentaId);
	
								if(lstCuentaIntegrante != null && !lstCuentaIntegrante.isEmpty()){
									for (CuentaIntegrante cuentaIntegrante : lstCuentaIntegrante) {
										if(cuentaIntegrante.getIntParaTipoIntegranteCod().compareTo(new Integer(1))==0){
											intIdPersona = cuentaIntegrante.getId().getIntPersonaIntegrante();
											break;
										}
									}
									//ccqfv
									persona = personaFacade.getPersonaNaturalPorIdPersona(intIdPersona);
	
									if(persona!=null){
										if(persona.getListaDocumento()!=null && persona.getListaDocumento().size()>0){
											for (Documento documento : persona.getListaDocumento()) {
												if(documento.getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))
												&& documento.getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
													persona.setDocumento(documento);
													//break;
												}
												if(documento.getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_RUC))
												&& documento.getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
													persona.setRuc(documento);
													//break;
												}
											}
										}
										socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI), persona.getDocumento().getStrNumeroIdentidad(), Constante.PARAM_EMPRESASESION);
										expedienteComp.setSocioComp(socioComp);
									 }
	
									if(intTipoPersona.compareTo(2)==0){
										juridica = personaFacade.getJuridicaPorPK(intIdPersona);
										if(juridica != null){
											expedienteComp.getSocioComp().getPersona().setJuridica(juridica);
										}
									}
								}
								
								// D. Recuperamos el tipo de empresa credito
								CreditoId creditoId= null;
								String strDescripcionExpedienteCredito = "Desconocido";
								Integer intParaCreditoEmpresa =0;
								// Recuperamos el nombre general del prestamo asociado al expediente
								creditoId = new CreditoId();
								creditoId.setIntItemCredito(expedienteComp.getExpedienteCredito().getIntItemCredito());
								creditoId.setIntParaTipoCreditoCod(expedienteComp.getExpedienteCredito().getIntParaTipoCreditoCod());
								creditoId.setIntPersEmpresaPk(expedienteComp.getExpedienteCredito().getIntPersEmpresaCreditoPk());
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
												intParaCreditoEmpresa = creditoRec.getIntParaTipoCreditoEmpresa();
												break;
											}
										}
									}
								}
								expedienteComp.setStrDescripcionExpedienteCredito(strDescripcionExpedienteCredito);
								expedienteComp.setIntParaCreditoEmpresa(intParaCreditoEmpresa);
								lstExpedientesComp.add(expedienteComp);
								
	
								// C. recuperamos el concatenado del nro de solicitud...
								//mbfp3
								//strConcatenadoSolicitud= ""+expedienteCredito.getId().getIntItemExpediente();
								//expedienteComp.setStrNroSolicitudBusqueda(strConcatenadoSolicitud);
	
							}
						}
					}
				}

			} catch (Exception e) {
				log.error("Error en getExpedientLiquidacionParaFiltros --> "+e);
			}

			return lstExpedientesComp;
		}
	
	
	/**
	 * Se recupera el sociocomp en base al nro de cuenta
	 * @param expedienteCredito
	 * @return
	 */
	public SocioComp recuperarSocioCompXCuenta(ExpedienteCredito expedienteCredito){
		
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
				cuentaId.setIntCuenta(expedienteCredito.getId().getIntCuentaPk());
				cuentaId.setIntPersEmpresaPk(expedienteCredito.getId().getIntPersEmpresaPk());
	
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
	public List<RequisitoCreditoComp> recuperarArchivosAdjuntos(Credito credito, SocioComp beanSocioComp, Integer intTipoCredito, Integer intSubTipoCredito) throws ParseException {
		log.info("-----------------------Debugging SolicitudPrestamoController.mostrarArchivosAdjuntos-----------------------------");
		ConfSolicitudFacadeRemote facade = null;
		//TablaFacadeRemote tablaFacade = null;
		EstructuraFacadeRemote estructuraFacade = null;
		ConfServSolicitud confServSolicitud = null;
		//String strToday = Constante.sdf.format(new Date());
		//Date dtToday = null;
		List<ConfServSolicitud> listaDocAdjuntos = new ArrayList<ConfServSolicitud>();
		EstructuraDetalle estructuraDet = null;
		List<EstructuraDetalle> listaEstructuraDet = new ArrayList<EstructuraDetalle>();
		List<RequisitoCreditoComp> listaRequisitoCreditoCompTemp = null;
		List<RequisitoCreditoComp> listaRequisitoCreditoComp = null;
		RequisitoCreditoComp requisitoCreditoComp;
		TablaFacadeRemote tablaFacade = null;
		try {
			
				//dtToday = Constante.sdf.parse(strToday);
				tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
				facade = (ConfSolicitudFacadeRemote) EJBFactory.getRemote(ConfSolicitudFacadeRemote.class);
				estructuraFacade = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
				//tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
				confServSolicitud = new ConfServSolicitud();
				if(intTipoCredito.compareTo(Constante.PARAM_T_TIPO_CREDITO_PRESTAMO)==0){
					confServSolicitud.setIntParaTipoRequertoAutorizaCod(Constante.PARAM_T_TIPOREQAUT_REQUISITO);
					confServSolicitud.setIntParaTipoOperacionCod(Constante.PARAM_T_TIPOOPERACION_PRESTAMO);
					confServSolicitud.setIntParaSubtipoOperacionCod(Constante.PARAM_T_SUBOPERACIONPRESTAMO_NUEVO_PRESTAMO);
					
				}else if(intTipoCredito.compareTo(Constante.PARAM_T_TIPO_CREDITO_ACTIVIDAD)==0){
					confServSolicitud = new ConfServSolicitud();
					confServSolicitud.setIntParaTipoRequertoAutorizaCod(Constante.PARAM_T_TIPOREQAUT_REQUISITO);
					confServSolicitud.setIntParaTipoOperacionCod(Constante.PARAM_T_TIPOOPERACION_ACTIVIDAD);
					confServSolicitud.setIntParaSubtipoOperacionCod(intSubTipoCredito);// 
				
				}else if(intTipoCredito.compareTo(Constante.PARAM_T_TIPO_CREDITO_REFINANCIADO)==0){
					confServSolicitud = new ConfServSolicitud();
					confServSolicitud.setIntParaTipoRequertoAutorizaCod(Constante.PARAM_T_TIPOREQAUT_REQUISITO);
					confServSolicitud.setIntParaTipoOperacionCod(Constante.PARAM_T_TIPOOPERACION_REFINANCIAMIENTO);
					confServSolicitud.setIntParaSubtipoOperacionCod(Constante.SUBTIPO_OPERACION_REFINANCIAMIENTO);//
					intSubTipoCredito = Constante.SUBTIPO_OPERACION_REFINANCIAMIENTO;
					intTipoCredito = Constante.PARAM_T_TIPOOPERACION_REFINANCIAMIENTO;
				}
				List<Tabla> listaTablaRequisitos = tablaFacade.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO), "A");
				listaDocAdjuntos = facade.buscarConfSolicitudRequisitoOptimizado(confServSolicitud, Constante.PARAM_T_TIPOREQAUT_REQUISITO, credito);
				
				if (listaDocAdjuntos != null && !listaDocAdjuntos.isEmpty()) {
					/*
					System.out.println("************************************ SOCIO ESTRUCTURA - ORIGEN ********************************************");
					System.out.println("SOCIO-ORIGEN-NIVEL---> "+beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
					System.out.println("SOCIO-ORIGEN-CODIGO---> "+beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
					System.out.println("SOCIO-ORIGEN-TIPO SOCIO---> "+beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio());
					System.out.println("SOCIO-ORIGEN-MODALIDAD---> "+beanSocioComp.getSocio().getSocioEstructura().getIntModalidad());
					System.out.println("***********************************************************************************************************");
					*/
					estructuraDet = new EstructuraDetalle();
					estructuraDet.setId(new EstructuraDetalleId());
					estructuraDet.getId().setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
					estructuraDet.getId().setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
					listaEstructuraDet = estructuraFacade.getListaEstructuraDetallePorEstructuraYTipoSocioYTipoModalidad(
									estructuraDet.getId(),beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio(),
									beanSocioComp.getSocio().getSocioEstructura().getIntModalidad());
					//Integer x = 0;
					for (ConfServSolicitud solicitud : listaDocAdjuntos) {
						if (solicitud.getIntParaTipoOperacionCod().compareTo(intTipoCredito)==0) {
							if (solicitud.getIntParaSubtipoOperacionCod().compareTo(intSubTipoCredito)==0) {
								if(solicitud.getListaCredito() != null && !solicitud.getListaCredito().isEmpty()){
									
									for (ConfServCredito ConfServCredito : solicitud.getListaCredito()) {
										if((ConfServCredito.getIntParaTipocreditoCod().compareTo(credito.getId().getIntParaTipoCreditoCod())==0) 
												&&(ConfServCredito.getIntSocioItemCredito().compareTo(credito.getId().getIntItemCredito())==0)){
											if (solicitud.getListaEstructuraDetalle() != null) {

												for (ConfServEstructuraDetalle estructuraDetalleReqAut : solicitud.getListaEstructuraDetalle()) {
													if (listaEstructuraDet != null && listaEstructuraDet.size() > 0) {
														
														
														for (EstructuraDetalle estructDetalleSocio : listaEstructuraDet) {
															//x++;
															if(estructDetalleSocio.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
																/*
																System.out.println("***************************** Iteracion "+ x + "Req Aut => "+solicitud.getId().getIntItemSolicitud() +"********************************");
																System.out.println("EstructDetReqAut-codigo ---> "+estructuraDetalleReqAut.getIntCodigoPk());
																System.out.println("SocioEstructOrigenSocio-codigo ---> "+beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
																System.out.println("------------------------------------------------------------------------------");
																System.out.println("EstructDetReqAut-nivel ---> "+estructuraDetalleReqAut.getIntNivelPk());
																System.out.println("SocioEstructOrigenSocio-nivel ---> "+beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
																System.out.println("------------------------------------------------------------------------------");
																System.out.println("EstructDetReqAut-caso ---> "+estructuraDetalleReqAut.getIntCaso());
																System.out.println("SocioEstructDetalleOrigenSocio-caso ---> "+estructDetalleSocio.getId().getIntCaso());
																System.out.println("------------------------------------------------------------------------------");
																System.out.println("EstructDetReqAut-itemcaso ---> "+estructuraDetalleReqAut.getIntItemCaso());
																System.out.println("SocioEstructDetalleOrigenSocio-itemcaso ---> "+estructDetalleSocio.getId().getIntItemCaso());
																System.out.println("------------------------------------------------------------------------------");
																System.out.println("RESULTADO ---> ");
																System.out.println(estructuraDetalleReqAut.getIntCodigoPk().compareTo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo())==0
																					&& estructuraDetalleReqAut.getIntNivelPk().compareTo(beanSocioComp.getSocio().getSocioEstructura().getIntNivel())==0
																					&& estructuraDetalleReqAut.getIntCaso().compareTo(estructDetalleSocio.getId().getIntCaso())==0
																					&& estructuraDetalleReqAut.getIntItemCaso().compareTo(estructDetalleSocio.getId().getIntItemCaso())==0);
																System.out.println("******************************************************************************");
																*/
																if (estructuraDetalleReqAut.getIntCodigoPk().compareTo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo())==0
																&& estructuraDetalleReqAut.getIntNivelPk().compareTo(beanSocioComp.getSocio().getSocioEstructura().getIntNivel())==0
																&& estructuraDetalleReqAut.getIntCaso().compareTo(estructDetalleSocio.getId().getIntCaso())==0
																&& estructuraDetalleReqAut.getIntItemCaso().compareTo(estructDetalleSocio.getId().getIntItemCaso())==0) {
																		
																	if (solicitud.getListaDetalle() != null	&& solicitud.getListaDetalle().size() > 0) {
																			listaRequisitoCreditoCompTemp = new ArrayList<RequisitoCreditoComp>();
																			for (ConfServDetalle detalle : solicitud.getListaDetalle()) {
																				if (detalle.getId().getIntPersEmpresaPk().equals(estructuraDetalleReqAut.getId().getIntPersEmpresaPk())
																					&& detalle.getId().getIntItemSolicitud().equals(estructuraDetalleReqAut.getId().getIntItemSolicitud())
																						){
																					requisitoCreditoComp = new RequisitoCreditoComp();
																					requisitoCreditoComp.setDetalle(detalle);
																					listaRequisitoCreditoCompTemp.add(requisitoCreditoComp);
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
							}
						}
					}
					//jchavez 24.06.2014 se agrega agrupamiento A
					listaRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
					if (listaRequisitoCreditoCompTemp!=null && !listaRequisitoCreditoCompTemp.isEmpty()) {
						if (listaTablaRequisitos!=null && !listaTablaRequisitos.isEmpty()) {
							
							for (RequisitoCreditoComp reqAutEstDetalle : listaRequisitoCreditoCompTemp) {
								for (Tabla tabla : listaTablaRequisitos) {
									if (tabla.getIntIdDetalle().equals(reqAutEstDetalle.getDetalle().getIntParaTipoDescripcion()) && tabla.getStrIdAgrupamientoA().equalsIgnoreCase("A")) {
										requisitoCreditoComp = new RequisitoCreditoComp();
										requisitoCreditoComp.setDetalle(reqAutEstDetalle.getDetalle());
										listaRequisitoCreditoComp.add(requisitoCreditoComp);
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
			return listaRequisitoCreditoComp;
	}
	
	/**
	 * modificado 20.03.2014 - jchavez
	 * Recupera modelo contable de refinancimaiento y genera asiento contable.
	 * @param expedienteCredito
	 * @param usuario
	 * @return 
	 * @throws BusinessException
	 */
	public String generarProcesosAutorizacionRefinanciamiento(ExpedienteCredito expedienteCreditoRef, ExpedienteCredito expedienteCreditoAut, Usuario usuario, SocioComp beanSocioComp)  throws BusinessException{

		BigDecimal bdInteresRecalculado = null;
		LibroDiario libroDiario = null;
		ModeloFacadeRemote	modeloFacade  = null;
		ConceptoFacadeRemote conceptoFacace = null;
		LibroDiarioFacadeRemote libroDiarioFacade = null;
		PrestamoFacadeLocal prestamoFacade = null;
		ExpedienteId expedienteId = null;
		List<LibroDiarioDetalle> lstDiarioDetalle = null;
		List<ModeloDetalle> listaModDet = null;
		
		Modelo modeloContable = null;
		CarteraCreditoDetalle carteraCredDet= null;
		CreditoFacadeRemote creditoFacade= null;
		Credito confCreditoViejo = null;
		Credito confCreditoNuevo = null;
		Expediente expedienteCreditoViejoMovimiento= null;
		ExpedienteCredito expCredViejo = null;
		String mensaje = "";
		PlanCuentaFacadeRemote planCtaFacade=null;
		
		InteresCancelado ultimoInteresCancelado = null;
		Calendar fecHoy = Calendar.getInstance();
		Date dtHoy = fecHoy.getTime();
		Integer intNumeroDias = 0;
		EstadoCredito estadoCredito = null;
		CuentaFacadeRemote cuentaFacade = null;
		CuentaIntegrante ctaInt = new CuentaIntegrante();
		try {
			modeloFacade  = (ModeloFacadeRemote)EJBFactory.getRemote(ModeloFacadeRemote.class);
			libroDiarioFacade= (LibroDiarioFacadeRemote)EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			conceptoFacace = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			creditoFacade = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
			prestamoFacade = (PrestamoFacadeLocal)EJBFactory.getLocal(PrestamoFacadeLocal.class);
			cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			
			planCtaFacade= (PlanCuentaFacadeRemote)EJBFactory.getRemote(PlanCuentaFacadeRemote.class);
			Integer intPeriodoCuenta = obtieneAnio(new Date());
			Integer anio =  obtieneAnio(new Date());
			String  mes  = obtieneMesCadena(new Date());
			
			lstDiarioDetalle = new ArrayList<LibroDiarioDetalle>();
			//Recuperamos el modelo para refinanciamiento
			//PARAM_T_TIPOMODELOCONTABLE_REFINANCIAMIENTO = 410
			//Se toma el 1er registro dado que la busqueda obtenerTipoModeloActual solo devuelve uno..
			modeloContable = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_REFINANCIAMIENTO, expedienteCreditoRef.getId().getIntPersEmpresaPk()).get(0);
			
			if(modeloContable != null){
				//listaModDet =  modeloFacade.getListaModeloDetallePorModeloId(modeloContable.getId());
				listaModDet = modeloContable.getListModeloDetalle();
				if(listaModDet != null && !listaModDet.isEmpty()){
					//Inicializando LibroDiario()...
					libroDiario = new LibroDiario();
					libroDiario.setId(new LibroDiarioId());
					libroDiario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
					
					libroDiario.getId().setIntContPeriodoLibro(new Integer(anio+""+mes));
					libroDiario.getId().setIntPersEmpresaLibro(expedienteCreditoRef.getId().getIntPersEmpresaPk());
					libroDiario.setStrGlosa("Asiento de Autorizacion de Refinanciamiento. ");
					libroDiario.setTsFechaRegistro(new Timestamp((new Date()).getTime())); //la fecha actual
					libroDiario.setTsFechaDocumento(new Timestamp((new Date()).getTime()));
					//MODIFICAION 13.03.2014
					libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
					libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					libroDiario.setIntPersEmpresaUsuario(usuario.getEmpresa().getIntIdEmpresa());
					libroDiario.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
					
					//----------------------------------------------------------------------------------------------
					// Recuperamos Datos del credito viejo (crédito que se esta refinanciando)
					// Riesgo - tipo credito empresa
					expedienteId = new ExpedienteId();
					expedienteId.setIntCuentaPk(expedienteCreditoRef.getIntCuentaRefPk());
					expedienteId.setIntItemExpediente(expedienteCreditoRef.getIntItemExpedienteRef());
					expedienteId.setIntItemExpedienteDetalle(expedienteCreditoRef.getIntItemDetExpedienteRef());
					expedienteId.setIntPersEmpresaPk(expedienteCreditoRef.getIntPersEmpresaRefPk());
					//Se recupera el tipo de riesgo.
					carteraCredDet = recuperarTipoRiesgo(expedienteId);
					/*
					 * Caso: Si se da que un refinanciamiento está siendo dado el mismo mes en el que se dio el prestamo,
					 * y si no existe registro de cartera para este préstamo, se tomará como modelo el tipo de riesgo vigente(1) 
					 */					 
					if(carteraCredDet == null){
						carteraCredDet = new CarteraCreditoDetalle();
						carteraCredDet.setIntParaTipocategoriariesgo(Constante.PARAM_T_TIPOCATEGORIADERIESGO_NORMAL);
					}
					log.info("Retorno de Cartera Credito Detalle: "+carteraCredDet);
										
					//Recupera el expediente credito de movimiento del credito que se está refinanciando...
					expedienteCreditoViejoMovimiento = conceptoFacace.getExpedientePorPK(expedienteId);
					//Recupera el expediente credito de servicio del credito que se está refinanciando...
					ExpedienteCreditoId expCredViejoId = new ExpedienteCreditoId();
					expCredViejoId.setIntCuentaPk(expedienteCreditoViejoMovimiento.getId().getIntCuentaPk());
					expCredViejoId.setIntItemDetExpediente(expedienteCreditoViejoMovimiento.getId().getIntItemExpedienteDetalle());
					expCredViejoId.setIntItemExpediente(expedienteCreditoViejoMovimiento.getId().getIntItemExpediente());
					expCredViejoId.setIntPersEmpresaPk(expedienteCreditoViejoMovimiento.getId().getIntPersEmpresaPk());
					expCredViejo= prestamoFacade.getExpedienteCreditoPorId(expCredViejoId);

					//Recuperando el tipo credito empresa del credito que se está refinanciando...
					CreditoId creditoId= new CreditoId();
					creditoId.setIntItemCredito(expCredViejo.getIntItemCredito());
					creditoId.setIntParaTipoCreditoCod(expCredViejo.getIntParaTipoCreditoCod());
					creditoId.setIntPersEmpresaPk(expCredViejo.getIntPersEmpresaCreditoPk());
					confCreditoViejo = creditoFacade.getCreditoPorIdCreditoDirecto(creditoId);
					
					//Recuperando el tipo credito empresa del credito a autorizar...
					CreditoId creditoId2= new CreditoId();
					creditoId2.setIntItemCredito(expedienteCreditoRef.getIntItemCredito());
					creditoId2.setIntParaTipoCreditoCod(expedienteCreditoRef.getIntParaTipoCreditoCod());
					creditoId2.setIntPersEmpresaPk(expedienteCreditoRef.getIntPersEmpresaCreditoPk());
					confCreditoNuevo = creditoFacade.getCreditoPorIdCreditoDirecto(creditoId2);

					//Recalculando el interés atrasado del exp. crédito original...
					Calendar calendar = Calendar.getInstance();
					Timestamp tsFechaInicial = null;
					ultimoInteresCancelado = conceptoFacace.getMaxInteresCanceladoPorExpediente(expedienteId);
					if (ultimoInteresCancelado!=null) {
						//Cálculo de dias del interes atrasado...
						
				        calendar.setTimeInMillis(ultimoInteresCancelado.getTsFechaMovimiento().getTime());
				        calendar.add(Calendar.DATE, 1);
				        tsFechaInicial = new Timestamp(calendar.getTimeInMillis());
					}else{
						if (confCreditoViejo.getIntParaTipoCreditoEmpresa().equals(Constante.PARAM_T_TIPOCREDITOEMPRESA_REFINANCIAMIENTO_ORDINARIO)
								|| confCreditoViejo.getIntParaTipoCreditoEmpresa().equals(Constante.PARAM_T_TIPOCREDITOEMPRESA_REFINANCIAMIENTO_CUBIERTO)
								|| confCreditoViejo.getIntParaTipoCreditoEmpresa().equals(Constante.PARAM_T_TIPOCREDITOEMPRESA_REFINANCIAMIENTO_PROMOCIONAL)
								|| confCreditoViejo.getIntParaTipoCreditoEmpresa().equals(Constante.PARAM_T_TIPOCREDITOEMPRESA_REFINANCIAMIENTO_ESPECIAL)) {
							estadoCredito = boEstadoCredito.getMaxEstadoCreditoPorPokExpedienteCredito(expCredViejo.getId());
							if (estadoCredito.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)) {
								calendar.setTimeInMillis(estadoCredito.getTsFechaEstado().getTime());
								calendar.add(Calendar.DATE, 1);
								tsFechaInicial = new Timestamp(calendar.getTimeInMillis());
							}
						}else{
							estadoCredito = boEstadoCredito.getMaxEstadoCreditoPorPokExpedienteCredito(expCredViejo.getId());
							if (estadoCredito.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO)) {
								calendar.setTimeInMillis(estadoCredito.getTsFechaEstado().getTime());
								calendar.add(Calendar.DATE, 1);
								tsFechaInicial = new Timestamp(calendar.getTimeInMillis());
							}
						}
					}
					intNumeroDias = obtenerDiasEntreFechas(convertirTimestampToDate(tsFechaInicial), dtHoy);
					log.info("Número de días de interes atrasado: "+intNumeroDias);
					
					bdInteresRecalculado = (new BigDecimal(intNumeroDias)).multiply(expedienteCreditoViejoMovimiento.getBdPorcentajeInteres()).multiply(expedienteCreditoViejoMovimiento.getBdSaldoCredito()).divide(new BigDecimal(3000), 2,RoundingMode.HALF_UP);
					expedienteCreditoRef.setBdMontoInteresAtrasado(bdInteresRecalculado);
					expedienteCreditoRef.setBdMontoTotal(expedienteCreditoRef.getBdMontoSolicitado().add(bdInteresRecalculado));
					//modificamos el expediente con el nuevo interes calculado
					expedienteCreditoRef = boExpedienteCredito.modificar(expedienteCreditoRef);
					
					// Usamos el modelo para generar y grabar el asiento contable
					for (ModeloDetalle modeloDetalle : listaModDet) {
						//JCHAVEZ 05.03.2014 ---> En teoría el plan de cta ya viene cargado en la búsqueda del modelo
						//CGD-26.12.2013
						PlanCuenta planCta = null;
						String strObservacionPlanCta = "";

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
						//FIN CGD-26.12.2013 

						//Armando el Asiento Contable en base al modelo obtenido
						if (modeloDetalle.getId().getIntContPeriodoCuenta().equals(intPeriodoCuenta)){
							LibroDiarioDetalle diarioDet = new LibroDiarioDetalle();
							diarioDet.setId(new LibroDiarioDetalleId());
							
							List<ModeloDetalleNivel> listaModDetNiv = null;
							//JCHAVEZ 05.03.2014 ---> En teoria al obtener el modelo, ya traigo del modelo detalle y modelo detalle nivel
//								listaModDetNiv = modeloFacade.getListaModeloDetNivelPorModeloDetalleId(modeloDetalle.getId());
							listaModDetNiv = modeloDetalle.getListModeloDetalleNivel();
							//MODELO DETALLE NIVEL
							//1. Si el credito anterior es un refinanciado...

							//2. Si el credito anterior es uno normal...
							Boolean blnTipoConceptoGral = false;
							Boolean blnTipoCategoriaRiesgo = false;
							Boolean blnTipoCreditoEmpresa = false;
							Boolean blnTieneRiesgo = false;
							BigDecimal bdMontoDebeHaber = BigDecimal.ZERO;

							/* Debe de cumplir con 3 condiciones, que el tipo concepto general sea amortizacion o interés,
							   y que el tipo categoria riesgo y tipo credito empresa sean los del expediente */
							if(listaModDetNiv != null && !listaModDetNiv.isEmpty()){
								for (ModeloDetalleNivel modeloDetalleNivel : listaModDetNiv) {
									if (modeloDetalleNivel.getIntParaTipoRegistro().compareTo(Constante.PARAM_T_MODELO_TIPO_REGISTRO_TABLA)==0) {
										if ((modeloDetalleNivel.getIntDatoTablas().equals(Constante.PARAM_T_DATOTABLA_CATEGORIARIESGO))) {
											blnTieneRiesgo = true;
											break;
										}
									}
								}
								if (!blnTieneRiesgo) {
									for (ModeloDetalleNivel modeloDetalleNivel : listaModDetNiv) {
										if(modeloDetalleNivel.getIntParaTipoRegistro().compareTo(Constante.PARAM_T_MODELO_TIPO_REGISTRO_TABLA)==0){
											if ((modeloDetalleNivel.getIntDatoTablas().equals(Integer.parseInt(Constante.PARAM_T_TIPOCONCEPTOGENERAL)))
													&& (modeloDetalleNivel.getIntDatoArgumento().equals(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION))){
												blnTipoConceptoGral = true;
												break;
											}
										}
									}
									if (blnTipoConceptoGral) {
										for (ModeloDetalleNivel modeloDetalleNivel : listaModDetNiv) {
											if(modeloDetalleNivel.getIntParaTipoRegistro().compareTo(Constante.PARAM_T_MODELO_TIPO_REGISTRO_TABLA)==0){
												if ((modeloDetalleNivel.getIntDatoTablas().equals(Constante.PARAM_T_DATOTABLA_TIPOCREDITOEMPRESA))
														&& (modeloDetalleNivel.getIntDatoArgumento().equals(confCreditoNuevo.getIntParaTipoCreditoEmpresa()))) {
													blnTipoCreditoEmpresa = true;
													break;
												}
											}
										}
										if (blnTipoCreditoEmpresa) {
											if (confCreditoViejo.getIntParaTipoCreditoEmpresa().equals(Constante.PARAM_T_TIPOCREDITOEMPRESA_REFINANCIAMIENTO_ORDINARIO)
													|| confCreditoViejo.getIntParaTipoCreditoEmpresa().equals(Constante.PARAM_T_TIPOCREDITOEMPRESA_REFINANCIAMIENTO_CUBIERTO)
													|| confCreditoViejo.getIntParaTipoCreditoEmpresa().equals(Constante.PARAM_T_TIPOCREDITOEMPRESA_REFINANCIAMIENTO_PROMOCIONAL)
													|| confCreditoViejo.getIntParaTipoCreditoEmpresa().equals(Constante.PARAM_T_TIPOCREDITOEMPRESA_REFINANCIAMIENTO_ESPECIAL)) {
												//Asignando los valores provisionales para la generacion del asiento
//												modeloDetalle.setIntParaOpcionDebeHaber(Constante.PARAM_T_OPCIONDEBEHABER_HABER);
//												for (ModeloDetalleNivel modeloDetalleNivel : listaModDetNiv) {
//													if (modeloDetalleNivel.getIntParaTipoRegistro().equals(Constante.PARAM_T_MODELO_TIPO_REGISTRO_FIJO)) {
//														modeloDetalleNivel.setStrDescripcion(Constante.PARAM_T_MODELO_REFINANCIAMIENTO_MONTO_SOLICITADO);
//													}													
//												}												

												bdMontoDebeHaber = bdMontoDebeHaber.add(expedienteCreditoRef.getBdMontoSolicitado());
												
												if (bdMontoDebeHaber.compareTo(BigDecimal.ZERO)==1) {
													diarioDet.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
													diarioDet.setIntContPeriodo(modeloDetalle.getId().getIntContPeriodoCuenta());
													diarioDet.setIntPersEmpresaCuenta(modeloDetalle.getId().getIntPersEmpresaCuenta());
													//
													CuentaId cId=new CuentaId();
													cId.setIntPersEmpresaPk(Constante.PARAM_EMPRESASESION);
													cId.setIntCuenta(expedienteCreditoRef.getId().getIntCuentaPk());
													ctaInt = cuentaFacade.getListaCuentaIntegrantePorPKCuenta(cId).get(0);
													//MOD 13.03.2014
													diarioDet.setIntPersPersona(ctaInt.getId().getIntPersonaIntegrante());
													//
													diarioDet.setIntPersEmpresaSucursal(Constante.PARAM_EMPRESASESION);
													diarioDet.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
													diarioDet.setIntSudeIdSubSucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
													diarioDet.setStrContNumeroCuenta(modeloDetalle.getId().getStrContNumeroCuenta());								
													diarioDet.setStrComentario(strObservacionPlanCta);
													
													diarioDet.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
													diarioDet.setStrNumeroDocumento(expCredViejo.getId().getIntItemExpediente()+"-"+expCredViejo.getId().getIntItemDetExpediente());
													
													diarioDet.setIntParaDocumentoGeneral(modeloDetalle.getIntParaDocumentoGral());
													diarioDet.setBdHaberSoles(bdMontoDebeHaber);
													diarioDet.setBdDebeSoles(null);

													lstDiarioDetalle.add(diarioDet);
												}
											}
											bdMontoDebeHaber = BigDecimal.ZERO;
											//Se reinicia el objeto.
											diarioDet = new LibroDiarioDetalle();
											diarioDet.setId(new LibroDiarioDetalleId());
											for (ModeloDetalleNivel modeloDetalleNivel : listaModDetNiv) {
												if (modeloDetalleNivel.getIntParaTipoRegistro().equals(Constante.PARAM_T_MODELO_TIPO_REGISTRO_FIJO)) {
													if (modeloDetalleNivel.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_REFINANCIAMIENTO_MONTOTOTAL)) {
														bdMontoDebeHaber = bdMontoDebeHaber.add(expedienteCreditoRef.getBdMontoTotal());
													}
													
													if (bdMontoDebeHaber.compareTo(BigDecimal.ZERO)==1) {
														diarioDet.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
														diarioDet.setIntContPeriodo(modeloDetalle.getId().getIntContPeriodoCuenta());
														diarioDet.setIntPersEmpresaCuenta(modeloDetalle.getId().getIntPersEmpresaCuenta());
														//
														CuentaId cId=new CuentaId();
														cId.setIntPersEmpresaPk(Constante.PARAM_EMPRESASESION);
														cId.setIntCuenta(expedienteCreditoRef.getId().getIntCuentaPk());
														ctaInt = cuentaFacade.getListaCuentaIntegrantePorPKCuenta(cId).get(0);
														//MOD 13.03.2014
														diarioDet.setIntPersPersona(ctaInt.getId().getIntPersonaIntegrante());
														diarioDet.setIntPersEmpresaSucursal(Constante.PARAM_EMPRESASESION);
														diarioDet.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
														diarioDet.setIntSudeIdSubSucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
														diarioDet.setStrContNumeroCuenta(modeloDetalle.getId().getStrContNumeroCuenta());								
														diarioDet.setStrComentario(strObservacionPlanCta);
														//
														diarioDet.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
														diarioDet.setStrNumeroDocumento(expedienteCreditoRef.getId().getIntItemExpediente()+"-"+expedienteCreditoRef.getId().getIntItemDetExpediente());
														if(modeloDetalle.getIntParaOpcionDebeHaber().compareTo(Constante.PARAM_T_LIBRO_DIARIO_DEBE)==0){
															diarioDet.setIntParaDocumentoGeneral(modeloDetalle.getIntParaDocumentoGral());
															diarioDet.setBdDebeSoles(bdMontoDebeHaber);
															diarioDet.setBdHaberSoles(null);
														}else if(modeloDetalle.getIntParaOpcionDebeHaber().compareTo(Constante.PARAM_T_LIBRO_DIARIO_HABER)==0){
															diarioDet.setIntParaDocumentoGeneral(modeloDetalle.getIntParaDocumentoGral());
															diarioDet.setBdHaberSoles(bdMontoDebeHaber);
															diarioDet.setBdDebeSoles(null);
														}
														lstDiarioDetalle.add(diarioDet);
													}
												}
											}
										}
									}											
								}
								//--------------------------------------------*--------------------------------------------
								if (blnTieneRiesgo) {
									for (ModeloDetalleNivel modeloDetalleNivel : listaModDetNiv) {
										if(modeloDetalleNivel.getIntParaTipoRegistro().equals(Constante.PARAM_T_MODELO_TIPO_REGISTRO_TABLA)){
											if (modeloDetalleNivel.getIntDatoTablas().equals(Integer.parseInt(Constante.PARAM_T_TIPOCONCEPTOGENERAL))
													&& (modeloDetalleNivel.getIntDatoArgumento().equals(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION) 
													|| modeloDetalleNivel.getIntDatoArgumento().equals(Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES))) {
												blnTipoConceptoGral = true;
												break;
											}
										}
									}
									if (blnTipoConceptoGral) {
										for (ModeloDetalleNivel modeloDetalleNivel : listaModDetNiv) {
											if(modeloDetalleNivel.getIntParaTipoRegistro().compareTo(Constante.PARAM_T_MODELO_TIPO_REGISTRO_TABLA)==0){
												if ((modeloDetalleNivel.getIntDatoTablas().equals(Constante.PARAM_T_DATOTABLA_CATEGORIARIESGO))
														&& (modeloDetalleNivel.getIntDatoArgumento().equals(carteraCredDet.getIntParaTipocategoriariesgo()!=null?carteraCredDet.getIntParaTipocategoriariesgo():Constante.PARAM_T_TIPOCATEGORIADERIESGO_NORMAL))) {
													blnTipoCategoriaRiesgo = true;
													break;
												}
											}												
										}
										if (blnTipoCategoriaRiesgo) {
											for (ModeloDetalleNivel modeloDetalleNivel : listaModDetNiv) {
												if(modeloDetalleNivel.getIntParaTipoRegistro().compareTo(Constante.PARAM_T_MODELO_TIPO_REGISTRO_TABLA)==0){
													if ((modeloDetalleNivel.getIntDatoTablas().equals(Constante.PARAM_T_DATOTABLA_TIPOCREDITOEMPRESA))
															&& (modeloDetalleNivel.getIntDatoArgumento().equals(confCreditoViejo.getIntParaTipoCreditoEmpresa()))) {
														blnTipoCreditoEmpresa = true;
														break;
													}
												}
											}
											if (blnTipoCreditoEmpresa) {
												for (ModeloDetalleNivel modeloDetalleNivel : listaModDetNiv) {
													if (modeloDetalleNivel.getIntParaTipoRegistro().equals(Constante.PARAM_T_MODELO_TIPO_REGISTRO_FIJO)) {
														if (modeloDetalleNivel.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_REFINANCIAMIENTO_MONTOSOLICITADO)) {
															bdMontoDebeHaber = bdMontoDebeHaber.add(expedienteCreditoRef.getBdMontoSolicitado());
														}
														if (modeloDetalleNivel.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELO_REFINANCIAMIENTO_MONTOINTERESATRSADO)) {
															bdMontoDebeHaber = bdMontoDebeHaber.add(expedienteCreditoRef.getBdMontoInteresAtrasado());
														}
														
														if (bdMontoDebeHaber.compareTo(BigDecimal.ZERO)==1) {
															diarioDet.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
															diarioDet.setIntContPeriodo(modeloDetalle.getId().getIntContPeriodoCuenta());
															diarioDet.setIntPersEmpresaCuenta(modeloDetalle.getId().getIntPersEmpresaCuenta());
															//
															CuentaId cId=new CuentaId();
															cId.setIntPersEmpresaPk(Constante.PARAM_EMPRESASESION);
															cId.setIntCuenta(expedienteCreditoRef.getId().getIntCuentaPk());
															ctaInt = cuentaFacade.getListaCuentaIntegrantePorPKCuenta(cId).get(0);
															//MOD 13.03.2014
															diarioDet.setIntPersPersona(ctaInt.getId().getIntPersonaIntegrante());
															//
															diarioDet.setIntPersEmpresaSucursal(Constante.PARAM_EMPRESASESION);
															diarioDet.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
															diarioDet.setIntSudeIdSubSucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
															diarioDet.setStrContNumeroCuenta(modeloDetalle.getId().getStrContNumeroCuenta());								
															diarioDet.setStrComentario(strObservacionPlanCta);
															//
															diarioDet.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
															diarioDet.setStrNumeroDocumento(expCredViejo.getId().getIntItemExpediente()+"-"+expCredViejo.getId().getIntItemDetExpediente());
															if(modeloDetalle.getIntParaOpcionDebeHaber().compareTo(Constante.PARAM_T_LIBRO_DIARIO_DEBE)==0){
																diarioDet.setIntParaDocumentoGeneral(modeloDetalle.getIntParaDocumentoGral());
																diarioDet.setBdDebeSoles(bdMontoDebeHaber);
																diarioDet.setBdHaberSoles(null);
															}else if(modeloDetalle.getIntParaOpcionDebeHaber().compareTo(Constante.PARAM_T_LIBRO_DIARIO_HABER)==0){
																diarioDet.setIntParaDocumentoGeneral(modeloDetalle.getIntParaDocumentoGral());
																diarioDet.setBdHaberSoles(bdMontoDebeHaber);
																diarioDet.setBdDebeSoles(null);
															}
															lstDiarioDetalle.add(diarioDet);
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
						
						libroDiario.setListaLibroDiarioDetalle(lstDiarioDetalle);
						// grabamos el libro diario generado:
						libroDiario = libroDiarioFacade.grabarLibroDiario(libroDiario);
						if(libroDiario.getId() != null){ // expedienteCreditoAut
							mensaje = generarMovimientosRefinanciamiento(expedienteCreditoRef, expedienteCreditoViejoMovimiento, usuario, beanSocioComp, expedienteCreditoAut);
							
							grabarAutorizacionPrestamo(expedienteCreditoAut);
							cambioEstadoActividad(expedienteCreditoAut, usuario,libroDiario);
							//Nuevo estado a grabar en el refinanciamiento
							cambioEstadoRefinanciamientoAGirado(expedienteCreditoAut, usuario,libroDiario);
						}else{
							mensaje = "No se pudo generar Libro Diario. Se cancela el proceso. ";
							throw new BusinessException("No se pudo generar Libro Diario. Se cancela el proceso. ");
						}
//					}else{
//						mensaje = "No se encontró tipo de riesgo para perido Actual y previo. Se cancela el proceso. ";
//						throw new BusinessException("No se encontró tipo de riesgo para perido Actual y previo. Se cancela el proceso. ");
//					}
				}
			}else{
				mensaje = "No se encontró Modelo Contable. Se cancela el proceso. ";
				throw new BusinessException("No se encontró Modelo Contable. Se cancela el proceso. ");
			}
		} catch (Exception e) {
			log.error("Error en generarProcesosAutorizacionRefinanciamiento ---> "+e);
		}
		return mensaje;
	}
	
	/**
	 * Recupera el credito moroso mediante el expediente delcredito refinanciado.
	 * @return
	 */
	public ExpedienteCredito recuperarCreditoViejo (ExpedienteCredito expCreditoRef){
		ExpedienteCredito expCreditoViejo = null;
		try {
			if(expCreditoRef != null){
				
				
				
				
				
			}
			
			
		} catch (Exception e) {
		}
		return expCreditoViejo;
	}
	
	
	/**
	 * Genera los registros de Movimiento para la autorizacion de refinanciamiento.
	 * @param expedienteCredito
	 * @param diario
	 * @return
	 * @throws Exception
	 */
	public String generarMovimientosRefinanciamiento(ExpedienteCredito expedienteCreditoRef, Expediente expedienteCreditoViejoMovimiento, Usuario usuario, SocioComp beanSocioComp, ExpedienteCredito expedienteCreditoAut) throws Exception{
		CuentaFacadeRemote cuentaFacade = null;
		ConceptoFacadeRemote conceptoFacade =null;
		SolicitudPrestamoFacadeLocal solicitudPrestamoFacade = null;
		Date dtHoy = null;
		String mensaje = "";
		EstructuraDetalle estructuraDetalle= null;
//		EstructuraFacadeRemote estructuraFacade = null;
		try {
			cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			solicitudPrestamoFacade= (SolicitudPrestamoFacadeLocal)EJBFactory.getLocal(SolicitudPrestamoFacadeLocal.class);
			conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
//			estructuraFacade = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
			
			Calendar fecHoy = Calendar.getInstance();
			dtHoy = fecHoy.getTime();
			
			InteresCancelado ultimoInteresCancelado = null;
			Movimiento ultimoMovimientoInteres = null;
			ExpedienteId expId = new ExpedienteId();
			expId.setIntCuentaPk(expedienteCreditoViejoMovimiento.getId().getIntCuentaPk());
			expId.setIntItemExpediente(expedienteCreditoViejoMovimiento.getId().getIntItemExpediente());
			expId.setIntItemExpedienteDetalle(expedienteCreditoViejoMovimiento.getId().getIntItemExpedienteDetalle());
			expId.setIntPersEmpresaPk(expedienteCreditoViejoMovimiento.getId().getIntPersEmpresaPk());
			
			ultimoInteresCancelado= conceptoFacade.getMaxInteresCanceladoPorExpediente(expId);
			ultimoMovimientoInteres = conceptoFacade.getMaxXExpYCtoGral( expedienteCreditoViejoMovimiento.getId().getIntPersEmpresaPk(), 
																		expedienteCreditoViejoMovimiento.getId().getIntCuentaPk(), 
																		expedienteCreditoViejoMovimiento.getId().getIntItemExpediente(),  
																		expedienteCreditoViejoMovimiento.getId().getIntItemExpedienteDetalle(), 
																		Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES);
			
		// 1. MOVIMIENTO: crear prestamo viejo - saldo cero.
			Integer intPersonaIntegrante = 0;
			CuentaId ctaId = new CuentaId();
			List<CuentaIntegrante> lstCuentaIntegrante = null;
			ctaId.setIntCuenta(expedienteCreditoViejoMovimiento.getId().getIntCuentaPk());
			ctaId.setIntPersEmpresaPk(Constante.PARAM_EMPRESASESION);
			
			lstCuentaIntegrante= cuentaFacade.getListaCuentaIntegrantePorCuenta(ctaId);
			if(lstCuentaIntegrante != null && !lstCuentaIntegrante.isEmpty()){
				for (CuentaIntegrante cuentaIntegrante : lstCuentaIntegrante) {
					if(cuentaIntegrante.getIntParaTipoIntegranteCod().compareTo(Constante.TIPOINTEGRANTE_ADMINISTRADOR)==0){
						intPersonaIntegrante = cuentaIntegrante.getId().getIntPersonaIntegrante();
					}
				}
			}
			//movimiento del prestamo que se esta refinanciando
			Movimiento movPrestAnterior = new Movimiento();
			movPrestAnterior.setTsFechaMovimiento(obtieneFechaActualEnTimesTamp());
			movPrestAnterior.setIntPersEmpresa(expedienteCreditoViejoMovimiento.getId().getIntPersEmpresaPk());
			movPrestAnterior.setIntCuenta(expedienteCreditoViejoMovimiento.getId().getIntCuentaPk());
			movPrestAnterior.setIntPersPersonaIntegrante(intPersonaIntegrante);
			movPrestAnterior.setIntItemExpediente(expedienteCreditoViejoMovimiento.getId().getIntItemExpediente());
			movPrestAnterior.setIntItemExpedienteDetalle(expedienteCreditoViejoMovimiento.getId().getIntItemExpedienteDetalle());
			movPrestAnterior.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION);
			movPrestAnterior.setIntParaTipoMovimiento(Constante.PARAM_T_TIPO_MOVIMIENTO_TRANSFERENCIA);
			movPrestAnterior.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_TX_ENTERCONCEPTOS);
			movPrestAnterior.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
			movPrestAnterior.setBdMontoMovimiento(expedienteCreditoViejoMovimiento.getBdSaldoCredito());
			movPrestAnterior.setBdMontoSaldo(BigDecimal.ZERO);
			movPrestAnterior.setIntPersEmpresaUsuario(Constante.PARAM_EMPRESASESION);
			movPrestAnterior.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			//21.03.2014 SE ADICIONA NUMERO DE DOCUMENTO.
			movPrestAnterior.setStrNumeroDocumento(""+expedienteCreditoViejoMovimiento.getId().getIntItemExpediente()+"-"+expedienteCreditoViejoMovimiento.getId().getIntItemExpedienteDetalle());
			movPrestAnterior = conceptoFacade.grabarMovimiento(movPrestAnterior);
			
			if(movPrestAnterior.getIntItemMovimiento() != null){
				//2. MOVIMIENTO: crear interes viejo. saldo: x-calculo. xxx.
				BigDecimal bdMontoRegistro = BigDecimal.ZERO;
				if(ultimoMovimientoInteres != null){
					bdMontoRegistro = 	(ultimoMovimientoInteres.getBdMontoSaldo()!=null?ultimoMovimientoInteres.getBdMontoSaldo():BigDecimal.ZERO)
											.subtract(expedienteCreditoRef.getBdMontoInteresAtrasado()!=null?expedienteCreditoRef.getBdMontoInteresAtrasado():BigDecimal.ZERO);
				}else{
					bdMontoRegistro = 	(expedienteCreditoViejoMovimiento.getBdSaldoInteres()!=null?expedienteCreditoViejoMovimiento.getBdSaldoInteres():BigDecimal.ZERO)
											.subtract(expedienteCreditoRef.getBdMontoInteresAtrasado()!=null?expedienteCreditoRef.getBdMontoInteresAtrasado():BigDecimal.ZERO);
				}
				
				Movimiento movPrestAnteriorInteres = new Movimiento();
				movPrestAnteriorInteres.setTsFechaMovimiento(obtieneFechaActualEnTimesTamp());
				movPrestAnteriorInteres.setIntPersEmpresa(expedienteCreditoViejoMovimiento.getId().getIntPersEmpresaPk());
				movPrestAnteriorInteres.setIntCuenta(expedienteCreditoViejoMovimiento.getId().getIntCuentaPk());
				movPrestAnteriorInteres.setIntPersPersonaIntegrante(intPersonaIntegrante);
				movPrestAnteriorInteres.setIntItemExpediente(expedienteCreditoViejoMovimiento.getId().getIntItemExpediente());
				movPrestAnteriorInteres.setIntItemExpedienteDetalle(expedienteCreditoViejoMovimiento.getId().getIntItemExpedienteDetalle());
				movPrestAnteriorInteres.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES);
				movPrestAnteriorInteres.setIntParaTipoMovimiento(Constante.PARAM_T_TIPO_MOVIMIENTO_TRANSFERENCIA);
				movPrestAnteriorInteres.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_TX_ENTERCONCEPTOS);
				movPrestAnteriorInteres.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_ABONO);
				movPrestAnteriorInteres.setBdMontoMovimiento(expedienteCreditoRef.getBdMontoInteresAtrasado());
				movPrestAnteriorInteres.setBdMontoSaldo(bdMontoRegistro);
				movPrestAnteriorInteres.setIntPersEmpresaUsuario(Constante.PARAM_EMPRESASESION);
				movPrestAnteriorInteres.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
				//21.03.2014 SE ADICIONA NUMERO DE DOCUMENTO.
				movPrestAnteriorInteres.setStrNumeroDocumento(""+expedienteCreditoViejoMovimiento.getId().getIntItemExpediente()+"-"+expedienteCreditoViejoMovimiento.getId().getIntItemExpedienteDetalle());
				movPrestAnteriorInteres= conceptoFacade.grabarMovimiento(movPrestAnteriorInteres);
				
				
				if(movPrestAnteriorInteres.getIntItemMovimiento() != null){
					//3. INTERES CANCELADO - crear antiguo.
					Timestamp tsFechaInicial = new Timestamp(new Date().getTime());
					//Date dtFechaMovimiento = new Date();
					//Timestamp tsFechaMovimiento = new Timestamp(new Date().getTime());
					Integer intNumeroDias = 0;

					if(ultimoInteresCancelado != null){
						// si existe se le suma un dia...
					        Calendar calendar = Calendar.getInstance();
					        calendar.setTimeInMillis(ultimoInteresCancelado.getTsFechaMovimiento().getTime());
					        calendar.add(Calendar.DATE, 1);
					        tsFechaInicial = new Timestamp(calendar.getTimeInMillis());		
					        
					        // calculo de nro dias
							intNumeroDias = obtenerDiasEntreFechas(convertirTimestampToDate(tsFechaInicial), dtHoy);   
					}else{
						List<EstadoCredito> lstEstados = null;
						ExpedienteCreditoId expCredId = new ExpedienteCreditoId();
						expCredId.setIntCuentaPk(expedienteCreditoViejoMovimiento.getId().getIntCuentaPk());
						expCredId.setIntItemDetExpediente(expedienteCreditoViejoMovimiento.getId().getIntItemExpedienteDetalle());
						expCredId.setIntItemExpediente(expedienteCreditoViejoMovimiento.getId().getIntItemExpediente());
						expCredId.setIntPersEmpresaPk(expedienteCreditoViejoMovimiento.getId().getIntPersEmpresaPk());
						
						lstEstados= solicitudPrestamoFacade.getListaEstadosPorExpedienteCreditoId(expCredId);
						if(lstEstados != null && !lstEstados.isEmpty()){
							for (EstadoCredito estadoCredito : lstEstados) {
								if(estadoCredito.getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO)==0){
									tsFechaInicial = estadoCredito.getTsFechaEstado();
									break;
								}
							}
						}
						// calculo de dias
						intNumeroDias = obtenerDiasEntreFechas(convertirTimestampToDate(tsFechaInicial), dtHoy);
					}

					InteresCancelado interesCancelado = new InteresCancelado();
					interesCancelado.setId(new InteresCanceladoId());
					interesCancelado.getId().setIntCuentaPk(expedienteCreditoViejoMovimiento.getId().getIntCuentaPk());
					//interesCancelado.getId().setIntItemCancelaInteres(expedienteCreditoViejoMovimiento.getId().get);
					interesCancelado.getId().setIntItemExpediente(expedienteCreditoViejoMovimiento.getId().getIntItemExpediente());
					interesCancelado.getId().setIntItemExpedienteDetalle(expedienteCreditoViejoMovimiento.getId().getIntItemExpedienteDetalle());
					interesCancelado.getId().setIntItemMovCtaCte(movPrestAnteriorInteres.getIntItemMovimiento());
					interesCancelado.getId().setIntPersEmpresaPk(expedienteCreditoViejoMovimiento.getId().getIntPersEmpresaPk());
					interesCancelado.setBdMontoInteres(expedienteCreditoRef.getBdMontoInteresAtrasado());
					interesCancelado.setBdSaldoCredito(expedienteCreditoViejoMovimiento.getBdMontoSaldoDetalle());
					interesCancelado.setBdTasa(expedienteCreditoViejoMovimiento.getBdPorcentajeInteres());
					interesCancelado.setIntDias(intNumeroDias);
					interesCancelado.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					interesCancelado.setIntParaTipoFormaPago(Constante.PARAM_T_FORMADEPAGO_PLANILLA);
					interesCancelado.setTsFechaInicio(tsFechaInicial);
					interesCancelado.setTsFechaMovimiento(obtieneFechaActualEnTimesTamp());
					interesCancelado=conceptoFacade.grabarInteresCancelado(interesCancelado);
					
					if(interesCancelado.getId().getIntItemCancelaInteres() != null){
						// 4. EXPEDIENTECREDITO -MOV: actualizar exp credito viejo - saldo cero.
						expedienteCreditoViejoMovimiento.setBdSaldoCredito(BigDecimal.ZERO);
						expedienteCreditoViejoMovimiento.setBdSaldoCreditoSoles(BigDecimal.ZERO);
						expedienteCreditoViejoMovimiento.setBdSaldoInteres(bdMontoRegistro);
						expedienteCreditoViejoMovimiento.setBdSaldoInteresSoles(bdMontoRegistro);
						expedienteCreditoViejoMovimiento = conceptoFacade.modificarExpediente(expedienteCreditoViejoMovimiento);
					
					// 5. se actualiza ceros en saldos de amortización 1.
					// recuperar el cronograma del credito viejo.
						List<Cronograma> lstCronograma = null;
						lstCronograma = conceptoFacade.getListaCronogramaPorPkExpediente(expedienteCreditoViejoMovimiento.getId());
						if(lstCronograma != null && !lstCronograma.isEmpty()){
							for (Cronograma cronograma : lstCronograma) {
								if(cronograma.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0
									&& cronograma.getIntParaTipoConceptoCreditoCod().compareTo(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_AMORTIZACION)==0){
									cronograma.setBdSaldoDetalleCredito(BigDecimal.ZERO);
									conceptoFacade.modificarCronograma(cronograma);
								}
							}
						}
						
					// 6. viejo - 2
						EstadoExpediente estadoCancelado = new EstadoExpediente();
						estadoCancelado.setId(new EstadoExpedienteId());
						estadoCancelado.getId().setIntEmpresaEstado(expedienteCreditoViejoMovimiento.getId().getIntPersEmpresaPk());
						estadoCancelado.setIntCuenta(expedienteCreditoViejoMovimiento.getId().getIntCuentaPk());
						estadoCancelado.setIntEmpresa(expedienteCreditoViejoMovimiento.getIntPersEmpresaCreditoPk());
						estadoCancelado.setIntItemDetExpediente(expedienteCreditoViejoMovimiento.getId().getIntItemExpedienteDetalle());
						estadoCancelado.setIntItemExpediente(expedienteCreditoViejoMovimiento.getId().getIntItemExpediente());
						estadoCancelado.setIntParaEstadoExpediente(Constante.PARAM_T_ESTADOS_EXPEDIENTE_MOVIMIENTO_CANCELADO);
						estadoCancelado= conceptoFacade.grabarEstado(estadoCancelado);
						
					// 7. crear expediente de refinanciado
						BigDecimal bdMontoSaldoInteres = BigDecimal.ZERO;
						if(expedienteCreditoRef.getListaCronogramaCredito() != null && !expedienteCreditoRef.getListaCronogramaCredito().isEmpty()){
							for (CronogramaCredito cronograma : expedienteCreditoRef.getListaCronogramaCredito()) {
								if(cronograma.getIntParaTipoConceptoCod().compareTo(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_INTERES)==0){
									bdMontoSaldoInteres = bdMontoSaldoInteres.add(cronograma.getBdMontoConcepto());
								}
							}
						}
						
						Expediente expRefinanciamiento = new Expediente();
						expRefinanciamiento.setId(new ExpedienteId());
						expRefinanciamiento.getId().setIntCuentaPk(expedienteCreditoRef.getId().getIntCuentaPk());
						expRefinanciamiento.getId().setIntItemExpediente(expedienteCreditoRef.getId().getIntItemExpediente());
						expRefinanciamiento.getId().setIntItemExpedienteDetalle(expedienteCreditoRef.getId().getIntItemDetExpediente());
						expRefinanciamiento.getId().setIntPersEmpresaPk(expedienteCreditoRef.getId().getIntPersEmpresaPk());
						expRefinanciamiento.setIntPersEmpresaCreditoPk(expedienteCreditoRef.getIntPersEmpresaCreditoPk());
						expRefinanciamiento.setIntParaTipoCreditoCod(Constante.PARAM_T_TIPO_CREDITO_REFINANCIADO);
						expRefinanciamiento.setIntItemCredito(expedienteCreditoRef.getIntItemCredito());
						expRefinanciamiento.setBdPorcentajeInteres(expedienteCreditoRef.getBdPorcentajeInteres());
						expRefinanciamiento.setBdMontoInteresAtrazado(expedienteCreditoRef.getBdMontoInteresAtrasado());
						expRefinanciamiento.setBdMontoSolicitado(expedienteCreditoRef.getBdMontoSolicitado());
						expRefinanciamiento.setBdMontoTotal(expedienteCreditoRef.getBdMontoTotal());
						expRefinanciamiento.setIntNumeroCuota(expedienteCreditoRef.getIntNumeroCuota());
						expRefinanciamiento.setBdSaldoCredito(expedienteCreditoRef.getBdMontoTotal());
						expRefinanciamiento.setBdSaldoInteres(bdMontoSaldoInteres);
						expRefinanciamiento.setIntPersEmpresaSucAdministra(expedienteCreditoRef.getIntPersEmpresaSucAdministra());
						expRefinanciamiento.setIntSucuIdSucursalAdministra(expedienteCreditoRef.getIntSucuIdSucursalAdministra());
						expRefinanciamiento.setIntSudeIdSubSucursalAdministra(expedienteCreditoRef.getIntSudeIdSubSucursalAdministra());
						expRefinanciamiento = conceptoFacade.grabarExpediente(expRefinanciamiento);


						estructuraDetalle = recuperarEstructuraDetalle(beanSocioComp.getSocio().getSocioEstructura());
						
					// CGD - 13.01.2014
					// Validar fecha de autorizacion (hoy) vs. la fecha de regsitro de la solicitud
					// Si son disitntas se recalcula el cronograma

						EstadoCredito primerEstadoCredito = null;
						Date dtFechaRegistro = new Date();

						primerEstadoCredito = boEstadoCredito.getMinEstadoCreditoPorPkExpedienteCredito (expedienteCreditoAut.getId());
						if(primerEstadoCredito != null){
							dtFechaRegistro= convertirTimestampToDate(primerEstadoCredito.getTsFechaEstado());
						}
						Boolean blnProcede = Boolean.FALSE;
						blnProcede = comparaDatesDDMMYYYY(dtFechaRegistro, dtHoy, null);

					// si son disitintos se realiza recalculo
						if(!blnProcede){
							// le cambia el estado del cronograma anterior
							if(expedienteCreditoRef.getListaCronogramaCredito() != null && !expedienteCreditoRef.getListaCronogramaCredito().isEmpty()){
								cambiarEstadoCronograma(expedienteCreditoRef.getListaCronogramaCredito(), Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
							}
							
							List<CronogramaCredito> lstNuevoCronograma = null;
							List<CronogramaCredito> lstNuevoCronogramaFinal = null;
							// se vuelve a generar el cronogrma
							lstNuevoCronograma = reCalcularCronogramaCredito(expedienteCreditoAut, estructuraDetalle, beanSocioComp);
							
							// grabamos en servicio el cronograma recientemente creado
							if(lstNuevoCronograma != null && !lstNuevoCronograma.isEmpty()){
								lstNuevoCronogramaFinal = new ArrayList<CronogramaCredito>();
								
								for (CronogramaCredito cronogramaCredito : lstNuevoCronograma) {
									cronogramaCredito = boCronogramaCredito.grabar(cronogramaCredito);
									lstNuevoCronogramaFinal.add(cronogramaCredito);
								}
							}
							
							//8. copiar nuevo cronograma a movimiento
							if(lstNuevoCronogramaFinal != null && !lstNuevoCronogramaFinal.isEmpty()){
								List<Cronograma> listaCronograma = new ArrayList<Cronograma>();
								for (CronogramaCredito cronograma : lstNuevoCronogramaFinal) {
									Cronograma cronogramaMov = new Cronograma();
									cronogramaMov.setId(new CronogramaId());
									cronogramaMov.getId().setIntCuentaPk(expRefinanciamiento.getId().getIntCuentaPk());
									cronogramaMov.getId().setIntItemExpediente(expRefinanciamiento.getId().getIntItemExpediente());
									cronogramaMov.getId().setIntItemExpedienteDetalle(expRefinanciamiento.getId().getIntItemExpedienteDetalle());
									cronogramaMov.getId().setIntPersEmpresaPk(expRefinanciamiento.getId().getIntPersEmpresaPk());
									cronogramaMov.setBdMontoCapital(cronograma.getBdMontoCapital());
									cronogramaMov.setBdMontoConcepto(cronograma.getBdMontoConcepto());
									cronogramaMov.setIntNumeroCuota(cronograma.getIntNroCuota());
									cronogramaMov.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
									cronogramaMov.setIntParaFormaPagoCod(cronograma.getIntParaFormaPagoCod());
									cronogramaMov.setIntParaTipoConceptoCreditoCod(cronograma.getIntParaTipoConceptoCod());
									cronogramaMov.setIntParaTipoCuotaCod(cronograma.getIntParaTipoCuotaCod());
									cronogramaMov.setIntPeriodoPlanilla(cronograma.getIntPeriodoPlanilla());
									cronogramaMov.setBdSaldoDetalleCredito(cronograma.getBdMontoConcepto());
									cronogramaMov.setTsFechaVencimiento(cronograma.getTsFechaVencimiento());
									listaCronograma.add(cronogramaMov);
								}
								if(listaCronograma != null && !listaCronograma.isEmpty()){
									for (Cronograma cronograma : listaCronograma) {
										cronograma = conceptoFacade.grabarCronograma(cronograma);
									}	
								}
							}
							
						}else{
							//8. copiar nuevo cronograma.
							if(expedienteCreditoRef.getListaCronogramaCredito() != null && !expedienteCreditoRef.getListaCronogramaCredito().isEmpty()){
								List<Cronograma> listaCronograma = new ArrayList<Cronograma>();
								for (CronogramaCredito cronograma : expedienteCreditoRef.getListaCronogramaCredito()) {
									Cronograma cronogramaMov = new Cronograma();
									cronogramaMov.setId(new CronogramaId());
									cronogramaMov.getId().setIntCuentaPk(expRefinanciamiento.getId().getIntCuentaPk());
									cronogramaMov.getId().setIntItemExpediente(expRefinanciamiento.getId().getIntItemExpediente());
									cronogramaMov.getId().setIntItemExpedienteDetalle(expRefinanciamiento.getId().getIntItemExpedienteDetalle());
									cronogramaMov.getId().setIntPersEmpresaPk(expRefinanciamiento.getId().getIntPersEmpresaPk());
									cronogramaMov.setBdMontoCapital(cronograma.getBdMontoCapital());
									cronogramaMov.setBdMontoConcepto(cronograma.getBdMontoConcepto());
									cronogramaMov.setIntNumeroCuota(cronograma.getIntNroCuota());
									cronogramaMov.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
									cronogramaMov.setIntParaFormaPagoCod(cronograma.getIntParaFormaPagoCod());
									cronogramaMov.setIntParaTipoConceptoCreditoCod(cronograma.getIntParaTipoConceptoCod());
									cronogramaMov.setIntParaTipoCuotaCod(cronograma.getIntParaTipoCuotaCod());
									cronogramaMov.setIntPeriodoPlanilla(cronograma.getIntPeriodoPlanilla());
									cronogramaMov.setBdSaldoDetalleCredito(cronograma.getBdMontoConcepto());
									cronogramaMov.setTsFechaVencimiento(cronograma.getTsFechaVencimiento());
									listaCronograma.add(cronogramaMov);
								}
								if(listaCronograma != null && !listaCronograma.isEmpty()){
									for (Cronograma cronograma : listaCronograma) {
										cronograma = conceptoFacade.grabarCronograma(cronograma);
									}	
								}
							}
						}

					// 9. nuevo estado 
						//PARAM_T_ESTADOS_EXPEDIENTE_MOVIMIENTO_VIGENTE
						EstadoExpediente estadoVigente = new EstadoExpediente();
						estadoVigente.setId(new EstadoExpedienteId());
						estadoVigente.getId().setIntEmpresaEstado(expRefinanciamiento.getId().getIntPersEmpresaPk());
						estadoVigente.setIntCuenta(expRefinanciamiento.getId().getIntCuentaPk());
						/*if(estadoCancelado.getId() != null){
							estadoVigente.getId().setIntItemEstado(estadoCancelado.getId().getIntItemEstado() + 1);	
						}*/
						estadoVigente.setIntEmpresa(expRefinanciamiento.getId().getIntPersEmpresaPk());
						estadoVigente.setIntItemDetExpediente(expRefinanciamiento.getId().getIntItemExpedienteDetalle());
						estadoVigente.setIntItemExpediente(expRefinanciamiento.getId().getIntItemExpediente());
						estadoVigente.setIntParaEstadoExpediente(Constante.PARAM_T_ESTADOS_EXPEDIENTE_MOVIMIENTO_VIGENTE);
						estadoVigente = conceptoFacade.grabarEstado(estadoVigente);
					
					// 10. crear registro en movimiento refinanciado	
						Movimiento movPrestRefinanciado = new Movimiento();
						movPrestRefinanciado.setTsFechaMovimiento(obtieneFechaActualEnTimesTamp());
						movPrestRefinanciado.setIntPersEmpresa(expRefinanciamiento.getId().getIntPersEmpresaPk());
						movPrestRefinanciado.setIntCuenta(expRefinanciamiento.getId().getIntCuentaPk());
						movPrestRefinanciado.setIntPersPersonaIntegrante(intPersonaIntegrante);
						movPrestRefinanciado.setIntItemExpediente(expRefinanciamiento.getId().getIntItemExpediente());
						movPrestRefinanciado.setIntItemExpedienteDetalle(expRefinanciamiento.getId().getIntItemExpedienteDetalle());
						movPrestRefinanciado.setIntParaTipoConceptoGeneral(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION);
						movPrestRefinanciado.setIntParaTipoMovimiento(Constante.PARAM_T_TIPO_MOVIMIENTO_TRANSFERENCIA);
						movPrestRefinanciado.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_TX_ENTERCONCEPTOS);
						movPrestRefinanciado.setIntParaTipoCargoAbono(Constante.PARAM_T_CARGOABONO_CARGO);
						movPrestRefinanciado.setBdMontoMovimiento(expRefinanciamiento.getBdMontoTotal());
						movPrestRefinanciado.setBdMontoSaldo(expRefinanciamiento.getBdMontoTotal());
						movPrestRefinanciado.setIntPersEmpresaUsuario(Constante.PARAM_EMPRESASESION);
						movPrestRefinanciado.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
						//21.03.2014 SE ADICIONA NUMERO DE DOCUMENTO.
						movPrestRefinanciado.setStrNumeroDocumento(""+expRefinanciamiento.getId().getIntItemExpediente()+"-"+expRefinanciamiento.getId().getIntItemExpedienteDetalle());
						movPrestRefinanciado = conceptoFacade.grabarMovimiento(movPrestRefinanciado);
						
						
					}else{
						mensaje = "No se pudo generar registro Interes Cancelado(3). Se cancela el proceso. ";
						throw new BusinessException("No se pudo generar registro Interes Cancelado(3). Se cancela el proceso. ");
					}
				}else{
					mensaje = "No se pudo generar registro Movimiento(2). Se cancela el proceso. ";
					throw new BusinessException("No se pudo generar registro Movimiento(2). Se cancela el proceso. ");
				}
			}else{
				mensaje = "No se pudo generar registro Movimiento(1). Se cancela el proceso. ";
				throw new BusinessException("No se pudo generar registro Movimiento(1). Se cancela el proceso. ");
			}

		} catch (BusinessException e) {
			log.error("Error - Exception - en generarMovimientosRefinanciamiento BusinessException --> "+e);
			mensaje = "Error en Proceso de Autorización. Se cancela el proceso. ";
			throw new BusinessException("Error en Proceso de Autorización. Se cancela el proceso. ");
		} catch (Exception e) {
			log.error("Error - Exception - en generarMovimientosRefinanciamiento Exception --> "+e);
			mensaje = "Error en Proceso de Autorización. Se cancela el proceso. ";
			throw new BusinessException("Error en Proceso de Autorización. Se cancela el proceso. ");
		}
		return mensaje;
	}

	
	/**
	 * Cambia el estado de todo el cronograma.
	 * @param lstCronograma
	 * @param intEstado
	 */
	public void cambiarEstadoCronograma (List<CronogramaCredito> lstCronograma, Integer intEstado){
		try {
			
			if(lstCronograma != null && !lstCronograma.isEmpty()){
				for (CronogramaCredito cronogramaCredito : lstCronograma) {
					cronogramaCredito.setIntParaEstadoCod(intEstado);
					boCronogramaCredito.modificar(cronogramaCredito);
				}
				
			}
			
			
			
		} catch (Exception e) {
			log.error("Error en cancelarCronogra ---> "+e);
		}
		
		
	}
	
	/**
	 * Realiza busqueda de cartera credito detalle, para obetener el tipode riesgo.
	 * Primero sobre el periodod actual, si no existiera registro lo hace con el periodod anterior.
	 * @param expId
	 * @return
	 */
	public CarteraCreditoDetalle recuperarTipoRiesgo(ExpedienteId expId){
		CarteraCreditoDetalle carteraCredDet = null;
		Integer intPeriodoActual=0, intPeridoAnterior = 0;
		String strPeridoActual = "";
		CarteraFacadeRemote carterafacade = null;
		
		try {
			carterafacade = (CarteraFacadeRemote)EJBFactory.getRemote(CarteraFacadeRemote.class);
			
			intPeriodoActual = obtenerPeriodoActual();
			if(intPeriodoActual != 0) strPeridoActual = intPeriodoActual.toString();
			
			int intMesActual    = Integer.parseInt(strPeridoActual.substring(4, 6));
			int intAnnoActual   = Integer.parseInt(strPeridoActual.substring(0,4));
			int intMesAnterior  = 0;
			int intAnnoAnterior = 0;

			if (intMesActual == 1) {
				intAnnoAnterior = intAnnoActual - 1;
				intMesAnterior = 12;
				intPeridoAnterior = new Integer(intAnnoAnterior+intMesAnterior);
			}else {
				intAnnoAnterior = intAnnoActual;
				intMesAnterior = intMesActual - 1;
				if(intMesAnterior < 10 ){
					intPeridoAnterior = new Integer(intAnnoAnterior+"0"+intMesAnterior);
				}else{
					intPeridoAnterior = new Integer(intAnnoAnterior+""+intMesAnterior);
				}
				
			}			
			
			// 1ro buscamos con el  peridod actual:
			carteraCredDet = carterafacade.getMaxPorExpediente(expId, intPeriodoActual);
			if(carteraCredDet == null){
				carteraCredDet =carterafacade.getMaxPorExpediente(expId, intPeridoAnterior);
			}

		} catch (Exception e) {
			log.error("Error en recuperarTipoRiesgo ---* ");
		}
		
		return carteraCredDet;
		
	}
	
	
	
	
	/**
	 * Recupera los expedientes de refinanciamineto segun filtros de busqueda.
	 * @param o
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCreditoComp> getBuscarRefinanciamientoCompFiltros(Integer intBusqTipo,String strBusqCadena,String strBusqNroSol,Integer intBusqSucursal,Integer intBusqEstado,Date dtBusqFechaEstadoDesde,Date dtBusqFechaEstadoHasta) throws BusinessException{
		ExpedienteCreditoComp dto = null;
		List<ExpedienteCreditoComp> lista = null;
		List<ExpedienteCredito> listaExpedienteCredito = null;
		EstadoCredito estadoCredito;
		List<EstadoCredito> listaEstadoCredito = null;
		List<CapacidadCredito> listaCapacidadCredito = null;
		SocioComp socioComp = null;
		Persona persona = null;
		Integer intIdPersona = null;
		PersonaFacadeRemote personaFacade = null;
		SocioFacadeRemote socioFacade = null;
		List<CronogramaCredito> listaCronograma = null;
		//Expediente expedientePorRefinanciar = null;
		//Integer intDetExp = null;
		CreditoFacadeRemote creditoFacade = null;
		ExpedienteCreditoComp expedienteBusqueda = null;
		
		try{
			socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			creditoFacade = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
			
			
			expedienteBusqueda = new ExpedienteCreditoComp();
			
			if(intBusqTipo.compareTo(0)!=0){
				expedienteBusqueda.setIntBusqTipo(intBusqTipo);
			}
			
			if(strBusqCadena.trim().length()==0){
				expedienteBusqueda.setStrBusqCadena("");
			}else{
				expedienteBusqueda.setStrBusqCadena(strBusqCadena.trim());
			}
			
			if(strBusqNroSol.trim().length()==0){
				expedienteBusqueda.setStrBusqNroSol("");
			}else{
				expedienteBusqueda.setStrBusqNroSol(strBusqNroSol.trim());
			}
			
			if(intBusqSucursal.compareTo(0)!=0){
				expedienteBusqueda.setIntBusqSucursal(intBusqSucursal);
			}
			
			if(intBusqEstado.compareTo(0)!=0){
				expedienteBusqueda.setIntBusqEstado(intBusqEstado);
			}
			if(dtBusqFechaEstadoDesde != null){
				expedienteBusqueda.setDtBusqFechaEstadoDesde(dtBusqFechaEstadoDesde);	
			}else{
				expedienteBusqueda.setDtBusqFechaEstadoDesde(null);
			}
			if(dtBusqFechaEstadoHasta != null){
				expedienteBusqueda.setDtBusqFechaEstadoHasta(dtBusqFechaEstadoHasta);	
			}else{
				expedienteBusqueda.setDtBusqFechaEstadoHasta(null);
			}

			listaExpedienteCredito = boExpedienteCredito.getListaBusqCreditoFiltros(expedienteBusqueda);
			
			if(listaExpedienteCredito != null && listaExpedienteCredito.size()>0){
				lista = new ArrayList<ExpedienteCreditoComp>();
				
				for(ExpedienteCredito expedienteCredito : listaExpedienteCredito){
					Credito credito = null;
					CreditoId creditoId = new CreditoId();

					creditoId.setIntItemCredito(expedienteCredito.getIntItemCredito());
					creditoId.setIntParaTipoCreditoCod(expedienteCredito.getIntParaTipoCreditoCod());
					creditoId.setIntPersEmpresaPk(expedienteCredito.getIntPersEmpresaCreditoPk());

					credito = creditoFacade.getCreditoPorIdCreditoDirecto(creditoId);
					
					dto = new ExpedienteCreditoComp();
					estadoCredito = boEstadoCredito.getMaxEstadoCreditoPorPokExpedienteCredito(expedienteCredito.getId());
					
					// solo los girados se refinancian
					if(estadoCredito.getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO)!=0){
						dto.setCredito(credito);
						dto.setEstadoCredito(estadoCredito);
						listaEstadoCredito = boEstadoCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
						if(listaEstadoCredito!=null){
							for (EstadoCredito $estadoCredito : listaEstadoCredito) {
								if($estadoCredito.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO))
								dto.setStrFechaRequisito(Constante.sdf.format($estadoCredito.getTsFechaEstado()));
								if($estadoCredito.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD))
									dto.setStrFechaSolicitud(Constante.sdf.format($estadoCredito.getTsFechaEstado()));
								if($estadoCredito.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)){
									dto.setStrFechaAutorizacion(Constante.sdf.format($estadoCredito.getTsFechaEstado()));
									// CGD-03.10.2013
									dto.setBlnLinkDetalle(Boolean.TRUE);
								}
									
								
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
						
						listaCronograma = boCronogramaCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
						
						//Ordenamos el cronograma por el iitemcronograma 
						Collections.sort(listaCronograma, new Comparator<CronogramaCredito>(){
							public int compare(CronogramaCredito uno, CronogramaCredito otro) {
								return uno.getId().getIntItemCronograma().compareTo(otro.getId().getIntItemCronograma());
							}
						});
						
						if(listaCronograma != null){
							BigDecimal bdCuotaFija = BigDecimal.ZERO;
							BigDecimal bdMontoUno = BigDecimal.ZERO;
							BigDecimal bdMontoDos = BigDecimal.ZERO;
							
							for (CronogramaCredito cronogramaCredito : listaCronograma) {
								if(cronogramaCredito.getId().getIntItemCronograma().compareTo(new Integer(1))==0){
									bdMontoUno = cronogramaCredito.getBdMontoConcepto();
								}
								if(cronogramaCredito.getId().getIntItemCronograma().compareTo(new Integer(2))==0){
									bdMontoDos = cronogramaCredito.getBdMontoConcepto();
								}
							}
							if(bdMontoUno.compareTo(BigDecimal.ZERO)!=0 && bdMontoDos.compareTo(BigDecimal.ZERO)!=0){
								bdCuotaFija = bdMontoUno.add(bdMontoDos);
							}
							//bdCuotaFija = listaCronograma.get(0).getBdMontoConcepto().add(listaCronograma.get(1).getBdMontoConcepto());
							dto.setBdCuotaFija(bdCuotaFija);
						}
						dto.setExpedienteCredito(expedienteCredito);
						lista.add(dto);

					}
					
				}
				//dto.setListaExpedienteCredito(listaExpedienteCredito);
			}
		}catch(BusinessException e){
			log.error("Error - BusinessException - en getListaExpedienteRefinanciamientoCompDeBusqueda ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en getListaExpedienteRefinanciamientoCompDeBusqueda ---> "+e);
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	/**
	 * Recupera y forma los expedientres refinanciados y sus creditos de origen,
	 * para ser mostrados en pop de detalle.
	 * @param registroSeleccionadoBusqueda
	 * @return
	 */
	public List<ExpedienteComp> recuperarDetallesCreditoRefinanciado(ExpedienteCreditoComp registroSeleccionadoBusqueda){
		List<ExpedienteComp> lstDetallesAMostrar= null;
		List<Expediente> lstExpedientesMov= null;
		ConceptoFacadeRemote conceptofacade= null;
		//CreditoFacadeRemote creditoFacade = null;
		PrestamoFacadeRemote prestamoFacade = null;
		PersonaFacadeRemote personaFacade = null;
		EmpresaFacadeRemote empresaFacade = null;
		try {

			conceptofacade= (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			//creditoFacade = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
			prestamoFacade = (PrestamoFacadeRemote)EJBFactory.getRemote(PrestamoFacadeRemote.class);
			personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			
			if(registroSeleccionadoBusqueda != null){
				ExpedienteId expMovId = new ExpedienteId();
				expMovId.setIntPersEmpresaPk(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntPersEmpresaPk());
				expMovId.setIntCuentaPk(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntCuentaPk());
				expMovId.setIntItemExpediente(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntItemExpediente());

				lstExpedientesMov = conceptofacade.getListaExpedientePorCtaYExp(expMovId);
				if(lstExpedientesMov != null && !lstExpedientesMov.isEmpty()){
					lstDetallesAMostrar = new ArrayList<ExpedienteComp>();
					
					for (Expediente expedienteMov : lstExpedientesMov) {
						if(expedienteMov.getIntParaTipoCreditoCod().compareTo(Constante.PARAM_T_TIPO_CREDITO_REFINANCIADO)==0){
							String strDescripcionCreditoRefinanciado = "";
							String strDescripcionCreditoAnterior = "";
							ExpedienteComp expedienteGrillaComp = new ExpedienteComp();
							ExpedienteCreditoComp expedienteCreditoAnteriorComp = new ExpedienteCreditoComp();
							ExpedienteCreditoComp expedienteCreditoRefinanciadoComp = new ExpedienteCreditoComp();
							ExpedienteCredito expedienteRefinan = new ExpedienteCredito();
							ExpedienteCredito expedienteAnterior = new ExpedienteCredito();
							ExpedienteCreditoId expIdRefinan = new ExpedienteCreditoId();
							ExpedienteCreditoId expIdAnterior = new ExpedienteCreditoId();
							expedienteGrillaComp.setExpedienteCreditoComp(expedienteCreditoRefinanciadoComp);
							expedienteGrillaComp.setExpedienteCreditoAnteriorComp(expedienteCreditoAnteriorComp);
							

							// Expediente servicio que dio origen  al refinanciado
							expIdRefinan.setIntPersEmpresaPk(expedienteMov.getId().getIntPersEmpresaPk());
							expIdRefinan.setIntCuentaPk(expedienteMov.getId().getIntCuentaPk());
							expIdRefinan.setIntItemExpediente(expedienteMov.getId().getIntItemExpediente());
							expIdRefinan.setIntItemDetExpediente(expedienteMov.getId().getIntItemExpedienteDetalle());
							// Expediente de credito refinanciado
							expedienteRefinan = prestamoFacade.getExpedienteCreditoPorId(expIdRefinan);

							// recuperamos el credito asociado al refinanciado
							if(expedienteRefinan.getId() != null){
								
								expedienteCreditoRefinanciadoComp.setExpedienteCredito(expedienteRefinan);
								expIdAnterior.setIntPersEmpresaPk(expedienteRefinan.getIntPersEmpresaRefPk());
								expIdAnterior.setIntCuentaPk(expedienteRefinan.getIntCuentaRefPk());
								expIdAnterior.setIntItemExpediente(expedienteRefinan.getIntItemExpedienteRef());
								expIdAnterior.setIntItemDetExpediente(expedienteRefinan.getIntItemDetExpedienteRef());
								
								expedienteAnterior = prestamoFacade.getExpedienteCreditoPorId(expIdAnterior);
								expedienteCreditoAnteriorComp.setExpedienteCredito(expedienteAnterior);
								
								List<EstadoCredito> listaEstadoCreditoRefinanciado = null;
								listaEstadoCreditoRefinanciado = boEstadoCredito.getListaPorPkExpedienteCredito(expedienteRefinan.getId());
								if(listaEstadoCreditoRefinanciado!=null && !listaEstadoCreditoRefinanciado.isEmpty()){
									for (EstadoCredito estado : listaEstadoCreditoRefinanciado) {
										if(estado.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)){
											List<Sucursal> listSucursal = null;
											
											listSucursal = empresaFacade.getListaSucursalPorPkEmpresa(Constante.PARAM_EMPRESASESION);
											if(listSucursal != null && !listSucursal.isEmpty()){
												for (Sucursal sucursal : listSucursal) {
													if(sucursal.getId().getIntIdSucursal().compareTo(estado.getIntIdUsuSucursalPk())==0){
														List<Juridica> lstJuridica = null;
														lstJuridica = personaFacade.getListaJuridicaPorInPk(sucursal.getIntPersPersonaPk().toString()); 
														if(lstJuridica != null && !lstJuridica.isEmpty()){
															expedienteCreditoRefinanciadoComp.setStrDescripcionSubActividad(lstJuridica.get(0).getStrSiglas());
														}
														break;
													}
												}
											}
											expedienteCreditoRefinanciadoComp.setStrFechaRequisito(Constante.sdf.format(estado.getTsFechaEstado()));
										}
										
										if(estado.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD))
											expedienteCreditoRefinanciadoComp.setStrFechaSolicitud(Constante.sdf.format(estado.getTsFechaEstado()));
										if(estado.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO))
											expedienteCreditoRefinanciadoComp.setStrFechaAutorizacion(Constante.sdf.format(estado.getTsFechaEstado()));
									}
								}
								//expedienteCreditoAnteriorComp.getExpedienteCredito().setListaEstadoCredito(listaEstadoCredito);
							}

							
							
							
							// tipo de credito - refinanciado
							CreditoId idCreditoRefinanciado = new CreditoId();
							idCreditoRefinanciado.setIntItemCredito(expedienteRefinan.getIntItemCredito());
							idCreditoRefinanciado.setIntParaTipoCreditoCod(expedienteRefinan.getIntParaTipoCreditoCod());
							idCreditoRefinanciado.setIntPersEmpresaPk(expedienteRefinan.getIntPersEmpresaCreditoPk());
							strDescripcionCreditoRefinanciado = recuperarDescripcionCredito(idCreditoRefinanciado);
							expedienteGrillaComp.getExpedienteCreditoComp().setStrDescripcionExpedienteCredito(strDescripcionCreditoRefinanciado);
							
							// tipo de credito - anterior
							CreditoId idCreditoAnterior = new CreditoId();
							idCreditoAnterior.setIntItemCredito(expedienteAnterior.getIntItemCredito());
							idCreditoAnterior.setIntParaTipoCreditoCod(expedienteAnterior.getIntParaTipoCreditoCod());
							idCreditoAnterior.setIntPersEmpresaPk(expedienteAnterior.getIntPersEmpresaCreditoPk());
							strDescripcionCreditoAnterior = recuperarDescripcionCredito(idCreditoAnterior);
							expedienteGrillaComp.getExpedienteCreditoAnteriorComp().setStrDescripcionExpedienteCredito(strDescripcionCreditoAnterior);
							
							// Cuota fioja de
							List<CronogramaCredito> listaCronograma = null;
							listaCronograma = boCronogramaCredito.getListaPorPkExpedienteCredito(expedienteRefinan.getId());
							
							//Ordenamos el cronograma por el iitemcronograma 
							Collections.sort(listaCronograma, new Comparator<CronogramaCredito>(){
								public int compare(CronogramaCredito uno, CronogramaCredito otro) {
									return uno.getId().getIntItemCronograma().compareTo(otro.getId().getIntItemCronograma());
								}
							});
							
							if(listaCronograma != null){
								BigDecimal bdCuotaFija = BigDecimal.ZERO;
								BigDecimal bdMontoUno = BigDecimal.ZERO;
//								BigDecimal bdMontoDos = BigDecimal.ZERO;
								
								for (CronogramaCredito cronogramaCredito : listaCronograma) {
									if(cronogramaCredito.getId().getIntItemCronograma().compareTo(new Integer(1))==0){
										bdMontoUno = cronogramaCredito.getBdMontoConcepto();
										break;
									}
									
								}
								if(bdMontoUno.compareTo(BigDecimal.ZERO)!=0){
									bdCuotaFija = bdMontoUno;
								}else{
									bdCuotaFija = BigDecimal.ZERO;
								}
								//bdCuotaFija = listaCronograma.get(0).getBdMontoConcepto().add(listaCronograma.get(1).getBdMontoConcepto());
								expedienteCreditoRefinanciadoComp.setBdCuotaFija(bdCuotaFija);
							}
							
							expedienteGrillaComp.setExpediente(expedienteMov);
							expedienteGrillaComp.setExpedienteCreditoComp(expedienteCreditoRefinanciadoComp); 
							expedienteGrillaComp.setExpedienteCreditoAnteriorComp(expedienteCreditoAnteriorComp); 
							
							lstDetallesAMostrar.add(expedienteGrillaComp);	
						}

					}
				}
			}
			log.info("lista resultante: "+lstDetallesAMostrar);
		} catch (Exception e) {
			log.error("Error en recuperarDetallesCreditoRefinanciado --> xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx "+e);
			e.printStackTrace();
			log.error("Error en recuperarDetallesCreditoRefinanciado --> yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy "+e);
		}
		return lstDetallesAMostrar;
		
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String recuperarDescripcionCredito(CreditoId idCredito){
		String strDescripcion = "";
		Credito credito = null;
		CreditoFacadeRemote creditoFacade = null;
		try {
			creditoFacade = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
			
			credito = creditoFacade.getCreditoPorIdCreditoDirecto(idCredito);
			if(credito != null){
				strDescripcion = credito.getStrDescripcion();
			}else{
				strDescripcion = "Desconocido";
			}
			
		} catch (Exception e) {
			log.error("Error en recuperarDescripcionCredito ---> "+e);
		}
		return strDescripcion;
	}
	
	
	/**
	 * Recupera los expedientes de refinanciamineto segun filtros de busqueda.
	 * @param o
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCreditoComp> getBuscarAutRefinanciamientoCompFiltrosxxxxxxxx(Integer intBusqTipo,String strBusqCadena,String strBusqNroSol,Integer intBusqSucursal,Integer intBusqEstado,Date dtBusqFechaEstadoDesde,Date dtBusqFechaEstadoHasta) throws BusinessException{
		ExpedienteCreditoComp dto = null;
		List<ExpedienteCreditoComp> lista = null;
		List<ExpedienteCredito> listaExpedienteCredito = null;
		EstadoCredito estadoCredito;
		List<EstadoCredito> listaEstadoCredito = null;
		List<CapacidadCredito> listaCapacidadCredito = null;
		SocioComp socioComp = null;
		Persona persona = null;
		Integer intIdPersona = null;
		PersonaFacadeRemote personaFacade = null;
		SocioFacadeRemote socioFacade = null;
		List<CronogramaCredito> listaCronograma = null;
		//Expediente expedientePorRefinanciar = null;
		//Integer intDetExp = null;
		CreditoFacadeRemote creditoFacade = null;
		ExpedienteCreditoComp expedienteBusqueda = null;
		
		try{
			socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			creditoFacade = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
			
			
			expedienteBusqueda = new ExpedienteCreditoComp();
			
			if(intBusqTipo.compareTo(0)!=0){
				expedienteBusqueda.setIntBusqTipo(intBusqTipo);
			}
			
			if(strBusqCadena.trim().length()==0){
				expedienteBusqueda.setStrBusqCadena("");
			}else{
				expedienteBusqueda.setStrBusqCadena(strBusqCadena.trim());
			}
			
			if(strBusqNroSol.trim().length()==0){
				expedienteBusqueda.setStrBusqNroSol("");
			}else{
				expedienteBusqueda.setStrBusqNroSol(strBusqNroSol.trim());
			}
			
			if(intBusqSucursal.compareTo(0)!=0){
				expedienteBusqueda.setIntBusqSucursal(intBusqSucursal);
			}
			
			if(intBusqEstado.compareTo(0)!=0){
				expedienteBusqueda.setIntBusqEstado(intBusqEstado);
			}
			if(dtBusqFechaEstadoDesde != null){
				expedienteBusqueda.setDtBusqFechaEstadoDesde(dtBusqFechaEstadoDesde);	
			}else{
				expedienteBusqueda.setDtBusqFechaEstadoDesde(null);
			}
			if(dtBusqFechaEstadoHasta != null){
				expedienteBusqueda.setDtBusqFechaEstadoHasta(dtBusqFechaEstadoHasta);	
			}else{
				expedienteBusqueda.setDtBusqFechaEstadoHasta(null);
			}

			listaExpedienteCredito = boExpedienteCredito.getListaBusqAutRefFiltros(expedienteBusqueda);
			
			if(listaExpedienteCredito != null && listaExpedienteCredito.size()>0){
				lista = new ArrayList<ExpedienteCreditoComp>();
				
				for(ExpedienteCredito expedienteCredito : listaExpedienteCredito){
					Credito credito = null;
					CreditoId creditoId = new CreditoId();

					creditoId.setIntItemCredito(expedienteCredito.getIntItemCredito());
					creditoId.setIntParaTipoCreditoCod(expedienteCredito.getIntParaTipoCreditoCod());
					creditoId.setIntPersEmpresaPk(expedienteCredito.getIntPersEmpresaCreditoPk());

					credito = creditoFacade.getCreditoPorIdCreditoDirecto(creditoId);
					
					dto = new ExpedienteCreditoComp();
					estadoCredito = boEstadoCredito.getMaxEstadoCreditoPorPokExpedienteCredito(expedienteCredito.getId());
					
					// solo los girados se refinancian
					if(estadoCredito.getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO)!=0){
						dto.setCredito(credito);
						dto.setEstadoCredito(estadoCredito);
						listaEstadoCredito = boEstadoCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
						if(listaEstadoCredito!=null){
							for (EstadoCredito $estadoCredito : listaEstadoCredito) {
								if($estadoCredito.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO))
								dto.setStrFechaRequisito(Constante.sdf.format($estadoCredito.getTsFechaEstado()));
								if($estadoCredito.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD))
									dto.setStrFechaSolicitud(Constante.sdf.format($estadoCredito.getTsFechaEstado()));
								if($estadoCredito.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)){
									dto.setStrFechaAutorizacion(Constante.sdf.format($estadoCredito.getTsFechaEstado()));
									// CGD-03.10.2013
									dto.setBlnLinkDetalle(Boolean.TRUE);
								}
									
								
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
						
						listaCronograma = boCronogramaCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
						
						//Ordenamos el cronograma por el iitemcronograma 
						Collections.sort(listaCronograma, new Comparator<CronogramaCredito>(){
							public int compare(CronogramaCredito uno, CronogramaCredito otro) {
								return uno.getId().getIntItemCronograma().compareTo(otro.getId().getIntItemCronograma());
							}
						});
						
						if(listaCronograma != null){
							BigDecimal bdCuotaFija = BigDecimal.ZERO;
							BigDecimal bdMontoUno = BigDecimal.ZERO;
							BigDecimal bdMontoDos = BigDecimal.ZERO;
							
							for (CronogramaCredito cronogramaCredito : listaCronograma) {
								if(cronogramaCredito.getId().getIntItemCronograma().compareTo(new Integer(1))==0){
									bdMontoUno = cronogramaCredito.getBdMontoConcepto();
								}
								if(cronogramaCredito.getId().getIntItemCronograma().compareTo(new Integer(2))==0){
									bdMontoDos = cronogramaCredito.getBdMontoConcepto();
								}
							}
							if(bdMontoUno.compareTo(BigDecimal.ZERO)!=0 && bdMontoDos.compareTo(BigDecimal.ZERO)!=0){
								bdCuotaFija = bdMontoUno.add(bdMontoDos);
							}
							//bdCuotaFija = listaCronograma.get(0).getBdMontoConcepto().add(listaCronograma.get(1).getBdMontoConcepto());
							dto.setBdCuotaFija(bdCuotaFija);
						}
						dto.setExpedienteCredito(expedienteCredito);
						lista.add(dto);

					}
					
				}
				//dto.setListaExpedienteCredito(listaExpedienteCredito);
			}
		}catch(BusinessException e){
			log.error("Error - BusinessException - en getListaExpedienteRefinanciamientoCompDeBusqueda ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en getListaExpedienteRefinanciamientoCompDeBusqueda ---> "+e);
			throw new BusinessException(e);
		}
		return lista;
	}
	

	
	/**
	 * Recupera los expedientes de credito segun filtros de busqueda
	 * @param expedienteCompBusq
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCreditoComp> getListaBusqCreditoFiltros(ExpedienteCreditoComp expedienteCompBusq)throws BusinessException{
		List<ExpedienteCreditoComp> lstCreditoComp = null;
		List<ExpedienteCredito> lstCreditoCompTemp = null;
		try {

			lstCreditoCompTemp = boExpedienteCredito.getListaBusqCreditoFiltros(expedienteCompBusq);
			if(lstCreditoCompTemp != null && !lstCreditoCompTemp.isEmpty()){
				lstCreditoComp = new ArrayList<ExpedienteCreditoComp>();
				lstCreditoComp = completarDatosExpediente_BusqFiltros(lstCreditoCompTemp);
			}
		
		} catch (Exception e) {
			log.error("Error en getListaBusqCreditoFiltros --> "+e);
		}
		return lstCreditoComp;
	}
	
	
	/**
	 * Recupera los expedientes de credito segun filtros de busqueda
	 * @param expedienteCompBusq
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCreditoComp> getListaBusqCreditoEspecialFiltros(ExpedienteCreditoComp expedienteCompBusq)throws BusinessException{
		List<ExpedienteCreditoComp> lstCreditoComp = null;
		List<ExpedienteCredito> lstCreditoCompTemp = null;
		try {

			lstCreditoCompTemp = boExpedienteCredito.getListaBusqCreditoEspecialFiltros(expedienteCompBusq);
			if(lstCreditoCompTemp != null && !lstCreditoCompTemp.isEmpty()){
				lstCreditoComp = new ArrayList<ExpedienteCreditoComp>();
				lstCreditoComp = completarDatosExpediente_BusqFiltros(lstCreditoCompTemp);
			}
		
		} catch (Exception e) {
			log.error("Error en getListaBusqCreditoEspecialFiltros --> "+e);
		}
		return lstCreditoComp;
	}
	
	/**
	 * 
	 * @param expediente
	 * @return
	 */
	public List<ExpedienteCreditoComp> completarDatosExpediente_BusqFiltros(List<ExpedienteCredito> lstExpediente){
		List<ExpedienteCreditoComp> lstExpComp = null;
		try {
			
			// Estados Req, Sol y Aut
			if(lstExpediente != null && !lstExpediente.isEmpty()){
				lstExpComp = new ArrayList<ExpedienteCreditoComp>();
				
				for (ExpedienteCredito expedienteCredito : lstExpediente) {
					List<EstadoCredito> listaEstadoCredito = null;

					ExpedienteCreditoComp expCreditoComp = new ExpedienteCreditoComp();
					expCreditoComp.setExpedienteCredito(expedienteCredito);
					
					listaEstadoCredito = boEstadoCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
					if(listaEstadoCredito!=null && !listaEstadoCredito.isEmpty()){
						for (EstadoCredito estado : listaEstadoCredito) {
							if(estado.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO))
								expCreditoComp.setStrFechaRequisito(Constante.sdf.format(estado.getTsFechaEstado()));
							if(estado.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD))
								expCreditoComp.setStrFechaSolicitud(Constante.sdf.format(estado.getTsFechaEstado()));
							if(estado.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)){
								expCreditoComp.setStrFechaAutorizacion(Constante.sdf.format(estado.getTsFechaEstado()));
								expCreditoComp.setBlnLinkDetalle(Boolean.TRUE);
							}
							
							
						}
					}
					lstExpComp.add(expCreditoComp);
				}
			}
			
			
			
			
			
		} catch (Exception e) {
			log.error("Error en completarDatosExpediente ---> "+e);
		}
		return lstExpComp;
	}
	
	
	
	public List<ExpedienteCreditoComp> completarDatosExpActividad_BusqFiltros(List<ExpedienteCredito> lstExpediente){
		List<ExpedienteCreditoComp> lstExpComp = null;
		try {
			
			// Estados Req, Sol y Aut
			if(lstExpediente != null && !lstExpediente.isEmpty()){
				lstExpComp = new ArrayList<ExpedienteCreditoComp>();
				
				for (ExpedienteCredito expedienteCredito : lstExpediente) {
					List<EstadoCredito> listaEstadoCredito = null;
					EstadoCredito ultimoEstado = null;
					List<ExpedienteActividad> listaExpedienteActividad = null;

					ExpedienteCreditoComp expCreditoComp = new ExpedienteCreditoComp();
					expCreditoComp.setExpedienteCredito(expedienteCredito);
					
					// Estados
					listaEstadoCredito = boEstadoCredito.getListaPorPkExpedienteCredito(expedienteCredito.getId());
					if(listaEstadoCredito!=null && !listaEstadoCredito.isEmpty()){
						for (EstadoCredito estado : listaEstadoCredito) {
							if(estado.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO))
								expCreditoComp.setStrFechaRequisito(Constante.sdf.format(estado.getTsFechaEstado()));
							if(estado.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD))
								expCreditoComp.setStrFechaSolicitud(Constante.sdf.format(estado.getTsFechaEstado()));
							if(estado.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)){
								expCreditoComp.setStrFechaAutorizacion(Constante.sdf.format(estado.getTsFechaEstado()));
								expCreditoComp.setBlnLinkDetalle(Boolean.TRUE);
							}
							
							
						}
					}
					
					// CGD-26.11.2013
					// Ultimo estado
					ultimoEstado = boEstadoCredito.getMaxEstadoCreditoPorPokExpedienteCredito(expedienteCredito.getId());
					if(ultimoEstado != null){
						expCreditoComp.setEstadoCredito(ultimoEstado);
					}
					
					// Credito/Contado
					listaExpedienteActividad = boExpedienteActividad.getListaPorPkExpedienteCredito(expedienteCredito.getId());
					if(listaExpedienteActividad != null && !listaExpedienteActividad.isEmpty()){
						for (ExpedienteActividad expedienteActividad : listaExpedienteActividad) {
							if(expedienteActividad.getIntParaTipoSolicitudCod().compareTo(Constante.PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_CREDITO)==0){
								expCreditoComp.setStrDescripcionActividad("Crédito");
							}else if(expedienteActividad.getIntParaTipoSolicitudCod().compareTo(Constante.PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_CONTADO)==0){
								expCreditoComp.setStrDescripcionActividad("Contado");
							}
						}
						
					}
					lstExpComp.add(expCreditoComp);
				}
			}

		} catch (Exception e) {
			log.error("Error en completarDatosExpediente ---> "+e);
		}
		return lstExpComp;
	}
	/**
	 * Recupera los expedientes de credito segun filtros de busqueda
	 * @param expedienteCompBusq
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCreditoComp> getListaBusqCreditosAutFiltros(ExpedienteCreditoComp expedienteCompBusq)throws BusinessException{
		List<ExpedienteCreditoComp> lstCreditoComp = null;
		List<ExpedienteCredito> lstCreditoCompTemp = null;
		try {

			lstCreditoCompTemp = boExpedienteCredito.getListaBusqCreditosAutFiltros(expedienteCompBusq);
			if(lstCreditoCompTemp != null && !lstCreditoCompTemp.isEmpty()){
				lstCreditoComp = new ArrayList<ExpedienteCreditoComp>();
				lstCreditoComp = completarDatosExpediente_BusqFiltros(lstCreditoCompTemp);
			}
		
		} catch (Exception e) {
			log.error("Error en getListaBusqCreditosAutFiltros --> "+e);
		}
		//return lstCreditoComp;
		return lstCreditoComp;
		
		
	}
	
	
	
	
	
	/**
	 * Recupera los expedientes de autorizacion de credito segun filtros de busqueda
	 * @param expedienteCompBusq
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCreditoComp> getListaBusqRefinanFiltros(ExpedienteCreditoComp expedienteCompBusq)throws BusinessException{
		List<ExpedienteCreditoComp> lstCreditoComp = null;
		List<ExpedienteCredito> lstCreditoCompTemp = null;
		try {

			lstCreditoCompTemp = boExpedienteCredito.getListaBusqRefinanFiltros(expedienteCompBusq);
			if(lstCreditoCompTemp != null && !lstCreditoCompTemp.isEmpty()){
				lstCreditoComp = new ArrayList<ExpedienteCreditoComp>();
				lstCreditoComp = completarDatosExpediente_BusqFiltros(lstCreditoCompTemp);
			}
		
		} catch (Exception e) {
			log.error("Error en getListaBusqCreditoFiltros --> "+e);
		}
		//return lstCreditoComp;
		return lstCreditoComp;
	}	
	
	/**
	 * Recupera los expedientes de autorizacion de credito segun filtros de busqueda
	 * @param expedienteCompBusq
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCreditoComp> getListaBusqAutRefFiltros(ExpedienteCreditoComp expedienteCompBusq)throws BusinessException{
		List<ExpedienteCreditoComp> lstCreditoComp = null;
		List<ExpedienteCredito> lstCreditoCompTemp = null;
		try {

			lstCreditoCompTemp = boExpedienteCredito.getListaBusqAutRefFiltros(expedienteCompBusq);
			if(lstCreditoCompTemp != null && !lstCreditoCompTemp.isEmpty()){
				lstCreditoComp = new ArrayList<ExpedienteCreditoComp>();
				lstCreditoComp = completarDatosExpediente_BusqFiltros(lstCreditoCompTemp);
			}
		
		} catch (Exception e) {
			log.error("Error en getListaBusqCreditoFiltros --> "+e);
		}
		//return lstCreditoComp;
		return lstCreditoComp;
	}	
	
	/**
	 * Recupera los expedientes de actividad segun filtros de busqueda
	 * @param expedienteCompBusq
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCreditoComp> getListaBusqActividadFiltros(ExpedienteCreditoComp expedienteCompBusq)throws BusinessException{
		List<ExpedienteCreditoComp> lstCreditoComp = null;
		List<ExpedienteCredito> lstCreditoCompTemp = null;
		try {

			lstCreditoCompTemp = boExpedienteCredito.getListaBusqActividadFiltros(expedienteCompBusq);
			if(lstCreditoCompTemp != null && !lstCreditoCompTemp.isEmpty()){
				lstCreditoComp = new ArrayList<ExpedienteCreditoComp>();
				lstCreditoComp = completarDatosExpActividad_BusqFiltros(lstCreditoCompTemp);
			}
		
		} catch (Exception e) {
			log.error("Error en getListaBusqActividadFiltros --> "+e);
		}
		return lstCreditoComp;
	}
		
	/**
	 * Recupera los expedientes de actividad por autorizar segun filtros de busqueda
	 * @param expedienteCompBusq
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCreditoComp> getListaBusqActAutFiltros(ExpedienteCreditoComp expedienteCompBusq)throws BusinessException{
		List<ExpedienteCreditoComp> lstCreditoComp = null;
		List<ExpedienteCredito> lstCreditoCompTemp = null;
		try {
			
			lstCreditoCompTemp = boExpedienteCredito.getListaBusqActAutFiltros(expedienteCompBusq);
			if(lstCreditoCompTemp != null && !lstCreditoCompTemp.isEmpty()){
				lstCreditoComp = new ArrayList<ExpedienteCreditoComp>();
				lstCreditoComp = completarDatosExpActividad_BusqFiltros(lstCreditoCompTemp);
			}
		
		} catch (Exception e) {
			log.error("Error en getListaBusqActividadFiltros --> "+e);
		}
		return lstCreditoComp;
	}	
	
	/**
	 * Graba Dinamicamente la Lista de Cancelacion de Creditos.
	 * @param lstEstadoCredito
	 * @param pPK
	 * @return
	 * @throws BusinessException
	 */
	public List<CancelacionCredito> grabarListaDinamicaCancelacionCredito(List<CancelacionCredito> lstCancelacion, ExpedienteCreditoId pPK) throws BusinessException{
		CancelacionCredito cancelacionCredito = null;
		CancelacionCreditoId pk = null;
		CancelacionCredito cancelacionTemp = null;
		try{
			for(int i=0; i<lstCancelacion.size(); i++){
				cancelacionCredito = (CancelacionCredito) lstCancelacion.get(i);
				if(cancelacionCredito.getId()== null || cancelacionCredito.getId().getIntItemCancelacion()==null){
					pk = new CancelacionCreditoId();
					pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
					pk.setIntCuentaPk(pPK.getIntCuentaPk());
					pk.setIntItemExpediente(pPK.getIntItemExpediente());
					pk.setIntItemDetExpediente(pPK.getIntItemDetExpediente());
					cancelacionCredito.setId(pk);
					cancelacionCredito = boCancelacionCredito.grabar(cancelacionCredito);
				}else{
					cancelacionTemp = boCancelacionCredito.getPorPk(cancelacionCredito.getId());
					if(cancelacionTemp == null){
						cancelacionCredito = boCancelacionCredito.grabar(cancelacionCredito);
					}else{
						cancelacionCredito = boCancelacionCredito.modificar(cancelacionCredito);
					}
				}
			}
		}catch(BusinessException e){
			log.error("Error - BusinessException - en grabarListaDinamicaCancelacionCredito ---> "+e);
			throw e;
		}catch(Exception e){
			log.error("Error - Exception - en grabarListaDinamicaCancelacionCredito ---> "+e);
			throw new BusinessException(e);
		}
		return lstCancelacion;
	}		
		
	/**
	 * Recupera cronogramaComp a partir de los cronograma credito
	 * @param expedienteId
	 * @param intNroCuota
	 * @return
	 * @throws BusinessException
	 */
	public CronogramaCreditoComp recuperarCronogramaCompVistaPopUp(ExpedienteCreditoId expedienteId, Integer intNroCuota) throws BusinessException{
		CronogramaCreditoComp cronogramaComp = null;
		List<CronogramaCredito> lstCronogramaCredito = null;
		try {
			lstCronogramaCredito= boCronogramaCredito.getListaPorPkExpedienteCuota(expedienteId, intNroCuota);
			if(lstCronogramaCredito != null && !lstCronogramaCredito.isEmpty()){
				cronogramaComp = new CronogramaCreditoComp();
				CronogramaCredito cronogramaCredito= new CronogramaCredito();
				cronogramaCredito = lstCronogramaCredito.get(0);
				
				cronogramaComp.setStrFechaEnvio(Constante.sdf.format(cronogramaCredito.getTsFechaEnvioView()));
				cronogramaComp.setStrFechaVencimiento(Constante.sdf.format(cronogramaCredito.getTsFechaVencimiento()));

				Calendar calInicio = Calendar.getInstance();
				Calendar calFinal = Calendar.getInstance();
				calInicio = StringToCalendar(Constante.sdf.format(cronogramaCredito.getTsFechaEnvioView()));
				calFinal = StringToCalendar(Constante.sdf.format(cronogramaCredito.getTsFechaVencimiento()));
				cronogramaComp.setIntDiasTranscurridos(fechasDiferenciaEnDias(calInicio.getTime(), calFinal.getTime()));
				cronogramaComp.setBdSaldoCapital(cronogramaCredito.getBdSaldoCapitalView());
				cronogramaComp.setBdAmortizacion(cronogramaCredito.getBdAmortizacionView());
				cronogramaComp.setBdInteres(cronogramaCredito.getBdInteresView());
				
				// Cuota mensual = amortizacion + interes
				BigDecimal bdMontoCuotaMensual = cronogramaCredito.getBdAmortizacionView().add(cronogramaCredito.getBdInteresView()); 
				bdMontoCuotaMensual = bdMontoCuotaMensual.divide(BigDecimal.ONE, 4,RoundingMode.HALF_UP);
				cronogramaComp.setBdCuotaMensual(bdMontoCuotaMensual);
				BigDecimal bdAportes = BigDecimal.ZERO;
				bdAportes= cronogramaCredito.getBdAportesView();
				bdAportes = bdAportes.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
				cronogramaComp.setBdAportes(bdAportes);// bdAportes
				BigDecimal bdMontoTotalCuota = BigDecimal.ZERO;
				bdMontoTotalCuota = bdMontoCuotaMensual.add(cronogramaCredito.getBdAportesView());
				bdMontoTotalCuota = bdMontoTotalCuota.divide(BigDecimal.ONE, 4,RoundingMode.HALF_UP);
				cronogramaComp.setBdTotalCuotaMensual(bdMontoTotalCuota);
			}

		} catch (Exception e) {
			log.error("Error en recuperarCronogramaCompVistaPopUp"+e);
		}
		return cronogramaComp;
	}
			
	/**
	 * Convierte una cadena a Calendar
	 * @param fecha
	 * @return Calendar cal
	 */
	public Calendar StringToCalendar(String fecha) {
		DateFormat formatter;
		Date date;
		formatter = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();

		try {
			date = (Date) formatter.parse(fecha);
			cal.setTime(date);
		} catch (ParseException e) {
			System.out.println("Exception :" + e);
		}

		return cal;
	}
	

	/**
	 * Calcula el nro de dias entre 2 fechas Calendar
	 * @param Date fechaInicial
	 * @param Date fechaFinal
	 * @return int dias
	 */
	public int fechasDiferenciaEnDias(Date fechaInicial, Date fechaFinal) {

		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		String fechaInicioString = df.format(fechaInicial);
		double dias = new Double(0);
		
		try {
			fechaInicial = df.parse(fechaInicioString);

			String fechaFinalString = df.format(fechaFinal);
			fechaFinal = df.parse(fechaFinalString);
			long fechaInicialMs = fechaInicial.getTime();
			long fechaFinalMs = fechaFinal.getTime();
			long diferencia = fechaFinalMs - fechaInicialMs;
			//Se le agrega 1 dia para que sea correcta la diferencia de fechas ---> 12.02.2014
			dias = Math.floor(diferencia / (1000 * 60 * 60 * 24))+1;
		
		} catch (ParseException ex) {
			log.error("Error ParseException en fechasDiferenciaEnDias ---> " + ex);
		}
		
		return ((int) dias);
	}
		
		
	/**
	 * x capricho justificado se regenra el cronograma
	 * @return
	 */
	public List<CronogramaCredito> reCalcularCronogramaCredito(ExpedienteCredito expedienteCredito, EstructuraDetalle estructuraDetalle, SocioComp beanSocioComp){
		List<CronogramaCredito> lstNuevoCronograma = null;
		Envioconcepto envioConcepto = null;
		Calendar miCal = Calendar.getInstance();
		String strPeriodoPlla = "";
		CronogramaCredito cronogramaCredito = new CronogramaCredito();
		CronogramaCreditoComp cronogramaCreditoComp = new CronogramaCreditoComp();
		BigDecimal bdPorcentajeInteres = BigDecimal.ZERO;
		SimpleDateFormat sdfPeriodo = new SimpleDateFormat("yyyyMM");
		/// globales
		List<CronogramaCreditoComp> listaCronogramaCreditoComp;
		PlanillaFacadeRemote planillaFacade = null;
		Integer intNroCuotas = 0;
		BigDecimal bdMontoTotalSolicitado = BigDecimal.ZERO;
		BigDecimal bdCuotaMensual = BigDecimal.ZERO;
		BigDecimal bdAportes = BigDecimal.ZERO;
		List<CuentaConcepto> lstCtaCto = null;
		CuentaConcepto ctaCtoAporte = null;
				
		try {
			listaCronogramaCreditoComp= new ArrayList<CronogramaCreditoComp>();
			intNroCuotas = expedienteCredito.getIntNumeroCuota();
			
			planillaFacade = (PlanillaFacadeRemote) EJBFactory.getRemote(PlanillaFacadeRemote.class);
			envioConcepto = planillaFacade.getEnvioConceptoPorEmpPerCta(Constante.PARAM_EMPRESASESION, 
					obtenerPeriodoActual(),
					beanSocioComp.getCuenta().getId().getIntCuenta());

			lstCtaCto = recuperarCuentasConceptoSocio(beanSocioComp);
			if(lstCtaCto != null && !lstCtaCto.isEmpty()){
				ctaCtoAporte = recuperarCuentasConceptoXCuentaYTipo(Constante.PARAM_T_CUENTACONCEPTO_APORTES, lstCtaCto);
				bdAportes = recuperarAportes(ctaCtoAporte);	
			}
			
			// detalle movimiento
			Calendar fec1erEnvio = Calendar.getInstance();
			Calendar envio = Calendar.getInstance();
			int dia = miCal.get(Calendar.DATE);
			int mes = miCal.get(Calendar.MONTH);
			int anno = miCal.get(Calendar.YEAR);

			Calendar fecHoy = Calendar.getInstance();
			Calendar fec1erVenc = Calendar.getInstance();

			// SI HAY ENVIO 
			if (envioConcepto != null){
				strPeriodoPlla = "" + envioConcepto.getIntPeriodoplanilla();

				// substring x---o x--->
				Calendar calendarTemp = Calendar.getInstance();
				int intUltimoMesPlla = Integer.parseInt(strPeriodoPlla.substring(4, 6));
				int intUltimoAnnoPlla = Integer.parseInt(strPeriodoPlla.substring(0,4));
				int intPrimerDiaPlla = Integer.parseInt("01");

				if (intUltimoMesPlla == 12) {
					intUltimoAnnoPlla = intUltimoAnnoPlla + 1;
					intUltimoMesPlla = 0;
				}

				// Definiendo fecha de 1er envio y 1er vencimiento
				fec1erEnvio.clear();
				fec1erEnvio.set(intUltimoAnnoPlla, intUltimoMesPlla + 1,intPrimerDiaPlla);

				calendarTemp.set(fec1erEnvio.get(Calendar.YEAR),
				fec1erEnvio.get(Calendar.MONTH),
				fec1erEnvio.get(Calendar.DATE));
				fec1erVenc.setTime(getUltimoDiaDelMes(calendarTemp));

			} else {
				fecHoy.set(anno, mes, dia);
				List listaEnvioVencimiento = new ArrayList();

				listaEnvioVencimiento.addAll(calcular1raFechaEnvioVencimiento(estructuraDetalle, fecHoy));
				fec1erEnvio = (Calendar) listaEnvioVencimiento.get(0);
				fec1erVenc = (Calendar) listaEnvioVencimiento.get(1);
			}

			//-----------------------------------------------------------------
			// Calculamos el resto de dias de envio y vencimiento en base a
			// fec1erEnvio y fec1erVenc
			//-----------------------------------------------------------------
			envio.set(fec1erEnvio.get(Calendar.YEAR),
			fec1erEnvio.get(Calendar.MONTH),
			fec1erEnvio.get(Calendar.DATE));

			List listaDiasEnvio = new ArrayList();
			List listaDiasVencimiento = new ArrayList();
			int vencDia, vencMes, vencAnno;
			int envDia, envMes, envAnno;

			envDia = envio.get(Calendar.DATE);
			envMes = envio.get(Calendar.MONTH);
			envAnno = envio.get(Calendar.YEAR);

			vencDia = fec1erVenc.get(Calendar.DATE);
			vencMes = fec1erVenc.get(Calendar.MONTH);
			vencAnno = fec1erVenc.get(Calendar.YEAR);
			Integer vencMesEsp = vencMes;
			// SE GENENERA LA LISTA DE LOS DIAS DE VENCIMIENTO listaDiasVencimiento
			for (int i = 0; i < intNroCuotas; i++) {
				Calendar nuevoDia = Calendar.getInstance();
				//CGD-07.01.2014 - envMes++;
				vencMesEsp++;
				
				if(i==0){
					if (envMes == 12) {
						//listaDiasEnvio.add(i, envDia + "/" + envMes + "/"+ envAnno);
						listaDiasEnvio.add(i, envDia + "/" + vencMes + "/"+ envAnno);
						envAnno = envAnno + 1;
						envMes = 0;
					} else {
						listaDiasEnvio.add(i, envDia + "/" + vencMes + "/"+ envAnno);
					}

					nuevoDia.set(vencAnno, vencMes, 15);
					if (vencMes == 12) {
						listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
						vencAnno = vencAnno + 1;
						vencMes = 0;
					} else {
						listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
					}
				}else{
					if (envMes == 12) {
						listaDiasEnvio.add(i, "01" + "/" + vencMesEsp + "/"+ envAnno);
						envAnno = envAnno + 1;
						envMes = 0;
					} else {
						listaDiasEnvio.add(i, "01" + "/" + vencMesEsp + "/"+ envAnno);
					}

					nuevoDia.set(vencAnno, vencMes, 15);
					if (vencMes == 12) {
						listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
						vencAnno = vencAnno + 1;
						vencMes = 0;
					} else {
						listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
					}
				}				
				vencMes++;
			}

			BigDecimal bdCuotaFinal = new BigDecimal(0);

			BigDecimal bdTea = BigDecimal.ZERO;
			if(bdPorcentajeInteres.compareTo(BigDecimal.ZERO)==0){
				bdTea = (((BigDecimal.ONE.add(bdPorcentajeInteres)).pow(12)).subtract(BigDecimal.ONE).setScale(4,
				RoundingMode.HALF_UP));
			}else{
				bdTea = (((BigDecimal.ONE.add(bdPorcentajeInteres.divide(new BigDecimal(100)))).pow(12)).subtract(BigDecimal.ONE).setScale(4,
				RoundingMode.HALF_UP));
			}

			// SE GENENERA LA LISTA DE LA DIEFERENICA DE DIAS diferenciaEntreDias
			List listaDias = new ArrayList(); // Lista que guarda la
			List listaSumaDias = new ArrayList(); // Lista que guarda la
			// sumatoria de dias.
			int diferenciaEntreDias = 0;
			int sumaDias = 0;

			for (int i = 0; i < intNroCuotas; i++) {
				// calculando diferencia entre el 1er vencimeitno y la fecha de hoy
				if (i == 0) {
					diferenciaEntreDias = fechasDiferenciaEnDias(fec1erEnvio.getTime(), fec1erVenc.getTime());
					listaDias.add(i, diferenciaEntreDias);
				} else {
					Calendar calendario = Calendar.getInstance();
					Calendar calend = new GregorianCalendar(
					getPrimerDiaDelMes(	StringToCalendar(listaDiasEnvio.get(i).toString())).getYear(),
					getPrimerDiaDelMes(	StringToCalendar(listaDiasEnvio.get(i).toString())).getMonth(), 1);

					calendario.set(calend.get(Calendar.YEAR),calend.get(Calendar.MONTH),calend.get(Calendar.DATE));
					diferenciaEntreDias = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);
					listaDias.add(i, diferenciaEntreDias);
				}
				sumaDias = sumaDias + diferenciaEntreDias;
				listaSumaDias.add(i, sumaDias);
			}

			// Calculamos el valor de la cuota en base a la formula:
			// ----------------------------------------------------------//
			// monto //
			// cuota = _____________________________________ //
			// //
			// 1/ (1+tea)^(sumdias/360) + ... n //
			// ----------------------------------------------------------//

			BigDecimal bdSumatoria = new BigDecimal(0);
			System.out.println("TEA" + bdTea);

			for (int i = 0; i < intNroCuotas; i++) {
				BigDecimal bdCalculo1 = new BigDecimal(0);
				BigDecimal bdCalculo2 = new BigDecimal(0);
				BigDecimal bdCalculo3 = new BigDecimal(0);
				BigDecimal bdCalculo4 = new BigDecimal(0);
				BigDecimal bdResultado = new BigDecimal(0);
				BigDecimal bdUno = BigDecimal.ONE;
				double dbResultDenom = 0;
				bdCalculo1 = new BigDecimal(listaSumaDias.get(i).toString()); // suma de dias
				bdCalculo2 = new BigDecimal(360); // 360
				bdCalculo3 = bdTea.add(bdUno); // tea + 1
				bdCalculo4 = bdCalculo1.divide(bdCalculo2, 4,RoundingMode.HALF_UP);
				dbResultDenom = Math.pow(bdCalculo3.doubleValue(),bdCalculo4.doubleValue());
				bdResultado = bdUno.divide(new BigDecimal(dbResultDenom),4, RoundingMode.HALF_UP);
				bdSumatoria = bdSumatoria.add(bdResultado);
			}

			bdCuotaFinal = bdMontoTotalSolicitado.divide(bdSumatoria, 4,RoundingMode.HALF_UP);
			System.out.println("CUOTA FINAL " + bdCuotaFinal);

			// Calculando Interes, Amortizacion, Saldo y la cuota mensual total y se conforma el cronograma
			BigDecimal bdAmortizacion = new BigDecimal(0);
			BigDecimal bdSaldo = new BigDecimal(0);
			BigDecimal bdSaldoMontoCapital = new BigDecimal(0);
			BigDecimal bdSumaAmortizacion = new BigDecimal(0);
			bdSaldo = bdMontoTotalSolicitado; // se inicializa el saldo con
			// el monto solicitado
			BigDecimal bdSaldoTemp = new BigDecimal(0);
			List listaSaldos = new ArrayList();

			//SE FORMA LA LISTA DEL CRONOGRAMA
			for (int i = 0; i < intNroCuotas; i++) {
				BigDecimal bdCalculo1 = new BigDecimal(0);
				BigDecimal bdCalculo2 = new BigDecimal(0);
				BigDecimal bdCalculo3 = new BigDecimal(0);
				BigDecimal bdCalculo4 = new BigDecimal(0);
				BigDecimal bdCalculo5 = new BigDecimal(0);
				BigDecimal bdInteresCuota = new BigDecimal(0);
				BigDecimal bdUno = BigDecimal.ONE;
				double dbResultDenom = 0;
				bdCalculo1 = new BigDecimal(listaDias.get(i).toString()); // suma de dias
				bdCalculo2 = new BigDecimal(360); // 360
				bdCalculo3 = bdTea.add(bdUno); // tea + 1

				bdCalculo4 = bdCalculo1.divide(bdCalculo2, 4,RoundingMode.HALF_UP);
				dbResultDenom = Math.pow(bdCalculo3.doubleValue(),bdCalculo4.doubleValue());
				bdCalculo5 = new BigDecimal(dbResultDenom).subtract(bdUno);

				// modificar el bdSaldoCapital para que vaya reduciendose
				bdInteresCuota = bdSaldo.multiply(bdCalculo5);
				bdInteresCuota = bdInteresCuota.divide(BigDecimal.ONE, 4,RoundingMode.HALF_UP);
				bdInteresCuota.setScale(4, RoundingMode.HALF_UP);// setScale(4,
				// BigDecimal.ROUND_HALF_UP);
				System.out.println("======================= CUOTA NRO " + i	+ " ========================");
				System.out.println("INTERES CUOTA " + bdInteresCuota);
				bdAmortizacion = bdCuotaFinal.subtract(bdInteresCuota);
				bdAmortizacion = bdAmortizacion.divide(BigDecimal.ONE, 4,RoundingMode.HALF_UP);
				bdSumaAmortizacion = bdSumaAmortizacion.add(bdAmortizacion);
				System.out.println("AMORTIZACION  " + bdAmortizacion);


				if(bdSaldo.compareTo(bdAmortizacion)<0){
					bdSaldo = BigDecimal.ZERO;
				}else{
					bdSaldo = bdSaldo.subtract(bdAmortizacion);
					bdSaldo = bdSaldo.divide(BigDecimal.ONE, 4,RoundingMode.HALF_UP);
				}

				listaSaldos.add(i, bdSaldo);
				System.out.println("SALDO   " + bdSaldo);

				if (i + 1 == intNroCuotas) {
					bdSaldo = BigDecimal.ZERO;
					BigDecimal bdSaldoRed = new BigDecimal(0);

					if (intNroCuotas == 1) {
						bdAmortizacion = bdMontoTotalSolicitado;
					} else {
						bdSaldoRed = new BigDecimal(listaSaldos.get(i - 1).toString());
						bdAmortizacion = bdSaldoRed;
					}
				} else {
					bdAmortizacion = bdCuotaFinal.subtract(bdInteresCuota);
					bdAmortizacion = bdAmortizacion.divide(BigDecimal.ONE,4, RoundingMode.HALF_UP);
				}

				bdCuotaMensual = bdAmortizacion.add(bdInteresCuota);
				bdAportes = bdAportes.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);

				// Formando el cronograma comp que es lo que se visualiza en popup
				cronogramaCreditoComp = new CronogramaCreditoComp();
				cronogramaCreditoComp.setStrFechaEnvio(Constante.sdf.format((StringToCalendar(listaDiasEnvio.get(i)	.toString())).getTime()));
				cronogramaCreditoComp.setStrFechaVencimiento(listaDiasVencimiento.get(i).toString());
				cronogramaCreditoComp.setIntDiasTranscurridos(new Integer(listaDias.get(i).toString()));
				cronogramaCreditoComp.setBdSaldoCapital(bdSaldo);
				cronogramaCreditoComp.setBdAmortizacion(bdAmortizacion);
				cronogramaCreditoComp.setBdInteres(bdInteresCuota);
				cronogramaCreditoComp.setBdCuotaMensual(bdCuotaMensual);
				cronogramaCreditoComp.setBdAportes(bdAportes);
				cronogramaCreditoComp.setBdTotalCuotaMensual(bdCuotaMensual.add(bdAportes));
				listaCronogramaCreditoComp.add(cronogramaCreditoComp);				
				
				// Agregando el tipo de Concepto - "Amortización"
				cronogramaCredito = new CronogramaCredito();
				cronogramaCredito.setId(new CronogramaCreditoId());
				cronogramaCredito.setIntNroCuota(i + 1);
				cronogramaCredito.setIntParaTipoCuotaCod(Constante.PARAM_T_TIPOCUOTACRONOGRAMA_NORMAL);
				cronogramaCredito.setIntParaFormaPagoCod(Constante.PARAM_T_FORMADEPAGO_PLANILLA);
				cronogramaCredito.setIntParaTipoConceptoCod(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_AMORTIZACION);
				if (i == 0) {
					bdSaldoMontoCapital = bdMontoTotalSolicitado;
				} else {
					bdSaldoMontoCapital = bdSaldoTemp;
				}
				bdSaldoTemp = bdSaldo;
				cronogramaCredito.setBdMontoCapital(bdSaldoMontoCapital);
				if (i + 1 == intNroCuotas) {
					cronogramaCredito.setBdMontoConcepto(bdSaldoMontoCapital);
				} else {
					cronogramaCredito.setBdMontoConcepto(i + 1 == intNroCuotas ? 
					bdAmortizacion.add(bdMontoTotalSolicitado.subtract(bdAmortizacion)): bdAmortizacion);
				}
				Date fechaVenc = new Date();
				fechaVenc = (StringToCalendar(listaDiasVencimiento.get(i).toString())).getTime();
				cronogramaCredito.setTsFechaVencimiento(new Timestamp(fechaVenc.getTime()));
				cronogramaCredito.setIntPeriodoPlanilla(new Integer(sdfPeriodo.format(fechaVenc.getTime()).toString()));
				cronogramaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				cronogramaCredito.setBdAmortizacionView(cronogramaCreditoComp.getBdAmortizacion());
				cronogramaCredito.setBdAportesView(cronogramaCreditoComp.getBdAportes());
				cronogramaCredito.setBdInteresView(cronogramaCreditoComp.getBdInteres());
				cronogramaCredito.setBdSaldoCapitalView(cronogramaCreditoComp.getBdSaldoCapital());
				Calendar clEnvio= Calendar.getInstance();
				clEnvio = (StringToCalendar(cronogramaCreditoComp.getStrFechaEnvio()));
				cronogramaCredito.setTsFechaEnvioView(new Timestamp(clEnvio.getTime().getTime()));
				lstNuevoCronograma.add(cronogramaCredito);

				// Agregando el tipo de Concepto - "Interés"
				cronogramaCredito = new CronogramaCredito();
				cronogramaCredito.setId(new CronogramaCreditoId());
				cronogramaCredito.setIntNroCuota(i + 1);
				cronogramaCredito.setIntParaTipoCuotaCod(Constante.PARAM_T_TIPOCUOTACRONOGRAMA_NORMAL);
				cronogramaCredito.setIntParaFormaPagoCod(Constante.PARAM_T_FORMADEPAGO_PLANILLA);
				cronogramaCredito.setIntParaTipoConceptoCod(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_INTERES);
				cronogramaCredito.setBdMontoConcepto(bdInteresCuota);
				cronogramaCredito.setTsFechaVencimiento(new Timestamp(fechaVenc.getTime()));
				cronogramaCredito.setIntPeriodoPlanilla(new Integer(sdfPeriodo.format(fechaVenc.getTime())));
				cronogramaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				// CGD-12.11.2013 - nuevos atributos
				cronogramaCredito.setBdAmortizacionView(cronogramaCreditoComp.getBdAmortizacion());
				cronogramaCredito.setBdAportesView(cronogramaCreditoComp.getBdAportes());
				cronogramaCredito.setBdInteresView(cronogramaCreditoComp.getBdInteres());
				cronogramaCredito.setBdSaldoCapitalView(cronogramaCreditoComp.getBdSaldoCapital());
				Calendar clEnvio2= Calendar.getInstance();
				clEnvio2 = (StringToCalendar(cronogramaCreditoComp.getStrFechaEnvio()));
				cronogramaCredito.setTsFechaEnvioView(new Timestamp(clEnvio2.getTime().getTime()));
				lstNuevoCronograma.add(cronogramaCredito);
			}
		} catch (Exception e) {
			log.error("Error en reCalcularCronograma ---> "+e);
		}
		return lstNuevoCronograma;			
	}		
		
	/**
	 * Retorna la fecha del ultimo dia del mes
	 * @param Calendar
	 * @return Date
	 */
	public Date getUltimoDiaDelMes(Calendar fecha) {
		fecha.set(fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH),
				fecha.getActualMaximum(Calendar.DAY_OF_MONTH),
				fecha.getMaximum(Calendar.HOUR_OF_DAY),
				fecha.getMaximum(Calendar.MINUTE),
				fecha.getMaximum(Calendar.SECOND));

		return fecha.getTime();
	}
	
	/**
	 * Retorna el primer dia del mes
	 * @param Calendar
	 * @return Date
	 */
	public Date getPrimerDiaDelMes(Calendar fecha) {

		fecha.set(fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH),
				fecha.getActualMinimum(Calendar.DAY_OF_MONTH),
				fecha.getMinimum(Calendar.HOUR_OF_DAY),
				fecha.getMinimum(Calendar.MINUTE),
				fecha.getMinimum(Calendar.SECOND));

		return fecha.getTime();
	}		
		
	/**
	 * Calcula la fecha del ler Envio y 1er Vencimiento.
	 * Toma en cuenta la existencia de salto al siguiente mes.
	 * @param estructuraDetalle
	 * @param fecHoy
	 * @return listaEnvioVencimiento (0, fec1erEnvio)(1, fec1erVenc);
	 */
	public List calcular1raFechaEnvioVencimiento(EstructuraDetalle estructuraDetalle, Calendar fecHoy) {
		Calendar fecEnvioTemp = Calendar.getInstance();
		String miFechaPrimerVenc = null;
		Calendar fec1erVenc  = Calendar.getInstance();
		Calendar fecVenc1 	 = Calendar.getInstance();
		Calendar fecVenc2 	 = Calendar.getInstance();
		Calendar fecVenc3 	 = Calendar.getInstance();
		Calendar fec1erEnvio = Calendar.getInstance();
		List listaEnvioVencimiento = new ArrayList();
		Integer intMesDummy = 0;
		
		intMesDummy = fecHoy.get(Calendar.MONTH);
		
		if ((fecHoy.get(Calendar.DATE)) < estructuraDetalle.getIntDiaEnviado()) {
			fecVenc1.clear();
			fecVenc1.set(fecHoy.get(Calendar.YEAR), 
						intMesDummy,
						fecHoy.get(Calendar.DATE));
			fecEnvioTemp.clear();
			fecEnvioTemp.set(fecHoy.get(Calendar.YEAR),
							fecHoy.get(Calendar.MONTH),
							fecHoy.get(Calendar.DATE));
						//estructuraDetalle.getIntDiaEnviado());

		} else { // Salta al mes siguiente
			fecVenc1.clear();
			fecVenc1.set(fecHoy.get(Calendar.YEAR),
					intMesDummy + 1, fecHoy.get(Calendar.DATE));
			fecEnvioTemp.clear();
			fecEnvioTemp.set(fecHoy.get(Calendar.YEAR),
					intMesDummy,
					//fecHoy.get(Calendar.MONTH) + 1,
					fecHoy.get(Calendar.DATE));
					//estructuraDetalle.getIntDiaEnviado());
		}
		fec1erEnvio = fecHoy;
		//fec1erEnvio = fecEnvioTemp;

		// fecEnvioTemp;
		// Se recalcula fecha de 1er Vencimiento - Se agrega un mes si intSalto
		// != 1
		if (estructuraDetalle.getIntSaltoEnviado().compareTo(1) == 0) {
			miFechaPrimerVenc = Constante.sdf.format(getUltimoDiaDelMes(
					fecVenc1).getTime());
			fecVenc3 = StringToCalendar(miFechaPrimerVenc);
		} else {
			int dd = fecVenc1.get(Calendar.DATE); // captura día actual
			int mm = fecVenc1.get(Calendar.MONTH); // captura mes actual
			int aa = fecVenc1.get(Calendar.YEAR); // captura año actual

			fecVenc2.set(aa, mm + 1, dd);
			miFechaPrimerVenc = Constante.sdf.format(getUltimoDiaDelMes(
					fecVenc2).getTime());
			fecVenc3 = StringToCalendar(miFechaPrimerVenc);
		}
		fec1erVenc.clear();
		fec1erVenc = fecVenc3;
		//fec1erEnvio.set(fec1erEnvio.get(Calendar.YEAR),	fec1erEnvio.get(Calendar.MONTH),estructuraDetalle.getIntDiaEnviado());
		//fec1erEnvio.set(fecHoy.get(Calendar.YEAR),	fecHoy.get(Calendar.MONTH),fecHoy.get(Calendar.DATE));

		listaEnvioVencimiento.add(0, fec1erEnvio);
		listaEnvioVencimiento.add(1, fec1erVenc);

		return listaEnvioVencimiento;
	}		
		
	/**
	 * Carga la variable global: listaCuentaConcepto.
	 * @param socioComp
	 */
	public List<CuentaConcepto> recuperarCuentasConceptoSocio (SocioComp socioComp){
		List<CuentaConcepto> listaCtaCto = null;
		List<CuentaConcepto> listaCuentaConcepto = null;
		
		ConceptoFacadeRemote conceptoFacade = null;
		try {
			conceptoFacade = (ConceptoFacadeRemote) EJBFactory.getRemote(ConceptoFacadeRemote.class);
			
			listaCuentaConcepto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(socioComp.getCuenta().getId());
			if(listaCuentaConcepto != null && !listaCuentaConcepto.isEmpty()){
				listaCtaCto = new ArrayList<CuentaConcepto>();
				listaCtaCto = listaCuentaConcepto ;
			}
		} catch (Exception e) {
			log.error("Error en recuperarCuentasConceptoSocio ---> "+e);
		}
		return listaCtaCto;
	}

	/**
	 * Recupera Cuenta COncepto segun parametro.
	 * @param intTipoCuentaConcepto
	 * @param listaCuentaConcepto
	 * @return
	 * @throws BusinessException
	 */
	public CuentaConcepto recuperarCuentasConceptoXCuentaYTipo(Integer intTipoCuentaConcepto, List<CuentaConcepto> listaCuentaConcepto)throws BusinessException{
		CuentaConcepto cuentaConceptoReturn = null;
		
		try {
			if(listaCuentaConcepto != null && !listaCuentaConcepto.isEmpty()){
					
					for (CuentaConcepto cuentaConcepto : listaCuentaConcepto) {
						CuentaConceptoDetalle detalle = null;
						
						if(cuentaConcepto.getListaCuentaConceptoDetalle() != null 
						&& !cuentaConcepto.getListaCuentaConceptoDetalle().isEmpty()){
							detalle = new CuentaConceptoDetalle();
							detalle = cuentaConcepto.getListaCuentaConceptoDetalle().get(0);
			
							if(detalle.getIntParaTipoConceptoCod().compareTo(intTipoCuentaConcepto)==0){
								cuentaConceptoReturn = new CuentaConcepto();
								cuentaConceptoReturn = cuentaConcepto ;
								break;
							}
						}		
					}
			}
			
		} catch (Exception e) {
			log.error("Error en recuperarCuentasConceptoXCuentaYTipo ---> "+e);
		}
		
		return cuentaConceptoReturn;
	}
		
	/**
	 * 
	 * @param ctaCtoAporte
	 * @return
	 */
	public BigDecimal recuperarAportes(CuentaConcepto ctaCtoAporte){
		BigDecimal bdAportes= BigDecimal.ZERO;
		try {
			
			if(ctaCtoAporte != null){
				if(ctaCtoAporte.getListaCuentaConceptoDetalle() != null && !ctaCtoAporte.getListaCuentaConceptoDetalle().isEmpty()){
					
					
					for (CuentaConceptoDetalle ctaCtoDet : ctaCtoAporte.getListaCuentaConceptoDetalle()) {
						Boolean blnContinua = Boolean.TRUE;
						
						// se valida el inicio
						if(ctaCtoDet.getTsInicio()!= null){
							if(ctaCtoDet.getTsInicio().before(new Timestamp(new Date().getTime()))){
								blnContinua = Boolean.TRUE;
							}else{
								blnContinua = Boolean.FALSE;
							}
							if(blnContinua){
								if(ctaCtoDet.getTsFin()!= null){
									if(ctaCtoDet.getTsFin().after(new Timestamp(new Date().getTime()))){
										blnContinua = Boolean.FALSE;	
									}
								}else{
									blnContinua = Boolean.TRUE;
								}
							}
						}else{
							blnContinua = Boolean.FALSE;
						}

						if(blnContinua){
							bdAportes = bdAportes.add(ctaCtoDet.getBdMontoConcepto());
						}
					}
				}
			}
			
		} catch (Exception e) {
			log.error("Error en recuperarAportes ---> "+e);
		}
		return bdAportes;
	}
		
	/**
	 * Compara dos DATE a nivel de dd/MM/yyyy.
	 * Devuelve TRUE si son IGUALES.
	 * Devuelve FALSE si son DSITINTOS.
	 * @param dateUno
	 * @param dateDos
	 * @param intTipoValidacion
	 * @return
	 */
	 public  Boolean comparaDatesDDMMYYYY(Date dateUno, Date dateDos, Integer intTipoValidacion) {
	   	 DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	   	 Boolean blnValido = Boolean.FALSE;
	   	 try {
	   		intTipoValidacion = 1;
	   		
	   		 switch (intTipoValidacion) {
			// valida que el date 2 puede ser solo mayor o igual al date 1
	   		 case 1:	
		   			 if (df.format(dateUno).equals(df.format(dateDos))){
		   				blnValido = Boolean.TRUE;
		   			 }
		   			 break;
				
	   		 }
	   		 
		} catch (Exception e) {
			log.error("Error en comparaDatesDDMMYYYY ---> "+e);
		}
		return blnValido; 
	}		 
		 
	/**
	 * Recupera la estrucutura detyalle en base al socio estructura
	 * @param socioEstructuraOrigen
	 * @return
	 */
	 public EstructuraDetalle recuperarEstructuraDetalle (SocioEstructura socioEstructuraOrigen){
		 
		 EstructuraDetalle estructuraDetalle = null;
		 EstructuraFacadeRemote estructuraFacade = null;
		 try {
			 estructuraFacade= (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
				if (socioEstructuraOrigen != null) {
					estructuraDetalle = new EstructuraDetalle();	
					estructuraDetalle.setId(new EstructuraDetalleId());
					estructuraDetalle.getId().setIntNivel(socioEstructuraOrigen.getIntNivel());
					estructuraDetalle.getId().setIntCodigo(socioEstructuraOrigen.getIntCodigo());
					estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(estructuraDetalle.getId(), 
							socioEstructuraOrigen.getIntTipoSocio(), 
							socioEstructuraOrigen.getIntModalidad());
				}
				
		} catch (Exception e) {
			log.error("Error en recuperarEstructuraDetalle ---> "+e);
		}
		return estructuraDetalle;			 
	 }
		 
	/**
	 * Define de acuerdo al valor de MONTO A CANCELAR si se aplica cronograma contado o crnograma adelanto.
	 * @param strMontoACancelar
	 * @return
	 */
	public Boolean recuperarTipoCronograma(String strMontoACancelar){
		Boolean blnCronogramaNormal= Boolean.TRUE;
		try {
			
			if(strMontoACancelar != null && !strMontoACancelar.equalsIgnoreCase("0.00") && strMontoACancelar.length()>0){
				blnCronogramaNormal = Boolean.FALSE;
			}
		} catch (Exception e) {
			log.error("Error en recuperarTipoCronograma --> "+e);
		}

		return blnCronogramaNormal;
	}

	/**
	 * Genera el cronogramade una actividad al contado.
	 * @param creditoSeleccionado
	 * @param estructuraDetalle
	 * @param calFechaRegistro
	 */
	public List<CronogramaCredito> generarCronogramaContado(Credito creditoSeleccionado,EstructuraDetalle estructuraDetalle, Calendar calFechaRegistro, ExpedienteCredito expedienteCredito , CreditoInteres creditoInteresExpediente) {
		
		CronogramaCredito cronogramaCredito = new CronogramaCredito();
		List<CronogramaCredito> listaCronogramaCredito = new ArrayList<CronogramaCredito>();
		SimpleDateFormat sdfPeriodo = new SimpleDateFormat("yyyyMM");
		
		BigDecimal bdMontoGenerarCronograma = BigDecimal.ZERO;
		Integer intNroCuotas = 0;
		try {
			// se agrega la primera cuota el monto q cancelo...
			bdMontoGenerarCronograma = creditoInteresExpediente.getBdMontoMaximo();

			intNroCuotas = new Integer(1);
			// Cuota CERO -  TRANSFERECNIA.
			bdMontoGenerarCronograma = bdMontoGenerarCronograma.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
			
			// generando cronograma para guardar
			cronogramaCredito = new CronogramaCredito();
			cronogramaCredito.setId(new CronogramaCreditoId());
			cronogramaCredito.getId().setIntCuentaPk(expedienteCredito.getId().getIntCuentaPk());
			cronogramaCredito.getId().setIntItemDetExpediente(expedienteCredito.getId().getIntItemDetExpediente());
			cronogramaCredito.getId().setIntItemExpediente(expedienteCredito.getId().getIntItemExpediente());
			cronogramaCredito.getId().setIntPersEmpresaPk(expedienteCredito.getId().getIntPersEmpresaPk());
			cronogramaCredito.setIntNroCuota(0);
			cronogramaCredito.setIntParaTipoCuotaCod(Constante.PARAM_T_TIPOCUOTACRONOGRAMA_CONTADO);
			cronogramaCredito.setIntParaFormaPagoCod(Constante.PARAM_T_FORMADEPAGO_CAJA);
			cronogramaCredito.setIntParaTipoConceptoCod(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_AMORTIZACION);
			cronogramaCredito.setBdMontoConcepto(bdMontoGenerarCronograma);
			cronogramaCredito.setBdMontoCapital(bdMontoGenerarCronograma);
			//cronogramaCredito.setTsFechaVencimiento(new Timestamp(new Date().getTime()));
			//cronogramaCredito.setIntPeriodoPlanilla(new Integer(sdfPeriodo.format(new Date().getTime()).toString()));
			cronogramaCredito.setTsFechaVencimiento(new Timestamp(calFechaRegistro.getTime().getTime()));
			cronogramaCredito.setIntPeriodoPlanilla(new Integer(sdfPeriodo.format(calFechaRegistro.getTime().getTime()).toString()));
			cronogramaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			listaCronogramaCredito.add(cronogramaCredito);
			log.info("Nro. Cuotas: "+intNroCuotas);
		} catch (Exception e) {
			log.error("Error al generarCronogramaAdelantoTotal() ---> "+e);
			e.printStackTrace();
		}
		return listaCronogramaCredito;
	}			
			
	/**
	 * Se genera el cronograma del tipo credito cuando no se ingresa adelanto.
	 * @param creditoSeleccionado
	 * @param estructuraDetalle
	 */
	public List<CronogramaCredito> generarCronogramaNormal(Credito creditoSeleccionado,EstructuraDetalle estructuraDetalle, Integer intNumeroDeCuotas, Calendar calFechaRegistro, SocioComp beanSocioComp, ExpedienteCredito expedienteCredito, String strMontoACancelar) {
		
		Envioconcepto envioConcepto = null;
		//
		Calendar miCal = Calendar.getInstance();
		miCal.clear();
		miCal.set(calFechaRegistro.get(Calendar.YEAR), calFechaRegistro.get(Calendar.MONTH), calFechaRegistro.get(Calendar.DATE));
		
		String strPeriodoPlla = "";
		CronogramaCredito cronogramaCredito = new CronogramaCredito();
		//CronogramaCreditoComp cronogramaCreditoComp = null;
		List<CronogramaCredito> listaCronogramaCredito = null;
		
		Calendar fec1erEnvio = Calendar.getInstance();
		fec1erEnvio.clear();
		fec1erEnvio.set(calFechaRegistro.get(Calendar.YEAR), calFechaRegistro.get(Calendar.MONTH), calFechaRegistro.get(Calendar.DATE));
		
		Calendar envio = Calendar.getInstance();
		envio.clear();
		envio.set(calFechaRegistro.get(Calendar.YEAR), calFechaRegistro.get(Calendar.MONTH), calFechaRegistro.get(Calendar.DATE));
		
		SimpleDateFormat sdfPeriodo = new SimpleDateFormat("yyyyMM");
		
		BigDecimal bdMontoGenerarCronograma = BigDecimal.ZERO;
		BigDecimal bdMontoPrestamo = expedienteCredito.getBdMontoSolicitado();
		BigDecimal bdMontoCuota = BigDecimal.ZERO;
		BigDecimal bdMontoUltimaCuota = BigDecimal.ZERO;
		Date fechaVenc = new Date();
//				BigDecimal bdMontoCancelarTx = BigDecimal.ZERO;
		MathContext mc1 = new MathContext(2, RoundingMode.HALF_UP);  
//				Integer intNroCuotasActual = 0;//  intNroCuotas
		
		Calendar fechaUno = Calendar.getInstance();
		fechaUno.clear();
		fechaUno.set(calFechaRegistro.get(Calendar.YEAR), calFechaRegistro.get(Calendar.MONTH), calFechaRegistro.get(Calendar.DATE));
		Integer fechaUnoDia, fechaMes, fechaUnoAnno;
		String strFechaMes="";
		String strFechaDia="";
		
		Integer intNroRecalculado = -1;
		
		PlanillaFacadeRemote planillaFacade= null;
		Integer intNroCuotas = 0;

		try {
			planillaFacade = (PlanillaFacadeRemote)EJBFactory.getRemote(PlanillaFacadeRemote.class);
			intNroCuotas = expedienteCredito.getIntNumeroCuota();
			
			while (intNroRecalculado.compareTo(0)!=0){
										
				listaCronogramaCredito = new ArrayList<CronogramaCredito>();
				//cronogramaCreditoComp = new CronogramaCreditoComp();
				
				fechaUnoDia = fechaUno.get(Calendar.DATE);
				fechaMes = fechaUno.get(Calendar.MONTH);
				fechaMes = fechaMes + 1 ;
				
				if(fechaMes.toString().length() == 1){
					strFechaMes = "0"+fechaMes;
				}else{
					strFechaMes = ""+fechaMes;
				}
				
				if(fechaUnoDia.toString().length() == 1){
					strFechaDia = "0"+fechaUnoDia;
				}else{
					strFechaDia = ""+fechaUnoDia;
				}
				
				
				
				fechaUnoAnno = fechaUno.get(Calendar.YEAR);
				
				recuperarTipoCronograma(strMontoACancelar);
				
				bdMontoGenerarCronograma = expedienteCredito.getBdMontoSolicitado();
				bdMontoCuota = bdMontoGenerarCronograma.divide(new BigDecimal(intNroCuotas),mc1);	

				// cgd - 02.09.2013
				envioConcepto = planillaFacade.getEnvioConceptoPorEmpPerCta(Constante.PARAM_EMPRESASESION, 
																			obtenerPeriodoActual(),
																			beanSocioComp.getCuenta().getId().getIntCuenta());

				// detalle movimiento
				int dia = miCal.get(Calendar.DATE);
				int mes = miCal.get(Calendar.MONTH);
				int anno = miCal.get(Calendar.YEAR);

				Calendar fecHoy = Calendar.getInstance();
				fecHoy.clear();
				fecHoy.set(calFechaRegistro.get(Calendar.YEAR), calFechaRegistro.get(Calendar.MONTH), calFechaRegistro.get(Calendar.DATE));
				Calendar fec1erVenc = Calendar.getInstance();
				fec1erVenc.clear();
				fec1erVenc.set(calFechaRegistro.get(Calendar.YEAR), calFechaRegistro.get(Calendar.MONTH), calFechaRegistro.get(Calendar.DATE));

				// no se realiza calculo de fechas de envio y vencimiento...
				if (envioConcepto != null) {
					strPeriodoPlla = "" + envioConcepto.getIntPeriodoplanilla();

					// substring x---o x--->
					Calendar calendarTemp = Calendar.getInstance();
					calendarTemp.clear();
					calendarTemp.set(calFechaRegistro.get(Calendar.YEAR), calFechaRegistro.get(Calendar.MONTH), calFechaRegistro.get(Calendar.DATE));

					int intUltimoMesPlla = Integer.parseInt(strPeriodoPlla.substring(4, 6));
					int intUltimoAnnoPlla = Integer.parseInt(strPeriodoPlla.substring(0,4));
					int intPrimerDiaPlla = Integer.parseInt("01");

					if (intUltimoMesPlla == 12) {
						intUltimoAnnoPlla = intUltimoAnnoPlla + 1;
						//intUltimoMesPlla = 0;
						intUltimoMesPlla = 1;
					}

					// Definiendo fecha de 1er envio y 1er vencimiento
					fec1erEnvio.clear();
					fec1erEnvio.set(intUltimoAnnoPlla, intUltimoMesPlla, intPrimerDiaPlla);

					calendarTemp.set(fec1erEnvio.get(Calendar.YEAR),
					fec1erEnvio.get(Calendar.MONTH),
					fec1erEnvio.get(Calendar.DATE));
					fec1erVenc.setTime(getUltimoDiaDelMes(calendarTemp));

				} else {
					// fecHoy.clear();
					fecHoy.set(anno, mes, dia);
					List listaEnvioVencimiento = new ArrayList();

					listaEnvioVencimiento.addAll(calcular1raFechaEnvioVencimientoOriginal(estructuraDetalle, fecHoy));
					//fec1erEnvio = (Calendar) listaEnvioVencimiento.get(0);
					//fec1erVenc = (Calendar) listaEnvioVencimiento.get(1);
					fec1erVenc = (Calendar) listaEnvioVencimiento.get(0);
				}

				
				// Calculamos el resto de dias de envio y vencimiento en base a
				List listaDiasVencimiento = new ArrayList();
				int vencDia, vencMes, vencAnno;
				vencDia = fec1erVenc.get(Calendar.DATE);
				vencMes = fec1erVenc.get(Calendar.MONTH);
				vencAnno = fec1erVenc.get(Calendar.YEAR);

				for (int i = 0; i < intNumeroDeCuotas; i++) {
					Calendar nuevoDia = Calendar.getInstance();
					nuevoDia.clear();
					nuevoDia.set(calFechaRegistro.get(Calendar.YEAR), calFechaRegistro.get(Calendar.MONTH), calFechaRegistro.get(Calendar.DATE));
					
					nuevoDia.clear();
					nuevoDia.set(vencAnno, vencMes, 15);
					if (vencMes == 12) {
						vencAnno = vencAnno + 1;
						vencMes = 0;
						listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
					} else {
						listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
					}
					
					vencMes++;
				}

				BigDecimal bdAmortizacion = new BigDecimal(0);
				BigDecimal bdSumaAmortizacion = BigDecimal.ZERO;
				BigDecimal bdSumaFaltaCancelar = expedienteCredito.getBdMontoSolicitado();

				for (int i = 0; i < intNroCuotas; i++) {
					bdAmortizacion = bdMontoCuota;
					
					bdSumaAmortizacion = bdSumaAmortizacion.add(bdAmortizacion);
					
					// Si es la ultima cuota realizamos ajuste de centimos
					if(i + 1 == intNroCuotas){
						if(bdMontoPrestamo.compareTo(bdSumaAmortizacion)!=0){
							bdMontoUltimaCuota = bdMontoPrestamo.subtract(bdSumaAmortizacion.subtract(bdAmortizacion));
							//bdMontoUltimaCuota = bdMontoPrestamo.subtract(bdSumaAmortizacion);
							bdAmortizacion = bdMontoUltimaCuota;
							bdAmortizacion = bdAmortizacion.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
							bdSumaFaltaCancelar = bdAmortizacion;
						}
					}

						// generando cronograma para guardar
						cronogramaCredito = new CronogramaCredito();
						cronogramaCredito.setId(new CronogramaCreditoId());
						cronogramaCredito.getId().setIntCuentaPk(expedienteCredito.getId().getIntCuentaPk());
						cronogramaCredito.getId().setIntItemDetExpediente(expedienteCredito.getId().getIntItemDetExpediente());
						cronogramaCredito.getId().setIntItemExpediente(expedienteCredito.getId().getIntItemExpediente());
						cronogramaCredito.getId().setIntPersEmpresaPk(expedienteCredito.getId().getIntPersEmpresaPk());
						cronogramaCredito.setIntNroCuota(i + 1);
						cronogramaCredito.setIntParaTipoCuotaCod(Constante.PARAM_T_TIPOCUOTACRONOGRAMA_NORMAL);
						cronogramaCredito.setIntParaFormaPagoCod(Constante.PARAM_T_FORMADEPAGO_PLANILLA);
						cronogramaCredito.setIntParaTipoConceptoCod(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_AMORTIZACION);
						cronogramaCredito.setBdMontoConcepto(bdAmortizacion);
						cronogramaCredito.setBdMontoCapital(bdSumaFaltaCancelar);

						fechaVenc = (StringToCalendar(listaDiasVencimiento.get(i).toString())).getTime();

						cronogramaCredito.setTsFechaVencimiento(new Timestamp(fechaVenc.getTime()));
						cronogramaCredito.setIntPeriodoPlanilla(new Integer(sdfPeriodo.format(fechaVenc.getTime()).toString()));
						cronogramaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						listaCronogramaCredito.add(cronogramaCredito);
						
						// Generando Cronograma para mostrar
						//cronogramaCreditoComp = new CronogramaCreditoComp();
						//cronogramaCreditoComp.setStrFechaVencimiento(listaDiasVencimiento.get(i).toString());
						//cronogramaCreditoComp.setBdAmortizacion(bdAmortizacion);
						//cronogramaCreditoComp.setBdSaldoCapital(bdSumaFaltaCancelar);
						//cronogramaCreditoComp.setBdCuotaMensual(bdAmortizacion);
						//cronogramaCreditoComp.setBdTotalCuotaMensual(BigDecimal.ZERO); //  movimiento
						//cronogramaCreditoComp.setIntParaTipoFormapago(Constante.PARAM_T_FORMADEPAGO_PLANILLA);
						//cronogramaCreditoComp.setCronogramaCredito(cronogramaCredito);
						//listaCronogramaCreditoComp.add(cronogramaCreditoComp);
						
						bdSumaFaltaCancelar = bdSumaFaltaCancelar.subtract(bdAmortizacion);

					//}
					
				}
				
				intNroRecalculado = validarRecalculoCronograma(intNroCuotas, listaCronogramaCredito, creditoSeleccionado);
				if(intNroRecalculado.compareTo(0)!=0){
					intNroCuotas = intNroRecalculado - 1;
				}
			};
			
			
		} catch (Exception e) {
			log.error("Error al generarCronograma() ---> "+e);
			e.printStackTrace();
		}
		return listaCronogramaCredito;
	}
			
	/**
	 * En el caso que el adelanto sea del mismo valor que la configuracion del monto.
	 * @param creditoSeleccionado
	 * @param estructuraDetalle
	 */
	public List<CronogramaCredito> generarCronogramaAdelantoTotal(Credito creditoSeleccionado,EstructuraDetalle estructuraDetalle, Calendar calFechaRegistro, ExpedienteCredito expedienteCredito, String strMontoACancelar) {
			CronogramaCredito cronogramaCredito = new CronogramaCredito();
			//CronogramaCreditoComp cronogramaCreditoComp = new CronogramaCreditoComp();
			List<CronogramaCredito> listaCronogramaCredito = new ArrayList<CronogramaCredito>();

			SimpleDateFormat sdfPeriodo = new SimpleDateFormat("yyyyMM");
			
			BigDecimal bdMontoGenerarCronograma = BigDecimal.ZERO;
			BigDecimal bdMontoPrestamo = expedienteCredito.getBdMontoSolicitado();
			BigDecimal bdMontoCuota = BigDecimal.ZERO;
			MathContext mc1 = new MathContext(2, RoundingMode.HALF_UP);  
			String strFechaMes="";
			String strFechaDia="";
			Integer intNroCuotas = 0;
			
			Double dMontoCancelar = new Double(strMontoACancelar);
			BigDecimal bdMontoCancelar = new BigDecimal(dMontoCancelar);
			try {

				recuperarTipoCronograma(strMontoACancelar);

					// se agrega la primera cuota el monto q cancelo...
					bdMontoGenerarCronograma = new BigDecimal(strMontoACancelar);
					//bdMontoCuota = bdMontoGenerarCronograma.divide(new BigDecimal(intNroCuotas - 1),2,RoundingMode.HALF_UP);

					bdMontoCuota = bdMontoGenerarCronograma;
					bdMontoGenerarCronograma = bdMontoCuota;
					intNroCuotas = new Integer(1);

				// y se conforma el cronograma
				//BigDecimal bdAmortizacion = bdMontoGenerarCronograma;
				BigDecimal bdSumaAmortizacion = BigDecimal.ZERO;
				BigDecimal bdSumaFaltaCancelar = bdMontoPrestamo;

					// Cuota CERO -  TRANSFERECNIA.
					bdMontoCancelar = bdMontoCancelar.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
					bdSumaAmortizacion = bdSumaAmortizacion.add(bdMontoCancelar);
					
					// generando cronograma para guardar
					cronogramaCredito = new CronogramaCredito();
					cronogramaCredito.setId(new CronogramaCreditoId());
					cronogramaCredito.getId().setIntCuentaPk(expedienteCredito.getId().getIntCuentaPk());
					cronogramaCredito.getId().setIntItemDetExpediente(expedienteCredito.getId().getIntItemDetExpediente());
					cronogramaCredito.getId().setIntItemExpediente(expedienteCredito.getId().getIntItemExpediente());
					cronogramaCredito.getId().setIntPersEmpresaPk(expedienteCredito.getId().getIntPersEmpresaPk());
					cronogramaCredito.setIntNroCuota(0);
					cronogramaCredito.setIntParaTipoCuotaCod(Constante.PARAM_T_TIPOCUOTACRONOGRAMA_REGULARIZACION);
					cronogramaCredito.setIntParaFormaPagoCod(0);
					cronogramaCredito.setIntParaTipoConceptoCod(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_AMORTIZACION);
					cronogramaCredito.setBdMontoConcepto(bdMontoCancelar);
					cronogramaCredito.setBdMontoCapital(bdSumaFaltaCancelar);
					//cronogramaCredito.setTsFechaVencimiento(new Timestamp(new Date().getTime()));
					//cronogramaCredito.setIntPeriodoPlanilla(new Integer(sdfPeriodo.format(new Date().getTime()).toString()));
					cronogramaCredito.setTsFechaVencimiento(new Timestamp(calFechaRegistro.getTime().getTime()));
					cronogramaCredito.setIntPeriodoPlanilla(new Integer(sdfPeriodo.format(calFechaRegistro.getTime().getTime()).toString()));
					cronogramaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					listaCronogramaCredito.add(cronogramaCredito);

			} catch (Exception e) {
				log.error("Error al generarCronogramaAdelantoTotal() ---> "+e);
				e.printStackTrace();
			}
			return listaCronogramaCredito;

	}			
	
	/**
	 * Genera el cronograma en elcaso que sea CRedito y exista un adelanto distinto a CERO y al MOnto Totald e la Actividad.
	 * @param creditoSeleccionado
	 * @param estructuraDetalle
	 */
	public List<CronogramaCredito> generarCronogramaAdelanto(Credito creditoSeleccionado,EstructuraDetalle estructuraDetalle, Integer intNumeroDeCuotas, Calendar calFechaRegistro, SocioComp beanSocioComp, ExpedienteCredito expedienteCredito, String strMontoACancelar) {
		
		Envioconcepto envioConcepto = null;
		Calendar miCal = Calendar.getInstance();
		miCal.clear();
		miCal.set(calFechaRegistro.get(Calendar.YEAR), calFechaRegistro.get(Calendar.MONTH), calFechaRegistro.get(Calendar.DATE));
		
		String strPeriodoPlla = "";
		CronogramaCredito cronogramaCredito = new CronogramaCredito();
		//CronogramaCreditoComp cronogramaCreditoComp = null;
		List<CronogramaCredito> listaCronogramaCredito = null;
		
		Calendar fec1erEnvio = Calendar.getInstance();
		fec1erEnvio.clear();
		fec1erEnvio.set(calFechaRegistro.get(Calendar.YEAR), calFechaRegistro.get(Calendar.MONTH), calFechaRegistro.get(Calendar.DATE));
		//Calendar envio = Calendar.getInstance();
		SimpleDateFormat sdfPeriodo = new SimpleDateFormat("yyyyMM");
		
		BigDecimal bdMontoGenerarCronograma = BigDecimal.ZERO;
		BigDecimal bdMontoPrestamo = expedienteCredito.getBdMontoSolicitado();
		BigDecimal bdMontoCuota = BigDecimal.ZERO;
		BigDecimal bdMontoUltimaCuota = BigDecimal.ZERO;
		Date fechaVenc = new Date();
//				BigDecimal bdMontoCancelarTx = BigDecimal.ZERO;
		//BigDecimal bdMontoGenerarCronograma = BigDecimal.ZERO;
		MathContext mc1 = new MathContext(2, RoundingMode.HALF_UP);  
		//Integer intCuotasMenosUno = 0;
//				Integer intNroCuotasActual = 0;//  intNroCuotas
		
		Calendar fechaUno = Calendar.getInstance();
		fechaUno.clear();
		fechaUno.set(calFechaRegistro.get(Calendar.YEAR), calFechaRegistro.get(Calendar.MONTH), calFechaRegistro.get(Calendar.DATE));
		Integer fechaUnoDia, fechaMes, fechaUnoAnno;
		String strFechaMes="";
		String strFechaDia="";
		
		Integer intNroRecalculado = -1;
		Integer intNroCuotas = 0;
		PlanillaFacadeRemote planillaFacade = null;
		Double dMontoCancelar = new Double(strMontoACancelar);
		BigDecimal bdMontoCancelar = new BigDecimal(dMontoCancelar);
		
		try {
			planillaFacade = (PlanillaFacadeRemote)EJBFactory.getRemote(PlanillaFacadeRemote.class);
			intNroCuotas = expedienteCredito.getIntNumeroCuota();
			
			while (intNroRecalculado.compareTo(0)!=0){
			
			//limpiarCronogramasGeneracion();
			
			listaCronogramaCredito = new ArrayList<CronogramaCredito>();
			//cronogramaCreditoComp = new CronogramaCreditoComp();
			
			fechaUnoDia = fechaUno.get(Calendar.DATE);
			fechaMes = fechaUno.get(Calendar.MONTH);
			fechaMes = fechaMes + 1 ;
			
			if(fechaMes.toString().length() == 1){
				strFechaMes = "0"+fechaMes;
			}else{
				strFechaMes = ""+fechaMes;
			}
			
			if(fechaUnoDia.toString().length() == 1){
				strFechaDia = "0"+fechaUnoDia;
			}else{
				strFechaDia = ""+fechaUnoDia;
			}
			
			
			
			fechaUnoAnno = fechaUno.get(Calendar.YEAR);
			
			recuperarTipoCronograma(strMontoACancelar);

				// se agrega la primera cuota el monto q cancelo...
				bdMontoGenerarCronograma = expedienteCredito.getBdMontoSolicitado().subtract(bdMontoCancelar);
				//bdMontoCuota = bdMontoGenerarCronograma.divide(new BigDecimal(intNroCuotas - 1),2,RoundingMode.HALF_UP);
				bdMontoCuota = bdMontoGenerarCronograma.divide(new BigDecimal(intNroCuotas),2,RoundingMode.HALF_UP);

			// CGD - 02.09.2013	
				envioConcepto = planillaFacade.getEnvioConceptoPorEmpPerCta(Constante.PARAM_EMPRESASESION, 
						obtenerPeriodoActual(),
						beanSocioComp.getCuenta().getId().getIntCuenta());

			//envioConcepto = planillaFacade.getEnvioconceptoPorPkMaxPeriodo(envioConcepto.getId());
			
			// detalle movimiento
			int dia = miCal.get(Calendar.DATE);
			int mes = miCal.get(Calendar.MONTH);
			int anno = miCal.get(Calendar.YEAR);

			Calendar fecHoy = Calendar.getInstance();
			fecHoy.clear();
			fecHoy.set(calFechaRegistro.get(Calendar.YEAR), calFechaRegistro.get(Calendar.MONTH), calFechaRegistro.get(Calendar.DATE));
			
			Calendar fec1erVenc = Calendar.getInstance();
			fec1erVenc.clear();
			fec1erVenc.set(calFechaRegistro.get(Calendar.YEAR), calFechaRegistro.get(Calendar.MONTH), calFechaRegistro.get(Calendar.DATE));

			// no se realiza calculo de fechas de envio y vencimiento...
			if (envioConcepto != null) {
				strPeriodoPlla = "" + envioConcepto.getIntPeriodoplanilla();

				// substring x---o x--->
				Calendar calendarTemp = Calendar.getInstance();
				calendarTemp.clear();
				calendarTemp.set(calFechaRegistro.get(Calendar.YEAR), calFechaRegistro.get(Calendar.MONTH), calFechaRegistro.get(Calendar.DATE));
				
				int intUltimoMesPlla = Integer.parseInt(strPeriodoPlla.substring(4, 6));
				int intUltimoAnnoPlla = Integer.parseInt(strPeriodoPlla.substring(0,4));
				int intPrimerDiaPlla = Integer.parseInt("01");

				if (intUltimoMesPlla == 12) {
					intUltimoAnnoPlla = intUltimoAnnoPlla + 1;
					//intUltimoMesPlla = 0;
					intUltimoMesPlla = 1;
				}

				// Definiendo fecha de 1er envio y 1er vencimiento
				fec1erEnvio.clear();
				fec1erEnvio.set(intUltimoAnnoPlla, intUltimoMesPlla, intPrimerDiaPlla);

				calendarTemp.set(fec1erEnvio.get(Calendar.YEAR),
				fec1erEnvio.get(Calendar.MONTH),
				fec1erEnvio.get(Calendar.DATE));
				fec1erVenc.setTime(getUltimoDiaDelMes(calendarTemp));

			} else {
				// fecHoy.clear();
				fecHoy.set(anno, mes, dia);
				List listaEnvioVencimiento = new ArrayList();

				listaEnvioVencimiento.addAll(calcular1raFechaEnvioVencimientoOriginal(estructuraDetalle, fecHoy));
				//fec1erEnvio = (Calendar) listaEnvioVencimiento.get(0);
				fec1erVenc = (Calendar) listaEnvioVencimiento.get(0);
			}

			
			// Calculamos el resto de dias de envio y vencimiento en base a
			List listaDiasVencimiento = new ArrayList();
			int vencDia, vencMes, vencAnno;
			vencDia = fec1erVenc.get(Calendar.DATE);
			vencMes = fec1erVenc.get(Calendar.MONTH);
			vencAnno = fec1erVenc.get(Calendar.YEAR);

			for (int i = 0; i < intNumeroDeCuotas; i++) {
				Calendar nuevoDia = Calendar.getInstance();
				nuevoDia.clear();
				nuevoDia.set(calFechaRegistro.get(Calendar.YEAR), calFechaRegistro.get(Calendar.MONTH), calFechaRegistro.get(Calendar.DATE));
				
				// CGD - 02.09.2013 - XXX
				nuevoDia.clear();
				nuevoDia.set(vencAnno, vencMes, 15);
				if (vencMes == 12) {
					listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
					vencAnno = vencAnno + 1;
					vencMes = 0;
				} else {
					listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
				}
				
				vencMes++;
			}

			/**
			 * Valor de la cuota Mensual
			 */
			BigDecimal bdAmortizacion = new BigDecimal(0); //la cuota mensual a pagar
			BigDecimal bdSumaAmortizacion = BigDecimal.ZERO;	// loq ue se va pagando
			BigDecimal bdSumaFaltaCancelar = expedienteCredito.getBdMontoSolicitado(); // se inicaliza con el valor total de actividad
			
			
			for (int i = 0; i < intNroCuotas; i++) {
				bdAmortizacion = bdMontoCuota;
				
				// blnCronogramaNormal = true es cuando no hay monto a cancelar. Si es normal
				if(i==0){
					
					// Cuota CERO -  TRANSFERECNIA.
					bdMontoCancelar = bdMontoCancelar.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);

					// generando cronograma para guardar
					cronogramaCredito = new CronogramaCredito();
					cronogramaCredito.setId(new CronogramaCreditoId());
					cronogramaCredito.getId().setIntCuentaPk(expedienteCredito.getId().getIntCuentaPk());
					cronogramaCredito.getId().setIntItemDetExpediente(expedienteCredito.getId().getIntItemDetExpediente());
					cronogramaCredito.getId().setIntItemExpediente(expedienteCredito.getId().getIntItemExpediente());
					cronogramaCredito.getId().setIntPersEmpresaPk(expedienteCredito.getId().getIntPersEmpresaPk());
					cronogramaCredito.setIntNroCuota(i);
					cronogramaCredito.setIntParaTipoCuotaCod(Constante.PARAM_T_TIPOCUOTACRONOGRAMA_REGULARIZACION);
					cronogramaCredito.setIntParaFormaPagoCod(0);
					cronogramaCredito.setIntParaTipoConceptoCod(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_AMORTIZACION);
					cronogramaCredito.setBdMontoConcepto(bdMontoCancelar);
					cronogramaCredito.setBdMontoCapital(bdSumaFaltaCancelar);
					//fechaVenc = (StringToCalendar(listaDiasVencimiento.get(i).toString())).getTime();
					//cronogramaCredito.setTsFechaVencimiento(new Timestamp( new Date().getTime()));
					//cronogramaCredito.setIntPeriodoPlanilla(new Integer(sdfPeriodo.format(new Date().getTime()).toString()));
					cronogramaCredito.setTsFechaVencimiento(new Timestamp(calFechaRegistro.getTime().getTime()));
					cronogramaCredito.setIntPeriodoPlanilla(new Integer(sdfPeriodo.format(calFechaRegistro.getTime().getTime()).toString()));

					cronogramaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					listaCronogramaCredito.add(cronogramaCredito);
					
					bdSumaAmortizacion = bdSumaAmortizacion.add(bdMontoCancelar);
					bdSumaFaltaCancelar = expedienteCredito.getBdMontoSolicitado().subtract(bdSumaAmortizacion);
					bdSumaFaltaCancelar = bdSumaFaltaCancelar.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
					

					//CUOTA  UNO:
					// generando cronograma para guardar
					cronogramaCredito = new CronogramaCredito();
					cronogramaCredito.setId(new CronogramaCreditoId());
					cronogramaCredito.getId().setIntCuentaPk(expedienteCredito.getId().getIntCuentaPk());
					cronogramaCredito.getId().setIntItemDetExpediente(expedienteCredito.getId().getIntItemDetExpediente());
					cronogramaCredito.getId().setIntItemExpediente(expedienteCredito.getId().getIntItemExpediente());
					cronogramaCredito.getId().setIntPersEmpresaPk(expedienteCredito.getId().getIntPersEmpresaPk());
					cronogramaCredito.setIntNroCuota(1);
					cronogramaCredito.setIntParaTipoCuotaCod(Constante.PARAM_T_TIPOCUOTACRONOGRAMA_NORMAL);
					cronogramaCredito.setIntParaFormaPagoCod(Constante.PARAM_T_FORMADEPAGO_PLANILLA);
					cronogramaCredito.setIntParaTipoConceptoCod(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_AMORTIZACION);
					cronogramaCredito.setBdMontoConcepto(bdAmortizacion);
					cronogramaCredito.setBdMontoCapital(bdSumaFaltaCancelar);

					fechaVenc = (StringToCalendar(listaDiasVencimiento.get(i).toString())).getTime();

					cronogramaCredito.setTsFechaVencimiento(new Timestamp(fechaVenc.getTime()));
					cronogramaCredito.setIntPeriodoPlanilla(new Integer(sdfPeriodo.format(fechaVenc.getTime()).toString()));
					cronogramaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);

					listaCronogramaCredito.add(cronogramaCredito);
					
					bdSumaAmortizacion = bdSumaAmortizacion.add(bdAmortizacion);
					bdSumaFaltaCancelar = expedienteCredito.getBdMontoSolicitado().subtract(bdSumaAmortizacion);
					bdSumaFaltaCancelar = bdSumaFaltaCancelar.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
					
				}else{

					// Si es la ultima cuota realizamos ajuste de centimos
					if(i + 1 == intNroCuotas){
						if(bdMontoPrestamo.compareTo(bdSumaAmortizacion)!=0){
							bdMontoUltimaCuota = bdMontoPrestamo.subtract(bdSumaAmortizacion);
							bdMontoUltimaCuota = bdMontoUltimaCuota.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
							//bdSumaFaltaCancelar = BigDecimal.ZERO;
							bdSumaFaltaCancelar = bdMontoUltimaCuota;
							bdAmortizacion = bdSumaFaltaCancelar;

						}
					}

					// generando cronograma para guardar
					cronogramaCredito = new CronogramaCredito();
					cronogramaCredito.setId(new CronogramaCreditoId());
					cronogramaCredito.getId().setIntCuentaPk(expedienteCredito.getId().getIntCuentaPk());
					cronogramaCredito.getId().setIntItemDetExpediente(expedienteCredito.getId().getIntItemDetExpediente());
					cronogramaCredito.getId().setIntItemExpediente(expedienteCredito.getId().getIntItemExpediente());
					cronogramaCredito.getId().setIntPersEmpresaPk(expedienteCredito.getId().getIntPersEmpresaPk());
					cronogramaCredito.setIntNroCuota(i + 1);
					
					cronogramaCredito.setIntParaTipoCuotaCod(Constante.PARAM_T_TIPOCUOTACRONOGRAMA_NORMAL);
					cronogramaCredito.setIntParaFormaPagoCod(Constante.PARAM_T_FORMADEPAGO_PLANILLA);
					cronogramaCredito.setIntParaTipoConceptoCod(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_AMORTIZACION);
					cronogramaCredito.setBdMontoConcepto(bdAmortizacion);
					cronogramaCredito.setBdMontoCapital(bdSumaFaltaCancelar);

					fechaVenc = (StringToCalendar(listaDiasVencimiento.get(i).toString())).getTime();

					cronogramaCredito.setTsFechaVencimiento(new Timestamp(fechaVenc.getTime()));
					cronogramaCredito.setIntPeriodoPlanilla(new Integer(sdfPeriodo.format(fechaVenc.getTime()).toString()));
					cronogramaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					listaCronogramaCredito.add(cronogramaCredito);
					
					// Generando Cronograma para mostrar
					//cronogramaCreditoComp = new CronogramaCreditoComp();
					//cronogramaCreditoComp.setStrFechaVencimiento(listaDiasVencimiento.get(i).toString());
					//cronogramaCreditoComp.setBdAmortizacion(bdAmortizacion);
					//cronogramaCreditoComp.setBdSaldoCapital(bdSumaFaltaCancelar);

					//cronogramaCreditoComp.setIntParaTipoFormapago(Constante.PARAM_T_FORMADEPAGO_PLANILLA);
					//cronogramaCreditoComp.setCronogramaCredito(cronogramaCredito);
					//listaCronogramaCreditoComp.add(cronogramaCreditoComp);
					
					bdSumaAmortizacion = bdSumaAmortizacion.add(bdAmortizacion);
					bdSumaFaltaCancelar = expedienteCredito.getBdMontoSolicitado().subtract(bdSumaAmortizacion);
					bdSumaFaltaCancelar = bdSumaFaltaCancelar.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);

				}
			}
							
			//-------------------------->
			// generamos el valor de la cuota a mostar
			/*if(listaCronogramaCreditoComp.size() == 1){
				setBdMontoDeCuotas(bdMontoGenerarCronograma);
			}else{
				setBdMontoDeCuotas(listaCronogramaCreditoComp.get(1).getBdAmortizacion()== null ? BigDecimal.ZERO : listaCronogramaCreditoComp.get(1).getBdAmortizacion());

			}*/
			
			intNroRecalculado = validarRecalculoCronograma(intNroCuotas, listaCronogramaCredito, creditoSeleccionado);
			if(intNroRecalculado.compareTo(0)!=0){
				intNroCuotas = intNroRecalculado - 1;
			}
		};
			

		} catch (Exception e) {
			log.error("Error al generarCronograma() ---> "+e);
			e.printStackTrace();
		}
		return listaCronogramaCredito;

}

	/**
	 * Valida sla necesidad de realizar un recalculo del nrod e cuotas.
	 * Estas no deben pasar la fecha de fin de vigenica de la configuraciond e la actividad
	 * @param intNroCuotas
	 * @param lstCronograma
	 */
	private Integer validarRecalculoCronograma(Integer intNroCuotas, List<CronogramaCredito> lstCronograma, Credito beanCredito){
		Timestamp dtFin = new Timestamp(new Date().getTime());
		Date dtVencimiento = null;
		//Boolean blnPasa = Boolean.TRUE; 
		Integer intCuota = 0;
//				Integer intNroMeses = 0;
//				CronogramaCredito cronogramaCred = null;
		
		try {
		
			dtFin = convierteStringATimestamp(beanCredito.getStrDtFechaFin());

			// 1.  orednamos el cronograma x nro cuota, cogemos la ultima....
			Collections.sort(lstCronograma, new Comparator<CronogramaCredito>(){
				public int compare(CronogramaCredito uno, CronogramaCredito otro) {
					return uno.getIntPeriodoPlanilla().compareTo(otro.getIntPeriodoPlanilla());
				}
			});
			
			for(int k=0; k<lstCronograma.size();k++){
				System.out.println("CRONOGRAMAS ---> "+lstCronograma.get(k).getIntNroCuota());
			}
			
			
			// 2. No deberia sobrepadar el periodo final de la fecha de find el cfrredito
			for (CronogramaCredito cronogramaCredito : lstCronograma) {
				dtVencimiento = cronogramaCredito.getTsFechaVencimiento();
				if(dtVencimiento.after(dtFin)){
					intCuota = cronogramaCredito.getIntNroCuota();
					break;
					
				}
			}

		} catch (Exception e) {
			log.error("Error en reCalcularNroCuotasActividad ---> "+e);
		}
		return intCuota;

	}	
	
	/**
	 * Clacula cuotas dif entre fech hoy y fin credito
	 * @param credito
	 * @return
	 */
	public static final Timestamp  convierteStringATimestamp(String str_date){
		DateFormat formatter ; 
		Date date ; 
		java.sql.Timestamp timeStampDate = null;
		formatter = new SimpleDateFormat("dd/MM/yyyy");
		try{
		    date = (Date)formatter.parse(str_date); 
		    timeStampDate = new  Timestamp(date.getTime());
		    System.out.println("Today is " +timeStampDate);
		}
		catch (ParseException e){
			System.out.println("Exception :"+e);  
		}  
		 
		return timeStampDate;
	 }
	
	
	/**
	 * Calcula la fecha del ler Envio y 1er Vencimiento.
	 * Toma en cuenta si existe o no salto de mes. Configurado.
	 * @param estructuraDetalle
	 * @param fecHoy
	 * @return listaEnvioVencimiento (0, fec1erEnvio)(1, fec1erVenc);
	 */
	public List calcular1raFechaEnvioVencimientoOriginal(EstructuraDetalle estructuraDetalle, Calendar fecHoy) {
		//Calendar fecEnvioTemp = Calendar.getInstance();
		String miFechaPrimerVenc = null;
		Calendar fec1erVenc  = Calendar.getInstance();
		Calendar fecVenc1 	 = Calendar.getInstance();
		Calendar fecVenc2 	 = Calendar.getInstance();
		Calendar fecVenc3 	 = Calendar.getInstance();
		List listaEnvioVencimiento = new ArrayList();

		if ((fecHoy.get(Calendar.DATE)) < estructuraDetalle.getIntDiaEnviado()) {
			fecVenc1.clear();
			fecVenc1.set(fecHoy.get(Calendar.YEAR), 
						fecHoy.get(Calendar.MONTH),
						fecHoy.get(Calendar.DATE));

		} else { // Salta al mes siguiente
			fecVenc1.clear();
			fecVenc1.set(fecHoy.get(Calendar.YEAR),fecHoy.get(Calendar.MONTH) + 1, fecHoy.get(Calendar.DATE));
		}
		//fec1erEnvio = fecEnvioTemp;

		// fecEnvioTemp;
		// Se recalcula fecha de 1er Vencimiento - Se agrega un mes si intSalto
		// != 1
		if (estructuraDetalle.getIntSaltoEnviado() == 1) {
			miFechaPrimerVenc = Constante.sdf.format(getUltimoDiaDelMes(
					fecVenc1).getTime());
			fecVenc3 = StringToCalendar(miFechaPrimerVenc);
		} else {
			int dd = fecVenc1.get(Calendar.DATE); // captura día actual
			int mm = fecVenc1.get(Calendar.MONTH); // captura mes actual
			int aa = fecVenc1.get(Calendar.YEAR); // captura año actual

			fecVenc2.set(aa, mm + 1, dd);
			miFechaPrimerVenc = Constante.sdf.format(getUltimoDiaDelMes(
					fecVenc2).getTime());
			fecVenc3 = StringToCalendar(miFechaPrimerVenc);
		}
		fec1erVenc.clear();
		fec1erVenc = fecVenc3;
		//fec1erEnvio.set(fec1erEnvio.get(Calendar.YEAR),	fec1erEnvio.get(Calendar.MONTH),estructuraDetalle.getIntDiaEnviado());

		//listaEnvioVencimiento.add(0, fec1erEnvio);
		//listaEnvioVencimiento.add(1, fec1erVenc);
		listaEnvioVencimiento.add(0, fec1erVenc);

		return listaEnvioVencimiento;
	}

}