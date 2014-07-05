package pe.com.tumi.contabilidad.cierre.bo;


import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import pe.com.tumi.contabilidad.cierre.dao.LibroDiarioDetalleDao;
import pe.com.tumi.contabilidad.cierre.dao.impl.LibroDiarioDetalleDaoIbatis;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalleId;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayor;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class LibroDiarioDetalleBO {
	
	private LibroDiarioDetalleDao dao = (LibroDiarioDetalleDao)TumiFactory.get(LibroDiarioDetalleDaoIbatis.class);

	public LibroDiarioDetalle grabar(LibroDiarioDetalle o) throws BusinessException{
		LibroDiarioDetalle dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
	
  	public LibroDiarioDetalle modificar(LibroDiarioDetalle o) throws BusinessException{
  		LibroDiarioDetalle dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public LibroDiarioDetalle getPorPk(LibroDiarioDetalleId pId) throws BusinessException{
		LibroDiarioDetalle domain = null;
		List<LibroDiarioDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaLibro", pId.getIntPersEmpresaLibro());
			mapa.put("intContPeriodoLibro", pId.getIntContPeriodoLibro());
			mapa.put("intContCodigoLibro", pId.getIntContCodigoLibro());
			mapa.put("intContItemLibro", pId.getIntContItemLibro());
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
	
	public List<LibroDiarioDetalle> getListaPorLibroMayorYPlanCuenta(LibroMayor o,PlanCuenta p) throws BusinessException{
		List<LibroDiarioDetalle> lista = null;
		try{
			DecimalFormat nft = new  DecimalFormat("#00.###"); 
			nft.setDecimalSeparatorAlwaysShown(false); 
			String strPeriodoLibro = o.getId().getIntContPeriodoMayor()+""+nft.format(o.getId().getIntContMesMayor());
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaMayor", o.getId().getIntPersEmpresaMayor());
			mapa.put("intPeriodoLibro", Integer.parseInt(strPeriodoLibro));
			mapa.put("intPersEmpresaCuenta", p.getId().getIntEmpresaCuentaPk());
			mapa.put("intContPeriodo", p.getId().getIntPeriodoCuenta());
			mapa.put("strContNumeroCuenta", p.getId().getStrNumeroCuenta());
			lista = dao.getListaPorLibroMayorYPlanCuenta(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<LibroDiarioDetalle> getPorLibroDiario(LibroDiario libroDiario) throws BusinessException{
		List<LibroDiarioDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaLibro", libroDiario.getId().getIntPersEmpresaLibro());
			mapa.put("intContPeriodoLibro", libroDiario.getId().getIntContPeriodoLibro());
			mapa.put("intContCodigoLibro", libroDiario.getId().getIntContCodigoLibro());
			lista = dao.getListaPorLibroDiario(mapa);

		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<LibroDiarioDetalle> getListaPorBuscar(LibroDiarioDetalle libroDiarioDetalle) throws BusinessException{
		List<LibroDiarioDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaLibro", libroDiarioDetalle.getId().getIntPersEmpresaLibro());
			mapa.put("intContPeriodoLibro", libroDiarioDetalle.getId().getIntContPeriodoLibro());
			mapa.put("intContCodigoLibro", libroDiarioDetalle.getId().getIntContCodigoLibro());
			mapa.put("intContItemLibro", libroDiarioDetalle.getId().getIntContItemLibro());
			mapa.put("intPersEmpresaCuenta", libroDiarioDetalle.getIntPersEmpresaCuenta());
			mapa.put("intContPeriodo", libroDiarioDetalle.getIntContPeriodo());
			mapa.put("strContNumeroCuenta", libroDiarioDetalle.getStrContNumeroCuenta());
			mapa.put("intPersEmpresaSucursal", libroDiarioDetalle.getIntPersEmpresaSucursal());
			mapa.put("intSucuIdSucursal", libroDiarioDetalle.getIntSucuIdSucursal());
			mapa.put("intSudeIdSubSucursal", libroDiarioDetalle.getIntSudeIdSubSucursal());
			lista = dao.getListaPorBuscar(mapa);

		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	/* Inicio - GTorresBroussetP - 05.abr.2014 */
	/* Buscar Libro Diario Detalle por Periodo y Documento */
	public List<LibroDiarioDetalle> getListaPorPeriodoDocumento(LibroDiarioDetalle libroDiarioDetalle) throws BusinessException{
		List<LibroDiarioDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaLibro", libroDiarioDetalle.getId().getIntPersEmpresaLibro());
			mapa.put("intContPeriodoLibro", libroDiarioDetalle.getId().getIntContPeriodoLibro());
			mapa.put("intParaDocumentoGeneral", libroDiarioDetalle.getIntParaDocumentoGeneral());
			lista = dao.getListaPorPeriodoDocumento(mapa);

		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	/* Fin - GTorresBroussetP - 05.abr.2014 */
}