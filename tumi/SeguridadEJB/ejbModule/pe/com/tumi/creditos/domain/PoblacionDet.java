package pe.com.tumi.creditos.domain;

public class PoblacionDet {
	private Integer nIdConvenio;
	private Integer nIdCorr;
	private Integer nTipoTrabajador;
	private String sTipoTrabajador;
	private Integer nTipoSocio;
	private String sTipoSocio;
	private Integer nPadron;

	public PoblacionDet() {

	}

	// Constructor
	public PoblacionDet(Integer nIdConvenio, Integer nTipoTrabajador,
			String sTipoTrabajador, Integer nTipoSocio, String sTipoSocio,
			Integer nPadron) {
		super();
		this.nIdConvenio = nIdConvenio;
		this.nTipoTrabajador = nTipoTrabajador;
		this.sTipoTrabajador = sTipoTrabajador;
		this.nTipoSocio = nTipoSocio;
		this.sTipoSocio = sTipoSocio;
		this.nPadron = nPadron;
	}

	// Getters y Setters
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

	public Integer getnTipoTrabajador() {
		return nTipoTrabajador;
	}

	public void setnTipoTrabajador(Integer nTipoTrabajador) {
		this.nTipoTrabajador = nTipoTrabajador;
	}

	public String getsTipoTrabajador() {
		return sTipoTrabajador;
	}

	public void setsTipoTrabajador(String sTipoTrabajador) {
		this.sTipoTrabajador = sTipoTrabajador;
	}

	public Integer getnTipoSocio() {
		return nTipoSocio;
	}

	public void setnTipoSocio(Integer nTipoSocio) {
		this.nTipoSocio = nTipoSocio;
	}

	public String getsTipoSocio() {
		return sTipoSocio;
	}

	public void setsTipoSocio(String sTipoSocio) {
		this.sTipoSocio = sTipoSocio;
	}

	public Integer getnPadron() {
		return nPadron;
	}

	public void setnPadron(Integer nPadron) {
		this.nPadron = nPadron;
	}
}
