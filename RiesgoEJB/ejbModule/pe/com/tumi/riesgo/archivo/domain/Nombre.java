package pe.com.tumi.riesgo.archivo.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Nombre extends TumiDomain{

	private NombreId id;
	private Configuracion configuracion;
	private Integer intParaTipoValorCod;
	private Integer intParaTipoVariableCod;
	private String strValorFijo;
	private Integer intParaEstadoCod;
	//strValorVariable solo usado para muestra de interfaz
	private String strValorVariable;
	
	public Nombre(){
		super();
		id = new NombreId();
	}
	
	public NombreId getId() {
		return id;
	}
	public void setId(NombreId id) {
		this.id = id;
	}
	public Configuracion getConfiguracion() {
		return configuracion;
	}
	public void setConfiguracion(Configuracion configuracion) {
		this.configuracion = configuracion;
	}
	public Integer getIntParaTipoValorCod() {
		return intParaTipoValorCod;
	}
	public void setIntParaTipoValorCod(Integer intParaTipoValorCod) {
		this.intParaTipoValorCod = intParaTipoValorCod;
	}
	public Integer getIntParaTipoVariableCod() {
		return intParaTipoVariableCod;
	}
	public void setIntParaTipoVariableCod(Integer intParaTipoVariableCod) {
		this.intParaTipoVariableCod = intParaTipoVariableCod;
	}
	public String getStrValorFijo() {
		return strValorFijo;
	}
	public void setStrValorFijo(String strValorFijo) {
		this.strValorFijo = strValorFijo;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public String getStrValorVariable() {
		return strValorVariable;
	}
	public void setStrValorVariable(String strValorVariable) {
		this.strValorVariable = strValorVariable;
	}
	@Override
	public String toString() {
		return "Nombre [id=" + id + ", configuracion=" + configuracion
				+ ", intParaTipoValorCod=" + intParaTipoValorCod
				+ ", intParaTipoVariableCod=" + intParaTipoVariableCod
				+ ", strValorFijo=" + strValorFijo + ", intParaEstadoCod="
				+ intParaEstadoCod + ", strValorVariable=" + strValorVariable
				+ "]";
	}
}