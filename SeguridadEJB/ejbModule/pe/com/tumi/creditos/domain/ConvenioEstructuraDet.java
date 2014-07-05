package pe.com.tumi.creditos.domain;

public class ConvenioEstructuraDet {
	private Integer nIdConvenio;
	private Integer nIdEmpresa;
	private Integer nIdNivel;
	private Long	nIdCodigo;
	private Integer nIdCaso;
	
	private String sDescripcion;
	private String sTipoNombrado;
	private Integer nPadron;
	private Integer nSocios;
	private Double doPorcDcto;
	private Double doPorcMoros;

	// Getters y Setters
	//Constructor con Padrón y Socios
	public ConvenioEstructuraDet(String sDescripcion, String sTipoNombrado, Integer nPadron,
			Integer nSocios, Double doPorcDcto, Double doPorcMoros) {
		super();
		this.sDescripcion = sDescripcion;
		this.sTipoNombrado = sTipoNombrado;
		this.nPadron = nPadron;
		this.nSocios = nSocios;
		this.doPorcDcto = doPorcDcto;
		this.doPorcMoros = doPorcMoros;
	}
	
	public ConvenioEstructuraDet(){
		
	}
	
	public Integer getnIdConvenio() {
		return nIdConvenio;
	}

	public void setnIdConvenio(Integer nIdConvenio) {
		this.nIdConvenio = nIdConvenio;
	}

	public Integer getnIdEmpresa() {
		return nIdEmpresa;
	}

	public void setnIdEmpresa(Integer nIdEmpresa) {
		this.nIdEmpresa = nIdEmpresa;
	}

	public Integer getnIdNivel() {
		return nIdNivel;
	}

	public void setnIdNivel(Integer nIdNivel) {
		this.nIdNivel = nIdNivel;
	}
	
	public Long getnIdCodigo() {
		return nIdCodigo;
	}

	public void setnIdCodigo(Long nIdCodigo) {
		this.nIdCodigo = nIdCodigo;
	}

	public Integer getnIdCaso() {
		return nIdCaso;
	}

	public void setnIdCaso(Integer nIdCaso) {
		this.nIdCaso = nIdCaso;
	}

	public String getsDescripcion() {
		return sDescripcion;
	}

	public void setsDescripcion(String sDescripcion) {
		this.sDescripcion = sDescripcion;
	}

	public String getsTipoNombrado() {
		return sTipoNombrado;
	}

	public void setsTipoNombrado(String sTipoNombrado) {
		this.sTipoNombrado = sTipoNombrado;
	}

	public Integer getnPadron() {
		return nPadron;
	}

	public void setnPadron(Integer nPadron) {
		this.nPadron = nPadron;
	}

	public Integer getnSocios() {
		return nSocios;
	}

	public void setnSocios(Integer nSocios) {
		this.nSocios = nSocios;
	}

	public Double getDoPorcDcto() {
		return doPorcDcto;
	}

	public void setDoPorcDcto(Double doPorcDcto) {
		this.doPorcDcto = doPorcDcto;
	}

	public Double getDoPorcMoros() {
		return doPorcMoros;
	}

	public void setDoPorcMoros(Double doPorcMoros) {
		this.doPorcMoros = doPorcMoros;
	}
}