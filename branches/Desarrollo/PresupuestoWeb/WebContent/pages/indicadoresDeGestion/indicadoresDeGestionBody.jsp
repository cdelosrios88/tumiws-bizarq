<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- EMPRESA   : COOPERATIVA TUMI         -->
<!-- AUTOR     : JUNIOR CHAVEZ VALVERDE   -->
<!-- MODULO    : PRESUPUESTO                    -->
<!-- PROTOTIPO : INDICADORES DE GESTION - BODY -->			
<!-- FECHA     : 10.10.2013               -->

<rich:modalPanel id="mpModificaEnProcesoDeGrabacion" width="600" height="150" 
	resizeable="false" style="background-color:#DEEBF5">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="Confirmar Modificación" styleClass="tamanioLetra"></h:outputText>
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<h:form id="frmModificaEnProcesoDeGrabacion">
		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5" >
			<h:panelGrid border="0">
				<h:outputText value="#{indicadoresDeGestionController.strMsgTxtExisteRegistro}" styleClass="tamanioLetra"/>
				<h:outputText value="¿Desea modificarlos?" styleClass="tamanioLetra"/>
			</h:panelGrid>
				<rich:spacer height="16px"/>
			<h:panelGrid columns="4" border="0" style="margin:0 auto; width: 100%">
				<a4j:commandButton value="Modificar" action="#{indicadoresDeGestionController.modificarEnProcesoGrabacion}" styleClass="btnEstilos1"
					reRender="rpProcesos" oncomplete="Richfaces.hideModalPanel('mpModificaEnProcesoDeGrabacion')"/>
				<a4j:commandButton value="Cancelar" 
					action="#{indicadoresDeGestionController.cancelarModificadion}" 
					styleClass="btnEstilos1"  reRender="rpProcesos"
					oncomplete="Richfaces.hideModalPanel('mpModificaEnProcesoDeGrabacion')"/>
			</h:panelGrid>
		</rich:panel>
	</h:form>
</rich:modalPanel>

<rich:modalPanel id="mpEditarIndicador" width="600" height="150"
	resizeable="false" style="background-color:#DEEBF5">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="Opciones" styleClass="tamanioLetra"></h:outputText>
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkIndicador"/>
			<rich:componentControl for="mpEditarIndicador" attachTo="hidelinkIndicador" operation="hide" event="onclick"/>
		</h:panelGroup>
	</f:facet>
	<h:form id="formEditIndicador">
		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5" >
			<h:panelGrid border="0">
				<h:outputText id="lblCodigo" value="Elija la opción que desee realizar..." styleClass="tamanioLetra"></h:outputText>
			</h:panelGrid>
			<rich:spacer height="16px"/>
			<h:panelGrid columns="4" border="0" style="margin:0 auto; width: 100%">
				<a4j:commandButton id="btnModificar" value="Modificar Indicador" action="#{indicadoresDeGestionController.modificarIndGestionPorIndicador}" 
				styleClass="btnEstilos1" reRender="opProcesarPorIndicador,btnGrb" oncomplete="Richfaces.hideModalPanel('mpEditarIndicador')"/>
				<a4j:commandButton id="btnEliminar" value="Eliminar Indicador" action="#{indicadoresDeGestionController.eliminarIndicador}" 
				styleClass="btnEstilos1" reRender="opProcesarPorIndicador" oncomplete="Richfaces.hideModalPanel('mpEditarIndicador')"/>
			</h:panelGrid>
		</rich:panel>
		<rich:spacer height="4px"/><rich:spacer height="8px"/>
	</h:form>    
</rich:modalPanel>

<a4j:include viewId="/pages/mensajes/mensajesPanel.jsp"/>

<a4j:region>
	<a4j:jsFunction name="selectedIndicador" action="#{indicadoresDeGestionController.setSelectedIndicador}">
		<f:param name="rowIndicador"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selectRbTipoInicador" reRender="cboTipIndGrb"
		action="#{indicadoresDeGestionController.disabledCboTipIndGrb}">
		<f:param name="pRbTipoInicador"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="selectRbSucursal" reRender="cboSucPanelEnBloque, cboSubSucPanelEnBloque"
		action="#{indicadoresDeGestionController.disabledCboSucursal}">
		<f:param name="pRbSucursal"></f:param>
	</a4j:jsFunction>
	
	<!-- Ajax para los combos de Sucursal - SubSucursal en los panels Por Indicador, En Bloque y Búsqueda -->
	<a4j:jsFunction name="getSubSucursalPanelEnBloque" reRender="cboSubSucPanelEnBloque"
		action="#{indicadoresDeGestionController.getListSubSucursalDeSucGrb}">
		<f:param name="pCboSucursal"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="getSubSucursalPanelPorIndicador" reRender="cboSubSucPanelPorIndicador"
		action="#{indicadoresDeGestionController.getListSubSucursalDeSucGrb}">
		<f:param name="pCboSucursal"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="getSubCucursalBusq" reRender="cboSubSucBusq"
		action="#{indicadoresDeGestionController.getListSubSucursalDeSucBusq}">
		<f:param name="pCboSucursalBusq"></f:param>
	</a4j:jsFunction>
	<!-- ------------------------------------------------------------------------------------------------ -->
	
	<a4j:jsFunction name="getAnioProyectado" reRender="txtAnioProyectado"
		action="#{indicadoresDeGestionController.getAnioProyectado}">
		<f:param name="pCboAnioBase"></f:param>
	</a4j:jsFunction>
