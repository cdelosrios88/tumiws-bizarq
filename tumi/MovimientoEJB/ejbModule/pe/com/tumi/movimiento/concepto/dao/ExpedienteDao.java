package pe.com.tumi.movimiento.concepto.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.movimiento.concepto.domain.Expediente;

public interface ExpedienteDao extends TumiDao{
	public Expediente grabar(Expediente o) throws DAOException;
	public Expediente modificar(Expediente o) throws DAOException;
	public List<Expediente> getListaPorPK(Object o) throws DAOException;
	public List<Expediente> getListaConSaldoPorEmpresaYcuenta(Object o) throws DAOException;
	public List<Expediente> getListaPorEmpresaYCta(Object o) throws DAOException;
	public List<Expediente> getListaExpedienteConSaldoPorEmpresaCtaYTipoCred(Object o) throws DAOException;
	public List<Expediente> getListaXEmpCtaExp(Object o) throws DAOException;
}
