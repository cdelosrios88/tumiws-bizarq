package pe.com.tumi.creditos.domain;

public class Poblacion {
	private Integer 	nIdConvenio;
	private Integer		nIdCorr;
	private String 		sCentroTrabajo;
	private Float 		flDistancia;

	public Poblacion(){
		
	}
	
	//Constructor
	public Poblacion(String sCentroTrabajo, Float flDistancia) {
		super();
		this.sCentroTrabajo = sCentroTrabajo;
		this.flDistancia = flDistancia;
	}
	
	public Integer getnIdConvenio() {
		return nIdConvenio;
	}

	public void setnIdConvenio(Integer nIdConvenio) {
		this.nIdConvenio = nIdConvenio;
	}

	public Integer getnIdCorr() {
		return nIdCorr;
	}

	public void setnIdCorr(Integer nIdCorr) {
		this.nIdCorr = nIdCorr;
	}

	public String getsCentroTrabajo() {
		return sCentroTrabajo;
	}

	public void setsCentroTrabajo(String sCentroTrabajo) {
		this.sCentroTrabajo = sCentroTrabajo;
	}

	public Float getFlDistancia() {
		return flDistancia;
	}

	public void setFlDistancia(Float flDistancia) {
		this.flDistancia = flDistancia;
	}

}