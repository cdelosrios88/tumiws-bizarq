package pe.com.tumi.persona.empresa.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.empresa.dao.EmpresaDao;
import pe.com.tumi.persona.empresa.dao.impl.EmpresaDaoIbatis;
import pe.com.tumi.persona.empresa.domain.Empresa;

public class EmpresaBO {
	
	protected  static Logger log = Logger.getLogger(EmpresaBO.class);
	private EmpresaDao dao = (EmpresaDao)TumiFactory.get(EmpresaDaoIbatis.class);
	
	public Empresa grabarEmpresa(Empresa o) throws BusinessException{
		log.info("-----------------------Debugging EmpresaBO.getListaEmpresa-----------------------------");
		Empresa dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Empresa modificarEmpresa(Empresa o) throws BusinessException{
		Empresa dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Empresa getEmpresaPorPK(Integer pIntPK) throws BusinessException{
		Empresa domain = null;
		List<Empresa> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdEmpresa", pIntPK);
			lista = dao.getListaEmpresaPorPK(mapa);
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
	
	public List<Empresa> getListaEmpresaTodos() throws BusinessException{
		List<Empresa> lista = null;
		try{
			HashMap mapa = new HashMap();
			lista = dao.getListaTodos(mapa);			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
}
