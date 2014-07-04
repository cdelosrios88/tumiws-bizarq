package pe.com.tumi.persona.empresa.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.empresa.dao.EmpresaDao;
import pe.com.tumi.persona.empresa.dao.EmpresaJuridicaDao;
import pe.com.tumi.persona.empresa.dao.impl.EmpresaDaoIbatis;
import pe.com.tumi.persona.empresa.dao.impl.EmpresaJuridicaDaoIbatis;
import pe.com.tumi.persona.empresa.domain.Empresa;

public class EmpresaJuridicaBO {
	
	protected  static Logger log = Logger.getLogger(EmpresaJuridicaBO.class);
	private EmpresaJuridicaDao dao = (EmpresaJuridicaDao)TumiFactory.get(EmpresaJuridicaDaoIbatis.class);
	
	public Empresa getEmpresaJuridicaPorPK(Integer pIntPK) throws BusinessException{
		log.info("-----------------------Debugging EmpresaJuridicaBO.getEmpresaJuridicaPorPK-----------------------------");
		Empresa domain = null;
		List<Empresa> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdEmpresa", pIntPK);
			lista = dao.getListaPorPK(mapa);
			log.info("lista.size: "+lista.size());
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
	
	public List<Empresa> getListaEmpresa(Empresa o) throws BusinessException{
		log.info("-----------------------Debugging EmpresaBO.getListaEmpresa-----------------------------");
		List<Empresa> lista = null;			
		
		try{
			HashMap map = new HashMap();
			map.put("pIntIdEmpresa", o.getIntIdEmpresa());
			map.put("pStrRazonSocial", o.getJuridica().getStrRazonSocial());
			map.put("pStrRuc", o.getJuridica().getPersona().getStrRuc());
			map.put("pIntTipoConformacionCod", o.getIntTipoConformacionCod());
			map.put("pIntEstadoCod", o.getIntEstadoCod());
			lista = dao.getListaEmpresa(map);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
