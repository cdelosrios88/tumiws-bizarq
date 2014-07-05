package pe.com.tumi.cobranza.planilla.domain;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Envioinflada extends TumiDomain{
		private EnvioinfladaId id;
     	private java.lang.Integer intTipoinfladaCod;
     	private java.math.BigDecimal bdMonto;
     	private java.lang.String strObservacion;
     	private java.lang.Integer intEstadoCod;
     	private java.sql.Timestamp tsFecharegistro;
     	private java.lang.Integer intEmpresausuarioPk;
     	private java.lang.Integer intPersonausuarioPk;
     	private Enviomonto enviomonto;
     	private java.lang.Integer intModalidad;
		
		public EnvioinfladaId getId(){
			return this.id;
		}
		public void setId(EnvioinfladaId id){
			this.id = id;
		}
		
		public java.lang.Integer getIntTipoinfladaCod(){
			return this.intTipoinfladaCod;
		}
		public void setIntTipoinfladaCod(java.lang.Integer intTipoinfladaCod){
			this.intTipoinfladaCod = intTipoinfladaCod;
		}
		
		public java.math.BigDecimal getBdMonto(){
			return this.bdMonto;
		}
		public void setBdMonto(java.math.BigDecimal bdMonto){
			this.bdMonto = bdMonto;
		}
		
		public java.lang.String getStrObservacion(){
			return this.strObservacion;
		}
		public void setStrObservacion(java.lang.String strObservacion){
			this.strObservacion = strObservacion;
		}
		
		public java.lang.Integer getIntEstadoCod(){
			return this.intEstadoCod;
		}
		public void setIntEstadoCod(java.lang.Integer intEstadoCod){
			this.intEstadoCod = intEstadoCod;
		}
		
		public java.sql.Timestamp getTsFecharegistro(){
			return this.tsFecharegistro;
		}
		public void setTsFecharegistro(java.sql.Timestamp tsFecharegistro){
			this.tsFecharegistro = tsFecharegistro;
		}
		
		public java.lang.Integer getIntEmpresausuarioPk(){
			return this.intEmpresausuarioPk;
		}
		public void setIntEmpresausuarioPk(java.lang.Integer intEmpresausuarioPk){
			this.intEmpresausuarioPk = intEmpresausuarioPk;
		}
		
		public java.lang.Integer getIntPersonausuarioPk(){
			return this.intPersonausuarioPk;
		}
		public void setIntPersonausuarioPk(java.lang.Integer intPersonausuarioPk){
			this.intPersonausuarioPk = intPersonausuarioPk;
		}
		
     	public Enviomonto getEnviomonto(){
			return this.enviomonto;
		}
		public void setEnviomonto(Enviomonto enviomonto){
			this.enviomonto = enviomonto;
		}
		public java.lang.Integer getIntModalidad() {
			return intModalidad;
		}
		public void setIntModalidad(java.lang.Integer intModalidad) {
			this.intModalidad = intModalidad;
		}
		
		@Override
		public String toString() {
			return "Envioinflada [bdMonto=" + bdMonto + ", enviomonto="
					+ enviomonto + ", intModalidad=" + intModalidad + "]";
		}
	
}
	