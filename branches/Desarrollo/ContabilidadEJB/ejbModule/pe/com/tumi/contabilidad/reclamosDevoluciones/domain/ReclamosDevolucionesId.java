package pe.com.tumi.contabilidad.reclamosDevoluciones.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ReclamosDevolucionesId extends TumiDomain{
private Integer intPersEmpresa;
private Integer intContItemReclamoDevolucion;

//getter and Setters
public Integer getIntPersEmpresa() {
	return intPersEmpresa;
}
public void setIntPersEmpresa(Integer intPersEmpresa) {
	this.intPersEmpresa = intPersEmpresa;
}
public Integer getIntContItemReclamoDevolucion() {
	return intContItemReclamoDevolucion;
}
public void setIntContItemReclamoDevolucion(Integer intContItemReclamoDevolucion) {
	this.intContItemReclamoDevolucion = intContItemReclamoDevolucion;
}
}
