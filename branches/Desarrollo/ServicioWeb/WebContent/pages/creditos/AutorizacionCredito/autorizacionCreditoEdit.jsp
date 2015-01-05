<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- Empresa   : Cooperativa Tumi         	-->
<!-- Modulo    : Autorización Crédito Edit  -->
<!-- Prototipo : Autorización de Crédito    -->
<!-- Fecha     : 10/09/2012               	-->

<%-- <rich:panel rendered="#{autorizacionPrestamoController.formAutorizacionPrestamoRendered} --%>
<h:panelGrid id="panelMensaje">
	<h:outputText value="#{autorizacionPrestamoController.strTxtMsgPerfil}"
		styleClass="msgError" />
	<h:outputText value="#{autorizacionPrestamoController.strTxtMsgUsuario}"
		styleClass="msgError" />
	<h:outputText value="#{autorizacionPrestamoController.strTxtMsgValidacion}"
		styleClass="msgError" />
	<h:outputText value="#{autorizacionPrestamoController.strTxtMsgError}"
		styleClass="msgError" />
		<h:outputText value="#{autorizacionPrestamoController.strMsgErrorValidarMovimientos}"
		styleClass="msgError" />
				
		
</h:panelGrid>

<rich:panel rendered="#{autorizacionPrestamoController.formAutorizacionPrestamoRendered}" style="width:960px;border:1px solid #17356f;background-color:#DEEBF5;">
	<a4j:commandLink value="Solicitud de Crédito" target="_blank"
		actionListener="#{solicitudPrestamoController.irModificarSolicitudPrestamoAutoriza}"
		reRender="frmSolicitudCreditoView,mpSolicitudCreditoView" 
		oncomplete="Richfaces.showModalPanel('mpSolicitudCreditoView')">
		<f:param name="intPersEmpresaPk"
			value="#{autorizacionPrestamoController.expedienteCreditoCompSelected.expedienteCredito.id.intPersEmpresaPk}" />
		<f:param name="intCuentaPk"
			value="#{autorizacionPrestamoController.expedienteCreditoCompSelected.expedienteCredito.id.intCuentaPk}" />
		<f:param name="intItemExpediente"
			value="#{autorizacionPrestamoController.expedienteCreditoCompSelected.expedienteCredito.id.intItemExpediente}" />
		<f:param name="intItemDetExpediente"
			value="#{autorizacionPrestamoController.expedienteCreditoCompSelected.expedienteCredito.id.intItemDetExpediente}" />
	</a4j:commandLink>

	<rich:spacer height="10"></rich:spacer>
	
	<rich:panel id="pgMsgErrorCuenta" style="border: 0px solid #17356f;background-color:#DEEBF5;" 
		rendered="#{autorizacionPrestamoController.blnBloquearXCuenta}" style="text-align: center">
		<h:outputText value="#{autorizacionPrestamoController.strMensajeValidacionCuenta}" styleClass="msgError4" style="text-align: center"/>
		<h:outputText value="#{autorizacionPrestamoController.strMsgErrorValidarMovimientos}" styleClass="msgError4" style="text-align: center"/>
	</rich:panel>
	
	<h:panelGrid columns="1">
		<rich:column width="800">
			<h:outputText value="#{autorizacionPrestamoController.strMensajeMorosidad}" styleClass="msgError4" style="text-align: center"/>
		</rich:column>
	</h:panelGrid>
	<rich:spacer height="10"></rich:spacer>
	
	<h:panelGrid columns="2">
		<rich:column width="120px">
			<h:outputText value="Autorización"
				style="font-weight:bold;text-decoration:underline;" />
		</rich:column>
		<rich:column>
			<rich:dataGrid
			value="#{autorizacionPrestamoController.listaAutorizaCreditoComp}"
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
					<h:outputText value="Fecha : "
						style="font-weight:bold" styleClass="label" />
					<h:outputText
						value="#{item.autorizaCredito.tsFechaAutorizacion}" />
				
				</h:panelGrid>
			</rich:panel>
		</rich:dataGrid>
		</rich:column>
	</h:panelGrid>


	<h:panelGrid columns="4" rendered="#{!autorizacionPrestamoController.blnBloquearXCuenta}">
		<rich:column width="120px">
			<h:outputText value="Operación" />
		</rich:column>
		<rich:column>
			<h:outputText value=":" />
		</rich:column>
		<rich:column>
			<h:selectOneMenu disabled="#{autorizacionPrestamoController.blnBloquearXCuenta}"
				value="#{autorizacionPrestamoController.beanAutorizaCredito.intParaEstadoAutorizar}"
				valueChangeListener="#{autorizacionPrestamoController.reloadCboTipoMotivoEstado}">
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

	<h:panelGrid columns="3" rendered="#{!autorizacionPrestamoController.blnBloquearXCuenta}">
		<rich:column width="120px">
			<h:outputText value="Tipo de Operación" />
		</rich:column>
		<rich:column>
			<h:outputText value=":" />
		</rich:column>
		<rich:column>
			<h:selectOneMenu id="cboTipoMotivoEstado"
				disabled="#{autorizacionPrestamoController.blnBloquearXCuenta}"
				value="#{autorizacionPrestamoController.beanAutorizaCredito.intParaTipoAureobCod}">
				<f:selectItem itemLabel="Seleccione.." itemValue="0" />
				<tumih:selectItems var="sel"
					value="#{autorizacionPrestamoController.listaTipoOperacion}"
					itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
					propertySort="intOrden" />
			</h:selectOneMenu>
		</rich:column>
	</h:panelGrid>

	<h:panelGrid columns="3" rendered="#{!autorizacionPrestamoController.blnBloquearXCuenta}">
		<rich:column width="120px">
			<h:outputText value="Observación" />
		</rich:column>
		<rich:column>
			<h:outputText value=":" />
		</rich:column>
		<rich:column>
			<h:inputTextarea disabled="#{autorizacionPrestamoController.blnBloquearXCuenta}"
				value="#{autorizacionPrestamoController.beanAutorizaCredito.strObservacion}"
				cols="150" rows="3" />
		</rich:column>
	</h:panelGrid>

	<h:panelGrid columns="8">
		<rich:column width="120px">
			<h:outputText value="Verificación Telefónica" />
		</rich:column>
		<rich:column>
			<h:outputText value=":" />
		</rich:column>
		<rich:column>
			<h:outputText value="Número :" />
		</rich:column>
		<rich:column>
			<h:inputText disabled="#{autorizacionPrestamoController.blnBloquearXCuenta}"
				value="#{autorizacionPrestamoController.beanAutorizaVerificacion.strNumero}"
				size="15" />
		</rich:column>
		<rich:column>
			<h:outputText value="Contacto :" />
		</rich:column>
		<rich:column>
			<h:inputText disabled="#{autorizacionPrestamoController.blnBloquearXCuenta}"
				value="#{autorizacionPrestamoController.beanAutorizaVerificacion.strContacto}"
				size="30" />
		</rich:column>
		<rich:column>
			<h:outputText value="Respuesta :" />
		</rich:column>
		<rich:column>
			<h:inputText disabled="#{autorizacionPrestamoController.blnBloquearXCuenta}"
				value="#{autorizacionPrestamoController.beanAutorizaVerificacion.strRespuesta}"
				size="50" />
		</rich:column>
	</h:panelGrid>
	<!-- docs adjuntos -->
		<h:panelGrid id="pgAdjuntos" columns="3" >
				<rich:column width="120px">
					<h:outputText value="Verificación Externa"/>
				</rich:column>
				<rich:column>
					<h:outputText value=":"/>
				</rich:column>
				<rich:column >
						<h:panelGrid id="pgListDocAdjuntosVerificacion" columns="2" >

				<rich:column style="border:1px solid #17356f; background-color:#DEEBF5" width="60">
					<h:panelGrid>
			                <rich:panel>
								<h:panelGrid columns="3" id="panelInfoCorpAutCredito">
										<rich:column >Infocorp
										</rich:column>
										<rich:column >
											<h:inputText  
												size="40"
												readonly="true" 
												style="background-color: #BFBFBF;"/>
											<h:inputText rendered="#{not empty autorizacionPrestamoController.archivoInfoCorp}"
												value="#{autorizacionPrestamoController.archivoInfoCorp.strNombrearchivo}"
												size="40"
												readonly="true" 
												style="background-color: #BFBFBF;"/>
										</rich:column>
										<rich:column rendered="#{!autorizacionPrestamoController.blnBloquearXCuenta}">
											<a4j:commandButton
												rendered="#{empty autorizacionPrestamoController.archivoInfoCorp}"
						                		styleClass="btnEstilos"
						                		value="Adjuntar"
						                		reRender="pAdjuntarInfoCorpAutCredito"
						                		oncomplete="Richfaces.showModalPanel('pAdjuntarInfoCorpAutCredito')"
						                		style="width:80px"/>
						                	<a4j:commandButton
												rendered="#{not empty autorizacionPrestamoController.archivoInfoCorp}"
						                		styleClass="btnEstilos"
						                		value="Quitar"
						                		reRender="panelInfoCorpAutCredito, popupInfoCorpAutCredito,pgListDocAdjuntosVerificacion,pAdjuntarInfoCorpAutCredito"
						                		action="#{autorizacionPrestamoController.quitarInfoCorp}"
						                		style="width:80px"/>	
	
										</rich:column>
										<rich:column 
											rendered="#{(not empty autorizacionPrestamoController.archivoInfoCorp)}">
											<h:commandLink  value=" Descargar"	target="_blank"	
												actionListener="#{fileUploadController.descargarArchivo}">
												<f:attribute name="archivo" value="#{autorizacionPrestamoController.archivoInfoCorp}"/>		
											</h:commandLink>
										</rich:column>
									</h:panelGrid>
			                </rich:panel>
					</h:panelGrid>
				</rich:column>
				
				<!--  -->
				<rich:column style="border:1px solid #17356f; background-color:#DEEBF5">
					<h:panelGrid>
			                <rich:panel>
								<h:panelGrid columns="3" id="panelReniecAutCredito">
										<rich:column >Reniec
										</rich:column>
										<rich:column>
											<h:inputText  
												size="40"
												readonly="true" 
												style="background-color: #BFBFBF;"/>
											<h:inputText rendered="#{not empty autorizacionPrestamoController.archivoReniec}"
												value="#{autorizacionPrestamoController.archivoReniec.strNombrearchivo}"
												size="40"
												readonly="true" 
												style="background-color: #BFBFBF;"/>
										</rich:column>
										<rich:column rendered="#{!autorizacionPrestamoController.blnBloquearXCuenta}">
											<a4j:commandButton 
												rendered="#{empty autorizacionPrestamoController.archivoReniec}"
						                		styleClass="btnEstilos"
						                		value="Adjuntar"
						                		reRender="pAdjuntarReniecAutCredito"
						                		oncomplete="Richfaces.showModalPanel('pAdjuntarReniecAutCredito')"
						                		style="width:80px"/>
						                	<a4j:commandButton
												rendered="#{not empty autorizacionPrestamoController.archivoReniec}"
						                		styleClass="btnEstilos"
						                		value="Quitar"
						                		reRender="popupReniecAutCredito,panelReniecAutCredito,pgListDocAdjuntosVerificacion,pAdjuntarReniecAutCredito "
						                		action="#{autorizacionPrestamoController.quitarReniec}"
						                		style="width:80px"/>	
	
										</rich:column>
										<rich:column 
											rendered="#{not empty autorizacionPrestamoController.archivoReniec}">
											<h:commandLink  value=" Descargar"	target="_blank"	
												actionListener="#{fileUploadController.descargarArchivo}">
												<f:attribute name="archivo" value="#{autorizacionPrestamoController.archivoReniec}"/>		
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
				</rich:column>

		</h:panelGrid>
	


	
	<!-- docs adjuntos -->
</rich:panel>