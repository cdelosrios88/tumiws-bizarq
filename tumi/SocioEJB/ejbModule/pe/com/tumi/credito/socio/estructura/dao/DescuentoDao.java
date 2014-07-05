package pe.com.tumi.credito.socio.estructura.dao;

import java.util.List;

import pe.com.tumi.credito.socio.estructura.domain.Descuento;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface DescuentoDao extends TumiDao{
	public Descuento grabar(Descuento o) throws DAOException;
	public Descuento modificar(Descuento o) throws DAOException;
	public List<Descuento> getListaDescuentoPorPK(Object o) throws DAOException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 21-08-2013 
	public List<Descuento> getListaColumnasPorPeriodoModalidadYDni(Object o) throws DAOException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 21-08-2013
	public List<Descuento> getListaFilasPorPeriodoModalidadYDni(Object o) throws DAOException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 26-08-2013
	public List<Descuento> getMontoTotalPorNomCptoYPeriodo(Object o) throws DAOException;
	
	public List<Descuento> getListaPorAdminPadron(Object o) throws DAOException;
}
