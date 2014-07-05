package pe.com.tumi.servicio.prevision.domain;

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaVerificacionId;

public class AutorizaVerificaPrevision extends TumiDomain{
	private AutorizaVerificaPrevisionId id;
	private Integer intParaEstadoCod;
	private Timestamp tsFechaRegistro;
	private Integer intParaTipoArchivoDeJu;
	private Integer intItemArchivoDeJu;
	private Integer intItemHistoricoDeJu;
	private Integer intParaTipoArchivoRen;
	private Integer intItemArchivoRen;
	private Integer intItemHistoricoRen;
	public AutorizaVerificaPrevisionId getId() {
		return id;
	}
	public void setId(AutorizaVerificaPrevisionId id) {
		this.id = id;
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
	public Integer getIntParaTipoArchivoDeJu() {
		return intParaTipoArchivoDeJu;
	}
	public void setIntParaTipoArchivoDeJu(Integer intParaTipoArchivoDeJu) {
		this.intParaTipoArchivoDeJu = intParaTipoArchivoDeJu;
	}
	public Integer getIntItemArchivoDeJu() {
		return intItemArchivoDeJu;
	}
	public void setIntItemArchivoDeJu(Integer intItemArchivoDeJu) {
		this.intItemArchivoDeJu = intItemArchivoDeJu;
	}
	public Integer getIntItemHistoricoDeJu() {
		return intItemHistoricoDeJu;
	}
	public void setIntItemHistoricoDeJu(Integer intItemHistoricoDeJu) {
		this.intItemHistoricoDeJu = intItemHistoricoDeJu;
	}
	public Integer getIntParaTipoArchivoRen() {
		return intParaTipoArchivoRen;
	}
	public void setIntParaTipoArchivoRen(Integer intParaTipoArchivoRen) {
		this.intParaTipoArchivoRen = intParaTipoArchivoRen;
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
