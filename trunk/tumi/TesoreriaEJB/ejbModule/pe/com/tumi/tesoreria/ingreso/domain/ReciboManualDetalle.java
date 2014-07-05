package pe.com.tumi.tesoreria.ingreso.domain;

import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.seguridad.domain.Usuario;

public class ReciboManualDetalle extends TumiDomain{

	private ReciboManualDetalleId	id;
	private Integer	intNumeroRecibo;
	private Integer	intPersEmpresaIngreso;
	private Integer	intItemIngresoGeneral;
	private Integer	intParaEstado;
	private	Integer	intPersEmpresaAnula;
	private Integer	intPersPersonaAnula;
	private Date	dtFechaAnula;
	private	String	strObservacionAnula;
	private	Integer	intPersPersonaGestor;
	private Integer intParaTipoEnlace;
	private Integer intParaTipoDesenlace;
	private Ingreso ingreso;
	private Natural gestor;
	private String  strDesSucursal;
	private ReciboManual reciboManual;
	
	
	public ReciboManualDetalle(){
		id = new ReciboManualDetalleId();
	}
	
	public ReciboManualDetalleId getId() {
		return id;
	}
	public void setId(ReciboManualDetalleId id) {
		this.id = id;
	}
	public Integer getIntNumeroRecibo() {
		return intNumeroRecibo;
	}
	public void setIntNumeroRecibo(Integer intNumeroRecibo) {
		this.intNumeroRecibo = intNumeroRecibo;
	}
	public Integer getIntPersEmpresaIngreso() {
		return intPersEmpresaIngreso;
	}
	public void setIntPersEmpresaIngreso(Integer intPersEmpresaIngreso) {
		this.intPersEmpresaIngreso = intPersEmpresaIngreso;
	}
	public Integer getIntItemIngresoGeneral() {
		return intItemIngresoGeneral;
	}
	public void setIntItemIngresoGeneral(Integer intItemIngresoGeneral) {
		this.intItemIngresoGeneral = intItemIngresoGeneral;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public Date getDtFechaAnula() {
		return dtFechaAnula;
	}
	public void setDtFechaAnula(Date dtFechaAnula) {
		this.dtFechaAnula = dtFechaAnula;
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
	public String getStrObservacionAnula() {
		return strObservacionAnula;
	}
	public void setStrObservacionAnula(String strObservacionAnula) {
		this.strObservacionAnula = strObservacionAnula;
	}
	public Integer getIntPersPersonaGestor() {
		return intPersPersonaGestor;
	}
	public void setIntPersPersonaGestor(Integer intPersPersonaGestor) {
		this.intPersPersonaGestor = intPersPersonaGestor;
	}
	
	public Ingreso getIngreso() {
		return ingreso;
	}

	public void setIngreso(Ingreso ingreso) {
		this.ingreso = ingreso;
	}
	

	public Natural getGestor() {
		return gestor;
	}

	public void setGestor(Natural gestor) {
		this.gestor = gestor;
	}
	
    public ReciboManual getReciboManual() {
		return reciboManual;
	}

	public void setReciboManual(ReciboManual reciboManual) {
		this.reciboManual = reciboManual;
	}

	public String getStrDesSucursal() {
		return strDesSucursal;
	}

	public void setStrDesSucursal(String strDesSucursal) {
		this.strDesSucursal = strDesSucursal;
	}
	
		

	public Integer getIntParaTipoEnlace() {
		return intParaTipoEnlace;
	}

	public void setIntParaTipoEnlace(Integer intParaTipoEnlace) {
		this.intParaTipoEnlace = intParaTipoEnlace;
	}

	
	public Integer getIntParaTipoDesenlace() {
		return intParaTipoDesenlace;
	}

	public void setIntParaTipoDesenlace(Integer intParaTipoDesenlace) {
		this.intParaTipoDesenlace = intParaTipoDesenlace;
	}

	@Override
	public String toString() {
		return "ReciboManualDetalle [id=" + id + ", intNumeroRecibo="
				+ intNumeroRecibo + ", intPersEmpresaIngreso="
				+ intPersEmpresaIngreso + ", intItemIngresoGeneral="
				+ intItemIngresoGeneral + ", intParaEstado=" + intParaEstado
				+ ", intPersEmpresaAnula=" + intPersEmpresaAnula
				+ ", intPersPersonaAnula=" + intPersPersonaAnula
				+ ", dtFechaAnula=" + dtFechaAnula + ", strObservacionAnula="
				+ strObservacionAnula + ", intPersPersonaGestor="
				+ intPersPersonaGestor + "]";
	}
	
	
}