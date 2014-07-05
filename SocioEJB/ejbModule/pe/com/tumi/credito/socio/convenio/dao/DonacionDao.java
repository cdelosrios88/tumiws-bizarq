package pe.com.tumi.credito.socio.convenio.dao;

import java.util.List;

import pe.com.tumi.credito.socio.convenio.domain.DonacionRegalia;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface DonacionDao extends TumiDao{
	public DonacionRegalia grabar(DonacionRegalia o) throws DAOException;
	public DonacionRegalia modificar(DonacionRegalia o) throws DAOException;
	public List<DonacionRegalia> getListaDonacionRegaliaPorPK(Object o) throws DAOException;
	public List<DonacionRegalia> getListaDonacionRegaliaPorPKAdenda(Object o) throws DAOException;
}
