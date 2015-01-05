<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         				-->
	<!-- Autor     : Christian De los Ríos    				-->
	<!-- Modulo    : Nuevo Crédito / Represtamo(F)			-->
	<!-- Prototipo : CONFIGURACIÓN DE APORTES - APORTACIONES-->
	<!-- Fecha     : 06/02/2012               				-->
	

	
	<h:outputText value="#{solicitudPrestamoController.limpiarPrestamo}" />
	
	<rich:jQuery name="disableFormCapacidadPago" selector="#frmCapacidadPago :input" query="attr('disabled','disabled');" timing="onJScall" />
	<rich:jQuery name="disableFormCapacidadPagoEspecial" selector="#frmCapacidadPagoEsp :input" query="attr('disabled','disabled');" timing="onJScall" />
	
	<rich:modalPanel id="panelUpdateDeleteSolicitudPrestamo" width="500" height="300"
	 	resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Actualizar/Eliminar Solicitud Préstamo"/>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkSolicitudPrestamo"/>
               <rich:componentControl for="panelUpdateDeleteSolicitudPrestamo" attachTo="hidelinkSolicitudPrestamo" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmSolicitudPrestamoModalPanel">
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 450px;">                 
                <h:panelGrid columns="1"  border="0" cellspacing="4" >
                    <h:outputText value="¿Desea Ud. Actualizar o Eliminar una Solicitud de Crédito?" style="padding-left: 60px;"/>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                <h:panelGrid columns="3" border="0"  style="width: 450px;">
                    <a4j:commandButton value="Actualizar" actionListener="#{solicitudPrestamoController.irModificarSolicitudPrestamo}" 
	                    styleClass="btnEstilos" rendered="#{solicitudPrestamoController.blnMostrarModificar}" 
	                    reRender="pgControlsPrestamo, opSolicitudCreditoUpdate, opSolicitudCreditoView"
	                    oncomplete="Richfaces.hideModalPanel('panelUpdateDeleteSolicitudPrestamo')"/>
                    <a4j:commandButton value="Ver" actionListener="#{solicitudPrestamoController.irVerSolicitudPrestamo}" styleClass="btnEstilos"
                    	reRender="pgControlsPrestamo, opSolicitudCreditoUpdate, opSolicitudCreditoView"
                    	oncomplete="Richfaces.hideModalPanel('panelUpdateDeleteSolicitudPrestamo')"/>
                    <h:commandButton value="Eliminar" actionListener="#{solicitudPrestamoController.irEliminarSolicitudPrestamo}" 
                    onclick="if (!confirm('Está Ud. Seguro de Eliminar esta Solicitud?')) return false;" styleClass="btnEstilos"
                    rendered="#{solicitudPrestamoController.blnMostrarEliminar}" />
                    </h:panelGrid>
             </rich:panel>
              <!-- aqui va lo de rodolfo -->
              <!-- Botones de las impreciones -->
              <rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 370px;">    
                  
                 <h:panelGrid columns="1"  border="0" cellspacing="4" >
                   		<h:outputText value="Imprimir formatos de Crédito" style="padding-right: 2px;"/>
                 
                 <rich:spacer height="16px"/>
                 <h:panelGrid columns="3" border="0"  style="width: 400px;">
                 <h:commandButton id="btnImprimir1" value="Solicitud de Préstamo" styleClass="btnEstilos1" action="#{solicitudPrestamoController.imprimirSolicitudPrestamo}"/>
                 <h:commandButton id="btnImprimir2" value="Autorización Descuento" styleClass="btnEstilos1" action="#{solicitudPrestamoController.imprimirAutorizacionDescuento}"/>
                 <h:commandButton id="btnImprimir3" value="Autorización de Dscto al 100%" styleClass="btnEstilos1" action="#{solicitudPrestamoController.imprimirautorizacionDscto100}"/>
                 </h:panelGrid>	
                 <h:panelGrid columns="3" border="0"  style="width: 400px;">
                 <rich:column visible="#{solicitudPrestamoController.mostrarBoton}"><h:commandButton id="btnImprimir4" value="Autorización Descuento Cesantes" styleClass="btnEstilos1"
              		action="#{solicitudPrestamoController.imprimirAutorizacionDescuentoCesantes}"/></rich:column>
             	 <rich:column ><h:commandButton id="btnImprimir5" value="Pagaré" styleClass="btnEstilos1" action="#{solicitudPrestamoController.imprimirPagare}"/></rich:column>
                 <rich:column ><h:commandButton id="btnImprimir6" value="Declaración Jurada" styleClass="btnEstilos1" action="#{solicitudPrestamoController.imprimirDeclaraciónJurada}"/></rich:column>
           		 </h:panelGrid>																													 	
                 </h:panelGrid>
             </rich:panel>
             <h:inputHidden id="hiddenIdEmpresa"/>
             <h:inputHidden id="hiddenIdCuenta"/>
             <h:inputHidden id="hiddenIdItemExpediente"/>
             <h:inputHidden id="hiddenIdItemDetExpediente"/>	
        </h:form>
    </rich:modalPanel>
    
    <rich:modalPanel id="panelUpdateDeleteAutorizacionPrestamo" width="400" height="155"
	 	resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Autorizar/Ver Solicitud Préstamo"/>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkAutorizacionPrestamo"/>
               <rich:componentControl for="panelUpdateDeleteAutorizacionPrestamo" attachTo="hidelinkAutorizacionPrestamo" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmAutorizacionPrestamoModalPanel">
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 370px;">                 
                <h:panelGrid columns="1"  border="0" cellspacing="4" >
                    <h:outputText value="¿Desea Ud. Autorizar o Ver el Préstamo?" style="padding-right: 35px;"/>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                <h:panelGrid columns="3" border="0"  style="width: 450px;">
                    <a4j:commandButton value="Autorizar" actionListener="#{autorizacionPrestamoController.showAutorizacionPrestamo}" 
                    	rendered="#{autorizacionPrestamoController.blnMostrarAutorizar}" styleClass="btnEstilos" 
                    	reRender="pgAutoricCredito, pgListAutorizacionCredito,pgMsgErrorCuenta,frmAutorizacionPrestamoModalPanel,panelUpdateDeleteAutorizacionPrestamo"
                    	oncomplete="Richfaces.hideModalPanel('panelUpdateDeleteAutorizacionPrestamo')"/>
                    <a4j:commandButton value="Ver" actionListener="#{autorizacionPrestamoController.showAutorizacionPrestamoView}"  styleClass="btnEstilos" 
                    	reRender="pgAutoricCredito,pgListAutorizacionCredito,pgMsgErrorCuenta,frmAutorizacionPrestamoModalPanel,panelUpdateDeleteAutorizacionPrestamo"
                    	oncomplete="Richfaces.hideModalPanel('panelUpdateDeleteAutorizacionPrestamo')"/>
                </h:panelGrid>
             </rich:panel>
        </h:form>
    </rich:modalPanel>
	
	<rich:modalPanel id="mpCapacidadCredito" width="980" height="450" 
		style="background-color:#DEEBF5;" onhide="disableFormCapacidadPago()">
	    <f:facet name="header">
	    	<h:panelGrid>
	    		<rich:column style="border: none;">
	            	<h:outputText value="Capacidad de Pago "/>
	            </rich:column>
	        </h:panelGrid>
	    </f:facet>
	    <f:facet name="controls">
	    	<h:panelGroup>
	    		<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
	            	<rich:componentControl for="mpCapacidadCredito" operation="hide" event="onclick"/>
	            </h:graphicImage>
	        </h:panelGroup>
	    </f:facet>
	    
	    <h:form id="frmCapacidadPago">
	    	<h:panelGroup rendered="#{capacidadPagoController.intPanelCapacidadPago == applicationScope.Constante.PARAM_T_MODALIDADPLANILLA_HABERES}" layout="block">
		    	<a4j:include viewId="/pages/creditos/solicitudPrestamo/capacidadPagoHaberes.jsp"/>
		    </h:panelGroup>
		    <h:panelGroup rendered="#{capacidadPagoController.intPanelCapacidadPago == applicationScope.Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS}" layout="block">
		    	<a4j:include viewId="/pages/creditos/solicitudPrestamo/capacidadPagoIncentivo.jsp"/>
		    </h:panelGroup>
		    <h:panelGroup rendered="#{capacidadPagoController.intPanelCapacidadPago == applicationScope.Constante.PARAM_T_MODALIDADPLANILLA_CAS}" layout="block">
		    	<a4j:include viewId="/pages/creditos/solicitudPrestamo/capacidadPagoCAS.jsp"/>
		    </h:panelGroup>
	    </h:form>
	</rich:modalPanel>
	
	<rich:modalPanel id="mpSocioEstructura" width="750" height="320"
	 resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Unidades Ejecutoras"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
               		<rich:componentControl for="mpSocioEstructura" operation="hide" event="onclick"/>
               </h:graphicImage>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmSocioEstructura">
        	<a4j:include id="inclSocioEstructura" viewId="/pages/creditos/popup/socioDependenciaBody.jsp"/>
        </h:form>
	</rich:modalPanel>
	
	
