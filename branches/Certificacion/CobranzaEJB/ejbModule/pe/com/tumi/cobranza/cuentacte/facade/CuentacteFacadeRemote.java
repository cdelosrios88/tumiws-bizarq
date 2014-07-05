package pe.com.tumi.cobranza.cuentacte.facade;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.cobranza.planilla.domain.DescuentoIndebido;
import pe.com.tumi.cobranza.planilla.domain.Devolucion;
import pe.com.tumi.cobranza.planilla.domain.EstadoSolicitudCtaCte;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCte;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCteTipo;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCteTipoId;
import pe.com.tumi.cobranza.planilla.domain.Transferencia;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacion;
import pe.com.tumi.servicio.prevision.domain.RequisitoLiquidacion;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.RequisitoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.ExpedienteCreditoComp;

@Remote
public interface CuentacteFacadeRemote {

	public SolicitudCtaCteTipo getSolicitudCtaCteTipoPorPk(SolicitudCtaCteTipoId pId) throws BusinessException;
	public SolicitudCtaCte grabarSolicitudCtaCteAntedido(SolicitudCtaCte o) throws BusinessException;
	
	public SolicitudCtaCte grabarSolicitudCtaCte(SolicitudCtaCte o) throws BusinessException;
	public SolicitudCtaCteTipo grabarSolicitudCtaCteTipo(SolicitudCtaCteTipo o) throws BusinessException;
	public EstadoSolicitudCtaCte grabarEstadoSolicitudCtaCte(EstadoSolicitudCtaCte o) throws BusinessException;
//	public LibroDiario generarTransferenciaRefinanciamiento(SolicitudCtaCteTipo solicitudCtaCteTipo, Usuario usuario, 
//			Expediente expedientePorRefinanciar, ExpedienteCredito expedienteCredito)throws BusinessException;
	
	public LibroDiario generarProcesosDeRefinanciamiento(SocioComp socioComp, String strPeriodo, RequisitoCredito requisitoCred, Usuario usuario, 
			Expediente expedienteMovAnterior, ExpedienteCredito expedienteCreditoNuevo, ExpedienteCreditoComp expedienteCreditoCompSelected) throws BusinessException;
	public List<SolicitudCtaCte> getListaPorCuenta(Integer empresasolctacte,  Integer intCsocCuenta)throws BusinessException;
	public List<SolicitudCtaCte> SolicitudesTipoTransf(Integer empresasolctacte,  Integer intCsocCuenta)throws BusinessException;
	//public SolicitudCtaCte grabarSolicitudCtaCteParaLiquidacion_1(SocioComp socioComp,Integer intPeriodo, RequisitoLiquidacion requisitoLiq, Usuario usuario) 
	//throws BusinessException;
	//public LibroDiario generarAsientoContableLiquidacionCuentasYTransferencia_2(SolicitudCtaCteTipo solicitudCtaCteTipo, SocioComp socioComp, Usuario usuario, ExpedienteLiquidacion expedienteLiquidacion) 
	//throws BusinessException;
	public SolicitudCtaCteTipo modificarSolicitudCuentaCorrienteTipo (SolicitudCtaCteTipo solicitudCtaCTeTipo) throws BusinessException;
	//public void validarYGenerarCuentaConceptoMovimentoYCuentaCtoDetalleActualizaciones_3(SocioComp socioComp, LibroDiario libroDiarioLiquidacion, Usuario usuario) 
	//throws BusinessException;
	//public Boolean validarYGenerarCuentaConceptoMovimentoYCuentaCtoDetalleActualizaciones_3(SocioComp socioComp, LibroDiario libroDiarioLiquidacion, Usuario usuario) 
	//throws BusinessException;
	
	public List<DescuentoIndebido> getListaDesceuntoIndebidoXEmpYCta(Integer intIdEmpresa, Integer intCsocCuenta) throws BusinessException;
	public Devolucion grabarDevolucion(Devolucion devolucion) throws BusinessException;
	public Transferencia grabarTransgerencia(Transferencia transferencia) throws BusinessException;

}
