package pe.com.tumi.tesoreria.cierreLogisticaOper.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.cierreLogisticaOper.bo.CierreLogisticaOperBo;
import pe.com.tumi.tesoreria.cierreLogisticaOper.domain.CierreLogisticaOper;
import pe.com.tumi.tesoreria.cierreLogisticaOper.domain.composite.CierreLogistica;

public class CierreLogisticaOperService {
	CierreLogisticaOperBo boCierreLogisticaOperBO = (CierreLogisticaOperBo)TumiFactory.get(CierreLogisticaOperBo.class);
	
	public CierreLogisticaOper grabarCierreLogistica(CierreLogisticaOper o) throws Exception{
		CierreLogisticaOper domain = null;
		
		domain = boCierreLogisticaOperBO.grabarCierreLogistica(o);
		
		return domain;
		}
	
	public CierreLogisticaOper grabarCierreLogis(CierreLogistica o) throws Exception{
		CierreLogisticaOper domain = null;
			if(o.getListaCierreLogistica()!=null && o.getListaCierreLogistica().size()>0){
				grabarListaDinamicaLogistica(o.getListaCierreLogistica());
			}
		return domain;
		}
	
	public CierreLogisticaOper modificarCierreLogis(CierreLogistica o) throws Exception{
		CierreLogisticaOper domain = null;
			if(o.getListaCierreLogistica()!=null && o.getListaCierreLogistica().size()>0){
				grabarListaDinamicaLogistica(o.getListaCierreLogistica());
			}
		return domain;
		}
	
	public List<CierreLogisticaOper> grabarListaDinamicaLogistica(List<CierreLogisticaOper> listCierreLogistica) throws BusinessException{
		CierreLogisticaOper cierreLogisticaOperTemp=null;
		try {
			for (CierreLogisticaOper cierreLogisticaOper : listCierreLogistica) {
				cierreLogisticaOperTemp = boCierreLogisticaOperBO.getListaCierreLogisticaValidar(cierreLogisticaOper.getId());
			if(cierreLogisticaOperTemp==null){	
				if(!cierreLogisticaOper.getIntParaEstadoCierreCod().equals(0)){
					cierreLogisticaOper.setTsCicoFechaRegistro(new Timestamp(new Date().getTime()));
				}
				boCierreLogisticaOperBO.grabarCierreLogistica(cierreLogisticaOper);
			}else{
				if(!cierreLogisticaOper.getIntParaEstadoCierreCod().equals(cierreLogisticaOperTemp.getIntParaEstadoCierreCod())){
					cierreLogisticaOper.setTsCicoFechaRegistro(new Timestamp(new Date().getTime()));
				}else{
					cierreLogisticaOper.setTsCicoFechaRegistro(cierreLogisticaOperTemp.getTsCicoFechaRegistro());
				}
				boCierreLogisticaOperBO.modificarCierreLogistica(cierreLogisticaOper);
			}
		}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return listCierreLogistica;
	}
}
