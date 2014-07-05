package pe.com.tumi.seguridad.permiso.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.permiso.dao.ComputadoraDao;
import pe.com.tumi.seguridad.permiso.dao.impl.ComputadoraDaoIbatis;
import pe.com.tumi.seguridad.permiso.domain.Computadora;
import pe.com.tumi.seguridad.permiso.domain.ComputadoraId;

public class ComputadoraBO {

	private ComputadoraDao dao = (ComputadoraDao)TumiFactory.get(ComputadoraDaoIbatis.class);
	
	public Computadora grabarComputadora(Computadora o) throws BusinessException {
		Computadora dto = null;
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
	
	public Computadora modificarComputadora(Computadora o) throws BusinessException{
		Computadora dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Computadora getComputadoraPorPk(ComputadoraId pId) throws BusinessException{
		List<Computadora> lista = null;
		Computadora domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intIdSucursal", pId.getIntIdSucursal());
			mapa.put("intIdArea", pId.getIntIdArea());
			mapa.put("intIdComputadora", pId.getIntIdComputadora());
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
	
	public List<Computadora> getComputadoraPorBusqueda(Computadora c) throws BusinessException{
		List<Computadora> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", c.getId().getIntPersEmpresaPk());
			mapa.put("intIdSucursal", c.getId().getIntIdSucursal());
			mapa.put("intIdArea", c.getId().getIntIdArea());
			mapa.put("intIdComputadora", c.getId().getIntIdComputadora());
			mapa.put("intIdEstado", c.getIntIdEstado());
			lista = dao.getListaBusqueda(mapa);			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
}
