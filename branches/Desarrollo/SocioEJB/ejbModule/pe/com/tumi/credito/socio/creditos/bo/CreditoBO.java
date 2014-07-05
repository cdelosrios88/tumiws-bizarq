package pe.com.tumi.credito.socio.creditos.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.captacion.dao.CaptacionDao;
import pe.com.tumi.credito.socio.captacion.dao.CondicionDao;
import pe.com.tumi.credito.socio.captacion.dao.impl.CaptacionDaoIbatis;
import pe.com.tumi.credito.socio.captacion.dao.impl.CondicionDaoIbatis;
import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.captacion.domain.Condicion;
import pe.com.tumi.credito.socio.captacion.domain.CondicionId;
import pe.com.tumi.credito.socio.creditos.dao.CreditoDao;
import pe.com.tumi.credito.socio.creditos.dao.impl.CreditoDaoIbatis;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class CreditoBO {
	
private CreditoDao dao = (CreditoDao)TumiFactory.get(CreditoDaoIbatis.class);
	
	public Credito grabarCredito(Credito o) throws BusinessException{
		Credito dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Credito modificarCredito(Credito o) throws BusinessException{
		Credito dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Credito getCreditoPorPK(CreditoId pPK) throws BusinessException{
		Credito domain = null;
		List<Credito> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 		pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 	pPK.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito", 			pPK.getIntItemCredito());
			
			lista = dao.getListaCreditoPorPK(mapa);
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
	
	public List<Credito> getListaCreditoDeBusqueda(Credito pCredito) throws BusinessException{
		List<Credito> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", 			pCredito.getId().getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 		pCredito.getId().getIntParaTipoCreditoCod());
			mapa.put("intItemCredito",				pCredito.getId().getIntItemCredito());
			mapa.put("intParaEstadoSolicitudCod", 	pCredito.getIntParaEstadoSolicitudCod());
			mapa.put("intParaEstadoCod", 			pCredito.getIntParaEstadoCod());
			mapa.put("strDescripcion", 				pCredito.getStrDescripcion());
			mapa.put("intParaCondicionLaboralCod", 	pCredito.getIntParaCondicionLaboralCod());
			mapa.put("intCondicionSocio", 			pCredito.getCondicionCredito().getId().getIntParaCondicionSocioCod());
			mapa.put("intTipoFecha", 				pCredito.getIntTipoFecha());
			mapa.put("dtInicio", 					pCredito.getStrDtFechaIni());
			mapa.put("dtFin", 						pCredito.getStrDtFechaFin());
			mapa.put("intParaTipoPersonaCod", 		pCredito.getIntParaTipoPersonaCod());
			mapa.put("intGarantia", 				pCredito.getIntGarantia());
			mapa.put("intDescuento", 				pCredito.getIntDescuento());
			mapa.put("intExcepcion", 				pCredito.getIntExcepcion());
			mapa.put("intActivo", 					pCredito.getIntActivo());
			mapa.put("intCaduco", 					pCredito.getIntCaduco());
			lista = dao.getListaParaFiltro(mapa);
		}catch(DAOException e){
			e.printStackTrace();
			throw new BusinessException(e);
		}catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public CreditoId eliminarCredito(CreditoId o) throws BusinessException{
		CreditoId dto = null;
		try{
			dto = dao.eliminarCredito(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public List<Credito> getlistaCreditoPorCredito(Credito pCredito) throws BusinessException{
		List<Credito> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			//mapa.put("intPersEmpresaPk", 			pCredito.getId().getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 		pCredito.getId().getIntParaTipoCreditoCod());
			mapa.put("intItemCredito",				pCredito.getId().getIntItemCredito());
			/*mapa.put("intParaEstadoSolicitudCod", pCredito.getIntParaEstadoSolicitudCod());
			mapa.put("intParaEstadoCod", 			pCredito.getIntParaEstadoCod());
			mapa.put("strDescripcion", 				pCredito.getStrDescripcion());
			mapa.put("intParaCondicionLaboralCod", 	pCredito.getIntParaCondicionLaboralCod());
			mapa.put("intCondicionSocio", 			pCredito.getCondicionCredito().getId().getIntParaCondicionSocioCod());
			mapa.put("intTipoFecha", 				pCredito.getIntTipoFecha());
			mapa.put("dtInicio", 					pCredito.getStrDtFechaIni());
			mapa.put("dtFin", 						pCredito.getStrDtFechaFin());*/
			mapa.put("intParaTipoCreditoEmpresa", 	pCredito.getIntParaTipoCreditoEmpresa());
			/*mapa.put("intGarantia", 				pCredito.getIntGarantia());
			mapa.put("intDescuento", 				pCredito.getIntDescuento());
			mapa.put("intExcepcion", 				pCredito.getIntExcepcion());
			mapa.put("intActivo", 					pCredito.getIntActivo());
			mapa.put("intCaduco", 					pCredito.getIntCaduco());*/
			lista = dao.getlistaCreditoPorCredito(mapa);
		}catch(DAOException e){
			System.out.println("getlistaCreditoPorCreditoDAO---> "+e);
			e.printStackTrace();
			throw new BusinessException(e);
		}catch(Exception e) {
			System.out.println("getlistaCreditoPorCreditoE---> "+e);
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return lista;
	}
	/*
	public List<Credito> getlistaCreditoTotal() throws BusinessException{
		List<Credito> lista = null;
		try{
			//HashMap<String, Object> mapa = new HashMap<String, Object>();
			lista = dao.getlistaCreditoTotal();
		}catch(DAOException e){
			System.out.println("getlistaCreditoPorCreditoDAO---> "+e);
			e.printStackTrace();
			throw new BusinessException(e);
		}catch(Exception e) {
			System.out.println("getlistaCreditoPorCreditoE---> "+e);
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return lista;
	}*/
	
	public List<Credito> getlistaCofCredito() throws BusinessException{
		List<Credito> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intParaTipoCreditoCod", 		null);
			mapa.put("intItemCredito",				null);
			mapa.put("intParaTipoCreditoEmpresa", 	null);
			lista = dao.getlistaCreditoPorCredito(mapa);
		}catch(DAOException e){
			System.out.println("getlistaCreditoPorCreditoDAO---> "+e);
			e.printStackTrace();
			throw new BusinessException(e);
		}catch(Exception e) {
			System.out.println("getlistaCreditoPorCreditoE---> "+e);
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return lista;
	}
}
 