<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Christian De los Ríos    -->
	<!-- Modulo    : Créditos                 -->
	<!-- Prototipo : DESCUENTO PLANILLA - GESTION - HOJA DE PLANEAMIENTO -->			
	<!-- Fecha     : 27/12/2011               -->

<script type="text/javascript">
	function jsSelectEstructura(estructuraKeys){
		//alert("Estructura keys: "+estructuraKeys);
        document.getElementById("formUpDelEstrucOrg:hiddenIdEstrucOrg").value=estructuraKeys;
    }
	
	function jsSelectEstructuraDetalle(estructuraKeys){
		//alert("Estructura keys: "+estructuraKeys);
        document.getElementById("formUpDelEstrucDet:hiddenIdEstrucDet").value=estructuraKeys;
    }
	
	function jsSetRenderedTable(id){
		//alert("RenderedTableDetalle: "+id);
        document.getElementById("formUpDelEstrucDet:hiddenIdRenderedTable").value=id;
    }
</script>

	<rich:modalPanel id="mpUpDelEstrucOrg" width="355" height="138"
	 	resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Actualizar/Eliminar Estructura Orgánica" styleClass="tamanioLetra"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkEstrucOrg"/>
               <rich:componentControl for="mpUpDelEstrucOrg" attachTo="hidelinkEstrucOrg" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        <h:form id="formUpDelEstrucOrg">
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5" >                 
                <h:panelGrid border="0">
                    <h:outputText id="lblCodigo" value="¿Desea Actualizar o Eliminar Estructura Orgánica?" styleClass="tamanioLetra"></h:outputText>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                	<h:panelGrid columns="2" border="0" style="margin:0 auto; width: 100%">
	                    <h:commandButton value="Actualizar" actionListener="#{estrucOrgController.obtenerEstructura}" styleClass="btnEstilos"></h:commandButton>
	                    <h:commandButton value="Eliminar" actionListener="#{estrucOrgController.eliminarEstructura}" styleClass="btnEstilos"></h:commandButton>
	                </h:panelGrid>
                <h:panelGrid>
					<h:inputHidden id="hiddenIdEstrucOrg"></h:inputHidden>
				</h:panelGrid>
             </rich:panel>
             <rich:spacer height="4px"/><rich:spacer height="8px"/>
        </h:form>    
    </rich:modalPanel>
    
    <rich:modalPanel id="mpUpDelEstructuraDetalle" width="355" height="138"
	 	resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Eliminar Configuración" styleClass="tamanioLetra"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkEstrucDet"/>
               <rich:componentControl for="mpUpDelEstructuraDetalle" attachTo="hidelinkEstrucDet" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        <h:form id="formUpDelEstrucDet">
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5" >                 
                <h:panelGrid border="0">
                    <h:outputText id="lblCodigo" value="¿Desea Eliminar la Configuración?" styleClass="tamanioLetra"></h:outputText>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                	<h:panelGrid columns="2" border="0" style="margin:0 auto; width: 200px">
	                    <a4j:commandButton value="Eliminar" actionListener="#{estrucOrgController.eliminarEstructuraDetalle}" styleClass="btnEstilos"
	                    				reRender="#{estrucOrgController.strRenderedTableDetalle}" oncomplete="Richfaces.hideModalPanel('mpUpDelEstructuraDetalle')"/>
			    		<a4j:commandLink value="Cancelar" action="#" styleClass="buttonLink1" style="color:#000000"
										oncomplete="Richfaces.hideModalPanel('mpUpDelEstructuraDetalle')">
						</a4j:commandLink>
	                </h:panelGrid>
                <h:panelGrid>
					<h:inputHidden id="hiddenIdEstrucDet"></h:inputHidden>
					<h:inputHidden id="hiddenIdRenderedTable"></h:inputHidden>
				</h:panelGrid>
             </rich:panel>
             <rich:spacer height="4px"/><rich:spacer height="8px"/>
        </h:form>    
    </rich:modalPanel>
    
	<rich:modalPanel id="pAgregarPerJuri" width="935" height="550"
	 resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Persona Jurídica"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidePanelPerJuri"/>
               <rich:componentControl for="pAgregarPerJuri" attachTo="hidePanelPerJuri" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet> 
        
        <a4j:include id="inclPerJuri" viewId="/pages/popup/juridicaBody.jsp"/>
	</rich:modalPanel>
	
	
