<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

<script type="text/javascript">
	function flagDescuentos(caja){		
		if(caja.checked){
		  document.frmEditarCerificado["frmEditarCerificado:txtPenalidad"].disabled=false; 
		  document.frmEditarCerificado["frmEditarCerificado:txtGarantia"].disabled=false;
		  document.frmEditarCerificado["frmEditarCerificado:txtCredito"].disabled= false;							  		   				
		}else{		    
			 document.frmEditarCerificado["frmEditarCerificado:txtPenalidad"].disabled=true; 
		     document.frmEditarCerificado["frmEditarCerificado:txtGarantia"].disabled=true;
		     document.frmEditarCerificado["frmEditarCerificado:txtCredito"].disabled= true;
		     document.frmEditarCerificado["frmEditarCerificado:txtPenalidad"].value=0; 
		     document.frmEditarCerificado["frmEditarCerificado:txtGarantia"].value=0;
		     document.frmEditarCerificado["frmEditarCerificado:txtCredito"].value= 0;		     			     		   	
		}
	}
	function flagObservaciones(Obsevacion){		
		if(Obsevacion.checked){  
		  document.frmEditarCerificado["frmEditarCerificado:chkPermitirModificacion"].disabled=false; 
		  document.frmEditarCerificado["frmEditarCerificado:txtDescObsIntervencion"].disabled=false;		 							  		   			
		}else{		    
			  document.frmEditarCerificado["frmEditarCerificado:chkPermitirModificacion"].disabled=true;
		      document.frmEditarCerificado["frmEditarCerificado:txtDescObsIntervencion"].disabled=true;		      
		      document.frmEditarCerificado["frmEditarCerificado:chkPermitirModificacion"].value=0; 
		      document.frmEditarCerificado["frmEditarCerificado:txtDescObsIntervencion"].value=0;
		}
	}	
