package pe.com.tumi.contabilidad.cierreContabilidad.domain;

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CierreContabilidad extends TumiDomain{
	
	private CierreContabilidadId id;
	private Integer intPersEmpresaRegistroPk;
	private Integer intPersPersonaRegistroPk;
	private Timestamp tsCicoFechaRegistro;
	
	private String strDescripcionEstado;
	
	//Nombre Usuario
	private String strNombreUsuario;
	
	public CierreContabilidadId getId() {
		return id;
	}
	public void setId(CierreContabilidadId id) {
		this.id = id;
	}
	public Integer getIntPersEmpresaRegistroPk() {
		return intPersEmpresaRegistroPk;
	}
	public void setIntPersEmpresaRegistroPk(Integer intPersEmpresaRegistroPk) {
		this.intPersEmpresaRegistroPk = intPersEmpresaRegistroPk;
	}
	public Integer getIntPersPersonaRegistroPk() {
		return intPersPersonaRegistroPk;
	}
	public void setIntPersPersonaRegistroPk(Integer intPersPersonaRegistroPk) {
		this.intPersPersonaRegistroPk = intPersPersonaRegistroPk;
	}
	public Timestamp getTsCicoFechaRegistro() {
		return tsCicoFechaRegistro;
	}
	public void setTsCicoFechaRegistro(Timestamp tsCicoFechaRegistro) {
		this.tsCicoFechaRegistro = tsCicoFechaRegistro;
	}
	public String getStrNombreUsuario() {
		return strNombreUsuario;
	}
	public void setStrNombreUsuario(String strNombreUsuario) {
		this.strNombreUsuario = strNombreUsuario;
	}
	public String getStrDescripcionEstado() {
		return strDescripcionEstado;
	}
	public void setStrDescripcionEstado(String strDescripcionEstado) {
		this.strDescripcionEstado = strDescripcionEstado;
	} 
}