<rich:modalPanel id="mpSocioEstructuraEsp" width="750" height="320"
	 resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Unidades Ejecutoras"></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
               		<rich:componentControl for="mpSocioEstructura" operation="hide" event="onclick"/>
               </h:graphicImage>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmSocioEstructuraEsp">
        	<a4j:include id="inclSocioEstructura" viewId="/pages/creditos/prestaTumi/popup/socioDependenciaBody.jsp"/>
        </h:form>
	</rich:modalPanel>
	
	
	<rich:modalPanel id="mpCronogramaCredito" width="1100" height="350"
	 resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Cronograma"/>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
               		<rich:componentControl for="mpCronogramaCredito" operation="hide" event="onclick"/>
               </h:graphicImage>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmCronogramaCredito">
        	<a4j:include viewId="/pages/creditos/popup/cronogramaCreditoBody.jsp"/>
        </h:form>
	</rich:modalPanel>
	
	<rich:modalPanel id="mpGarantesSolidarios" width="950" height="350"
	 resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
              	<h:outputText value="Garante Solidario"/>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
               		<rich:componentControl for="mpGarantesSolidarios" operation="hide" event="onclick"/>
               </h:graphicImage>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmGaranteSolidario">
        	<a4j:include viewId="/pages/creditos/popup/garanteSolidarioBody.jsp"/>
        </h:form>
	</rich:modalPanel>
	
	<rich:modalPanel id="mpSolicitudCreditoView" width="1000" height="460" 
		style="background-color:#DEEBF5;">
	    <f:facet name="header">
	    	<h:panelGrid>
	    		<rich:column style="border: none;">
	            	<h:outputText value="Solicitud de Préstamo "/>
	            </rich:column>
	        </h:panelGrid>
	    </f:facet>
	    <f:facet name="controls">
	    	<h:panelGroup>
	    		<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
	            	<rich:componentControl for="mpSolicitudCreditoView" operation="hide" event="onclick"/>
	            </h:graphicImage>
	        </h:panelGroup>
	    </f:facet>
	    
	    <h:form id="frmSolicitudCreditoView">
		    <a4j:include viewId="/pages/creditos/AutorizacionCredito/solicitudCreditoVista.jsp"/>
	    </h:form>
	</rich:modalPanel>
	
	<rich:modalPanel id="mpFileUpload" width="370" height="230"
		resizeable="false" style="background-color:#DEEBF5">
		<f:facet name="header">
			<h:panelGrid>
				<rich:column style="border: none;">
					<h:outputText value="#{fileUploadControllerServicio.strTitle}"></h:outputText>
				</rich:column>
			</h:panelGrid>
		</f:facet>
		<f:facet name="controls">
			<h:panelGroup>
				<h:graphicImage value="#{fileUploadControllerServicio.strCloseIconPath}"
					styleClass="hidelink" id="hideFileUpload" />
				<rich:componentControl for="mpFileUpload" attachTo="hideFileUpload"
					operation="hide" event="onclick" />
			</h:panelGroup>
		</f:facet>

		<h:form>
			<h:panelGroup>
				<h:panelGrid border="0" columns="1" style="text-align:left; margin:0 auto">
					<rich:column>
						<h:outputText value="#{fileUploadControllerServicio.strDescripcion}"></h:outputText>
					</rich:column>
					<rich:column>
						<rich:fileUpload 
							addControlLabel="Adjuntar Archivo"
							clearAllControlLabel="Limpiar Todo"
							clearControlLabel="Limpiar"  
							cancelEntryControlLabel="Cancelar"
							uploadControlLabel="Subir Archivo" 
							listHeight="64" listWidth="320"
							fileUploadListener="#{fileUploadControllerServicio.adjuntarArchivo}"
							maxFilesQuantity="1" 
							doneLabel="Archivo cargado correctamente"
							immediateUpload="#{applicationScope.FileUtil.autoUpload}"
							acceptedTypes="jpg, gif, png, bmp">
							<f:facet name="label">
								<h:outputText value="{_KB}KB de {KB}KB cargados --- {mm}:{ss}" />
							</f:facet>
						</rich:fileUpload>
					</rich:column>
				</h:panelGrid>
				<rich:spacer height="16px" />
				<h:panelGrid border="0" style="margin:0 auto">
					<a4j:commandButton value="Aceptar" 
										styleClass="btnEstilos" 
										onmousedown="#{fileUploadControllerServicio.strJsFunction}"
										reRender="opAdjuntarDocumento"
										oncomplete="Richfaces.hideModalPanel('mpFileUpload')">
					</a4j:commandButton>
				</h:panelGrid>
			</h:panelGroup>
		</h:form>
	</rich:modalPanel>
	