</script>
<!-- Declaro El Formulario Para  Editar Certificado SAP -->
<h:form id="frmEditarCerificado">
	
	<rich:panel >
	<f:facet name="header">
				<h:outputText value = " Editar Certificado SAP" style="text-align:left"> </h:outputText>
	</f:facet>
	<h:panelGrid columns="2"  id="certificadoPanel">
		<h:outputText value="Nro Correlativo: " styleClass="spacio" style="padding-right:122px;"/>
		<h:inputText value="#{CertificadoController.bean.id}" readonly="true" disabled="true"
			size="14" style="background-color: #D0D0D0" id="id11">
			<f:validateLength minimum="1" maximum="6" />
		</h:inputText>
		 
	</h:panelGrid>
	<rich:separator height="5px"></rich:separator>
	<br/>
	
	<!--Campos para Gestor -->
	<h:panelGrid columns="4"  id="certificadoPanelgestor" >
	<h:outputText value="Gestor: " styleClass="spacio"
				style="color:#063A4F;font-weight:bold;"  />
	<h:outputText value=""  /><h:outputText value=""  /><h:outputText value=""  />
	
	<h:outputText value="Flag de Descuento:" styleClass="spacio" />
    <h:selectBooleanCheckbox value="#{CertificadoController.bean.tieneDescuento}"  onclick="flagDescuentos(this)"  disabled="#{CertificadoController.beanControles.panelGestorHabilitado}">         
			</h:selectBooleanCheckbox>						
			<h:outputText value="Moneda: " styleClass="spacio" />
			<h:selectOneMenu value="#{CertificadoController.bean.moneda}" disabled="true">
				<f:selectItems value="#{CertificadoController.listaMoneda}" />
			</h:selectOneMenu>

			<h:outputText value="Importe por Penalidad: " styleClass="spacio" />
			<h:inputText value="#{CertificadoController.bean.importeDescuento1}"  disabled="#{CertificadoController.bloquearDescuentos}" size="10"
				style="background-color: #D0D0D0" id="txtPenalidad">												
			</h:inputText>

			<h:outputText value="Importe por Fondo Garantia: "
				styleClass="spacio" />
			<h:inputText value="#{CertificadoController.bean.importeDescuento2}"   disabled="#{CertificadoController.bloquearDescuentos}" size="10"
				style="background-color: #D0D0D0" id="txtGarantia">				
			</h:inputText>

			<h:outputText value="Importe por Nota de Credito: "
				styleClass="spacio" />
			<h:inputText id="txtCredito" value="#{CertificadoController.bean.importeDescuento3}"  disabled="#{CertificadoController.bloquearDescuentos}"  size="10"
				style="background-color: #D0D0D0" id="txtCredito">				
			</h:inputText>
			
			<h:outputText value="Contrato Firmado: " styleClass="spacio" />
			<h:selectBooleanCheckbox value="#{CertificadoController.bean.contratoFirmado}" disabled="#{CertificadoController.beanControles.panelGestorHabilitado}" >
			</h:selectBooleanCheckbox>
			
			<h:outputText value="Presenta Garantias Economicas Financiera: "
				styleClass="spacio" />
			<h:selectOneMenu value="#{CertificadoController.bean.tipoGarantia}" styleClass="SelectOption2" disabled="#{CertificadoController.beanControles.panelGestorHabilitado}" >
				<f:selectItem itemValue="-1" itemLabel="--SELECCIONAR--" />
				<f:selectItem itemValue="1" itemLabel="Poliza de Seguros" />
				<f:selectItem itemValue="2" itemLabel="Carta Fianzas" />
				<f:selectItem itemValue="3" itemLabel="Otros" />
			</h:selectOneMenu>
			
			<h:outputText value="Fecha de Aceptacion: " styleClass="spacio" />
			<rich:calendar value="#{CertificadoController.bean.fechaRecepcion}" datePattern="dd/MM/yyyy HH:mm" disabled="#{CertificadoController.beanControles.panelGestorHabilitado}" >			
				</rich:calendar>

			</h:panelGrid>
			<h:panelGrid columns="2"  id="certificadoObservacion" >
			<h:outputText value="Observacion de Area Gestora: "
				styleClass="spacio" style="padding-right:61px;"/>
			<h:inputTextarea value="#{CertificadoController.bean.observacionGestor}" rows="5"  cols="250" style="width:450px" disabled="#{CertificadoController.beanControles.panelObservacionGestorHabilitado}" />
			
		</h:panelGrid>
		
		<rich:separator height="5px"></rich:separator>
		<br/>
		
		<!--Campos para Cuentas Por Cobrar -->
		<h:panelGrid columns="2" id="PanelCuentasPorCobrar" border="0" >

			<h:outputText value="Cuentas Por Cobrar: " styleClass="spacio"
				style="color:#063A4F;font-weight:bold;" />

			

			<h:outputText value=" " styleClass="spacio" />
			<h:outputText value="Factura Registrada:" styleClass="spacio" style="padding-right:115px;"/>
			<h:selectBooleanCheckbox value="#{CertificadoController.bean.facturaRegistrada}" disabled="#{CertificadoController.beanControles.panelCuentasPorCobrarHabilitado}" ></h:selectBooleanCheckbox>
			    <rich:spacer height="4px"/><rich:spacer height="4px"/>																																								
				<rich:spacer height="4px"/><rich:spacer height="4px"/>
			<h:outputText value="Factura Recibida:" styleClass="spacio" style="padding-right:115px;"/>
			<h:selectBooleanCheckbox value="#{CertificadoController.bean.facturaRecibida}" disabled="#{CertificadoController.beanControles.panelCuentasPorCobrarHabilitado}"></h:selectBooleanCheckbox>
		
			
		</h:panelGrid>
		<rich:separator height="5px"></rich:separator>
	    <br/>
		
		<!--Campos para Interventor -->
		<h:panelGrid columns="2" id="PanelInterventor" border="0">
			<h:outputText value="Interventor: " styleClass="spacio"
				style="color:#063A4F;font-weight:bold;" />
			<h:outputText value=" " styleClass="spacio" />
					
			<h:outputText value="Contiene Observaciones: "   styleClass="spacio" style="padding-right:88px;"/>
			<h:selectBooleanCheckbox value="#{CertificadoController.bean.observacionIntervencion}" onclick="flagObservaciones(this)" disabled="#{CertificadoController.beanControles.panelInterventorHabilitado}" ></h:selectBooleanCheckbox>
			    <rich:spacer height="4px"/><rich:spacer height="4px"/>																																								
				<rich:spacer height="4px"/><rich:spacer height="4px"/>
			<h:outputText value="Permitir Modificacion: " styleClass="spacio" style="padding-right:88px;"/>
			<h:selectBooleanCheckbox id = "chkPermitirModificacion" value="#{CertificadoController.bean.permitirModificacion}" disabled="#{CertificadoController.beanControles.panelInterventorHabilitado}" ></h:selectBooleanCheckbox>
		</h:panelGrid>
			<h:panelGrid columns="2" id="PanelInterventorObservacion" border="0">
			<h:outputText  value="Descripcion de la Observacion: " styleClass="spacio" style="padding-right:61px;"/>
			<h:inputTextarea id ="txtDescObsIntervencion" value="#{CertificadoController.bean.descripcionObservacionIntervencion}" style="width:450px" disabled="#{CertificadoController.beanControles.panelInterventorObservacionHabilitado}" />
		</h:panelGrid>
	
	<!-- Fin de Editar Cer -->
	
	<a4j:commandButton action="#" value=" Actualizar " 
			actionListener="#{CertificadoController.updateCertificados}">
			<f:param name="sid" id="sid1" value="#{CertificadoController.bean.id}" />
	</a4j:commandButton>
	
	<a4j:commandButton action="buscarCertificado" value=" Volver "   			
			reRender="pgDatatable2" styleClass="spacio"/>	
	
		
 </rich:panel>

</h:form>
