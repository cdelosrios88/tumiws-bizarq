package pe.com.tumi.credito.socio.captacion.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.captacion.dao.CaptacionDao;
import pe.com.tumi.credito.socio.captacion.dao.impl.CaptacionDaoIbatis;
import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;

public class CaptacionBO {
	
	private CaptacionDao dao = (CaptacionDao)TumiFactory.get(CaptacionDaoIbatis.class);
	
	public Captacion grabarCaptacion(Captacion o) throws BusinessException{
		Captacion dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Captacion modificarCaptacion(Captacion o) throws BusinessException{
		Captacion dto = null;
		try{
			dto = dao.modificarCaptacion(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Captacion getCaptacionPorPK(CaptacionId pPK) throws BusinessException{
		Captacion domain = null;
		List<Captacion> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCaptacionCod", pPK.getIntParaTipoCaptacionCod());
			mapa.put("intItem", pPK.getIntItem());
			
			lista = dao.getListaCaptacionPorPKCaptacion(mapa);
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
	
	public List<Captacion> getListaCaptacionDeBusqueda(Captacion pCaptacion) throws BusinessException{
		List<Captacion> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", 			pCaptacion.getId().getIntPersEmpresaPk());
			mapa.put("intParaTipoCaptacionCod", 	pCaptacion.getId().getIntParaTipoCaptacionCod());
			mapa.put("intItem", 					pCaptacion.getId().getIntItem());
			mapa.put("strDescripcion", 				pCaptacion.getStrDescripcion());
			mapa.put("intParaTipoConfiguracionCod", pCaptacion.getIntParaTipoConfiguracionCod());
			mapa.put("intTipoFecha", 				pCaptacion.getIntTipoFecha());
			mapa.put("dtInicio", 					pCaptacion.getStrDtFechaIni());
			mapa.put("dtFin", 						pCaptacion.getStrDtFechaFin());
			mapa.put("intParaTipopersonaCod", 		pCaptacion.getIntParaTipopersonaCod());
			mapa.put("bdTna", 						pCaptacion.getBdTna());
			mapa.put("intEdadLimite", 				pCaptacion.getIntEdadLimite());
			mapa.put("intParaCondicionLaboralCod", 	pCaptacion.getIntParaCondicionLaboralCod());
			mapa.put("intCondicionSocio", 			pCaptacion.getCondicion().getId().getIntParaCondicionSocioCod());
			mapa.put("intParaRolPk", 				pCaptacion.getIntParaRolPk());
			mapa.put("intParaEstadoSolicitudCod", 	pCaptacion.getIntParaEstadoSolicitudCod());
			mapa.put("intTasaInteres", 				pCaptacion.getIntTasaInteres());
			mapa.put("intLimiteEdad", 				pCaptacion.getIntLimiteEdad());
			//mapa.put("intIndeterminado", 			pCaptacion.getIntIndeterminado());
			mapa.put("intVigencia", 				pCaptacion.getIntVigencia());
			mapa.put("intAportacionVigente", 		pCaptacion.getIntAportacionVigente());
			mapa.put("intTipoCuenta", 				pCaptacion.getIntTipoCuenta());
			lista = dao.getListaParaFiltro(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Captacion> getListaCaptacionPorPKCaptacion(CaptacionId pPK) throws BusinessException{
		List<Captacion> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCaptacionCod", pPK.getIntParaTipoCaptacionCod());
			mapa.put("intItem", pPK.getIntItem());
			
			lista = dao.getListaCaptacionPorPKCaptacion(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public CaptacionId eliminarCaptacion(CaptacionId o) throws BusinessException{
		CaptacionId dto = null;
		try{
			dto = dao.eliminarCaptacion(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public List<Captacion> getListaCaptacionPorPKOpcional(CaptacionId pPK) throws BusinessException{
		List<Captacion> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCaptacionCod", pPK.getIntParaTipoCaptacionCod());
			mapa.put("intItem", pPK.getIntItem());
			
			lista = dao.getListaCaptacionPorPKCaptacionOpcional(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Captacion getCaptacionPorCuentaConceptoDetalle(CuentaConceptoDetalle cuentaConceptoDetalle) throws BusinessException{
		Captacion dto = null;
		try{
			CaptacionId captacionId = new CaptacionId();
			captacionId.setIntPersEmpresaPk(cuentaConceptoDetalle.getId().getIntPersEmpresaPk());
			captacionId.setIntParaTipoCaptacionCod(cuentaConceptoDetalle.getIntParaTipoConceptoCod());
			captacionId.setIntItem(cuentaConceptoDetalle.getIntItemConcepto());
			
			dto = getCaptacionPorPK(captacionId);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
}
 