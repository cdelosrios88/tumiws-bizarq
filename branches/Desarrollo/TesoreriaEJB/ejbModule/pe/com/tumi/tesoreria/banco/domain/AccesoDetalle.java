package pe.com.tumi.tesoreria.banco.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AccesoDetalle extends TumiDomain{

	private AccesoDetalleId id;
	private Integer 	intPersEmpresa;
	private Integer 	intItemBancoFondo;
	private Integer 	intItemBancoCuenta;
	private BigDecimal 	bdMontoFondo;
	private Integer 	intParaTipoValorAlerta;
	private BigDecimal 	bdMontoAlerta;
	private Integer 	intParaFrecuenciaTiempo;
	private Integer 	intCantidadTiempo;
	private BigDecimal 	bdMontoSobregiro;
	private Integer 	intParaEstadoEmiteCheque;
	private Integer 	intParaEstadoImprimeCheque;
	private String 		strObservacion;
	private Integer 	intParaEstado;
	private Timestamp 	tsFechaRegistro;
	private Integer 	intPersEmpresaUsuario;
	private Integer 	intPersPersonaUsuario;
	private Timestamp 	tsFechaEliminacion;
	private Integer 	intPersEmpresaElimina;
	private Integer 	intPersPersonaElimina;
	private BigDecimal 	bdMontoMaximo;
	
	private List<AccesoDetalleRes> listaAccesoDetalleRes;
	
	//interfaz
	private Bancocuenta bancoCuenta;
	private	Integer		intBancoCod;
	private Integer		intTipoAccesoDetalle;
	private Bancofondo	fondoFijo;
	private	boolean 	poseeTipoFondoFijo;
	
	public AccesoDetalle(){
		id = new AccesoDetalleId();
		listaAccesoDetalleRes = new ArrayList<AccesoDetalleRes>();
		fondoFijo = new Bancofondo();
	}

	public AccesoDetalleId getId() {
		return id;
	}

	public void setId(AccesoDetalleId id) {
		this.id = id;
	}

	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}

	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}

	public Integer getIntItemBancoFondo() {
		return intItemBancoFondo;
	}

	public void setIntItemBancoFondo(Integer intItemBancoFondo) {
		this.intItemBancoFondo = intItemBancoFondo;
	}

	public Integer getIntItemBancoCuenta() {
		return intItemBancoCuenta;
	}

	public void setIntItemBancoCuenta(Integer intItemBancoCuenta) {
		this.intItemBancoCuenta = intItemBancoCuenta;
	}

	public BigDecimal getBdMontoFondo() {
		return bdMontoFondo;
	}

	public void setBdMontoFondo(BigDecimal bdMontoFondo) {
		this.bdMontoFondo = bdMontoFondo;
	}

	public Integer getIntParaTipoValorAlerta() {
		return intParaTipoValorAlerta;
	}

	public void setIntParaTipoValorAlerta(Integer intParaTipoValorAlerta) {
		this.intParaTipoValorAlerta = intParaTipoValorAlerta;
	}

	public BigDecimal getBdMontoAlerta() {
		return bdMontoAlerta;
	}

	public void setBdMontoAlerta(BigDecimal bdMontoAlerta) {
		this.bdMontoAlerta = bdMontoAlerta;
	}

	public Integer getIntParaFrecuenciaTiempo() {
		return intParaFrecuenciaTiempo;
	}

	public void setIntParaFrecuenciaTiempo(Integer intParaFrecuenciaTiempo) {
		this.intParaFrecuenciaTiempo = intParaFrecuenciaTiempo;
	}

	public Integer getIntCantidadTiempo() {
		return intCantidadTiempo;
	}

	public void setIntCantidadTiempo(Integer intCantidadTiempo) {
		this.intCantidadTiempo = intCantidadTiempo;
	}

	public BigDecimal getBdMontoSobregiro() {
		return bdMontoSobregiro;
	}

	public void setBdMontoSobregiro(BigDecimal bdMontoSobregiro) {
		this.bdMontoSobregiro = bdMontoSobregiro;
	}

	public Integer getIntParaEstadoEmiteCheque() {
		return intParaEstadoEmiteCheque;
	}

	public void setIntParaEstadoEmiteCheque(Integer intParaEstadoEmiteCheque) {
		this.intParaEstadoEmiteCheque = intParaEstadoEmiteCheque;
	}

	public Integer getIntParaEstadoImprimeCheque() {
		return intParaEstadoImprimeCheque;
	}

	public void setIntParaEstadoImprimeCheque(Integer intParaEstadoImprimeCheque) {
		this.intParaEstadoImprimeCheque = intParaEstadoImprimeCheque;
	}

	public String getStrObservacion() {
		return strObservacion;
	}

	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}

	public Integer getIntParaEstado() {
		return intParaEstado;
	}

	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}

	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}

	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
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

	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}

	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}

	public Integer getIntPersEmpresaElimina() {
		return intPersEmpresaElimina;
	}

	public void setIntPersEmpresaElimina(Integer intPersEmpresaElimina) {
		this.intPersEmpresaElimina = intPersEmpresaElimina;
	}

	public Integer getIntPersPersonaElimina() {
		return intPersPersonaElimina;
	}

	public void setIntPersPersonaElimina(Integer intPersPersonaElimina) {
		this.intPersPersonaElimina = intPersPersonaElimina;
	}

	public BigDecimal getBdMontoMaximo() {
		return bdMontoMaximo;
	}

	public void setBdMontoMaximo(BigDecimal bdMontoMaximo) {
		this.bdMontoMaximo = bdMontoMaximo;
	}

	public Bancocuenta getBancoCuenta() {
		return bancoCuenta;
	}

	public void setBancoCuenta(Bancocuenta bancoCuenta) {
		this.bancoCuenta = bancoCuenta;
	}

	public Integer getIntBancoCod() {
		return intBancoCod;
	}

	public void setIntBancoCod(Integer intBancoCod) {
		this.intBancoCod = intBancoCod;
	}

	public Integer getIntTipoAccesoDetalle() {
		return intTipoAccesoDetalle;
	}

	public void setIntTipoAccesoDetalle(Integer intTipoAccesoDetalle) {
		this.intTipoAccesoDetalle = intTipoAccesoDetalle;
	}
	public List<AccesoDetalleRes> getListaAccesoDetalleRes() {
		return listaAccesoDetalleRes;
	}
	public void setListaAccesoDetalleRes(
			List<AccesoDetalleRes> listaAccesoDetalleRes) {
		this.listaAccesoDetalleRes = listaAccesoDetalleRes;
	}
	public Bancofondo getFondoFijo() {
		return fondoFijo;
	}
	public void setFondoFijo(Bancofondo fondoFijo) {
		this.fondoFijo = fondoFijo;
	}
	public boolean isPoseeTipoFondoFijo() {
		return poseeTipoFondoFijo;
	}
	public void setPoseeTipoFondoFijo(boolean poseeTipoFondoFijo) {
		this.poseeTipoFondoFijo = poseeTipoFondoFijo;
	}

	@Override
	public String toString() {
		return "AccesoDetalle [id=" + id + ", intPersEmpresa=" + intPersEmpresa
				+ ", intItemBancoFondo=" + intItemBancoFondo
				+ ", intItemBancoCuenta=" + intItemBancoCuenta
				+ ", bdMontoFondo=" + bdMontoFondo
				+ ", intParaTipoValorAlerta=" + intParaTipoValorAlerta
				+ ", bdMontoAlerta=" + bdMontoAlerta
				+ ", intParaFrecuenciaTiempo=" + intParaFrecuenciaTiempo
				+ ", intCantidadTiempo=" + intCantidadTiempo
				+ ", bdMontoSobregiro=" + bdMontoSobregiro
				+ ", intParaEstadoEmiteCheque=" + intParaEstadoEmiteCheque
				+ ", intParaEstadoImprimeCheque=" + intParaEstadoImprimeCheque
				+ ", strObservacion=" + strObservacion + ", intParaEstado="
				+ intParaEstado + ", tsFechaRegistro=" + tsFechaRegistro
				+ ", intPersEmpresaUsuario=" + intPersEmpresaUsuario
				+ ", intPersPersonaUsuario=" + intPersPersonaUsuario
				+ ", tsFechaEliminacion=" + tsFechaEliminacion
				+ ", intPersEmpresaElimina=" + intPersEmpresaElimina
				+ ", intPersPersonaElimina=" + intPersPersonaElimina
				+ ", bdMontoMaximo=" + bdMontoMaximo + "]";
	}


	
}
