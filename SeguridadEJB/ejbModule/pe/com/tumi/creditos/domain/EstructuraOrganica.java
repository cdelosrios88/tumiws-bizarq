package pe.com.tumi.creditos.domain;

import java.util.List;

import javax.faces.model.SelectItem;

public class EstructuraOrganica {
	private Integer intTipoEntidad;
	private String  strTipoEntidad;
	private Integer intEstadoDocumento;
	private String 	strEstadoDocumento;
	private String  strNombreEntidad;
	private Integer intTipoEntidadTerceros;
	private String  strTipoEntidadTerceros;
	private Integer intTipoSocio;
	private String  strTipoSocio;
	private SelectItem itemTipoSocio;
	private Integer intModalidad;
	private String  strModalidad;
	private Integer intSucursal;
	private String  strSucursal;
	private Integer intSubsucursal;
	private String  strSubsucursal;
	private Boolean blnCheckSubsucursal;
	private Long 	intCodigoEstructura;
	private Boolean blnCheckCodigo;
	private Long 	intCodigoRel;
	private Long 	intCodigoRelOut;
	private Integer intIdPersona;
	private Integer intIdUsuario;
	private Integer intNivelRel;
	private List 	cursorLista;
	private Integer intIdEmpresa;
	private Integer intNivel;
	private String  strNivel;
	private Integer intCasoConfig;
	private String	strCasoConfig;
	private String 	strFechaDesde;
	private String  strFechaHasta;
	private String  strFechaRegistro;
	private String  strFechaEnviado;
	private String  strFechaEfectuado;
	private String  strFechaCheque;
	private Integer intDiaEnviado;
	private Integer intDiaEfectuado;
	private Integer intDiaCheque;
	private Integer intFechaEnviado;
	private Integer intFechaEfectuado;
	private Integer intFechaCheque;
	private String  strCodigoExterno;
	
