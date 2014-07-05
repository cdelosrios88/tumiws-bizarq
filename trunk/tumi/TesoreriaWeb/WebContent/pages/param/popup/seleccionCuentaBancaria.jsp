<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	
	<h:form id="frmSeleccionCuentaBancaria">
	   	<h:panelGroup id="divSeleccionCuentaBancaria" layout="block">
	    	
	    	<h:panelGrid columns="5">
	    		<rich:column width="150">
           			<h:outputText value="Nombre de Cuenta : "></h:outputText>
           		</rich:column>
           		<rich:column width="250">
           			<h:inputText value="#{bancoFondoController.bancoCuentaAgregar.strNombrecuenta}" size="45" onkeypress="return soloLetras(event)"/>
           		</rich:column> 		
	    	</h:panelGrid>
		
	    	<h:panelGrid columns="5">
	    		<rich:column width="150">
           			<h:outputText value="Tipo de Cuenta : "></h:outputText>
           		</rich:column>
           		<rich:column width="250">
           			<h:selectOneMenu style="width: 250px;" 
           				value="#{bancoFondoController.bancoCuentaAgregar.cuentaBancaria.intTipoCuentaCod}">           				
           				<tumih:selectItems var="sel" 
           					cache="#{applicationScope.Constante.PARAM_T_CUENTABANCARIA}" 
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"/>	
           			</h:selectOneMenu>
           		</rich:column>	
	    	</h:panelGrid>
	    	
	    	<h:panelGrid columns="5">
	    		<rich:column width="150">
           			<h:outputText value="Tipo de Moneda : "></h:outputText>
           		</rich:column>
           		<rich:column width="250">
           			<h:selectOneMenu style="width: 250px;" 
           				value="#{bancoFondoController.bancoCuentaAgregar.cuentaBancaria.intMonedaCod}">           				
           				<tumih:selectItems var="sel" 
           					cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
							itemValue="#{sel.intIdDetalle}" 
							itemLabel="#{sel.strDescripcion}"/>	
           			</h:selectOneMenu>
           		</rich:column> 		
	    	</h:panelGrid>
	    	
	    	<h:panelGrid columns="5">
	    		<rich:column width="150">
           			<h:outputText value="Numero de Cuenta : "></h:outputText>
           		</rich:column>
           		<rich:column width="250">
           			<h:inputText value="#{bancoFondoController.bancoCuentaAgregar.cuentaBancaria.strNroCuentaBancaria}" size="45"
           				/>
           		</rich:column> 		
	    	</h:panelGrid>
	    	
	    	<h:panelGrid columns="5">
	    		<rich:column width="150">
           			<h:outputText value="Cuenta Contable: "></h:outputText>
           		</rich:column>
           		<rich:column width="250">
           			<h:inputText value="#{bancoFondoController.bancoCuentaAgregar.strNumerocuenta}" size="45" readonly="true"/>
           		</rich:column>
           		<rich:column width="70">           			
                    <a4j:commandButton styleClass="btnEstilos"
						value="Agregar"
				        actionListener="#{bancoFondoController.abrirPopUpPlanCuenta}"
						reRender="pSeleccionPlanCuenta"
						oncomplete="Richfaces.showModalPanel('pSeleccionPlanCuenta')"
						disabled="#{bancoFondoController.deshabilitarNuevo}"
						style="width:70px">
						<f:attribute name="tipo" value="#{applicationScope.Constante.BANCOCUENTA}"/>
					</a4j:commandButton>
           		</rich:column>
	    	</h:panelGrid>
	    	
	    	<h:panelGrid columns="5">
	    		<rich:column width="150">
           			<h:outputText value="Monto Sobregiro : "></h:outputText>
           		</rich:column>
           		<rich:column width="250">
           			<h:inputText value="#{bancoFondoController.bancoCuentaAgregar.bdMontosobregiro}" size="45"
           				onkeypress="return soloNumerosDecimales(this)"/>
           		</rich:column> 		
	    	</h:panelGrid>
	    	
	    	<h:panelGrid columns="5">
	    		<rich:column width="150">
           			<h:outputText value="Observación : "></h:outputText>
           		</rich:column>
           		<rich:column width="250">
           			<h:inputTextarea cols="46" 
						value="#{bancoFondoController.bancoCuentaAgregar.cuentaBancaria.strObservacion}" rows="2"/>
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
				<rich:column width="150">
			    </rich:column>
			    <rich:column style="border:none" id="colBtnModificar">
			    	<a4j:commandButton value="Aceptar" styleClass="btnEstilos"
			    		action="#{bancoFondoController.aceptarSeleccionBancoCuenta}"
		        		oncomplete="if(#{bancoFondoController.mostrarMensajePopUp}){rendPopUp1();}else{Richfaces.hideModalPanel('pSeleccionCuentaBancaria');rendTabla1();}"/>
		        	<a4j:commandButton value="Cancelar" styleClass="btnEstilos"
		           		onclick="Richfaces.hideModalPanel('pSeleccionCuentaBancaria')"/>
			 	</rich:column>
			</h:panelGrid>
			
			<a4j:jsFunction name="rendTabla1" reRender="panelListaBancoCuenta,panelListaBancoCuentaCheques" ajaxSingle="true"/>
			<a4j:jsFunction name="rendPopUp1" reRender="frmSeleccionFondoCuenta" ajaxSingle="true"/>
	   	</h:panelGroup>
	</h:form>