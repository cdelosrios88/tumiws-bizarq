package pe.com.tumi.creditos.domain;

public class PerfilConvenio {
	private Integer		inIdConvenio;
	private Integer		inIdAmpliacion;
	private Integer		inIdEmpresa;
	private Integer		inIdPerfil;
	private Integer 	inIdValidacion;
	private String		stDescripcion;
	private String		stObservacion;
	private Boolean		inEstadoPerf;
	private Integer 	inValorPerfDet;
	
	//Getters y Setters
	public Integer getInIdValidacion() {
		return inIdValidacion;
	}
	public Integer getInIdConvenio() {
		return inIdConvenio;
	}
	public void setInIdConvenio(Integer inIdConvenio) {
		this.inIdConvenio = inIdConvenio;
	}
	public Integer getInIdAmpliacion() {
		return inIdAmpliacion;
	}
	public void setInIdAmpliacion(Integer inIdAmpliacion) {
		this.inIdAmpliacion = inIdAmpliacion;
	}
	public Integer getInIdEmpresa() {
		return inIdEmpresa;
	}
	public void setInIdEmpresa(Integer inIdEmpresa) {
		this.inIdEmpresa = inIdEmpresa;
	}
	public Integer getInIdPerfil() {
		return inIdPerfil;
	}
	public void setInIdPerfil(Integer inIdPerfil) {
		this.inIdPerfil = inIdPerfil;
	}
	public void setInIdValidacion(Integer inIdValidacion) {
		this.inIdValidacion = inIdValidacion;
	}
	public String getStDescripcion() {
		return stDescripcion;
	}
	public void setStDescripcion(String stDescripcion) {
		this.stDescripcion = stDescripcion;
	}
	public String getStObservacion() {
		return stObservacion;
	}
	public void setStObservacion(String stObservacion) {
		this.stObservacion = stObservacion;
	}
	public Boolean getInEstadoPerf() {
		return inEstadoPerf;
	}
	public void setInEstadoPerf(Boolean inEstadoPerf) {
		this.inEstadoPerf = inEstadoPerf;
	}
	public Integer getInValorPerfDet() {
		return inValorPerfDet;
	}
	public void setInValorPerfDet(Integer inValorPerfDet) {
		this.inValorPerfDet = inValorPerfDet;
	}
}