package pe.com.tumi.contabilidad.cierre.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class LibroDiario extends TumiDomain {

	private LibroDiarioId id;
	private String strGlosa;
	private Timestamp tsFechaRegistro;
	private Timestamp tsFechaDocumento;
	private Integer intPersEmpresaUsuario;
	private Integer intPersPersonaUsuario;
	private Integer intParaEstado;
	private Timestamp tsfFechaEliminacion;
	private Integer	intParaTipoDocumentoGeneral;
	
	private List<LibroDiarioDetalle> listaLibroDiarioDetalle;
	private	String	strNumeroAsiento;
	
	//JCHAVEZ 06.01.2014
	private Integer intErrorGeneracionLibroDiario;
	private String strMsgErrorGeneracionLibroDiario;
	
	
	public LibroDiario(){
		id = new LibroDiarioId();
		listaLibroDiarioDetalle = new ArrayList<LibroDiarioDetalle>();
	}

	//GETTERS & SETTERS
	public LibroDiarioId getId() {
		return id;
	}
	public void setId(LibroDiarioId id) {
		this.id = id;
	}
	public String getStrGlosa() {
		return strGlosa;
	}
	public void setStrGlosa(String strGlosa) {
		this.strGlosa = strGlosa;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Timestamp getTsFechaDocumento() {
		return tsFechaDocumento;
	}
	public void setTsFechaDocumento(Timestamp tsFechaDocumento) {
		this.tsFechaDocumento = tsFechaDocumento;
	}
	public Integer getIntPersEmpresaUsuario() {
		return intPersEmpresaUsuario;
	}
	public void setIntPersEmpresaUsuario(Integer intPersEmpresaUsuario) {
		this.intPersEmpresaUsuario = intPersEmpresaUsuario;
	}
	public Integer getIntPersPersonaUsuario() {
		return intPersPersonaUsuario;
	}
	public void setIntPersPersonaUsuario(Integer intPersPersonaUsuario) {
		this.intPersPersonaUsuario = intPersPersonaUsuario;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public Timestamp getTsfFechaEliminacion() {
		return tsfFechaEliminacion;
	}
	public void setTsfFechaEliminacion(Timestamp tsfFechaEliminacion) {
		this.tsfFechaEliminacion = tsfFechaEliminacion;
	}
	public Integer getIntParaTipoDocumentoGeneral() {
		return intParaTipoDocumentoGeneral;
	}
	public void setIntParaTipoDocumentoGeneral(Integer intParaTipoDocumentoGeneral) {
		this.intParaTipoDocumentoGeneral = intParaTipoDocumentoGeneral;
	}
	public List<LibroDiarioDetalle> getListaLibroDiarioDetalle() {
		return listaLibroDiarioDetalle;
	}
	public void setListaLibroDiarioDetalle(
			List<LibroDiarioDetalle> listaLibroDiarioDetalle) {
		this.listaLibroDiarioDetalle = listaLibroDiarioDetalle;
	}
	public String getStrNumeroAsiento() {
		return strNumeroAsiento;
	}
	public void setStrNumeroAsiento(String strNumeroAsiento) {
		this.strNumeroAsiento = strNumeroAsiento;
	}
	public Integer getIntErrorGeneracionLibroDiario() {
		return intErrorGeneracionLibroDiario;
	}
	public void setIntErrorGeneracionLibroDiario(
			Integer intErrorGeneracionLibroDiario) {
		this.intErrorGeneracionLibroDiario = intErrorGeneracionLibroDiario;
	}
	public String getStrMsgErrorGeneracionLibroDiario() {
		return strMsgErrorGeneracionLibroDiario;
	}
	public void setStrMsgErrorGeneracionLibroDiario(
			String strMsgErrorGeneracionLibroDiario) {
		this.strMsgErrorGeneracionLibroDiario = strMsgErrorGeneracionLibroDiario;
	}
	
	@Override
	public String toString() {
		return "LibroDiario [id=" + id 
				+ ", intParaTipoDocumentoGeneral=" + intParaTipoDocumentoGeneral 
				+ ", strGlosa=" + strGlosa
				+ ", tsFechaRegistro=" + tsFechaRegistro
				+ ", tsFechaDocumento=" + tsFechaDocumento
				+ ", intPersEmpresaUsuario=" + intPersEmpresaUsuario
				+ ", intPersPersonaUsuario=" + intPersPersonaUsuario
				+ ", intParaEstado=" + intParaEstado 
				+ ", tsfFechaEliminacion="+ tsfFechaEliminacion 
				+ ", strNumeroAsiento="+ strNumeroAsiento + "]";
	}
}
