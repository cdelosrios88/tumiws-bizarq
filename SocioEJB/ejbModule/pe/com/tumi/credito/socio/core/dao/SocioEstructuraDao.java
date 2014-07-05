package pe.com.tumi.credito.socio.core.dao;

import java.util.List;

import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface SocioEstructuraDao extends TumiDao{
	public List<SocioEstructura> getListaSocioEstructuraBusq(Object o) throws DAOException;
	public SocioEstructura grabar(SocioEstructura o) throws DAOException;
	public SocioEstructura modificar(SocioEstructura o) throws DAOException;
	public List<SocioEstructura> getListaSocioEstructuraPorPK(Object o) throws DAOException;
	public List<SocioEstructura> getListaPorPkSocioYPkEstructuraYTipoEstructura(Object o) throws DAOException;
	public List<SocioEstructura> getListaSocioEstructuraPorSocioPK(Object o) throws DAOException;
	public List<SocioEstructura> getListaSocioEstructuraPorPkSocioYTipoEstructura(Object o) throws DAOException;
	public List<SocioEstructura> getListaPorPkSocioYPkEstructura(Object o) throws DAOException;
	
	public List<SocioEstructura> getListaSocioEstructuraPorSocioPKyActivo(Object o) throws DAOException;
	
	public List<SocioEstructura> getListaSocioEstructuraPorPKTipoSocio(Object o) throws DAOException;
	//public List<SocioEstructura> getListaSocEstPorSocioPKActivoTipoSocio(Object o) throws DAOException;
	
	public List<SocioEstructura> getListaXSocioPKActivoTipoSocio(Object o) throws DAOException;
	
	public List<SocioEstructura> getListaXNivelCodigoNoCas(Object o) throws DAOException;
	
	public List<SocioEstructura> getListaXNivelCodigosoloCas(Object o) throws DAOException;
	
	public List<SocioEstructura> getListaSocioEstructuraAgregarSocioEfectuado(Object o) throws DAOException;
	
	public List<SocioEstructura> getLstBySocioAndEst(Object o) throws DAOException;
	
	public List<SocioEstructura> getListaXAdminySubAdminHABERINCENT(Object o) throws DAOException;
	
	public List<SocioEstructura> getListaXNivelCodigosoloHaberIncentivo(Object o) throws DAOException;
	
	public List<SocioEstructura> getListaPorCodPersonaOfEnviado(Object o) throws DAOException;
	
	public List<SocioEstructura> getListaXAdminySubAdminSOLOCAS(Object o) throws DAOException;
}
