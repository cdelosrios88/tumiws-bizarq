/* -----------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo                      Fecha            Nombre                      Descripci�n
* -----------------------------------------------------------------------------------------------------------
* REQ14-005       			  19/10/2014       Christian De los R�os       Se agrego el metodo processDailyAmount()        
*/
package pe.com.tumi.tesoreria.egreso.bo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.egreso.dao.SaldoDao;
import pe.com.tumi.tesoreria.egreso.dao.impl.SaldoDaoIbatis;
import pe.com.tumi.tesoreria.egreso.domain.Saldo;
import pe.com.tumi.tesoreria.egreso.domain.SaldoId;


public class SaldoBO{

	private SaldoDao dao = (SaldoDao)TumiFactory.get(SaldoDaoIbatis.class);

	public Saldo grabar(Saldo o) throws BusinessException{
		Saldo dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public Saldo modificar(Saldo o) throws BusinessException{
  		Saldo dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public Saldo getPorPk(SaldoId saldoId) throws BusinessException{
		Saldo domain = null;
		List<Saldo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", saldoId.getIntPersEmpresa());
			mapa.put("intSucuIdSucursal", saldoId.getIntItemSaldo());
			mapa.put("intSudeIdSucursal", saldoId.getIntItemSaldo());
			mapa.put("dtFechaSaldo", saldoId.getIntItemSaldo());
			mapa.put("intItemSaldo", saldoId.getIntItemSaldo());
			lista = dao.getListaPorPk(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtenci�n de mas de un registro coincidente");
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
	
	public Saldo getSaldoAnterior(Saldo saldo) throws BusinessException{
		Saldo domain = null;
		List<Saldo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", saldo.getId().getIntPersEmpresa());
			mapa.put("intSucuIdSucursal", saldo.getId().getIntSucuIdSucursal());
			mapa.put("intSudeIdSucursal", saldo.getId().getIntSudeIdSucursal());
			mapa.put("intEmpresaPk", saldo.getIntEmpresaPk());
			mapa.put("intItemBancoCuenta", saldo.getIntItemBancoCuenta());
			mapa.put("intItemBancoFondo", saldo.getIntItemBancoFondo());
			mapa.put("intParaTipoFondoFijo", saldo.getIntParaTipoFondoFijo());
			lista = dao.getListaAnterior(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtenci�n de mas de un registro coincidente");
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
	
	public Saldo getSaldoUltimaFechaRegistro(Integer intIdEmpresa) throws BusinessException{
		Saldo domain = null;
		List<Saldo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", intIdEmpresa);
			lista = dao.getListaUltimaFechaRegistro(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtenci�n de mas de un registro coincidente");
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
	
	public Saldo getSaldoUltimaFechaSaldo(Integer intIdEmpresa) throws BusinessException{
		Saldo domain = null;
		List<Saldo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", intIdEmpresa);
			lista = dao.getListaUltimaFechaSaldo(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtenci�n de mas de un registro coincidente");
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
	
	public List<Saldo> getListaPorBuscar(Saldo saldo) throws BusinessException{
		List<Saldo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", saldo.getId().getIntPersEmpresa());
			mapa.put("intItemBancoFondo", saldo.getIntItemBancoFondo());
			mapa.put("dtFechaDesde", saldo.getDtFechaDesde());
			mapa.put("dtFechaHasta", saldo.getDtFechaHasta());
			
			lista = dao.getListaPorBuscar(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	//Inicio: REQ14-005 - bizarq - 19/10/2014
	public List<Map> verificarSaldoProcesado(Usuario objUsuario, Date dtFechaInicioSaldo) throws BusinessException{
		List<Map> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", objUsuario.getEmpresa().getIntIdEmpresa());
			mapa.put("intSucuIdSucursal", objUsuario.getSucursal().getId().getIntIdSucursal());
			mapa.put("intSudeIdSucursal", objUsuario.getSubSucursal().getId().getIntIdSubSucursal());
			mapa.put("dtFechaSaldo", dtFechaInicioSaldo);
			lista = dao.verificarSaldoProcesado(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	//Fin: REQ14-005 - bizarq - 19/10/2014
	//Inicio: REQ14-005 - bizarq - 19/10/2014
	/**
	 * M�todo encargado de procesar los montos diarios.
	 * 
     * @author Bizarq
     * @param dtFechaInicio: Fecha inicio para el procesamiento de los montos <code>Date</code>
     * @param dtFechaFin: Fecha fin para el procesamiento de los montos <code>Date</code>
     * @param usuario: Objeto con la informacion del usuario autenticado (auditoria) <code>Usuario</code>
     * 
     * @throws BusinessException
     * */
	public Integer processDailyAmount(Date dtFechaInicio, Date dtFechaFin, Usuario usuario) throws BusinessException{
		Integer intEscalar = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("dtFechaInicio", 		dtFechaInicio);
			mapa.put("dtFechaFin",   		dtFechaFin);
			mapa.put("intIdEmpresaUsuario", usuario.getEmpresa().getIntIdEmpresa());
			mapa.put("intIdUsuario", 		usuario.getIntPersPersonaPk());
			intEscalar = dao.processDailyAmount(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return intEscalar;
	}
	//Fin: REQ14-005 - bizarq - 19/10/2014
}