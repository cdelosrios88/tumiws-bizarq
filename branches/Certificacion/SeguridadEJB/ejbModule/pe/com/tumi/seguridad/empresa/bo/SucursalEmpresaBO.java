package pe.com.tumi.seguridad.empresa.bo;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.empresa.dao.SucursalEmpresaDao;
import pe.com.tumi.seguridad.empresa.dao.impl.SucursalEmpresaDaoIbatis;

public class SucursalEmpresaBO {

	private SucursalEmpresaDao dao = (SucursalEmpresaDao)TumiFactory.get(SucursalEmpresaDaoIbatis.class);
	
	public Integer getCantidadSucursalPorIdEmpresa(Integer intIdEmpresa) throws BusinessException{
		Integer cant = null;
		try{
			cant = dao.getCantidadSucursalPorIdEmpresa(intIdEmpresa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return cant;
	}
}