<a4j:include viewId="/pages/fileupload/fileuploadEspecial.jsp"/>

	<a4j:jsFunction name="putFileDocAdjunto" reRender="infoDocAdjunto, colImgDocAdjunto"
		actionListener="#{solicitudPrestamoController.putFile}">
	</a4j:jsFunction>
	
	<a4j:jsFunction name="putFileDocAdjuntoEspecial" reRender="colImgDocAdjuntoEspecial"
		actionListener="#{solicitudEspecialController.putFile}">
	</a4j:jsFunction>
	
	<rich:modalPanel id="pAlertaRegistro" width="360" height="135" resizeable="false" style="background-color:#DEEBF5;">
	    <f:facet name="header">
	    	<h:panelGrid>
	          <rich:column style="border: none;">
	            <h:outputText value="Alerta"/>
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
	    <h:form>
	    	<h:panelGroup style="background-color:#DEEBF5">
	    		<h:panelGrid columns="1">
		    		<rich:column style="border:none">
		        		<h:outputText value="¿Qué operación desea realizar con el registro seleccionado?"/>
		        	</rich:column>
		    	</h:panelGrid>
		    	<rich:spacer height="12px"/>  
		    	<h:panelGrid columns="2">
		    		<rich:column width="100">
		    		</rich:column>
		    		<rich:column style="border:none" id="colBtnModificar">
		    			<a4j:commandButton value="Girar" styleClass="btnEstilos"
	            			action="#{giroController.verRegistro}"
	             			onclick="Richfaces.hideModalPanel('pAlertaRegistro')"
	             			reRender="contPanelInferior,panelMensajeGiro,panelBotones"/>     		
		        	</rich:column>
		    	</h:panelGrid>
	    	</h:panelGroup>
	    </h:form>
	</rich:modalPanel>


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
	   <a4j:include viewId="/pages/creditos/GiroCredito/buscarApoderado.jsp"/>
	</rich:modalPanel>
	
	<rich:modalPanel id="pAdjuntarCartaPoder" width="530" height="200" resizeable="false" style="background-color:#DEEBF5;">
	    <f:facet name="header">
	        <h:panelGrid>
	          <rich:column style="border: none;">
	            <h:outputText value="Adjuntar Carta Poder"/>
	          </rich:column>
	        </h:panelGrid>
	    </f:facet>
	    <f:facet name="controls">
	        <h:panelGroup>
	           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
	           		<rich:componentControl for="pAdjuntarCartaPoder" operation="hide" event="onclick"/>
	           </h:graphicImage>
	       </h:panelGroup>
	    </f:facet>
	   <a4j:include viewId="/pages/creditos/GiroCredito/adjuntarCartaPoder.jsp"/>
	</rich:modalPanel>
	
	<rich:modalPanel id="pAdjuntarGiroCredito" width="530" height="200" resizeable="false" style="background-color:#DEEBF5;">
	    <f:facet name="header">
	        <h:panelGrid>
	          <rich:column style="border: none;">
	            <h:outputText value="Adjuntar Requisito Giro Credito"/>
	          </rich:column>
	        </h:panelGrid>
	    </f:facet>
	    <f:facet name="controls">
	        <h:panelGroup>
	           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
	           		<rich:componentControl for="pAdjuntarGiroCredito" operation="hide" event="onclick"/>
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
				             fileUploadListener="#{fileUploadControllerServicio.adjuntarGiroCredito}"
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
				    		action="#{giroCreditoController.aceptarAdjuntarGiroCredito}"
			        		oncomplete="Richfaces.hideModalPanel('pAdjuntarGiroCredito')"/>
				 	</rich:column>
				</h:panelGrid>
	    	</h:panelGroup>
	    </h:form>
	    
	</rich:modalPanel>	
	
	
	<rich:modalPanel id="pAdjuntarInfoCorpAutCredito" width="350" height="200" resizeable="false" style="background-color:#DEEBF5;">
	    <f:facet name="header">
	        <h:panelGrid>
	          <rich:column style="border: none;">
	            <h:outputText value="Adjuntar InfoCorp"/>
	          </rich:column>
	        </h:panelGrid>
	    </f:facet>
	    <f:facet name="controls">
	        <h:panelGroup>
	           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
	           		<rich:componentControl for="pAdjuntarInfoCorpAutCredito" operation="hide" event="onclick"/>
	           </h:graphicImage>
	       </h:panelGroup>
	    </f:facet>
	   <a4j:include viewId="/pages/creditos/AutorizacionCredito/adjuntarInfoCorp.jsp"/>
	</rich:modalPanel>
	
	
	<rich:modalPanel id="pAdjuntarReniecAutCredito" width="350" height="200" resizeable="false" style="background-color:#DEEBF5;">
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
	           		<rich:componentControl for="pAdjuntarReniecAutCredito" operation="hide" event="onclick"/>
	           </h:graphicImage>
	       </h:panelGroup>
	    </f:facet>
	   <a4j:include viewId="/pages/creditos/AutorizacionCredito/adjuntarReniec.jsp"/>
	</rich:modalPanel>	

