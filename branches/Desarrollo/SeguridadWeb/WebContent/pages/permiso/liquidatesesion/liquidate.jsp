<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         	-->
	<!-- Autor     : Bizarq			   			-->
	<!-- Modulo    : Seguridad	     			-->
	<!-- Prototipo : Liquidacion Sesiones		-->			
	<!-- Fecha     : 01-08-2014     			-->

<rich:modalPanel id="panelInactiveSess" width="380" height="130"
	 resizeable="false" style="background-color:#DEEBF5" autosized="true">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Desactivar Sesión"/>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
               		<rich:componentControl for="panelInactiveSess" operation="hide" event="onclick"/>
               </h:graphicImage>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmEmprModalPanel">
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5" >                 
                <h:panelGrid columns="2"  border="0" cellspacing="4" >
                    <h:outputText id="lblCodigo" value="¿Desea Ud. desactivar sesión?" style="padding-right: 35px;"></h:outputText>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                <h:panelGrid columns="2" border="0"  style="width: 200px;">
                    <h:commandButton value="Si" actionListener="#{liquidateSessionController.desactivarSesion}" styleClass="btnEstilos"  ></h:commandButton>
                    
                    <a4j:commandButton value="No" 
                      styleClass="btnEstilos"  onclick="Richfaces.hideModalPanel('panelInactiveSess');">
                     	
                    </a4j:commandButton>
                </h:panelGrid>
                <h:panelGrid>
					<h:inputHidden id="hiddenId"></h:inputHidden>
				</h:panelGrid>
             </rich:panel>
             <rich:spacer height="4px"/><rich:spacer height="8px"/>
        </h:form>    
    </rich:modalPanel>
    
<rich:modalPanel id="panelInactiveBlockDB" width="380" height="130"
	 resizeable="false" style="background-color:#DEEBF5" autosized="true">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Desactivar Sesión"/>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
               		<rich:componentControl for="panelInactiveBlockDB" operation="hide" event="onclick"/>
               </h:graphicImage>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmBlockDB">
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5" >                 
                <h:panelGrid columns="2"  border="0" cellspacing="4" >
                    <h:outputText id="lblCodigo" value="¿Desea Ud. desactivar sesión?" style="padding-right: 35px;"/>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                <h:panelGrid columns="2" border="0"  style="width: 200px;">
                    <h:commandButton value="Si" actionListener="#{liquidateSessionController.desactivarSesion}" styleClass="btnEstilos"/>
                    <a4j:commandButton value="No" 
                      styleClass="btnEstilos"  onclick="Richfaces.hideModalPanel('panelInactiveBlockDB');">
                    </a4j:commandButton>
                </h:panelGrid>
             </rich:panel>
             <rich:spacer height="4px"/><rich:spacer height="8px"/>
        </h:form>    
</rich:modalPanel>

<rich:modalPanel id="panelInactiveBlockSessDB" width="380" height="130"
	 resizeable="false" style="background-color:#DEEBF5" autosized="true">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Eliminar Sesiones BD"/>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
               		<rich:componentControl for="panelInactiveBlockSessDB" operation="hide" event="onclick"/>
               </h:graphicImage>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmBlockSessionDB">
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5" >                 
                <h:panelGrid columns="2"  border="0" cellspacing="4" >
                    <h:outputText id="lblCodigo" value="¿Desea Ud. eliminar la sesión de BD seleccionada?" style="padding-right: 35px;"/>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                <h:panelGrid columns="2" border="0"  style="width: 200px;">
                    <h:commandButton value="Si" actionListener="#{liquidateSessionController.killSessionDB}" styleClass="btnEstilos"/>
                    <a4j:commandButton value="No" 
                      styleClass="btnEstilos"  onclick="Richfaces.hideModalPanel('panelInactiveBlockSessDB');">
                    </a4j:commandButton>
                </h:panelGrid>
             </rich:panel>
             <rich:spacer height="4px"/><rich:spacer height="8px"/>
        </h:form>    
</rich:modalPanel>

<h:panelGroup style="border:1px solid #17356f; width:1000px; background-color:#DEEBF5;">
<rich:panel style="margin-left: auto; margin-right: auto;width:900px;">
<h:form id="frmPrincipal">
	<rich:tabPanel>
    	<rich:tab label="Sesiones Web" actionListener="#{liquidateSessionController.mostrarSessionWeb}">
        	<a4j:include viewId="/pages/permiso/liquidatesesion/tabSessionWeb.jsp"/>
    	</rich:tab>	
    	<rich:tab label="Bloqueos de Base de Datos" actionListener="#{liquidateSessionController.mostrarBlockDataBase}">
        	<a4j:include viewId="/pages/permiso/liquidatesesion/tabBlockDataBase.jsp"/>
    	</rich:tab>	
    	<rich:tab label="Sesiones de Base de Datos" actionListener="#{liquidateSessionController.mostrarSessionDataBase}">
        	<a4j:include viewId="/pages/permiso/liquidatesesion/tabSessionDataBase.jsp"/>
    	</rich:tab>		
  	</rich:tabPanel>
</h:form>
</rich:panel>
</h:panelGroup>