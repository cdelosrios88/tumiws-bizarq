package pe.com.tumi.servicio.prevision.facade;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCte;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;
import pe.com.tumi.movimiento.concepto.domain.composite.CuentaConceptoComp;
import pe.com.tumi.parametro.auditoria.domain.Auditoria;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.prevision.domain.AutorizaLiquidacion;
import pe.com.tumi.servicio.prevision.domain.AutorizaVerificaLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionId;
import pe.com.tumi.servicio.prevision.domain.RequisitoLiquidacion;

@Local
public interface AutorizacionLiquidacionFacadeLocal {
	public ExpedienteLiquidacion grabarAutorizacionLiquidacion(ExpedienteLiquidacion o) throws BusinessException;
	public List<AutorizaLiquidacion> getListaAutorizaLiquidacionPorPkExpediente(ExpedienteLiquidacionId pId) throws BusinessException;
	public List<AutorizaVerificaLiquidacion> getListaVerificaLiquidacionPorPkExpediente(ExpedienteLiquidacionId pId) throws BusinessException;
	public LibroDiario generarLibroDiarioLiquidacion(ExpedienteLiquidacion expedienteLiquidacion) throws BusinessException;
	public LibroDiario generarLibroDiarioAnulacionLiquidacion(ExpedienteLiquidacion expedienteLiquidacion) throws BusinessException;
	//public void aprobarLiquidacionCuentas(SocioComp socioComp, Integer intPeriodo, RequisitoLiquidacion requisitoLiq, ExpedienteLiquidacion expedienteLiquidacionSeleccionado,Usuario usuario)throws BusinessException;
	//public Boolean aprobarLiquidacionCuentas(SocioComp socioComp, Integer intPeriodo, RequisitoLiquidacion requisitoLiq, ExpedienteLiquidacion expedienteLiquidacionSeleccionado,Usuario usuario)throws BusinessException;
	//public LibroDiario aprobarLiquidacionCuentas(SocioComp socioComp, Integer intPeriodo, RequisitoLiquidacion requisitoLiq, ExpedienteLiquidacion expedienteLiquidacionSeleccionado,Usuario usuario)throws BusinessException;
	//public LibroDiario aprobarLiquidacionCuentas(SocioComp socioComp, Integer intPeriodo, RequisitoLiquidacion requisitoLiq, ExpedienteLiquidacion expedienteLiquidacionSeleccionado,Usuario usuario,
	//		ExpedienteLiquidacion expedienteLiquidacion, Integer intEstadoAprobado )throws BusinessException;
	
	public SolicitudCtaCte grabarSolicitudCtaCteParaLiquidacion_1(SocioComp socioComp,Integer intPeriodo, RequisitoLiquidacion requisitoLiq, Usuario usuario) 
	throws BusinessException;
	public List<CuentaConceptoComp> recuperarCuentasConceptoAporteRetiroEInteres(SocioComp socioComp,ExpedienteLiquidacion expedienteLiquidacion) throws BusinessException;
	public SolicitudCtaCte grabarSolicitudCtaCteParaDevolucionLiquidacion(SocioComp socioComp,Integer intPeriodo, RequisitoLiquidacion requisitoLiq, Usuario usuario,
			ExpedienteLiquidacion expedienteLiquidacion, LibroDiario libroDiario)  throws BusinessException;
	public List<Movimiento> validarYGenerarCuentaConceptoMovimentoYCuentaCtoDetalleActualizaciones_3(SocioComp socioComp, LibroDiario libroDiarioLiquidacion, Usuario usuario,ExpedienteLiquidacion expedienteLiquidacion) 
	throws BusinessException;
	public LibroDiario aprobarLiquidacionCuentas(SocioComp socioComp, Integer intPeriodo, RequisitoLiquidacion requisitoLiq, ExpedienteLiquidacion expedienteLiquidacionSeleccionado,Usuario usuario,
			ExpedienteLiquidacion expedienteLiquidacion, Integer intEstadoAprobado, Integer intTipoCambio, Date dtNuevoFechaProgramacionPago, Integer intNuevoMotivoRenuncia,Auditoria auditoria )throws BusinessException;
	public ExpedienteLiquidacion modificarExpedienteLiquidacionParaAuditoria(ExpedienteLiquidacion pExpedienteLiquidacion, Integer intTipoCambio, Date dtNuevoFechaProgramacionPago, Integer intNuevoMotivoRenuncia) throws BusinessException;
	public Auditoria grabarAuditoria(Auditoria auditoria)throws BusinessException;
}
