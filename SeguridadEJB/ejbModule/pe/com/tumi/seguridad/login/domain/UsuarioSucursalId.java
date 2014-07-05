package pe.com.tumi.seguridad.login.domain;

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class UsuarioSucursalId extends TumiDomain {

	private Integer intPersEmpresaPk;
	private Integer intIdSucursal;
	private Integer intPersPersonaPk;
	private Timestamp dtFechaRegistro;
	
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public Integer getIntIdSucursal() {
		return intIdSucursal;
	}
	public void setIntIdSucursal(Integer intIdSucursal) {
		this.intIdSucursal = intIdSucursal;
	}
	public Integer getIntPersPersonaPk() {
		return intPersPersonaPk;
	}
	public void setIntPersPersonaPk(Integer intPersPersonaPk) {
		this.intPersPersonaPk = intPersPersonaPk;
	}
	public Timestamp getDtFechaRegistro() {
		return dtFechaRegistro;
	}
	public void setDtFechaRegistro(Timestamp dtFechaRegistro) {
		this.dtFechaRegistro = dtFechaRegistro;
	}
	

}
