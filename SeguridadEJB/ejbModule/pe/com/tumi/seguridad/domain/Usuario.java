package pe.com.tumi.seguridad.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import pe.com.tumi.common.domain.EntidadBase;

public class Usuario extends EntidadBase {
	
	private String codigo;
	private String contrasenha;
	private String nombre;
	private String apePa;
	private String apeMa;
	private String correoElectronico;
	private String estado;
	private Date fechaCambioPass;
	private List roles = new ArrayList();
	private String areaGestora;
	private Integer empresa;
	private Integer perfil;
	private String  strPerfil;
	private String sucursal;
	

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public Long getId() {
		return super.getId();
	}

	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getContrasenha() {
		return contrasenha;
	}
	public void setContrasenha(String contrasenha) {
		this.contrasenha = contrasenha;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List get	() {
		return roles;
	}
	public void setRoles(List roles) {
		this.roles = roles;
	}
	
	public String getApePa() {
		return apePa;
	}
	public void setApePa(String apePa) {
		this.apePa = apePa;
	}

	public String getApeMa() {
		return apeMa;
	}
	public void setApeMa(String apeMa) {
		this.apeMa = apeMa;
	}

	public Date getFechaCambioPass() {
		return fechaCambioPass;
	}
	public void setFechaCambioPass(Date fechaCambioPass) {
		this.fechaCambioPass = fechaCambioPass;
	}
	public String getCondition(){
		return "ID_USR="+codigo;
	}

	public String getAreaGestora() {
		return areaGestora;
	}

	public void setAreaGestora(String areaGestora) {
		this.areaGestora = areaGestora;
	}
	
	public Integer getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Integer empresa) {
		this.empresa = empresa;
	}

	public Integer getPerfil() {
		return perfil;
	}

	public void setPerfil(Integer perfil) {
		this.perfil = perfil;
	}

	public String getStrPerfil() {
		return strPerfil;
	}

	public void setStrPerfil(String strPerfil) {
		this.strPerfil = strPerfil;
	}
	
}