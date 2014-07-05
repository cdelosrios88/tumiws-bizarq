package pe.com.tumi.reporte.operativo.credito.asociativo.bo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.reporte.operativo.credito.asociativo.dao.PrevisionDao;
import pe.com.tumi.reporte.operativo.credito.asociativo.dao.impl.PrevisionDaoIbatis;
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.Prevision;

public class PrevisionBO {
	protected  static Logger log = Logger.getLogger(PrevisionBO.class);
	private PrevisionDao dao = (PrevisionDao)TumiFactory.get(PrevisionDaoIbatis.class);
	
	public List<Prevision> getListaFondoSepelio(
				Integer intIdDocumentoGeneral,
				Integer intIdSucursal, 
				Integer intIdSubSucursal,
				Integer intTipoFiltro,
				String strTipoFiltro,
				Integer intIdTipoVinculo,
				Integer intIdEstadoSolicitud,
				Integer intIdEstadoPago,
				Date dtFechaSolicitudDesde,
				Date dtFechaSolicitudHasta) throws BusinessException {
		List<Prevision> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdDocumentoGeneral",intIdDocumentoGeneral);
			mapa.put("intIdSucursal", 		intIdSucursal);
			mapa.put("intIdSubSucursal", 	intIdSubSucursal);
			mapa.put("intTipoFiltro", 		intTipoFiltro);
			mapa.put("strTipoFiltro", 		strTipoFiltro);
			mapa.put("intIdTipoVinculo", 	intIdTipoVinculo);
			mapa.put("intIdEstadoSolicitud",intIdEstadoSolicitud);
			mapa.put("intIdEstadoPago", 	intIdEstadoPago);
			mapa.put("dtFechaSolicitudDesde",dtFechaSolicitudDesde);
			mapa.put("dtFechaSolicitudHasta",dtFechaSolicitudHasta);
			lista = dao.getListaFondoSepelio(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Prevision> getListaFondoRetiro(
			Integer intIdDocumentoGeneral,
			Integer intIdSucursal, 
			Integer intIdSubSucursal,
			Integer intTipoFiltro,
			String strTipoFiltro,
			Integer intIdTipoVinculo,
			Integer intIdEstadoSolicitud,
			Integer intIdEstadoPago,
			Date dtFechaSolicitudDesde,
			Date dtFechaSolicitudHasta) throws BusinessException {
		List<Prevision> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdDocumentoGeneral",intIdDocumentoGeneral);
			mapa.put("intIdSucursal", 		intIdSucursal);
			mapa.put("intIdSubSucursal", 	intIdSubSucursal);
			mapa.put("intTipoFiltro", 		intTipoFiltro);
			mapa.put("strTipoFiltro", 		strTipoFiltro);
			mapa.put("intIdTipoVinculo", 	intIdTipoVinculo);
			mapa.put("intIdEstadoSolicitud",intIdEstadoSolicitud);
			mapa.put("intIdEstadoPago", 	intIdEstadoPago);
			mapa.put("dtFechaSolicitudDesde",dtFechaSolicitudDesde);
			mapa.put("dtFechaSolicitudHasta",dtFechaSolicitudHasta);
			lista = dao.getListaFondoRetiro(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Prevision> getListaAes(
			Integer intIdDocumentoGeneral,
			Integer intIdSucursal, 
			Integer intIdSubSucursal,
			Integer intTipoFiltro,
			String strTipoFiltro,
			Integer intIdTipoVinculo,
			Integer intIdEstadoSolicitud,
			Integer intIdEstadoPago,
			Date dtFechaSolicitudDesde,
			Date dtFechaSolicitudHasta) throws BusinessException {
		List<Prevision> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdDocumentoGeneral",intIdDocumentoGeneral);
			mapa.put("intIdSucursal", 		intIdSucursal);
			mapa.put("intIdSubSucursal", 	intIdSubSucursal);
			mapa.put("intTipoFiltro", 		intTipoFiltro);
			mapa.put("strTipoFiltro", 		strTipoFiltro);
			mapa.put("intIdTipoVinculo", 	intIdTipoVinculo);
			mapa.put("intIdEstadoSolicitud",intIdEstadoSolicitud);
			mapa.put("intIdEstadoPago", 	intIdEstadoPago);
			mapa.put("dtFechaSolicitudDesde",dtFechaSolicitudDesde);
			mapa.put("dtFechaSolicitudHasta",dtFechaSolicitudHasta);
			lista = dao.getListaAes(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
