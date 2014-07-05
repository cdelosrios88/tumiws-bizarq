package pe.com.tumi.adminCuenta.service;

import java.util.ArrayList;

import pe.com.tumi.adminCuenta.dao.AdminCuentaDao;
import pe.com.tumi.adminCuenta.domain.PersonaJuridica;
import pe.com.tumi.common.util.DaoException;

public interface AdminCuentaService {
	public abstract AdminCuentaDao getAdminCuentaDAO();

	public abstract void grabarPerJuridica(Object juri) throws DaoException;

	public abstract ArrayList obtenerPerJuridica(Object juri) throws DaoException;
}
