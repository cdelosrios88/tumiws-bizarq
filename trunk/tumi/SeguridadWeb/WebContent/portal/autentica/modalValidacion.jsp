<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<rich:modalPanel id="panelMostrarMensaje" width="380" height="130"
 	resizeable="false" style="background-color:#DEEBF5" autosized="true">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Login" ></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
               		<rich:componentControl for="panelMostrarMensaje" operation="hide" event="onclick"/>
               </h:graphicImage>
           </h:panelGroup>
        </f:facet>
       <h:form id="frmEmprModalPanel">
     	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5" >                 
             <h:panelGrid columns="2"  border="0" cellspacing="4" >
                 <h:outputText id="lblMensaje" value="El usuario actual no tiene permiso para ingresar al sistema" style="padding-right: 35px;"></h:outputText>
             </h:panelGrid>             
             
          </rich:panel>
          <rich:spacer height="4px"/><rich:spacer height="8px"/>
          <div align="center">
          	<h:panelGrid border="0" style="width: 200px;">
          		<h:commandButton value="Aceptar" styleClass="btnEstilos">
          			<rich:componentControl for="panelMostrarMensaje" operation="hide" event="onclick"/>
          		</h:commandButton>
      		</h:panelGrid>
      	  </div>
     </h:form>    
</rich:modalPanel>
