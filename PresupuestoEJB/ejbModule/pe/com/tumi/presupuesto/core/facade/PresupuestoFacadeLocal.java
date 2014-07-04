package pe.com.tumi.presupuesto.core.facade;
import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.presupuesto.core.domain.Presupuesto;
import pe.com.tumi.presupuesto.core.domain.PresupuestoId;

@Local
public interface PresupuestoFacadeLocal {
	//AUTOR Y FECHA CREACION: JCHAVEZ / 10.10.2013 
	public Presupuesto grabarPresupuesto(Presupuesto presupuesto)throws BusinessException;
	public Presupuesto modificarPresupuesto(Presupuesto presupuesto)throws BusinessException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 21.10.2013 
	public List<Presupuesto> getListaPresupuestoPorFiltros(PresupuestoId pId, Integer intTipoCuentaBusq) throws BusinessException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 22.10.2013 
	public Presupuesto getPresupuestoPorPK(PresupuestoId pId) throws BusinessException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 24.10.2013 
	public List<Presupuesto> getListaPorRangoFechas(Integer intEmpresaPresupuestoPk, Integer intPeriodoPresupuesto, Integer intMesDesde, Integer intMesHasta) throws BusinessException;
	public List<Presupuesto> getMesesDelAnioBase(Integer intEmpresaPresupuestoPk, Integer intPeriodoPresupuesto, Integer intMesDesde, Integer intMesHasta) throws BusinessException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 25.10.2013 
	public Presupuesto eliminarPresupuesto(PresupuestoId o)throws BusinessException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 28.10.2013
	public List<Presupuesto> getLstPstoAnioBase(Integer intEmpresa, Integer intPeriodo, Integer intMesDesde, Integer intMesHasta) throws BusinessException;
}
