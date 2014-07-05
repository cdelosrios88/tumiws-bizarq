package pe.com.tumi.servicio.configuracion.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.configuracion.domain.ConfServGrupoCta;

public interface ConfServGrupoCtaDao extends TumiDao{
	public ConfServGrupoCta grabar(ConfServGrupoCta o) throws DAOException;
	public ConfServGrupoCta modificar(ConfServGrupoCta o) throws DAOException;
	public List<ConfServGrupoCta> getListaPorPk(Object o) throws DAOException;	
	public List<ConfServGrupoCta> getListaPorCabecera(Object o) throws DAOException;
}
