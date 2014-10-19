package pe.com.tumi.tesoreria.ingreso.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.ingreso.dao.IngresoDao;
import pe.com.tumi.tesoreria.ingreso.dao.impl.IngresoDaoIbatis;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoId;


public class IngresoBO{

	private IngresoDao dao = (IngresoDao)TumiFactory.get(IngresoDaoIbatis.class);

	public Ingreso grabar(Ingreso o) throws BusinessException{
		Ingreso dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public Ingreso modificar(Ingreso o) throws BusinessException{
  		Ingreso dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public Ingreso getPorId(IngresoId pId) throws BusinessException{
		Ingreso domain = null;
		List<Ingreso> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdEmpresa", pId.getIntIdEmpresa());
			mapa.put("intIdIngresoGeneral", pId.getIntIdIngresoGeneral());
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
	
	public List<Ingreso> getListaParaItem(Ingreso ingreso) throws BusinessException{
		List<Ingreso> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intParaDocumentoGeneral", ingreso.getIntParaDocumentoGeneral());
			mapa.put("intItemPeriodoIngreso", ingreso.getIntItemPeriodoIngreso());
			mapa.put("intSucuIdSucursal", ingreso.getIntSucuIdSucursal());
			lista = dao.getListaParaItem(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Ingreso> getListaParaBuscar(Ingreso ingreso) throws BusinessException{
		List<Ingreso> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdEmpresa", ingreso.getId().getIntIdEmpresa());
			mapa.put("intParaDocumentoGeneral", ingreso.getIntParaDocumentoGeneral());
			mapa.put("intItemIngreso", ingreso.getIntItemIngreso());
			mapa.put("dtFechaDesde", ingreso.getDtDechaDesde());
			mapa.put("dtFechaHasta", ingreso.getDtDechaHasta());
			mapa.put("bdMontoTotal", ingreso.getBdMontoTotal());
			mapa.put("intParaEstado", ingreso.getIntParaEstado());
			mapa.put("strNumeroOperacion", ingreso.getStrNumeroOperacion());
			mapa.put("intItemBancoFondo", ingreso.getIntItemBancoFondo());
			mapa.put("intItemBancoCuenta", ingreso.getIntItemBancoCuenta());
			lista = dao.getListaParaBuscar(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Ingreso> getListaParaDepositar(Integer intIdEmpresa, Integer intParaTipoMoneda, Usuario usuario) throws BusinessException{
		List<Ingreso> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdEmpresa", intIdEmpresa);
			mapa.put("intParaTipoMoneda", intParaTipoMoneda);
			mapa.put("intSucuIdSucursal", usuario.getSucursal().getId().getIntIdSucursal());
			mapa.put("intSudeIdSubsucursal", usuario.getSubSucursal().getId().getIntIdSubSucursal());
			lista = dao.getListaParaDepositar(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	public List<Ingreso> getListaIngNoEnlazados(Ingreso pIng) throws BusinessException{
		List<Ingreso> lista = null;	
		try{	
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdEmpresa", pIng.getId().getIntIdEmpresa());
			mapa.put("intSucuIdSucursal", pIng.getIntSucuIdSucursal());
			mapa.put("intSudeIdSubsucursal", pIng.getIntSudeIdSubsucursal());
			lista = dao.getListaIngNoEnlazados(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		
	return lista;
	}
}