package pe.com.tumi.cobranza.planilla.domain.composite;

import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.empresa.domain.SubSucursalPK;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class PlanillaAdministra extends TumiDomain{

	private List<ItemPlanilla> listaPlanilla = new ArrayList<ItemPlanilla>();
	private SubSucursalPK subSucursalPK;
	
	public List<ItemPlanilla> getListaPlanilla() {
		return listaPlanilla;
	}
	public void setListaPlanilla(List<ItemPlanilla> listaPlanilla) {
		this.listaPlanilla = listaPlanilla;
	}
	public SubSucursalPK getSubSucursalPK() {
		return subSucursalPK;
	}
	public void setSubSucursalPK(SubSucursalPK subSucursalPK) {
		this.subSucursalPK = subSucursalPK;
	}
	
}
