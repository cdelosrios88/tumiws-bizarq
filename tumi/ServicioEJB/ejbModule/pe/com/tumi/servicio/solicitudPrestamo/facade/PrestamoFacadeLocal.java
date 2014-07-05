package pe.com.tumi.servicio.solicitudPrestamo.facade;
import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalleId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CancelacionCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CronogramaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EgresoDetalleInterfaz;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.RequisitoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.RequisitoCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.RequisitoCreditoComp2;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;

@Local
public interface PrestamoFacadeLocal {
	public List<ExpedienteCredito> obtenerExpedientePorIdPersonayIdEmpresa(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException;
	public List<ExpedienteCredito> buscarExpedienteParaGiro(List<Persona> listaPersonaFiltro, Integer intTipoCreditoFiltro,
			EstadoCredito estadoCreditoFiltro, Integer intItemExpedienteFiltro, Integer intTipoBusquedaSucursal, 
			Integer intIdSucursalFiltro, Integer intIdSubsucursalFiltro) throws BusinessException;
	public List<CancelacionCredito> getListaCancelacionCreditoPorExpedienteCredito(ExpedienteCredito expedienteCredito) throws BusinessException;
	public ExpedienteCredito grabarGiroPrestamo(ExpedienteCredito expedienteCredito) throws BusinessException;
	public List<CronogramaCredito> getListaCronogramaCreditoPorExpedienteCredito(ExpedienteCredito expedienteCredito) throws BusinessException;
	public EstadoCredito obtenerUltimoEstadoCredito(ExpedienteCredito expedienteCredito, Integer intTipoEstado) throws BusinessException;
	public Cuenta getCuentaActualPorIdPersona(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException;
	public List<EgresoDetalleInterfaz> cargarListaEgresoDetalleInterfaz(ExpedienteCredito expedienteCredito) throws BusinessException;
	public Egreso generarEgresoPrestamo(ExpedienteCredito expedienteCredito, ControlFondosFijos controlFondosFijos, Usuario usuario) 
	throws BusinessException;
	public ExpedienteCredito getExpedienteCreditoPorId(ExpedienteCreditoId expedienteCreditoId) throws BusinessException;
	public ExpedienteCredito obtenerExpedientePorEgreso(Egreso egreso)throws BusinessException;
	public Egreso generarEgresoPrestamoCheque(ExpedienteCredito expedienteCredito, Bancocuenta bancoCuenta, Usuario usuario, 
			Integer intNumeroCheque, Integer intTipoDocumentoValidar) throws BusinessException;
	public Egreso generarEgresoPrestamoTransferencia(ExpedienteCredito expedienteCredito, Bancocuenta bancoCuentaOrigen, Usuario usuario, 
	    	Integer intNumeroTransferencia, Integer intTipoDocumentoValidar, CuentaBancaria cuentaBancariaDestino)throws BusinessException;
	//JCHAVEZ 31.01.2014
	public List<RequisitoCreditoComp> getRequisitoGiroPrestamo(ExpedienteCredito expCred) throws BusinessException;
	//JCHAVEZ 03.02.2014
	public RequisitoCredito grabarRequisito(RequisitoCredito reqCred) throws BusinessException;
	public List<RequisitoCredito> getListaPorPkExpedienteCreditoYRequisitoDetalle(ExpedienteCreditoId pId, ConfServDetalleId rId) throws BusinessException;
	public Archivo getArchivoPorRequisitoCredito(RequisitoCredito reqCred)throws BusinessException;
	//JCHAVEZ 11.02.2014
	public List<RequisitoCreditoComp2> getRequisitoGiroPrestamoBanco(ExpedienteCredito expCred) throws BusinessException;
	public ExpedienteCredito grabarGiroPrestamoPorTesoreria(ExpedienteCredito expedienteCredito) throws BusinessException;
}