package pe.com.tumi.servicio.prevision.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.prevision.dao.ExpedientePrevisionDao;
import pe.com.tumi.servicio.prevision.dao.impl.ExpedientePrevisionDaoIbatis;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioPrevision;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevision;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevisionId;
import pe.com.tumi.servicio.prevision.domain.composite.ExpedientePrevisionComp;
import pe.com.tumi.servicio.prevision.domain.composite.RequisitoPrevisionComp;

public class ExpedientePrevisionBO {
	
	private ExpedientePrevisionDao dao = (ExpedientePrevisionDao)TumiFactory.get(ExpedientePrevisionDaoIbatis.class);
	
	public ExpedientePrevision grabar(ExpedientePrevision o) throws BusinessException{
		ExpedientePrevision dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ExpedientePrevision modificar(ExpedientePrevision o) throws BusinessException{
		ExpedientePrevision dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ExpedientePrevision getPorPk(ExpedientePrevisionId pId) throws BusinessException{
		ExpedientePrevision domain = null;
		List<ExpedientePrevision> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", pId.getIntCuentaPk());
			mapa.put("intItemExpediente", pId.getIntItemExpediente());
			lista = dao.getListaPorPk(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}	
		}catch(DAOException e){
			System.out.println("DAOException "+e);
			throw new BusinessException(e);
		}catch(BusinessException e1){
			System.out.println("BusinessException "+e1);
			throw e1;
		}catch(Exception e2) {
			System.out.println("Exception"+e2);
			throw new BusinessException(e2);
		}
		return domain;
	}
	
	public List<ExpedientePrevision> getListaPorCuenta(Cuenta cuenta) throws BusinessException{
		List<ExpedientePrevision> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", cuenta.getId().getIntPersEmpresaPk());
			mapa.put("intCuentaPk", cuenta.getId().getIntCuenta());
			lista = dao.getListaPorCuenta(mapa);
		}catch(DAOException e){
			System.out.println("DAOExceptionDAOExceptionDAOException--> "+e);
			throw new BusinessException(e);
		}catch(Exception e1) {
			System.out.println("BusinessExceptionBusinessExceptionBusinessException--> "+e1);
			throw new BusinessException(e1);
		}
		return lista;
	}
	
