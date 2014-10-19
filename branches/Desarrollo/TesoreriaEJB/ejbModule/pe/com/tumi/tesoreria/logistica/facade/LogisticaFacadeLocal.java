package pe.com.tumi.tesoreria.logistica.facade;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.common.util.DocumentoRequisicion;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.general.domain.Tarifa;
import pe.com.tumi.parametro.general.domain.TipoCambio;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.logistica.domain.AdelantoSunat;
import pe.com.tumi.tesoreria.logistica.domain.Contrato;
import pe.com.tumi.tesoreria.logistica.domain.ContratoId;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativo;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativoProveedor;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunat;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatDetalle;
import pe.com.tumi.tesoreria.logistica.domain.InformeGerencia;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompra;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDetalle;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDocumento;
import pe.com.tumi.tesoreria.logistica.domain.Proveedor;
import pe.com.tumi.tesoreria.logistica.domain.ProveedorId;
import pe.com.tumi.tesoreria.logistica.domain.Requisicion;
import pe.com.tumi.tesoreria.logistica.domain.RequisicionDetalle;
import pe.com.tumi.tesoreria.logistica.domain.RequisicionId;


@Local
public interface LogisticaFacadeLocal {

	public List<Proveedor> buscarProveedor(Proveedor o) throws BusinessException;
	public Proveedor getProveedorPorPK(ProveedorId o) throws BusinessException;
	public Proveedor grabarProveedor(Proveedor o) throws BusinessException;
	public Proveedor modificarProveedor(Proveedor o) throws BusinessException;
	public Requisicion grabarRequisicion(Requisicion requisicion) throws BusinessException;
	public List<Requisicion> buscarRequisicion(Requisicion requisicion) throws BusinessException;
	public Requisicion modificarRequisicion(Requisicion requisicion, Usuario usuario) throws BusinessException;
	public Requisicion modificarRequisicionDirecto(Requisicion requisicion) throws BusinessException;
	public List<RequisicionDetalle> obtenerListaRequisicionDetallePorRequisicion(Requisicion requisicion) throws BusinessException;
	public Requisicion obtenerRequisicionPorId(RequisicionId requisicionId) throws BusinessException;
	public List<Requisicion> obtenerListaRequisicionReferencia(Integer intIdEmpresa, Integer intParaTipoRequisicion) throws BusinessException;
	public InformeGerencia grabarInformeGerencia(InformeGerencia informeGerencia) throws BusinessException;
	public List<InformeGerencia> buscarInformeGerencia(InformeGerencia informeGerencia) throws BusinessException;
	public InformeGerencia modificarInformeGerenciaDirecto(InformeGerencia informeGerencia) throws BusinessException;
	public Contrato grabarContrato(Contrato contrato) throws BusinessException;
	public List<Contrato> buscarContrato(Contrato contrato) throws BusinessException;
	public Contrato modificarContratoDirecto(Contrato contrato) throws BusinessException;
	public Contrato obtenerContratoPorId(ContratoId contratoId) throws BusinessException;
	public CuadroComparativo grabarCuadroComparativo(CuadroComparativo cuadroComparativo) throws BusinessException;
	public List<CuadroComparativo> buscarCuadroComparativo(CuadroComparativo cuadroComparativo) throws BusinessException;
	public CuadroComparativo modificarCuadroComparativoDirecto(CuadroComparativo cuadroComparativo) throws BusinessException;
	public CuadroComparativo modificarCuadroComparativo(CuadroComparativo cuadroComparativo) throws BusinessException;
	//Modificado por cdelosrios, 29/09/2013 - se agrego el param itemRequisicion
	public List<DocumentoRequisicion> obtenerListaDocumentoRequisicion(Integer intIdEmpresa, Integer intParaTipoAprobacion, Integer intItemRequisicion) throws BusinessException;
	//Fin modificado por cdelosrios, 29/09/2013
	public List<CuadroComparativoProveedor> obtenerListaCuadroComparativoProveedor(CuadroComparativo cuadroComparativo) throws BusinessException;
	public OrdenCompra grabarOrdenCompra(OrdenCompra ordenCompra) throws BusinessException;
	public List<OrdenCompra> buscarOrdenCompra(OrdenCompra ordenCompraFiltro, List<Persona> listaPersonaFiltro) throws BusinessException;
	public OrdenCompra obtenerOrdenCompraPorContrato(Contrato contrato) throws BusinessException;
	public OrdenCompra obtenerOrdenCompraPorInformeGerencia(InformeGerencia informeGerencia) throws BusinessException;
	public OrdenCompra obtenerOrdenCompraPorCuadroComparativo(CuadroComparativo cuadroComparativo) throws BusinessException;
	public List<DocumentoSunat> grabarDocumentoSunat(List<DocumentoSunat> listaDocumentoSunat, OrdenCompra ordenCompra) throws BusinessException;
	public List<DocumentoSunat> buscarDocumentoSunat(DocumentoSunat DocumentoSunatFiltro, List<Persona> listaPersona) throws BusinessException;
	//Modificado por cdelosrios, 01/11/2013
	//public DocumentoSunat modificarDocumentoSunat(DocumentoSunat documentoSunat) throws BusinessException;
	public List<DocumentoSunat> modificarDocumentoSunat(List<DocumentoSunat> listaDocumentoSunat, OrdenCompra ordenCompra, List<DocumentoSunat> listaDocSunatLetra) throws BusinessException;
    public DocumentoSunat eliminarDocumentoSunat(DocumentoSunat o) throws BusinessException;
    //Fin modificado por cdelosrios, 01/11/2013
    public List<DocumentoSunat> getListaDocumentoSunatPorOrdenCompra(OrdenCompra ordenCompra) throws BusinessException;
	public OrdenCompra modificarOrdenCompraDirecto(OrdenCompra ordenCompra) throws BusinessException;
	public DocumentoSunat modificarDocumentoSunatDirecto(DocumentoSunat documentoSunat) throws BusinessException;
	public List<DocumentoSunat> buscarDocumentoSunatParaGiroDesdeFondo(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException;
	public List<DocumentoRequisicion> obtenerListaDocumentoRequisicionPorRequisicion(Integer intIdEmpresa, Requisicion requisicion,
			Integer intLlamadoDesde) throws BusinessException;
	public DocumentoSunat agregarDocumentoSunatLetra(DocumentoSunat documentoSunat) throws BusinessException;
	public List<DocumentoSunatDetalle> getListaDocumentoSunatDetallePorOrdenCompraDetalle(OrdenCompraDetalle ordenCompraDetalle)
		throws BusinessException;
	public List<AdelantoSunat> getListaAdelantoSunatPorOrdenCompraDocumento(OrdenCompraDocumento ordenCompraDocumento)throws BusinessException;
	public OrdenCompra modificarOrdenCompra(OrdenCompra ordenCompra) throws BusinessException;
	public Persona obtenerPersonaProveedorDeContrato(Contrato contrato) throws BusinessException;
	public Persona obtenerPersonaProveedorDeInformeGerencia(InformeGerencia informeGerencia) throws BusinessException;
	public Persona obtenerPersonaProveedorDeCuadroComparativo(CuadroComparativo cuadroComparativo) throws BusinessException;
	public DocumentoSunat calcularMontosDocumentoSunat(DocumentoSunat documentoSunat, TipoCambio tipoCambio) throws BusinessException;
	public Tarifa cargarTarifaIGVDesdeDocumentoSunat(DocumentoSunat documentoSunat) throws BusinessException;
	//Agregado por cdelosrios, 18/11/2013
    public List<DocumentoSunat> getListDocumentoSunatPorOrdenCompraYTipoDocumento(DocumentoSunat documentoSunat) throws BusinessException;
    //Fin agregado por cdelosrios, 18/11/2013
    //Autor: jchavez / Tarea: Creación / Fecha: 03.10.2014
    public boolean validarMontoOrdenCompraDetalle(OrdenCompraDetalle ocdNuevo, BigDecimal bdMontoValidar) throws BusinessException;
    //Fin jchavez - 03.10.2014
    //Autor: jchavez / Tarea: Creación / Fecha: 06.10.2014
    public List<OrdenCompra> buscarDocumentoAdelantoGarantiaParaGiroPorTesoreria(Integer intIdPersona, Integer intIdEmpresa, Integer intParaTipoDocumento) throws BusinessException;
    //Fin jchavez - 06.10.2014
}