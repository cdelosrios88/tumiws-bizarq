package pe.com.tumi.credito.popup;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.persona.contacto.domain.Comunicacion;
import pe.com.tumi.persona.contacto.domain.ComunicacionPK;
import pe.com.tumi.persona.contacto.facade.ContactoFacadeRemote;

public class ComunicacionController {
	protected   static Logger 		    	log = Logger.getLogger(ComunicacionController.class);
	private 	Comunicacion				beanComunicacion = null;
	private 	List<Comunicacion>			beanListComunicacion = null;
	private 	List<Comunicacion>			beanListComuniPerJuri = null;
	private 	List<Comunicacion>			beanListComuniRepLegal = null;
	private 	List<Comunicacion>			listComuniContactoNatu = null;
	private 	List<Comunicacion>			listComuniSocioNatu = null;
	private 	List<Comunicacion>			listComuniSocioNatuEmp = null;
	private 	List<Comunicacion>			listComuniBeneficiario = null;
	private 	Boolean						chkDescripcion;
	private 	Boolean						chkCasoEmerg;
	private 	String 						strInsertUpdate;
	private 	String 						strIdModalPanel; 
	private 	String 						pgListComunicacion;
	private 	Boolean						enableDisableFormComunicacion = false;
	private 	Boolean						enableDisableDescComunicacion = true;
	private 	Boolean						renderEmail = false;
	private 	Boolean						renderWebSite = false;
	private 	Boolean						renderTelefono = false;
	private 	String 						strCallingFormId;
	private 	String 						strFormIdJuriConve;
	private 	String 						strFormIdRepLegal;
	private 	String 						strFormIdContactoNatu;
	private 	String 						strFormIdSocioNatu;
	private 	String 						strFormIdSocioNatuEmp;
	private 	String 						strFormIdBeneficiario;
	private 	ArrayList<Comunicacion>		arrayComunicacion = new ArrayList<Comunicacion>();
	private 	String 						strEmail;
	private 	String 						strWeb;
	
	
	public ComunicacionController(){
		strIdModalPanel = "pAgregarComunicacion"; 
		pgListComunicacion = "divFormSocioNatu,pgListComunicacion,tbComunicaciones,divComunicacionRepLegal,divComuniContactoNatu,pBeneficiario";
		
		strFormIdJuriConve = "frmPerJuriConve";
		strFormIdRepLegal = "frmRepLegal";
		strFormIdContactoNatu = "frmContactoNatu";
		strFormIdSocioNatu = "frmSocioNatural";
		strFormIdSocioNatuEmp = "frmSocioEmpresa";
		strFormIdBeneficiario = "frmBeneficiario";
		
		beanComunicacion = new Comunicacion();
		beanComunicacion.setId(new ComunicacionPK());
		beanListComunicacion = new ArrayList<Comunicacion>();
		beanListComuniPerJuri = new ArrayList<Comunicacion>();
		beanListComuniRepLegal = new ArrayList<Comunicacion>();
		listComuniSocioNatu = new ArrayList<Comunicacion>();
		listComuniSocioNatuEmp = new ArrayList<Comunicacion>();
		listComuniBeneficiario = new ArrayList<Comunicacion>();
		
		setStrEmail("");
		setStrWeb("");
	}
	
	// ---------------------------------------------------
	// Métodos de ComunicacionController
	// ---------------------------------------------------
	public void showComuniPerJuridica(ActionEvent event){
		log.info("--------------------Debugging ComunicacionController.agregarComunicacion-------------------");
		limpiarFormComunicacion();
		setStrCallingFormId(strFormIdJuriConve);
	}
	
	public void showComuniRepLegal(ActionEvent event){
		log.info("--------------------Debugging ComunicacionController.agregarComunicacion-------------------");
		limpiarFormComunicacion();
		setStrCallingFormId(strFormIdRepLegal);
	}
	
	public void showComuniContactoNatu(ActionEvent event){
		log.info("--------------------Debugging ComunicacionController.showComuniContactoNatu-------------------");
		limpiarFormComunicacion();
		setStrCallingFormId(strFormIdContactoNatu);
	}
	
