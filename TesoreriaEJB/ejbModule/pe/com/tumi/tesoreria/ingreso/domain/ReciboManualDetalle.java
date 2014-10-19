package pe.com.tumi.tesoreria.ingreso.domain;

import java.sql.Timestamp;
import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Natural;

@SuppressWarnings("serial")
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
	private String	strDesSucursal;
	//inicio Autor : jchavez 	/ Tarea : nuevos campos	/ 17.09.2014
	private Integer intPersonaRegistro;
	private Timestamp	tsFechaRegistro;
	//fin jchavez - 17.09.2014
	//inicio Autor : jbermudez 	/ Tarea : nuevos campos	/ 17.09.2014
	private String	strEstadoRecibo;
	private Natural	usuario;
	private Ingreso ingreso;
	private Natural	gestor;
	private ReciboManual	reciboManual;
	//fin jbermudez - 17.09.2014

	public ReciboManualDetalle(){
		id = new ReciboManualDetalleId();
		//inicio Autor : jbermudez 	/ Tarea : agregado	/ 17.09.2014
		ingreso = new Ingreso();
		gestor = new Natural();
		reciboManual = new ReciboManual();
		usuario = new Natural();
		//fin jbermudez - 17.09.2014
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

	public String getStrEstadoRecibo() {
		return strEstadoRecibo;
	}

	public void setStrEstadoRecibo(String strEstadoRecibo) {
		this.strEstadoRecibo = strEstadoRecibo;
	}

	public Integer getIntPersonaRegistro() {
		return intPersonaRegistro;
	}

	public void setIntPersonaRegistro(Integer intPersonaRegistro) {
		this.intPersonaRegistro = intPersonaRegistro;
	}

	public String getStrDesSucursal() {
		return strDesSucursal;
	}

	public void setStrDesSucursal(String strDesSucursal) {
		this.strDesSucursal = strDesSucursal;
	}

	public Natural getUsuario() {
		return usuario;
	}

	public void setUsuario(Natural usuario) {
		this.usuario = usuario;
	}

	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}

	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
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

	public Date getDtFechaAnula() {
		return dtFechaAnula;
	}

	public void setDtFechaAnula(Date dtFechaAnula) {
		this.dtFechaAnula = dtFechaAnula;
	}

	public String getStrObservacionAnula() {
		return strObservacionAnula;
	}

	public void setStrObservacionAnula(String strObservacionAnula) {
		this.strObservacionAnula = strObservacionAnula;
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
				+ intPersPersonaGestor + ", intParaTipoEnlace="
				+ intParaTipoEnlace + ", intParaTipoDesenlace="
				+ intParaTipoDesenlace + ", strDesSucursal=" + strDesSucursal
				+ ", intPersonaRegistro=" + intPersonaRegistro
				+ ", tsFechaRegistro=" + tsFechaRegistro + ", strEstadoRecibo="
				+ strEstadoRecibo + ", usuario=" + usuario + ", ingreso="
				+ ingreso + ", gestor=" + gestor + ", reciboManual="
				+ reciboManual + "]";
	}
}