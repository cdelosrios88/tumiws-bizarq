package pe.com.tumi.seguridad.login.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.login.dao.UsuarioCompDao;
import pe.com.tumi.seguridad.login.dao.impl.UsuarioCompDaoIbatis;
import pe.com.tumi.seguridad.login.domain.composite.UsuarioComp;

public class UsuarioCompBO {

	private UsuarioCompDao dao = (UsuarioCompDao)TumiFactory.get(UsuarioCompDaoIbatis.class);
	
	public List<UsuarioComp> getListaDeBusqueda(UsuarioComp pUsuario) throws BusinessException{
		List<UsuarioComp> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdEmpresa", pUsuario.getEmpresaUsuario().getId().getIntPersEmpresaPk());
			mapa.put("intTipoUsuario", pUsuario.getUsuario().getIntTipoUsuario());
			mapa.put("intIdEstado", pUsuario.getUsuario().getIntIdEstado());
			mapa.put("intIdPerfil", pUsuario.getIntIdPerfil());
			mapa.put("strUsuario", pUsuario.getUsuario().getStrUsuario());
			mapa.put("intTipoSucursal", pUsuario.getIntIdTipoSucursal());
			lista = dao.getListaDeBusqueda(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
