package pe.com.tumi.tesoreria.egreso.bo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.egreso.dao.EgresoDao;
import pe.com.tumi.tesoreria.egreso.dao.impl.EgresoDaoIbatis;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.domain.EgresoId;


public class EgresoBO{

	private EgresoDao dao = (EgresoDao)TumiFactory.get(EgresoDaoIbatis.class);

	public Egreso grabar(Egreso o) throws BusinessException{
		Egreso dto = null;
		try{
			if(o.getStrObservacion() == null) o.setStrObservacion(" "); //Egreso observacion x defeccto. BO
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public Egreso modificar(Egreso o) throws BusinessException{
  		Egreso dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public Egreso getPorPk(EgresoId pId) throws BusinessException{
		Egreso domain = null;
		List<Egreso> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaEgreso", pId.getIntPersEmpresaEgreso());
			mapa.put("intItemEgresoGeneral", pId.getIntItemEgresoGeneral());
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
	
	public List<Egreso> getListaParaItem(Egreso o) throws BusinessException{
		List<Egreso> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intParaDocumentoGeneral", o.getIntParaDocumentoGeneral());
			mapa.put("intItemPeriodoEgreso", o.getIntItemPeriodoEgreso());
			if(o.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION)){
				mapa.put("intParaTipoFondoFijo", o.getIntParaTipoFondoFijo());
			}
			mapa.put("intSucuIdSucursal", o.getIntSucuIdSucursal());
			lista = dao.getListaParaItem(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Egreso getPorControlFondosFijos(ControlFondosFijos o) throws BusinessException{
		Egreso egreso;
		try{
			EgresoId egresoId = new EgresoId();
			egresoId.setIntPersEmpresaEgreso(o.getIntPersEmpresaEgreso());
			egresoId.setIntItemEgresoGeneral(o.getIntEgresoGeneral());
			egreso = getPorPk(egresoId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return egreso;
	}
	
	public List<Egreso> buscarTransferencia(Egreso egreso, Date dtFiltroDesde, Date dtFiltroHasta) throws BusinessException{
		List<Egreso> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intParaDocumentoGeneral", egreso.getIntParaDocumentoGeneral());
			mapa.put("intNumeroTransferencia", egreso.getIntNumeroTransferencia());
			mapa.put("bdMontoTotal", egreso.getBdMontoTotal());
			mapa.put("dtFiltroDesde", dtFiltroDesde);
			mapa.put("dtFiltroHasta", dtFiltroHasta);
			lista = dao.getBuscarTransferencia(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Egreso> getListaPorControlFondosFijos(ControlFondosFijos o) throws BusinessException{
		List<Egreso> listaEgreso;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaEgreso", o.getId().getIntPersEmpresa());
			mapa.put("intParaTipoFondoFijo", o.getId().getIntParaTipoFondoFijo());
			mapa.put("intItemPeriodoFondo", o.getId().getIntItemPeriodoFondo());
			mapa.put("intSucuIdSucursal", o.getId().getIntSucuIdSucursal());
			mapa.put("intItemFondoFijo", o.getId().getIntItemFondoFijo());
			
			listaEgreso = dao.getListaPorCFF(mapa);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return listaEgreso;
	}
	
	public List<Egreso> getListaPorBuscar(Egreso egreso, Date dtDesdeFiltro, Date dtHastaFiltro) throws BusinessException{
		List<Egreso> listaEgreso;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaEgreso", egreso.getId().getIntPersEmpresaEgreso());
			mapa.put("intParaDocumentoGeneral", egreso.getIntParaDocumentoGeneral());
			mapa.put("intNumeroCheque", egreso.getIntNumeroCheque());
			mapa.put("bdMontoTotal", egreso.getBdMontoTotal());
			mapa.put("intParaEstado", egreso.getIntParaEstado());
			mapa.put("tsFechaRegistro", egreso.getTsFechaRegistro());
			mapa.put("dtDesdeFiltro", dtDesdeFiltro);
			mapa.put("dtHastaFiltro", dtHastaFiltro);
			mapa.put("intItemBancoFondo", egreso.getIntItemBancoFondo());
			mapa.put("intItemBancoCuenta", egreso.getIntItemBancoCuenta());
			
			listaEgreso = dao.getListaPorBuscar(mapa);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return listaEgreso;
	}
	
	public List<Egreso> getListaParaTelecredito(Egreso egreso) throws BusinessException{
		List<Egreso> listaEgreso;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaEgreso", egreso.getId().getIntPersEmpresaEgreso());
			mapa.put("dtFechaEgreso", egreso.getDtFechaEgreso());
			mapa.put("bdMontoTotal", egreso.getBdMontoTotal());
			
			listaEgreso = dao.getListaParaTelecredito(mapa);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return listaEgreso;
	}
	
	public List<Egreso> getListaPorLibroDiario(LibroDiario libroDiario) throws BusinessException{
		List<Egreso> listaEgreso;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaLibro", libroDiario.getId().getIntPersEmpresaLibro());
			mapa.put("intContPeriodoLibro", libroDiario.getId().getIntContPeriodoLibro());
			mapa.put("intContCodigoLibro", libroDiario.getId().getIntContCodigoLibro());
			
			listaEgreso = dao.getListaPorLibroDiario(mapa);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return listaEgreso;
	}	
}