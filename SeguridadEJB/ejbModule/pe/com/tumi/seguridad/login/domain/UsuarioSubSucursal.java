package pe.com.tumi.seguridad.login.domain;

import java.sql.Timestamp;
import java.util.Date;

import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class UsuarioSubSucursal extends TumiDomain {

	private UsuarioSubSucursalId id;
	private Subsucursal subSucursal;
	private EmpresaUsuario empresaUsuario;
	private Date dtDesde;
	private Date dtHasta;
	private Integer intIdEstado;
	private Timestamp tsFechaEliminacion;
	
	private Boolean blnIndeterminado;
	
	public UsuarioSubSucursalId getId() {
		return id;
	}
	public void setId(UsuarioSubSucursalId id) {
		this.id = id;
	}
	public Subsucursal getSubSucursal() {
		return subSucursal;
	}
	public void setSubSucursal(Subsucursal subSucursal) {
		this.subSucursal = subSucursal;
	}
	public EmpresaUsuario getEmpresaUsuario() {
		return empresaUsuario;
	}
	public void setEmpresaUsuario(EmpresaUsuario empresaUsuario) {
		this.empresaUsuario = empresaUsuario;
	}
	public Date getDtDesde() {
		return dtDesde;
	}
	public void setDtDesde(Date dtDesde) {
		this.dtDesde = dtDesde;
	}
	public Date getDtHasta() {
		return dtHasta;
	}
	public void setDtHasta(Date dtHasta) {
		this.dtHasta = dtHasta;
	}
	public Integer getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(Integer intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	public Boolean getBlnIndeterminado() {
		return blnIndeterminado;
	}
	public void setBlnIndeterminado(Boolean blnIndeterminado) {
		this.blnIndeterminado = blnIndeterminado;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
	
}
