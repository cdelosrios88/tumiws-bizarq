package pe.com.tumi.seguridad.permiso.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.permiso.dao.DocumentoDao;
import pe.com.tumi.seguridad.permiso.dao.impl.DocumentoDaoIbatis;
import pe.com.tumi.seguridad.permiso.domain.Documento;
import pe.com.tumi.seguridad.permiso.domain.DocumentoId;

public class DocumentoBO {

	private DocumentoDao dao = (DocumentoDao)TumiFactory.get(DocumentoDaoIbatis.class);
	
	public Documento grabarDocumento(Documento o) throws BusinessException {
		Documento dto = null;
		try{
			o.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Documento modificarDocumento(Documento o) throws BusinessException{
		Documento dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Documento getListaDocumentoPorPk(DocumentoId pId) throws BusinessException{
		List<Documento> lista = null;
		Documento domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intIdPerfil", pId.getIntIdPerfil());
			mapa.put("intIdTransaccion", pId.getIntIdTransaccion());
			mapa.put("intIdTipoDocumento", pId.getIntIdTipoDocumento());
			mapa.put("intVersion", pId.getIntVersion());
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