<!-- POPUP DE ACCIONES PARA CREDITOS ESPECIALES 777-->
		<rich:modalPanel id="panelAccionesSolicitudPrestamoEspecial" width="500" height="300"
			resizeable="false" style="background-color:#DEEBF5;">
			<f:facet name="header">
				<h:panelGrid>
					<rich:column style="border: none;">
						<h:outputText value="Actualizar/Eliminar Solicitud Préstamo"/>
					</rich:column>
				</h:panelGrid>
			</f:facet>
			<f:facet name="controls">
				<h:panelGroup>
					<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkSolicitudPrestamoEspecial"/>
					<rich:componentControl for="panelAccionesSolicitudPrestamoEspecial" attachTo="hidelinkSolicitudPrestamoEspecial" 
						operation="hide" event="onclick"/>
				</h:panelGroup>
			</f:facet>
			
			<h:form id="frmAccionesSolicitudPrestamoEspecial">
				<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 450px;">                 
					<h:panelGrid columns="1"  border="0" cellspacing="4" >
						<h:outputText value="¿Desea Ud. Actualizar o Eliminar una Solicitud de Crédito Especial?" style="padding-left: 60px;"/>
					</h:panelGrid>
					<rich:spacer height="16px"/>
					<h:panelGrid columns="3" border="0"  style="width: 450px;">
						<a4j:commandButton value="Actualizar" actionListener="#{solicitudEspecialController.irModificarSolicitudPrestamo}" 
										styleClass="btnEstilos" rendered="#{solicitudEspecialController.blnMostrarModificar}" 
										reRender="pgSolicCreditoEspecial,frmAccionesSolicitudPrestamoEspecial,panelAccionesSolicitudPrestamoEspecial,pgControlsEspecial"/>
						<a4j:commandButton value="Ver" actionListener="#{solicitudEspecialController.irVerSolicitudPrestamo}" styleClass="btnEstilos"
										reRender="pgSolicCreditoEspecial, frmAccionesSolicitudPrestamoEspecial,panelAccionesSolicitudPrestamoEspecial,dgDocumentos,pgControlsEspecial"/>
						<h:commandButton value="Eliminar" actionListener="#{solicitudEspecialController.irEliminarSolicitudPrestamo}" 
										onclick="if (!confirm('Está Ud. Seguro de Eliminar esta Solicitud?')) return false;" styleClass="btnEstilos"
										rendered="#{solicitudEspecialController.blnMostrarEliminar}" />
					</h:panelGrid>
				</rich:panel>
				              <!-- Botones de las impreciones -->
              <rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 450px;">    
                  
                 <h:panelGrid columns="1"  border="0" cellspacing="4" >
                   		<h:outputText value="Imprimir formatos de Crédito" style="padding-right: 2px;"/>
                 
                 <rich:spacer height="16px"/>
                 <h:panelGrid columns="3" border="0"  style="width: 400px;">
                 <h:commandButton id="btnImprimir7" value="Solicitud de Préstamo" styleClass="btnEstilos1" action="#{solicitudEspecialController.imprimirSolicitudPrestamo}"/>
                 <h:commandButton id="btnImprimir8" value="Autorización Descuento" styleClass="btnEstilos1" action="#{solicitudEspecialController.imprimirAutorizacionDescuento}"/>
                 <h:commandButton id="btnImprimir9" value="Autorización de Dscto al 100%" styleClass="btnEstilos1" action="#{solicitudEspecialController.imprimirautorizacionDscto100}"/>
                 </h:panelGrid>
                 <h:panelGrid columns="3" border="0"  style="width: 400px;">
                  <rich:column visible="#{solicitudEspecialController.mostrarBoton}"><h:commandButton id="btnImprimir10" value="Autorización Descuento Cesantes" styleClass="btnEstilos1" 
             				  action="#{solicitudEspecialController.imprimirAutorizacionDescuentoCesantes}"/></rich:column>
            	  <rich:column ><h:commandButton id="btnImprimir11" value="Pagaré" styleClass="btnEstilos1" action="#{solicitudEspecialController.imprimirPagare}"/></rich:column>
             	  <rich:column ><h:commandButton id="btnImprimir12" value="Declaración Jurada" styleClass="btnEstilos1" action="#{solicitudEspecialController.imprimirDeclaraciónJurada}"/></rich:column>
             	  </h:panelGrid>	
                 																												 	
                 </h:panelGrid>
             </rich:panel>
              <!-- Aca termina los botones -->
              
            
			</h:form>
		</rich:modalPanel>
		
		<!-- POP UP DE UNIDADES EJECUTORAS - CAPACIDADES PARA CREDITOS ESPECIALES -->
		
			<rich:modalPanel id="mpCapacidadCreditoEsp" width="980" height="450"  style="background-color:#DEEBF5;" onhide="disableFormCapacidadPagoEspecial()">
				<f:facet name="header">
					<h:panelGrid>
						<rich:column style="border: none;">
							<h:outputText value="Capacidad de Pago "/>
						</rich:column>
					</h:panelGrid>
				</f:facet>
				<f:facet name="controls">
					<h:panelGroup>
						<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
							<rich:componentControl for="mpCapacidadCreditoEsp" operation="hide" event="onclick"/>
						</h:graphicImage>
					</h:panelGroup>
				</f:facet>

				<rich:jQuery name="disableFormCapacidadPagoEspecial" selector="#frmCapacidadPagoEsp :input" query="attr('disabled','disabled');" timing="onJScall" />
				<h:form id="frmCapacidadPagoEspecial">
					<h:panelGroup rendered="#{capacidadPagoController.intPanelCapacidadPago == applicationScope.Constante.PARAM_T_MODALIDADPLANILLA_HABERES}" layout="block">
						<a4j:include viewId="/pages/creditos/prestaTumi/popup/capacidadPagoHaberes.jsp"/>
					</h:panelGroup>
					<h:panelGroup rendered="#{capacidadPagoController.intPanelCapacidadPago == applicationScope.Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS}" layout="block">
						<a4j:include viewId="/pages/creditos/prestaTumi/popup/capacidadPagoIncentivo.jsp"/>
					</h:panelGroup>
					<h:panelGroup rendered="#{capacidadPagoController.intPanelCapacidadPago == applicationScope.Constante.PARAM_T_MODALIDADPLANILLA_CAS}" layout="block">
						<a4j:include viewId="/pages/creditos/prestaTumi/popup/capacidadPagoCAS.jsp"/>
					</h:panelGroup>
				</h:form>
			</rich:modalPanel>
			
			
			<!-- CRONOGRAMA CREDITO ESPECIAL -->
	<rich:modalPanel id="mpCronogramaCreditoEsp" width="950" height="350"
	 	resizeable="false" style="background-color:#DEEBF5">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Cronograma"/>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
               		<rich:componentControl for="mpCronogramaCreditoEsp" operation="hide" event="onclick"/>
               </h:graphicImage>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmCronogramaCreditoEsp">
        	<a4j:include viewId="/pages/creditos/prestaTumi/popup/cronogramaEspecial.jsp"/>
        </h:form>
	</rich:modalPanel>
	<%--Inicio del popup autor: Rodolfo Villarreal Acuña --%>
  <rich:modalPanel id="formEnBlanco" width="500" height="180" 
	resizeable="false" style="background-color:#DEEBF5;" onhide="enableFormEnBlanco()">
     <f:facet name="header">
         <h:panelGrid>
           <rich:column style="border: none;">
             <h:outputText value="Opciones"/>
           </rich:column>
         </h:panelGrid>
     </f:facet>
     <f:facet name="controls">
         <h:panelGroup>
            <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
            	<rich:componentControl for="formEnBlanco" operation="hide" event="onclick"/>
            </h:graphicImage>
        </h:panelGroup>
     </f:facet>
     <a4j:include viewId="/pages/creditos/popup/formatosEnBlancoSolicitudCredito.jsp" layout="block"/>
  </rich:modalPanel>
			    <%--Fin del pop imprimir formtos en blanco --%>
	<a4j:include viewId="/pages/creditos/popup/tercerosPrestamo.jsp"/>
	
	<!-- Autor: jchavez / Tarea: Creación / Fecha: 16.12.2014 -->
	<rich:modalPanel id="mpAdjuntarDocSolicitudPrestamo" width="370" height="230"
		resizeable="false" style="background-color:#DEEBF5">
		<f:facet name="header">
			<h:panelGrid>
				<rich:column style="border: none;">
					<h:outputText value="#{fileUploadControllerServicio.strTitle}"></h:outputText>
				</rich:column>
			</h:panelGrid>
		</f:facet>
		<f:facet name="controls">
			<h:panelGroup>
				<h:graphicImage value="#{fileUploadControllerServicio.strCloseIconPath}"
					styleClass="hidelink" id="hideAdjuntarDocSolicitudPrestamo" />
				<rich:componentControl for="mpAdjuntarDocSolicitudPrestamo" attachTo="hideAdjuntarDocSolicitudPrestamo"
					operation="hide" event="onclick" />
			</h:panelGroup>
		</f:facet>	
		<a4j:include viewId="/pages/creditos/popup/adjuntarDocSolicitudPrestamo.jsp"/>	
	</rich:modalPanel>	
		
	<a4j:region>
		<a4j:jsFunction name="getMontoSolicitado" reRender="idMontoSolicitado"
			actionListener="#{solicitudPrestamoController.setMontoSolicitado}">
			<f:param name="bdMontoSolicitado"/>
		</a4j:jsFunction>
		
		<a4j:jsFunction name="renderizarTextBusquedaAut" reRender="txtBusquedaAut"
			actionListener="#{autorizacionPrestamoController.renderizarTextBusqueda}">
			<f:param name="pIntTipoConsultaBusqueda"></f:param>
		</a4j:jsFunction>
		
		<a4j:jsFunction name="renderizarBusquedaFechasAut" reRender="cmbFechaAut, dtDesdeAut, dtHastaAut"
			actionListener="#{autorizacionPrestamoController.renderizarBusquedaFechas}">
			<f:param name="pIntParaEstado"></f:param>
		</a4j:jsFunction>
		
		<a4j:jsFunction name="renderizarBusquedaCondicionAut" reRender="cmbCondicionAut"
			actionListener="#{autorizacionPrestamoController.renderizarBusquedaCondicion}">
			<f:param name="pIntParaEstadoCondicion"></f:param>
		</a4j:jsFunction>
		
		<a4j:jsFunction name="renderizarTextBusqueda" reRender="txtBusqueda"
			actionListener="#{solicitudPrestamoController.renderizarTextBusqueda}">
			<f:param name="pIntTipoConsultaBusqueda"></f:param>
		</a4j:jsFunction>
	</a4j:region>

	

	<h:outputText value="#{giroCreditoController.inicioPage}" />

    <h:form id="frmPrincipal">
    	<h:outputText value="#{solicitudPrestamoController.limpiarPrestamo}"/>
    	<h:outputText value="#{autorizacionPrestamoController.limpiarAutorizacion}"/>
    	<h:outputText value="#{solicitudEspecialController.limpiarPrestamoEspecial}"/> 	
    	
    	<rich:tabPanel activeTabClass="activo" inactiveTabClass="inactivo" disabledTabClass="disabled">
	        <rich:tab label="Solicitud" disabled="#{solicitudPrestamoController.blnSolicitud}">	        	
	         	<a4j:include viewId="/pages/creditos/SolicitudCredito/solicitudCreditoMain.jsp"/>
	    	</rich:tab>
	    	
	    	 <rich:tab label="Solicitudes Especiales" disabled="#{solicitudPrestamoController.blnCreditosEspeciales}">	        	
	         	<a4j:include viewId="/pages/creditos/prestaTumi/solicitudPrestaTumiMain.jsp"/>
	    	</rich:tab>
	    	
	    	<rich:tab label="Autorización" disabled="#{solicitudPrestamoController.blnAutorizacion}">
	         	<a4j:include viewId="/pages/creditos/AutorizacionCredito/autorizacionCreditoMain.jsp"/>
	    	</rich:tab>
	    	<rich:tab label="Giro" disabled="#{solicitudPrestamoController.blnGiro}">
	         	<a4j:include viewId="/pages/creditos/GiroCredito/giroCredito.jsp"/>
	    	</rich:tab>
	    	<rich:tab label="Archivamiento" disabled="#{solicitudPrestamoController.blnArchivamiento}">
	         	
	    	</rich:tab>
      	</rich:tabPanel>
    </h:form>