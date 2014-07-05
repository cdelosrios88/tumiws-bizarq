package pe.com.tumi.cobranza.planilla.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;

public interface EnvioconceptoDao extends TumiDao{
	public Envioconcepto grabar(Envioconcepto pDto) throws DAOException;
	public Envioconcepto grabarSub(Envioconcepto pDto) throws DAOException;
	public Envioconcepto modificar(Envioconcepto o) throws DAOException;
	public List<Envioconcepto> getListaPorPk(Object o) throws DAOException;
	public List<Envioconcepto> getListaPorMaxPeriodo(Object o) throws DAOException;
	public Integer getMaxPeriodoPorEmpresaYEstructuraYTipoSocioYModalidad(Object o) throws DAOException;
	public Integer getMaxPeriodoPorEmpresaYEstructuraYTipoSocio(Object o) throws DAOException;	
	public String getCsvCuentaPorEmpresaYTipoSocioYModalidadYPeriodo(Object o) throws DAOException;
	public Integer getMaxPeriodoPorEmpresaYEstructuraYTipoSocioYModalidadTipoEstru(Object o) throws DAOException ;
	public List<Envioconcepto> getListaPorEmpresaPeriodoYNroCta(Object o) throws DAOException;
	public List<Envioconcepto> getListaPorEmpresaNroCta(Object o) throws DAOException;	
	public List<Envioconcepto> getListaXPerCtaItemCto(Object o) throws DAOException;
	
	public List<Envioconcepto> getEnvioconceptoPorItemEnvioConcepto(Object o) throws DAOException;
	/** CREADO 05-08-2013 **/
	public List<Envioconcepto> getListaEnvioconceptoPorPkExpedienteCredito(Object o) throws DAOException;
	/** CREADO 08-08-2013 **/
	public List<Envioconcepto> getListaEnvioconceptoPorCtaCptoDetYPer(Object o) throws DAOException;
	public List<Envioconcepto> getListaEnvioconceptoPorCtaYPer(Object o) throws DAOException;
	
	//AUTOR Y FECHA CREACION: JCHAVEZ / 10-09-2013 
	public List<Envioconcepto> getListaPorCuentaYPeriodo(Object o) throws DAOException;
	public List<Envioconcepto> getListaPorEmpPerCta(Object o) throws DAOException;
	
	public Integer getMaxPeriodoPorEmpresaYEstructuraYTipoSocioCAS(Object o) throws DAOException;
	
	public List<Envioconcepto> getListaPorEmpresaPeriodoYNroCtaNivelCodigo(Object o) throws DAOException;
	public List<Envioconcepto> getListaEnvioMinimoPorEmpCtaYEstado(Object o) throws DAOException;
	
	public List<Envioconcepto> getCuentaOfConArchivo(Object o)throws DAOException;
	
	public List<Envioconcepto> getListaCuentaEnConArchivo(Object o) throws DAOException;
}	
