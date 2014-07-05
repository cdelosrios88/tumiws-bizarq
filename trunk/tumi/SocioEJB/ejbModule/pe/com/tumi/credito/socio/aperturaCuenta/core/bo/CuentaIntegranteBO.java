package pe.com.tumi.credito.socio.aperturaCuenta.core.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.dao.CuentaIntegranteDao;
import pe.com.tumi.credito.socio.aperturaCuenta.core.dao.impl.CuentaIntegranteDaoIbatis;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegranteId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.composite.CuentaComp;
import pe.com.tumi.credito.socio.core.domain.SocioPK;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class CuentaIntegranteBO {
	private CuentaIntegranteDao dao = (CuentaIntegranteDao)TumiFactory.get(CuentaIntegranteDaoIbatis.class);
	
	public CuentaIntegrante grabar(CuentaIntegrante o) throws BusinessException{
		CuentaIntegrante dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CuentaIntegrante modificar(CuentaIntegrante o) throws BusinessException{
		CuentaIntegrante dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CuentaIntegrante getCuentaIntegrantePorPK(CuentaIntegranteId pPK) throws BusinessException{
		CuentaIntegrante domain = null;
		List<CuentaIntegrante> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 	pPK.getIntPersEmpresaPk());
			mapa.put("intCuenta", 		 	pPK.getIntCuenta());
			mapa.put("intPersonaIntegrante",pPK.getIntPersonaIntegrante());
			lista = dao.getListaCuentaIntegrantePorPK(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public List<CuentaIntegrante> getListaCuentaIntegrantePorPKCuenta(CuentaId pPK) throws BusinessException{
		List<CuentaIntegrante> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 	pPK.getIntPersEmpresaPk());
			mapa.put("intCuenta", 		 	pPK.getIntCuenta());
			lista = dao.getListaCuentaIntegrantePorPK(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	/**
	 * Recupera CuentaIntegrante de cuenta en situacion activa
	 * @param pPK
	 * @return
	 * @throws BusinessException
	 */
	public CuentaIntegrante getCuentaIntegrantePorPKSocio(CuentaIntegranteId pPK) throws BusinessException{
		CuentaIntegrante domain = null;
		List<CuentaIntegrante> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 	pPK.getIntPersEmpresaPk());
			mapa.put("intPersonaIntegrante",pPK.getIntPersonaIntegrante());
			lista = dao.getIdCtaActivaXPkSocio(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public CuentaIntegrante getCuentaIntegrantePorPKSocioYTipoCuentaYTipoIntegrante(SocioPK pPK,Integer intParaTipoCuenta,Integer intParaTipoIntegrante) throws BusinessException{
		CuentaIntegrante domain = null;
		List<CuentaIntegrante> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pPK.getIntIdEmpresa());
			mapa.put("intPersonaIntegrante",	pPK.getIntIdPersona());
			mapa.put("intParaTipoCuentaCod", 	intParaTipoCuenta);
			mapa.put("intParaTipoIntegranteCod",intParaTipoIntegrante);
			lista = dao.getListaPorPKSocioYTipoCuentaYTipoIntegrante(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public String getCsvPersonaPorEmpresaYTipoIntegranteYINCuenta(Integer intEmpresa,Integer intTipoIntegrante,String strINCuenta) throws BusinessException{
		String strEscalar = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", intEmpresa);
			mapa.put("intParaTipoIntegranteCod", intTipoIntegrante);
			mapa.put("strCsvCuenta", strINCuenta);
			strEscalar = dao.getCsvPersonaPorEmpresaYTipoIntegranteYINCuenta(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return strEscalar;
	}
	
	public List<CuentaIntegrante> getCuentaIntegrantePorIdPersona(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException{
		List<CuentaIntegrante> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 	 intIdEmpresa);
			mapa.put("intPersonaIntegrante", intIdPersona);
			lista = dao.getListaCuentaIntegrantePorPersona(mapa);

		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/**
	 * Recupera List<CuentaIntegrante> en base a intPersEmpresaPk y intPersonaIntegrante.
	 * Y cuyo estado de cuenta sean 1.
	 * @param pPK
	 * @return
	 * @throws BusinessException
	 */
	public List<CuentaIntegrante> getCuentaIntegrantePorPkPersona(CuentaIntegranteId pPK) throws BusinessException{
		List<CuentaIntegrante> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 	pPK.getIntPersEmpresaPk());
			mapa.put("intPersonaIntegrante",pPK.getIntPersonaIntegrante());
			lista = dao.getIdCtaActivaXPkSocio(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<CuentaIntegrante> getListaCuentaIntegrantePorCuenta(CuentaId cuentaId) throws BusinessException{
		List<CuentaIntegrante> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 	cuentaId.getIntPersEmpresaPk());
			mapa.put("intCuenta",cuentaId.getIntCuenta());
			lista = dao.getListaCuentaIntegrantePorPKCuenta(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	/**
	 * Recupera Cuenta iNtegrante cuya cuenta esta en estado Activo
	 * @param pPK
	 * @return
	 * @throws BusinessException
	 */
	public CuentaIntegrante getIdCtaActivaXPkSocio(CuentaIntegranteId pPK) throws BusinessException{
		CuentaIntegrante domain = null;
		List<CuentaIntegrante> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 	pPK.getIntPersEmpresaPk());
			mapa.put("intPersonaIntegrante",pPK.getIntPersonaIntegrante());
			lista = dao.getIdCtaActivaXPkSocio(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente - getIdCtaActivaXPkSocio");
				}
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 04.10.2013
	 * Recupera Cta. Integrante por Empresa, Persona Integrante, Tipo Cta. y Situacion Cta.
	 * @param pPK
	 * @param intParaTipoCuenta
	 * @param intParaSituacionCuentaCod
	 * @return
	 * @throws BusinessException
	 */
	public List<CuentaIntegrante> getLstPorSocioPKTipoCtaSituacionCta(SocioPK pPK,Integer intParaTipoCuenta, Integer intParaSituacionCuentaCod) throws BusinessException{
		List<CuentaIntegrante> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pPK.getIntIdEmpresa());
			mapa.put("intPersonaIntegrante",	pPK.getIntIdPersona());
			mapa.put("intParaTipoCuentaCod", 	intParaTipoCuenta);
			mapa.put("intParaSituacionCuentaCod", intParaSituacionCuentaCod);
			
			lista = dao.getLstPorSocioPKTipoCtaSituacionCta(mapa);	
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}		
		return lista;
	}
	/**
	 * EfectuadoCon Archivo en cobranza mediante una cuenta y empresa traigo solo su codPersona
	 * @return
	 * @throws BusinessException
	 */
	public CuentaIntegrante getCodPersonaOfCobranza(Integer empresa,
													Integer cuenta) throws BusinessException
	{
		CuentaIntegrante domain = null;
		List<CuentaIntegrante> lista = null;
		try
		{
			HashMap mapa= new HashMap();
			mapa.put("empresa", empresa );
			mapa.put("cuenta", cuenta);
			lista= dao.getCodPersonaOfCobranza(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente - getIdCtaActivaXPkSocio");
				}
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
}
