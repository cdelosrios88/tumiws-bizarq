package pe.com.tumi.creditos.domain;

public class EstructuraHojaPlan {
	private Integer nIdConvenio;
	private Integer nIdEmpresa;
	private Integer nIdNivel;
	private Long nIdCodigo;
	private Integer nIdCaso;
	private String sNivelDesc;
	private Integer nIdPersona;
	private String sNombrePersona;
	private Integer nIdUsuario;
	private Integer nIdModalidad;
	private String sModalidadDesc;
	private Integer nIdTipoSocio;
	private String sTipoSocioDesc;
	private Integer nNroSocios;

	public EstructuraHojaPlan() {
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

	public Integer getnIdConvenio() {
		return nIdConvenio;
	}

	public void setnIdConvenio(Integer nIdConvenio) {
		this.nIdConvenio = nIdConvenio;
	}

	public String getsNivelDesc() {
		return sNivelDesc;
	}

	public void setsNivelDesc(String sNivelDesc) {
		this.sNivelDesc = sNivelDesc;
	}

	public Integer getnIdPersona() {
		return nIdPersona;
	}

	public void setnIdPersona(Integer nIdPersona) {
		this.nIdPersona = nIdPersona;
	}

	public String getsNombrePersona() {
		return sNombrePersona;
	}

	public void setsNombrePersona(String sNombrePersona) {
		this.sNombrePersona = sNombrePersona;
	}

	public Integer getnIdUsuario() {
		return nIdUsuario;
	}

	public void setnIdUsuario(Integer nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}

	public Integer getnIdModalidad() {
		return nIdModalidad;
	}

	public void setnIdModalidad(Integer nIdModalidad) {
		this.nIdModalidad = nIdModalidad;
	}

	public String getsModalidadDesc() {
		return sModalidadDesc;
	}

	public void setsModalidadDesc(String sModalidadDesc) {
		this.sModalidadDesc = sModalidadDesc;
	}

	public Integer getnIdTipoSocio() {
		return nIdTipoSocio;
	}

	public void setnIdTipoSocio(Integer nIdTipoSocio) {
		this.nIdTipoSocio = nIdTipoSocio;
	}

	public String getsTipoSocioDesc() {
		return sTipoSocioDesc;
	}

	public void setsTipoSocioDesc(String sTipoSocioDesc) {
		this.sTipoSocioDesc = sTipoSocioDesc;
	}

	public Integer getnNroSocios() {
		return nNroSocios;
	}

	public void setnNroSocios(Integer nNroSocios) {
		this.nNroSocios = nNroSocios;
	}

}