package pe.com.tumi.adminCuenta.dao;

import java.util.ArrayList;

import pe.com.tumi.common.util.DaoException;

public interface AdminCuentaDao {

	void grabarPerJuridica(Object juri) throws DaoException;

	ArrayList obtenerPerJuridica(Object juri) throws DaoException;

}
