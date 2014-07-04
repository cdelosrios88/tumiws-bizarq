<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Arturo Julca    		  -->			
	<!-- Fecha     : 05/10/2012               -->
	<!-- <h:outputText value="#{solicitudPrevisionController.limpiarPrevision}" /> -->

	
	<h:outputText value="#{solicitudPrevisionController.limpiarPrevision}" />
    	
	<rich:modalPanel id="pBuscarApoderado" width="530" height="200" resizeable="false" style="background-color:#DEEBF5;">
	    <f:facet name="header">
	        <h:panelGrid>
	          <rich:column style="border: none;">
	            <h:outputText value="Buscar Apoderado"/>
	          </rich:column>
	        </h:panelGrid>
	    </f:facet>
	    <f:facet name="controls">
	        <h:panelGroup>
	           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
	           		<rich:componentControl for="pBuscarApoderado" operation="hide" event="onclick"/>
	           </h:graphicImage>
	       </h:panelGroup>
	    </f:facet>
	   <a4j:include viewId="/pages/prevision/popup/buscarApoderado.jsp"/>
	</rich:modalPanel>
	
	<rich:modalPanel id="pAdjuntarCartaPoder" width="420" height="200" resizeable="false" style="background-color:#DEEBF5;">
	    <f:facet name="header">
	        <h:panelGrid>
	          <rich:column style="border: none;">
	            <h:outputText value="Adjuntar Carta Poder"/>
	          </rich:column>
	        </h:panelGrid>
	    </f:facet>
	   <a4j:include viewId="/pages/prevision/popup/adjuntarCartaPoder.jsp"/>
	</rich:modalPanel>

	<rich:modalPanel id="mpUpDelSolPrevision" width="450" height="230"
					resizeable="false" style="background-color:#DEEBF5;">
		<f:facet name="header">
			<h:panelGrid>
				<rich:column style="border: none;">
					<h:outputText value="Actualizar/Eliminar Solicitud Previsión"/>
				</rich:column>
			</h:panelGrid>
		</f:facet>
		
		<f:facet name="controls">
			<h:panelGroup>
				<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkSolicitudPrevision"/>
				<rich:componentControl for="mpUpDelSolPrevision" attachTo="hidelinkSolicitudPrevision" operation="hide" event="onclick"/>
			</h:panelGroup>
		</f:facet>
		
		<h:form id="frmSolicitudPrevisionModalPanel">
			<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 370px;">                 
				<h:panelGrid columns="2"  border="0" cellspacing="4" >
					<h:outputText value="¿Desea Ud. Actualizar o Eliminar una Solicitud de Previsión?" rendered="#{solicitudPrevisionController.blnBotonActulizar}"
								style="padding-right: 35px;"/>
					<h:outputText value="¿Desea ver los datos registrados en la Solicitud de Previsión?" rendered="#{!solicitudPrevisionController.blnBotonActulizar}"
								style="padding-left: 60px;"/>
				</h:panelGrid>
				<rich:spacer height="16px"/>
				<h:panelGrid columns="3" border="0"  style="width: 300px;">
					<a4j:commandButton value="Actualizar" actionListener="#{solicitudPrevisionController.irModificarSolicitudPrevision}" 
						styleClass="btnEstilos" rendered="#{solicitudPrevisionController.blnBotonActulizar}" reRender="pgSolicitudPrevision,frmSolicitudPrevisionModalPanel,mpUpDelSolPrevision, divFormPrevision"/>
					<a4j:commandButton value="Ver" actionListener="#{solicitudPrevisionController.verSolicitudPrevision}" styleClass="btnEstilos"
						reRender="pgSolicitudPrevision,frmSolicitudPrevisionModalPanel,mpUpDelSolPrevision,divFormPrevision, frmSolicitudPrevisionView"/>
					<a4j:commandButton value="Eliminar" actionListener="#{solicitudPrevisionController.irEliminarSolicitudPrevision}" 
						onclick="if (!confirm('Está Ud. Seguro de Eliminar esta Solicitud?')) return false;" styleClass="btnEstilos"
						rendered="#{solicitudPrevisionController.blnBotonEliminar}" reRender="pgSolicitudPrevision,frmSolicitudPrevisionModalPanel,mpUpDelSolPrevision,divFormPrevision"/>
				</h:panelGrid>
				
				 <rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 370px;">    
                 <h:panelGrid columns="1"  border="0" cellspacing="4" >
                 <h:outputText value="Imprimir formatos de Previsión" style="padding-right: 2px;"/>
                 <rich:spacer height="16px"/>
                 <h:panelGrid columns="3" border="0"  style="width: 400px;">
                 <rich:column rendered="#{solicitudPrevisionController.mostrarBotonUno}"><h:commandButton id="btnImprimir11" value="Solicitud Sepelio" styleClass="btnEstilos1" action="#{solicitudPrevisionController.imprimirSepelio}"/></rich:column>
                 <rich:column rendered="#{solicitudPrevisionController.mostrarBoton}"><h:commandButton id="btnImprimir21" value="Solicitud de AES" styleClass="btnEstilos1" action="#{solicitudPrevisionController.imprimirSepelioAes}"/></rich:column>
                 <rich:column rendered="#{solicitudPrevisionController.mostrarBotonDos}"><h:commandButton id="btnImprimir3" value="Solicitud Renuncia Fonret" action="#{solicitudPrevisionController.renunciaFonret}" styleClass="btnEstilos1" /></rich:column>
                 </h:panelGrid>	
                 </h:panelGrid>
             </rich:panel>
				
			</rich:panel>
		</h:form>
    
	</rich:modalPanel>

  	
	<rich:modalPanel id="mpBeneficiario" width="750" height="350" style="background-color:#DEEBF5;">
		<f:facet name="header">
			<h:panelGrid>
				<rich:column style="border: none;">
					<h:outputText value="Beneficiario"/>
				</rich:column>
			</h:panelGrid>
		</f:facet>
		<h:panelGroup layout="block">
			<a4j:include viewId="/pages/prevision/popup/agregarBeneficiario.jsp" layout="block"/>
		</h:panelGroup>
	</rich:modalPanel>
	
	  	
	<rich:modalPanel id="mpFallecido" width="750" height="350" style="background-color:#DEEBF5;">
		<f:facet name="header">
			<h:panelGrid>
				<rich:column style="border: none;">
					<h:outputText value="Fallecidos"/>
				</rich:column>
			</h:panelGrid>
		</f:facet>
		<h:panelGroup layout="block">
			<a4j:include viewId="/pages/prevision/popup/agregarFallecido.jsp" layout="block"/>
		</h:panelGroup>
	</rich:modalPanel>
	
	<rich:modalPanel id="pAdjuntarGiroPrevision" width="530" height="200" resizeable="false" style="background-color:#DEEBF5;">
	    <f:facet name="header">
	        <h:panelGrid>
	          <rich:column style="border: none;">
	            <h:outputText value="Adjuntar Requisito Giro Previsión"/>
	          </rich:column>
	        </h:panelGrid>
	    </f:facet>
	    <f:facet name="controls">
	        <h:panelGroup>
	           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
	           		<rich:componentControl for="pAdjuntarGiroPrevision" operation="hide" event="onclick"/>
	           </h:graphicImage>
	       </h:panelGroup>
	    </f:facet>
	    		<h:form>
	    	<h:panelGroup style="background-color:#DEEBF5">
	    		<h:panelGrid columns="1">
		    		<rich:column style="border:none">
        				<rich:fileUpload id="upload" 
			            	 addControlLabel="Adjuntar Imagen"
				             clearControlLabel="Limpiar" 
				             cancelEntryControlLabel="Cancelar"
				             uploadControlLabel="Subir Imagen" 
				             listHeight="65" 
				             listWidth="320"
				             fileUploadListener="#{fileUploadControllerServicio.adjuntarGiroPrevision}"
							 maxFilesQuantity="1"
							 doneLabel="Archivo cargado correctamente"
							 immediateUpload="false"
							 acceptedTypes="jpg, gif, png, bmp">
							 <f:facet name="label">
							 	<h:outputText value="{_KB}KB de {KB}KB cargados --- {mm}:{ss}" />
							 </f:facet>
						</rich:fileUpload>
		        	</rich:column>
		    	</h:panelGrid>
		    	<h:panelGrid columns="2">
					<rich:column width="100">
				    </rich:column>
				    <rich:column style="border:none">
				    	<a4j:commandButton value="Aceptar" 
				    		reRender="panelAdjuntoRequisito"
				    		styleClass="btnEstilos"
				    		action="#{giroPrevisionController.aceptarAdjuntarGiroPrevision}"
			        		oncomplete="Richfaces.hideModalPanel('pAdjuntarGiroPrevision')"/>
				 	</rich:column>
				</h:panelGrid>
	    	</h:panelGroup>
	    </h:form>  
	</rich:modalPanel>	
	
		<a4j:region>
		<a4j:jsFunction name="selecTipoPersona" reRender="cboTipoIdentidad"
						actionListener="#{solicitudPrevisionController.loadListDocumentoBusq}">
			<f:param name="pIntTipoPersona"></f:param>
		</a4j:jsFunction>
		
		<a4j:jsFunction name="selecTipoSolicitud" reRender="pgMsgErrorSolPrev,
															pgLitstaBeneficiarios,pgLitstaBeneficiariosSepelio,lbFechaSustento,
															clFechaSustento,pgBeneficiario,lbGastos,lbMontoGastos,lbNeto,lbMontoNeto,pgPostEvaluacion,
															pgBeneficiarioSepelio,mpCondicionPeriocidadAES, mpCondicionTenerDeuda, mpCondicionPresentacionMaximo,
															pgSoloAplicaSepelio,pgSolicitudPrevision, pgLitstaBeneficiariosSepelio,tblBenenficiarioSepelio,pgLitstaFallecidosSepelio,tblFallecidoSepelio"
						actionListener="#{solicitudPrevisionController.loadSubTipoSolicitud}">
			<f:param name="pIntTipoSolicitud"></f:param>
		</a4j:jsFunction>
		
		<a4j:jsFunction name="selecTipoSolicitudBusqueda" reRender="clnSubOperacionBusqueda"
						actionListener="#{solicitudPrevisionController.loadSubTipoSolicitudBusqueda}">
			<f:param name="pIntTipoSolicitudBusqueda"></f:param>
		</a4j:jsFunction>
		<a4j:jsFunction name="selecTipoSolicitudBusquedaAut" reRender="clnSubOperacionBusquedaAut"
						actionListener="#{autorizacionPrevisionController.loadSubTipoSolicitudBusqueda}">
			<f:param name="pIntTipoSolicitudBusquedaAut"></f:param>
		</a4j:jsFunction>
		
		
		<a4j:jsFunction name="putFileDocAdjunto" reRender="pgListDocAdjuntosPrev"
						actionListener="#{solicitudPrevisionController.putFile}">
		</a4j:jsFunction>
	
		<a4j:include viewId="/pages/fileupload/fileupload.jsp"/>
	</a4j:region>