	public ExpedientePrevision getPorBeneficiarioPrevision(BeneficiarioPrevision beneficiarioPrevision) throws BusinessException{
		ExpedientePrevision domain = null;
		try{
			ExpedientePrevisionId expedientePrevisionId = new ExpedientePrevisionId();
			expedientePrevisionId.setIntPersEmpresaPk(beneficiarioPrevision.getId().getIntPersEmpresaPrevision());
			expedientePrevisionId.setIntCuentaPk(beneficiarioPrevision.getId().getIntCuenta());
			expedientePrevisionId.setIntItemExpediente(beneficiarioPrevision.getId().getIntItemExpediente());
			
			domain = getPorPk(expedientePrevisionId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	
	public List<ExpedientePrevision> getListaExpedientePrevisionBusqueda() throws BusinessException{
		List<ExpedientePrevision> lista = null;
		//HashMap<String,Object> mapa = new HashMap<String,Object>();
		try{
			lista = dao.getListaExpedientePrevisionBusqueda();
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/**
	 * Consulta que recupera los expedientes de prevision, Segun filtros de busqueda.
	 * @param expPrevComp
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedientePrevision> getListaBusqPrevisionFiltros(ExpedientePrevisionComp expPrevComp) throws BusinessException{
		List<ExpedientePrevision> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intBusquedaTipo", expPrevComp.getIntBusquedaTipo());
			mapa.put("strBusqCadena", expPrevComp.getStrBusqCadena());
			mapa.put("strBusqNroSol", expPrevComp.getStrBusqNroSol());
			//mapa.put("intTipoCreditoFiltro", expPrevComp.getIntTipoCreditoFiltro());
			//mapa.put("intSubTipoCreditoFiltro", expPrevComp.getIntSubTipoCreditoFiltro());
			mapa.put("intBusqEstado", expPrevComp.getIntBusqEstado());
			mapa.put("dtBusqFechaEstadoDesde", expPrevComp.getDtBusqFechaEstadoDesde());
			mapa.put("dtBusqFechaEstadoHasta", expPrevComp.getDtBusqFechaEstadoHasta());
			mapa.put("intBusqSucursal", expPrevComp.getIntBusqSucursal());
			mapa.put("intBusqSubSucursal", expPrevComp.getIntBusqSubSucursal());
			
			lista = dao.getListaBusqPrevisionFiltros(mapa);
		}catch(DAOException e){
			System.out.println("DAOException getListaBusqPrevisionFiltros --> "+e);
			throw new BusinessException(e);
		}catch(Exception e1) {
			System.out.println("Exception getListaBusqPrevisionFiltros--> "+e1);
			throw new BusinessException(e1);
		}
		return lista;
	}
	
	/**
	 * Recupera los expedientes de prevision segun filtros de busqueda
	 * @param expPrevComp
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedientePrevision> getListaBusqExpPrevFiltros(ExpedientePrevisionComp expPrevComp) throws BusinessException{
		List<ExpedientePrevision> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intBusqTipo", expPrevComp.getIntBusquedaTipo());
			mapa.put("strBusqCadena", expPrevComp.getStrBusqCadena());
			mapa.put("strBusqNroSol", expPrevComp.getStrBusqNroSol());
			mapa.put("intBusqSucursal", expPrevComp.getIntBusqSucursal());
			mapa.put("intBusqEstado", expPrevComp.getIntBusqEstado());
			mapa.put("dtBusqFechaEstadoDesde", expPrevComp.getDtBusqFechaEstadoDesde());
			mapa.put("dtBusqFechaEstadoHasta", expPrevComp.getDtBusqFechaEstadoHasta());
			mapa.put("intBusqTipoPrevision", expPrevComp.getIntBusqTipoPrevision());
			mapa.put("intBusqSubTipoPrevision", expPrevComp.getIntBusqSubTipoPrevision());		      
			lista = dao.getListaBusqExpPrevFiltros(mapa);
		}catch(DAOException e){
			System.out.println("DAOException getListaBusqPrevisionFiltros --> "+e);
			throw new BusinessException(e);
		}catch(Exception e1) {
			System.out.println("Exception getListaBusqPrevisionFiltros--> "+e1);
			throw new BusinessException(e1);
		}
		return lista;
	}
	
	
	/**
	 * Recupera los expedientes de prevision segun filtros de busqueda
	 * @param expPrevComp
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedientePrevision> getListaBusqAutExpPrevFiltros(ExpedientePrevisionComp expPrevComp) throws BusinessException{
		List<ExpedientePrevision> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intBusqTipo", expPrevComp.getIntBusquedaTipo());
			mapa.put("strBusqCadena", expPrevComp.getStrBusqCadena());
			mapa.put("strBusqNroSol", expPrevComp.getStrBusqNroSol());
			mapa.put("intBusqSucursal", expPrevComp.getIntBusqSucursal());
			mapa.put("intBusqEstado", expPrevComp.getIntBusqEstado());
			mapa.put("dtBusqFechaEstadoDesde", expPrevComp.getDtBusqFechaEstadoDesde());
			mapa.put("dtBusqFechaEstadoHasta", expPrevComp.getDtBusqFechaEstadoHasta());
			mapa.put("intBusqTipoPrevision", expPrevComp.getIntBusqTipoPrevision());
			mapa.put("intBusqSubTipoPrevision", expPrevComp.getIntBusqSubTipoPrevision());		      
			lista = dao.getListaBusqAutExpPrevFiltros(mapa);
		}catch(DAOException e){
			System.out.println("DAOException getListaBusqAutExpPrevFiltros --> "+e);
			throw new BusinessException(e);
		}catch(Exception e1) {
			System.out.println("Exception getListaBusqAutExpPrevFiltros--> "+e1);
			throw new BusinessException(e1);
		}
		return lista;
	}
	/**
	 * JCHAVEZ 28.01.2014
	 * Recupera lista de expediente prevision y su ultimo estado credito.
	 * @param cId
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedientePrevision> getUltimoEstadoExpPrev(CuentaId cId) throws BusinessException{
		List<ExpedientePrevision> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", cId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", cId.getIntCuenta());
			lista = dao.getUltimoEstadoExpPrev(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e1) {
			throw new BusinessException(e1);
		}
		return lista;
	}	

	/**
	 * JCHAVEZ 31.01.2014
	 * Procedimiento que recupera los Requisitos necesarios para el giro en Giro REPRESTAMO
	 * Modificacion 12.05.2014 jchavez: Se agregan atributos de idmaestro y tipodescripcion
	 * @param expPrev
	 * @return
	 * @throws BusinessException
	 */
	public List<RequisitoPrevisionComp> getRequisitoGiroPrevision(ExpedientePrevision expPrev, Integer intIdMaestro, Integer intParaTipoDescripcion) throws BusinessException{
		List<RequisitoPrevisionComp> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa",			expPrev.getId().getIntPersEmpresaPk());
			mapa.put("intParaTipoOperacion", 	expPrev.getIntParaTipoOperacion());	
			mapa.put("intParaSubTipoOperacion", 		expPrev.getIntParaSubTipoOperacion());
			mapa.put("intIdMaestro", 		intIdMaestro);
			mapa.put("intParaTipoDescripcion", 		intParaTipoDescripcion);
			
			lista = dao.getRequisitoGiroPrevision(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<RequisitoPrevisionComp> getRequisitoGiroPrevisionPKrequisitoDetalle(Integer intPersEmpresaPk, Integer intItemReqAut, Integer intItemReqAutEstDetalle) throws BusinessException{
		List<RequisitoPrevisionComp> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", intPersEmpresaPk);
			mapa.put("intItemReqAut", 	intItemReqAut);	
			mapa.put("intItemReqAutDetalle", intItemReqAutEstDetalle);
			lista = dao.getRequisitoGiroPrevision(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}