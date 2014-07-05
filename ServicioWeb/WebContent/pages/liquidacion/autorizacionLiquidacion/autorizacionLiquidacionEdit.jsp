<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- Empresa   : Cooperativa Tumi         	-->
<!-- Modulo    : Autorización Crédito Edit  -->
<!-- Prototipo : Autorización de Crédito    -->
<!-- Fecha     : 10/09/2012               	-->

<%-- <rich:panel rendered="#{autorizacionLiquidacionController.formAutorizacionLiquidacionRendered}" --%>
<h:panelGrid id="panelMensaje">
	<h:outputText value="#{autorizacionLiquidacionController.strTxtMsgPerfil}"
		styleClass="msgError" />
	<h:outputText
		value="#{autorizacionLiquidacionController.strTxtMsgUsuario}"
		styleClass="msgError" />
	<h:outputText value="#{autorizacionLiquidacionController.strTxtMsgValidacion}"
		styleClass="msgError" />
	<h:outputText value="#{autorizacionLiquidacionController.strTxtMsgError}"
		styleClass="msgError" />		
		
</h:panelGrid>

<rich:panel	rendered="#{autorizacionLiquidacionController.formAutorizacionLiquidacionRendered}"
			style="width:960px;border:1px solid #17356f;background-color:#DEEBF5;">
	<a4j:commandLink value="Solicitud de Liquidación" target="_blank"
		reRender="mpSolicitudLiquidacionView, frmSolicitudLiquidacionView"
		actionListener="#{solicitudLiquidacionController.irModificarSolicitudLiquidacionAutoriza}"
		oncomplete="Richfaces.showModalPanel('mpSolicitudLiquidacionView')">
	</a4j:commandLink>
	
	<rich:spacer height="10"></rich:spacer>
	
	<rich:panel id="pgMsgErrorCuenta" style="border: 0px solid #17356f;background-color:#DEEBF5;" 
		rendered="#{autorizacionLiquidacionController.blnBloquearXCuenta}" style="text-align: center">
		<h:outputText value="#{autorizacionLiquidacionController.strMensajeValidacionCuenta}" styleClass="msgError4" style="text-align: center"/>
	</rich:panel>
	
	<h:panelGrid border="0" id="pgPersonasEncargadas">
		<rich:dataGrid
			value="#{autorizacionLiquidacionController.listaAutorizaLiquidacionComp}"
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
						value="#{item.autorizaLiquidacion.intPersUsuarioAutoriza} - #{item.persona.natural.strNombres} #{item.persona.natural.strApellidoPaterno} - DNI: #{item.persona.documento.strNumeroIdentidad} - #{item.strPerfil}" />
					<h:outputText value="Resultado : " style="font-weight:bold"
						styleClass="label" />
					<h:panelGroup>
						<tumih:outputText
							cache="#{applicationScope.Constante.PARAM_T_ESTADOAUTORIZACIONPRESTAMO}"
							itemValue="intIdDetalle" itemLabel="strDescripcion"
							property="#{item.autorizaLiquidacion.intParaEstadoAutorizar}" />.&nbsp; 				
			            <tumih:outputText
							cache="#{applicationScope.Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_RETIRO}"
							itemValue="intIdDetalle" itemLabel="strDescripcion"
							property="#{item.autorizaLiquidacion.intParaTipoAureobCod}" />
					</h:panelGroup>
					
					<h:outputText value="Observación : "
						style="font-weight:bold" styleClass="label" />
					<h:outputText
						value="#{item.autorizaLiquidacion.strObservacion}" />
				
				</h:panelGrid>
			</rich:panel>
		</rich:dataGrid>
	</h:panelGrid>



	
	<h:panelGrid border="0" rendered="true" style="width:900px;border:1px solid #17356f;background-color:#DEEBF5;">
		<h:panelGroup>
			<h:panelGrid>
				<rich:column>
					<h:outputText value="CAMBIOS EN LA SOLICITUD:" styleClass="estiloLetra1"></h:outputText>
				</rich:column>
				<rich:column>
				</rich:column>
				<rich:column>
				</rich:column>
			</h:panelGrid>
	
			<!-------------------------- INICIA DATOS DE CAMBIO  ---------------- -->

			<h:panelGroup id="pgCambiosEnSolicitud">
				<h:panelGrid columns="4">
					<rich:column width="120px">
						<h:outputText value="Tipo de Cambio :" />
					</rich:column>
					<rich:column>
						<h:outputText value="" />
					</rich:column>
					<rich:column width="150px">
						<h:selectOneMenu id="somTipoCambio" disabled="#{autorizacionLiquidacionController.blnBloquearXCuenta}"
							value="#{autorizacionLiquidacionController.intTipoCambio}"
							onchange="selecTipoCambio(#{applicationScope.Constante.ONCHANGE_VALUE})">
							<f:selectItem itemValue="0" itemLabel="Seleccione.." />
							<tumih:selectItems var="sel"
								value="#{autorizacionLiquidacionController.listaTipoCambio}"
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
								propertySort="intOrden" />
						</h:selectOneMenu>
					</rich:column>
				</h:panelGrid>
				<!----------------------------------- CAMBIO  DE FECHA  ----------------------------->
				<h:panelGrid id="pgCambioFecha" columns="4" rendered="#{autorizacionLiquidacionController.blnCambioFecha}">
					<rich:column width="120px">
						<h:outputText value="" />
					</rich:column>
					<rich:column>
						<h:outputText value="" />
					</rich:column>
					<rich:column width="150px">
						<rich:calendar datePattern="dd/MM/yyyy" inputStyle="width:76px" disabled="#{autorizacionLiquidacionController.blnBloquearXCuenta}"
										value="#{autorizacionLiquidacionController.dtNuevoFechaProgramacionPago}">
						</rich:calendar>
					</rich:column>
				</h:panelGrid>
					<!----------------------------------- CAMBIO  DE MOTIVO DE RENUINCIA   ----------------------------->
				<h:panelGrid id="pgMotivoRenuncia" columns="4" rendered="#{autorizacionLiquidacionController.blnCambioMotivoRenuncia}">
					<rich:column width="120px">
						<h:outputText value="" />
					</rich:column>
					<rich:column>
						<h:outputText value="" />
					</rich:column>
					<rich:column width="150px">
						<h:selectOneMenu value="#{autorizacionLiquidacionController.intNuevoMotivoRenuncia}" disabled="#{autorizacionLiquidacionController.blnBloquearXCuenta}"
										style="width: 200px;">
							<f:selectItem itemValue="0" itemLabel="Seleccione"/>
							<tumih:selectItems var="sel"
								value="#{autorizacionLiquidacionController.listaMotivoRenuncia}"
								itemValue="#{sel.intIdDetalle}"
								itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
					</rich:column>
				</h:panelGrid>
			<!----------------------------------- MOTIVO DE cambio   ----------------------------->
				<h:panelGrid columns="3">
					<rich:column width="120px">
						<h:outputText value="Motivo de Cambio :" />
					</rich:column>
					<rich:column>
						<h:outputText value="" />
					</rich:column>
							<h:inputText value="#{autorizacionLiquidacionController.strMotivoCambio}" size="100" disabled="#{autorizacionLiquidacionController.blnBloquearXCuenta}" />
					<rich:column>

					</rich:column>
				</h:panelGrid>
	
			</h:panelGroup>
			
			<rich:spacer height="20"></rich:spacer>
			
	
		</h:panelGroup>
	</h:panelGrid>
	
	<rich:spacer height="20"></rich:spacer>

	<h:panelGrid columns="4">
		<rich:column width="120px">
			<h:outputText value="Tipo de Autorización :" />
		</rich:column>
		<rich:column>
			<h:outputText value="" />
		</rich:column>
		<rich:column>
			<h:selectOneMenu
				value="#{autorizacionLiquidacionController.beanAutorizaLiquidacion.intParaEstadoAutorizar}"
				disabled="#{autorizacionLiquidacionController.blnBloquearXCuenta}"
				style="width: 170px;">
				<f:selectItem itemLabel="Seleccionar.." itemValue="0" />
				<tumih:selectItems var="sel"
					cache="#{applicationScope.Constante.PARAM_T_ESTADOAUTORIZACIONPRESTAMO}"
					itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
					propertySort="intOrden" />
			</h:selectOneMenu>
		</rich:column>
	</h:panelGrid>


	<h:panelGrid columns="3">
		<rich:column width="120px">
			<h:outputText value="Observación :" />
		</rich:column>
		<rich:column>
			<h:outputText value="" />
		</rich:column>
		<rich:column>
			<h:inputTextarea
				value="#{autorizacionLiquidacionController.beanAutorizaLiquidacion.strObservacion}"
				disabled="#{autorizacionLiquidacionController.blnBloquearXCuenta}"
				cols="150" rows="3" />
		</rich:column>
	</h:panelGrid>


	<!-- docs adjuntos -->
		<h:panelGrid id="pgAdjuntos" columns="3" >
				<rich:column width="120px">
					<h:outputText value="Archivos Adjuntos :"/>
				</rich:column>
				<rich:column>
					<h:outputText value=""/>
				</rich:column>
				<rich:column >

				</rich:column>

		</h:panelGrid>
	

	<h:panelGrid id="pgListDocAdjuntosVerificacionLiqui" columns="3" >
				<rich:column width="120px">
				</rich:column>
				<rich:column style="border:1px solid #17356f; background-color:#DEEBF5" width="380px">
					<h:panelGrid>
			                <rich:panel>
								<h:panelGrid columns="8" id="panelDeJuLiq">
										<rich:column >Declaración Jurada
										</rich:column>
										<rich:column >
											<h:inputText size="30" readonly="true" style="background-color: #BFBFBF;"/>
											<h:inputText rendered="#{not empty autorizacionLiquidacionController.archivoDeJu}"
														value="#{autorizacionLiquidacionController.archivoDeJu.strNombrearchivo}"
														size="30"
														readonly="true" 
														style="background-color: #BFBFBF;"/>
										</rich:column>
										<rich:column >
											<a4j:commandButton
												rendered="#{empty autorizacionLiquidacionController.archivoDeJu}"
						                		styleClass="btnEstilos"
						                		value="Adjuntar"
						                		disabled="#{autorizacionLiquidacionController.blnBloquearXCuenta}"
						                		reRender="pAdjuntarDeJu"
						                		oncomplete="Richfaces.showModalPanel('pAdjuntarDeJuLiq')"
						                		style="width:70px"/>
						                	<a4j:commandButton
												rendered="#{not empty autorizacionLiquidacionController.archivoDeJu}"
						                		styleClass="btnEstilos"
						                		value="Quitar"
						                		reRender="panelDeJuLiq, popupDeJuLiq"
						                		action="#{autorizacionLiquidacionController.quitarDeJu}"
						                		style="width:70px"/>	
	
										</rich:column>
										<rich:column 
											rendered="#{(not empty autorizacionLiquidacionController.archivoDeJu)}">
											<h:commandLink  value=" Descargar"	target="_blank"		
												actionListener="#{fileUploadController.descargarArchivo}">
												<f:attribute name="archivo" value="#{autorizacionLiquidacionController.archivoDeJu}"/>		
											</h:commandLink>
										</rich:column>
									</h:panelGrid>
			                </rich:panel>
					</h:panelGrid>
				</rich:column>
				
				<!--  -->
				<rich:column style="border:1px solid #17356f; background-color:#DEEBF5" width="380px">
					<h:panelGrid>
			                <rich:panel>
								<h:panelGrid columns="8" id="panelReniecAutLiq">
										<rich:column >Reniec
										</rich:column>
										<rich:column >
											<h:inputText  
												size="30"
												readonly="true" 
												style="background-color: #BFBFBF;"/>
											<h:inputText rendered="#{not empty autorizacionLiquidacionController.archivoReniec}"
												value="#{autorizacionLiquidacionController.archivoReniec.strNombrearchivo}"
												size="30"
												readonly="true" 
												style="background-color: #BFBFBF;"/>
										</rich:column>
										<rich:column >
											<a4j:commandButton
												rendered="#{empty autorizacionLiquidacionController.archivoReniec}"
						                		styleClass="btnEstilos"
						                		value="Adjuntar"
						                		disabled="#{autorizacionLiquidacionController.blnBloquearXCuenta}"
						                		reRender="pAdjuntarReniecAut"
						                		oncomplete="Richfaces.showModalPanel('pAdjuntarReniecAutLiq')"
						                		style="width:70px"/>
						                	<a4j:commandButton
												rendered="#{not empty autorizacionLiquidacionController.archivoReniec}"
						                		styleClass="btnEstilos"
						                		value="Quitar"
						                		reRender="panelReniecAutLiq, popupReniecLiq"
						                		action="#{autorizacionLiquidacionController.quitarReniecAut}"
						                		style="width:70px"/>	
	
										</rich:column>
										<rich:column 
											rendered="#{not empty autorizacionLiquidacionController.archivoReniec}">
											<h:commandLink  value=" Descargar"	target="_blank"
												actionListener="#{fileUploadController.descargarArchivo}">
												<f:attribute name="archivo" value="#{autorizacionLiquidacionController.archivoReniec}"/>		
											</h:commandLink>
										</rich:column>
									</h:panelGrid>
			                </rich:panel>
					</h:panelGrid>
				</rich:column>

		</h:panelGrid>
	
	<!-- docs adjuntos -->
</rich:panel>

