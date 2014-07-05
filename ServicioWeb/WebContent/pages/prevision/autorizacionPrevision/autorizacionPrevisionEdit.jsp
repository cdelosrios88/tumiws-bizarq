<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- Empresa   : Cooperativa Tumi         	-->
<!-- Modulo    : Autorización Crédito Edit  -->
<!-- Prototipo : Autorización de Crédito    -->
<!-- Fecha     : 10/09/2012               	-->

<%-- <rich:panel rendered="#{autorizacionPrevisionController.formAutorizacionPrestamoRendered} --%>
<h:panelGrid id="panelMensajeAutPrev">
	<h:outputText value="#{autorizacionPrevisionController.strTxtMsgPerfil}" styleClass="msgError" />
	<h:outputText value="#{autorizacionPrevisionController.strTxtMsgUsuario}" styleClass="msgError" />
	<h:outputText value="#{autorizacionPrevisionController.strTxtMsgValidacion}" styleClass="msgError" />
	<h:outputText value="#{autorizacionPrevisionController.strTxtMsgError}" styleClass="msgError" />
	<h:outputText value="#{autorizacionPrevisionController.strTxtMsgConfiguracion}" styleClass="msgError" />		
		
</h:panelGrid>

<rich:panel	rendered="#{autorizacionPrevisionController.formAutorizacionPrevisionRendered}"
			style="width:960px;border:1px solid #17356f;background-color:#DEEBF5;">
	<a4j:commandLink value="Solicitud de Previsión" target="_blank"
		reRender="mpSolicitudPrevisionView, frmSolicitudPrevisionView"
		actionListener="#{solicitudPrevisionController.irModificarSolicitudPrevisionAutoriza}"
		oncomplete="Richfaces.showModalPanel('mpSolicitudPrevisionView')">
	</a4j:commandLink>
	
	<rich:spacer height="10"></rich:spacer>
	
	<rich:panel id="pgMsgErrorCuenta" style="border: 0px solid #17356f;background-color:#DEEBF5;" 
		rendered="#{autorizacionPrevisionController.blnBloquearXCuenta}" style="text-align: center">
		<h:outputText value="#{autorizacionPrevisionController.strMensajeValidacionCuenta}" styleClass="msgError4" style="text-align: center"/>
	</rich:panel>
	
	
	<h:panelGrid columns="4">
		<rich:column width="120px">
			<h:outputText value="" />
		</rich:column>
		<rich:column>
			<h:outputText value="" />
		</rich:column>
		<rich:column>
			<rich:dataGrid
			value="#{autorizacionPrevisionController.listaAutorizaPrevisionComp}"
			var="item" columns="1" width="700px" border="1"
			style="background-color:#DEEBF5">
			<rich:panel style="border:0px">
				<f:facet name="header">
					<h:outputText value="Personas encargadas de Autorización" />
				</f:facet>
				<h:panelGrid columns="2">
					<h:outputText value="Encargado de Autorización : "
						style="font-weight:bold" styleClass="label" />
					<h:outputText
						value="#{item.autorizaPrevision.intPersUsuarioAutoriza} - #{item.persona.natural.strNombres} #{item.persona.natural.strApellidoPaterno} - DNI: #{item.persona.documento.strNumeroIdentidad} - #{item.strPerfil}" />
					<h:outputText value="Resultado : " style="font-weight:bold"
						styleClass="label" />
					<h:panelGroup>
						<tumih:outputText
							cache="#{applicationScope.Constante.PARAM_T_ESTADOAUTORIZACIONPRESTAMO}"
							itemValue="intIdDetalle" itemLabel="strDescripcion"
							property="#{item.autorizaPrevision.intParaEstadoAutorizar}" />.&nbsp;	   				
			            <tumih:outputText
							cache="#{applicationScope.Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_RETIRO}"
							itemValue="intIdDetalle" itemLabel="strDescripcion"
							property="#{item.autorizaPrevision.intParaTipoAureobCod}" />
					</h:panelGroup>
					
					<h:outputText value="Observación : "
						style="font-weight:bold" styleClass="label" />
					<h:outputText
						value="#{item.autorizaPrevision.strObservacion}" />
				
				</h:panelGrid>
			</rich:panel>
		</rich:dataGrid>
		</rich:column>
	</h:panelGrid>


	<h:panelGrid columns="4">
		<rich:column width="120px">
			<h:outputText value="Operación" />
		</rich:column>
		<rich:column>
			<h:outputText value=":" />
		</rich:column>
		<rich:column>
			<h:selectOneMenu disabled="#{autorizacionPrevisionController.blnBloquearXCuenta}"
				value="#{autorizacionPrevisionController.beanAutorizaPrevision.intParaEstadoAutorizar}">
				<f:selectItem itemLabel="Seleccionar.." itemValue="0" />
				<tumih:selectItems var="sel"
					cache="#{applicationScope.Constante.PARAM_T_ESTADOAUTORIZACIONPRESTAMO}"
					itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
					propertySort="intOrden" />
			</h:selectOneMenu>
		</rich:column>
	</h:panelGrid>
 
	<h:panelGrid id="pgTipoOperacion" columns="3" rendered="#{autorizacionPrevisionController.blnIsRetiro}" >
		<rich:column width="120px">
			<h:outputText value="Tipo de Operación" />
		</rich:column>
		<rich:column>
			<h:outputText value=":" />
		</rich:column>
		<rich:column>
			<h:selectOneMenu id="cboTipoMotivoEstado" disabled="#{autorizacionPrevisionController.blnBloquearXCuenta}"
				value="#{autorizacionPrevisionController.beanAutorizaPrevision.intParaTipoAureobCod}">
				<f:selectItem itemLabel="Seleccione.." itemValue="0" />
				<tumih:selectItems var="sel"
					value="#{autorizacionPrevisionController.listaTipoOperacion}"
					itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
					propertySort="intOrden" />
			</h:selectOneMenu>
		</rich:column>
	</h:panelGrid>

	<h:panelGrid columns="3">
		<rich:column width="120px">
			<h:outputText value="Observación" />
		</rich:column>
		<rich:column>
			<h:outputText value=":" />
		</rich:column>
		<rich:column>
			<h:inputTextarea disabled="#{autorizacionPrevisionController.blnBloquearXCuenta}"
				value="#{autorizacionPrevisionController.beanAutorizaPrevision.strObservacion}"
				cols="150" rows="3" />
		</rich:column>
	</h:panelGrid>


	<!-- docs adjuntos -->
		<h:panelGrid id="pgAdjuntos" columns="3" >
				<rich:column width="120px">
					<h:outputText value="Archivos Adjuntos :"/>
				</rich:column>
				<rich:column>
					<h:outputText value=":"/>
				</rich:column>
				<rich:column >

				</rich:column>

		</h:panelGrid>
	

	<h:panelGrid id="pgListDocAdjuntosVerificacion" columns="2" >

				<rich:column style="border:1px solid #17356f; background-color:#DEEBF5" width="60">
					<h:panelGrid>
			                <rich:panel>
								<h:panelGrid columns="8" id="panelDeJu">
										<rich:column >Declaración Jurada
										</rich:column>
										<rich:column >
											<h:inputText size="40" readonly="true" style="background-color: #BFBFBF;"/>
											<h:inputText rendered="#{not empty autorizacionPrevisionController.archivoDeJu}"
														value="#{autorizacionPrevisionController.archivoDeJu.strNombrearchivo}"
														size="40"
														readonly="true" 
														style="background-color: #BFBFBF;"/>
										</rich:column>
										<rich:column >
											<a4j:commandButton disabled="#{autorizacionPrevisionController.blnBloquearXCuenta}"
												rendered="#{empty autorizacionPrevisionController.archivoDeJu}"
						                		styleClass="btnEstilos"
						                		value="Adjuntar"
						                		reRender="pAdjuntarDeJu"
						                		oncomplete="Richfaces.showModalPanel('pAdjuntarDeJu')"
						                		style="width:80px"/>
						                	<a4j:commandButton
												rendered="#{not empty autorizacionPrevisionController.archivoDeJu}"
						                		styleClass="btnEstilos"
						                		value="Quitar"
						                		reRender="panelDeJu, popupDeJu"
						                		action="#{autorizacionPrevisionController.quitarDeJu}"
						                		style="width:80px"/>	
	
										</rich:column>
										<rich:column 
											rendered="#{(not empty autorizacionPrevisionController.archivoDeJu)}">
											<h:commandLink  value=" Descargar"	target="_blank"		
												actionListener="#{fileUploadController.descargarArchivo}">
												<f:attribute name="archivo" value="#{autorizacionPrevisionController.archivoDeJu}"/>		
											</h:commandLink>
										</rich:column>
									</h:panelGrid>
			                </rich:panel>
					</h:panelGrid>
				</rich:column>
				
				<!--  -->
				<rich:column style="border:1px solid #17356f; background-color:#DEEBF5" width="60">
					<h:panelGrid>
			                <rich:panel>
								<h:panelGrid columns="8" id="panelReniecAut">
										<rich:column >Reniec
										</rich:column>
										<rich:column >
											<h:inputText  
												size="40"
												readonly="true" 
												style="background-color: #BFBFBF;"/>
											<h:inputText rendered="#{not empty autorizacionPrevisionController.archivoReniecAut}"
												value="#{autorizacionPrevisionController.archivoReniecAut.strNombrearchivo}"
												size="40"
												readonly="true" 
												style="background-color: #BFBFBF;"/>
										</rich:column>
										<rich:column >
											<a4j:commandButton
												rendered="#{empty autorizacionPrevisionController.archivoReniecAut}"
												disabled="#{autorizacionPrevisionController.blnBloquearXCuenta}"
						                		styleClass="btnEstilos"
						                		value="Adjuntar"
						                		reRender="pAdjuntarReniecAut"
						                		oncomplete="Richfaces.showModalPanel('pAdjuntarReniecAut')"
						                		style="width:80px"/>
						                	<a4j:commandButton
												rendered="#{not empty autorizacionPrevisionController.archivoReniecAut}"
						                		styleClass="btnEstilos"
						                		value="Quitar"
						                		reRender="panelReniec, popupReniec"
						                		action="#{autorizacionPrevisionController.quitarReniecAut}"
						                		style="width:80px"/>	
	
										</rich:column>
										<rich:column 
											rendered="#{not empty autorizacionPrevisionController.archivoReniecAut}">
											<h:commandLink  value=" Descargar"	target="_blank"		
												actionListener="#{fileUploadController.descargarArchivo}">
												<f:attribute name="archivo" value="#{autorizacionPrevisionController.archivoReniecAut}"/>		
											</h:commandLink>
										</rich:column>
									</h:panelGrid>
			                </rich:panel>
					</h:panelGrid>
				</rich:column>

				<!--  -->
				<rich:column >
					<h:outputText />
				</rich:column>
				<rich:column>
					<h:outputText/>
				</rich:column>

		</h:panelGrid>
	
	<!-- docs adjuntos -->
</rich:panel>

