<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- 	Empresa   : TUMI							-->
<!-- 	Modulo    : REFINANCIAMIENTO				-->
<!-- 	Prototipo : SOLICITUD DE REFINANCIAMIENTO	-->
<!-- 	Fecha     : 22/02/2013						-->

<!-- Contiene:
		1. Los tabs de la ventana de Refinanciamiento.
		2. selecTipoPersonaRefinan
		3. seleccionarTipoOperacion
		4. putFileDocAdjunto
		5. fileupload.jsp
		6. mpSocioEstructuraRef. Redirecciona a: socioDependenciaBody.jsp 
		7. mpCapacidadCreditoRef. Redirecciona a: capacidadPagoHaberes.jsp - capacidadPagoIncentivo.jsp - capacidadPagoCAS.jsp
		8. panelUpdateDeleteSolicitudRefinan.(A/V/E)
		9. mpCronogramaRefinanciamiento. Redirecciona a: cronogramaRefinan.jsp
	   10. mpGarantesSolidariosRef. Redirecciona a: garanteSolidarioBody.jsp
	   11. pAdjuntarInfoCorpRef. Redirecciona a: adjuntarInfoCorp.jsp 
	   12. pAdjuntarReniecRef. Redirecciona a: adjuntarReniec.jsp
	   13. mpSolicitudRefinanciamientoView. Redirecciona a: SolicitudRefinanView.jsp
-->

