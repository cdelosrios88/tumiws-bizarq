package pe.com.tumi.seguridad.service.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.dao.GenericDao;
import pe.com.tumi.common.service.impl.GenericServiceImpl;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.seguridad.service.RolService;

public class RolServiceImpl extends GenericServiceImpl implements RolService{

	private GenericDao rolDAO;
	protected static Logger log = Logger.getLogger(RolServiceImpl.class);

	public void setRolDAO(GenericDao rolDAO) {
		this.rolDAO = rolDAO;
	}

	public GenericDao getRolDAO() {
		return rolDAO;
	}
	
}