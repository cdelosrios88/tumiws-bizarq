package pe.com.tumi.movimiento.concepto.bo;

//import java.util.HashMap;
//import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.dao.InteresProvisionadoDao;
import pe.com.tumi.movimiento.concepto.dao.impl.InteresProvisionadoDaoIbatis;
import pe.com.tumi.movimiento.concepto.domain.InteresProvisionado;


public class InteresProvisionadoBO {
	private InteresProvisionadoDao dao = (InteresProvisionadoDao)TumiFactory.get(InteresProvisionadoDaoIbatis.class);
	
	public InteresProvisionado grabarInteresProvisionado(InteresProvisionado o) throws BusinessException{
		InteresProvisionado dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			System.out.println("xxxxxxxxxxxxxxxx"+e);
			throw new BusinessException(e);
		}catch(Exception e){
			System.out.println("yyyyyyyyyyyyyyyyyy"+e);
			throw new BusinessException(e);
		}
		return dto;
	}
//	public InteresProvisionado modificar(InteresProvisionado o) throws BusinessException{
//		InteresProvisionado dto = null;
//		try{
//			dto = dao.modificar(o);
//		}catch(DAOException e){
//			throw new BusinessException(e);
//		}catch(Exception e) {
//			throw new BusinessException(e);
//		}
//		return dto;
//	}
//	public InteresProvisionado getInteresProvisionadoPorPK(InteresProvisionadoId pId) throws BusinessException{
//		InteresProvisionado domain = null;
//		List<InteresProvisionado> lista = null;
//		try{
//			HashMap<String,Object> mapa = new HashMap<String,Object>();
//			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
//			mapa.put("intCuentaPk", pId.getIntCuentaPk());
//			mapa.put("intItemExpediente", pId.getIntItemExpediente());
//			mapa.put("intItemExpedienteDetalle", pId.getIntItemExpedienteDetalle());
//			mapa.put("intItemInteres", pId.getIntItemInteres());
//
//			lista = dao.getListaPorPK(mapa);
//			if(lista!=null){
//				if(lista.size()==1){
//				   domain = lista.get(0);
//				}else if(lista.size()==0){
//				   domain = null;
//				}else{
//				   throw new BusinessException("Obtención de mas de un registro coincidente");
//				}
//			}
//		}catch(DAOException e){
//			throw new BusinessException(e);
//		}catch(BusinessException e){
//			throw e;
//		}catch(Exception e) {
//			throw new BusinessException(e);
//		}
//		return domain;
//	}
	
}
