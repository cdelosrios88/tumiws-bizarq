<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Christian De los Ríos    -->
	<!-- Modulo    : Créditos                 -->
	<!-- Prototipo : PROTOTIPO BENEFICIARIO   -->
	<!-- Fecha     : 24/01/2012               -->
	
	<script language="JavaScript" src="/tumi/js/main.js"  type="text/javascript"></script>
	
	<script type="text/javascript">
		function getJSSelectionTipoVinculo(){
			var idTipoVinculo = document.frmBeneficiario["frmBeneficiario:cboTipoVinculo"].selectedIndex;
		  	var vcTipoVinculo = document.frmBeneficiario["frmBeneficiario:cboTipoVinculo"][idTipoVinculo].text;
		  	document.frmBeneficiario["frmBeneficiario:hiddenStrTipoVinculo"].value=vcTipoVinculo;
		}
		
		function getJSSelectionTipoPersona(){
			var idTipoPersona = document.frmBeneficiario["frmBeneficiario:cboTipoPersona"].selectedIndex;
		  	var vcTipoPersona = document.frmBeneficiario["frmBeneficiario:cboTipoPersona"][idTipoPersona].text;
		  	document.frmBeneficiario["frmBeneficiario:hiddenStrTipoPersona"].value=vcTipoPersona;
		}
	</script>
	
    <rich:modalPanel id="pAgregarDomicilio" width="700" height="500"
	  		resizeable="false" style="background-color:#DEEBF5;">
	        <f:facet name="header">
	            <h:panelGrid>
	              <rich:column style="border: none;">
	                <h:outputText value="Domicilio"/>
	              </rich:column>
	            </h:panelGrid>
	        </f:facet>
	        <f:facet name="controls">
	            <h:panelGroup>
	               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidePanelDomicilio"/>
	               <rich:componentControl for="pAgregarDomicilio" attachTo="hidePanelDomicilio" operation="hide" event="onclick"/>
	           </h:panelGroup>
	        </f:facet>
	        <a4j:include viewId="/pages/popups/domicilioBody.jsp"/>
    </rich:modalPanel>
    
    <rich:modalPanel id="pAgregarComunicacion" width="780" height="310"
  		resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Comunicación"/>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidePanelComunicacion"/>
               <rich:componentControl for="pAgregarComunicacion" attachTo="hidePanelComunicacion" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        <a4j:include viewId="/pages/popups/comunicacionBody.jsp"/>
    </rich:modalPanel>
    
    <h:form id="frmBeneficiario" onkeypress="ifEnterClick(event, document.getElementById('frmBeneficiario:btnValidar'));">
    	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 860px;">
    		<h:panelGrid columns="3">
    			<rich:column style="border:none;" width="120px"><h:outputText value="Nombre de Socio : "/></rich:column>
    			<rich:column style="border:none;padding-left:10px;">
    				<h:inputText value="#{hojaPlaneamientoController.sNombEntidad}" size="60"/>
				</rich:column>
				<rich:column style="border:none;padding-left:10px;">
			    	<h:inputText value="#{hojaPlaneamientoController.sNombEntidad}" size="10"/>
				</rich:column>
    		</h:panelGrid>
    		<rich:spacer height="8px"/>
    		
    		<h:panelGrid columns="3">
    			<rich:column style="border:none;">
    				<a4j:commandButton value="Grabar" actionListener="#{beneficiarioController.grabarBeneficiario}" reRender="pBeneficiario" styleClass="btnEstilos"/>
    			</rich:column>
    			<rich:spacer width="4px;"/>
    			<rich:column style="border:none;">
    				<a4j:commandButton value="Cancelar" actionListener="#{beneficiarioController.cancelarBeneficiario}" reRender="pBeneficiario" styleClass="btnEstilos"/>
    			</rich:column>
    		</h:panelGrid>
    		<rich:spacer height="8px"/>
    		
    		<h:panelGrid id="pBeneficiario">
	        	<rich:panel style="width: 860px;border:1px solid #17356f;background-color:#DEEBF5;">
	        		<h:inputHidden value="#{beneficiarioController.beanBeneficiario.intIdPersona}"/>
	        		<h:inputHidden id="hiddenStrTipoVinculo" value="#{beneficiarioController.hiddenStrTipoVinculo}"/>
	        		<h:inputHidden id="hiddenStrTipoPersona" value="#{beneficiarioController.hiddenStrTipoPersona}"/>
	        		<h:panelGrid columns="7">
	        			<rich:column style="border:none;" width="100">
	        				<h:outputText value="Tipo de Vínculo: "/>
	        			</rich:column>
	        			<rich:column style="border:none;">
	        				<h:selectOneMenu id="cboTipoVinculo" value="#{beneficiarioController.intTipoVinculo}" onchange="getJSSelectionTipoVinculo();">
	        					<%--<f:selectItems value="#{parametersController.cboTipoVinculo}"/>--%>
	        					<f:selectItem itemValue="0" itemLabel="Seleccionar.."/>
	        					<f:selectItem itemValue="1" itemLabel="Cónyuge"/>
	        					<f:selectItem itemValue="2" itemLabel="Hijo"/>
	        					<f:selectItem itemValue="3" itemLabel="Padre"/>
	        					<f:selectItem itemValue="5" itemLabel="Otros"/>
	        				</h:selectOneMenu>
	        			</rich:column>
	        			<rich:column width="110" style="border:none;padding-left:10px;">
	        				<h:outputText value="Tipo de Persona: "/>
	        			</rich:column>
	        			<rich:column style="border:none;">
	        				<h:selectOneMenu id="cboTipoPersona" onchange="getJSSelectionTipoPersona();">
	        					<f:selectItem itemLabel="Persona Natural" itemValue="1"/>
	        					<%--<f:selectItems value="#{parametersController.cboTiposPersona}"/>--%>
	        				</h:selectOneMenu>
	        			</rich:column>
	        			
	        			<rich:column width="150" style="border:none;padding-left:10px;">
	        				<h:outputText value="Número de Documento: "/>
	        			</rich:column>
	        			<rich:column style="border:none;">
	        				<h:selectOneMenu id="cboTipoDoc" value="#{beneficiarioController.intTipoDoc}">
	        					<f:selectItems value="#{parametersController.cboTiposDocumento}"/>
	        				</h:selectOneMenu>
	        			</rich:column>
	        			<rich:column style="border:none; padding-left:10px;">
	        				<h:inputText size="11" value="#{beneficiarioController.strNroDoc}" onkeypress="return soloNumeros(event);" maxlength="11"/>
	        			</rich:column>
	        		</h:panelGrid>
	        		
	        		<div align="center">
		        		<h:panelGrid>
		        			<a4j:commandButton id="btnValidar" value="Validar Datos" styleClass="btnEstilos1" actionListener="#{beneficiarioController.validarBeneficiario}" reRender="pBeneficiario,pgDomicilio"/>
		        		</h:panelGrid>
	        		</div>
	        		<h:outputText value="#{beneficiarioController.msgTipoVinculo}" styleClass="msgError"/>
	        		<h:outputText value="#{beneficiarioController.msgTipoDoc}" styleClass="msgError"/>
	        		<h:outputText value="#{beneficiarioController.msgNroDoc}" styleClass="msgError"/>
	        		<rich:spacer height="4px"/><rich:spacer height="4px"/>
	        		<rich:separator height="5px"/>
	        		<rich:spacer height="8px"/>
	         		
	         		<rich:panel rendered="#{beneficiarioController.formBeneficiario}" style="width: 840px;border:1px solid #17356f;background-color:#DEEBF5;">
			         	<h:panelGrid>
			         		<h:outputText value="DATOS PERSONALES" style="font-weight:bold;text-decoration:underline;"/>
			         	</h:panelGrid>
		         		
			         	<h:panelGrid columns="6">
			         		<rich:column style="border:none;">
			         			<h:outputText value="Ap. Paterno:"/>
			         		</rich:column>
			         		<rich:column style="border:none;">
			         			<h:inputText value="#{beneficiarioController.beanBeneficiario.strApPaterno}" size="30"/>
			         		</rich:column>
			         		<rich:column style="border:none;">
			         			<h:outputText value="Ap. Materno: "/>
			         		</rich:column>
			         		<rich:column style="border:none;">
			         			<h:inputText value="#{beneficiarioController.beanBeneficiario.strApMaterno}" size="30"/>
			         		</rich:column>
			         		<rich:column style="border:none;">
			         			<h:outputText value="Nombres: "/>
			         		</rich:column>
			         		<rich:column style="border:none;">
			         			<h:inputText value="#{beneficiarioController.beanBeneficiario.strNombres}" size="40"/>
			         		</rich:column>
			         	</h:panelGrid>
		         	
			         	<h:panelGrid columns="2">
			         		<rich:column style="border:none;">
			         			<h:panelGrid columns="3">
					          		<rich:column style="border:none;" width="110px;">
					          			<h:outputText value="Fec. de Nacimiento"/>
					          		</rich:column>
					          		<rich:column style="border:none;">
					          			<h:outputText value=":"/>
					          		</rich:column>
					          		<rich:column style="border:none; padding-left:10px;">
					          			<rich:calendar popup="true" enableManualInput="true"
					                   	value="#{beneficiarioController.daFechaNacimiento}"
					                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
					                    cellWidth="10px" cellHeight="20px"/>
					          		</rich:column>
				          		</h:panelGrid>
			          		
			          			<h:panelGrid columns="3">
					          		<rich:column style="border:none;" width="70px;">
					          			<h:outputText value="Sexo"/>
					          		</rich:column>
					          		<rich:column style="border:none;">
					          			<h:outputText value=":"/>
					          		</rich:column>
					          		<rich:column style="border:none; padding-left:10px;">
					          			<h:selectOneRadio value="#{beneficiarioController.beanBeneficiario.intIdSexo}">
					          				<f:selectItem itemValue="1" itemLabel="Femenino"/>
					          				<f:selectItem itemValue="2" itemLabel="Masculino"/>
					          			</h:selectOneRadio>
					          		</rich:column>
				          		</h:panelGrid>
				          		
				          		<h:panelGrid columns="3">
					          		<rich:column style="border:none;" width="110px;">
					          			<h:outputText value="Estado Civil"/>
					          		</rich:column>
					          		<rich:column style="border:none;">
					          			<h:outputText value=":"/>
					          		</rich:column>
					          		<rich:column style="border:none;padding-left:10px;">
					          			<h:selectOneMenu value="#{beneficiarioController.beanBeneficiario.intEstCivil}">
					          				<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
					          				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOCIVIL}" 
												itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					          			</h:selectOneMenu>
					          		</rich:column>
				          		</h:panelGrid>
				          	
					          	<h:panelGrid columns="3">
					          		<rich:column style="border:none;" width="130px;">
					          			<h:outputText value="Tipo de Persona"/>
					          		</rich:column>
					          		<rich:column style="border:none;">
					          			<h:outputText value=":"/>
					          		</rich:column>
					          		<rich:column style="border:none;padding-left:10px;">
					          			<h:inputText value="#{beneficiarioController.beanBeneficiario.strTipoPersonaBenef}" disabled="true" size="20"/>
					          		</rich:column>
					          	</h:panelGrid>
				          	
					          	<h:panelGrid columns="3">
					          		<rich:column style="border:none;" width="130px;">
					          			<h:outputText value="Tipo de Vínculo"/>
					          		</rich:column>
					          		<rich:column style="border:none;">
					          			<h:outputText value=":"/>
					          		</rich:column>
					          		<rich:column style="border:none;padding-left:10px;">
					          			<h:inputText value="#{beneficiarioController.beanBeneficiario.strTipoVinculoBenef}" disabled="true" size="20"/>
					          		</rich:column>
					          	</h:panelGrid>
				          	
					          	<h:panelGrid columns="3">
					          		<rich:column style="border:none;" width="130px;">
					          			<h:outputText value="Roles Actuales"/>
					          		</rich:column>
					          		<rich:column style="border:none;">
					          			<h:outputText value=":"/>
					          		</rich:column>
					          		<rich:column style="border:none;padding-left:10px;">
					          			<h:inputText disabled="true" size="20"/>
					          		</rich:column>
					          	</h:panelGrid>
				          	
					          	<h:panelGrid columns="3">
					          		<rich:column style="border:none;" width="130px;">
					          			<h:outputText value="Número Documento"/>
					          		</rich:column>
					          		<rich:column style="border:none;">
					          			<h:outputText value=":"/>
					          		</rich:column>
					          		<rich:column style="border:none;padding-left:10px;">
					          			<h:inputText value="#{beneficiarioController.beanBeneficiario.strNroDocBenef}" disabled="true" size="20"/>
					          		</rich:column>
					          	</h:panelGrid>
			         		</rich:column>
			         		
			         		<rich:column style="border:none;">
			         			<h:panelGrid columns="4">
			         				<rich:column style="border:none;">
			         					<h:outputText value="Firma :"/>
			         				</rich:column>
			         				<rich:column style="border:none;">
			         					<rich:fileUpload id="upldFirmaBenef" addControlLabel="Adjuntar Firma"
							             clearControlLabel="Limpiar" cancelEntryControlLabel="Cancelar"
							             uploadControlLabel="Subir Archivo" listHeight="65" listWidth="220"
							             fileUploadListener="#{fileUploadController.upldPhoto}"
										 maxFilesQuantity="1" doneLabel="Archivo cargado correctamente"
										 immediateUpload="#{fileUploadController.autoUpload}"
										 acceptedTypes="jpg,png,gif">
										 <f:facet name="label">
										 	<h:outputText value="{_KB}KB de {KB}KB cargados --- {mm}:{ss}" />
										 </f:facet>
										</rich:fileUpload>
			         				</rich:column>
			         				<rich:column style="border:none;padding-left:10px;">
			         					<h:outputText value="Foto :"/>
			         				</rich:column>
			         				<rich:column style="border:none;">
			         					<rich:fileUpload id="upldFotoBenef" addControlLabel="Adjuntar Imagen"
							             clearControlLabel="Limpiar" cancelEntryControlLabel="Cancelar"
							             uploadControlLabel="Subir" listHeight="65" listWidth="220"
							             fileUploadListener="#{fileUploadController.upldPhoto}"
										 maxFilesQuantity="1" doneLabel="Archivo cargado correctamente"
										 immediateUpload="#{fileUploadController.autoUpload}"
										 acceptedTypes="jpg,png,gif">
										 <f:facet name="label">
										 	<h:outputText value="{_KB}KB de {KB}KB cargados --- {mm}:{ss}" />
										 </f:facet>
										</rich:fileUpload>
			         				</rich:column>
			         			</h:panelGrid>
			         			
			         			<h:panelGrid id="pgFotos" columns="2">
			         				<rich:column width="200px" style="border:none;padding-left:40px;">
			         					<div align="center">
			         						<h:graphicImage value="#{beneficiarioController.beanBeneficiario.strFirma}" width="200px" height="80px" rendered="#{beneficiarioController.imgFirmaBenef}"/>
			         					</div>
			         				</rich:column>
			         				<rich:column width="160px" style="border:none;padding-left:140px;">
			         					<h:graphicImage value="#{beneficiarioController.beanBeneficiario.strFoto}" width="80px" height="80px" rendered="#{beneficiarioController.imgFotoBenef}"/>
			         				</rich:column>
			         			</h:panelGrid>
			         		</rich:column>
			         	</h:panelGrid>
		         	
			         	<h:panelGrid columns="5">
			         		<rich:column style="border:none;"  width="130px;">
			         			<h:outputText value="Otros Documentos"/>
			         		</rich:column>
			         		<rich:column style="border:none;">
			         			<h:outputText value=":"/>
			         		</rich:column>
			         		<rich:column style="border:none;padding-left:10px;">
			         			<h:selectOneMenu>
			         				<f:selectItem itemLabel="RUC" itemValue="1"/>
			         				<f:selectItem itemLabel="Pasaporte" itemValue="2"/>
			         				<f:selectItem itemLabel="Carnet de Extranjería" itemValue="3"/>
			         			</h:selectOneMenu>
			         		</rich:column>
			         		<rich:column style="border:none;padding-left:10px;">
			         			<h:inputText size="20"/>
			         		</rich:column>
			         		<rich:column style="border:none;">
			         			<h:commandButton value="Agregar" styleClass="btnEstilos"/>
			         		</rich:column>
			         	</h:panelGrid>
		         		
			         	<h:panelGrid id="pgAportes" columns="8">
			         		<rich:column style="border:none;" width="130px;">
			         			<h:outputText value="Tipo de Beneficio"/>
			         		</rich:column>
			         		<rich:column style="border:none;">
			         			<h:outputText value=":"/>
			         		</rich:column>
			         		<rich:column style="border:none;padding-left:10px;">
			         			<h:selectBooleanCheckbox value="#{beneficiarioController.chkAportes}">
			         				<a4j:support event="onclick" actionListener="#{beneficiarioController.enableDisableControls}" reRender="pgAportes"/>
			         			</h:selectBooleanCheckbox>
			         		</rich:column>
			         		<rich:column style="border:none;" width="250">
			         			<h:outputText value="Aportes"/>
			         		</rich:column>
			         		<rich:column style="border:none;"  width="150">
			         			<h:outputText value="Porcentaje de Beneficio"/>
			         		</rich:column>
			         		<rich:column style="border:none;">
			         			<h:outputText value=":"/>
			         		</rich:column>
			         		<rich:column style="border:none;padding-left:10px;">
			         			<h:inputText value="#{beneficiarioController.flPorcAportes}" disabled="#{beneficiarioController.formEnableDisableAportes}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" size="6"/>
			         		</rich:column>
			         		<rich:column style="border:none;">
			         			<h:outputText value="%"/>
			         		</rich:column>
			         	</h:panelGrid>
		         	
			         	<h:panelGrid id="pgFondoSepelio" columns="8">
			         		<rich:column style="border:none;" width="130px;">
			         			<h:outputText />
			         		</rich:column>
			         		<rich:column style="border:none;" width="3px;">
			         			<h:outputText />
			         		</rich:column>
			         		<rich:column style="border:none;padding-left:10px;">
			         			<h:selectBooleanCheckbox value="#{beneficiarioController.chkFondoSepelio}">
			         				<a4j:support event="onclick" actionListener="#{beneficiarioController.enableDisableControls}" reRender="pgFondoSepelio"/>
			         			</h:selectBooleanCheckbox>
			         		</rich:column>
			         		<rich:column style="border:none;" width="250">
			         			<h:outputText value="Fondo de Sepelio"/>
			         		</rich:column>
			         		<rich:column style="border:none;" width="150">
			         			<h:outputText value="Porcentaje de Beneficio"/>
			         		</rich:column>
			         		<rich:column style="border:none;">
			         			<h:outputText value=":"/>
			         		</rich:column>
			         		<rich:column style="border:none;padding-left:10px;">
			         			<h:inputText value="#{beneficiarioController.flPorcFondoSepelio}" disabled="#{beneficiarioController.formEnableDisableFondoSep}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" size="6"/>
			         		</rich:column>
			         		<rich:column style="border:none;">
			         			<h:outputText value="%"/>
			         		</rich:column>
			         	</h:panelGrid>
		         	
			         	<h:panelGrid id="pgFondoRetiro" columns="8">
			         		<rich:column style="border:none;" width="130px;">
			         			<h:outputText />
			         		</rich:column>
			         		<rich:column style="border:none;" width="3px;">
			         			<h:outputText />
			         		</rich:column>
			         		<rich:column style="border:none;padding-left:10px;">
			         			<h:selectBooleanCheckbox value="#{beneficiarioController.chkFondoRetiro}">
			         				<a4j:support event="onclick" actionListener="#{beneficiarioController.enableDisableControls}" reRender="pgFondoRetiro"/>
			         			</h:selectBooleanCheckbox>
			         		</rich:column>
			         		<rich:column style="border:none;" width="250">
			         			<h:outputText value="Fondo de Retiro"/>
			         		</rich:column>
			         		<rich:column style="border:none;" width="150">
			         			<h:outputText value="Porcentaje de Beneficio"/>
			         		</rich:column>
			         		<rich:column style="border:none;">
			         			<h:outputText value=":"/>
			         		</rich:column>
			         		<rich:column style="border:none;padding-left:10px;">
			         			<h:inputText value="#{beneficiarioController.flPorcFondoRetiro}" disabled="#{beneficiarioController.formEnableDisableFondoReten}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" size="6"/>
			         		</rich:column>
			         		<rich:column style="border:none;">
			         			<h:outputText value="%"/>
			         		</rich:column>
			         	</h:panelGrid>
		         		
			         	<h:panelGrid columns="3">
			         		<rich:column style="border:none;">
			         			<h:selectBooleanCheckbox/>
			         		</rich:column>
			         		<rich:column style="border:none;" width="120px">
			         			<h:outputText value="Cuentas Bancarias"/>
			         		</rich:column>
			         		<rich:column style="border:none;padding-left:10px;">
			                  	<a4j:commandLink oncomplete="Richfaces.showModalPanel('panelEstructura').show()" reRender="pgBusqEntidad,tbTablasMenu" immediate="true">
			                      	<h:graphicImage value="/images/icons/agregar2.jpg" style="border:0px"/>
			                   </a4j:commandLink>
			         		</rich:column>
			         	</h:panelGrid>
		         		
			         	<h:panelGrid id="pDomicilio" columns="4">
			         		<rich:column style="border:none;">
			         			<h:selectBooleanCheckbox value="#{beneficiarioController.chkDomicilio}">
			         				<a4j:support event="onclick" actionListener="#{beneficiarioController.enableDisableControls}" reRender="pDomicilio"/>
			         			</h:selectBooleanCheckbox>
			         		</rich:column>
			         		<rich:column style="border:none;" width="120px">
			         			<h:outputText value="Direcciones"/>
			         		</rich:column>
			         		<rich:column style="border:none;">
			                  	<a4j:commandButton id="btnAgregarDomicilio" value="Agregar" styleClass="btnEstilos" reRender="pgListDomicilio" disabled="#{beneficiarioController.formEnableDisableDomicilio}" action="#{domicilioController.limpiarFormDomicilio}">
			                		<rich:componentControl for="pAgregarDomicilio" attachTo="btnAgregarDomicilio" operation="show" event="onclick"/>
			                	</a4j:commandButton>
			         		</rich:column>
			         		<rich:column style="border:none;">
			                  	<a4j:commandButton id="btnEliminarDomicilio" value="Eliminar" styleClass="btnEstilos" actionListener="#{domicilioController.removeDomicilio}" disabled="#{beneficiarioController.formEnableDisableDomicilio}" reRender="pgDomicilio"/>
			         		</rich:column>
			         	</h:panelGrid>
			         	
			         	<h:panelGrid id="pgListDomicilio">
			         		<rich:dataTable id="edtDomicilio" value="#{domicilioController.beanListDomicilio}" rows="10"
			         			rendered="#{not empty domicilioController.beanListDomicilio}"  
			                    var="item" rowKeyVar="rowKey" sortMode="single" width="500px">
			                	<rich:column width="15px;">
					                <h:selectBooleanCheckbox value="#{item.chkDir}"></h:selectBooleanCheckbox>
					            </rich:column>
					            <rich:column width="500">
					            	<f:facet name="header">
                                        <h:outputText value="Dirección"/>
                                    </f:facet>
                                    <a4j:commandLink id="lnkDomicilio" value="#{item.strDireccion}" actionListener="#{domicilioController.viewDomicilio}" style="color:blue" reRender="pgFormDomicilio">
                                    	<rich:componentControl for="pAgregarDomicilio" attachTo="lnkDomicilio" operation="show" event="onclick"/>
			                       		<f:param id="idPersona" 	name="pIntIdPersona" 	value="#{item.intIdPersona}" />
			                       		<f:param id="idDomicilio" 	name="pIntIdDomicilio" 	value="#{item.intIdDireccion}" />
			                        </a4j:commandLink>
					            	<%--<h:outputText id="lblValDireccion" value="#{item.strDireccion}"/>--%>
					            </rich:column>
			                </rich:dataTable>
			         	</h:panelGrid>
		         		
			         	<h:panelGrid id="pComunicacion" columns="4">
			         		<rich:column style="border:none;">
			         			<h:selectBooleanCheckbox value="#{beneficiarioController.chkComunicacion}">
			         				<a4j:support event="onclick" actionListener="#{beneficiarioController.enableDisableControls}" reRender="pComunicacion"/>
			         			</h:selectBooleanCheckbox>
			         		</rich:column>
			         		<rich:column style="border:none;" width="120px">
			         			<h:outputText value="Comunicaciones"/>
			         		</rich:column>
			         		<rich:column style="border:none;padding-left:10px;">
			                  	<a4j:commandButton id="btnAgregarComunicacion" value="Agregar" styleClass="btnEstilos" disabled="#{beneficiarioController.formEnableDisableComunicacion}" action="#{comunicacionController.limpiarFormComunicacion}">
			                		<rich:componentControl for="pAgregarComunicacion" attachTo="btnAgregarComunicacion" operation="show" event="onclick"/>
			                	</a4j:commandButton>
			         		</rich:column>
			         		<rich:column style="border:none;">
			                  	<a4j:commandButton id="btnEliminarComunicacion" value="Eliminar" actionListener="#{comunicacionController.removeComunicacion}" disabled="#{beneficiarioController.formEnableDisableComunicacion}" reRender="pgListComunicacion" styleClass="btnEstilos"/>
			         		</rich:column>
			         	</h:panelGrid>
			         	
			         	<h:panelGrid id="pgListComunicacion">
			         		<rich:dataTable id="edtComunicacion" value="#{comunicacionController.beanListComunicacion}"
			         			rendered="#{not empty comunicacionController.beanListComunicacion}"
			         			rows="5" var="item" rowKeyVar="rowKey" sortMode="single" width="500px">
			                	<rich:column width="15px;">
					                <h:selectBooleanCheckbox value="#{item.chkCom}"/>
					            </rich:column>
					            <rich:column width="500">
					            	<f:facet name="header">
                                        <h:outputText value="Comunicación"/>
                                    </f:facet>
					            	<a4j:commandLink id="lnkComunicacion" value="#{item.strDescCom}" actionListener="#{comunicacionController.viewComunicacion}" style="color:blue" reRender="pgFormComunicacion,pgFormComunic">
                                    	<rich:componentControl for="pAgregarComunicacion" attachTo="lnkComunicacion" operation="show" event="onclick"/>
			                       		<f:param name="pIntIdPersona" 		value="#{item.intIdPersona}" />
			                       		<f:param name="pIntIdComunicacion" 	value="#{item.intIdComunicacion}" />
			                        </a4j:commandLink>
					            	<%--<h:outputText value="#{item.strDescCom}"/>--%>
					            </rich:column>
			                </rich:dataTable>
			         	</h:panelGrid>
			        </rich:panel>
	         </rich:panel>
       	 </h:panelGrid>
         </rich:panel>
    </h:form>