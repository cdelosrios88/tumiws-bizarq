package pe.com.tumi.credito.socio.estadoCuenta.facade;
import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaSoc;
import pe.com.tumi.cobranza.planilla.domain.EnviadoEfectuadoComp;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.composite.CuentaComp;
import pe.com.tumi.credito.socio.core.domain.SocioPK;
import pe.com.tumi.credito.socio.estadoCuenta.domain.composite.MovimientoEstCtaComp;
import pe.com.tumi.credito.socio.estadoCuenta.domain.composite.PrevisionSocialComp;
import pe.com.tumi.credito.socio.estructura.domain.Descuento;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.movimiento.concepto.domain.ConceptoPago;
import pe.com.tumi.movimiento.concepto.domain.composite.ExpedienteComp;
import pe.com.tumi.servicio.prevision.domain.composite.ExpedientePrevisionComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.ExpedienteCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.GarantiaCreditoComp;

@Local
public interface EstadoCtaFacadeLocal {
	public List<CuentaComp> getLsCtasPorPkSocioYTipCta(SocioPK pPK,Integer intParaTipoCuenta, Integer intParaSituacionCuentaCod, Integer intCuenta) throws BusinessException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 21.08.2013 
	public List<Descuento> getListaColumnasPorPeriodoModalidadYDni(Integer intPeriodo, Integer intParaModalidadCod, String strLibEle) throws BusinessException;
	public List<Descuento> getListaFilasPorPeriodoModalidadYDni(Integer intPeriodo, Integer intParaModalidadCod, String strLibEle) throws BusinessException;
	public List<Descuento> getMontoTotalPorNomCptoYPeriodo(String strDsteCpto, String strNomCpto, Integer intPeriodo, Integer intMes, Integer intParaModalidadCod, String strLibEle) throws BusinessException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 06.09.2013 
	public List<GarantiaCreditoComp> getListaGarantiaCreditoCompPorEmpPersCta(GarantiaCredito gCred) throws BusinessException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 10.09.2013
	public List<EnviadoEfectuadoComp> getListaEnviadoEfectuado(CuentaComp cuentaComp, Integer intPeriodo) throws BusinessException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 17.09.2013
	public List<ExpedienteCreditoComp> getListaPrestamosRechazadosPorCuenta (CuentaComp cuentaComp, Integer intTipoCredito) throws BusinessException;
	public List<ExpedienteComp> getListaPrestamosPorCuenta (CuentaComp cuentaComp, Integer intEstadoSolicPrestamo) throws BusinessException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 23.09.2013
	public List<ExpedientePrevisionComp> getListaExpedientePrevisionComp (CuentaComp cuentaComp) throws BusinessException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 26.09.2013
	public List<PrevisionSocialComp> getListaPrevisionSocialComp (CuentaComp cuentaComp) throws BusinessException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 27.09.2013
	public List<MovimientoEstCtaComp> getListaMovimiento (List<ConceptoPago> listaConceptoPago) throws BusinessException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 02.10.2013
	public List<MovimientoEstCtaComp> getListaMovimientoFdoRetiroInteres (CuentaComp cuentaComp) throws BusinessException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 05.10.2013
	public List<GestionCobranzaSoc> getListaGestionCobranzaSoc(CuentaId cId, String strFechaBusqueda) throws BusinessException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 29.10.2013
	public List<CuentaComp> getCtaIntYCtaXSocTipYSitCta(SocioPK pPK,Integer intCuenta,Integer intParaTipoCuenta, Integer intParaSituacionCuentaCod) throws BusinessException;
}
