package pe.com.tumi.cobranza.planilla.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DescuentoIndebidoId extends TumiDomain{
	private int	intPersEmpresaDsctoindeb	;
    private	int	intCcobItemDescuentoindebido;
    
    
	public int getIntPersEmpresaDsctoindeb() {
		return intPersEmpresaDsctoindeb;
	}
	public void setIntPersEmpresaDsctoindeb(int intPersEmpresaDsctoindeb) {
		this.intPersEmpresaDsctoindeb = intPersEmpresaDsctoindeb;
	}
	public int getIntCcobItemDescuentoindebido() {
		return intCcobItemDescuentoindebido;
	}
	public void setIntCcobItemDescuentoindebido(int intCcobItemDescuentoindebido) {
		this.intCcobItemDescuentoindebido = intCcobItemDescuentoindebido;
	}
    
	
	
}
