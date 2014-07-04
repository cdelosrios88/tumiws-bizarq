<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>


<rich:modalPanel id="pAlertaRegistroCC" width="360" height="120"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Alerta"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pAlertaRegistroCC" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <h:form>
    	<h:panelGroup style="background-color:#DEEBF5">
    		<h:panelGrid columns="1">
	    		<rich:column style="border:none">
	        		<h:outputText value="¿Qué operación desea realizar con el registro seleccionado?"/>
	        	</rich:column>
	    	</h:panelGrid>
	    	<rich:spacer height="12px"/>
	    	<h:panelGrid columns="4">
	    		<rich:column width="50">
	    		</rich:column>
	    		<rich:column style="border:none" width="110">
	    			<a4j:commandButton value="Ver" 
	    				rendered="#{cuadroController.registroSeleccionado.intParaEstadoAprobacion == applicationScope.Constante.PARAM_T_ESTADOAPROBACIONCUADRO_APROBADO}"
	    				styleClass="btnEstilos"
	    				style="width:100px"	
            			action="#{cuadroController.verRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistroCC')"
             			reRender="contPanelInferiorCC,panelBotonesCC"/>
	    			<a4j:commandButton value="Aprobar" 
	    				rendered="#{cuadroController.registroSeleccionado.intParaEstadoAprobacion == applicationScope.Constante.PARAM_T_ESTADOAPROBACIONCUADRO_PENDIENTE}"
	    				styleClass="btnEstilos"
	    				style="width:100px"	
            			action="#{cuadroController.verRegistroAprobar}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistroCC')"
             			reRender="contPanelInferiorCC,panelBotonesCC"/>
             	</rich:column>             	
             	<rich:column style="border:none" width="110">
        			<a4j:commandButton value="Eliminar"
        				rendered="#{cuadroController.registroSeleccionado.intParaEstado == applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO}"
        				styleClass="btnEstilos"
        				style="width:100px"
            			action="#{cuadroController.eliminarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistroCC')"
             			reRender="panelMensajeCC,panelTablaResultadosCC"/>
	        	</rich:column>
	    	</h:panelGrid>
    	</h:panelGroup>
    </h:form>
</rich:modalPanel>