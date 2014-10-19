package pe.com.tumi.tesoreria.cierreLogisticaOper.domain.composite;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.tesoreria.cierreLogisticaOper.domain.CierreLogisticaOper;

public class CierreLogisticaOperComp extends TumiDomain{

	private CierreLogisticaOper cierreLogisticaOper; 
	private Tabla paraLogisticaOper;
	
	
	public Tabla getParaLogisticaOper() {
		return paraLogisticaOper;
	}
	public void setParaLogisticaOper(Tabla paraLogisticaOper) {
		this.paraLogisticaOper = paraLogisticaOper;
	}
	public CierreLogisticaOper getCierreLogisticaOper() {
		return cierreLogisticaOper;
	}
	public void setCierreLogisticaOper(CierreLogisticaOper cierreLogisticaOper) {
		this.cierreLogisticaOper = cierreLogisticaOper;
	}
}