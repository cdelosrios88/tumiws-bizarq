<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	
<rich:modalPanel id="pAgregarDocumentoOrden" width="860" height="280"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Agregar Documento Orden"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pAgregarDocumentoOrden" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>	
	<h:form id="fAgregarDocumentoOrden">
	   
	    <rich:spacer height="3px"/>
	    

		<h:panelGrid columns="8">
			<rich:column width=	"130">
				<h:outputText value="Tipo de Documento : "/>
			</rich:column>
			<rich:column width="210">
				<h:selectOneMenu
					disabled="#{not empty ordenController.ordenCompraDocumento.id.intItemOrdenCompra}"
					value="#{ordenController.ordenCompraDocumento.intParaDocumentoGeneral}"
					style="width: 210px;">
					<tumih:selectItems var="sel"
						value="#{ordenController.listaTablaDocumentoGeneral}"
						itemValue="#{sel.intIdDetalle}"
						itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="90">
				<h:outputText value="Fecha : "/>
			</rich:column>
			<rich:column width="250">
				<rich:calendar datePattern="dd/MM/yyyy"  
					disabled="#{not empty ordenController.ordenCompraDocumento.id.intItemOrdenCompra}"
					value="#{ordenController.ordenCompraDocumento.tsFechaDocumento}"
					converter="calendarTimestampConverter"
					inputSize="20" 
					showApplyButton="true"/>
			</rich:column>
		</h:panelGrid>
		
	    <h:panelGrid columns="8">
			<rich:column width=	"130">
				<h:outputText value="Tipo de Cancelación : "/>
			</rich:column>
			<rich:column width="210">
				<h:selectOneMenu
					disabled="#{not empty ordenController.ordenCompraDocumento.id.intItemOrdenCompra}"
					value="#{ordenController.ordenCompraDocumento.intParaTipoCancelacion}"
					style="width: 210px;">
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_CANCELACIONORDEN}"
						itemValue="#{sel.intIdDetalle}"
						itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="90">
				<h:outputText value="Monto :"/>
			</rich:column>
			<rich:column width="150">
				<h:inputText size="24"
					disabled="#{not empty ordenController.ordenCompraDocumento.id.intItemOrdenCompra}"
					rendered="#{empty ordenController.ordenCompraDocumento.id.intItemOrdenCompra}"
					onkeypress="return soloNumerosDecimales(this)" 
					value="#{ordenController.ordenCompraDocumento.bdMontoDocumento}"/>
				<h:inputText size="24"
					disabled="#{not empty ordenController.ordenCompraDocumento.id.intItemOrdenCompra}"
					rendered="#{not empty ordenController.ordenCompraDocumento.id.intItemOrdenCompra}"
					value="#{ordenController.ordenCompraDocumento.bdMontoDocumento}">
					<f:converter converterId="ConvertidorMontos"/>
				</h:inputText>	
			</rich:column>
			<rich:column width="50">				
				<h:outputText value="Moneda :"/>
			</rich:column>
			<rich:column width="150">
				<h:selectOneMenu					
					disabled="true"
					value="#{ordenController.ordenCompraDocumento.intParaTipoMoneda}"
					style="width: 150px;">
					<f:selectItem itemValue="1" itemLabel="Soles"/>
					<f:selectItem itemValue="2" itemLabel="Dolares"/>
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>

		<h:panelGrid columns="8" id="panelSucursalODD">
			<rich:column width="130">
				<h:outputText value="Sucursal :"/>
			</rich:column>
			<rich:column width="210">
				<h:selectOneMenu
					disabled="#{not empty ordenController.ordenCompraDocumento.id.intItemOrdenCompra}"
					value="#{ordenController.ordenCompraDocumento.intSucuIdSucursal}"
					style="width: 210px;">
					<tumih:selectItems var="sel"
						value="#{ordenController.listaSucursal}"
						itemValue="#{sel.id.intIdSucursal}"
						itemLabel="#{sel.juridica.strRazonSocial}"/>
					<a4j:support event="onchange"
						action="#{ordenController.seleccionarSucursalDocumento}" 
						reRender="panelSucursalODD"  />	
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="90">
				<h:outputText value="Subsucursal : "/>
			</rich:column>
			<rich:column width="150">
				<h:selectOneMenu
					disabled="#{not empty ordenController.ordenCompraDocumento.id.intItemOrdenCompra}"
					value="#{ordenController.ordenCompraDocumento.intSudeIdSubsucursal}"
					style="width: 150px;">
					<f:selectItem itemValue="0" itemLabel="Seleccione"/>
					<tumih:selectItems var="sel"
						value="#{ordenController.ordenCompraDocumento.sucursal.listaSubSucursal}"
						itemValue="#{sel.id.intIdSubSucursal}"
						itemLabel="#{sel.strDescripcion}"/>	
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="50">
				<h:outputText value="Área : "/>
			</rich:column>
			<rich:column width="150">
				<h:selectOneMenu
					disabled="#{not empty ordenController.ordenCompraDocumento.id.intItemOrdenCompra}"
					value="#{ordenController.ordenCompraDocumento.intIdArea}"
					style="width: 150px;">
					<f:selectItem itemValue="0" itemLabel="Seleccione"/>
					<tumih:selectItems var="sel"
						value="#{ordenController.ordenCompraDocumento.sucursal.listaArea}"
						itemValue="#{sel.id.intIdArea}"
						itemLabel="#{sel.strDescripcion}"/>	
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="8">
			<rich:column width="130">
				<h:outputText value="Pagado :"/>
			</rich:column>
			<rich:column width="210">
				<h:inputText
					size="36"
					readonly="true"
					style="background-color: #BFBFBF;"
					value="#{ordenController.ordenCompraDocumento.bdMontoPagado}">
					<f:converter converterId="ConvertidorMontos"/>
				</h:inputText>
			</rich:column>
			<rich:column width="90">
				<h:outputText value="Ingresado : "/>
			</rich:column>
			<rich:column width="150">
				<h:inputText
					size="24"
					readonly="true"
					style="background-color: #BFBFBF;"
					value="#{ordenController.ordenCompraDocumento.bdMontoIngresado}">
					<f:converter converterId="ConvertidorMontos"/>						
				</h:inputText>
			</rich:column>
			<rich:column width="50">
				<h:outputText value="Saldo : "/>
			</rich:column>
			<rich:column width="150">
				<h:inputText
					size="24"
					readonly="true"
					style="background-color: #BFBFBF;"
					value="#{ordenController.ordenCompraDocumento.bdMontoPagado - ordenController.ordenCompraDocumento.bdMontoIngresado}">
					<f:converter converterId="ConvertidorMontos"/>						
				</h:inputText>
			</rich:column>
		</h:panelGrid>

		
		<h:panelGrid columns="6">
			<rich:column width="130" style="vertical-align: top">
				<h:outputText value="Observación : "/>
			</rich:column>
			<rich:column width="330">
				<h:inputTextarea cols="84" rows="2"
					disabled="#{not empty ordenController.ordenCompraDocumento.id.intItemOrdenCompra}"
					value="#{ordenController.ordenCompraDocumento.strObservacion}"/>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="20px"/>
		<h:outputText value="#{ordenController.strMensajeDocumento}" 
			styleClass="msgError"
			style="font-weight:bold"
			rendered="#{not empty ordenController.strMensajeDocumento}"/>
		<rich:spacer height="10px"
			rendered="#{empty ordenController.strMensajeDocumento}"/>
		
		<h:panelGrid columns="6">
			<rich:column width="250" >				
			</rich:column>
			<rich:column width="120" style="text-align: left;">
                <a4j:commandButton styleClass="btnEstilos"
                	disabled="#{not empty ordenController.ordenCompraDocumento.id.intItemOrdenCompra}"
                	value="Aceptar"
                	oncomplete="if(#{ordenController.mostrarMensajeDocumento}){rendPopUp3();}else{Richfaces.hideModalPanel('pAgregarDocumentoOrden');rendTabla3();}"
                   	action="#{ordenController.agregarOrdenCompraDocumento}" 
                   	style="width:120px"/>
            </rich:column>
            <rich:column width="30">				
			</rich:column>
			<rich:column width="120" style="text-align: left;">
                <a4j:commandButton styleClass="btnEstilos"
                	value="Cancelar"
                	onclick="Richfaces.hideModalPanel('pAgregarDocumentoOrden')"
                   	style="width:120px"/>
            </rich:column>
    	</h:panelGrid>
    	

    	<a4j:jsFunction name="rendTabla3" reRender="panelDocumentoO" ajaxSingle="true"/>
		<a4j:jsFunction name="rendPopUp3" reRender="fAgregarDocumentoOrden" ajaxSingle="true"/>					
	</h:form>
</rich:modalPanel>	