<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<rich:modalPanel id="pAgregarRequisicionDetalle" width="480" height="220"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Agregar Requisición Detalle"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pAgregarRequisicionDetalle" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
	<h:form>
	    	
		<rich:spacer height="3px"/>
		
		<h:panelGrid columns="6">
			<rich:column width="100">
				<h:outputText value="Cantidad : "/>
			</rich:column>
			<rich:column width="300">
				<h:inputText size="55" 
					value="#{requisicionController.requisicionDetalleNuevo.intCantidad}"
					onkeypress="return soloNumerosNaturales(event)"/>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="6">
			<rich:column width="100" style="vertical-align: top">
				<h:outputText value="Descripción : "/>
			</rich:column>
			<rich:column width="300">
				<h:inputTextarea cols="55" rows="3"
					value="#{requisicionController.requisicionDetalleNuevo.strDetalle}"/>
			</rich:column>
		</h:panelGrid>
		
		<h:panelGrid columns="6">
			<rich:column width="100" style="vertical-align: top">
				<h:outputText value="Observación : "/>
			</rich:column>
			<rich:column width="300">
				<h:inputTextarea cols="55" rows="3"
					value="#{requisicionController.requisicionDetalleNuevo.strObservacion}"/>
			</rich:column>
		</h:panelGrid>		
		
		<rich:spacer height="5px"/>
		
		<h:panelGrid columns="6">
			<rich:column width="150" >				
			</rich:column>
			<rich:column width="120" style="text-align: left;">
                <a4j:commandButton styleClass="btnEstilos"
                	value="Aceptar"
                	reRender="panelDetalleR,panelMensajeR"
                	onclick="Richfaces.hideModalPanel('pAgregarRequisicionDetalle')"
                   	action="#{requisicionController.agregarRequisicionDetalle}" 
                   	style="width:120px"/>
            </rich:column>
    	</h:panelGrid>		
	</h:form>	
</rich:modalPanel>