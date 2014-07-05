package pe.com.tumi.servicio.prevision.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;

public class FallecidoPrevision extends TumiDomain{
		private FallecidoPrevisionId id;
		private Integer intPersPersonaFallecido;
		private Integer intItemViculo;
		private Integer intParaEstado;
		
		private Persona persona;
		private String 	strEtiqueta;
		
		private Integer intTipoViculo;
		
		//AUTOR Y FECHA CREACION: JCHAVEZ / 24-09-2013
		private String strNomApeFallecido;
		
		//autor y fecha rVillarreal / 18/06/2014
		
		private String strApePaterno;
		private String strApeMaterno;
		private String strNombre;
		private String strNomApeBeneficiario;
		private Integer intItemVinculoCod;
		private Integer intTipoVinculoCod;
		
		public FallecidoPrevisionId getId() {
			return id;
		}
		public void setId(FallecidoPrevisionId id) {
			this.id = id;
		}
		public Integer getIntItemViculo() {
			return intItemViculo;
		}
		public void setIntItemViculo(Integer intItemViculo) {
			this.intItemViculo = intItemViculo;
		}
		public Integer getIntParaEstado() {
			return intParaEstado;
		}
		public void setIntParaEstado(Integer intParaEstado) {
			this.intParaEstado = intParaEstado;
		}
		public Integer getIntPersPersonaFallecido() {
			return intPersPersonaFallecido;
		}
		public void setIntPersPersonaFallecido(Integer intPersPersonaFallecido) {
			this.intPersPersonaFallecido = intPersPersonaFallecido;
		}
		public Persona getPersona() {
			return persona;
		}
		public void setPersona(Persona persona) {
			this.persona = persona;
		}
		public String getStrEtiqueta() {
			return strEtiqueta;
		}
		public void setStrEtiqueta(String strEtiqueta) {
			this.strEtiqueta = strEtiqueta;
		}
		public Integer getIntTipoViculo() {
			return intTipoViculo;
		}
		public void setIntTipoViculo(Integer intTipoViculo) {
			this.intTipoViculo = intTipoViculo;
		}
		public String getStrNomApeFallecido() {
			return strNomApeFallecido;
		}
		public void setStrNomApeFallecido(String strNomApeFallecido) {
			this.strNomApeFallecido = strNomApeFallecido;
		}
		public String getStrApePaterno() {
			return strApePaterno;
		}
		public void setStrApePaterno(String strApePaterno) {
			this.strApePaterno = strApePaterno;
		}
		public String getStrApeMaterno() {
			return strApeMaterno;
		}
		public void setStrApeMaterno(String strApeMaterno) {
			this.strApeMaterno = strApeMaterno;
		}
		public String getStrNombre() {
			return strNombre;
		}
		public void setStrNombre(String strNombre) {
			this.strNombre = strNombre;
		}
		public Integer getIntItemVinculoCod() {
			return intItemVinculoCod;
		}
		public void setIntItemVinculoCod(Integer intItemVinculoCod) {
			this.intItemVinculoCod = intItemVinculoCod;
		}
		public Integer getIntTipoVinculoCod() {
			return intTipoVinculoCod;
		}
		public void setIntTipoVinculoCod(Integer intTipoVinculoCod) {
			this.intTipoVinculoCod = intTipoVinculoCod;
		}
		public String getStrNomApeBeneficiario() {
			return strNomApeBeneficiario;
		}
		public void setStrNomApeBeneficiario(String strNomApeBeneficiario) {
			this.strNomApeBeneficiario = strNomApeBeneficiario;
		}
}