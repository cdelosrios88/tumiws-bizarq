package pe.com.tumi.riesgo.archivo.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.riesgo.archivo.dao.ConfEstructuraDao;
import pe.com.tumi.riesgo.archivo.dao.impl.ConfEstructuraDaoIbatis;
import pe.com.tumi.riesgo.archivo.domain.ConfEstructura;
import pe.com.tumi.riesgo.archivo.domain.ConfEstructuraId;

public class ConfEstructuraBO {
	
	private ConfEstructuraDao dao = (ConfEstructuraDao)TumiFactory.get(ConfEstructuraDaoIbatis.class);
	
	public ConfEstructura grabar(ConfEstructura o) throws BusinessException{
		ConfEstructura dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ConfEstructura modificar(ConfEstructura o) throws BusinessException{
		ConfEstructura dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ConfEstructura getPorPk(ConfEstructuraId pId) throws BusinessException{
		ConfEstructura domain = null;
		List<ConfEstructura> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intItemConfiguracion", pId.getIntItemConfiguracion());
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
	
	public List<ConfEstructura> getListaModTipoSoEmp(Integer intModalidad, Integer  intTipoSocio, 
								Integer intEmpresa, Integer intNivel, Integer intCodigo, Integer intFormato) throws BusinessException{
		List<ConfEstructura> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intModalidad",intModalidad);
			mapa.put("intTipoSocio",intTipoSocio);
			mapa.put("intEmpresa",intEmpresa);
			mapa.put("intNivel",intNivel);
			mapa.put("intCodigo",intCodigo);
			mapa.put("intFormato",intFormato);
			lista = dao.getListaModTipoSoEmp(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