</a4j:region>

<h:form onkeypress=" if (event.keyCode == 13) event.returnValue = false; " id="frmPrincipal">
	<h:outputLabel value="#{indicadoresDeGestionController.inicioPage}"/>
	<rich:panel id="rpPrincipal" style="border:0px solid #17356f;width:100%; background-color:#DEEBF5">
		<div align="center">
			<b>INDICADORES DE GESTIÓN</b>
		</div>
		<rich:spacer width="10px"/>
		<!-- FILTROS DE BUSQUEDA -->
		<h:panelGroup>
		
			<a4j:include viewId="/pages/indicadoresDeGestion/filtrosBusqueda.jsp"/>
			
			<rich:spacer height="8px"/>
			<h:panelGrid id="pgPanelControlIndicadores" columns="4">
				<rich:column width="1200px" style="text-align: left">
					<table>
						<tr>
							<td align="left">
								<a4j:commandButton value="En Bloque" styleClass="btnEstilos1" action="#{indicadoresDeGestionController.showPanelEnBloque}" 
									reRender="rpProcesos,btnGrb"/>
								<rich:spacer width="4px"/>
							</td>
							<td align="left">
								<a4j:commandButton value="Por Indicador" styleClass="btnEstilos1" action="#{indicadoresDeGestionController.showPanelPorIndicador}" 
									reRender="rpProcesos,btnGrb"/>
								<rich:spacer width="4px"/>
							</td>							
							<td align="left">
								<a4j:commandButton id="btnGrb" value="Grabar" styleClass="btnEstilos1" action="#{indicadoresDeGestionController.procesarEvento}" 
									reRender="rpProcesos,mpMessage,frmModificaEnProcesoDeGrabacion"
									disabled="#{indicadoresDeGestionController.blnDisabledGrabar}"
									oncomplete="if(#{not empty indicadoresDeGestionController.listaValidaIndicador}){Richfaces.showModalPanel('mpModificaEnProcesoDeGrabacion')}
												else
												if(#{indicadoresDeGestionController.intTipoGrabacion == applicationScope.Constante.PARAM_T_TIPOGRABACION_EN_BLOQUE && indicadoresDeGestionController.intErrorIndPorAnioBase == 1}){Richfaces.showModalPanel('mpMessage')}"/>
								<rich:spacer width="4px"/>
							</td>
						</tr>
					</table>
				</rich:column>
			</h:panelGrid>
			<rich:spacer height="8px"/>
			<rich:panel id="rpProcesos" style="border:0px solid #17356f;width:100%; background-color:#DEEBF5">

				<h:panelGrid id="pgMsgErrorIndicador">
					<h:outputText value="#{indicadoresDeGestionController.strMsgTxtPeriodo}" styleClass="msgError" />
					<h:outputText value="#{indicadoresDeGestionController.strMsgTxtMes}" styleClass="msgError" />
					<h:outputText value="#{indicadoresDeGestionController.strMsgTxtRangoMeses}" styleClass="msgError" />
					<h:outputText value="#{indicadoresDeGestionController.strMsgTxtPorcentajeCrecimiento}" styleClass="msgError" />
					<h:outputText value="#{indicadoresDeGestionController.strMsgTxtTipoIndicador}" styleClass="msgError" />
					<h:outputText value="#{indicadoresDeGestionController.strMsgTxtTipoValor}" styleClass="msgError" />
					<h:outputText value="#{indicadoresDeGestionController.strMsgTxtSucursal}" styleClass="msgError" />
					<h:outputText value="#{indicadoresDeGestionController.strMsgTxtSubSucursal}" styleClass="msgError" />
					<h:outputText value="#{indicadoresDeGestionController.strMsgTxtMonto}" styleClass="msgError" />
				</h:panelGrid>					
			
				<a4j:outputPanel id="opProcesarPorIndicador" >
					<rich:panel rendered="#{indicadoresDeGestionController.blnShowPanelTabPorIndicador}">
						<a4j:include viewId="/pages/indicadoresDeGestion/grabacion/porIndicador.jsp"/>
					</rich:panel>
				</a4j:outputPanel>

				<a4j:outputPanel id="opProcesarEnBloque" >
					<rich:panel rendered="#{indicadoresDeGestionController.blnShowPanelTabEnBloque}">
						<a4j:include viewId="/pages/indicadoresDeGestion/grabacion/enBloque.jsp"/>
					</rich:panel>
				</a4j:outputPanel>

			</rich:panel>
		</h:panelGroup>
	</rich:panel>
</h:form>

