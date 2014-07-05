package pe.com.tumi.credito.socio.estructura.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import pe.com.tumi.credito.socio.captacion.domain.EstructuraCaptacion;
import pe.com.tumi.credito.socio.convenio.domain.Adenda;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ConvenioEstructuraDetalle extends TumiDomain {

	private ConvenioEstructuraDetalleId id;
	private EstructuraDetalle estructuraDetalle;
	private Adenda adenda;
	private Integer intSocio;
	private BigDecimal bdDescuento;
	private BigDecimal bdMorosidad;
	private Integer intParaEstadoCod;
	private BigDecimal bdIndiceDescuento;
	private List<EstructuraCaptacion> listaEstructuraCaptacion;
	
	public ConvenioEstructuraDetalleId getId() {
		return id;
	}
	public void setId(ConvenioEstructuraDetalleId id) {
		this.id = id;
	}
	public EstructuraDetalle getEstructuraDetalle() {
		return estructuraDetalle;
	}
	public void setEstructuraDetalle(EstructuraDetalle estructuraDetalle) {
		this.estructuraDetalle = estructuraDetalle;
	}
	public Adenda getAdenda() {
		return adenda;
	}
	public void setAdenda(Adenda adenda) {
		this.adenda = adenda;
	}
	public Integer getIntSocio() {
		return intSocio;
	}
	public void setIntSocio(Integer intSocio) {
		this.intSocio = intSocio;
	}
	public BigDecimal getBdDescuento() {
		return bdDescuento;
	}
	public void setBdDescuento(BigDecimal bdDescuento) {
		this.bdDescuento = bdDescuento;
	}
	public BigDecimal getBdMorosidad() {
		return bdMorosidad;
	}
	public void setBdMorosidad(BigDecimal bdMorosidad) {
		this.bdMorosidad = bdMorosidad;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public List<EstructuraCaptacion> getListaEstructuraCaptacion() {
		return listaEstructuraCaptacion;
	}
	public void setListaEstructuraCaptacion(
			List<EstructuraCaptacion> listaEstructuraCaptacion) {
		this.listaEstructuraCaptacion = listaEstructuraCaptacion;
	}
	public BigDecimal getBdIndiceDescuento() {
		return bdIndiceDescuento;
	}
	public void setBdIndiceDescuento(BigDecimal bdIndiceDescuento) {
		this.bdIndiceDescuento = bdIndiceDescuento;
	}
}
