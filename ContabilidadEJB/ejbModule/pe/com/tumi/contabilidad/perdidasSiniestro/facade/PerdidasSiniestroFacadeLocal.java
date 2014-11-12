package pe.com.tumi.contabilidad.perdidasSiniestro.facade;
import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.contabilidad.perdidasSiniestro.domain.PerdidasSiniestro;
import pe.com.tumi.framework.negocio.exception.BusinessException;

@Local
public interface PerdidasSiniestroFacadeLocal {
	public PerdidasSiniestro grabarPerdidasSiniestro(PerdidasSiniestro o)throws BusinessException;
	public PerdidasSiniestro modificarPerdidasSiniestro(PerdidasSiniestro o)throws BusinessException;
	public List<PerdidasSiniestro> getListaJuridicaRuc(PerdidasSiniestro o) throws BusinessException;
	public List<PerdidasSiniestro> getListaBuscar(PerdidasSiniestro o) throws BusinessException;
}
