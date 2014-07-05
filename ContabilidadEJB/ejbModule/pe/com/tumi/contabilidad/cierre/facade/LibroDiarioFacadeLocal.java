package pe.com.tumi.contabilidad.cierre.facade;

import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioId;
import pe.com.tumi.framework.negocio.exception.BusinessException;

@Local
public interface LibroDiarioFacadeLocal {
	public LibroDiario grabarLibroDiario(LibroDiario o)throws BusinessException;
	public LibroDiario modificarLibroDiario(LibroDiario o)throws BusinessException;
	public LibroDiario getLibroDiarioPorPk(LibroDiarioId pId) throws BusinessException;
	public List<LibroDiarioDetalle> getListaLibroDiarioDetallePorLibroDiario(LibroDiario libroDiario) throws BusinessException;
	public LibroDiarioDetalle modificarLibroDiarioDetalle(LibroDiarioDetalle o)throws BusinessException;
	public LibroDiario getLibroDiarioUltimoPorPk(LibroDiarioId pId) throws BusinessException;
	public List<LibroDiario> buscarLibroDiario(LibroDiario libroDiario)throws BusinessException;
	public List<LibroDiarioDetalle> buscarLibroDiarioDetalle(LibroDiarioDetalle libroDiarioDetalle)throws BusinessException;
	public LibroDiarioDetalle grabarLibroDiarioDetalle(LibroDiarioDetalle o)throws BusinessException;
	/* Inicio - GTorresBroussetP - 05.abr.2014 */
	/* Buscar Libro Diario Detalle por Periodo y Documento */
	public List<LibroDiarioDetalle> buscarLibroDiarioDetallePorPeriodoDocumento(LibroDiarioDetalle libroDiarioDetalle)
			throws BusinessException;
	/* Fin - GTorresBroussetP - 05.abr.2014 */
}
