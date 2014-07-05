package pe.com.tumi.cobranza.planilla.service;

import java.math.BigDecimal;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pe.com.tumi.cobranza.planilla.bo.DescuentoIndebidoBO;
import pe.com.tumi.cobranza.planilla.bo.EfectuadoBO;
import pe.com.tumi.cobranza.planilla.bo.EfectuadoConceptoBO;
import pe.com.tumi.cobranza.planilla.bo.EnvioconceptoBO;
import pe.com.tumi.cobranza.planilla.bo.EnviomontoBO;
import pe.com.tumi.cobranza.planilla.bo.EnvioresumenBO;
import pe.com.tumi.cobranza.planilla.domain.DescuentoIndebido;
import pe.com.tumi.cobranza.planilla.domain.Efectuado;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoConcepto;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoId;
import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;
import pe.com.tumi.cobranza.planilla.domain.Enviomonto;
import pe.com.tumi.cobranza.planilla.domain.Envioresumen;
import pe.com.tumi.cobranza.planilla.domain.EnvioresumenId;
import pe.com.tumi.cobranza.planilla.domain.composite.EnvioConceptoComp;
import pe.com.tumi.cobranza.planilla.domain.composite.ItemPlanilla;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.empresa.domain.SucursalId;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.framework.util.fecha.JFecha;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class DescuentoIndebidoService {
	
	private EfectuadoBO         boEfectuado         = (EfectuadoBO)TumiFactory.get(EfectuadoBO.class);
	private EfectuadoConceptoBO boEfectuadoConcepto = (EfectuadoConceptoBO)TumiFactory.get(EfectuadoConceptoBO.class);
	private DescuentoIndebidoBO boDescuentoIndebido = (DescuentoIndebidoBO)TumiFactory.get(DescuentoIndebidoBO.class);
	
	public List<DescuentoIndebido> getListaDescuentoIndebido(SocioComp socioComp,SocioEstructura socioEstructura) throws BusinessException{
		
		ConceptoFacadeRemote  conceptoFacade  = null;
		List<DescuentoIndebido> listDescuentoIndebido = new ArrayList<DescuentoIndebido>();
		Integer intEmpresa = null;
		Integer intCuenta = null;
		Integer intPersona = null;
		Integer intTipoModalidad = null;
		Integer intTipoEstructura = null;
		
		Integer intPeriodo = null;
		
		
		
       try{
    	    conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
    	   
    	    intEmpresa = socioComp.getCuenta().getId().getIntPersEmpresaPk();
    	    intCuenta  = socioComp.getCuenta().getId().getIntCuenta();
    	    intPersona = socioComp.getSocio().getId().getIntIdPersona();
    	    
    	    
    	    List<Movimiento> listMovimiento =  conceptoFacade.getListaMovimientoPorCtaPersonaConceptoGeneral(intEmpresa, intCuenta, intPersona, Constante.PARAM_T_TIPOCONCEPTOGENERAL_CTAXPAGAR);
    	  
             
    	    //Obtemos Unico Periodo
    	    Set<Integer> periodos = new HashSet<Integer>();
    	    
    	    for (Movimiento movimiento : listMovimiento) {
    	    	periodos.add(movimiento.getIntPeriodoPlanilla());
			}
    	    
    	    
    	      List<Movimiento> listMovimientoTemp  = listMovimiento;
    	   for (Iterator iterator = periodos.iterator(); iterator.hasNext();) {
    	    	Integer intPeriodos = (Integer) iterator.next();
    	        BigDecimal saldoMonto = obtenerSaldoDstoIndebidoPorPeriodo(intPeriodos,listMovimientoTemp);
    	      
    		          intPeriodo       = intPeriodos;
    			      intTipoModalidad = socioEstructura.getIntModalidad();
    				  EstructuraId pId = new EstructuraId();
	    			  pId.setIntCodigo(socioEstructura.getIntCodigo());
	    			  pId.setIntNivel(socioEstructura.getIntNivel());
	    			  intTipoEstructura = socioEstructura.getIntTipoEstructura();
	    			  List<Efectuado> listEfectuado =  boEfectuado.getListaEfectuadoPorIdEmpresaYPkEstructuraYTipoModalidadYPeriodo(intEmpresa, pId, intTipoModalidad,intPeriodo);
	    			  
	    			  for (Efectuado efectuado : listEfectuado) {
	    				  
	    				   if (efectuado.getIntCuentaPk().equals(intCuenta) && efectuado.getIntTipoestructuraCod().equals(intTipoEstructura)){
		    				  EfectuadoId pid = efectuado.getId();
		    				  List<EfectuadoConcepto> listaEfecConcepto =  boEfectuadoConcepto.getListaPorEfectuado(pid);
		    				 
		    				  for (EfectuadoConcepto efectuadoConcepto : listaEfecConcepto) {
		    					  List<DescuentoIndebido> listDescIndebido = boDescuentoIndebido.getListaPorEmpCptoEfeGnralyCuenta(efectuadoConcepto.getId().getIntEmpresaCuentaEnvioPk(), efectuadoConcepto.getId(), intCuenta);
		    					 
		    					  
		    					  
			    					  if(listDescIndebido == null || listDescIndebido.size() == 0){
			    						  if (saldoMonto.compareTo(new BigDecimal(0)) == 1){
				    						  DescuentoIndebido dsctoIndebido =   new DescuentoIndebido();
				    						  
				    						  dsctoIndebido.setPeriodoPlanilla(obtenerDescPeriodo(intPeriodo));
				    						  dsctoIndebido.setSocioEstructura(socioEstructura);
				    						  dsctoIndebido.setBdDeinMonto(saldoMonto);
				    						  dsctoIndebido.setIntCcobItemEfectuado(efectuadoConcepto.getId().getIntItemEfectuado());
				    						  dsctoIndebido.setIntCcobItemEfectuadoconcepto(efectuadoConcepto.getId().getIntItemEfectuadoConcepto());
				    						  
				    						  listDescuentoIndebido.add(dsctoIndebido);
			    						  }
			    					  }
			    					  else{
			    						  
			    						  
			    						  for (DescuentoIndebido descuentoIndebido : listDescIndebido) {
			    							  descuentoIndebido.setPeriodoPlanilla(obtenerDescPeriodo(intPeriodo));
			    							  descuentoIndebido.setSocioEstructura(socioEstructura);
			    							  listDescuentoIndebido.add(descuentoIndebido);
										  }  
			    					  }
			    					  
			    					  
			    					  
		    				   }
	    				    
					        }
    			 }  
		   } 
          
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listDescuentoIndebido;
	}
	
	private String obtenerDescPeriodo(Integer periodo){
		String anio = periodo.toString().substring(0,4);
		   int mes  = Integer.parseInt(periodo.toString().substring(4,periodo.toString().length()));
		String meses[] ={"xx","Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
		String desMes = "";
		for (int i = 0; i < meses.length; i++) {
			int inedice = meses[i].indexOf(i);
			if (mes == i){
				desMes =  meses[i];
				break;
			}
		}
		return anio+"-"+desMes;
	}
	
	
	private BigDecimal obtenerSaldoDstoIndebidoPorPeriodo(Integer perdiodo, List<Movimiento> lista){
		
		BigDecimal montoSaldo  = new BigDecimal(0);
		BigDecimal montoDebe   = new BigDecimal(0);
		BigDecimal montoHaber  = new BigDecimal(0);
		for (Movimiento movimiento : lista) {
			
			if (perdiodo.equals(movimiento.getIntPeriodoPlanilla())){
			    if (movimiento.getIntParaTipoCargoAbono() != null && movimiento.getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)){
				      if (movimiento.getBdMontoMovimiento() == null) movimiento.setBdMontoMovimiento(new BigDecimal(0));
					  montoHaber = montoHaber.add(movimiento.getBdMontoMovimiento());
				}
			   
				if (movimiento.getIntParaTipoCargoAbono() != null && movimiento.getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)){
					  if (movimiento.getBdMontoMovimiento() == null) movimiento.setBdMontoMovimiento(new BigDecimal(0));
					  montoDebe  = montoHaber.add(movimiento.getBdMontoMovimiento());
				}
			}
		 }
				
		montoSaldo = montoDebe.subtract(montoHaber);
		return montoSaldo;
	}
}
