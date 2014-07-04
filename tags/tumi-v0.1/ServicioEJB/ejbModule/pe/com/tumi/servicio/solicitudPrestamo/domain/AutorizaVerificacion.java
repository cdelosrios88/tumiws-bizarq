package pe.com.tumi.servicio.solicitudPrestamo.domain;

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AutorizaVerificacion extends TumiDomain {
	private AutorizaVerificacionId id;
	private String strNumero;
	private String strContacto;
	private String strRespuesta;
	private Integer intParaEstadoCod;
	private Timestamp tsFechaRegistro;
	private Integer intParaTipoArchivoInfoCod;
	private Integer intItemArchivoInfo;
	private Integer intItemHistoricoInfo;
	private Integer intParaTipoArchivoRenCod;
	private Integer intItemArchivoRen;
	private Integer intItemHistoricoRen;
	
	public AutorizaVerificacionId getId() {
		return id;
	}
	public void setId(AutorizaVerificacionId id) {
		this.id = id;
	}
	public String getStrNumero() {
		return strNumero;
	}
	public void setStrNumero(String strNumero) {
		this.strNumero = strNumero;
	}
	public String getStrContacto() {
		return strContacto;
	}
	public void setStrContacto(String strContacto) {
		this.strContacto = strContacto;
	}
	public String getStrRespuesta() {
		return strRespuesta;
	}
	public void setStrRespuesta(String strRespuesta) {
		this.strRespuesta = strRespuesta;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntParaTipoArchivoInfoCod() {
		return intParaTipoArchivoInfoCod;
	}
	public void setIntParaTipoArchivoInfoCod(Integer intParaTipoArchivoInfoCod) {
		this.intParaTipoArchivoInfoCod = intParaTipoArchivoInfoCod;
	}
	public Integer getIntItemArchivoInfo() {
		return intItemArchivoInfo;
	}
	public void setIntItemArchivoInfo(Integer intItemArchivoInfo) {
		this.intItemArchivoInfo = intItemArchivoInfo;
	}
	public Integer getIntItemHistoricoInfo() {
		return intItemHistoricoInfo;
	}
	public void setIntItemHistoricoInfo(Integer intItemHistoricoInfo) {
		this.intItemHistoricoInfo = intItemHistoricoInfo;
	}
	public Integer getIntParaTipoArchivoRenCod() {
		return intParaTipoArchivoRenCod;
	}
	public void setIntParaTipoArchivoRenCod(Integer intParaTipoArchivoRenCod) {
		this.intParaTipoArchivoRenCod = intParaTipoArchivoRenCod;
	}
	public Integer getIntItemArchivoRen() {
		return intItemArchivoRen;
	}
	public void setIntItemArchivoRen(Integer intItemArchivoRen) {
		this.intItemArchivoRen = intItemArchivoRen;
	}
	public Integer getIntItemHistoricoRen() {
		return intItemHistoricoRen;
	}
	public void setIntItemHistoricoRen(Integer intItemHistoricoRen) {
		this.intItemHistoricoRen = intItemHistoricoRen;
	}
}