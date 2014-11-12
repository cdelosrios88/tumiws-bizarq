<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>


<rich:modalPanel id="mpConfimarEliminacion" width="360" height="180"
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
           		<rich:componentControl for="mpConfimarEliminacion" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <h:form>
    	<h:panelGroup style="background-color:#DEEBF5">
    		<h:panelGrid columns="2">
    			<rich:column width="80">
    			</rich:column>
	    		<rich:column style="border:none">
	        		<h:outputText value="¿Esta seguro de eliminar el registro?"/>
	        	</rich:column>
	    	</h:panelGrid>
	    	<rich:spacer height="50px"/>
	    	<h:panelGrid columns="3">
	    		<rich:column width="50">
	    		</rich:column>
             	<rich:column style="border:none" width="150">
        			<a4j:commandButton value="Aceptar"
        				styleClass="btnEstilos"
        				style="width:100px"
            			action="#{reclamosDevolucionesController.eliminarRegistro}"
             			reRender="mpModificarReclamo,mpConfimarEliminacion,frmTributacion"/>	   
             			<f:attribute name="itemReclamoBusq" value="#{item}" />     		
	        	</rich:column>
             	<rich:column style="border:none" width="110">
        			<a4j:commandButton value="Cancelar"
        				styleClass="btnEstilos"
        				style="width:100px"
             			reRender="mpModificarReclamo,mpConfimarEliminacion"/>        		
	        	</rich:column>
	    	</h:panelGrid>
    	</h:panelGroup>
    </h:form>
    <!-- rendered="#{solicitudPersonalController.registroSeleccionado.intParaEstado==applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO}" -->
</rich:modalPanel>
