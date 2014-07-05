package pe.com.tumi.creditos.domain;

public class Competencia {
	private Integer nIdConvenio;
	private String sEntidadFinanc;
	private Integer nSocios;
	private Float flPlazoPrestamo;
	private Float flInteres;
	private Float flMontoAporte;
	private String sServOfrec;
	private Boolean chkCompetencia;
	
	
	public Competencia(String sEntidadFinanc,
			Integer nSocios, Float flPlazoPrestamo, Float flInteres,
			Float flMontoAporte, String sServOfrec, Boolean chkCompetencia) {
		super();
		this.sEntidadFinanc = sEntidadFinanc;
		this.nSocios = nSocios;
		this.flPlazoPrestamo = flPlazoPrestamo;
		this.flInteres = flInteres;
		this.flMontoAporte = flMontoAporte;
		this.sServOfrec = sServOfrec;
		this.chkCompetencia = chkCompetencia;
	}

	/*public Competencia(String sEntidadFinanc, Integer nSocios,
			Float flPlazoPrestamo, Float doInteres, Float doMontoAporte,
			String sServOfrec, Boolean chkCompetencia) {
		this.sEntidadFinanc = sEntidadFinanc;
		this.nSocios = nSocios;
		this.flPlazoPrestamo = flPlazoPrestamo;
		this.flInteres = flInteres;
		this.flMontoAporte = flMontoAporte;
		this.sServOfrec = sServOfrec;
		this.chkCompetencia = chkCompetencia;
	}*/

	public Competencia() {
	}
	
	public Integer getnIdConvenio() {
		return nIdConvenio;
	}

	public void setnIdConvenio(Integer nIdConvenio) {
		this.nIdConvenio = nIdConvenio;
	}

	public String getsEntidadFinanc() {
		return sEntidadFinanc;
	}

	public void setsEntidadFinanc(String sEntidadFinanc) {
		this.sEntidadFinanc = sEntidadFinanc;
	}

	public Integer getnSocios() {
		return nSocios;
	}

	public void setnSocios(Integer nSocios) {
		this.nSocios = nSocios;
	}

	public Float getFlPlazoPrestamo() {
		return flPlazoPrestamo;
	}

	public void setFlPlazoPrestamo(Float flPlazoPrestamo) {
		this.flPlazoPrestamo = flPlazoPrestamo;
	}
	
	public Float getFlInteres() {
		return flInteres;
	}

	public void setFlInteres(Float flInteres) {
		this.flInteres = flInteres;
	}

	public Float getFlMontoAporte() {
		return flMontoAporte;
	}

	public void setFlMontoAporte(Float flMontoAporte) {
		this.flMontoAporte = flMontoAporte;
	}

	public String getsServOfrec() {
		return sServOfrec;
	}

	public void setsServOfrec(String sServOfrec) {
		this.sServOfrec = sServOfrec;
	}

	public Boolean getChkCompetencia() {
		return chkCompetencia;
	}

	public void setChkCompetencia(Boolean chkCompetencia) {
		this.chkCompetencia = chkCompetencia;
	}	
}