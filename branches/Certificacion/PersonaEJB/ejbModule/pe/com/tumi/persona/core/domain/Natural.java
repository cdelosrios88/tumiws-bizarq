package pe.com.tumi.persona.core.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.empresa.domain.PerLaboral;

import java.util.Date;
import java.util.List;

public class Natural extends TumiDomain{

	private Integer intIdPersona;
	private String strApellidoMaterno;
	private String strApellidoPaterno;
	private String strNombres;
	private Date dtFechaNacimiento;
	private Integer intSexoCod;
	private Integer intEstadoCivilCod;
	private Integer intTipoArchivoFoto;
	private Integer intItemArchivoFoto;
	private Integer intItemHistoricoFoto;
	private Integer intTipoArchivoFirma;
	private Integer intItemArchivoFirma;
	private Integer intItemHistoricoFirma;	
	
	private Archivo foto;
	private Archivo firma;
	
	private String strFechaNacimiento;
	private Integer intTieneEmpresa;
	private Persona persona;
	private PerLaboral perLaboral;
	private List<PerLaboral> listaPerLaboral;
	
	private String strNombreCompleto;
	private Date dtFechaNacDesde;
	private Date dtFechaNacHasta;
	
	private	String	strPathFoto;
	private	String	strParhFirma;
	
	
	public Integer getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(Integer intIdPersona) {
		this.intIdPersona = intIdPersona;
	}
	public String getStrApellidoMaterno() {
		return strApellidoMaterno;
	}
	public void setStrApellidoMaterno(String strApellidoMaterno) {
		this.strApellidoMaterno = strApellidoMaterno;
	}
	public String getStrApellidoPaterno() {
		return strApellidoPaterno;
	}
	public void setStrApellidoPaterno(String strApellidoPaterno) {
		this.strApellidoPaterno = strApellidoPaterno;
	}
	public Integer getIntEstadoCivilCod() {
		return intEstadoCivilCod;
	}
	public void setIntEstadoCivilCod(Integer intEstadoCivilCod) {
		this.intEstadoCivilCod = intEstadoCivilCod;
	}
	public Date getDtFechaNacimiento() {
		return dtFechaNacimiento;
	}
	public void setDtFechaNacimiento(Date dtFechaNacimiento) {
		this.dtFechaNacimiento = dtFechaNacimiento;
	}
	public String getStrNombres() {
		return strNombres;
	}
	public void setStrNombres(String strNombres) {
		this.strNombres = strNombres;
	}
	/*public String getStrNroIdentidad() {
		return strNroIdentidad;
	}
	public void setStrNroIdentidad(String strNroIdentidad) {
		this.strNroIdentidad = strNroIdentidad;
	}*/
	public Integer getIntSexoCod() {
		return intSexoCod;
	}
	public void setIntSexoCod(Integer intSexoCod) {
		this.intSexoCod = intSexoCod;
	}
	public Integer getIntTieneEmpresa() {
		return intTieneEmpresa;
	}
	public void setIntTieneEmpresa(Integer intTieneEmpresa) {
		this.intTieneEmpresa = intTieneEmpresa;
	}
	/*public Integer getIntTipoIdentidadCod() {
		return intTipoIdentidadCod;
	}
	public void setIntTipoIdentidadCod(Integer intTipoIdentidadCod) {
		this.intTipoIdentidadCod = intTipoIdentidadCod;
	}*/
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public String getStrFechaNacimiento() {
		return strFechaNacimiento;
	}
	public void setStrFechaNacimiento(String strFechaNacimiento) {
		this.strFechaNacimiento = strFechaNacimiento;
	}
	public String getStrNombreCompleto() {
		return strNombreCompleto;
	}
	public void setStrNombreCompleto(String strNombreCompleto) {
		this.strNombreCompleto = strNombreCompleto;
	}
	public PerLaboral getPerLaboral() {
		return perLaboral;
	}
	public void setPerLaboral(PerLaboral perLaboral) {
		this.perLaboral = perLaboral;
	}
	public Date getDtFechaNacDesde() {
		return dtFechaNacDesde;
	}
	public void setDtFechaNacDesde(Date dtFechaNacDesde) {
		this.dtFechaNacDesde = dtFechaNacDesde;
	}
	public Date getDtFechaNacHasta() {
		return dtFechaNacHasta;
	}
	public void setDtFechaNacHasta(Date dtFechaNacHasta) {
		this.dtFechaNacHasta = dtFechaNacHasta;
	}
	public List<PerLaboral> getListaPerLaboral() {
		return listaPerLaboral;
	}
	public void setListaPerLaboral(List<PerLaboral> listaPerLaboral) {
		this.listaPerLaboral = listaPerLaboral;
	}
	public Integer getIntTipoArchivoFoto() {
		return intTipoArchivoFoto;
	}
	public void setIntTipoArchivoFoto(Integer intTipoArchivoFoto) {
		this.intTipoArchivoFoto = intTipoArchivoFoto;
	}
	public Integer getIntItemArchivoFoto() {
		return intItemArchivoFoto;
	}
	public void setIntItemArchivoFoto(Integer intItemArchivoFoto) {
		this.intItemArchivoFoto = intItemArchivoFoto;
	}
	public Integer getIntItemHistoricoFoto() {
		return intItemHistoricoFoto;
	}
	public void setIntItemHistoricoFoto(Integer intItemHistoricoFoto) {
		this.intItemHistoricoFoto = intItemHistoricoFoto;
	}
	public Integer getIntTipoArchivoFirma() {
		return intTipoArchivoFirma;
	}
	public void setIntTipoArchivoFirma(Integer intTipoArchivoFirma) {
		this.intTipoArchivoFirma = intTipoArchivoFirma;
	}
	public Integer getIntItemArchivoFirma() {
		return intItemArchivoFirma;
	}
	public void setIntItemArchivoFirma(Integer intItemArchivoFirma) {
		this.intItemArchivoFirma = intItemArchivoFirma;
	}
	public Integer getIntItemHistoricoFirma() {
		return intItemHistoricoFirma;
	}
	public void setIntItemHistoricoFirma(Integer intItemHistoricoFirma) {
		this.intItemHistoricoFirma = intItemHistoricoFirma;
	}
	public Archivo getFoto() {
		return foto;
	}
	public void setFoto(Archivo foto) {
		this.foto = foto;
	}
	public Archivo getFirma() {
		return firma;
	}
	public void setFirma(Archivo firma) {
		this.firma = firma;
	}
	public String getStrPathFoto() {
		return strPathFoto;
	}
	public void setStrPathFoto(String strPathFoto) {
		this.strPathFoto = strPathFoto;
	}
	public String getStrParhFirma() {
		return strParhFirma;
	}
	public void setStrParhFirma(String strParhFirma) {
		this.strParhFirma = strParhFirma;
	}
	
	
	
	
	@Override
	public String toString() {
		return "Natural [intIdPersona=" + intIdPersona
				+ ", strApellidoMaterno=" + strApellidoMaterno
				+ ", strApellidoPaterno=" + strApellidoPaterno
				+ ", strNombres=" + strNombres + ", dtFechaNacimiento="
				+ dtFechaNacimiento + ", intSexoCod=" + intSexoCod
				+ ", intEstadoCivilCod=" + intEstadoCivilCod
				+ ", intTipoArchivoFoto=" + intTipoArchivoFoto
				+ ", intItemArchivoFoto=" + intItemArchivoFoto
				+ ", intItemHistoricoFoto=" + intItemHistoricoFoto
				+ ", intTipoArchivoFirma=" + intTipoArchivoFirma
				+ ", intItemArchivoFirma=" + intItemArchivoFirma
				+ ", intItemHistoricoFirma=" + intItemHistoricoFirma + "]";
	}   	
}