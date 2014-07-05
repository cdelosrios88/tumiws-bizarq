package pe.com.tumi.seguridad.permiso.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.permiso.dao.SolCambioDiccionarioDao;
import pe.com.tumi.seguridad.permiso.dao.impl.SolCambioDiccionarioDaoIbatis;
import pe.com.tumi.seguridad.permiso.domain.SolCambioDiccionario;
import pe.com.tumi.seguridad.permiso.domain.SolCambioDiccionarioId;

public class SolCambioDiccionarioBO {

	private SolCambioDiccionarioDao dao = (SolCambioDiccionarioDao)TumiFactory.get(SolCambioDiccionarioDaoIbatis.class);
	
	public SolCambioDiccionario grabarSolicitudCambioDetalle(SolCambioDiccionario o) throws BusinessException {
		SolCambioDiccionario dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public SolCambioDiccionario modificarSolicitudCambioDetalle(SolCambioDiccionario o) throws BusinessException{
		SolCambioDiccionario dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public SolCambioDiccionario getListaSolicitudCambioDetallePorPk(SolCambioDiccionarioId pId) throws BusinessException{
		List<SolCambioDiccionario> lista = null;
		SolCambioDiccionario domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intIdTransaccion", pId.getIntIdTransaccion());
			mapa.put("intItem", pId.getIntItem());
			mapa.put("intCodigo", pId.getIntCodigo());
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
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
}