	public void showComuniSocioNatu(ActionEvent event){
		log.info("--------------------Debugging ComunicacionController.agregarComunicacion-------------------");
		limpiarFormComunicacion();
		setStrCallingFormId(strFormIdSocioNatu);
	}
	
	public void showComuniSocioNatuEmp(ActionEvent event){
		log.info("--------------------Debugging ComunicacionController.showComuniSocioNatuEmp-------------------");
		limpiarFormComunicacion();
		setStrCallingFormId(strFormIdSocioNatuEmp);
	}
	
	public void showComuniBeneficiario(ActionEvent event){
		log.info("--------------------Debugging ComunicacionController.showComuniBeneficiario-------------------");
		limpiarFormComunicacion();
		setStrCallingFormId(strFormIdBeneficiario);
	}
	
	public void limpiarFormComunicacion(){
		log.info("--------------------Debugging ComunicacionController.limpiarFormComunicacion-------------------");
		//Limpiar el bean comunicacion
		Comunicacion comu = new Comunicacion();
		setBeanComunicacion(comu);
		setChkDescripcion(false);
		setChkCasoEmerg(false);
	}
	
	public void limpiarArrayComunicacion(){
		log.info("--------------------Debugging ComunicacionController.limpiarArrayComunicacion-------------------");
		if(arrayComunicacion!=null){
			arrayComunicacion.clear();
		}
	}
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  listarComunicacion()           						*/
	/*                                                    	 		*/
	/*  Parametros. :  Integer intIdPersona               	 		*/
	/*                 intIdPersona = el Id de la persona a la cual */ 
	/*                 se listará el o los Comunicacions que tenga     */
	/*                 registrados                         	 		*/
	/*  Objetivo: Grabar un Nuevo Comunicacion de acuerdo al Id de la  */
	/*            Persona                                  	 		*/
	/*  Retorno : Nuevo Comunicacion generado correctamente	 		*/
	/**************************************************************/
	public void listarComunicacion(Integer intIdPersona, Integer intIdComunicacion){
		log.info("-----------------------Debugging ComunicacionController.listarComunicacion-----------------------------");
	    log.info("Se ha seteado el Service");
	    log.info("Seteando los parametros de busqueda de comunicación: ");
		log.info("-----------------------------------------------------");
		log.info("pIntIdpersona = "+ intIdPersona);
		
	    List<Comunicacion> listaComunicacion = null;
	    
		try {
    		ContactoFacadeRemote contactoFacade = (ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);
    		listaComunicacion = contactoFacade.getListaComunicacion(intIdPersona);
    		log.info("listaComunicacion.size: "+listaComunicacion.size());
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		setMessageSuccess("Listado de Comunicacions ha sido satisfactorio");
		
		for(int i=0; i<arrayComunicacion.size(); i++){
			Comunicacion com = (Comunicacion) arrayComunicacion.get(i);
			log.info("COMU_IDPERSONA_N   			= " +com.getId().getIntIdPersona());
			log.info("COMU_IDCOMUNICACION_N 		= " +com.getId().getIntIdComunicacion());
			log.info("COMU_IDTIPOCOMUNICACION_N 	= " +com.getIntTipoComunicacionCod());
			log.info("COMU_IDSUBTIPOCOMUNICACION_N 	= " +com.getIntSubTipoComunicacionCod());
			log.info("COMU_IDTIPOLINEA_N 			= " +com.getIntTipoLineaCod());
			log.info("COMU_NUMERO_N 				= " +com.getIntNumero());
			log.info("COMU_ANEXO_N 					= " +com.getIntAnexo());
			log.info("COMU_DESCRIPCION_C 			= " +com.getStrDescripcion());
			log.info("COMU_CASOEMERGENCIA_N 		= " +com.getIntCasoEmergencia());
			log.info("COMU_IDESTADO_N 				= " +com.getIntEstadoCod());
			com.setStrDato("Tipo Línea: " + (com.getIntTipoComunicacionCod() == 1? "Teléfono" : "Fax" ) + " - Nro.: " + com.getIntNumero());
			
			listaComunicacion.add(com);
		}
		
		log.info("getBeanListComunicacion().size(): "+getBeanListComunicacion().size());
		setBeanListComunicacion(listaComunicacion);
	}
	
	/**************************************************************/
	/*                                                    	 					*/
	/*  Nombre :  grabarComunicacion()           								*/
	/*                                                    	 					*/
	/*  Parametros. :  Integer intIdPersona               	 					*/
	/*                 intIdPersona = el Id de la persona a la cual				*/ 
	/*                 se le agregará un nuevo Comunicacion            			*/
	/*                                                    	 					*/
	/*  Objetivo: Grabar un Nuevo Comunicacion de acuerdo al Id de la Persona 	*/
	/*  Retorno : Nuevo Comunicacion generado correctamente	 		*/
	/**************************************************************/
	public void grabarComunicacion(ActionEvent event){
	  log.info("-----------------------Debugging ComunicacionController.grabarComunicacion-----------------------------");
	  System.out.println("beanComunicacion.intTipoComunicacionCod: "+getBeanComunicacion().getIntTipoComunicacionCod());
	  System.out.println("beanComunicacion.intSubTipoComunicacionCod: "+getBeanComunicacion().getIntSubTipoComunicacionCod());
	  System.out.println("beanComunicacion.intTipoLineaCod: "+getBeanComunicacion().getIntTipoLineaCod());
	  System.out.println("beanComunicacion.intNumero: "+getBeanComunicacion().getIntNumero());
	  System.out.println("beanComunicacion.intAnexo: "+getBeanComunicacion().getIntAnexo());
      Comunicacion com = new Comunicacion();
      com = (Comunicacion) getBeanComunicacion();
      com.setIntCasoEmergencia(getChkCasoEmerg()==true?1:0);
      
      if(strEmail!=null && !strEmail.equals("")){
    	  com.setStrDato(strEmail);
      }else if(strWeb!=null && !strWeb.equals("")){
    	  com.setStrDato(strWeb);
      }
      log.info("strDato: "+com.getStrDato());
      
      String descCom = "";
      System.out.println("com.getIntTipoComunicacionCod(): "+com.getIntTipoComunicacionCod());
      System.out.println("com.getIntTipoLineaCod(): "+com.getIntTipoLineaCod());
      System.out.println("com.getIntNumero(): "+com.getIntNumero());
      if(com.getIntTipoComunicacionCod()!=null && !com.getIntTipoComunicacionCod().equals(0)){
    	  if(com.getIntTipoComunicacionCod().equals(Constante.PARAM_T_TIPOCOMUNICACION_TELEFONO)){
    		  descCom = ("Tipo Línea: "+ (com.getIntTipoLineaCod().equals(Constante.PARAM_T_TIPOLINEATELEF_FIJO) ? "Fijo" : 
					 com.getIntTipoLineaCod().equals(Constante.PARAM_T_TIPOLINEATELEF_CLARO) ? "Claro" : 
					 com.getIntTipoLineaCod().equals(Constante.PARAM_T_TIPOLINEATELEF_MOVISTAR) ? "Movistar" :
					 com.getIntTipoLineaCod().equals(Constante.PARAM_T_TIPOLINEATELEF_NEXTEL) ? "Nextel" :"") + 
					 " - Nro.: " + com.getIntNumero());
          }else if(com.getIntTipoComunicacionCod().equals(Constante.PARAM_T_TIPOCOMUNICACION_EMAIL)){
        	  descCom = "E-mail: "+com.getStrDato();
          }else if(com.getIntTipoComunicacionCod().equals(Constante.PARAM_T_TIPOCOMUNICACION_WEBSITE)){
        	  descCom = "Website: "+com.getStrDato();
          }
      }
      System.out.println("descCom: "+descCom);
      com.setStrComunicacion(descCom);
      
      ArrayList<Comunicacion> arrCom = new ArrayList<Comunicacion>();
      
      log.info("strIdBtnComunicacion: "+getStrCallingFormId());
      if(getStrCallingFormId()!=null && getStrCallingFormId().equals(strFormIdJuriConve)){
    	  if(getBeanListComuniPerJuri()!=null){
        	  arrCom = (ArrayList<Comunicacion>) getBeanListComuniPerJuri();
          }
    	  arrCom.add(com);
    	  
    	  setBeanListComuniPerJuri(arrCom);
      }else if(getStrCallingFormId()!=null && getStrCallingFormId().equals(strFormIdRepLegal)){
    	  if(getBeanListComuniRepLegal()!=null){
        	  arrCom = (ArrayList<Comunicacion>) getBeanListComuniRepLegal();
          }
    	  arrCom.add(com);
    	  
    	  setBeanListComuniRepLegal(arrCom);
	  }else if(getStrCallingFormId()!=null && getStrCallingFormId().equals(strFormIdContactoNatu)){
    	  if(getListComuniContactoNatu()!=null){
        	  arrCom = (ArrayList<Comunicacion>) getListComuniContactoNatu();
          }
    	  arrCom.add(com);
    	  
    	  setListComuniContactoNatu(arrCom);
	  }else if(getStrCallingFormId()!=null && getStrCallingFormId().equals(strFormIdSocioNatu)){
    	  if(getListComuniSocioNatu()!=null){
        	  arrCom = (ArrayList<Comunicacion>) getListComuniSocioNatu();
          }
    	  arrCom.add(com);
    	  
    	  setListComuniSocioNatu(arrCom);
	  }else if(getStrCallingFormId()!=null && getStrCallingFormId().equals(strFormIdSocioNatuEmp)){
    	  if(getListComuniSocioNatuEmp()!=null){
        	  arrCom = (ArrayList<Comunicacion>) getListComuniSocioNatuEmp();
          }
    	  arrCom.add(com);
    	  
    	  setListComuniSocioNatuEmp(arrCom);
	  }else if(getStrCallingFormId()!=null && getStrCallingFormId().equals(strFormIdBeneficiario)){
    	  if(getListComuniBeneficiario()!=null){
        	  arrCom = (ArrayList<Comunicacion>) getListComuniBeneficiario();
          }
    	  arrCom.add(com);
    	  
    	  setListComuniBeneficiario(arrCom);
	  }
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  removeComunicacion()           					*/
	/*                                                    	 		*/
	/*  Parametros. :  Integer intIdPersona               	 		*/
	/*                 intIdPersona = el Id de la persona a la cual */ 
	/*                 se le agregará un nuevo Comunicacion         */
	/*                                                    	 		*/
	/*  Objetivo: Grabar un Nuevo Comunicacion de acuerdo al Id de la Persona */
	/*  Retorno : Nuevo Comunicacion generado correctamente	 		*/
	/**************************************************************/
	public void removeComunicacion(ActionEvent event){
		log.info("-------------------Debugging ComunicacionController.removeComunicacion-----------------------");
	    log.info("Se ha seteado el Service");
	    ArrayList<Comunicacion> arrayCom = new ArrayList<Comunicacion>();
	    for(int i=0; i<getBeanListComunicacion().size(); i++){
	    	Comunicacion com = new Comunicacion();
	    	com = (Comunicacion) getBeanListComunicacion().get(i);
	    	if(com.getChkCom() == false){
	    		arrayCom.add(com);
	    	}
	    }
	    setBeanListComunicacion(arrayCom);
	    
	}
	
	public void disableTipoComunicacion(ActionEvent event) {
		log.info("----------------Debugging ComunicacionController.enableDisableControls-------------------");
		log.info("pIntTipoComunicacion: "+getRequestParameter("pIntTipoComunicacion"));
		Integer intTipoComunicacion = Integer.valueOf(getRequestParameter("pIntTipoComunicacion"));
		log.info("intTipoComunicacion: "+intTipoComunicacion);
		log.info("strEmail: "+strEmail);
		log.info("strWeb: "+strWeb);
		if(intTipoComunicacion.equals(Constante.PARAM_T_TIPOCOMUNICACION_EMAIL)){
			setRenderEmail(true);
			setStrEmail("@hotmail.com");
			setStrWeb("");
			setRenderWebSite(false);
			setRenderTelefono(false);
		}else if(intTipoComunicacion.equals(Constante.PARAM_T_TIPOCOMUNICACION_WEBSITE)){
			setRenderWebSite(true);
			setStrEmail("");
			setStrWeb("http://");
			setRenderEmail(false);
			setRenderTelefono(false);
		}else if(intTipoComunicacion.equals(Constante.PARAM_T_TIPOCOMUNICACION_TELEFONO)){
			setRenderTelefono(true);
			setStrEmail("");
			setStrWeb("");
			setRenderEmail(false);
			setRenderWebSite(false);
		}else{
			setStrEmail("");
			setStrWeb("");
			setRenderEmail(false);
			setRenderWebSite(false);
			setRenderTelefono(false);
		}
		log.info("strEmail: "+strEmail);
		log.info("strWeb: "+strWeb);
		//Limpiar bean comuniacion
		limpiarFormComunicacion();
	}
	
	public void disableControls(ActionEvent event) {
		log.info("----------------Debugging ComunicacionController.enableDisableControls-------------------");
		setEnableDisableDescComunicacion(getChkDescripcion()!=true);
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  viewComunicacionJuridica()           						*/
	/*                                                    	 		*/
	/*  Parametros. :  event				               	 		*/
	/*                                                    	 		*/
	/*  Objetivo: Visualizar los datos del listado de Comunicaciones*/
	/*  Retorno : Se visualizan todos los datos de la Comunicación	*/
	/*            seleccionada.                            	 		*/
	/**************************************************************/
	public void viewComunicacionJuridica(ActionEvent event){
		log.info("------------------Debugging ComunicacionController.viewComunicacionJuridica-------------------");
	    String rowKey = getRequestParameter("rowKeyComunicacionJuridica");
		log.info("rowKeyComunicacionJuridica = "+ rowKey);
		
		viewComunicacion(beanListComuniPerJuri, rowKey);
	}
	
	public void viewComunicacionNatural(ActionEvent event){
		log.info("------------------Debugging ComunicacionController.viewComunicacionNatural-------------------");
	    log.info("Se ha seteado el Service");
	    String rowKey = getRequestParameter("rowKeyComunicacionRepLegal");
		log.info("rowKeyComunicaciones = "+ rowKey);
		
		viewComunicacion(beanListComuniRepLegal, rowKey);
	}
	
	public void viewComuniSocioNatu(ActionEvent event){
		log.info("------------------Debugging ComunicacionController.viewComuniSocioNatu-------------------");
	    String rowKey = getRequestParameter("rowKeyComuniSocioNatu");
		log.info("rowKeyComuniSocioNatu = "+ rowKey);
		
		viewComunicacion(listComuniSocioNatu, rowKey);
	}
	
	/*
	public void viewComuniSocioNatu(ActionEvent event){
		log.info("------------------Debugging ComunicacionController.viewComuniSocioNatu-------------------");
	    String rowKey = getRequestParameter("rowKeyComuniSocioNatu");
		log.info("rowKeyComuniSocioNatu = "+ rowKey);
		
		Comunicacion comunicacion = null;
		
	    if(listComuniSocioNatu!=null){
	    	for(int i=0; i<listComuniSocioNatu.size(); i++){
				if(rowKey!=null && Integer.parseInt(rowKey)==i){
					comunicacion = listComuniSocioNatu.get(i);
					break;
				}
			}
	    }
	    
	    //Desde AuditoriaSistController
	    //log.info("getIntCboTipoArchivo(): "+getIntCboTipoArchivo());
		String strTipoArchivo="__pdf__";
		//switch(getIntCboTipoArchivo()){
		//case 3: strTipoArchivo="__excel__"; break;
		//case 4: strTipoArchivo="__pdf__"; break;
		//case 6: strTipoArchivo="__html__"; break;
		//}
		if(strTipoArchivo.equals("")){
			//setMessageError("La exportación no está disponible en este formato.");
			return;
		}
		
		Map parameters = new HashMap();
		//parameters.put("cursorlista", listAuditoria);
		parameters.put("v_fechaini_v", (getDtAudiDesde()!=null)?sdf.format(getDtAudiDesde()):null);
		parameters.put("v_fechafin_v", (getDtAudiHasta()!=null)?sdf.format(getDtAudiHasta()):null);
		parameters.put("v_valoresnull", getBlnValoresNull()==true?1:0);
		parameters.put("v_valorescero", getBlnValoresCero()==true?1:0);
		parameters.put("v_operlogico", operador);
		parameters.put("v_tabla", strNombreTabla);
		parameters.put("v_columna", strNombreColumna);
		parameters.put("v_valorcolumna", strValorColumna);
		
		java.util.Date fecha = new Date();
		DateFormat formatoFecha = new SimpleDateFormat("yyyyMMddHHmmss");
		setExportFileType(strTipoArchivo);
		setReportName("Rpt_Certificados_"+String.valueOf(formatoFecha.format(fecha)));
		setInHardDisk(false);
		
		try {
			log.debug("La ruta del Reporte es  :  "+ ConstanteReporte.RUTA_REPORTES + "data_report01.jasper");
			String jasperFileReporte=getServletContext().getRealPath("") + ConstanteReporte.RUTA_REPORTES + "data_report01.jasper";
			log.info("jasperFileReporte: "+jasperFileReporte);
			setParameters(parameters);
			setJasperFile(jasperFileReporte);
			super.generate(event);
		}catch (Exception ex) {
			log.debug("El errror es  de generacion de reporte  ......... : "+ ex.getMessage());
		}
	}
	*/
	public void viewComuniSocioNatuEmp(ActionEvent event){
		log.info("------------------Debugging ComunicacionController.viewComuniSocioNatuEmp-------------------");
	    String rowKey = getRequestParameter("rowKeyComuniSocioNatuEmp");
		log.info("rowKeyComuniSocioNatuEmp = "+ rowKey);
		
		viewComunicacion(listComuniSocioNatuEmp, rowKey);
	}
	
	public void viewComuniBeneficiario(ActionEvent event){
		log.info("------------------Debugging ComunicacionController.viewComuniSocioNatuEmp-------------------");
	    String rowKey = getRequestParameter("rowKeyComuniBeneficiario");
		
		viewComunicacion(listComuniBeneficiario, rowKey);
	}
	
	public void viewComunicacion(List<Comunicacion> listComunicacion, String rowKey){
		log.info("------------------Debugging ComunicacionController.viewComunicacion-------------------");
	    Comunicacion comunicacion = null;
		
	    if(listComunicacion!=null){
	    	for(int i=0; i<listComunicacion.size(); i++){
				if(rowKey!=null && Integer.parseInt(rowKey)==i){
					comunicacion = listComunicacion.get(i);
					break;
				}
			}
	    }
	    
	    if(comunicacion!=null){
	    	if(comunicacion.getIntTipoComunicacionCod().equals(Constante.PARAM_T_TIPOCOMUNICACION_TELEFONO)){
	    		setRenderTelefono(true);
	    		setRenderEmail(false);
	    		setRenderWebSite(false);
		    }else if(comunicacion.getIntTipoComunicacionCod().equals(Constante.PARAM_T_TIPOCOMUNICACION_EMAIL)){
		    	setRenderTelefono(false);
	    		setRenderEmail(true);
	    		setRenderWebSite(false);
		    }else if(comunicacion.getIntTipoComunicacionCod().equals(Constante.PARAM_T_TIPOCOMUNICACION_WEBSITE)){
		    	setRenderTelefono(false);
	    		setRenderEmail(false);
	    		setRenderWebSite(true);
		    }
	    }
		
		setBeanComunicacion(comunicacion);
		
		if(comunicacion.getStrDato()!=null && !comunicacion.getStrDato().equals("")){
			if(comunicacion.getIntTipoComunicacionCod().equals(Constante.PARAM_T_TIPOCOMUNICACION_EMAIL)){
				setStrEmail(comunicacion.getStrDato());
			}else if(comunicacion.getIntTipoComunicacionCod().equals(Constante.PARAM_T_TIPOCOMUNICACION_WEBSITE)){
				setStrWeb(comunicacion.getStrDato());
			}
		}
	}
	
	public void quitarComunicacionJuridica(ActionEvent event){
		log.info("-------------------------------------Debugging quitarComunicacionJuridica-------------------------------------");
		String rowKey = getRequestParameter("rowKeyComunicacionJuridica");
		
		quitarComunicacion(beanListComuniPerJuri, rowKey);
		log.info("beanListComuniPerJuri.size: "+beanListComuniPerJuri.size());
	}
	
	public void quitarComunicacionRepLegal(ActionEvent event){
		log.info("-------------------------------------Debugging quitarComunicacionRepLegal-------------------------------------");
		String rowKey = getRequestParameter("rowKeyComunicacionRepLegal");
		
		quitarComunicacion(beanListComuniRepLegal, rowKey);
		log.info("beanListComuniRepLegal.size: "+beanListComuniRepLegal.size());
	}
	
	public void quitarComuniContactoNatu(ActionEvent event){
		log.info("-------------------------------------Debugging quitarComuniContactoNatu-------------------------------------");
		String rowKey = getRequestParameter("rowKeyComuniContactoNatu");
		
		quitarComunicacion(listComuniContactoNatu, rowKey);
		log.info("listComuniContactoNatu.size: "+listComuniContactoNatu.size());
	}
	
	public void quitarComuniSocioNatu(ActionEvent event){
		log.info("-------------------------------------Debugging ComunicacionController.quitarComuniSocioNatu-------------------------------------");
		String rowKey = getRequestParameter("rowKeyComuniSocioNatu");
		
		quitarComunicacion(listComuniSocioNatu, rowKey);
		log.info("listComuniSocioNatu.size: "+listComuniSocioNatu.size());
	}
	
	public void quitarComuniSocioNatuEmp(ActionEvent event){
		log.info("-------------------------------------Debugging ComunicacionController.quitarComuniSocioNatuEmp-------------------------------------");
		String rowKey = getRequestParameter("rowKeyComuniSocioNatuEmp");
		
		quitarComunicacion(listComuniSocioNatuEmp, rowKey);
		log.info("listComuniSocioNatuEmp.size: "+listComuniSocioNatuEmp.size());
	}
	
	public void quitarComuniBeneficiario(ActionEvent event){
		log.info("-------------------------------------Debugging ComunicacionController.quitarComuniSocioNatuEmp-------------------------------------");
		String rowKey = getRequestParameter("rowKeyComuniBeneficiario");
		
		quitarComunicacion(listComuniBeneficiario, rowKey);
	}
	
	public void quitarComunicacion(List<Comunicacion> listComunicacion, String rowKey){
		log.info("-------------------------------------Debugging quitarComunicacion-------------------------------------");
		
		Comunicacion comuniTmp = null;
		if(listComunicacion!=null){
			for(int i=0; i<listComunicacion.size(); i++){
				if(Integer.parseInt(rowKey)==i){
					Comunicacion comunicacion = listComunicacion.get(i);
					log.info("comunicacion.id: "+comunicacion.getId());
					if(comunicacion.getId()!=null && comunicacion.getId().getIntIdComunicacion()!=null){
						comuniTmp = listComunicacion.get(i);
						comuniTmp.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					}
					listComunicacion.remove(i);
					break;
				}
			}
			if(comuniTmp!=null && comuniTmp.getId().getIntIdComunicacion()!=null){
				listComunicacion.add(comuniTmp);
			}
		}
	}
	
	protected void setMessageSuccess(String msg) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(msg));
	}
	protected void setMessageSuccess(String zone, String msg) {
		FacesContext.getCurrentInstance().addMessage(zone,
				new FacesMessage(msg));
	}
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}
	
