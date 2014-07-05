package pe.com.tumi.seguridad.login.domain;

import java.sql.Timestamp;
import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class UsuarioSubSucursalId extends TumiDomain {

	private Integer intPersEmpresaPk;
	private Integer intIdSucursal;
	private Integer intIdSubSucursal;
	private Timestamp dtFechaRegistro;
	private Integer intPersPersonaPk;
	
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
	public Integer getIntIdSubSucursal() {
		return intIdSubSucursal;
	}
	public void setIntIdSubSucursal(Integer intIdSubSucursal) {
		this.intIdSubSucursal = intIdSubSucursal;
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
