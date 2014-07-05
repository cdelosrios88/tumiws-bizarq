package pe.com.tumi.creditos.domain;

public class AdendaPerfil {
	private	Integer			nIdConvenio;
	private	Integer			nIdAmpliacion;
	private	Integer			nIdEmpresa;
	private	Integer			nIdPerfil;
	private	String			sObservacion;
	
	//Getters y Setters
	public Integer getnIdConvenio() {
		return nIdConvenio;
	}
	public void setnIdConvenio(Integer nIdConvenio) {
		this.nIdConvenio = nIdConvenio;
	}
	public Integer getnIdAmpliacion() {
		return nIdAmpliacion;
	}
	public void setnIdAmpliacion(Integer nIdAmpliacion) {
		this.nIdAmpliacion = nIdAmpliacion;
	}
	public Integer getnIdEmpresa() {
		return nIdEmpresa;
	}
	public void setnIdEmpresa(Integer nIdEmpresa) {
		this.nIdEmpresa = nIdEmpresa;
	}
	public Integer getnIdPerfil() {
		return nIdPerfil;
	}
	public void setnIdPerfil(Integer nIdPerfil) {
		this.nIdPerfil = nIdPerfil;
	}
	public String getsObservacion() {
		return sObservacion;
	}
	public void setsObservacion(String sObservacion) {
		this.sObservacion = sObservacion;
	}
}