	//Getters and Setters
	public Comunicacion getBeanComunicacion() {
		return beanComunicacion;
	}
	public void setBeanComunicacion(Comunicacion beanComunicacion) {
		this.beanComunicacion = beanComunicacion;
	}
	public List<Comunicacion> getBeanListComunicacion() {
		return beanListComunicacion;
	}
	public void setBeanListComunicacion(List<Comunicacion> beanListComunicacion) {
		this.beanListComunicacion = beanListComunicacion;
	}
	public Boolean getChkDescripcion() {
		return chkDescripcion;
	}
	public void setChkDescripcion(Boolean chkDescripcion) {
		this.chkDescripcion = chkDescripcion;
	}
	public Boolean getChkCasoEmerg() {
		return chkCasoEmerg;
	}
	public void setChkCasoEmerg(Boolean chkCasoEmerg) {
		this.chkCasoEmerg = chkCasoEmerg;
	}
	public String getStrInsertUpdate() {
		return strInsertUpdate;
	}
	public void setStrInsertUpdate(String strInsertUpdate) {
		this.strInsertUpdate = strInsertUpdate;
	}
	public String getStrIdModalPanel() {
		return strIdModalPanel;
	}
	public void setStrIdModalPanel(String strIdModalPanel) {
		this.strIdModalPanel = strIdModalPanel;
	}
	public String getPgListComunicacion() {
		return pgListComunicacion;
	}
	public void setPgListComunicacion(String pgListComunicacion) {
		this.pgListComunicacion = pgListComunicacion;
	}
	public Boolean getEnableDisableFormComunicacion() {
		return enableDisableFormComunicacion;
	}
	public void setEnableDisableFormComunicacion(
			Boolean enableDisableFormComunicacion) {
		this.enableDisableFormComunicacion = enableDisableFormComunicacion;
	}
	public Boolean getEnableDisableDescComunicacion() {
		return enableDisableDescComunicacion;
	}
	public void setEnableDisableDescComunicacion(
			Boolean enableDisableDescComunicacion) {
		this.enableDisableDescComunicacion = enableDisableDescComunicacion;
	}
	public List<Comunicacion> getBeanListComuniPerJuri() {
		return beanListComuniPerJuri;
	}
	public void setBeanListComuniPerJuri(List<Comunicacion> beanListComuniPerJuri) {
		this.beanListComuniPerJuri = beanListComuniPerJuri;
	}
	public List<Comunicacion> getBeanListComuniRepLegal() {
		return beanListComuniRepLegal;
	}
	public void setBeanListComuniRepLegal(List<Comunicacion> beanListComuniRepLegal) {
		this.beanListComuniRepLegal = beanListComuniRepLegal;
	}
	public String getStrCallingFormId() {
		return strCallingFormId;
	}
	public void setStrCallingFormId(String strCallingFormId) {
		this.strCallingFormId = strCallingFormId;
	}
	public ArrayList<Comunicacion> getArrayComunicacion() {
		return arrayComunicacion;
	}
	public void setArrayComunicacion(ArrayList<Comunicacion> arrayComunicacion) {
		this.arrayComunicacion = arrayComunicacion;
	}
	public Boolean getRenderEmail() {
		return renderEmail;
	}
	public void setRenderEmail(Boolean renderEmail) {
		this.renderEmail = renderEmail;
	}
	public Boolean getRenderWebSite() {
		return renderWebSite;
	}
	public void setRenderWebSite(Boolean renderWebSite) {
		this.renderWebSite = renderWebSite;
	}
	public Boolean getRenderTelefono() {
		return renderTelefono;
	}
	public void setRenderTelefono(Boolean renderTelefono) {
		this.renderTelefono = renderTelefono;
	}
	public String getStrEmail() {
		return strEmail;
	}
	public void setStrEmail(String strEmail) {
		this.strEmail = strEmail;
	}
	public String getStrWeb() {
		return strWeb;
	}
	public void setStrWeb(String strWeb) {
		this.strWeb = strWeb;
	}
	public String getStrFormIdBeneficiario() {
		return strFormIdBeneficiario;
	}
	public void setStrFormIdBeneficiario(String strFormIdBeneficiario) {
		this.strFormIdBeneficiario = strFormIdBeneficiario;
	}
	public List<Comunicacion> getListComuniSocioNatu() {
		return listComuniSocioNatu;
	}
	public void setListComuniSocioNatu(List<Comunicacion> listComuniSocioNatu) {
		this.listComuniSocioNatu = listComuniSocioNatu;
	}
	public List<Comunicacion> getListComuniSocioNatuEmp() {
		return listComuniSocioNatuEmp;
	}
	public void setListComuniSocioNatuEmp(List<Comunicacion> listComuniSocioNatuEmp) {
		this.listComuniSocioNatuEmp = listComuniSocioNatuEmp;
	}
	public List<Comunicacion> getListComuniContactoNatu() {
		return listComuniContactoNatu;
	}
	public void setListComuniContactoNatu(List<Comunicacion> listComuniContactoNatu) {
		this.listComuniContactoNatu = listComuniContactoNatu;
	}
	public List<Comunicacion> getListComuniBeneficiario() {
		return listComuniBeneficiario;
	}
	public void setListComuniBeneficiario(List<Comunicacion> listComuniBeneficiario) {
		this.listComuniBeneficiario = listComuniBeneficiario;
	}
}