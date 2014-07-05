package pe.com.tumi.credito.socio.estructura.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.credito.socio.estructura.dao.DescuentoDao;
import pe.com.tumi.credito.socio.estructura.dao.impl.DescuentoDaoIbatis;
import pe.com.tumi.credito.socio.estructura.domain.AdminPadron;
import pe.com.tumi.credito.socio.estructura.domain.Descuento;
import pe.com.tumi.credito.socio.estructura.domain.DescuentoId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class DescuentoBO {
	
	private DescuentoDao dao = (DescuentoDao)TumiFactory.get(DescuentoDaoIbatis.class);
	protected static Logger log = Logger.getLogger(DescuentoBO.class);
	
	public Descuento grabarDescuento(Descuento o) throws BusinessException{
		//log.info("BO:"+o.toString());
		Descuento dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Descuento modificarDescuento(Descuento o) throws BusinessException{
		Descuento dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Descuento getDescuentoPorPK(DescuentoId pPK) throws BusinessException{
		Descuento domain = null;
		List<Descuento> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPeriodo", pPK.getIntPeriodo());
			mapa.put("intMes", pPK.getIntMes());
			mapa.put("intNivel", pPK.getIntNivel());
			mapa.put("intCodigo", pPK.getIntCodigo());
			mapa.put("intParaTipoArchivoCod", pPK.getIntParaTipoArchivoPadronCod());
			mapa.put("intParaModalidad", pPK.getIntParaModalidadCod());
			mapa.put("intParaTipoSocio", pPK.getIntParaTipoSocio());
			mapa.put("intItemAdministraPadron", pPK.getIntItemAdministraPadron());
			mapa.put("intItemDescuento", pPK.getIntItemDescuento());
			lista = dao.getListaDescuentoPorPK(mapa);
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
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 21-08-2013
	 * OBTENER COLUMNAS DESCUENTO TERCEROS (ESTADO DE CUENTA - TAB TERCEROS)
	 **/
	public List<Descuento> getListaColumnasPorPeriodoModalidadYDni(Integer intPeriodo, Integer intParaModalidadCod, String strLibEle) throws BusinessException{
		List<Descuento> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPeriodo", intPeriodo);
			mapa.put("intParaModalidadCod", intParaModalidadCod);
			mapa.put("strLibEle", 			strLibEle);
			lista = dao.getListaColumnasPorPeriodoModalidadYDni(mapa);	
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}		
		return lista;
	}
	
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 21-08-2013
	 * OBTENER FILAS DESCUENTO TERCEROS (ESTADO DE CUENTA - TAB TERCEROS)
	 **/
	public List<Descuento> getListaFilasPorPeriodoModalidadYDni(Integer intPeriodo, Integer intParaModalidadCod, String strLibEle) throws BusinessException{
		List<Descuento> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPeriodo", intPeriodo);
			mapa.put("intParaModalidadCod", intParaModalidadCod);
			mapa.put("strLibEle", 			strLibEle);
			lista = dao.getListaFilasPorPeriodoModalidadYDni(mapa);	
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}		
		return lista;
	}
	
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 26-08-2013
	 * OBTENER MONTOS TOTALES POR EMPRESA-DESCUENTA Y PERIODO (ESTADO DE CUENTA - TAB TERCEROS)
	 */
	public List<Descuento> getMontoTotalPorNomCptoYPeriodo(String strDsteCpto,String strNomCpto,Integer intPeriodo, Integer intMes, Integer intParaModalidadCod, String strLibEle) throws BusinessException{
		List<Descuento> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("strDsteCpto", strDsteCpto);
			mapa.put("strNomCpto", strNomCpto);
			mapa.put("intPeriodo", intPeriodo);
			mapa.put("intMes", intMes);
			mapa.put("intParaModalidadCod", intParaModalidadCod);
			mapa.put("strLibEle", 			strLibEle);
			lista = dao.getMontoTotalPorNomCptoYPeriodo(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}	
		return lista;
	}	
	
	/**
	 * Recupera lista de desceuntos x admin padron
	 * @param administraPadron
	 * @param strDni
	 * @return
	 * @throws BusinessException
	 */
	public List<Descuento> getListaPorAdminPadron(AdminPadron administraPadron, String strDni) throws BusinessException{
		List<Descuento> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPeriodo", administraPadron.getId().getIntPeriodo());
			mapa.put("intMes", administraPadron.getId().getIntMes());
			mapa.put("intNivel", administraPadron.getId().getIntNivel());
			mapa.put("intCodigo", administraPadron.getId().getIntCodigo());
			mapa.put("intParaTipoArchivoPadronCod", administraPadron.getId().getIntParaTipoArchivoPadronCod());
			mapa.put("intParaModalidadCod", administraPadron.getId().getIntParaModalidadCod());
			mapa.put("intParaTipoSocio", administraPadron.getId().getIntParaTipoSocioCod());
			mapa.put("intItemAdministraPadron", administraPadron.getId().getIntItemAdministraPadron());
			mapa.put("strLibEle", strDni);
			lista = dao.getListaPorAdminPadron(mapa);	
		}catch(DAOException e){
			e.printStackTrace();
			throw new BusinessException(e);
		}catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return lista;
	}
	
		
}
