package pe.com.tumi.credito.socio.estadoCuenta.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.composite.CuentaComp;
import pe.com.tumi.credito.socio.core.domain.SocioPK;
import pe.com.tumi.credito.socio.estadoCuenta.dao.impl.CuentaCompDao;
import pe.com.tumi.credito.socio.estadoCuenta.dao.impl.CuentaCompDaoIbatis;
import pe.com.tumi.credito.socio.estadoCuenta.dao.impl.EstadoCuentaDao;
import pe.com.tumi.credito.socio.estadoCuenta.dao.impl.EstadoCuentaDaoIbatis;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class EstadoCuentaBO {
	protected  static Logger log = Logger.getLogger(EstadoCuentaBO.class);
	private EstadoCuentaDao dao = (EstadoCuentaDao)TumiFactory.get(EstadoCuentaDaoIbatis.class);
	private CuentaCompDao ctaCompDao = (CuentaCompDao)TumiFactory.get(CuentaCompDaoIbatis.class);
	
	/** CREADO 08-08-2013
	 * OBTIENE DATOS CUENTA POR PK DEL SOCIO, TIPO Y SITUACION DE LA CUENTA
	 * **/
	public List<CuentaComp> getLsCtasPorPkSocioYTipCta(SocioPK pPK,Integer intParaTipoCuenta, Integer intParaSituacionCuentaCod, Integer intCuenta) throws BusinessException{
		
		List<CuentaComp> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pPK.getIntIdEmpresa());
			mapa.put("intPersonaIntegrante",	pPK.getIntIdPersona());
			mapa.put("intParaTipoCuentaCod", 	intParaTipoCuenta);
			mapa.put("intParaSituacionCuentaCod", intParaSituacionCuentaCod);
			mapa.put("intCuenta", intCuenta);
			
			lista = dao.getLsCtasPorPkSocioYTipCta(mapa);	
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}		
		return lista;
	}	
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 29.10.2013
	 * Recupera Datos de Cta. Integrante Y Cuenta por Socio, Tipo Cta. y Situacion Cta.
	 * @param pPK
	 * @param intCuenta
	 * @param intParaTipoCuenta
	 * @param intParaSituacionCuentaCod
	 * @return
	 * @throws BusinessException
	 */
	public List<CuentaComp> getCtaIntYCtaXSocTipYSitCta(SocioPK pPK,Integer intCuenta, Integer intParaTipoCuenta, Integer intParaSituacionCuentaCod) throws BusinessException{
		List<CuentaComp> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pPK.getIntIdEmpresa());
			mapa.put("intCuenta", intCuenta);
			mapa.put("intPersonaIntegrante",	pPK.getIntIdPersona());
			mapa.put("intParaTipoCuentaCod", 	intParaTipoCuenta);
			mapa.put("intParaSituacionCuentaCod", intParaSituacionCuentaCod);
			lista = ctaCompDao.getCtaIntYCtaXSocTipYSitCta(mapa);	
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}		
		return lista;
	}	
}
