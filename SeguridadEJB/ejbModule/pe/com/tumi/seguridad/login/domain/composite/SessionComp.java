package pe.com.tumi.seguridad.login.domain.composite;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.seguridad.login.domain.Session;

public class SessionComp extends TumiDomain {
	private Session session;
	private Integer intPersonaPk;
	private String strFullName;
	private Integer intIdEmpresa;
	private String strEmpresa;
	private String strEmpresaSiglas;
	private String strSucursal;
	private String strSucursalSiglas;
	
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public Integer getIntPersonaPk() {
		return intPersonaPk;
	}
	public void setIntPersonaPk(Integer intPersonaPk) {
		this.intPersonaPk = intPersonaPk;
	}
	public String getStrFullName() {
		return strFullName;
	}
	public void setStrFullName(String strFullName) {
		this.strFullName = strFullName;
	}
	public Integer getIntIdEmpresa() {
		return intIdEmpresa;
	}
	public void setIntIdEmpresa(Integer intIdEmpresa) {
		this.intIdEmpresa = intIdEmpresa;
	}
	public String getStrEmpresa() {
		return strEmpresa;
	}
	public void setStrEmpresa(String strEmpresa) {
		this.strEmpresa = strEmpresa;
	}
	public String getStrEmpresaSiglas() {
		return strEmpresaSiglas;
	}
	public void setStrEmpresaSiglas(String strEmpresaSiglas) {
		this.strEmpresaSiglas = strEmpresaSiglas;
	}
	public String getStrSucursal() {
		return strSucursal;
	}
	public void setStrSucursal(String strSucursal) {
		this.strSucursal = strSucursal;
	}
	public String getStrSucursalSiglas() {
		return strSucursalSiglas;
	}
	public void setStrSucursalSiglas(String strSucursalSiglas) {
		this.strSucursalSiglas = strSucursalSiglas;
	}
}