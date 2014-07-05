package pe.com.tumi.seguridad.empresa.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.empresa.domain.Area;
import pe.com.tumi.empresa.domain.AreaCodigo;
import pe.com.tumi.empresa.domain.AreaCodigoId;
import pe.com.tumi.empresa.domain.composite.AreaComp;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.empresa.dao.AreaCodigoDao;
import pe.com.tumi.seguridad.empresa.dao.impl.AreaCodigoDaoIbatis;

public class AreaCodigoBO {
	protected  static Logger log = Logger.getLogger(AreaCodigoBO.class);
	private AreaCodigoDao dao = (AreaCodigoDao)TumiFactory.get(AreaCodigoDaoIbatis.class);
	
	public AreaCodigo grabarAreaCodigo(AreaCodigo o) throws BusinessException {
		log.info("-----------------------Debugging AreaCodigoBO.grabarAreaCodigo-----------------------------");
		AreaCodigo dto = null;
		try{
			dto = dao.grabar(o);
			log.info("Se grabo AreaCodigo con intIdTipoCodigo: "+dto.getId().getIntIdTipoCodigoPk());
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public AreaCodigo modificarAreaCodigo(AreaCodigo o) throws BusinessException {
		log.info("-----------------------Debugging AreaCodigoBO.modificarAreaCodigo-----------------------------");
		AreaCodigo dto = null;
		try{
			dto = dao.modificar(o);
			log.info("Se modifico AreaCodigo con intIdTipoCodigo: "+dto.getId().getIntIdTipoCodigoPk());
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}

	public List<AreaCodigo> getListaAreaCodigo(Area o) throws BusinessException {
		log.info("-----------------------Debugging AreaCodigoBO.getListaAreaCodigo-----------------------------");
		List<AreaCodigo> lista = null;
		
		log.info("Seteando los parametros de busqueda de AreaCodigo: ");
		log.info("-------------------------------------------------");
		Area area = (Area)o;
		HashMap map = new HashMap();
		map.put("pIntIdEmpresaPk", area.getId().getIntPersEmpresaPk());
		map.put("pIntIdSucursalPk", area.getId().getIntIdSucursalPk());
		map.put("pIntIdAreaPk", area.getId().getIntIdArea());
		
		try{
			lista = dao.getListaAreaCodigo(map);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	public AreaCodigo getAreaCodigoPorPK(AreaCodigoId id) throws BusinessException {
		log.info("-----------------------Debugging AreaCodigoBO.getAreaCodigoPorPK-----------------------------");
		List<AreaCodigo> lista = null;
		AreaCodigo areaCodigo = null;
		
		log.info("Seteando los parametros de busqueda de AreaCodigo: ");
		log.info("-------------------------------------------------");
		HashMap map = new HashMap();
		map.put("pIntPersEmpresaPk", id.getIntIdEmpresaPk());
		map.put("pIntIdSucursalPk", id.getIntIdSucursalPk());
		map.put("pIntIdArea", id.getIntIdAreaPk());
		map.put("pIntIdAreaCodigo", id.getIntIdTipoCodigoPk());
		
		try{
			lista = dao.getAreaCodigoPorPK(map);
			if(lista!=null){
				if(lista.size()==1){
				   areaCodigo = lista.get(0);
				}else if(lista.size()==0){
				   areaCodigo = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return areaCodigo;
	}
}
