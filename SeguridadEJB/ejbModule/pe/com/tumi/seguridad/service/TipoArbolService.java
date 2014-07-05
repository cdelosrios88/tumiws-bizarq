package pe.com.tumi.seguridad.service;

import java.util.List;

import pe.com.tumi.common.service.GenericService;
import pe.com.tumi.common.util.DaoException;

public interface TipoArbolService extends GenericService{

	//todas las opciones para el rol
	public abstract List getOpcionesxRol(Long id)  throws DaoException;
	//todo el arbol father & nodes para el usuario
	public abstract List getTreeByUser(Long id, Long sistema) throws DaoException;	
	//todos los nodes para el father con id=id
	public abstract List getNodes(Long id) throws DaoException;	

}
