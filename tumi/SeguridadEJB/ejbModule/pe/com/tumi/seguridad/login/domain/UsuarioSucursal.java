package pe.com.tumi.seguridad.login.domain;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class UsuarioSucursal extends TumiDomain {

	private UsuarioSucursalId id;
	private Sucursal sucursal;
	private EmpresaUsuario empresaUsuario;
	private Date dtDesde;
	private Date dtHasta;
	private Integer intIdEstado;
	private Timestamp tsFechaEliminacion;
	
	private Boolean blnIndeterminado;
	private List<Subsucursal> listaSubSucursal;

	public UsuarioSucursalId getId() {
		return id;
	}
	public void setId(UsuarioSucursalId id) {
		this.id = id;
	}
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
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
	public List<Subsucursal> getListaSubSucursal() {
		return listaSubSucursal;
	}
	public void setListaSubSucursal(List<Subsucursal> listaSubSucursal) {
		this.listaSubSucursal = listaSubSucursal;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
	
}
