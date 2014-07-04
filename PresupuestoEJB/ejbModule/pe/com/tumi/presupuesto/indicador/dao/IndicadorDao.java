package pe.com.tumi.presupuesto.indicador.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.presupuesto.indicador.domain.Indicador;
import pe.com.tumi.presupuesto.indicador.domain.IndicadorId;

public interface IndicadorDao extends TumiDao {
	//AUTOR Y FECHA CREACION: JCHAVEZ / 09.10.2013
	public Indicador grabar(Indicador o) throws DAOException;
	public Indicador modificar(Indicador o) throws DAOException;
	public List<Indicador> getListaPorPK(Object o) throws DAOException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 11.10.2013
	public List<Indicador> getListaIndicadorPorFiltros(Object o) throws DAOException;
	public Indicador eliminar(IndicadorId o) throws DAOException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 24.10.2013	
	public List<Indicador> getListaPorRangoFechas(Object o) throws DAOException;
	public List<Indicador> getMesesDelAnioBase(Object o) throws DAOException;
}
