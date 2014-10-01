package pe.com.tumi.contabilidad.cierreContabilidad.facade;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.contabilidad.cierreContabilidad.domain.CierreContabilidad;
import pe.com.tumi.framework.negocio.exception.BusinessException;

@Remote
public interface CierreContabilidadFacadeRemote {
	//Autor: Rodolfo Villarreal / Tarea: Creación / Fecha: 19.08.2014 /
	public CierreContabilidad grabarCierreContabilidad(CierreContabilidad o)throws BusinessException;
	public CierreContabilidad modificarCierreContabilidad(CierreContabilidad o)throws BusinessException;
	public List<CierreContabilidad> getListaBuscarCierre(CierreContabilidad o) throws BusinessException;
	public List<CierreContabilidad> getListaCierre(CierreContabilidad o) throws BusinessException;
}
