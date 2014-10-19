package pe.com.tumi.tesoreria.cierreLogisticaOper.domain;

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CierreLogisticaOper extends TumiDomain{

	private CierreLogisticaOperId id;
	private Integer intParaEstadoCierreCod;
	private Integer intPersEmpresaRegistroPk;
	private Integer intPersPersonaRegistroPk;
	private Timestamp tsCicoFechaRegistro;
	//atributos para la busqueda
	private String strNombreUsuario;
	private String strDescripcionEstado;
	private String strDescripcionTipo;
	private String strPeriodoNombre;
	
	
	public CierreLogisticaOperId getId() {
		return id;
	}
	public void setId(CierreLogisticaOperId id) {
		this.id = id;
	}
	public Integer getIntParaEstadoCierreCod() {
		return intParaEstadoCierreCod;
	}
	public void setIntParaEstadoCierreCod(Integer intParaEstadoCierreCod) {
		this.intParaEstadoCierreCod = intParaEstadoCierreCod;
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
	public String getStrDescripcionTipo() {
		return strDescripcionTipo;
	}
	public void setStrDescripcionTipo(String strDescripcionTipo) {
		this.strDescripcionTipo = strDescripcionTipo;
	}
	public String getStrPeriodoNombre() {
		return strPeriodoNombre;
	}
	public void setStrPeriodoNombre(String strPeriodoNombre) {
		this.strPeriodoNombre = strPeriodoNombre;
	}
}
