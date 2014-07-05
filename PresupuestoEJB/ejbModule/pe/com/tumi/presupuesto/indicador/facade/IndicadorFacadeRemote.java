package pe.com.tumi.presupuesto.indicador.facade;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.presupuesto.indicador.domain.Indicador;
import pe.com.tumi.presupuesto.indicador.domain.IndicadorId;

@Remote
public interface IndicadorFacadeRemote {
	//AUTOR Y FECHA CREACION: JCHAVEZ / 09.10.2013 
	public Indicador grabarIndicador(Indicador indicador)throws BusinessException;
	public Indicador modificarIndicador(Indicador indicador)throws BusinessException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 10.10.2013 
	public List<Indicador> getListaIndicadorPorFiltros(IndicadorId pId) throws BusinessException;
	public Indicador getIndicadorPorPK(IndicadorId pId) throws BusinessException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 16.10.2013
	public Indicador eliminarIndicador(IndicadorId o)throws BusinessException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 24.10.2013	
	public List<Indicador> getListaPorRangoFechas(Integer intEmpresaIndicadorPk, Integer intPeriodoIndicador, Integer intMesDesde, Integer intMesHasta) throws BusinessException;
	public List<Indicador> getMesesDelAnioBase(Integer intEmpresaIndicadorPk, Integer intPeriodoIndicador, Integer intMesDesde, Integer intMesHasta) throws BusinessException;
}
