package pe.com.tumi.parametro.general.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.parametro.general.domain.Ubigeo;

public interface UbigeoDao extends TumiDao{
	public List<Ubigeo> getListaUbigeoDeDepartamento() throws DAOException;
	public List<Ubigeo> getListaUbigeoDeProvinciaPorIdUbigeo(Object pO) throws DAOException;
	public List<Ubigeo> getListaUbigeoDeDistritoPorIdUbigeo(Object pO) throws DAOException;
	public List<Ubigeo> getListaPorIdUbigeo(Object pO) throws DAOException;
}
