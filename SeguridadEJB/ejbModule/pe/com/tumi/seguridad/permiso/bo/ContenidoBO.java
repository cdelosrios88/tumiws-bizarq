package pe.com.tumi.seguridad.permiso.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.permiso.dao.ContenidoDao;
import pe.com.tumi.seguridad.permiso.dao.impl.ContenidoDaoIbatis;
import pe.com.tumi.seguridad.permiso.domain.Contenido;

public class ContenidoBO {

	private ContenidoDao dao = (ContenidoDao)TumiFactory.get(ContenidoDaoIbatis.class);
	
	public Contenido grabarContenido(Contenido o) throws BusinessException {
		Contenido dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Contenido modificarContenido(Contenido o) throws BusinessException{
		Contenido dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Contenido getListaContenidoPorPk(Integer pId) throws BusinessException{
		List<Contenido> lista = null;
		Contenido domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pId);
			mapa.put("intIdTransaccion", pId);
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
