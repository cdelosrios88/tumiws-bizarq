<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Arturo Julca    		  -->			
	<!-- Fecha     : 05/10/2012               -->


<h:outputText value="#{solicitudLiquidacionController.limpiarLiquidacion}"/>

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
	   <a4j:include viewId="/pages/liquidacion/popup/buscarApoderado.jsp"/>
	</rich:modalPanel>
	
	<rich:modalPanel id="pAdjuntarCartaPoder" width="400" height="200" resizeable="false" style="background-color:#DEEBF5;">
	    <f:facet name="header">
	        <h:panelGrid>
	          <rich:column style="border: none;">
	            <h:outputText value="Adjuntar Carta Poder"/>
	          </rich:column>
	        </h:panelGrid>
	    </f:facet>
	   <a4j:include viewId="/pages/liquidacion/popup/adjuntarCartaPoder.jsp"/>
	</rich:modalPanel>



	<rich:tabPanel activeTabClass="activo" inactiveTabClass="inactivo" disabledTabClass="disabled">
		<h:outputText value="#{giroLiquidacionController.inicioPage}" />
    	
     	<rich:tab label="Solicitud" disabled="#{solicitudLiquidacionController.blnSol}">
        	 <a4j:include viewId="/pages/liquidacion/tabSolicitud.jsp"/>
    	</rich:tab>
		<rich:tab label="Autorización" disabled="#{solicitudLiquidacionController.blnAut}">
            <a4j:include viewId="/pages/liquidacion/autorizacionLiquidacion/autorizacionLiquidacionMain.jsp"/>
     	</rich:tab>
     	<rich:tab label="Giro" disabled="#{solicitudLiquidacionController.blnGir}">
            <a4j:include viewId="/pages/liquidacion/tabGiro.jsp"/>
     	</rich:tab> 
     	<rich:tab label="Archivamiento" disabled="#{solicitudLiquidacionController.blnArch}">
            
     	</rich:tab>

	</rich:tabPanel>  
	
	<a4j:jsFunction name="selecTipoPersonaLiquidacion" reRender="cboTipoIdentidadLiquidacion"
						actionListener="#{solicitudLiquidacionController.loadListDocumentoBusq}">
			<f:param name="pIntTipoPersonaLiquidacion"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selecTipoSolicitudLiquidacion" reRender="pgMotivoRenuncia, pgdMotivoRenuncia, pgSolicitudLiquidacion"
				actionListener="#{solicitudLiquidacionController.loadMotivoRenuncia}">
	<f:param name="pIntSubTipoOperacion"></f:param>
	</a4j:jsFunction>
	
	
		<rich:modalPanel id="mpBeneficiarioLiquidacion" width="750" height="350" style="background-color:#DEEBF5;">
		<f:facet name="header">
			<h:panelGrid>
				<rich:column style="border: none;">
					<h:outputText value="Beneficiario"/>
				</rich:column>
			</h:panelGrid>
		</f:facet>
		<h:panelGroup layout="block">
			<a4j:include viewId="/pages/liquidacion/popup/agregarBeneficiario.jsp" layout="block"/>
		</h:panelGroup>
	</rich:modalPanel>
	
	<a4j:jsFunction name="selecFechaRenuncia" reRender="calFecRecep"
				actionListener="#{solicitudLiquidacionController.loadFechaRenuncia}">
		<f:param name="pdtFechaRenuncia"></f:param>
	</a4j:jsFunction>
	
	
	
	<rich:modalPanel id="mpUpDelSolLiquidacion" width="480" height="230"
					resizeable="false" style="background-color:#DEEBF5;">
		<f:facet name="header">
			<h:panelGrid>
				<rich:column style="border: none;">
					<h:outputText value="Actualizar/Eliminar Solicitud Liquidación"/>
				</rich:column>
			</h:panelGrid>
		</f:facet>
		
		<f:facet name="controls">
			<h:panelGroup>
				<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkSolicitudLiquidacion"/>
				<rich:componentControl for="mpUpDelSolLiquidacion" attachTo="hidelinkSolicitudLiquidacion" operation="hide" event="onclick"/>
			</h:panelGroup>
		</f:facet>
		
	<rich:modalPanel id="pAdjuntarGiroLiquidacion" width="530" height="260" resizeable="false" style="background-color:#DEEBF5;">
	    <f:facet name="header">
	        <h:panelGrid>
	          <rich:column style="border: none;">
	            <h:outputText value="Adjuntar Requisito Giro Liquidación"/>
	          </rich:column>
	        </h:panelGrid>
	    </f:facet>
	    <f:facet name="controls">
	        <h:panelGroup>
	           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
	           		<rich:componentControl for="pAdjuntarGiroLiquidacion" operation="hide" event="onclick"/>
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
				             fileUploadListener="#{fileUploadControllerServicio.adjuntarGiroLiquidacion}"
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
				    		action="#{giroLiquidacionController.aceptarAdjuntarGiroLiquidacion}"
			        		oncomplete="Richfaces.hideModalPanel('pAdjuntarGiroLiquidacion')"/>
				 	</rich:column>
				</h:panelGrid>
	    	</h:panelGroup>
	    </h:form>
	</rich:modalPanel>	
	
		<h:form id="frmSolicitudLiquidacionModalPanel">
			<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 370px;">                 
				<h:panelGrid columns="2"  border="0" cellspacing="4" >
					<h:outputText value="¿Desea Ud. Actualizar o Eliminar una Solicitud de Liquidación?" style="padding-right: 35px;"/>
				</h:panelGrid>
				<rich:spacer height="16px"/>
				<h:panelGrid columns="3" border="0"  style="width: 300px;">
					<a4j:commandButton value="Actualizar" actionListener="#{solicitudLiquidacionController.irModificarSolicitudLiquidacion}" 
									rendered="#{solicitudLiquidacionController.blnMonstrarActualizar}"	styleClass="btnEstilos"
									reRender="pgSolicitudLiquidacion,frmSolicitudLiquidacionModalPanel,mpUpDelSolLiquidacion,pgMsgErrorInicial,divFormLiquidacion,divFormSolicitudLiquidacion,pgViewSolicitudLiquidacion,opViewSolicitudLiquidacion,pgAutorizacionesPreviasLiquidacion">
					</a4j:commandButton>
					<a4j:commandButton value="Ver" actionListener="#{solicitudLiquidacionController.verSolicitudLiquidacion}" styleClass="btnEstilos"
						reRender="pgSolicitudLiquidacionView,frmSolicitudLiquidacionModalPanel,mpUpDelSolLiquidacion,pgMsgErrorInicial,pgVerSolicitudLiquidacion,divFormLiquidacion,pgViewSolicitudLiquidacion,opViewSolicitudLiquidacion,pgAutorizacionesPreviasLiquidacion">
					</a4j:commandButton>
					<a4j:commandButton value="Eliminar" actionListener="#{solicitudLiquidacionController.irEliminarSolicitudLiquidacion}"
									rendered="#{solicitudLiquidacionController.blnMonstrarEliminar}" onclick="if (!confirm('Está Ud. Seguro de Eliminar esta Solicitud?')) return false;" styleClass="btnEstilos">
					</a4j:commandButton>
				</h:panelGrid>
			</rich:panel>
			<!-- Botones de las impreciones -->
              <rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 400px;" id="rpBotonesImpresion">    
					<h:panelGrid columns="1"  border="0" cellspacing="4" >
                   		<h:outputText value="Imprimir Formatos de Liquidación" style="padding-right: 2px;"/>
                <rich:spacer height="16px"/>
              	<h:panelGrid columns="3" border="0"  style="width: 390px;">
         		<h:commandButton id="btnImprimir31" value="Solicitud Renuncia" action="#{solicitudLiquidacionController.imprimirSepelioRenuncia}" styleClass="btnEstilos1" />
              	</h:panelGrid>
				</h:panelGrid>
             </rich:panel>
		</h:form>
    
	</rich:modalPanel>
	
	
