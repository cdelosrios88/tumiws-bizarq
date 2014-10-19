package pe.com.tumi.tesoreria.cierreLogisticaOper.facade;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.tesoreria.cierreLogisticaOper.domain.CierreLogisticaOper;
import pe.com.tumi.tesoreria.cierreLogisticaOper.domain.composite.CierreLogistica;

@Remote
public interface CierreLogisticaOperFacadeRemote {
	//Autor: Rodolfo Villarreal / Tarea: Creación / Fecha: 27.08.2014 /
	public CierreLogisticaOper grabarCierreLogistica(CierreLogisticaOper o)throws BusinessException;
	public CierreLogistica grabarCierreLogis(CierreLogistica o)throws BusinessException;
	public CierreLogistica modificarCierreLogis(CierreLogistica o)throws BusinessException;
	public List<CierreLogisticaOper> getListaCierreLogisticaVista(CierreLogisticaOper o) throws BusinessException;
	public List<CierreLogisticaOper> getListaBuscarCierre(CierreLogisticaOper o) throws BusinessException;
}
