<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- 	Empresa   : TUMI							-->
<!-- 	Modulo    : REFINANCIAMIENTO				-->
<!-- 	Prototipo : SOLICITUD DE ACTIVIDAD			-->
<!-- 	Fecha     : 08/04/2013						-->

<!-- Contiene:
		1. Los tabs de la ventana de Actividad.
		2. selecTipoPersonaRefinan
		3. seleccionarTipoOperacion
		4. putFileDocAdjunto
		5. fileupload.jsp
		6. mpSocioEstructuraRef. Redirecciona a: socioDependenciaBody.jsp 
		7. mpCapacidadCreditoRef. Redirecciona a: capacidadPagoHaberes.jsp - capacidadPagoIncentivo.jsp - capacidadPagoCAS.jsp
		8. panelUpdateDeleteAutorizacionActividad.(A/V/E)
		9. mpCronogramaRefinanciamiento. Redirecciona a: cronogramaRefinan.jsp
	   10. mpGarantesSolidariosRef. Redirecciona a: garanteSolidarioBody.jsp
	   11. pAdjuntarInfoCorpRef. Redirecciona a: adjuntarInfoCorp.jsp 
	   12. pAdjuntarReniecRef. Redirecciona a: adjuntarReniec.jsp
	   13. mpSolicitudRefinanciamientoView. Redirecciona a: SolicitudRefinanView.jsp
-->

<h:outputText value="#{solicitudActividadController.limpiarSolicitud}"/>

	<!-- 1. Tabs de refinanciamiento				-->
		<h:form id="frmPrincipalActividad">
			<h:outputText value="#{solicitudActividadController.limpiarSolicitud}"/>
			<h:outputText value="#{autorizacionActividadController.limpiarAutorizacion}"/>
    				
				<rich:tabPanel activeTabClass="activo" inactiveTabClass="inactivo" disabledTabClass="disabled">
					<rich:tab label="Solicitud" disabled="#{solicitudActividadController.blnSolicitud}">
						<a4j:include viewId="/pages/actividades/solicitudActividadMain.jsp"/>
					</rich:tab>
					<rich:tab label="Autorización" disabled="#{solicitudActividadController.blnAutorizacion}">
						<a4j:include viewId="/pages/actividades/autorizacionActividadMain.jsp"/>
					</rich:tab>
				</rich:tabPanel>
			</h:form>


<a4j:region>
	<!-- 2. selecTipoPersonaRefinan ==> Carga el combo de Tipo de Documento, dependiendo del Tipo de persona seleccionado.  -->
		<a4j:jsFunction name="cargarComboSubActividad" reRender="cboSubActividad"
						actionListener="#{solicitudActividadController.cargarComboSubActividad}">
			<f:param name="pIntTipoActividad"></f:param>
	</a4j:jsFunction>
	
	<!-- 3. selecTipoPersonaRefinan ==> Carga el combo de Tipo de Documento, dependiendo del Tipo de persona seleccionado.  -->
		<a4j:jsFunction name="cargarComboSubActividadBusqueda" reRender="cboSubActividadBusq"
						actionListener="#{solicitudActividadController.cargarComboSubActividadBusqueda}">
			<f:param name="pIntBusqTipoActividad"></f:param>
		</a4j:jsFunction>
		<a4j:jsFunction name="cargarComboSubActividadBusquedaAut" reRender="cboSubActividadBusqAut"
						actionListener="#{autorizacionActividadController.cargarComboSubActividadBusqueda}">
			<f:param name="pIntBusqTipoActividad"></f:param>
		</a4j:jsFunction>
	
	<!-- 3. seleccionarTipoOperacion ==> Carga el combo de Tipo de Operacion en la validacion de socio.  -->
	<a4j:jsFunction name="definirTipoDeSolicitud" actionListener="#{solicitudActividadController.definirTipoDeSolicitud}" reRender="pgMontoACancelar">
			<f:param name="pIntValidarDatosTipoSolicitud"></f:param>
	</a4j:jsFunction>
	
