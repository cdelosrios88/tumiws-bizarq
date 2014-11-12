package pe.com.tumi.contabilidad.impuesto.facade;
import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.contabilidad.impuesto.domain.Impuesto;
import pe.com.tumi.contabilidad.impuesto.domain.ImpuestoId;
import pe.com.tumi.framework.negocio.exception.BusinessException;

@Local
public interface ImpuestoFacadeLocal {
	public Impuesto grabarImpuesto(Impuesto o)throws BusinessException;
	public Impuesto modificarImpuesto(Impuesto o)throws BusinessException;
	public List<Impuesto> getListaPersonaJuridica(Impuesto o) throws BusinessException;
	public List<Impuesto> getListaNombreDniRol(Impuesto o) throws BusinessException;
	public List<Impuesto> getBuscar(Impuesto o) throws BusinessException;
	public List<Impuesto> getListaImpuesto(Impuesto o) throws BusinessException;
	//Autor: jchavez / Tarea: Creación / Fecha: 18.08.2014 / 
	public Impuesto getListaPorPk(ImpuestoId o) throws BusinessException;
}
