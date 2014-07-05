package pe.com.tumi.credito.socio.creditos.service;

import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.creditos.bo.CreditoTipoGarantiaCondicionLaboralBO;
import pe.com.tumi.credito.socio.creditos.domain.CondicionLaboralTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CondicionLaboralTipoGarantiaId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTipoGarantiaId;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
	
public class CreditoTipoGarantiaCondicionLaboralService {
	
	private CreditoTipoGarantiaCondicionLaboralBO boCondicionLaboralTipoGarantia = (CreditoTipoGarantiaCondicionLaboralBO)TumiFactory.get(CreditoTipoGarantiaCondicionLaboralBO.class);
	
	public List<CondicionLaboralTipoGarantia> grabarListaDinamicaCondicionHabilTipoCredito(List<CondicionLaboralTipoGarantia> lstCondicionLaboralTipoGarantia, CreditoTipoGarantiaId pPK) throws BusinessException{
		CondicionLaboralTipoGarantia condicionLaboralTipoGarantia = null;
		CondicionLaboralTipoGarantiaId pk = null;
		CondicionLaboralTipoGarantia condicionLaboralTipoGarantiaTemp = null;
		TablaFacadeRemote tablaFacade = null;
		List<Tabla> listaTabla = new ArrayList<Tabla>();
		try{
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listaTabla = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_CONDICIONLABORAL));
			for(int i=0;i<listaTabla.size();i++){
				condicionLaboralTipoGarantia = new CondicionLaboralTipoGarantia();
				pk = new CondicionLaboralTipoGarantiaId();
				pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
				pk.setIntParaTipoCreditoCod(pPK.getIntParaTipoCreditoCod());
				pk.setIntItemCredito(pPK.getIntItemCredito());
				pk.setIntParaTipoGarantiaCod(pPK.getIntParaTipoGarantiaCod());
				pk.setIntItemCreditoGarantia(pPK.getIntItemCreditoGarantia());
				pk.setIntItemGarantiaTipo(pPK.getIntItemGarantiaTipo());
				pk.setIntParaCondicionLaboralCod(listaTabla.get(i).getIntIdDetalle());
				condicionLaboralTipoGarantia.setIntValor(0);
				for(int j=0;j<lstCondicionLaboralTipoGarantia.size();j++){
					if(listaTabla.get(i).getIntIdDetalle().equals(lstCondicionLaboralTipoGarantia.get(j).getId().getIntParaCondicionLaboralCod())){
						condicionLaboralTipoGarantia.setIntValor(1);
					}
				}
				condicionLaboralTipoGarantia.setId(pk);
				condicionLaboralTipoGarantiaTemp = boCondicionLaboralTipoGarantia.getCreditoCondicionLaboralPorPK(condicionLaboralTipoGarantia.getId());
				if(condicionLaboralTipoGarantiaTemp == null){
					condicionLaboralTipoGarantia = boCondicionLaboralTipoGarantia.grabar(condicionLaboralTipoGarantia);
				}else{
					condicionLaboralTipoGarantia = boCondicionLaboralTipoGarantia.modificar(condicionLaboralTipoGarantia);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstCondicionLaboralTipoGarantia;
	}
}