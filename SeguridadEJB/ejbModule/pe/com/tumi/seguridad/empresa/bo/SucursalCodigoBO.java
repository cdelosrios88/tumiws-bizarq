package pe.com.tumi.seguridad.empresa.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.empresa.domain.SucursalCodigo;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.contacto.domain.Comunicacion;
import pe.com.tumi.seguridad.empresa.dao.SucursalCodigoDao;
import pe.com.tumi.seguridad.empresa.dao.impl.SucursalCodigoDaoIbatis;

public class SucursalCodigoBO {

	private SucursalCodigoDao dao = (SucursalCodigoDao)TumiFactory.get(SucursalCodigoDaoIbatis.class);
	
	public List<SucursalCodigo> getListaSucursalCodigoPorIdSucursal(Integer pId) throws BusinessException{
		List<SucursalCodigo> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdSucursal", pId);
			lista = dao.getListaSucursalCodigoPorIdSucursal(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public SucursalCodigo getSucursalCodigoPorPK(SucursalCodigo pPK) throws BusinessException{
		SucursalCodigo domain = null;
		List<SucursalCodigo> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", pPK.getSucursal().getId().getIntPersEmpresaPk());
			mapa.put("intIdSucursal", 	 pPK.getSucursal().getId().getIntIdSucursal());
			mapa.put("intIdTipoCodigo",  pPK.getSucursal().getIntIdTipoSucursal());
			lista = dao.getListaSucursalCodigoPorPK(mapa);
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
	
	public SucursalCodigo modificarSucursalCodigo(SucursalCodigo o) throws BusinessException{
		SucursalCodigo dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public SucursalCodigo grabarSucursalCodigo(SucursalCodigo o) throws BusinessException {
		SucursalCodigo dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
}
