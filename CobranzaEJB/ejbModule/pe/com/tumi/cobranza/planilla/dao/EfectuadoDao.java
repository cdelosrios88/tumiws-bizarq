package pe.com.tumi.cobranza.planilla.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.cobranza.planilla.domain.Efectuado;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoConcepto;

public interface EfectuadoDao extends TumiDao{
	public Efectuado grabar(Efectuado pDto) throws DAOException;
	public Efectuado modificar(Efectuado o) throws DAOException;
	public List<Efectuado> getListaPorPk(Object o) throws DAOException;
	public List<Efectuado> getListaPorIdEmpresaYPkEstructuraYTipoModalidadYPeriodo(Object o) throws DAOException;
	public Integer getMaxPeriodoPorEmpresaYEstructuraYTipoSocioYModalidad(Object o) throws DAOException;
	public Integer getMaxPeriodoPorEmpresaYEstructuraYTipoSocioYModalidadTipoEstrucura(Object o) throws DAOException;
	
	public Integer getMaxPeriodoPorEmpresaYEstructuraYTipoSocio(Object o) throws DAOException;
	/** CREADO 06/08/2013 **/	
	public List<Efectuado> getListaEfectuadoPorPkEnviomontoYPeriodo(Object o) throws DAOException;
	public List<Efectuado> getListaEfectuadoXNiveCodigoModaliPeriodoTipoSocio(Object o) throws DAOException;
	
	public List<Efectuado> getEfectuadoPorItemEnvioConcepto(Object o) throws DAOException;	
	public List<Efectuado> getListaPorEmpCtaPeriodo(Object o) throws DAOException;

	public List<Efectuado> getListaPorAdministra(Object o) throws DAOException;
	
	public List<Efectuado> getMontoTotalPorConcepto(Object o) throws DAOException;
	
	public Integer getNumerodeRolesUsuarios(Object o)throws DAOException;
	
}	
