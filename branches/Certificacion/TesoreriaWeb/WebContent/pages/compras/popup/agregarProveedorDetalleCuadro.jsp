<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	
<rich:modalPanel id="pAgregarProveedorDetalleCuadro" width="550" height="190"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Agregar Producto / Servicio"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pAgregarProveedorDetalleCuadro" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>	
	<h:form id="fAgregarProveedorDetalleCuadro">
	   
	    <rich:spacer height="3px"/>
	    
	    <h:panelGrid columns="6">
			<rich:column width="80">
				<h:outputText value="Descripción :"/>
			</rich:column>
			<rich:column width="440">
				<h:inputText size="75" 
					value="#{cuadroController.cuadroComparativoProveedorDetalleNuevo.strDetalle}"/>
			</rich:column>
		</h:panelGrid>
		
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="6">
			<rich:column width="80">
				<h:outputText value="Marca : "/>
			</rich:column>
			<rich:column width="200">
				<h:inputText size="28" 
					value="#{cuadroController.cuadroComparativoProveedorDetalleNuevo.strMarca}"/>
			</rich:column>
			<rich:column width="90">
				<h:outputText value="Cantidad : "/>
			</rich:column>
			<rich:column width="150">
				<h:inputText size="20"
					onkeypress="return soloNumerosDecimales(this)"
					value="#{cuadroController.cuadroComparativoProveedorDetalleNuevo.bdCantidad}"/>
			</rich:column>
		</h:panelGrid>
			
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="6">
			<rich:column width="80">
				<h:outputText value="Moneda : "/>
			</rich:column>
			<rich:column width="200">
				<h:selectOneMenu
					disabled="#{not empty cuadroController.cuadroComparativoProveedorNuevo.listaCuadroComparativoProveedorDetalle}"
					value="#{cuadroController.cuadroComparativoProveedorDetalleNuevo.intParaTipoMoneda}"
					style="width: 170px;">
					<f:selectItem itemValue="1" itemLabel="Soles"/>
					<f:selectItem itemValue="2" itemLabel="Dolares"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column width="95">
				<h:outputText value="Precio Unitario : "/>
			</rich:column>
			<rich:column width="150">
				<h:inputText size="20"
					onkeypress="return soloNumerosDecimales(this)"
					value="#{cuadroController.cuadroComparativoProveedorDetalleNuevo.bdPrecioUnitario}"/>
			</rich:column>
		</h:panelGrid>
		
		
		<rich:spacer height="10px"/>
		<h:outputText value="#{cuadroController.strMensajeProveedorDetalle}" 
			styleClass="msgError"
			style="font-weight:bold"
			rendered="#{not empty cuadroController.strMensajeProveedorDetalle}"/>
		<rich:spacer height="10px"
			rendered="#{empty cuadroController.strMensajeProveedorDetalle}"/>
		
		<h:panelGrid columns="6">
			<rich:column width="120" >				
			</rich:column>
			<rich:column width="120" style="text-align: left;">
                <a4j:commandButton styleClass="btnEstilos"
                	value="Aceptar"
                	oncomplete="if(#{cuadroController.mostrarMensajeProveedorDetalle}){rendPopUp2();}else{Richfaces.hideModalPanel('pAgregarProveedorDetalleCuadro');rendTabla2();}"
                	action="#{cuadroController.agregarProveedorDetalle}" 
                   	style="width:120px"/>
            </rich:column>
            <rich:column width="30">
			</rich:column>
			<rich:column width="120" style="text-align: left;">
                <a4j:commandButton styleClass="btnEstilos"
                	value="Cancelar"
                	onclick="Richfaces.hideModalPanel('pAgregarProveedorDetalleCuadro')"
                   	style="width:120px"/>
            </rich:column>
    	</h:panelGrid>
    	
    	<a4j:jsFunction name="rendTabla2" reRender="panelDetalleCC" ajaxSingle="true"/>
		<a4j:jsFunction name="rendPopUp2" reRender="fAgregarProveedorDetalleCuadro" ajaxSingle="true"/>		
	</h:form>
</rich:modalPanel>	