<h:outputText value="#{solicitudRefinanController.limpiarRefinanciamiento}" />

	<!-- 1. Tabs de refinanciamiento				-->
    <!-- disabled="{solicitudPrestamoController.blnAutorizacion}  disabled="{solicitudPrestamoController.blnSolicitud}" -->
			<h:form id="frmPrincipalRefinan">
				<h:outputText value="#{solicitudRefinanController.limpiarRefinanciamiento}" />
				<h:outputText value="#{autorizacionRefinanController.limpiarAutorizacion}" />
				
				<rich:tabPanel activeTabClass="activo" inactiveTabClass="inactivo" disabledTabClass="disabled">
					<rich:tab label="Solicitud" disabled="#{solicitudRefinanController.blnSolicitud}">
						<a4j:include viewId="/pages/reprogRefinan/Solicitud/solicitudRefinanMain.jsp"/>
					</rich:tab>
					<rich:tab label="Autorización" disabled="#{solicitudRefinanController.blnAutorizacion}">
						<a4j:include viewId="/pages/reprogRefinan/Autorizacion/autorizacionRefinanMain.jsp"/>
					</rich:tab>
					<rich:tab label="Archivamiento">
					
					</rich:tab>
				</rich:tabPanel>
			</h:form>


	<!-- 2. selecTipoPersonaRefinan ==> Carga el combo de Tipo de Documento, dependiendo del Tipo de persona seleccionado.  -->
			<a4j:jsFunction name="selecTipoPersonaRefinan" reRender="cboTipoIdentidad" actionListener="#{solicitudRefinanController.cargarComboDocumento}">
					<f:param name="pIntTipoPersona"></f:param>
			</a4j:jsFunction>
	
	
	<!-- 3. seleccionarTipoOperacion ==> Carga el combo de Tipo de Operacion en la validacion de socio.  -->
			<a4j:jsFunction name="seleccionarTipoOperacion" actionListener="#{solicitudRefinanController.cargarTipoOperacion}">
					<f:param name="pIntTipoOperacion"></f:param>
			</a4j:jsFunction>
	
	
	<!-- 4. putFileDocAdjunto -->
			<a4j:jsFunction name="putFileDocAdjunto" reRender="colImgDocAdjunto,pgListDocAdjuntosRef" actionListener="#{solicitudRefinanController.putFile}"/>
	
	
	<!-- 5. fileupload.jsp -->
			<a4j:include viewId="/pages/fileupload/fileupload.jsp"/>
	
	
	<!-- 6. mpSocioEstructuraRef => PopUp para agregar Capacidad de Credito. Redirecciona a socioDependenciaBody.jsp -->	
			<rich:modalPanel id="mpSocioEstructuraRef" width="750" height="320" resizeable="false" style="background-color:#DEEBF5">
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
							<rich:componentControl for="mpSocioEstructuraRef" operation="hide" event="onclick"/>
						</h:graphicImage>
					</h:panelGroup>
				</f:facet>
				<h:form id="frmSocioEstructuraRef">
					<a4j:include id="inclSocioEstructura" viewId="/pages/reprogRefinan/popUp/socioDependenciaBody.jsp"/>
				</h:form>
			</rich:modalPanel>
	
	
	<!-- 7. mpCapacidadCreditoRef => PopUp para agregar Capacidad de Credito segun tipo de socio-->		
			<rich:modalPanel id="mpCapacidadCreditoRef" width="980" height="450"  style="background-color:#DEEBF5;" onhide="disableFormCapacidadPago()">
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
							<rich:componentControl for="mpCapacidadCreditoRef" operation="hide" event="onclick"/>
						</h:graphicImage>
					</h:panelGroup>
				</f:facet>

				<rich:jQuery name="disableFormCapacidadPago" selector="#frmCapacidadPago :input" query="attr('disabled','disabled');" timing="onJScall" />
				<h:form id="frmCapacidadPago">
					<h:panelGroup rendered="#{capacidadPagoController.intPanelCapacidadPago == applicationScope.Constante.PARAM_T_MODALIDADPLANILLA_HABERES}" layout="block">
						<a4j:include viewId="/pages/reprogRefinan/Solicitud/capacidadPagoHaberes.jsp"/>
					</h:panelGroup>
					<h:panelGroup rendered="#{capacidadPagoController.intPanelCapacidadPago == applicationScope.Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS}" layout="block">
						<a4j:include viewId="/pages/reprogRefinan/Solicitud/capacidadPagoIncentivo.jsp"/>
					</h:panelGroup>
					<h:panelGroup rendered="#{capacidadPagoController.intPanelCapacidadPago == applicationScope.Constante.PARAM_T_MODALIDADPLANILLA_CAS}" layout="block">
						<a4j:include viewId="/pages/reprogRefinan/Solicitud/capacidadPagoCAS.jsp"/>
					</h:panelGroup>
				</h:form>
			</rich:modalPanel>
			
			
	<!-- 8. panelUpdateDeleteSolicitudRefinan => PopUp que muestra: Actualizar, Ver y Eliminar -->
			<rich:modalPanel id="panelUpdateDeleteSolicitudRefinan" width="500" height="300" resizeable="false" style="background-color:#DEEBF5;">
				<f:facet name="header">
					<h:panelGrid>
						<rich:column style="border: none;">
							<h:outputText value="Modificar/Ver Solicitud Refinanciamiento"/>
						</rich:column>
					</h:panelGrid>
				</f:facet>
				<f:facet name="controls">
					<h:panelGroup>
						<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkSolicitudRefinan"/>
						<rich:componentControl for="panelUpdateDeleteSolicitudRefinan" attachTo="hidelinkSolicitudRefinan" operation="hide" event="onclick"/>
					</h:panelGroup>
				</f:facet>
				<h:form id="frmSolicitudRefinanModalPanel">
					<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 450px;">                 
						<h:panelGrid columns="2"  border="0" cellspacing="4" >
							<h:outputText value="¿Desea Ud. Actualizar o Eliminar una Solicitud de Refinanciamiento?" style="padding-left: 60px;"/>
						</h:panelGrid>
						<rich:spacer height="16px"/>
						<h:panelGrid columns="3" border="0"  style="width: 450px;">
							
							<a4j:commandButton value="Actualizar" rendered="#{solicitudRefinanController.blnMonstrarActualizar}" 
								actionListener="#{solicitudRefinanController.irModificarSolicitudRefinan}" styleClass="btnEstilos"
								reRender="pgSolicRefinan, frmSolicitudRefinanModalPanel, panelUpdateDeleteSolicitudRefinan,pgSolicitudRefinanView"/>
							<a4j:commandButton value="Ver" actionListener="#{solicitudRefinanController.irVerSolicitudRefinan}"
								styleClass="btnEstilos"
								reRender="pgSolicRefinan, frmSolicitudRefinanModalPanel, panelUpdateDeleteSolicitudRefinan,"/>
							<a4j:commandButton value="Eliminar" actionListener="#{solicitudRefinanController.irEliminarSolicitudRefinan}" 
								rendered="#{solicitudRefinanController.blnMonstrarEliminar}" 
								onclick="if (!confirm('Está Ud. Seguro de Eliminar esta Solicitud?')) return false;" styleClass="btnEstilos"
								reRender="pgSolicRefinan, frmSolicitudRefinanModalPanel, panelUpdateDeleteSolicitudRefinan"/>
						</h:panelGrid>
					</rich:panel>
					              <!-- Botones de las impreciones -->
              <rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 370px;">    
                  
                 <h:panelGrid columns="1"  border="0" cellspacing="4" >
                   		<h:outputText value="Imprimir formatos de Crédito" style="padding-right: 2px;"/>
                 
                 <rich:spacer height="16px"/>
                 <h:panelGrid columns="3" border="0"  style="width: 400px;">
                 <h:commandButton id="btnImprimir1" value="Solicitud de Préstamo" styleClass="btnEstilos1" action="#{solicitudRefinanController.imprimirSolicitudPrestamo}"/>
                 <h:commandButton id="btnImprimir3" value="Autorización Descuento" styleClass="btnEstilos1" action="#{solicitudRefinanController.imprimirAutorizacionDescuento}"/>
                 <h:commandButton id="btnImprimir4" value="Autorización de Dscto al 100%" styleClass="btnEstilos1" action="#{solicitudRefinanController.imprimirautorizacionDscto100}"/>
                 </h:panelGrid>
                 <h:panelGrid columns="3" border="0"  style="width: 400px;">
             	 <rich:column visible="#{solicitudPrestamoController.mostrarBoton}"><h:commandButton id="btnImprimir5" value="Autorización Descuento Cesantes" styleClass="btnEstilos1" action="#{solicitudRefinanController.imprimirAutorizacionDescuentoCesantes}"/></rich:column>
                 <rich:column ><h:commandButton id="btnImprimir6" value="Pagaré" styleClass="btnEstilos1" action="#{solicitudRefinanController.imprimirPagare}"/></rich:column>
                 <rich:column ><h:commandButton id="btnImprimir7" value="Declaración Jurada" styleClass="btnEstilos1" action="#{solicitudRefinanController.imprimirDeclaraciónJurada}"/></rich:column>
                 </h:panelGrid>																										 	
                 </h:panelGrid>
             </rich:panel>
				</h:form>
			</rich:modalPanel> 
    
    
    <!-- 9. mpCronogramaRefinanciamiento => PopUp que muestra el cronograma redireccionado. cronogramaRefinan.jsp -->
			<rich:modalPanel id="mpCronogramaRefinanciamiento" width="950" height="350" resizeable="false" style="background-color:#DEEBF5">
				<f:facet name="header">
					<h:panelGrid>
						<rich:column style="border: none;">
							<h:outputText value="Cronograma Refinanciamiento"/>
						</rich:column>
					</h:panelGrid>
				</f:facet>
				<f:facet name="controls">
					<h:panelGroup>
						<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
							<rich:componentControl for="mpCronogramaRefinanciamiento" operation="hide" event="onclick"/>
						</h:graphicImage>
					</h:panelGroup>
				</f:facet>
				<h:form id="frmCronogramaRefinan">
					<a4j:include viewId="/pages/reprogRefinan/popUp/cronogramaRefinan.jsp"/>
				</h:form>
			</rich:modalPanel>
	
	
	<!-- 10. mpGarantesSolidariosRef => PopUp para agregar Garantes Solidarios. garanteSolidarioBody.jsp -->
			<rich:modalPanel id="mpGarantesSolidariosRef" width="950" height="350" resizeable="false" style="background-color:#DEEBF5">
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
							<rich:componentControl for="mpGarantesSolidariosRef" operation="hide" event="onclick"/>
						</h:graphicImage>
					</h:panelGroup>
				</f:facet>
				<h:form id="frmGaranteSolidario">
					<a4j:include viewId="/pages/reprogRefinan/popUp/garanteSolidarioBody.jsp"/>
				</h:form>
			</rich:modalPanel>
	
	
	<!-- 10. panelUpdateDeleteAutorizacionRefinan => PopUp que muestra: Autorizar y Ver -->	
			<rich:modalPanel id="panelUpdateDeleteAutorizacionRefinan" width="400" height="155" resizeable="false" style="background-color:#DEEBF5;">
				<f:facet name="header">
					<h:panelGrid>
						<rich:column style="border: none;">
							<h:outputText value="Autorizar/Ver Solicitud Refinanciamiento"/>
						</rich:column>
					</h:panelGrid>
				</f:facet>
				<f:facet name="controls">
					<h:panelGroup>
						<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkAutorizacionRefinan"/>
						<rich:componentControl for="panelUpdateDeleteAutorizacionRefinan" attachTo="hidelinkAutorizacionRefinan" operation="hide" event="onclick"/>
					</h:panelGroup>
				</f:facet>
				<h:form id="frmAutorizacionRefinanModalPanel">
					<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 370px;">                 
						<h:panelGrid columns="2"  border="0" cellspacing="4" >
							<h:outputText value="¿Desea Ud. Autorizar o Ver el Refinanciamiento?" style="padding-right: 35px;"/>
						</h:panelGrid>
						<rich:spacer height="16px"/>
						<h:panelGrid columns="3" border="0"  style="width: 300px;">
							<a4j:commandButton value="Autorizar" actionListener="#{autorizacionRefinanController.showAutorizacionRefinanciamiento}" 
									styleClass="btnEstilos" reRender="pgAutoricRefinan"
									oncomplete="Richfaces.hideModalPanel('panelUpdateDeleteAutorizacionRefinan')"/>
							<h:commandButton value="Ver"  styleClass="btnEstilos"/>
						</h:panelGrid>
					</rich:panel>
				</h:form>
			</rich:modalPanel>
    
    
   	<!-- 11. pAdjuntarInfoCorpRef => PopUp para cargar documento infocorp. adjuntarInfoCorp.jsp  -->		
			<rich:modalPanel id="pAdjuntarInfoCorpRef" width="530" height="200" resizeable="false" style="background-color:#DEEBF5;">
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
							<rich:componentControl for="pAdjuntarInfoCorpRef" operation="hide" event="onclick"/>
						</h:graphicImage>
					</h:panelGroup>
				</f:facet>
				<a4j:include viewId="/pages/reprogRefinan/Autorizacion/adjuntarInfoCorp.jsp"/>
			</rich:modalPanel>
	
	
	<!-- 12. pAdjuntarReniecRef => PopUp para cargar documento reniec. adjuntarReniec.jsp -->	
			<rich:modalPanel id="pAdjuntarReniecRef" width="530" height="200" resizeable="false" style="background-color:#DEEBF5;">
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
							<rich:componentControl for="pAdjuntarReniecRef" operation="hide" event="onclick"/>
						</h:graphicImage>
					</h:panelGroup>
				</f:facet>
				<a4j:include viewId="/pages/reprogRefinan/Autorizacion/adjuntarReniec.jsp"/>
			</rich:modalPanel>	
			
			
			
    <!-- 13. mpSolicitudRefinanciamientoView => PopUp donde se visualiza la solicitus en modo solo lectura. SolicitudRefinanView.jsp -->	
			<rich:modalPanel id="mpSolicitudRefinanciamientoLink" width="1000" height="460"  style="background-color:#DEEBF5;">
				<f:facet name="header">
					<h:panelGrid>
						<rich:column style="border: none;">
							<h:outputText value="Solicitud de Refinanciamiento "/>
						</rich:column>
					</h:panelGrid>
				</f:facet>
				<f:facet name="controls">
					<h:panelGroup>
						<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
							<rich:componentControl for="mpSolicitudRefinanciamientoLink" operation="hide" event="onclick"/>
						</h:graphicImage>
					</h:panelGroup>
				</f:facet>
				<h:form>
					<a4j:include viewId="/pages/reprogRefinan/Solicitud/SolicitudRefinanLink.jsp"/>
				</h:form>
			</rich:modalPanel>
			
	
	
	<rich:modalPanel id="mpDetalleRefinanciamiento" width="1100" height="300" style="background-color:#DEEBF5;">
		<f:facet name="header">
			<h:panelGrid>
				<rich:column style="border: none;">
					<h:outputText value="Detalle"/>
				</rich:column>
			</h:panelGrid>
		</f:facet>
		<f:facet name="controls">
	        <h:panelGroup>
		           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
		           		<rich:componentControl for="mpDetalleRefinanciamiento" operation="hide" event="onclick"/>
		           </h:graphicImage>
       			</h:panelGroup>
	    </f:facet>
			<a4j:include viewId="/pages/reprogRefinan/popUp/detalleRefinanciamiento.jsp"/>
	</rich:modalPanel>
	