<rich:modalPanel id="pBuscarEntidadEnvio" width="548" height="348"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Estructura"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarEntidadEnvio" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   	<a4j:include viewId="/pages/estructura/popup/estructuraBody.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="pBuscarEntidadPadron" width="548" height="348"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Estructura"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarEntidadPadron" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   	<a4j:include viewId="/pages/popup/estructuraBody.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="pBuscarAdminPadron" width="750" height="400"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Padrones"></h:outputText>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarAdminPadron" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
   	<a4j:include viewId="/pages/popup/adminPadronBody.jsp"/>
</rich:modalPanel>

<rich:modalPanel id="pEliminarRegistro" width="400" height="140"
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
           		<rich:componentControl for="pEliminarRegistro" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <h:form id="frmEliminarRegistro">
    	<h:panelGroup id ="panelEliminarRegistroPadron">
    	<rich:panel style="background-color:#DEEBF5" rendered="#{padronController.permitirEliminarRegistro}">
    		<h:panelGrid columns="1">
	    		<rich:column style="text-align: center;border:none">
	        		<h:outputText value="¿Está seguro que desea eliminar el registro seleccionado?"></h:outputText>
	        	</rich:column>
	    	</h:panelGrid>
	    	<a4j:commandButton value="Sì" styleClass="btnEstilos"
            	actionListener="#{padronController.eliminarRegistro}"
             	onclick="Richfaces.hideModalPanel('pEliminarRegistro')"
             	reRender="panelTablaPadrones,panelMensaje"
        	/>
    	</rich:panel>
    	<rich:panel style="background-color:#DEEBF5" rendered="#{!padronController.permitirEliminarRegistro}">
    		<h:panelGrid columns="1">
	    		<rich:column style="text-align: center;border:none">
	        		<h:outputText value="No Puede eliminar el registro porque no se encuentra activo o porque tiene una solicitud de pago activa"></h:outputText>
	        	</rich:column>
	    	</h:panelGrid>
	    	<a4j:commandButton value="Aceptar" styleClass="btnEstilos"
            	onclick="Richfaces.hideModalPanel('pEliminarRegistro')"
        	/>
    	</rich:panel>
    	</h:panelGroup>
    </h:form>
</rich:modalPanel>

<rich:modalPanel id="pAlertaRegistro" width="400" height="150"
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
           		<rich:componentControl for="pAlertaRegistro" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>
    <h:form id="frmAlertaRegistro">
    	<rich:panel style="background-color:#DEEBF5">
    		<h:panelGrid columns="1">
	    		<rich:column style="border:none">
	        		<h:outputText value="¿Qué operación desea realizar con el registro seleccionado?"/>
	        	</rich:column>
	    	</h:panelGrid>
	    	<rich:spacer height="12px"/>  
	    	<h:panelGrid columns="1">
	    		<rich:column style="border:none" id="colBtnModificar">
	    			<a4j:commandButton value="Modificar" styleClass="btnEstilos"
            			action="#{configuracionController.modificarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistro')"
             			reRender="contPanelInferior,panelMensaje"
        			/>
        			<a4j:commandButton value="Eliminar" styleClass="btnEstilos"
            			action="#{configuracionController.eliminarRegistro}"
             			onclick="Richfaces.hideModalPanel('pAlertaRegistro')"
             			reRender="panelMensaje,contPanelInferior,panelTablaConfiguracion"
             			rendered="#{configuracionController.mostrarBtnEliminar}"
        			/>
	        	</rich:column>
	    	</h:panelGrid>
    	</rich:panel>
    </h:form>
</rich:modalPanel>

	<a4j:include viewId="/pages/mensajes/mensajesPanel.jsp"/>
	
    <rich:tabPanel style="margin:15px; width:970px">
     	 <rich:tab label="Institución" disabled="#{estrucOrgController.blnInstitucionPermisoMenu}">
            <a4j:include viewId="/pages/estructura/tabInstitucion.jsp"/>
     	 </rich:tab>
     	 <rich:tab label="Unidad Ejecutora" disabled="#{estrucOrgController.blnUniEjePermisoMenu}">
            <a4j:include viewId="/pages/estructura/tabUnidadEjecutora.jsp"/>
     	 </rich:tab>
     	 <rich:tab label="Dependencia" disabled="#{estrucOrgController.blnDependenciaPermisoMenu}">
            <a4j:include viewId="/pages/estructura/tabDependencia.jsp"/>
     	 </rich:tab>
     	 <rich:tab label="Administración de Padrones">
            <a4j:include viewId="/pages/estructura/tabPadron.jsp"/>
     	 </rich:tab>
     	 <rich:tab label="Envio de Archivos">
            <a4j:include viewId="/pages/estructura/tabEnvio.jsp"/>
     	 </rich:tab>
     </rich:tabPanel>  