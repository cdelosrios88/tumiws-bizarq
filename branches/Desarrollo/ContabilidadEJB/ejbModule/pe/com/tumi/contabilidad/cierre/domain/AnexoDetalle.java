package pe.com.tumi.contabilidad.cierre.domain;

import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AnexoDetalle extends TumiDomain implements Comparable<AnexoDetalle>{

	private AnexoDetalleId id;
	private	Integer		intPersEmpresaAnexoRef;
	private	Integer		intContPeriodoAnexoRef;
	private Integer		intParaTipoAnexoRef;
	private Integer		intAndeItemAnexoDetalleRef;
	private Integer 	intNivel;
	private Integer 	intPosicion;
	private	Integer		intItem;
	private	Integer		intNivelPadre;
	private	Integer		intPosicionPadre;
	private	Integer		intItemPadre;
	private Integer		intTipoOperacion;
	private	String		strTexto;
	private Integer		intHabilitarCuenta;
	private	List<AnexoDetalleOperador> listaAnexoDetalleOperador;
	private	List<AnexoDetalleCuenta> listaAnexoDetalleCuenta;
	
	//interfaz
	private	Integer intOrden;
	private boolean revisado;
	private String	strNumeracion;	
	private boolean habilitarConfigurar;
	private boolean habilitarVerCuentas;
	private boolean habilitarVerReferencia;
	private	AnexoDetalle	anexoDetalleReferencia;
	
	public AnexoDetalle(){
		id = new AnexoDetalleId();
		listaAnexoDetalleOperador = new ArrayList<AnexoDetalleOperador>();
		listaAnexoDetalleCuenta = new ArrayList<AnexoDetalleCuenta>();
		revisado = Boolean.FALSE;
	}
	

	public Integer getIntPersEmpresaAnexoRef() {
		return intPersEmpresaAnexoRef;
	}
	public void setIntPersEmpresaAnexoRef(Integer intPersEmpresaAnexoRef) {
		this.intPersEmpresaAnexoRef = intPersEmpresaAnexoRef;
	}
	public Integer getIntContPeriodoAnexoRef() {
		return intContPeriodoAnexoRef;
	}
	public void setIntContPeriodoAnexoRef(Integer intContPeriodoAnexoRef) {
		this.intContPeriodoAnexoRef = intContPeriodoAnexoRef;
	}
	public Integer getIntParaTipoAnexoRef() {
		return intParaTipoAnexoRef;
	}
	public void setIntParaTipoAnexoRef(Integer intParaTipoAnexoRef) {
		this.intParaTipoAnexoRef = intParaTipoAnexoRef;
	}
	public Integer getIntAndeItemAnexoDetalleRef() {
		return intAndeItemAnexoDetalleRef;
	}
	public void setIntAndeItemAnexoDetalleRef(Integer intAndeItemAnexoDetalleRef) {
		this.intAndeItemAnexoDetalleRef = intAndeItemAnexoDetalleRef;
	}
	public AnexoDetalleId getId() {
		return id;
	}
	public void setId(AnexoDetalleId id) {
		this.id = id;
	}
	public String getStrTexto() {
		return strTexto;
	}
	public void setStrTexto(String strTexto) {
		this.strTexto = strTexto;
	}
	public Integer getIntOrden() {
		return intOrden;
	}
	public void setIntOrden(Integer intOrden) {
		this.intOrden = intOrden;
	}
	public int compareTo(AnexoDetalle otro) {
        return getIntOrden().compareTo(otro.getIntOrden());
    }
	public boolean isRevisado() {
		return revisado;
	}
	public void setRevisado(boolean revisado) {
		this.revisado = revisado;
	}
	public String getStrNumeracion() {
		return strNumeracion;
	}
	public void setStrNumeracion(String strNumeracion) {
		this.strNumeracion = strNumeracion;
	}
	public Integer getIntNivelPadre() {
		return intNivelPadre;
	}
	public void setIntNivelPadre(Integer intNivelPadre) {
		this.intNivelPadre = intNivelPadre;
	}
	public Integer getIntPosicionPadre() {
		return intPosicionPadre;
	}
	public void setIntPosicionPadre(Integer intPosicionPadre) {
		this.intPosicionPadre = intPosicionPadre;
	}
	public Integer getIntItemPadre() {
		return intItemPadre;
	}
	public void setIntItemPadre(Integer intItemPadre) {
		this.intItemPadre = intItemPadre;
	}
	public Integer getIntTipoOperacion() {
		return intTipoOperacion;
	}
	public boolean isHabilitarConfigurar() {
		return habilitarConfigurar;
	}
	public void setHabilitarConfigurar(boolean habilitarConfigurar) {
		this.habilitarConfigurar = habilitarConfigurar;
	}
	public void setIntTipoOperacion(Integer intTipoOperacion) {
		this.intTipoOperacion = intTipoOperacion;
	}
	public List<AnexoDetalleOperador> getListaAnexoDetalleOperador() {
		return listaAnexoDetalleOperador;
	}
	public void setListaAnexoDetalleOperador(List<AnexoDetalleOperador> listaAnexoDetalleOperador) {
		this.listaAnexoDetalleOperador = listaAnexoDetalleOperador;
	}
	public Integer getIntHabilitarCuenta() {
		return intHabilitarCuenta;
	}
	public void setIntHabilitarCuenta(Integer intHabilitarCuenta) {
		this.intHabilitarCuenta = intHabilitarCuenta;
	}
	public List<AnexoDetalleCuenta> getListaAnexoDetalleCuenta() {
		return listaAnexoDetalleCuenta;
	}
	public void setListaAnexoDetalleCuenta(List<AnexoDetalleCuenta> listaAnexoDetalleCuenta) {
		this.listaAnexoDetalleCuenta = listaAnexoDetalleCuenta;
	}
	public boolean isHabilitarVerCuentas() {
		return habilitarVerCuentas;
	}
	public void setHabilitarVerCuentas(boolean habilitarVerCuentas) {
		this.habilitarVerCuentas = habilitarVerCuentas;
	}
	public Integer getIntNivel() {
		return intNivel;
	}
	public void setIntNivel(Integer intNivel) {
		this.intNivel = intNivel;
	}
	public Integer getIntPosicion() {
		return intPosicion;
	}
	public void setIntPosicion(Integer intPosicion) {
		this.intPosicion = intPosicion;
	}
	public Integer getIntItem() {
		return intItem;
	}
	public void setIntItem(Integer intItem) {
		this.intItem = intItem;
	}
	public AnexoDetalle getAnexoDetalleReferencia() {
		return anexoDetalleReferencia;
	}
	public void setAnexoDetalleReferencia(AnexoDetalle anexoDetalleReferencia) {
		this.anexoDetalleReferencia = anexoDetalleReferencia;
	}
	public boolean isHabilitarVerReferencia() {
		return habilitarVerReferencia;
	}
	public void setHabilitarVerReferencia(boolean habilitarVerReferencia) {
		this.habilitarVerReferencia = habilitarVerReferencia;
	}

	@Override
	public String toString() {
		return "AnexoDetalle [id=" + id + ", intNivel=" + intNivel
				+ ", intPosicion=" + intPosicion + ", intItem=" + intItem
				+ ", intNivelPadre=" + intNivelPadre + ", intPosicionPadre="
				+ intPosicionPadre + ", intItemPadre=" + intItemPadre
				+ ", intTipoOperacion=" + intTipoOperacion + ", strTexto="
				+ strTexto + ", intHabilitarCuenta=" + intHabilitarCuenta + "]";
	}

}