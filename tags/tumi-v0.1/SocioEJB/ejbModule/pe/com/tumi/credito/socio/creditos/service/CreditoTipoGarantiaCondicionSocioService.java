package pe.com.tumi.credito.socio.creditos.service;

import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.creditos.bo.CreditoTipoGarantiaCondicionSocioBO;
import pe.com.tumi.credito.socio.creditos.domain.CondicionSocioTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CondicionSocioTipoGarantiaId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTipoGarantiaId;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
	
public class CreditoTipoGarantiaCondicionSocioService {
	
	private CreditoTipoGarantiaCondicionSocioBO boCondicionSocioTipoGarantia = (CreditoTipoGarantiaCondicionSocioBO)TumiFactory.get(CreditoTipoGarantiaCondicionSocioBO.class);
	
	public List<CondicionSocioTipoGarantia> grabarListaDinamicaCondicionSocioTipoCredito(List<CondicionSocioTipoGarantia> lstCondicionSocioTipoGarantia, CreditoTipoGarantiaId pPK) throws BusinessException{
		CondicionSocioTipoGarantia condicionSocioTipoGarantia = null;
		CondicionSocioTipoGarantiaId pk = null;
		CondicionSocioTipoGarantia condicionSocioTipoGarantiaTemp = null;
		TablaFacadeRemote tablaFacade = null;
		List<Tabla> listaTabla = new ArrayList<Tabla>();
		try{
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listaTabla = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_CONDICIONSOCIO));
			for(int i=0;i<listaTabla.size();i++){
				condicionSocioTipoGarantia = new CondicionSocioTipoGarantia();
				pk = new CondicionSocioTipoGarantiaId();
				pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
				pk.setIntParaTipoCreditoCod(pPK.getIntParaTipoCreditoCod());
				pk.setIntItemCredito(pPK.getIntItemCredito());
				pk.setIntParaTipoGarantiaCod(pPK.getIntParaTipoGarantiaCod());
				pk.setIntItemCreditoGarantia(pPK.getIntItemCreditoGarantia());
				pk.setIntItemGarantiaTipo(pPK.getIntItemGarantiaTipo());
				pk.setIntParaCondicionSocioCod(listaTabla.get(i).getIntIdDetalle());
				condicionSocioTipoGarantia.setIntValor(0);
				for(int j=0;j<lstCondicionSocioTipoGarantia.size();j++){
					if(listaTabla.get(i).getIntIdDetalle().equals(lstCondicionSocioTipoGarantia.get(j).getId().getIntParaCondicionSocioCod())){
						condicionSocioTipoGarantia.setIntValor(1);
					}
				}
				condicionSocioTipoGarantia.setId(pk);
				condicionSocioTipoGarantiaTemp = boCondicionSocioTipoGarantia.getCreditoTipoGarantiaPorPK(condicionSocioTipoGarantia.getId());
				if(condicionSocioTipoGarantiaTemp == null){
					condicionSocioTipoGarantia = boCondicionSocioTipoGarantia.grabarCreditoTipoGarantia(condicionSocioTipoGarantia);
				}else{
					condicionSocioTipoGarantia = boCondicionSocioTipoGarantia.modificarCreditoTipoGarantia(condicionSocioTipoGarantia);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstCondicionSocioTipoGarantia;
	}
}