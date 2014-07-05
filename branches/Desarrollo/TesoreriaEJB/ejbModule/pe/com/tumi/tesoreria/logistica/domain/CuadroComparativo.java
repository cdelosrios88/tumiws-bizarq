package pe.com.tumi.tesoreria.logistica.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;

public class CuadroComparativo extends TumiDomain{

	private CuadroComparativoId	id;
	private Timestamp tsFechaRegistro;
	private Integer intParaTipoPropuesta;
	private Integer intPersEmpresaRequisicion;
	private Integer intItemRequisicion;
	private Integer intParaEstado;
	private Integer intParaEstadoAprobacion;
	private Timestamp tsFechaAutoriza;
	private Integer intPersEmpresaAutoriza;
	private Integer intPersPersonaAutoriza;
	private Integer intItemCuadroComparativoPropuestaAutoriza;
	private String	strObservacionAutoriza;
	private Integer intPersEmpresaUsuario;
	private Integer intPersPersonaUsuario;
	private Timestamp tsFechaAnula;
	private Integer intPersEmpresaAnula;
	private Integer intPersPersonaAnula;
	
	private Requisicion	requisicion;
	private List<CuadroComparativoProveedor> 	listaCuadroComparativoProveedor;
	private CuadroComparativoProveedor 			proveedorAprobado;
	private Persona 	personaAprueba;
	private OrdenCompra	ordenCompra;
	
	private	Date	dtFiltroDesde;
	private	Date	dtFiltroHasta;
	private Integer	intSucuIdSucursalFiltro;
	
	
	public CuadroComparativo(){
		id = new CuadroComparativoId();
		listaCuadroComparativoProveedor = new ArrayList<CuadroComparativoProveedor>();
	}
	
