package pe.com.tumi.credito.socio.aperturaCuenta.core.dao;

import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CuentaIntegranteDao extends TumiDao{
	public CuentaIntegrante grabar(CuentaIntegrante o) throws DAOException;
	public CuentaIntegrante modificar(CuentaIntegrante o) throws DAOException;
	public List<CuentaIntegrante> getListaCuentaIntegrantePorPK(Object o) throws DAOException;
	public List<CuentaIntegrante> getListaCuentaIntegrantePorPKCuenta(Object o) throws DAOException;
	public List<CuentaIntegrante> getListaCuentaIntegrantePorPKSocio(Object o) throws DAOException;
	public List<CuentaIntegrante> getListaPorPKSocioYTipoCuentaYTipoIntegrante(Object o) throws DAOException;
	public String getCsvPersonaPorEmpresaYTipoIntegranteYINCuenta(Object o) throws DAOException;
	public List<CuentaIntegrante> getListaCuentaIntegrantePorPersona(Object o) throws DAOException;
	public List<CuentaIntegrante> getListaIntegrantesPorPKPersona(Object o) throws DAOException;
	public List<CuentaIntegrante> getListaPorPKSocioYTipoCuenta(Object o) throws DAOException;
	/**
	 * Rewcupera lista CuentaIntegrante con cuenta activa.
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<CuentaIntegrante> getIdCtaActivaXPkSocio(Object o) throws DAOException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 04.10.2013 
	public List<CuentaIntegrante> getLstPorSocioPKTipoCtaSituacionCta(Object o) throws DAOException;
	
	public List<CuentaIntegrante> getCodPersonaOfCobranza(Object o) throws DAOException;
}