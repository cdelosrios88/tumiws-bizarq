<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- Empresa   : Cooperativa Tumi         		-->
<!-- Modulo    : Autorización Actividad Edit  	-->
<!-- Prototipo : Autorización de Actividad    	-->
<!-- Fecha     : 18/04/2013               		-->

	<h:panelGrid id="panelMensaje">
		<h:outputText value="#{autorizacionActividadController.strTxtMsgPerfil}" styleClass="msgError" />
		<h:outputText value="#{autorizacionActividadController.strTxtMsgUsuario}" styleClass="msgError" />
		<h:outputText value="#{autorizacionActividadController.strTxtMsgValidacion}" styleClass="msgError" />
		<h:outputText value="#{autorizacionActividadController.strTxtMsgError}" styleClass="msgError" />	
	</h:panelGrid>

	<rich:panel	rendered="#{autorizacionActividadController.formAutorizacionActividadRendered}"
				style="width:990px;border:1px solid #17356f;background-color:#DEEBF5;">
			<a4j:commandLink value="Solicitud de Actividad"
						reRender="mpSolicitudActividadView, frmSolicitudActividadView"
						actionListener="#{solicitudActividadController.irModificarSolicitudActividadAutoriza}"
						oncomplete="Richfaces.showModalPanel('mpSolicitudActividadView')">
			</a4j:commandLink>
		
		
			
		<h:panelGrid columns="4">
			<rich:column width="120px">
				<h:outputText value="" />
			</rich:column>
			<rich:column>
				<h:outputText value="" />
			</rich:column>
			<rich:column>
		<h:panelGrid border="0">
			<rich:dataGrid
				value="#{autorizacionActividadController.listaAutorizaActividadComp}"
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
								property="#{item.autorizaCredito.intParaEstadoAutorizar}" />.&nbsp;	   				
				            <tumih:outputText
								cache="#{applicationScope.Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_RETIRO}"
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
		</h:panelGrid>


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
				<h:selectOneMenu value="#{autorizacionActividadController.intParaEstadoAutorizar}"
									onchange="cargarComboTipoOperacionAut(#{applicationScope.Constante.ONCHANGE_VALUE})">
									<f:selectItem itemLabel="Seleccione.." itemValue="0" />
									<tumih:selectItems var="selec" 
													value="#{autorizacionActividadController.listaOperacionAutorizacion}"
													itemValue="#{selec.intIdDetalle}"
													itemLabel="#{selec.strDescripcion}"/>
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
	 
		<h:panelGrid columns="3" id="pgTipoOperacion" >
			<rich:column width="120px">
				<h:outputText value="Tipo de Operación" />
			</rich:column>
			<rich:column>
				<h:outputText value=":" />
			</rich:column>
			<rich:column>
				<h:selectOneMenu id="cboTipoOperacionAut"
					value="#{autorizacionActividadController.intParaTipoAureobCod}">
					<f:selectItem itemLabel="Seleccione.." itemValue="0" />
					<tumih:selectItems var="sel"
						value="#{autorizacionActividadController.listaTipoOperacionAut}"
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
					value="#{autorizacionActividadController.strObservacion}"
					cols="100" rows="3" />
			</rich:column>
		</h:panelGrid>
	
		
	</rich:panel>