	public CuadroComparativoId getId() {
		return id;
	}
	public void setId(CuadroComparativoId id) {
		this.id = id;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntParaTipoPropuesta() {
		return intParaTipoPropuesta;
	}
	public void setIntParaTipoPropuesta(Integer intParaTipoPropuesta) {
		this.intParaTipoPropuesta = intParaTipoPropuesta;
	}
	public Integer getIntPersEmpresaRequisicion() {
		return intPersEmpresaRequisicion;
	}
	public void setIntPersEmpresaRequisicion(Integer intPersEmpresaRequisicion) {
		this.intPersEmpresaRequisicion = intPersEmpresaRequisicion;
	}
	public Integer getIntItemRequisicion() {
		return intItemRequisicion;
	}
	public void setIntItemRequisicion(Integer intItemRequisicion) {
		this.intItemRequisicion = intItemRequisicion;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public Integer getIntParaEstadoAprobacion() {
		return intParaEstadoAprobacion;
	}
	public void setIntParaEstadoAprobacion(Integer intParaEstadoAprobacion) {
		this.intParaEstadoAprobacion = intParaEstadoAprobacion;
	}
	public Timestamp getTsFechaAutoriza() {
		return tsFechaAutoriza;
	}
	public void setTsFechaAutoriza(Timestamp tsFechaAutoriza) {
		this.tsFechaAutoriza = tsFechaAutoriza;
	}
	public Integer getIntPersEmpresaAutoriza() {
		return intPersEmpresaAutoriza;
	}
	public void setIntPersEmpresaAutoriza(Integer intPersEmpresaAutoriza) {
		this.intPersEmpresaAutoriza = intPersEmpresaAutoriza;
	}
	public Integer getIntItemCuadroComparativoPropuestaAutoriza() {
		return intItemCuadroComparativoPropuestaAutoriza;
	}
	public void setIntItemCuadroComparativoPropuestaAutoriza(
			Integer intItemCuadroComparativoPropuestaAutoriza) {
		this.intItemCuadroComparativoPropuestaAutoriza = intItemCuadroComparativoPropuestaAutoriza;
	}
	public String getStrObservacionAutoriza() {
		return strObservacionAutoriza;
	}
	public void setStrObservacionAutoriza(String strObservacionAutoriza) {
		this.strObservacionAutoriza = strObservacionAutoriza;
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
	public Timestamp getTsFechaAnula() {
		return tsFechaAnula;
	}
	public void setTsFechaAnula(Timestamp tsFechaAnula) {
		this.tsFechaAnula = tsFechaAnula;
	}
	public Integer getIntPersEmpresaAnula() {
		return intPersEmpresaAnula;
	}
	public void setIntPersEmpresaAnula(Integer intPersEmpresaAnula) {
		this.intPersEmpresaAnula = intPersEmpresaAnula;
	}
	public Integer getIntPersPersonaAnula() {
		return intPersPersonaAnula;
	}
	public void setIntPersPersonaAnula(Integer intPersPersonaAnula) {
		this.intPersPersonaAnula = intPersPersonaAnula;
	}
	public Integer getIntPersPersonaAutoriza() {
		return intPersPersonaAutoriza;
	}
	public void setIntPersPersonaAutoriza(Integer intPersPersonaAutoriza) {
		this.intPersPersonaAutoriza = intPersPersonaAutoriza;
	}
	public Requisicion getRequisicion() {
		return requisicion;
	}
	public void setRequisicion(Requisicion requisicion) {
		this.requisicion = requisicion;
	}
	public List<CuadroComparativoProveedor> getListaCuadroComparativoProveedor() {
		return listaCuadroComparativoProveedor;
	}
	public void setListaCuadroComparativoProveedor(List<CuadroComparativoProveedor> listaCuadroComparativoProveedor) {
		this.listaCuadroComparativoProveedor = listaCuadroComparativoProveedor;
	}
	public Date getDtFiltroDesde() {
		return dtFiltroDesde;
	}
	public void setDtFiltroDesde(Date dtFiltroDesde) {
		this.dtFiltroDesde = dtFiltroDesde;
	}
	public Date getDtFiltroHasta() {
		return dtFiltroHasta;
	}
	public void setDtFiltroHasta(Date dtFiltroHasta) {
		this.dtFiltroHasta = dtFiltroHasta;
	}
	public CuadroComparativoProveedor getProveedorAprobado() {
		return proveedorAprobado;
	}
	public void setProveedorAprobado(CuadroComparativoProveedor proveedorAprobado) {
		this.proveedorAprobado = proveedorAprobado;
	}
	public Persona getPersonaAprueba() {
		return personaAprueba;
	}
	public void setPersonaAprueba(Persona personaAprueba) {
		this.personaAprueba = personaAprueba;
	}
	public OrdenCompra getOrdenCompra() {
		return ordenCompra;
	}
	public void setOrdenCompra(OrdenCompra ordenCompra) {
		this.ordenCompra = ordenCompra;
	}
	public Integer getIntSucuIdSucursalFiltro() {
		return intSucuIdSucursalFiltro;
	}
	public void setIntSucuIdSucursalFiltro(Integer intSucuIdSucursalFiltro) {
		this.intSucuIdSucursalFiltro = intSucuIdSucursalFiltro;
	}

	@Override
	public String toString() {
		return "CuadroComparativo [id=" + id + ", tsFechaRegistro="
				+ tsFechaRegistro + ", intParaTipoPropuesta="
				+ intParaTipoPropuesta + ", intPersEmpresaRequisicion="
				+ intPersEmpresaRequisicion + ", intItemRequisicion="
				+ intItemRequisicion + ", intParaEstado=" + intParaEstado
				+ ", intParaEstadoAprobacion=" + intParaEstadoAprobacion
				+ ", tsFechaAutoriza=" + tsFechaAutoriza
				+ ", intPersEmpresaAutoriza=" + intPersEmpresaAutoriza
				+ ", intPersPersonaAutoriza=" + intPersPersonaAutoriza
				+ ", intItemCuadroComparativoPropuestaAutoriza="
				+ intItemCuadroComparativoPropuestaAutoriza
				+ ", strObservacionAutoriza=" + strObservacionAutoriza
				+ ", intPersEmpresaUsuario=" + intPersEmpresaUsuario
				+ ", intPersPersonaUsuario=" + intPersPersonaUsuario
				+ ", tsFechaAnula=" + tsFechaAnula + ", intPersEmpresaAnula="
				+ intPersEmpresaAnula + ", intPersPersonaAnula="
				+ intPersPersonaAnula + "]";
	}	
}