package pe.com.tumi.adminCuenta.dao.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pe.com.tumi.adminCuenta.dao.AdminCuentaDao;
import pe.com.tumi.adminCuenta.domain.PersonaJuridica;
import pe.com.tumi.common.dao.impl.GenericDaoIbatis;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.creditos.domain.EstructuraOrganica;
import pe.com.tumi.seguridad.domain.Sesiones;

public class AdminCuentaDaoIbatis extends SqlMapClientDaoSupport implements AdminCuentaDao{
	private String nameSpace;
	private String prefixColumnName;
	private String idTable;
	protected static Logger log = Logger.getLogger(GenericDaoIbatis.class);
	
	
	public String getNameSpace() {
		return nameSpace;
	}
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}
	public String getPrefixColumnName() {
		return prefixColumnName;
	}
	public void setPrefixColumnName(String prefixColumnName) {
		this.prefixColumnName = prefixColumnName;
	}
	public String getIdTable() {
		return idTable;
	}
	public void setIdTable(String idTable) {
		this.idTable = idTable;
	}
	
	public void grabarPerJuridica(Object juri) throws DaoException {
		log.info("Entrando al método AperturaCuentaDaoIbatis.grabarPerJuridica()");
		PersonaJuridica prmt = (PersonaJuridica)juri;	
		log.info("Parametros de registro de Persona Juridica: ");
		log.info("-----------------------------------------------");
		log.info("intIdPersona: "+prmt.getIntIdPersona());
		log.info("strRazonSocial: "+prmt.getStrRazonSocial());
		log.info("strNombreComercial: "+prmt.getStrNombreComercial());
		log.info("strSiglas: "+prmt.getStrSiglas());
		log.info("strFechaInscripcion: "+prmt.getStrFechaInscripcion());
		log.info("intNumTrabajadores: "+prmt.getIntNumTrabajadores()); //IdGrupo
		log.info("intTipoEmpresa: "+prmt.getIntTipoEmpresa());
		log.info("intTipoContribuyente: "+prmt.getIntTipoContribuyente());
		log.info("intCondContribuyente: "+prmt.getIntCondContribuyente());
		log.info("intEstadoContribuyente: "+prmt.getIntEstadoContribuyente());
		log.info("intEmisionComprobante: "+prmt.getIntEmisionComprobante());
		log.info("intSistemaContable: "+prmt.getIntSistemaContable());
		log.info("intComercioExterior: "+prmt.getIntComercioExterior());
		log.info("registros en Per_M_Persona");
		log.info("IntTipoPersona:"+prmt.getIntTipoPersona());
		log.info("intRuc: "+prmt.getIntRuc());
		log.info("intEstadoPerJuridica: "+prmt.getIntEstado());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarPerJuridica", prmt);
			log.info("Se invocó al procedure *.grabarPerJuridica");
		}catch (Exception e){
			log.info("ERROR EN  .grabarPerJuridica GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public ArrayList obtenerPerJuridica(Object juri) throws DaoException {
		log.info("Entrando al método GenericDaoIbatis.obtenerPerJuridica()");
		PersonaJuridica prmt = (PersonaJuridica)juri;
		log.info("Parametros de Busqueda de Persona Juridica: ");
		log.info("-------------------------------------");
		log.info("intRUC: "+prmt.getIntRuc());
		
		ArrayList rs = new ArrayList();
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getPerJuridica", prmt);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaSesiones, prmtBusqueda)");
		return rs;
	}
	
}
