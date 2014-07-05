package pe.com.tumi.credito.socio.estructura.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.estructura.dao.AdminPadronDao;
import pe.com.tumi.credito.socio.estructura.dao.SolicitudPagoDao;
import pe.com.tumi.credito.socio.estructura.dao.SolicitudPagoDetalleDao;
import pe.com.tumi.credito.socio.estructura.dao.impl.AdminPadronDaoIbatis;
import pe.com.tumi.credito.socio.estructura.dao.impl.SolicitudPagoDetalleDaoIbatis;
import pe.com.tumi.credito.socio.estructura.domain.AdminPadron;
import pe.com.tumi.credito.socio.estructura.domain.AdminPadronId;
import pe.com.tumi.credito.socio.estructura.domain.Padron;
import pe.com.tumi.credito.socio.estructura.domain.PadronId;
import pe.com.tumi.credito.socio.estructura.domain.SolicitudPago;
import pe.com.tumi.credito.socio.estructura.domain.SolicitudPagoDetalle;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class SolicitudPagoDetalleBO {
	
	private SolicitudPagoDetalleDao dao = (SolicitudPagoDetalleDao)TumiFactory.get(SolicitudPagoDetalleDaoIbatis.class);
	
	public SolicitudPagoDetalle grabar(SolicitudPagoDetalle o) throws BusinessException{
		SolicitudPagoDetalle dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public List<SolicitudPagoDetalle> getLista() throws BusinessException{
		List<SolicitudPagoDetalle> lista = null;
		try{
			HashMap mapa = new HashMap();
			lista = dao.getLista(mapa);				
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<SolicitudPagoDetalle> getBusqueda(SolicitudPagoDetalle o) throws BusinessException{
		List<SolicitudPagoDetalle> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPeriodo", o.getIntPeriodo());
			mapa.put("intMes", o.getIntMes());
			mapa.put("intNivel", o.getIntNivel());
			mapa.put("intCodigo", o.getIntCodigo());
			mapa.put("intParaTipoArchivo", o.getIntParaTipoArchivoPadronCod());
			mapa.put("intParaModalidadCod", o.getIntParaModalidadCod());
			mapa.put("intParaTipoSocio", o.getIntParaTipoSocioCod());
			mapa.put("intItemAdministraPadron", o.getIntItemAdministraPadron());
			
			lista = dao.getBusqueda(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
