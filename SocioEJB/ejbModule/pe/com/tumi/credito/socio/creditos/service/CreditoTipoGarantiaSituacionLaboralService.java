package pe.com.tumi.credito.socio.creditos.service;

import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.creditos.bo.CreditoTipoGarantiaSituacionLaboralBO;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTipoGarantiaId;
import pe.com.tumi.credito.socio.creditos.domain.SituacionLaboralTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.SituacionLaboralTipoGarantiaId;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
	
public class CreditoTipoGarantiaSituacionLaboralService {
	
	private CreditoTipoGarantiaSituacionLaboralBO boSituacionLaboralTipoGarantia = (CreditoTipoGarantiaSituacionLaboralBO)TumiFactory.get(CreditoTipoGarantiaSituacionLaboralBO.class);
	
	public List<SituacionLaboralTipoGarantia> grabarListaDinamicaCondicionHabilTipoCredito(List<SituacionLaboralTipoGarantia> lstSituacionLaboralTipoGarantia, CreditoTipoGarantiaId pPK) throws BusinessException{
		SituacionLaboralTipoGarantia situacionLaboralTipoGarantia = null;
		SituacionLaboralTipoGarantiaId pk = null;
		SituacionLaboralTipoGarantia situacionLaboralTipoGarantiaTemp = null;
		TablaFacadeRemote tablaFacade = null;
		List<Tabla> listaTabla = new ArrayList<Tabla>();
		try{
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listaTabla = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOSOCIO));
			for(int i=0;i<listaTabla.size();i++){
				situacionLaboralTipoGarantia = new SituacionLaboralTipoGarantia();
				pk = new SituacionLaboralTipoGarantiaId();
				pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
				pk.setIntParaTipoCreditoCod(pPK.getIntParaTipoCreditoCod());
				pk.setIntItemCredito(pPK.getIntItemCredito());
				pk.setIntParaTipoGarantiaCod(pPK.getIntParaTipoGarantiaCod());
				pk.setIntItemCreditoGarantia(pPK.getIntItemCreditoGarantia());
				pk.setIntItemGarantiaTipo(pPK.getIntItemGarantiaTipo());
				pk.setIntParaSituacionLaboralCod(listaTabla.get(i).getIntIdDetalle());
				situacionLaboralTipoGarantia.setIntValor(0);
				for(int j=0;j<lstSituacionLaboralTipoGarantia.size();j++){
					if(listaTabla.get(i).getIntIdDetalle().equals(lstSituacionLaboralTipoGarantia.get(j).getId().getIntParaSituacionLaboralCod())){
						situacionLaboralTipoGarantia.setIntValor(1);
					}
				}
				situacionLaboralTipoGarantia.setId(pk);
				situacionLaboralTipoGarantiaTemp = boSituacionLaboralTipoGarantia.getCreditoSituacionLaboralPorPK(situacionLaboralTipoGarantia.getId());
				if(situacionLaboralTipoGarantiaTemp == null){
					situacionLaboralTipoGarantia = boSituacionLaboralTipoGarantia.grabar(situacionLaboralTipoGarantia);
				}else{
					situacionLaboralTipoGarantia = boSituacionLaboralTipoGarantia.modificar(situacionLaboralTipoGarantia);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstSituacionLaboralTipoGarantia;
	}
}