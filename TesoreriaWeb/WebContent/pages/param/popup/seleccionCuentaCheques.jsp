<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	
	<h:form id="frmSeleccionCuentaCheques">
	   	<h:panelGroup layout="block">
	    	
	    	<h:panelGrid columns="5">
	    		<rich:column width="130">
           			<h:outputText value="Cuenta Bancaria : "></h:outputText>
           		</rich:column>
           		<rich:column width="350">
           			<h:selectOneMenu style="width: 350px;" 
           				disabled="#{bancoFondoController.habilitarBancoCuentaCheque}"
           				value="#{bancoFondoController.intItemInterfazSeleccionado}">
           				<f:selectItem itemValue="0" itemLabel="Seleccionar"/>
           				<tumih:selectItems var="sel"
							value="#{bancoFondoController.listaBancoCuenta}" 
							itemValue="#{sel.intItemInterfaz}"
							itemLabel="#{sel.cuentaBancaria.strNroCuentaBancaria}"/>
						<a4j:support event="onchange" action="#{bancoFondoController.seleccionarCuentaBancaria}" 
							reRender="frmSeleccionCuentaCheques"/>
           			</h:selectOneMenu>
           		</rich:column> 		
	    	</h:panelGrid>
	    	
	    	<h:panelGrid columns="6">
	    		<rich:column width="130">
           			<h:outputText value="Serie : "></h:outputText>
           		</rich:column>
           		<rich:column width="120">
           			<h:inputText size="20" disabled="#{!bancoFondoController.habilitarBancoCuentaCheque}"
           				value="#{bancoFondoController.bancoCuentaChequeAgregar.strSerie}"/>
           		</rich:column>
           		<rich:column width="75" style="text-align: left">
           			<h:outputText value="Nro. Control : "></h:outputText>
           		</rich:column>
           		<rich:column width="135">
           			<h:inputText value="30" size="22" disabled="#{!bancoFondoController.habilitarBancoCuentaCheque}"
           				value="#{bancoFondoController.bancoCuentaChequeAgregar.strControl}"/>
           		</rich:column>
	    	</h:panelGrid>
	    	
	    	<h:panelGrid columns="6">
	    		<rich:column width="130">
           			<h:outputText value="Desde : "></h:outputText>
           		</rich:column>
           		<rich:column width="120">
           			<h:inputText size="20" disabled="#{!bancoFondoController.habilitarBancoCuentaCheque}"
           				value="#{bancoFondoController.bancoCuentaChequeAgregar.intNumeroinicio}"
           				onkeypress="return soloNumerosNaturales(event)"/>
           		</rich:column>
           		<rich:column width="75" style="text-align: left">
           			<h:outputText value="Hasta : "></h:outputText>
           		</rich:column>
           		<rich:column width="135">
           			<h:inputText size="22" disabled="#{!bancoFondoController.habilitarBancoCuentaCheque}"
           				value="#{bancoFondoController.bancoCuentaChequeAgregar.intNumerofin}"
           				onkeypress="return soloNumerosNaturales(event)"/>
           		</rich:column>
	    	</h:panelGrid>
	    	
	    	<h:panelGrid columns="5">
	    		<rich:column width="130">
           			<h:outputText value="Número Actual : "></h:outputText>
           		</rich:column>
           		<rich:column width="120">
           			<h:inputText size="20" disabled="true"/>
           		</rich:column>
	    	</h:panelGrid>
	    	
	    	<h:panelGrid columns="5">
	    		<rich:column width="130">
           			<h:outputText value="Adjuntar Formato : "></h:outputText>
           		</rich:column>
           		<rich:column width="350">
           			<h:inputText size="65" value="#{bancoFondoController.bancoCuentaChequeAgregar.archivo.strNombrearchivo}"
						readonly="true" style="background-color: #BFBFBF;"/>
           		</rich:column>
           	</h:panelGrid>
	    	
	    	<h:panelGrid columns="5">
	    		<rich:column width="130">           			
           		</rich:column>
           		<rich:column width="350">
           			<rich:fileUpload id="uploadDBF" 
	            			addControlLabel="Adjuntar Archivo"
							clearControlLabel="Limpiar" 
							cancelEntryControlLabel="Cancelar"
							uploadControlLabel="Subir Archivo" 
							listHeight="65" 
							listWidth="345"
							fileUploadListener="#{bancoFondoController.manejarSubirArchivo}"
							maxFilesQuantity="1"
							doneLabel="Archivo cargado correctamente"
							immediateUpload="false"
							disabled="#{!bancoFondoController.habilitarBancoCuentaCheque}">
							<f:facet name="label">
								<h:outputText value="{_KB}KB de {KB}KB cargados --- {mm}:{ss}" />
							</f:facet>
							<a4j:support event="onuploadcomplete" reRender="cboTipoArchivo"/>								
					</rich:fileUpload>
           		</rich:column>	
	    	</h:panelGrid>
	    	
	    	<h:panelGrid columns="5" id="panelChequesDiferidos">
	    		<rich:column width="130">
           			<h:outputText value="Cheques diferidos : "></h:outputText>
           		</rich:column>
           		<rich:column width="270">
           			<h:inputText value="30" size="48" disabled="#{!bancoFondoController.habilitarBancoCuentaCheque}" readonly="true"
           				value="#{bancoFondoController.bancoCuentaChequeAgregar.strNumerocuenta}"/>
           		</rich:column>
           		<rich:column width="70">
           			<a4j:commandButton styleClass="btnEstilos"
                		value="Agregar"
                		actionListener="#{bancoFondoController.abrirPopUpPlanCuenta}"
						reRender="pSeleccionPlanCuenta"
						oncomplete="Richfaces.showModalPanel('pSeleccionPlanCuenta')"
						disabled="#{!bancoFondoController.habilitarBancoCuentaCheque}"
                    	style="width:70px">
                    	<f:attribute name="tipo" value="#{applicationScope.Constante.BANCOCUENTACHEQUE}"/>
                    </a4j:commandButton>
           		</rich:column>
	    	</h:panelGrid>
	    	
	    	<h:panelGrid columns="5">
	    		<rich:column width="130">
           			<h:outputText value="Idioma de montos : "></h:outputText>
           		</rich:column>
           		<rich:column width="270">
           			<h:selectOneMenu style="width: 265px;" 
           				value="#{bancoFondoController.bancoCuentaChequeAgregar.intIdiomaCod}"
           				disabled="#{!bancoFondoController.habilitarBancoCuentaCheque}">
           				<tumih:selectItems var="sel"
							cache="#{applicationScope.Constante.PARAM_T_IDIOMA}" 
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"/>
           			</h:selectOneMenu>
           		</rich:column>
           		<rich:column width="70">
           			Usado en impresión de cheques
           		</rich:column>	
	    	</h:panelGrid>
	    	
	    	<h:panelGrid columns="5">
	    		<rich:column width="130">
           			<h:outputText value="Observación : "></h:outputText>
           		</rich:column>
           		<rich:column width="350">
           			<h:inputTextarea cols="65" 
           				value="#{bancoFondoController.bancoCuentaChequeAgregar.strObservacion}"
						disabled="#{!bancoFondoController.habilitarBancoCuentaCheque}" rows="2"/>
           		</rich:column> 		
	    	</h:panelGrid>
	    	
	    	<h:panelGroup rendered="#{bancoFondoController.mostrarMensajePopUp}">
	    		<h:outputText value="#{bancoFondoController.mensajePopUp}" 
					styleClass="msgError"
					style="font-weight:bold"/>
	    	</h:panelGroup>
	    	<h:panelGroup rendered="#{!bancoFondoController.mostrarMensajePopUp}">
	    		<rich:spacer height="20px"/>
	    	</h:panelGroup>	
	    	
	    	<h:panelGrid columns="2">
				<rich:column width="180">
			    </rich:column>
			    <rich:column style="border:none" id="colBtnModificar">
			    	<a4j:commandButton value="Aceptar" styleClass="btnEstilos"
			    		action="#{bancoFondoController.aceptarSeleccionBancoCuentaCheque}"
		        		oncomplete="if(#{bancoFondoController.mostrarMensajePopUp}){rendPopUp2();}else{Richfaces.hideModalPanel('pSeleccionCuentaCheques');rendTabla2();}"
		        		disabled="#{!bancoFondoController.habilitarBancoCuentaCheque}"/>
		        	<a4j:commandButton value="Cancelar" styleClass="btnEstilos"
		           		onclick="Richfaces.hideModalPanel('pSeleccionCuentaCheques')"/>
			 	</rich:column>
			</h:panelGrid>
			
			<a4j:jsFunction name="rendTabla2" reRender="panelListaBancoCuentaCheques" ajaxSingle="true"/>
			<a4j:jsFunction name="rendPopUp2" reRender="frmSeleccionFondoCuenta" ajaxSingle="true"/>
	   	</h:panelGroup>
	</h:form>