package pe.com.tumi.adminCuenta.service.impl;

import java.util.ArrayList;

import pe.com.tumi.adminCuenta.dao.AdminCuentaDao;
import pe.com.tumi.adminCuenta.domain.PersonaJuridica;
import pe.com.tumi.adminCuenta.service.AdminCuentaService;
import pe.com.tumi.common.util.DaoException;

public class AdminCuentaServiceImpl implements AdminCuentaService{
	
	private AdminCuentaDao adminCuentaDAO;
	

	public AdminCuentaDao getAdminCuentaDAO() {
		return adminCuentaDAO;
	}

	public void setAdminCuentaDAO(AdminCuentaDao adminCuentaDAO) {
		this.adminCuentaDAO = adminCuentaDAO;
	}

	public void grabarPerJuridica(Object juri) throws DaoException {
		try{
			getAdminCuentaDAO().grabarPerJuridica(juri);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public ArrayList obtenerPerJuridica(Object juri) throws DaoException {
		try{
			return getAdminCuentaDAO().obtenerPerJuridica(juri);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
}
