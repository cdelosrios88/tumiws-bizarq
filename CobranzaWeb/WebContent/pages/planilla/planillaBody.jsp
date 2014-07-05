<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Cobranza : Cooperativa Tumi         -->
	<!-- Autor     : Franko Yalico	  -->
	<!-- Modulo    : Cobranza                -->
	<!-- Prototipo : Planilla        -->
	<!-- Fecha     : 11/10/2011               -->

<script language="JavaScript" src="/js/main.js"  type="text/javascript"></script>


<script type="text/javascript">
function seleccionarFilaDeEnvio(itemId){
   document.getElementById("frmPrincipal:idIncludeTabEnvio:idHiddenIndexFilaDeEnvio").value=itemId;
   var lControl = document.getElementById("frmPrincipal:idIncludeTabEnvio:idBtnSeleccionarFilaDeEnvio");
   if(lControl){
	   lControl.click();
   }
}

function seleccionarFilaDeEfectuado(itemId){
   document.getElementById("frmPrincipal:idIncludeTabEfectuado:idHiddenIndexFilaDeEfectuado").value=itemId;
   var lControl = document.getElementById("frmPrincipal:idIncludeTabEfectuado:idBtnSeleccionarFilaDeEfectuado");
   if(lControl){
	   lControl.click();
   }
}
	
function seleccionarFilaDePlanillaEnvio(itemId){
}
</script>


<rich:modalPanel id="idPopupBuscarEjecutoraDeEfectuado" width="610" height="340" resizeable="false" style="background-color:#DEEBF5;">
   <f:facet name="header">
     <h:panelGrid>
        <rich:column style="border: none;"><h:outputText value="Buscar Unidad Ejecutora"/></rich:column>
     </h:panelGrid>
   </f:facet>
   <f:facet name="controls">
     <h:panelGroup>
         <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="idLinkBuscarEjecutoraDeEfectuado"/>
         <rich:componentControl for="idPopupBuscarEjecutoraDeEfectuado" attachTo="idLinkBuscarEjecutoraDeEfectuado" operation="hide" event="onclick"/>
     </h:panelGroup>
   </f:facet>
   <h:form id="idFrmPopupBuscarEjecutoraDeEfectuado">
  	<a4j:include id="idIncludePopupEjecutoraEfectuado" viewId="/pages/planilla/efectuado/popup/ejecutora_buscar.jsp"/>
   </h:form>
</rich:modalPanel>





<rich:modalPanel id="idPopupAgregarEnviado" width="600" height="375" resizeable="false" style="background-color:#DEEBF5;">
   <f:facet name="header">
     <h:panelGrid>
        <rich:column style="border: none;"><h:outputText value="Agregar Enviado"/></rich:column>
     </h:panelGrid>
   </f:facet>
   <f:facet name="controls">
     <h:panelGroup>
         <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="idLinkAgregarEnviado"/>
         <rich:componentControl for="idPopupAgregarEnviado" attachTo="idLinkAgregarEnviado" operation="hide" event="onclick"/>
     </h:panelGroup>
   </f:facet>
   <h:form id="idFrmPopupAgregarEnviado">
  	<a4j:include id="idIncludePopupAgregarEnviado" viewId="/pages/planilla/efectuado/popup/agregarEnviado.jsp"/>
   </h:form>
</rich:modalPanel>


<rich:modalPanel id="idPopupAbreviatura" width="610" height="200" resizeable="false" style="background-color:#DEEBF5;">
   <f:facet name="header">
     <h:panelGrid>
        <rich:column style="border: none;"><h:outputText value="Abreviaturas"/></rich:column>
     </h:panelGrid>
   </f:facet>
   <f:facet name="controls">
     <h:panelGroup>
         <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="idLinkAbreviatura"/>
         <rich:componentControl for="idPopupAbreviatura" attachTo="idLinkAbreviatura" operation="hide" event="onclick"/>
     </h:panelGroup>
   </f:facet>
   <h:form id="idFrmPopupAbreviatura">
  	<a4j:include id="idIncludePopupAbreviatura" viewId="/pages/planilla/popup/abreviatura.jsp"/>
   </h:form>
</rich:modalPanel>

