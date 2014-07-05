package pe.com.tumi.cobranza.gestion.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.gestion.dao.GestionCobranzaSocDao;
import pe.com.tumi.cobranza.gestion.dao.impl.GestionCobranzaSocDaoIbatis;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaSoc;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
 
public class GestionCobranzaSocBO {
	
protected  static Logger log = Logger.getLogger(GestionCobranzaSocBO.class);
private GestionCobranzaSocDao dao = (GestionCobranzaSocDao)TumiFactory.get(GestionCobranzaSocDaoIbatis.class);
	
	public List<GestionCobranzaSoc> getListaGestionCobranzaSoc(Object o) throws BusinessException{
		log.info("-----------------------Debugging GestionCobranzaSocBO.getListaGestionCobranzaSoc-----------------------------");
		List<GestionCobranzaSoc> lista = null;
		
		log.info("Seteando los parametros de busqueda de getListaGestionCobranzaSoc: ");
		log.info("-------------------------------------------------");
		GestionCobranzaSoc gestionCobranzaSoc = (GestionCobranzaSoc)o;
		HashMap map = new HashMap();
		
		map.put("intPersEmpresaGestion", gestionCobranzaSoc.getId().getIntPersEmpresaGestion());
		map.put("intItemGestionCobranza", gestionCobranzaSoc.getId().getIntItemGestionCobranza());
		try{
			lista = dao.getListaGestionCobranzaSoc(map);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public GestionCobranzaSoc getGestionCobranzaSocPorPK(GestionCobranzaSoc o) throws BusinessException {
		log.info("-----------------------Debugging GestionCobranzaSocBO.getGestionCobranzaSocPorPK-----------------------------");
		List<GestionCobranzaSoc> lista = null;
		GestionCobranzaSoc GestionCobranzaSoc = null;
		
		log.info("Seteando los parametros de busqueda de getGestionCobranzaSocPorPK : ");
		log.info("-------------------------------------------------");
		HashMap map = new HashMap();
		
		map.put("intPersEmpresaGestion", o.getId().getIntPersEmpresaGestion());
		map.put("intItemGestionCobranza", o.getId().getIntItemGestionCobranza());
		map.put("intItemGestCobrSocio", o.getId().getIntItemGestCobrSocio());
		
		try{
			lista = dao.getGestionCobranzaSocPorPK(map);
			if(lista!=null){
				if(lista.size()==1){
				   GestionCobranzaSoc = lista.get(0);
				}else if(lista.size()==0){
				   GestionCobranzaSoc = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidSoce");
				}
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return GestionCobranzaSoc;
	}
	
	public GestionCobranzaSoc grabarGestionCobranzaSoc(GestionCobranzaSoc o) throws BusinessException {
		GestionCobranzaSoc dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public GestionCobranzaSoc modificarGestionCobranzaSoc(GestionCobranzaSoc o) throws BusinessException {
		GestionCobranzaSoc dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 04.10.2013
	 * Recupera Gestion Cobranza Soc por Cuenta Id desde un peiorio en adelante
	 * @param pId
	 * @param strFechaGestion
	 * @return
	 * @throws BusinessException
	 */
	public List<GestionCobranzaSoc> getListaPorCuentaPkYPeriodo(CuentaId pId, String strFechaGestion) throws BusinessException {
		List<GestionCobranzaSoc> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", 	pId.getIntPersEmpresaPk());
			mapa.put("intCsocCuenta",	pId.getIntCuenta());
			mapa.put("strFechaGestion",	strFechaGestion);
			lista = dao.getListaPorCuentaPkYPeriodo(mapa);	
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}		
		return lista;
	}
}
