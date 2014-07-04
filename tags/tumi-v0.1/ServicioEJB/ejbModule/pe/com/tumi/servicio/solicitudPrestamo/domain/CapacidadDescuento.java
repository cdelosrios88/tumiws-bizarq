package pe.com.tumi.servicio.solicitudPrestamo.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CapacidadDescuento extends TumiDomain {
	private CapacidadDescuentoId id;
	private String		strEntidad;
	private BigDecimal 	bdMonto;
	private Integer		intParaEstadoCod;
	private Timestamp	tsFechaRegistro;
	private Integer		intPersPersonaUsuarioPk;
	private Timestamp	tsFechaEliminacion;
	private Integer		intPersPersonaEliminarPk;
	
	public CapacidadDescuentoId getId() {
		return id;
	}
	public void setId(CapacidadDescuentoId id) {
		this.id = id;
	}
	public String getStrEntidad() {
		return strEntidad;
	}
	public void setStrEntidad(String strEntidad) {
		this.strEntidad = strEntidad;
	}
	public BigDecimal getBdMonto() {
		return bdMonto;
	}
	public void setBdMonto(BigDecimal bdMonto) {
		this.bdMonto = bdMonto;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntPersPersonaUsuarioPk() {
		return intPersPersonaUsuarioPk;
	}
	public void setIntPersPersonaUsuarioPk(Integer intPersPersonaUsuarioPk) {
		this.intPersPersonaUsuarioPk = intPersPersonaUsuarioPk;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
	public Integer getIntPersPersonaEliminarPk() {
		return intPersPersonaEliminarPk;
	}
	public void setIntPersPersonaEliminarPk(Integer intPersPersonaEliminarPk) {
		this.intPersPersonaEliminarPk = intPersPersonaEliminarPk;
	}
}
