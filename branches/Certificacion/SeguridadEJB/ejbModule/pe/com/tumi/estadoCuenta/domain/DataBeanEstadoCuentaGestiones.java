package pe.com.tumi.estadoCuenta.domain;

import java.sql.Timestamp;
import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DataBeanEstadoCuentaGestiones extends TumiDomain {
	private Integer intTipoGestionCobCod;
	private Integer intSubTipoGestionCobCod;
	private Integer intTipoPersonaOpe;
	private Date dtFechaGestion;
	private Timestamp dtHoraInicio;
	private Timestamp dtHoraFin;
	private Integer intParaLugarGestion;
	private Integer intCserItemExp;
	private Integer intCserdeteItemExp;
	private Integer intParaTipoResultado;
	private Integer intParaTipoSubResultado;
	private String strObservacion;
	private String strContacto;
	private String strDescSucursal;
	private String strNombreGestor;
	
	public Integer getIntTipoGestionCobCod() {
		return intTipoGestionCobCod;
	}
	public void setIntTipoGestionCobCod(Integer intTipoGestionCobCod) {
		this.intTipoGestionCobCod = intTipoGestionCobCod;
	}
	public Integer getIntSubTipoGestionCobCod() {
		return intSubTipoGestionCobCod;
	}
	public void setIntSubTipoGestionCobCod(Integer intSubTipoGestionCobCod) {
		this.intSubTipoGestionCobCod = intSubTipoGestionCobCod;
	}
	public Integer getIntTipoPersonaOpe() {
		return intTipoPersonaOpe;
	}
	public void setIntTipoPersonaOpe(Integer intTipoPersonaOpe) {
		this.intTipoPersonaOpe = intTipoPersonaOpe;
	}
	public Date getDtFechaGestion() {
		return dtFechaGestion;
	}
	public void setDtFechaGestion(Date dtFechaGestion) {
		this.dtFechaGestion = dtFechaGestion;
	}
	public Timestamp getDtHoraInicio() {
		return dtHoraInicio;
	}
	public void setDtHoraInicio(Timestamp dtHoraInicio) {
		this.dtHoraInicio = dtHoraInicio;
	}
	public Timestamp getDtHoraFin() {
		return dtHoraFin;
	}
	public void setDtHoraFin(Timestamp dtHoraFin) {
		this.dtHoraFin = dtHoraFin;
	}
	public Integer getIntParaLugarGestion() {
		return intParaLugarGestion;
	}
	public void setIntParaLugarGestion(Integer intParaLugarGestion) {
		this.intParaLugarGestion = intParaLugarGestion;
	}
	public Integer getIntCserItemExp() {
		return intCserItemExp;
	}
	public void setIntCserItemExp(Integer intCserItemExp) {
		this.intCserItemExp = intCserItemExp;
	}
	public Integer getIntCserdeteItemExp() {
		return intCserdeteItemExp;
	}
	public void setIntCserdeteItemExp(Integer intCserdeteItemExp) {
		this.intCserdeteItemExp = intCserdeteItemExp;
	}
	public Integer getIntParaTipoResultado() {
		return intParaTipoResultado;
	}
	public void setIntParaTipoResultado(Integer intParaTipoResultado) {
		this.intParaTipoResultado = intParaTipoResultado;
	}
	public Integer getIntParaTipoSubResultado() {
		return intParaTipoSubResultado;
	}
	public void setIntParaTipoSubResultado(Integer intParaTipoSubResultado) {
		this.intParaTipoSubResultado = intParaTipoSubResultado;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}
	public String getStrContacto() {
		return strContacto;
	}
	public void setStrContacto(String strContacto) {
		this.strContacto = strContacto;
	}
	public String getStrDescSucursal() {
		return strDescSucursal;
	}
	public void setStrDescSucursal(String strDescSucursal) {
		this.strDescSucursal = strDescSucursal;
	}
	public String getStrNombreGestor() {
		return strNombreGestor;
	}
	public void setStrNombreGestor(String strNombreGestor) {
		this.strNombreGestor = strNombreGestor;
	}
}
