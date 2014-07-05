package pe.com.tumi.persona.core.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.dao.NaturalDao;
import pe.com.tumi.persona.core.dao.impl.NaturalDaoIbatis;
import pe.com.tumi.persona.core.domain.Natural;

public class NaturalBO {
	
	private NaturalDao dao = (NaturalDao)TumiFactory.get(NaturalDaoIbatis.class);
	protected static Logger log = Logger.getLogger(NaturalBO.class);
	
	public Natural grabarNatural(Natural o) throws BusinessException{
		Natural dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Natural modificarNatural(Natural o) throws BusinessException{
		Natural dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Natural getNaturalPorPK(Integer pIntPK) throws BusinessException{
		Natural domain = null;
		List<Natural> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdPersona", pIntPK);
			lista = dao.getListaNaturalPorPK(mapa);
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
	
	public List<Natural> getListaNaturalPorInPk(String pStrPK) throws BusinessException{
		List<Natural> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("csvIdPersona", pStrPK);
			lista = dao.getListaNaturalPorInPk(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Natural getNaturalPorTipoIdentidadYNroIdentidad(Integer intTipoIdentidad ,String strNroIdentidad) throws BusinessException{
		Natural domain = null;
		List<Natural> lista = null;
		try{
			log.info(intTipoIdentidad);
			log.info(strNroIdentidad);
			HashMap mapa = new HashMap();
			mapa.put("pIntTipoIdentidadCod", intTipoIdentidad);
			mapa.put("pStrNroIdentidad", strNroIdentidad);
			lista = dao.getNaturalPorTipoIdentidadYNroIdentidad(mapa);
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
	
	public List<Natural> getListaNaturalBusqueda(Natural o)throws BusinessException {
		List<Natural> lista = null;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("pIntIdPersona", o.getIntIdPersona());
		map.put("pStrApellidoPaterno", o.getStrApellidoPaterno());
		map.put("pStrApellidoMaterno", o.getStrApellidoMaterno());
		map.put("pStrNombres", o.getStrNombres());
		map.put("pDtFechaNacimiento", o.getDtFechaNacimiento());
		map.put("pIntSexoCod", o.getIntSexoCod());
		map.put("pIntEstadoCivilCod", o.getIntEstadoCivilCod());
		map.put("pIntTieneEmpresa", o.getIntTieneEmpresa());
		map.put("pFechaDesde", o.getDtFechaNacDesde());
		map.put("pFechaHasta", o.getDtFechaNacHasta());
		log.info(o);
		try{
			lista = dao.getListaPerNaturalBusqueda(map);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
   
	public List<Natural> getListaBusqRolODniONomb(Integer pIntIdEmpresa,String pStrNombres,Integer pIntTipoIdentidadCod, String pStrNumeroIdentidad,Integer pIntParaRolPk)throws BusinessException {
		List<Natural> lista = null;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("pIntIdEmpresa", pIntIdEmpresa);
		map.put("pStrNombres",pStrNombres);
		map.put("pIntTipoIdentidadCod", pIntTipoIdentidadCod);
		map.put("pStrNumeroIdentidad", pStrNumeroIdentidad);
		map.put("pIntParaRolPk",pIntParaRolPk);
		
		try{
			lista = dao.getListaBusqRolODniONomb(map);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	
}
