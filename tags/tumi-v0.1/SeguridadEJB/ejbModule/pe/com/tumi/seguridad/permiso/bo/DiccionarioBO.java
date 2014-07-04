package pe.com.tumi.seguridad.permiso.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.permiso.dao.DiccionarioDao;
import pe.com.tumi.seguridad.permiso.dao.impl.DiccionarioDaoIbatis;
import pe.com.tumi.seguridad.permiso.domain.Diccionario;

public class DiccionarioBO {

	private DiccionarioDao dao = (DiccionarioDao)TumiFactory.get(DiccionarioDaoIbatis.class);
	
	public Diccionario grabarDiccionario(Diccionario o) throws BusinessException {
		Diccionario dto = null;
		try{
			o.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Diccionario modificarDiccionario(Diccionario o) throws BusinessException{
		Diccionario dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Diccionario getListaDiccionarioPorPk(Integer pId) throws BusinessException{
		List<Diccionario> lista = null;
		Diccionario domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intCodigo", pId);
			lista = dao.getListaPorPk(mapa);
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
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
}
