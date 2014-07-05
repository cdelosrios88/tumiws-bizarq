package pe.com.tumi.contabilidad.core.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.contabilidad.core.dao.ModeloDao;
import pe.com.tumi.contabilidad.core.dao.ModeloDetalleDao;
import pe.com.tumi.contabilidad.core.dao.PlanCuentaDao;
import pe.com.tumi.contabilidad.core.dao.impl.ModeloDaoIbatis;
import pe.com.tumi.contabilidad.core.dao.impl.ModeloDetalleDaoIbatis;
import pe.com.tumi.contabilidad.core.dao.impl.PlanCuentaDaoIbatis;
import pe.com.tumi.contabilidad.core.domain.Modelo;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalle;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleId;
import pe.com.tumi.contabilidad.core.domain.ModeloId;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;

public class ModeloDetalleBO{

	private ModeloDetalleDao dao = (ModeloDetalleDao)TumiFactory.get(ModeloDetalleDaoIbatis.class);

	public ModeloDetalle grabarModeloDetalle(ModeloDetalle o) throws BusinessException{
		ModeloDetalle dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public ModeloDetalle modificarModeloDetalle(ModeloDetalle o) throws BusinessException{
     	ModeloDetalle dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public ModeloDetalle getModeloDetallePorPk(ModeloDetalleId pId) throws BusinessException{
		ModeloDetalle domain = null;
		List<ModeloDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("pIntEmpresaPk", pId.getIntEmpresaPk());
			mapa.put("pIntCodigoModelo", pId.getIntCodigoModelo());
			mapa.put("pIntPersEmpresaCuenta", pId.getIntPersEmpresaCuenta());
			mapa.put("pIntContPeriodoCuenta", pId.getIntContPeriodoCuenta());
			mapa.put("pStrContNumeroCuenta", pId.getStrContNumeroCuenta());
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
	
	public List<ModeloDetalle> getListaPorModeloId(ModeloId pId) throws BusinessException{
		List<ModeloDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			System.out.println("pId.intEmpresaPk: "+pId.getIntEmpresaPk());
			System.out.println("pId.intEmpresaPk: "+pId.getIntCodigoModelo());
			mapa.put("pIntEmpresaPk", pId.getIntEmpresaPk());
			mapa.put("pIntCodigoModelo", pId.getIntCodigoModelo());
			lista = dao.getListaPorModeloId(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	public ModeloDetalle eliminarModeloDetalle(ModeloDetalleId o) throws BusinessException{
		ModeloDetalle dto = null;
		try{
			System.out.println("eliminando...");
			System.out.println("intEmpresaPk: "+o.getIntEmpresaPk());
			System.out.println("intCodigoModelo: "+o.getIntCodigoModelo());
			System.out.println("intPersEmpresaCuenta: "+o.getIntPersEmpresaCuenta());
			System.out.println("intContPeriodoCuenta: "+o.getIntContPeriodoCuenta());
			System.out.println("strContNumeroCuenta: "+o.getStrContNumeroCuenta());
		    dto = dao.eliminar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}


    public List<ModeloDetalle> getListaDebeOfCobranza(Integer intEmpresa,    												
													  Integer intPeriodo,												
													  Integer intCodigoModelo) throws BusinessException{
				List<ModeloDetalle> lista = null;
				try{
					HashMap<String,Object> mapa = new HashMap<String,Object>();			
					mapa.put("intEmpresa", intEmpresa);
					mapa.put("intPeriodo", intPeriodo);
					mapa.put("intCodigoModelo",intCodigoModelo);
					lista = dao.getListaDebeOfCobranza(mapa);
				}catch(DAOException e){
					throw new BusinessException(e);
				}catch(Exception e) {
					throw new BusinessException(e);
				}
				return lista;
			}
    

	public List<ModeloDetalle> getListaDebeOfCobranzaUSUARIO10(Integer empresaDebe,
															  Integer periodoDebe,
															  Integer codigoModeloDebe,
															  Integer codigo) throws BusinessException
	{
		List<ModeloDetalle> lista = null;
		try
		{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intEmpresaDebe",empresaDebe);
			mapa.put("intPeriodoDebe",periodoDebe);
			mapa.put("intCodigoModeloDebe",codigoModeloDebe);
			mapa.put("intCodigo",codigo);
			lista = dao.getListaDebeOfCobranzaUSUARIO10(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e)
		{
			throw new BusinessException(e);
		}
		return lista;
	}


	public List<ModeloDetalle> getListaDebeOfCobranzaUSUARIONO10(Integer empresaDebe,
																  Integer periodoDebe,
																  Integer codigoModeloDebe,
																  Integer codigo) throws BusinessException
	{
		List<ModeloDetalle> lista = null;
		try
		{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intEmpresaDebe",empresaDebe);
			mapa.put("intPeriodoDebe",periodoDebe);
			mapa.put("intCodigoModeloDebe",codigoModeloDebe);
			mapa.put("intCodigo",codigo);
			lista = dao.getListaDebeOfCobranzaUSUARIONO10(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e)
		{
			throw new BusinessException(e);
		}
		return lista;
	}
	


	
}
