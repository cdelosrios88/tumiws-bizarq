package pe.com.tumi.tesoreria.banco.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.banco.domain.Bancocuentacheque;

public interface BancocuentachequeDao extends TumiDao{
	public Bancocuentacheque grabar(Bancocuentacheque pDto) throws DAOException;
	public Bancocuentacheque modificar(Bancocuentacheque o) throws DAOException;
	public List<Bancocuentacheque> getListaPorPk(Object o) throws DAOException;
	public List<Bancocuentacheque> getListaPorBancoCuenta(Object o) throws DAOException;
}	
