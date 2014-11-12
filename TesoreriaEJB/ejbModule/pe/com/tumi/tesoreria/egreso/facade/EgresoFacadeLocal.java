/* -----------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo                      Fecha            Nombre                      Descripción
* -----------------------------------------------------------------------------------------------------------
* REQ14-005       			19/10/2014     Christian De los Ríos        Se modificó el método de anulación de saldos         
*/
package pe.com.tumi.tesoreria.egreso.facade;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.common.util.DocumentoGeneral;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EgresoDetalleInterfaz;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.cierre.fdoFijo.domain.ControlFondoFijoAnula;
import pe.com.tumi.tesoreria.egreso.domain.CierreDiarioArqueo;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijosId;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.domain.EgresoDetalle;
import pe.com.tumi.tesoreria.egreso.domain.EgresoId;
import pe.com.tumi.tesoreria.egreso.domain.Movilidad;
import pe.com.tumi.tesoreria.egreso.domain.MovilidadDetalle;
import pe.com.tumi.tesoreria.egreso.domain.Saldo;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonal;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonalDetalle;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonalPago;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunat;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompra;


@Local
public interface EgresoFacadeLocal {

	public Movilidad grabarMovilidad(Movilidad o) throws BusinessException;
	public List<Movilidad> buscarMovilidad(Movilidad o) throws BusinessException;
	public List<MovilidadDetalle> getListaMovilidadDetalleValidar(Movilidad m, MovilidadDetalle md) throws BusinessException;
	public Movilidad modificarMovilidad(Movilidad o) throws BusinessException;
	public Movilidad anularMovilidad(Movilidad o) throws BusinessException;
	public ControlFondosFijos grabarAperturaFondoCheque(Egreso egreso) throws BusinessException;
	public ControlFondosFijos grabarAperturaFondoRendicion(Egreso egreso, ControlFondosFijos rendicion) throws BusinessException;
	public List<ControlFondosFijos> buscarControlFondosFijos(ControlFondosFijos o) throws BusinessException;
	public Egreso getEgresoPorId(EgresoId pId) throws BusinessException;
	public ControlFondosFijos obtenerControlFondosFijosUltimo(List<ControlFondosFijos> l) throws BusinessException;
	public List<ControlFondosFijos> buscarApertura(ControlFondosFijos o, List<Sucursal> listaSucursal) throws BusinessException;
	public List<ControlFondosFijos> buscarCierre(ControlFondosFijos o, List<Sucursal> listaSucursal) throws BusinessException;
	public ControlFondosFijos modificarControlFondosFijos(ControlFondosFijos o) throws BusinessException;
	public List<EgresoDetalle> getListaEgresoDetallePorEgreso(Egreso o) throws BusinessException;
	public Egreso getEgresoPorControlFondosFijos(ControlFondosFijos o) throws BusinessException;
	public Egreso grabarTransferenciaEntreCuentas(Egreso egreso) throws BusinessException;
	public List<Egreso> buscarTransferencia(Egreso egreso, Date dtFiltroDesde, Date dtFiltroHasta, List<Persona> listaPersona) throws BusinessException;
	public List<Movilidad> buscarMovilidadPorIdPersona(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException;
	public List<ControlFondosFijos> buscarControlFondosParaGiro(Integer intIdSucursal, Integer intIdSubSucursal, Integer intIdEmpresa) throws BusinessException;
	public Egreso grabarEgresoParaGiroPrestamo(Egreso egreso) throws BusinessException;
	public ControlFondosFijos getControlFondosFijosPorId(ControlFondosFijosId controlFondosFijosId) throws BusinessException;
	public Egreso obtenerEgresoYLibroDiario(EgresoId egresoId) throws BusinessException;
	public Integer obtenerMonedaDeCFF(ControlFondosFijos controlFondosFijos) throws BusinessException;
	public List<Movilidad> buscarMovilidadParaGiroDesdeFondo(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException;
	public List<ControlFondosFijos> obtenerListaNumeroApertura(Integer intTipoFondoFijo, Integer intAño, Integer intIdSucursal) throws BusinessException;
	public List<Egreso> buscarEgresoParaFondosFijos(List<Persona>listaPersona, Egreso egresoFiltro,
			List<ControlFondosFijos> listaControlFondosFijos, Date dtFechaDesde, Date dtFechaHasta)throws BusinessException;
	public List<EgresoDetalleInterfaz> cargarListaEgresoDetalleInterfaz(Movilidad movilidad)throws BusinessException;
	public Egreso generarEgresoMovilidad(List<Movilidad> listaMovilidad, ControlFondosFijos controlFondosFijos, Usuario usuario) throws BusinessException;
	public Egreso grabarGiroMovilidad(Egreso egreso, List<Movilidad> listaMovilidad)throws BusinessException;
	public List<Movilidad> obtenerListaMovilidadPorEgreso(Egreso egreso)throws BusinessException;
	public Egreso getEgresoPorExpedienteCredito(ExpedienteCredito expedienteCredito)throws BusinessException;
	public Archivo getArchivoPorEgreso(Egreso egreso)throws BusinessException;
	public List<DocumentoGeneral> filtrarDuplicidadDocumentoGeneralParaEgreso(List<DocumentoGeneral> listaDocumentoPorAgregar, Integer intTipoDocumentoAgregar, 
			List<DocumentoGeneral>listaDocumentoAgregados)throws BusinessException;
	public List<ControlFondosFijos> obtenerControlFondosFijosACerrar(Integer intIdEmpresa,Integer intIdSucursal, Integer intIdSubsucursal)throws BusinessException;
	public CierreDiarioArqueo grabarCierreDiarioArqueo(CierreDiarioArqueo cierreDiarioArqueo) throws BusinessException;
	public List<CierreDiarioArqueo> buscarCierreDiarioArqueo(CierreDiarioArqueo cierreDiarioArqueo, List<Persona> listaPersona)throws BusinessException;
	public void procesarSaldo(Date dtFechaInicio, Date dtFechaFin, Usuario usuario, List<Bancofondo> listaBanco, List<Bancofondo> listaFondo) throws BusinessException;
	//Inicio: REQ14-005 - bizarq - 19/10/2014
	public Integer processDailyAmount(Date dtFechaInicio, Date dtFechaFin, Usuario usuario) 
		throws BusinessException;
	//Fin: REQ14-005 - bizarq - 19/10/2014
	public Saldo obtenerSaldoUltimaFechaRegistro(Integer intIdEmpresa) throws BusinessException;
	public Date  obtenerUltimaFechaSaldo(Integer intIdEmpresa) throws BusinessException;
	public List<Saldo> buscarSaldo(Saldo saldo)throws BusinessException;
	public Egreso generarEgresoMovilidadCheque(List<Movilidad> listaMovilidad, Bancocuenta bancoCuenta, Usuario usuario, Integer intNumeroCheque, 
			Integer intTipoDocumentoValidar) throws BusinessException;
	public List<Egreso> buscarEgresoParaCaja(List<Persona>listaPersona, Egreso egresoFiltro, Date dtDesdeFiltro, Date dtHastaFiltro)throws BusinessException;
	//Inicio: REQ14-005 - bizarq - 19/10/2014
	//public void anularSaldo(Integer intIdEmpresa, Date dtFechaInicio)throws BusinessException;
	public void anularSaldo(Usuario usuario, Date dtFechaInicio)throws BusinessException;
	public void anularSaldo(Usuario usuario, Saldo saldoFiltro)throws BusinessException;
	//Fin: REQ14-005 - bizarq - 19/10/2014
	public List<Egreso> buscarEgresoParaTelecredito(Egreso egreso)throws BusinessException;
	public Egreso grabarTransferenciaTelecredito(Egreso egreso, List<Egreso> listaEgresoTelecredito) throws BusinessException;
	public Egreso generarEgresoMovilidadTransferencia(List<Movilidad> listaMovilidad, Bancocuenta bancoCuenta, Usuario usuario, Integer intNumeroCheque, 
	    	Integer intTipoDocumentoValidar, CuentaBancaria cuentaBancariaDestino)  throws BusinessException;
	public ControlFondosFijos obtenerControlFondosFijosPorEgresoDetalle(EgresoDetalle egresoDetalle) throws BusinessException;
	public List<Egreso> obtenerListaEgresoPorControlFondosFijos(ControlFondosFijos controlFondosFijos)throws BusinessException;
	public List<EgresoDetalleInterfaz> cargarListaEgresoDetalleInterfaz(DocumentoSunat documentoSunat)throws BusinessException;
	public Egreso grabarGiroDocumentoSunat(List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz, ControlFondosFijos controlFondosFijos, 
	    	Usuario usuario)throws BusinessException;
	public List<DocumentoSunat> obtenerListaDocumentoSunatPorEgreso(Egreso egreso)throws BusinessException;
	public SolicitudPersonal grabarSolicitudPersonal(SolicitudPersonal solicitudPersonal)throws BusinessException;
	public List<SolicitudPersonal> buscarSolicitudPersonal(SolicitudPersonal solicitudPersonalFiltro, List<Persona> listaPersonaFiltro)
    throws BusinessException;
	public SolicitudPersonal modificarSolicitudPersonaDirecto(SolicitudPersonal solicitudPersonal) throws BusinessException;
	public List<SolicitudPersonalDetalle> getListaSolicitudPersonalDetalle(SolicitudPersonal solicitudPersonal)throws BusinessException;
	public SolicitudPersonal modificarSolicitudPersonal(SolicitudPersonal solicitudPersonal)throws BusinessException;
	public List<SolicitudPersonalPago> getListaSolicitudPersonalPago(SolicitudPersonal solicitudPersonal)throws BusinessException;
	public Egreso procesarItems(Egreso egreso)throws BusinessException;
	//Agregado 05.12.2013 JCHAVEZ
	public ControlFondoFijoAnula grabarAnulaCierre(ControlFondosFijos o, Integer intEmpresa, Integer intPersona, String strObservacion, Integer intParaTipoAnulaFondo) throws BusinessException;
	public List<Egreso> buscarEgresoParaFondosFijos(List<Persona>listaPersona, Egreso egresoFiltro,
			ControlFondosFijos controlFondosFijos, Date dtFechaDesde, Date dtFechaHasta) throws BusinessException;
	//Autor: jchavez / Tarea: Creacion / Fecha: 06.10.2014
	public List<EgresoDetalleInterfaz> cargarListaEgresoDetalleInterfazOrdenCompra(OrdenCompra ordenCompra, Integer intTipoDocumento)throws BusinessException;
	//Fin jchavez - 06.10.2014
	//Autor: jchavez / Tarea: Creacion / Fecha: 07.10.2014
	public Egreso grabarGiroOrdenCompraDocumento(List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz, ControlFondosFijos controlFondosFijos, Usuario usuario, Integer intParaTipoDocumento)throws BusinessException;
	//Fin jchavez - 07.10.2014
	//Autor: jchavez / Tarea: Creacion / Fecha: 07.10.2014
	public Egreso grabarGiroOrdenCompraDocumentoPorTesoreria(List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz, Bancocuenta bancoCuenta, Usuario usuario,  Integer intNroTransferencia, Integer intParaTipoDocumento, Integer intTipoDocumentoValidar)throws BusinessException;
	//Fin jchavez - 13.10.2014
	//Autor: jchavez / Tarea: Creacion / Fecha: 15.10.2014
	public Egreso grabarGiroOrdenCompraDocumentoPorCheque(List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz, Bancocuenta bancoCuenta, Usuario usuario, Integer intNroCheque, Integer intParaTipoDocumento, Integer intTipoDocumentoValidar)throws BusinessException;
	//Fin jchavez - 15.10.2014
}