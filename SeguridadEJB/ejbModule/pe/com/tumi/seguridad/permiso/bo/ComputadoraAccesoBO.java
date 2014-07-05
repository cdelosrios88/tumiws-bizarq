package pe.com.tumi.seguridad.permiso.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.permiso.dao.ComputadoraAccesoDao;
import pe.com.tumi.seguridad.permiso.dao.impl.ComputadoraAccesoDaoIbatis;
import pe.com.tumi.seguridad.permiso.domain.Computadora;
import pe.com.tumi.seguridad.permiso.domain.ComputadoraAcceso;
import pe.com.tumi.seguridad.permiso.domain.ComputadoraAccesoId;

public class ComputadoraAccesoBO {

	private ComputadoraAccesoDao dao = (ComputadoraAccesoDao)TumiFactory.get(ComputadoraAccesoDaoIbatis.class);
	
	public ComputadoraAcceso grabarComputadoraAcceso(ComputadoraAcceso o) throws BusinessException {
		ComputadoraAcceso dto = null;
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
	
	public ComputadoraAcceso modificarComputadoraAcceso(ComputadoraAcceso o) throws BusinessException{
		ComputadoraAcceso dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ComputadoraAcceso getComputadoraAccesoPorPk(ComputadoraAccesoId pId) throws BusinessException{
		List<ComputadoraAcceso> lista = null;
		ComputadoraAcceso domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intIdSucursal", pId.getIntIdSucursal());
			mapa.put("intIdArea", pId.getIntIdArea());
			mapa.put("intIdComputadora", pId.getIntIdComputadora());
			mapa.put("intIdComputadoraAcceso", pId.getIntIdComputadoraAcceso());
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
	
	public List<ComputadoraAcceso> getComputadoraAccesoPorCabecera(Computadora o) throws BusinessException{
		List<ComputadoraAcceso> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", o.getId().getIntPersEmpresaPk());
			mapa.put("intIdSucursal", o.getId().getIntIdSucursal());
			mapa.put("intIdArea", o.getId().getIntIdArea());
			mapa.put("intIdComputadora", o.getId().getIntIdComputadora());
			lista = dao.getListaPorCabecera(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
}
