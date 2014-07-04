<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- Empresa   : Cooperativa Tumi         	-->
<!-- Modulo    : Autorización Crédito Edit  -->
<!-- Prototipo : Autorización de Crédito    -->
<!-- Fecha     : 10/09/2012               	-->

<%-- <rich:panel rendered="#{autorizacionRefinanController.formAutorizacionRefinanRendered} --%>
<h:panelGrid id="panelMensaje">
	<h:outputText value="#{autorizacionRefinanController.strTxtMsgPerfil}" styleClass="msgError" />
	<h:outputText value="#{autorizacionRefinanController.strTxtMsgUsuario}" styleClass="msgError" />
	<h:outputText value="#{autorizacionRefinanController.strTxtMsgValidacion}" styleClass="msgError" />
	<h:outputText value="#{autorizacionRefinanController.strTxtMsgError}" styleClass="msgError" />			
</h:panelGrid>

<rich:panel
	rendered="#{autorizacionRefinanController.formAutorizacionRefinanRendered}"
	style="width:960px;border:1px solid #17356f;background-color:#DEEBF5;">
	<a4j:commandLink value="Solicitud de Refinanciamiento" reRender="pgSolicitudRefinanLink"
					actionListener="#{autorizacionRefinanController.irModificarSolicitudRefinanAutoriza}"
					oncomplete="Richfaces.showModalPanel('mpSolicitudRefinanciamientoLink')">
		<f:param name="intPersEmpresaPk" value="#{autorizacionRefinanController.expedienteCreditoCompSelected.expedienteCredito.id.intPersEmpresaPk}" />
		<f:param name="intCuentaPk" value="#{autorizacionRefinanController.expedienteCreditoCompSelected.expedienteCredito.id.intCuentaPk}" />
		<f:param name="intItemExpediente" value="#{autorizacionRefinanController.expedienteCreditoCompSelected.expedienteCredito.id.intItemExpediente}" />
		<f:param name="intItemDetExpediente" value="#{autorizacionRefinanController.expedienteCreditoCompSelected.expedienteCredito.id.intItemDetExpediente}" />
	</a4j:commandLink>

	<h:panelGrid columns="8">
		<rich:column width="120px">
			<h:outputText value="Autorización"
				style="font-weight:bold;text-decoration:underline;" />
		</rich:column>
		<rich:column>
			<h:outputText value=":" />
		</rich:column>
		<rich:column>
			<a4j:commandButton value="Historial descuentos"
				styleClass="btnEstilos1" disabled="true" />
		</rich:column>
		<rich:column>
			<a4j:commandButton value="Historial de crédito"
				styleClass="btnEstilos1" disabled="true" />
		</rich:column>
		<rich:column>
			<a4j:commandButton value="Cartera de crédito"
				styleClass="btnEstilos1" disabled="true" />
		</rich:column>
		<rich:column>
			<a4j:commandButton value="Historial Movimientos"
				styleClass="btnEstilos1" disabled="true" />
		</rich:column>
		<rich:column>
			<a4j:commandButton value="Terceros" styleClass="btnEstilos1" disabled="false" 
								actionListener="#{autorizacionRefinanController.recuperarDatosCartera}" />
		</rich:column>
   
		<rich:column>
			<a4j:commandButton value="Cronograma"
				oncomplete="#{rich:component('mpCronogramaCredito')}.show()"
				disabled="true" styleClass="btnEstilos"
				reRender="frmCronogramaCredito">
			</a4j:commandButton>
		</rich:column>
	</h:panelGrid>

	<h:panelGrid border="0" columns="4">
	<rich:column width="120px">
			<h:outputText value="" />
		</rich:column>
		<rich:column>
			<h:outputText value="" />
		</rich:column>
		<rich:column>
		
		<rich:dataGrid
			value="#{autorizacionRefinanController.listaAutorizaCreditoComp}"
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
						value="#{item.autorizaCredito.intPersUsuarioAutoriza} - #{item.persona.natural.strNombres} #{item.persona.natural.strApellidoPaterno} - DNI: #{item.persona.documento.strNumeroIdentidad} - #{item.strPerfil}" />
					<h:outputText value="Resultado : " style="font-weight:bold"
						styleClass="label" />
					<h:panelGroup>
						<tumih:outputText
							cache="#{applicationScope.Constante.PARAM_T_ESTADOAUTORIZACIONPRESTAMO}"
							itemValue="intIdDetalle" itemLabel="strDescripcion"
							property="#{item.autorizaCredito.intParaEstadoAutorizar}" />-
			            <tumih:outputText
							cache="#{applicationScope.Constante.PARAM_T_TIPOMOTIVOESTADOAUTPTMO}"
							itemValue="intIdDetalle" itemLabel="strDescripcion"
							property="#{item.autorizaCredito.intParaTipoAureobCod}" />
					</h:panelGroup>
					
					<h:outputText value="Observación : "
						style="font-weight:bold" styleClass="label" />
					<h:outputText
						value="#{item.autorizaCredito.strObservacion}" />
				
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
			<h:selectOneMenu
				value="#{autorizacionRefinanController.beanAutorizaCredito.intParaEstadoAutorizar}"
				valueChangeListener="#{autorizacionRefinanController.reloadCboTipoMotivoEstado}">
				<f:selectItem itemLabel="Seleccionar.." itemValue="0" />
				<tumih:selectItems var="sel"
					cache="#{applicationScope.Constante.PARAM_T_ESTADOAUTORIZACIONPRESTAMO}"
					itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
					propertySort="intOrden" />
				<a4j:support event="onchange" reRender="cboTipoMotivoEstado"
					ajaxSingle="true" />
			</h:selectOneMenu>
		</rich:column>
	</h:panelGrid>

	<h:panelGrid columns="3">
		<rich:column width="120px">
			<h:outputText value="Tipo de Operación" />
		</rich:column>
		<rich:column>
			<h:outputText value=":" />
		</rich:column>
		<rich:column>
			<h:selectOneMenu id="cboTipoMotivoEstado"
				value="#{autorizacionRefinanController.beanAutorizaCredito.intParaTipoAureobCod}">
				<f:selectItem itemLabel="Seleccione.." itemValue="0" />
				<tumih:selectItems var="sel"
					value="#{autorizacionRefinanController.listaTipoOperacion}"
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
			<h:inputTextarea
				value="#{autorizacionRefinanController.beanAutorizaCredito.strObservacion}"
				cols="150" rows="3" />
		</rich:column>
	</h:panelGrid>

	<!-- docs adjuntos -->
		<h:panelGrid id="pgAdjuntos" columns="3" >
				<rich:column width="120px">
					<h:outputText value="Documentos Adjuntos:"/>
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
								<h:panelGrid columns="8" id="panelInfoCorpRef">
										<rich:column >Infocorp
										</rich:column>
										<rich:column >
											<h:inputText  
												size="40"
												readonly="true" 
												style="background-color: #BFBFBF;"/>
											<h:inputText rendered="#{not empty autorizacionRefinanController.archivoInfoCorp}"
												value="#{autorizacionRefinanController.archivoInfoCorp.strNombrearchivo}"
												size="40"
												readonly="true" 
												style="background-color: #BFBFBF;"/>
										</rich:column>
										<rich:column >
											<a4j:commandButton
												rendered="#{empty autorizacionRefinanController.archivoInfoCorp}"
						                		styleClass="btnEstilos"
						                		value="Adjuntar"
						                		reRender="frmAdjuntarInfoCorpRef, pAdjuntarInfoCorpRef"
						                		oncomplete="Richfaces.showModalPanel('pAdjuntarInfoCorpRef')"
						                		style="width:80px"/>
						                	<a4j:commandButton
												rendered="#{not empty autorizacionRefinanController.archivoInfoCorp}"
						                		styleClass="btnEstilos"
						                		value="Quitar"
						                		reRender="panelInfoCorpRef, popupInfoCorpRef"
						                		action="#{autorizacionRefinanController.quitarInfoCorp}"
						                		style="width:80px"/>	
	
										</rich:column>
										<rich:column 
											rendered="#{(not empty autorizacionRefinanController.archivoInfoCorp)}">
											<h:commandLink  value=" Descargar"	target="_blank"	
												actionListener="#{fileUploadController.descargarArchivo}">
												<f:attribute name="archivo" value="#{autorizacionRefinanController.archivoInfoCorp}"/>		
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
								<h:panelGrid columns="8" id="panelReniecRef">
										<rich:column >Reniec
										</rich:column>
										<rich:column >
											<h:inputText  
												size="40"
												readonly="true" 
												style="background-color: #BFBFBF;"/>
											<h:inputText rendered="#{not empty autorizacionRefinanController.archivoReniec}"
												value="#{autorizacionRefinanController.archivoReniec.strNombrearchivo}"
												size="40"
												readonly="true" 
												style="background-color: #BFBFBF;"/>
										</rich:column>
										<rich:column >
											<a4j:commandButton
												rendered="#{empty autorizacionRefinanController.archivoReniec}"
						                		styleClass="btnEstilos"
						                		value="Adjuntar"
						                		reRender="frmAdjuntarReniecRef, pAdjuntarReniecRef"
						                		oncomplete="Richfaces.showModalPanel('pAdjuntarReniecRef')"
						                		style="width:80px"/>
						                	<a4j:commandButton
												rendered="#{not empty autorizacionRefinanController.archivoReniec}"
						                		styleClass="btnEstilos"
						                		value="Quitar"
						                		reRender="panelReniecRef, popupReniecRef"
						                		action="#{autorizacionRefinanController.quitarReniec}"
						                		style="width:80px"/>	
	
										</rich:column>
										<rich:column 
											rendered="#{not empty autorizacionRefinanController.archivoReniec}">
											<h:commandLink  value=" Descargar"	target="_blank"	
												actionListener="#{fileUploadController.descargarArchivo}">
												<f:attribute name="archivo" value="#{autorizacionRefinanController.archivoReniec}"/>		
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