<rich:modalPanel id="idPopupAlertaAgregarEnviado" width="350" height="200" resizeable="false" style="background-color:#DEEBF5;">
   <f:facet name="header">
     <h:panelGrid>
        <rich:column style="border: none;"><h:outputText value="Advertencia"/></rich:column>
     </h:panelGrid>
   </f:facet>
   <f:facet name="controls">
     <h:panelGroup>
         <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="idLinkAdvertenciaAE"/>
         <rich:componentControl for="idPopupAlertaAgregarEnviado" attachTo="idLinkAdvertenciaAE" operation="hide" event="onclick"/>
     </h:panelGroup>
   </f:facet>
   <h:form id="idFrmPopupAlertaAE">
  	<a4j:include id="idIncludePopupAlertaAE" viewId="/pages/planilla/efectuado/popup/alertaEnAgregarEnviado.jsp"/>
   </h:form>
</rich:modalPanel>

<rich:modalPanel id="idPopupAlertaModalidadDistinta" width="350" height="150" resizeable="false" style="background-color:#DEEBF5;">
   <f:facet name="header">
     <h:panelGrid>
        <rich:column style="border: none;"><h:outputText value="Advertencia"/></rich:column>
     </h:panelGrid>
   </f:facet>
   <f:facet name="controls">
     <h:panelGroup>
         <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="idLinkAdvertenciaMD"/>
         <rich:componentControl for="idPopupAlertaModalidadDistinta" attachTo="idLinkAdvertenciaMD" operation="hide" event="onclick"/>
     </h:panelGroup>
   </f:facet>
   <h:form id="idFrmPopupAlertaMD">
  	<a4j:include id="idIncludePopupAlertaMD" viewId="/pages/planilla/efectuado/popup/alertaModalidadDistinta.jsp"/>
   </h:form>
</rich:modalPanel>

<rich:modalPanel id="idPopupAlertaAgregarEfectuado" width="350" height="200" resizeable="false" style="background-color:#DEEBF5;">
   <f:facet name="header">
     <h:panelGrid>
        <rich:column style="border: none;"><h:outputText value="Advertencia"/></rich:column>
     </h:panelGrid>
   </f:facet>
   <f:facet name="controls">
     <h:panelGroup>
         <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="idLinkAdvertencia"/>
         <rich:componentControl for="idPopupAlertaAgregarEfectuado" attachTo="idLinkAdvertencia" operation="hide" event="onclick"/>
     </h:panelGroup>
   </f:facet>
   <h:form id="idFrmPopupAlerta">
  	<a4j:include id="idIncludePopupAlerta" viewId="/pages/planilla/efectuado/popup/alertaEnAgregarEfectuado.jsp"/>
   </h:form>
</rich:modalPanel>

<rich:modalPanel id="idPopupAlertaAgregarEfectuadoPL" width="350" height="200" resizeable="false" style="background-color:#DEEBF5;">
   <f:facet name="header">
     <h:panelGrid>
        <rich:column style="border: none;"><h:outputText value="Advertencia Planilla a Efectuar"/></rich:column>
     </h:panelGrid>
   </f:facet>
   <f:facet name="controls">
     <h:panelGroup>
         <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="idLinkAdvertenciaPL"/>
         <rich:componentControl for="idPopupAlertaAgregarEfectuadoPL" attachTo="idLinkAdvertenciaPL" operation="hide" event="onclick"/>
     </h:panelGroup>
   </f:facet>
   <h:form id="idFrmPopupAlertaPL">
  	<a4j:include id="idIncludePopupAlertaPL" viewId="/pages/planilla/efectuado/popup/alertaEnAgregarEfectuadoPL.jsp"/>
   </h:form>
</rich:modalPanel>