<rich:tabPanel activeTabClass="activo" inactiveTabClass="inactivo" disabledTabClass="disabled">  
  	<h:outputText value="#{giroPrevisionController.inicioPage}" />
  	<h:outputText value=""/>
     	<rich:tab label="Solicitud" disabled="#{solicitudPrevisionController.blnSolicitud}">
        	<a4j:include viewId="/pages/prevision/tabPrevision.jsp"/>
    	</rich:tab>
		<rich:tab label="Autorización" disabled="#{solicitudPrevisionController.blnAutorizacion}">    
     		<a4j:include viewId="/pages/prevision/autorizacionPrevision/autorizacionPrevisionMain.jsp"/>
     	</rich:tab>
     	<rich:tab label="Giro" disabled="#{solicitudPrevisionController.blnGiro}">
            <a4j:include viewId="/pages/prevision/tabGiro.jsp"/>
     	</rich:tab>
     	<rich:tab label="Archivamiento" disabled="#{solicitudPrevisionController.blnArchivamiento}">
            
     	</rich:tab>
</rich:tabPanel> 


<!-- pop up que muestra las opciones de AUTORIZA / VER para autorizacion prevision   -->

    <rich:modalPanel id="panelUpdateDeleteAutorizacionPrevision" width="400" height="155"
	 	resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Autorizar/Ver Solicitud Previsión"/>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkAutorizacionPrevision"/>
               <rich:componentControl for="panelUpdateDeleteAutorizacionPrevision" attachTo="hidelinkAutorizacionPrevision" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmAutorizacionPrevisionModalPanel">
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 370px;">                 
                <h:panelGrid columns="2"  border="0" cellspacing="4" >
                    <h:outputText value="¿Desea Ud. Autorizar o Ver la Previsión?" style="padding-right: 35px;"/>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                <h:panelGrid columns="3" border="0"  style="width: 300px;">
                    <a4j:commandButton value="Autorizar" actionListener="#{autorizacionPrevisionController.showAutorizacionPrevision}"
                    	reRender="pgAutoricPrevision, panelMensajeAutPrev, frmBusqPrevSocial,pgAutoricPrevision,frmAutorizacionPrevisionModalPanel,panelUpdateDeleteAutorizacionPrevision"
                    	rendered="#{autorizacionPrevisionController.blnMostrarAutorizacion}" styleClass="btnEstilos" 
                    	oncomplete="Richfaces.hideModalPanel('panelUpdateDeleteAutorizacionPrevision')"/>
                    <h:commandButton value="Ver"  styleClass="btnEstilos"/>
                </h:panelGrid>
             </rich:panel>
        </h:form>
    </rich:modalPanel> 
    
    
    <!-- Pop up donde se levanta la solicitud de prevision desde autorizacion -->
    
    <rich:modalPanel id="mpSolicitudPrevisionView" width="1000" height="460" 
		style="background-color:#DEEBF5;">
	    <f:facet name="header">
	    	<h:panelGrid>
	    		<rich:column style="border: none;">
	            	<h:outputText value="Solicitud de Previsión "/>
	            </rich:column>
	        </h:panelGrid>
	    </f:facet>
	    <f:facet name="controls">
	    	<h:panelGroup>
	    		<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
	            	<rich:componentControl for="mpSolicitudPrevisionView" operation="hide" event="onclick"/>
	            </h:graphicImage>
	        </h:panelGroup>
	    </f:facet>
	    
	    <h:form id="frmSolicitudPrevisionView">
		    <a4j:include viewId="/pages/prevision/solicitudPrevisionView.jsp"/>
	    </h:form>
	</rich:modalPanel>
	
	<rich:modalPanel id="pAdjuntarReniecAut" width="400" height="250" resizeable="false" style="background-color:#DEEBF5;">
	    <f:facet name="header">
	        <h:panelGrid>
	          <rich:column style="border: none;">
	            <h:outputText value="Adjuntar Reniec"/>
	          </rich:column>
	        </h:panelGrid>
	    </f:facet>
	    <f:facet name="controls">
	        <h:panelGroup>
	           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
	           		<rich:componentControl for="pAdjuntarReniecAut" operation="hide" event="onclick"/>
	           </h:graphicImage>
	       </h:panelGroup>
	    </f:facet>
	   <a4j:include viewId="/pages/prevision/popup/adjuntarReniec.jsp"/>
	</rich:modalPanel>
	
		<rich:modalPanel id="pAdjuntarDeJu" width="400" height="250" resizeable="false" style="background-color:#DEEBF5;">
	    <f:facet name="header">
	        <h:panelGrid>
	          <rich:column style="border: none;">
	            <h:outputText value="Adjuntar Declaración Jurada"/>
	          </rich:column>
	        </h:panelGrid>
	    </f:facet>
	    <f:facet name="controls">
	        <h:panelGroup>
	           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
	           		<rich:componentControl for="pAdjuntarDeJu" operation="hide" event="onclick"/>
	           </h:graphicImage>
	       </h:panelGroup>
	    </f:facet>
	   <a4j:include viewId="/pages/prevision/popup/adjuntarDeclaracionJurada.jsp"/>
	</rich:modalPanel>
	
	<a4j:jsFunction name="limpiarFallecidos" reRender="tblFallecidoSepelio,blnIsSepelioTitular,clgFallecido,clnBenefRel,pgSolicitudPrevision"
					actionListener="#{solicitudPrevisionController.limpiarGrillasSepelio}">
	</a4j:jsFunction>
	
	<a4j:jsFunction name="recalcularGrillaBeneficiariosPrevision" reRender="pgLitstaBeneficiarios,pgLitstaFallecidosSepelio,pgBeneficiarioSepelio "
					actionListener="#{solicitudPrevisionController.calcularMontosPorcentajeBeneficiariosSepelio}">
	</a4j:jsFunction>