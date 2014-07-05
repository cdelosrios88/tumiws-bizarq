package pe.com.tumi.contabilidad.core.facade;
import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleCuenta;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.seguridad.login.domain.Usuario;

@Local
public interface PlanCuentaFacadeLocal {
	public PlanCuenta grabarPlanCuenta(PlanCuenta o)throws BusinessException;
	public PlanCuenta modificarPlanCuenta(PlanCuenta o)throws BusinessException;
	public PlanCuenta getPlanCuentaPorPk(PlanCuentaId pId) throws BusinessException;
	public List<PlanCuenta> getListaPlanCuentaBusqueda(PlanCuenta pPlanCuenta) throws BusinessException;
	public List<PlanCuenta> findListCuentaOperacional(PlanCuenta pPlanCuenta) throws BusinessException;
	public List<Integer> getListaPeriodos() throws BusinessException;
	public List<PlanCuenta> getListaPlanCuentaPorEmpresaCuentaYPeriodoCuenta(Integer intEmpresaCuenta, Integer intPeriodoCuenta)
	throws BusinessException;
	public List<PlanCuenta> getListaPlanCuentaBusqueda(PlanCuenta planCuentaFiltro, Usuario usuario, Integer intIdTransaccion) 
	throws BusinessException;
	public boolean validarMontoPlanCuenta(PlanCuenta planCuenta, BigDecimal bdMontoValidar) throws BusinessException;
	//Agregado por cdelosrios, 16/09/2013
    public List<AnexoDetalleCuenta> getListaAnexoDetalleCuentaPorPlanCuenta(PlanCuenta o) throws BusinessException;
    //Fin agregado por cdelosrios, 16/09/2013
    //AUTOR Y FECHA CREACION: JCHAVEZ / 23.10.2013
    public List<PlanCuenta> getPlanCtaPorPeriodoBase(Integer intPeriodoCuenta, Integer intEmpresaCuentaPk) throws BusinessException;
    //AUTOR Y FECHA CREACION: JCHAVEZ / 23.10.2013
    public List<PlanCuenta> getBusqPorNroCtaDesc(PlanCuenta pCta) throws BusinessException;
}