<rich:modalPanel id="idPopupAlertaAgregarNoSocio" width="350" height="200" resizeable="false" style="background-color:#DEEBF5;">
   <f:facet name="header">
     <h:panelGrid>
        <rich:column style="border: none;"><h:outputText value="Advertencia Agregar No socio"/></rich:column>
     </h:panelGrid>
   </f:facet>
   <f:facet name="controls">
     <h:panelGroup>
         <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="idLinkAdvertenciaANS"/>
         <rich:componentControl for="idPopupAlertaAgregarNoSocio" attachTo="idLinkAdvertenciaANS" operation="hide" event="onclick"/>
     </h:panelGroup>
   </f:facet>
   <h:form id="idFrmPopupAlertaans">
  	<a4j:include id="idIncludePopupAlertaAgregarNoSocio" viewId="/pages/planilla/efectuado/popup/alertaEnAgregarNoSocio.jsp"/>
   </h:form>
</rich:modalPanel>

<rich:modalPanel id="idPopupAlertaSocioPerteneceAlaPlanilla"
			     width="350" height="100" 
				 resizeable="false" style="background-color:#DEEBF5;">
   <f:facet name="header">
     <h:panelGrid>
        <rich:column style="border: none;">
        	<h:outputText value="Advertencia Agregar socio"/>
        </rich:column>
     </h:panelGrid>
   </f:facet>
   <f:facet name="controls">
     <h:panelGroup>
         <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="idLinkAdvertenciaASPP"/>
         <rich:componentControl for="idPopupAlertaSocioPerteneceAlaPlanilla"
         					    attachTo="idLinkAdvertenciaASPP"
         						operation="hide" event="onclick"/>
     </h:panelGroup>
   </f:facet>
   <h:form id="idFrmPopupAlertaSocioPerteneceAlaPlanilla"> 
  	<a4j:include id="idIncludePopupAlertaSocioPerteneceAlaPlanilla" 
  				 viewId="/pages/planilla/efectuado/popup/alertaSocioPerteneceAlaPlanilla.jsp"/>
   </h:form>
</rich:modalPanel>



<rich:modalPanel id="pAgregarEfectuado" width="600" height="335" resizeable="false" style="background-color:#DEEBF5;">
   <f:facet name="header">
     <h:panelGrid>
        <rich:column style="border: none;"><h:outputText value="Agregar Efectuado"/></rich:column>
     </h:panelGrid>
   </f:facet>
   <f:facet name="controls">
     <h:panelGroup>
         <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="idLinkAgregarEfectuadoo"/>
         <rich:componentControl for="pAgregarEfectuado" attachTo="idLinkAgregarEfectuadoo" operation="hide" event="onclick"/>
     </h:panelGroup>
   </f:facet>
   <h:form id="fAgregarEfectuado">
  	<a4j:include id="idIncludePopupAgregarEfectuado" viewId="/pages/planilla/efectuado/popup/agregarEfectuado.jsp"/>
   </h:form>
</rich:modalPanel>



<rich:modalPanel id="pAdjuntarDocumento" width="530" height="200" resizeable="false" style="background-color:#DEEBF5;">
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
	   <a4j:include viewId="/pages/planilla/solicitud/popup/mpAdjuntaDocumento.jsp"/>
</rich:modalPanel>

<!-- Ventana emergente para la confirmación Modificar/Anular -->
<rich:modalPanel id="mdpOpcionSolicitudCtaCte" width="400" height="140"
	 	resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Modificar/Anular Solicitud de Cuenta Corriente" />
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
				<h:graphicImage value="/images/icons/remove_20.png" 
					styleClass="hidelink" >
					<rich:componentControl for="mdpOpcionSolicitudCtaCte" operation="hide"
						event="onclick" />
            	</h:graphicImage>
            </h:panelGroup>
        </f:facet>
        <h:form id="fromOpcionSolicitudCtaCte">
			<rich:panel style="background-color: #DEEBF5; margin: 0 auto;">                 
          		<h:panelGrid columns="1">
					<rich:column style="border:none">
						<h:outputText value="¿Qué operación desea realizar con el registro seleccionado?" />
					</rich:column>
          		</h:panelGrid>
				<rich:spacer height="12px"/>
				<h:panelGrid columns="2" style="margin: 0 auto;">
					<rich:column>
						<a4j:commandButton value="Modificar" actionListener="#{solicitudCtaCteController.obtener}" styleClass="btnEstilos"
							reRender="divFormSolicitudCtaCte,btnGrabar" 
							oncomplete="Richfaces.hideModalPanel('mdpOpcionSolicitudCtaCte')" />
					</rich:column>
					<rich:column>
						<a4j:commandButton value="Anular" 
							actionListener="#{solicitudCtaCteController.anular}" styleClass="btnEstilos"
							disabled="#{solicitudCtaCteController.btnAnularDisabled}"
							reRender="pgFilSolicitudCtaCte,btnGrabar" 
							oncomplete="Richfaces.hideModalPanel('mdpOpcionSolicitudCtaCte')" />
					</rich:column>
				</h:panelGrid>
			</rich:panel>
        </h:form>
