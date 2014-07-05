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
<style type="text/css">
      .active-row {background-color: #DAE8FB !important;
	               cursor: pointer;
	              }
      .tabla {border: 1px;
	          }
   /* Definicion de los estilos para la tabla*/
      .encabezado {text-align: center;
   				   font: 11px Arial, sans-serif;
   				   font-weight: bold;
                   color: Snow;
                   background:  #87CEFA;
                   }
   /* Definicion de estilos para la columnas */
      .primero {text-align: center;
   				font: 11px Arial, sans-serif;
   				background: #A2CD5A;
				}
      .ultimo {font: 11px Arial, sans-serif;
   			   text-align: center;
   			   background: #BCEE68;
			  }

</style>



<a4j:include viewId="/pages/gestion/cobranza/popup/mpGestionDetalleBody.jsp"/>
<a4j:include viewId="/pages/gestion/cobranza/popup/mpBusqSocio.jsp"/>
<a4j:include viewId="/pages/gestion/cobranza/popup/mpIngresos.jsp"/>


<a4j:include viewId="/pages/mensajes/mensajesPanel.jsp"/>

<rich:modalPanel id="mpUpDelGestionCobranza" width="450" height="138"
	 	resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Modificar/Eliminar Gestión Cobranza" styleClass="tamanioLetra"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkGestionCobranza"/>
               <rich:componentControl for="mpUpDelGestionCobranza" attachTo="hidelinkGestionCobranza" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        <h:form id="formUpDelGestionCobranza">
       	  <rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5" >                 
          <h:panelGrid border="0">
             <h:outputText id="lblCodigo" value="Elija la opción que desee realizar..." styleClass="tamanioLetra"></h:outputText>
          </h:panelGrid>
          <rich:spacer height="16px"/>
         	<h:panelGrid columns="3" border="0" style="margin:0 auto; width: 100%">
              <a4j:commandButton value="Modificar" actionListener="#{cobranzaController.obtenerGestionCobranza}" styleClass="btnEstilos"
              				reRender="divFormCobranza,divTblGestionCobranzaEnt,divTblGestionCobranzaDet,idBotones" oncomplete="Richfaces.hideModalPanel('mpUpDelGestionCobranza')"></a4j:commandButton>
              				
              				
              <a4j:commandButton rendered="#{cobranzaController.botonEliminarDisabled}" value="Eliminar" actionListener="#{cobranzaController.eliminarGestionCobranza}" styleClass="btnEstilos"
              				reRender="pgFilArea,divFormCobranza,divTblGestionCobranzaEnt" oncomplete="Richfaces.hideModalPanel('mpUpDelGestionCobranza')"></a4j:commandButton>
          </h:panelGrid>
          </rich:panel>
          <rich:spacer height="4px"/><rich:spacer height="8px"/>
        </h:form>
</rich:modalPanel>

<rich:modalPanel id="pAdjuntarDocCobranza" width="530" height="200" resizeable="false" style="background-color:#DEEBF5;">
	    <f:facet name="header">
	        <h:panelGrid>
	          <rich:column style="border: none;">
	            <h:outputText value="Adjuntar Documento Cobranza"/>
	          </rich:column>
	        </h:panelGrid>
	    </f:facet>
	    <f:facet name="controls">
	        <h:panelGroup>
	           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
	           		<rich:componentControl for="pAdjuntarDocCobranza" operation="hide" event="onclick"/>
	           </h:graphicImage>
	       </h:panelGroup>
	    </f:facet>
	   <a4j:include viewId="/pages/gestion/cobranza/popup/adjuntarDocCobranza.jsp"/>
</rich:modalPanel>
	


    <h:form id="frmPrincipal">
    
      <rich:tabPanel activeTabClass="activo" inactiveTabClass="inactivo" disabledTabClass="disabled">
     	 <rich:tab label="Gestión de Cobranza" disabled="#{cobranzaController.bolGestionCobranza}">
            <a4j:include viewId="/pages/gestion/cobranza/tabCobranza.jsp"/>
     	 </rich:tab>
	     <rich:tab label="Enlace y desenlace de recibos" disabled="#{cobranzaController.bolRecibosEnlaces}" actionListener="#{enlaceReciboController.inicio}">
	     	<a4j:include viewId="/pages/gestion/cobranza/tabRecibos.jsp"/>
	     </rich:tab>
     </rich:tabPanel>           
   </h:form>