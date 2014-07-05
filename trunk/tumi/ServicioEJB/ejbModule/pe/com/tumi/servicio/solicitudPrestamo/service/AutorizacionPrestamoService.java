package pe.com.tumi.servicio.solicitudPrestamo.service;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioId;
import pe.com.tumi.contabilidad.cierre.facade.LibroDiarioFacadeRemote;
import pe.com.tumi.contabilidad.core.domain.Modelo;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalle;
import pe.com.tumi.contabilidad.core.facade.ModeloFacadeRemote;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.servicio.solicitudPrestamo.bo.AutorizaCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.AutorizaVerificacionBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.CapacidadCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.CapacidadDescuentoBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.CronogramaCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.EstadoCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.ExpedienteCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.GarantiaCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.bo.RequisitoCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.facade.SolicitudPrestamoFacadeLocal;


public class AutorizacionPrestamoService {

	
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
	private GeneralFacadeRemote generalFacade = null;
	private ModeloFacadeRemote modeloFacade = null;
	private LibroDiarioFacadeRemote libroDiarioFacade =null;
	private SolicitudPrestamoFacadeLocal SolicitudFacade = null;
	
	public AutorizacionPrestamoService(){
		try {
			generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
			libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			SolicitudFacade = (SolicitudPrestamoFacadeLocal) EJBFactory.getLocal(SolicitudPrestamoFacadeLocal.class);
			
		} catch (EJBFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