</rich:modalPanel>

<a4j:include viewId="/pages/planilla/solicitud/popup/mpBusqSocio.jsp"/>
<a4j:include id="divFormEnvioInflada" viewId="/pages/planilla/envio/popup/envioInflada.jsp" layout="block" />
<a4j:include id="divFormEnvioMonto"   viewId="/pages/planilla/envio/popup/MontoEnvio.jsp" layout="block" />
<a4j:include id="divFormTipoPlanilla" viewId="/pages/planilla/envio/popup/tipoPlanilla.jsp" layout="block" />
<a4j:include id="divFormAgregarSocio" viewId="/pages/planilla/efectuado/popup/agregarSocioEnEfectuado.jsp" layout="block"/>
<a4j:include id="divFormRelacionarNoSocio" viewId="/pages/planilla/efectuado/popup/relacionarNoSocio.jsp" layout="block"/>
<a4j:include id="divFormAgregarPersona" viewId="/pages/planilla/efectuado/popup/agregarPersona.jsp" layout="block"/>
<a4j:include id="divFormNuevaPersona" viewId="/pages/planilla/efectuado/popup/newPersona.jsp" layout="block"/>
<a4j:include id="divFormOpcionesExportar" viewId="/pages/planilla/envio/popup/OpcionesExportar.jsp" layout="block"/>
<a4j:include id="divFormBuscarUnidadEjecutora" viewId="/pages/planilla/envio/popup/buscar_UnidadEjecutora.jsp" layout="block"/>
<a4j:include id="divFormBuscarUnidadEjecutoraEfectuado" viewId="/pages/planilla/efectuado/popup/buscar_UnidadEjecutoraEfectuado.jsp" layout="block"/>
<a4j:include id="divFormUploadConArchivo" viewId="/pages/planilla/efectuado/popup/uploadEfectuadoConArchivo.jsp" layout="block"/>
	
	<h:form id="frmPrincipal" >
 		<h:outputText value="#{solicitudCtaCteController.limpiarSolicitud}"/>
 		<h:outputText value="#{cobroController.limpiarCobro}"/>
 		<h:outputText value="#{envioController.limpiarEnvio}"/>
 		<h:outputText value="#{efectuadoController.limpiarEfectuado}"/>
 		
			<rich:tabPanel activeTabClass="activo" inactiveTabClass="inactivo" disabledTabClass="disabled" 
					style="margin:15px; width:970px">
		  	
			    <rich:tab label="Envío de Planilla"  disabled="#{!envioController.poseePermiso}">
			     	<a4j:include id="idIncludeTabEnvio" viewId="/pages/planilla/envio/tabEnvioPlanilla.jsp"/>
			  	</rich:tab>
			  	
			   <rich:tab label="Efectuado de Planilla"  disabled="#{!efectuadoController.poseePermiso}">
			     	<a4j:include id="idIncludeTabEfectuado" viewId="/pages/planilla/efectuado/tabEfectuadoPlanilla.jsp"/>
			  	</rich:tab>
			    
			    <rich:tab label="Cobro de Planilla" disabled="#{!cobroController.poseePermiso}">
			     	<a4j:include id="idIncludeTabCobro" viewId="/pages/planilla/cobro/tabCobro.jsp"/>
			    </rich:tab>
			    
			    <rich:tab label="Solicitud de Cta. Cte." disabled="#{!solicitudCtaCteController.poseePermiso}">
			     	<a4j:include id="idIncludeTabSolicitud" viewId="/pages/planilla/solicitud/tabSolicitud.jsp"/>
			    </rich:tab>
			    
		  	</rich:tabPanel>
  	
</h:form>