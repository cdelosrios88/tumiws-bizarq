<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<a4j:include viewId="/pages/reclamosDevoluciones/popup/popConfirmarEliminar.jsp"/>

<rich:modalPanel id="mpModificarReclamo" width="360" height="135"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Opciones"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="mpModificarReclamo" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <h:form id="formBotonesReclamo">
    	<h:panelGroup style="background-color:#DEEBF5">
    		<h:panelGrid columns="1">
	    		<rich:column style="border:none">
	        		<h:outputText value="�Qu� operaci�n desea realizar con el registro seleccionado?"/>
	        	</rich:column>
	    	</h:panelGrid>
	    	<rich:spacer height="12px"/>
	    	<h:panelGrid columns="4" id="pgBotonesReclamo">
	    		<rich:column width="50">
	    		</rich:column>
	    		<rich:column style="border:none" width="110" rendered="#{reclamosDevolucionesController.btnVer}">
	    			<a4j:commandButton value="Ver" 
	    				styleClass="btnEstilos"
	    				style="width:100px"	
            			action="#{reclamosDevolucionesController.verValores}"
             			onclick="Richfaces.hideModalPanel('mpModificarReclamo')"
             			reRender="opReclamo,divTblTributacion,pgBotonesReclamo,pgBotones"/>
             			<f:attribute name="itemReclamoBusq" value="#{item}" />
             	</rich:column>
             	<rich:column style="border:none" width="110" rendered="#{reclamosDevolucionesController.btnModificar}">
        			<a4j:commandButton value="Modificar"
        				styleClass="btnEstilos"
        				style="width:100px"
            			action="#{reclamosDevolucionesController.modificarValores}"
             			onclick="Richfaces.hideModalPanel('mpModificarReclamo')"
             			reRender="opReclamo,divTblTributacion,pgBotonesReclamo,pgBotones"/>	   
             			<f:attribute name="itemReclamoBusq" value="#{item}" />     		
	        	</rich:column>
             	<rich:column style="border:none" width="110" rendered="#{reclamosDevolucionesController.btnEliminar}">
        			<a4j:commandButton value="Eliminar"
        				styleClass="btnEstilos"
        				style="width:100px"
            			oncomplete="Richfaces.showModalPanel('mpConfimarEliminacion')"
            			reRender="pgBotonesReclamo,pgBotones"/>
	        	</rich:column>
	    	</h:panelGrid>
    	</h:panelGroup>
    </h:form>
</rich:modalPanel>