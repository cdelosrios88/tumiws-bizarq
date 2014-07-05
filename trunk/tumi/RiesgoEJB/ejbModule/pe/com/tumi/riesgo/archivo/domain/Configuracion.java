package pe.com.tumi.riesgo.archivo.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Natural;

public class Configuracion extends TumiDomain{

	private ConfiguracionId id;
	private Integer intParaTipoConfiguracionCod;
	private Integer intParaFormatoArchivoCod;
	private Integer intParaEstadoCod;
	private Integer intPersPersonaUsuarioPk;
	private Timestamp tsFechaRegistro;
	private Integer intPersPersonaEliminaPk;
	private Timestamp tsFechaEliminacion;
	private List<Nombre> listaNombre;
	private List<ConfDetalle> listaConfDetalle;
	private ConfEstructura confEstructura;
	private Natural natural;
	
	public Configuracion(){
		super();
		id = new ConfiguracionId();
		natural = new Natural();
	}
	
	public ConfiguracionId getId() {
		return id;
	}
	public void setId(ConfiguracionId id) {
		this.id = id;
	}
	public Integer getIntParaTipoConfiguracionCod() {
		return intParaTipoConfiguracionCod;
	}
	public void setIntParaTipoConfiguracionCod(Integer intParaTipoConfiguracionCod) {
		this.intParaTipoConfiguracionCod = intParaTipoConfiguracionCod;
	}
	public Integer getIntParaFormatoArchivoCod() {
		return intParaFormatoArchivoCod;
	}
	public void setIntParaFormatoArchivoCod(Integer intParaFormatoArchivoCod) {
		this.intParaFormatoArchivoCod = intParaFormatoArchivoCod;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public Integer getIntPersPersonaUsuarioPk() {
		return intPersPersonaUsuarioPk;
	}
	public void setIntPersPersonaUsuarioPk(Integer intPersPersonaUsuarioPk) {
		this.intPersPersonaUsuarioPk = intPersPersonaUsuarioPk;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}	
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntPersPersonaEliminaPk() {
		return intPersPersonaEliminaPk;
	}
	public void setIntPersPersonaEliminaPk(Integer intPersPersonaEliminaPk) {
		this.intPersPersonaEliminaPk = intPersPersonaEliminaPk;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
	public List<Nombre> getListaNombre() {
		return listaNombre;
	}
	public void setListaNombre(List<Nombre> listaNombre) {
		this.listaNombre = listaNombre;
	}
	public List<ConfDetalle> getListaConfDetalle() {
		return listaConfDetalle;
	}
	public void setListaConfDetalle(List<ConfDetalle> listaConfDetalle) {
		this.listaConfDetalle = listaConfDetalle;
	}
	public ConfEstructura getConfEstructura() {
		return confEstructura;
	}
	public void setConfEstructura(ConfEstructura confEstructura) {
		this.confEstructura = confEstructura;
	}
	public Natural getNatural() {
		return natural;
	}
	public void setNatural(Natural natural) {
		this.natural = natural;
	}

	@Override
	public String toString() {
		return "Configuracion [id=" + id + ", intParaTipoConfiguracionCod="
				+ intParaTipoConfiguracionCod + ", intParaFormatoArchivoCod="
				+ intParaFormatoArchivoCod + ", intParaEstadoCod="
				+ intParaEstadoCod + ", intPersPersonaUsuarioPk="
				+ intPersPersonaUsuarioPk + ", tsFechaRegistro="
				+ tsFechaRegistro 
				+ ", confEstructura=" + confEstructura + "]";
	}

}