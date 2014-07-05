package pe.com.tumi.popup.dao.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pe.com.tumi.adminCuenta.domain.PersonaJuridica;
import pe.com.tumi.common.dao.impl.GenericDaoIbatis;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.empresa.domain.PerNatural;
import pe.com.tumi.empresa.domain.RepresentanteLegal;
import pe.com.tumi.popup.dao.PopupDao;
import pe.com.tumi.popup.domain.Beneficiario;
import pe.com.tumi.popup.domain.Comunicacion;
import pe.com.tumi.popup.domain.CuentaBancaria;
import pe.com.tumi.popup.domain.Domicilio;
import pe.com.tumi.popup.domain.VinculoBeneficio;

public class PopupDaoIbatis extends SqlMapClientDaoSupport implements PopupDao {
	private String nameSpace;
	private String prefixColumnName;
	private String idTable;
	protected static Logger log = Logger.getLogger(GenericDaoIbatis.class);
	
	public String getNameSpace() {
		return nameSpace;
	}
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}
	public String getPrefixColumnName() {
		return prefixColumnName;
	}
	public void setPrefixColumnName(String prefixColumnName) {
		this.prefixColumnName = prefixColumnName;
	}
	public String getIdTable() {
		return idTable;
	}
	public void setIdTable(String idTable) {
		this.idTable = idTable;
	}
	
	//Métodos de Popup's
	public ArrayList listarPersonaNatural(Object prmtBusq) throws DaoException, ParseException {
		log.info("Entrando al método GenericDaoIbatis.listarPersonaNatural()");
		HashMap prmtBusqueda = (HashMap)prmtBusq;
		log.info("Parametros de Búsqueda de Formulario y Reportes:");
		log.info("-------------------------------------");
		log.info("pIntIdPersona: "	+prmtBusqueda.get("pIntIdPersona"));
		log.info("pIntTipoDoc: "		+prmtBusqueda.get("pIntTipoDoc"));
		log.info("pStrNroDoc: " 		+prmtBusqueda.get("pStrNroDoc"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPersonaNatural", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaPersonaNatural, prmtBusqueda)");
		return rs;
	}
	
	public void grabarBeneficiario(Object o) throws DaoException, ParseException{
		log.info("-----------------Debugging grabarBeneficiario-------------------");
	    Beneficiario ben = (Beneficiario) o;
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarBeneficiario(Object o)");
		log.info("--------------------Tabla PER_PERSONA----------------------------");
		log.info("ben.getIntIdPersona() = "     + ben.getIntIdPersona());
		log.info("ben.getStrApPaterno() = "	   	+ ben.getStrApPaterno());
		log.info("ben.getStrApMaterno() = "  	+ ben.getStrApMaterno());
		log.info("ben.getStrNombres() = "       + ben.getStrNombres());
		log.info("ben.getStrFecNac() = "  	   	+ ben.getStrFecNac());
		log.info("ben.getIntTipoDocumento() = "	+ ben.getIntTipoDocumento());
		log.info("ben.getIntNroDoc() = "	   	+ ben.getStrNroDoc());
		log.info("ben.getIntIdSexo() = " 	   	+ ben.getIntIdSexo());
		log.info("ben.getIntEstCivil() = " 	   	+ ben.getIntEstCivil());
		log.info("ben.getStrTieneEmpresa() = " 	+ ben.getStrTieneEmpresa());
		log.info("ben.getStrFoto() = "    		+ ben.getStrFoto());
		log.info("ben.getStrFirma() = " 	   	+ ben.getStrFirma());
		log.info("ben.getIntIdTipoVinculo() = " + ben.getIntIdTipoVinculo());
		log.info("ben.getIntIdEmpresaBeneficiario() = " + ben.getIntIdEmpresaBeneficiario());
		log.info("ben.getIntIdEmpresaSocio() = "+ ben.getIntIdEmpresaSocio());
		log.info("ben.getIntIdPersonaSocio() = "+ ben.getIntIdPersonaSocio());
		
		HashMap map = new HashMap();
		// Tabla PER_M_NATURAL 
		map.put("pIntIdBeneficiario", 	ben.getIntIdPersona());
		map.put("pStrApepat", 			ben.getStrApPaterno());
		map.put("pStrApemat", 			ben.getStrApMaterno());
		map.put("pStrNombres",			ben.getStrNombres());
		map.put("pDaFecNac", 			ben.getStrFecNac());
		map.put("pIntIdTipoDoc",		ben.getIntTipoDocumento());
		map.put("pIntNroDoc", 			ben.getStrNroDoc());
		map.put("pIntIdSexo", 			ben.getIntIdSexo());
		map.put("pIntIdEstCivil",		ben.getIntEstCivil());
		map.put("pStrFoto",				ben.getStrFoto());
		map.put("pStrFirma",			ben.getStrFirma());
		map.put("pIntIdTipoVinculo",	ben.getIntIdTipoVinculo());
		map.put("pIntIdEmpresaBenef",	ben.getIntIdEmpresaBeneficiario());
		map.put("pIntIdEmpresaSocio",	ben.getIntIdEmpresaSocio());
		map.put("pIntIdPersonaSocio",	ben.getIntIdPersonaSocio());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarBeneficiario", map);
			log.info("pIntIdBeneficiario: " + map.get("pIntIdBeneficiario"));
			Integer intIdPersonaBenef = (Integer) map.get("pIntIdBeneficiario");
			log.info("intIdPersonaBenef: "+intIdPersonaBenef);
			ben.setIntIdPersona(intIdPersonaBenef);
			log.info("Se invocó al procedure *.grabarBeneficiario");
		}catch (Exception e){
			log.info("ERROR EN  grabarBeneficiario GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public void grabarVinculoBeneficio(Object o) throws DaoException, ParseException{
		log.info("----------------Debugging grabarVinculoBeneficio-----------------");
		VinculoBeneficio vinc = (VinculoBeneficio) o;
		log.info("PARAMETROS DE ENTRDA ... GenericDaoIbatis.grabarVinculoBeneficio(Object o)");
		log.info("--------------------Tabla PER_M_VINBENEFICIOS----------------------");
		log.info("vinc.getIntIdEmpresaBeneficiario() = "    + vinc.getIntIdEmpresaBeneficiario());
		log.info("vinc.getIntIdPersonaBeneficiario() = "	+ vinc.getIntIdPersonaBeneficiario());
		log.info("vinc.getIntIdTipoVinculo() = "  			+ vinc.getIntIdTipoVinculo());
		log.info("vinc.getIntIdEmpresaVinculo() = "       	+ vinc.getIntIdEmpresaVinculo());
		log.info("vinc.getIntIdPersonaVinculo() = "  	   	+ vinc.getIntIdPersonaVinculo());
		log.info("vinc.getIntIdTipoBeneficio() = "			+ vinc.getIntIdTipoBeneficio());
		log.info("vinc.getFlPorcentaje() = "				+ vinc.getFlPorcentaje());
		
		HashMap map = new HashMap();
		// Tabla PER_M_VINBENEFICIOS
		map.put("pIntIdEmpresaBeneficiario", 	vinc.getIntIdEmpresaBeneficiario());
		map.put("pIntIdPersonaBeneficiario", 	vinc.getIntIdPersonaBeneficiario());
		map.put("pIntIdTipoVinculo", 			vinc.getIntIdTipoVinculo());
		map.put("pIntIdEmpresaVinculo",			vinc.getIntIdEmpresaVinculo());
		map.put("pIntIdPersonaVinculo",			vinc.getIntIdPersonaVinculo());
		map.put("pIntIdTipoBeneficio",			vinc.getIntIdTipoBeneficio());
		map.put("pFlPorcentaje",				vinc.getFlPorcentaje());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarVinculoBeneficio", map);
			log.info("Se invocó al procedure *.grabarVinculoBeneficio");
		}catch (Exception e){
			log.info("ERROR EN grabarVinculoBeneficio GenericDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarDomicilio(Object prmtBusqDomicilio) throws DaoException{
		log.info("Entrando al método listarDomicilio()");
		HashMap prmtBusqueda = (HashMap)prmtBusqDomicilio;
		log.info("Parametros de Busqueda de Dirección: ");
		log.info("-------------------------------------");
		log.info("pIntIdPersona = "+prmtBusqueda.get("pIntIdPersona"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaDomicilio", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaDomicilio, prmtBusqueda)");
		return rs;
	}
	
	public void grabarDomicilio(Object o) throws DaoException, ParseException{
		log.info("-----------------Debugging grabarDomicilio-------------------");
		Domicilio dom = new Domicilio();
		dom=(Domicilio)o;
		
		log.info("PARAMETROS DE ENTRDA ... PopupDaoIbatis.grabarDomicilio(Object o)");
		log.info("--------------------Tabla PER_DOMICILIO----------------------------");
		log.info("dom.getIntIdPersona() = "        + dom.getIntIdPersona());
		log.info("dom.getIntIdTipoDomicilio() = "  + dom.getIntIdTipoDomicilio());
		log.info("dom.getIntIdTipoDireccion() = "  + dom.getIntIdTipoDireccion());
		log.info("dom.getIntIdTipoVivienda() = "   + dom.getIntIdTipoVivienda());
		log.info("dom.getIntIdTipoVia() = " 	   + dom.getIntIdTipoVia());
		log.info("dom.getStrNombreVia() = "   	   + dom.getStrNombreVia());
		log.info("dom.getIntInterior() = "   	   + dom.getIntInterior());
		log.info("dom.getIntIdTipoZona() = "   	   + dom.getIntIdTipoZona());
		log.info("dom.getStrNombreZona() = "   	   + dom.getStrNombreZona());
		log.info("dom.getStrReferencia() = "   	   + dom.getStrReferencia());
		log.info("dom.getIntIdDepartamento() = "   + dom.getIntIdDepartamento());
		log.info("dom.getIntIdProvincia() = "      + dom.getIntIdProvincia());
		log.info("dom.getIntIdDistrito() = "   	   + dom.getIntIdDistrito());
		log.info("dom.getFgCroquis() = "   	   	   + (dom.isFgCroquis()==true?"1":"0"));
		dom.setIntFgCroquis(dom.isFgCroquis()==true?1:0);
		log.info("dom.isFgCorrespondencia() = "    + (dom.isFgCorrespondencia()==true?"1":"0"));
		dom.setIntFgCorrespondencia(dom.isFgCorrespondencia()==true?1:0);
		log.info("dom.getStrObservacion() = "      + dom.getStrObservacion());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarDomicilio", dom);
			log.info("Se invocó al procedure *.grabarDomicilio");
		}catch (Exception e){
			log.info("ERROR EN  grabarDomicilio PopupDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarComunicacion(Object prmtBusqComunicacion) throws DaoException{
		log.info("Entrando al método listarComunicacion()");
		HashMap prmtBusqueda = (HashMap)prmtBusqComunicacion;
		log.info("Parametros de Busqueda de Comunicación: ");
		log.info("-------------------------------------");
		log.info("pIntIdPersona 	 = "+prmtBusqueda.get("pIntIdPersona"));
		log.info("pIntIdComunicacion = "+prmtBusqueda.get("pIntIdComunicacion"));
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaComunicacion", prmtBusqueda);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaComunicacion, prmtBusqueda)");
		return rs;
	}
	
	public void grabarComunicacion(Object o) throws DaoException, ParseException {
		log.info("-----------------Debugging grabarComunicacion-------------------");
	    Comunicacion com = new Comunicacion();
		com = (Comunicacion)o;
		log.info("PARAMETROS DE ENTRDA ... PopupDaoIbatis.grabarComunicacion(Object o)");
		log.info("--------------------Tabla PER_COMUNICACION----------------------------");
		log.info("usu.getIntIdPersona() = "        	   	+ com.getIntIdPersona());
		log.info("com.getIntIdComunicacion() = "       	+ com.getIntIdComunicacion());
		log.info("com.getIntIdTipoComunicacion() = "   	+ com.getIntIdTipoComunicacion());
		log.info("com.getIntIdSubTipoComunicacion()= " 	+ com.getIntIdSubTipoComunicacion());
		log.info("com.getIntIdTipoLinea() = "          	+ com.getIntIdTipoLinea());
		log.info("com.getIntNro() = " 	   	    	   	+ com.getIntNro());
		log.info("com.getIntAnexo() = "   	    	   	+ com.getIntAnexo());
		log.info("com.getStrDescripcion() = "    	   	+ com.getStrDescripcion());
		log.info("com.getChkCasoEmerg() = "	    	   	+ (com.getChkCasoEmerg()==null?0:(com.getChkCasoEmerg()==true?1:0))); //(com.getChkCasoEmerg()==false||com.getChkCasoEmerg()==null?0:1));
		com.setIntCasoEmerg(com.getChkCasoEmerg()==null?0:(com.getChkCasoEmerg()==true?1:0));
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarComunicacion", com);
			log.info("Se invocó al procedure *.grabarComunicacion");
		}catch (Exception e){
			log.info("ERROR EN  grabarUsuario PopupDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public void eliminarPersonaDet(Object prmtPersonaDet)throws DaoException {
	    HashMap map = new HashMap();
	    map = (HashMap)prmtPersonaDet;
	    try
	    {
	        getSqlMapClientTemplate().delete((new StringBuilder(String.valueOf(getNameSpace()))).append(".eliminarPersonaDet").toString(), map);
	        log.info("Se invocó al procedure *.eliminarPersonaDet");
	    }
	    catch(Exception e)
	    {
	        log.info((new StringBuilder("ERROR EN .eliminarPersonaDet GenericDaoIbatis ")).append(e.getMessage()).toString());
	        throw new DaoException(e);
	    }
	}
	
	public void grabarCtaBancaria(Object beanCtaBancaria) throws DaoException {
		log.info("-----------------Debugging grabarCtaBancaria-------------------");
	    CuentaBancaria cta = new CuentaBancaria();
		cta = (CuentaBancaria)beanCtaBancaria;
		log.info("PARAMETROS DE ENTRDA ... PopupDaoIbatis.grabarCtaBancaria()");
		log.info("--------------------Tabla PER_COMUNICACION----------------------------");
		log.info("intIdPersona: " + cta.getIntIdPersona());
		log.info("intIdCtaBancaria: " + cta.getIntIdCtaBancaria());
		log.info("intIdBanco: " + cta.getIntIdBanco());
		log.info("intIdMoneda: " + cta.getIntIdTipoCuenta());
		log.info("intIdMoneda: " + cta.getIntIdMoneda());
		log.info("strNroCtaBancaria: " + cta.getStrNroCtaBancaria());
		log.info("intAbono: " + cta.getIntAbono());
		log.info("intDepositaCTS: " + cta.getIntDepositaCTS());
		log.info("intCargos: " + cta.getIntCargos());
		log.info("intIdEstado: " + cta.getIntIdEstado());
		log.info("strObservacion: " + cta.getStrObservacion());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarCtaBancaria", cta);
			log.info("Se invocó al procedure *.grabarCtaBancaria");
		}catch (Exception e){
			log.info("ERROR EN  grabarCtaBancaria PopupDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public ArrayList listarCtaBancaria(Object cta) throws DaoException {
		log.info("Entrando al método listarCtaBancaria()");
		CuentaBancaria ctabancaria = (CuentaBancaria)cta;
		log.info("Parametros de Busqueda de Cuenta Bancaria: ");
		log.info("-------------------------------------");
		log.info("intIdPersona = "+ctabancaria.getIntIdPersona());
		log.info("intIdCtaBancaria = "+ctabancaria.getIntIdCtaBancaria());
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaCtaBancaria", ctabancaria);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaCtaBancaria, prmtBusqueda)");
		return rs;
	}
	
	public ArrayList listarRepLegal(Object replegal) throws DaoException {
		log.info("Entrando al método listarRepLegal()");
		RepresentanteLegal repleg = (RepresentanteLegal)replegal;
		log.info("Parametros de Busqueda de Representante Legal: ");
		log.info("-------------------------------------");
		log.info("intTipoVinculo: "+repleg.getIntTipoVinculo());
		log.info("intTipoPersona: "+repleg.getIntTipoPersona());
		log.info("intTipoDocIdentidad: "+repleg.getIntTipoDocIdentidad());
		log.info("strNroDocIdentidad: "+repleg.getStrNroDocIdentidad());
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaRepLegal", repleg);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaRepLegal, prmtBusqueda)");
		return rs;
	}
	
	public void grabarRepLegal(Object repLegal) throws DaoException {
		log.info("-----------------Debugging grabarRepLegal-------------------");
	    RepresentanteLegal replegal = new RepresentanteLegal();
		replegal = (RepresentanteLegal)repLegal;
		log.info("PARAMETROS DE ENTRDA ...");
		log.info("--------------------Tabla PER_M_NATURAL----------------------------");
		log.info("intIdEmpresa: "+replegal.getIntIdEmpresa());
		log.info("intIdPerJuridica: "+replegal.getIntIdPerJuridica());
		log.info("intIdPersona: "+replegal.getIntIdPersona());
		log.info("strApePat: "+replegal.getStrApePat());
		log.info("strApeMat: "+replegal.getStrApeMat());
		log.info("strNombres: "+replegal.getStrNombres());
		log.info("strFechaNac: "+replegal.getStrFechaNac());
		log.info("intTipoDocIdentidad: "+replegal.getIntTipoDocIdentidad());
		log.info("strNroDocIdentidad: "+replegal.getStrNroDocIdentidad());
		log.info("intSexo: "+replegal.getIntSexo());
		log.info("intEstadoCivil: "+replegal.getIntEstadoCivil());
		log.info("intTipoPersona: "+replegal.getIntTipoPersona());
		log.info("intTipoVinculo: "+replegal.getIntTipoVinculo());
		log.info("strPathFoto: "+replegal.getStrPathFoto());
		log.info("strPathFirma: "+replegal.getStrPathFirma());
		log.info("intEstado: "+replegal.getIntEstado());
		
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarRepLegal", replegal);
			log.info("Se invocó al procedure *.grabarRepLegal");
		}catch (Exception e){
			log.info("ERROR EN  grabarRepLegal PopupDaoIbatis " + e.getMessage());
			throw new DaoException(e);
		}
	}
	
	public ArrayList getRolesPersona(Object replegal) throws DaoException {
		log.info("Entrando al método getRolesPersona()");
		RepresentanteLegal repleg = (RepresentanteLegal)replegal;
		log.info("Parametros de Busqueda de Roles: ");
		log.info("---------------------------------");
		log.info("intIdEmpresa: "+repleg.getIntIdEmpresa());
		log.info("intTipoPersona: "+repleg.getIntTipoPersona());
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getRolesPersona", repleg);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getRolesPersona, prmtBusqueda)");
		return rs;
	}
	
	public ArrayList listarPerNaturalVinculo(Object perjuri)
			throws DaoException {
		log.info("Entrando al método listarPerNaturalVinculo()");
		PersonaJuridica perjuridica = (PersonaJuridica) perjuri;
		log.info("Parametros de Busqueda de Representante Legal: ");
		log.info("-------------------------------------");
		log.info("intTipoVinculo: "+perjuridica.getIntIdPersona());
		
		ArrayList rs = new ArrayList();
		log.info("Hashmap seteado.");
		rs = (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPerNaturalVinculo", perjuridica);
		log.info("Ejecutado correctamente: (ArrayList) getSqlMapClientTemplate().queryForList(getNameSpace() + .getListaPerNaturalVinculo, prmtBusqueda)");
		return rs;
	}
	
}
