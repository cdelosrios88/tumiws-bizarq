package pe.com.tumi.riesgo.archivo.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.riesgo.archivo.dao.NombreDao;
import pe.com.tumi.riesgo.archivo.dao.impl.NombreDaoIbatis;
import pe.com.tumi.riesgo.archivo.domain.Nombre;
import pe.com.tumi.riesgo.archivo.domain.NombreId;

public class NombreBO {
	
	private NombreDao dao = (NombreDao)TumiFactory.get(NombreDaoIbatis.class);
	
	public Nombre grabar(Nombre o) throws BusinessException{
		Nombre dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Nombre modificar(Nombre o) throws BusinessException{
		Nombre dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Nombre getPorPk(NombreId pId) throws BusinessException{
		Nombre domain = null;
		List<Nombre> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intItemConfiguracion", pId.getIntItemConfiguracion());
			mapa.put("intItemNombre", pId.getIntItemNombre());
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
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public List<Nombre> getPorIntItemConfiguracion(Integer intItemConfiguracion) throws BusinessException{
		List<Nombre> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intItemConfiguracion", intItemConfiguracion);
			lista = dao.getListaPorIntItemConfiguracion(mapa);			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
}
