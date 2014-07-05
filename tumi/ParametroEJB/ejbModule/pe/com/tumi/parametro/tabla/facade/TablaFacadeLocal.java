package pe.com.tumi.parametro.tabla.facade;
import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;

@Local
public interface TablaFacadeLocal {
	public List<Tabla> getListaTablaMaestro() throws BusinessException;
	public List<Tabla> getListaTablaPorIdMaestro(Integer pIntIdMaestro) throws BusinessException;
	public Tabla getTablaPorIdMaestroYIdDetalle(Integer pIntIdMaestro, Integer pIntIdDetalle) throws BusinessException;
	public List<Tabla> getListaTablaPorAgrupamientoA(Integer pIntIdMaestro, String pStrAgrupamiento) throws BusinessException;
	public List<Tabla> getListaTablaPorAgrupamientoB(Integer pIntIdMaestro, Integer pIntIdAgrupamiento) throws BusinessException;
	public List<Tabla> getListaTablaPorIdMaestroYNotInIdDetalle(Integer pIntIdMaestro, String pCsv) throws BusinessException;
}
