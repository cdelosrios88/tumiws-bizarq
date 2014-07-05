package pe.com.tumi.servicio.prevision.facade;
import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalleId;
import pe.com.tumi.servicio.prevision.domain.AutorizaPrevision;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioPrevision;
import pe.com.tumi.servicio.prevision.domain.EstadoPrevision;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevision;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevisionId;
import pe.com.tumi.servicio.prevision.domain.FallecidoPrevision;
import pe.com.tumi.servicio.prevision.domain.RequisitoPrevision;
import pe.com.tumi.servicio.prevision.domain.composite.ExpedientePrevisionComp;
import pe.com.tumi.servicio.prevision.domain.composite.RequisitoPrevisionComp;
import pe.com.tumi.servicio.prevision.domain.composite.RequisitoPrevisionComp2;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EgresoDetalleInterfaz;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;

@Local
public interface PrevisionFacadeLocal {
	public List<ExpedientePrevision> buscarExpedienteParaGiro(List<Persona> listaPersonaFiltro, Integer intTipoCreditoFiltro,
			EstadoPrevision estadoPrevisionFiltro, Integer intItemExpedienteFiltro, Integer intTipoBusquedaSucursal, 
			Integer intIdSucursalFiltro, Integer intIdSubsucursalFiltro) throws BusinessException;
	public List<BeneficiarioPrevision> getListaBeneficiarioPrevision(ExpedientePrevision expedientePrevision) throws BusinessException;
	public ExpedientePrevision grabarGiroPrevision(ExpedientePrevision expedientePrevision) throws BusinessException;
	public List<ExpedientePrevision> buscarExpedienteParaGiroDesdeFondo(Integer IntIdPersona, Integer intIdEmpresa, Integer intTipoDocumento) throws BusinessException;
	public List<EgresoDetalleInterfaz> cargarListaEgresoDetalleInterfaz(ExpedientePrevision expedientePrevision, BeneficiarioPrevision beneficiarioPrevision) throws BusinessException;
	public Egreso generarEgresoPrevision(ExpedientePrevision expedientePrevision, ControlFondosFijos controlFondosFijos, Usuario usuario) throws BusinessException;
	public BeneficiarioPrevision getBeneficiarioPrevisionPorEgreso(Egreso egreso) throws BusinessException;
	public ExpedientePrevision getExpedientePrevisionPorBeneficiarioPrevision(BeneficiarioPrevision beneficiarioPrevision) throws BusinessException;
	public List<ExpedientePrevision> getListaExpedienteCreditoCompDeBusqueda() throws BusinessException;
	public List<ExpedientePrevisionComp> getListaExpedienteCreditoCompDeBusqueda(ExpedientePrevisionComp expedientePrevisionComp) throws BusinessException;
	public ExpedientePrevision grabarExpedientePrevision(ExpedientePrevision o) throws BusinessException;
	public List<ExpedientePrevisionComp> getListaExpedienteCreditoPorEmpresaYCuenta(ExpedientePrevisionId expedientePrevisionId) throws BusinessException;
	public ExpedientePrevision getExpedientePrevisionPorId(ExpedientePrevisionId expedientePrevisionId) throws BusinessException;
	public List<EstadoPrevision> getListaEstadosPrevisionPorExpediente(ExpedientePrevision expedientePrevision) throws BusinessException;
	public ExpedientePrevision modificarExpedientePrevision(ExpedientePrevision o) throws BusinessException;
	public ExpedientePrevision getExpedientePrevisionCompletoPorIdExpedientePrevision(ExpedientePrevisionId o) throws BusinessException;
	public Egreso generarEgresoPrevisionCheque(ExpedientePrevision expedientePrevision, Bancocuenta bancoCuenta, Usuario usuario, 
			Integer intNumeroCuenta, Integer intTipoDocumentoValidar)throws BusinessException;
	public Egreso generarEgresoPrevisionTransferencia(ExpedientePrevision expedientePrevision, Bancocuenta bancoCuentaOrigen, Usuario usuario, 
			Integer intNumeroTransferencia, Integer intTipoDocumentoValidar, CuentaBancaria cuentaBancariaDestino)throws BusinessException;	
	public List<ExpedientePrevision> getListaExpedientePrevisionPorCuenta(Cuenta cuenta) throws BusinessException;
	public EstadoPrevision getListaExpedientePrevisionPorCuenta(ExpedientePrevision expediente) throws BusinessException;
	public List<ExpedientePrevision> buscarExpedientePrevisionFiltro(List<Persona> listaPersonaFiltro, Integer intTipoCreditoFiltro,Integer intSubTipoCreditoFiltro,
			EstadoPrevision estadoPrevisionFiltro, Integer intItemExpedienteFiltro, Integer intTipoBusquedaSucursal, 
			Integer intIdSucursalFiltro, Integer intIdSubsucursalFiltro) throws BusinessException;
	public EstadoPrevision getUltimoEstadoExpedientePrevision(ExpedientePrevision expediente) throws BusinessException;
	// prevision - fallecidos
	public List<FallecidoPrevision> getListaFallecidosPrevisionPorExpediente(ExpedientePrevision expedientePrevision) throws BusinessException;
	/*
	 * // autorizaciones
	public ExpedientePrevision grabarAutorizacionPrevision(ExpedientePrevision o) throws BusinessException;
	public List<AutorizaPrevision> getListaAutorizaPrevisionPorPkExpediente(ExpedientePrevisionId pId) throws BusinessException;
	public List<AutorizaPrevision> getListaVerificaPrevisionPorPkExpediente(ExpedientePrevisionId pId) throws BusinessException;
	*/
	public List<AutorizaPrevision> getListaVerificaPrevisionPorPkExpediente(ExpedientePrevisionId pId) throws BusinessException;
	public void eliminarVerificaAutorizacionAdjuntosPorObservacion(ExpedientePrevision pExpedientePrev) throws BusinessException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 23-09-2013
	public List<ExpedientePrevision> getListaPorCuenta (Cuenta cuenta) throws BusinessException;
	public List<EstadoPrevision> getEstPrevPorExpediente(ExpedientePrevision expedientePrevision) throws BusinessException;
	public List<BeneficiarioPrevision> getBenefPorExpediente(ExpedientePrevision expedientePrevision) throws BusinessException;
	public List<FallecidoPrevision> getFallecidoPorExpediente(ExpedientePrevision expedientePrevision) throws BusinessException;
	/*public List<ExpedientePrevisionComp> buscarExpedientePrevisionSegunFiltro(Integer intBusqTipo, String strBusqCadena, String strBusqNroSol, Integer intTipoCreditoFiltro, 
			Integer intSubTipoCreditoFiltro, Integer intBusqEstado, Date dtBusqFechaEstadoDesde, Date dtBusqFechaEstadoHasta, Integer intBusqSucursal, 
			Integer intBusqSubSucursal)throws BusinessException;*/
	public List<ExpedientePrevisionComp> getListaBusqExpPrevFiltros(ExpedientePrevisionComp expPrevisionComp)throws BusinessException;
	public List<ExpedientePrevisionComp> getListaBusqAutExpPrevFiltros(ExpedientePrevisionComp expPrevisionComp)throws BusinessException;
	//JCHAVEZ 21.01.2014
	public Egreso generarEgresoGiroPrevision(ExpedientePrevision expedientePrevision, ControlFondosFijos controlFondosFijos, Usuario usuario)throws BusinessException;
	public List<ExpedientePrevision> getUltimoEstadoExpPrev (CuentaId cuentaId) throws BusinessException;
	//JCHAVEZ 05.02.2014
	public List<RequisitoPrevision> getListaPorPkExpedientePrevisionYRequisitoDetalle(ExpedientePrevisionId pId, ConfServDetalleId rId,  Integer intBeneficiario) throws BusinessException;
	public Archivo getArchivoPorRequisitoPrevision(RequisitoPrevision reqPrev)throws BusinessException;
	//JCHAVEZ 06.02.2014
	public RequisitoPrevision grabarRequisito(RequisitoPrevision reqPrev) throws BusinessException;
	//modificado jchavez 12.05.2014
	public List<RequisitoPrevisionComp> getRequisitoGiroPrevision(ExpedientePrevision expPrev, Integer intIdMaestro, Integer intParaTipoDescripcion) throws BusinessException;
	public List<RequisitoPrevisionComp2> getRequisitoGiroPrevisionBanco(ExpedientePrevision expPrev, Integer intIdMaestro, Integer intParaTipoDescripcion) throws BusinessException;
	public ExpedientePrevision grabarGiroPrevisionPorTesoreria(ExpedientePrevision expedientePrevision)throws BusinessException;
	//RvILLARREAL 21.05.0214
	public List<RequisitoPrevisionComp> getRequisitoGiroPrevisionPKrequisitoDetalle(Integer intPersEmpresaPk, Integer intItemReqAut, Integer intItemReqAutEstDetalle) throws BusinessException;
	public List<BeneficiarioPrevision> getListaNombreCompletoBeneficiario(Integer intPersEmpresaPk, Integer intCuenta, Integer intExpediente) throws BusinessException;
	public List<BeneficiarioPrevision> getListaVinculo(Integer intPersEmpresaPk, Integer intCuenta, Integer intExpediente) throws BusinessException;
	public List<FallecidoPrevision> getListaNombreCompletoAes(Integer intPersEmpresaPk, Integer intCuenta, Integer intExpediente) throws BusinessException;
	public List<FallecidoPrevision> getListaVinculoAes(Integer intPersEmpresaPk, Integer intCuenta, Integer intExpediente) throws BusinessException;
}