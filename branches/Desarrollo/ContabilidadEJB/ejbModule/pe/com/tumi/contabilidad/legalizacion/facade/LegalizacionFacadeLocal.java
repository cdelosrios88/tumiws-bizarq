package pe.com.tumi.contabilidad.legalizacion.facade;

import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.contabilidad.legalizacion.domain.LibroContableDetalle;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroContableDetalleComp;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroLegalizacion;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroLegalizacionComp;
import pe.com.tumi.framework.negocio.exception.BusinessException;

@Local
public interface LegalizacionFacadeLocal {
	public LibroLegalizacion grabarLibroLegalizacion(LibroLegalizacionComp o)throws BusinessException;
	public LibroLegalizacion modificarLibroLegalizacion(LibroLegalizacion o)throws BusinessException;
	public void eliminar(LibroLegalizacionComp o) throws BusinessException;
	public LibroContableDetalle grabarLibroContableDetalle (LibroContableDetalle o)throws BusinessException;
	public LibroContableDetalle modificarLibroContableDetalle (LibroContableDetalle o)throws BusinessException;
	public void eliminar(LibroContableDetalleComp o) throws BusinessException;
	public List<LibroLegalizacionComp> getListaPersonaJuridica(String strRazonSocial, String strRuc, Integer intIdEmpresa) throws BusinessException;
	public List<LibroLegalizacionComp> getListaLegalizaciones(Integer intIdEmpresa, Integer intParaLibroContable) throws BusinessException;
	public List<LibroContableDetalleComp> getListaLibroContableDetalle(Integer intIdEmpresa, Integer intParaLibroContable) throws BusinessException;
	public Integer getUltimoFolio(Integer intIdEmpresa, Integer intParaLibroContable) throws BusinessException;
	public List<LibroLegalizacionComp> getListaLibrosLegalizaciones(Integer intIdEmpresa, Integer intParaLibroContable) throws BusinessException;
	public List<LibroContableDetalleComp> getListaUltimoFolioDetalle(Integer intIdEmpresa, Integer intParaLibroContable, Integer intItemLibroLegalizacion) throws BusinessException;
	public List<LibroContableDetalleComp> getListaLibroContaDetaNulo(Integer intIdEmpresa, Integer intParaLibroContable) throws BusinessException;
}