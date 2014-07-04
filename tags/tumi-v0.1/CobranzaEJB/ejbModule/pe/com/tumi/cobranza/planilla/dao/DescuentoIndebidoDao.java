package pe.com.tumi.cobranza.planilla.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.cobranza.planilla.domain.DescuentoIndebido;

public interface DescuentoIndebidoDao extends TumiDao{
	public DescuentoIndebido grabar(DescuentoIndebido pDto) throws DAOException;
	public DescuentoIndebido modificar(DescuentoIndebido o) throws DAOException;
	public List<DescuentoIndebido>  getListaPorEmpCptoEfeGnralyCuenta(Object o) throws DAOException;
	public List<DescuentoIndebido> getListPorEmpYCta(Object o) throws DAOException;
		
}	
