package pe.com.tumi.credito.socio.captacion.facade;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.captacion.domain.Condicion;
import pe.com.tumi.framework.negocio.exception.BusinessException;

@Remote
public interface CondicionFacadeRemote {
	public Condicion grabar(Condicion o) throws BusinessException;
	public List<Condicion> listarCondicion(CaptacionId o) throws BusinessException;
}
