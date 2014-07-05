package pe.com.tumi.parametro.general.bo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.dao.ArchivoDao;
import pe.com.tumi.parametro.general.dao.TarifaDao;
import pe.com.tumi.parametro.general.dao.TipoCambioDao;
import pe.com.tumi.parametro.general.dao.impl.ArchivoDaoIbatis;
import pe.com.tumi.parametro.general.dao.impl.TarifaDaoIbatis;
import pe.com.tumi.parametro.general.dao.impl.TipoCambioDaoIbatis;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.Tarifa;
import pe.com.tumi.parametro.general.domain.TarifaId;
import pe.com.tumi.parametro.general.domain.TipoCambio;
import pe.com.tumi.parametro.general.domain.TipoCambioId;

public class TarifaBO {
	
	private TarifaDao dao = (TarifaDao)TumiFactory.get(TarifaDaoIbatis.class);
	
	public Tarifa grabarTarifa(Tarifa o) throws BusinessException{
		Tarifa dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Tarifa modificarTarifa(Tarifa o) throws BusinessException{
		Tarifa dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Tarifa getTarifaPorPK(TarifaId pId) throws BusinessException{
		Tarifa domain = null;
		List<Tarifa> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntPersEmpresaTarifa", pId.getIntPersEmpresaTarifa());
			mapa.put("pIntParaTipoTarifaCod", pId.getIntParaTipoTarifaCod());
			mapa.put("pDtParaTarifaDesde", pId.getDtParaTarifaDesde());
			lista = dao.getListaPorPK(mapa);
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
	
	public List<Tarifa> getTarifaBusqueda(Tarifa pTarifa) throws BusinessException{
		List<Tarifa> lista = null;
		try{
			System.out.println("pTarifa.getId().getIntParaTipoTarifaCod(): "+pTarifa.getId().getIntParaTipoTarifaCod());
			HashMap mapa = new HashMap();
			mapa.put("pIntParaTipoTarifaCod", pTarifa.getId().getIntParaTipoTarifaCod());
			lista = dao.getBusqueda(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Tarifa getTarifaIGV(Integer intIdEmpresa, Date dtFecha) throws BusinessException{
		Tarifa tarifa = null;
		try{
			if(dtFecha==null) return null;
			
			List<Tarifa> lista = null;
			HashMap mapa = new HashMap();
			mapa.put("pIntPersEmpresaTarifa", intIdEmpresa);
			mapa.put("pIntParaTipoTarifaCod", Constante.PARAM_T_TIPOTARIFA_IGV);
			lista = dao.getListaPorPK(mapa);
			for(Tarifa tarifaBD : lista){
				if(tarifaBD.getId().getDtParaTarifaDesde().compareTo(dtFecha)<=0
				&& tarifaBD.getDtTarifaHasta().compareTo(dtFecha)>=0){
					tarifa = tarifaBD;
					break;
				}
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return tarifa;
	}
	
	public Tarifa getTarifaIGVActual(Integer intIdEmpresa) throws BusinessException{
		Tarifa tarifa = null;
		try{
			tarifa = getTarifaIGV(intIdEmpresa, new Date());
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return tarifa;
	}	
}