	public Integer getIntTipoEntidad() {
		return intTipoEntidad;
	}
	public void setIntTipoEntidad(Integer intTipoEntidad) {
		this.intTipoEntidad = intTipoEntidad;
	}
	public Integer getIntEstadoDocumento() {
		return intEstadoDocumento;
	}
	public void setIntEstadoDocumento(Integer intEstadoDocumento) {
		this.intEstadoDocumento = intEstadoDocumento;
	}
	public String getStrNombreEntidad() {
		return strNombreEntidad;
	}
	public void setStrNombreEntidad(String strNombreEntidad) {
		this.strNombreEntidad = strNombreEntidad;
	}
	public Integer getIntTipoEntidadTerceros() {
		return intTipoEntidadTerceros;
	}
	public void setIntTipoEntidadTerceros(Integer intTipoEntidadTerceros) {
		this.intTipoEntidadTerceros = intTipoEntidadTerceros;
	}
	public Integer getIntTipoSocio() {
		return intTipoSocio;
	}
	public void setIntTipoSocio(Integer intTipoSocio) {
		this.intTipoSocio = intTipoSocio;
	}
	public Integer getIntModalidad() {
		return intModalidad;
	}
	public void setIntModalidad(Integer intModalidad) {
		this.intModalidad = intModalidad;
	}
	public Integer getIntSucursal() {
		return intSucursal;
	}
	public void setIntSucursal(Integer intSucursal) {
		this.intSucursal = intSucursal;
	}
	public Integer getIntSubsucursal() {
		return intSubsucursal;
	}
	public void setIntSubsucursal(Integer intSubsucursal) {
		this.intSubsucursal = intSubsucursal;
	}
	public Long getIntCodigoEstructura() {
		return intCodigoEstructura;
	}
	public void setIntCodigoEstructura(Long intCodigoEstructura) {
		this.intCodigoEstructura = intCodigoEstructura;
	}
	public List getCursorLista() {
		return cursorLista;
	}
	public void setCursorLista(List cursorLista) {
		this.cursorLista = cursorLista;
	}
	public String getStrTipoEntidadTerceros() {
		return strTipoEntidadTerceros;
	}
	public void setStrTipoEntidadTerceros(String strTipoEntidadTerceros) {
		this.strTipoEntidadTerceros = strTipoEntidadTerceros;
	}
	public Integer getIntIdEmpresa() {
		return intIdEmpresa;
	}
	public void setIntIdEmpresa(Integer intIdEmpresa) {
		this.intIdEmpresa = intIdEmpresa;
	}
	public Integer getIntNivel() {
		return intNivel;
	}
	public void setIntNivel(Integer intNivel) {
		this.intNivel = intNivel;
	}
	public String getStrNivel() {
		return strNivel;
	}
	public void setStrNivel(String strNivel) {
		this.strNivel = strNivel;
	}
	public Integer getIntCasoConfig() {
		return intCasoConfig;
	}
	public void setIntCasoConfig(Integer intCasoConfig) {
		this.intCasoConfig = intCasoConfig;
	}
	public String getStrFechaDesde() {
		return strFechaDesde;
	}
	public void setStrFechaDesde(String strFechaDesde) {
		this.strFechaDesde = strFechaDesde;
	}
	public String getStrFechaHasta() {
		return strFechaHasta;
	}
	public void setStrFechaHasta(String strFechaHasta) {
		this.strFechaHasta = strFechaHasta;
	}
	public String getStrFechaRegistro() {
		return strFechaRegistro;
	}
	public void setStrFechaRegistro(String strFechaRegistro) {
		this.strFechaRegistro = strFechaRegistro;
	}
	public String getStrTipoSocio() {
		return strTipoSocio;
	}
	public void setStrTipoSocio(String strTipoSocio) {
		this.strTipoSocio = strTipoSocio;
	}
	public String getStrEstadoDocumento() {
		return strEstadoDocumento;
	}
	public void setStrEstadoDocumento(String strEstadoDocumento) {
		this.strEstadoDocumento = strEstadoDocumento;
	}
	public String getStrCasoConfig() {
		return strCasoConfig;
	}
	public void setStrCasoConfig(String strCasoConfig) {
		this.strCasoConfig = strCasoConfig;
	}
	public String getStrModalidad() {
		return strModalidad;
	}
	public void setStrModalidad(String strModalidad) {
		this.strModalidad = strModalidad;
	}
	public Integer getIntDiaEnviado() {
		return intDiaEnviado;
	}
	public void setIntDiaEnviado(Integer intDiaEnviado) {
		this.intDiaEnviado = intDiaEnviado;
	}
	public Integer getIntDiaEfectuado() {
		return intDiaEfectuado;
	}
	public void setIntDiaEfectuado(Integer intDiaEfectuado) {
		this.intDiaEfectuado = intDiaEfectuado;
	}
	public Integer getIntDiaCheque() {
		return intDiaCheque;
	}
	public void setIntDiaCheque(Integer intDiaCheque) {
		this.intDiaCheque = intDiaCheque;
	}
	public Boolean getBlnCheckCodigo() {
		return blnCheckCodigo;
	}
	public void setBlnCheckCodigo(Boolean blnCheckCodigo) {
		this.blnCheckCodigo = blnCheckCodigo;
	}
	public Boolean getBlnCheckSubsucursal() {
		return blnCheckSubsucursal;
	}
	public void setBlnCheckSubsucursal(Boolean blnCheckSubsucursal) {
		this.blnCheckSubsucursal = blnCheckSubsucursal;
	}
	public String getStrFechaEnviado() {
		return strFechaEnviado;
	}
	public void setStrFechaEnviado(String strFechaEnviado) {
		this.strFechaEnviado = strFechaEnviado;
	}
	public String getStrFechaEfectuado() {
		return strFechaEfectuado;
	}
	public void setStrFechaEfectuado(String strFechaEfectuado) {
		this.strFechaEfectuado = strFechaEfectuado;
	}
	public String getStrFechaCheque() {
		return strFechaCheque;
	}
	public void setStrFechaCheque(String strFechaCheque) {
		this.strFechaCheque = strFechaCheque;
	}
	public String getStrSucursal() {
		return strSucursal;
	}
	public void setStrSucursal(String strSucursal) {
		this.strSucursal = strSucursal;
	}
	public String getStrSubsucursal() {
		return strSubsucursal;
	}
	public void setStrSubsucursal(String strSubsucursal) {
		this.strSubsucursal = strSubsucursal;
	}
	public SelectItem getItemTipoSocio() {
		return itemTipoSocio;
	}
	public void setItemTipoSocio(SelectItem itemTipoSocio) {
		this.itemTipoSocio = itemTipoSocio;
	}
	public Integer getIntFechaEnviado() {
		return intFechaEnviado;
	}
	public void setIntFechaEnviado(Integer intFechaEnviado) {
		this.intFechaEnviado = intFechaEnviado;
	}
	public Integer getIntFechaEfectuado() {
		return intFechaEfectuado;
	}
	public void setIntFechaEfectuado(Integer intFechaEfectuado) {
		this.intFechaEfectuado = intFechaEfectuado;
	}
	public Integer getIntFechaCheque() {
		return intFechaCheque;
	}
	public void setIntFechaCheque(Integer intFechaCheque) {
		this.intFechaCheque = intFechaCheque;
	}
	public Long getIntCodigoRel() {
		return intCodigoRel;
	}
	public void setIntCodigoRel(Long intCodigoRel) {
		this.intCodigoRel = intCodigoRel;
	}
	public Integer getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(Integer intIdPersona) {
		this.intIdPersona = intIdPersona;
	}
	public Integer getIntIdUsuario() {
		return intIdUsuario;
	}
	public void setIntIdUsuario(Integer intIdUsuario) {
		this.intIdUsuario = intIdUsuario;
	}
	public Integer getIntNivelRel() {
		return intNivelRel;
	}
	public void setIntNivelRel(Integer intNivelRel) {
		this.intNivelRel = intNivelRel;
	}
	public Long getIntCodigoRelOut() {
		return intCodigoRelOut;
	}
	public void setIntCodigoRelOut(Long intCodigoRelOut) {
		this.intCodigoRelOut = intCodigoRelOut;
	}
	public String getStrCodigoExterno() {
		return strCodigoExterno;
	}
	public void setStrCodigoExterno(String strCodigoExterno) {
		this.strCodigoExterno = strCodigoExterno;
	}
	public String getStrTipoEntidad() {
		return strTipoEntidad;
	}
	public void setStrTipoEntidad(String strTipoEntidad) {
		this.strTipoEntidad = strTipoEntidad;
	}
}
