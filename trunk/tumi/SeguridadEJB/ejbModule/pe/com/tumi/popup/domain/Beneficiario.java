package pe.com.tumi.popup.domain;

public class Beneficiario {
	private Integer intIdPersona;
	private Integer intIdTipoVinculo;
	private Integer intTipoDocumento;
	private String strTipoDocumento;
	private String strNroDoc;
	private String strApPaterno;
	private String strApMaterno;
	private String strNombres;
	private String strFecNac;
	private Integer intIdSexo;
	private Integer intEstCivil;
	private String strTipoVinculoBenef;
	private String strTipoPersonaBenef;
	private String strTipoDocBenef;
	private String strTipoRolesActuales;
	private String strNroDocBenef;
	private String strTieneEmpresa;
	private String strFoto;
	private String strFirma;
	private Integer intIdEmpresaBeneficiario;
	private Integer intIdEmpresaSocio;
	private Integer intIdPersonaSocio;
	private Float	flPorcAportes;
	private Float	flPorcFondoSepelio;
	private Float	flPorcFondoRetiro;
	
	//Getters y Setters
	public Integer getIntTipoDocumento() {
		return intTipoDocumento;
	}
	public Integer getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(Integer intIdPersona) {
		this.intIdPersona = intIdPersona;
	}
	public Integer getIntIdTipoVinculo() {
		return intIdTipoVinculo;
	}
	public void setIntIdTipoVinculo(Integer intIdTipoVinculo) {
		this.intIdTipoVinculo = intIdTipoVinculo;
	}
	public void setIntTipoDocumento(Integer intTipoDocumento) {
		this.intTipoDocumento = intTipoDocumento;
	}
	public String getStrTipoDocumento() {
		return strTipoDocumento;
	}
	public void setStrTipoDocumento(String strTipoDocumento) {
		this.strTipoDocumento = strTipoDocumento;
	}
	public String getStrNroDoc() {
		return strNroDoc;
	}
	public void setStrNroDoc(String strNroDoc) {
		this.strNroDoc = strNroDoc;
	}
	public String getStrApPaterno() {
		return strApPaterno;
	}
	public void setStrApPaterno(String strApPaterno) {
		this.strApPaterno = strApPaterno;
	}
	public String getStrApMaterno() {
		return strApMaterno;
	}
	public void setStrApMaterno(String strApMaterno) {
		this.strApMaterno = strApMaterno;
	}
	public String getStrNombres() {
		return strNombres;
	}
	public void setStrNombres(String strNombres) {
		this.strNombres = strNombres;
	}
	public String getStrFecNac() {
		return strFecNac;
	}
	public void setStrFecNac(String strFecNac) {
		this.strFecNac = strFecNac;
	}
	public Integer getIntIdSexo() {
		return intIdSexo;
	}
	public void setIntIdSexo(Integer intIdSexo) {
		this.intIdSexo = intIdSexo;
	}
	public Integer getIntEstCivil() {
		return intEstCivil;
	}
	public void setIntEstCivil(Integer intEstCivil) {
		this.intEstCivil = intEstCivil;
	}
	public String getStrTipoPersonaBenef() {
		return strTipoPersonaBenef;
	}
	public void setStrTipoPersonaBenef(String strTipoPersonaBenef) {
		this.strTipoPersonaBenef = strTipoPersonaBenef;
	}
	public String getStrTipoDocBenef() {
		return strTipoDocBenef;
	}
	public void setStrTipoDocBenef(String strTipoDocBenef) {
		this.strTipoDocBenef = strTipoDocBenef;
	}
	public String getStrNroDocBenef() {
		return strNroDocBenef;
	}
	public void setStrNroDocBenef(String strNroDocBenef) {
		this.strNroDocBenef = strNroDocBenef;
	}
	public String getStrTipoRolesActuales() {
		return strTipoRolesActuales;
	}
	public void setStrTipoRolesActuales(String strTipoRolesActuales) {
		this.strTipoRolesActuales = strTipoRolesActuales;
	}
	public String getStrTipoVinculoBenef() {
		return strTipoVinculoBenef;
	}
	public void setStrTipoVinculoBenef(String strTipoVinculoBenef) {
		this.strTipoVinculoBenef = strTipoVinculoBenef;
	}
	public String getStrTieneEmpresa() {
		return strTieneEmpresa;
	}
	public void setStrTieneEmpresa(String strTieneEmpresa) {
		this.strTieneEmpresa = strTieneEmpresa;
	}
	public String getStrFoto() {
		return strFoto;
	}
	public void setStrFoto(String strFoto) {
		this.strFoto = strFoto;
	}
	public String getStrFirma() {
		return strFirma;
	}
	public void setStrFirma(String strFirma) {
		this.strFirma = strFirma;
	}
	public Integer getIntIdEmpresaBeneficiario() {
		return intIdEmpresaBeneficiario;
	}
	public void setIntIdEmpresaBeneficiario(Integer intIdEmpresaBeneficiario) {
		this.intIdEmpresaBeneficiario = intIdEmpresaBeneficiario;
	}
	public Integer getIntIdEmpresaSocio() {
		return intIdEmpresaSocio;
	}
	public void setIntIdEmpresaSocio(Integer intIdEmpresaSocio) {
		this.intIdEmpresaSocio = intIdEmpresaSocio;
	}
	public Integer getIntIdPersonaSocio() {
		return intIdPersonaSocio;
	}
	public void setIntIdPersonaSocio(Integer intIdPersonaSocio) {
		this.intIdPersonaSocio = intIdPersonaSocio;
	}
	public Float getFlPorcAportes() {
		return flPorcAportes;
	}
	public void setFlPorcAportes(Float flPorcAportes) {
		this.flPorcAportes = flPorcAportes;
	}
	public Float getFlPorcFondoSepelio() {
		return flPorcFondoSepelio;
	}
	public void setFlPorcFondoSepelio(Float flPorcFondoSepelio) {
		this.flPorcFondoSepelio = flPorcFondoSepelio;
	}
	public Float getFlPorcFondoRetiro() {
		return flPorcFondoRetiro;
	}
	public void setFlPorcFondoRetiro(Float flPorcFondoRetiro) {
		this.flPorcFondoRetiro = flPorcFondoRetiro;
	}
}