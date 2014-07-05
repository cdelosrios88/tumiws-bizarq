package pe.com.tumi.cobranza.planilla.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.cobranza.planilla.dao.TransferenciaDao;
import pe.com.tumi.cobranza.planilla.dao.impl.TransferenciaDaoIbatis;
import pe.com.tumi.cobranza.planilla.domain.Transferencia;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class TransferenciaBO{

	private TransferenciaDao dao = (TransferenciaDao)TumiFactory.get(TransferenciaDaoIbatis.class);

	public Transferencia grabarTransferencia(Transferencia o) throws BusinessException{
		Transferencia dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
			System.out.println("ERROR EN grabarTransferenciaA ---> "+e);
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	System.out.println("grabarTransferencia---> "+e);
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
	
//	public List<Transferencia> getTransferenciasPorCtaTipo(Integer o) throws BusinessException{
//		Transferencia dto = null;
//		try{
//		    dto = dao.grabar(o);
//		}catch(DAOException e){
//			System.out.println("ERROR EN grabarTransferenciaA ---> "+e);
//	  		throw new BusinessException(e);
//	    }catch(Exception e){
//	    	System.out.println("grabarTransferencia---> "+e);
//	    	throw new BusinessException(e);
//	    }
//	    return dto;
//	}
	
	public List<Transferencia> getListaTransferencias(Integer intPersEmpresasolctacte,
														Integer intCcobItemsolctacte, 
														Integer intTipoSolicitudctacte) throws BusinessException{
		List<Transferencia> lista = null;
		
		try{
			
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaSolctacte", intPersEmpresasolctacte);
			mapa.put("intCcobItemSolctacte", intCcobItemsolctacte);
			mapa.put("intParaTipoSolicitudctacte", intTipoSolicitudctacte);
			
			lista = dao.getListaTransferencias(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
}