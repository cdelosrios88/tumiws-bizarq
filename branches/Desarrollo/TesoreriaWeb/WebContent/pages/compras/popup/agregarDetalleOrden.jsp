<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	
<rich:modalPanel id="pAgregarDetalleOrden" width="870" height="320"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Agregar Detalle Orden"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pAgregarDetalleOrden" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>	
	<h:form id="fAgregarDetalleOrden">
	   
	    <rich:spacer height="3px"/>
	    

		<h:panelGrid columns="8">
			<rich:column width=	"130">
				<h:outputText value="Cantidad : "/>
			</rich:column>
			<rich:column width="200">
				<h:inputText size="32" 
					rendered="#{empty ordenController.ordenCompraDetalle.id.intItemOrdenCompraDetalle}"
					onkeypress="return soloNumerosDecimalesPositivos(this)"
					value="#{ordenController.ordenCompraDetalle.bdCantidad}"/>
				<h:inputText size="32"
					disabled="true" 
					rendered="#{not empty ordenController.ordenCompraDetalle.id.intItemOrdenCompraDetalle}"
					value="#{ordenController.ordenCompraDetalle.bdCantidad}">
					<f:converter converterId="ConvertidorMontos"/>
				</h:inputText>	
			</rich:column>
			<rich:column width=	"110">
				<h:outputText value="Unidad de Medida : "/>
			</rich:column>
			<rich:column width="150">
				<h:selectOneMenu
					disabled="#{not empty ordenController.ordenCompraDetalle.id.intItemOrdenCompraDetalle}"
					value="#{ordenController.ordenCompraDetalle.intParaUnidadMedida}"
					style="width: 150px;">
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_UNIDADMEDIDA}"
						itemValue="#{sel.intIdDetalle}"
						itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
		
	    <h:panelGrid columns="8">
			<rich:column width=	"130">
				<h:outputText value="Precio Unitario : "/>
			</rich:column>
			<rich:column width="200">
				<h:inputText size="32" 
					rendered="#{empty ordenController.ordenCompraDetalle.id.intItemOrdenCompraDetalle}"
					onkeypress="return soloNumerosDecimalesPositivos(this)"
					value="#{ordenController.ordenCompraDetalle.bdPrecioUnitario}"/>
				<h:inputText size="32" 
					readonly="true"
					style="background-color: #BFBFBF;"
					rendered="#{not empty ordenController.ordenCompraDetalle.id.intItemOrdenCompraDetalle}"
					value="#{ordenController.ordenCompraDetalle.bdPrecioUnitario}">
					<f:converter converterId="ConvertidorMontos"/>
				</h:inputText>
			</rich:column>
			<rich:column width="260">
				<h:selectOneMenu
					disabled="true"
					value="#{ordenController.ordenCompraDetalle.intParaTipoMoneda}"
					style="width: 267px;">
					<f:selectItem itemValue="1" itemLabel="Soles"/>
					<f:selectItem itemValue="2" itemLabel="Dolares"/>
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>

		
		<h:panelGrid columns="6">
			<rich:column width="130">
				<h:outputText value="Estado de Afectación : "/>
			</rich:column>
			<rich:column width="200">
				<h:selectOneMenu
					disabled="#{not empty ordenController.ordenCompraDetalle.id.intItemOrdenCompraDetalle}"
					value="#{ordenController.ordenCompraDetalle.intAfectoIGV}"
					style="width: 190px;">
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_ESTADOAFECTACION}"
						itemValue="#{sel.intIdDetalle}"
						itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="6">
			<rich:column width="130" style="vertical-align: top">
				<h:outputText value="Descripción : "/>
			</rich:column>
			<rich:column width="330">
				<h:inputTextarea cols="86" rows="2"
					disabled="#{not empty ordenController.ordenCompraDetalle.id.intItemOrdenCompraDetalle}"
					value="#{ordenController.ordenCompraDetalle.strDescripcion}"/>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="8" id="panelCuentaContableOD">
			<rich:column width=	"130">
				<h:outputText value="Tipo de Gasto : "/>
			</rich:column>
			<rich:column width="530">
				<h:inputText 
					rendered="#{not empty ordenController.ordenCompraDetalle.planCuenta}"
					value="#{ordenController.ordenCompraDetalle.planCuenta.id.intPeriodoCuenta} - 
					#{ordenController.ordenCompraDetalle.planCuenta.id.strNumeroCuenta} - #{ordenController.ordenCompraDetalle.planCuenta.strDescripcion}"
					size="86"
					readonly="true"
					style="background-color: #BFBFBF;"/>
				<h:inputText 
					rendered="#{empty ordenController.ordenCompraDetalle.planCuenta}"
					size="86"
					readonly="true"
					style="background-color: #BFBFBF;"/>
			</rich:column>
			<rich:column width=	"150">
				<a4j:commandButton styleClass="btnEstilos"
					disabled="#{not empty ordenController.ordenCompraDetalle.id.intItemOrdenCompraDetalle}"
					value="Buscar Cuenta"
	                reRender="pBuscarPlanCuentaOrden"
	                oncomplete="Richfaces.showModalPanel('pBuscarPlanCuentaOrden')"
	                action="#{ordenController.abrirPopUpBuscarPlanCuenta}" 
	                style="width:150px"/>				 	
			</rich:column>
		</h:panelGrid>
		

		
		<h:panelGrid columns="8" id="panelSucursalOD">
			<rich:column width="130">
				<h:outputText value="Sucursal :"/>
			</rich:column>
			<rich:column width="200">
				<h:selectOneMenu
					disabled="#{not empty ordenController.ordenCompraDetalle.id.intItemOrdenCompraDetalle ||
								ordenController.blnHabilitarDetOrden}"
					value="#{ordenController.ordenCompraDetalle.intSucuIdSucursal}"
					style="width: 190px;">
					<tumih:selectItems var="sel"
						value="#{ordenController.listaSucursal}"
						itemValue="#{sel.id.intIdSucursal}"
						itemLabel="#{sel.juridica.strRazonSocial}"/>
					<a4j:support event="onchange" 
						action="#{ordenController.seleccionarSucursalDetalle}" 
						reRender="panelSucursalOD"  />	
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="110">
				<h:outputText value="Subsucursal : "/>
			</rich:column>
			<rich:column width="150">
				<h:selectOneMenu
					disabled="#{not empty ordenController.ordenCompraDetalle.id.intItemOrdenCompraDetalle ||
								ordenController.blnHabilitarDetOrden}"
					value="#{ordenController.ordenCompraDetalle.intSudeIdSubsucursal}"
					style="width: 150px;">
					<f:selectItem itemValue="0" itemLabel="Seleccione"/>
					<tumih:selectItems var="sel"
						value="#{ordenController.ordenCompraDetalle.sucursal.listaSubSucursal}"
						itemValue="#{sel.id.intIdSubSucursal}"
						itemLabel="#{sel.strDescripcion}"/>	
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="50">
				<h:outputText value="Área : "/>
			</rich:column>
			<rich:column width="150">
				<h:selectOneMenu
					disabled="#{not empty ordenController.ordenCompraDetalle.id.intItemOrdenCompraDetalle}"
					value="#{ordenController.ordenCompraDetalle.intIdArea}"
					style="width: 150px;">
					<f:selectItem itemValue="0" itemLabel="Seleccione"/>
					<tumih:selectItems var="sel"
						value="#{ordenController.ordenCompraDetalle.sucursal.listaArea}"
						itemValue="#{sel.id.intIdArea}"
						itemLabel="#{sel.strDescripcion}"/>	
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
		

		
		<h:panelGrid columns="6">
			<rich:column width="130" style="vertical-align: top">
				<h:outputText value="Observación : "/>
			</rich:column>
			<rich:column width="330">
				<h:inputTextarea cols="86" rows="2" 
					disabled="#{not empty ordenController.ordenCompraDetalle.id.intItemOrdenCompraDetalle}"
					value="#{ordenController.ordenCompraDetalle.strObservacion}"/>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="5px"/>
		<h:outputText value="#{ordenController.strMensajeDetalle}" 
			styleClass="msgError"
			style="font-weight:bold"
			rendered="#{not empty ordenController.strMensajeDetalle}"/>
		<rich:spacer height="10px"
			rendered="#{empty ordenController.strMensajeDetalle}"/>
		
		<h:panelGrid columns="6">
			<rich:column width="250" >				
			</rich:column>
			<rich:column width="120" style="text-align: left;">
                <a4j:commandButton styleClass="btnEstilos"
                	disabled="#{not empty ordenController.ordenCompraDetalle.id.intItemOrdenCompraDetalle}"
                	value="Aceptar"
                	oncomplete="if(#{ordenController.mostrarMensajeDetalle}){rendPopUp2();}else{Richfaces.hideModalPanel('pAgregarDetalleOrden');rendTabla2();}"
                   	action="#{ordenController.agregarOrdenCompraDetalle}" 
                   	style="width:120px"/>
            </rich:column>
            <rich:column width="30">				
			</rich:column>
			<rich:column width="120" style="text-align: left;">
                <a4j:commandButton styleClass="btnEstilos"
                	value="Cancelar"
                	onclick="Richfaces.hideModalPanel('pAgregarDetalleOrden')"
                   	style="width:120px"/>
            </rich:column>
    	</h:panelGrid>
    	

    	<a4j:jsFunction name="rendTabla2" reRender="panelDetalleO,panelDocumentoO" ajaxSingle="true"/>
		<a4j:jsFunction name="rendPopUp2" reRender="fAgregarDetalleOrden" ajaxSingle="true"/>					
	</h:form>
</rich:modalPanel>	