<!-- ----------------- autorizacion  -->

<!-- pop up que muestra las opciones de AUTORIZA / VER para autorizacion prevision   -->

    <rich:modalPanel id="panelUpdateDeleteAutorizacionLiquidacion" width="400" height="155"
	 	resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Autorizar/Ver Solicitud Liquidación"/>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkAutorizacionLiquidacion"/>
               <rich:componentControl for="panelUpdateDeleteAutorizacionLiquidacion" attachTo="hidelinkAutorizacionLiquidacion" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmAutorizacionLiquidacionModalPanel">
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 370px;">                 
                <h:panelGrid columns="2"  border="0" cellspacing="4" >
                    <h:outputText value="¿Desea Ud. Autorizar o Ver la Liquidación?" style="padding-right: 35px;"/>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                <h:panelGrid columns="3" border="0"  style="width: 300px;">
                    <a4j:commandButton value="Autorizar" actionListener="#{autorizacionLiquidacionController.showAutorizacionLiquidacion}" styleClass="btnEstilos" 
                    	reRender="pgAutoricLiquidacion,pgPersonasEncargadas,pgListDocAdjuntosVerificacionLiqui,frmAutorizacionLiquidacionModalPanel,panelUpdateDeleteAutorizacionLiquidacion"
                    	oncomplete="Richfaces.hideModalPanel('panelUpdateDeleteAutorizacionLiquidacion')"
                    	rendered="#{autorizacionLiquidacionController.blnBotonAutorizar}"/>
                    <a4j:commandButton value="Ver"  styleClass="btnEstilos"/>
                </h:panelGrid>
             </rich:panel>
        </h:form>
    </rich:modalPanel> 
    
    
    <!-- Pop up donde se levanta la solicitud de prevision desde autorizacion -->
    
    <rich:modalPanel id="mpSolicitudLiquidacionView" width="1000" height="460" 
		style="background-color:#DEEBF5;">
	    <f:facet name="header">
	    	<h:panelGrid>
	    		<rich:column style="border: none;">
	            	<h:outputText value="Solicitud de Liquidación "/>
	            </rich:column>
	        </h:panelGrid>
	    </f:facet>
	    <f:facet name="controls">
	    	<h:panelGroup>
	    		<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
	            	<rich:componentControl for="mpSolicitudLiquidacionView" operation="hide" event="onclick"/>
	            </h:graphicImage>
	        </h:panelGroup>
	    </f:facet>
	    
	    <h:form id="frmSolicitudLiquidacionView">
		    <a4j:include viewId="/pages/liquidacion/solicitudLiquidacionView.jsp"/>
	    </h:form>
	</rich:modalPanel>
	
	<rich:modalPanel id="pAdjuntarReniecAutLiq" width="360" height="200" resizeable="false" style="background-color:#DEEBF5;">
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
	           		<rich:componentControl for="pAdjuntarReniecAutLiq" operation="hide" event="onclick"/>
	           </h:graphicImage>
	       </h:panelGroup>
	    </f:facet>
	   <a4j:include viewId="/pages/liquidacion/popup/adjuntarReniec.jsp"/>
	</rich:modalPanel>
	
		<rich:modalPanel id="pAdjuntarDeJuLiq" width="360" height="200" resizeable="false" style="background-color:#DEEBF5;">
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
	           		<rich:componentControl for="pAdjuntarDeJuLiq" operation="hide" event="onclick"/>
	           </h:graphicImage>
	       </h:panelGroup>
	    </f:facet>
	   <a4j:include viewId="/pages/liquidacion/popup/adjuntarDeclaracionJurada.jsp"/>
	</rich:modalPanel>


	<a4j:jsFunction name="selecTipoCambio" reRender="pgCambiosEnSolicitud, pgCambioFecha, pgMotivoRenuncia"
						actionListener="#{autorizacionLiquidacionController.loadCampoCambio}">
			<f:param name="pIntTipoCambio"></f:param>
	</a4j:jsFunction>   
	
	<a4j:jsFunction name="renderizarTextBusqueda" reRender="txtBusqueda"
					actionListener="#{solicitudLiquidacionController.renderizarTextBusqueda}">
		<f:param name="pIntTipoConsultaBusqueda"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="renderizarBusquedaFechas" reRender="cmbFecha, dtDesde, dtHasta"
					actionListener="#{solicitudLiquidacionController.renderizarBusquedaFechas}">
		<f:param name="pIntParaEstado"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="renderizarBusquedaCondicion" reRender="cmbCondicion"
					actionListener="#{solicitudLiquidacionController.renderizarBusquedaCondicion}">
		<f:param name="pIntParaEstadoCondicion"></f:param>
	</a4j:jsFunction>
	
	
<a4j:include viewId="/pages/fileupload/fileupload.jsp"/>
	<a4j:jsFunction name="putFileDocAdjunto" reRender="colImgDocAdjunto, pgListDocAdjuntosLiquidacion"
		actionListener="#{solicitudLiquidacionController.putFile}">
	</a4j:jsFunction>
	
	
	<a4j:jsFunction name="renderizarTextBusquedaAut" reRender="txtBusquedaAut"
					actionListener="#{autorizacionLiquidacionController.renderizarTextBusqueda}">
		<f:param name="pIntTipoConsultaBusqueda"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="renderizarBusquedaFechasAut" reRender="cmbFechaAut, dtDesdeAut, dtHastaAut"
					actionListener="#{autorizacionLiquidacionController.renderizarBusquedaFechas}">
		<f:param name="pIntParaEstado"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="renderizarBusquedaCondicionAut" reRender="cmbCondicionAut"
					actionListener="#{autorizacionLiquidacionController.renderizarBusquedaCondicion}">
		<f:param name="pIntParaEstadoCondicion"></f:param>
	</a4j:jsFunction>
	
