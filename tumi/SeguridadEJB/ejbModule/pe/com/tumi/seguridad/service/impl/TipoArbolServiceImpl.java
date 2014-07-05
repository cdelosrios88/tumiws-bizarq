package pe.com.tumi.seguridad.service.impl;

import java.util.List;

import pe.com.tumi.common.dao.GenericDao;
import pe.com.tumi.common.service.impl.GenericServiceImpl;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.seguridad.service.TipoArbolService;

public class TipoArbolServiceImpl extends GenericServiceImpl implements TipoArbolService{

	private GenericDao tipoArbolDAO;
	
	public List getOpcionesxRol(Long id) throws DaoException {
		try {
			return getTipoArbolDAO().getOpcionesxRol(id);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public List getNodes(Long id) throws DaoException {
		try {
			return getTipoArbolDAO().getNodes(id);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public List getTreeByUser(Long id, Long sistema) throws DaoException {
		try {
			return getTipoArbolDAO().getTreeByUser(id,sistema);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public void setTipoArbolDAO(GenericDao tipoArbolDAO) {
		this.tipoArbolDAO = tipoArbolDAO;
	}

	public GenericDao getTipoArbolDAO() {
		return tipoArbolDAO;
	}
}
