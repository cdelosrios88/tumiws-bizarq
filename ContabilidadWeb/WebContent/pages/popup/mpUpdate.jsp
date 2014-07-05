<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Modulo    : Seguridad                -->
	<!-- Prototipo : Empresas			      -->			
	<!-- Fecha     : 31/08/2011               -->
	
<rich:modalPanel id="mpUpdate" width="450" height="138"
	 	resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Modificar" styleClass="tamanioLetra"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="imgHide"/>
               <rich:componentControl for="mpUpDelModelo" attachTo="imgHide" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
       <h:form id="formUpdate">
       	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5" >                 
         <h:panelGrid border="0">
             <h:outputText id="lblCodigo" value="¿Desea modificar el registro?" styleClass="tamanioLetra"></h:outputText>
         </h:panelGrid>
         <rich:spacer height="16px"/>
         	<h:panelGrid columns="3" border="0" style="margin:0 auto; width: 100%">
              <a4j:commandButton value="Modificar" styleClass="btnEstilos"
              					oncomplete="Richfaces.hideModalPanel('mpUpdateDelete'),updateRow()"></a4j:commandButton>
          	</h:panelGrid>
            </rich:panel>
            <rich:spacer height="4px"/><rich:spacer height="8px"/>
       </h:form>    
</rich:modalPanel>