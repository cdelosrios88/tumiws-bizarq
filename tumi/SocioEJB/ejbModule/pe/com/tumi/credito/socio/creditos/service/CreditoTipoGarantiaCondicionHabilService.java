package pe.com.tumi.credito.socio.creditos.service;

import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.creditos.bo.CreditoTipoGarantiaCondicionHabilBO;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabilTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabilTipoGarantiaId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTipoGarantiaId;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
	
public class CreditoTipoGarantiaCondicionHabilService {
	
	private CreditoTipoGarantiaCondicionHabilBO boCondicionHabilTipoGarantia = (CreditoTipoGarantiaCondicionHabilBO)TumiFactory.get(CreditoTipoGarantiaCondicionHabilBO.class);
	
	public List<CondicionHabilTipoGarantia> grabarListaDinamicaCondicionHabilTipoCredito(List<CondicionHabilTipoGarantia> lstCondicionHabilTipoGarantia, CreditoTipoGarantiaId pPK) throws BusinessException{
		CondicionHabilTipoGarantia condicionHabilTipoGarantia = null;
		CondicionHabilTipoGarantiaId pk = null;
		CondicionHabilTipoGarantia condicionHabilTipoGarantiaTemp = null;
		TablaFacadeRemote tablaFacade = null;
		List<Tabla> listaTabla = new ArrayList<Tabla>();
		try{
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listaTabla = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPO_CONDSOCIO));
			for(int i=0;i<listaTabla.size();i++){
				condicionHabilTipoGarantia = new CondicionHabilTipoGarantia();
				pk = new CondicionHabilTipoGarantiaId();
				pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
				pk.setIntParaTipoCreditoCod(pPK.getIntParaTipoCreditoCod());
				pk.setIntItemCredito(pPK.getIntItemCredito());
				pk.setIntParaTipoGarantiaCod(pPK.getIntParaTipoGarantiaCod());
				pk.setIntItemCreditoGarantia(pPK.getIntItemCreditoGarantia());
				pk.setIntItemGarantiaTipo(pPK.getIntItemGarantiaTipo());
				pk.setIntParaTipoHabilCod(listaTabla.get(i).getIntIdDetalle());
				condicionHabilTipoGarantia.setIntValor(0);
				for(int j=0;j<lstCondicionHabilTipoGarantia.size();j++){
					if(listaTabla.get(i).getIntIdDetalle().equals(lstCondicionHabilTipoGarantia.get(j).getId().getIntParaTipoHabilCod())){
						condicionHabilTipoGarantia.setIntValor(1);
					}
				}
				condicionHabilTipoGarantia.setId(pk);
				condicionHabilTipoGarantiaTemp = boCondicionHabilTipoGarantia.getCreditoTipoGarantiaPorPK(condicionHabilTipoGarantia.getId());
				if(condicionHabilTipoGarantiaTemp == null){
					condicionHabilTipoGarantia = boCondicionHabilTipoGarantia.grabarCreditoTipoGarantia(condicionHabilTipoGarantia);
				}else{
					condicionHabilTipoGarantia = boCondicionHabilTipoGarantia.modificarCreditoTipoGarantia(condicionHabilTipoGarantia);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstCondicionHabilTipoGarantia;
	}
}