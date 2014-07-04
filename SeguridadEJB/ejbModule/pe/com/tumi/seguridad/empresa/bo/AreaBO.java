package pe.com.tumi.seguridad.empresa.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.empresa.domain.Area;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.empresa.dao.AreaDao;
import pe.com.tumi.seguridad.empresa.dao.impl.AreaDaoIbatis;

public class AreaBO {
	
protected  static Logger log = Logger.getLogger(AreaBO.class);
private AreaDao dao = (AreaDao)TumiFactory.get(AreaDaoIbatis.class);
	
	public List<Area> getListaArea(Object o) throws BusinessException{
		//log.info("-----------------------Debugging AreaBO.getListaArea-----------------------------");
		List<Area> lista = null;
		
		//log.info("Seteando los parametros de busqueda de áreas: ");
		//log.info("-------------------------------------------------");
		Area area = (Area)o;
		HashMap map = new HashMap();
		map.put("pIntIdEmpresa", area.getId().getIntPersEmpresaPk());
		map.put("pIntIdSucursal", area.getId().getIntIdSucursalPk());
		map.put("pIntIdArea", area.getId().getIntIdArea());
		map.put("pStrDescripcion", area.getStrDescripcion());
		map.put("pIntIdTipoArea", area.getIntIdTipoArea());
		map.put("pIntIdEstado", area.getIntIdEstado());
		
		try{
			lista = dao.getListaArea(map);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Area getAreaPorPK(Area o) throws BusinessException {
		//log.info("-----------------------Debugging AreaBO.getAreaPorPK-----------------------------");
		List<Area> lista = null;
		Area area = null;
		
		//log.info("Seteando los parametros de busqueda de áreas: ");
		//log.info("-------------------------------------------------");
		HashMap map = new HashMap();
		map.put("pIntPersEmpresaPk", o.getId().getIntPersEmpresaPk());
		map.put("pIntIdSucursalPk", o.getId().getIntIdSucursalPk());
		map.put("pIntIdArea", o.getId().getIntIdArea());
		
		try{
			lista = dao.getAreaPorPK(map);
			if(lista!=null){
				if(lista.size()==1){
				   area = lista.get(0);
				}else if(lista.size()==0){
				   area = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return area;
	}
	
	public Area grabarArea(Area o) throws BusinessException {
		Area dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Area modificarArea(Area o) throws BusinessException {
		Area dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
}
