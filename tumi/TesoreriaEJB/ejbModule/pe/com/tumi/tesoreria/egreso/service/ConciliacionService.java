package pe.com.tumi.tesoreria.egreso.service;


import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.egreso.bo.EgresoBO;
import pe.com.tumi.tesoreria.egreso.domain.Conciliacion;
import pe.com.tumi.tesoreria.egreso.domain.ConciliacionDetalle;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.ingreso.bo.IngresoBO;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;


public class ConciliacionService {
	
	protected static Logger log = Logger.getLogger(ConciliacionService.class);
	
	IngresoBO boIngreso = (IngresoBO)TumiFactory.get(IngresoBO.class);
	EgresoBO boEgreso = (EgresoBO)TumiFactory.get(EgresoBO.class);
	
	
	public List<ConciliacionDetalle> buscarRegistrosConciliacion(Conciliacion conciliacion)throws BusinessException{
		List<ConciliacionDetalle> listaConciliacionDetalle = new ArrayList<ConciliacionDetalle>();
		try{
			Ingreso ingresoFiltro = new Ingreso();
			ingresoFiltro.getId().setIntIdEmpresa((conciliacion.getBancoCuenta().getId().getIntEmpresaPk()));
			ingresoFiltro.setIntParaDocumentoGeneral(conciliacion.getIntParaDocumentoGeneralFiltro());
			ingresoFiltro.setIntItemBancoFondo((conciliacion.getBancoCuenta().getId().getIntItembancofondo()));
			ingresoFiltro.setIntItemBancoCuenta(conciliacion.getBancoCuenta().getId().getIntItembancocuenta());
			List<Ingreso> listaIngreso = boIngreso.getListaParaBuscar(ingresoFiltro);
			for(Ingreso ingreso : listaIngreso){
				
			}
			
			Egreso egresoFiltro = new Egreso();
			egresoFiltro.getId().setIntPersEmpresaEgreso(conciliacion.getBancoCuenta().getId().getIntEmpresaPk());
			egresoFiltro.setIntParaDocumentoGeneral(conciliacion.getIntParaDocumentoGeneralFiltro());
			egresoFiltro.setIntItemBancoFondo((conciliacion.getBancoCuenta().getId().getIntItembancofondo()));
			egresoFiltro.setIntItemBancoCuenta(conciliacion.getBancoCuenta().getId().getIntItembancocuenta());
			List<Egreso> listaEgreso = boEgreso.getListaPorBuscar(egresoFiltro, null, null);
			
		}catch (Exception e) {
			throw new BusinessException(e);
		}
		return listaConciliacionDetalle;
	}
}