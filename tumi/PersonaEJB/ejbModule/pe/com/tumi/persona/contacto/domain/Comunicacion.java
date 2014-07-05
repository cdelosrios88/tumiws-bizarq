package pe.com.tumi.persona.contacto.domain;

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;

public class Comunicacion extends TumiDomain{

	private ComunicacionPK id;
	private Integer intAnexo;
	private Integer intCasoEmergencia;
	private String strDato;
	private String strDescripcion;
	private String strComunicacion;
	private Integer intEstadoCod;
	private Integer intNumero;
	private Integer intSubTipoComunicacionCod;
	private Integer intTipoComunicacionCod;
	private Integer intTipoLineaCod;
	private Timestamp tsFechaEliminacion;
	private Boolean chkCom;
	
	private Persona persona;

	public ComunicacionPK getId() {
		return id;
	}

	public void setId(ComunicacionPK id) {
		this.id = id;
	}

	public Integer getIntAnexo() {
		return intAnexo;
	}

	public void setIntAnexo(Integer intAnexo) {
		this.intAnexo = intAnexo;
	}

	public Integer getIntCasoEmergencia() {
		return intCasoEmergencia;
	}

	public void setIntCasoEmergencia(Integer intCasoEmergencia) {
		this.intCasoEmergencia = intCasoEmergencia;
	}

	public String getStrDato() {
		return strDato;
	}

	public void setStrDato(String strDato) {
		this.strDato = strDato;
	}

	public String getStrDescripcion() {
		return strDescripcion;
	}

	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}

	public String getStrComunicacion() {
		return strComunicacion;
	}

	public void setStrComunicacion(String strComunicacion) {
		this.strComunicacion = strComunicacion;
	}

	public Integer getIntEstadoCod() {
		return intEstadoCod;
	}

	public void setIntEstadoCod(Integer intEstadoCod) {
		this.intEstadoCod = intEstadoCod;
	}

	public Integer getIntNumero() {
		return intNumero;
	}

	public void setIntNumero(Integer intNumero) {
		this.intNumero = intNumero;
	}

	public Integer getIntSubTipoComunicacionCod() {
		return intSubTipoComunicacionCod;
	}

	public void setIntSubTipoComunicacionCod(Integer intSubTipoComunicacionCod) {
		this.intSubTipoComunicacionCod = intSubTipoComunicacionCod;
	}

	public Integer getIntTipoComunicacionCod() {
		return intTipoComunicacionCod;
	}

	public void setIntTipoComunicacionCod(Integer intTipoComunicacionCod) {
		this.intTipoComunicacionCod = intTipoComunicacionCod;
	}

	public Integer getIntTipoLineaCod() {
		return intTipoLineaCod;
	}

	public void setIntTipoLineaCod(Integer intTipoLineaCod) {
		this.intTipoLineaCod = intTipoLineaCod;
	}

	public Boolean getChkCom() {
		return chkCom;
	}

	public void setChkCom(Boolean chkCom) {
		this.chkCom = chkCom;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}

	@Override
	public String toString() {
		return "Comunicacion [id=" + id + ", intAnexo=" + intAnexo
				+ ", intCasoEmergencia=" + intCasoEmergencia + ", strDato="
				+ strDato + ", strDescripcion=" + strDescripcion
				+ ", strComunicacion=" + strComunicacion + ", intEstadoCod="
				+ intEstadoCod + ", intNumero=" + intNumero
				+ ", intSubTipoComunicacionCod=" + intSubTipoComunicacionCod
				+ ", intTipoComunicacionCod=" + intTipoComunicacionCod
				+ ", intTipoLineaCod=" + intTipoLineaCod
				+ ", tsFechaEliminacion=" + tsFechaEliminacion + "]";
	}

}