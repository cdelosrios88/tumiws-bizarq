<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>


<rich:modalPanel id="pAlertaRegistroD" width="360" height="135"
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
           		<rich:componentControl for="pAlertaRegistroD" operation="hide" event="onclick"/>
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
	    		<!-- Modificado por cdelosrios, 28/10/2013 -->
	    		<%--<rich:column style="border:none" width="100"> --%>
	    		<rich:column style="border:none" width="100" 
	    			rendered="#{sunatController.registroSeleccionado.ordenCompra.intParaEstadoOrden!=applicationScope.Constante.PARAM_T_ESTADOORDEN_CERRADO}"> 
	    			<a4j:commandButton value="Modificar" 
	    				styleClass="btnEstilos"
	    				style="width:90px"	
            			action="#{sunatController.modificarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistroD')"
             			reRender="contPanelInferiorD,panelBotonesD"/>
             	</rich:column>
             	<!-- Fin modificado por cdelosrios, 28/10/2013 -->
             	<!-- Modificado por cdelosrios, 25/10/2013 -->
             	<%--<rich:column style="border:none" width="110">
	    			<a4j:commandButton value="Letras" 
	    				styleClass="btnEstilos"
	    				style="width:90px"	
            			action="#{sunatController.agregarLetras}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistroD')"
             			reRender="contPanelInferiorD,panelBotonesD"/>
             	</rich:column>
             	<rich:column style="border:none" width="100">
        			<a4j:commandButton value="Eliminar"
        				rendered="#{sunatController.registroSeleccionado.intParaEstado==applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO}"
        				styleClass="btnEstilos"
        				style="width:90px"
            			action="#{sunatController.eliminarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistroD')"
             			reRender="panelBotonesD,panelMensajeD,panelTablaResultadosD"/>	        		
	        	</rich:column> --%>
	        	<rich:column style="border:none" width="110">
	    			<a4j:commandButton value="Ver" 
	    				styleClass="btnEstilos"
	    				style="width:90px"	
            			action="#{sunatController.verRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistroD')"
             			reRender="contPanelInferiorD,panelBotonesD"/>
             	</rich:column>
	        	<!-- Fin modificado por cdelosrios, 25/10/2013 -->
	    	</h:panelGrid>
    	</h:panelGroup>
    </h:form>
</rich:modalPanel>