</a4j:region>
	
	
		<a4j:jsFunction name="putFileDocAdjunto" reRender="colImgDocAdjunto"
		actionListener="#{solicitudActividadController.putFile}">
	</a4j:jsFunction>
	
	
	<!-- 5. fileupload.jsp -->
			<a4j:include viewId="/pages/fileupload/fileupload.jsp"/>
	
	
	<!-- 6. mpSocioEstructuraRef => PopUp para agregar Capacidad de Credito. Redirecciona a socioDependenciaBody.jsp -->	

	
	
	<!-- 7. mpCapacidadCreditoRef => PopUp para agregar Capacidad de Credito segun tipo de socio-->		
			
			
	<!-- 8. panelUpdateDeleteSolicitudActividad => PopUp que muestra: Actualizar, Ver y Eliminar - OK -->
			<rich:modalPanel id="panelUpdateDeleteSolicitudActividad" width="400" height="155" resizeable="false" style="background-color:#DEEBF5;">
				<f:facet name="header">
					<h:panelGrid>
						<rich:column style="border: none;">
							<h:outputText value="Modificar/Ver Solicitud Actividad"/>
						</rich:column>
					</h:panelGrid>
				</f:facet>
				<f:facet name="controls">
					<h:panelGroup>
						<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkSolicitudActividad"/>
						<rich:componentControl for="panelUpdateDeleteSolicitudActividad" attachTo="hidelinkSolicitudActividad" operation="hide" event="onclick"/>
					</h:panelGroup>
				</f:facet>
				<h:form id="frmSolicitudActividadModalPanel">
					<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 370px;">                 
						<h:panelGrid columns="2"  border="0" cellspacing="4" >
							<h:outputText value="¿Desea Ud. Actualizar o Eliminar una Solicitud de Actividad?" style="padding-right: 35px;"/>
						</h:panelGrid>
						<rich:spacer height="16px"/>
						<h:panelGrid columns="3" border="0"  style="width: 300px;">
							<a4j:commandButton value="Actualizar" actionListener="#{solicitudActividadController.irModificarSolicitudActividad}" 
								styleClass="btnEstilos" rendered="#{solicitudActividadController.blnMostrarModificar}" 
								reRender="pgSolicActividad,frmSolicitudActividadModalPanel,panelUpdateDeleteSolicitudActividad"/>
							<a4j:commandButton value="Ver" actionListener="#{solicitudActividadController.irVerSolicitudActividad}" 
								styleClass="btnEstilos" reRender="pgSolicActividad,frmSolicitudActividadModalPanel,panelUpdateDeleteSolicitudActividad"/>
							<a4j:commandButton value="Eliminar" actionListener="#{solicitudActividadController.irEliminarSolicitudActividad}" 
								onclick="if (!confirm('Está Ud. Seguro de Eliminar esta Solicitud?')) return false;" styleClass="btnEstilos"
								rendered="#{solicitudActividadController.blnMostrarEliminar}"/>
						</h:panelGrid>
					</rich:panel>
				</h:form>
			</rich:modalPanel> 
    
    
    <!-- 9. mpCronogramaRefinanciamiento => PopUp que muestra el cronograma redireccionado. cronogramaRefinan.jsp -->
			<rich:modalPanel id="mpCronogramaActividad" width="610" height="470" resizeable="false" style="background-color:#DEEBF5">
				<f:facet name="header">
					<h:panelGrid>
						<rich:column style="border: none;">
							<h:outputText value="Cronograma Actividad"/>
						</rich:column>
					</h:panelGrid>
				</f:facet>
				<f:facet name="controls">
					<h:panelGroup>
						<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
							<rich:componentControl for="mpCronogramaActividad" operation="hide" event="onclick"/>
						</h:graphicImage>
					</h:panelGroup>
				</f:facet>
				<h:form id="frmCronogramaActividad">
					<a4j:include viewId="/pages/actividades/popUp/cronogramaActividad.jsp"/>
				</h:form>
			</rich:modalPanel>
	

	<!-- 10. panelUpdateDeleteAutorizacionActividad => PopUp que muestra: Autorizar y Ver -->	
					
			<rich:modalPanel id="panelUpdateDeleteAutorizacionActividad" width="400" height="155" resizeable="false" style="background-color:#DEEBF5;">
				<f:facet name="header">
					<h:panelGrid>
						<rich:column style="border: none;">
							<h:outputText value="Autorizar/Ver Solicitud Actividad"/>
						</rich:column>
					</h:panelGrid>
				</f:facet>
				<f:facet name="controls">
					<h:panelGroup>
						<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkAutorizacionActividad"/>
						<rich:componentControl for="panelUpdateDeleteAutorizacionActividad" attachTo="hidelinkAutorizacionActividad" operation="hide" event="onclick"/>
					</h:panelGroup>
				</f:facet>
				<h:form id="frmAutorizacionActividadModalPanel">
					<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 370px;">                 
						<h:panelGrid columns="2"  border="0" cellspacing="4" >
							<h:outputText value="¿Desea Ud. Autorizar o Ver la Actividad?" style="padding-right: 35px;"/>
						</h:panelGrid>
						<rich:spacer height="16px"/>
						<h:panelGrid columns="3" border="0"  style="width: 300px;">
							<a4j:commandButton value="Autorizar" rendered="#{autorizacionActividadController.blnMostrarAutorizar}"
											actionListener="#{autorizacionActividadController.showAutorizacionActividad}" styleClass="btnEstilos" 
											reRender="pgAutoricActividad,frmAutorizacionActividadModalPanel,panelUpdateDeleteAutorizacionActividad"
											oncomplete="Richfaces.hideModalPanel('panelUpdateDeleteAutorizacionActividad')"/>
							<a4j:commandButton value="Ver" rendered="#{autorizacionActividadController.blnMostrarVer}"
											actionListener="#{autorizacionActividadController.showViewAutorizacionActividad}" styleClass="btnEstilos" 
											reRender="pgAutoricActividad,frmAutorizacionActividadModalPanel,panelUpdateDeleteAutorizacionActividad"
											oncomplete="Richfaces.hideModalPanel('panelUpdateDeleteAutorizacionActividad')"/>
						</h:panelGrid>
					</rich:panel>
				</h:form>
			</rich:modalPanel>

   	<!-- 11. pAdjuntarInfoCorpRef => PopUp para cargar documento infocorp. adjuntarInfoCorp.jsp  -->		
			
	
	
	<!-- 12. pAdjuntarReniecRef => PopUp para cargar documento reniec. adjuntarReniec.jsp -->	
			
			
			

<a4j:region>
	<a4j:jsFunction name="cargarComboTipoOperacionAut" actionListener="#{autorizacionActividadController.cargarComboTipoOperacionAut}" 
					reRender="cboTipoOperacionAut, pgTipoOperacion">
			<f:param name="pIntParaEstadoAutorizar"></f:param>
	</a4j:jsFunction>

	</a4j:region>
	
	  <rich:modalPanel id="mpSolicitudActividadView" width="900" height="500" 
		style="background-color:#DEEBF5;">
	    <f:facet name="header">
	    	<h:panelGrid>
	    		<rich:column style="border: none;">
	            	<h:outputText value="Solicitud de Actividad "/>
	            </rich:column>
	        </h:panelGrid>
	    </f:facet>
	    <f:facet name="controls">
	    	<h:panelGroup>
	    		<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
	            	<rich:componentControl for="mpSolicitudActividadView" operation="hide" event="onclick"/>
	            </h:graphicImage>
	        </h:panelGroup>
	    </f:facet>
	    
	    <h:form id="frmSolicitudActividadView">
		    <a4j:include viewId="/pages/actividades/solicitudActividadView.jsp"/>
	    </h:form>
	</rich:modalPanel>
	