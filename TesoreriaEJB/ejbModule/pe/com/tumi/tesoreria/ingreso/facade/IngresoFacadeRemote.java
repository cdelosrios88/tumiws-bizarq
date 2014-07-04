package pe.com.tumi.tesoreria.ingreso.facade;
import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.cobranza.planilla.domain.EfectuadoResumen;
import pe.com.tumi.common.util.DocumentoGeneral;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijosId;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoDetalle;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoId;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManual;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManualDetalle;

@Remote
public interface IngresoFacadeRemote {

	public ReciboManual getReciboManualPorSubsucursal(Subsucursal subsucursal) throws BusinessException;
	public List<DocumentoGeneral> filtrarDuplicidadDocumentoGeneralParaIngreso(
	    	List<DocumentoGeneral> listaDocumentoPorAgregar, Integer intTipoDocumentoAgregar, List<DocumentoGeneral>listaDocumentoAgregados)
	    	throws BusinessException;
	public Ingreso generarIngresoEfectuadoResumen(List<EfectuadoResumen> listaEfectuadoResumen, Bancofondo bancoFondo, Usuario usuario) 
		throws BusinessException;
	public ReciboManualDetalle grabarReciboManualDetalle(ReciboManualDetalle reciboManualDetalle) throws BusinessException;
	public Ingreso grabarIngresoGeneral(Ingreso ingreso) throws BusinessException;
	public Ingreso grabarIngresoEfectuadoResumen(Ingreso ingreso,  List<EfectuadoResumen> listaEfectuadoResumen) throws BusinessException;
	public List<Ingreso> buscarIngresoParaCaja(Ingreso ingresoFiltro, List<Persona> listaPersonaFiltro)throws BusinessException;
	public List<IngresoDetalle> getListaIngresoDetallePorIngreso(Ingreso ingreso)throws BusinessException;
	public ReciboManual obtenerReciboManualPorIngreso(Ingreso ingreso)throws BusinessException;
	public List<EfectuadoResumen> obtenerListaEfectuadoResumenPorIngreso(Ingreso ingreso) throws BusinessException;
	public LibroDiario obtenerLibroDiarioPorIngreso(Ingreso ingreso)throws BusinessException;
	public List<Ingreso> obtenerListaIngresoParaDepositar(Integer intIdEmpresa, Integer intMoneda, Integer intFormaPago, Usuario usuario)throws BusinessException;
	public Ingreso grabarDeposito(List<Ingreso> listaIngreso, Usuario usuario, Bancofondo bancoFondo, String strObservacion, 
			Archivo archivo, BigDecimal bdOtrosIngresos, String strNumeroOperacion, Bancofondo bancoFondoIngresar)throws BusinessException;
	public Integer validarNroReciboPorSuc(Integer idEmpresa,Integer sucursal,Integer subsuc,Integer nroRecibo) throws BusinessException;
	public String existeNroReciboEnlazado(Integer idEmpresa,Integer idSucursal,Integer idSubSuc,Integer nroRecibo) throws BusinessException;
	public List<Ingreso> getListaIngNoEnlazados(IngresoId id)throws BusinessException;
	public List<ReciboManualDetalle> buscarRecibosEnlazados(Integer idEmpresa,Integer idSucursal,Integer idSubSuc,Integer idEstadoCierre,Integer nroRecibo)throws BusinessException;
	public ReciboManualDetalle modificarReciboManualDetalle(ReciboManualDetalle reciboManualDetalle) throws BusinessException;
	public Ingreso grabarIngresoCierreFondo(ControlFondosFijos controlFondosFijos, Bancofondo bancoFondo, Usuario usuario, String strObservacion)
		throws BusinessException;
	public List<Ingreso> getListaIngresoParaBuscar(Ingreso ingreso) throws BusinessException;
	//Agregado 12.12.2013 JCHAVEZ
	public List<IngresoDetalle> getPorControlFondosFijos(ControlFondosFijosId pId) throws BusinessException;
}
