package pe.com.tumi.credito.socio.convenio.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.convenio.dao.AdendaDao;
import pe.com.tumi.credito.socio.convenio.dao.impl.AdendaDaoIbatis;
import pe.com.tumi.credito.socio.convenio.domain.Adenda;
import pe.com.tumi.credito.socio.convenio.domain.AdendaId;
import pe.com.tumi.credito.socio.convenio.domain.composite.ConvenioComp;
import pe.com.tumi.credito.socio.convenio.domain.composite.HojaPlaneamientoComp;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.estructura.dao.impl.EstructuraDetalleDaoIbatis;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class AdendaBO {
	
	private AdendaDao dao = (AdendaDao)TumiFactory.get(AdendaDaoIbatis.class);
	
	public Adenda grabarAdenda(Adenda o) throws BusinessException{
		Adenda dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Adenda modificarAdenda(Adenda o) throws BusinessException{
		Adenda dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Adenda getAdendaPorPK(AdendaId pPK) throws BusinessException{
		Adenda domain = null;
		List<Adenda> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intConvenio", 	pPK.getIntConvenio());
			mapa.put("intItemConvenio", pPK.getIntItemConvenio());
			lista = dao.getListaAdendaPorPK(mapa);
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
	

	public List<HojaPlaneamientoComp> getListaHojaPlaneamientoCompDeBusqueda(HojaPlaneamientoComp pHojaPlaneamientoComp) throws BusinessException{
		List<HojaPlaneamientoComp> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdConvenio", 			pHojaPlaneamientoComp.getAdenda().getId().getIntConvenio());
			mapa.put("intParaEstadoCod", 		pHojaPlaneamientoComp.getAdenda().getIntParaEstadoHojaPlan());
			mapa.put("intIdSucursal", 			pHojaPlaneamientoComp.getAdenda().getIntSeguSucursalPk());
			//Modificado por cdelosrios, 04/12/2013
			/*mapa.put("intIdItemConvenio", 		pHojaPlaneamientoComp.getAdenda().getId().getIntItemConvenio());
			mapa.put("intIdNivel", 				pHojaPlaneamientoComp.getEstructuraDetalle().getId().getIntNivel());
			mapa.put("intModalidad", 			pHojaPlaneamientoComp.getEstructuraDetalle().getIntParaModalidadCod());
			mapa.put("intTipoSocio", 			pHojaPlaneamientoComp.getEstructuraDetalle().getIntParaTipoSocioCod());
			mapa.put("intRangoFec", 			pHojaPlaneamientoComp.getAdenda().getIntSeguSucursalPk());
			mapa.put("dtInicio", 				pHojaPlaneamientoComp.getAdenda().getDtInicio());
			mapa.put("dtFin", 					pHojaPlaneamientoComp.getAdenda().getDtCese());
			mapa.put("intDonacion", 			pHojaPlaneamientoComp.getAdenda().getIntDonacion());
			mapa.put("intIndeterminado", 		pHojaPlaneamientoComp.getIntIndeterminado());
			mapa.put("intDocAdjunto", 			pHojaPlaneamientoComp.getIntDocAdjunto());
			mapa.put("intCartaAutorizacion", 	pHojaPlaneamientoComp.getIntCartaAutorizacion());*/
			//Fin modificado por cdelosrios, 04/12/2013
			
			lista = dao.getListaParaFiltro(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Adenda eliminarAdenda(Adenda o) throws BusinessException{
		Adenda dto = null;
		try{
			dto = dao.eliminarAdenda(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Adenda aprobarRechazarAdenda(Adenda o) throws BusinessException{
		Adenda dto = null;
		try{
			dto = dao.aprobarRechazarAdenda(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public List<ConvenioComp> getListaConvenioComp(ConvenioComp pConvenioComp) throws BusinessException{
		List<ConvenioComp> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			//Modificado por cdelosrios, 08/12/2013
			/*mapa.put("intIdItemConvenio", 		pConvenioComp.getAdenda().getId().getIntItemConvenio());
			mapa.put("intParaEstadoConvenioCod",pConvenioComp.getAdenda().getIntParaEstadoConvenioCod());
			mapa.put("intRenovacion", 			pConvenioComp.getAdenda().getIntRenovacion());
			mapa.put("intRetencPlla", 			pConvenioComp.getIntRetencionPlanilla());
			mapa.put("intDonacion", 			pConvenioComp.getIntDonacionRegalia());
			mapa.put("intDocAdjunto", 			pConvenioComp.getIntDocAdjunto());*/
			mapa.put("intIdConvenio", 			pConvenioComp.getAdenda().getId().getIntConvenio());
			mapa.put("intIdSucursalConv", 		pConvenioComp.getAdenda().getIntSeguSucursalPk());
			mapa.put("intVigenciaConvenio", 	pConvenioComp.getIntVigenciaConvenio());
			//Fin modificado por cdelosrios, 08/12/2013
			
			lista = dao.getListaConvenio(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<ConvenioComp> getListaConvenioDet(ConvenioComp pHojaPlaneamientoComp) throws BusinessException{
		List<ConvenioComp> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdConvenio", 			pHojaPlaneamientoComp.getAdenda().getId().getIntConvenio());
			
			lista = dao.getListaConvenioDet(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<ConvenioComp> getListaConvenioPorTipoConvenio(ConvenioComp pConvenioComp) throws BusinessException{
		List<ConvenioComp> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdItemConvenio", 		pConvenioComp.getAdenda().getId().getIntItemConvenio());
			
			lista = dao.getListaConvenioPorTipoConvenio(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Adenda grabarConvenio(Adenda o) throws BusinessException{
		//Adenda dto = null;
		try{
			o = dao.grabarConvenio(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return o;
	}
	
	public Adenda modificarConvenio(Adenda o) throws BusinessException{
		//Adenda dto = null;
		try{
			o = dao.modificarConvenio(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return o;
	}
}